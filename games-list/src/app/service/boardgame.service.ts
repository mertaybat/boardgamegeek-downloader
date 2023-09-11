import { Injectable } from '@angular/core';
import { Boardgame } from '../model/boardgame';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class BoardgameService {

  constructor(private httpClient: HttpClient) { }

  getBoardgames(): Observable<Boardgame[]> {
    return this.httpClient.get<Boardgame[]>("../../assets/boardgames-custom.json");
  }
}
