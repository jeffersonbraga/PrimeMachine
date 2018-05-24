import { ModalService } from './../../core/services/modal/modal.service';
import { Router } from '@angular/router';
import { ControlService } from 'core/services/control/control.service';
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { StreamService } from 'core/services/stream/stream.service';
import { Stream } from 'modelo/stream/Stream';
import { DragulaService } from 'ng2-dragula/ng2-dragula';

// import * as autoScroll from 'dom-autoscroller';

declare const autoScroll;
declare const $;

@Component({
    selector: 'feed',
    templateUrl: './feed.component.html',
    styleUrls: ['./feed.component.css']
})

export class FeedComponent implements OnInit, OnDestroy {
    numberProfiles = 0;

    streams: Stream[] = [];
    destroyed: boolean;

    @ViewChild('autoScroll') autoScroll: ElementRef;

    loaded = false;

    constructor(
        private dragula: DragulaService,
        private ProfileService: ProfilesService,
        private StreamService: StreamService,
        private controlService: ControlService,
        private router: Router,
        private modalService: ModalService
    ) {
        try {
            this.dragula.setOptions('bag-stream', {
                revertOnSpill: true,
                direction: 'horizontal',
                moves: function (el, container, handle) {
                    return handle.className === 'handle';
                }
            });
        } catch(e) {
            console.log(e);
        }
    }

    ngOnInit() {
        this.getStreams();

        this.dragula
            .drag
            .subscribe(value => {
                // do something
            });

        this.dragula
            .drop
            .subscribe(value => {
                this.reorderStreams();
            });

        setTimeout(() => {
            const scroll = autoScroll([
                this.autoScroll.nativeElement
            ], {
                margin: 20,
                maxSpeed: 20,
                scrollWhenOutside: true,
                autoScroll: function () {
                    // Only scroll when the pointer is down.
                    return this.down;
                    // return true;
                }
            });
        }, 1000);

        this.ProfileService.profiles$
            .subscribe(
                profiles => {
                    if (!this.destroyed) {
                        this.checkProfilesUpdated(profiles);
                    }
                }
            );
    }

    checkProfilesUpdated(profiles) {
        if (!this.numberProfiles || this.numberProfiles < profiles.length) {
            this.numberProfiles = profiles.length;
        } else {
            this.getStreams(true);
        }
    }

    getStreams(onDelete?: boolean) {
        this.StreamService.getStreams()
            .subscribe(
                streams => {
                    this.checkHtmlOverflow();
                    this.streams = streams;
                    this.StreamService.stream = this.streams;
                    this.StreamService.streamSource.next(this.streams);
                    this.loaded = true;
                    if (onDelete) {
                        this.reorderStreams();
                    }
                }
            );
    }

    checkHtmlOverflow() {
        if (!this.streams.length) {
            setTimeout(() => {
                $('html').css('overflow', 'hidden');
            }, 100);
        } else {
            $('html').css('overflow', '');
        }
    }

    reorderStreams() {
        this.checkHtmlOverflow();
        setTimeout(() => {
            this.StreamService.reorderStreams(this.streams)
                .subscribe();
        }, 300);
    }

    openStreamsModal() {
        this.modalService.showAddStreamsModal();
    }

    ngOnDestroy() {
        this.destroyed = true;
        setTimeout(() => {
            $('html').css('overflow', '');
        }, 200);
    }
}
