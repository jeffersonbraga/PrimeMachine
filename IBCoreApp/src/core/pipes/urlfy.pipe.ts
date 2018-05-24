import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name : 'urlfy'
})
export class UrlfyPipe implements PipeTransform {
    transform(value) {
        if (value) {
            const REG_EXP_URL = '[-a-zA-Z0-9@:%_\\+.~#?&//=]{2,256}\\.[a-z]{2,4}\\b(\\/[-a-zA-Z0-9@:%_\\+.~#?&//=]*)?';
            const s: string[] = value ? value.replace( /\n/g, ' ').split(' ') : [];
            let matcher;

            for (let i = 0; i < s.length; i++) {
                matcher = s[i].match(REG_EXP_URL);

                if (matcher) {
                    s[i] = '<a href="' + this.checkHTTP(s[i]) + '" target="_blank">' + s[i] + '</a>';
                }
            }

            return s.join(' ').trim();
        }else {
            return null;
        }
    }

    checkHTTP(url: string) {
        if (!/^(f|ht)tps?:\/\//i.test(url)) {
            url = 'http://' + url;
        }

        return url;
    }
}
