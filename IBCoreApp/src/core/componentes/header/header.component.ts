import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ControlService } from 'core/services/control/control.service';
import { LogoutService } from 'core/services/logout/logout.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { QuickviewService } from 'core/services/quickview/quickview.service';
import { Profile } from 'modelo/profile/Profile';
import { Subscription } from 'rxjs/subscription';

import { GroupsService } from './../../services/groups/groups.service';
import { ModalService } from './../../services/modal/modal.service';

declare const $;

@Component({
    selector: 'header-root',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {
    public user: any;
    admins: any;
    users: any;
    hasAvatar: boolean;
    showMenuReconnect: boolean;

    profilesToReconnect: Profile[];

    existsUnread: boolean;

    @Input()
    quickviewState = false;

    subscriptionProfile: Subscription;
    subscriptionAccount: Subscription;

    constructor(
        public ControlService: ControlService,
        private QuickviewService: QuickviewService,
        private ProfilesService: ProfilesService,
        private logoutService: LogoutService,
        private groupsService: GroupsService,
        private router: Router,
        private modalService: ModalService
    ) {}

    ngOnInit() {
        this.subscriptionAccount = this.ControlService.user$
            .subscribe(
                user => {
                    this.user = user;
                    if (this.user) {
                        this.checkAvatar();
                    }
                }
            );

        this.subscriptionProfile = this.ProfilesService.profilesToReconnect$
            .subscribe(
                profiles => {
                    this.profilesToReconnect = profiles;
                    this.checkForShowMenu();
                    this.showAlertProfilesToReconnect();
                }
            );

        this.QuickviewService.unread$
            .subscribe(
                existsUnread => this.existsUnread = existsUnread
            );
    }

    checkForShowMenu() {
        this.showMenuReconnect = false;
        this.profilesToReconnect.filter(
            (profile: Profile) => {
                if (profile.isTokenValid === 'F') {
                    this.showMenuReconnect = true;
                }
            }
        );
    }

    showAlertProfilesToReconnect() {
        let find = false;
        const name = 'profilesToReconnectMessage' + '=';
        const ca = document.cookie.split(';');
        for (let i = 0; i < ca.length; i++) {
            let c = ca[i];
            while (c.charAt(0) === ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) === 0) {
                find = true;
            }
        }

        if (this.profilesToReconnect.length && !find) {
            const options = {
                message: `<a onclick="this.parentElement.parentElement.style.display='none'" 
                    href="javascript:window.angularComponentRef.zone.run(
                        () => window.angularComponentRef.QuickViewComponent.openReconnectModal()
                    )" class="alert-link">
                        There are profiles to be reconnected
                    </a>`,
                style: 'bar',
                timeout: 8000,
                position: 'top',
                type: 'danger'
            };

            $('body').pgNotification(options).show();

            document.cookie = 'profilesToReconnectMessage=true';
        }
    }

    logout() {
        this.logoutService.logout().subscribe(
            result => {
                this.logoutService.onLogout().subscribe(
                    result => this.onLogout(result),
                    error => console.log(error)
                );
            }
        );
    }

    onLogout(result: any) {
        this.ControlService.setUser(null);
        window.open('https://woopsocial.com/home', '_self');
    }

    openQuickview() {
        this.QuickviewService.openQuickview();
    }


    checkAvatar(): any {
        if (this.user.avatar) {
            this.hasAvatar = true;
        }
    }

    toggleSidebar() {
        $('[data-pages="sidebar"]').data('pg.sidebar').toggleSidebar();
    }

    setGroupType() {
        this.groupsService.groupType = 'P';
        this.groupsService.fromSettingsGroup = true;
    }

    openChannelsModal() {
        this.modalService.showChannelsModal();
    }

    openSettingsModal() {
        this.modalService.showSettingsManagementModal();
    }

    openReconnectModal () {
        this.modalService.showProfileReconnectModal();
    }

    openGroupsModal() {
        this.modalService.showGroupsManagementModal();
    }

    openTeamModal() {
        this.modalService.showTeamManagementModal();
    }

    openTutorialModal() {
        this.modalService.showTutorialModal();
    }

    ngOnDestroy(): void {

        if (this.subscriptionAccount) {
            this.subscriptionAccount.unsubscribe();
        }

        if (this.subscriptionProfile) {
            this.subscriptionProfile.unsubscribe();
        }
    }
}
