import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ControlService } from 'core/services/control/control.service';
import { DialogService } from 'core/services/dialog/dialog.service';
import { EmojiService } from 'core/services/emojis/emojis.service';
import { StreamService } from 'core/services/stream/stream.service';
import { UploadService } from 'core/services/upload/upload.service';
import { Profile } from 'modelo/profile/Profile';

declare const $;

@Component({
    selector: 'stream-post-comment',
    templateUrl: './stream-post-comment.component.html',
    styleUrls: ['./stream-post-comment.component.css']
})

export class StreamPostCommentComponent implements OnInit {

    @Input()
    comment;

    @Input()
    post;

    @Input()
    profile: Profile;

    @Input()
    index: number;

    @Output() emit: EventEmitter<number> = new EventEmitter<number>();

    sendingReply = false;
    replyShowed = false;
    preliked: boolean;
    deleting = false;
    hasImage = false;
    loadingImageComment = false;
    emojiShowed = false;

    type: string;
    showed: boolean;
    typeModal: string;
    mediaPreview: string;
    videoName: string;
    typeMedia: any;
    attachment: any;

    @ViewChild('textarea')
    textarea: ElementRef;

    @ViewChild('imgPreview')
    imgPreview: ElementRef;

    // upload
    fileList: FileList;

    @ViewChild('fileInput') fileInput: ElementRef;

    constructor(
        private StreamService: StreamService,
        private DialogService: DialogService,
        private EmojiService: EmojiService,
        private UploadService: UploadService,
        private ControlService: ControlService
    ) {
    }

    ngOnInit() {
        this.preliked = this.comment.userLikes;
        this.type = this.profile.type;
    }

    getReplies() {
        if (this.comment.replies.paging.limit !== 2) {
            this.comment.replies.paging.limit = 2;
        }

        const streamPostCommentRepliesAux = {
            streamPostPaging: this.comment.replies.paging,
            streamPostComment: this.comment,
            idProfile: this.profile.idProfile
        };

        this.StreamService.getReplies(streamPostCommentRepliesAux)
            .subscribe(
                result => {
                    this.comment.replies.comments.push.apply(this.comment.replies.comments, result.comments);
                    this.comment.replies.paging = result.paging;
                }
            );
    }

    confirmDeleteComment() {
        const promise = this.DialogService.confirm({
            titleBar: 'Delete comment',
            message: 'Do you really want to delete this comment?',
            yesMessage: '<i class="fa fa-times" aria-hidden="true"></i> Delete',
            noMessage: 'Cancel'
        });

        promise.then(res => {
            if (res) {
                this.deleteComment();
            }
        });
    }

    deleteComment() {
        this.deleting = true;

        const actionDeleteAux = {
            postId: this.post.postId,
            commentId: this.comment.commentId,
            idProfile: this.profile.idProfile
        };

        this.StreamService.deleteComment(actionDeleteAux)
            .subscribe(
                result => {
                    this.emit.emit(this.index);
                }
            );
    }

    spliceReply(index) {
        this.comment.replies.comments.splice(index, 1);
        this.comment.commentCount--;
    }

    showReply() {
        this.replyShowed = !this.replyShowed;

        if (this.replyShowed) {
            setTimeout(
                () => this.textarea.nativeElement.focus(),
                100
            );
        }
    }

    sendReply($event: KeyboardEvent, textarea: HTMLTextAreaElement) {
        if ($event.keyCode === 13 && !$event.shiftKey) {
            $event.preventDefault();

            if (textarea.value.trim() && !this.sendingReply) {
                this.sendingReply = true;
                const actionCommentAux = {
                    streamPostComment: {
                        message: textarea.value,
                        attachment: this.attachment,
                        type: this.typeMedia
                    },
                    streamPost: this.post,
                    idProfile: this.profile.idProfile,
                    commentId: this.comment.commentId
                };

                this.StreamService.replyPost(actionCommentAux)
                    .subscribe(
                        result => {
                            this.comment.replies.comments.push(result);
                            textarea.value = '';
                            this.sendingReply = false;
                        }
                    );
            }
        }
    }

    resizeTextarea(textarea: HTMLTextAreaElement) {
        if (textarea.scrollHeight > 34) {
            textarea.style.height = '37px';
            textarea.style.height = (textarea.scrollHeight + 3) + 'px';
        }else {
            textarea.style.height = '37px';
        }
    }

    likeComment() {
        this.preliked = !this.preliked;

        const actionLikeAux = {
            streamPost: this.post,
            streamPostComment: this.comment,
            idProfile: this.profile.idProfile
        };

        this.StreamService.likeCommentAndReply(actionLikeAux)
        .subscribe(
            result => {
                this.comment.userLikes = !this.comment.userLikes;
            }
        );
    }

    public showEmoji(emojiTrigger: HTMLElement) {
        if (this.emojiShowed) {
            this.EmojiService.closeEmoji();
        } else {
            const position = emojiTrigger.getBoundingClientRect();
            this.EmojiService.currentElement = emojiTrigger;
            this.EmojiService.showEmoji(this.textarea.nativeElement, 'value', position);
        }

        this.emojiShowed = !this.emojiShowed;
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

    openFileUpload() {
        this.fileInput.nativeElement.click();
    }

    saveImage(fileInput: any) {
        if (fileInput.files && fileInput.files[0]) {
            this.fileList = fileInput.files;
            const file: File = this.fileList[0];

            if (file.type.split('/')[0] === 'video') {
                this.hasImage = true;
                this.videoName = file.name;
                this.typeMedia = 'video';
            }else {
                const reader = new FileReader();
                const comp = this;
                this.typeMedia = 'photo';

                reader.onload = function (e: any) {
                    comp.hasImage = true;
                    $(comp.imgPreview.nativeElement).attr('src', e.target.result);
                };

                reader.readAsDataURL(fileInput.files[0]);
            }

            this.loadingImageComment = true;
            this.UploadService.makeSingleFileRequest(this.ControlService.context_app + 'api/uploadcommentmedia', file)
                .subscribe(
                    response => {
                        this.mountImageComment(response);
                    }
                );
        }
    }

    mountImageComment(image) {
        this.loadingImageComment = false;
        this.attachment =  {
            media: {
                image: {
                    src: image.file_name
                }
            }
        };
    }

    cleanAttachment() {
        this.loadingImageComment = false;
        this.hasImage = false;
        this.attachment = null;
        this.typeMedia = null;
        this.fileInput.nativeElement.value = null;
    }
}
