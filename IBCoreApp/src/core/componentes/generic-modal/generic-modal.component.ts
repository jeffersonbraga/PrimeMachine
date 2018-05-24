import { TutorialComponent } from './../tutorial-modal/tutorial-modal.component';
import { BlankComponent } from './../blank-component/blank.component';
import { Component, Input, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { AddFacebookChannellsComponent } from 'core/componentes/add-facebook-channells/add-facebook-channells.component';
import { AddInstagramChannelComponent } from 'core/componentes/add-instagram-channel/add-instagram-channel.component';
import { AddStreamsComponent } from 'core/componentes/add-streams/add-streams.component';
import { CancelSubscriptionComponent } from 'core/componentes/cancel-subscription/cancel-subscription.component';
import { DeleteChannelComponent } from 'core/componentes/delete-channel/delete-channel.component';
import { DeleteGroupComponent } from 'core/componentes/delete-group/delete-group.component';
import { Edit_groupsComponent } from 'core/componentes/edit_groups/edit_groups.component';
import { Groups_managementComponent } from 'core/componentes/groups_management/groups_management.component';
import { HelloWoopComponent } from 'core/componentes/hello-woop/hello-woop.component';
import { ManageChannelsComponent } from 'core/componentes/manage-channels/manage-channels.component';
import { ManageRssfeedsComponent } from 'core/componentes/manage-rssfeeds/manage-rssfeeds.component';
import { ProfileReconnectComponent } from 'core/componentes/profile-reconnect/profile-reconnect.component';
import { ScheduleAuthComponent } from 'core/componentes/schedule-auth/schedule-auth.component';
import { SettingsManagementComponent } from 'core/componentes/settings-management/settings-management.component';
import { TeamManagementComponent } from 'core/componentes/team-management/team-management.component';
import { UnlockReportsComponent } from 'core/componentes/unlock-reports/unlock-reports.component';
import { ViewRssFeedComponent } from 'core/componentes/view-rssfeed/view-rssFeed.component';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { VideoThumbnailComponent } from 'views/compose-post/videoThumbnail.component';

@Component({
  selector: 'generic-modal',
  templateUrl: './generic-modal.component.html',
  styleUrls: ['./generic-modal.component.css'],
})
export class GenericModalComponent implements OnInit, OnDestroy {

  @ViewChild('genericModal') public modal: ModalDirective;

  @Input('headerTitle') headerTitle: string;
  @Input('componentClassName') componentClassName: string;
  @Input('data') data: string;
  @Input('idModal') idModal: string;

  @Input()
  currentModal: string; //GenericModalComponent;

  @Input()
  slideRight: boolean;

  @Input()
  slideUp: boolean;

  @Input() componentData: any;

  components: Object = {
    ManageChannelsComponent: ManageChannelsComponent,
    Groups_managementComponent: Groups_managementComponent,
    Edit_groupsComponent: Edit_groupsComponent,
    VideoThumbnailComponent: VideoThumbnailComponent,
    AddFacebookChannellsComponent: AddFacebookChannellsComponent,
    AddInstagramChannelComponent: AddInstagramChannelComponent,
    DeleteChannelComponent: DeleteChannelComponent,
    DeleteGroupComponent: DeleteGroupComponent,
    SettingsManagementComponent: SettingsManagementComponent,
    TeamManagementComponent: TeamManagementComponent,
    ProfileReconnectComponent: ProfileReconnectComponent,
    ManageRssfeedsComponent: ManageRssfeedsComponent,
    ViewRssFeedComponent: ViewRssFeedComponent,
    AddStreamsComponent: AddStreamsComponent,
    CancelSubscriptionComponent: CancelSubscriptionComponent,
    HelloWoopComponent: HelloWoopComponent,
    UnlockReportsComponent: UnlockReportsComponent,
    ScheduleAuthComponent: ScheduleAuthComponent,
    BlankComponent: BlankComponent,
    TutorialComponent: TutorialComponent
  }

  constructor(private profileService: ProfilesService) {
  }

  ngOnInit() {
  }

  showModal(): void {
    this.setDynamicModalContent(this.componentClassName);
    this.modal.show();
  }

  hideModal(): void {
    this.componentData = {
      component: this.components['BlankComponent'],
      inputs: {
        currentModal: this.currentModal
      }
    };

    this.profileService.lockCheck = false;
    this.modal.hide();
  }

  setDynamicModalContent(componentClassName: string): void {

    this.componentData = {
      component: this.components[componentClassName],
      inputs: {
        currentModal: this.currentModal
      }
    };
  }

  ngOnDestroy(): void {
    this.profileService.lockCheck = false;
  }
}
