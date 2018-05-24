import { Component } from '@angular/core';

import { DialogService } from 'core/services/dialog/dialog.service';

@Component({
    selector: 'confirm-dialog-modal',
    templateUrl: './confirm-dialog.component.html',
    styleUrls: ['./confirm-dialog.component.css']
})

export class ConfirmDialogComponent {
    constructor(public dialogService: DialogService) {}

    confirm() {
        this.dialogService.dialogConfirmation();
    }

    reject() {
        this.dialogService.dialogRejection();
    }
}
