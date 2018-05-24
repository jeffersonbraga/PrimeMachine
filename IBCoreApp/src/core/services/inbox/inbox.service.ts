import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import { ControlService } from './../control/control.service';
import { GeneralService } from './../general.service';


@Injectable()
export class InboxService extends GeneralService {
    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http, private Control: ControlService) {
        super();
    }

    loadInbox(streamConversationsAux) {
        return this.http.post(this.Control.context_app + 'woopsocial/streams/conversations/', streamConversationsAux, { headers: this.headers, withCredentials: true })
            .map(this.extractData)
            .catch(this.handleError);
    }

    sendMessage(streamMessageAux) {
        return this.http.post(this.Control.context_app + 'woopsocial/streams/messages/send/', streamMessageAux, { headers: this.headers, withCredentials: true })
            .map(this.extractData)
            .catch(this.handleError);
    }

    loadMoreMessages(streamMessageAux) {
        return this.http.post(this.Control.context_app + 'woopsocial/streams/messages/', streamMessageAux, { headers: this.headers, withCredentials: true })
            .map(this.extractData)
            .catch(this.handleError);
    }
}
