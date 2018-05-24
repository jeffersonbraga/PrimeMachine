import { HomeService } from './service/home/home.service';
import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import 'rxjs/add/operator/distinctUntilChanged';

// This still has to be declared
declare var gtag: Function;

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

    constructor(
        private HomeService: HomeService,
        private router: Router
    ) {}

    ngOnInit(): void {

        this.router.events.distinctUntilChanged((previous: any, current: any) => {
            // Subscribe to any `NavigationEnd` events where the url has changed
            if(current instanceof NavigationEnd) {
                return previous.url === current.url;
            }
            return true;
        }).subscribe((x: any) => {
            gtag('config', 'UA-112964711-1', {'page_path': x.url});
        });

        this.HomeService.getLogged()
            .subscribe(
                result => this.onGetLogged(result)
            );
    }

    onGetLogged(result) {
        if (result) {
            this.HomeService.setUser(JSON.parse(result));
            // this.HomeService.userSource.next(JSON.parse(result));
        }else {
            this.HomeService.userSource.next(null);
        }
    }
}
