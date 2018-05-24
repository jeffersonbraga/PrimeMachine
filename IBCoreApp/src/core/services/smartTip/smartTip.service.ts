import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';
import { Subject } from 'rxjs/Subject';

import { ControlService } from './../control/control.service';
@Injectable()
export class SmartTipService {
    public dataSource = new Subject<any>();
    dataSource$ = this.dataSource.asObservable();

    public dataField = new Subject<string>();
    dataField$ = this.dataField.asObservable();

    public showSmartTipList = new Subject<boolean>();
    showSmartTipList$ = this.showSmartTipList.asObservable();

    public positionElement = new Subject<ClientRect>();
    positionElement$ = this.positionElement.asObservable();

    public element = new Subject<HTMLElement>();
    element$ = this.element.asObservable();

    public cursorPosition: number;
    public smartTipShowed: boolean;

    currentElement: HTMLElement;

    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http, private controlApp: ControlService) {    }

    showSmartTip(source: any, field: any, position: ClientRect) {
        this.dataSource.next(source);
        this.dataField.next(field);
        this.positionElement.next(position);
        this.showSmartTipList.next(true);
        this.smartTipShowed = true;
    }

    updatePosition() {
        if (this.currentElement) {
            this.element.next(this.currentElement);
            const position = this.currentElement.getBoundingClientRect();
            this.positionElement.next(position);
        }
    }

    closeSmartTip() {
        this.showSmartTipList.next(false);
        this.element.next(null);
        this.smartTipShowed = false;
    }

    getSmartScheduleInfo(arr) {

        return this.http.post(this.controlApp.context_app + 'woopsocial/get_smart_tip', arr, {headers: this.headers, withCredentials: true})
                    .map(this.extractData)
                    .catch(this.handleError);
    }

    private extractData(res: Response) {
        if (res.status === 200) {
            return res.json();
        }
        return null;
    }

    private handleError(error: any) {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }
}