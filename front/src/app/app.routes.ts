import { Routes } from '@angular/router';
import {Login} from './components/login/login';
import {featureConcertDetailRoutes} from './components/concert-detail/feature-concert-detail.route';
import {ConcertList} from './components/concert-list/concert-list';

export const routes: Routes = [
  {
    path: 'concerts',
    children: [
      {
        path: '',
        component: ConcertList,
      },
      ...featureConcertDetailRoutes,
    ],
  },
  {
    path: 'login',
    component: Login,
    pathMatch: 'full',
  },
  {
    path: '',
    redirectTo: 'concerts',
    pathMatch: 'full',
  },
  {
    path: '**',
    redirectTo: 'concerts',
  },
];
