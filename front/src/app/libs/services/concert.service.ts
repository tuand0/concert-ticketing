import {Injectable, signal} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable, of } from 'rxjs';
import {ConcertFilter, ConcertModel, PaginatedResponse} from '../models/concert.model';
import {ConcertEntity} from '../entities/concert.entity';
import { ConcertMapper} from '../mappers/concert.mapper';
import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ConcertService {
  private readonly baseApiUrl = 'http://localhost:4200/api'

  // Signals for state management
  private readonly loadingSignal = signal(false);
  private readonly errorSignal = signal<string | null>(null);

  readonly loading = this.loadingSignal.asReadonly();
  readonly error = this.errorSignal.asReadonly();

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
    return this.http.get<ConcertEntity>(`${this.baseApiUrl}/concerts/${id}`)
      .pipe(
        map(entity => this.concertMapper.mapToModel(entity))
      );
  }

  public searchConcerts(
    filter?: ConcertFilter,
    page = 1,
    pageSize = 12
  ): Observable<PaginatedResponse<ConcertModel>> {
    this.loadingSignal.set(true);
    this.errorSignal.set(null);

    let params = new HttpParams();

    if (filter) {
      if (filter.titre) {
        params = params.set('titre', filter.titre);
      }
      if (filter.artiste) {
        params = params.set('artiste', filter.artiste);
      }
      if (filter.ville) {
        params = params.set('ville', filter.ville);
      }
      if (filter.lieu) {
        params = params.set('lieu', filter.lieu);
      }
      if (filter.genre) {
        params = params.set('genre', filter.genre);
      }
      if (filter.statut) {
        params = params.set('statut', filter.statut);
      }
      if (filter.dateMin) {
        params = params.set('dateMin', filter.dateMin);
      }
      if (filter.dateMax) {
        params = params.set('dateMax', filter.dateMax);
      }
      if (filter.prixMin !== undefined) {
        params = params.set('prixMin', filter.prixMin.toString());
      }
      if (filter.prixMax !== undefined) {
        params = params.set('prixMax', filter.prixMax.toString());
      }
    }

    return this.http
      .get<ConcertEntity[]>(`${this.baseApiUrl}/concerts`, { params })
      .pipe(
        map((entities) => {
          this.loadingSignal.set(false);

          const allConcerts = entities.map((entity) =>
            this.concertMapper.mapToModel(entity)
          );

          const startIndex = (page - 1) * pageSize;
          const endIndex = startIndex + pageSize;
          const pagedConcerts = allConcerts.slice(startIndex, endIndex);

          return {
            items: pagedConcerts,
            total: allConcerts.length,
            page,
            pageSize,
            totalPages: Math.ceil(allConcerts.length / pageSize),
          };
        }),
        catchError((error) => {
          this.loadingSignal.set(false);
          this.errorSignal.set(
            error.message || 'An error occurred while loading concerts'
          );
          console.error('Error loading concerts:', error);

          return of({
            items: [],
            total: 0,
            page: 1,
            pageSize,
            totalPages: 0,
          });
        })
      );
  }

  public createConcert(
    concert: Partial<ConcertModel>
  ): Observable<void> {

    return this.http.post<void>(
      `${this.baseApiUrl}/concerts`,
      concert
    );
  }
}
