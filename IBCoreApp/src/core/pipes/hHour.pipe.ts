import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name : 'hHour'
})
export class HHourPipe implements PipeTransform {
    transform(value) {
        return value.split(':').join('h');
    }
}
