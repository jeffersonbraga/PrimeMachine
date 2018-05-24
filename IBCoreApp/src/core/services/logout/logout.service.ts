import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import { ControlService } from 'core/services/control/control.service';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';

@Injectable()
export class LogoutService {

    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http, private controlApp: ControlService) {}

    logout() {
        return this.http.get(this.controlApp.context_app + 'woopsocial/logout', { headers: this.headers, withCredentials: true })
            .map(response => {
                return response['_body'];
            })
            .catch(this.handleError);
    }

    onLogout() {
        return this.http.post(this.controlApp.context_app + 'api/logout', { headers: this.headers, withCredentials: true })
            .map(response => {
                return response['_body'];
            })
            .catch(this.handleError);
    }

    private extractData(res: Response) {
        if (res.status == 200) {
            //return res.json();
            return true;
        }
        return false;
    }

    private handleError(error: any) {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }

    private createObservable(data: any) : Observable<any> {
        return Observable.create((observer: Observer<any>) => {
            observer.next(data);
            observer.complete();
        });
    }
}