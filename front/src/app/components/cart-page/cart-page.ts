import {Component, OnInit} from '@angular/core';
import {CommandeModel} from '../../libs/models/commande.model';
import {CommandeService} from '../../libs/services/commande.service';
import {AuthService} from '../../libs/services/auth.service';

@Component({
  selector: 'app-cart-page',
  imports: [],
  templateUrl: './cart-page.html',
  styleUrl: './cart-page.css',
})
export class CartPageComponent implements OnInit {
  public pendingCommandes: CommandeModel[] = [];
  public paidCommandes: CommandeModel[] = [];
  public loading = false;

  public constructor(
    private readonly commandeService: CommandeService,
    private readonly authService: AuthService
  ) {}

  public ngOnInit(): void {
    this.loadCommandes();
  }

  public loadCommandes(): void {
    const clientId = Number(this.authService.getCurrentUserId());

    this.loading = true;

    this.commandeService.getCommandesByClient(clientId).subscribe({
      next: commandes => {
        this.pendingCommandes = commandes.filter(
          commande => commande.statut === 'EN_ATTENTE'
        );

        this.paidCommandes = commandes.filter(
          commande => commande.statut === 'PAYEE'
        );

        this.loading = false;
      },
      error: err => {
        console.error('Load commandes failed:', err);
        this.loading = false;
      }
    });
  }

  public removeTicket(ticketId: number): void {
    const clientId = Number(this.authService.getCurrentUserId());

    this.commandeService.removeTicketFromCart(clientId, ticketId).subscribe({
      next: () => {
        this.loadCommandes();
      },
      error: err => {
        console.error('Remove ticket failed:', err);
      }
    });
  }

  public confirmCart(): void {
    const clientId = Number(this.authService.getCurrentUserId());

    this.commandeService.confirmCart(clientId).subscribe({
      next: () => {
        this.loadCommandes();
      },
      error: err => {
        console.error('Confirm cart failed:', err);
      }
    });
  }

  public hasPendingTickets(): boolean {
    return this.pendingCommandes.some(
      commande => commande.tickets && commande.tickets.length > 0
    );
  }

  public hasPaidTickets(): boolean {
    return this.paidCommandes.some(
      commande => commande.tickets && commande.tickets.length > 0
    );
  }
}
