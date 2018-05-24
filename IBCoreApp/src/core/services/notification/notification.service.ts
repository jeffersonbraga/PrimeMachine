import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { Notification } from 'modelo/notification/Notification';
import { Observable } from 'rxjs/Observable';

import { ControlService } from './../control/control.service';
import { GeneralService } from './../general.service';

@Injectable()
export class NotificationService extends GeneralService {
    private headers = new Headers({'Content-Type': 'application/json'});

    public COLOR_SUCCESS = '#48b0f7';
    public COLOR_WARNING = '#f8d053';
    public COLOR_ERROR = '#f55753';

    constructor(private http: Http, private Control: ControlService) {
        super();
    }

    getNotifications() : Observable<Notification[]> {
		return this.http.get(this.Control.context_app + 'woopsocial/list_notifications', {headers: this.headers, withCredentials: true})
            .map(response => {
                return super.extractData(response)[0];
            })
            .catch(super.handleError);
    }
        
    addNotifications(notification: Notification) : Observable<Notification[]> {
		return this.http.put(this.Control.context_app + 'woopsocial/add_notification', notification, {headers: this.headers, withCredentials: true})
            .map(response => {
                return super.extractData(response)[0];
            })
            .catch(super.handleError);
    }

    updateNotifications(notification: Notification): Observable<Notification[]> {
        return this.http.post(this.Control.context_app + 'woopsocial/update_notifications',
            notification, {headers: this.headers, withCredentials: true})
            .map(response => {
                return super.extractData(response)[0];
            })
            .catch(super.handleError);
    }
}
