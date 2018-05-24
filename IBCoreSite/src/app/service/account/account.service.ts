import { IpagDTO } from './../../models/ipag/IpagDTO';
import { User } from './../../models/user/user';
import { AccountUser } from './../../model/AccountUser';
import { GeneralService } from './../general/general.service';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';
import 'rxjs/add/observable/throw';

@Injectable()
export class AccountService extends GeneralService {
    
    private headers = new Headers({ 'Content-Type': 'application/json' });

    public planSelected = '';
    public numberOfProfiles: number;
    public teamMembers: number;

    constructor(private http: Http) {
        super();
     }

     createAccount(accountUser : AccountUser): Observable<User> {
         return this.http.post(this.environment.contexto_aplicacao + 'woopsocial/create_account', accountUser, { headers: this.headers, withCredentials: true })
             .map(this.extractData)
             .catch(this.handleError);
     }

     makeTransaction(ipagDTO : IpagDTO, planType): Observable<User> {
         return this.http.post(this.environment.contexto_aplicacao + 'api/ipagTransaction', ipagDTO, { headers: this.headers, withCredentials: true })
             .map(this.extractDataTransaction)
             .catch(this.handleTransactionError);
     }

     makeTransactionCielo(ipagDTO : IpagDTO, planType): Observable<User> {
         /*return this.http.post(this.environment.contexto_aplicacao + 'api/ipagTransaction', ipagDTO, { headers: this.headers, withCredentials: true })
             .map(this.extractDataTransaction)
             .catch(this.handleError);*/
        return this.http.post(this.environment.contexto_aplicacao + 'api/cieloTransaction', ipagDTO, { headers: this.headers, withCredentials: true })
             .map(this.extractDataTransaction)
             .catch(this.handleError);
     }

     extractDataTransaction(res: Response) {		
         return res;
     }

     extractData(res: Response) {		
         if (res.status == 200) {
             return res.json();
         }
         return null;
     }
    
     handleTransactionError(error: any) {
         return Observable.throw(error._body);
     }
    
     handleError(error: any) {
         return Observable.throw(error);
     }
}
