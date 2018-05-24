import { EmojiService } from 'core/services/emojis/emojis.service';
import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
    selector: '[getCursorPosition]'
})
export class GetCursorPositionDirective {

    @HostListener('click', ['$event'])
    @HostListener('blur', ['$event'])
    @HostListener('keyup', ['$event'])
    @HostListener('focus', ['$event']) getPosition(event) {
        this.saveCursorPosition(event);
    };

    constructor(
        private emojiService: EmojiService
    ) {

    }

    saveCursorPosition(event) {
        this.emojiService.cursorPosition = event.target.selectionStart;
    }
}
