import {
    ChangeDetectorRef,
    Component,
    ElementRef,
    EventEmitter,
    forwardRef,
    Inject,
    Input,
    OnInit,
    Output,
    ViewChild,
} from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import * as lodash from 'lodash/';
import { Profile } from 'modelo/profile/Profile';

import { StreamComponent } from './../stream/stream.component';
import { InboxMessageComponent } from './inbox-message/inbox-message.component';

@Component({
    selector: 'inbox',
    templateUrl: './inbox.component.html',
    styleUrls: ['./inbox.component.css']
})

export class InboxComponent implements OnInit {
    @Input()
    inbox: any;

    @Input()
    offset: number;

    @Input()
    profile: Profile;

    @Input()
    loaded: boolean;

    @ViewChild('child')
    child: InboxMessageComponent;

    @ViewChild('quickviewChat')
    quickviewChat: ElementRef;

    @Output('emit')
    emit: EventEmitter<boolean> = new EventEmitter<boolean>();

    inboxOpen = false;

    messages: any;
    paging: any;
    conversation: any;
    currentIndex: number;
    moreOrLess = true;

    constructor(
        private ProfileService: ProfilesService,
        private ChangeDetectorRef: ChangeDetectorRef,
        private domSanitizer: DomSanitizer,
        @Inject(forwardRef(() => StreamComponent)) private _parent: StreamComponent
    ) {	}

    ngOnInit() {
        this.autoUpdateView();
    }

    autoUpdateView() {
        setInterval(
            () => {
                if (this.inbox) {
                    const tam = this.inbox.conversations.length;

                    for (let i = 0; i < tam; i++) {
                        this.moreOrLess ?
                            this.inbox.conversations[i].updatedTime++ :
                            this.inbox.conversations[i].updatedTime--;
                    }
                }
            },
            30000
        );

        this.moreOrLess = !this.moreOrLess;
    }

    toggleInbox(conversation, i) {
        this.currentIndex = i;
        if (conversation) {
            this.messages = conversation.streamMessages.messages.slice();
            this.conversation = conversation;
            this.messages.reverse();
            this.paging = conversation.streamMessages.streamPostPaging;
        }else {
            this.messages = null;
            this.conversation = null;
            this.paging = null;
        }

        this.inboxOpen = !this.inboxOpen;
        this.emit.emit(this.inboxOpen);
        this.child.haveMessages = true;
        this.child.goToTheEnd();
    }

    getPreview(messages: any): any {
        const lgt = messages.length;

        for (let i = 0; i < lgt; i++) {
            if (messages[i].message) {
                return messages[i].message;
            } else if (messages[i].share) {
                if (!messages[i].share.name) {
                    return this.domSanitizer.bypassSecurityTrustHtml(`<img src="${messages[i].share.link}" style="max-width: 15px;">`);
                } else {
                    return '<i class="fa fa-link" aria-hidden="true"></i> ' + messages[i].share.name;
                }
            }
        }

        return '';
    }

    addToMessages(message) {
        if (message.idMessage) {
            this.conversation.streamMessages.messages.unshift(message);
            this.conversation.updatedTime = new Date().getTime() / 1000;
            this.messages.push(message);
            this.messages = lodash.uniqBy(this.messages, 'idMessage');
        }
    }

    loadMore(event) {
        this.conversation.streamMessages.messages.push.apply(this.conversation.streamMessages.messages, event.messages);
        this.messages.unshift.apply(this.messages, event.messages);

        this.conversation.streamMessages.messages = lodash.orderBy(this.conversation.streamMessages.messages, ['createdTime'], ['desc']);
        this.messages = lodash.orderBy(this.messages, ['createdTime'], ['asc']);

        this.conversation.streamMessages.messages = lodash.uniqBy(this.conversation.streamMessages.messages, 'idMessage');
        this.messages = lodash.uniqBy(this.messages, 'idMessage');

        this.paging = event.streamPostPaging;
    }

    scrollBottom() {
        if (this.quickviewChat.nativeElement.scrollTop + this.quickviewChat.nativeElement.offsetHeight
            > this.quickviewChat.nativeElement.scrollHeight - 20) {
                this._parent.loadInbox();
        }
    }
}
