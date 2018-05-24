import { User } from './../../models/user/user';
import { HomeService } from './../../service/home/home.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GeneralService } from 'app/service/general/general.service';

@Component({
    selector: 'header-site',
    templateUrl: './header-site.component.html',
    styleUrls: ['./header-site.component.css']
})
export class HeaderSiteComponent implements OnInit {

    public user: User;

    constructor(
        private homeService: HomeService,
        private router: Router,
        private generalService: GeneralService
    ) { }

    ngOnInit() {
        this.homeService.user$
            .subscribe(
                user => this.user = user
            );
    }

    openPlans() {
        this.router.navigate(['/plans']);
    }

    logout() {
        this.homeService.logout().subscribe(
            result => {
                this.homeService.onLogout().subscribe(
                    result => this.onLogout(result),
                    error => console.log(error)
                );
            }
        );
    }

    onLogout(result: any) {
        this.homeService.userSource.next(null);
        this.router.navigate(['']);
    }

    goToDashboard() {
        window.open('https://app.woopsocial.com/home/stream', '_self');
    }
}