import {Routes} from '@angular/router';
import {ConcertDetail} from './concert-detail';

export const featureConcertDetailRoutes: Routes = [
  {
    path: ':id',
    component: ConcertDetail,
  },
];
