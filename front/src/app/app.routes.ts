import { Routes } from '@angular/router';
import {Login} from './components/login/login';
import {featureConcertDetailRoutes} from './components/concert-detail/feature-concert-detail.route';
import {ConcertList} from './components/concert-list/concert-list';
import {authenticatedGuard} from './libs/guards/authenticated-guard';
import {authorizedGuard} from './libs/guards/authorized-guard';
import {ManageConcerts} from './components/manage-concerts/manage-concerts';
import {CartPageComponent} from './components/cart-page/cart-page';

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
    path: 'manage',
    canActivateChild: [authenticatedGuard, authorizedGuard],
    data: {
      roles: ['administrateur']
    },
    children: [
      {
        path: '',
        component: ManageConcerts
      }
    ]
  },
  {
    path: 'cart',
    component: CartPageComponent
  },
  {
    path: '**',
    redirectTo: 'concerts',
  },
];
