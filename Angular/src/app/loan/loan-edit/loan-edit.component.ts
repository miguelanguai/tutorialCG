import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { LoanService } from '../loan.service';
import { Loan } from '../model/Loan';
import { Client } from 'src/app/client/model/Client';
import { ClientService } from 'src/app/client/client.service';
import { Game } from 'src/app/game/model/Game';
import { GameService } from 'src/app/game/game.service';

@Component({
  selector: 'app-loan-edit',
  templateUrl: './loan-edit.component.html',
  styleUrls: ['./loan-edit.component.scss']
})
export class LoanEditComponent implements OnInit{

  loan : Loan;
  games: Game[];
  clients: Client[];
  alreadyOnLoanError:boolean=false;

    constructor(
        public dialogRef: MatDialogRef<LoanEditComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private loanService: LoanService,
        private gameService: GameService,
        private clientService: ClientService,
    ) { }

    ngOnInit(): void {
        if (this.data.loan != null) {
            this.loan = Object.assign({}, this.data.loan);
        }
        else {
            this.loan = new Loan();
        }
        this.clientService.getClients().subscribe(
            clients => {
                this.clients = clients;
            }
        );
        this.gameService.getGames().subscribe(
            games => {
                this.games = games;
            }
        );
    }

    onSave() {
        this.loanService.saveLoan(this.loan).subscribe(result =>  {
            this.dialogRef.close();
        }, error => {
          console.log("error al guardar");
          this.alreadyOnLoanError=true;
        }
        /*
        esta es una manera rudimentaria de solucionar el error al guardar (si no se pone nada, el botón Guardar
          no funciona, y el usuario no recibe ningún mensaje), pero como el backend no devuelve nada
          más que un error, es lo que hay, por lo que se le envía al usuario un mensaje de error, y él
          verá que es lo que ha hecho mal, si coger un juego que ya está siendo prestado a otro cliente en
          esas fechas, o coger un juego con un cliente que ya tiene un préstamo en esas fechas. No se me
          ocurre otra manera de hacerlo
        */
        );
    }

    onClose() {
        this.dialogRef.close();
    }
}
