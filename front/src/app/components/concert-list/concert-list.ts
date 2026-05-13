import {Component, inject, signal, computed, ChangeDetectionStrategy, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import {ConcertModel, ConcertFilter} from '../../libs/models/concert.model';
import {ConcertService} from '../../libs/services/concert.service';
import {ConcertGrid} from '../concert-grid/concert-grid';
import {ErrorMessage} from '../error-message/error-message';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {debounceTime, Subject} from 'rxjs';
import { ButtonModule } from 'primeng/button';
import { CreateConcertCard } from '../create-concert-card/create-concert-card';
import {AuthService} from '../../libs/services/auth.service';

@Component({
  standalone: true,
  selector: 'app-concert-list',
  imports: [CommonModule,
    FormsModule,
    ConcertGrid,
    CreateConcertCard,
    ErrorMessage,
    ButtonModule
  ],
  templateUrl: './concert-list.html',
  styleUrl: './concert-list.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ConcertList implements OnInit {

  private readonly concertService = inject(ConcertService);
  private readonly router = inject(Router);
  private readonly searchSubject = new Subject<void>();
  public readonly authService = inject(AuthService);

  // State signals
  public concerts = signal<ConcertModel[]>([]);
  readonly totalconcerts = signal(0);
  readonly currentPage = signal(1);
  readonly totalPages = signal(0);
  readonly genres = signal<String[]>([]);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);
  readonly pageSize = signal(12);
  readonly allFilters = signal(false);

  // Filter state
  searchTerm = '';
  selectedGenre = '';
  public inStockOnly = false;
  selectedArtiste = '';
  selectedVille = '';
  selectedLieu = '';
  prixMin?: number;
  prixMax?: number;
  dateMin = '';
  dateMax = '';

  // Computed values
  readonly hasMorePages = computed(() => this.totalPages() > 1);

  public ngOnInit(): void {
    this.loadGenres();

    this.searchSubject
      .pipe(debounceTime(400))
      .subscribe(() => {
        this.currentPage.set(1);
        this.loadConcerts();
      });

    this.loadConcerts();
  }

  protected showAllFilters():void {
    this.allFilters.update(value => !value);
  }

  protected loadGenres(): void {
    this.concertService.getAll().subscribe({
      next: concerts => {
        const genres = [...new Set(concerts.map(concert => concert.genre))];
        this.genres.set(genres);
      },
      error: err => console.error('Error loading genres:', err),
    });
  }

  protected loadConcerts(): void {
    this.loading.set(true);
    this.error.set(null);

    const filter: ConcertFilter = {};

    // Search by title
    if (this.searchTerm.trim()) {
      filter.titre = this.searchTerm.trim();
    }

    // Genre filter
    if (this.selectedGenre) {
      filter.genre = this.selectedGenre.trim();
    }

    // Optional future filters
    if (this.selectedVille?.trim()) {
      filter.ville = this.selectedVille.trim();
    }

    if (this.selectedArtiste?.trim()) {
      filter.artiste = this.selectedArtiste.trim();
    }

    if (this.selectedLieu?.trim()) {
      filter.lieu = this.selectedLieu.trim();
    }

    if (this.inStockOnly) {
      filter.inStock = true;
    }

    if (this.prixMin !== undefined && this.prixMin !== null) {
      filter.prixMin = this.prixMin;
    }

    if (this.prixMax !== undefined && this.prixMax !== null) {
      filter.prixMax = this.prixMax;
    }

    this.concertService
      .searchConcerts(
        filter,
        this.currentPage(),
        this.pageSize()
      )
      .subscribe({
        next: response => {
          this.concerts.set(response.items);
          this.totalconcerts.set(response.total);
          this.totalPages.set(response.totalPages);
          this.loading.set(false);
        },

        error: err => {
          this.error.set(
            'Failed to load concerts. Please try again.'
          );

          this.loading.set(false);

          console.error('Error loading concerts:', err);
        },
      });
  }

  public onPageSizeChange(value: string): void {
    this.pageSize.set(Number(value));
    this.currentPage.set(1);
    this.loadConcerts();
  }

  public onSearchChange() {
    this.searchSubject.next();
  }

  public onFilterChange() {
    this.currentPage.set(1);
    this.loadConcerts();
  }

  public async onConcertSelect(
    concert: ConcertModel
  ): Promise<void> {

    await this.router.navigate([
      '/concerts',
      concert.id
    ]);
  }

  public nextPage():void {
    if (this.currentPage() < this.totalPages()) {
      this.currentPage.update(page => page + 1);
      this.loadConcerts();
    }
  }

  public previousPage() {
    if (this.currentPage() > 1) {
      this.currentPage.update(page => page - 1);
      this.loadConcerts();
    }
  }
}
