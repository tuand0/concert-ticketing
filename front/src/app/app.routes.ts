import { Routes } from '@angular/router';
import {Login} from './components/login/login';
import {ConcertGrid} from './components/concert-grid/concert-grid';
import {ConcertCard} from './components/concert-card/concert-card';

export const routes: Routes = [
    {
    path: '**',
    redirectTo: 'concerts',
    pathMatch: 'full',
  },
  {
    path: 'concerts',
    children: [
      {
        path: '',
        component: ConcertCard,
      }
    ]
  },
  {
    path: 'login',
    component: Login,
    pathMatch: 'full',
  },
];
