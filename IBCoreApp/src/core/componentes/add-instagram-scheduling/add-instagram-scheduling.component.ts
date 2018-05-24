import { Component, Injector, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';
import { ControlService } from 'core/services/control/control.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { WindowRefService } from 'core/services/window/window-ref.service';
import { Profile } from 'modelo/profile/Profile';
import { Subscription } from 'rxjs/subscription';

@Component({
  selector: 'add-instagram-scheduling',
  templateUrl: './add-instagram-scheduling.component.html',
  styleUrls: ['./add-instagram-scheduling.component.css']
})
export class AddInstagramSchedulingComponent implements OnInit, OnDestroy {

  @ViewChild('inputUserName') inputUserName: any;

  @Input()
  profileInserted: Profile;

  @Input()
  fromAddChannels = true;

  @Input()
  firstTimeAuthentication = false;

  profilesToConnect: Profile[] = [];
  hasSchedulingAuthenticated: boolean;
  isAuthenticating: boolean;
  errorMessage: String;
  hasError: boolean;

  nativeWindow: any;
  instagramAuthWindow: any;

  subscriptionForm: Subscription;

  timerSubscription: any;

  public authInstagramShedulingForm: FormGroup;

  constructor(
    private profileService: ProfilesService,
    private controlApp: ControlService,
    private winRef: WindowRefService,
    private injector: Injector,
    public fb: FormBuilder,
    private breadcrumbsService: BreadcrumbsService) { }

  ngOnInit() {

    if (this.profileInserted.isSchedulingAuthenticated === 'T') {
      this.hasSchedulingAuthenticated = true;
    } else {
      this.hasSchedulingAuthenticated = false;
    }

    this.isAuthenticating = false;

    this.authInstagramShedulingForm = this.fb.group({
      screenName: [this.profileInserted.screenName, <any>Validators.required],
      instagramPassword: [this.profileInserted.instagramPassword, <any>Validators.required]
    });

    this.subcribeToFormChanges();
  }

  ngOnDestroy() {
    if (this.profileService) {
      this.profileService.currentProfileToEdit = null;
    }

    if (this.subscriptionForm) {
      this.subscriptionForm.unsubscribe();
    }
  }

  addInstagramChannel(profiles: any) {
    this.profileService.saveProfiles(profiles).subscribe(
      result => this.onAddInstagramChannel(result),
      error => this.showErrorMessage(error)
    );
  }

  onAddInstagramChannel(profiles: Profile[]) {
    if (profiles.length > 0) {
      this.profileInserted = profiles[0];
      this.onInstagramShedulingOk();
      if (this.fromAddChannels) {
        this.profileService.addProfilesToSouce(profiles[0]);
      }
    }
  }

  onInstagramShedulingOk() {

    this.hasSchedulingAuthenticated = true;
    this.isAuthenticating = false;
    this.inputUserName.nativeElement.focus();
  }

  doAuthInstagramSheduling(profile: Profile, isValid: boolean) {

    if (isValid) {

      this.profileInserted.screenName = profile.screenName;
      this.profileInserted.instagramPassword = profile.instagramPassword;

      this.isAuthenticating = true;

      if(this.profileInserted.idProfile != null) {

        if(this.firstTimeAuthentication) {

          this.profileService.authenticateInstagramSheduling(this.profileInserted).subscribe(
            result => {
              this.onDoAuthInstagramSheduling(result);
            },
            error => {
              this.showErrorMessage(error);
              this.isAuthenticating = false;
            }
          );

        } else {

          this.profileService.editInstagramSheduling(this.profileInserted).subscribe(
            result => {
              this.onDoAuthInstagramSheduling(result);
            },
            error => {
              this.showErrorMessage(error);
              this.isAuthenticating = false;
            }
          );
        }

      } else {

        this.profileService.insertInstagramSheduling(this.profileInserted).subscribe(
          result => {
            this.onDoAuthInstagramSheduling(result);
          },
          error => {
            this.showErrorMessage(error);
            this.isAuthenticating = false;
          }
        );
      }
    }
  }

  onDoAuthInstagramSheduling(profile: Profile) {
    
    this.profileInserted = profile;

    if(this.profileInserted.idProfile == null) {

      this.profilesToConnect.push(this.profileInserted);
      this.addInstagramChannel(this.profilesToConnect);

    } else {

      this.onInstagramShedulingOk();

      this.profileService.updateProfileScheduling(profile);
    }
  }

  subcribeToFormChanges() {

    this.subscriptionForm = this.authInstagramShedulingForm.valueChanges.subscribe(value => {
      this.hasSchedulingAuthenticated = false;
    });
  }

  showErrorMessage(error) {
    this.errorMessage = error;
    this.hasError = true;
  }

  hideErrorMessage() {
    this.errorMessage = '';
    this.hasError = false;
  }

}
