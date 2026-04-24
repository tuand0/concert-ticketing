export interface ConcertModel {
  id: number;
  titre: string;
  artiste: string;
  description: string;
  date: string;
  lieu: string;
  ville: string;
  genre: string;
  prix: number;
  capacite: number;
  statut: string;
}

export interface ApiResponse<T> {
  data: T;
  success: boolean;
  message?: string;
  error?: string;
}

export interface PaginatedResponse<T> {
  items: T[];
  total: number;
  page: number;
  pageSize: number;
  totalPages: number;
}

export interface ConcertFilter {
  category?: string;
  minPrice?: number;
  maxPrice?: number;
  inStock?: boolean;
  searchTerm?: string;
}
