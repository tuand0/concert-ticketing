import { Routes } from '@angular/router';
import {Login} from './components/login/login';
import {featureConcertDetailRoutes} from './components/concert-detail/feature-concert-detail.route';
import {ConcertList} from './components/concert-list/concert-list';
import {CartPageComponent} from './components/cart-page/cart-page';
import {AdminConcertsPageComponent} from './components/admin-concerts-page/admin-concerts-page';
import {adminGuard} from './libs/guards/admin.guard';
import {clientGuard} from './libs/guards/client.guard';

export const routes: Routes = [
  {
    path: 'admin/concerts',
    canActivate: [adminGuard],
    component: AdminConcertsPageComponent
  },
  {
    path: 'cart',
    canActivate: [clientGuard],
    component: CartPageComponent
  },
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
