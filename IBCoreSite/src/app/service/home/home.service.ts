import { User } from './../../models/user/user';
import { GeneralService } from './../general/general.service';
import { Http, Headers, Response } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';
import { Subject } from 'rxjs/Subject';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class HomeService extends GeneralService {
    private headers = new Headers({ 'Content-Type': 'application/json' });

    public userSource = new Subject<User>();
    public user$ = this.userSource.asObservable();

    public user: User;

    constructor(private http: Http) {
        super();
    }

    getLogged(): Observable<User> {
        // tslint:disable-next-line:max-line-length
        return this.http.get(this.environment.contexto_aplicacao + 'woopsocial/get_logged_user', { headers: this.headers, withCredentials: true })
            .map(response => {
                return response['_body'];
            })
            .catch(super.handleError);
    }

    logout() {
        return this.http.get(this.environment.contexto_aplicacao + 'woopsocial/logout', { headers: this.headers, withCredentials: true })
            .map(response => {
                return response['_body'];
            })
            .catch(super.handleError);
    }

    onLogout() {
        return this.http.post(this.environment.contexto_aplicacao + 'api/logout', { headers: this.headers, withCredentials: true })
            .map(response => {
                return response['_body'];
            })
            .catch(super.handleError);
    }

    login(user : User) : Observable<Object> {
        return this.http.post( this.environment.contexto_aplicacao + 'api/authentication?login=' + user.email 
        + '&password=' + encodeURIComponent(user.password), null, {headers: this.headers, withCredentials: true})
                    .map(this.extractDataLogin)
                    .catch(super.handleError);
	}
    
    sendMessage(email: string): Observable<any> {
        return this.http.post(this.environment.contexto_aplicacao + 'woopsocial/redefine_password_site?email=' + email, null, { headers: this.headers, withCredentials: true })
            .map(this.extractData)
            .catch(this.handleError);
    }

	extractData(res: Response) : any {
        return res;
	}

	handleError(error: any) : any {
        console.log(error);
        return error;
	}

    extractDataLogin(res) {
		if (res.status == 200) {
			return true;
		}
		return false;
    }

    setUser(user : User) {
        this.user = user;
        this.userSource.next(this.user);
    }
}
