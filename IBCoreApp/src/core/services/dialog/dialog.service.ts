import { Injectable } from '@angular/core';
import { IConfirm } from 'modelo/confirm/Confirm';

declare var jQuery: any;

@Injectable()
export class DialogService {
    public titleBar: string;
    public message: string;
    public icon: string;
    public yesMessage: string;
    public noMessage: string;
    public yesClass: string;
    public noClass: string;

    public dialogConfirmation: () => void;
    public dialogRejection: () => void;

    confirm(options: IConfirm) {
        this.titleBar = options.titleBar;
        this.message = options.message;
        this.icon = options.icon ? options.icon : 'fa-exclamation-triangle';
        this.yesMessage = options.yesMessage ? options.yesMessage : 'Sim';
        this.noMessage = options.noMessage ? options.noMessage : 'Não';
        this.yesClass = options.yesClass ? options.yesClass : 'btn-danger';
        this.noClass = options.noClass ? options.noClass : 'btn-info';

        return new Promise<boolean>((resolve, reject) => {
            this.dialogConfirmation = () => resolve(true);
            this.dialogRejection = () => resolve(false);

            jQuery('#confirm-dialog').modal();
        });
    };
}
