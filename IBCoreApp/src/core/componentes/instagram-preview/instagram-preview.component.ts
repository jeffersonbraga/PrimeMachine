import { Component, Input } from '@angular/core';
import { ControlService } from 'core/services/control/control.service';
import { Post } from 'modelo/post/Post';

@Component({
    selector: 'instagram-preview',
    templateUrl: './instagram-preview.component.html',
    styleUrls: ['./instagram-preview.component.css']
})
export class InstagramPreviewComponent {

    @Input() post: Post;
    @Input() channel: any;

    constructor(private controlService: ControlService) {
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
