import { HomeComponent } from 'views/home/home.component';
import 'rxjs/Rx';

import { ChangeDetectorRef, Component, Injector, OnDestroy, OnInit, ViewChild, forwardRef, Inject } from '@angular/core';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { AlertService } from 'core/services/alert/alert.service';
import { ControlService } from 'core/services/control/control.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { TimezoneService } from 'core/services/timezone/timezone.service';
import { UserService } from 'core/services/user/user.service';
import { WindowRefService } from 'core/services/window/window-ref.service';
import { Profile } from 'modelo/profile/Profile';
import { Observable } from 'rxjs/Observable';

declare var $;
@Component({
    selector: 'hello-woop',
    templateUrl: './hello-woop.component.html',
    styleUrls: ['./hello-woop.component.css']
})
export class HelloWoopComponent implements OnInit, OnDestroy {

    profiles: any = [];
    profilesToConnect: Profile[] = [];
    profilesInstagramToConnect: Profile[] = [];
    selectedProfiles: Profile[] = [];

    nativeWindow: any;
    facebookAuthWindow: any;
    twitterAuthWindow: any;
    linkedInAuthWindow: any;
    instagramAuthWindow: any;
    showSchedulePost: any;

    currentModal: GenericModalComponent;

    timerSubscription: any;

    timezones: any = [];

    @ViewChild('containerError')
    public my_timezone: any;

    @ViewChild('instachannels')
    public instachannels: any;

    @ViewChild('facechannels')
    public facechannels: any;

    timezone: string;

    instagramSelected = false;

    facebookSelected = false;

    twitterSelected = false;

    hasError = false;

    hasErrorTimezone = false;

    firstMessage = true;

    errorMessage = '';

    userName = '';

    canSave = true;

    tutorialComponentClassName = 'TutorialComponent';

    constructor(
        private profileService: ProfilesService,
        private controlApp: ControlService,
        private winRef: WindowRefService,
        private injector: Injector,
        private TimezoneService: TimezoneService,
        private ref: ChangeDetectorRef,
        private alertService: AlertService,
        private userService: UserService,
        @Inject(forwardRef(() => HomeComponent)) private _parent: HomeComponent
    ) {
        this.profiles = [];
        this.selectedProfiles = profileService.getSelectedProfilesCrossNetwork();
        this.nativeWindow = winRef.getNativeWindow();
        this.currentModal = this.injector.get('currentModal');
        this.userName = this.controlApp.getUser().name;
    }

    ngOnInit() {
        this.getAccountInfo();
        this.getTimezones();
        this.profileService.getProfiles()
            .subscribe(
                result => this.onGetProfiles(result),
                error => console.log(error));
        this.getTimezones();
    }

    ngOnDestroy() {
        if (this.timerSubscription) {
            this.timerSubscription.unsubscribe();
        }
    }

    onGetProfiles(result: any) {
        if (!result) {
            console.log('Houve um erro ao recuperar canais. ');
        } else {
            this.profiles = result;
        }
    }

    getAccountInfo(event?: MouseEvent) {
        /*
        if (event) {
            event.stopImmediatePropagation();
            event.stopPropagation();
        }
        this.profileService.validateStatusInsertProfile(this.selectedProfiles)
            .subscribe(
                result => this.validationSuccess(result),
                error => this.validationError(error)
            );*/
        if (this.hasError) {
            this.hideErrorMessage();
        }
    }

    getTimezones() {
        this.TimezoneService.getTimezone()
            .subscribe(
                result => this.setTimezones(result),
                error => console.log(error)
            );
    }

    setTimezones(result: any) {
        if (!result) {
            console.log('Não foi possível recuperar fuso horários.');
        } else {
            this.timezones = result[0];
            this.ref.detectChanges();
            $('#timezone').select2({
                dropdownParent: $('#hello-woop-modal')
            });
            $('#timezone').on(
                'change',
                (e: any) => this.timezone = e.target.value
            );
        }
    }

    openFacebookAuthPage(): void {
        this.facebookAuthWindow = this.nativeWindow.open('', '_blank');
        this.profileService.getFacebookAuthURL().subscribe(
            result => {

                this.facebookAuthWindow.location = result['authenticationUrl'];

                if (this.facebookAuthWindow == null || typeof (this.facebookAuthWindow) === 'undefined') {
                    this.showErrorPopup();
                } else {
                    // start a timer after one second
                    const timer = Observable.timer(1000, 1000);
                    this.timerSubscription = timer.subscribe((t: any) => {
                        this.profileService.authFacebookCallbak().subscribe(
                            response => {
                                this.onGetProfilesToConnect(response, 'F');
                            },
                            error => console.log(error)
                        );
                    });
                }
            },
            error => console.log(error)
        );
    }

    openTwitterAuthPage() {
        this.twitterAuthWindow = this.nativeWindow.open('', '_blank');
        this.profileService.getTwitterAuthURL().subscribe(
            result => {

                this.twitterAuthWindow.location = result['authenticationUrl'];

                if (this.twitterAuthWindow == null || typeof (this.twitterAuthWindow) === 'undefined') {
                    this.showErrorPopup();
                } else {
                    // start a timer after one second
                    const timer = Observable.timer(1000, 1000);
                    this.timerSubscription = timer.subscribe((t: any) => {
                        this.profileService.authTwitterCallbak().subscribe(
                            response => {
                                this.onGetProfilesToConnect(response, 'T');
                            },
                            error => console.log(error)
                        );
                    });
                }
            },
            error => console.log(error)
        );
    }

    openLinkedInAuthPage() {
        this.linkedInAuthWindow = this.nativeWindow.open('', '_blank');
        this.profileService.getLinkedInAuthURL().subscribe(
            result => {
                this.linkedInAuthWindow.location = result['authenticationUrl'];

                if (this.linkedInAuthWindow == null || typeof (this.linkedInAuthWindow) === 'undefined') {
                    this.showErrorPopup();
                } else {
                    // start a timer after one second
                    const timer = Observable.timer(1000, 1000);
                    this.timerSubscription = timer.subscribe((t: any) => {
                        this.profileService.authLinkedinCallbak().subscribe(
                            response => {
                                this.onGetProfilesToConnect(response, 'L');
                            },
                            error => console.log(error)
                        );
                    });
                }
            },
            error => console.log(error)
        );
    }

    onGetProfilesToConnect(result: any, networkType: string) {

        if (result.length) {
            if (result[0]) {
                this.hasError = true;
                this.errorMessage = result[0];
                this.profilesToConnect = result[1];
                this.finalizeAuth(networkType);
            } else {

                if (networkType === 'T') {
                    const objResult: Profile = result[1][0];
                    this.selectedProfiles.push(objResult);
                } else {
                    this.profilesToConnect = result[1];
                }

                this.finalizeAuth(networkType);
            }
        }
    }

    finalizeAuth(networkType: String) {

        this.timerSubscription.unsubscribe();

        this.facebookSelected = false;
        this.instagramSelected = false;
        this.twitterSelected = false;

        if (networkType === 'F') {

            this.facebookSelected = true;
            this.instagramSelected = false;
            this.twitterSelected = false;

            this.profileService.profilesToConnect = this.profilesToConnect;
            this.facebookAuthWindow.close();
            // this.currentModal.setDynamicModalContent("AddFacebookChannellsComponent");
            // this.breadcrumbsService.addBreadcrumb("Adicionar Canais do Facebook", "");
        } else if (networkType === 'T') {

            this.facebookSelected = false;
            this.instagramSelected = false;
            this.twitterSelected = true;

            this.twitterAuthWindow.close();
            // this.addTwitterChannel(this.profilesToConnect);
            this.profileService.addProfilesToSouce(this.profilesToConnect[0]);
        } else if (networkType === 'L' || networkType === 'H') {

            this.facebookSelected = true;
            this.instagramSelected = false;
            this.twitterSelected = false;

            this.profileService.profilesToConnect = this.profilesToConnect;
            this.linkedInAuthWindow.close();
            // this.currentModal.setDynamicModalContent("AddFacebookChannellsComponent");
            // this.breadcrumbsService.addBreadcrumb("Adicionar Canais do LinkedIn", "");
        } else if (networkType === 'I') {

            this.facebookSelected = true;
            this.instagramSelected = true;
            this.twitterSelected = false;

            this.instagramAuthWindow.close();
            this.profileService.profilesToConnect = this.profilesToConnect;
            if (this.instachannels) {
                this.instachannels.profilesToConnect = this.profileService.getSelectedProfilesCrossNetwork();
            }
            // this.currentModal.setDynamicModalContent("AddFacebookChannellsComponent");
            // this.breadcrumbsService.addBreadcrumb("Adicionar Canais do LinkedIn", "");
        }

        this.getAccountInfo(null);
    }

    addTwitterChannel(profiles: Profile[]) {

        this.facebookSelected = false;
        this.instagramSelected = false;
        this.twitterSelected = false;

        this.profileService.saveProfiles(profiles).subscribe(
            result => this.onAddTwitterChannel(result),
            error => error
        );
    }

    addFacebookChannel() {

        this.facebookSelected = false;
        this.instagramSelected = false;
        this.twitterSelected = false;

        this.openFacebookAuthPage();
    }

    addLinkedInChannel(profiles?: Profile[]) {

        this.facebookSelected = false;
        this.instagramSelected = false;
        this.twitterSelected = false;

        this.openLinkedInAuthPage();
    }

    onAddTwitterChannel(result: any) {
        this.currentModal.setDynamicModalContent('HelloWoopComponent');
    }

    onAddLinkedInChannel(result: any) {
        this.currentModal.setDynamicModalContent('HelloWoopComponent');
    }

    addInstagramChannelComponent() {
        this.facebookSelected = false;
        this.instagramSelected = false;
        this.twitterSelected = false;
        this.openInstagramAuthPage();
    }

    editInstagramChannel(event) {
        this.profileService.currentProfileToEdit = event;
        this.currentModal.setDynamicModalContent('AddInstagramChannelComponent');
    }

    deleteChannel(event: Profile) {
        this.profileService.getSelectedProfilesCrossNetwork()
            .splice(this.profileService.getSelectedProfilesCrossNetwork().indexOf(event), 1);
        // this.profileService.profilesToConnect.splice(this.profileService.profilesToConnect.indexOf(event), 1);

        if (this.facechannels) {
            this.facechannels.addRemoveObjectChecked(event);
        }

        if (this.instachannels) {
            this.instachannels.hideErrorMessage();
        }

        this.getAccountInfo(null);
    }

    saveProfiles() {

        if (this.selectedProfiles && this.selectedProfiles.length > 0 && this.timezone) {

            this.canSave = false;
            this.profileService.saveProfiles(this.selectedProfiles).subscribe(
                result => this.updateTimeZone(),
                error => this.validationError(error));
        } else {

            if (!this.timezone) {
                this.hasErrorTimezone = true;
            } else {
                this.hasErrorTimezone = false;
            }

            if (!this.selectedProfiles) {
                this.validationError('Select at least one profile to connect and start your experience.');
            } else if (!this.selectedProfiles.length) {
                this.validationError('Select at least one profile to connect and start your experience.');
            }
        }
    }

    updateTimeZone() {
        // Do not set a listner
        this.profileService.updateTimezone(this.timezone).subscribe(
            result => this.closeHelloWoop(),
            error => this.validationError(error));
        ;
    }

    closeHelloWoop() {
        this.canSave = true;
        this.currentModal.hideModal();

        if (this.controlApp.mobileAndTabletcheck()) {
            window.location.reload();
        } else {
            if (this.controlApp.getUser().tutorialShowed === 'F') {
                    this._parent.showTutorialModal();
            }
        }

        // for (let i = 0; i < this.selectedProfiles.length; i++) {
        //     this.profileService.addProfilesToSouce(this.selectedProfiles[i]);
        // }
    }

    validationError(arg0: any): any {
        if (this.facechannels) {
            this.facechannels.showErrorMessage(arg0);
        }

        if (this.instachannels) {
            this.instachannels.showErrorMessage(arg0);
        }

        if (!this.instachannels && !this.facechannels) {
            this.hasError = true;
            this.errorMessage = arg0;
        }

        this.canSave = true;
    }

    showErrorPopup() {
        this.hasError = true;
        this.errorMessage = 'Unblock our pop-ups so we can connect to your social networks';
        // this.errorMessage = "Desbloqueie os nossos pop-ups para conseguirmos fazer login nas redes sociais";
    }

    validationSuccess(arg0: any): any {
        if (this.facechannels) {
            this.facechannels.hideErrorMessage();
        }

        if (this.instachannels) {
            this.instachannels.hideErrorMessage();
        }
    }

    hideErrorMessage() {
        this.hasError = false;
        this.firstMessage = false;
        this.errorMessage = '';
    }

    hideErrorTimezoneMessage() {
        this.hasErrorTimezone = false;
    }

    openInstagramAuthPage(): void {
        this.instagramAuthWindow = this.nativeWindow.open('', '_blank');
        this.profileService.getInstagramAuthURL().subscribe(
            result => {
                this.instagramAuthWindow.location = result['authenticationUrl'];

                if (this.instagramAuthWindow == null || typeof (this.instagramAuthWindow) === 'undefined') {
                    this.showErrorPopup();
                } else {
                    // start a timer after one second
                    const timer = Observable.timer(1000, 1000);
                    this.timerSubscription = timer.subscribe((t: any) => {
                        this.profileService.authInstagramCallbak().subscribe(
                            response => this.onGetProfilesToConnect(response, 'I'),
                            error => console.log(error)
                        );
                    });
                }
            },
            error => console.log(error)
        );
    }
}
