import {Component, input, output, signal} from '@angular/core';
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
  public readonly concert = input.required<ConcertModel>();
  public readonly concertClick = output<ConcertModel>();

}
