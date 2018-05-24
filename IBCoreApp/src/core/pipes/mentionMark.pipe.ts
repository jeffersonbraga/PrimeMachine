import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import * as lodash from 'lodash/';

@Pipe({
    name: 'mentionMark'
})
export class MentionMark implements PipeTransform {
    constructor(
        private sanitizer: DomSanitizer
    ) {}

    transform(value, tags, type) {
        let expression,
            href;
        if (value && tags) {
            value = this.checkValue(value);
            if (type !== 'T' && type !== 'I') {
                tags = lodash.uniqBy(tags, 'id');
                const tam = tags.length;

                for (let i = 0; i < tam; i++) {
                    expression = tags[i].name;
                    href = tags[i].id;

                    value = value.replace(new RegExp(expression, 'g'),
                        `<span class="mentionMark" data-type="m" data-network="${type}" 
                            data-href="${href}">${expression}</span>`);
                }
                return this.sanitizer.bypassSecurityTrustHtml(value);
            } else {
                return this.sanitizer.bypassSecurityTrustHtml(value);
            }
        }else {
            return value;
        }
    }

    checkValue(value): string {
        if (value.changingThisBreaksApplicationSecurity) {
            return value.changingThisBreaksApplicationSecurity;
        } else {
            return value;
        }
    }
}
