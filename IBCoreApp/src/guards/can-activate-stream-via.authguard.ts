import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { ControlService } from 'core/services/control/control.service';
import { LoginService } from 'core/services/login/login.service';
import { Observable } from 'rxjs/Observable';

declare const $;

@Injectable()
export class CanActivateStreamViaAuthguard implements CanActivate {

    constructor(
        private router: Router,
        private permissions: LoginService,
        private controlService: ControlService
    ) { }

    canActivate(): Observable<boolean> | Promise<boolean> | boolean {
        if (this.controlService.mobileAndTabletcheck()) {
            this.router.navigate(['/home/compose']);
            return false;
        }

        return this.permissions.isLoggedIn()
            .map(
            data => {
                setTimeout(() => {
                    $('#sidebar').sidebar();
                }, 200);

                if (data === false) {
                    this.router.navigate(['/login']);
                    return data;
                } else {
                    return data;
                }
            },
            error => {
                this.router.navigate(['/login']);
                return error;
            }
        );
    }
}
