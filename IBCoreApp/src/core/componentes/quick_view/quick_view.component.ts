import { ModalService } from './../../services/modal/modal.service';
import { DatePipe } from '@angular/common';
import { Component, ElementRef, NgZone, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { NotificationService } from 'core/services/notification/notification.service';
import { QuickviewService } from 'core/services/quickview/quickview.service';
import { Notification } from 'modelo/notification/Notification';
import { Subscription } from 'rxjs/subscription';

@Component({
    selector: 'quick_view-root',
    templateUrl: './quick_view.component.html',
    styleUrls: ['quick_view.component.css'],
    host: {
        '(document:click)': 'onClick($event)'
    }
})
export class Quick_viewComponent implements OnInit, OnDestroy{
    public static refAux;

    subscription: Subscription;

    // javascript:window.angularComponentRef.zone.run(() => window.angularComponentRef.QuickViewComponent.openReconnectModal())

    quickviewState: boolean;
    @ViewChild('quickview') quickview: ElementRef;
    @ViewChild('reconnectModal') reconnectModal;

    headerTitleReconnect = 'Reconectar Perfis';
    profileReconnectComponentClassName = 'ProfileReconnectComponent';

    notifications: any[] = [];
    dates: string[] = [];

    constructor(
        private QuickviewService: QuickviewService,
        private NotificationService: NotificationService,
        private DatePipe: DatePipe,
        private zone: NgZone,
        private modalService: ModalService
    ) {
        Quick_viewComponent.refAux = this;

        window['angularComponentRef'] = {
            zone: this.zone,
            componentFn: (value) => this.openReconnectModal(),
            QuickViewComponent: this
        };
    }

    ngOnInit() {
        this.subscription = this.QuickviewService.quickview$.subscribe(
            quickview => {
                this.quickviewState = quickview;
                console.log(this.quickviewState);
                this.setRead();
            }
        );

        this.getNotifications();

        setInterval(
            () => {
                this.getNotifications();
            },
        60000);
    }

    getNotifications() {
        this.NotificationService.getNotifications().subscribe(
            notifications =>  this.extractDates(notifications)
        );
    }

    extractDates(notification: Notification[]) {
        if (notification) {
            let data;
            let idx;
            let existsUnread = false;

            this.notifications = [];
            this.dates = [];

            notification.filter(
                n => {
                    data = this.DatePipe.transform(new Date(n.date), 'MM/dd/yyyy');
                    idx = this.dates.indexOf(data);

                    if (n.unread === true) {
                        existsUnread = true;
                    }

                    if (idx === -1) {
                        this.dates.push(data);
                        this.notifications.push({
                            data: data,
                            values: [n]
                        });
                    }else {
                        this.notifications[idx].values.push(n);
                    }
                }
            );

            this.QuickviewService.unreadSource.next(existsUnread);
        }
    }

    setRead() {
        if (this.quickviewState) {
            this.notifications.filter(
                v => {
                    v.values.filter(
                        (n: Notification) => {
                            if (n.unread === true) {
                                this.NotificationService.updateNotifications(n)
                                    .subscribe();
                            }
                        }
                    );
                }
            );

            this.QuickviewService.unreadSource.next(false);
        }
    }

    onClick(event) {
        if (!this.quickview.nativeElement.contains(event.target) && event.srcElement.getAttribute('data-toggle-element') !== '#quickview') {
            this.closeQuickview();
        }
    }

    closeQuickview() {
        this.QuickviewService.toggleQuickviewState();
    }

    openReconnectModal() {
        this.modalService.showProfileReconnectModal();
    }

    ngOnDestroy(): void {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }
}
