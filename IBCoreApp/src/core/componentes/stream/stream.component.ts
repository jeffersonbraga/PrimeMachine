import {
    AfterViewChecked,
    ChangeDetectorRef,
    Component,
    ElementRef,
    forwardRef,
    HostListener,
    Inject,
    Input,
    OnDestroy,
    OnInit,
    ViewChild,
} from '@angular/core';
import { BindMarksService } from 'core/services/bindMarks/bindMarks.service';
import { DialogService } from 'core/services/dialog/dialog.service';
import { EmojiService } from 'core/services/emojis/emojis.service';
import { FollowersService } from 'core/services/followers/followers.service';
import { InboxService } from 'core/services/inbox/inbox.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { StreamService } from 'core/services/stream/stream.service';
import * as lodash from 'lodash/';
import { Profile } from 'modelo/profile/Profile';
import { Stream } from 'modelo/stream/Stream';
import { StreamPostPaging } from 'modelo/stream/StreamPostPaging';
import { Observable } from 'rxjs/Observable';
import { Subscription } from 'rxjs/subscription';
import { FeedComponent } from 'views/feed/feed.component';

@Component({
    selector: 'stream',
    templateUrl: './stream.component.html',
    styleUrls: ['./stream.component.css']
})

export class StreamComponent implements OnInit, OnDestroy, AfterViewChecked {
    timerSubscription: Subscription;

    @Input()
    profile: Profile;

    @Input()
    stream: Stream;

    tabType: string;
    offset: number;
    oldWidth: number;
    inboxOpen: boolean;

    @ViewChild('main')
    main: ElementRef;

    @ViewChild('myPosts')
    myPosts: ElementRef;

    @ViewChild('timeline')
    timeline: ElementRef;

    @ViewChild('mentions')
    mentions: ElementRef;

    @ViewChild('mainContainer')
    mainContainer: ElementRef;

    emojiComponent: HTMLElement;

    inbox: any = {
        conversations: [],
        streamPostPaging: null
    };
    followers: any = [];

    activeTab = 'P';

    scroll: number;

    posts: Object = {
        P: [], 	// My Posts
        T: [], 	// Timeline
        M: []	// Mentions
    };

    pagings = {
        P: new StreamPostPaging(),	// My Posts
        T: new StreamPostPaging(),	// Timeline
        M: new StreamPostPaging(),	// Mentions
        I: new StreamPostPaging(),	// Inbox
        F: new StreamPostPaging() 	// Followers
    };

    loadings = {
        P: false,
        T: false,
        M: false,
        I: false,
        F: false
    };

    loadingMore = {
        P: false,
        T: false,
        M: false
    };

    loaded = {
        P: false,
        T: false,
        M: false,
        I: false,
        F: false
    };

    havingMore = {
        P: true,
        T: true,
        M: true,
        I: true,
        F: true
    };

    @HostListener('window:resize') onResize() {
        this.adjust();
    }

    constructor(
        private ProfileService: ProfilesService,
        private StreamService: StreamService,
        private DialogService: DialogService,
        private ChangeDetectorRef: ChangeDetectorRef,
        private InboxService: InboxService,
        private FollowersService: FollowersService,
        private EmojiService: EmojiService,
        private bindMarksService: BindMarksService,
        @Inject(forwardRef(() => FeedComponent)) private _parent: FeedComponent
    ) { }

    ngOnInit() {
        this.onLoadStream();
        this.adjust();
        this.getNewConversations();

        this.EmojiService.element$
            .subscribe(
                element => this.emojiComponent = element
            );
    }

    ngAfterViewChecked() {
        if (this.oldWidth !== this.main.nativeElement.offsetWidth) {
            this.oldWidth = this.main.nativeElement.offsetWidth;
            this.adjust();
            this.ChangeDetectorRef.detectChanges();
        }
    }

    setActiveTab(tab: string) {
        this.activeTab = tab;
        if (!this.loaded[tab]) {
            if (tab === 'I') {
                this.loadInbox();
            } else if (tab === 'F') {
                this.getFollowers();
            } else {
                if (!this.posts[tab].length) {
                    this.onLoadStream();
                }
            }
        }
    }

    onLoadStream() {
        const tab = this.activeTab;
        if (!this.loadings[tab]) {
            if (tab !== 'I') {
                this.loadings[tab] = true;

                // Exemplo de chamada para pegar os posts na aba MyPosts.
                this.pagings[tab].limit = 20;

                this.genericLoad(tab);
            }
        }
    }

    reloadStreams() {
        const tab = this.activeTab;
        if (!this.loadings[tab]) {
            if (tab !== 'I' && tab !== 'F') {
                this.loadingMore[tab] = true;

                const streamPostAux = {
                    profile: this.profile,
                    streamPostPaging: new StreamPostPaging().limit = 20
                };

                this.StreamService.getMyPosts(streamPostAux, tab)
                    .subscribe(
                    result => {
                        this.posts[tab] = result[0];
                        this.pagings[tab] = result[1];
                        this.loadingMore[tab] = false;
                        this.havingMore[tab] = true;
                        this.bindMarksService.bind();
                    },
                        error => error
                    );
            } else if (tab === 'I') {
                this.inbox.conversations = [];
                this.loadings['I'] = true;

                const streamConversationsAux = {
                    streamPostPaging: new StreamPostPaging().limit = 20,
                    streamPostPagingAux: new StreamPostPaging().limit = 20,
                    idProfile: this.profile.idProfile
                };

                this.InboxService.loadInbox(streamConversationsAux)
                    .subscribe(
                        result => {
                            this.inbox.conversations = result.conversations;
                            this.inbox.streamPostPaging = result.streamPostPaging;
                            this.pagings['I'] = result.streamPostPaging;
                            this.loadings['I'] = false;
                            this.havingMore['I'] = true;
                        }
                    );
            } else if (tab === 'F') {
                this.followers = [];
                this.loadings['F'] = true;

                const streamFollowersAux = {
                    streamPostPaging: new StreamPostPaging().limit = 20,
                    idProfile: this.profile.idProfile
                };

                this.FollowersService.getFollowers(streamFollowersAux)
                    .subscribe(
                        result => {
                            this.followers = result.followers;
                            this.pagings['F'] = result.paging;
                            this.loadings['F'] = false;
                            this.havingMore['F']  = true;

                            if (result.paging) {
                                if (!result.paging.until) {
                                    this.havingMore['F'] = false;
                                }
                            }
                        }
                    );
            }
        }
    }

    genericLoad(tab) {
        const streamPostAux = {
            profile: this.profile,
            streamPostPaging: this.pagings[tab]
        };

        this.StreamService.getMyPosts(streamPostAux, tab)
            .subscribe(
                result => {
                    this.onFinishLoad(result, tab);
                },
                error => error
            );
    }

    splicePost(index, tab) {
        this.posts[tab].splice(index, 1);
        this.posts[tab].commentsCount--;
    }

    deleteStream() {
        this.StreamService.deleteStream(this.stream)
            .subscribe(
                stream => {
                    this.StreamService.removeFromSource(stream);
                    this._parent.reorderStreams();
                    this.adjust();
                }
            );
    }

    onScroll(scroll: number) {
        if (this.main.nativeElement.offsetHeight - window.innerHeight - 100 <= scroll) {
            this.loadMore();
        }
    }

    loadMore() {
        const tab = this.activeTab;

        if (tab === 'M' || tab === 'T' || tab === 'P') {
            if (!this.loadings[tab] && this.havingMore[tab]) {
                this.loadings[tab] = true;

                this.genericLoad(tab);
            }
        } else if (tab === 'F') {
            if (!this.loadings[tab] && this.havingMore[tab]) {
                this.loadings[tab] = true;

                this.getFollowers();
            }
        }
    }

    onFinishLoad(result, tab, reload?) {
        console.log(result);
        this.posts[tab].push.apply(this.posts[tab], result[0]);
        this.pagings[tab] = result[1];
        this.loadings[tab] = false;
        this.bindMarksService.bind();

        // Caso o scroll tenha chegado ao fim. Evita de ficar enviando requisições
        if (!result[0].length) {
            this.havingMore[tab] = false;
        }
    }

    adjust() {
        this.offset = this.main.nativeElement.offsetWidth;
        if (this.offset < 365) {
            this.tabType = 'small';
        } else {
            this.tabType = 'full';
        }
    }

    loadInbox() {
        if (!this.loadings['I']) {
            this.loadings['I'] = true;

            this.pagings['I'].limit = 20;

            const streamConversationsAux = {
                streamPostPaging: this.pagings['I'],
                streamPostPagingAux: this.pagings['I'],
                idProfile: this.profile.idProfile
            };

            this.InboxService.loadInbox(streamConversationsAux)
                .subscribe(
                    result => {
                        console.log(result);
                        this.inbox.conversations.push.apply(this.inbox.conversations, result.conversations);
                        this.inbox.streamPostPaging = result.streamPostPaging;
                        this.pagings['I'] = result.streamPostPaging;
                        this.loadings['I'] = false;
                    }
                );
        }
    }

    getFollowers(event?: number) {
        this.loadings['F'] = true;
        this.pagings['F'].limit = 20;

        const streamFollowersAux = {
            streamPostPaging: this.pagings['F'],
            idProfile: this.profile.idProfile
        };

        this.FollowersService.getFollowers(streamFollowersAux)
            .subscribe(
                result => {
                    this.followers.push.apply(this.followers, result.followers);
                    this.pagings['F'] = result.paging;
                    this.loadings['F'] = false;

                    if (result.paging) {
                        if (!result.paging.until) {
                            this.havingMore['F'] = false;
                        }
                    }
                }
            );
    }

    inboxState(event) {
        this.inboxOpen = event;
    }

    getNewConversations() {
        const timer = Observable.timer(60000, 60000);
        this.timerSubscription = timer.subscribe((t: any) => {
            if (!this.inboxOpen && this.activeTab === 'I') {
                const streamConversationsAux = {
                    streamPostPaging: new StreamPostPaging().limit = 20,
                    streamPostPagingAux: new StreamPostPaging().limit = 20,
                    idProfile: this.profile.idProfile
                };

                this.InboxService.loadInbox(streamConversationsAux)
                    .subscribe(
                    result => {
                        this.inbox.conversations.unshift.apply(this.inbox.conversations, result.conversations);
                        this.inbox.conversations = lodash.uniqBy(this.inbox.conversations, 'idConversation');
                    }
                    );
            }
        });
    }

    scrollMyPosts() {
        if (this.myPosts.nativeElement.scrollTop + this.myPosts.nativeElement.offsetHeight
            > this.myPosts.nativeElement.scrollHeight - 20) {
            this.loadMore();
        }
        this.EmojiService.updatePosition();
        this.checkEmojiVisibility();
    }

    scrollTimeline() {
        if (this.timeline.nativeElement.scrollTop + this.timeline.nativeElement.offsetHeight
            > this.timeline.nativeElement.scrollHeight - 20) {
            this.loadMore();
        }
        this.EmojiService.updatePosition();
        this.checkEmojiVisibility();
    }

    scrollMentions() {
        if (this.mentions.nativeElement.scrollTop + this.mentions.nativeElement.offsetHeight
            > this.mentions.nativeElement.scrollHeight - 20) {
            this.loadMore();
        }
        this.EmojiService.updatePosition();
        this.checkEmojiVisibility();
    }

    checkEmojiVisibility() {
        if (this.emojiComponent) {
            if (this.emojiComponent.getBoundingClientRect().top < this.mainContainer.nativeElement.getBoundingClientRect().top + 49
                || this.emojiComponent.getBoundingClientRect().top > window.innerHeight) {
                this.EmojiService.closeEmoji();
            }
        }
    }

    getHref(profile: Profile): string {
        if (profile.type === 'F' || profile.type === 'P') {
            return 'https://facebook.com/' + profile.networkId;
        } else if (profile.type === 'T') {
            return 'https://twitter.com/' + profile.screenName;
        } else if (profile.type === 'I') {
            return 'https://instagram.com/' + profile.screenName;
        }
    }

    ngOnDestroy(): void {

        if (this.timerSubscription) {
            this.timerSubscription.unsubscribe();
        }
    }
}
