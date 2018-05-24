import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { Tab } from 'modelo/tab/Tab';
import { Observable } from 'rxjs/Observable';

import { ControlService } from './../control/control.service';
import { GeneralService } from './../general.service';


@Injectable()
export class TabService extends GeneralService{
    private headers = new Headers({'Content-Type': 'application/json'});
    
	constructor(private http: Http, private Control: ControlService){
        super();
    }

	getTabs() : Observable<Tab[]> {
		return this.http.get(this.Control.context_app + 'woopsocial/show_feeds_action')
            .map(response => {
                return super.extractData(response);
            })
            .catch(super.handleError);
	}
}