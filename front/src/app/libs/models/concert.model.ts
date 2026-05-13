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
  imageUrl: string;
  inStock: boolean;
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
  titre?: string;
  artiste?: string;
  ville?: string;
  lieu?: string;
  genre?: string;
  statut?: string;
  prixMin?: number;
  prixMax?: number;
  inStock?: boolean;
}
