import { Component, Injector, Input, OnInit } from '@angular/core';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';
import { ControlService } from 'core/services/control/control.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { WindowRefService } from 'core/services/window/window-ref.service';
import { Profile } from 'modelo/profile/Profile';

@Component({
  selector: 'add-facebook-channells',
  templateUrl: './add-facebook-channells.component.html',
  styleUrls: ['./add-facebook-channells.component.css']
})
export class AddFacebookChannellsComponent implements OnInit {

  checkedProfiles: Profile[];
  profiles: Profile[];
  currentModal: GenericModalComponent;
  errorMessage: String;
  hasError: boolean;
  nativeWindow: any;

  checkedProfilesAux = [];

  @Input('disableBackButton')
  disableBackButton = false;

  @Input('singleSelect')
  singleSelect: boolean;

  constructor(
    private profileService: ProfilesService,
    private controlApp: ControlService,
    private injector: Injector,
    private winRef: WindowRefService,
    private breadcrumbsService: BreadcrumbsService) {

    this.profiles = [];
    this.checkedProfiles = this.profileService.getSelectedProfilesCrossNetwork();
    this.nativeWindow = winRef.getNativeWindow();
    this.currentModal = this.injector.get('currentModal');
    this.errorMessage = '';
    this.hasError = false;
  }

  ngOnInit() {
    this.profiles = this.profileService.profilesToConnect;

    // Backup dos checked profiles
    for (let i = 0; i < this.profileService.checkedProfiles.length; i++) {
        this.checkedProfilesAux[i] = this.profileService.checkedProfiles[i];
    }

    // Zera checked profiles para não marca-los no manage channels e reatribui valores no onDestroy
    this.profileService.checkedProfiles.splice(0, this.profileService.checkedProfiles.length);
  }

  hasInstagramProfile() {

    let hasInstagramProfile = false;

    for (const profile of this.checkedProfiles) {
      if (profile.type === Profile.INSTAGRAM) {
        hasInstagramProfile = true;
        break;
      }
    }

    return hasInstagramProfile;
  }

  addFacebookChannels() {

    if (!this.hasInstagramProfile()) {
      this.profileService.saveProfiles(this.checkedProfiles).subscribe(
        result => this.onAddFacebookChannels(result),
        error => {
              this.profileService.profilesToConnect = [];
              this.profileService.setSelectedProfilesCrossNetwork([]);
              this.showErrorMessage(error);
        }
      );
    } else {
      this.profileService.profilesToConnect = this.checkedProfiles;
      this.currentModal.setDynamicModalContent('AddInstagramChannelComponent');
    }

  }

  onAddFacebookChannels(result: any) {
    const tam = result.length;
    for (let i = 0; i < tam; i++) {
      this.profileService.addProfilesToSouce(result[i]);
    }

    this.profileService.profilesToConnect = [];
    this.profileService.setSelectedProfilesCrossNetwork([]);

    this.profileService.lockCheck = false;

    // Limpa checked profiles
    this.profileService.checkedProfiles.splice(0, this.profileService.checkedProfiles.length);

    // Volta backup dos checked profiles
    for (let i = 0; i < this.checkedProfilesAux.length; i++) {
        this.profileService.checkedProfiles[i] = this.checkedProfilesAux[i];
    }

    this.currentModal.setDynamicModalContent('ManageChannelsComponent');
    /*
    this.objectChecked.emit({
        checked: true,
        profile: null,
        revert: this
    });*/
  }

  cancel() {

    // Limpa checked profiles
    this.profileService.checkedProfiles.splice(0, this.profileService.checkedProfiles.length);

    // Volta backup dos checked profiles
    for (let i = 0; i < this.checkedProfilesAux.length; i++) {
        this.profileService.checkedProfiles[i] = this.checkedProfilesAux[i];
    }

    this.profileService.lockCheck = false;
    this.currentModal.setDynamicModalContent('ManageChannelsComponent');
    for (const profile of this.profileService.profilesToConnect) {
      this.profileService.removeCheckedProfile(profile);
    }
    if (this.profileService) {
      this.profileService.profilesToConnect = [];
    }
  }

  validate(event) {
    if (event) {
      this.addRemoveObjectChecked(event);
      this.profileService.validateStatusInsertProfile(this.checkedProfiles)
        .subscribe(
          result => this.validationSuccess(event),
          error => this.validationError(event, error)
        );
    }
  }

  validationSuccess(event) {
    this.hideErrorMessage();
    this.updateInstagramSource(event);
  }

  validationError(event, error) {
    event.revert.invalidateCheckButton();
    this.checkedProfiles.splice(this.checkedProfiles.indexOf(event.profile), 1);
    this.showErrorMessage(error);
    this.updateInstagramSource(event);
  }

  updateInstagramSource(event) {
    if (event.profile.type === 'I') {
      this.profileService.profilesCheckedInstagramSource.next(this.checkedProfiles.filter(
        p => p.type === 'I'
      ));
    }
  }

  addRemoveObjectChecked(event) {
    if (event.checked) {
      this.checkedProfiles.push(event.profile);
    } else {

      this.checkedProfiles.filter(profile => {

        if ((profile.idProfile === null) && (profile.idProfile === event.profile.idProfile)) {

            if (profile.networkId === event.profile.networkId) {
              this.checkedProfiles.splice(this.checkedProfiles.indexOf(profile), 1);
            }

        } else if (profile.idProfile === event.profile.idProfile) {
          this.checkedProfiles.splice(this.checkedProfiles.indexOf(profile), 1);
        }
      });
      //this.checkedProfiles.splice(this.checkedProfiles.indexOf(event.profile), 1);
    }
  }

  showErrorMessage(errorMessage: string) {
    this.errorMessage = errorMessage;
    this.hasError = true;
  }

  hideErrorMessage() {
    this.errorMessage = '';
    this.hasError = false;
  }
}
