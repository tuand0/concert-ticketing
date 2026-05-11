import {ConcertModel} from './concert.model';

export interface TicketModel {
  id: number;
  prix: number;
  dateAchat: string;
  statut: string;
  concertId: number;
  concertTitre: string;
  concertArtiste: string;
  concertDate: string;
  concertLieu: string;
  concertVille: string;
}
