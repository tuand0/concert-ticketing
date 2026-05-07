import {Component, input, output, signal} from '@angular/core';
import {ConcertModel} from '../../libs/models/concert.model';
import {ConcertCard} from '../concert-card/concert-card';
import {CommonModule} from '@angular/common';
import {ConcertService} from '../../libs/services/concert.service';

@Component({
  selector: 'app-concert-grid',
  imports: [
    CommonModule,
    ConcertCard
  ],
  templateUrl: './concert-grid.html',
  styleUrl: './concert-grid.css',
})
export class ConcertGrid {
  readonly concerts = input.required<ConcertModel[]>();
  readonly concertSelect = output<ConcertModel>();

  constructor(private readonly concertService: ConcertService) {}
}
