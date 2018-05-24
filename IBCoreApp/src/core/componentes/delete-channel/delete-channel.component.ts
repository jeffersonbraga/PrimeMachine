import { Component, Injector, OnInit } from '@angular/core';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { ControlService } from 'core/services/control/control.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { Profile } from 'modelo/profile/Profile';

@Component({
  selector: 'delete-channel',
  templateUrl: './delete-channel.component.html',
  styleUrls: ['./delete-channel.component.css']
})
export class DeleteChannelComponent implements OnInit {

  profile: Profile;

  currentModal : GenericModalComponent;
  returnComponent: string;

  checkedProfilesAux = [];

  constructor(
    private profileService: ProfilesService, 
		private controlApp: ControlService,
    private injector: Injector) {
      this.currentModal = this.injector.get("currentModal");
	}

  ngOnInit() {
    this.profile = this.profileService.currentProfileToDelete;
    this.returnComponent = this.profileService.returnComponent;

    // Backup dos checked profiles
    for (let i = 0; i < this.profileService.checkedProfiles.length; i++) {
        this.checkedProfilesAux[i] = this.profileService.checkedProfiles[i];
    }

    // Zera checked profiles para não marca-los no manage channels e reatribui valores no onDestroy
    this.profileService.checkedProfiles.splice(0, this.profileService.checkedProfiles.length);
  }

  cancel() {

    // Limpa checked profiles
    this.profileService.checkedProfiles.splice(0, this.profileService.checkedProfiles.length);

    // Volta backup dos checked profiles
    for (let i = 0; i < this.checkedProfilesAux.length; i++) {
        this.profileService.checkedProfiles[i] = this.checkedProfilesAux[i];
    }

    this.currentModal.setDynamicModalContent(this.returnComponent);
  }

  delete() {
    this.profileService.deleteProfile(this.profile).subscribe(
      result => this.onDelete(result),
      error => console.log(error)
    );
  }

  onDelete(result) {
    this.profileService.removeProfileFromSource(this.profile);

    // Volta backup dos checked profiles
    for (let i = 0; i < this.checkedProfilesAux.length; i++) {
        if (this.checkedProfilesAux[i].idProfile !== this.profile.idProfile) {
          this.profileService.checkedProfiles[i] = this.checkedProfilesAux[i];
        } else {
          this.checkedProfilesAux.splice(i, 1);
          i--;
        }
    }

    this.profileService.profilesCheckedSource.next(this.profileService.checkedProfiles);

    this.currentModal.setDynamicModalContent(this.returnComponent);
  }
}
