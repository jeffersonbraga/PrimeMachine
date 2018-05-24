import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { ControlService } from './../control/control.service';
import { GeneralService } from './../general.service';

@Injectable()
export class TimezoneService extends GeneralService{

    private headers = new Headers({'Content-Type': 'application/json'});

    public defaultTimezoneValue : any = 13;
    
	constructor(private http: Http, private Control: ControlService){
        super();
    }

	getTimezone() : Observable<Object> {
		return this.http.get(this.Control.context_app + 'woopsocial/list_timezones', {headers: this.headers, withCredentials: true})
            .map(response => {
                return super.extractData(response);
            })
            .catch(super.handleError);
	}

	getDefaultTimezone() : Observable<Object> {
		return this.http.get(this.Control.context_app + 'woopsocial/get_timezone', {headers: this.headers, withCredentials: true})
            .map(response => {
                this.defaultTimezoneValue = super.extractData(response);
                return this.defaultTimezoneValue;
            })
            .catch(super.handleError);
	}
}