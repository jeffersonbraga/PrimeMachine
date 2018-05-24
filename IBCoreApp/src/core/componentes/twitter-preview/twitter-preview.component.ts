import { Component, Input } from '@angular/core';
import { ControlService } from 'core/services/control/control.service';
import { Post } from 'modelo/post/Post';

@Component({
    selector: 'twitter-preview',
    templateUrl: './twitter-preview.component.html',
    styleUrls: ['./twitter-preview.component.css']
})
export class TwitterPreviewComponent {

    @Input() post: Post;
    @Input() channel: any;
    oldValue = 0;

    constructor(private controlService: ControlService) {
    }

    hideErrorMessage() {
        this.oldValue = this.post.postsAttachments.length;
    }

    play(element: HTMLVideoElement, type: String) {
        if (element) {
            if (element.paused) {
                element.play();
            } else {
                element.pause();
            }
        }
    }
}
