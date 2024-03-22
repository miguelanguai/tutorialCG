import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Loan } from '../model/Loan';
import { Game } from 'src/app/game/model/Game';
import { Client } from 'src/app/client/model/Client';
import { LoanEditComponent } from '../loan-edit/loan-edit.component';
import { LoanService } from '../loan.service';
import { ClientService } from 'src/app/client/client.service';
import { MatDialog } from '@angular/material/dialog';
import { GameService } from 'src/app/game/game.service';
import { DialogConfirmationComponent } from '../../core/dialog-confirmation/dialog-confirmation.component';
import { Pageable } from 'src/app/core/model/page/Pageable';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-loan-list',
  templateUrl: './loan-list.component.html',
  styleUrls: ['./loan-list.component.scss']
})
export class LoanListComponent implements OnInit {

  loans: Loan[];
  clients: Client[];
  games: Game[];
  filterDate: Date;
  filterGame: Game;
  filterClient: Client;
  onSearching: boolean = false; //para saber si los filtros están en marcha

  pageNumber: number = 0;
  pageSize: number = 5;
  totalElements: number = 0;

  dataSource = new MatTableDataSource<Loan>();
  displayedColumns: string[] = ['id', 'game', 'client', 'initDate', 'endDate', 'action'];

  constructor(
    private loanService: LoanService,
    private gameService: GameService,
    private clientService: ClientService,
    public dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.clientService.getClients().subscribe(
      clients => this.clients = clients
    );

    this.gameService.getGames().subscribe(
      games => this.games = games
    );

    this.loadPage();
  }

  onCleanFilter(): void {
    this.filterDate = null;
    this.filterClient = null;
    this.filterGame = null;

    this.onSearching = false;

    this.loadPage();
    /*
    cambiado para que se carguen los datos sin filtros sin llamar a onSearch()
    la unica diferencia es que se queda en la página en la que estaba al hacer click en Limpiar
    */
  }

  onSearch(): void {
    //funcion solo llamada cuando se filtran datos

    let date = this.filterDate;
    let clientId = this.filterClient != null ? this.filterClient.id : null;
    let gameId = this.filterGame != null ? this.filterGame.id : null;

    this.onSearching = true; //se pasa la variable a true

    let pageable: Pageable = {
      pageNumber: 0, //cada vez que se busca por filtros, se devuelve a primera página
      pageSize: this.pageSize,
      sort: [{
        property: 'id',
        direction: 'ASC'
      }]
    }

    if (date) {
      date = new Date(date.getTime() + 1 * 60 * 60 * 1000);
    }

    this.loanService.getLoans(pageable, gameId, clientId, date).subscribe(loans => {
      this.dataSource.data = loans.content;
      this.pageNumber = loans.pageable.pageNumber;
      this.pageSize = loans.pageable.pageSize;
      this.totalElements = loans.totalElements;
    });
  }

  isPageFiltered(event?: PageEvent) {
    /*
    Esta funcion es la que se llama desde el html.
    Si el onSearching está a true, se ejecuta el loadPageFiltered, que filtrará con nuestros
    parámetros en cada página por la que pase.
    Si está a false, simplemente cargará la página como está en loadPage()
    */

    this.onSearching ? this.loadPageFiltered(event) : this.loadPage(event);
  }

  loadPageFiltered(event?: PageEvent) {
    //este metodo es para cargar la pagina filtrada

    let date = this.filterDate;
    let clientId = this.filterClient != null ? this.filterClient.id : null;
    let gameId = this.filterGame != null ? this.filterGame.id : null;

    if (date) {
      date = new Date(date.getTime() + 1 * 60 * 60 * 1000);
    }

    //coge datos para la paginacion y demas
    let pageable: Pageable = {
      pageNumber: this.pageNumber,
      pageSize: this.pageSize,
      sort: [{
        property: 'id',
        direction: 'ASC'
      }]
    }

    if (event != null) {
      pageable.pageSize = event.pageSize;
      pageable.pageNumber = event.pageIndex;
    }

    this.loanService.getLoans(pageable, gameId, clientId, date).subscribe(data => {
      this.dataSource.data = data.content;
      this.pageNumber = data.pageable.pageNumber;
      this.pageSize = data.pageable.pageSize;
      this.totalElements = data.totalElements;
    })

  }

  loadPage(event?: PageEvent) {
    //método para cargar las páginas sin filtrar
    let pageable: Pageable = {
      pageNumber: this.pageNumber,
      pageSize: this.pageSize,
      sort: [{
        property: 'id',
        direction: 'ASC'
      }]
    }
    if (event != null) {
      pageable.pageSize = event.pageSize;
      pageable.pageNumber = event.pageIndex;
    }
      this.loanService.getLoans(pageable).subscribe(data => {
        this.dataSource.data = data.content;
        this.pageNumber = data.pageable.pageNumber;
        this.pageSize = data.pageable.pageSize;
        this.totalElements = data.totalElements;
      })
  }

  createLoan() {
    const dialogRef = this.dialog.open(LoanEditComponent, {
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  deleteLoan(loan: Loan) {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      data: { title: "Eliminar prestamo", description: "Atención si borra el prestamo se perderán sus datos.<br> ¿Desea eliminar el prestamo?" }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loanService.deleteLoan(loan.id).subscribe(result => {
          this.ngOnInit();
        });
      }
    });
  }
}
