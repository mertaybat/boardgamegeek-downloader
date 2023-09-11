import { Component, OnInit, ViewChild } from '@angular/core';
import { BoardgameService } from './service/boardgame.service';
import { Boardgame } from './model/boardgame';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import { BoardgameDetailsComponent } from './boardgame-details.component';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  dataSource: MatTableDataSource<Boardgame> = new MatTableDataSource<Boardgame>();
  displayedColumns: string[] = ['thumbnailImageLink', 'boardGameRank', 'name', 'bestPlayedWith', 'gameMechanics']

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private boardgameService: BoardgameService,
    private dialog: MatDialog) { }

  ngOnInit() {
      this.boardgameService.getBoardgames().subscribe(
        x => {
                this.dataSource.data = x;
                this.dataSource.paginator = this.paginator;
                this.dataSource.sort = this.sort;
              }
      );
  }

  applyFilter(event: Event) {
      const filterValue = (event.target as HTMLInputElement).value;
      this.dataSource.filter = filterValue.trim().toLowerCase();

      if (this.dataSource.paginator) {
        this.dataSource.paginator.firstPage();
      }
    }

  showDetails(boardgame: Boardgame) {
      this.dialog.open(BoardgameDetailsComponent,
        {
          height: '800px',
          width: '8800px',
          data: boardgame
        }
      )
  }
}
