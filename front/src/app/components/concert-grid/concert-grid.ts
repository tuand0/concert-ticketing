import {Component, input, output} from '@angular/core';
import {ConcertModel} from '../../libs/models/concert.model';
import {ConcertCard} from '../concert-card/concert-card';

@Component({
  selector: 'app-concert-grid',
  imports: [
    ConcertCard
  ],
  templateUrl: './concert-grid.html',
  styleUrl: './concert-grid.css',
})
export class ConcertGrid {
  readonly concerts = input.required<ConcertModel[]>();
  readonly concertSelect = output<ConcertModel>();
}
