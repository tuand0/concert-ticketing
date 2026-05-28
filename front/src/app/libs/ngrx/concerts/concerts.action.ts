import { createAction, props } from '@ngrx/store';
import {ConcertModel} from '../../models/concert.model';

export const ConcertsActions = {
  onConcertsNeeded: createAction(
    '[Concerts] Concerts Needed',
  ),

  onConcertsCollectedFailed: createAction(
    '[Concerts] Collect concerts failed',
  ),

  onConcertsCollected: createAction(
    '[Concerts] All Concerts Collected',
    props<{ concerts: ConcertModel[] }>()
  ),

  onDetailConcertNeeded: createAction(
    '[Concerts] A specific concert Needed',
    props<{ concertsId: string }>()
  ),
}
