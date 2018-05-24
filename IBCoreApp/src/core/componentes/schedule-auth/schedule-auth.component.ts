import { Component, forwardRef, Inject, Injector, OnInit } from '@angular/core';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';
import { ControlService } from 'core/services/control/control.service';
import { Profile } from 'modelo/profile/Profile';

import { GenericModalComponent } from './../generic-modal/generic-modal.component';

@Component({
    selector: 'schedule-auth',
    templateUrl: './schedule-auth.component.html',
    styleUrls: ['./schedule-auth.component.css']
})

export class ScheduleAuthComponent implements OnInit {

    currentModal: GenericModalComponent;

    profile: Profile;

    constructor(
        private breadcrumbsService: BreadcrumbsService,
        private controlService: ControlService,
        private injector: Injector,
        @Inject(forwardRef(() => GenericModalComponent)) private _parent: GenericModalComponent
    ) {
        this.breadcrumbsService.initializeBreadcrumb();
    }

    ngOnInit() {
        this.currentModal = this.injector.get('currentModal');

        this.breadcrumbsService.addBreadcrumb('Instagram Content Authorization', '#');

        this.profile = this.controlService.profileAuth;
    }

    closeModal() {
        this._parent.hideModal();
    }
}
