import 'rxjs/Rx';

import { Component, forwardRef, Inject, Injector, OnDestroy, OnInit } from '@angular/core';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { StreamService } from 'core/services/stream/stream.service';
import { WindowRefService } from 'core/services/window/window-ref.service';
import { Profile } from 'modelo/profile/Profile';
import { Stream } from 'modelo/stream/Stream';

import { ControlService } from './../../services/control/control.service';

@Component({
  selector: 'add-streams',
  templateUrl: './add-streams.component.html',
  styleUrls: ['./add-streams.component.css']
})
export class AddStreamsComponent implements OnInit, OnDestroy {
    total = 0;
    profilesAllowed = 0;
    profiles: Profile[] = [];
    objectCheckeds: Profile[] = [];

    nativeWindow: any;

    currentModal: GenericModalComponent;

    timerSubscription: any;

    adding: boolean;
    loaded = false;

    constructor(
        private profileService: ProfilesService,
        private controlApp: ControlService,
        private winRef: WindowRefService,
        private injector: Injector,
        private breadcrumbsService: BreadcrumbsService,
        private StreamService: StreamService,
        @Inject(forwardRef(() => GenericModalComponent)) private _parent: GenericModalComponent
    ) {
        this.profiles = [];
        this.nativeWindow = winRef.getNativeWindow();
        this.currentModal = this.injector.get('currentModal');

        this.breadcrumbsService.initializeBreadcrumb();
        this.breadcrumbsService.addBreadcrumb('Adicionar Streams', '');
    }

    ngOnInit() {
        this.profileService.getProfiles()
            .subscribe(
                result => this.onGetProfiles(result),
                error => console.log(error)
            );
        this.profileService.lockCheck = true;
    }

    ngOnDestroy() {
        if (this.timerSubscription) {
            this.timerSubscription.unsubscribe();
        }
        this.profileService.lockCheck = true;
    }

    onGetProfiles(result: any) {
        if (!result) {
            console.log('Ops! Something went wrong recovering channels. ');
            // console.log('Houve um erro ao recuperar canais. ');
        } else {
            this.profiles = result;
            this.checkTotal();

            // Limpa checked profiles e previne que o add-streams mostre-os
            this.profileService.checkedProfiles = [];
        }
    }

    addRemoveProfile(event) {
        if (event.checked) {
            this.objectCheckeds.push(event.profile);
        }else {
            this.objectCheckeds.splice(this.objectCheckeds.indexOf(event.profile), 1);
        }
    }

    addStreams() {
        if (!this.adding) {
            this.adding = true;

            this.StreamService.addStreamsByProfile(this.objectCheckeds)
            .subscribe(
                streams => this.mergeStreams(streams)
            );
        }
    }

    mergeStreams(streams: Stream[]) {
        const tam = streams.length;

        for (let i = 0; i < tam; i++) {
            this.StreamService.addStreamToSource(streams[i]);
        }

        setTimeout(() => {
            this.StreamService.reorderStreams(streams)
                .subscribe();
        }, 300);

        this.adding = false;
        this.closeModal();
    }

    checkTotal() {
        this.StreamService.stream.filter(
            s => {
                this.profiles.filter(
                    p => {
                        if (p.idProfile === s.profile.idProfile) {
                            this.total++;
                        }
                    }
                );
            }
        );

        this.profiles.filter(
            p => {
                if (p.type !== 'L' && p.type !== 'F' && p.type !== 'H') {
                    this.profilesAllowed++;
                }
            }
        );

        this.loaded = true;
    }

    checkForExists(profile: Profile) {
        const check = this.StreamService.stream.filter(
            f => {
                if (f.profile.idProfile === profile.idProfile) {
                    return true;
                }
            }
        );

        if (check.length) {
            return false;
        }else {
            return true;
        }
    }

    closeModal() {
        // Limpa checked profiles e previne que o add-streams mostre-os
        this.profileService.checkedProfiles = [];

        this.profileService.lockCheck = true;
        this._parent.hideModal();
    }
}
