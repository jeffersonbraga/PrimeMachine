import { Component } from '@angular/core';
import { AlertService } from 'core/services/alert/alert.service';

@Component({
    selector: 'alert-dialog-modal',
    templateUrl: './alert-dialog.component.html',
    styleUrls: ['./alert-dialog.component.css']
})

export class AlertDialogComponent {
    constructor(public alertService: AlertService) { }

    dismiss() {
        this.alertService.dialogDismiss();
    }
}
