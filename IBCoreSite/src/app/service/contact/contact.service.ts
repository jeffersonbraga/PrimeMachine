import { GeneralService } from './../general/general.service';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';

@Injectable()
export class ContactService extends GeneralService {
    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http) {
        super();
     }

    sendMessage(name, email, message): Observable<any> {
        // tslint:disable-next-line:max-line-length
        return this.http.get(this.environment.contexto_aplicacao + 'woopsocial/contact?name=' + name + '&email=' + email + '&message=' + message, { headers: this.headers, withCredentials: true })
            .map(response => {
                return response['_body'];
            })
            .catch(super.handleError);
    }
}
