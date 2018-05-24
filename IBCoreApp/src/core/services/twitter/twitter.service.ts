import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';

import { ControlService } from './../control/control.service';

@Injectable()
export class TwitterService{

    private headers = new Headers({'Content-Type': 'application/json'});
    
    constructor(private http: Http, private Control: ControlService){}

	getReport(idProfile: number, dateFrom: number, dateUntil: number) : Observable<Object> {
		return this.http.get(this.Control.context_app + 'woopsocial/report_twitter_overview/?idProfile=' + idProfile + '&dateFrom=' + dateFrom + '&dateUntil=' + dateUntil, {headers: this.headers, withCredentials: true})
            .map(response => {
                return this.extractData(response);
            })
            .catch(this.handleError);
    }
    
    getMorePosts(idProfile: number, dateFrom: number, dateUntil: number, offset: number) : Observable<Object> {
		return this.http.get(this.Control.context_app + 'woopsocial/report_twitter_statuses/?idProfile=' + idProfile + '&dateFrom=' + dateFrom + '&dateUntil=' + dateUntil + '&offset=' + offset, {headers: this.headers, withCredentials: true})
            .map(response => {
                return this.extractData(response);
            })
            .catch(this.handleError);
    }

    getXLS(idProfile: number, dateFrom: number, dateUntil: number) : Observable<Object>{
        return this.http.get(this.Control.context_app + 'woopsocial/report_twitter_overview_xls/?idProfile=' + idProfile + '&dateFrom=' + dateFrom + '&dateUntil=' + dateUntil, {headers: this.headers, withCredentials: true})
            .map(response => {
                let obj = this.extractData(response);
                return {
                    fileName: obj[0].value,
                    formattedFileName: obj[1].value,
                    idAccount: obj[2].value
                }
            })
            .catch(this.handleError);
    }

	private extractData(res: Response) {
		let body = res.json();
		return body || [];
	}
    
    private createObservable(data: any) : Observable<any> {
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