import {Component, inject} from '@angular/core';
import {RouterModule, RouterOutlet} from '@angular/router';
import {AuthService} from './libs/services/auth.service';
import { ButtonModule } from 'primeng/button';

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
}
