import { HomeService } from './../../service/home/home.service';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { GenericModalComponent } from './../generic-modal/generic-modal.component';
import { Component, Inject, forwardRef, Injector } from '@angular/core';

@Component({
    selector: 'forgot-password',
    templateUrl: './forgot-password.component.html',
    styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
    
    currentModal : GenericModalComponent;
    form: FormGroup;
    email: string;
    emailValid = false;
    emailError = false;
    emailSent = false;
    emailSending = false;
    emailNotFound = false;
    passwordChanged = false;

    constructor(
        private homeService: HomeService,
		private injector: Injector,
        private formBuilder: FormBuilder,
        @Inject(forwardRef(() => GenericModalComponent)) private _parent: GenericModalComponent
    ) {
        this.email = "";
		this.currentModal = this.injector.get("currentModal");
    }

    ngOnInit() {
        this.form = this.formBuilder.group({
            email: [null, [Validators.required]]
        });
    }

    get emailInput() { return this.form.get('email'); }

    validateEmail() {
        const email = this.email;
        // tslint:disable-next-line:max-line-length
        const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        this.emailValid = re.test(email);
        return this.emailValid;
    }

    validateForm() {
        if (this.email && this.emailValid) {
            return true;
        }else {
            return false;
        }
    }

    sendEmail() {
        if (this.validateForm()) {
            this.emailSending = true;
            
            this.homeService.sendMessage(this.email)
            .subscribe(
                result => this.onSendMessage(result),
                error => this.onSendEmailError(error)
            );
        }
    }

    onSendMessage(result: any) {
        if(result.status === 200) {
            this.emailSending = false;
            this.emailSent = true;
            this.emailError = false;
            this.emailValid = true;
            this.passwordChanged = true;
            setTimeout(() => this.currentModal.setDynamicModalContent("LoginComponent"), 10000);
        } else {
            console.log(result);
            this.emailSending = false;
            this.emailSent = false;
            this.emailError = true;
        }

        this.email = "";
    }

    onSendEmailError(error: any) {
        // if (error.status === 500) {
            this.emailNotFound = true;
            this.emailSending = false;
            this.emailSent = false;
            this.emailError = true;
        // } else {
        //     this.emailSending = false;
        //     this.emailSent = false;
        //     this.emailError = true;
        // }
    }

    goBack() {
        this.currentModal.setDynamicModalContent("LoginComponent");
    }

    hideModal() {
        this._parent.hideModal();
        setTimeout(
            () => this.emailSent = false,
            100
        );
    }
}