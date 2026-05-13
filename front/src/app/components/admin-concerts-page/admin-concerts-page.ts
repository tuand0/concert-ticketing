import {Component, OnInit} from '@angular/core';
import {ConcertModel} from '../../libs/models/concert.model';
import {ConcertService} from '../../libs/services/concert.service';

@Component({
  selector: 'app-admin-concerts-page',
  imports: [],
  templateUrl: './admin-concerts-page.html',
  styleUrl: './admin-concerts-page.css',
})
export class AdminConcertsPageComponent implements OnInit {
  protected allConcerts: ConcertModel[] = [];
  protected publishedConcerts: ConcertModel[] = [];
  protected draftConcerts: ConcertModel[] = [];
  protected cancelledConcerts: ConcertModel[] = [];
  protected loading = false;

  public constructor(
    private readonly concertService: ConcertService
  ) {}

  public ngOnInit(): void {
    this.loadConcerts();
  }

  protected loadConcerts(): void {
    this.loading = true;

    this.concertService.getAll().subscribe({
      next: concerts => {
        this.allConcerts = concerts;

        this.publishedConcerts = concerts.filter(c => c.statut === 'PUBLIE');
        this.draftConcerts = concerts.filter(c => c.statut === 'BROUILLON');
        this.cancelledConcerts = concerts.filter(c => c.statut === 'ANNULE');

        this.loading = false;
      },
      error: err => {
        window.alert(
          'Failed to load concert.'
        );
        this.loading = false;
      }
    });
  }

  protected publishConcert(concertId: number): void {
    this.concertService.updateConcertStatut(concertId, 'PUBLIE').subscribe({
      next: () => this.loadConcerts(),
      error: err => {
        window.alert(
          'Failed to publish concert.'
        );
      }
    });
  }

  protected cancelConcert(concertId: number): void {
    this.concertService.updateConcertStatut(concertId, 'ANNULE').subscribe({
      next: () => this.loadConcerts(),
      error: err => {
        window.alert(
          'Failed to calcel concert.'
        );
      }
    });
  }

  protected deleteConcert(concertId: number): void {
    const confirmed = window.confirm(
      'Are you sure? If this concert already has tickets, it will be cancelled instead of deleted.'
    );

    if (!confirmed) {
      return;
    }

    this.concertService.deleteConcert(concertId).subscribe({
      next: () => this.loadConcerts(),
      error: err => {
        window.alert(
          'Failed to delete concert. Annule concert & inform client about their ticket'
        );
      }
    });
  }
}
