import {Component, inject} from '@angular/core';
import {RouterModule, RouterOutlet} from '@angular/router';
import {AuthService} from './libs/services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Concert-ticketing Shop';
  protected readonly authService = inject(AuthService)
}
