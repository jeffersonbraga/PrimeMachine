import { Router } from '@angular/router';
import { AccountService } from './../../service/account/account.service';
import { User } from './../../models/user/user';
import { HomeService } from './../../service/home/home.service';
import { GenericModalComponent } from './../generic-modal/generic-modal.component';
import { Component, OnInit, Inject, forwardRef, Injector } from '@angular/core';

@Component({
    selector: 'login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    public email: string;
    public password: string;
    public error: string;

    accountSending = false;
    accountSent = false;
    accountError = false;

    currentModal: GenericModalComponent;

    constructor(
        private homeService: HomeService,
        private injector: Injector,
        private accountService: AccountService,
        private router: Router,
        @Inject(forwardRef(() => GenericModalComponent)) private _parent: GenericModalComponent
    ) {
        this.email = '';
        this.password = null;
        this.currentModal = this.injector.get('currentModal');
    }

    ngOnInit() {
        console.log("test");
    }

    hideModal(){
        this.email = '';
        this.password = null;
        this._parent.hideModal();
    }

    createAccount() {
        if (
        window.location.pathname.split('/')[1] !== 'plans') {
            this.router.navigate(['create']);
        } else {
            if (this.accountService.planSelected === 'custom') {
                this.router.navigate(['create'], { queryParams: { plan: this.accountService.planSelected, 
                                                                  channels: this.accountService.numberOfProfiles, 
                                                                  users: this.accountService.teamMembers } });
            } else {
                this.router.navigate(['create'], { queryParams: { plan: this.accountService.planSelected } });
            }
        }

        this.hideModal();
    }

    login() {
        this.accountSending = true;
        this.accountError = false;

        const user: User = new User();
        user.email = this.email.toLowerCase().trim();
        user.password = this.password.trim();

        this.homeService.login(user)
        .subscribe (
            result => this.onLogin(result),
            error => this.onLoginError(error)
        );
    }

    onLogin(result: any) {
        this.accountSending = false;
        this.accountSent = true;

        this.homeService.getLogged()
            .subscribe(
                user => this.onGetLogged(user)
            );

        if (window.location.pathname.split('/')[1] !== 'plans') {
            window.open('https://app.woopsocial.com/home/stream', '_self');
        } else {
            this.hideModal();

            if (this.accountService.planSelected === 'custom') {
                // window.location.replace('/create&plan=' + this.accountService.planSelected + '&channels='
                //                                         + this.accountService.numberOfProfiles);

                this.router.navigate(['create'], { queryParams: { plan: this.accountService.planSelected, 
                                                                  channels: this.accountService.numberOfProfiles, 
                                                                  users: this.accountService.teamMembers } });
            } else {
                // window.location.replace('/create&plan=' + this.accountService.planSelected);

                this.router.navigate(['create'], { queryParams: { plan: this.accountService.planSelected } });
            }
        }
    }

    onLoginError(error: any) {
        this.accountError = true;
        this.accountSending = false;
        this.error = error.message;
    }

    onGetLogged(result) {
        if (result) {
            this.homeService.userSource.next(JSON.parse(result));
            this.hideModal();
        }else {
            this.homeService.userSource.next(null);
        }
    }

    clearError() {
        this.error = null;
    }

    forgotPassword() {
        this.currentModal.setDynamicModalContent('ForgotPasswordComponent');
    }
}
