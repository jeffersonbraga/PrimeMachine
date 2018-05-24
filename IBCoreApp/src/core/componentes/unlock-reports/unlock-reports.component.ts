import { Component, forwardRef, Inject, OnInit } from '@angular/core';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';

import { GenericModalComponent } from './../generic-modal/generic-modal.component';

declare const $: any;

@Component({
    selector: 'unlock-reports',
    templateUrl: './unlock-reports.component.html',
    styleUrls: ['./unlock-reports.component.css']
})

export class UnlockReportsComponent implements OnInit {

    constructor(
        private BreadcrumbsService: BreadcrumbsService,
        @Inject(forwardRef(() => GenericModalComponent)) private _parent: GenericModalComponent
    ) {
        this.BreadcrumbsService.initializeBreadcrumb();
    }

    ngOnInit() {
        this.BreadcrumbsService.addBreadcrumb('Unlock Analytics', '#');
    }

    close() {
        this._parent.hideModal();
    }
}
