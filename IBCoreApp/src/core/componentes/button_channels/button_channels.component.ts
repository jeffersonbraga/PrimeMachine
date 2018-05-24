import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ControlService } from 'core/services/control/control.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { Profile } from 'modelo/profile/Profile';

import { ModalService } from './../../services/modal/modal.service';

declare const $;

@Component({
    selector: 'button_channels',
    templateUrl: './button_channels.component.html'
})

export class Button_channelsComponent implements OnInit, AfterViewInit {

    @Input() isClassVisible: boolean;
    @Input() isAddChannel: boolean;

    @Input() profile: Profile = new Profile();
    @Input() isEditable: boolean;
    @Input() isExcludable: boolean;
    @Input() isSelectable: boolean;
    @Input() emitAnyway: boolean;
    @Input() singleSelect: boolean;
    @Input() verifySelected: boolean = true;
    @Input() validateSchedule: boolean;

    @Input('btnDefault')
    btnDefault: any;

    @Output() objectChecked: EventEmitter<Object> = new EventEmitter<Object>();
    @Output() profileToDelete: EventEmitter<Profile> = new EventEmitter<Profile>();
    @Output() profileToEdit: EventEmitter<Profile> = new EventEmitter<Profile>();

    @ViewChild('checkedButton')
    checkedButton: ElementRef;

    network = '';
    networkBgColor = '';

    @Input('listCheckedProfiles')
    public listCheckedProfiles: Profile[];

    constructor(
        private profilesService: ProfilesService,
        private controlService: ControlService,
        private modalService: ModalService
    ) {
        this.isSelectable = true;
    }

    ngOnInit() {
        this.network = '';
        this.networkBgColor = '';

        switch (this.profile.type) {
            case 'F':
                this.network = 'fa-facebook';
                this.networkBgColor = 'bg-facebook';
                break;
            case 'P':
                this.network = 'fa-facebook';
                this.networkBgColor = 'bg-facebook';
                break;
            case 'A':
                this.network = 'fa-facebook';
                this.networkBgColor = 'bg-facebook';
                break;
            case 'T':
                this.network = 'fa-twitter';
                this.networkBgColor = 'bg-twitter';
                break;
            case 'I':
                this.network = 'fa-instagram';
                this.networkBgColor = 'bg-instagram';
                break;
            case 'G':
                this.network = 'fa-google-plus';
                this.networkBgColor = 'bg-google';
                break;
            case 'Y':
                this.network = 'fa-youtube-play';
                this.networkBgColor = 'bg-youtube';
                break;
            case 'R':
                this.network = 'fa-newspaper-o';
                this.networkBgColor = 'bg-news';
                break;
            case 'N':
                this.network = 'fa-newspaper-o';
                this.networkBgColor = 'bg-news';
                break;
            case 'B':
                this.network = 'fa-newspaper-o';
                this.networkBgColor = 'bg-news';
                break;
            case 'L':
                this.network = 'fa-linkedin';
                this.networkBgColor = 'bg-linkedin';
                break;
            case 'H':
                this.network = 'fa-linkedin';
                this.networkBgColor = 'bg-linkedin';
                break;
        }

        this.profilesService.profilesChecked$.subscribe(
            () => {
                this.checkClickedAfterUserAction();
            }
        );

        this.checkClicked(null);
    }

    ngAfterViewInit() {
        if (this.btnDefault) {
            this.btnDefault.addEventListener('verifyCheckProfile', this.checkClicked);
        }
    }

    /**
     * TODO SABEDOT
     * Método é chamado desnecessáriamente porém não acarreta em bug e resolveu a tarefa.
     * Melhorar quando puder.
     * ass: Felipe kkk
     */
    checkClickedAfterUserAction() {
        this.profilesService.checkedProfiles.filter(profile => {

            if ((profile.idProfile === null) && (profile.idProfile === this.profile.idProfile)) {

                if (profile.networkId === this.profile.networkId) {
                    this.isClassVisible = true;
                }

            } else if (profile.idProfile === this.profile.idProfile) {
                this.isClassVisible = true;
            }
        });
    }

    checkClicked(e) {
        this.isClassVisible = false;
        if (this.verifySelected) {
            setTimeout(() => {

                if (this.listCheckedProfiles) {
                    this.listCheckedProfiles.filter(profile => {

                        if ((profile.idProfile === null) && (profile.idProfile === this.profile.idProfile)) {

                            if (profile.networkId === this.profile.networkId) {
                                this.isClassVisible = true;
                            }

                        } else if (profile.idProfile === this.profile.idProfile) {
                            this.isClassVisible = true;
                        }
                    });
                } else {
                    this.profilesService.checkedProfiles.filter(profile => {

                        if ((profile.idProfile === null) && (profile.idProfile === this.profile.idProfile)) {

                            if (profile.networkId === this.profile.networkId) {
                                this.isClassVisible = true;
                            }

                        } else if (profile.idProfile === this.profile.idProfile) {
                            this.isClassVisible = true;
                        }
                    });
                }
            }, 100);
        }
    }

    public checkUncheckButton() {
        if (this.validateSchedule && this.profile.type === 'I' && this.profile.isSchedulingAuthenticated === 'F') {
            this.openModalAuth();
        } else {
            if (this.isSelectable || this.emitAnyway) {
                if (!this.singleSelect) {
                    if (this.isSelectable) {
                        this.isClassVisible = !this.isClassVisible;
                    }

                    this.objectChecked.emit({
                        checked: this.isClassVisible,
                        profile: this.profile,
                        revert: this
                    });

                    if (this.isClassVisible) {
                        if (!this.isAddChannel) {
                            this.profilesService.addCheckedProfile(this.profile);
                        }
                    } else {
                        this.profilesService.removeCheckedProfile(this.profile);
                    }
                } else {
                    this.checkedButton.nativeElement.click();

                    // Previne atualização duplicada de relatórios
                    this.profilesService.singleCheckedProfile = this.profile;

                    this.objectChecked.emit({
                        checked: true,
                        profile: this.profile,
                        revert: this
                    });
                }
            }
        }
    }

    openModalAuth(): any {
        this.controlService.profileAuth = this.profile;
        this.modalService.showScheduleAuthModal();
    }

    editProfile() {
        this.profileToEdit.emit(this.profile);
    }

    deleteProfile() {
        this.profileToDelete.emit(this.profile);
    }

    public invalidateCheckButton() {
        this.isClassVisible = !this.isClassVisible;
    }
}
