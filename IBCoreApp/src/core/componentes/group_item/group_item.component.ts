import { AfterViewInit, ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { GroupsService } from 'core/services/groups/groups.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { Group } from 'modelo/group/Group';
import { GroupsRssFeeds } from 'modelo/group/GroupsRssFeeds';
import { RssFeed } from 'modelo/rssFeed/RssFeed';

declare const $;

@Component({
  selector: 'group-item',
  templateUrl: './group_item.component.html'
})
export class Group_itemComponent implements AfterViewInit, OnDestroy, OnInit {

    isClassVisible = false;
    errorMessage = '';
    result: any;
    rssFeedAdd = false;
    rssFeedAdding = false;
    rssFeedError = false;

    @Input()
    group: Group;
    rssFeed: RssFeed = new RssFeed();

    @Input()
    groupType: any;

    @Input()
    index: number;

    @Output() sendGroup: EventEmitter<Group> = new EventEmitter<Group>();
    @Output() deleteGroup: EventEmitter<Group> = new EventEmitter<Group>();

    constructor(
      private groupService: GroupsService,
      private profileService: ProfilesService,
      private changeDetectorRef: ChangeDetectorRef
    ) {}

    ngOnInit() {
      this.groupService.groupType = this.groupType;
    }

    ngAfterViewInit(): void {
      if (this.group.idGroup === this.groupService.editingGroupId) {
        this.isClassVisible = true;
        this.changeDetectorRef.detectChanges();
      }
    }

    addRssFeed(idGroup : number) {
      this.groupService.editingGroupId = this.group.idGroup;

      if (this.groupService.editingGroupIds != null) {
        if (this.groupService.editingGroupIds.indexOf(this.group.idGroup) === -1) {
            this.groupService.editingGroupIds.push(this.group.idGroup);
            localStorage.setItem('editingGroupIds', JSON.stringify(this.groupService.editingGroupIds));
        }
      }

      this.rssFeedAdding = true;
      this.rssFeedError = false;
      let alreadyExists = false;

      if (this.group.groupsObject != null) {
        if (this.group.groupsObject.length > 0) {

          let groupsRssFeeds : GroupsRssFeeds[] = this.group.groupsObject;

          groupsRssFeeds.map(fav => {
            if (this.rssFeed.url == fav.rssFeed.url) {
              alreadyExists = true;
            }
          });
        }
      }

      if (!alreadyExists) {
        this.groupService.currentIndex = this.index;
        this.groupService.addRssFeedOnGroup(this.group, this.rssFeed)
          .subscribe(
          result => this.onInsertRssFeed(result),
          error => this.errorOnInsertRssFeed(error));
      } else {

        this.rssFeed.url = "";
        let groupId = this.group.idGroup;

        $('#alertRssExists_' + groupId).show()
        
        setTimeout(function() {
          this.rssFeedError = false;
          $('#alertRssExists_' + groupId).hide();
        }, 4000);

        this.rssFeedAdd = false;
        this.rssFeedError = true;
        this.rssFeedAdding = false;
      }
    }

    onInsertRssFeed(result: Object[]) {
      if (!result) {
        console.log('Houve um erro ao inserir rss feed no grupo');
      } else {
        // currentGroups são usados nas modais pois não há 
        // possibilidade de usar observable corretamente lá
        this.groupService.currentGroups = result[0] as Group[];
        // Atualiza source dos grupos para fazer o binding com o mychannels
        this.groupService.updateRssGroupSource();
        //  Atualiza os sources de feed e group para mostralos 
        // no componente rssfeed
        this.showRssFeedContent(result[1] as RssFeed);
        // flag do ver todos
        this.groupService.checkAllBtnSelected = false;
        this.rssFeedAdd = true;
        this.rssFeedError = false;
        this.rssFeedAdding = false;
        this.isClassVisible = true;

        this.groupService.waitingSource.next(false);

        this.changeDetectorRef.detectChanges();
      }
    }

    showRssFeedContent(rssFeed: RssFeed) {
        this.groupService.loadingSource.next(true);

        this.groupService.getRssFeedMessages(rssFeed).subscribe (
          result => this.onGetRssFeedGroups(result),
          error => console.log(error)
        );
      }

    onGetRssFeedGroups(result: RssFeed) {
      if (!result) {
        this.errorMessage = 'Houve ao recuperar rss feed messages';
      } else {
        this.rssFeed = result;

        var groupsRssFeeds: GroupsRssFeeds = new GroupsRssFeeds;
        groupsRssFeeds.group = this.group;
        groupsRssFeeds.rssFeed = this.rssFeed;
        this.group.groupsObject.push(groupsRssFeeds);

        this.groupService.addRssFeedOnSource(this.rssFeed);
        this.groupService.currentGroupSource.next(this.group);
        this.groupService.loadingSource.next(false);
      }
    }

    errorOnInsertRssFeed(error: any) {
        this.rssFeed.url = "";
        let groupId = this.group.idGroup;

        $('#alertInvalidRss_' + groupId).show()
        
        setTimeout(function() {
          $('#alertInvalidRss_' + groupId).hide();
        }, 4000);

        this.rssFeedAdd = false;
        this.rssFeedError = true;
        this.rssFeedAdding = false;
    }

    editGroup(groupsProfiles : any) {
      this.profileService.lockCheck = true;
      this.sendGroup.emit(this.group);
    }

    onSearchGroup(result: any) {
      if (!result) {
        this.errorMessage = 'Ops! Something went wrong recovering groups';
        // this.errorMessage = 'Houve um erro ao recuperar grupo';
      } else {
        this.result = result[0];
      }
    }

    confirmDeleteGroup(group) {
      this.deleteGroup.emit(this.group);
    }

    ngOnDestroy() {
      this.profileService.lockCheck = true;
    }
}
