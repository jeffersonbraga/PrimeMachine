import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import { Profile } from 'modelo/profile/Profile';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';

import { ControlService } from './../control/control.service';

@Injectable()
export class InstagramService{

	private headers = new Headers({'Content-Type': 'application/json'});

	constructor(private http: Http, private Control: ControlService){}

	getReportOverview(profile_: Profile, dateFrom_: number, dateUntil_: number) : Observable<Object> {

        var data = JSON.stringify(profile_);
		return this.http.post(this.Control.context_app + 'woopsocial/report_instagram_overview/?dateFrom_=' + dateFrom_ + '&dateUntil_=' + dateUntil_, data, {headers: this.headers, withCredentials: true})
                    .map(response => {
						return this.extractData(response);
					})
                    .catch(this.handleError);
	}

	getOverviewReportXLS(idProfile: number, dateFrom: number, dateUntil: number) : Observable<Object> {
        return this.http.get(this.Control.context_app + 'woopsocial/report_instagram_overview_xls/?idProfile=' + idProfile + '&dateFrom=' + dateFrom + '&dateUntil=' + dateUntil, {headers: this.headers, withCredentials: true})
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

	getReportHistorical(profile_: Profile) : Observable<Object> {

        var data = JSON.stringify(profile_);
		return this.http.post(this.Control.context_app + 'woopsocial/report_instagram_historical/', data, {headers: this.headers, withCredentials: true})
                    .map(response => {
						return this.extractData(response);
					})
                    .catch(this.handleError);
    }
    
    getReportHistoricalXLS(idProfile: number) : Observable<Object> {
        return this.http.get(this.Control.context_app + 'woopsocial/report_instagram_historical_xls/?idProfile=' + idProfile, {headers: this.headers, withCredentials: true})
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

	getReportDashboard(profile_: Profile, dateFrom_: number, dateUntil_: number) : Observable<Object> {

        var data = JSON.stringify(profile_);
		return this.http.post(this.Control.context_app + 'woopsocial/dashboard_report_instagram/?dateFrom_=' + dateFrom_ + '&dateUntil_=' + dateUntil_, data, {headers: this.headers, withCredentials: true})
                    .map(response => {
						return this.extractData(response);
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