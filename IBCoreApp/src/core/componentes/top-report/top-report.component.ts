import { ModalService } from './../../services/modal/modal.service';
import { DatePipe } from '@angular/common';
import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { ControlService } from 'core/services/control/control.service';
import { BsDatepickerConfig } from 'ngx-bootstrap/datepicker';

@Component({
    selector: 'top-report',
    templateUrl: './top-report.component.html',
    styleUrls: ['./top-report.component.css']
})

export class TopReportComponent implements OnInit {
    user: any;

    dateFrom: Date = new Date(new Date().setDate(new Date().getDate() - 7));
    dateUntil: Date = new Date();

    maxDate: Date;
    minDate: Date;

    isValidPeriod = true;
    freemiunPlan = 0;

    @ViewChild('unlockModal') unlockModal: GenericModalComponent;

    bsRangeValue: any = [];

    @Output() changeDate: EventEmitter<Object> = new EventEmitter<Object>();
    @Output() changeStatic: EventEmitter<boolean> = new EventEmitter<boolean>();

    colorTheme = 'theme-blue';

    bsConfig: Partial<BsDatepickerConfig> = {
        containerClass: this.colorTheme,
        showWeekNumbers: false
    };

    constructor(
        private ControlService: ControlService,
        private DatePipe: DatePipe,
        private router: Router,
        private modalService: ModalService
    ) {
        router.events.subscribe(event => {
            if (event instanceof NavigationStart) {
                this.ControlService.showedUnlock = false;
            }
        });
    }

    ngOnInit() {
        const user = this.ControlService.getUser();

        if (user) {
            this.user = user;
            this.minDate = new Date(this.user.account.cadastreDate);
            this.maxDate = new Date(new Date(this.user.account.cadastreDate)
                .setDate(new Date(this.user.account.cadastreDate).getDate() + 10));
            this.checkInitialDates();
        } else {
            this.ControlService.user$.subscribe(
                userControl => {
                    this.user = userControl;
                    this.minDate = new Date(this.user.account.cadastreDate);
                    this.maxDate = new Date(new Date(this.user.account.cadastreDate)
                        .setDate(new Date(this.user.account.cadastreDate).getDate() + 10));
                    this.checkInitialDates();
                }
            );
        }
    }

    setInitialDates() {
        this.dateUntil = new Date();
        this.dateFrom = new Date();
        this.dateFrom.setDate(this.dateFrom.getDate() - 7);

        this.checkInitialDates();
    }

    checkInitialDates() {
        if (this.maxDate.getTime() < this.dateUntil.getTime()
            && this.user.account.planType === this.freemiunPlan) {
            this.dateUntil = this.maxDate;
            this.dateFrom = this.minDate;

            this.isValidPeriod = false;
            this.changeStatic.emit(true);
            this.bsRangeValue = [this.dateFrom, this.dateUntil];
            this.emit();
            if (!this.ControlService.showedUnlock) {
                this.openModal();
                this.ControlService.showedUnlock = true;
            }
        } else {
            this.bsRangeValue = [this.dateFrom, this.dateUntil];
        }

    }

    openModal(): any {
        setTimeout(() => {
            this.modalService.showUnlockReportsModal();
        }, 100);
    }

    choseInterval(interval) {
        this.dateFrom = new Date();
        this.dateUntil = new Date();

        this.dateFrom.setDate(this.dateFrom.getDate() - interval);

        this.bsRangeValue = [this.dateFrom, this.dateUntil];

        this.emit();
    }

    emit() {
        setTimeout(() => {
            if (this.bsRangeValue.length) {
                this.dateFrom = this.bsRangeValue[0];
                this.dateUntil = this.bsRangeValue[1];

                this.changeDate.emit({ dateFrom: this.dateFrom, dateUntil: this.dateUntil });
            }
        }, 10);
    }
}
