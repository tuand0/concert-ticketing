import {Component, inject, signal} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {MatDialogRef} from '@angular/material/dialog';
import { ConcertService } from '../../libs/services/concert.service';
import  {AuthService} from '../../libs/services/auth.service';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-create-concert-dialog',
  standalone: true,
  imports: [ ReactiveFormsModule,ButtonModule, DialogModule, InputTextModule],
  templateUrl: './create-concert-dialog.html',
})
export class CreateConcertDialog {

  private readonly concertService = inject(ConcertService);
  private readonly authService = inject(AuthService);

  private readonly dialogRef = inject(MatDialogRef<CreateConcertDialog>);
  readonly genres = signal<String[]>([]);

  public constructor() {
    this.loadGenres()
  }

  form: FormGroup = new FormGroup(
    {
      titre : new FormControl(''),
      artiste : new FormControl(''),
      ville : new FormControl(''),
      lieu : new FormControl(''),
      genre : new FormControl(this.genres),
      prix : new FormControl(''),
      description : new FormControl(''),
      date : new FormControl(''),
      capacite : new FormControl(''),
    }
  )
  titre = '';
  artiste = '';
  ville = '';
  lieu = '';
  genre = '';
  prix = 0;
  description ='';
  dateTime = new Date();
  capacite = 0;

  protected loadGenres(): void {
    this.concertService.getAll().subscribe({
      next: concerts => {
        const genres = [...new Set(concerts.map(concert => concert.genre))];
        this.genres.set(genres);
      },
      error: err => console.error('Error loading genres:', err),
    });
  }

  public onSubmit(): void {
    const organisateurId = this.authService.getCurrentUserId();

    if (!organisateurId) {
      console.error('No organisateur id found');
      return;
    }

    const formData = this.form.value;

    this.concertService.createConcert({
      id:organisateurId,
      titre: this.titre,
      artiste: this.artiste,
      lieu: this.lieu,
      ville: this.ville,
      description: this.description,
      date: this.dateTime.getDate().toString(),
      prix: this.prix,
      capacite: this.capacite,
      genre: this.genre,
    }).subscribe({

      next: () => {
        this.dialogRef.close(true);
      },

      error: (err: any) => {
        console.error(err);
      }

    });
  }

  public close(): void {
    this.dialogRef.close(false);
  }
}
