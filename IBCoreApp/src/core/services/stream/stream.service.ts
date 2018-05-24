import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { Profile } from 'modelo/profile/Profile';
import { Stream } from 'modelo/stream/Stream';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';

import { ControlService } from './../control/control.service';
import { GeneralService } from './../general.service';


@Injectable()
export class StreamService extends GeneralService {
    private headers = new Headers({ 'Content-Type': 'application/json' });

    public streamSource = new Subject<Stream[]>();
    streams$ = this.streamSource.asObservable();
    stream: Stream[];

    constructor(private http: Http, private Control: ControlService) {
        super();
    }

    getStreams(): Observable<Stream[]> {
        return this.http.get(this.Control.context_app + 'woopsocial/get_streams', { headers: this.headers, withCredentials: true })
            .map(response => {
                return super.extractData(response);
            })
            .catch(super.handleError);
    }

    addStreamsByProfile(profiles: Profile[]) {
        return this.http.put(this.Control.context_app + 'woopsocial/add_streams_by_profile', profiles, { headers: this.headers, withCredentials: true })
            .map(response => {
                return super.extractData(response);
            })
            .catch(super.handleError);
    }

    reorderStreams(streams: any[]) {
        return this.http.put(this.Control.context_app + 'woopsocial/reorder_streams', streams, { headers: this.headers, withCredentials: true })
            .map(response => {
                return super.extractData(response);
            })
            .catch(super.handleError);
    }

    deleteStream(stream: any) {
        return this.http.delete(this.Control.context_app + 'woopsocial/delete_stream?idStream=' + stream.idStream, { headers: this.headers, withCredentials: true })
            .map(response => {
                return super.extractData(response);
            })
            .catch(super.handleError);
    }

    deletePost(actionDeleteAux: any) {
        return this.http.post(this.Control.context_app + 'woopsocial/streams/posts/delete/', actionDeleteAux, { headers: this.headers, withCredentials: true })
            .map(response => {
                return response
            })
            .catch(super.handleError);
    }

    deleteComment(actionDeleteAux: any) {
        return this.http.post(this.Control.context_app + 'woopsocial/streams/comments/delete', actionDeleteAux, { headers: this.headers, withCredentials: true })
            .map(response => {
                return response
            })
            .catch(super.handleError);
    }

    addStreamToSource(stream: Stream) {
        this.stream.push(stream);
        this.streamSource.next(this.stream);
    }

    removeFromSource(stream: Stream) {
        this.stream.filter(
            (s, i) => {
                if (s.idStream === stream.idStream) {
                    this.stream.splice(i, 1);
                }
            }
        );

        this.streamSource.next(this.stream);
    }

    getMyPosts(streamPostsAux: any, tab: string): Observable<any> {
        let url;

        if (tab === 'P') {
            url = 'myposts';
        } else if (tab === 'T') {
            url = 'timeline';
        } else url = 'mentions';

        return this.http.post(this.Control.context_app + 'woopsocial/streams/' + url + '/', streamPostsAux, { headers: this.headers, withCredentials: true })
            .map(this.extractData)
            .catch(this.handleError);
    }

    getComments(streamPostCommentsAux): Observable<any> {
        return this.http.post(this.Control.context_app + 'woopsocial/streams/comments/', streamPostCommentsAux, { headers: this.headers, withCredentials: true })
            .map(this.extractData)
            .catch(this.handleError);
    }

    getReplies(streamPostCommentRepliesAux): Observable<any> {
        return this.http.post(this.Control.context_app + 'woopsocial/streams/comments/replies/', streamPostCommentRepliesAux, { headers: this.headers, withCredentials: true })
            .map(this.extractData)
            .catch(this.handleError);
    }

    likePost(streamPostsAux: any) {
        return this.http.post(this.Control.context_app + 'woopsocial/streams/posts/like/', streamPostsAux, { headers: this.headers, withCredentials: true })
            .map(response => { return response })
            .catch(this.handleError);
    }

    likeCommentAndReply(actionLikeAux){
        return this.http.post(this.Control.context_app + 'woopsocial/streams/comments/like/', actionLikeAux, { headers: this.headers, withCredentials: true })
            .map(response => { return response })
            .catch(this.handleError);
    }

    commentPost(actionCommentAux) {
        return this.http.post(this.Control.context_app + 'woopsocial/streams/posts/comment/', actionCommentAux, { headers: this.headers, withCredentials: true })
            .map(this.extractData)
            .catch(this.handleError);
    }

    editPost(actionShareAux) {
        return this.http.post(this.Control.context_app + 'woopsocial/streams/posts/edit/', actionShareAux, { headers: this.headers, withCredentials: true })
            .map(result => result)
            .catch(this.handleError);
    }

    replyPost(actionCommentAux) {
        return this.http.post(this.Control.context_app + 'woopsocial/streams/comments/reply/', actionCommentAux, { headers: this.headers, withCredentials: true })
            .map(this.extractData)
            .catch(this.handleError);
    }

    sharePost(actionShareAux) {
        return this.http.post(this.Control.context_app + 'woopsocial/streams/posts/share/', actionShareAux, { headers: this.headers, withCredentials: true })
            .map(response => {
                return response
            })
            .catch(this.handleError);
    }
}
