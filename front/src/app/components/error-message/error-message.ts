import {Component, input, output} from '@angular/core';

@Component({
  selector: 'app-error-message',
  imports: [],
  templateUrl: './error-message.html',
  styleUrl: './error-message.css',
})
export class ErrorMessage {
  readonly title = input<string>();
  readonly message = input<string>();
  readonly showRetry = input(true);
  readonly retry = output<void>();
}
