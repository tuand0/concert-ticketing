import {Injectable, signal} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {CommandeModel} from '../models/commande.model';

@Injectable({
  providedIn: 'root'
})
export class CommandeService {
  private readonly baseApiUrl = '/api/commandes';

  public constructor(private readonly http: HttpClient) {}

  public getCommandesByClient(clientId: number): Observable<CommandeModel[]> {
    return this.http.get<CommandeModel[]>(
      `${this.baseApiUrl}/client/${clientId}`
    );
  }
  
  public getCart(clientId: number): Observable<CommandeModel> {
    return this.http.get<CommandeModel>(
      `${this.baseApiUrl}/cart?clientId=${clientId}`
    );
  }

  public addConcertToCart(clientId: number, concertId: number): Observable<CommandeModel> {
    return this.http.post<CommandeModel>(
      `${this.baseApiUrl}/cart/concerts/${concertId}?clientId=${clientId}`,
      {}
    );
  }

  public removeTicketFromCart(clientId: number, ticketId: number): Observable<CommandeModel> {
    return this.http.delete<CommandeModel>(
      `${this.baseApiUrl}/cart/tickets/${ticketId}?clientId=${clientId}`
    );
  }

  public confirmCart(clientId: number): Observable<CommandeModel> {
    return this.http.post<CommandeModel>(
      `${this.baseApiUrl}/cart/confirm?clientId=${clientId}`,
      {}
    );
  }
}
