import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';
import { IBreadcrumb } from 'modelo/breadcrumb/Breadcrumb';
import { Subscription } from 'rxjs/subscription';

@Component({
    selector: 'breadcrumb-modal',
    templateUrl: './breadcrumb-modal.component.html',
    styleUrls: ['./breadcrumb-modal.component.css']
})
export class BreadcrumbModalComponent implements OnInit, OnDestroy {
    subscriptionBreadcrumb: Subscription;

    public breadcrumbs: IBreadcrumb[] = [];

    constructor(private BreadcrumbsService: BreadcrumbsService, private ref: ChangeDetectorRef) {
        this.subscriptionBreadcrumb = BreadcrumbsService.breadcrumb$.subscribe(
            bread => {
                this.setBreadcrumbs(bread);
            });
    }

    ngOnInit() { }

    setBreadcrumbs(bread) {
        this.breadcrumbs = bread;
        this.ref.detectChanges();
    }

    ngOnDestroy(): void {
        if (this.subscriptionBreadcrumb) {
            this.subscriptionBreadcrumb.unsubscribe();
        }
    }
}
