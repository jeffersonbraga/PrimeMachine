import { BlankComponent } from './../blank-component/blank.component';
import {
    Component,
    ComponentFactoryResolver,
    Input,
    OnInit,
    ReflectiveInjector,
    ViewChild,
    ViewContainerRef,
} from '@angular/core';
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
import { VideoThumbnailComponent } from 'views/compose-post/videoThumbnail.component';
import { TutorialComponent } from '../tutorial-modal/tutorial-modal.component';

// import { ConfirmDialog } from './../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'dynamic-modal-content',
  templateUrl: './dynamic-modal-content.component.html',
  styleUrls: ['./dynamic-modal-content.component.css'],
  entryComponents: [
    ManageChannelsComponent, 
    Groups_managementComponent, 
    Edit_groupsComponent, 
    AddFacebookChannellsComponent, 
    AddInstagramChannelComponent, 
    DeleteChannelComponent,
    DeleteGroupComponent, 
    SettingsManagementComponent,
    TeamManagementComponent,
    ProfileReconnectComponent,
    ManageRssfeedsComponent,
    ViewRssFeedComponent,
    AddStreamsComponent,
    CancelSubscriptionComponent,
    HelloWoopComponent,
    UnlockReportsComponent,
    VideoThumbnailComponent,
    ScheduleAuthComponent,
    BlankComponent,
    TutorialComponent
  ], // Reference to the components must be here in order to dynamically create them
})
export class DynamicModalContentComponent implements OnInit {

  currentComponent = null;
  @ViewChild('dynamicModalContentContainer', { read: ViewContainerRef }) dynamicComponentContainer: ViewContainerRef;

  constructor(private resolver: ComponentFactoryResolver) {

  }

  ngOnInit() {
  }

  // component: Class for the component you want to create
  // inputs: An object with key/value pairs mapped to input name/input value
  @Input() set componentData(data: {component: any, inputs: any }) {
    if (!data) {
      return;
    }

    // Inputs need to be in the following format to be resolved properly
    let inputProviders = Object.keys(data.inputs).map((inputName) => {return {provide: inputName, useValue: data.inputs[inputName]};});
    let resolvedInputs = ReflectiveInjector.resolve(inputProviders);
    
    // We create an injector out of the data we want to pass down and this components injector
    let injector = ReflectiveInjector.fromResolvedProviders(resolvedInputs, this.dynamicComponentContainer.parentInjector);
    
    // We create a factory out of the component we want to create
    let factory = this.resolver.resolveComponentFactory(data.component);
    
    // We create the component using the factory and the injector
    let component = factory.create(injector);
    
    // We insert the component into the dom container
    this.dynamicComponentContainer.insert(component.hostView);
    
    // We can destroy the old component is we like by calling destroy
    if (this.currentComponent) {
      this.currentComponent.destroy();
    }
    
    this.currentComponent = component;
  }

}
