import { Component, input, output, ChangeDetectionStrategy } from '@angular/core';
import {Concert} from '../../libs/event.model';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-event-card',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './event-card.html',
  styleUrl: './event-card.css',
})
export class EventCard {

  readonly concert = input.required<Concert>();
  readonly concertClick = output<Concert>();

}
