import { ModalService } from './../../services/modal/modal.service';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { ControlService } from 'core/services/control/control.service';
import { GroupsService } from 'core/services/groups/groups.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { Group } from 'modelo/group/Group';
import { GroupsProfiles } from 'modelo/group/GroupsProfiles';
import { Profile } from 'modelo/profile/Profile';
import { Subscription } from 'rxjs/subscription';

declare var $;

@Component({
    selector: 'my_channels',
    templateUrl: './my_channels.component.html'
})
export class My_channelsComponent implements OnInit, OnDestroy {

    checkedProfiles: Profile[] = [];

    @Input()
    showCheckAllBtn = false;

    @Input()
    singleSelect = true;

    @Input()
    groupType: any;

    @Input()
    activeModuleName: string;

    @Input()
    currentModal: string; // GenericModalComponent;

    @Input()
    validateSchedule: boolean;

    headerTitleChannels = 'Gerenciar Canais';
    headerTitleGroups = 'Gerenciar Grupos';
    headerTitleRssFeedGroups = 'Gerenciar Rss Feeds';

    manageChannelsComponentClassName = 'ManageChannelsComponent';
    groupsManagementComponentClassName = 'Groups_managementComponent';
    manageRssfeedsComponentClassName = 'ManageRssfeedsComponent';

    @Output() sendProfiles: EventEmitter<Profile[]> = new EventEmitter<Profile[]>();
    profiles: Profile[] = [];

    groups: Group[];

    groupsToShow: Group[];

    groupDefault: Group;

    noProfiles: boolean;
    noRssFeeds: boolean;
    noChannelForAnalytics: boolean;

    loading: boolean;

    subscription: Subscription;

    route_name: any = '';

    selectedProfiles = [] = [];

    constructor(
        private groupsService: GroupsService,
        private profilesService: ProfilesService,
        private controlApp: ControlService,
        private modalService: ModalService
    ) {
        this.profiles = [];
        this.groups = [];
        this.groupsToShow = [];
        this.groupDefault = new Group();
        this.noProfiles = true;
        this.noRssFeeds = true;
        this.noChannelForAnalytics = false;
        this.loading = true;
    }

    ngOnInit() {
        this.profilesService.initCheckedProfiles();

        this.groupsService.groupType = this.groupType;

        if (this.groupType === 'P') {

            this.subscription = this.profilesService.profiles$.subscribe(
                result => this.onGetProfiles(result)
            );

            this.profilesService.getProfiles()
                .subscribe(
                profiles => {
                    if (profiles.length > 0) {

                        this.profilesService.profiles = profiles;
                        this.profilesService.profilesSource.next(profiles);

                        this.noProfiles = false;
                        this.noRssFeeds = false;
                    } else {
                        // Precisa de ambos para mostrar mensagem de
                        // adição de canais
                        this.noProfiles = true;
                        this.noRssFeeds = true;
                    }
                },
                error => console.log(error)
                );

            this.profilesService.profilesChecked$.subscribe(
                profiles => {
                    this.checkedProfiles = profiles;
                    this.sendProfiles.emit(this.checkedProfiles);
                }
            );

        } else if (this.groupType === 'R') {

            // this.headerTitleGroups = this.headerTitleRssFeedGroups;

            this.groupsService.getRssFeedGroups()
                .subscribe(
                result => this.onGetRssFeedGroups(result),
                error => console.log(error));

            this.groupsService.groupsRss$
                .subscribe(
                    groups => this.onUpdateGroup(groups)
                );
        }
    }

    onUpdateGroup(groups: Group[]) {
        this.groups = groups;

        if (this.groups) {
            if (this.groups.length > 0 && this.hasObjectOnGroup(this.groups)) {
                // Precisa de ambos para mostrar mensagem de
                // adição de canais
                this.noRssFeeds = false;
                this.noProfiles = false;
            } else {
                this.noRssFeeds = true;
                this.noProfiles = true;
            }
        }
    }

    onGetRssFeedGroups(result: any) {
        if (!result) {
            console.log('Houve um erro ao recuperar rss feeds');
        } else {
            this.groupsService.currentGroups = result;
            // this.groups = this.groupsService.currentGroups;
            this.groupsService.rssGroupsSource.next(result);

            if (this.groups.length > 0 && this.hasObjectOnGroup(this.groups)) { // typeof this.groups[0] !== 'undefined' ||
                // Precisa de ambos para mostrar mensagem de
                // adição de canais
                this.noRssFeeds = false;
                this.noProfiles = false;
            } else {
                this.noRssFeeds = true;
                this.noProfiles = true;
            }

            this.loading = false;
        }
    }

    hasObjectOnGroup(groups: Group[]) {

        let found;
        if (groups.length > 0) {
            for (let i = 0; i < this.groups.length; i++) {
                if (this.groups[i].groupsObject != null) {
                    if (this.groups[i].groupsObject.length > 0) {
                        found = true;
                    }
                }
            }
        } else {
            found = false;
        }

        if (found === false || !found) {
            return false;
        } else {
            return true;
        }
    }

    onGetProfiles(result: any) {
        if (!result) {
            console.log('Houve um erro ao recuperar canais');
        } else {
            this.profiles = result;

            if (this.activeModuleName === 'reports') {
                this.profiles = this.removeProfilesOnReportComponent(this.profiles);
            }

            this.groupsService.getGroups()
                .subscribe(
                groups => this.onGetGroups(groups),
                error => console.log(error));
        }
    }

    onGetGroups(result: any) {
        if (!result) {
            console.log('Houve um erro ao recuperar grupos.');
        } else {
            this.groupsService.currentGroups = result;

            this.groups = this.groupsService.currentGroups;

            if (this.activeModuleName === 'reports') {
                for (let i = 0; i < this.groups.length; i++) {
                    for (let k = 0; k < this.groups[i].groupsObject.length; k++) {
                        if (this.groups[i].groupsObject[k].profile.type === 'L' || this.groups[i].groupsObject[k].profile.type === 'F') {
                            this.groups[i].groupsObject.splice(k, 1);
                            k--;
                        }
                    }
                }
            }

            this.manageGroupDefault();

            this.loading = false;
        }
    }

    removeProfilesOnReportComponent(profiles: Profile[]) {
        let pageProfilesCount = 0;

        for (let i = 0; i < profiles.length; i++) {
            if (profiles[i].type === 'L' || profiles[i].type === 'F') {
                profiles.splice(i, 1);
                i--;
            } else {
                pageProfilesCount += 1;
            }
        }

        if (pageProfilesCount === 0) {
            this.noChannelForAnalytics = true;
        } else {
            this.noChannelForAnalytics = false;
        }

        return profiles;
    }

    setProfilesCheckeds(event) {
        if (this.singleSelect) {
            this.sendProfiles.emit(event);
        }
    }

    manageGroupDefault() {
        this.groupDefault = new Group();

        // **Código comentado pois a principio não tem utilidade**
        // this.groupDefault.account = this.profiles[0].account;
        this.groupDefault.groupsObject = [];
        this.groupDefault.idGroup = 1234567; // this.profiles[0].account.idAccount + 1234567;
        this.groupDefault.name = 'Canais Adicionados (Default)';

        const profilesOnGroups: Profile[] = [];

        for (let i = 0; i < this.groups.length; i++) {
            for (let k = 0; k < this.groups[i].groupsObject.length; k++) {
                profilesOnGroups.push(this.groups[i].groupsObject[k].profile);
            }
        }

        const aux: any = [];

        for (let i = 0; i < profilesOnGroups.length; i++) {
            for (let k = 0; k < this.profiles.length; k++) {
                if ((profilesOnGroups[i] as Profile).idProfile === this.profiles[k].idProfile) {
                    aux.push(this.profiles[k]);
                }
            }
        }

        // Remove profiles que estão no grupoDefault
        this.profiles = $(this.profiles).not(aux).get();
        // Atualiza profiles source se achar algum perfil diferente dos já existentes
        if (this.groupDefaultChanged(this.profiles, this.profilesService.profiles)) {
            this.profilesService.profiles = this.profiles;
            this.profilesService.profilesSource.next(this.profiles);
        }

        for (let i = 0; i < this.profiles.length; i++) {
            const groupsProfilesAux: GroupsProfiles = new GroupsProfiles();
            // Antes era assim:
            // groupsProfilesAux.group = this.groupDefault;
            groupsProfilesAux.group = new Group;
            groupsProfilesAux.group.idGroup = 1234567; // this.profiles[0].account.idAccount + 1234567;
            groupsProfilesAux.group.name = 'Canais Adicionados (Default)';
            groupsProfilesAux.profile = this.profiles[i];

            this.groupDefault.groupsObject.push(groupsProfilesAux);
        }
    }

    groupDefaultChanged(profilesUpdated, profilesOnService) {

        let anyDif = profilesOnService.length;

        for (let i = 0; i < profilesUpdated.length; i++) {
            for (let k = 0; k < profilesOnService.length; k++) {
                if ((profilesUpdated[i] as Profile).idProfile === profilesOnService[k].idProfile) {
                    anyDif--;
                }
            }
        }
        if (anyDif === 0) {
            return false;
        } else {
            return true;
        }
    }

    openChannelsModal() {
        this.modalService.showChannelsModal();
    }

    openGroupsModal() {
        this.modalService.showGroupsManagementModal();
    }

    ngOnDestroy(): void {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }
}
