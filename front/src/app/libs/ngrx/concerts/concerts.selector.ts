

import {concertsFeatureKey, ConcertsState} from './concerts.state'
import {createFeatureSelector, createSelector} from '@ngrx/store';

const  selectConcertsFeature = createFeatureSelector<ConcertsState>(concertsFeatureKey)

export const ConcertsSelector = {
  getAllConcerts: createSelector(
    selectConcertsFeature,
    (state: ConcertsState) => state.allConcerts
  ),

  getDetailedConcerts: createSelector(
    selectConcertsFeature,
    (state: ConcertsState) => state.detailedConcerts
  )
}
