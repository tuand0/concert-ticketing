import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs';
import {NotificationModel} from '../models/notification.model';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private readonly baseApiUrl = '/api/notifications';

  public constructor(private readonly http: HttpClient) {}

  public getMyNotifications(userId: number): Observable<NotificationModel[]> {
    return this.http.get<NotificationModel[]>(
      `${this.baseApiUrl}/user/${userId}`
    );
  }
}
