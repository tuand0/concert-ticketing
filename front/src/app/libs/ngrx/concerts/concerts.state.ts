import {ConcertModel} from '../../models/concert.model';

export interface ConcertsState {
  allConcerts: ConcertModel[] | undefined
  detailedConcerts: ConcertModel | undefined
}

export const concertsFeatureKey = 'concerts';
