import { Component, input, output } from '@angular/core';
import {EventCard} from '../event-card/event-card';
import {Concert} from '../../libs/event.model';

@Component({
  selector: 'app-event-grid',
  imports: [
    EventCard
  ],
  templateUrl: './event-grid.html',
  styleUrl: './event-grid.css',
})
export class EventGrid {
  readonly concerts = input.required<Concert[]>();
  readonly concertSelect = output<Concert>();
}
