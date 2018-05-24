import { RightSidebarComponent } from './../core/componentes/right-sidebar/right-sidebar.component';
import { CrudService } from './../core/services/crud/crud.service';
import { LaudarComponent } from './../views/laudar/laudar.component';
import { RealizarComponent } from './../views/realizar/realizar.component';
import { CadastrarComponent } from './../views/cadastrar/cadastrar.component';
import { AgendarComponent } from './../views/agendar/agendar.component';
/* Angular Imports */
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { DatePipe } from '@angular/common';
import { DecimalPipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA, ApplicationRef } from '@angular/core';

/* Modules */
import { BsDatepickerModule } from 'ngx-bootstrap';
import { CoreModule } from 'core/componentes/opsocial/core.module';
import { DatepickerModule } from 'ngx-bootstrap/datepicker';
import { DragulaModule } from 'ng2-dragula';
import { DropzoneModule } from 'angular2-dropzone-wrapper';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgBoxModule } from 'core/componentes/ngbox/ngbox.module';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';

/* Views */
import { ComposePostComponent } from 'views/compose-post/compose-post.component';
import { FeedComponent } from 'views/feed/feed.component';
import { HomeComponent } from 'views/home/home.component';
import { LoginComponent } from 'views/login/login.component';
import { NewUserComponent } from 'views/new-user/new-user.component';
import { ReportComponent } from 'views/reports/report/report.component';
import { RssfeedComponent } from 'views/rssfeed/rssfeed.component';
import { ScheduleComponent } from 'views/schedule/schedule.component';

/* Components */
import { Accordion_menuComponent } from 'core/componentes/accordion_menu/accordion_menu.component';
import { AddFacebookChannellsComponent } from 'core/componentes/add-facebook-channells/add-facebook-channells.component';
import { AddInstagramChannelComponent } from 'core/componentes/add-instagram-channel/add-instagram-channel.component';
import { AddInstagramSchedulingComponent } from 'core/componentes/add-instagram-scheduling/add-instagram-scheduling.component';
import { AddStreamsComponent } from 'core/componentes/add-streams/add-streams.component';
import { AlertDialogComponent } from 'core/componentes/alert-dialog/alert-dialog.component';
import { AppComponent } from './app.component';
import { AreaChartComponent } from 'core/componentes/charts/area-chart/area-chart.component';
import { AsideHorizontalTextIconComponent } from 'core/componentes/complements/aside-horizontal-text-icon/aside-horizontal-text-icon.component';
import { AsidePieChartComponent } from 'core/componentes/complements/aside-pie-chart/aside-pie-chart.component';
import { AsidePieChartDoubleComponent } from 'core/componentes/complements/aside-pie-chart-double/aside-pie-chart-double.component';
import { AsideProgressBarComponent } from 'core/componentes/complements/aside-progress-bar/aside-progress-bar.component';
import { AsideSmallLineChartComponent } from 'core/componentes/complements/aside-small-line-chart/aside-small-line-chart.component';
import { AsideTextGrowthComponent } from 'core/componentes/complements/aside-text-growth/aside-text-growth.component';
import { AsideTextIconComponent } from 'core/componentes/complements/aside-text-icon/aside-text-icon.component';
import { BarChartComponent } from 'core/componentes/charts/bar-chart/bar-chart.component';
import { BreadcrumbComponent } from 'core/componentes/breadcrumb/breadcrumb.component';
import { BreadcrumbModalComponent } from 'core/componentes/breadcrumb-modal/breadcrumb-modal.component';
import { BubbleChartComponent } from 'core/componentes/charts/bubble-chart/bubble-chart.component';
import { Button_channelsComponent } from 'core/componentes/button_channels/button_channels.component';
import { Button_check_allComponent } from 'core/componentes/button_check_all/button_check_all.component';
import { Button_rssfeedComponent } from 'core/componentes/button_rssfeed/button_rssfeed.component';
import { CancelSubscriptionComponent } from 'core/componentes/cancel-subscription/cancel-subscription.component';
import { TutorialComponent } from 'core/componentes/tutorial-modal/tutorial-modal.component';
import { ColumnChartComponent } from 'core/componentes/charts/column-chart/column-chart.component';
import { ConfirmDialogComponent } from 'core/componentes/confirm-dialog/confirm-dialog.component';
import { DeleteChannelComponent } from 'core/componentes/delete-channel/delete-channel.component';
import { DeleteGroupComponent } from 'core/componentes/delete-group/delete-group.component';
import { DynamicModalContentComponent } from 'core/componentes/dynamic-modal-content/dynamic-modal-content.component';
import { Edit_groupsComponent } from 'core/componentes/edit_groups/edit_groups.component';
import { EmojiComponent } from 'core/componentes/emoji/emoji.component';
import { FacebookPreviewComponent } from 'core/componentes/facebook-preview/facebook-preview.component';
import { FacebookReportComponent } from 'views/reports/facebook-report/facebook-report.component';
import { FacebookReportContentComponent } from 'views/reports/facebook-report-content/facebook-report-content.component';
import { FacebookReportHistoricalComponent } from 'views/reports/facebook-report-historical/facebook-report-historical.component';
import { FacebookReportOverviewComponent } from 'views/reports/facebook-report-overview/facebook-report-overview.component';
import { FacebookReportPostToPostComponent } from 'views/reports/facebook-report-post-to-post/facebook-report-post-to-post.component';
import { FollowersComponent } from 'core/componentes/followers/followers.component';
import { FooterComponent } from 'core/componentes/footer/footer.component';
import { FooterTextIconComponent } from 'core/componentes/complements/footer-text-icon/footer-text-icon.component';
import { FooterTextIconAlternativeComponent } from './../core/componentes/complements/footer-text-icon-alternative/footer-text-icon-alternative.component';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { Group_itemComponent } from 'core/componentes/group_item/group_item.component';
import { Groups_managementComponent } from 'core/componentes/groups_management/groups_management.component';
import { HeaderComponent } from 'core/componentes/header/header.component';
import { HelloWoopComponent } from 'core/componentes/hello-woop/hello-woop.component';
import { InboxComponent } from 'core/componentes/inbox/inbox.component';
import { InboxMessageComponent } from 'core/componentes/inbox/inbox-message/inbox-message.component';
import { InstagramPreviewComponent } from 'core/componentes/instagram-preview/instagram-preview.component';
import { InstagramReportComponent } from 'views/reports/instagram-report/instagram-report.component';
import { InstagramReportMainComponent } from 'views/reports/instagram-report-main/instagram-report-main.component';
import { InstagramReportOverviewComponent } from 'views/reports/instagram-report-overview/instagram-report-overview.component';
import { InstagramReportHistoricalComponent } from 'views/reports/instagram-report-historical/instagram-report-historical.component';
import { JumbotronComponent } from 'core/componentes/jumbotron/jumbotron.component';
import { LineBarChartComponent } from 'core/componentes/charts/line-bar-chart/line-bar-chart.component';
import { LinkedinPreviewComponent } from 'core/componentes/linkedin-preview/linkedin-preview.component';
import { LinkedinReportComponent } from 'views/reports/linkedin-report/linkedin-report.component';
import { ManageChannelsComponent } from 'core/componentes/manage-channels/manage-channels.component';
import { ManageRssfeedsComponent } from 'core/componentes/manage-rssfeeds/manage-rssfeeds.component';
import { MultiChartComponent } from 'core/componentes/charts/multi-chart/multi-chart.component';
import { ModalsComponent } from 'core/componentes/modals/modals.component';
import { My_channelsComponent } from 'core/componentes/my_channels/my_channels.component';
import { Op_DatePickerComponent } from 'core/componentes/op_datepicker/op_datepicker.component';
import { Op_TimePickerComponent } from 'core/componentes/op_timepicker/op-timepicker.component';
import { PieChartComponent } from 'core/componentes/charts/pie-chart/pie-chart.component';
import { PostPreviewComponent } from 'core/componentes/post-preview/post-preview.component';
import { ProfileReconnectComponent } from 'core/componentes/profile-reconnect/profile-reconnect.component';
import { Quick_searchComponent } from 'core/componentes/quick_search/quick_search.component';
import { Quick_viewComponent } from 'core/componentes/quick_view/quick_view.component';
import { ScheduleItemComponent } from 'core/componentes/schedule-item/schedule-item.component';
import { ScheduleAuthComponent } from './../core/componentes/schedule-auth/schedule-auth.component';
import { SettingsManagementComponent } from 'core/componentes/settings-management/settings-management.component';
import { SidebarComponent } from 'core/componentes/sidebar/sidebar.component';
import { SmallLineChartComponent } from 'core/componentes/charts/small-line-chart/small-line-chart.component';
import { StackedAreaChartComponent } from 'core/componentes/charts/stacked-area-chart/stacked-area-chart.component';
import { StackedColumnChartComponent } from 'core/componentes/charts/stacked-column-chart/stacked-column-chart.component';
import { StreamComponent } from 'core/componentes/stream/stream.component';
import { StreamPostCommentComponent } from 'core/componentes/stream-post-comment/stream-post-comment.component';
import { StreamPostComponent } from 'core/componentes/stream-post/stream-post.component';
import { StreamPostReplyComponent } from 'core/componentes/stream-post-reply/stream-post-reply.component';
import { TableChartComponent } from 'core/componentes/charts/table/table-chart.component';
import { TeamManagementComponent } from 'core/componentes/team-management/team-management.component';
import { TopReportComponent } from 'core/componentes/top-report/top-report.component';
import { TwitterPreviewComponent } from 'core/componentes/twitter-preview/twitter-preview.component';
import { TwitterReportComponent } from 'views/reports/twitter-report/twitter-report.component';
import { UnlockReportsComponent } from 'core/componentes/unlock-reports/unlock-reports.component';
import { UnsubscribeEmailsComponent } from 'views/unsubscribe-emails/unsubscribe-emails.component';
import { VideoThumbnailComponent } from 'views/compose-post/videoThumbnail.component';
import { ViewRssFeedComponent } from 'core/componentes/view-rssfeed/view-rssFeed.component';
import { WordCloudComponent } from 'core/componentes/charts/word-cloud/word-cloud.component';
import { BlankComponent } from './../core/componentes/blank-component/blank.component';
import { OpSmartTipComponent } from './../core/componentes/op-smarttip/op-smarttip.component';

/* Services */
import { AlertService } from 'core/services/alert/alert.service';
import { BindMarksService } from 'core/services/bindMarks/bindMarks.service';
import { BreadcrumbsService } from 'core/services/breadcrumbs/breadcrumbs.service';
import { CheckUrlService } from 'core/services/checkUrl/checkUrl.service';
import { ControlService } from 'core/services/control/control.service';
import { DialogService } from 'core/services/dialog/dialog.service';
import { EmojiService } from 'core/services/emojis/emojis.service';
import { FacebookService } from 'core/services/facebook/facebook.service';
import { FollowersService } from 'core/services/followers/followers.service';
import { GroupsService } from 'core/services/groups/groups.service';
import { InboxService } from 'core/services/inbox/inbox.service';
import { InstagramService } from 'core/services/instagram/instagram.service';
import { LinkedinService } from 'core/services/linkedin/linkedin.service';
import { LoginService } from 'core/services/login/login.service';
import { LogoutService } from 'core/services/logout/logout.service';
import { NgBoxService } from 'core/componentes/ngbox/ngbox.service';
import { NotificationService } from 'core/services/notification/notification.service';
import { PdfService } from 'core/services/pdf/pdf.service';
import { PostService } from 'core/services/post/post.service';
import { ProfilesService } from 'core/services/profiles/profiles.service';
import { ModalService } from './../core/services/modal/modal.service';
import { QuickviewService } from 'core/services/quickview/quickview.service';
import { ScheduleService } from 'core/services/schedule/schedule.service';
import { StreamService } from 'core/services/stream/stream.service';
import { TabService } from 'core/services/tab/tab.service';
import { TimezoneService } from 'core/services/timezone/timezone.service';
import { TwitterService } from 'core/services/twitter/twitter.service';
import { UploadService } from 'core/services/upload/upload.service';
import { UserService } from 'core/services/user/user.service';
import { WindowRefService } from 'core/services/window/window-ref.service';

/* Guards */
import { CanActivateViaAuthguard } from 'guards/can-activate-via.authguard';
import { CanActivateStreamViaAuthguard } from 'guards/can-activate-stream-via.authguard';
import { CanAlwaysActivateViaAuthguard } from 'guards/can-always-activate-via.authguard';

/* Pipes */
import { AbbreviateNumberPipe } from 'core/pipes/abbreviateNumber.pipe';
import { HashtagsMark } from '../core/pipes/hashtags.pipe';
import { HHourPipe } from 'core/pipes/hHour.pipe';
import { LimitTextPipe } from 'core/pipes/limitText.pipe';
import { MentionMark } from 'core/pipes/mentionMark.pipe';
import { MonthNamePipe } from 'core/pipes/monthName.pipe';
import { ProfileType } from 'core/pipes/profileType.pipe';
import { ScapeBreakLinesPipe } from 'core/pipes/scapeBreakLines.pipe';
import { SeeMoreFbPipe } from 'core/pipes/seeMoreFb.pipe';
import { TruncHour } from 'core/pipes/truncHour.pipe';
import { UnurlfyPipe } from 'core/pipes/unurlfy.pipe';
import { UrlfyPipe } from 'core/pipes/urlfy.pipe';

/* Routes */
import { app_routing } from './app.routing';

/* Directives */
import { GetCursorPositionDirective } from 'core/directives/get-cursor-position.directive';
import { NvD3Module } from 'angular2-nvd3';
import { StreamWidthAdjustDirective } from 'core/directives/stream-width-adjust.directive';

import { DropzoneConfigInterface } from 'angular2-dropzone-wrapper';
import { TruncatePipe } from 'core/pipes/truncate';
import { SmartTipService } from 'core/services/smartTip/smartTip.service';

const DROPZONE_CONFIG: DropzoneConfigInterface = {
  // Change this to your upload POST address:
  server: 'http://localhost:8080/OpSocialBack/api/upload/',
  // server: 'https://app.woopsocial.com/api/upload/',
  maxFilesize: 100,
  acceptedFiles: 'image/*,video/*',
  addRemoveLinks: true,
  dictCancelUpload: '',
  dictRemoveFile: ''
};

@NgModule({
  declarations: [
    AbbreviateNumberPipe,
    Accordion_menuComponent,
    AddFacebookChannellsComponent,
    AddInstagramChannelComponent,
    AddStreamsComponent,
    AlertDialogComponent,
    AppComponent,
    AreaChartComponent,
    AsideHorizontalTextIconComponent,
    AsidePieChartComponent,
    AsidePieChartDoubleComponent,
    AsideProgressBarComponent,
    AsideSmallLineChartComponent,
    AsideTextGrowthComponent,
    AsideTextIconComponent,
    BarChartComponent,
    BreadcrumbComponent,
    BreadcrumbModalComponent,
    BubbleChartComponent,
    Button_channelsComponent,
    Button_check_allComponent,
    Button_rssfeedComponent,
    CancelSubscriptionComponent,
    ColumnChartComponent,
    ComposePostComponent,
    ConfirmDialogComponent,
    DeleteChannelComponent,
    DeleteGroupComponent,
    DynamicModalContentComponent,
    Edit_groupsComponent,
    EmojiComponent,
    FacebookPreviewComponent,
    FacebookReportComponent,
    FacebookReportContentComponent,
    FacebookReportHistoricalComponent,
    FacebookReportOverviewComponent,
    FacebookReportPostToPostComponent,
    FeedComponent,
    FollowersComponent,
    FooterComponent,
    FooterTextIconComponent,
    FooterTextIconAlternativeComponent,
    GenericModalComponent,
    GetCursorPositionDirective,
    Group_itemComponent,
    Groups_managementComponent,
    HashtagsMark,
    HeaderComponent,
    HelloWoopComponent,
    HHourPipe,
    HomeComponent,
    InboxComponent,
    InboxMessageComponent,
    InstagramReportHistoricalComponent,
    InstagramPreviewComponent,
    InstagramReportComponent,
    JumbotronComponent,
    LimitTextPipe,
    LineBarChartComponent,
    LinkedinPreviewComponent,
    LinkedinReportComponent,
    LoginComponent,
    ManageChannelsComponent,
    ManageRssfeedsComponent,
    MentionMark,
    MonthNamePipe,
    MultiChartComponent,
    My_channelsComponent,
    NewUserComponent,
    Op_DatePickerComponent,
    Op_TimePickerComponent,
    PieChartComponent,
    PostPreviewComponent,
    ProfileReconnectComponent,
    ProfileType,
    Quick_searchComponent,
    Quick_viewComponent,
    ReportComponent,
    RssfeedComponent,
    ScapeBreakLinesPipe,
    ScheduleComponent,
    ScheduleAuthComponent,
    ScheduleItemComponent,
    SeeMoreFbPipe,
    SettingsManagementComponent,
    SidebarComponent,
    SmallLineChartComponent,
    StackedAreaChartComponent,
    StackedColumnChartComponent,
    StreamComponent,
    StreamPostCommentComponent,
    StreamPostComponent,
    StreamPostReplyComponent,
    StreamWidthAdjustDirective,
    TableChartComponent,
    TeamManagementComponent,
    TopReportComponent,
    TruncatePipe,
    TruncHour,
    TwitterPreviewComponent,
    TwitterReportComponent,
    UnlockReportsComponent,
    UnsubscribeEmailsComponent,
    UnurlfyPipe,
    UrlfyPipe,
    VideoThumbnailComponent,
    ViewRssFeedComponent,
    WordCloudComponent,
    AddInstagramSchedulingComponent,
    InstagramReportMainComponent,
    InstagramReportOverviewComponent,
    InstagramReportHistoricalComponent,
    OpSmartTipComponent,
    BlankComponent,
    TutorialComponent,
    ModalsComponent,
    AgendarComponent,
    CadastrarComponent,
    RealizarComponent,
    LaudarComponent,
    RightSidebarComponent
  ],
  imports: [
    app_routing,
    BrowserModule,
    CommonModule,
    CoreModule,
    DragulaModule,
    FormsModule,
    HttpModule,
    NgBoxModule,
    NvD3Module,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    DatepickerModule.forRoot(),
    DropzoneModule.forRoot(DROPZONE_CONFIG),
    ModalModule.forRoot(),
    TimepickerModule.forRoot(),
  ],
  providers: [
    AbbreviateNumberPipe,
    AlertService,
    BindMarksService,
    BreadcrumbsService,
    CanActivateViaAuthguard,
    CanActivateStreamViaAuthguard,
    CanAlwaysActivateViaAuthguard,
    CheckUrlService,
    ControlService,
    DatePipe,
    DecimalPipe,
    DialogService,
    EmojiService,
    FacebookService,
    FollowersService,
    GroupsService,
    HHourPipe,
    InboxService,
    InstagramService,
    LinkedinService,
    LoginService,
    LogoutService,
    MonthNamePipe,
    NgBoxService,
    NotificationService,
    PdfService,
    PostService,
    ProfilesService,
    QuickviewService,
    ScheduleService,
    StreamService,
    TabService,
    TimezoneService,
    TwitterService,
    UploadService,
    UserService,
    WindowRefService,
    LogoutService,
    SmartTipService,
    ModalService,
    CrudService
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule {
  constructor(private _appRef: ApplicationRef) { }

  ngDoBootstrap() {
    this._appRef.bootstrap(AppModule);
  }
}
