import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import { BitlyShortenUrlDTO } from 'modelo/shortenLink/BitlyShortenUrlDTO';
import { Observable } from 'rxjs/Observable';

import { ControlService } from './../control/control.service';

@Injectable()
export class CheckUrlService {

	headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http, private controlService : ControlService) {}

    checkValidUrl(url  : string) : Observable<Object> {

        return this.http.get(this.controlService.context_app + 'woopsocial/recover_link_resume/?url=' + url, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
	}

    shortnerUrlService(list : BitlyShortenUrlDTO[]) : Observable<Object> {

        return this.http.post(this.controlService.context_app + 'woopsocial/shorten_links/', list,{headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
	}

	extractData(res: Response) {		
		if (res.status == 200) {
			return res.json();
		}
		return false;
	}

    handleError(error: any) {
        return Observable.throw('::URL NOT FOUND::');
    }
}