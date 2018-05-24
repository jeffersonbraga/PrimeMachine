import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'core/services/user/user.service';
import { User } from 'modelo/user/User';
import { Subscription } from 'rxjs/subscription';

declare const $;

@Component({
    moduleId: module.id,
    selector: 'new-user',
    templateUrl: './new-user.component.html',
    styleUrls: ['./new-user.component.css']
})
export class NewUserComponent implements  OnInit, OnDestroy {

    sub: Subscription;
    id: string;
    found: boolean;
    user: User;
    loading = true;

    password: string;
    passwordRepeat: string;

    emailValid = true;
    formValid = false;

    constructor(
        private route: ActivatedRoute,
        private UserService: UserService
    ) { }

    ngOnInit() {
        this.sub = this.route.params.subscribe(
            params => this.getByUrlActivation(params['url'])
        );
    }

    getByUrlActivation(url: string) {
        this.id = url;

        this.UserService.getByUrlActivation(url).
        subscribe(
            response => this.mapResponse(response)
        );
    }

    mapResponse(response) {
        this.loading = false;

        if (typeof response[0] === 'string') {
            this.found = false;
        }else {
            this.found = true;
            this.user = response[0];
            this.user.name = '';
            this.user.password = '';
        }
    }

    validateEmail() {
        const email = this.user.email;
        // tslint:disable-next-line:max-line-length
        const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        this.emailValid = re.test(email);
        return this.emailValid;
    }

    saveUser() {
        if (this.validateForm()) {
            this.UserService.activateUser(this.user)
            .subscribe(
                response => {
                    if (response['_body'] === 'OK') {
                        window.location.replace('https://woopsocial.com');
                    }else {
                        const options: Object = {
                            message: response['_body'],
                            type: 'danger'
                        };

                        $('body').pgNotification(options).show();
                    }
                }
            );
        }
    }

    validateForm() {
        if (!this.user.name) {
            this.formValid = false;
        }else if (!this.user.email) {
            this.formValid = false;
        }else if (!this.user.password) {
            this.formValid = false;
        }else if (this.user.password !== this.passwordRepeat) {
            this.formValid = false;
        } else if (!this.validateEmail()) {
            this.formValid = false;
        }else {
            this.formValid = true;
        }

        return this.formValid;
    }

    ngOnDestroy() {
        if (this.sub) {
            this.sub.unsubscribe();
        }
    }
}
