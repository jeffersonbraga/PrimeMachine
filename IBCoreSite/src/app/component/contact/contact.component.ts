import { ContactService } from './../../service/contact/contact.service';
import { GenericModalComponent } from './../generic-modal/generic-modal.component';
import { Component, OnInit, Inject, forwardRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
    selector: 'contact',
    templateUrl: './contact.component.html',
    styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

    form: FormGroup;

    name: string;
    email: string;
    message: string;
    emailValid = false;
    emailSending = false;
    emailSent = false;

    constructor(
        private ContactService: ContactService,
        private formBuilder: FormBuilder,
        @Inject(forwardRef(() => GenericModalComponent)) private _parent: GenericModalComponent
    ) { }

    ngOnInit() {
        this.form = this.formBuilder.group({
            name: [null, Validators.required],
            email: [null, Validators.required],
            message: [null, Validators.required],
        });
    }

    get nameInput() {
        return this.form.get('name');
    }
    get emailInput() {
        return this.form.get('email');
    }
    get messageInput() {
        return this.form.get('message');
    }

    hideModal() {
        this._parent.hideModal();
        setTimeout(
            () => this.emailSent = false,
            100
        );
    }

    validateForm() {
        if (this.email && this.name && this.message && this.emailValid) {
            return true;
        }else {
            return false;
        }
    }

    sendMessage() {
        if (this.validateForm()) {
            this.emailSending = true;
            this.ContactService.sendMessage(this.name, this.email, this.message)
                .subscribe(
                    result => {
                        this.emailSending = false;
                        this.emailSent = true;
                        setTimeout(
                            () => this.hideModal(),
                            200
                        );
                    }
                );
        }
    }

    validateEmail() {
        this.email = this.email.trim();
        const email = this.email;
        // tslint:disable-next-line:max-line-length
        const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        this.emailValid = re.test(email);
        return this.emailValid;
    }
}
