import 'rxjs/add/operator/filter';

import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, PRIMARY_OUTLET, Router } from '@angular/router';
import { Subscription } from 'rxjs/subscription';

import { IBreadcrumb } from '../../../modelo/breadcrumb/Breadcrumb';
import { ControlService } from '../../services/control/control.service';

@Component({
    selector: 'breadcrumb-root',
    templateUrl: './breadcrumb.component.html'
})

export class BreadcrumbComponent implements OnInit, OnDestroy {
    subscription: Subscription;

    public breadcrumbs: IBreadcrumb[] = [];

    constructor(
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private controlService: ControlService
    ) { }

    ngOnInit() {
        const root: ActivatedRoute = this.activatedRoute.root;
        this.breadcrumbs = this.getBreadcrumbs(root);

        const ROUTE_DATA_BREADCRUMB = 'breadcrumb';
        // subscribe to the NavigationEnd event
        this.subscription = this.router.events.filter(event => event instanceof NavigationEnd).subscribe(event => {

            // set breadcrumbs
            const rootSubscribe: ActivatedRoute = this.activatedRoute.root;
            this.breadcrumbs = this.getBreadcrumbs(rootSubscribe);
        });
    }

    private getBreadcrumbs(route: ActivatedRoute, url: string = '', breadcrumbs: IBreadcrumb[] = []): IBreadcrumb[] {
        const ROUTE_DATA_BREADCRUMB = 'breadcrumb';

        // get the child routes
        const children: ActivatedRoute[] = route.children;

        // return if there are no more children
        if (children.length === 0) {
            return breadcrumbs;
        }

        // iterate over each children
        for (const child of children) {
            // verify primary route
            if (child.outlet !== PRIMARY_OUTLET) {
                continue;
            }

            // verify the custom data property "breadcrumb" is specified on the route
            if (!child.snapshot.data.hasOwnProperty(ROUTE_DATA_BREADCRUMB)) {
                return this.getBreadcrumbs(child, url, breadcrumbs);
            }

            // get the route's URL segment
            const routeURL: string = child.snapshot.url.map(segment => segment.path).join('/');

            // append route URL to URL
            url += `/${routeURL}`;

            // add breadcrumb
            const breadcrumb: IBreadcrumb = {
                label: child.snapshot.data[ROUTE_DATA_BREADCRUMB],
                params: child.snapshot.params,
                url: url
            };
            breadcrumbs.push(breadcrumb);

            // recursive
            return this.getBreadcrumbs(child, url, breadcrumbs);
        }

        // we should never get here, but just in case
        return breadcrumbs;
    }

    setStream() {
        this.controlService.urlSource.next('stream');
    }

    setCompose() {
        this.controlService.urlSource.next('compose');
    }

    ngOnDestroy(): void {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }
}
