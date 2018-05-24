import 'rxjs/Rx';

import { Component, Injector, OnDestroy, OnInit } from '@angular/core';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';
import { ControlService } from 'core/services/control/control.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { WindowRefService } from 'core/services/window/window-ref.service';
import { Profile } from 'modelo/profile/Profile';
import { Observable } from 'rxjs/Observable';

declare const $;

@Component({
  selector: 'manage-channels',
  templateUrl: './manage-channels.component.html',
  styleUrls: ['./manage-channels.component.css']
})
export class ManageChannelsComponent implements OnInit, OnDestroy {

  profiles: any = [];
  profilesToConnect: Profile[] = [];

  nativeWindow: any;
  facebookAuthWindow: any;
  twitterAuthWindow: any;
  linkedInAuthWindow: any;
  instagramAuthWindow: any;
  errorMessage: String;
  hasError: boolean;
  href: string;

  checkedProfilesAux = [];

  currentModal: GenericModalComponent;

  timerSubscription: any;

    constructor(
        private profileService: ProfilesService,
        private controlApp: ControlService,
        private winRef: WindowRefService,
        private injector: Injector,
        private breadcrumbsService: BreadcrumbsService
    ) {
        this.profiles = [];
        this.nativeWindow = winRef.getNativeWindow();
        this.currentModal = this.injector.get('currentModal');

        this.breadcrumbsService.initializeBreadcrumb();
    }

    ngOnInit() {

        // Backup dos checked profiles
        for (let i = 0; i < this.profileService.checkedProfiles.length; i++) {
            this.checkedProfilesAux[i] = this.profileService.checkedProfiles[i];
        }

        // Zera checked profiles para não marca-los no manage channels e reatribui valores no onDestroy
        this.profileService.checkedProfiles.splice(0, this.profileService.checkedProfiles.length);

        this.profileService.getProfiles()
            .subscribe(
            result => this.onGetProfiles(result),
            error => console.log(error));
        this.breadcrumbsService.addBreadcrumb('Gerenciar Canais', '');
    }

    ngOnDestroy () {
        if (this.timerSubscription) {
            this.timerSubscription.unsubscribe();
        }

        if (this.profileService.checkedProfiles.length === 0) {
            // Volta backup dos checked profiles
            for (let i = 0; i < this.checkedProfilesAux.length; i++) {
                this.profileService.checkedProfiles[i] = this.checkedProfilesAux[i];
            }

            this.profileService.profilesCheckedSource.next(this.profileService.checkedProfiles);
        } else {
            this.profileService.profilesCheckedSource.next(this.profileService.checkedProfiles);
        }
    }

    onGetProfiles(result: any) {
        if (!result) {
            console.log('Houve um erro ao recuperar canais. ');
        } else {
            this.profiles = result;
        }
    }

    openInstagramAuthPage(): boolean {
        this.instagramAuthWindow = this.nativeWindow.open('',"nova","width=800,height=600");
        this.profileService.getInstagramAuthURL().subscribe(
            result => {
                this.instagramAuthWindow.location = result['authenticationUrl'];

                if (this.instagramAuthWindow == null || typeof(this.instagramAuthWindow) === 'undefined') {
                    this.showErrorPopup();
                }else {
                    // start a timer after one second
                    const timer = Observable.timer(1000, 1000);
                    this.timerSubscription = timer.subscribe((t: any) => {
                        this.profileService.authInstagramCallbak().subscribe(
                            auth => this.onGetProfilesToConnect(auth, 'I'),
                            error => console.log(error)
                        );
                    });
                }
            },
            error => console.log(error)
        );

        return false;
    }

    openFacebookAuthPage(): void {
        this.facebookAuthWindow = window.open('', '_blank');
        this.profileService.getFacebookAuthURL().subscribe(
            result => {

                this.facebookAuthWindow.location = result['authenticationUrl'];

                if (this.facebookAuthWindow == null || typeof(this.facebookAuthWindow) === 'undefined') {
                    this.showErrorPopup();
                }else {
                    // start a timer after one second
                    const timer = Observable.timer(1000, 1000);
                    this.timerSubscription = timer.subscribe((t: any) => {
                        this.profileService.authFacebookCallbak().subscribe(
                            auth => this.onGetProfilesToConnect(auth, 'F'),
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

                if (this.twitterAuthWindow == null || typeof(this.twitterAuthWindow) === 'undefined') {
                    this.showErrorPopup();
                } else {
                    // start a timer after one second
                    const timer = Observable.timer(1000, 1000);
                    this.timerSubscription = timer.subscribe((t: any) => {
                        this.profileService.authTwitterCallbak().subscribe(
                            auth => {
                                this.onGetProfilesToConnect(auth, 'T');
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

                if (this.linkedInAuthWindow == null || typeof(this.linkedInAuthWindow) === 'undefined') {
                    this.showErrorPopup();
                } else {
                    // start a timer after one second
                    const timer = Observable.timer(1000, 1000);
                    this.timerSubscription = timer.subscribe((t: any) => {
                        this.profileService.authLinkedinCallbak().subscribe(
                            auth => {
                                this.onGetProfilesToConnect(auth, 'L');
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
                this.profilesToConnect = result[1];
                this.finalizeAuth(networkType);
            }
        }
    }

    finalizeAuth(networkType: String) {

        this.timerSubscription.unsubscribe();

        if (networkType === 'F') {
            this.profileService.lockCheck = true;
            this.profileService.profilesToConnect = this.profilesToConnect;
            this.facebookAuthWindow.close();
            this.currentModal.setDynamicModalContent('AddFacebookChannellsComponent');
            this.breadcrumbsService.addBreadcrumb('Adicionar Canais do Facebook', '');
        } else if (networkType === 'I') {
            this.profileService.lockCheck = true;
            this.profileService.profilesToConnect = this.profilesToConnect;
            this.instagramAuthWindow.close();
            this.currentModal.setDynamicModalContent('AddFacebookChannellsComponent');
            this.breadcrumbsService.addBreadcrumb('Selecionar Canais do Instagram', '');
        } else if (networkType === 'T') {
            this.twitterAuthWindow.close();
            this.addTwitterChannel(this.profilesToConnect);
            this.profileService.addProfilesToSouce(this.profilesToConnect[0]);
        } else if (networkType === 'L' || networkType === 'H') {
            this.profileService.lockCheck = true;
            this.profileService.profilesToConnect = this.profilesToConnect;
            this.linkedInAuthWindow.close();
            this.currentModal.setDynamicModalContent('AddFacebookChannellsComponent');
            this.breadcrumbsService.addBreadcrumb('Adicionar Canais do LinkedIn', '');
        }
    }

    addTwitterChannel(profiles: Profile[]) {
        this.profileService.saveProfiles(profiles).subscribe(
            result => this.onAddTwitterChannel(result),
            error => this.showErrorMessage(error)
        );
    }

    addLinkedInChannel(profiles: Profile[]) {
        this.profileService.saveProfiles(profiles).subscribe(
            result => this.onAddLinkedInChannel(result),
            error => error
        );
    }

    onAddTwitterChannel(result: any) {
        this.currentModal.setDynamicModalContent('ManageChannelsComponent');
    }

    onAddLinkedInChannel(result: any) {
        this.currentModal.setDynamicModalContent('ManageChannelsComponent');
    }

    addInstagramChannelComponent() {
        this.currentModal.setDynamicModalContent('AddInstagramChannelComponent');
    }

    editInstagramChannel(event) {
        this.profileService.currentProfileToEdit = event;
        this.currentModal.setDynamicModalContent('AddInstagramChannelComponent');
    }

    deleteChannel(event) {
        this.profileService.currentProfileToDelete = event;
        this.profileService.returnComponent = 'ManageChannelsComponent';
        this.currentModal.setDynamicModalContent('DeleteChannelComponent');
    }

    showErrorMessage(errorMessage: string) {
        this.errorMessage = errorMessage;
        this.hasError = true;
    }

    showErrorPopup() {
        this.hasError = true;
        this.errorMessage = 'Unblock our pop-ups so we can connect to your social networks';
        // this.errorMessage = 'Desbloqueie os nossos pop-ups para conseguirmos fazer login nas redes sociais';
    }

    hideErrorMessage() {
        this.errorMessage = '';
        this.hasError = false;
    }
}
