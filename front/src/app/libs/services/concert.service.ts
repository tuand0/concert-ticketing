import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {map, Observable} from 'rxjs';
import { ConcertModel } from '../models/concert.model';
import {ConcertEntity} from '../entities/concert.entity';
import { ConcertMapper} from '../mappers/concert.mapper';

@Injectable({
  providedIn: 'root'
})
export class ConcertService {
  private readonly baseApiUrl = 'http://localhost:4200/api'

  public constructor(
    private readonly http: HttpClient,
    private readonly concertMapper: ConcertMapper
  ) {}

  public getAll(): Observable<ConcertModel[]> {
    return this.http.get<ConcertEntity[]>(`${this.baseApiUrl}/concerts`)
      .pipe(
        map(entities => entities.map(entity => this.concertMapper.mapToModel(entity)))
      );
  }

  public getOne(id: number): Observable<ConcertModel> {
    return this.http.get<ConcertEntity>(`${this.baseApiUrl}/Concerts/${id}`)
      .pipe(
        map(entity => this.concertMapper.mapToModel(entity))
      );
  }
}
