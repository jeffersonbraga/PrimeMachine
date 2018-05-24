import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ControlService } from 'core/services/control/control.service';
import { Account } from 'modelo/account/Account';

import { ModalService } from './../../services/modal/modal.service';

declare const $;

@Component({
    selector: 'sidebar-root',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.css']
})

export class SidebarComponent implements OnInit {
    account: Account;

    headerTitleChannels = 'Gerenciar Canais';
    manageChannelsComponentClassName = 'ManageChannelsComponent';
    menuPin = false;

    route_selected = '';

    constructor(
        private route: ActivatedRoute,
        private ControlService: ControlService,
        private modalService: ModalService
    ) {
        this.route_selected = window.location.pathname.split('/')[2];
    }

    ngOnInit() {

        this.ControlService.user$.subscribe(
            user => {
                if (user) {
                    this.account = user.account;
                }
            }
        );

        this.ControlService.url$.subscribe(
            url => this.route_selected = url
        );
    }

    toggleSidebar() {
        if (!this.menuPin) {
            $('body').addClass('sidebar-visible').addClass('menu-pin');
        } else {
            $('body').removeClass('menu-pin');
        }

        this.menuPin = !this.menuPin;
    }

    changeRouteSelected(item): void {
        this.route_selected = item.currentTarget.pathname.split('/')[2];
        this.closeSidebar();
    }

    openModal() {
        this.modalService.showChannelsModal();
        this.closeSidebar();
    }

    closeSidebar() {
        if (this.ControlService.mobileAndTabletcheck()) {
            $('[data-pages="sidebar"]').data('pg.sidebar').toggleSidebar();
        }
    }
}
