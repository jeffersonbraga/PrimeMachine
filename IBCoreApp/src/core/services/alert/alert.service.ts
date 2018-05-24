import { Injectable } from '@angular/core';
import { IAlert } from 'modelo/alert/Alert';

declare var jQuery: any;

@Injectable()
export class AlertService {
    public titleBar: string;
    public message: string;
    public icon: string;
    public okMessage: string;
    public okClass: string;

    public dialogDismiss: () => void;

    alert(options: IAlert) {
        this.titleBar = options.titleBar;
        this.message = options.message;
        this.icon = options.icon ? options.icon : 'fa-exclamation-triangle';
        this.okMessage = options.okMessage ? options.okMessage : 'Ok';
        this.okClass = options.okClass ? options.okClass : 'btn-info';

        return new Promise<boolean>((resolve, reject) =>{
            this.dialogDismiss = () => resolve(true);

            jQuery('#alert-dialog').modal();
        });
    };
}