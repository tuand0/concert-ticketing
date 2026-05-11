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
    const clientId = Number(this.authService.getCurrentUserId());

    this.commandeService.addConcertToCart(clientId, concertId).subscribe({
      next: () => {
        console.log('Concert added to cart');
      },
      error: err => {
        console.error('Add to cart failed:', err);
      }
    });
  }

  protected  addToWishlist() {
    // This would typically call a wishlist service
    console.log('Adding to wishlist:', this.concert()?.id);
    alert('concert added to wishlist!');
  }
}
