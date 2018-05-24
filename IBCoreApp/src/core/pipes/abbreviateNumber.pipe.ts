import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name : 'abbreviateNumber'
})

export class AbbreviateNumberPipe implements PipeTransform {

    transform(value: number) {

        let abbreviatedValue: string;

        abbreviatedValue = String(value);

        if(value >= 1000 && value < 1000000) {
            abbreviatedValue = this.verifyDozenValue(value,  1000) + 'k';
        } else if(value >= 1000000 && value < 1000000000) {
            abbreviatedValue = this.verifyDozenValue(value, 1000000) + 'M';
        } else if(value >= 1000000000 && value < 1000000000000) {
            abbreviatedValue = this.verifyDozenValue(value, 1000000000000) + 'B';
        }

        return abbreviatedValue;
    }

    // Verify if a truncatedvalue is less than ten.
    verifyDozenValue(realValue: number, dividedBy: number) {

        let truncatedValue: string;
        let truncatedValueN: number;

        truncatedValueN = Math.floor(realValue / dividedBy);

        if(truncatedValueN < 10) {
            truncatedValue = String(parseFloat((realValue / dividedBy).toFixed(1)));
        } else {
            truncatedValue = String(truncatedValueN);
        }

        return truncatedValue;
    }

}
