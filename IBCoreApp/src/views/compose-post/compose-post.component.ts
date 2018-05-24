import { ModalService } from './../../core/services/modal/modal.service';
import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, OnDestroy, OnInit, Renderer2, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Op_TimePickerComponent } from 'core/componentes/op_timepicker/op-timepicker.component';
import { CheckUrlService } from 'core/services/checkUrl/checkUrl.service';
import { ControlService } from 'core/services/control/control.service';
import { EmojiService } from 'core/services/emojis/emojis.service';
import { PostService } from 'core/services/post/post.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { UploadService } from 'core/services/upload/upload.service';
import { Post } from 'modelo/post/Post';
import { PostAttachment } from 'modelo/postAttachment/PostAttachment';
import { Profile } from 'modelo/profile/Profile';
import { BitlyShortenUrlDTO } from 'modelo/shortenLink/BitlyShortenUrlDTO';
import { DragulaService } from 'ng2-dragula/ng2-dragula';
import { BsDatepickerConfig } from 'ngx-bootstrap/datepicker';
import { Subject } from 'rxjs/Subject';
import { Subscription } from 'rxjs/subscription';

import { VideoThumbnailComponent } from './videoThumbnail.component';
import { TimeoutError } from 'rxjs/util/TimeoutError';
import { SmartTipService } from 'core/services/smartTip/smartTip.service';

declare const $;
@Component({
  selector: 'app-compose-post',
  templateUrl: './compose-post.component.html',
  styleUrls: ['./compose-post.component.css']
})

export class ComposePostComponent implements OnInit, AfterViewInit, OnDestroy {
  resetFlag: boolean = false;

  alreadyListen: boolean = false;

  public REG_EXP_URL: string = '^(http)+[-a-zA-Z0-9@:%_\\+.~#?&//=]{2,256}\\.[a-z]{2,4}\\b(\\/[-a-zA-Z0-9@:%_\\+.~#?&//=]*)?';

  public REG_EXP_HTTP: string = '/^(http:\/\/www\.|https:\/\/www\.|http:\/\/|https:\/\/)';

  public REG_EXP_HASH_TAGS: string = '(^|\\s)(#[a-z\\d-_]+)';
  public TWITTER_URL_CHARACTER_SIZE: number = 23;

  public TWITTER_VIDEO_SIZE_LIMIT: number = 15728640; // 15MB - limit for twitter videos.

  public TWITTER_VIDEO_MAX_DURATION_LIMIT: number = 140.000;
  public TWITTER_VIDEO_MIN_DURATION_LIMIT: number = 5.000;

  public INSTAGRAM_VIDEO_MAX_DURATION_LIMIT: number = 60.000;
  public INSTAGRAM_VIDEO_MIN_DURATION_LIMIT: number = 3.000;

  public INSTAGRAM_STORY_VIDEO_MAX_DURATION_LIMIT: number = 15.000;
  public INSTAGRAM_STORY_VIDEO_MIN_DURATION_LIMIT: number = 3.000;

  public FACEBOOK_VIDEO_MAX_DURATION_LIMIT: number = 1200.000;
  public FACEBOOK_VIDEO_MIN_DURATION_LIMIT: number = 1.000;
  public INSTAGRAM_HASHTAGS_LIMIT = 30;

  public static VIDEOGIF: string = "image/gif";

  public static VIDEOMP4: string = "video/mp4";

  public static VIDEOWEBM: string = "video/webm";

  public static IMG_JPEG: string = "jpeg";

  public static IMG_PNG: string = "png";
  public static VIDEO_AVI: string = "avi";
  public static VIDEO_ASF: string = "x-ms-asf";
  public static VIDEO_DV: string = "x-dv";
  public static VIDEO_MOD: string = "mod";
  public static VIDEO_MOV: string = "quicktime";
  public static VIDEO_MPEG: string = "mpeg";
  public static VIDEO_XFLV: string = "x-flv";
  public static VIDEO_3GP: string = "3gpp";
  public static VIDEO_MKV: string = "x-matroska";

  public static MIN_RATIO = 0.8;
  public static MAX_RATIO = 1.91;

  public static MIN_STORY_RATIO = 0.56;
  public static MAX_STORY_RATIO = 0.67;

  public INSTAGRAM_CHECKPOINT_REQUIRED: string = "You need to provide an authorization on your mobile device. Instagram has sent a notification to your account, you should mark the new device as secure so that the schedule can work properly. If you have already made this authorization and the channel has not been connected, you must log in again in the menu \"my channels\"";

  public error: string;
  public successMessage: string;
  public alertMessage: string;
  public videoModalTitle: string = "EDIT VIDEO THUMBNAIL";

  public reorderAttachment: boolean = false;

  @ViewChild('textAreaTitle')
  public textAreaTitle: any;

  @ViewChild('textAreaDescription')
  public textAreaDescription: any;

  @ViewChild('mychannels')
  public mychannels: any;

  @ViewChild('postText')
  public postText: any;

  @ViewChild('formFileUpload')
  public formFileUpload: any;

  @ViewChild('dropZoneContainer')
  public dropZoneContainer: any;

  public static dropZoneContainerAux: any;

  @ViewChild('inputFileUpload')
  public inputFileUpload: any;

  @ViewChild('datePickerSchedule')
  public datePickerSchedule: any;

  @ViewChild('timerPickerSchedule')
  public timerPickerSchedule: Op_TimePickerComponent;

  @ViewChild('videoThumbnailModal')
  public videoThumbnailModal: any;
  /*
    @ViewChild('containerError')
    public containerError: any;
  
    @ViewChild('containerSuccess')
    public containerSuccess: any;
  
    @ViewChild('containerAlert')
    public containerAlert: any;
  */
  @ViewChild('emoji_itemContainer')
  public containerEmoji: any;

  @ViewChild('checkBoxStories')
  public checkBoxStories: any;

  public textDataField: string = "text";

  public static videoThumbnailModalAux: any;

  public textComposeLength: string = '0 CHARACTERS';

  public selectedProfiles: any = [];

  public upload_files: any;

  public hasInstagramSelected: boolean = false;

  public hasStoriesSelected: boolean = false;

  public hasFacebookSelected: boolean = false;

  public hasTwitterSelected: boolean = false;

  public hasLinkedInSelected: boolean = false;

  public invalidStoriesSelection: boolean = false;

  public showLinkEditor: boolean = false;

  public showSchedulePost: boolean = false;

  public showReloadPostFeature: boolean = false;

  public showAlbum: boolean = false;

  public removedLinkEditor: boolean = false;

  //public flagCheckIsLink    : boolean = false;

  public link_img: string = '';

  public post: Post = new Post();

  public postReload: Post = null;

  public static postAux: Post = new Post();

  public showCheckAllBtn: boolean = false;

  public groupType: any = 'P';

  public static refAux;

  public static modalServiceAux;

  public datePost: any = "";

  /* public hourPost : any = ""; */

  subscriptionId: Subscription;

  subscriptionUrl: Subscription;

  public listFilesUpload;

  public uploadPath: string = "";

  public configDropzone = { url: "{{controlService.contexto_aplicacao}}/api/upload/" };

  public minDate = new Date();

  public canSave: boolean = true;

  public isUploading: boolean = false;

  colorTheme = 'theme-blue';

  bsConfig: Partial<BsDatepickerConfig> = { containerClass: this.colorTheme, showWeekNumbers: false };

  public idxDragItem = -1;

  public idxDragOverItem = -1;

  public idxDropTarget: number;

  public static idxDragItemAux: number;

  public static idxDragOverItemAux: number;

  hourPost: any;

  @ViewChild('VideoThumbnailModal') VideoThumbnailModal: any;

  private searchLink = new Subject<string>();

  constructor(
    private checkUrl: CheckUrlService,
    private dataService: PostService,
    private uploadService: UploadService,
    private ref: ChangeDetectorRef,
    private route: ActivatedRoute,
    private emojiService: EmojiService,
    private smartTipService: SmartTipService,
    private dragula: DragulaService,
    public controlService: ControlService,
    private myElement: ElementRef,
    private Renderer: Renderer2,
    private profileService: ProfilesService,
    private modalService: ModalService
  ) {

    this.post = new Post();
    this.post.postsAttachments = [];
    ComposePostComponent.refAux = ref;
    ComposePostComponent.modalServiceAux = modalService;

    this.uploadPath = this.controlService.context_app;

    this.subscriptionId = this.route.params.subscribe(params => {

      let id = +params['id'];
      this.post.idPost = id;

      if ((this.post.idPost != undefined) && (!isNaN(id)) && (this.post.idPost != null)) {
        this.showReloadPostFeature = false;

        this.dataService.getPostById(this.post).subscribe(
          result => this.onLoadPostEdit(result),
          error => console.log("Erro ao recuperar post.............")
        );
      }
    });

    /*
		this.dragula.setOptions('bag-postsAttachments', {
			revertOnSpill: true,
			direction: 'horizontal',
			moves: function (el, container, handle) {
				return handle.className === 'handle';
			}
    });

    try {
      this.dragula.setOptions('bag-postsAttachments', {
        revertOnSpill : true,
        direction     : 'horizontal',
        moves         : function (el, container, handle) {
          return handle.className === 'handle';
        }
      } );
    } catch(e) { 

      console.log(e);
    }
*/
    this.profileService.resetSelectionList();
  }

  ngOnInit() {
    //this.containerError.nativeElement.hidden = true;
    if (this.dropZoneContainer && this.dropZoneContainer.nativeElement.dropzone != null && this.dropZoneContainer.nativeElement.dropzone != undefined) {
      if (!this.alreadyListen) {

        this.alreadyListen = true;
        ComposePostComponent.postAux = this.post;

        if (ComposePostComponent.postAux.postsAttachments == null || ComposePostComponent.postAux.postsAttachments == undefined) {
          ComposePostComponent.postAux.postsAttachments = [];
        }

        this.post.postsAttachments = ComposePostComponent.postAux.postsAttachments;
        ComposePostComponent.videoThumbnailModalAux = this.videoThumbnailModal;

        if (this.dropZoneContainer.nativeElement.dropzone.files == undefined) {
          this.dropZoneContainer.nativeElement.dropzone.files = [];
        }

        //this.listFilesUpload = this.dropZoneContainer.nativeElement.dropzone.files;

        //this.smartTipService.smartTipShowed = false;

        //this.smartTipService.smartTipShowed = false;
      }
    } else {

    }
    /*
        if (this.post.postsAttachments != undefined && (this.post.postsAttachments.length > 0) ) {
          this.showAlbum = true;
        } else {
          this.showAlbum = false;
        }*/

    /*
            this.dragula
                .drag
                .subscribe(value => {
                    // do something
                });
    
            this.dragula
                .drop
                .subscribe(value => {
                    this.reorderAttachments();
                });
    */







    this.searchLink.subscribe(
      data =>
        this.checkUrl.checkValidUrl(this.post.link)
          .debounceTime(300)
          .subscribe(
            result => this.onCheckIsLink(result),
            error => this.onCheckIsLink(false))
    );





    /*
    */

  }

  ngAfterViewInit(): void {
    if (this.dropZoneContainer && this.dropZoneContainer.nativeElement.dropzone != null && this.dropZoneContainer.nativeElement.dropzone != undefined) {
      if (!this.alreadyListen) {
        this.alreadyListen = true;

        if (ComposePostComponent.postAux == null) {
          ComposePostComponent.postAux = this.post;
        }

        if (ComposePostComponent.postAux.postsAttachments == null || ComposePostComponent.postAux.postsAttachments == undefined) {
          ComposePostComponent.postAux.postsAttachments = [];
        }

        this.post.postsAttachments = ComposePostComponent.postAux.postsAttachments;
        ComposePostComponent.videoThumbnailModalAux = this.videoThumbnailModal;
        /*
                if ($) {
                  $('data-toggle="tooltip"').tooltip();
                }*/
      }
    }

    this.subscriptionUrl = this.route.params.subscribe(params => {
      let urlFeed = params['url'];
      this.post.text = urlFeed;
      this.checkIsLink();

      window.history.pushState('compose', 'title', '/home/compose');
      this.controlService.urlSource.next('compose');
    });

    if (this.containerEmoji != undefined) {
      this.containerEmoji.textFieldSource = this.post.text;
      //this.containerEmoji.loadDataProvider();
    }

    ComposePostComponent.dropZoneContainerAux = this.dropZoneContainer;
  }

  public onLoadPostEdit(result: any) {
    this.post = result;
    this.timerPickerSchedule.optimerPicker.setdefault = false;
    this.selectProfileToEdit(this.post.profile);

    for (var i: number = 0; i < this.mychannels.profilesService.profiles.length; i++) {

      if (this.post.profile.idProfile == this.mychannels.profilesService.profiles[i].idProfile) {

        //this.mychannels.checkedProfiles.push(this.mychannels.profilesService.profiles[i]);
        this.profileService.addCheckedProfile(this.mychannels.profilesService.profiles[i]);
      }
    }

    for (i = 0; i < this.post.postsAttachments.length; i++) {

      this.handlerThumnailEditPost(this.post.postsAttachments[i]);
      if (i == 0) {
        (this.dropZoneContainer.nativeElement.dropzone).previewsContainer.classList.add("dz-started");
      }
    }

    //"22/11/2017 14:35:00 GMT-02:00"

    let tmp = this.post.scheduleDate.toString().split(" ");
    let date = tmp[0].toString().split("/");
    let hours = tmp[1].toString().split(":");
    let gmtScheduleHour   : any = tmp[2].toString().substring(3,6);
    gmtScheduleHour = new Number(gmtScheduleHour)
    let gmtScheduleMinute : any = tmp[2].toString().substring(7,9);

    gmtScheduleMinute = (0.50 * gmtScheduleMinute) / 30;
    gmtScheduleMinute = new Number(gmtScheduleMinute);
    this.showSchedulePost = true;
    this.datePost = new Date(date[0] + "/" + date[1] + "/" + date[2]);
    this.timerPickerSchedule.optimerPicker.changeHours(hours[0]);
    this.timerPickerSchedule.optimerPicker.changeMinutes(hours[1]);
    //this.timerPickerSchedule.optimerPicker.timezone = (gmtScheduleHour + gmtScheduleMinute);
    this.timerPickerSchedule.optimerPicker.timezone_bean = this.post.timezone;
    this.timerPickerSchedule.formatTMZString();

    this.dropZoneContainer.nativeElement.dropzone.files = this.post.postsAttachments;

    if (this.post.appendType == Post.APPEND_RESUME) {
      this.showLinkEditor = true;
    }

    if (this.post.profile.type == Profile.INSTAGRAM) {
      this.hasInstagramSelected = true;

      if (this.post.targetFeed == "S") {
        this.hasStoriesSelected = true;
      }
    } else {
      this.hasInstagramSelected = false;
      this.hasStoriesSelected = false;
    }
  }

  handlerThumnailEditPost(postAtt: PostAttachment) {
    // Create the mock file:
    //file.previewElement['postAttachment'] = postAttachment;
    var mockFile = { name: postAtt.fileName, size: postAtt.size, postAttachment: postAtt };

    // Call the default addedfile event handler
    this.dropZoneContainer.nativeElement.dropzone.emit("addedfile", mockFile);

    // And optionally show the thumbnail of the file:
    var filePath: string = this.controlService.temp_image_path + postAtt.fileName;
    let obj = this.dropZoneContainer.nativeElement.dropzone.emit("thumbnail", mockFile, filePath);
    // Or if the file on your server is not yet in the right
    // size, you can let Dropzone download and resize it
    // callback and crossOrigin are optional.
    //this.dropZoneContainer.nativeElement.dropzone.createThumbnailFromUrl(file, imageUrl, callback, crossOrigin);

    // Make sure that there is no progress bar, etc...
    this.dropZoneContainer.nativeElement.dropzone.emit("complete", mockFile);
    //this.dropZoneContainer.nativeElement.dropzone.resize();
    // If you use the maxFiles option, make sure you adjust it to the
    // correct amount:
    var existingFileCount = 1; // The number of files already uploaded
    //this.dropZoneContainer.nativeElement.dropzone.options.maxFiles = myDropzone.options.maxFiles - existingFileCount;
  }

  checkIsLink(): any {

    if (this.removedLinkEditor && (this.post.link == null) && !this.showLinkEditor) {
      this.removedLinkEditor = false;
    }

    let length = this.post.text ? this.post.text.length : 0;

    if (this.hasTwitterSelected) {
      this.textComposeLength = length + ' / 280 CHARACTERS';

      if (length > 280) {

        this.Renderer.setStyle(
          this.postText.nativeElement, 'color', 'red');
      } else {

        this.Renderer.setStyle(
          this.postText.nativeElement, 'color', 'black');
      }

    } else {
      this.textComposeLength = length + ' CHARACTERS';
    }
    this.resizeTextAreaCompose();


    if (!this.removedLinkEditor) {
      let matchFound: boolean = false;
      let s: string[] = this.post.text ? this.post.text.split(' ') : [];
      let listURL: string[] = [];
      for (var i = 0; i < s.length; i++) {

        var matcher = s[i].match(this.REG_EXP_URL);
        matchFound = (matcher != null);

        if (matchFound) {
          listURL.push(s[i]);
        }
      }

      if ((listURL.length > 0)) {
        if (this.post.link != listURL[0]) {

          //this.flagCheckIsLink = true;
          this.showLinkEditor = true;
          this.post.link = listURL[0];
          this.searchLink.next(this.post.link);





          /*
          this.checkUrl.checkValidUrl(this.post.link)
          .debounceTime(300)
          .subscribe(
            result => this.onCheckIsLink(result),
            error => this.onCheckIsLink(false));*/
        }
      } else {

        if (this.removedLinkEditor || !this.post.link || ((this.post.link == null) || (this.post.link == ""))) {

          this.post.link = null;
          this.post.picture = null;
          this.post.title = '';
          this.post.description = '';
          this.showLinkEditor = false;
        }
      }
    }
  }

  getUrlToShort(): any {

    if (this.hasStoriesSelected) {
      return;
    }

    let matchFound: boolean = false;
    let s: string[] = this.post.text ? this.post.text.split(' ') : [];
    let listURL: string[] = [];
    for (var i = 0; i < s.length; i++) {

      var matcher = s[i].match(this.REG_EXP_URL);
      matchFound = (matcher != null);

      if (matchFound) {
        listURL.push(s[i]);
      }
    }

    if (listURL.length > 0) {

      let listToShort: BitlyShortenUrlDTO[] = [];

      for (var m = 0; m < listURL.length; m++) {

        var urlToShort = new BitlyShortenUrlDTO();
        urlToShort.longUrl = listURL[m];
        urlToShort.isShortened = false;
        listToShort.push(urlToShort);
      }

      this.checkUrl.shortnerUrlService(listToShort)
        .debounceTime(300)
        .subscribe(
          result => this.onGetShortnerUrl((result as BitlyShortenUrlDTO[])),
          error => console.log(error));

    } else {

      /* this.post.picture = null;
      this.post.title = '';
      this.post.description = '';
      this.showLinkEditor = false; */
    }
  }

  onCheckIsLink(result: any) {

    //this.flagCheckIsLink = false;
    if (!result) {
      console.log('Erro checar URL::::');
      this.showLinkEditor = false;
    } else {

      if (
        ((result != null) && (result != undefined)) &&

        (result.images != null) && (result.images.length)) {
        this.post.picture = result.images['0'].image;
        this.post.title = result.title.value;
        this.post.description = result.description.value;

        if (this.post.picture != '' && this.post.picture != null) {
          this.showLinkEditor = true;
          this.getProtocolUrl();
        } else {
          this.showLinkEditor = false;
        }
      } else {

        // this.post.picture = null;
        // this.post.title = '';
        // this.post.description = '';
        // this.showLinkEditor = false;
      }
    }
    this.resizeTextAreaTitleLink();
  }

  ativarDatePicker() {
    //this.showSchedulePost = true;
    this.datePickerSchedule.show();
  }

  resizeTextAreaTitleLink() {
    if (this.textAreaTitle) {
      this.textAreaTitle.nativeElement.rows = 0;
      this.textAreaTitle.nativeElement.rows = ((this.textAreaTitle.nativeElement.scrollHeight) / 22);
    }
  }

  resizeTextAreaDescription() {
    this.textAreaDescription.nativeElement.rows = 0;
    this.textAreaDescription.nativeElement.rows = ((this.textAreaDescription.nativeElement.scrollHeight) / 16);
  }

  resizeTextAreaCompose() {
    this.postText.nativeElement.rows = 0;
    var rowUpdate: any = ((this.postText.nativeElement.scrollHeight) / 16);
    if (rowUpdate > 5) {
      this.postText.nativeElement.rows = rowUpdate;
    } else if (rowUpdate <= 5) {
      this.postText.nativeElement.rows = 5;
    }
  }

  removeLinkPost() {

    this.post.picture = null;
    this.post.link = null;
    this.post.title = '';
    this.post.description = '';
    this.showLinkEditor = false;
    this.removedLinkEditor = true;
  }

  validateFormPost() {

    let canSave: boolean = true;

    if (this.showSchedulePost || this.datePost || this.timerPickerSchedule.optimerPicker.hours) {

      if ((!this.datePost) || (!this.timerPickerSchedule.optimerPicker.hours) || (!this.timerPickerSchedule.optimerPicker.minutes)) {
        this.error = 'Ops! You must select day and hour to schedule.';
        canSave = false;
      }

      const tmpDate: Date = new Date(this.datePost);
      let valHour: number = +this.timerPickerSchedule.optimerPicker.hours;

      if (valHour == 12) {
        valHour = 0;
      }

      let hours: string = (this.timerPickerSchedule.optimerPicker.meridian == "PM") ? (valHour + 12) + "" : (valHour + "");
      this.post.scheduleDate = (tmpDate.getUTCMonth() + 1) + "/" + tmpDate.getDate() + "/" + tmpDate.getFullYear() + " " + hours + ":" + this.timerPickerSchedule.optimerPicker.minutes + ":00 GMT" + this.timerPickerSchedule.formatTMZString();

      let timeDiff = (-1 * +this.timerPickerSchedule.formatTMZString());
      if (timeDiff > 0) {
        timeDiff = -1 * timeDiff;
      }

      const tmpDateAux: Date = new Date(tmpDate.getFullYear(), tmpDate.getUTCMonth(), tmpDate.getDate(), (+hours + timeDiff), this.timerPickerSchedule.optimerPicker.minutes, 0, 0);

      if ((tmpDateAux.getTime() - new Date().getTime()) < 0) {
        this.error = "Ops! You must select a valid date at timezone to schedule.";
        this.canSave = false;
      }

      this.post.status = Post.SCHEDULED;

      if ((this.post.countPostingAttempts == undefined) || (this.post.countPostingAttempts == null)) {
        this.post.countPostingAttempts = 0;
      }
    } else {
      this.post.scheduleDate = null;
      this.post.status = null;
    }

    if (canSave && this.validadeBeforePost()) {

      if (this.post.targetFeed == undefined || this.post.targetFeed == null || this.post.targetFeed == "") {
        this.post.targetFeed = "T";
      }

      if (this.showLinkEditor) {
        this.post.appendType = Post.APPEND_RESUME;
        this.getProtocolUrl();
      }

      this.postReload = this.post;
      this.canSave = false;
      //VERIFICAR STATUS UPLOAD
      if (this.hasStoriesSelected) {
        this.post.targetFeed = "S";
        this.post.appendType = Post.APPEND_PICTURE;
      }

      this.dataService.persistPost(this.post, this.mychannels.checkedProfiles, this.timerPickerSchedule.optimerPicker.timezone_bean)
        .timeout(300000)
        .subscribe(
          result => this.onPersistPost(result),
          error => this.showErrorPersist(error));
    } else {
      this.showErrorPersist(this.error);
    }
  }

  checkSocialMedia(event) {

    this.mychannels.checkedProfiles = event;

    this.hasTwitterSelected = false;
    this.hasInstagramSelected = false;
    this.hasFacebookSelected = false;
    this.hasLinkedInSelected = false;

    for (var i: number = 0; i < this.mychannels.checkedProfiles.length; i++) {
      if ((this.mychannels.checkedProfiles[i] as Profile).type == 'I') {
        this.hasInstagramSelected = true;
      } else if ((this.mychannels.checkedProfiles[i] as Profile).type == 'T') {
        this.hasTwitterSelected = true;
      } else if ((this.mychannels.checkedProfiles[i] as Profile).type == 'F') {
        this.hasFacebookSelected = true;
      } else if ((this.mychannels.checkedProfiles[i] as Profile).type == 'P') {
        this.hasFacebookSelected = true;
      } else if ((this.mychannels.checkedProfiles[i] as Profile).type == 'L') {
        this.hasLinkedInSelected = true;
      }
    }

    if (this.hasInstagramSelected) {
      if (this.hasTwitterSelected || this.hasLinkedInSelected || this.hasFacebookSelected) {
        this.invalidStoriesSelection = true;
        this.hasStoriesSelected = false;
      } else {
        this.invalidStoriesSelection = false;
      }
    } else {
      this.invalidStoriesSelection = false;
    }

    this.smartTipService.getSmartScheduleInfo(this.mychannels.checkedProfiles);
  }

  public selectProfileToEdit(profile: Profile) {

  }

  onPersistPost(e: any) {

    if (e && e.false) {
      this.showErrorPersist(e.false);
    } else {

      if (e && e.true) {

        this.successMessage = e.true;
        this.showSuccessMessage();
      }

      this.resetComposeComponent();

      this.showLinkEditor = false;
      this.showAlbum = false;
      this.showSchedulePost = false;
      this.removedLinkEditor = false;

      if (this.datePickerSchedule && this.timerPickerSchedule) {
        this.datePickerSchedule.bsValue = null;
        //this.timerPickerSchedule.nativeElement.value  = "";
      }

      if (this.postText != undefined) {
        this.postText.nativeElement.focus();
      }
      this.resetFlag = false;
    }

    this.storiesHandler();
    this.startCountdown();
    this.canSave = true;
  }

  setFocusDate() {
    if (this.showSchedulePost) {
      setTimeout(() => {
        if (this.datePickerSchedule) {
          //this.datePickerSchedule.nativeElement.focus();
        }
      }
      );
      /*
      if (this.timerPickerSchedule) {
        this.timerPickerSchedule.nativeElement.timepikerElement.gmt = this.getDefaultTimeZone();
      }*/
    } else {

      this.datePickerSchedule.bsValue = null;
      this.timerPickerSchedule.resetValue();
    }
  }

  public addPostAttachment(file: any, response: any) {
    if ((file.type == 'image/png') || (file.type == 'image/jpg') || (file.type == 'image/jpeg')) {
      ComposePostComponent.postAux.appendType = Post.APPEND_PICTURE;
    } else {
      // file.previewElement.addEventListener('click', ComposePostComponent.postAttachmentClickHandler);
      ComposePostComponent.postAux.appendType = Post.APPEND_VIDEO;
    }

    var postAttachment: PostAttachment = new PostAttachment();
    postAttachment.fileName = response.file_name;
    postAttachment.size = file.size;
    postAttachment.extension = file.type;
    postAttachment.fileType = ComposePostComponent.getFileType(file.type);

    if ((ComposePostComponent.postAux.postsAttachments != undefined) &&
      (ComposePostComponent.postAux.postsAttachments.length > 0)) {
      ComposePostComponent.postAux.appendType = Post.APPEND_ALBUM;
    }

    file.previewElement['postAttachment'] = postAttachment;
    ComposePostComponent.postAux.postsAttachments.push(postAttachment);
    ComposePostComponent.refAux.detectChanges();

    /*
        file.previewElement.addEventListener('dragstart', ComposePostComponent.handleDragStart, false);
        file.previewElement.addEventListener('dragenter', ComposePostComponent.handleDragEnter, false);
        file.previewElement.addEventListener('dragover', ComposePostComponent.handleDragOver, false);
        file.previewElement.addEventListener('dragleave', ComposePostComponent.handleDragLeave, false);
    */

  }

  public static postAttachmentClickHandler(mouseEvent: any) {
    // ComposePostComponent.videoThumbnailModalAux.setDynamicModalContent('VideoThumbnailComponent');
    VideoThumbnailComponent.setPost(ComposePostComponent.postAux);
    ComposePostComponent.modalServiceAux.showVideoThumbnailModal();
    VideoThumbnailComponent.setAttachment(mouseEvent.currentTarget.postAttachment);
  }

  public validateAmountOfChars(checkedProfiles, picture) {

    if ((this.post != null) && (this.post != undefined)) {

      if (this.hasInstagramSelected && this.post.hashtags) {
        let arrHashs: any[] = this.post.hashtags.split(" ");
        if (arrHashs && arrHashs.length >= 30) {
          this.error = 'Ops! You exceeded the limit of hashtags per post. Instagram allows 30 hashtags.';
          return false;
        }
      }

      if (this.hasFacebookSelected && this.post.text.length > 2000) {
        this.error = 'Ops! Too much characters. You exceeded the limit.';
        // this.error = 'O número de caracteres excedeu o máximo permitido.';
        return false;
      } else if (this.hasInstagramSelected && this.post.text.length > 2000) {
        this.error = 'Ops! Too much characters. You exceeded the limit.';
        // this.error = 'O número de caracteres excedeu o máximo permitido.';
        return false;
      } else if (this.hasTwitterSelected && this.getTwitterMessageCount() > 280) {
        this.error = 'Ops! Too much characters. You exceeded the limit.';
        // this.error = 'O número de caracteres excedeu o máximo permitido.';
        return false;
      } else if (this.hasLinkedInSelected && this.getTwitterMessageCount() > 1300) {
        this.error = 'Ops! Too much characters. You exceeded the limit.';
        // this.error = 'O número de caracteres excedeu o máximo permitido.';
        return false;
      }
      return true;
    }

    // this.error = 'Objeto nulo, vazio. Informe suport@woop.com';
    this.error = 'Ops! No data was found. Please contact support@woop.com';
    return false;
  }

  public validateImageContent() {

    for (var i: number = 0; i < this.post.postsAttachments.length; i++) {

      var itAttachment: PostAttachment = this.post.postsAttachments[i];
      itAttachment.index = i;

      if (itAttachment.fileType != null && itAttachment.fileType.indexOf(Post.APPEND_PICTURE) > -1) {

        if (this.hasInstagramSelected) {

          var aspectRatio: Number = itAttachment.width / itAttachment.height;
          var aspectRatioName: string = "";

          if (aspectRatio.toFixed(2) == "1.33" || aspectRatio.toFixed(2) == "0.75") {

            aspectRatioName = "4:3";
          } else if (aspectRatio.toFixed(2) == "2.33" || aspectRatio.toFixed(2) == "0.42") {

            aspectRatioName = "7:3";
          } else if (aspectRatio.toFixed(2) == "1.66" || aspectRatio.toFixed(2) == "0.6") {

            aspectRatioName = "5:3";
          } else if (aspectRatio.toFixed(2) == "1.85" || aspectRatio.toFixed(2) == "0.53") {

            aspectRatioName = "13:7";
          } else if (aspectRatio.toFixed(2) == "1.77" || aspectRatio.toFixed(2) == "0.56") {

            aspectRatioName = "16:9";
          }

          if (this.hasStoriesSelected) {

            if (aspectRatio < ComposePostComponent.MIN_STORY_RATIO || aspectRatio > ComposePostComponent.MAX_STORY_RATIO) {
              this.error = "Ops! In Stories, Instagram only allows images of aspect ratio 16:9. Please upload images 1080x1920px";
              return false;
            }

          } else {

            if (aspectRatio < ComposePostComponent.MIN_RATIO || aspectRatio > ComposePostComponent.MAX_RATIO) {
              this.error = "Ops! Instagram do not allows images of aspect ratio " + aspectRatioName + ". Please upload images on a different size.";
              return false;
            }
          }
        }
      }
    }

    return true;
  }

  public validateVideoContent() {

    for (var i: number = 0; i < this.post.postsAttachments.length; i++) {

      var itAttachment: PostAttachment = this.post.postsAttachments[i];

      if (itAttachment.videoThumbnail && itAttachment.videoThumbnail.indexOf(this.controlService.thumbnail_image_path) > -1) {
        itAttachment.videoThumbnail = itAttachment.videoThumbnail.replace(this.controlService.thumbnail_image_path, "");
      }

      if (itAttachment.extension == ComposePostComponent.VIDEOGIF) {
        this.error = 'Ops! Not support .GIF files.';
        // this.error = 'O formato de seu vídeo deve ser MP4, o único suportado pelo Twitter.';
        return false;
      }

      if (itAttachment.fileType != null && itAttachment.fileType.indexOf(Post.APPEND_VIDEO) > -1) {

        if (this.hasTwitterSelected) {
          if (itAttachment.size > this.TWITTER_VIDEO_SIZE_LIMIT) {
            this.error = 'Ops! On Twitter, your video can’t be bigger than 15MB.';
            // this.error = 'O seu vídeo excedeu o tamanho permitido pelo Twitter, que é de 15MB.';
            return false;
          } else if (itAttachment.extension != ComposePostComponent.VIDEOMP4) {
            this.error = 'Ops! Twitter only supports videos on .MP4 or .MOV.';
            // this.error = 'O formato de seu vídeo deve ser MP4, o único suportado pelo Twitter.';
            return false;
          } else if (itAttachment.duration > this.TWITTER_VIDEO_MAX_DURATION_LIMIT) {
            this.error = 'Ops! On Twitter, your video can’t last longer than 140 seconds. ';
            // this.error = 'A duração de seu vídeo deve ser de no máximo 15 segundos.';
            return false;
          } else if (itAttachment.duration < this.TWITTER_VIDEO_MIN_DURATION_LIMIT) {
            this.error = 'Ops! On Twitter, your video must last longer than 5 seconds. ';
            // this.error = 'A duração de seu vídeo deve ser de no mínimo0 0.5 segundos.';
            return false;
          } else if (itAttachment.extension != ComposePostComponent.VIDEOMP4) {

          }

          if (this.post.appendType != null && this.post.appendType == Post.APPEND_PICTURE) {
            if (itAttachment.fileName.length > 256) {
              this.error = this.error + '\nRemember your image also counts as characters on Twitter! You have 256 characters left :)';
              // this.error = this.error + '\nPara postar no twitter com imagem lembre-se que o link também ocupa espaço! Escreva sua mensagem com 110 caracteres :)';
              return false;
            }
          }
        }

        if (this.hasInstagramSelected) {

          var aspectRatio: Number = itAttachment.width / itAttachment.height;

          if (this.post.targetFeed == Post.STORY_FEED) {

            if (aspectRatio < ComposePostComponent.MIN_STORY_RATIO || aspectRatio > ComposePostComponent.MAX_STORY_RATIO) {
              this.error = 'Ops! In Stories, Instagram only allows videos measuring between ' + ComposePostComponent.MIN_STORY_RATIO + ' and ' + ComposePostComponent.MAX_STORY_RATIO +
                '. Your video is ' + aspectRatio.toFixed(2) + ". Please upload your video on a different size.";
              // this.error = 'O Instagram permite vídeos em histórias com dimensões entre ' + ComposePostComponent.MIN_STORY_RATIO + ' e ' + ComposePostComponent.MAX_STORY_RATIO + 
              //     '. Seu vídeo tem dimensão de ' + aspectRatio.toFixed(2);
              return false;
            } else if (itAttachment.duration > this.INSTAGRAM_STORY_VIDEO_MAX_DURATION_LIMIT) {
              this.error = 'Ops! On Instagram Stories, your video can’t last longer than 15 seconds.';
              // this.error = 'A duração de seu vídeo em uma história deve ser de no máximo 15 segundos.';
              return false;
            } else if (itAttachment.duration < this.INSTAGRAM_STORY_VIDEO_MIN_DURATION_LIMIT) {
              this.error = 'Ops! On Instagram Stories, your video must last longer than 3 seconds.';
              // this.error = 'A duração de seu vídeo em uma história deve ser de no mínimo 3 segundos.';
              return false;
            }

          } else {

            if (aspectRatio < ComposePostComponent.MIN_RATIO || aspectRatio > ComposePostComponent.MAX_RATIO) {
              this.error = 'Ops! Instagram only allows videos measuring between ' + ComposePostComponent.MIN_RATIO + ' and ' + ComposePostComponent.MAX_RATIO +
                '. Your video is ' + aspectRatio.toFixed(2) + ". Please upload your video on a different size.";
              // this.error = 'O Instagram permite vídeos com dimensões entre ' + ComposePostComponent.MIN_RATIO + ' e ' + ComposePostComponent.MAX_RATIO + 
              //     '. Seu vídeo tem dimensão de ' + aspectRatio.toFixed(2);
              return false;
            } else if (itAttachment.duration > this.INSTAGRAM_VIDEO_MAX_DURATION_LIMIT) {
              this.error = 'Ops! On Instagram, your video can’t last longer than 60 seconds.';
              // this.error = 'A duração de seu vídeo deve ser de no máximo 60 segundos.';
              return false;
            } else if (itAttachment.duration < this.INSTAGRAM_VIDEO_MIN_DURATION_LIMIT) {
              this.error = 'Ops! On Instagram, your video must last longer than 3 seconds.';
              // this.error = 'A duração de seu vídeo deve ser de no mínimo 3 segundos.';
              return false;
            }
          }

          if (itAttachment.extension == 'VIDEO_MP4') {
            this.error = 'Ops! Instagram only supports videos on .MP4 or .MOV.';
            // this.error = 'O formato de seu vídeo deve ser MP4, o único suportado pelo Instagram.';
            return false;
          }
        }

        if (this.hasFacebookSelected) {
          if (itAttachment.duration > this.FACEBOOK_VIDEO_MAX_DURATION_LIMIT) {
            this.error = 'Ops! On Facebook, your video can’t last longer than 20 minutes.';
            // this.error = 'A duração de seu vídeo deve ser de no máximo 20 minutos.';
            return false;
          } else if (itAttachment.duration < this.FACEBOOK_VIDEO_MIN_DURATION_LIMIT) {
            this.error = 'Ops! On Facebook, your video must last longer than 1 second.';
            // this.error = 'A duração de seu vídeo deve ser de no mínimo 1 segundo.';
            return false;
          }

          this.alertMessage = "Your video could take a few minutos to upload on Facebook.";
          this.showWarningMessage();
        }
      }
    }
    return true;
  }

  public validateHashTagsLimit() {

    if (this.post.hashtags != null) {

      var hashTagsCount: number = 0;
      var matchFound;

      for (let str of this.post.hashtags.split(' ')) {

        str = str.trim();

        var matcher = str.match(this.REG_EXP_HASH_TAGS);
        matchFound = (matcher != null);

        if (matchFound) {
          hashTagsCount++;
        }
      }

      if (hashTagsCount > this.INSTAGRAM_HASHTAGS_LIMIT) {
        this.error = 'Ops! You exceeded the limit of hashtags per post. Instagram allows 30 hashtags.';
        // this.error = 'O número de hashtags na postagem excedeu o limite máximo de 30.';
        return false;
      } else {
        return true;
      }
    } else {
      return true;
    }
  }

  public getTwitterMessageCount() {

    var message: string = this.post.text;
    var countOfValidURLs: number = 0;
    var excludedURLs: any = [];

    var matchFound: boolean = false;
    for (let str of this.post.text.split(' ')) {

      str = str.trim();

      var matcher = str.match(this.REG_EXP_URL);
      matchFound = (matcher != null);

      if (matchFound) {

        excludedURLs.push(str);
        countOfValidURLs++;
      }
    }

    for (const strAux of excludedURLs) {
      message = message.replace(strAux, '');
    }

    return message.length + (this.TWITTER_URL_CHARACTER_SIZE * countOfValidURLs);

  }

  public validadeBeforePost() {
    this.error = '';
    if ((this.mychannels.checkedProfiles != null) && (this.mychannels.checkedProfiles != undefined) && (this.mychannels.checkedProfiles.length > 0)) {

      var canContinue: boolean = true;

      if (this.post && this.post.text) {
        canContinue = this.validateAmountOfChars(this.mychannels.checkedProfiles, this.post.picture);
      } else if (this.post && (this.post.text == undefined)) {
        if (
          (this.post.postsAttachments == undefined)
          ||
          (this.post.postsAttachments && this.post.postsAttachments.length == 0)) {

          canContinue = false;
          this.error = this.error + "\nInsert text and/or image to make a post.";
        }
      }
      // Validate video/image metadata.
      if (this.post.postsAttachments != undefined) {
        if (!this.validateVideoContent() || !this.validateImageContent()) {
          canContinue = false;
        }

      }
      // Validate the number of hashtags in an instagram post.
      if (this.hasInstagramSelected && !this.validateHashTagsLimit()) {
        canContinue = false;
      }

      // Validate if a picture/video was uploaded when a post to Instagram will be save/sheduling.
      if (this.hasInstagramSelected
        && (this.post.appendType == null
          || (!(this.post.appendType == Post.APPEND_PICTURE)) && (!(this.post.appendType == Post.APPEND_ALBUM)) && (!(this.post.appendType == Post.APPEND_VIDEO)))) {
        this.error = this.error + '\nYou need to upload an image or video to post on Instagram.';
        // this.error = this.error + '\nVocê precisa anexar uma imagem ou vídeo para postar no Instagram.';
        canContinue = false;
      }

      if (this.hasLinkedInSelected) {
        if (this.post.text && this.post.text.length > 256) {
          this.error = this.error + '\nYour text can’t exceed the 256 character limit on Linkedin.';
          // this.error = this.error + '\nO texto deve conter menos de 256 caracteres para postar no LinkedIn.';
          canContinue = false;
        }

        if (this.post.postsAttachments != undefined && this.post.postsAttachments.length > 1) {
          this.error = this.error + '\nOps! You can only attach one image or video on LinkedIn posts';
          // this.error = this.error + '\nVocê pode anexar APENAS uma(1) imagem ou vídeo para postar no LinkedIn.';
          canContinue = false;
        }
      }
      /*
          LINKEDIN restrições
            Text < 256
          - Img > 80 x 150px pra boa qualidade, mas não tem tamanho mínimo
          - Vídeo tenho que ver ainda, vou fazer sexta kkk
          - Limite de 1 img ou vídeo
      */

      if ((this.post.appendType == Post.APPEND_ALBUM) && ((this.post.title == undefined || this.post.title == null || this.post.title == ""))) {
        this.post.title = null;
      }

      if (this.hasInstagramSelected && ((this.post.targetFeed == undefined) || (this.post.targetFeed == null) || (this.post.targetFeed == ""))) {

        if (this.hasStoriesSelected) {
          this.post.targetFeed = Post.STORY_FEED;
        } else {
          this.post.targetFeed = Post.TIMELINE_FEED;
        }
      }
    } else {
      // this.error = this.error + 'Nenhum conteúdo definido.';
      this.error = this.error + 'Please select a social channel to post.';
      canContinue = false;
    }

    return canContinue;
  }

  callInstagramBusiness(profile: Profile) {
    this.dataService.callInstagramBusiness(profile)
      .subscribe(
        result => this.onGetInstagramBusiness(result),
        error => console.log(error));
  }

  public onGetInstagramBusiness(result: any) {
    console.log('onGetInstagramBusiness');
  }

  public static getFileType(videoExtension: string): any {

    let type: string = PostAttachment.PHOTO;

    if (videoExtension.indexOf(ComposePostComponent.VIDEO_AVI) > -1) {
      type = PostAttachment.VIDEO;
    } else if (videoExtension.indexOf(ComposePostComponent.VIDEO_ASF) > -1) {
      type = PostAttachment.VIDEO;
    } else if (videoExtension.indexOf(ComposePostComponent.VIDEO_DV) > -1) {
      type = PostAttachment.VIDEO;
    } else if (videoExtension.indexOf(ComposePostComponent.VIDEO_MOD) > -1) {
      type = PostAttachment.VIDEO;
    } else if (videoExtension.indexOf(ComposePostComponent.VIDEO_MOV) > -1) {
      type = PostAttachment.VIDEO;
    } else if (videoExtension.indexOf(ComposePostComponent.VIDEOMP4) > -1) {
      type = PostAttachment.VIDEO;
    } else if (videoExtension.indexOf(ComposePostComponent.VIDEO_MPEG) > -1) {
      type = PostAttachment.VIDEO;
    } else if (videoExtension.indexOf(ComposePostComponent.VIDEO_XFLV) > -1) {
      type = PostAttachment.VIDEO;
    } else if (videoExtension.indexOf(ComposePostComponent.VIDEO_3GP) > -1) {
      type = PostAttachment.VIDEO;
    } else if (videoExtension.indexOf(ComposePostComponent.VIDEO_MKV) > -1) {
      type = PostAttachment.VIDEO;
    } else if (videoExtension.indexOf(ComposePostComponent.VIDEOGIF) > -1) {
      type = ComposePostComponent.VIDEOGIF;
    }
    return type;
  }

  public showEmoji(emojiTrigger: HTMLElement) {

    if (this.hasStoriesSelected) {
      return;
    }

    if (this.emojiService.emojiShowed) {
      this.emojiService.closeEmoji();
    } else {
      const position = emojiTrigger.getBoundingClientRect();
      this.emojiService.showEmoji(this.post, this.textDataField, position);
    };
  }

  public showSmartTip(smartTipTrigger: HTMLElement) {

    if (this.hasStoriesSelected) {
      return;
    }

    if (this.smartTipService.smartTipShowed) {
      this.smartTipService.closeSmartTip();
    } else {
      const position = smartTipTrigger.getBoundingClientRect();
      this.smartTipService.showSmartTip(this.timerPickerSchedule, this.textDataField, position);
    };

    console.log("showSmartTip");
  }

  ngOnDestroy() {
    if (this.subscriptionId) {
      this.subscriptionId.unsubscribe();
    }

    if (this.subscriptionUrl) {
      this.subscriptionUrl.unsubscribe();
    }
  }

  public drag_postsAttachments(event) {
    console.log("drag_postsAttachments");
  }

  public setAttachmentsIndex() {
    this.post.postsAttachments
  }

  onUploadError(evento) {

    if (evento[2]) {
      this.error = JSON.parse(evento[2].response).message;
    } else {
      this.error = "File format not suported";
    }
    //this.containerError.nativeElement.hidden = false;
    this.startCountdown();
  }

  onUploadSuccess(evento) {

    this.canSave = true;
    var file: any = evento[0];
    var response: any = evento[1];

    var postAttachment: PostAttachment = new PostAttachment();

    if ((file.type == 'image/png') || (file.type == 'image/jpg') || (file.type == 'image/jpeg')) {
      this.post.appendType = Post.APPEND_PICTURE;
    } else {
      // file.previewElement.addEventListener('click', ComposePostComponent.postAttachmentClickHandler);
      this.post.appendType = Post.APPEND_VIDEO;

      postAttachment.duration = response.duration;
    }

    postAttachment.fileName = response.file_name;
    postAttachment.size = file.size;
    postAttachment.extension = file.type;
    postAttachment.fileType = ComposePostComponent.getFileType(file.type);
    postAttachment.width = response.width;
    postAttachment.height = response.height;

    if ((this.post.postsAttachments != undefined) &&
      (this.post.postsAttachments.length > 0)) {
      this.post.appendType = Post.APPEND_ALBUM;
    }

    file.previewElement['postAttachment'] = postAttachment;

    if (this.post.postsAttachments == undefined) {
      this.post.postsAttachments = [];
    }

    this.post.postsAttachments.push(postAttachment);

    if (this.post.postsAttachments != undefined && (this.post.postsAttachments.length > 1)) {
      this.showAlbum = true;
    } else {
      this.showAlbum = false;
    }

    ComposePostComponent.refAux.detectChanges();

    file.previewElement.addEventListener('dragstart', this.dragEnterEvent_Handler, false);
    file.previewElement.addEventListener('dragover', this.handleDragOver, false);
    file.previewElement.addEventListener('dragend', this.dragEndEvent_Handler, false);

    /*
        file.previewElement.addEventListener('dragstart', ComposePostComponent.handleDragStart, false);
        file.previewElement.addEventListener('dragenter', ComposePostComponent.handleDragEnter, false);
        file.previewElement.addEventListener('dragover', ComposePostComponent.handleDragOver, false);
        file.previewElement.addEventListener('dragleave', ComposePostComponent.handleDragLeave, false);
    */
  }

  storiesHandler() {

    if (this.hasStoriesSelected) {
      this.post.targetFeed = "S";
    } else {
      this.post.targetFeed = "T";
    }
  }

  resetComposeComponent() {

    ComposePostComponent.postAux = null;
    this.post.postsAttachments = null;
    this.post = null;
    this.post = new Post();

    this.post.postsAttachments = [];
    if (this.dropZoneContainer) {
      this.resetFlag = true;
      (this.dropZoneContainer.nativeElement.dropzone).removeAllFiles();
    }

    this.datePost = null;
    this.timerPickerSchedule.resetValue();
    this.showSchedulePost = false;
    /*    
    this.datePickerSchedule.reset();
    */
  }

  triggerDropzone() {
    console.log(this.post);
    this.dropZoneContainer.nativeElement.click();
  }

  onGetShortnerUrl(result: BitlyShortenUrlDTO[]) {

    for (var m = 0; m < result.length; m++) {

      const itemShortned: BitlyShortenUrlDTO = result[m];

      if (itemShortned.url) {
        this.post.text = this.post.text.replace(itemShortned.longUrl, itemShortned.url);
      }
    }
  }

  startCountdown() {
    setTimeout(
      () => {
        this.hideMessages();
      },
      8000
    );
  }

  getLastPost() {
    this.post = this.postReload;
  }

  getProtocolUrl() {

    let matchFound: boolean = false;
    var matcher = this.post.link.match(this.REG_EXP_HTTP);
    matchFound = (matcher != null);

    if (!matchFound) {

      if (this.post.link.indexOf("http") == -1) {
        this.post.link = "https://" + this.post.link;
      }
    }
  }

  hideMessages() {
    /*
          this.containerError.nativeElement.hidden    = true;
          this.containerSuccess.nativeElement.hidden  = true;
          this.containerAlert.nativeElement.hidden    = true;*/
    this.successMessage = "";
    this.error = "";
    this.alertMessage = "";
  }

  dragEnterEvent_Handler(event) {

    //this.reorderAttachment = true;
    //this.idxDragItem = -1;
    let dragSrcEl = event.target;
    let innerHTML = event.target.id;
    let innerHTML_img = "img_" + event.target.id;
    let l = ComposePostComponent.dropZoneContainerAux.nativeElement.dropzone.previewsContainer.children.length;

    for (var i = 0; i < l; i++) {

      if (ComposePostComponent.dropZoneContainerAux.nativeElement.dropzone.previewsContainer.children[i] == dragSrcEl) {
        ComposePostComponent.idxDragItemAux = i;
        break;
      }
    }

    event.dataTransfer.effectAllowed = 'move';
    event.dataTransfer.setData('woopIcon', innerHTML);

    //var dragIcon = document.createElement('img');
    //dragIcon.innerHTML = dragSrcEl.fileName;
    //dragIcon.src = ControlService.temp_image_path_aux + dragSrcEl.postAttachment.fileName;
    //dragIcon.src = "/assets/img/logo.png";
    //dragIcon.width = 100;
    event.dataTransfer.setDragImage(document.getElementById(innerHTML_img), 0, 0);
  }

  dropEvent_Handler(event) {

    if (this.reorderAttachment) {
      this.reorderAttachment = false;

      event.preventDefault();
      var data = event.dataTransfer.getData("woopIcon");
      this.dropZoneContainer.nativeElement.dropzone.previewsContainer.appendChild(document.getElementById(data));
      event.dataTransfer.clearData("woopIcon");
    }
  }

  public static handleDragStart(e) {
    e.currentTarget.opacity = '0.4';  // this / e.target is the source node.
  }

  public handleDragOver(e) {

    if (ComposePostComponent.dropZoneContainerAux) {
      let l = ComposePostComponent.dropZoneContainerAux.nativeElement.dropzone.previewsContainer.children.length;

      for (var i = 0; i < l; i++) {

        if (ComposePostComponent.dropZoneContainerAux.nativeElement.dropzone.previewsContainer.children[i] == e.currentTarget) {
          ComposePostComponent.idxDragOverItemAux = i;
          break;
        }
      }

      if (e.preventDefault) {
        e.preventDefault(); // Necessary. Allows us to drop.
      }

      e.dataTransfer.dropEffect = 'move';  // See the section on the DataTransfer object.
    }
  }

  public dragEndEvent_Handler(e) {

    e.preventDefault();
    var data = e.dataTransfer.getData("woopIcon");

    var arr = Array.from(ComposePostComponent.dropZoneContainerAux.nativeElement.dropzone.previewsContainer.children);

    if (data && data != null && data != "") {
      ComposePostComponent.dropZoneContainerAux.nativeElement.dropzone.previewsContainer.appendChild(document.getElementById(data));
    } else {

      //this.dropZoneContainer.nativeElement.dropzone.previewsContainer.appendChild(e.currentTarget);
    }

    e.dataTransfer.clearData("woopIcon");
    ComposePostComponent.swapArrayElements(
      //ComposePostComponent.dropZoneContainerAux.nativeElement.dropzone.previewsContainer.children, 
      //ComposePostComponent.postAux.postsAttachments,
      arr,
      (ComposePostComponent.idxDragItemAux),
      (ComposePostComponent.idxDragOverItemAux));

    ComposePostComponent.swapArrayElements(
      //ComposePostComponent.dropZoneContainerAux.nativeElement.dropzone.previewsContainer.children, 
      ComposePostComponent.postAux.postsAttachments,
      (ComposePostComponent.idxDragItemAux) - 1,
      (ComposePostComponent.idxDragOverItemAux) - 1);

    if (ComposePostComponent.dropZoneContainerAux) {

      //ComposePostComponent.dropZoneContainerAux.nativeElement.dropzone.removeAllFiles();      
      ComposePostComponent.dropZoneContainerAux.nativeElement.dropzone.files = ComposePostComponent.postAux.postsAttachments;
    }

    //ComposePostComponent.dropZoneContainerAux.nativeElement.dropzone.previewsContainer.children.append(arr);
    let l = arr.length;

    for (var i = 0; i < l; i++) {
      ComposePostComponent.dropZoneContainerAux.nativeElement.dropzone.previewsContainer.appendChild(arr[i]);
    }
  }

  public static swapArrayElements(arr, indexA, indexB) {
    var temp = arr[indexA];
    arr[indexA] = arr[indexB];
    arr[indexB] = temp;
  }

  public onRemoveFile(e) {

    try {
      if (e && e.previewElement && e.previewElement['postAttachment']) {

        if (this.post && this.post.postsAttachments && (this.post.postsAttachments.indexOf(e.previewElement['postAttachment']) > -1)) {
          this.post.postsAttachments.splice(this.post.postsAttachments.indexOf(e.previewElement['postAttachment']), 1);

          if (e.previewElement['postAttachment']) {
            this.dataService.deletePostAttachment(e.previewElement['postAttachment']).subscribe();
          }

          if (this.post && this.post.postsAttachments && this.post.postsAttachments.length == 0 &&
            this.dropZoneContainer && (this.dropZoneContainer.nativeElement.dropzone)) {
            //(this.dropZoneContainer.nativeElement.dropzone).reset();
            (this.dropZoneContainer.nativeElement.dropzone).previewsContainer.classList.remove("dz-started");
          }
        }
      }
    } catch (e) {
      console.log("Got an error!", e);
    }
  }

  checkDatePost() {
    if (!this.datePost) {
      this.datePost = new Date();
    }
  }

  validateCanSave(e) {
    if ((this.dropZoneContainer.nativeElement.dropzone).getUploadingFiles().length == 0) {
      this.isUploading = false;
    } else {
      this.isUploading = true;
    }
  }

  validateAddedFile(e) {

    if (e.type == ComposePostComponent.VIDEOGIF || e.type == ComposePostComponent.VIDEOWEBM) {
      this.error = 'Ops! Not support ' + e.type + ' files.';
      // this.error = 'O formato de seu vídeo deve ser MP4, o único suportado pelo Twitter.';
      this.dropZoneContainer.nativeElement.dropzone.emit("canceled", e);
      return false;
    }
  }

  showWarningMessage() {

    if (this.alertMessage) {
      const options = {
        message: this.alertMessage,
        style: 'bar',
        timeout: 4000,
        position: 'top',
        type: 'warning'
      };

      $('body').pgNotification(options).show();
    }
  }

  showSuccessMessage() {

    if (this.successMessage) {
      const options = {
        message: this.successMessage,
        style: 'bar',
        timeout: 4000,
        position: 'top',
        type: 'success'
      };

      $('body').pgNotification(options).show();
    }
  }

  showErrorPersist(error) {
    this.canSave = true;
    this.error = error;

    if (this.error) {

      var timeout = 4000;

      if (this.error == this.INSTAGRAM_CHECKPOINT_REQUIRED) {
        timeout = 10000;
      }

      if (error instanceof TimeoutError) {

        this.error = "A TimeoutError problem occours, please try again.";
      } else if (this.error.indexOf("Unexpected token < in JSON at position 0") > -1) {

        this.error = "A problem with the proxy occours, please try again.";
      }

      const options = {
        message: this.error,
        style: 'bar',
        timeout: timeout,
        position: 'top',
        type: 'danger'
      };

      $('body').pgNotification(options).show();
    }
  }

  onDeleteAttachment(res) {

  }
}