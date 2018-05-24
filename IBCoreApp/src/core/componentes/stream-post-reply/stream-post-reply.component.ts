import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DialogService } from 'core/services/dialog/dialog.service';
import { StreamService } from 'core/services/stream/stream.service';
import { Profile } from 'modelo/profile/Profile';

@Component({
    selector: 'stream-post-reply',
    templateUrl: './stream-post-reply.component.html',
    styleUrls: ['./stream-post-reply.component.css']
})

export class StreamPostReplyComponent implements OnInit {

    @Input()
    reply;

    @Input()
    comment;

    @Input()
    post;

    @Input()
    profile: Profile;

    @Input()
    index: number;

    @Output() emit: EventEmitter<number> = new EventEmitter<number>();

    preliked: boolean;
    deleting: boolean;

    showed: boolean;
    typeModal: string;
    mediaPreview: string;

    constructor(
        private StreamService: StreamService,
        private DialogService: DialogService
    ) {
    }

    ngOnInit() {
        this.preliked = this.reply.userLikes;
    }

    confirmDeleteReply() {
        const promise = this.DialogService.confirm({
            titleBar: 'Delete reply',
            message: 'Do you really want to delete this reply?',
            yesMessage: '<i class="fa fa-times" aria-hidden="true"></i> Delete',
            noMessage: 'Cancel'
        });

        promise.then(res => {
            if (res) {
                this.deleteReply();
            }
        });
    }

    deleteReply() {
        this.deleting = true;

        const actionDeleteAux = {
            postId: this.post.postId,
            commentId: this.reply.commentId,
            idProfile: this.profile.idProfile
        };

        this.StreamService.deleteComment(actionDeleteAux)
            .subscribe(
                result => {
                    this.emit.emit(this.index);
                }
            );
    }

    likeReply() {
        this.preliked = !this.preliked;

        const actionLikeAux = {
            streamPost: this.post,
            streamPostComment: this.reply,
            idProfile: this.profile.idProfile
        };

        this.StreamService.likeCommentAndReply(actionLikeAux)
        .subscribe(
            result => {
                this.reply.userLikes = !this.reply.userLikes;
            }
        );
    }

    openPhoto(media, type) {
        this.showed = true;
        this.mediaPreview = media;

        if (type === 'photo') {
            this.typeModal = 'P';
        }else if (type === 'video') {
            this.typeModal = 'video_inline';
        }else {
            this.typeModal = 'animated_image_video';
        }
    }
}
