import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'scapeBreakLines'
})
export class ScapeBreakLinesPipe implements PipeTransform {
    transform(value) {
        if (value) {
            return value.replace(/(?:\r\n|\r|\n)/g, ' <br> ');
        } else {
            return value;
        }
    }
}
