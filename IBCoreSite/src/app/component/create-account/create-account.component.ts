import { IpagDTO } from './../../models/ipag/IpagDTO';
import { Subscription } from 'rxjs/Subscription';
import { AccountService } from './../../service/account/account.service';
import { AccountUser } from './../../model/AccountUser';
import { User } from './../../models/user/user';
import { HomeService } from './../../service/home/home.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { GeneralService } from '../../service/general/general.service';

declare const $;

@Component({
    selector: 'create-account',
    templateUrl: './create-account.component.html',
    styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent implements OnInit, OnDestroy {

    subscription: Subscription;
    accountUser: AccountUser;

    message: string;
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    repeatPassword: string;
    country: string;
    city: string;
    company: string;
    phoneNumber: string;

    agree: boolean;
    cardholder: string;
    cardnumber: string;
    cvc: string;

    user: User;
    expirationMonth: string;
    expirationYear: string;
    planType: string;
    yearly: boolean;

    numberOfProfiles = 35;
    teamMembers = 3;
    planPrice: number;
    planPriceYearly: number;

    form: FormGroup;
    formPayment: FormGroup;
    emailValid = false;
    passwordChecked = false;
    accountSending = false;
    accountSent = false;
    accountError = false;
    showDetails = false;

    ipagdto : IpagDTO = new IpagDTO();

    Bandeiras : any = {};

    transactionSuccess : boolean = false;

    canSave : boolean = true;
    cardCheckSuccess: boolean = false;

    constructor(
        private generalService: GeneralService,
        private homeService: HomeService,
        private accountService: AccountService,
        private route: ActivatedRoute,
        private router: Router,
        private formBuilder: FormBuilder
    ) { 
        this.Bandeiras["Elo"] = {
        regexpBin: /^401178|^401179|^431274|^438935|^451416|^457393|^457631|^457632|^504175|^627780|^636297|^636368|^(506699|5067[0-6]\d|50677[0-8])|^(50900\d|5090[1-9]\d|509[1-9]\d{2})|^65003[1-3]|^(65003[5-9]|65004\d|65005[0-1])|^(65040[5-9]|6504[1-3]\d)|^(65048[5-9]|65049\d|6505[0-2]\d|65053     [0-8])|^(65054[1-9]|6505[5-8]\d|65059[0-8])|^(65070\d|65071[0-8])|^65072[0-7]|^(65090[1-9]|65091\d|650920)|^(65165[2-9]|6516[6-7]\d)|^(65500\d|65501\d)|^(65502[1-9]|6550[3-4]\d|65505[0-8])/,
        regexpFull: /^(401178|401179|431274|438935|451416|457393|457631|457632|504175|627780|636297|636368|(506699|5067[0-6]\d|50677[0-8])|(50900\d|5090[1-9]\d|509[1-9]\d{2})|65003[1-3]|(65003[5-9]|65004\d|65005[0-1])|(65040[5-9]|6504[1-3]\d)|(65048[5-9]|65049\d|6505[0-2]\d|65053[0-8])|(65054[1-9]| 6505[5-8]\d|65059[0-8])|(65070\d|65071[0-8])|65072[0-7]|(65090[1-9]|65091\d|650920)|(65165[2-9]|6516[6-7]\d)|(65500\d|65501\d)|(65502[1-9]|6550[3-4]\d|65505[0-8]))[0-9]{10,12}/,
        regexpCvv: /^\d{3}$/,
        }; 
        this.Bandeiras["Dinners"] = {
            regexpBin: /^3(?:0[0-5]|[68][0-9])/,
            regexpFull: /^3(?:0[0-5]|[68][0-9])[0-9]{11}$/,
            regexpCvv: /^\d{3}$/
        }; 
        this.Bandeiras["Discover"] = {
            regexpBin: /^6(?:011|5[0-9]{2})/,
            regexpFull: /^6(?:011|5[0-9]{2})[0-9]{12}$/,
            regexpCvv: /^\d{3}$/
        }; 
        this.Bandeiras["Hipercard"] = {
            regexpBin: /^3841[046]0|^60/,
            regexpFull: /^(38[0-9]{17}|60[0-9]{14})$/,
            regexpCvv: /^\d{3}$/
        }; 
        this.Bandeiras["Amex"] = {
            regexpBin: /^3[47]/,
            regexpFull: /^3[47][0-9]{13}$/,
            regexpCvv: /^\d{3,4}$/
        }; 
        this.Bandeiras["Aura"] = {
            regexpBin: /^50[0-9]/,
            regexpFull: /^50[0-9]{14,17}$/,
            regexpCvv: /^\d{3}$/
        }; 
        this.Bandeiras["Master"] = {
            regexpBin: /^5[1-5][0-9][0-9]/,
            regexpFull: /^5[1-5][0-9]{14}$/,
            regexpCvv: /^\d{3}$/
        };
        this.Bandeiras["Visa"] = {
            regexpBin: /^4/,
            regexpFull: /^4[0-9]{12}(?:[0-9]{3})?$/,
            regexpCvv: /^\d{3}$/
        };

        this.showDetails = false;
    }

    ngOnInit() {
        this.form = this.formBuilder.group({
            firstName: [null, [Validators.required]],
            email: [null, [Validators.required]],
            password : [null, [Validators.required, Validators.minLength(4)]],
            repeatPassword : [null, [Validators.required]],
            country : [null, [Validators.required]],
            agree : [null, [Validators.required]],
        });

        this.formPayment = this.formBuilder.group({
            cardholder: [null, [Validators.required]],
            cardnumber: [null, [Validators.required]],
            expirationMonth: [null, [Validators.required]],
            expirationYear : [null, [Validators.required]],
            cvc : [null, [Validators.required]],
        });

        this.user = this.homeService.user;

        if (!this.user) {
            this.subscription = this.homeService.user$
                .subscribe(
                    user => {
                        this.user = user;
                        if (this.user) {
                            this.showDetails = true;
                            this.goToTab2();
                        }
                    }
                );
        } else {
            this.goToTab2();
        }

        this.route
        .queryParams
        .subscribe(params => {
            this.planType = params.plan;
            if (this.planType === 'custom') {
                this.numberOfProfiles = this.accountService.numberOfProfiles;
                this.teamMembers = this.accountService.teamMembers;
                this.calculatePrice();
            }

            if (this.planType) {
                this.showDetails = true;
                this.goToTab2();
            }
        });

        this.calculatePrice();

        $('#country').select2();
        $('#country').on(
            'change',
            (e: any) => this.country = e.target.value
        );

        $('#expirationMonth').select2();
        $('#expirationMonth').on(
            'change',
            (e: any) => this.expirationMonth = e.target.value
        );

        $('#expirationYear').select2();
        $('#expirationYear').on(
            'change',
            (e: any) => this.expirationYear = e.target.value
        );
    }

    goToTab2() {
        setTimeout(() => {
            $('.nav-tabs a[href="#tab2"]').tab('show');
            $('.nav-tabs a[href="#tab2"]').click();
        }, 100);
    }

    get firstNameInput() { return this.form.get('firstName'); }
    get emailInput() { return this.form.get('email'); }
    get passwordInput() { return this.form.get('password'); }
    get repeatPasswordInput() { return this.form.get('repeatPassword'); }
    get countryInput() { return this.form.get('country'); }
    get agreeInput() { return this.form.get('agree'); }

    get cardholderInput() { return this.formPayment.get('cardholder'); }
    get cardnumberInput() { return this.formPayment.get('cardnumber'); }
    get expirationMonthInput() { return this.formPayment.get('expirationMonth'); }
    get expirationYearInput() { return this.formPayment.get('expirationYear'); }
    get cvcInput() { return this.formPayment.get('cvc'); }

    plus() {
        if (this.numberOfProfiles < 300) {
            this.numberOfProfiles = this.numberOfProfiles + 5;
            this.calculatePrice();

            if (this.numberOfProfiles % 10 === 0) {
                this.teamMembers += 1;
            }
        }
    }

    minus() {
        if (this.numberOfProfiles > 35) {

            if (this.teamMembers > 3 && this.numberOfProfiles % 10 === 0) {
                this.teamMembers -= 1;
            }

            this.numberOfProfiles = this.numberOfProfiles - 5;
            this.calculatePrice();
        }
    }

    calculatePrice() {
        this.planPrice = this.numberOfProfiles;
        this.planPriceYearly = this.planPrice * 12 - (this.planPrice * 12 * 20 / 100);
    }

    validateEmail() {
        this.message = null;
        const email = this.email;
        const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        this.emailValid = re.test(email);
        return this.emailValid;
    }

    passwordMatch() {
        if (this.password === this.repeatPassword) {
            this.passwordChecked = true;
        } else {
            this.passwordChecked = false;
        }
    }

    createAccount() {
        this.accountSending = true;
        const accountUser: AccountUser = new AccountUser();

        accountUser.firstName = this.firstName;
        accountUser.email = this.email.toLowerCase();
        accountUser.password = this.password;
        accountUser.country = this.country;

        if (this.user) {
            this.user.email = this.email;
            this.user.password = this.password;
        } else {
            this.user = new User();
            this.user.email = this.email.toLowerCase();
            this.user.password = this.password;
        }

        this.showDetails = false;

        this.accountService.createAccount(accountUser)
        .subscribe(
            result => this.onCreateAccount(result),
            error => this.onCreateAccountError(error)
        );
    }

    onCreateAccount(userAux: any) {
        this.accountSending = false;
        this.accountSent = true;
        this.accountError = false;

        this.homeService.login(this.user).subscribe(
            result => this.onLogin(result),
            error => this.onLoginError(error)
        );
    }

    onLogin(result: any) {
        this.homeService.getLogged()
            .subscribe(
                user => this.onGetLogged(user)
            );
    }

    onGetLogged(user: User) {
        this.homeService.userSource.next(user);

        this.route.queryParams.subscribe(params => {
            this.planType = params.plan;
            if (this.planType === 'custom') {
                this.numberOfProfiles = this.accountService.numberOfProfiles;
                this.teamMembers = this.accountService.teamMembers;
                this.calculatePrice();
            }

            if (this.planType) {
                this.showDetails = true;
                this.goToTab2();
            } else {
                this.showDetails = false;
                window.open('https://app.woopsocial.com/home/stream', '_self'); // this.generalService.environment.contexto_aplicacao +
            }
        });
    }

    onLoginError(error: any) {
        console.log(error);
    }

    onCreateAccountError(error: any) {
        if (error.status === 403) {
            this.message = 'User email already registered';
        }
        this.accountSending = false;
        this.accountSent = false;
        this.accountError = true;
    }

    ngOnDestroy(): void {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }

    makeTransaction() {

        if (this.canSave) {
            this.ipagdto.valor = "2";
            this.ipagdto.ano_cartao = this.expirationYear;
            this.ipagdto.mes_cartao = this.expirationMonth;
            this.ipagdto.nome = this.ipagdto.nome_cartao;
            this.ipagdto.plantype = this.planType;

            if (this.ipagdto.plantype == "custom") {
                this.ipagdto.plantype = this.ipagdto.plantype + ":" + this.numberOfProfiles + ":" + this.teamMembers;
            }

            this.ipagdto.metodo = this.obterNome(this.ipagdto.num_cartao);
            if (this.yearly) {
                this.ipagdto.frequencia = "Y";
            } else {
                this.ipagdto.frequencia = "M";
            }

            this.canSave = false;
            this.accountService.makeTransaction(this.ipagdto, this.planType)
            .subscribe(
                result => this.onMakeTransaction(result),
                error => this.onMakeTransactionError(error)
            );
        }
    }

    onMakeTransaction(res) {
        this.showSuccess();
        this.cardCheckSuccess = true;
    }

    onMakeTransactionError(error: any) {
        this.transactionSuccess = false;
        this.showErrorPersist(error);
        this.canSave = true;
    }

    obterNome(num) {
        var results = new Array();
        if(this.validarNumero('Master', num)) {
            return ('mastercard');
        }

        if(this.validarNumero('Visa', num))return('visa');
        if(this.validarNumero('Amex', num))return('amex');
        if(this.validarNumero('Elo', num))return('elo');
        if(this.validarNumero('Dinners', num))return('diners');
        if(this.validarNumero('Discover', num))return('discover');
        if(this.validarNumero('Aura', num))return('aura');
        if(this.validarNumero('Hipercard', num))return('hipercard');

        return null;
    } 

    validarNumero(card, num){
        return this.Bandeiras[card].regexpFull.test(Number(num));
    };

    showErrorPersist(error) {
        const options = {
            message: "Error: " + error,
            style: 'bar',
            timeout: 8000,
            position: 'top',
            type: 'danger'
        };

        $('body').pgNotification(options).show();
    }

    showSuccess() {
        const options = {
            message: "Payment successfully.",
            style: 'bar',
            timeout: 6000,
            position: 'top',
            type: 'success'
        };

        $('body').pgNotification(options).show();
    }
}