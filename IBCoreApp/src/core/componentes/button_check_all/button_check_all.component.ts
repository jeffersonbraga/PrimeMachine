import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
    selector: 'button_check_all',
    templateUrl: './button_check_all.component.html'
})
export class Button_check_allComponent {

    // isClassVisible = false;

    @Output() checkAll: EventEmitter<boolean> = new EventEmitter<boolean>();

    @Input('isClassVisible') isClassVisible: boolean;

    checkUncheck() {
        this.isClassVisible = !this.isClassVisible;
        this.checkAll.emit(this.isClassVisible);
    }
}
