import { DatePipe } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'truncHour'
})
export class TruncHour implements PipeTransform {
    constructor(
        private DatePipe: DatePipe
    ) {};

    transform(value): string {
        if (value) {
            const now = new Date();
            const date = new Date(value * 1000);
            const timeToTrunc = (now.getTime() - date.getTime()) / 1000;

            if (timeToTrunc < 60) {
                return 'Now';
            }else if (timeToTrunc < 3600) {
                return Math.trunc(timeToTrunc / 60) + ' minutes ago';
            }else if (timeToTrunc < 86400) {
                const hours = Math.trunc(timeToTrunc / 3600);
                return hours === 1 ? '1 hour ago' : hours + ' hours ago';
            }else if (timeToTrunc < 86400) {
                const hours = Math.trunc(timeToTrunc / 3600);
                return hours === 1 ? '1 hour ago' : hours + ' hours ago';
            }else if (timeToTrunc < 172800) {
                return 'Yesterday';
            }else {
                return this.DatePipe.transform(date, 'MM/dd/yyyy');
            }

        }else {
            return 'Now';
        }
    }

    // transform(value): string {
    //     if (value) {
    //         const now = new Date();
    //         const date = new Date(value * 1000);
    //         const timeToTrunc = (now.getTime() - date.getTime()) / 1000;

    //         if (timeToTrunc < 60) {
    //             return 'Agora mesmo';
    //         }else if (timeToTrunc < 3600) {
    //             return Math.trunc(timeToTrunc / 60) + ' minutos atrás';
    //         }else if (timeToTrunc < 86400) {
    //             const hours = Math.trunc(timeToTrunc / 3600);
    //             return hours === 1 ? '1 hora atrás' : hours + ' horas atrás';
    //         }else if (timeToTrunc < 86400) {
    //             const hours = Math.trunc(timeToTrunc / 3600);
    //             return hours === 1 ? '1 hora atrás' : hours + ' horas atrás';
    //         }else if (timeToTrunc < 172800) {
    //             return 'Ontem';
    //         }else {
    //             return this.DatePipe.transform(date, 'MM/dd/yyyy');
    //         }

    //     }else {
    //         return 'Agora mesmo';
    //     }
    // }
}
