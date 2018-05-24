import { Component, Injector, OnInit } from '@angular/core';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { Profile } from 'modelo/profile/Profile';
import { Observable } from 'rxjs/Observable';
import { Subscription } from 'rxjs/subscription';

declare const $;

@Component({
    selector: 'profile-reconnect',
    templateUrl: './profile-reconnect.component.html',
    styleUrls: ['./profile-reconnect.component.css']
})

export class ProfileReconnectComponent implements OnInit {
    currentModal: GenericModalComponent;

    windowFacebookAuth: any;
    subscriptionFacebook: Subscription;
    facebookCurrentIndex: number;

    windowTwitterAuth: any;
    subscriptionTwitter: Subscription;
    twitterCurrentIndex: number;

    windowInstagramAuth: any;
    subscriptionInstagram: Subscription;
    instagramCurrentIndex: number;
    isAuthenticating = false;

    windowLinkedinAuth: any;
    subscriptionLinkedin: Subscription;
    linkedinCurrentIndex: number;

    profilesToReconnect: Profile[];

    timer: Observable<number>;

    errorMessage: String;
    hasError: boolean;

    constructor(
        private BreadcrumbsService: BreadcrumbsService,
        private ProfilesService: ProfilesService,
        private injector: Injector
    ) {
        this.BreadcrumbsService.initializeBreadcrumb();
        this.currentModal = this.injector.get('currentModal');
    }

    ngOnInit() {
        this.BreadcrumbsService.addBreadcrumb('Reconectar Perfis', '#');

        this.profilesToReconnect = this.ProfilesService.profilesToReconnect;
    }

    testForPoupup(poupup: Window): boolean {
        if (poupup) {
            return true;
        } else {
            this.showErrorPopup();
            return false;
        }
    }

    reconnect(profile: Profile, index: number) {
        if (profile.isTokenValid === 'F') {
            switch (profile.type) {
                case 'P':
                    this.reconnectFacebook(profile, index);
                    break;
                case 'F':
                    this.reconnectFacebook(profile, index);
                    break;
                case 'I':
                    this.reconnectInstagram(profile, index);
                    break;
                case 'T':
                    this.reconnectTwitter(profile, index);
                    break;
                case 'L':
                    this.reconnectLinkedin(profile, index);
                    break;
            }
        }
    }

    reconnectFacebook(profile: Profile, index: number) {
        this.windowFacebookAuth = window.open('', 'FacebookReconnection', '_blank');
        if (!this.facebookCurrentIndex && this.facebookCurrentIndex !== 0) {
            this.facebookCurrentIndex = index;
            this.ProfilesService.getLinkAuthFacebook(profile)
                .subscribe(
                    url => this.waitForCallbackFacebook(url)
                );
        }
    }

    waitForCallbackFacebook(url) {
        this.windowFacebookAuth.location = url;

        if (this.testForPoupup(this.windowFacebookAuth)) {
            // start a timer after one second
            this.timer = Observable.timer(1000, 1000);
            this.subscriptionFacebook = this.timer.subscribe((t: any) => {
                this.ProfilesService.authFacebookCallbak()
                    .subscribe(
                        result => {
                            this.updateProfileInList(result);
                    },
                    error => console.log(error)
                    );
            });
        } else {
            this.facebookCurrentIndex = null;
        }
    }

    reconnectTwitter(profile: Profile, index: number) {
        this.windowTwitterAuth = window.open('', 'TwitterReconnection', '_blank');
        if (!this.twitterCurrentIndex && this.twitterCurrentIndex !== 0) {
            this.twitterCurrentIndex = index;
            this.ProfilesService.getLinkAuthTwitter(profile)
                .subscribe(
                    url => this.waitForCallbackTwitter(url)
                );
        }
    }

    waitForCallbackTwitter(url) {
        this.windowTwitterAuth.location = url;

        if (this.testForPoupup(this.windowTwitterAuth)) {
            // start a timer after one second
            this.timer = Observable.timer(1000, 1000);
            this.subscriptionTwitter = this.timer.subscribe((t: any) => {
                this.ProfilesService.authTwitterCallbak()
                    .subscribe(
                        result => {
                            this.updateProfileInList(result);
                    },
                    error => console.log(error)
                    );
            });
        } else {
            this.twitterCurrentIndex = null;
        }
    }

    reconnectInstagram(profile: Profile, index: number) {
        this.windowInstagramAuth = window.open('', 'InstagramReconnection', '_blank');
        if (!this.instagramCurrentIndex && this.instagramCurrentIndex !== 0) {
            this.instagramCurrentIndex = index;
            this.ProfilesService.getLinkAuthInstagram(profile)
                .subscribe(
                    url => this.waitForCallbackInstagram(url)
                );
        }
    }

    waitForCallbackInstagram(url) {
        this.windowInstagramAuth.location = url;

        if (this.testForPoupup(this.windowInstagramAuth)) {
            // start a timer after one second
            this.timer = Observable.timer(1000, 1000);
            this.subscriptionInstagram = this.timer.subscribe((t: any) => {
                this.ProfilesService.authInstagramCallbak()
                    .subscribe(
                        result => {
                            this.updateProfileInList(result);
                    },
                    error => console.log(error)
                    );
            });
        } else {
            this.instagramCurrentIndex = null;
        }
    }

    reconnectLinkedin(profile: Profile, index: number) {
        this.windowLinkedinAuth = window.open('', 'LinkedinReconnection', '_blank');
        if (!this.linkedinCurrentIndex && this.linkedinCurrentIndex !== 0) {
            this.linkedinCurrentIndex = index;
            this.ProfilesService.getLinkAuthLinkedin(profile)
                .subscribe(
                    url => this.waitForCallbackLinkedin(url)
                );
        }
    }

    waitForCallbackLinkedin(url) {
        this.windowLinkedinAuth.location = url;

        if (this.testForPoupup(this.windowLinkedinAuth)) {
            // start a timer after one second
            this.timer = Observable.timer(1000, 1000);
            this.subscriptionLinkedin = this.timer.subscribe((t: any) => {
                this.ProfilesService.authLinkedinCallbak()
                    .subscribe(
                        result => {
                            this.updateProfileInList(result);
                    },
                    error => console.log(error)
                    );
            });
        } else {
            this.linkedinCurrentIndex = null;
        }
    }

    updateProfileInList(result) {
        if (result.length) {
            if (result[0]) {
                this.hasError = true;
                this.errorMessage = result[0];
                this.finalizeAuth(result[1]);
            } else {
                const profileToUpdate: Profile = result[1];
                this.finalizeAuth(profileToUpdate);
            }
        }
    }

    finalizeAuth(profile: Profile) {
        if (profile.type === 'P' || profile.type === 'F') {
            this.profilesToReconnect[this.facebookCurrentIndex] = profile;
            this.facebookCurrentIndex = null;
            this.subscriptionFacebook.unsubscribe();
            this.windowFacebookAuth.close();
        } else if (profile.type === 'T') {
            this.profilesToReconnect[this.twitterCurrentIndex] = profile;
            this.twitterCurrentIndex = null;
            this.subscriptionTwitter.unsubscribe();
            this.windowTwitterAuth.close();
        } else if (profile.type === 'I') {
            this.profilesToReconnect[this.instagramCurrentIndex] = profile;
            this.instagramCurrentIndex = null;
            this.subscriptionInstagram.unsubscribe();
            this.windowInstagramAuth.close();
        } else if (profile.type === 'L') {
            this.profilesToReconnect[this.linkedinCurrentIndex] = profile;
            this.linkedinCurrentIndex = null;
            this.subscriptionLinkedin.unsubscribe();
            this.windowLinkedinAuth.close();
        }

        this.ProfilesService.profilesToReconnectSource.next(this.profilesToReconnect);
    }

    deleteProfile(profile: Profile, index: number) {
        this.ProfilesService.currentProfileToDelete = profile;
        this.ProfilesService.returnComponent = 'ProfileReconnectComponent';
        this.currentModal.setDynamicModalContent('DeleteChannelComponent');
    }

    showErrorPopup() {
        this.hasError = true;
        this.errorMessage = 'Unblock our pop-ups so we can connect to your social networks';
        // this.errorMessage = 'Desbloqueie os nossos pop-ups para conseguirmos fazer login nas redes sociais';
    }

    hideErrorMessage() {
        this.hasError = false;
        this.errorMessage = '';
    }
}
