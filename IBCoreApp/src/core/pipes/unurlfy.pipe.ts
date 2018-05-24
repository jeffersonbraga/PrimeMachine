import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'unurlfy'
})
export class UnurlfyPipe implements PipeTransform {
    transform(value) {
        if (value) {
            const protomatch = /^(https?|ftp):\/\//; // NB: not '.*'
            return value.replace(protomatch, '');
        }else {
            return null;
        }
    }
}
