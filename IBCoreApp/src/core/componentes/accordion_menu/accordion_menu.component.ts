import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ControlService } from 'core/services/control/control.service';
import { GroupsService } from 'core/services/groups/groups.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { Group } from 'modelo/group/Group';
import { Profile } from 'modelo/profile/Profile';
import { RssFeed } from 'modelo/rssFeed/RssFeed';

declare const $;

@Component({
    selector: 'accordion_menu',
    templateUrl: './accordion_menu.component.html'
})
export class Accordion_menuComponent implements OnInit, AfterViewInit {

    @Input('checkedProfiles')
    checkedProfiles: Profile[] = [];

    objectCheckeds: Profile[];

    @Input()
    showCheckAllBtn: boolean;

    @Input()
    groupType: string;

    @Input()
    singleSelect: boolean;

    @Input()
    index: number;

    allOpened;

    @Input()
    isGroupDefault: boolean;

    @Input()
    group: Group;

    @Input()
    rssFeed: RssFeed;

    @Input()
    validateSchedule: boolean;

    isClassVisible: boolean;
    isEditingGroup;
    loading: boolean;

    @Output() sendProfiles: EventEmitter<Profile[]> = new EventEmitter<Profile[]>();

    checkAll = false;

    currentCheckedProfiles: Profile[];

    @ViewChild('checkedButtonRss')
    checkedButtonRss: ElementRef;

    constructor(
        private groupService: GroupsService,
        private controlApp: ControlService,
        private profilesService: ProfilesService
    ) {}

    ngOnInit(): void {
        this.objectCheckeds = [];

        this.groupService.checkAll$
            .subscribe(
                group => this.checkGroupSelected(group)
            );

        this.profilesService.profilesChecked$.subscribe(
            profiles => {
                if (!this.checkAll) {
                    this.currentCheckedProfiles = profiles;
                }
            }
        );

        this.groupService.loading$.subscribe(
            loading => this.loading = loading
        );

        if (this.groupService.editingGroupIds) {
                if (this.groupService.editingGroupIds.indexOf(this.group.idGroup) > -1) {
                    this.isEditingGroup = true;
                    this.allOpened = false;
                } else {
                    this.isEditingGroup = false;
                    this.allOpened = false;
                }

                if (this.group.idGroup === this.groupService.editingGroupId) {
                    this.isEditingGroup = true;
                    this.allOpened = false;
                }
        } else {
            this.allOpened = true;

            if (this.group.idGroup === this.groupService.editingGroupId) {
                this.isEditingGroup = true;
                this.allOpened = false;
            }
        }
    }

    ngAfterViewInit(): void {
    }

    checkGroupSelected(group: Group) {
        if (group.idGroup !== this.group.idGroup) {
            this.checkAll = false;
        }
    }

    manageEditingGroup(event) {
        if (this.groupService.editingGroupIds) {
            if (this.groupService.editingGroupIds.indexOf(this.group.idGroup) > -1) {
                this.groupService.editingGroupIds.splice(this.groupService.editingGroupIds.indexOf(this.group.idGroup), 1);
                localStorage.setItem('editingGroupIds', JSON.stringify(this.groupService.editingGroupIds));
                this.uncheckProfiles();
            } else {
                this.groupService.editingGroupIds.push(this.group.idGroup);
                localStorage.setItem('editingGroupIds', JSON.stringify(this.groupService.editingGroupIds));
            }
        } else {
            this.groupService.editingGroupIds = [];

            // Adiciona todas grupos no array de estados dos grupos e remove o que foi fechado
            for (let i = 0; i < this.groupService.currentGroups.length; i++) {
                this.groupService.editingGroupIds.push(this.groupService.currentGroups[i].idGroup);
            }

            const groupDefaultId = 1234567;
            this.groupService.editingGroupIds.push(groupDefaultId);

            this.groupService.editingGroupIds.splice(this.groupService.editingGroupIds.indexOf(this.group.idGroup), 1);

            localStorage.setItem('editingGroupIds', JSON.stringify(this.groupService.editingGroupIds));
        }
    }

    uncheckProfiles(): any {
        if (!this.controlApp.mobileAndTabletcheck()) {
            this.group.groupsObject.filter(
                group => {
                    this.profilesService.removeCheckedProfile(group.profile);
                }
            );

            $('#collapse_' + this.group.idGroup).find('input:checked').click();

            if (this.singleSelect) {
                $('#collapse_' + this.group.idGroup).find('input:checked').attr('checked', false);
            }
        }
    }

    addRemoveObjectChecked(event) {
        this.checkAll = false;
        if  (!this.singleSelect) {
            if  (event.checked) {
                this.objectCheckeds.push(event.profile);
            }else {
                this.objectCheckeds.splice(this.objectCheckeds.indexOf(event.profile), 1);
            }
        }else {
            if  (event.checked) {
                this.objectCheckeds = [event.profile];
            }else {
                this.objectCheckeds = [];
            }
        }

        if  (!this.checkAll) {
            this.sendProfiles.emit(this.objectCheckeds);
        }
    }

    checkAllObjects(event) {
        const profiles = this.group.groupsObject.map(
            group => group.profile
        );

        this.checkAll = event;

        this.groupService.currentCheckAll.next(this.group);

        if (event) {
            this.profilesService.profilesCheckedSource.next(profiles);
        } else {
            this.profilesService.profilesCheckedSource.next(this.currentCheckedProfiles);
        }
    }

    checkAllRssFeeds(group: Group) {
        this.groupService.loadingSource.next(true);
        this.groupService.waitingSource.next(false);
        this.groupService.currentGroupSource.next(this.group);

        this.checkedButtonRss.nativeElement.click();

        this.groupService.getAllRssFeedsFromGroupByDate(group)
            .subscribe(
            result => this.onGetAllRssFeedsFromGroupByDate(result),
            error => console.log(error));
    }

    onGetAllRssFeedsFromGroupByDate(result: RssFeed[]) {
        if (!result) {
            console.log('Houve um erro ao recuperar rss feeds');
        } else {

            const rssFeeds: RssFeed[] = result;

            this.groupService.checkAllBtnSelected = true;

            this.groupService.addAllRssFeedOnSource(rssFeeds);

            this.groupService.loadingSource.next(false);
        }
    }
}
