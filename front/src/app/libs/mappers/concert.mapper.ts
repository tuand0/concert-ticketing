// concert.mapper.ts
import { Injectable } from '@angular/core';
import {ConcertModel} from '../models/concert.model';
import {ConcertEntity} from '../entities/concert.entity';

@Injectable({ providedIn: 'root' })
export class ConcertMapper {
  public mapToModel(entity: ConcertEntity): ConcertModel {
    return {
      ...entity,
    };
  }
}
