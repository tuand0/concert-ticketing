import {Actions, createEffect, ofType} from '@ngrx/effects';
import {inject} from '@angular/core';
import {ConcertService} from '../../services/concert.service';
import {ConcertsActions} from './concerts.action';
import {catchError, map, of, switchMap} from 'rxjs';

export const allConcert$ = createEffect(
  (action$=inject(Actions), concertService = inject(ConcertService)) => {
    return action$.pipe(
      ofType(ConcertsActions.onConcertsNeeded),
      switchMap(() => concertService.getAll().pipe(
        map((concerts) => ConcertsActions.onConcertsCollected({concerts})),
        catchError((error => of(ConcertsActions.onConcertsCollectedFailed)))
      )),
    )
  },
  { functional: true }
)
