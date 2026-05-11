import {TicketModel} from './ticket.model';

export interface CommandeModel {
  id: number;
  dateCommande: string;
  montantTotal: number;
  statut: string;
  modePaiement?: string;
  tickets: TicketModel[];
}
