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
  public draftConcerts: ConcertModel[] = [];
  public loading = false;
  public errorMessage = '';

  public constructor(
    private readonly concertService: ConcertService
  ) {}

  public ngOnInit(): void {
    this.loadDraftConcerts();
  }

  public loadDraftConcerts(): void {
    this.loading = true;
    this.errorMessage = '';

    this.concertService.getDraftConcerts().subscribe({
      next: concerts => {
        this.draftConcerts = concerts;
        this.loading = false;
      },
      error: err => {
        console.error('Load draft concerts failed:', err);
        this.errorMessage = 'Failed to load draft concerts.';
        this.loading = false;
      }
    });
  }

  public publishConcert(concertId: number): void {
    this.concertService.updateConcertStatut(concertId, 'PUBLIE').subscribe({
      next: () => {
        this.loadDraftConcerts();
      },
      error: err => {
        console.error('Publish concert failed:', err);
        this.errorMessage = 'Failed to publish concert.';
      }
    });
  }

  public cancelConcert(concertId: number): void {
    this.concertService.updateConcertStatut(concertId, 'ANNULE').subscribe({
      next: () => {
        this.loadDraftConcerts();
      },
      error: err => {
        console.error('Cancel concert failed:', err);
        this.errorMessage = 'Failed to cancel concert.';
      }
    });
  }
}
