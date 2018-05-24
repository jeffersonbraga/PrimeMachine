import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Component, Injector, OnInit } from '@angular/core';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';

@Component({
    selector: 'tutorial-modal',
    templateUrl: './tutorial-modal.component.html',
    styleUrls: ['./tutorial-modal.component.css']
})
export class TutorialComponent implements OnInit {

    currentModal: GenericModalComponent;

    iframeUrl: SafeUrl;
    titleVideo: string;
    videoId: string;

    videos = {
        introduction: {
            title: 'Introduction',
            url: ''
        },
        addChannels: {
            title: 'How to add channels to your account',
            url: 'https://www.youtube.com/embed/EjT3b2xxbAo'
        },
        addInstagramChannels: {
            title: 'How to add Instagram Channels',
            url: 'https://www.youtube.com/embed/r19i8QyNfs4'
        },
        manageChannelGroup: {
            title: 'How to manage your channel\'s groups',
            url: 'https://www.youtube.com/embed/664MqJjxInU'
        },
        addStreams: {
            title: 'How to use Streams',
            url: 'https://www.youtube.com/embed/gw-teKQsWO4'
        },
        createImageOrVideoPost: {
            title: 'How to add an image or video to a post',
            url: 'https://www.youtube.com/embed/yscb8a3Kxtk'
        },
        createPostLink: {
            title: 'How to embed a link into a post',
            url: 'https://www.youtube.com/embed/mkdkoeP13NY'
        },
        createInstagramStories: {
            title: 'How to create an Instagram Stories',
            url: 'https://www.youtube.com/embed/bn7VY_r-o-k'
        },
        manageTeamMembers: {
            title: 'How to manage your team members',
            url: 'https://www.youtube.com/embed/GMGJU3jnCjo'
        },
    };

    constructor(
        private injector: Injector,
        private breadcrumbsService: BreadcrumbsService,
        private domSanitizer: DomSanitizer
    ) {
        this.currentModal = this.injector.get('currentModal');

        this.breadcrumbsService.initializeBreadcrumb();
    }

    ngOnInit(): void {
        this.breadcrumbsService.addBreadcrumb('Tutorials', '');

        this.openVideo('introduction');

        console.log('showed tutorial');
    }

    openVideo(videoName) {
        if (this.videoId !== videoName) {
            this.videoId = videoName;
            this.titleVideo = this.videos[videoName].title;
            this.iframeUrl = this.domSanitizer.bypassSecurityTrustResourceUrl(this.videos[videoName].url);
        }
    }
}
