import {Component, inject} from '@angular/core';
import {RouterModule, RouterOutlet} from '@angular/router';
import {AuthService} from './libs/services/auth.service';
import { ButtonModule } from 'primeng/button';
import {MatDialog} from '@angular/material/dialog';
import {NotificationDialog} from './components/notification-dialog/notification-dialog';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule, ButtonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Concert-ticketing Shop';
  protected readonly authService = inject(AuthService)

  public constructor(
    private readonly dialog: MatDialog,
  ) {}

  protected openNotifications(): void {
    this.dialog.open(NotificationDialog, {
      width: '560px',
      maxWidth: '95vw',
      autoFocus: false,
    });
  }
}
