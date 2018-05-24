import { Timezone } from './../../../modelo/timezone/Timezone';
import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import { Post } from 'modelo/post/Post';
import { PostAttachment } from 'modelo/postAttachment/PostAttachment';
import { Profile } from 'modelo/profile/Profile';
import { Observable } from 'rxjs/Observable';

import { ControlService } from './../control/control.service';

@Injectable()
export class PostService {

	private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http, private controlApp : ControlService) { 

    }

    persistPost(post : Post, listProfiles : Profile[], timezone : Timezone) : Observable<any> {

        var data : any = [];
        data.push(post);
        data.push(listProfiles);
        data.push(timezone);
        return this.http.put( this.controlApp.context_app + '/woopsocial/persist_post', data, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    getPostById(post : Post) : Observable<any> {

        /*var data : object = [];
		return this.http.post(this.controlApp.context_app + '/woopsocial/get_profiles/' + data, null, {headers: this.headers})
                    .map(this.extractData)
                    .catch(this.handleError);*/
		return this.http.post( this.controlApp.context_app + '/woopsocial/getPostById', JSON.stringify(post), {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    getThumbnail(postAttachment : PostAttachment) : Observable<any> {

        return this.http.put( this.controlApp.context_app + '/woopsocial/getThumbnail', postAttachment, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    deletePostAttachment(postAttachment : PostAttachment) : Observable<any> {

        return this.http.post( this.controlApp.context_app + '/woopsocial/deleteAttachment', postAttachment, {headers: this.headers, withCredentials: true})
                    .map(this.extractDataDeletePostAtt)
                    .catch(this.handleError);
    }

	private extractData(res: Response) : Profile[] {		
		if (res.status == 200) {
			return res.json();
		}
		return null;
	}

	private extractDataDeletePostAtt(res: Response) {		
		if (res.status == 200) {
			return true;
		}
		return null;
	}

    private handleError(error: any) {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }

    public callInstagramBusiness(profile : Profile) {

		return this.http.post( this.controlApp.context_app + '/api/instagram_business', JSON.stringify(profile), {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }
    
    public getEmojisList() : Observable<Object[]> {
            return this.http.get('assets/json/emojis.json')
                        .map((res: any) => {
                            var obj = res.json();

			                return obj;
                        })
                        .catch(this.handleError);
        /*if (!this.usuario) {
            return this.http.get(this._baseUrl + 'customers.json')
                        .map((res: Response) => {
                            this.usuario = res.json();
                            return this.usuario;
                        })
                        .catch(this.handleError);
        }
        else {
            //return cached data
            return this.createObservable(this.usuario);
        }*/
    }
}