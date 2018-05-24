import { ControlService } from 'core/services/control/control.service';
import { Component, EventEmitter, Injector, Input, OnInit, Output, OnChanges } from '@angular/core';
import { Headers, Response } from '@angular/http';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { PostService } from 'core/services/post/post.service';
import { WindowRefService } from 'core/services/window/window-ref.service';
import { Post } from 'modelo/post/Post';
import { PostAttachment } from 'modelo/postAttachment/PostAttachment';
import { Observable } from 'rxjs/Observable';
import { SimpleChanges } from '@angular/core/src/metadata/lifecycle_hooks';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';

@Component({
	selector: 'video-thumbnail',
	templateUrl: './videoThumbnail.component.html'
})

export class VideoThumbnailComponent implements OnInit, OnChanges {

	currentModal: GenericModalComponent;
	nativeWindow: any;

	@Input("post")
	public post: Post;

	public postAttachment: PostAttachment;

	@Input("post")
	public static postRefer: Post;

	public static selectedAttachment: PostAttachment;

	public static searchAttachmentAux: any;

	public static thumnail: any;

	public static selectedThumnailAux: any;

	public selectedThumnail: string = "";

	public videoThumbnailAux: string = "";

	@Output()
	receivePostObject: EventEmitter<Post> = new EventEmitter<Post>();

	constructor(
		private winRef: WindowRefService,
		private injector: Injector,
		private searchAttachment: PostService,
		private controlService: ControlService,
        private breadcrumbsService: BreadcrumbsService
	) {
		this.nativeWindow = winRef.getNativeWindow();
		this.currentModal = this.injector.get("currentModal");

        this.breadcrumbsService.initializeBreadcrumb();
		this.post = VideoThumbnailComponent.postRefer;
		this.postAttachment = VideoThumbnailComponent.selectedAttachment;
		VideoThumbnailComponent.searchAttachmentAux = this.searchAttachment;

		VideoThumbnailComponent.selectedThumnailAux = this.selectedThumnail;
		VideoThumbnailComponent.getThumbnail().subscribe(
          result => console.log(result),
          error => console.log(error));
	}

	ngOnInit(): void {
		console.log(" VideoThumbnailComponent INIT");
        this.breadcrumbsService.addBreadcrumb('Edit Video Thumbnail', '');
	}

	ngOnChanges(changes: SimpleChanges): void {

		if (this.postAttachment && !this.postAttachment.videoThumbnail) {

			if (VideoThumbnailComponent.thumnail && VideoThumbnailComponent.thumnail.lenght() > 0) {
				this.postAttachment.videoThumbnail = this.controlService.thumbnail_image_path + VideoThumbnailComponent.thumnail[0];
			}
		}
	}

	public static setPost(obj: Object): void {
		VideoThumbnailComponent.postRefer = obj as Post;
	}

	public static getPost(): Post {
		return VideoThumbnailComponent.postRefer;
	}

	public static setAttachment(obj: PostAttachment) {
		VideoThumbnailComponent.selectedAttachment = obj as PostAttachment;
	}

	public static extractData(res: Response): Object[] {
		if (res.status == 200) {
			VideoThumbnailComponent.thumnail = res.json();
			VideoThumbnailComponent.selectedAttachment["thumbnails"] = res.json();

			if (!VideoThumbnailComponent.selectedAttachment.videoThumbnail || VideoThumbnailComponent.selectedAttachment.videoThumbnail == "") {

				VideoThumbnailComponent.selectedAttachment.videoThumbnail = ControlService.thumbnail_image_path_aux + VideoThumbnailComponent.thumnail[0];
				VideoThumbnailComponent.selectedThumnailAux = ControlService.thumbnail_image_path_aux + VideoThumbnailComponent.thumnail[0];
			} else if ( (VideoThumbnailComponent.selectedAttachment.videoThumbnail) && (VideoThumbnailComponent.selectedAttachment.videoThumbnail != "") ) {

				VideoThumbnailComponent.selectedThumnailAux = VideoThumbnailComponent.selectedAttachment.videoThumbnail;
			}
		}
		return res.json();
	}
	
	public static handleError(error: any) {
		console.error(error);
		return Observable.throw(error.json().error || 'Server error');
	}

	public static getThumbnail() : Observable<Object> {
		VideoThumbnailComponent.searchAttachmentAux.http.url = ControlService.context_app_aux + 'api/getThumbnail';
		return VideoThumbnailComponent.searchAttachmentAux.http.post(ControlService.context_app_aux  + 'api/getThumbnail', VideoThumbnailComponent.selectedAttachment,  {header:new Headers({'Content-Type': 'application/json'}), withCredentials: true})
			.map(this.extractData)
			.catch(this.handleError);
	}

	public showSelectedImage(event : any) {
		if (this.videoThumbnailAux == "") {
			this.videoThumbnailAux = this.postAttachment.videoThumbnail;
		}

		this.postAttachment.videoThumbnail = event.currentTarget.currentSrc;
		this.selectedThumnail = this.postAttachment.videoThumbnail;
	}

	public cancelSubmit() {
		this.postAttachment.videoThumbnail = this.videoThumbnailAux;
		this.currentModal.hideModal();
	}

	public validateSubmit() {
		this.currentModal.hideModal();
	}
}