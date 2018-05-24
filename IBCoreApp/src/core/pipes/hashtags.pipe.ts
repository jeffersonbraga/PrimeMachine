import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

declare const $;

@Pipe({
    name: 'hashtags'
})
export class HashtagsMark implements PipeTransform {
    constructor(
        private sanitizer: DomSanitizer,
    ) {}

    transform(value, type) {
        if (value) {
            // value = this.hashtag(value, /(\s|^)\#\w\w+\b/gm, type);
            value = this.hashtag(value, /\#(\w|[\u00C0-\u00FF])\w+[\u00C0-\u00FF|\w+\b]+(\w+\b|(.|^))/gmi, type);
            if (type === 'T' || type === 'I') {
                value = this.mention(value, /\@(\w|[\u00C0-\u00FF])\w+[\u00C0-\u00FF|\w+\b]+(\w+\b|(.|^))/gmi, type);
            }
            return this.sanitizer.bypassSecurityTrustHtml(value);
        }else {
            return null;
        }
    }

    hashtag(value, regexp, type) {
        let result = value.match(regexp);
        if (result) {
            result = result.map(function(s){ return s.trim(); });

            for (let i = 0; i < result.length; i++) {
                value = value.replace(new RegExp(result[i], 'g'),
                    `<span class="mentionMark" data-type="h" data-network="${type}" 
                        data-href="${result[i].replace('#', '')}">${result[i]}</span>`);
            }
        }
        return value;
    }

    mention(value, regexp, type) {
        let result = value.match(regexp);

        if (result) {
            result = result.map(function(s){ return s.trim(); });

            for (let i = 0; i < result.length; i++) {
                value = value.replace(new RegExp(result[i], 'g'),
                    `<span class="mentionMark" data-type="m" data-network="${type}" 
                        data-href="${result[i].replace('@', '')}">${result[i]}</span>`);
            }
        }
        return value;
    }
}
