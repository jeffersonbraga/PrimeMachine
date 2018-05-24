import 'rxjs/add/observable/throw';

import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import { Router } from '@angular/router';
import { ControlService } from 'core/services/control/control.service';
import { User } from 'modelo/user/User';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';

@Injectable()
export class LoginService {

    public static isLoggedAux = false;

    public isLogged: boolean = null;

    private headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});

    constructor(
        private http: Http,
        private controlApp: ControlService,
        private router: Router
    ) {}

    efetuarLogin(usr: User): Observable<Object> {
        return this.http.post( this.controlApp.context_app + 'api/authentication?login=' + usr.email + '&password=' + encodeURIComponent(usr.password), null, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    isLoggedIn(): Observable<boolean> {

        return this.http.post( this.controlApp.context_app + 'woopsocial/authenticate', null, {headers: this.headers, withCredentials: true})
                    .map(this.extractDataCheckLogin)
                    .catch(this.handleError);


    }

    canActivate(): Observable<Response> {
        return this.http.post( this.controlApp.context_app + 'woopsocial/authenticate', null, {headers: this.headers, withCredentials: true})
                    .map(this.extractDataCheckActivate)
                    .catch(this.handleError);
    }

    getUsuarioSession(): Observable<Object[]> {
        /*if (!this.usuario) {
            return this.http.get(this._baseUrl + 'customers.json')
                        .map((res: Response) => {
                            this.usuario = res.json();
                            return this.usuario;
                        })
                        .catch(this.handleError);
        }
        else {
            //return cached data
            return this.createObservable(this.usuario);
        }*/

        return null;
    }

    private extractDataCheckLogin(res: Response): boolean {
        if (res.status === 200) {
            // return res.json();
            this.isLogged = res.json();

            return this.isLogged;
        } else {
            this.isLogged = res.json();
            return false;
        }
    }

    private extractDataCheckActivate(res: Response): boolean {
        let logged = false;
        if (res.status === 200) {
            logged = res.json();
        };

        if (!logged) {
            window.location.href = 'https://woopsocial.com';
        }
        return this.isLogged;
    }

    private extractData(res: Response) {
        if (res.status === 200) {
            // return res.json();
            return true;
        }
        return false;
    }

    private createObservable(data: any): Observable<any> {
        return Observable.create((observer: Observer<any>) => {
            observer.next(data);
            observer.complete();
        });
    }

    private handleError(error: any) {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }
}
