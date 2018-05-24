import { Component, Injector, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';
import { ControlService } from 'core/services/control/control.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { Profile } from 'modelo/profile/Profile';
import { Subscription } from 'rxjs/subscription';

declare let $;

@Component({
  selector: 'add-instagram-channel',
  templateUrl: './add-instagram-channel.component.html',
  styleUrls: ['./add-instagram-channel.component.css']
})
export class AddInstagramChannelComponent implements OnInit, OnDestroy {

  @ViewChild('inputUserName') inputUserName: any;

  profileInserted: Profile;

  @Input('profilesToConnect')
  profilesToConnect: Profile[] = [];

  hasProfileInserted: boolean;

  hasSchedulingAuthenticated: boolean;

  isAuthenticating: boolean;

  errorMessage: String;

  hasError: boolean;

  nativeWindow: any;

  instagramAuthWindow: any;

  currentModal : GenericModalComponent;

  subscriptionForm: Subscription;

  timerSubscription: any;

  public authInstagramShedulingForm: FormGroup;

  @Input('cancelReset')
  cancelReset: boolean;

  @Input('disableBackButton')
  disableBackButton : boolean = false;

  constructor(
    private profileService: ProfilesService, 
    private controlApp: ControlService,
    private injector: Injector,
    public fb: FormBuilder,
    private breadcrumbsService: BreadcrumbsService) {

      this.currentModal = this.injector.get('currentModal');

      this.breadcrumbsService.addBreadcrumb('Adicionar Novo Instagram', '');
  }

  ngOnInit() {

    if(this.profileService.currentProfileToEdit != null) {
      this.profilesToConnect.push(this.profileService.currentProfileToEdit);
    } else {

      if(this.cancelReset) {
        this.profileService.profilesCheckedInstagram$.subscribe(
          profiles => {
            this.profilesToConnect = profiles;
          }
        );
      } else {
        this.profilesToConnect = this.profileService.profilesToConnect;
      }
    }

  }

  ngOnDestroy() {
    if (!this.cancelReset) {
      this.profileService.resetSelectionList();
    }
  }

  backToChannels() {
    this.currentModal.setDynamicModalContent('ManageChannelsComponent');
  }

}
