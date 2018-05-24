import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'seeMoreFb'
})
export class SeeMoreFbPipe implements PipeTransform {
    part: number;

    transform(value: string, part: number) {
        this.part = part;
        if (value) {
            const length = value.length,
                numberOfLines = value.split(/\r\n|\r|\n/).length,
                splitedLines = value.split(/\r\n|\r|\n/);
            let sumFirstLines = 0;

            if (numberOfLines > 5) {
                for (let i = 0; i < 5; i++) {
                    sumFirstLines += splitedLines[i].length;
                }

                if (sumFirstLines > 850) {
                    return this.transformByLength(value);
                }else {
                    return this.transformByLines(splitedLines);
                }
            } else if (length > 850) {
                return this.transformByLength(value);
            } else {
                return value;
            }
        }else {
            return value;
        }
    }

    transformByLength(value) {
        const part1: string = value.substring(0, 480),
            part2: string = value.substring(480);

        return this.composeReturn(part1, part2);
    }

    transformByLines(splitedLines) {
        let part1 = '',
            part2 = '';
        const length: number = splitedLines.length;

        for (let i = 0; i < 5; i++) {
            part1 += splitedLines[i];
            part1 += i < 4 ? '\n' : '';
        }

        for (let i = 5; i < length; i++) {
            part2 += i === 5 ? '\n' : '';
            part2 += splitedLines[i] + '\n';
        }

        return this.composeReturn(part1, part2);
    }

    composeReturn(part1: string, part2: string) {
        if (this.part === 1) {
            return part1;
        }else {
            return part2;
        }
    }
}
