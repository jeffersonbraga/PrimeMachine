package br.com.lsd.bo.sincronizar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.primum.bo.criar_bean.Criar_beanBO;
import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.campo.Campo;
import br.com.primum.modelo.tabela.Tabela;
import br.com.primum.reflection.Reflection;

@RestController
@RequestMapping("/lsd")
public class GeradorAngular2 {

	//private String DIR_GRAVAR = "D:\\Projetos\\PrimeMachine\\src\\main\\webapp\\app\\entities";
	private String DIR_GRAVAR 				= "D:\\Projetos\\AngularAutoModel\\";
	private String DIR_GRAVAR_TYPESCRIPT	= "D:\\Projetos\\AngularAutoModel\\ts_modelo\\";
	private String DIR_GRAVAR_CONF			= "D:\\Projetos\\AngularAutoModel\\config_module_route\\";

	@SuppressWarnings("unchecked")
    @CrossOrigin
	@RequestMapping(value= "/criarAngular2", method = RequestMethod.GET)
	public void criarBaseSQLite() throws FileNotFoundException {

    	//String nomeSchema="public";
    	String nomeSchema="opsocial";
    	boolean checkTables = false;

		String sqlTabelas = "SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname IN ('"+nomeSchema+"') and tablename not in ('codigosfixos','sessaodocumento','campospesquisa','databasechangelog','databasechangeloglock') and tablename not like 'jhi_%' ORDER BY schemaname, tablename ";
    	//ORACLE String sqlTabelas = "SELECT TABLE_NAME AS \"tablename\" FROM all_tables where owner = 'VIASOFTMCP' and TABLESPACE_NAME IS NOT NULL";
		GenericDAO dao = new GenericDAO();
		Object o = dao.getSession().createSQLQuery(sqlTabelas).list();

		List<Tabela> listaTabelaChecar = null;

		if (checkTables) {
			listaTabelaChecar = dao.getSession().createCriteria(Tabela.class).list();
		}

		String criar 		= "";
		String criarHTML 	= "";
		ArrayList<String> lTipos = new ArrayList<>();

		String menuItem 	= "";
		String indexItem 	= "";

		String StringAppModuleImport	= "";
		String StringAppModuleNg		= "";
		String StringAppRouting			= "";
		String RootIndex				= "";

		for (String nm_tabela : (ArrayList<String>) o) {

			String nm_tabelaaUX 	= nm_tabela.replaceAll("_", "-");
			String nm_tabelaObjeto 	= nm_tabela.substring(0, 1).toUpperCase() + nm_tabela.substring(1).toLowerCase();

			//Criar lista para acesso rápido aos programas.
			RootIndex =  RootIndex + "<p><a [routerLink]=\"['/" + nm_tabela.toLowerCase() + "']\">" + nm_tabelaObjeto + "</a></p>";
			
			StringAppModuleImport	= StringAppModuleImport	+ "	import { " + nm_tabelaObjeto + "Module } from './form/" + nm_tabela.toLowerCase() + "/pesquisa/" + nm_tabela.toLowerCase() + ".module';";
			StringAppModuleNg		= StringAppModuleNg 	+ "," + nm_tabelaObjeto + "Module";
			StringAppRouting		= StringAppRouting 		+ ",{ path: 'dados-" + nm_tabela.toLowerCase() + "/:id', loadChildren: 'app/form/" + nm_tabela.toLowerCase() + "/detalhe/" + nm_tabela.toLowerCase() + "_dados.module'}";

			String DIR_ 				= DIR_GRAVAR + nm_tabela.toLowerCase();
			String DIR_Modelo	 		= DIR_GRAVAR + "\\ts_modelo";
			String DIR_PESQUISA 		= DIR_GRAVAR + nm_tabela.toLowerCase() + "\\pesquisa";
			String DIR_DETALHE			= DIR_GRAVAR + nm_tabela.toLowerCase() + "\\detalhe";

			File diretorio = new File(DIR_);
			if (!diretorio.exists()) {
				diretorio.mkdir();
			}
			File diretorioConf = new File(DIR_GRAVAR_CONF);
			if (!diretorioConf.exists()) {
				diretorioConf.mkdir();
			}
			File diretorioModelo = new File(DIR_Modelo);
			if (!diretorioModelo.exists()) {
				diretorioModelo.mkdir();
			}
			File diretorioPesquisa = new File(DIR_PESQUISA);
			if (!diretorioPesquisa.exists()) {
				diretorioPesquisa.mkdir();
			}
			File diretorioDetalhe = new File(DIR_DETALHE);
			if (!diretorioDetalhe.exists()) {
				diretorioDetalhe.mkdir();
			}

			File fileIndexPesquisa 		= new File(DIR_PESQUISA + "\\index.ts");
			File fileModuloPesquisa 	= new File(DIR_PESQUISA + "\\" + nm_tabelaObjeto.toLowerCase() +".module.ts");
			File fileRoutingPesquisa 	= new File(DIR_PESQUISA + "\\" + nm_tabelaObjeto.toLowerCase() +".routing.ts");
			File fileComponentPesquisa 	= new File(DIR_PESQUISA + "\\" + nm_tabelaObjeto.toLowerCase() +".component.ts");
			File fileHtmlPesquisa 		= new File(DIR_PESQUISA + "\\" + nm_tabelaObjeto.toLowerCase() +".component.html");

			File fileIndexDados 		= new File(DIR_DETALHE + "\\index.ts");
			File fileDadosComponent 	= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dados.component.ts");
			File fileDadosModulo		= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dados.module.ts");
			File fileEditComponent		= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dadosEdit.component.ts");
			File fileDetailComponent	= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dadosDetails.component.ts");
			File fileDadosRouting 		= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dados.routing.ts");
			File fileDadosHTML 			= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dados.component.html");
			File fileEditHTML			= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dadosEdit.component.html");
			File fileDetailsHTML		= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dadosDetails.component.html");

			String StringIndexPesquisa 		= "export * from './" + nm_tabelaObjeto.toLowerCase() + ".component';";
			String StringModuloPesquisa 	= "import { NgModule }      from '@angular/core'; import { CommonModule } from '@angular/common';  import { SharedModule }   from '../../../shared/shared.module'; import { " + nm_tabelaObjeto + "Component } from './" + nm_tabelaObjeto.toLowerCase() + ".component'; import { " + nm_tabelaObjeto.toLowerCase() + "_routing } from './" + nm_tabelaObjeto.toLowerCase() + ".routing';  @NgModule({   imports:      [ CommonModule, " + nm_tabelaObjeto.toLowerCase() + "_routing, SharedModule],   declarations: [ " + nm_tabelaObjeto + "Component ],   exports:      [ " + nm_tabelaObjeto + "Component ] }) export class " + nm_tabelaObjeto + "Module { }";
			String StringRoutingPesquisa 	= "import { Routes, RouterModule } from '@angular/router';  import { " + nm_tabelaObjeto + "Component } from './" + nm_tabelaObjeto.toLowerCase() + ".component';  const " + nm_tabelaObjeto.toLowerCase() + "_routes: Routes = [   { path: '" + nm_tabelaObjeto.toLowerCase() + "*', pathMatch:'full', redirectTo: '/" + nm_tabelaObjeto.toLowerCase() + "' },   { path: '" + nm_tabelaObjeto.toLowerCase() + "', component: " + nm_tabelaObjeto + "Component} ];  export const " + nm_tabelaObjeto.toLowerCase() + "_routing = RouterModule.forChild(" + nm_tabelaObjeto.toLowerCase() + "_routes);";
			String StringComponentPesquisa 	= "import { Component, Input, OnInit } from '@angular/core'; import { Router } from '@angular/router'; import { " + nm_tabelaObjeto + " } from '../../../modelo/" + nm_tabelaObjeto + "'; import { ObjetoVirtual } from '../../../modelo/ObjetoVirtual';  import { PesquisaService } from '../../../core/services/pesquisa/pesquisa.service';  @Component({   moduleId: module.id,   selector: '" + nm_tabelaObjeto.toLowerCase() + "-selector',   templateUrl: '" + nm_tabelaObjeto.toLowerCase() + ".component.html' })  export class " + nm_tabelaObjeto + "Component implements OnInit { 	 	errorMessage : string = '';      @Input() listaDados : " + nm_tabelaObjeto + "[] = [];     " + nm_tabelaObjeto.toLowerCase() + "Aux : " + nm_tabelaObjeto + ";     constructor(        private dataService: PesquisaService,        private router: Router) { }      ngOnInit() {         this." + nm_tabelaObjeto.toLowerCase() + "Aux = new " + nm_tabelaObjeto + "(); let dataAux : ObjetoVirtual = new ObjetoVirtual();         dataAux.classeJava  = 'br.com.primum.modelo." + nm_tabelaObjeto.toLowerCase() + "." + nm_tabelaObjeto + "';         dataAux.objetoJson = this." + nm_tabelaObjeto.toLowerCase() + "Aux;          this.dataService.efetuarPesquisa(dataAux)             .subscribe(             result => this.onEfetuarPesquisa(result),             error =>  this.errorMessage = <any>error);     }  	onEfetuarPesquisa(result : any) { 		if (!result) { 			this.errorMessage = 'Houve um erro a pesquisa de " + nm_tabelaObjeto + ". ';             this.router.navigate(['/home']); 		}          this.listaDados = result; 	} }";
			String StringHtmlPesquisa 		= "<div class=\"table\">	<table class=\"table table-striped table-hover\">		<thead>			<tr>				";
			String StringHtmlCabecalho		= "";
			String StringHtmlDetalhe		= "<tbody><tr *ngFor=\"let item" + nm_tabelaObjeto + " of listaDados;\">";
			String OBJETO_TYPESCRIPT		= "export class " + nm_tabelaObjeto + "{";
			
			
			String StringIndexDados			= "export * from './" + nm_tabelaObjeto.toLowerCase() + "_dados.component';export * from './" + nm_tabelaObjeto.toLowerCase() + "_dadosDetails.component';export * from './" + nm_tabelaObjeto.toLowerCase() + "_dadosEdit.component';";
			String StringDadosComponent 	= "import { Component, OnInit } from '@angular/core'; import { Router } from '@angular/router';  @Component({   moduleId: module.id,   selector: '" + nm_tabelaObjeto.toLowerCase() + "-dados',   templateUrl: '" + nm_tabelaObjeto.toLowerCase() + "_dados.component.html' }) export class " + nm_tabelaObjeto + "DadosComponent implements OnInit {       displayMode: " + nm_tabelaObjeto + "DadosDisplayModeEnum;     displayModeEnum = " + nm_tabelaObjeto + "DadosDisplayModeEnum;       constructor(private router: Router) { }      ngOnInit() {       const path = this.router.url.split('/')[3];       switch (path) {         case 'details':           this.displayMode = " + nm_tabelaObjeto + "DadosDisplayModeEnum.Details;           break;         case 'info':           this.displayMode = " + nm_tabelaObjeto + "DadosDisplayModeEnum.Orders;           break;         case 'edit':           this.displayMode = " + nm_tabelaObjeto + "DadosDisplayModeEnum.Edit;           break;       }     }  }  enum " + nm_tabelaObjeto + "DadosDisplayModeEnum {   Details=0,   Orders=1,   Edit=2 }";
			String StringDadosModulo 		= "import { NgModule }      from '@angular/core'; import { CommonModule } from '@angular/common';  import { " + nm_tabelaObjeto + "DadosComponent } from './" + nm_tabelaObjeto.toLowerCase() + "_dados.component'; import { " + nm_tabelaObjeto + "DadosDetailsComponent } from './" + nm_tabelaObjeto.toLowerCase() + "_dadosDetails.component'; import { " + nm_tabelaObjeto + "DadosEditComponent } from './" + nm_tabelaObjeto.toLowerCase() + "_dadosEdit.component'; import { SharedModule }   from '../../../shared/shared.module'; import { " + nm_tabelaObjeto.toLowerCase() + "_dados_routing } from './" + nm_tabelaObjeto.toLowerCase() + "_dados.routing';  @NgModule({   imports:      [ CommonModule, " + nm_tabelaObjeto.toLowerCase() + "_dados_routing, SharedModule ],   declarations: [ " + nm_tabelaObjeto + "DadosComponent, " + nm_tabelaObjeto + "DadosDetailsComponent,                   " + nm_tabelaObjeto + "DadosEditComponent ] }) export default class AppModule { }";
			String StringEditComponent		= "";
			String StringDetailComponent 	= "";
			String StringDadosRouting 		= "import { Routes, RouterModule } from '@angular/router';  import { " + nm_tabelaObjeto + "DadosComponent }   from './" + nm_tabelaObjeto.toLowerCase() + "_dados.component'; import { " + nm_tabelaObjeto + "DadosDetailsComponent } from './" + nm_tabelaObjeto.toLowerCase() + "_dadosDetails.component'; import { " + nm_tabelaObjeto + "DadosEditComponent } from './" + nm_tabelaObjeto.toLowerCase() + "_dadosEdit.component';  const " + nm_tabelaObjeto.toLowerCase() + "_dados_routes: Routes = [   {     path: '',     component: " + nm_tabelaObjeto + "DadosComponent,     children: [       { path:'details', component: " + nm_tabelaObjeto + "DadosDetailsComponent },       { path:'edit', component: " + nm_tabelaObjeto + "DadosEditComponent }     ]   } ];  export const " + nm_tabelaObjeto.toLowerCase() + "_dados_routing = RouterModule.forChild(" + nm_tabelaObjeto.toLowerCase() + "_dados_routes); ";
			String StringDadosHTML	 		= "    <header>        <h3><span class=\"glyphicon glyphicon-user\"></span>&nbsp;&nbsp;Informações " + nm_tabelaObjeto + "</h3>    </header> <br />    <div class=\"navbar\">        <ul class=\"nav navbar-nav\">            <li class=\"toolbar-item\">                <a routerLink=\"details\" (click)=\"displayMode = displayModeEnum.Details\" [class.active]=\"displayMode === displayModeEnum.Details\">                <span class=\"glyphicon glyphicon-list\"></span>&nbsp;&nbsp;Detalhes </a>            </li>            <li class=\"toolbar-item\">                <a routerLink=\"edit\" (click)=\"displayMode = displayModeEnum.Edit\" [class.active]=\"displayMode === displayModeEnum.Edit\">                <span class=\"glyphicon glyphicon-edit edit-icon\"></span>&nbsp;&nbsp;Editar </a>            </li>            <li class=\"toolbar-item\">                <a routerLink=\"/" + nm_tabelaObjeto.toLowerCase() + "\"><span class=\"glyphicon glyphicon-list-alt\"></span>&nbsp;&nbsp;Voltar à lista</a>            </li>        </ul>    </div>        <router-outlet></router-outlet>";
			String StringEditHTML			= "<div class=\"well\"><form (ngSubmit)=\"onSubmit()\" #" + nm_tabelaObjeto.toLowerCase() + "Form=\"ngForm\" class=\"editForm\">";
			String StringDetailHTML			= "<div class=\"well\" *ngIf=\"" + nm_tabelaObjeto.toLowerCase() + "\"><div class=\"table\">	<table class=\"table table-striped table-hover\">		<tbody>";

			Criar_beanBO c = new Criar_beanBO();
			try {
				c.Criar_arquivos(nm_tabela);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			int contColunas = 0;
			boolean sicronizarTabela = false;

			if(checkTables) {
				for (Tabela t : listaTabelaChecar) {
					if (t.getNm_tabela().equalsIgnoreCase(nm_tabela) && ((t.getTp_sincronizar() != null) && (t.getTp_sincronizar().equals(1)))) {
						sicronizarTabela = true;
						listaTabelaChecar.remove(t);
						break;
					}
				}
			}

			if (!nm_tabela.equals("espelho")) {

				//ORACLE String sqlCamposTabela = "SELECT COLUMN_NAME as \"nm_campo\", DATA_TYPE as \"ds_descricao\" FROM USER_TAB_COLUMNS WHERE table_name = '" + nm_tabela + "'";
				String sqlCamposTabela = "select column_name as nm_campo, data_type as ds_descricao, case when is_nullable = 'YES' then 1 else 0 end as st_aceitavalornull from information_schema.columns where table_name = '" + nm_tabela + "' and table_schema IN ('"+nomeSchema+"')";
				
				List<?> listaColunas = null;
				try {
					listaColunas = dao.findBySql(sqlCamposTabela, new Campo());
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				String detail = "";
				for (Campo itemColuna : (List<Campo>) listaColunas) {
					
					StringHtmlCabecalho = StringHtmlCabecalho + "<th sort-by=\"" + itemColuna.getNm_campo().toLowerCase() + "\" (sorted)=\"sort($event)\">" + itemColuna.getNm_campo() + "</th>";
					StringHtmlDetalhe	= StringHtmlDetalhe + "<td>{{ item" + nm_tabelaObjeto + "." + itemColuna.getNm_campo().toLowerCase() + "}}</td>";
					
					String caminho			= "";
					String ch 				= "";
					String tipoTypeScript 	= "number";
					String tipoColuna 		= itemColuna.getDs_descricao();

					if ( (tipoColuna == "timestamp with time zone") || (tipoColuna.indexOf("timestamp") > -1) || (tipoColuna.indexOf("date") > -1)) {
						tipoTypeScript = "string";						
					} else if (tipoColuna.indexOf("character varying") > -1){
						tipoTypeScript = "string";  
					} else if ( tipoColuna.indexOf("char") > -1 ) {
						tipoTypeScript = "string";
					} else if ( tipoColuna.indexOf("double precision") > -1 ) {
						tipoTypeScript = "string";
					} else if ( tipoColuna.indexOf("boolean") > -1 ) {
						tipoTypeScript = "string";
					}
					
					if (tipoTypeScript == "string") {
						StringEditHTML = StringEditHTML + "<div class=\"form-group\"><label>" + itemColuna.getNm_campo().toUpperCase() + "</label><input type=\"text\" class=\"form-control\" name=\"input_" + itemColuna.getNm_campo().toLowerCase() + "\" [(ngModel)]=\"" + nm_tabelaObjeto.toLowerCase() + "." + itemColuna.getNm_campo().toLowerCase() + "\"></div>";
					} else {
						StringEditHTML = StringEditHTML + "<div class=\"form-group\"><label>" + itemColuna.getNm_campo().toUpperCase() + "</label><input type=\"number\" class=\"form-control\" name=\"input_" + itemColuna.getNm_campo().toLowerCase() + "\" [(ngModel)]=\"" + nm_tabelaObjeto.toLowerCase() + "." + itemColuna.getNm_campo().toLowerCase() + "\"></div>";
					}

					if (nm_tabela.equals("pre_avaliacao_ideia_pic")) {
						caminho = "br.com.primum.modelo.reuniao_pic." + nm_tabela.substring(0, 1).toUpperCase() + nm_tabela.substring(1);
					} else {
						caminho = "br.com.primum.modelo." + nm_tabela.toLowerCase() + "." + nm_tabela.substring(0, 1).toUpperCase() + nm_tabela.substring(1);
					}

					try {
						//ch = Reflection.getNomeChavePrimaria(Class.forName(caminho));Reflection.getNomeChavePrimaria(Class.forName(caminho));
						ch = "";
					} catch (Exception e) {

						e.printStackTrace();
					}	
					
					OBJETO_TYPESCRIPT = OBJETO_TYPESCRIPT + itemColuna.getNm_campo().toLowerCase() + " : " + tipoTypeScript + ";";

					if(ch.toLowerCase().equals(itemColuna.getNm_campo().toLowerCase())) {

						detail = "<td><a [routerLink]=\"['/dados-" + nm_tabela.toLowerCase() + "',item" + nm_tabelaObjeto + "." + ch.toLowerCase() + ",'details']\">Detalhes</a></td>";
						StringDetailHTML	= StringDetailHTML + "<tr>				<td class=\"col-md-2\"><h5>" + ch + "</h5></td>				<td>{{ " + nm_tabelaObjeto.toLowerCase() + "." + ch.toLowerCase() + "}}</td>			</tr>";
						StringEditComponent		= "import { Component, OnInit } from '@angular/core'; import { Router, ActivatedRoute } from '@angular/router'; import { Subscription } from 'rxjs/Subscription';  import { " + nm_tabelaObjeto + " } from '../../../modelo/" + nm_tabelaObjeto + "'; import { ObjetoVirtual } from '../../../modelo/ObjetoVirtual'; import { PesquisaService } from '../../../core/services/pesquisa/pesquisa.service'; import { CrudService } from '../../../core/services/crud/crud.service'; @Component({       moduleId: module.id,       selector: '" + nm_tabelaObjeto.toLowerCase() + "-dados-edit',       templateUrl: '" + nm_tabelaObjeto.toLowerCase() + "_dadosEdit.component.html' }) export class " + nm_tabelaObjeto + "DadosEditComponent implements OnInit {        " + nm_tabelaObjeto.toLowerCase() + ": " + nm_tabelaObjeto + ";        private sub: Subscription;        private errorMessage: string;             constructor(private router: Router,                               private route: ActivatedRoute,                               private pesquisaService: PesquisaService,                               private crudService: CrudService) { }        ngOnInit() {               this.sub = this.route.parent.params.subscribe(params => {                     let o : ObjetoVirtual = new ObjetoVirtual();                     o.classeJava = \"br.com.primum.modelo." + nm_tabelaObjeto.toLowerCase() + "." + nm_tabelaObjeto + "\";                               let id = +params['id'];                     this." + nm_tabelaObjeto.toLowerCase() + " = new " + nm_tabelaObjeto + "();              if (id > -1) {                  this." + nm_tabelaObjeto.toLowerCase() + "." + ch.toLowerCase() + " = id;                         o.objetoJson = this." + nm_tabelaObjeto.toLowerCase() + ";                          this.pesquisaService.efetuarPesquisa(o)                             .subscribe(                                 result => this.onEfetuarPesquisa(result),                                 error =>  this.errorMessage = <any>error);             }                                });           }            onEfetuarPesquisa(result : any) {             if (!result) {                   this.errorMessage = 'Houve um erro ao pesquisar " + nm_tabelaObjeto + ". ';               }                      this." + nm_tabelaObjeto.toLowerCase() + " = result;       }            ngOnDestroy() {     this.sub.unsubscribe();   }             onSubmit() {                      this.sub = this.route.parent.params.subscribe(params => {                     let o : ObjetoVirtual = new ObjetoVirtual();                     o.classeJava = \"br.com.primum.modelo." + nm_tabelaObjeto.toLowerCase() + "." + nm_tabelaObjeto + "\";                     o.objetoJson = this." + nm_tabelaObjeto.toLowerCase() + ";                      this.crudService.salvarObjetoCompleto(o)                         .subscribe(                 result => this.onSalvarObjeto(result),                             error =>  this.errorMessage = <any>error);                   });     }         onCancel(event: Event) {             event.preventDefault();             this.router.navigate(['/']);       }       onSalvarObjeto(result : any) {             if (!result) {                   this.errorMessage = 'Houve um erro ao pesquisar " + nm_tabelaObjeto + ". ';               }          event.preventDefault();             this.router.navigate(['/dados-" + nm_tabelaObjeto.toLowerCase() + "/']);     }      btnExcluirClickHandler() {          this.sub = this.route.parent.params.subscribe(params => {                     let o : ObjetoVirtual = new ObjetoVirtual();                     o.classeJava = \"br.com.primum.modelo." + nm_tabelaObjeto.toLowerCase() + "." + nm_tabelaObjeto + "\";                     o.objetoJson = this." + nm_tabelaObjeto.toLowerCase() + ";                      this.crudService.excluirObjetoCompleto(o)                         .subscribe(                 result => this.onExcluirObjeto(result),                             error =>  this.errorMessage = <any>error);                   }         );     }        onExcluirObjeto(result : any) {             if (!result) {                   this.errorMessage = 'Houve um erro ao excluir o registro. ';               }          event.preventDefault();             this.router.navigate(['/dados-" + nm_tabelaObjeto.toLowerCase() + "/']);     }        }";
						StringDetailComponent 	= "import { Component, OnInit } from '@angular/core'; import { Router, ActivatedRoute } from '@angular/router'; import { Subscription } from 'rxjs/Subscription';  import { " + nm_tabelaObjeto + " } from '../../../modelo/" + nm_tabelaObjeto + "'; import { ObjetoVirtual } from '../../../modelo/ObjetoVirtual'; import { PesquisaService } from '../../../core/services/pesquisa/pesquisa.service';  @Component({   moduleId: module.id,   selector: '" + nm_tabelaObjeto.toLowerCase() + "-dados-details',   templateUrl: '" + nm_tabelaObjeto.toLowerCase() + "_dadosDetails.component.html' }) export class " + nm_tabelaObjeto + "DadosDetailsComponent implements OnInit {    " + nm_tabelaObjeto.toLowerCase() + ": " + nm_tabelaObjeto + ";    private sub: Subscription;    private errorMessage: string;    constructor(private route: ActivatedRoute, private pesquisaService: PesquisaService) { }    ngOnInit() {       this.sub = this.route.parent.params.subscribe(params => {         let o : ObjetoVirtual = new ObjetoVirtual();         o.classeJava = \"br.com.primum.modelo." + nm_tabelaObjeto.toLowerCase() + "." + nm_tabelaObjeto + "\";                 let id = +params['id'];         this." + nm_tabelaObjeto.toLowerCase() + " = new " + nm_tabelaObjeto + "();         this." + nm_tabelaObjeto.toLowerCase() + "." + ch.toLowerCase() + " = id;         o.objetoJson = this." + nm_tabelaObjeto.toLowerCase() + ";          this.pesquisaService.efetuarPesquisa(o)             .subscribe(             result => this.onEfetuarPesquisa(result),             error =>  this.errorMessage = <any>error);       });   }    onEfetuarPesquisa(result : any) {     if (!result) {       this.errorMessage = 'Houve um erro ao pesquisa a " + nm_tabelaObjeto.toLowerCase() + ". '; }      this." + nm_tabelaObjeto.toLowerCase() + " = result;   }    ngOnDestroy() {     this.sub.unsubscribe();   } }";

					}else {
						StringDetailHTML	= StringDetailHTML + "<tr>				<td class=\"col-md-2\"><h5>" + itemColuna.getNm_campo().toLowerCase() + "</h5></td>				<td>{{ " + nm_tabelaObjeto.toLowerCase() + "." + itemColuna.getNm_campo().toLowerCase() + "}}</td>			</tr>";
					}
				}

				StringHtmlCabecalho = StringHtmlCabecalho + "</tr></thead>";
				StringHtmlDetalhe	= StringHtmlDetalhe + detail + "</tr><tr *ngIf=\"!listaDados.length\"><td>&nbsp;</td><td colspan=\"6\">Não há registros para exibir</td></tr></tbody></table></div></div></div></div> <div style=\"bottom: 80px; position: fixed; margin: 1em;\">	<a class=\"btn btn-primary btn-fab\" [routerLink]=\"['/dados-" + nm_tabela.toLowerCase() + "',-1,'edit']\">		<i class=\"material-icons\">add</i>	</a></div>";
					
				StringHtmlPesquisa	= StringHtmlPesquisa + StringHtmlCabecalho + StringHtmlDetalhe;
				
				StringDetailHTML = StringDetailHTML + "		</tbody>	</table></div>";
				StringEditHTML	= StringEditHTML + "<button class=\"btn btn-raised btn-danger\" (click)=\"btnExcluirClickHandler($event)\">Excluir</button>		<button class=\"btn btn-default\" (click)=\"onCancel($event)\">Cancelar</button>&nbsp;&nbsp;		<button type=\"submit\" class=\"btn btn-raised btn-success\" [disabled]=\"!" + nm_tabelaObjeto.toLowerCase() + "Form.valid\">Salvar</button>		<div class=\"alert alert-danger\" *ngIf=\"errorMessage != null\">{{ errorMessage }}</div></form></div>";
				/***
				 * 
				 * 
				fileIndexPesquisa 		= new File(DIR_PESQUISA + "\\index.ts");
				fileModuloPesquisa 	= new File(DIR_PESQUISA + "\\" + nm_tabelaObjeto.toLowerCase() +".module.ts");
				fileRoutingesquisa 	= new File(DIR_PESQUISA + "\\" + nm_tabelaObjeto.toLowerCase() +".routing.ts");
				fileComponentPesquisa 	= new File(DIR_PESQUISA + "\\" + nm_tabelaObjeto.toLowerCase() +".component.ts");
				fileHtmlPesquisa 		= new File(DIR_PESQUISA + "\\" + nm_tabelaObjeto.toLowerCase() +".component.html");
	
				StringIndexPesquisa 		= "export * from './" + nm_tabelaObjeto.toLowerCase() + ".component';";
				StringModuloPesquisa 	= "import { NgModule }      from '@angular/core'; import { CommonModule } from '@angular/common';  import { SharedModule }   from '../../../shared/shared.module'; import { " + nm_tabelaObjeto + "Component } from './" + nm_tabelaObjeto.toLowerCase() + ".component'; import { " + nm_tabelaObjeto.toLowerCase() + "_routing } from './" + nm_tabelaObjeto.toLowerCase() + ".routing';  @NgModule({   imports:      [ CommonModule, " + nm_tabelaObjeto.toLowerCase() + "_routing, SharedModule],   declarations: [ " + nm_tabelaObjeto + "Component ],   exports:      [ " + nm_tabelaObjeto + "Component ] }) export class " + nm_tabelaObjeto + "Module { }";
				StringRoutingPesquisa 	= "import { Routes, RouterModule } from '@angular/router';  import { " + nm_tabelaObjeto + "Component } from './" + nm_tabelaObjeto.toLowerCase() + ".component';  const " + nm_tabelaObjeto.toLowerCase() + "_routes: Routes = [   { path: '" + nm_tabelaObjeto.toLowerCase() + "*', pathMatch:'full', redirectTo: '/" + nm_tabelaObjeto.toLowerCase() + "' },   { path: '" + nm_tabelaObjeto.toLowerCase() + "', component: " + nm_tabelaObjeto + "Component} ];  export const " + nm_tabelaObjeto.toLowerCase() + "_routing = RouterModule.forChild(" + nm_tabelaObjeto.toLowerCase() + "_routes);";
				StringComponentPesquisa 	= "import { Component, Input, OnInit } from '@angular/core'; import { Router } from '@angular/router'; import { " + nm_tabelaObjeto + " } from '../../../modelo/" + nm_tabelaObjeto + "'; import { ObjetoVirtual } from '../../../modelo/ObjetoVirtual';  import { PesquisaService } from '../../../core/services/pesquisa/pesquisa.service';  @Component({   moduleId: module.id,   selector: '" + nm_tabelaObjeto.toLowerCase() + "-selector',   templateUrl: '" + nm_tabelaObjeto.toLowerCase() + ".component.html' })  export class " + nm_tabelaObjeto + "Component implements OnInit { 	 	errorMessage : string = '';      @Input() listaDados : " + nm_tabelaObjeto + "[] = [];     " + nm_tabelaObjeto.toLowerCase() + "Aux : " + nm_tabelaObjeto + ";     constructor(        private dataService: PesquisaService,        private router: Router) { }      ngOnInit() {         this." + nm_tabelaObjeto.toLowerCase() + "Aux = new " + nm_tabelaObjeto + "(); let dataAux : ObjetoVirtual = new ObjetoVirtual();         dataAux.classeJava  = 'br.com.primum.modelo." + nm_tabelaObjeto.toLowerCase() + "." + nm_tabelaObjeto + "';         dataAux.objetoJson = this." + nm_tabelaObjeto.toLowerCase() + "Aux;          this.dataService.efetuarPesquisa(dataAux)             .subscribe(             result => this.onEfetuarPesquisa(result),             error =>  this.errorMessage = <any>error);     }  	onEfetuarPesquisa(result : any) { 		if (!result) { 			this.errorMessage = 'Houve um erro a pesquisa de " + nm_tabelaObjeto + ". ';             this.router.navigate(['/home']); 		}          this.listaDados = result; 	} }";
				StringHtmlPesquisa 		= "<div class=\"container\"> <div class=\"row grid-container\"> <div class=\"col-md-10\"> <div class=\"table\"> <table class=\"table table-striped table-hover\"> <thead> <tr>";

			File fileIndexDados 		= new File(DIR_DETALHE + "\\index.ts");
			File fileDadosComponent 	= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dados.component.ts");
			File fileDadosModulo		= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dados.module.ts");
			File fileEditComponent		= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dadosEdit.component.ts");
			File fileDetailComponent	= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dadosDetails.component.ts");
			File fileDadosRouting 		= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dados.routing.ts");
			File fileDadosHTML 			= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dados.component.html");
			File fileEditHTML			= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dadosEdit.component.html");
			File fileDetailsHTML		= new File(DIR_DETALHE + "\\" + nm_tabelaObjeto.toLowerCase() +"_dadosDetails.component.html");
			
				 */

				byte[] bIndexPesquisa 		= StringIndexPesquisa.getBytes();
				byte[] bModuloPesquisa 		= StringModuloPesquisa.getBytes();
				byte[] bRoutingPesquisa 	= StringRoutingPesquisa.getBytes();
				byte[] bComponentPesquisa 	= StringComponentPesquisa.getBytes();
				byte[] bHtmlPesquisa 		= StringHtmlPesquisa.getBytes();

				
				byte[]  bIndexDados			= StringIndexDados.getBytes();
				byte[]  bDadosComponent 	= StringDadosComponent.getBytes();
				byte[]  bDadosModulo 		= StringDadosModulo.getBytes();
				byte[]  bEditComponent		= StringEditComponent.getBytes();
				byte[]  bDetailComponent 	= StringDetailComponent.getBytes();
				byte[]  bDadosRouting 		= StringDadosRouting.getBytes();
				byte[]  bDadosHTML	 		= StringDadosHTML.getBytes();
				byte[]  bEditHTML			= StringEditHTML.getBytes();
				byte[]  bDetailHTML			= StringDetailHTML.getBytes();

			    try {

			    	OutputStream outfileIndexPesquisa = new FileOutputStream(fileIndexPesquisa);
					outfileIndexPesquisa.write(bIndexPesquisa, 0, bIndexPesquisa.length);
					outfileIndexPesquisa.close();


			    	OutputStream outStringModuloPesquisa = new FileOutputStream(fileModuloPesquisa);
					outStringModuloPesquisa.write(bModuloPesquisa, 0, bModuloPesquisa.length);
					outStringModuloPesquisa.close();
					

			    	OutputStream outfileRoutingesquisa = new FileOutputStream(fileRoutingPesquisa);
					outfileRoutingesquisa.write(bRoutingPesquisa, 0, bRoutingPesquisa.length);
					outfileRoutingesquisa.close();
					

			    	OutputStream outfileComponentPesquisa = new FileOutputStream(fileComponentPesquisa);
					outfileComponentPesquisa.write(bComponentPesquisa, 0, bComponentPesquisa.length);
					outfileComponentPesquisa.close();
					

			    	OutputStream outfileHtmlPesquisa = new FileOutputStream(fileHtmlPesquisa);
			    	outfileHtmlPesquisa.write(bHtmlPesquisa, 0, bHtmlPesquisa.length);
					outfileHtmlPesquisa.close();			

				    File fileTypeScript = new File(DIR_GRAVAR_TYPESCRIPT + "\\" + nm_tabelaObjeto +".ts");
				    OBJETO_TYPESCRIPT = OBJETO_TYPESCRIPT + "}";
				    byte[] bType = OBJETO_TYPESCRIPT.getBytes();
					

			    	OutputStream outfileTypeScript = new FileOutputStream(fileTypeScript);
			    	outfileTypeScript.write(bType, 0, bType.length);
			    	outfileTypeScript.close();
			    	
			    	
			    	
			    	
			    	


			    	OutputStream outfileIndexDados = new FileOutputStream(fileIndexDados);
			    	outfileIndexDados.write(bIndexDados, 0, bIndexDados.length);
			    	outfileIndexDados.close();


			    	OutputStream outfileDadosComponent = new FileOutputStream(fileDadosComponent);
			    	outfileDadosComponent.write(bDadosComponent, 0, bDadosComponent.length);
			    	outfileDadosComponent.close();


			    	OutputStream outfileDadosModulo = new FileOutputStream(fileDadosModulo);
			    	outfileDadosModulo.write(bDadosModulo, 0, bDadosModulo.length);
			    	outfileDadosModulo.close();


			    	OutputStream outfileEditComponent = new FileOutputStream(fileEditComponent);
			    	outfileEditComponent.write(bEditComponent, 0, bEditComponent.length);
			    	outfileEditComponent.close();


			    	OutputStream outfileDetailComponent = new FileOutputStream(fileDetailComponent);
			    	outfileDetailComponent.write(bDetailComponent, 0, bDetailComponent.length);
			    	outfileDetailComponent.close();


			    	OutputStream outfileDadosRouting = new FileOutputStream(fileDadosRouting);
			    	outfileDadosRouting.write(bDadosRouting, 0, bDadosRouting.length);
			    	outfileDadosRouting.close();


			    	OutputStream outfileDadosHTML = new FileOutputStream(fileDadosHTML);
			    	outfileDadosHTML.write(bDadosHTML, 0, bDadosHTML.length);
			    	outfileDadosHTML.close();


			    	OutputStream outfileEditHTML = new FileOutputStream(fileEditHTML);
			    	outfileEditHTML.write(bEditHTML, 0, bEditHTML.length);
			    	outfileEditHTML.close();


			    	OutputStream outfileDetailHTML = new FileOutputStream(fileDetailsHTML);
			    	outfileDetailHTML.write(bDetailHTML, 0, bDetailHTML.length);
			    	outfileDetailHTML.close();

		         } catch (FileNotFoundException e) {
		                     LogPrimum.mensagem("File Not Found.");
		                     e.printStackTrace();
		         } catch (IOException e1) {
		                  LogPrimum.mensagem("Error Reading The File.");
		                   e1.printStackTrace();
		         }
			}
		}

		byte[] bAppModuleImport 	= StringAppModuleImport.getBytes();
		byte[] bAppModuleNg 		= StringAppModuleNg.getBytes();
		byte[] bAppRouting 			= StringAppRouting.getBytes();
		byte[] bAppRootIndex		= RootIndex.getBytes();
		
		File fileAppModuleImport	= new File(DIR_GRAVAR_CONF + "\\app_module_ts.txt");
		File fileAppModuleNg 		= new File(DIR_GRAVAR_CONF + "\\app_module_NG.txt");
		File fileAppRouting 		= new File(DIR_GRAVAR_CONF + "\\app_routing_ts.txt");
		File fileRootIndex	 		= new File(DIR_GRAVAR_CONF + "\\index_root.html");

    	OutputStream outfileAppModuleImport;
    	OutputStream outfileAppModuleNg;
    	OutputStream outfileAppRouting;
    	OutputStream outfileRootIndex;
		try {
			outfileAppModuleImport = new FileOutputStream(fileAppModuleImport);
	    	outfileAppModuleImport.write(bAppModuleImport, 0, bAppModuleImport.length);
	    	outfileAppModuleImport.close();

			outfileAppModuleNg = new FileOutputStream(fileAppModuleNg);
			outfileAppModuleNg.write(bAppModuleNg, 0, bAppModuleNg.length);
			outfileAppModuleNg.close();

			outfileAppRouting = new FileOutputStream(fileAppRouting);
			outfileAppRouting.write(bAppRouting, 0, bAppRouting.length);
			outfileAppRouting.close();

			outfileRootIndex = new FileOutputStream(fileRootIndex);
			outfileRootIndex.write(bAppRootIndex, 0, bAppRootIndex.length);
			outfileRootIndex.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}