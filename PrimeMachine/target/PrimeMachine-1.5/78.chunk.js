webpackJsonp([78,165],{

/***/ 1627:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__helpdesk_ocorrencia_dados_component__ = __webpack_require__(1958);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__helpdesk_ocorrencia_dadosDetails_component__ = __webpack_require__(1959);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__helpdesk_ocorrencia_dadosEdit_component__ = __webpack_require__(1960);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__shared_shared_module__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__helpdesk_ocorrencia_dados_routing__ = __webpack_require__(2276);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var AppModule = (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["b" /* NgModule */])({ imports: [__WEBPACK_IMPORTED_MODULE_1__angular_common__["a" /* CommonModule */], __WEBPACK_IMPORTED_MODULE_6__helpdesk_ocorrencia_dados_routing__["a" /* helpdesk_ocorrencia_dados_routing */], __WEBPACK_IMPORTED_MODULE_5__shared_shared_module__["a" /* SharedModule */]], declarations: [__WEBPACK_IMPORTED_MODULE_2__helpdesk_ocorrencia_dados_component__["a" /* Helpdesk_ocorrenciaDadosComponent */], __WEBPACK_IMPORTED_MODULE_3__helpdesk_ocorrencia_dadosDetails_component__["a" /* Helpdesk_ocorrenciaDadosDetailsComponent */], __WEBPACK_IMPORTED_MODULE_4__helpdesk_ocorrencia_dadosEdit_component__["a" /* Helpdesk_ocorrenciaDadosEditComponent */]] }), 
        __metadata('design:paramtypes', [])
    ], AppModule);
    return AppModule;
}());
/* harmony default export */ __webpack_exports__["default"] = AppModule;
//# sourceMappingURL=D:/Projetos/my-maps-project/src/helpdesk_ocorrencia_dados.module.js.map

/***/ }),

/***/ 1958:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__(1);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Helpdesk_ocorrenciaDadosComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var Helpdesk_ocorrenciaDadosComponent = (function () {
    function Helpdesk_ocorrenciaDadosComponent(router) {
        this.router = router;
        this.displayModeEnum = Helpdesk_ocorrenciaDadosDisplayModeEnum;
    }
    Helpdesk_ocorrenciaDadosComponent.prototype.ngOnInit = function () { var path = this.router.url.split('/')[3]; switch (path) {
        case 'details':
            this.displayMode = Helpdesk_ocorrenciaDadosDisplayModeEnum.Details;
            break;
        case 'info':
            this.displayMode = Helpdesk_ocorrenciaDadosDisplayModeEnum.Orders;
            break;
        case 'edit':
            this.displayMode = Helpdesk_ocorrenciaDadosDisplayModeEnum.Edit;
            break;
    } };
    Helpdesk_ocorrenciaDadosComponent = __decorate([
        __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["e" /* Component */])({ selector: 'helpdesk_ocorrencia-dados', template: __webpack_require__(2609) }), 
        __metadata('design:paramtypes', [(typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */] !== 'undefined' && __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */]) === 'function' && _a) || Object])
    ], Helpdesk_ocorrenciaDadosComponent);
    return Helpdesk_ocorrenciaDadosComponent;
    var _a;
}());
var Helpdesk_ocorrenciaDadosDisplayModeEnum;
(function (Helpdesk_ocorrenciaDadosDisplayModeEnum) {
    Helpdesk_ocorrenciaDadosDisplayModeEnum[Helpdesk_ocorrenciaDadosDisplayModeEnum["Details"] = 0] = "Details";
    Helpdesk_ocorrenciaDadosDisplayModeEnum[Helpdesk_ocorrenciaDadosDisplayModeEnum["Orders"] = 1] = "Orders";
    Helpdesk_ocorrenciaDadosDisplayModeEnum[Helpdesk_ocorrenciaDadosDisplayModeEnum["Edit"] = 2] = "Edit";
})(Helpdesk_ocorrenciaDadosDisplayModeEnum || (Helpdesk_ocorrenciaDadosDisplayModeEnum = {}));
//# sourceMappingURL=D:/Projetos/my-maps-project/src/helpdesk_ocorrencia_dados.component.js.map

/***/ }),

/***/ 1959:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__modelo_Helpdesk_ocorrencia__ = __webpack_require__(666);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__modelo_ObjetoVirtual__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__core_services_pesquisa_pesquisa_service__ = __webpack_require__(4);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Helpdesk_ocorrenciaDadosDetailsComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var Helpdesk_ocorrenciaDadosDetailsComponent = (function () {
    function Helpdesk_ocorrenciaDadosDetailsComponent(route, pesquisaService) {
        this.route = route;
        this.pesquisaService = pesquisaService;
    }
    Helpdesk_ocorrenciaDadosDetailsComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.sub = this.route.parent.params.subscribe(function (params) { var o = new __WEBPACK_IMPORTED_MODULE_3__modelo_ObjetoVirtual__["a" /* ObjetoVirtual */](); o.classeJava = "br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia"; var id = +params['id']; _this.helpdesk_ocorrencia = new __WEBPACK_IMPORTED_MODULE_2__modelo_Helpdesk_ocorrencia__["a" /* Helpdesk_ocorrencia */](); _this.helpdesk_ocorrencia.cd_helpdesk_ocorrencia = id; o.objetoJson = _this.helpdesk_ocorrencia; _this.pesquisaService.efetuarPesquisa(o).subscribe(function (result) { return _this.onEfetuarPesquisa(result); }, function (error) { return _this.errorMessage = error; }); });
    };
    Helpdesk_ocorrenciaDadosDetailsComponent.prototype.onEfetuarPesquisa = function (result) { if (!result) {
        this.errorMessage = 'Houve um erro ao pesquisa a helpdesk_ocorrencia. ';
    } this.helpdesk_ocorrencia = result; };
    Helpdesk_ocorrenciaDadosDetailsComponent.prototype.ngOnDestroy = function () { this.sub.unsubscribe(); };
    Helpdesk_ocorrenciaDadosDetailsComponent = __decorate([
        __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["e" /* Component */])({ selector: 'helpdesk_ocorrencia-dados-details', template: __webpack_require__(2610) }), 
        __metadata('design:paramtypes', [(typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* ActivatedRoute */] !== 'undefined' && __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* ActivatedRoute */]) === 'function' && _a) || Object, (typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_4__core_services_pesquisa_pesquisa_service__["a" /* PesquisaService */] !== 'undefined' && __WEBPACK_IMPORTED_MODULE_4__core_services_pesquisa_pesquisa_service__["a" /* PesquisaService */]) === 'function' && _b) || Object])
    ], Helpdesk_ocorrenciaDadosDetailsComponent);
    return Helpdesk_ocorrenciaDadosDetailsComponent;
    var _a, _b;
}());
//# sourceMappingURL=D:/Projetos/my-maps-project/src/helpdesk_ocorrencia_dadosDetails.component.js.map

/***/ }),

/***/ 1960:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__modelo_Helpdesk_ocorrencia__ = __webpack_require__(666);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__modelo_ObjetoVirtual__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__core_services_pesquisa_pesquisa_service__ = __webpack_require__(4);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Helpdesk_ocorrenciaDadosEditComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var Helpdesk_ocorrenciaDadosEditComponent = (function () {
    function Helpdesk_ocorrenciaDadosEditComponent(router, route, pesquisaService) {
        this.router = router;
        this.route = route;
        this.pesquisaService = pesquisaService;
    }
    Helpdesk_ocorrenciaDadosEditComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.sub = this.route.parent.params.subscribe(function (params) { var o = new __WEBPACK_IMPORTED_MODULE_3__modelo_ObjetoVirtual__["a" /* ObjetoVirtual */](); o.classeJava = "br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia"; var id = +params['id']; _this.helpdesk_ocorrencia = new __WEBPACK_IMPORTED_MODULE_2__modelo_Helpdesk_ocorrencia__["a" /* Helpdesk_ocorrencia */](); _this.helpdesk_ocorrencia.cd_helpdesk_ocorrencia = id; o.objetoJson = _this.helpdesk_ocorrencia; _this.pesquisaService.efetuarPesquisa(o).subscribe(function (result) { return _this.onEfetuarPesquisa(result); }, function (error) { return _this.errorMessage = error; }); });
    };
    Helpdesk_ocorrenciaDadosEditComponent.prototype.onEfetuarPesquisa = function (result) { if (!result) {
        this.errorMessage = 'Houve um erro ao pesquisa a helpdesk_ocorrencia. ';
    } this.helpdesk_ocorrencia = result; };
    Helpdesk_ocorrenciaDadosEditComponent.prototype.ngOnDestroy = function () { this.sub.unsubscribe(); };
    Helpdesk_ocorrenciaDadosEditComponent.prototype.onSubmit = function () { };
    Helpdesk_ocorrenciaDadosEditComponent.prototype.onCancel = function (event) { event.preventDefault(); this.router.navigate(['/']); };
    Helpdesk_ocorrenciaDadosEditComponent = __decorate([
        __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["e" /* Component */])({ selector: 'helpdesk_ocorrencia-dados-edit', template: __webpack_require__(2611) }), 
        __metadata('design:paramtypes', [(typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */] !== 'undefined' && __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */]) === 'function' && _a) || Object, (typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* ActivatedRoute */] !== 'undefined' && __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* ActivatedRoute */]) === 'function' && _b) || Object, (typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_4__core_services_pesquisa_pesquisa_service__["a" /* PesquisaService */] !== 'undefined' && __WEBPACK_IMPORTED_MODULE_4__core_services_pesquisa_pesquisa_service__["a" /* PesquisaService */]) === 'function' && _c) || Object])
    ], Helpdesk_ocorrenciaDadosEditComponent);
    return Helpdesk_ocorrenciaDadosEditComponent;
    var _a, _b, _c;
}());
//# sourceMappingURL=D:/Projetos/my-maps-project/src/helpdesk_ocorrencia_dadosEdit.component.js.map

/***/ }),

/***/ 2276:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_router__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__helpdesk_ocorrencia_dados_component__ = __webpack_require__(1958);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__helpdesk_ocorrencia_dadosDetails_component__ = __webpack_require__(1959);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__helpdesk_ocorrencia_dadosEdit_component__ = __webpack_require__(1960);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return helpdesk_ocorrencia_dados_routing; });




var helpdesk_ocorrencia_dados_routes = [{ path: '', component: __WEBPACK_IMPORTED_MODULE_1__helpdesk_ocorrencia_dados_component__["a" /* Helpdesk_ocorrenciaDadosComponent */], children: [{ path: 'details', component: __WEBPACK_IMPORTED_MODULE_2__helpdesk_ocorrencia_dadosDetails_component__["a" /* Helpdesk_ocorrenciaDadosDetailsComponent */] }, { path: 'edit', component: __WEBPACK_IMPORTED_MODULE_3__helpdesk_ocorrencia_dadosEdit_component__["a" /* Helpdesk_ocorrenciaDadosEditComponent */] }] }];
var helpdesk_ocorrencia_dados_routing = __WEBPACK_IMPORTED_MODULE_0__angular_router__["c" /* RouterModule */].forChild(helpdesk_ocorrencia_dados_routes);
//# sourceMappingURL=D:/Projetos/my-maps-project/src/helpdesk_ocorrencia_dados.routing.js.map

/***/ }),

/***/ 2609:
/***/ (function(module, exports) {

module.exports = "<div class=\"orders view\">     <div class=\"container\">         <header>             <h3><span class=\"glyphicon glyphicon-user\"></span>&nbsp;&nbsp;Informações da Helpdesk_ocorrencia</h3>         </header>         <br />         <div class=\"navbar\">             <ul class=\"nav navbar-nav\">                 <li class=\"toolbar-item\">                     <a routerLink=\"details\"                        (click)=\"displayMode = displayModeEnum.Details\"                        [class.active]=\"displayMode === displayModeEnum.Details\">                        <span class=\"glyphicon glyphicon-list\"></span>&nbsp;&nbsp;Detalhes                     </a>                 </li>                 <li class=\"toolbar-item\">                     <a routerLink=\"edit\"                        (click)=\"displayMode = displayModeEnum.Edit\"                        [class.active]=\"displayMode === displayModeEnum.Edit\">                         <span class=\"glyphicon glyphicon-edit edit-icon\"></span>&nbsp;&nbsp;Editar                     </a>                 </li>             </ul>         </div>         <div class=\"container\">             <router-outlet></router-outlet>             <br />             <br />             <a routerLink=\"/helpdesk_ocorrencia\">View all Helpdesk_ocorrencia</a>         </div>     </div> </div>      "

/***/ }),

/***/ 2610:
/***/ (function(module, exports) {

module.exports = "<div *ngIf=\"helpdesk_ocorrencia\">   <div class=\"row\">     <div class=\"col-md-1\">    &nbsp;   </div>     <div class=\"col-md-11\"><h4>{{ helpdesk_ocorrencia.cd_helpdesk_ocorrencia }}</h4><br />{{ helpdesk_ocorrencia.dt_criacao }}<br />{{ helpdesk_ocorrencia.st_helpdesk_ocorrencia }}<br />{{ helpdesk_ocorrencia.ds_ocorrencia }}<br />{{ helpdesk_ocorrencia.cd_responsavel_ocorrencia }}<br />{{ helpdesk_ocorrencia.cd_unidade_registro }}<br />{{ helpdesk_ocorrencia.cd_responsavel_registro }}<br />{{ helpdesk_ocorrencia.cd_classificacao_helpdesk }}<br />{{ helpdesk_ocorrencia.cd_unidade }}<br />{{ helpdesk_ocorrencia.ds_resp_tecnica }}<br />{{ helpdesk_ocorrencia.ds_resp_usuario }}<br />{{ helpdesk_ocorrencia.tp_parecer_reclamante }}<br />{{ helpdesk_ocorrencia.ds_observacoes }}<br />{{ helpdesk_ocorrencia.cd_resp_resolucao }}<br />{{ helpdesk_ocorrencia.cd_resp_salvar }}<br />{{ helpdesk_ocorrencia.cd_helpdesk_origem }}<br />{{ helpdesk_ocorrencia.cd_natureza_ocorrencia }}<br />{{ helpdesk_ocorrencia.tp_notificados }}<br />{{ helpdesk_ocorrencia.ds_historico }}<br />{{ helpdesk_ocorrencia.cd_resp_analise }}<br />{{ helpdesk_ocorrencia.cd_setor }}<br />{{ helpdesk_ocorrencia.ds_titulo }}</div></div></div><div *ngIf=\"!helpdesk_ocorrencia\" class=\"row\">No helpdesk_ocorrencia found</div>"

/***/ }),

/***/ 2611:
/***/ (function(module, exports) {

module.exports = "<div>  <form (ngSubmit)=\"onSubmit()\" #helpdesk_ocorrenciaForm=\"ngForm\" class=\"editForm\"><div class=\"form-group\"><label>CD_HELPDESK_OCORRENCIA</label><input type=\"number\" class=\"form-control\" name=\"input_cd_helpdesk_ocorrencia\" [(ngModel)]=\"helpdesk_ocorrencia.cd_helpdesk_ocorrencia\"></div><div class=\"form-group\"><label>DT_CRIACAO</label><input type=\"text\" class=\"form-control\" name=\"input_dt_criacao\" [(ngModel)]=\"helpdesk_ocorrencia.dt_criacao\"></div><div class=\"form-group\"><label>ST_HELPDESK_OCORRENCIA</label><input type=\"number\" class=\"form-control\" name=\"input_st_helpdesk_ocorrencia\" [(ngModel)]=\"helpdesk_ocorrencia.st_helpdesk_ocorrencia\"></div><div class=\"form-group\"><label>DS_OCORRENCIA</label><input type=\"number\" class=\"form-control\" name=\"input_ds_ocorrencia\" [(ngModel)]=\"helpdesk_ocorrencia.ds_ocorrencia\"></div><div class=\"form-group\"><label>CD_RESPONSAVEL_OCORRENCIA</label><input type=\"number\" class=\"form-control\" name=\"input_cd_responsavel_ocorrencia\" [(ngModel)]=\"helpdesk_ocorrencia.cd_responsavel_ocorrencia\"></div><div class=\"form-group\"><label>CD_UNIDADE_REGISTRO</label><input type=\"number\" class=\"form-control\" name=\"input_cd_unidade_registro\" [(ngModel)]=\"helpdesk_ocorrencia.cd_unidade_registro\"></div><div class=\"form-group\"><label>CD_RESPONSAVEL_REGISTRO</label><input type=\"number\" class=\"form-control\" name=\"input_cd_responsavel_registro\" [(ngModel)]=\"helpdesk_ocorrencia.cd_responsavel_registro\"></div><div class=\"form-group\"><label>CD_CLASSIFICACAO_HELPDESK</label><input type=\"number\" class=\"form-control\" name=\"input_cd_classificacao_helpdesk\" [(ngModel)]=\"helpdesk_ocorrencia.cd_classificacao_helpdesk\"></div><div class=\"form-group\"><label>CD_UNIDADE</label><input type=\"number\" class=\"form-control\" name=\"input_cd_unidade\" [(ngModel)]=\"helpdesk_ocorrencia.cd_unidade\"></div><div class=\"form-group\"><label>DS_RESP_TECNICA</label><input type=\"number\" class=\"form-control\" name=\"input_ds_resp_tecnica\" [(ngModel)]=\"helpdesk_ocorrencia.ds_resp_tecnica\"></div><div class=\"form-group\"><label>DS_RESP_USUARIO</label><input type=\"number\" class=\"form-control\" name=\"input_ds_resp_usuario\" [(ngModel)]=\"helpdesk_ocorrencia.ds_resp_usuario\"></div><div class=\"form-group\"><label>TP_PARECER_RECLAMANTE</label><input type=\"number\" class=\"form-control\" name=\"input_tp_parecer_reclamante\" [(ngModel)]=\"helpdesk_ocorrencia.tp_parecer_reclamante\"></div><div class=\"form-group\"><label>DS_OBSERVACOES</label><input type=\"number\" class=\"form-control\" name=\"input_ds_observacoes\" [(ngModel)]=\"helpdesk_ocorrencia.ds_observacoes\"></div><div class=\"form-group\"><label>CD_RESP_RESOLUCAO</label><input type=\"number\" class=\"form-control\" name=\"input_cd_resp_resolucao\" [(ngModel)]=\"helpdesk_ocorrencia.cd_resp_resolucao\"></div><div class=\"form-group\"><label>CD_RESP_SALVAR</label><input type=\"number\" class=\"form-control\" name=\"input_cd_resp_salvar\" [(ngModel)]=\"helpdesk_ocorrencia.cd_resp_salvar\"></div><div class=\"form-group\"><label>CD_HELPDESK_ORIGEM</label><input type=\"number\" class=\"form-control\" name=\"input_cd_helpdesk_origem\" [(ngModel)]=\"helpdesk_ocorrencia.cd_helpdesk_origem\"></div><div class=\"form-group\"><label>CD_NATUREZA_OCORRENCIA</label><input type=\"number\" class=\"form-control\" name=\"input_cd_natureza_ocorrencia\" [(ngModel)]=\"helpdesk_ocorrencia.cd_natureza_ocorrencia\"></div><div class=\"form-group\"><label>TP_NOTIFICADOS</label><input type=\"number\" class=\"form-control\" name=\"input_tp_notificados\" [(ngModel)]=\"helpdesk_ocorrencia.tp_notificados\"></div><div class=\"form-group\"><label>DS_HISTORICO</label><input type=\"number\" class=\"form-control\" name=\"input_ds_historico\" [(ngModel)]=\"helpdesk_ocorrencia.ds_historico\"></div><div class=\"form-group\"><label>CD_RESP_ANALISE</label><input type=\"number\" class=\"form-control\" name=\"input_cd_resp_analise\" [(ngModel)]=\"helpdesk_ocorrencia.cd_resp_analise\"></div><div class=\"form-group\"><label>CD_SETOR</label><input type=\"number\" class=\"form-control\" name=\"input_cd_setor\" [(ngModel)]=\"helpdesk_ocorrencia.cd_setor\"></div><div class=\"form-group\"><label>DS_TITULO</label><input type=\"text\" class=\"form-control\" name=\"input_ds_titulo\" [(ngModel)]=\"helpdesk_ocorrencia.ds_titulo\"></div></form></div>"

/***/ })

});
//# sourceMappingURL=78.chunk.js.map