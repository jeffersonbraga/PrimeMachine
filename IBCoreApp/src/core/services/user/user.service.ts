import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { ControlService } from './../control/control.service';
import { GeneralService } from './../general.service';

@Injectable()
export class UserService extends GeneralService {

    private headers = new Headers({ 'Content-Type': 'application/json' });

    public returnComponent: string;

    constructor(private http: Http, private Control: ControlService) {
        super();
    }

    updateUser(user): Observable<Object> {
        return this.http.put(this.Control.context_app + 'woopsocial/update_user', user, { headers: this.headers, withCredentials: true })
            .map(response => {
                return super.extractData(response);
            })
            .catch(super.handleError);
    }

    setTutorialShowed(): Observable<Object> {
        return this.http.put(this.Control.context_app + 'woopsocial/set_tutorial_showed', null, { headers: this.headers, withCredentials: true })
            .map(response => {
                return super.extractData(response);
            })
            .catch(super.handleError);
    }

    /*
        Class java: UpdateTypeUserAction.java
     */
    updateTypeUser(user): Observable<Object> {
        return this.http.put(this.Control.context_app + 'woopsocial/update_type_user', user, { headers: this.headers, withCredentials: true })
            .map(response => {
                return super.extractData(response);
            })
            .catch(super.handleError);
    }

    /*
        Class java: DeleteUserAction.java
     */
    deleteUser(user): Observable<Object> {
        return this.http.delete(this.Control.context_app + 'woopsocial/delete_user?idUser=' + user.idUser, { headers: this.headers, withCredentials: true })
            .map(response => {
                return response;
            })
            .catch(super.handleError);
    }

    insertUser(type: string, email: string): Observable<Object> {
        return this.http.post(this.Control.context_app + 'woopsocial/insert_user?type=' + type + '&email=' + email, null, { headers: this.headers, withCredentials: true })
            .map(response => {
                return super.extractData(response);
            })
            .catch(super.handleError);
    }

    getByUrlActivation(url: string): Observable<Object> {
        return this.http.get(this.Control.context_app + 'woopsocial/get_activation?url=' + url, { headers: this.headers, withCredentials: true })
            .map(response => {
                return super.extractData(response);
            })
            .catch(super.handleError);
    }

    /*
        Class java: ActivateUserAction.java
     */
    activateUser(user): Observable<Object> {
        return this.http.post(this.Control.context_app + 'woopsocial/activate_user', user, { headers: this.headers, withCredentials: true })
            .map(response => {
                return response;
            })
            .catch(super.handleError);
    }

    cancelSubscription(account): Observable<Object> {
        return this.http.post(this.Control.context_app + 'woopsocial/cancel_subscription', account, { headers: this.headers, withCredentials: true })
            .map(response => {
                return response;
            })
            .catch(super.handleError);
    }

    unsubscribeEmails(email: string): Observable<Object> {
        return this.http.post(this.Control.context_app + 'woopsocial/unsubscribe_emails', email, { headers: this.headers, withCredentials: true })
            .map(response => {
                return response;
            })
            .catch(super.handleError);
    }
}