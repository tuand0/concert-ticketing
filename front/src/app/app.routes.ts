import { Routes } from '@angular/router';
import { EventCard } from './components/event-card/event-card';

export const routes: Routes = [
    {
    path: 'events',
    redirectTo: 'artistes',
    pathMatch: 'full',
  },
  {
    path: 'events',
    children: [
      {
        path: '',
        component: EventCard,
      }
    ]
  },
];
