import { Routes } from '@angular/router';
import { EventCard } from './components/event-card/event-card';
import {Login} from './components/login/login';

export const routes: Routes = [
    {
    path: '**',
    redirectTo: 'events',
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
  {
    path: 'login',
    component: Login,
    pathMatch: 'full',
  },
];
