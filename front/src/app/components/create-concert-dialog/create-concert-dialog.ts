import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {MatDialogRef} from '@angular/material/dialog';
import { ConcertService } from '../../libs/services/concert.service';
import  {AuthService} from '../../libs/services/auth.service';
import {GenreEnum} from '../../libs/enums/genre-enum';

@Component({
  selector: 'app-create-concert-dialog',
  standalone: true,
  imports: [ ReactiveFormsModule],
  styleUrl: './create-concert-dialog.css',
  templateUrl: './create-concert-dialog.html',
})
export class CreateConcertDialog {

  public genres = Object.values(GenreEnum);
  private readonly concertService = inject(ConcertService);
  private readonly authService = inject(AuthService);

  private readonly dialogRef = inject(MatDialogRef<CreateConcertDialog>);

  form: FormGroup = new FormGroup(
    {
      titre : new FormControl(''),
      artiste : new FormControl(''),
      ville : new FormControl(''),
      lieu : new FormControl(''),
      genre : new FormControl<GenreEnum |''>(''),
      prix : new FormControl(''),
      description : new FormControl(''),
      date : new FormControl(''),
      capacite : new FormControl(''),
    }
  )

  public onSubmit(): void {
    const organisateurId = this.authService.getCurrentUserId();

    console.log('SAVE CLICKED');
    console.log('form valid:', this.form.valid);
    console.log('form value:', this.form.value);

    if (!organisateurId) {
      console.error('No organisateur id found');
      return;
    }

    const formData = this.form.value;

    if (this.form.invalid) {
      console.log('INVALID FORM', this.form.errors, this.form.controls);
      return;
    }

    this.concertService.createConcert({
      organisateurId:organisateurId,
      titre: formData.titre,
      artiste: formData.artiste,
      lieu: formData.lieu,
      ville: formData.ville,
      description: formData.description,
      dateTime: formData.date?.length === 16
        ? formData.date + ':00'
        : formData.date,
      prix: formData.prix,
      capacite: formData.capacite,
      genre: formData.genre,
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
