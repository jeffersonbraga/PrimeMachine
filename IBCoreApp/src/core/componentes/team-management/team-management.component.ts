import { Component, OnInit } from '@angular/core';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';
import { ControlService } from 'core/services/control/control.service';
import { DialogService } from 'core/services/dialog/dialog.service';
import { UserService } from 'core/services/user/user.service';
import { User } from 'modelo/user/User';

declare const $: any;

@Component({
    selector: 'team-management',
    templateUrl: './team-management.component.html',
    styleUrls: ['./team-management.component.css']
})

export class TeamManagementComponent implements OnInit {
    admins: any;
    analysts: any;

    typeUser = 'N';
    email: string;

    emailInvalid = false;
    adding = false;

    hasError = false;
    errorMessage = '';

    action: string;
    index: number;
    actioning: boolean;

    constructor(
        private BreadcrumbsService: BreadcrumbsService,
        private ControlService: ControlService,
        private UserService: UserService,
        private DialogService: DialogService
    ) {
        this.BreadcrumbsService.initializeBreadcrumb();
    }

    ngOnInit() {
        this.BreadcrumbsService.addBreadcrumb('Equipe', '#');

        this.admins = this.ControlService.admins;
        this.analysts = this.ControlService.analysts;
    }

    cancelAction() {
        this.action = null;
        this.index = null;
        this.actioning = false;
    }

    confirmAction() {
        this.actioning = true;

        switch (this.action) {
            case 'updateToAdmin':
                this.makeAdmin();
                break;
            case 'updateToAnalyst':
                this.makeAnalyst();
                break;
            case 'deleteAdmin':
                this.deleteAdmin();
                break;
            case 'deleteAnalyst':
                this.deleteAnalyst();
                break;
        };
    }

    setAction(action: string, index: number) {
        if (!this.actioning) {
            this.action = action;
            this.index = index;
        }
    }

    makeAnalyst() {
        this.admins[this.index].type = 'N';

        this.UserService.updateTypeUser(this.admins[this.index])
            .subscribe(
                response => {
                    this.analysts.push(this.admins[this.index]);
                    this.admins.splice(this.index, 1);
                    this.cancelAction();
                }
            );

    }

    makeAdmin() {
        this.analysts[this.index].type = 'A';

        this.UserService.updateTypeUser(this.analysts[this.index])
            .subscribe(
                response => {
                    this.admins.push(this.analysts[this.index]);
                    this.analysts.splice(this.index, 1);
                    this.cancelAction();
                }
            );
    }

    deleteAdmin() {
        const user = this.admins[this.index];

        this.UserService.deleteUser(user)
            .subscribe(
                response => {
                    this.admins.splice(this.index, 1);
                    this.cancelAction();
                }
            );
    }

    deleteAnalyst() {
        const user = this.analysts[this.index];

        this.UserService.deleteUser(user)
            .subscribe(
                response => {
                    this.analysts.splice(this.index, 1);
                    this.cancelAction();
                }
            );
    }

    confirmDelete(user) {
        return this.DialogService.confirm({
            titleBar: 'Delete user',
            message: 'Do you really want to delete this user?',
            yesMessage: '<i class="fa fa-times" aria-hidden="true"></i> Delete',
            noMessage: 'Cancel'
        });
    }

    addNewUser() {
        if (this.validateEmail(this.email)) {
            this.adding = true;
            this.emailInvalid = false;

            this.UserService.insertUser(this.typeUser, this.email)
                .subscribe(
                    response => this.updateListUser(response)
                );
        } else {
            this.emailInvalid = true;
        }
    }

    validateEmail(email) {
        const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
    }

    updateListUser(response) {
        if (typeof response[0] === 'string') {
            this.showError(response[0]);
        } else {
            const user: User = response[0];

            if (user.type === 'A') {
                this.admins.push(user);
            } else {
                this.analysts.push(user);
            }
        }
        this.adding = false;
    }

    showError(msg) {
        this.hasError = true;
        this.errorMessage = msg;
    }

    hideErrorMessage() {
        this.hasError = false;
        this.errorMessage = '';
    }
}
