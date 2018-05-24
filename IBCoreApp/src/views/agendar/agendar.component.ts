import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-agendar',
  templateUrl: './agendar.component.html',
  styleUrls: ['./agendar.component.css']
})

export class AgendarComponent  implements OnInit {

  public listaProcedimentos : any = [];

  public listaProcedimentosSelecionados : any = [];

  public listaProcedimentosFiltrados : any = [];

  public dataTableCronograma : any = [];

  public dataTableQuestionario : any = [];

  public listaQuestionario : any = [];

  ngOnInit(): void {
    this.createTable();
  }

  createTable() {

    this.dataTableCronograma = {

      cols: [
          {value: 'Procedimento', class: 'left'}, {value: 'Data', class: 'right', width: '100px'}, {value: '', class: 'right', width: '30px'}
        ]
    }

    this.dataTableQuestionario = {

      cols: [
          {value: 'Questão', class: 'left'}, {value: 'Resposta', class: 'right', width: '100px'}, {value: '', class: 'right', width: '30px'}
        ]
    }
  }

  createMapQuestionario() {

			let dataQuestionario = [];
			this.listaQuestionario.map(
					fav => {
						dataQuestionario.push([
							{value: fav.tag, class: 'left'}, 
							{value: fav.comments, class: 'right'}, 
							{value: fav.likes, class: 'right'}, 
							{value: fav.interactions, class: 'right'}]);
						});

			this.dataTableQuestionario = {
				cols: [
					{value: 'Hashtag', class: 'left'}, {value: 'Comentários', class: 'right', width: '100px'}, {value: 'Likes', class: 'right', width: '100px'}, {value: 'Total', class: 'right', width: '100px'}
				], 
				rows: dataQuestionario
			}
  }

  createMapCronograma() {
    
			let dataCronograma = [];
			this.listaProcedimentosSelecionados.map(
					fav => {
						dataCronograma.push([
							{value: fav.NM_PROCEDIMENTO, class: 'left'}, 
							{value: fav.comments, class: 'right'}, 
							{value: fav.likes, class: 'right'}, 
							{value: fav.interactions, class: 'right'}]);
						});

			this.dataTableCronograma = {
				cols: [
					{value: 'Hashtag', class: 'left'}, {value: 'Comentários', class: 'right', width: '100px'}, {value: 'Likes', class: 'right', width: '100px'}, {value: 'Total', class: 'right', width: '100px'}
				], 
				rows: dataCronograma
			}
  }
}