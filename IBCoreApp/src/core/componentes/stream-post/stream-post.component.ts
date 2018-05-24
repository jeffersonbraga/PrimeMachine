import { Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ControlService } from 'core/services/control/control.service';
import { DialogService } from 'core/services/dialog/dialog.service';
import { EmojiService } from 'core/services/emojis/emojis.service';
import { StreamService } from 'core/services/stream/stream.service';
import { UploadService } from 'core/services/upload/upload.service';
import { Profile } from 'modelo/profile/Profile';
import { StreamPostPaging } from 'modelo/stream/StreamPostPaging';

declare const $;

@Component({
    selector: 'stream-post',
    templateUrl: './stream-post.component.html',
    styleUrls: ['./stream-post.component.css']
})
export class StreamPostComponent implements OnInit {
    myDataField = 'value';
    showed: boolean;
    typeModal: string;
    mediaPreview: string;
    videoName: string;
    typeMedia: any;
    attachment: any;
    preliked: boolean;

    liked = false;
    editing = false;
    editingPost = false;
    deleting = false;
    hasImage = false;
    loadingImageComment = false;
    openTwitterReply = false;
    retweeting = false;
    seeMore = false;
    enableSeeMore = false;
    showedPost = false;
    type: string;

    @Input()
    post: any;

    @Input()
    profile: Profile;

    @Input()
    index: number;

    @Input()
    tab: string;

    sendingComment = false;

    streamPostPaging: StreamPostPaging = new StreamPostPaging();

    @Output() emit: EventEmitter<number> = new EventEmitter<number>();

    @ViewChild('textareaEdit')
    textareaEdit: ElementRef;

    @ViewChild('textarea')
    textarea: ElementRef;

    @ViewChild('imgPreview')
    imgPreview: ElementRef;

    // emoji
    showEmojiList = false;
    @ViewChild('containerEmoji')
    public containerEmoji: any;

    emojiShowed = false;
    emojiEditShowed = false;

    // upload
    fileList: FileList;

    @ViewChild('fileInput') fileInput: ElementRef;

    @ViewChild('streamComment') streamComment: ElementRef;

    @HostListener('document:click', ['$event'])
    clickout(event) {
        if (this.streamComment) {
            if (!this.streamComment.nativeElement.contains(event.target)
                && event.srcElement.getAttribute('data-toggle-element') !== 'reply'
                && this.openTwitterReply) {
                    if (document.getElementById('emojiComponent')) {
                        if (!document.getElementById('emojiComponent').contains(event.target)) {
                            this.toggleTwitterReply();
                        }
                    } else {
                        this.toggleTwitterReply();
                    }
                }
        }
    }

    constructor(
        private controlService: ControlService,
        private StreamService: StreamService,
        private DialogService: DialogService,
        private EmojiService: EmojiService,
        private UploadService: UploadService,
        private ControlService: ControlService
    ) {
    }

    ngOnInit() {
        if (this.profile.type === 'I') {
            this.getComments();
        }

        this.preliked = this.post.liked;
        this.type = this.profile.type;
        this.checkSeeMore();
    }

    openPhoto(media, type) {
        this.showed = true;
        this.mediaPreview = media;

        if (type === 'photo') {
            this.typeModal = 'P';
        }else if (type === 'video') {
            this.typeModal = 'V';
        }else {
            this.typeModal = 'G';
        }
    }

    like() {
        this.preliked = !this.preliked;
        this.preliked ? this.post.likesCount++ : this.post.likesCount--;

        const streamPostAux = {
            streamPost: this.post,
            idProfile: this.profile.idProfile
        };

        this.StreamService.likePost(streamPostAux)
        .subscribe(
            result => {
                    this.post.liked = !this.post.liked;
                }
            );
    }

    confirmDeletePost() {
        const promise = this.DialogService.confirm({
            titleBar: 'Delete post',
            message: 'Do you really want to delete this post?',
            yesMessage: '<i class="fa fa-times" aria-hidden="true"></i> Deletar',
            noMessage: 'Cancelar'
        });

        promise.then(res => {
            if (res) {
                this.deletePost();
            }
        });
    }

    deletePost() {
        this.deleting = true;

        const actionDeleteAux = {
            postId: this.post.postId,
            commentId: null,
            idProfile: this.profile.idProfile
        };

        this.StreamService.deletePost(actionDeleteAux)
            .subscribe(
                result => {
                    this.emit.emit(this.index);
                }
            );
    }

    getComments() {
        if (this.streamPostPaging.limit !== 2) {
            this.streamPostPaging.limit = 2;
        }

        const streamPostCommentsAux = {
            streamPostPaging: this.streamPostPaging,
            streamPost: this.post,
            idProfile: this.profile.idProfile
        };

        this.StreamService.getComments(streamPostCommentsAux)
        .subscribe(
            result => {
                this.post.streamPostComments = result;
            }
        );
    }

    loadMoreComments() {
        if (this.post.streamPostComments.paging.limit !== 2) {
            this.post.streamPostComments.paging.limit = 2;
        }

        const streamPostCommentsAux = {
            streamPostPaging: this.post.streamPostComments.paging,
            streamPost: this.post,
            idProfile: this.profile.idProfile
        };

        this.StreamService.getComments(streamPostCommentsAux)
        .subscribe(
            result => {
                this.post.streamPostComments.comments.push.apply(this.post.streamPostComments.comments, result.comments);
                this.post.streamPostComments.paging = result.paging;
            }
        );
    }

    spliceComment(index) {
        this.post.streamPostComments.comments.splice(index, 1);
        this.post.commentsCount--;
    }

    startEditPost() {
        this.editing = true;
        setTimeout(
            () => {
                this.resizeTextarea(this.textareaEdit.nativeElement);
                this.textareaEdit.nativeElement.focus();
            },
            100
        );
    }

    editPost($event: KeyboardEvent, textarea: HTMLTextAreaElement) {
        if ($event.keyCode === 27) {
            this.editing = false;
        }
        if ($event.keyCode === 13 && !$event.shiftKey && !this.editingPost) {
            $event.preventDefault();
            this.editing = false;
            this.editingPost = true;

            if (textarea.value.trim()) {
                const actionShareAux = {
                    streamPost: this.post,
                    message: this.post.text,
                    idProfile: this.profile.idProfile
                };

                this.StreamService.editPost(actionShareAux)
                    .subscribe(
                        result => {
                            this.editingPost = false;
                        }
                    );
            }
        }
    }

    sendComment($event: KeyboardEvent, textarea: HTMLTextAreaElement) {
        if ($event.keyCode === 13 && !$event.shiftKey) {
            $event.preventDefault();

            if ((textarea.value.trim() || this.attachment) && !this.sendingComment) {
                this.sendingComment = true;
                const actionCommentAux = {
                    streamPostComment: {
                        message: textarea.value,
                        attachment: this.attachment,
                        type: this.typeMedia
                    },
                    streamPost: this.post,
                    idProfile: this.profile.idProfile,
                    commentId: null
                };

                this.StreamService.commentPost(actionCommentAux)
                    .subscribe(
                        result => {
                            if (this.profile.type !== 'T') {
                                this.post.streamPostComments.comments.push(result);
                            } else {
                                this.toggleTwitterReply();

                                const options = {
                                    message: 'Tweet respondido com sucesso!',
                                    style: 'bar',
                                    timeout: 8000,
                                    position: 'top'
                                };

                                $('body').pgNotification(options).show();
                            }
                            textarea.value = '';
                            this.sendingComment = false;
                            this.post.commentsCount++;
                            this.cleanAttachment();
                        }
                    );
            }
        }
    }

    resizeTextarea(textarea: HTMLTextAreaElement) {
        if (!textarea.value.trim() && this.profile.type === 'T') {
            this.toggleTwitterReply();
        }

        if (textarea.scrollHeight > 34) {
            textarea.style.height = '37px';
            textarea.style.height = (textarea.scrollHeight + 3) + 'px';
        } else {
            textarea.style.height = '37px';
        }
    }

    public showEmoji(emojiTrigger: HTMLElement) {
        if (this.emojiShowed) {
            this.EmojiService.closeEmoji();
        } else {
            this.openEmoji(emojiTrigger, this.textarea);
        }

        this.emojiShowed = !this.emojiShowed;
    }

    public showEmojiEdit(emojiTrigger: HTMLElement) {
        if (this.EmojiService.emojiShowed) {
            this.EmojiService.closeEmoji();
        } else {
            this.openEmoji(emojiTrigger, this.textareaEdit);
        }
    }

    openEmoji(emojiTrigger: HTMLElement, element: ElementRef) {
        const position = emojiTrigger.getBoundingClientRect();
        this.EmojiService.currentElement = emojiTrigger;
        this.EmojiService.showEmoji(element.nativeElement, 'value', position);
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

    toggleTwitterReply() {
        if (this.tab !== 'P') {
            this.openTwitterReply = !this.openTwitterReply;

            setTimeout(() => {
                if (this.openTwitterReply) {
                    this.textarea.nativeElement.focus();
                }
            }, 10);
        }
    }

    toggleRetweet() {
        if (this.tab !== 'P') {
            this.retweeting = !this.retweeting;
        }
    }

    retweet() {
        const actionShareAux = {
            streamPost: this.post,
            message: '',
            idProfile: this.profile.idProfile
        };

        this.StreamService.sharePost(actionShareAux)
            .subscribe(
                result => {
                    this.toggleRetweet();

                    const options = {
                        message: 'Retweet feito com sucesso!',
                        style: 'bar',
                        timeout: 8000,
                        position: 'top'
                    };

                    $('body').pgNotification(options).show();
                }
            );
    }

    showContent() {
        this.showedPost = true;
        this.seeMore = false;
    }

    hideContent() {
        this.showedPost = false;
        this.seeMore = true;
    }

    getTypeMedia(url) {
        const extension = url.match(/.jpeg|.jpg|.png/gi);

        if (extension) {
            return 'image';
        } else {
            return 'video';
        }
    }

    checkSeeMore() {
        const value = this.post.text;

        if (value && !this.showed) {
            const length = value.length,
                numberOfLines = value.split(/\r\n|\r|\n/).length;

            if (numberOfLines > 5 || length > 850) {
                this.seeMore = true;
                this.enableSeeMore = true;
            } else {
                this.seeMore = false;
            }
        }
    }
}
