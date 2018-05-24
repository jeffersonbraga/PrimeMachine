import { SmartTipService } from 'core/services/smartTip/smartTip.service';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/subscription';

import { ControlService } from '../core/services/control/control.service';
import { EmojiService } from './../core/services/emojis/emojis.service';
import { LoginService } from './../core/services/login/login.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {

    isTriggered: boolean = false;

    manageChannelsComponentClassName: string = 'HelloWoopComponent';

    @ViewChild('helloWoopModal')
    helloWoopModal: any;

    emojiSource: any;
    emojiField: string;
    showEmojiList = false;

    smartTipSource: any;
    smartTipField: string;
    showSmartTipList = false;

    position = {
        left: 0,
        top: 0
    };

    positionSmartTip = {
        left: 0,
        top: 0
    };

    private subscriptionSource              : Subscription;
    private subscriptionField               : Subscription;
    private subscriptionShow                : Subscription;
    private subscriptionPosition            : Subscription;

    private subscriptionSmartTipSource      : Subscription;
    private subscriptionSmartTipField       : Subscription;
    private subscriptionSmartTipShow        : Subscription;
    private subscriptionSmartTipPosition    : Subscription;

    constructor(
        private controlApp: ControlService,
        private loginService: LoginService,
        private EmojiService: EmojiService,
        private SmartTipService: SmartTipService,
        private router: Router,
        private activatedRoute: ActivatedRoute
    ) {
        this.loginService.isLoggedIn()
            .subscribe(
                result => 
                    this.loginService.isLogged = result,
                error => 
                    console.log(error));

    }

    ngOnInit() {
        this.validateUser();
        this.setEmojiFields();
        this.setSmartTipFields();
    }

    setEmojiFields() {
        this.subscriptionSource = this.EmojiService.dataSource$
            .subscribe(
                source => this.emojiSource = source
            );

        this.subscriptionField = this.EmojiService.dataField$
            .subscribe(
                field => this.emojiField = field
            );

        this.subscriptionShow = this.EmojiService.showEmojiList$
            .subscribe(
                show => this.showEmojiList = show
            );

        this.subscriptionPosition = this.EmojiService.positionElement$
            .subscribe(
                positionElement => this.adjustPosition(positionElement)
            );
    }

    setSmartTipFields() {
        this.subscriptionSmartTipSource = this.SmartTipService.dataSource$
            .subscribe(
                source => this.smartTipSource = source
            );

        this.subscriptionSmartTipField = this.SmartTipService.dataField$
            .subscribe(
                field => this.smartTipField = field
            );

        this.subscriptionSmartTipShow = this.SmartTipService.showSmartTipList$
            .subscribe(
                show => this.showSmartTipList = show
            );

        this.subscriptionSmartTipPosition = this.SmartTipService.positionElement$
            .subscribe(
                positionElement => this.adjustSmartTipPosition(positionElement)
            );
    }

    adjustPosition(positionElement: ClientRect) {
        if (positionElement.left + 280 > window.innerWidth) {
            this.position.left = window.innerWidth - 280;
        }else {
            this.position.left = positionElement.left - 130 + document.body.scrollLeft;
        }

        if (positionElement.top + 351 > window.innerHeight) {
            this.position.top = window.innerHeight - 351;
        }else {
            this.position.top = positionElement.top + 20 + document.body.scrollTop;
        }
    }

    adjustSmartTipPosition(positionElement: ClientRect) {
        if (positionElement.left + 280 > window.innerWidth) {
            this.positionSmartTip.left = window.innerWidth - 280;
        }else {
            this.positionSmartTip.left = positionElement.left - 130 + document.body.scrollLeft;
        }

        if (positionElement.top + 200 > window.innerHeight) {
            this.positionSmartTip.top = window.innerHeight - 200;
        }else {
            this.positionSmartTip.top = positionElement.top + 20 + document.body.scrollTop;
        }
    }

    ngOnDestroy() {

        if (this.subscriptionSource) {
            this.subscriptionSource.unsubscribe();
        }

        if (this.subscriptionField) {
            this.subscriptionField.unsubscribe();
        }

        if (this.subscriptionShow) {
            this.subscriptionShow.unsubscribe();
        }

        if (this.subscriptionPosition) {
            this.subscriptionPosition.unsubscribe();
        }

        if (this.subscriptionSource) {
            this.subscriptionSource.unsubscribe();
        }

        if (this.subscriptionField) {
            this.subscriptionField.unsubscribe();
        }

        if (this.subscriptionShow) {
            this.subscriptionShow.unsubscribe();
        }

        if (this.subscriptionPosition) {
            this.subscriptionPosition.unsubscribe();
        }

        if (this.subscriptionSmartTipSource) {
            this.subscriptionSmartTipSource.unsubscribe();
        }

        if (this.subscriptionSmartTipField) {
            this.subscriptionSmartTipField.unsubscribe();
        }

        if (this.subscriptionSmartTipShow) {
            this.subscriptionSmartTipShow.unsubscribe();
        }

        if (this.subscriptionSmartTipPosition) {
            this.subscriptionSmartTipPosition.unsubscribe();
        }
    }

    validateUser() {

        if (!this.isTriggered) {

            this.isTriggered = true;
            this.loginService.isLoggedIn()
                .subscribe(
                result =>
                    this.handlerUserSession(result),
                error => console.log(error));
        } else {

            setTimeout(() => {
                this.loginService.isLoggedIn()
                    .subscribe(
                    result =>
                        this.handlerUserSession(result),
                    error => console.log(error));
            }, 2000);
        }
    }

    handlerUserSession(result) {
        this.loginService.isLogged = result;
        if (this.loginService.isLogged) {
            this.validateUser();
        } else {
            // this.router.navigate(['/login']);
            // this.router.navigateByUrl('https://woopsocial.com/home');
            // window.open('https://woopsocial.com/home', '_self');
        }
    }
}