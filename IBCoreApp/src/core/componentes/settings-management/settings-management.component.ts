import { ChangeDetectorRef, Component, ElementRef, forwardRef, Inject, Injector, OnInit, ViewChild } from '@angular/core';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';
import { ControlService } from 'core/services/control/control.service';
import { TimezoneService } from 'core/services/timezone/timezone.service';
import { UploadService } from 'core/services/upload/upload.service';
import { UserService } from 'core/services/user/user.service';
import { Account } from 'modelo/account/Account';
import { User } from 'modelo/user/User';

import { GenericModalComponent } from './../generic-modal/generic-modal.component';

declare const $: any;

@Component({
    selector: 'settings-management',
    templateUrl: './settings-management.component.html',
    styleUrls: ['./settings-management.component.css']
})

export class SettingsManagementComponent implements OnInit {
    timezones: any;
    avatarChanged = false;

    /*Inputs */
    password: string;
    passwordRepeat: string;
    account: Account;
    user: User;
    hasAvatar: boolean;
    previewAvatar: string;

    hasError = false;
    errorMessage = '';

    fileList: FileList;

    validated = false;

    @ViewChild('fileInput') fileInput: ElementRef;

    currentModal: GenericModalComponent;

    constructor(
        private BreadcrumbsService: BreadcrumbsService,
        private TimezoneService: TimezoneService,
        private ref: ChangeDetectorRef,
        private ControlService: ControlService,
        private UploadService: UploadService,
        private UserService: UserService,
        private injector: Injector,
        @Inject(forwardRef(() => GenericModalComponent)) private _parent: GenericModalComponent
    ) {
        this.BreadcrumbsService.initializeBreadcrumb();
    }

    ngOnInit() {
        this.user = Object.assign({}, this.ControlService.getUser());
        this.account = this.user.account;

        if (this.user.avatar) {
            this.hasAvatar = true;
        }

        this.currentModal = this.injector.get('currentModal');

        this.getTimezones();
        this.BreadcrumbsService.addBreadcrumb('Minha Conta', '#');
        this.validate();
    }

    getTimezones() {
        this.TimezoneService.getTimezone()
            .subscribe(
                result => this.setTimezones(result),
                error => console.log(error)
            );
    }

    setTimezones(result: any) {
        if (!result) {
            console.log('Não foi possível recuperar fuso horários.');
        } else {
            this.timezones = result[0];
            this.ref.detectChanges();
            $('#timezone').select2({
                dropdownParent: $('#settings-modal')
            });
            $('#timezone').on(
                'change',
                (e: any) => this.user.account.timezone.idTimezone = parseInt(e.target.value, 10)
            );
        }
    }

    openFileUpload() {
        this.fileInput.nativeElement.click();
    }

    changeAvatar(fileInput: any) {
        if (fileInput.files && fileInput.files[0]) {
            this.fileList = fileInput.files;
            this.avatarChanged = true;
            const reader = new FileReader();

            reader.onload = (e: any) => {
                $('#preview-avatar').attr('src', e.target.result);
                this.previewAvatar = e.target.result;
            };

            reader.readAsDataURL(fileInput.files[0]);
        }
    }

    validate() {
        if (!this.user.name) {
            this.validated = false;
        } else if (this.password !== this.passwordRepeat) {
            this.validated = false;
        } else if (!this.user.email) {
            this.validated = false;
        } else {
            this.validated = true;
        }
    }

    close() {
        this._parent.hideModal();
    }

    save() {
        this.ControlService.setUser(this.user);
        this.ControlService.userSource.next(this.user);
        this.validate();

        if (this.validated) {
            if (this.fileList) {
                this.ControlService.uploadingAvatar = true;
                const file: File = this.fileList[0];
                this.UploadService.makeSingleFileRequest(this.ControlService.context_app + 'woopsocial/upload_avatar', file)
                    .subscribe(
                        response => {
                            this.ControlService.uploadingAvatar = false;
                            this.user.avatar = response[0];
                            this.updateUser();
                        }
                    );
            } else {
                this.updateUser();
            }
        }
    }

    setTimezone(timezone) {
        return this.timezones.filter(t => {
            if (t.idTimezone === timezone) {
                return t;
            }
        })[0];
    }

    updateUser() {
        if (this.validated) {
            if (this.password) {
                this.user.password = this.password;
            }

            this.user.account.timezone = this.setTimezone(this.user.account.timezone.idTimezone);

            this.ControlService.setUser(this.user);

            this.UserService.updateUser(this.user)
                .subscribe(
                    response => this.onUpdateUser(response)
                );
        }
    }

    onUpdateUser(response) {
        if (typeof response[0] === 'string') {
            this.showError(response[0]);
        } else {
            this.close();
        }
    }

    showError(msg) {
        this.hasError = true;
        this.errorMessage = msg;
    }

    hideError() {
        this.hasError = false;
        this.errorMessage = '';
    }

    cancelSubscription() {
        this.UserService.returnComponent = 'SettingsManagementComponent';
        this.currentModal.setDynamicModalContent('CancelSubscriptionComponent');
    }
}
