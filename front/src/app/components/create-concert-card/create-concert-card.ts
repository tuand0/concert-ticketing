import { Component } from '@angular/core';

@Component({
  selector: 'app-create-concert-card',
  imports: [],
  templateUrl: './create-concert-card.html',
  styleUrl: './create-concert-card.css',
})
export class CreateConcertCard {
  public todo():void{
    console.log('Creating new concert card...');
  }
}
