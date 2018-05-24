import {
    Component,
    ElementRef,
    EventEmitter,
    forwardRef,
    Inject,
    Input,
    OnChanges,
    OnDestroy,
    OnInit,
    Output,
    SimpleChanges,
    ViewChild,
} from '@angular/core';
import { InboxComponent } from 'core/componentes/inbox/inbox.component';
import { EmojiService } from 'core/services/emojis/emojis.service';
import { InboxService } from 'core/services/inbox/inbox.service';
import { Profile } from 'modelo/profile/Profile';
import { StreamPostPaging } from 'modelo/stream/StreamPostPaging';
import { Observable } from 'rxjs/Observable';
import { Subscription } from 'rxjs/subscription';

declare const $;

@Component({
    selector: 'inbox-message',
    templateUrl: './inbox-message.component.html',
    styleUrls: ['./inbox-message.component.css']
})

export class InboxMessageComponent implements OnInit, OnDestroy, OnChanges {
    @Input()
    inbox: any;

    @Input()
    profile: Profile;

    @Input()
    inboxOpen: boolean;

    @ViewChild('chatInner')
    chatInner: ElementRef;

    @ViewChild('textareaEdit')
    textareaEdit: ElementRef;

    offset = 1;
    haveMessages = true;
    loadingMessages = false;

    @Input()
    messages;

    @Input()
    paging;

    @Input()
    conversation: any;

    @Output() emit: EventEmitter<any> = new EventEmitter<any>();

    @Output() newMessages: EventEmitter<any> = new EventEmitter<any>();

    timerSubscription: Subscription;

    constructor(
        private InboxService: InboxService,
        private EmojiService: EmojiService,
        @Inject(forwardRef(() => InboxComponent)) private _parent: InboxComponent
    ) {	}

    ngOnInit() {
        this.getNewMessages();
        this.enableTooltip();
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (!changes['messages'].previousValue) {
            this.haveMessages = true;
        }
        this.enableTooltip();
    }

    toggleInbox() {
        this._parent.toggleInbox(null, null);
    }

    goToTheEnd() {
        setTimeout(
            () => {
                if (this.conversation) {
                    this.chatInner.nativeElement.scrollTop = this.chatInner.nativeElement.scrollHeight;
                }
            },
            10
        );
    }

    scrollTop() {
        if (this.chatInner.nativeElement.scrollTop <= 20 && this.haveMessages) {
            this.loadMoreMessages();
        }
    }

    loadMoreMessages() {
        if ((!this.loadingMessages) && this.haveMessages) {
            this.loadingMessages = true;
            const originalScroll = this.chatInner.nativeElement.scrollHeight;

            const streamMessageAux = {
                streamMessage: null,
                streamConversation: this.conversation,
                streamPostPaging: this.paging,
                idProfile: this.profile.idProfile
            };

            this.InboxService.loadMoreMessages(streamMessageAux)
                .subscribe(
                    result => {
                        this.newMessages.emit(result);
                        setTimeout(
                            () => {
                                // manter scroll onde estava antes de carregar as novas mensagens
                                this.chatInner.nativeElement.scrollTop = this.chatInner.nativeElement.scrollHeight - originalScroll;
                                this.loadingMessages = false;
                            },
                            10 // tempo para renderizar os elementos no DOM
                        );

                        if (!result.streamPostPaging.after) {
                            this.haveMessages = false;
                        }
                    }
                );
        }
    }

    getNewMessages() {
        const timer = Observable.timer(30000, 30000);
        this.timerSubscription = timer.subscribe((t: any) => {
            if (this.conversation) {
                const streamMessageAux = {
                    streamMessage: null,
                    streamConversation: this.conversation,
                    streamPostPaging: new StreamPostPaging().limit = 20,
                    idProfile: this.profile.idProfile
                };

                this.InboxService.loadMoreMessages(streamMessageAux)
                    .subscribe(
                        result => {
                            if (this.chatInner.nativeElement.scrollTop + this.chatInner.nativeElement.offsetHeight
                                >= this.chatInner.nativeElement.scrollHeight) {
                                this.goToTheEnd();
                            }
                            this.newMessages.emit(result);
                        }
                    );
            }
        });
    }

    sendReply($event: KeyboardEvent, textarea: HTMLTextAreaElement) {
        if ($event.keyCode === 13 && !$event.shiftKey) {
            $event.preventDefault();

            if (textarea.value.trim()) {
                const message =  textarea.value;
                textarea.value = '';

                const streamMessageAux = {
                    streamMessage: {
                        idMessage: null,
                        createdTime: null,
                        userName: this.conversation.name,
                        userId: this.conversation.userId,
                        message: message,
                        idConversation: this.conversation.idConversation
                    },
                    idProfile: this.profile.idProfile
                };

                this.InboxService.sendMessage(streamMessageAux)
                    .subscribe(
                        result => {
                            result.createdTime = new Date().getTime() / 1000;
                            this.emit.emit(result);
                            this.goToTheEnd();
                        }
                    );
            }
        }
    }

    enableTooltip() {
        setTimeout(() => {
            $('[data-toggle="tooltip"]').tooltip();
        }, 200);
    }

    resizeTextarea(textarea: HTMLTextAreaElement) {
        if (textarea.scrollHeight > 34) {
            textarea.style.height = '37px';
            textarea.style.height = (textarea.scrollHeight + 3) + 'px';
            this.chatInner.nativeElement.style.heigth = 'calc(100% - '  + (45 - 34 + textarea.scrollHeight) + 'px)';
        }else {
            textarea.style.height = '37px';
            this.chatInner.nativeElement.style.heigth = 'calc(100% - 45px)';
        }

        this.goToTheEnd();
    }

    public showEmojiEdit(emojiTrigger: HTMLElement) {
        this.openEmoji(emojiTrigger, this.textareaEdit);
    }

    openEmoji(emojiTrigger: HTMLElement, element: ElementRef) {
        const position = emojiTrigger.getBoundingClientRect();
        this.EmojiService.currentElement = emojiTrigger;
        this.EmojiService.showEmoji(element.nativeElement, 'value', position);
    }

    itsImage(url) {
        const extension = url.match(/.jpeg|.jpg|.png/gi);

        if (extension) {
            return true;
        } else {
            return false;
        }
    }

    sendLike() {
        const like = '👍';
        const streamMessageAux = {
            streamMessage: {
                idMessage: null,
                createdTime: null,
                userName: this.conversation.name,
                userId: this.conversation.userId,
                message: like,
                idConversation: this.conversation.idConversation
            },
            idProfile: this.profile.idProfile
        };

        this.InboxService.sendMessage(streamMessageAux)
            .subscribe(
                result => {
                    result.createdTime = new Date().getTime() / 1000;
                    this.emit.emit(result);
                    this.goToTheEnd();
                }
            );
    }

    ngOnDestroy(): void {
        if (this.timerSubscription) {
            this.timerSubscription.unsubscribe();
        }
    }
}
