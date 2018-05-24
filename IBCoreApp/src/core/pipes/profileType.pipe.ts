import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'profileType'
})
export class ProfileType implements PipeTransform {
    transform(value) {
        let type: string;

        switch (value) {
            case 'P':
                type = 'facebook';
                break;
            case 'F':
                type = 'facebook';
                break;
            case 'I':
                type = 'instagram';
                break;
            case 'T':
                type = 'twitter';
                break;
            case 'L':
                type = 'linkedin';
                break;
        }

        return type;
    }
}
