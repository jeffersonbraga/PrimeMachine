import { Pessoa } from './../../modelo/pessoa/Pessoa';
import { ObjetoVirtual } from './../../modelo/objetoVirtual/ObjetoVirtual';
import { CrudService } from './../../core/services/crud/crud.service';
import { Documento } from './../../modelo/documento/Documento';
import { Endereco } from './../../modelo/endereco/Endereco';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cadastrar',
  templateUrl: './cadastrar.component.html',
  styleUrls: ['./cadastrar.component.css']
})

export class CadastrarComponent implements OnInit {

  public registroCadastro : Pessoa = new Pessoa();

  public lista_documentosSaude : any = [];

  public dataTableDocumentos : any = [];

  public documentosSaude : Documento = new Documento();

  constructor(private crudService : CrudService) {
    this.registroCadastro = new Pessoa();
    this.registroCadastro.endereco = new Endereco();
    this.documentosSaude = new Documento();
  }

  ngOnInit(): void {

    this.registroCadastro = new Pessoa();
    this.registroCadastro.endereco = new Endereco();
    this.documentosSaude = new Documento();
    this.createTable();
  }

  createTable() {

  this.lista_documentosSaude = {

    cols: [
        {value: 'Tipo doc.', class: 'left'}, {value: 'Num. doc.', class: 'center', width: '100px'}, {value: 'Validade', class: 'right', width: '100px'}, {value: '', class: 'right', width: '30px'},
        
      ],
      
		rows: this.dataTableDocumentos
    }
  }

  save() {

    this.crudService.save(this.registroCadastro).subscribe(
          result => this.onSave(result),
          error => console.log("Erro ao recuperar save.............")
        );
  }

  onSave(result) {
    console.log("OK");
    this.registroCadastro = new Pessoa();
  }

  addDocSaude() {

    this.registroCadastro.documentos.push(this.documentosSaude);
    this.documentosSaude = new Documento();

			this.dataTableDocumentos = [];
			this.registroCadastro.documentos.map(
					fav => {
						this.dataTableDocumentos.push([
							{value: fav.tp_documento, class: 'left'}, 
							{value: fav.ds_numero_documento, class: 'center'}, 
							{value: fav.dt_validade, class: 'right'}, 
							{value: '', class: 'right'}]);
						});

			this.lista_documentosSaude = {
				cols: [
          {value: 'Tipo doc.', class: 'left'}, {value: 'Num. doc.', class: 'center', width: '100px'}, {value: 'Validade', class: 'right', width: '100px'}, {value: '', class: 'right', width: '30px'},
				], 
				rows: this.dataTableDocumentos
			}
  }
}