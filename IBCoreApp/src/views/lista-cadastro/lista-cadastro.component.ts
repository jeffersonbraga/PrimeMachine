import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'lista-cadastro',
  templateUrl: './lista-cadastro.component.html',
  styleUrls: ['./lista-cadastro.component.css']
})

export class ListaCadastroComponent implements OnInit {

  public lista_pacientes : any = [];

  constructor() {
  }

  ngOnInit(): void {
  }
}