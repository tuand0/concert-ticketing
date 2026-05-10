// concert.mapper.ts
import { Injectable } from '@angular/core';
import {ConcertModel} from '../models/concert.model';
import {ConcertEntity} from '../entities/concert.entity';

@Injectable({ providedIn: 'root' })
export class ConcertMapper {
  public mapToModel(entity: ConcertEntity): ConcertModel {
    return {
      ...entity,
      inStock: entity.inStock,
      imageUrl: `https://placehold.co/300x300?text=${encodeURIComponent(entity.titre)}`
    };
  }

  public mapToEntity(model: ConcertModel): ConcertEntity {
    return {
      ...model,
    };
  }
}
