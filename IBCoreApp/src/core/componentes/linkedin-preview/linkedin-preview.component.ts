import { AfterContentChecked, Component, ElementRef, Input, ViewChild } from '@angular/core';
import { ControlService } from 'core/services/control/control.service';
import { Post } from 'modelo/post/Post';

@Component({
    selector: 'linkedin-preview',
    templateUrl: './linkedin-preview.component.html',
    styleUrls: ['./linkedin-preview.component.css']
})
export class LinkedinPreviewComponent implements AfterContentChecked {
    @Input() post: Post;
    @Input() channel: any;

    @ViewChild('textarea') elementView: ElementRef;
    viewHeight: number;

    seeMore = true;
    oldValue = 0;

    constructor(
        private controlService: ControlService
    ) { }

    ngAfterContentChecked() {
        this.viewHeight = this.elementView.nativeElement.offsetHeight;
    }

    showHiddenContent() {
        this.seeMore = false;
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
