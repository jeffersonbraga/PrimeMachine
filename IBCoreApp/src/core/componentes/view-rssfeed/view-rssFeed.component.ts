import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { GroupsService } from 'core/services/groups/groups.service';
import { Group } from 'modelo/group/Group';
import { RssFeed } from 'modelo/rssFeed/RssFeed';
import { RssFeedMessage } from 'modelo/rssFeed/RssFeedMessage';

declare const $;

@Component({
    moduleId: module.id,
    selector: 'view-rssFeed',
    templateUrl: './view-rssFeed.component.html',
    styleUrls: ['./view-rssFeed.component.css']
})

export class ViewRssFeedComponent implements OnInit {
    public static selectedRssFeedAux: RssFeed = new RssFeed();
    public static selectedRssFeedMessageAux: RssFeedMessage = new RssFeedMessage();
    public static groupaAux: Group = new Group();

    public selectedRssFeed: RssFeed;

    public selectedRssFeedMessage: RssFeedMessage;

    public group: Group;

    public static setAttachment(itemMsg, itemRssFeed, group) {
        ViewRssFeedComponent.groupaAux = group;
        ViewRssFeedComponent.selectedRssFeedAux = itemRssFeed;
        ViewRssFeedComponent.selectedRssFeedMessageAux = itemMsg;
        // ViewRssFeedComponent.containerHtmlContentAux = itemMsg;
    }

    constructor(private groupService: GroupsService,
                private router: Router,
                private route: ActivatedRoute
    ) {
        this.group = ViewRssFeedComponent.groupaAux;
        this.selectedRssFeed = ViewRssFeedComponent.selectedRssFeedAux;
        this.selectedRssFeedMessage = ViewRssFeedComponent.selectedRssFeedMessageAux;
        // ViewRssFeedComponent.containerHtmlContentAux = this.containerHtmlContent;
    }

    ngOnInit(): void {
        // ViewRssFeedComponent.containerHtmlContentAux = this.containerHtmlContent;
        // if(ViewRssFeedComponent.containerHtmlContentAux) {
        // 	ViewRssFeedComponent.containerHtmlContentAux.nativeElement.innerHtml =
        // 		ViewRssFeedComponent.selectedRssFeedMessageAux.messageDescription;
        // }
    }

    sendLinkToCompose(url: string) {
        this.router.navigate(['/home/compose', { url: url }], { relativeTo: this.route, skipLocationChange: true });
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

        $('#tooltip3-rss_' + idRssfeedMsg).tooltip({ trigger: 'click', placement: 'right' });
        $('#tooltip3-rss_' + idRssfeedMsg)
            .attr('data-original-title', 'Copiado')
            .tooltip('show');

        setTimeout(function() {
            $('#tooltip3-rss_' + idRssfeedMsg).tooltip('hide');
        }, 1000);
    }

    copyLink2(link: string, idRssfeedMsg: number) {

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

        $('#tooltip2-rss_' + idRssfeedMsg).tooltip({ trigger: 'click', placement: 'bottom' });
        $('#tooltip2-rss_' + idRssfeedMsg)
            .attr('data-original-title', 'Copiado')
            .tooltip('show');

        setTimeout(function() {
            $('#tooltip2-rss_' + idRssfeedMsg).tooltip('hide');
        }, 1000);
    }
}
