import { Component, inject, signal, computed, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { Router } from '@angular/router';
import {ConcertModel, ConcertFilter} from '../../libs/models/concert.model';
import {ConcertService} from '../../libs/services/concert.service';
import {ConcertGrid} from '../concert-grid/concert-grid';
import {ErrorMessage} from '../error-message/error-message';
import {LoadingSpinner} from '../loading-spinner/loading-spinner';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-concert-list',
  imports: [CommonModule, FormsModule, ConcertGrid , ErrorMessage, LoadingSpinner],
  templateUrl: './concert-list.html',
  styleUrl: './concert-list.css',
})
export class ConcertList {

  private readonly concertService = inject(ConcertService);
  private readonly router = inject(Router);

  // State signals
  public concerts = signal<ConcertModel[]>([]);
  readonly totalconcerts = signal(0);
  readonly currentPage = signal(1);
  readonly totalPages = signal(0);
  readonly genres = signal<String[]>([]);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  // Filter state
  searchTerm = '';
  selectedGenre = '';
  inStockOnly = false;

  // Computed values
  readonly hasMorePages = computed(() => this.totalPages() > 1);

  ngOnInit() {
    this.loadGenres();
    this.loadConcerts();
  }

  public loadGenres(): void {
    this.concertService.getAll().subscribe({
      next: concerts => {
        const genres = [...new Set(concerts.map(concert => concert.genre))];
        this.genres.set(genres);
      },
      error: err => console.error('Error loading genres:', err),
    });
  }

  protected loadConcerts() {
    this.loading.set(true);
    this.error.set(null);

    const filter: ConcertFilter = {};

    if (this.searchTerm) {
      filter.titre = this.searchTerm;
    }
    if (this.selectedGenre) {
      filter.genre = this.selectedGenre;
    }
    if (this.inStockOnly) {
      filter.inStock = true;
    }

    this.concertService.searchConcerts(filter, this.currentPage(), 12).subscribe({
      next: (response: { items: ConcertModel[]; total: number; totalPages: number; }) => {
        this.concerts.set(response.items);
        this.totalconcerts.set(response.total);
        this.totalPages.set(response.totalPages);
        this.loading.set(false);
      },
      error: (err: any) => {
        this.error.set('Failed to load concerts. Please try again.');
        this.loading.set(false);
        console.error('Error loading concerts:', err);
      },
    });
  }

  public onSearchChange() {
    this.currentPage.set(1);
    this.loadConcerts();
  }

  public onFilterChange() {
    this.currentPage.set(1);
    this.loadConcerts();
  }

  public onConcertSelect(concert: ConcertModel) {
    this.router.navigate(['/concerts', concert.id]);
  }

  public nextPage() {
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
