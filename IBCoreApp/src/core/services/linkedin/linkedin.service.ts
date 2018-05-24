import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import { ReportLinkedinOverviewDTO } from 'modelo/reports/ReportLinkedinOverviewDTO';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';

import { ControlService } from './../control/control.service';

@Injectable()
export class LinkedinService {

	private headers = new Headers({'Content-Type': 'application/json'});

	constructor(private http: Http, private Control: ControlService){}

	getReport(idProfile: number, dateFrom: number, dateUntil: number) : Observable<Object> {
		return this.http.get(this.Control.context_app + 'woopsocial/report_linkedin_overview/?idProfile=' + idProfile + '&dateFrom=' + dateFrom + '&dateUntil=' + dateUntil, {headers: this.headers, withCredentials: true})
            .map(response => {
                return this.extractData(response);
            })
            .catch(this.handleError);
	}
        
    getLinkedinReportXLS(reportLinkedinOverviewDTO : ReportLinkedinOverviewDTO, idProfile : number) : Observable<Object>{
        return this.http.put(this.Control.context_app + 'woopsocial/report_linkedin_overview_xls', reportLinkedinOverviewDTO, {headers: this.headers, withCredentials: true})
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