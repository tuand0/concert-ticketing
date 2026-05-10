import {Component, EventEmitter, inject, Output} from '@angular/core';
import { CreateConcertDialog } from '../create-concert-dialog/create-concert-dialog';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-create-concert-card',
  imports: [],
  templateUrl: './create-concert-card.html',
  styleUrl: './create-concert-card.css',
})
export class CreateConcertCard {
  @Output()
  readonly concertCreated =
    new EventEmitter<void>();

  private readonly dialog = inject(MatDialog);

  protected openCreateConcertDialog(): void {
    const dialogRef = this.dialog.open(
      CreateConcertDialog,
      {
        width: 'fit-content',
        maxWidth: '95vw',
      }
    );

    dialogRef.afterClosed().subscribe(created => {

      if (created) {
        this.concertCreated.emit();
      }

    });
  }
}
