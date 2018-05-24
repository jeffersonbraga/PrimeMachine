import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { Injectable, EventEmitter } from '@angular/core';

@Injectable()
export class ModalService {
    modalSelected: EventEmitter<string> = new EventEmitter<string>();

    CHANNELSMODAL = 'channelsModal';
    SETTINGSMANAGEMENTMODAL = 'settingsManagementModal';
    GROUPSMANAGEMENTMODAL = 'groupsManagementModal';
    TEAMMANAGEMENTMODAL = 'teamManagementModal';
    PROFILERECONNECTMODAL = 'profileReconnectModal';
    TUTORIALMODAL = 'tutorialModal';
    SCHEDULEAUTHMODAL = 'scheduleAuthModal';
    UNLOCKREPORTSMODAL = 'unlockReportsModal';
    VIDEOTHUMBNAILMODAL = 'videoThumbnailModal';
    ADDSTREAMSMODAL = 'addStreamsModal';
    HELLOWOOPMODAL = 'helloWoopModal';
    VIEWRSSFEEDMODAL = 'viewRssFeedModal';

    currentModal: GenericModalComponent;

    showModal(modalName) {
        this.modalSelected.emit(modalName);
    }

    showChannelsModal() {
        this.modalSelected.emit(this.CHANNELSMODAL);
    }

    showSettingsManagementModal() {
        this.modalSelected.emit(this.SETTINGSMANAGEMENTMODAL);
    }

    showGroupsManagementModal() {
        this.modalSelected.emit(this.GROUPSMANAGEMENTMODAL);
    }

    showTeamManagementModal() {
        this.modalSelected.emit(this.TEAMMANAGEMENTMODAL);
    }

    showProfileReconnectModal() {
        this.modalSelected.emit(this.PROFILERECONNECTMODAL);
    }

    showTutorialModal() {
        this.modalSelected.emit(this.TUTORIALMODAL);
    }

    showScheduleAuthModal() {
        this.modalSelected.emit(this.SCHEDULEAUTHMODAL);
    }

    showUnlockReportsModal() {
        this.modalSelected.emit(this.UNLOCKREPORTSMODAL);
    }

    showVideoThumbnailModal() {
        this.modalSelected.emit(this.VIDEOTHUMBNAILMODAL);
    }

    showAddStreamsModal() {
        this.modalSelected.emit(this.ADDSTREAMSMODAL);
    }

    showHelloWoopModal() {
        this.modalSelected.emit(this.HELLOWOOPMODAL);
    }

    showViewRssFeedModal() {
        this.modalSelected.emit(this.VIEWRSSFEEDMODAL);
    }
}
