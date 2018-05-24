import { AccountService } from './../../service/account/account.service';
import { Router, ActivatedRoute } from '@angular/router';
import { GenericModalComponent } from './../generic-modal/generic-modal.component';
import { User } from './../../models/user/user';
import { HomeService } from './../../service/home/home.service';
import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

@Component({
    selector: 'plans-pricing',
    templateUrl: './plans-pricing.component.html',
    styleUrls: ['./plans-pricing.component.css']
})
export class PlansPricingComponent implements OnInit, OnDestroy {
    subscription: Subscription;

    teamMembers = 3;
    numberOfProfiles = 35;
    planPrice: number;
    planPriceYearly: number;
    save: number;
    loading: boolean;

    public user: User;

    @ViewChild('loginModal')
    loginModal: GenericModalComponent;

    constructor(
        private HomeService: HomeService,
        private router: Router,
        private ActivatedRoute: ActivatedRoute,
        private accountService: AccountService
    ) {
        ActivatedRoute.params.subscribe(
            params => this.ngOnInit()
        );
     }

    ngOnInit() {
        this.calculatePrice();

        this.subscription = this.HomeService.user$
            .subscribe(
                user => this.user = user
            );

        this.loading = false;
    }

    plus() {
        if (!this.loading) {
            this.loading = true;
            if (this.numberOfProfiles < 300) {
                this.numberOfProfiles += 5;
                this.calculatePrice();

                if (this.numberOfProfiles % 10 === 0) {
                    this.teamMembers += 1;
                }
            }
            this.loading = false;
        }
    }

    minus() {
        if (!this.loading) {
            this.loading = true;
            if (this.numberOfProfiles > 35) {

                if (this.teamMembers > 3 && this.numberOfProfiles % 10 === 0) {
                    this.teamMembers -= 1;
                }

                this.numberOfProfiles -= 5;
                this.calculatePrice();
            }
            this.loading = false;
        }
    }

    calculatePrice() {
        this.planPrice = this.numberOfProfiles;
        this.planPriceYearly = this.planPrice * 12 - (this.planPrice * 12 * 20 / 100);
        this.save = this.planPrice * 12 - this.planPriceYearly;
    }

    subscribe(plan) {
        this.accountService.planSelected = plan;
        this.accountService.numberOfProfiles = this.numberOfProfiles;
        this.accountService.teamMembers = this.teamMembers;
        if (this.user) {
            if (plan === 'custom') {
                // window.location.replace('/create&plan=' + plan + '&channels=' + this.numberOfProfiles);
                this.router.navigate(['create'],
                            { queryParams: { plan: plan, channels: this.numberOfProfiles, users: this.teamMembers } });
            } else {
                // window.location.replace('/create&plan=' + plan);
                this.router.navigate(['create'], { queryParams: { plan: plan } });
            }
        } else {
            this.loginModal.showModal();
        }
    }

    goToDashboard() {
        window.open('https://app.woopsocial.com/home/stream', '_self');
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}
