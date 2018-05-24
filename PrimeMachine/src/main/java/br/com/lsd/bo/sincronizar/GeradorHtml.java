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
public class GeradorHtml {

	//private String DIR_GRAVAR = "D:\\Projetos\\PrimeMachine\\src\\main\\webapp\\app\\entities";
	private String DIR_GRAVAR 				= "D:\\Projetos\\PrimeMachine\\src\\main\\webapp\\app\\entities";
	private String DIR_GRAVAR_JSON 			= "D:\\Projetos\\PrimeMachine\\src\\main\\webapp\\json\\";
	private String DIR_GRAVAR_TRANSLATE		= "D:\\Projetos\\PrimeMachine\\src\\main\\webapp\\i18n\\pt-br\\";
	private String DIR_GRAVAR_TYPESCRIPT	= "D:\\Projetos\\PrimeMachine\\src\\main\\webapp\\ts_modelo\\";

    @CrossOrigin
	@RequestMapping(value= "/criar", method = RequestMethod.GET)
	public void criarBaseSQLite() {
    	String nomeSchema="public";

		String sqlTabelas = "SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname IN ('"+nomeSchema+"') ORDER BY schemaname, tablename";
		GenericDAO dao = new GenericDAO();
		Object o = dao.getSession().createSQLQuery(sqlTabelas).list();

		List<Tabela> listaTabelaChecar = dao.getSession().createCriteria(Tabela.class).list();

		String criar 	= "";
		String criarHTML 	= "";
		ArrayList<String> lTipos = new ArrayList<>();

		String menuItem 	= "";
		String indexItem 	= "";
		for (String nm_tabela : (ArrayList<String>) o) {
			
			Criar_beanBO c = new Criar_beanBO();
			try {
				c.Criar_arquivos(nm_tabela);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			/*
			int contColunas = 0;
			String nm_tabelaaUX 	= nm_tabela.replaceAll("_", "-");
			String nm_tabelaObjeto 	= nm_tabela.substring(0, 1).toUpperCase() + nm_tabela.substring(1).toLowerCase();
			String OBJETO_TYPESCRIPT= "export class " + nm_tabelaObjeto + "{";
			String ARQUIVO_JSON		= "{\"relationships\": [], \"fields\": [";
			
			boolean sicronizarTabela = false;

			for (Tabela t : listaTabelaChecar) {
				if (t.getNm_tabela().equalsIgnoreCase(nm_tabela) && ((t.getTp_sincronizar() != null) && (t.getTp_sincronizar().equals(1)))) {
					sicronizarTabela = true;
					listaTabelaChecar.remove(t);
					break;
				}
			}

			if (!nm_tabela.equals("espelho")) {

				String sqlCamposTabela = "select column_name as nm_campo, data_type as ds_descricao, case when is_nullable = 'YES' then 1 else 0 end as st_aceitavalornull from information_schema.columns where table_name = '" + nm_tabela + "' and table_schema IN ('"+nomeSchema+"')";
				List<?> listaColunas = null;
				try {
					listaColunas = dao.findBySql(sqlCamposTabela, new Campo());
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				int i = 0;
				String temp 	= "";
				temp += "DROP TABLE IF EXISTS " + nm_tabela + "�CREATE TABLE IF NOT EXISTS " + nm_tabela + "(";
				String cpoInsert = "";
				String caminho = "";

				if (nm_tabela.equals("campospesquisa")) {				
					nm_tabela ="CamposPesquisa";
				} else if (nm_tabela.equals("codigosfixos")) {				
					nm_tabela ="CodigosFixos";
				} else if (nm_tabela.equals("sessaodocumento")) {				
					nm_tabela ="SessaoDocumento";
				}

				if (nm_tabela.equals("pre_avaliacao_ideia_pic")) {
					caminho = "br.com.primum.modelo.reuniao_pic." + nm_tabela.substring(0, 1).toUpperCase() + nm_tabela.substring(1);
				} else {
					caminho = "br.com.primum.modelo." + nm_tabela.toLowerCase() + "." + nm_tabela.substring(0, 1).toUpperCase() + nm_tabela.substring(1);
				}

				try {
//					if () {
//						
//						String aqv_index = this.arquivo_principal;
//						aqv_index = aqv_index.replaceAll("$NOME_TABELA_AQV_HTML", nm_tabela.replaceAll("_", "-").toLowerCase());
//						aqv_index = aqv_index.replaceAll("$DESCRICAO_COLUNAS_TABELA", SCOLUNAS);
//						aqv_index = aqv_index.replaceAll("$COLUNAS_TABELA", cpoHTML);
//						aqv_index = aqv_index.replaceAll("$NOME_TABELA", nm_tabela.toLowerCase());
//						System.out.println(aqv_index);
//					}

					String ARQUIVO_DIALG_HTML	=	"<form name=\"editForm\" role=\"form\" novalidate ng-submit=\"vm.save()\" show-validation>      <div class=\"modal-header\">         <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\"                 ng-click=\"vm.clear()\">&times;</button>         <h4 class=\"modal-title\" id=\"my" + nm_tabelaObjeto + "Label\">Create or edit a " + nm_tabelaObjeto + "</h4>     </div>     <div class=\"modal-body\">         <jhi-alert-error></jhi-alert-error>";
					String ARQUIVO_DIALG_CTRL	=	"";
					String ARQUIVO_DETAIL_CTRL	=	"(function() {     'use strict';      angular         .module('hipsterApp')         .controller('" + nm_tabelaObjeto + "DetailController', " + nm_tabelaObjeto + "DetailController);      " + nm_tabelaObjeto + "DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', '" + nm_tabelaObjeto + "'];      function " + nm_tabelaObjeto + "DetailController($scope, $rootScope, $stateParams, entity, " + nm_tabelaObjeto + ") {         var vm = this;          vm." + nm_tabela + " = entity;          var unsubscribe = $rootScope.$on('hipsterApp:" + nm_tabela + "Update', function(event, result) {             vm." + nm_tabela + " = result;         });         $scope.$on('$destroy', unsubscribe);     } })(); ";
					String ARQUIVO_DEL_HTML		=	"<form name=\"deleteForm\" ng-submit=\"vm.confirmDelete(vm." + nm_tabela + ".id)\">     <div class=\"modal-header\">         <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\"                 ng-click=\"vm.clear()\">&times;</button>         <h4 class=\"modal-title\">Confirm delete operation</h4>     </div>     <div class=\"modal-body\">         <jhi-alert-error></jhi-alert-error>         <p>Você confirma a exclusão de " + nm_tabela.toUpperCase() + "?</p>     </div>     <div class=\"modal-footer\">         <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\" ng-click=\"vm.clear()\">             <span class=\"glyphicon glyphicon-ban-circle\"></span>&nbsp;<span>Cancel</span>         </button>         <button type=\"submit\" ng-disabled=\"deleteForm.$invalid\" class=\"btn btn-danger\">             <span class=\"glyphicon glyphicon-remove-circle\"></span>&nbsp;<span>Delete</span>         </button>     </div> </form>";
					String ARQUIVO_DEL_CTRL		=	"";
					String ARQUIVO_SEARCH		=	"(function() {     'use strict';      angular         .module('hipsterApp')         .factory('" + nm_tabelaObjeto + "Search', " + nm_tabelaObjeto + "Search);      " + nm_tabelaObjeto + "Search.$inject = ['$resource'];      function " + nm_tabelaObjeto + "Search($resource) {         var resourceUrl =  'api/_search/" + nm_tabelaaUX + "/:id';          return $resource(resourceUrl, {}, {             'query': { method: 'GET', isArray: true}         });     } })();";
					String ARQUIVO_SERVICE		=	"(function() {     'use strict';     angular         .module('hipsterApp')         .factory('" + nm_tabelaObjeto + "', " + nm_tabelaObjeto + ");      " + nm_tabelaObjeto + ".$inject = ['$resource'];      function " + nm_tabelaObjeto + " ($resource) {         var resourceUrl =  'api/pesquisar/:id';          return $resource(resourceUrl, {}, {             'query': { method: 'GET', isArray: true},             'get': {        method : 'POST', headers: {'Content-Type': 'application/json','Accept': 'application/json'}         },             'update': { method:'PUT' }         });     } })();";
					String ARQUIVO_CONTROLLER 	= 	"";
					String ARQUIVO_STATE 		= 	"";
					String ARQUIVO_TRANSLATE	= 	"{ \"hipsterApp\": { \"" + nm_tabela + "\" : { \"home\": { \"title\": \"" + nm_tabela + "\", \"createLabel\": \"Criar um novo registro\", \"createOrEditLabel\": \"Criar ou editar registro\", \"search\": \"Pesquisar por registro\" }, \"created\": \"Um novo registro foi criado com o identificador {{ param }}\", \"updated\": \"Um novo registro foi atualizado com o identificador {{ param }}\", \"deleted\": \"Registro deletado com o identificador {{ param }}\", \"delete\": { \"question\": \"Tem certeza de que deseja excluir o reigistro {{ id }}?\" }, \"detail\": { \"title\": \"" + nm_tabelaObjeto + "\" }";
					String CAMPO_PK 		= "";
					String arquivo_gerar 	= "<div><h2>" + nm_tabela + "</h2><jhi-alert></jhi-alert><div class=\"container-fluid\"><div class=\"row\"><div class=\"col-xs-4 no-padding-left\"><button class=\"btn btn-primary\" ui-sref=\"" + nm_tabelaaUX + ".new\" ><span class=\"glyphicon glyphicon-plus\"></span><span class=\"hidden-xs\">Criar novo " + nm_tabela + "</span></button></div><div class=\"col-xs-8 no-padding-right\"><form name=\"searchForm\" class=\"form-inline\"><div class=\"input-group pull-right\" ><input type=\"text\" class=\"form-control\" ng-model=\"vm.searchQuery\" id=\"searchQuery\" placeholder=\"{{ 'hipsterApp." + nm_tabela + ".home.search' | translate }}\"><button class=\"btn btn-info\" ng-click=\"vm.search(vm.searchQuery)\"><span class=\"glyphicon glyphicon-search\"></span></button></span><span class=\"input-group-btn width-min\" ng-if=\"vm.currentSearch\"><button class=\"btn btn-info\" ng-click=\"vm.clear()\"><span class=\"glyphicon glyphicon-trash\"></span></button></span></div></form></div></div></div><br/><div class=\"table-responsive\">";			
					String cpoHTML 			= "";
					String TABELA_CABECALHO = "<table class=\"jh-table table table-striped\"><thead><tr jh-sort=\"vm.predicate\" ascending=\"vm.reverse\" callback=\"vm.reset()\">";
					String TABELA_DETALHE 	= "";//"<th jh-sort-by=\"$TABELA_PK\">";

					String DETAIL_CAMPO_PK	= "";
					String DETAIL_CAMPO		= "";

					String DIALOG_PK		= "";
					String DIALOG_CAMPO		= "";

					String nome_pk			= "";
					for (Campo itemColuna : (List<Campo>) listaColunas) {

						cpoHTML = "";
						System.out.println(caminho);
						String ch = "";

						String tipoTypeScript 		= "number";
						String tipoColuna 		= itemColuna.getDs_descricao();
						String tipoColunaJson 	= itemColuna.getDs_descricao();

						if (lTipos.toString().indexOf(tipoColuna) == -1) {
							LogPrimum.mensagem(tipoColuna);
							lTipos.add(tipoColuna);
						}

						if ( (tipoColuna == "timestamp with time zone") || (tipoColuna.indexOf("timestamp") > -1) || (tipoColuna.indexOf("date") > -1)) {
							tipoTypeScript = "string";
							tipoColuna = "text";
							tipoColunaJson = "date";
						} else if (tipoColuna.indexOf("character varying") > -1){
							tipoTypeScript = "string";
							tipoColuna = "text";
							tipoColunaJson = "text";  
						} else if ( tipoColuna.indexOf("char") > -1 ) {
							tipoTypeScript = "string";
							tipoColuna = "varchar";
							tipoColunaJson = "text";
						} else if ( tipoColuna.indexOf("double precision") > -1 ) {
							tipoTypeScript = "string";
							tipoColuna = "numeric(12,2)";
							tipoColunaJson = "number";
						} else if ( tipoColuna.indexOf("boolean") > -1 ) {
							tipoTypeScript = "string";
							tipoColuna = "integer";
							tipoColunaJson = "number";
						}

						try {
							ch = Reflection.getNomeChavePrimaria(Class.forName(caminho));Reflection.getNomeChavePrimaria(Class.forName(caminho));
							CAMPO_PK = ch;
						} catch (Exception e) {

							e.printStackTrace();
						}

						String pk = "";
						
						
						OBJETO_TYPESCRIPT = OBJETO_TYPESCRIPT + itemColuna.getNm_campo().toLowerCase() + " : " + tipoTypeScript + ";";

						if (ARQUIVO_JSON.indexOf("fieldName") > 1) {
							ARQUIVO_JSON = ARQUIVO_JSON + ",{\"fieldName\": \"" + itemColuna.getNm_campo().toLowerCase() + "\",\"fieldType\": \"" + tipoColunaJson + "\",\"fieldValidateRules\": [\"required\"]}";
						} else {
							ARQUIVO_JSON = ARQUIVO_JSON + "{\"fieldName\": \"" + itemColuna.getNm_campo().toLowerCase() + "\",\"fieldType\": \"" + tipoColunaJson + "\",\"fieldValidateRules\": [\"required\"]}";
						}

						if(ch.toLowerCase().equals(itemColuna.getNm_campo().toLowerCase())) {

							nome_pk = itemColuna.getNm_campo().toLowerCase();
							

							String a = "var objetoVirtual = JSON.parse(\"{\\\"classeJava\\\":\\\"br.com.primum.modelo." + nm_tabela + "." + nm_tabelaObjeto + "\\\",\\\"valorFiltro\\\":\\\"\"+$stateParams." + nome_pk + "+\"\\\"}\"); return " + nm_tabela + ".get(objetoVirtual);";
							ARQUIVO_STATE 		= 	"(function() {    'use strict';    angular        .module('hipsterApp')        .config(stateConfig);    stateConfig.$inject = ['$stateProvider'];    function stateConfig($stateProvider) {        $stateProvider        .state('" + nm_tabelaaUX + "', {            parent: 'entity',            url: '/" + nm_tabelaaUX + "',            data: {                authorities: ['ROLE_USER'],                pageTitle: 'hipsterApp." + nm_tabela + ".home.title'            },            views: {                'content@': {                    templateUrl: 'app/entities/" + nm_tabelaaUX + "/" + nm_tabelaaUX + "s.html',			controller: '" + nm_tabelaObjeto + "Controller',                    controllerAs: 'vm'                }            },            resolve: {                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {                    $translatePartialLoader.addPart('" + nm_tabela + "');                    $translatePartialLoader.addPart('global');                    return $translate.refresh();                }]            }        })        .state('" + nm_tabelaaUX + "-detail', {            parent: 'entity',            url: '/" + nm_tabelaaUX + "/{" + itemColuna.getNm_campo().toLowerCase() + "}',            data: {                authorities: ['ROLE_USER'],                pageTitle: 'hipsterApp." + nm_tabela + ".detail.title'            },            views: {                'content@': {                    templateUrl: 'app/entities/" + nm_tabelaaUX + "/" + nm_tabelaaUX + "-detail.html',                    controller: '" + nm_tabelaObjeto + "DetailController',                    controllerAs: 'vm'                }            },            resolve: {                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {                    $translatePartialLoader.addPart('" + nm_tabela + "');                    return $translate.refresh();                }],                entity: ['$stateParams', '" + nm_tabela + "', function($stateParams, " + nm_tabela + ") {                    return " + nm_tabela + ".get({" + itemColuna.getNm_campo().toLowerCase() + " : $stateParams." + itemColuna.getNm_campo().toLowerCase() + "}).$promise;                }]            }        })        .state('" + nm_tabelaaUX + ".new', {            parent: '" + nm_tabelaaUX + "',            url: '/new',            data: {                authorities: ['ROLE_USER']            },            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {                $uibModal.open({                    templateUrl: 'app/entities/" + nm_tabelaaUX + "/" + nm_tabelaaUX + "-dialog.html',                    controller: '" + nm_tabelaObjeto + "DialogController',                    controllerAs: 'vm',                    backdrop: 'static',                    size: 'lg',                    resolve: {                        entity: function () {                            return {                                cd_" + nm_tabela + ": null,                                nm_" + nm_tabela + ": null,                                " + nome_pk + ": null                            };                        }                    }                }).result.then(function() {                    $state.go('" + nm_tabelaaUX + "', null, { reload: true });                }, function() {                    $state.go('" + nm_tabelaaUX + "');                });            }]        })        .state('" + nm_tabelaaUX + ".edit', {            parent: '" + nm_tabelaaUX + "',            url: '/{" + itemColuna.getNm_campo().toLowerCase() + "}/edit',            data: {                authorities: ['ROLE_USER']            },            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {                $uibModal.open({                    templateUrl: 'app/entities/" + nm_tabelaaUX + "/" + nm_tabelaaUX + "-dialog.html',                    controller: '" + nm_tabelaObjeto + "DialogController',                    controllerAs: 'vm',                    backdrop: 'static',                    size: 'lg',                    resolve: {                        entity: ['" + nm_tabelaObjeto + "', function(" + nm_tabela + ") { " + a + "}]                    }                }).result.then(function() {                    $state.go('" + nm_tabelaaUX + "', null, { reload: true });                }, function() {                    $state.go('^');                });            }]        })        .state('" + nm_tabelaaUX + ".delete', {            parent: '" + nm_tabelaaUX + "',            url: '/{" + itemColuna.getNm_campo().toLowerCase() + "}/delete',            data: {                authorities: ['ROLE_USER']            },            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {                $uibModal.open({                    templateUrl: 'app/entities/" + nm_tabelaaUX + "/" + nm_tabelaaUX + "-delete-dialog.html',                    controller: '" + nm_tabelaObjeto + "DeleteController',                    controllerAs: 'vm',                    size: 'md',                    resolve: {                        entity: ['" + nm_tabela + "', function(" + nm_tabela + ") {                            return " + nm_tabela + ".get({" + itemColuna.getNm_campo().toLowerCase() + " : $stateParams." + itemColuna.getNm_campo().toLowerCase() + "}).$promise;                        }]                    }                }).result.then(function() {                    $state.go('" + nm_tabelaaUX + "', null, { reload: true });                }, function() {                    $state.go('^');                });            }]        });    }})();";
							ARQUIVO_DIALG_CTRL	=	"(function() {     'use strict';      angular         .module('hipsterApp')         .controller('" + nm_tabelaObjeto + "DialogController', " + nm_tabelaObjeto + "DialogController);      " + nm_tabelaObjeto + "DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', '" + nm_tabelaObjeto + "'];      function " + nm_tabelaObjeto + "DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, " + nm_tabelaObjeto + ") {         var vm = this;          vm." + nm_tabela + " = entity;          vm.clear = clear;         vm.save = save;          $timeout(function (){             angular.element('.form-group:eq(1)>input').focus();         });          function clear () {             $uibModalInstance.dismiss('cancel');         }          function save () {             vm.isSaving = true;             if (vm." + nm_tabela + "." + itemColuna.getNm_campo().toLowerCase() + " !== null) {                 " + nm_tabelaObjeto + ".update({\"objetoJson\":vm." + nm_tabela + ",\"classeJava\":\"br.com.primum.modelo." + nm_tabela + "." + nm_tabelaObjeto+ "\"}, onSaveSuccess, onSaveError);             } else {                 " + nm_tabelaObjeto + ".save(vm." + nm_tabela + ", onSaveSuccess, onSaveError);             }         }          function onSaveSuccess (result) {             $scope.$emit('hipsterApp:" + nm_tabela + "Update', result);             $uibModalInstance.close(result);             vm.isSaving = false;         }          function onSaveError () {             vm.isSaving = false;         }       } })(); ";
							ARQUIVO_CONTROLLER = "(function() {     'use strict';      angular         .module('hipsterApp')         .controller('" + nm_tabelaObjeto + "Controller', " + nm_tabelaObjeto + "Controller);      " + nm_tabelaObjeto + "Controller.$inject = ['$scope', '$state', '" + nm_tabelaObjeto + "', '" + nm_tabelaObjeto + "Search', 'ParseLinks', 'AlertService'];      function " + nm_tabelaObjeto + "Controller ($scope, $state, " + nm_tabelaObjeto + ", " + nm_tabelaObjeto + "Search, ParseLinks, AlertService) {         var vm = this;                 vm.lista_dados = [];         vm.loadPage = loadPage;         vm.page = 0;         vm.predicate = '" + itemColuna.getNm_campo().toLowerCase() + "';         vm.reset = reset;         vm.reverse = true;         vm.clear = clear;         vm.search = search;          loadAll();          function loadAll () {             if (vm.currentSearch) {                 " + nm_tabelaObjeto + "Search.query({ ds_caminho_classe : 'br.com.primum.modelo." + nm_tabela + "." + nm_tabelaObjeto + "'                 }, onSuccess, onError);             } else {                 " + nm_tabelaObjeto + ".query({ ds_caminho_classe : 'br.com.primum.modelo." + nm_tabela + "." + nm_tabelaObjeto + "'}, onSuccess, onError);             }             function sort() {                 var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];                 if (vm.predicate !== '" + itemColuna.getNm_campo().toLowerCase() + "') {                     result.push('" + itemColuna.getNm_campo().toLowerCase() + "');                 }                 return result;             }             function onSuccess(data, headers) {                 for (var i = 0; i < data.length; i++) {                     vm.lista_dados.push(data[i]);                 }             }             function onError(error) {                 AlertService.error(error.data.message);             }         }          function reset () {             vm.page = 0;             vm.lista_dados = [];             }          function loadPage(page) {             vm.page = page;             }          function clear () {             vm.lista_dados = [];             vm.links = null;             vm.page = 0;             vm.predicate = '" + itemColuna.getNm_campo().toLowerCase() + "';             vm.reverse = true;             vm.searchQuery = null;             vm.currentSearch = null;             }          function search (searchQuery) {             if (!searchQuery){                 return vm.clear();             }             vm.lista_dados = [];             vm.links = null;             vm.page = 0;             vm.predicate = '_score';             vm.reverse = false;             vm.currentSearch = searchQuery;             }     } })(); ";
							ARQUIVO_DEL_CTRL		=	"(function() {     'use strict';      angular         .module('hipsterApp')         .controller('" + nm_tabelaObjeto + "DeleteController'," + nm_tabelaObjeto + "DeleteController);      " + nm_tabelaObjeto + "DeleteController.$inject = ['$uibModalInstance', 'entity', '" + nm_tabelaObjeto + "'];      function " + nm_tabelaObjeto + "DeleteController($uibModalInstance, entity, " + nm_tabelaObjeto + ") {         var vm = this;          vm." + nm_tabela + " = entity;         vm.clear = clear;         vm.confirmDelete = confirmDelete;                 function clear () {             $uibModalInstance.dismiss('cancel');         }          function confirmDelete (id) {             " + nm_tabelaObjeto + ".delete({" + itemColuna.getNm_campo().toLowerCase() + ":" + itemColuna.getNm_campo().toLowerCase() + "},                 function () {                     $uibModalInstance.close(true);                 });         }     } })();";
							DIALOG_PK			= "<div class=\"form-group\" ng-show=\"vm." + nm_tabela + "." + itemColuna.getNm_campo().toLowerCase() + "\"><label for=\"" + itemColuna.getNm_campo().toLowerCase() + "\">" + itemColuna.getDs_descricao() + "</label><input type=\"" + tipoColunaJson + "\" class=\"form-control\" id=\"" + itemColuna.getNm_campo().toLowerCase() + "\" name=\"" + itemColuna.getNm_campo().toLowerCase() + "\" ng-model=\"vm." + nm_tabela + "." + itemColuna.getNm_campo().toLowerCase() + "\" readonly /></div>";
							DETAIL_CAMPO_PK		= "<h2><span translate=\"hipsterApp." + nm_tabela + ".detail.title\">" + itemColuna.getDs_descricao() + "</span> {{vm." + nm_tabela + "." + itemColuna.getNm_campo().toLowerCase() + "}}</h2>";
							TABELA_CABECALHO 	= TABELA_CABECALHO + "<th jh-sort-by=\""+ CAMPO_PK +"\"><span>" + itemColuna.getNm_campo().toLowerCase() + "</span> <span class=\"glyphicon glyphicon-sort\"></span></th>";
							TABELA_DETALHE 		= TABELA_DETALHE + "<td><a ui-sref=\"" + nm_tabelaaUX + "-detail({" + itemColuna.getNm_campo().toLowerCase() + ":item_grid."+ itemColuna.getNm_campo().toLowerCase() +"})\">{{item_grid." + CAMPO_PK + "}}</a></td>";
							pk = " PRIMARY KEY ";

							ARQUIVO_TRANSLATE = ARQUIVO_TRANSLATE + ",\"" + itemColuna.getNm_campo().toLowerCase() + "\":\"" + itemColuna.getNm_campo().toLowerCase() + "\"";
						} else {

							DIALOG_CAMPO		= DIALOG_CAMPO + "<div class=\"form-group\"><label class=\"control-label\" for=\"field_" + itemColuna.getNm_campo().toLowerCase() + "\">" + itemColuna.getDs_descricao().toLowerCase() + "</label><input type=\"" + tipoColunaJson + "\" class=\"form-control\" name=\"" + itemColuna.getNm_campo().toLowerCase() + "\" id=\"field_" + itemColuna.getNm_campo().toLowerCase() + "\" ng-model=\"vm." + nm_tabela + "." + itemColuna.getNm_campo().toLowerCase() + "\" required /></div>";
							DETAIL_CAMPO		= DETAIL_CAMPO + "<dt><span>" + itemColuna.getDs_descricao() + "</span></dt><dd><span>{{vm." + nm_tabela + "." + itemColuna.getNm_campo().toLowerCase() + "}}</span></dd>";
							ARQUIVO_TRANSLATE = ARQUIVO_TRANSLATE + ",\"" + itemColuna.getNm_campo().toLowerCase() + "\":\"" + itemColuna.getNm_campo().toLowerCase() + "\"";
							contColunas++;
							if (contColunas < 4) {
								TABELA_CABECALHO 	= TABELA_CABECALHO + "<th jh-sort-by=\""+ itemColuna.getNm_campo().toLowerCase() + "\"><span>"+ itemColuna.getNm_campo().toUpperCase() + "</span> <span class=\"glyphicon glyphicon-sort\"></span></th>";
								TABELA_DETALHE 		= TABELA_DETALHE + "<td>{{item_grid."+itemColuna.getNm_campo().toLowerCase()+"}}</td>";
							}
						}

						if (i == 0) {
							temp += itemColuna.getNm_campo() + " " + tipoColuna + pk + ((itemColuna.getSt_aceitavalornull() == 0)?" NOT NULL":"") ;
							cpoInsert += itemColuna.getNm_campo();
							i++;
						} else {
							temp += "," + itemColuna.getNm_campo() + " " + tipoColuna + pk + ((itemColuna.getSt_aceitavalornull() == 0)?" NOT NULL":"") ;
							cpoInsert += "," + itemColuna.getNm_campo();
						}
					}

					TABELA_DETALHE		= TABELA_DETALHE 	+ "<td class=\"text-right\"><div class=\"btn-group flex-btn-group-container\"><button type=\"submit\" ui-sref=\"" + nm_tabelaaUX + "-detail({" + nome_pk + ":item_grid." + CAMPO_PK + "})\" class=\"btn btn-info btn-sm\"><span class=\"glyphicon glyphicon-eye-open\"></span><span class=\"hidden-xs hidden-sm\"></span></button><button type=\"submit\" ui-sref=\"" + nm_tabelaaUX + ".edit({" + nome_pk + ":item_grid." + CAMPO_PK + "})\" class=\"btn btn-primary btn-sm\"><span class=\"glyphicon glyphicon-pencil\"></span><span class=\"hidden-xs hidden-sm\"></span></button><button type=\"submit\" ui-sref=\"" + nm_tabelaaUX + ".delete({" + nome_pk + ":item_grid." + CAMPO_PK + "})\" class=\"btn btn-danger btn-sm\"><span class=\"glyphicon glyphicon-remove-circle\"></span><span class=\"hidden-xs hidden-sm\"></span></button></div></td>";
					TABELA_CABECALHO 	= TABELA_CABECALHO  + "</tr></thead><tbody infinite-scroll=\"vm.loadPage(vm.page + 1)\" infinite-scroll-disabled=\"vm.page >= vm.links['last']\"><tr ng-repeat=\"item_grid in vm.lista_dados\">";
					TABELA_CABECALHO	= TABELA_CABECALHO	+ TABELA_DETALHE + "</tr></tbody></table></div></div>";

                    //menuItem 	= menuItem + "<li ui-sref-active=\"active\" ><a ui-sref=\"" + nm_tabelaaUX + "\" ng-click=\"vm.collapseNavbar()\"><span class=\"glyphicon glyphicon-asterisk\"></span>&nbsp;<span>" + nm_tabela + "</span></a></li>";
					menuItem 	= menuItem + "<li ui-sref-active=\"active\" ><a ui-sref=\"" + nm_tabelaaUX + "\" ng-click=\"primeVm.changeMenuItem(this);primeVm.collapseNavbar();\"><span class=\"glyphicon glyphicon-asterisk\"></span>&nbsp;<span>" + nm_tabela + "</span></a></li>";

                    indexItem 	= indexItem + "<script src=\"app/entities/" + nm_tabelaaUX + "/" + nm_tabelaaUX + "-dialog.controller.js\"></script>";
					indexItem 	= indexItem + "<script src=\"app/entities/" + nm_tabelaaUX + "/" + nm_tabelaaUX + "-detail.controller.js\"></script>";
					indexItem 	= indexItem + "<script src=\"app/entities/" + nm_tabelaaUX + "/" + nm_tabelaaUX + "-delete-dialog.controller.js\"></script>";
					indexItem 	= indexItem + "<script src=\"app/entities/" + nm_tabelaaUX + "/" + nm_tabelaaUX + ".state.js\"></script>";
					indexItem 	= indexItem + "<script src=\"app/entities/" + nm_tabelaaUX + "/" + nm_tabelaaUX + ".service.js\"></script>";
					indexItem 	= indexItem + "<script src=\"app/entities/" + nm_tabelaaUX + "/" + nm_tabelaaUX + ".search.service.js\"></script>";
					indexItem 	= indexItem + "<script src=\"app/entities/" + nm_tabelaaUX + "/" + nm_tabelaaUX + ".controller.js\"></script>";

					if (nm_tabela.equals("execucao_formulario")) {
						temp += ", st_sincronizado integer";
					} else if (nm_tabela.equals("campo_formulario")) {				
						temp += ", identificador integer";				
					}

					List<Object> listaRegistros = null;
					temp += ")�";



					File diretorio = new File(DIR_GRAVAR + "\\" + nm_tabelaaUX.toLowerCase());

					if (!diretorio.exists()) {
						diretorio.mkdir();
					}

				    File file = new File(DIR_GRAVAR + "\\" + nm_tabelaaUX.toLowerCase() +"\\" + nm_tabelaaUX +"s.html");
				    arquivo_gerar = arquivo_gerar + TABELA_CABECALHO;
				    byte[] b = arquivo_gerar.getBytes();

				    try {
				    	OutputStream out = new FileOutputStream(file);
						out.write(b, 0, b.length);
						out.close();
			         } catch (FileNotFoundException e) {
			                     LogPrimum.mensagem("File Not Found.");
			                     e.printStackTrace();
			         }
			         catch (IOException e1) {
			                  LogPrimum.mensagem("Error Reading The File.");
			                   e1.printStackTrace();
			         }



				    File fileState = new File(DIR_GRAVAR + "\\" + nm_tabelaaUX.toLowerCase() +"\\" + nm_tabelaaUX +".state.js");
				    byte[] bState = ARQUIVO_STATE.getBytes();

				    try {

				    	OutputStream out = new FileOutputStream(fileState);
						out.write(bState, 0, bState.length);
						out.close();
			         } catch (FileNotFoundException e) {
			                     LogPrimum.mensagem("File Not Found.");
			                     e.printStackTrace();
			         }
			         catch (IOException e1) {
			                  LogPrimum.mensagem("Error Reading The File.");
			                   e1.printStackTrace();
			         }

				    File fileController = new File(DIR_GRAVAR + "\\" + nm_tabelaaUX.toLowerCase() +"\\" + nm_tabelaaUX +".controller.js");
				    byte[] bController = ARQUIVO_CONTROLLER.getBytes();

				    try {

				    	OutputStream out = new FileOutputStream(fileController);
						out.write(bController, 0, bController.length);
						out.close();
			         } catch (FileNotFoundException e) {
			                     LogPrimum.mensagem("File Not Found.");
			                     e.printStackTrace();
			         }
			         catch (IOException e1) {
			                  LogPrimum.mensagem("Error Reading The File.");
			                   e1.printStackTrace();
			         }

				    File fileService = new File(DIR_GRAVAR + "\\" + nm_tabelaaUX.toLowerCase() +"\\" + nm_tabelaaUX +".service.js");
				    byte[] bService = ARQUIVO_SERVICE.getBytes();

				    try {

				    	OutputStream out = new FileOutputStream(fileService);
						out.write(bService, 0, bService.length);
						out.close();
			         } catch (FileNotFoundException e) {
			                     LogPrimum.mensagem("File Not Found.");
			                     e.printStackTrace();
			         }
			         catch (IOException e1) {
			                  LogPrimum.mensagem("Error Reading The File.");
			                   e1.printStackTrace();
			         }

				    File fileSearch = new File(DIR_GRAVAR + "\\" + nm_tabelaaUX.toLowerCase() +"\\" + nm_tabelaaUX +".search.service.js");
				    byte[] bSearch = ARQUIVO_SEARCH.getBytes();

				    try {

				    	OutputStream out = new FileOutputStream(fileSearch);
						out.write(bSearch, 0, bSearch.length);
						out.close();
			         } catch (FileNotFoundException e) {
			                     LogPrimum.mensagem("File Not Found.");
			                     e.printStackTrace();
			         } catch (IOException e1) {
			                  LogPrimum.mensagem("Error Reading The File.");
			                   e1.printStackTrace();
			         }

				    File fileDelCtrl = new File(DIR_GRAVAR + "\\" + nm_tabelaaUX.toLowerCase() +"\\" + nm_tabelaaUX +"-delete-dialog.controller.js");
				    byte[] bDelCtrl = ARQUIVO_DEL_CTRL.getBytes();

				    try {

				    	OutputStream out = new FileOutputStream(fileDelCtrl);
						out.write(bDelCtrl, 0, bDelCtrl.length);
						out.close();
			         } catch (FileNotFoundException e) {
			                     LogPrimum.mensagem("File Not Found.");
			                     e.printStackTrace();
			         } catch (IOException e1) {
			                  LogPrimum.mensagem("Error Reading The File.");
			                   e1.printStackTrace();
			         }

				    File fileDelHTML = new File(DIR_GRAVAR + "\\" + nm_tabelaaUX.toLowerCase() +"\\" + nm_tabelaaUX +"-delete-dialog.html");
				    byte[] bDelHTML = ARQUIVO_DEL_HTML.getBytes();

				    try {

				    	OutputStream out = new FileOutputStream(fileDelHTML);
						out.write(bDelHTML, 0, bDelHTML.length);
						out.close();
			         } catch (FileNotFoundException e) {
			                     LogPrimum.mensagem("File Not Found.");
			                     e.printStackTrace();
			         } catch (IOException e1) {
			                  LogPrimum.mensagem("Error Reading The File.");
			                   e1.printStackTrace();
			         }

				    File fileDetailCtrl = new File(DIR_GRAVAR + "\\" + nm_tabelaaUX.toLowerCase() +"\\" + nm_tabelaaUX +"-detail.controller.js");
				    byte[] bDetailCtrl = ARQUIVO_DETAIL_CTRL.getBytes();

				    try {

				    	OutputStream out = new FileOutputStream(fileDetailCtrl);
						out.write(bDetailCtrl, 0, bDetailCtrl.length);
						out.close();
			         } catch (FileNotFoundException e) {
			                     LogPrimum.mensagem("File Not Found.");
			                     e.printStackTrace();
			         } catch (IOException e1) {
			                  LogPrimum.mensagem("Error Reading The File.");
			                   e1.printStackTrace();
			         }

				    File fileDetailHTML = new File(DIR_GRAVAR + "\\" + nm_tabelaaUX.toLowerCase() +"\\" + nm_tabelaaUX +"-detail.html");
				    DETAIL_CAMPO_PK = "<div>" + DETAIL_CAMPO_PK + "<hr><jhi-alert-error></jhi-alert-error><dl class=\"dl-horizontal jh-entity-details\">" + DETAIL_CAMPO + "<button type=\"submit\"onclick=\"window.history.back()\" class=\"btn btn-info\"><span class=\"glyphicon glyphicon-arrow-left\"></span>&nbsp;<span> Back</span></button></div>";
				    byte[] bDetailHTML = DETAIL_CAMPO_PK.getBytes();

				    try {

				    	OutputStream out = new FileOutputStream(fileDetailHTML);
						out.write(bDetailHTML, 0, bDetailHTML.length);
						out.close();
			         } catch (FileNotFoundException e) {
			                     LogPrimum.mensagem("File Not Found.");
			                     e.printStackTrace();
			         } catch (IOException e1) {
			                  LogPrimum.mensagem("Error Reading The File.");
			                   e1.printStackTrace();
			         }

				    File fileDIALG_CTRL = new File(DIR_GRAVAR + "\\" + nm_tabelaaUX.toLowerCase() +"\\" + nm_tabelaaUX +"-dialog.controller.js");
				    byte[] bDIALG_CTRL = ARQUIVO_DIALG_CTRL.getBytes();

				    try {

				    	OutputStream out = new FileOutputStream(fileDIALG_CTRL);
						out.write(bDIALG_CTRL, 0, bDIALG_CTRL.length);
						out.close();
			         } catch (FileNotFoundException e) {
			                     LogPrimum.mensagem("File Not Found.");
			                     e.printStackTrace();
			         } catch (IOException e1) {
			                  LogPrimum.mensagem("Error Reading The File.");
			                   e1.printStackTrace();
			         }

				    File fileDIALG_HTML = new File(DIR_GRAVAR + "\\" + nm_tabelaaUX.toLowerCase() +"\\" + nm_tabelaaUX +"-dialog.html");
				    
				    ARQUIVO_DIALG_HTML = ARQUIVO_DIALG_HTML + DIALOG_PK + DIALOG_CAMPO + "</div><div class=\"modal-footer\"><button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\" ng-click=\"vm.clear()\"><span class=\"glyphicon glyphicon-ban-circle\"></span>&nbsp;<span>Cancel</span></button><button type=\"submit\" ng-disabled=\"editForm.$invalid || vm.isSaving\" class=\"btn btn-primary\"><span class=\"glyphicon glyphicon-save\"></span>&nbsp;<span>Save</span></button></div>";
				    byte[] bDIALG_HTML = ARQUIVO_DIALG_HTML.getBytes();

				    try {

				    	OutputStream out = new FileOutputStream(fileDIALG_HTML);
						out.write(bDIALG_HTML, 0, bDIALG_HTML.length);
						out.close();
			         } catch (FileNotFoundException e) {
			                     LogPrimum.mensagem("File Not Found.");
			                     e.printStackTrace();
			         } catch (IOException e1) {
			                  LogPrimum.mensagem("Error Reading The File.");
			                   e1.printStackTrace();
			         }

				    File fileJSON = new File(DIR_GRAVAR_JSON + "\\" + nm_tabelaObjeto +".json");
				    ARQUIVO_JSON = ARQUIVO_JSON + "],\"changelogDate\": \"20160620021421\",\"dto\": \"no\",\"service\": \"serviceImpl\",\"entityTableName\": \"" + nm_tabelaObjeto + "\",\"pagination\": \"infinite-scroll\"}";
				    byte[] bJSON = ARQUIVO_JSON.getBytes();

				    try {

				    	OutputStream out = new FileOutputStream(fileJSON);
						out.write(bJSON, 0, bJSON.length);
						out.close();
			         } catch (FileNotFoundException e) {
			                     LogPrimum.mensagem("File Not Found.");
			                     e.printStackTrace();
			         } catch (IOException e1) {
			                  LogPrimum.mensagem("Error Reading The File.");
			                   e1.printStackTrace();
			         }

				    File fileTypeScript = new File(DIR_GRAVAR_TYPESCRIPT + "\\" + nm_tabelaObjeto +".ts");
				    OBJETO_TYPESCRIPT = OBJETO_TYPESCRIPT + "}";
				    byte[] bType = OBJETO_TYPESCRIPT.getBytes();

				    try {

				    	OutputStream out = new FileOutputStream(fileTypeScript);
						out.write(bType, 0, bType.length);
						out.close();
			         } catch (FileNotFoundException e) {
			                     LogPrimum.mensagem("File Not Found.");
			                     e.printStackTrace();
			         } catch (IOException e1) {
			                  LogPrimum.mensagem("Error Reading The File.");
			                   e1.printStackTrace();
			         }

				    File fileTRANSLATE = new File(DIR_GRAVAR_TRANSLATE + "\\" + nm_tabela +".json");
				    ARQUIVO_TRANSLATE = ARQUIVO_TRANSLATE + "}}}";
				    byte[] bTRANSLATE = ARQUIVO_TRANSLATE.getBytes();

				    try {

				    	OutputStream out = new FileOutputStream(fileTRANSLATE);
						out.write(bTRANSLATE, 0, bTRANSLATE.length);
						out.close();
			         } catch (FileNotFoundException e) {
			                     LogPrimum.mensagem("File Not Found.");
			                     e.printStackTrace();
			         } catch (IOException e1) {
			                  LogPrimum.mensagem("Error Reading The File.");
			                   e1.printStackTrace();
			         }

				} catch(NoClassDefFoundError e) {
					e.printStackTrace();
					temp = "";
					dao.getSession().flush();
				} catch (Exception e) {
					e.printStackTrace();
					temp = "";
					dao.getSession().flush();
				}
				criar += temp;
			}*/
		}

	    File file = new File(DIR_GRAVAR + "\\menu.html");
	    byte[] b = menuItem.getBytes();

	    try {

	    	OutputStream out = new FileOutputStream(file);
			out.write(b, 0, b.length);
			out.close();
         } catch (FileNotFoundException e) {
                     LogPrimum.mensagem("File Not Found.");
                     e.printStackTrace();
         }
         catch (IOException e1) {
                  LogPrimum.mensagem("Error Reading The File.");
                   e1.printStackTrace();
         }
		criar += "update execucao_formulario set st_sincronizado = 1";
		//return criar;

	    File fileIdx = new File(DIR_GRAVAR + "\\index.html");
	    byte[] bIdx = indexItem.getBytes();

	    try {

	    	OutputStream out = new FileOutputStream(fileIdx);
			out.write(bIdx, 0, bIdx.length);
			out.close();
         } catch (FileNotFoundException e) {
                     LogPrimum.mensagem("File Not Found.");
                     e.printStackTrace();
         }
         catch (IOException e1) {
                  LogPrimum.mensagem("Error Reading The File.");
                   e1.printStackTrace();
         }
		criar += "update execucao_formulario set st_sincronizado = 1";
		//return criar;
	}

	private String arquivo_principal = "<div><h2>$NOME_TABELA</h2><jhi-alert></jhi-alert><div class=\"container-fluid\"><div class=\"row\"><div class=\"col-xs-4 no-padding-left\"><button class=\"btn btn-primary\" ui-sref=\"$NOME_TABELA_AQV_HTML.new\" ><span class=\"glyphicon glyphicon-plus\"></span><span class=\"hidden-xs\">Criar novo $NOME_TABELA</span></button></div><div class=\"col-xs-8 no-padding-right\"><form name=\"searchForm\" class=\"form-inline\"><div class=\"input-group pull-right\" ><input type=\"text\" class=\"form-control\" ng-model=\"vm.searchQuery\" id=\"searchQuery\" placeholder=\"{{ 'hipsterApp.$NOME_TABELA.home.search' | translate }}\"><button class=\"btn btn-info\" ng-click=\"vm.search(vm.searchQuery)\"><span class=\"glyphicon glyphicon-search\"></span></button></span><span class=\"input-group-btn width-min\" ng-if=\"vm.currentSearch\"><button class=\"btn btn-info\" ng-click=\"vm.clear()\"><span class=\"glyphicon glyphicon-trash\"></span></button></span></div></form></div></div></div><br/><div class=\"table-responsive\">";

	private String arquivo_final = "";
}