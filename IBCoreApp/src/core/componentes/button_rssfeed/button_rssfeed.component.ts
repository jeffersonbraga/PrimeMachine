import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { ControlService } from 'core/services/control/control.service';
import { DialogService } from 'core/services/dialog/dialog.service';
import { GroupsService } from 'core/services/groups/groups.service';
import { Group } from 'modelo/group/Group';
import { RssFeed } from 'modelo/rssFeed/RssFeed';

declare let $;
@Component({
    selector: 'button_rssfeed',
    templateUrl: './button_rssfeed.component.html'
})

export class Button_rssfeedComponent {

    @Input()
    isClassVisible: boolean;

    errorMessage: string = '';
    canExclude: boolean = false;
    loading: boolean;

    @Input() rssFeed : RssFeed = new RssFeed();
    @Input() isEditable: boolean;
    @Input() isSelectable: boolean;
    @Input() isExcludable: boolean;
    @Input() group: Group;
    @Input() index: number;

    @Output() objectChecked: EventEmitter<Object> = new EventEmitter<Object>();

    @ViewChild('checkedButtonRss')
    checkedButtonRss: ElementRef;

    constructor(
        private groupService: GroupsService,
        private dialogService: DialogService,
        private controlApp: ControlService
    ) {
        this.isSelectable = true;
    }

    ngOnInit(): void {
        this.groupService.loading$.subscribe(
            loading => this.loading = loading
        );
    }

    confirmDeleteRssFeed(rssFeed?) {
        this.canExclude = true;
        this.isExcludable = false;
    }

    deleteRssFeed() {
        this.groupService.currentIndex = this.index;
        this.groupService.deleteRssFeed(this.rssFeed, this.group)
        .subscribe(
            result => this.onDeleteRssFeed(result),
            error => console.log(error)
        );
    }

    onDeleteRssFeed(result: Group) {
        if (!result) {
            this.errorMessage = 'Ops! Something went wrong when deleting the rss feed';
            // this.errorMessage = 'Houve um erro ao deletar rss feed';
        } else {
            this.groupService.currentGroups[this.groupService.currentIndex] = result;
            this.groupService.removeRssFeedFromSource(this.rssFeed);

            this.groupService.editingGroupId = this.group.idGroup;

            if (this.groupService.editingGroupIds.indexOf(this.group.idGroup) === -1) {
                this.groupService.editingGroupIds.push(this.group.idGroup);
                localStorage.setItem('editingGroupIds', JSON.stringify(this.groupService.editingGroupIds));
            }

            this.canExclude = false;
            this.isExcludable = true;
        }
    }

    goBack() {
            this.canExclude = false;
            this.isExcludable = true;
    }

    checkUncheckButton() {
        if (this.isSelectable) {
            this.checkedButtonRss.nativeElement.click();
            this.groupService.checkAllBtnSelected = false;
            this.groupService.loadingSource.next(true);
            this.groupService.waitingSource.next(false);

            this.groupService.getRssFeedMessages(this.rssFeed).subscribe (
                result => this.onGetRssFeedGroups(result),
                error => this.onGetRssFeedGroupsError(error)
            );
        }
    }

    onGetRssFeedGroupsError(error) {
        this.groupService.loadingSource.next(false);
    }

    onGetRssFeedGroups(result: RssFeed) {
        if (!result) {
            this.errorMessage = 'Houve ao recuperar rss feed messages';
        } else {
            this.rssFeed = result;

            this.groupService.addRssFeedOnSource(this.rssFeed);
            this.groupService.currentGroupSource.next(this.group);
        }
    }
}