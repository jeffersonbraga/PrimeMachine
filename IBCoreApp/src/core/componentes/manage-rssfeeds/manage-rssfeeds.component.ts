import { Component, Injector } from '@angular/core';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';
import { ControlService } from 'core/services/control/control.service';
import { GroupsService } from 'core/services/groups/groups.service';
import { Account } from 'modelo/account/Account';
import { Group } from 'modelo/group/Group';
import { User } from 'modelo/user/User';

@Component({ 
	moduleId: module.id,
	selector: 'manage-rssfeeds', 
	templateUrl: './manage-rssfeeds.component.html', 
	styleUrls: ['./manage-rssfeeds.component.css']
}) 

export class ManageRssfeedsComponent {

  	groups : any = [];
	group : Group = new Group();
	currentModal : GenericModalComponent;
	errorMessage: string = '';
	result: any;
	P: any;

	constructor(
		private groupService: GroupsService,
		private controlApp: ControlService,
		private injector: Injector,
		private breadcrumbsService: BreadcrumbsService
	) {
		this.groups = [];
		this.currentModal = this.injector.get("currentModal");

		this.breadcrumbsService.initializeBreadcrumb();
	}

	ngOnInit() {

		let user : User = new User();

		user.idUser = 1;
		user.account = new Account();
		user.account.idAccount = 1;

		this.controlApp.setUser(user);

		this.groups = this.groupService.currentGroups;
		this.breadcrumbsService.addBreadcrumb("Gerenciar Feeds", "");
	}

	createGroup(p: any) {

	}

}