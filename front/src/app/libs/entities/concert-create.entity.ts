export interface ConcertCreateEntity {
  organisateurId: number;
  titre: string;
  artiste: string;
  lieu: string;
  ville: string;
  description?: string;
  dateTime: string;
  prix: number;
  capacite: number;
  genre: string;
}
