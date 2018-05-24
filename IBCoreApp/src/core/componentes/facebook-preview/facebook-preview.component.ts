import { AfterContentChecked, Component, Input, OnInit } from '@angular/core';
import { ControlService } from 'core/services/control/control.service';
import { Post } from 'modelo/post/Post';

@Component({
    selector: 'facebook-preview',
    templateUrl: './facebook-preview.component.html',
    styleUrls: ['./facebook-preview.component.css']/*,
     encapsulation: ViewEncapsulation.None*/
})
export class FacebookPreviewComponent implements OnInit, AfterContentChecked {

    @Input() post: Post;
    @Input() channel: any;

    seeMore = false;
    showed = false;
    prevValue = '';

    constructor(private controlService: ControlService) { }

    ngOnInit() { }

    getLengthText() {
        if (this.post.text) {
            if (this.post.text.length < 85) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    showHiddenContent() {
        this.showed = true;
        this.seeMore = false;
    }

    ngAfterContentChecked() {
        const value = this.post.text;

        if (this.prevValue !== value) {
            this.showed = false;
            this.prevValue = value;
        }

        if (value && !this.showed) {
            const length = value.length,
                numberOfLines = value.split(/\r\n|\r|\n/).length;

            if (numberOfLines > 5 || length > 850) {
                this.seeMore = true;
            } else {
                this.seeMore = false;
            }
        }
    }

    play(element: HTMLElement, type: String) {
        if (type === 'V') {
            const video = element.getElementsByTagName('video');

            if (video) {
                if (video[0].paused) {
                    video[0].play();
                } else {
                    video[0].pause();
                }
            }
        }
    }
}
