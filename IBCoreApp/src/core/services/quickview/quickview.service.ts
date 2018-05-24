import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Subject } from 'rxjs/Subject';

import { ControlService } from './../control/control.service';
import { GeneralService } from './../general.service';

declare const $;

@Injectable()
export class QuickviewService extends GeneralService {

    quickviewState = false;
    public quickviewSource = new Subject<boolean>();
    quickview$ = this.quickviewSource.asObservable();

    public unreadSource = new Subject<boolean>();
    unread$ = this.unreadSource.asObservable();

    constructor(private http: Http, private Control: ControlService) {
        super();
    }

    openQuickview() {
        this.quickviewState = true;
        this.setQuickviewState(true);
    }

    toggleQuickviewState() {
        if (this.quickviewState) {
            this.quickviewState = false;
            this.setQuickviewState(false);
            $('#quickview-op').click();
        }

        // this.quickviewState = false;
        // this.quickviewSource.next(this.quickviewState);
    }

    setQuickviewState(state: boolean) {
        this.quickviewState = state;
        this.quickviewSource.next(this.quickviewState);
    }
}
