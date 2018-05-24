import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ControlService } from 'core/services/control/control.service';
import { LoginService } from 'core/services/login/login.service';
import { User } from 'modelo/user/User';

declare const $: any;

@Component({
    moduleId: module.id,
    selector: 'login',
    templateUrl: 'login.component.html'
})

export class LoginComponent implements OnInit {

    user: User;

    errorMessage: string;

    enviroment  : string;

    isNotVisible   : boolean = true;

    @ViewChild('containerLogin')
    containerLogin : any;

    @ViewChild('containerError')
    public containerError: any;

    constructor(private dataService: LoginService, private router: Router, private controlApp: ControlService, 
                private route: ActivatedRoute) {

        this.user = new User();
    }

    ngOnInit() {
        this.containerError.nativeElement.hidden = true;
        //this.containerLogin.nativeElement.hidden = true;
        this.enviroment = this.controlApp.enviroment;

        if (this.enviroment === 'prod') {

            this.isNotVisible = true;
            this.route.params.subscribe(params => {

                let matrix = +params['matrix'];

                if (!matrix){
                    window.location.href = 'https://woopsocial.com/';
                } else {

                    this.isNotVisible = false;

                }
            });

            if (this.isNotVisible) {
                window.location.href = 'https://woopsocial.com/';
            } else {
                this.containerLogin.nativeElement.hidden = this.isNotVisible;
            }
        } else {

            this.isNotVisible = false;
            this.containerLogin.nativeElement.hidden = false;
            if (this.controlApp && this.controlApp.getUser() && this.controlApp.getUser().type && this.controlApp.getUser().type == "W") {
                this.user.email = "rafaelalbani@gmail.com";
            }
        }

        this.containerLogin.nativeElement.hidden = this.isNotVisible;
    }

    onSubmit() {
        this.dataService.efetuarLogin(this.user)
            .subscribe(
            result => this.onLogin(result),
            error => this.onLogin(false));
    }

    onLogin(result: any) {
        if (!result) {
            this.containerError.nativeElement.hidden = false;
            this.errorMessage = 'Houve um erro ao efetuar o login. Por favor verifique usuário e senha.';
        } else {
            // this.controlApp.setUser(result);

            this.controlApp.getInfoAccount().subscribe(
            account => {
                this.controlApp.adminSource.next(account[0]);
                this.controlApp.analystsSource.next(account[1]);
                this.controlApp.userSource.next(account[2]);

                this.controlApp.admins     = account[0];
                this.controlApp.analysts      = account[1];
                this.controlApp.setUser(account[2]);
                // this.router.navigate(['home/compose']);
                // this.router.navigateByUrl('/home/compose');
                // window.location.href = this.controlApp.context_app + "/home/stream";
            },
            error => console.log(error));

            this.dataService.isLogged = result;
            // window.location.href = "http://192.168.0.13:4200/home/stream";
            window.location.href = this.controlApp.context_app_front + "home/stream";
            // this.router.navigate(['/home/stream']);
        }
    }
}