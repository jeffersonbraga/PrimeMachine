import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { GeneralService } from 'core/services/general.service';
import { Observable } from 'rxjs/Observable';

import { ControlService } from './../control/control.service';

@Injectable()
export class FacebookService extends GeneralService {

	private headers = new Headers({'Content-Type': 'application/json'});
    
    constructor(private http: Http, private Control: ControlService) {
        super();
    }

	getOverviewReport(idProfile: number, dateFrom: number, dateUntil: number) : Observable<Object> {
		return this.http.get(this.Control.context_app + 'woopsocial/report_facebook_overview/?idProfile=' + idProfile + '&dateFrom=' + dateFrom + '&dateUntil=' + dateUntil, {headers: this.headers, withCredentials: true})
            .map(response => {
                return this.extractData(response);
            })
            .catch(this.handleError);
    }
        
    getOverviewReportXLS(idProfile: number, dateFrom: number, dateUntil: number) : Observable<Object> {
        return this.http.get(this.Control.context_app + 'woopsocial/report_facebook_overview_xls/?idProfile=' + idProfile + '&dateFrom=' + dateFrom + '&dateUntil=' + dateUntil, {headers: this.headers, withCredentials: true})
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

    getContentReport(idProfile: number, dateFrom: number, dateUntil: number) : Observable<Object> {
		return this.http.get(this.Control.context_app + 'woopsocial/report_facebook_content/?idProfile=' + idProfile + '&dateFrom=' + dateFrom + '&dateUntil=' + dateUntil, {headers: this.headers, withCredentials: true})
            .map(response => {
                return this.extractData(response);
            })
            .catch(this.handleError);
    }

    getContentReportXLS(idProfile: number, dateFrom: number, dateUntil: number) : Observable<Object>{
        return this.http.get(this.Control.context_app + 'woopsocial/report_facebook_content_xls/?idProfile=' + idProfile + '&dateFrom=' + dateFrom + '&dateUntil=' + dateUntil, {headers: this.headers, withCredentials: true})
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
        
    getPostToPostReport(idProfile: number, dateFrom: number, dateUntil: number) : Observable<Object> {
		return this.http.get(this.Control.context_app + 'woopsocial/report_facebook_post_to_post/?idProfile=' + idProfile + '&dateFrom=' + dateFrom + '&dateUntil=' + dateUntil, {headers: this.headers, withCredentials: true})
            .map(response => {
                return this.extractData(response);
            })
            .catch(this.handleError);
    }

    getPostToPostReportXLS(idProfile: number, dateFrom: number, dateUntil: number) : Observable<Object> {
		return this.http.get(this.Control.context_app + 'woopsocial/report_facebook_post_to_post_xls/?idProfile=' + idProfile + '&dateFrom=' + dateFrom + '&dateUntil=' + dateUntil, {headers: this.headers, withCredentials: true})
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

    getHistoricalReport(idProfile: number) : Observable<Object> {
		return this.http.get(this.Control.context_app + 'woopsocial/report_facebook_historical/?idProfile=' + idProfile, {headers: this.headers, withCredentials: true})
            .map(response => {
                return this.extractData(response);
            })
            .catch(this.handleError);
    }

    getHistoricalReportXLS(idProfile: number) : Observable<Object> {
		return this.http.get(this.Control.context_app + 'woopsocial/report_facebook_historical_xls/?idProfile=' + idProfile, {headers: this.headers, withCredentials: true})
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

}