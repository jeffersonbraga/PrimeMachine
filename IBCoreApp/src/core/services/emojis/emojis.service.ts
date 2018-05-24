import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class EmojiService {
    public dataSource = new Subject<any>();
    dataSource$ = this.dataSource.asObservable();

    public dataField = new Subject<string>();
    dataField$ = this.dataField.asObservable();

    public showEmojiList = new Subject<boolean>();
    showEmojiList$ = this.showEmojiList.asObservable();

    public positionElement = new Subject<ClientRect>();
    positionElement$ = this.positionElement.asObservable();

    public element = new Subject<HTMLElement>();
    element$ = this.element.asObservable();

    public cursorPosition: number;
    public emojiShowed: boolean;

    currentElement: HTMLElement;

    showEmoji(source: any, field: any, position: ClientRect) {
        this.dataSource.next(source);
        this.dataField.next(field);
        this.positionElement.next(position);
        this.showEmojiList.next(true);
        this.emojiShowed = true;
    }

    updatePosition() {
        if (this.currentElement) {
            this.element.next(this.currentElement);
            const position = this.currentElement.getBoundingClientRect();
            this.positionElement.next(position);
        }
    }

    closeEmoji() {
        this.showEmojiList.next(false);
        this.element.next(null);
        this.emojiShowed = false;
    }
}
