import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { User } from 'modelo/user/User';
import { Observable } from 'rxjs/Observable';

import { ControlService } from './../control/control.service';
import { GeneralService } from './../general.service';

@Injectable()
export class ScheduleService extends GeneralService {

    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http, private Control: ControlService) {
        super();
    }

    getSchedule(usr: User, status: string, profiles: string[]): Observable<Object> {
        return this.http.get(this.Control.context_app + 'woopsocial/list_schedulingsby_profile/?profileList=' + profiles + '&status=' + status + '&offset=0', {headers: this.headers, withCredentials: true})
                    .map(response => {
                        response['statusPost'] = status;
                        const body = response.json() || [[]];
                        body.push({status: status});
                        return body;
                    })
                    .catch(super.handleError);
    }

    deleteSchedule(id) {
        return this.http.delete(this.Control.context_app + 'woopsocial/delete_schedule/?idpost=' + id, {headers: this.headers, withCredentials: true})
            .map(super.extractData)
            .catch(super.handleError);
    }
}
