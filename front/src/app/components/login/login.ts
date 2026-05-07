import {Component, computed, inject, signal} from '@angular/core';
import { FormsModule } from '@angular/forms';
import {Router} from '@angular/router';
import {email, form, FormField, minLength, required} from '@angular/forms/signals';
import {AuthService} from '../../libs/services/auth.service';
import {finalize} from 'rxjs';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [FormsModule, FormField, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
  standalone: true
})
export class Login {
  private readonly authService = inject(AuthService)
  private readonly router = inject(Router)

  readonly formModel = signal({
    email: '',
    password: '',
  })

  readonly form = form(this.formModel, (path) => {
    required(path.email)
    email(path.email)
    required(path.password)
    minLength(path.password, 6)
  })

  readonly loading = signal(false)
  readonly errorMessage = signal<string | null>(null)

  readonly canSubmit = computed(() => this.form().valid() && this.form().touched() && !this.loading())

  protected onSubmit(event: Event) {
    event.preventDefault()
    if (!this.canSubmit()) {
      return
    }

    const { email, password } = this.form().value()
    this.loading.set(true)
    this.errorMessage.set(null)
    this.authService.login(email, password)
      .pipe(
        finalize(() => this.loading.set(false))
      )
      .subscribe({
        next: () => {
          this.router.navigate(['/concerts/']).then()
        },
        error: (err) => {
          this.errorMessage.set(`The connection failed`)
          console.error('Connection error', err)
        }
      })
  }

  public testFct():void{
    console.log('button works!')
  }
}
