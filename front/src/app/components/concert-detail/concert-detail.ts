import {Component, inject, signal} from '@angular/core';
import {ActivatedRoute, Router, RouterModule} from '@angular/router';
import {ConcertService} from '../../libs/services/concert.service';
import {ConcertModel} from '../../libs/models/concert.model';
import {ErrorMessage} from '../error-message/error-message';
import {LoadingSpinner} from '../loading-spinner/loading-spinner';
import {AuthService} from '../../libs/services/auth.service';
import {CommandeService} from '../../libs/services/commande.service';

@Component({
  selector: 'app-concert-detail',
  imports: [
    RouterModule,
    ErrorMessage,
    LoadingSpinner
  ],
  templateUrl: './concert-detail.html',
  styleUrl: './concert-detail.css',
})
export class ConcertDetail {

  private readonly route = inject(ActivatedRoute);
  private readonly concertsService = inject(ConcertService);
  public pendingTicketCountByConcertId = new Map<number, number>();
  public addingToCartIds = new Set<number>();

  // State signals
  readonly concert = signal<ConcertModel | null>(null);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  public constructor(
    private readonly commandeService: CommandeService,
    private readonly authService: AuthService
  )
  {
    this.loadconcert();
    this.loadPendingTicketsInCart()
  }

  private loadPendingTicketsInCart(): void {
    const clientId = Number(this.authService.getCurrentUserId());

    this.commandeService.getCommandesByClient(clientId).subscribe({
      next: commandes => {
        const pendingCommandes = commandes.filter(
          commande => commande.statut === 'EN_ATTENTE'
        );

        const countByConcertId = new Map<number, number>();

        for (const commande of pendingCommandes) {
          for (const ticket of commande.tickets ?? []) {
            const currentCount = countByConcertId.get(ticket.concertId) ?? 0;
            countByConcertId.set(ticket.concertId, currentCount + 1);
          }
        }

        this.pendingTicketCountByConcertId = countByConcertId;
      },
      error: err => {
        console.error('Load pending tickets failed:', err);
      }
    });
  }

  protected loadconcert() {
    const concertIdString = this.route.snapshot.paramMap.get('id');
    const concertId = Number(concertIdString);

    if (!concertIdString) {
      this.error.set('concert ID not provided');
      return;
    }

    this.loading.set(true);
    this.error.set(null);

    this.concertsService.getOne(concertId).subscribe({
      next: (concert) => {
        if (concert) {
          this.concert.set(concert);
        } else {
          this.error.set('concert not found');
        }
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set('Failed to load concert details');
        this.loading.set(false);
        console.error('Error loading concert:', err);
      },
    });
  }

  protected addToCart(concertId: number): void {
    if (this.isAddingToCart(concertId)) {
      return;
    }

    if (this.getRemainingTickets() <= 0) {
      return;
    }

    this.addingToCartIds.add(concertId);

    const clientId = Number(this.authService.getCurrentUserId());

    this.commandeService.addConcertToCart(clientId, concertId).subscribe({
      next: () => {
        this.addingToCartIds.delete(concertId);
        this.loadPendingTicketsInCart();
      },
      error: err => {
        console.error('Add to cart failed:', err);
        this.addingToCartIds.delete(concertId);
      }
    });
  }

  protected getTicketCountInCart(concertId: number): number {
    return this.pendingTicketCountByConcertId.get(concertId) ?? 0;
  }

  protected getRemainingTickets(): number {
    const currentConcert = this.concert();

    if (!currentConcert) {
      return 0;
    }

    const alreadyInCart = this.getTicketCountInCart(currentConcert.id);

    return Math.max(currentConcert.capacite - alreadyInCart, 0);
  }

  protected isAddingToCart(concertId: number): boolean {
    return this.addingToCartIds.has(concertId);
  }
}
