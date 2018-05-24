import { Profile } from 'modelo/profile/Profile';
import { Component, Injector, OnInit, OnDestroy } from '@angular/core';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';
import { ControlService } from 'core/services/control/control.service';
import { GroupsService } from 'core/services/groups/groups.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { Group } from 'modelo/group/Group';

@Component({
    selector: 'groups-management',
    templateUrl: './groups_management.component.html'
})
export class Groups_managementComponent implements OnInit, OnDestroy {

    groups: any = [];
    group: Group = new Group();
    currentModal: GenericModalComponent;
    result: any;
    groupType: any;
    checkedProfilesAux: Profile[] = [];

    constructor(
        private groupService: GroupsService,
        private controlApp: ControlService,
        private injector: Injector,
        private profileService: ProfilesService,
        private breadcrumbsService: BreadcrumbsService
    ) {
        this.groups = [];
        this.currentModal = this.injector.get("currentModal");
        this.groupType = groupService.groupType;

        this.breadcrumbsService.initializeBreadcrumb();
    }

    ngOnInit() {

        if (window.location.pathname.split('/')[2] === 'rssfeed' && !this.groupService.fromSettingsGroup) {
            this.groupService.groupType = 'R';
        }

        this.groupType = this.groupService.groupType;

        if (this.groupType === 'P') {

            this.breadcrumbsService.addBreadcrumb("Gerenciar Grupos", "");
            this.groupService.getGroups()
                .subscribe(
                result => this.onGetGroups(result),
                error => console.log(error));

        } else {
            this.breadcrumbsService.addBreadcrumb("Manage RSS Groups", "");
            this.groupService.getRssFeedGroups()
                .subscribe(
                result => this.onGetGroups(result),
                error => console.log(error));
        }

        this.groupService.groupsRss$
            .subscribe(
            groups => this.onGetGroups(groups)
            );

        this.groupService.fromSettingsGroup = false;
    }

    ngOnDestroy () {
        this.profileService.lockCheck = false;
    }


    onGetGroups(result: any) {
        if (!result) {
            console.log('Houve um erro ao recuperar grupos.');
        } else {
            this.groupService.currentGroups = result;
            this.groups = this.groupService.currentGroups;
        }
    }

    createGroup() {
        this.group.groupType = this.groupType;
        this.groupService.persistGroup(this.group)
            .subscribe(
            result => this.onPersistGroup(result),
            error => console.log(error));
    }

    onPersistGroup(group: Group) {
        if (!group) {
            console.log('Houve um erro ao criar grupo.');
        } else {
            this.groupService.addGroup(group);
            this.groups = this.groupService.currentGroups;
            this.group = new Group();
        }
    }

    editGroup(event, index: number) {
        this.group = event;
        this.groupService.currentGroup = this.group;
        this.groupService.currentIndex = index;
        this.currentModal.setDynamicModalContent("Edit_groupsComponent");
    }

    deleteGroup(event) {
        this.groupService.currentGroupToDelete = event;
        this.groupService.returnComponent = "Groups_managementComponent";
        this.currentModal.setDynamicModalContent("DeleteGroupComponent");
    }
}