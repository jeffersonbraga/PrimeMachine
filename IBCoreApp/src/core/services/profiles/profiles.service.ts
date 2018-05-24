import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/timeout';

import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import { Profile } from 'modelo/profile/Profile';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';
import { Subject } from 'rxjs/Subject';

import { ControlService } from './../control/control.service';

@Injectable()
export class ProfilesService {

    private headers = new Headers({'Content-Type': 'application/json'});

    public profilesSource = new Subject<Profile[]>();
    profiles$ = this.profilesSource.asObservable();

    public profilesToReconnectSource = new Subject<Profile[]>();
    profilesToReconnect$ = this.profilesToReconnectSource.asObservable();

    public profilesCheckedSource = new Subject<Profile[]>();
    profilesChecked$ = this.profilesCheckedSource.asObservable();

    public profilesCheckedInstagramSource = new Subject<Profile[]>();
    profilesCheckedInstagram$ = this.profilesCheckedInstagramSource.asObservable();

    private data: string;

    public checkedProfiles: Profile[] = [];

    public singleCheckedProfile: Profile;

    private selectedProfilesCrossNetwork: Profile[];

    public profilesToConnect: Profile[];

    public currentProfileToEdit: Profile;
    public currentProfileToDelete: Profile;
    public returnComponent: string;

    public profilesToReconnect: Profile[];
    // Profiles que não estão adicionados em grupos
    public profiles = [];

    _baseUrl = '';
    lockCheck = false;

    constructor(private http: Http, private controlApp: ControlService) {

        this.selectedProfilesCrossNetwork = [];
    }

    public resetSelectionList() : void {

        this.setSelectedProfilesCrossNetwork([]);
        this.checkedProfiles        = [];
        this.profilesToConnect      = [];
        // this.profilesToReconnect    = [];
    }

    getProfiles(): Observable<Profile[]> {

        return this.http.post(this.controlApp.context_app + 'woopsocial/get_profiles', null, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    getFacebookAuthURL(): Observable<any[]> {
        return this.http.get(this.controlApp.context_app + 'woopsocial/insertby_face/', {headers: this.headers, withCredentials: true})
                    .map(this.extractJson)
                    .catch(this.handleError);
    }

    getTwitterAuthURL(): Observable<any[]> {
        return this.http.get(this.controlApp.context_app + 'woopsocial/insertby_twitter/', {headers: this.headers, withCredentials: true})
                    .map(this.extractJson)
                    .catch(this.handleError);
    }

    getInstagramAuthURL(): Observable<any[]> {
        return this.http.get(this.controlApp.context_app + 'woopsocial/insert_instagram/', {headers: this.headers, withCredentials: true})
                    .map(this.extractJson)
                    .catch(this.handleError);
    }

    getLinkedInAuthURL(): Observable<any[]> {
        return this.http.get(this.controlApp.context_app + 'woopsocial/insert_linkedin/', {headers: this.headers, withCredentials: true})
                    .map(this.extractJson)
                    .catch(this.handleError);
    }

    getProfilesToConnect(): Observable<Profile[]> {
        return this.http.get(this.controlApp.context_app + 'woopsocial/get_profiles_to_connect/', {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    getProfilesToReconnect(): Observable<Profile[]> {
        return this.http.get(this.controlApp.context_app + 'woopsocial/get_profiles_to_reconnect/', {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    saveProfiles(profiles: Profile[]): Observable<any[]> {
        return this.http.post(this.controlApp.context_app + 'woopsocial/insert_profiles/', profiles, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    deleteProfile(profile: Profile): Observable<any> {
        return this.http.delete(this.controlApp.context_app + 'woopsocial/delete_profile?idProfile=' + profile.idProfile, {headers: this.headers, withCredentials: true})
                    .map(response => response.status === 200 ? null: this.handleError)
                    .catch(this.handleError);
    }

    insertInstagramSheduling(profile: Profile): Observable<Profile> {
        return this.http.post(this.controlApp.context_app + 'woopsocial/insert_instagram_scheduling/', profile, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    editInstagramSheduling(profile: Profile): Observable<Profile> {
        return this.http.post(this.controlApp.context_app + 'woopsocial/edit-instagram-scheduling/', profile, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    authenticateInstagramSheduling(profile: Profile): Observable<Profile> {
        return this.http.post(this.controlApp.context_app + 'woopsocial/authenticate-instagram-scheduling/', profile, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    getLinkAuthFacebook(profile: Profile) {
        return this.http.put(this.controlApp.context_app + 'woopsocial/refresh_tokenby_face/', profile, {headers: this.headers, withCredentials: true})
            .map(response => {
                return response['_body'];
            })
            .catch(this.handleError);
    }

    authFacebookCallbak() {
         return this.http.get(this.controlApp.context_app + 'woopsocial/refresh_tokenby_face_contents/', {headers: this.headers, withCredentials: true})
            .map(this.extractData)
            .catch(this.handleError);
    }

    getLinkAuthTwitter(profile: Profile) {
        return this.http.put(this.controlApp.context_app + 'woopsocial/refresh_tokenby_twitter/', profile, {headers: this.headers, withCredentials: true})
            .map(response => {
                return response['_body'];
            })
            .catch(this.handleError);
    }

    authTwitterCallbak() {
         return this.http.get(this.controlApp.context_app + 'woopsocial/refresh_tokenby_twitter_contents/', {headers: this.headers, withCredentials: true})
            .map(this.extractData)
            .catch(this.handleError);
    }

    getLinkAuthInstagram(profile: Profile) {
        return this.http.put(this.controlApp.context_app + 'woopsocial/refresh_tokenby_instagram/', profile, {headers: this.headers, withCredentials: true})
            .map(response => {
                return response['_body'];
            })
            .catch(this.handleError);
    }

    authInstagramCallbak() {
         return this.http.get(this.controlApp.context_app + 'woopsocial/refresh_tokenby_instagram_contents/', {headers: this.headers, withCredentials: true})
            .map(this.extractData)
            .catch(this.handleError);
    }

    getLinkAuthLinkedin(profile: Profile) {
        return this.http.put(this.controlApp.context_app + 'woopsocial/refresh_tokenby_linkedin/', profile, {headers: this.headers, withCredentials: true})
            .map(response => {
                return response['_body'];
            })
            .catch(this.handleError);
    }

    authLinkedinCallbak() {
         return this.http.get(this.controlApp.context_app + 'woopsocial/refresh_tokenby_linkedin_contents/', {headers: this.headers, withCredentials: true})
            .map(this.extractData)
            .catch(this.handleError);
    }

    validateStatusInsertProfile(profiles: Profile[]) {
         return this.http.post(this.controlApp.context_app + 'woopsocial/validate_insert_profiles/', profiles, {headers: this.headers, withCredentials: true})
            .map(this.extractData)
            .catch(this.handleError);
    }

    updateTimezone(timzone: string) {
        return this.http.put(this.controlApp.context_app + 'woopsocial/updatetimezone/', timzone, {headers: this.headers, withCredentials: true})
            .map(response => {
                return true;
            })
            .catch(this.handleErrorTimezone);
    }

    private extractData(res: Response): Profile[] {
        if (res.status === 200) {
            return res.json();
        }
        return null;
    }

    private extractJson(res: Response): any[] {
        const body = res.json();
        return body;
    }

    private createObservable(data: any): Observable<any> {
        return Observable.create((observer: Observer<any>) => {
            observer.next(data);
            observer.complete();
        });
    }

    private handleError(error: any) {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }

    private handleErrorTimezone(error: any) {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }

    setCheckedProfiles(profiles) {
        this.profilesSource.next(profiles);
    }

    getSelectedProfilesCrossNetwork(): Profile[] {
        return this.selectedProfilesCrossNetwork;
    }

    setSelectedProfilesCrossNetwork(profiles: Profile[]) {
        this.selectedProfilesCrossNetwork = profiles;
    }

    addProfilesToSouce(profile: Profile) {
        this.profiles.push(profile);
        this.profilesSource.next(this.profiles);
    }

    removeProfileFromSource(profile: Profile) {
        this.profiles.filter(
            (p: Profile, i: number) => {
                if (p.idProfile === profile.idProfile) {
                    this.profiles.splice(i, 1);
                }
            }
        );

        if (this.profilesToReconnect !== undefined) {
            this.profilesToReconnect.filter(
                (p: Profile, i: number) => {
                    if (p.idProfile === profile.idProfile) {
                        this.profilesToReconnect.splice(i, 1);
                    }
                }
            );
        }

        this.profilesSource.next(this.profiles);
    }

    addCheckedProfile(profile: Profile) {
        if (!this.lockCheck) {
            this.checkedProfiles.push(profile);
            this.profilesCheckedSource.next(this.checkedProfiles);
        }
    }

    removeCheckedProfile(profile: Profile) {
        if (!this.lockCheck) {
            this.checkedProfiles.filter(
                (p: Profile, i: number) => {
                    if (p.idProfile === profile.idProfile) {
                        this.checkedProfiles.splice(i, 1);
                    }
                }
            );
            this.profilesCheckedSource.next(this.checkedProfiles);
        }
    }

    initCheckedProfiles() {
        this.checkedProfiles = [];
        this.profilesCheckedSource.next(this.checkedProfiles);
    }

    updateProfileScheduling(profile: Profile) {
        this.profilesToReconnect.filter(
            p => {
                if (p.idProfile === profile.idProfile) {
                    p.isSchedulingAuthenticated = 'T';
                }
            }
        );

        this.profiles.filter(
            p => {
                if (p.idProfile === profile.idProfile) {
                    p.isSchedulingAuthenticated = 'T';
                }
            }
        );
    };
}
