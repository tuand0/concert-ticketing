import {Component, input, output} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ConcertService} from '../../libs/services/concert.service';
import {ConcertModel} from '../../libs/models/concert.model';

@Component({
  selector: 'app-concert-card',
  imports: [CommonModule],
  templateUrl: './concert-card.html',
  styleUrl: './concert-card.css',
})
export class ConcertCard {
  concerts: ConcertModel[] = [];
  loading = false;
  error = '';
  readonly concert = input.required<ConcertModel>();
  readonly concertClick = output<ConcertModel>();

  constructor(private concertService: ConcertService) {}

  ngOnInit(): void {
    this.concertService.getAll().subscribe(concerts => {
      this.concerts = concerts;
    });
  }

  protected loadConcerts(): void {
    this.loading = true;

    this.concertService.getAll().subscribe({
      next: (data) => {
        this.concerts = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Impossible de charger les concerts';
        this.loading = false;
      }
    });
  }
}
