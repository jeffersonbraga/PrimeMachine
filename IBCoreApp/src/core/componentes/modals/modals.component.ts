import { ModalService } from './../../services/modal/modal.service';
import { GenericModalComponent } from './../generic-modal/generic-modal.component';
import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
    selector: 'modals',
    templateUrl: './modals.component.html'
})
export class ModalsComponent implements OnInit {
    manageChannelsComponentClassName = 'ManageChannelsComponent';
    groupsManagementComponentClassName = 'Groups_managementComponent';
    manageSettingsComponentClassName = 'SettingsManagementComponent';
    manageTeamComponentClassName = 'TeamManagementComponent';
    profileReconnectComponentClassName = 'ProfileReconnectComponent';
    tutorialComponentClassName = 'TutorialComponent';
    manageScheduleComponentClassName = 'ScheduleAuthComponent';
    videoThumbnailComponentClassName = 'VideoThumbnailComponent';
    unlockReportsComponentClassName = 'UnlockReportsComponent';
    componentClassName = 'AddStreamsComponent';
    helloWoopComponentClassName = 'HelloWoopComponent';
    viewRssFeedComponentClassName = 'ViewRssFeedComponent';

    // Criar uma variável ViewChild para cada modal no html. Deve ser o mesmo nome do #
    @ViewChild('channelsModal')
    channelsModal: GenericModalComponent;

    @ViewChild('settingsManagementModal')
    settingsManagementModal: GenericModalComponent;

    @ViewChild('groupsManagementModal')
    groupsManagementModal: GenericModalComponent;

    @ViewChild('teamManagementModal')
    teamManagementModal: GenericModalComponent;

    @ViewChild('profileReconnectModal')
    profileReconnectModal: GenericModalComponent;

    @ViewChild('tutorialModal')
    tutorialModal: GenericModalComponent;

    @ViewChild('scheduleAuthModal')
    scheduleAuthModal: GenericModalComponent;

    @ViewChild('unlockReportsModal')
    unlockReportsModal: GenericModalComponent;

    @ViewChild('videoThumbnailModal')
    videoThumbnailModal: GenericModalComponent;

    @ViewChild('addStreamsModal')
    addStreamsModal: GenericModalComponent;

    @ViewChild('helloWoopModal')
    helloWoopModal: GenericModalComponent;

    @ViewChild('viewRssFeedModal')
    viewRssFeedModal: GenericModalComponent;

    constructor(
        private modalService: ModalService
    ) { }

    ngOnInit() {
        this.modalService.modalSelected.subscribe(
            modal => {
                this[modal].showModal();
                this.modalService.currentModal = this[modal];
            }
        );
    }
}
