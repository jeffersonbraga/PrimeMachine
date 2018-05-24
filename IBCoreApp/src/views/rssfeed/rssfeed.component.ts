import { ModalService } from './../../core/services/modal/modal.service';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { CheckUrlService } from 'core/services/checkUrl/checkUrl.service';
import { ControlService } from 'core/services/control/control.service';
import { GroupsService } from 'core/services/groups/groups.service';
import { Group } from 'modelo/group/Group';
import { RssFeed } from 'modelo/rssFeed/RssFeed';
import { RssFeedMessage } from 'modelo/rssFeed/RssFeedMessage';
import { Subscription } from 'rxjs/Subscription';

declare const $;

@Component({
    moduleId: module.id,
    selector: 'rssfeed',
    templateUrl: './rssfeed.component.html',
    styleUrls: ['./rssfeed.component.css']
})

export class RssfeedComponent implements OnInit, OnDestroy {
    subscriptionGroup: Subscription;
    subscriptionRss: Subscription;

    groupType: any = 'R';

    headerTitleGroups: string = 'Gerenciar Grupos';

    rssFeeds: RssFeed[] = [];

    rssFeedsExibir: RssFeedMessage[] = [];

    loading: boolean = true;
    waiting: boolean = true;
    protected timer;

    group: Group;

    checkAllBtnSelected: boolean = false;

    rssFeedMsgImgLink: string = "";

    errorMessage: string = '';

    public REG_EXP_URL: string = "[-a-zA-Z0-9@:%_\\+.~#?&//=]{2,256}\\.[a-z]{2,4}\\b(\\/[-a-zA-Z0-9@:%_\\+.~#?&//=]*)?";

    @ViewChild('virtualScrollFeed')
    virtualScrollFeed: any;

    public showCheckAllBtn: boolean = true;

    constructor(private checkUrl: CheckUrlService,
        private groupService: GroupsService,
        private controlApp: ControlService,
        private router: Router,
        private route: ActivatedRoute,
        private modalService: ModalService
    ) {

        console.log("RssfeedComponent");
    }

    ngOnInit(): void {
        this.waiting = true;

        this.subscriptionRss = this.groupService.rssfeed$.subscribe(
            result => this.onSelectRssFeed(result),
            error => console.log(error)
        );

        this.groupService.loading$.subscribe(
            loading => this.loading = loading
        );

        this.groupService.waiting$.subscribe(
            waiting => this.waiting = waiting
        );
    }

    onSelectRssFeed(rssFeeds: RssFeed[]) {
        this.subscriptionGroup = this.groupService.group$.subscribe(
            result => this.group = result,
            error => console.log(error)
        );

        this.rssFeeds = rssFeeds;
        // this.rssFeedsExibir = this.rssFeeds[0].rssFeedMessage.slice(0, 20);
        this.checkAllBtnSelected = this.groupService.checkAllBtnSelected;
        this.groupService.loadingSource.next(false);
    }

    checkIsLink(url: string): any {
        this.checkUrl.checkValidUrl(url).subscribe(
            result => this.onCheckIsLink(result),
            error => console.log(error));
    }

    onCheckIsLink(result: any) {
        if (!result) {
            console.log('Erro checar URL::::');
            this.rssFeedMsgImgLink = "";
        } else {

            if ((result.images != null) && (result.images != undefined)) {
                this.rssFeedMsgImgLink = result.images["0"].image;
            } else {
                this.rssFeedMsgImgLink = "";
            }
        }
    }

    sendLinkToCompose(url: string) {
        this.router.navigate(['/home/compose', { url: url }], { relativeTo: this.route, skipLocationChange: true });
    }

    rssFeedPreview(itemMsg, itemRssFeed) {
        this.modalService.showViewRssFeedModal();

        if (this.checkAllBtnSelected) {
            (this.modalService.currentModal.componentData.component).setAttachment(itemMsg, itemMsg.rssFeed, this.group as Group);
        } else {
            (this.modalService.currentModal.componentData.component).setAttachment(itemMsg, itemRssFeed, this.group as Group);
        }
    }

    copyLink(link: string, idRssfeedMsg: number) {
        const textArea = document.createElement('textarea');

        textArea.style.position = 'fixed';
        textArea.style.width = '2em';
        textArea.style.height = '2em';
        textArea.style.border = 'none';
        textArea.style.outline = 'none';
        textArea.style.boxShadow = 'none';
        textArea.style.background = 'transparent';
        textArea.value = link;
        document.body.appendChild(textArea);
        textArea.select();
        try {
            const successful = document.execCommand('copy');
            const msg = successful ? 'successful' : 'unsuccessful';
            console.log('Copying text command was ' + msg);
        } catch (err) {
            console.log('Oops, unable to copy');
        }

        document.body.removeChild(textArea);

        $('#tooltip-rss_' + idRssfeedMsg).tooltip({ trigger: "click", placement: 'right' });
        $('#tooltip-rss_' + idRssfeedMsg)
            .attr('data-original-title', 'Copiado')
            .tooltip('show');

        setTimeout(function () {
            $('#tooltip-rss_' + idRssfeedMsg).tooltip('hide');
        }, 1000);
    }

    updateRssFeedMsgs() {
        this.loading = true;
        this.groupService.getRssFeedMessages(this.rssFeeds[0]).subscribe(
            result => this.onGetRssFeedGroups(result),
            error => console.log(error)
        );
    }

    updateRssFeedMsgsAll() {
        this.loading = true;
        this.groupService.currentGroupSource.next(this.group);

        this.groupService.getAllRssFeedsFromGroupByDate(this.group)
            .subscribe(
                result => this.onGetAllRssFeedsFromGroupByDate(result),
                error => console.log(error));
    }

    onGetAllRssFeedsFromGroupByDate(result: RssFeed[]) {
        if (!result) {
            console.log('Houve um erro ao recuperar rss feeds');
        } else {

            let rssFeeds: RssFeed[] = result;

            this.groupService.addAllRssFeedOnSource(rssFeeds);

            this.loading = false;
        }
    }

    onGetRssFeedGroups(result: RssFeed) {
        if (!result) {
            this.errorMessage = 'Houve ao recuperar rss feed messages';
        } else {
            this.groupService.addRssFeedOnSource(result);
            this.loading = false;
        }
    }

    checkSocialMedia(event) { }

    // protected fetchNextChunk(skip: number, limit: number): Promise<any[]> {
    // 	return new Promise((resolve, reject) => {
    // 	clearTimeout(this.timer);
    // 	this.timer = setTimeout(() => {
    // 		if (skip < this.rssFeedsExibir.length) {
    // 			return resolve(this.rssFeedsExibir.slice(skip, skip + limit));
    // 		}
    // 		reject();
    // 	}, 1000 + Math.random() * 1000);
    // 	});
    // }

    ngOnDestroy(): void {
        if (this.subscriptionGroup) {
            this.subscriptionGroup.unsubscribe();
        }

        if (this.subscriptionRss) {
            this.subscriptionRss.unsubscribe();
        }
    }
}
