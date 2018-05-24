import { Injectable } from '@angular/core';
import { IBreadcrumb } from 'modelo/breadcrumb/Breadcrumb';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class BreadcrumbsService {

    breadcrumbSource = new Subject<IBreadcrumb[]>();

    breadcrumbs: IBreadcrumb[] = [];

    breadcrumb$ = this.breadcrumbSource.asObservable();

    constructor() {
    }

    addBreadcrumb(label, url) {

        const b = {
            url: url,
            label: label
        };

        this.breadcrumbs.push(b);

        this.breadcrumbSource.next(this.breadcrumbs);
    }

    removeBreadcrumb() {
        this.breadcrumbs.pop();

        this.breadcrumbSource.next(this.breadcrumbs);
    }

    initializeBreadcrumb() {
        this.breadcrumbs = [];
    }
}
