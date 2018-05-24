import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import { ControlService } from './../control/control.service';
import { GeneralService } from './../general.service';


@Injectable()
export class FollowersService extends GeneralService {
    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http, private Control: ControlService) {
        super();
    }
    
    getFollowers(streamFollowersAux){
        return this.http.post(this.Control.context_app + 'woopsocial/streams/followers/', streamFollowersAux, { headers: this.headers, withCredentials: true })
            .map(this.extractData)
            .catch(this.handleError);
    }
    
    follow(streamFollower){
        return this.http.post(this.Control.context_app + 'woopsocial/streams/followers/follow/', streamFollower, { headers: this.headers, withCredentials: true })
            .map(this.extractData)
            .catch(this.handleError);
    }
    
    unfollow(streamFollower){
        return this.http.post(this.Control.context_app + 'woopsocial/streams/followers/unfollow/', streamFollower, { headers: this.headers, withCredentials: true })
            .map(this.extractData)
            .catch(this.handleError);
    }
}