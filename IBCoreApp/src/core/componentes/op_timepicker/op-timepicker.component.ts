import { Component, HostListener, Input, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { ControlService } from 'core/services/control/control.service';
import { TimezoneService } from 'core/services/timezone/timezone.service';

@Component({
    selector: 'op-timepicker',
    templateUrl: './op-timepicker.component.html',
      styleUrls: ['./op-timepicker.component.css']
})

export class Op_TimePickerComponent implements OnInit {

    public isClassVisible = true;

    @Input('dataSource')
    public dataSource: any;

    @Input('dataField')
    public dataField: string;

    @Input('fieldDate')
    public fieldDate:Date;

    @ViewChild('optimerPicker')
    optimerPicker: any;

    @ViewChild('opTimepickerContainer')
    opTimepickerContainer: any;

    @Input('defaultTimezone')
    defaultTimezone: any = '';

    listTimezoneValues : any[] = [];

    public selected_value: Date = new Date();

    @Input('setDefault')
    public setdefault: boolean = true;

    @HostListener('document:click', ['$event'])
    clickout(event) {
        if (!this.opTimepickerContainer.nativeElement.contains(event.target)) {
                    this.isClassVisible = true;
                }
    }

    constructor(private timeZoneService: TimezoneService, private controlApp: ControlService,
        private ref: ChangeDetectorRef) {
        if (this.controlApp && this.controlApp.account && this.controlApp.account.timezone) {
            this.defaultTimezone = this.controlApp.account.timezone;
        } else {
        }


        this.timeZoneService.getTimezone()
            .subscribe(
            result => this.setTimezoneList(result),
            error => console.log(error)
            );
    }

    ngOnInit() {

        this.optimerPicker.arrowkeys 			= true;
        this.optimerPicker.canDecrementHours 	= true;
        this.optimerPicker.canDecrementMinutes 	= true;
        this.optimerPicker.canDecrementSeconds 	= true;
        this.optimerPicker.canIncrementHours 	= true;
        this.optimerPicker.canIncrementMinutes 	= true;
        this.optimerPicker.canIncrementSeconds 	= true;
        this.optimerPicker.hourStep 			= 1;
        this.optimerPicker.hours 				= '';
        this.optimerPicker.invalidHours 		= false;
        this.optimerPicker.invalidMinutes 		= false;
        this.optimerPicker.invalidSeconds 		= false;
        this.optimerPicker.meridian 			= 'AM';
        this.optimerPicker.minuteStep 			= 5;
        this.optimerPicker.minutes 				= '';
        this.optimerPicker.mousewheel 			= true;
        this.optimerPicker.readonlyInput 		= false;
        this.optimerPicker.seconds 				= '';
        this.optimerPicker.timezone				= this.defaultTimezone;
        this.optimerPicker.secondsStep 			= 10;
        this.optimerPicker.showMeridian 		= true;
        this.optimerPicker.showTimezone 		= true;
        this.optimerPicker.showSeconds 			= false;
        this.optimerPicker.showSpinners 		= true;
        //this.optimerPicker.timezone_bean 		= {};
        this.optimerPicker.timezone_list 		= this.listTimezoneValues;
        this.selected_value = new Date();
    }

    adjustPosition(positionElement) {

        if (this.dataSource && this.dataField) {
            if (this.isClassVisible) {
                this.dataSource[this.dataField] = this.optimerPicker.hours + ':' + this.optimerPicker.minutes;
            } else {
                const aux: Date = new Date(this.fieldDate);

                if (aux) {
                    this.optimerPicker.value 	= aux;
                }
            }
        } else if (this.dataField) {
            if (this.isClassVisible) {
                this.dataField = this.optimerPicker.hours + ':' + this.optimerPicker.minutes;
            } else {
                const aux: Date = new Date(this.fieldDate);

                if (aux) {
                    this.optimerPicker.value 	= aux;
                }
            }
        }

        this.optimerPicker.changeTimezone(null);
        this.isClassVisible = !this.isClassVisible;
    }

    verifyTimeSelected() {
        this.selected_value.setHours(this.optimerPicker.hours);
        this.selected_value.setMinutes(this.optimerPicker.minutes);
        // console.log(this.optimerPicker.hours + ":" + this.optimerPicker.minutes + "TMZ" + this.optimerPicker.timezone);
    }

    formatTMZString() {

        let tmz = '';
        let resto   = 0;
        var valTmz : any = new Number(this.optimerPicker.timezone);
        if ((this.optimerPicker.timezone_bean.value - Math.floor(this.optimerPicker.timezone_bean.value)) > 0) {

             resto = (30 * (this.optimerPicker.timezone_bean.value - Math.floor(this.optimerPicker.timezone_bean.value))) / 0.50;
             valTmz  = new Number(Math.floor(this.optimerPicker.timezone_bean.value));
       } else if ((!this.optimerPicker || !this.optimerPicker.timezone_bean || !this.optimerPicker.timezone_bean.value) && this.optimerPicker.timezone) {

            resto = (30 * (this.optimerPicker.timezone - Math.floor(this.optimerPicker.timezone))) / 0.50;
            valTmz  = new Number(Math.floor(this.optimerPicker.timezone));
       }

        if ( valTmz < 10) {

            if ( valTmz < 0) {

                if ( valTmz > -10) {
                    valTmz = (-1 * valTmz);
                    tmz = '-0' + valTmz;
                } else {
                    tmz = valTmz.toString();
                }
            } else {

                if ( valTmz > -1) {
                    tmz = '+0' + valTmz;
                } else {
                    tmz = '+' + valTmz.toString();
                }
            }
        } else {
            tmz = '+' + valTmz.toString();
        }

        tmz = tmz + ":" + ((resto == 0)?"00":resto.toString());
        return tmz;
    }

    getSelectedValue() {
        let str = '';

        if (this.optimerPicker.hours && this.optimerPicker.minutes) {
            str = this.optimerPicker.hours + ':' + this.optimerPicker.minutes + this.optimerPicker.meridian + '(GMT' + this.formatTMZString() + ')';
        }

        return str;
    }

    resetValue() {
        this.optimerPicker.hours	= null;
        this.optimerPicker.minutes	= null;
        this.optimerPicker.timezone	= this.controlApp.account.timezone.value;
    }

    setDefaultTimeZone(res) {
        this.optimerPicker.timezone = res.value;
        this.defaultTimezone = res;

        if (this.listTimezoneValues && this.listTimezoneValues.length > 0 && !this.optimerPicker.timezone_bean) {
            this.listTimezoneValues.forEach(element => {
                if (element.idTimezone == res.idTimezone) {
                    this.optimerPicker.timezone_bean = element;
                }
            });
        } else {
            if (!this.optimerPicker.timezone_bean) {
                this.optimerPicker.timezone_bean = res;
            }
        }
        this.optimerPicker.changeTimezone(null);
        this.optimerPicker._cd.detectChanges();
    }

    setTimezoneList(result: any) {
        if (!result) {
            console.log('Não foi possível recuperar fuso horários.')
        } else {

            this.listTimezoneValues.splice(0, this.listTimezoneValues.length);
            result[0].forEach(element => {
                if (this.setdefault && this.controlApp && this.controlApp.account && this.controlApp.account.timezone && (element.idTimezone == this.controlApp.account.timezone.idTimezone)) {
                    this.optimerPicker.timezone_bean = element;
                } else if (this.setdefault && (!this.controlApp.account || !this.controlApp.account.timezone) && (this.optimerPicker && this.optimerPicker.timezone_bean) && (element.idTimezone == this.optimerPicker.timezone_bean.idTimezone)) {
                    this.optimerPicker.timezone_bean = element;
                }
                this.listTimezoneValues.push(element);
            });

            if (this.setdefault) {
                this.timeZoneService.getDefaultTimezone().subscribe(
                    result => this.setDefaultTimeZone(result),
                    error => console.log('Erro ao recuperar timzone.............')
                );
            }
        }

    }


    hide(event) {
        this.isClassVisible = event;
    }
}
