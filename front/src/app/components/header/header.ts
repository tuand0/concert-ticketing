import { Component } from '@angular/core';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-header',
  imports: [InputTextModule],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  protected value: string = '';
}
