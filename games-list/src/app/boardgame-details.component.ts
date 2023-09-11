import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Boardgame } from './model/boardgame';

@Component({
  selector: 'app-boardgame-details',
  templateUrl: './boardgame-details.component.html',
  styleUrls: ['./boardgame-details.component.css']
})
export class BoardgameDetailsComponent {

    constructor(@Inject(MAT_DIALOG_DATA) public data: Boardgame) {}
}
