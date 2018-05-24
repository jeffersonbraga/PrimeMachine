import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import { Group } from 'modelo/group/Group';
import { Profile } from 'modelo/profile/Profile';
import { RssFeed } from 'modelo/rssFeed/RssFeed';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';
import { Subject } from 'rxjs/Subject';

import { ControlService } from './../control/control.service';

@Injectable()
export class GroupsService {

    private headers = new Headers({'Content-Type': 'application/json'});

    public rssGroupsSource = new Subject<Group[]>();
    public rssfeedSource = new Subject<RssFeed[]>();
    public currentGroupSource = new Subject<Group>();
    public currentCheckAll = new Subject<Group>();
    public loadingSource = new Subject<boolean>();
    public waitingSource = new Subject<boolean>();

    groupsRss$ = this.rssGroupsSource.asObservable();
    group$ = this.currentGroupSource.asObservable();
    rssfeed$ = this.rssfeedSource.asObservable();
    checkAll$ = this.currentCheckAll.asObservable();
    loading$ = this.loadingSource.asObservable();
    waiting$ = this.waitingSource.asObservable();

    rssFeeds : RssFeed[] = [];
    editingGroupId: number;
    editingGroupIds: number[] = [];

    private data : string;

    public currentIndex : number;
    public currentRssfeed : RssFeed;
    
    public groupType : any;
    public fromSettingsGroup: boolean;

    public checkAllBtnSelected: boolean = false;

    _baseUrl: string = '';

    public currentGroups: Group[] = [];
    currentGroup: Group;
    
    public currentGroupToDelete : Group;
    public returnComponent: string;

    constructor(private http: Http, private controlApp : ControlService) { 

    }

    addGroup(group : Group) {
        this.currentGroups.push(group);
        this.currentGroups.sort(function(a,b) {return (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0);} );
        this.rssGroupsSource.next(this.currentGroups);
    }

    getRssFeedMessages(rssFeed : RssFeed) {
        return this.http.get(this.controlApp.context_app + 'woopsocial/get_rssfeedmsgs/?idRssfeed=' + rssFeed.idRssfeed, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    getAllRssFeedsFromGroupByDate(group : Group) {
        return this.http.post(this.controlApp.context_app + 'woopsocial/all_rssfeeds/?idGroup=' + group.idGroup, null, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    deleteRssFeed(rssFeed : RssFeed, group : Group) {
        return this.http.delete(this.controlApp.context_app + 'woopsocial/rssfeed_delete/?idRssfeed=' + rssFeed.idRssfeed + '&idGroup=' + group.idGroup, {headers: this.headers, withCredentials: true})
            .map(this.extractData)
            .catch(this.handleError);
    }

    addRssFeedOnSource(rssFeed : RssFeed) {
        this.rssFeeds = [];
        this.rssFeeds.push(rssFeed);
        this.rssfeedSource.next(this.rssFeeds);
    }

    addAllRssFeedOnSource(rssFeeds : RssFeed[]) {
        this.rssFeeds = [];
        this.rssFeeds = rssFeeds;
        this.rssfeedSource.next(this.rssFeeds);
    }

    removeRssFeedFromSource(rssFeed : RssFeed) {

        if (!this.checkAllBtnSelected) {
            this.rssFeeds.filter((r, index) => {
                if (r.idRssfeed == rssFeed.idRssfeed) {
                    this.rssFeeds.splice(index, 1);
                }
            });
        }

        this.rssfeedSource.next(this.rssFeeds);
    }

    removeRssFromSource(group: Group){
        this.currentGroups.filter(
            (g: Group, i: number) =>{
                if (g.idGroup == group.idGroup){
                    this.currentGroups.splice(i, 1);
                }
            }
        );

        this.rssGroupsSource.next(this.currentGroups);
    }

    addRssFeedOnGroup(group : Group, rssFeed : RssFeed) : Observable<any> {
        var data = {
            rssFeed: rssFeed,
            group: group
        }
        return this.http.put(this.controlApp.context_app + 'woopsocial/rssfeed_insert', data, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleRssFeedError);
    }
    
    getRssFeedGroups() : Observable<Group[]> {
        return this.http.post(this.controlApp.context_app + 'woopsocial/groups_feeds/list', null, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }
    
    getGroups() : Observable<Group[]> {
        return this.http.post(this.controlApp.context_app + 'woopsocial/groups_profiles/list', null, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    persistGroup(group : Group) : Observable<any> {
        return this.http.put( this.controlApp.context_app + 'woopsocial/groups/insert', group, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    deleteGroup(group : Group){
        return this.http.delete(this.controlApp.context_app + 'woopsocial/groups_delete/?idGroup=' + group.idGroup, {headers: this.headers, withCredentials: true})
            .map(this.extractData)
            .catch(this.handleError);
    }

    updateGroup(profiles : Profile[], group : Group) : Observable<any> {
        var data = {
            profiles: profiles,
            group: group
        }
        return this.http.put( this.controlApp.context_app + 'woopsocial/groups_update', data, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    updateRssGroupSource() {
        this.rssGroupsSource.next(this.currentGroups);
    }

    private extractData(res: Response) : Group[] {		
        if (res.status == 200) {
            return res.json();
        }
        return null;
    }
    
    private createObservable(data: any) : Observable<any> {
        return Observable.create((observer: Observer<any>) => {
            observer.next(data);
            observer.complete();
        });
    }
    
    private handleError(error: any) {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }
    
    private handleRssFeedError(error: any) {
        if (error.status == 400 || error.status == 403) {
            return error.status;
        }
    }
}
