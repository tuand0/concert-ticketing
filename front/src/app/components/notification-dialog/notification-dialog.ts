import { Component, OnInit } from '@angular/core';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { NotificationService } from '../../libs/services/notification.service';
import { AuthService } from '../../libs/services/auth.service';
import { NotificationModel } from '../../libs/models/notification.model';

@Component({
  selector: 'app-notification-dialog',
  imports: [CommonModule,
    MatDialogModule],
  templateUrl: './notification-dialog.html',
  styleUrl: './notification-dialog.css',
})
export class NotificationDialog implements OnInit {
  public notifications: NotificationModel[] = [];
  public loading = false;
  public errorMessage = '';

  public constructor(
    private readonly dialogRef: MatDialogRef<NotificationDialog>,
    private readonly notificationService: NotificationService,
    private readonly authService: AuthService,
  ) {}

  public ngOnInit(): void {
    this.loadNotifications();
  }

  public loadNotifications(): void {
    const userId = this.authService.getCurrentUserId();

    if (!userId) {
      this.errorMessage = 'User not found.';
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.notificationService.getMyNotifications(userId).subscribe({
      next: notifications => {
        this.notifications = notifications;
        this.loading = false;
      },
      error: err => {
        console.error('Load notifications failed:', err);
        this.errorMessage = 'Failed to load notifications.';
        this.loading = false;
      },
    });
  }

  public close(): void {
    this.dialogRef.close();
  }
}
