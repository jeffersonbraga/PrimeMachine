import { ModalService } from './../../core/services/modal/modal.service';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { ControlService } from 'core/services/control/control.service';
import { GroupsService } from 'core/services/groups/groups.service';
import { LoginService } from 'core/services/login/login.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { TimezoneService } from 'core/services/timezone/timezone.service';
import { UserService } from 'core/services/user/user.service';

@Component({
    moduleId: module.id,
    selector: 'home-selector',
    templateUrl: 'home.component.html'
})

export class HomeComponent implements OnInit, AfterViewInit {

    quickviewState = false;

    constructor(
        private router: Router,
        private controlApp: ControlService,
        private ProfilesService: ProfilesService,
        private profileService: ProfilesService,
        private timeZoneService: TimezoneService,
        private groupsService: GroupsService,
        private loginService: LoginService,
        private userService: UserService,
        private modalService: ModalService
    ) {

        this.timeZoneService.getDefaultTimezone().subscribe(
            result => this.setDefaultTimezone(result));
    }

    ngOnInit() {
        this.setInfoAccount();
        this.getProfiles();
        this.getProfilesToReconnect();
        this.getEditingGroupsIds();
    }

    getEditingGroupsIds() {
        const editingGroupIds = JSON.parse(localStorage.getItem('editingGroupIds'));
        this.groupsService.editingGroupIds = editingGroupIds;
    }

    setInfoAccount() {
        this.controlApp.getInfoAccount()
            .subscribe(
                account => {
                    this.controlApp.adminSource.next(account[0]);
                    this.controlApp.analystsSource.next(account[1]);
                    this.controlApp.userSource.next(account[2]);

                    this.controlApp.admins = account[0];
                    this.controlApp.analysts = account[1];
                    this.controlApp.setUser(account[2]);
                    const user = this.controlApp.getUser();
                    if (user) {
                        this.controlApp.account = this.controlApp.getUser().account;
                    }
                }
            );
    }

    getProfiles() {
        this.ProfilesService.getProfiles()
            .subscribe(
                profiles => {
                    this.ProfilesService.profiles = profiles;
                    this.ProfilesService.profilesSource.next(profiles);
                },
                error => console.log(error));
    }

    getProfilesToReconnect() {
        this.ProfilesService.getProfilesToReconnect()
            .subscribe(
                profiles => {
                    this.ProfilesService.profilesToReconnect = profiles;
                    this.ProfilesService.profilesToReconnectSource.next(profiles);
                }
            );
    }

    ngAfterViewInit(): void {
        this.profileService.getProfiles()
            .subscribe(
                result => this.onGetProfiles(result),
                error => console.log(error));
    }

    setProfilesCheckeds(event) {
        this.profileService.setCheckedProfiles(event);
    }

    onGetProfiles(result: any) {
        if (!result) {
            console.log('Houve um erro ao recuperar canais. Exibir HelloWoop ');
            this.validateShowHelloWoop();
        } else {
            if (result.length === 0) {
                this.validateShowHelloWoop();
            } else {
                if (this.controlApp.getUser().tutorialShowed === 'F') {
                    this.showTutorialModal();
                }
            }
        }
    }

    showTutorialModal() {
        this.modalService.showTutorialModal();

        this.userService.setTutorialShowed().subscribe();
    }

    validateShowHelloWoop() {
        if (this.loginService.isLogged) {
            this.modalService.showHelloWoopModal();
        }
    }

    setDefaultTimezone(tmzValue) {
        this.timeZoneService.defaultTimezoneValue = tmzValue;
    }
}
