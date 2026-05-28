
import {ConcertsState} from './concerts.state';
import {createReducer, on} from '@ngrx/store';
import {ConcertsActions} from './concerts.action';

const initialState: ConcertsState = {
  allConcerts: undefined,
  detailedConcerts: undefined
}

export const concertsReducer = createReducer(
  initialState,
  on(ConcertsActions.onConcertsCollected, (state) => ({
    ...state,
    allConcerts: state.allConcerts
  })),
  on(ConcertsActions.onDetailConcertNeeded, (state, arg) => ({
    ...state,
    concertId: arg.concertsId
  }))
)
