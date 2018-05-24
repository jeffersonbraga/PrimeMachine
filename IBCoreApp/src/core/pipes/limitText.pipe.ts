import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'limitText'
})
export class LimitTextPipe implements PipeTransform {
    transform(value: string, limit: number) {
        if (value) {
            return value.substr(0, limit);
        } else {
            return '';
        };
    }
}
