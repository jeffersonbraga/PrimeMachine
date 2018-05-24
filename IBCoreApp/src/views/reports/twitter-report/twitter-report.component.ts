import { ProfilesService } from './../../../core/services/profiles/profiles.service';
import { DatePipe, DecimalPipe } from '@angular/common';
import { Component, DoCheck, Input, KeyValueDiffers, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { AbbreviateNumberPipe } from 'core/pipes/abbreviateNumber.pipe';
import { HHourPipe } from 'core/pipes/hHour.pipe';
import { MonthNamePipe } from 'core/pipes/monthName.pipe';
import { ControlService } from 'core/services/control/control.service';
import { NotificationService } from 'core/services/notification/notification.service';
import { PdfService } from 'core/services/pdf/pdf.service';
import { TwitterService } from 'core/services/twitter/twitter.service';
import { IAsidePieChart, IAsideTextIcon } from 'modelo/complements/Complements';
import { Notification } from 'modelo/notification/Notification';
import { Profile } from 'modelo/profile/Profile';

declare const html2canvas;
declare const $;

@Component({
    moduleId: module.id,
    selector: 'twitter-report',
    templateUrl: './twitter-report.component.html',
    styleUrls: ['./twitter-report.component.css']
})
export class TwitterReportComponent implements DoCheck, OnInit {

    showCheckAllBtn = false;

    isLoading = true;

    @Input() reportConfigs = {
        dateFrom: null,
        dateUntil: null,
        profile: null
    };

    differ: any;
    dateFrom: Date;
    dateUntil: Date;
    profile: Profile;

    account;

    reportState: string = Profile.REPORT_FINALIZED;

    period;

    weekDays: string[] = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
    weekDaysFull: string[] = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

    // weekDays: string[] = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'];
    // weekDaysFull: string[] = ['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'];
    totalTweetsPerHour: number;
    totalTweetsPerDay: number;
    biggestHour: number;
    biggestDay: number;

    data: any;
    followersByDayData;
    twittesTypeData;
    tweetsPerHourData;
    tweetsPerWeekDayData;
    favoritesByDayData;
    retweetsByDayData;
    repliesByDayData;
    mentionsByDayData;
    followersOfFollowersData;
    followersActivityData;
    citiesWithMoreFollowersData;
    mostInfluentialFollowersData1;
    mostInfluentialFollowersData2;
    twitterReportStatusData;

    totalFollowersAside: IAsideTextIcon;
    bestAmoutOfFollowersAside: IAsideTextIcon;
    growthRateAside: IAsideTextIcon;
    averageTweetsAside: IAsideTextIcon;
    averageFollowersOfFollowersAside: IAsideTextIcon;
    potentialReachAside: IAsideTextIcon;
    followersEngagementAside: IAsideTextIcon;
    followersActivityBiggerPeriodAside: IAsideTextIcon;

    tweetsPeakHourFooter: IAsideTextIcon;
    tweetsPeakDayFooter: IAsideTextIcon;
    favoritesFooter: IAsideTextIcon;
    retweetsFooter: IAsideTextIcon;
    repliesFooter: IAsideTextIcon;
    mentionsFooter: IAsideTextIcon;

    perTypeOfTweetAsidePie: IAsidePieChart;
    perSexAsidePie: IAsidePieChart;

    legendsType1 = {
        legends: true,
        color: ['#20ffa2']
    };

    legendsType2 = {
        legends: true,
        color: ['#0085be', '#01aed9', '#01d6f8']
    };

    legendsType3 = {
        legends: true,
        color: ['#01d6f8']
    };

    saving = true;
    htmlElementsTotal = 11;
    currentElementsCount: number;

    loadMorePosts: Boolean = false;
    loadingMorePosts: Boolean = false;
    postsOffset: number;
    postsLimitPerCall = 50;

    constructor(
        private twitterService: TwitterService,
        private datePipe: DatePipe,
        private decimalPipe: DecimalPipe,
        private monthNamePipe: MonthNamePipe,
        private hHourPipe: HHourPipe,
        private sanitizer: DomSanitizer,
        private controlService: ControlService,
        private pdfService: PdfService,
        private abbreviateNumberPipe: AbbreviateNumberPipe,
        private notificationService: NotificationService,
        private differs: KeyValueDiffers,
        private profilesService: ProfilesService
    ) {
        this.differ = differs.find({}).create(null);
    }

    ngDoCheck() {
        const changes = this.differ.diff(this.reportConfigs);

        if (changes) {
            this.initValues();
        }
    }

    initValues() {
        if (this.reportConfigs.profile.idProfile === this.profilesService.singleCheckedProfile.idProfile) {
            this.dateFrom = this.reportConfigs.dateFrom;
            this.dateUntil = this.reportConfigs.dateUntil;
            this.profile = this.reportConfigs.profile;

            if (this.dateFrom && this.dateUntil && this.profile) {
                this.getReport();
                this.setPeriod();
            }
        }
    }

    ngOnInit() {
        this.account = this.controlService.account;
    }

    // ngOnChanges() {
    //     this.getReport();
    //     this.setPeriod();
    // }

    setPeriod() {
        const timeDiff = this.dateUntil.getTime() - this.dateFrom.getTime();
        this.period = Math.ceil(timeDiff / (1000 * 3600 * 24));
    }

    getReport() {
        this.isLoading = true;

        if (this.validateForm()) {
            this.twitterService.getReport(this.profile.idProfile, this.dateFrom.getTime(), this.dateUntil.getTime())
                .subscribe(
                    result => this.mountCharts(result),
                    error => console.log(error)
                );
        }
    }

    getXLS() {
        this.twitterService.getXLS(this.profile.idProfile, this.dateFrom.getTime(), this.dateUntil.getTime())
            .subscribe(
                result => this.downloadXLS(result),
                error => console.log(error)
            );
    }

    downloadXLS(result) {
        window.open(this.controlService.context_app + 'woopsocial/get_xls?file='
            + result.fileName + '&filename=' + result.formattedFileName + '&idAccount=' + result.idAccount);
    }

    getPDF() {
        const notification: Notification = {
            account: this.account,
            profile: this.profile,
            date: new Date(),
            message: this.account.name + ' created a PDF report for channel ' + this.profile.screenName + '.',
            // message: this.account.name + ' gerou um relatório em PDF do canal ' + this.profile.screenName + '.',
            color: this.notificationService.COLOR_SUCCESS,
            action: '',
            unread: true
        };

        this.notificationService.addNotifications(notification)
            .subscribe(
                result => console.log(result),
                error => console.log(error)
            );
        window.print();

        // TODO: Implementar futuramente para salvar em PDF
        if (!this.saving) {
            this.saving = true;

            this.currentElementsCount = 0;
            this.pdfService.new();

            this.pdfService.setInlineStyle();

            this.pdfService.addText(this.profile.screenName + ' - Visão Geral - Dados de '
                + this.datePipe.transform(this.dateFrom, 'dd/MM/yyyy') + ' to ' +
                this.datePipe.transform(this.dateFrom, 'dd/MM/yyyy'), 15, 20);
            this.pdfService.addText('SALDO DE SEGUIDORES NO PERÍODO', 15, 40);
            // this.pdfService.appendHtml($('#totalFollowersPdf').find('.aside-content')[0], 270, 50, 1).then(()=> this.savePdf());
            // this.pdfService.appendHtml($('#bestAmoutOfFollowersPdf').find('.aside-content')[0], 270, 80, 1).then(()=> this.savePdf());
            // this.pdfService.appendHtml($('#growthRatePdf').find('.aside-content')[0], 270, 130, 1).then(()=> this.savePdf());
            this.pdfService.appendSvg('followersByDayPdf', 15, 50);

            this.pdfService.addText('TWEETS ENVIADOS NO PERÍODO', 15, 210);
            // this.pdfService.appendHtml($('#averageTweetsPdf').find('.aside-content')[0], 270, 220, 1).then(()=> this.savePdf());
            // this.pdfService.appendHtml($('#perTypeOfTweetPdf').find('.aside-content')[0], 270, 270, 1).then(()=> this.savePdf());
            this.pdfService.appendSvg('twittesTypePdf', 15, 220);

            this.pdfService.addText('TWEETS PUBLICADOS DIVIDIDOS POR HORÁRIO', 15, 370);
            // this.pdfService.appendHtml($('#tweetsPeakHourPdf').find('.footer-content')[0], 15, 530, 1).then(()=> this.savePdf());
            this.pdfService.appendSvg('tweetsPerHourPdf', 15, 380);

            this.pdfService.addPage();
            this.pdfService.setPage(2);

            this.pdfService.addText('TWEETS POR DIA DA SEMANA', 15, 40);
            // this.pdfService.appendHtml($('#tweetsPeakDayPdf').find('.footer-content')[0], 15, 180, 2).then(()=> this.savePdf());
            this.pdfService.appendSvg('tweetsPerWeekDayPdf', 15, 50);

            this.pdfService.addText('CURTIR', 15, 210);
            // this.pdfService.appendHtml($('#favoritesPdf').find('.footer-content')[0], 15, 350, 2).then(()=> this.savePdf());
            this.pdfService.appendSvg('favoritesByDayPdf', 15, 220);

            this.pdfService.addText('RETWEETS', 15, 370);
            // this.pdfService.appendHtml($('#retweetsPdf').find('.footer-content')[0], 15, 510, 2).then(()=> this.savePdf());
            this.pdfService.appendSvg('retweetsByDayPdf', 15, 380);

            this.pdfService.addPage();
            this.pdfService.setPage(3);

            this.pdfService.addText('RESPOSTAS', 15, 40);
            // this.pdfService.appendHtml($('#repliesPdf').find('.footer-content')[0], 15, 180, 3).then(()=> this.savePdf());
            this.pdfService.appendSvg('repliesByDayPdf', 15, 50);

            this.pdfService.addText('MENÇÕES', 15, 210);
            // this.pdfService.appendHtml($('#mentionsPdf').find('.footer-content')[0], 15, 350, 3).then(()=> this.savePdf());
            this.pdfService.appendSvg('mentionsByDayPdf', 15, 220);
            this.saving = false;
            this.pdfService.save('teste');
        }
    }

    savePdf() {
        this.currentElementsCount++;
        if (this.currentElementsCount === this.htmlElementsTotal) {
            this.saving = false;
            this.pdfService.save('teste');
        }
    }

    validateForm(): boolean {
        if (this.dateFrom && this.dateUntil && this.profile) {
            return true;
        } else {
            return false;
        }
    }

    mountCharts(report) {
        this.isLoading = false;

        this.data = report.reportTwitterOverview;
        this.reportState = report.reportState;

        if (this.reportState === Profile.REPORT_FINALIZED) {

            this.followersByDay();
            this.twittsTypes();
            this.tweetsPerHour();
            this.tweetsPerWeekDay();
            this.favoritesByDay();
            this.retweetsByDay();
            this.repliesByDay();
            this.mentionsByDay();
            this.followersOfFollowers();
            this.followersActivity();
            this.citiesWithMoreFollowers();
            this.mostInfluentialFollowers();
            this.twitterReportStatus();

            this.totalFollowers();
            this.bestAmoutOfFollowers();
            this.growthRate();
            this.averageTweets();
            this.averageFollowersOfFollowers();
            this.potentialReach();
            this.followersEngagement();
            this.followersActivityBiggerPeriod();

            this.tweetsPeakHour();
            this.tweetsPeakDay();
            this.favorites();
            this.retweets();
            this.replies();
            this.mentions();

            this.perTypeOfTweet();
            this.perSex();
        }

    }

    formatDate(date) {
        date = new Date(date * 1000);
        return this.datePipe.transform(date, 'MM/dd');
    }

    formatHour(hour) {
        hour = hour === 24 ? 0 : hour;

        return (hour < 10 ? '0' + hour : hour) + ':00';
    }

    formatDateReportStatus(date) {
        return this.sanitizer.bypassSecurityTrustHtml(`<div>
                    <span class="fa fa-calendar"></span>
                    <span class="date"><strong>` + this.datePipe.transform(date, 'dd') + ` `
            + this.monthNamePipe.transform(this.datePipe.transform(date, 'MM')) + `</strong></span>
                    <span class="year">` + this.datePipe.transform(date, 'yyyy') + `</span>
                </div>
                <div class="time">
                    <span class="fa fa-clock-o"></span>
                    <span class="hour"><strong>` + this.hHourPipe.transform(this.datePipe.transform(date, 'HH:mm')) + `</strong></span>
                </div>`);
    }

    formatTextReportStatus(text: string, image: string) {
        if (image) {
            return this.sanitizer.bypassSecurityTrustHtml(`
                <div class="img-adjust-table-chart pull-left">
                    <img style="object-fit: contain;" src="` + image + `">
                </div>
                ` + text + `
            `);
        } else {
            return text;
        }
    }

    formatStatusCount(icon, label) {
        return this.sanitizer.bypassSecurityTrustHtml(`<i class="cyan fa ` + icon +
            `" aria-hidden="true"></i> <strong>` + label + `</strong>`);
    }

    createTdValue(img, text) {
        return '<span><img style="object-fit: contain;" height="28" src="' + img + '" /> ' + text + '</span>';
    }

    getValuesChart3() {
        const arr = [];
        let currentBiggestValue = 0;

        this.totalTweetsPerHour = 0;
        this.biggestHour = 0;

        for (let i = 0; i < 24; i++) {
            this.totalTweetsPerHour += this.data.tweetsPerHour[i];

            this.biggestHour = currentBiggestValue < this.data.tweetsPerHour[i] ? i : this.biggestHour;
            currentBiggestValue = currentBiggestValue < this.data.tweetsPerHour[i] ? this.data.tweetsPerHour[i] : currentBiggestValue;

            arr.push({
                label: this.formatHour(i),
                value: this.data.tweetsPerHour[i],
                color: '#01d6f8'
            });
        }

        return arr;
    }

    getValuesChart4() {
        const arr = [];
        let currentBiggestValue = 0;

        this.totalTweetsPerDay = 0;
        this.biggestDay = 0;

        for (let i = 0; i < 7; i++) {
            this.totalTweetsPerDay += this.data.tweetsPerWeekDay[i];

            this.biggestDay = currentBiggestValue < this.data.tweetsPerWeekDay[i] ? i : this.biggestDay;
            currentBiggestValue = currentBiggestValue < this.data.tweetsPerWeekDay[i] ? this.data.tweetsPerWeekDay[i] : currentBiggestValue;

            arr.push({
                label: this.weekDays[i],
                value: this.data.tweetsPerWeekDay[i],
                color: '#01d6f8'
            });
        }

        return arr;
    }

    followersByDay() {
        this.followersByDayData = [
            {
                key: 'Novos seguidores',
                values: this.data.followersByDay.map(
                    fav => {
                        return {
                            label: this.formatDate(fav.date),
                            value: fav.value,
                            color: '#20ffa2'
                        };
                    })
            }
        ];
    }

    twittsTypes() {
        this.twittesTypeData = [{
            'key': 'Link',
            'values': this.data.tweetsSentWithLink.map(
                fav => {
                    return {
                        x: this.formatDate(fav.date),
                        y: fav.value,
                        color: '#0085be'
                    };
                })
        }, {
            'key': 'Photo/video',
            'values': this.data.tweetsSentWithPhotoVideo.map(
                fav => {
                    return {
                        x: this.formatDate(fav.date),
                        y: fav.value,
                        color: '#01aed9'
                    };
                })
        }, {
            'key': 'Text',
            'values': this.data.tweetsSentWithText.map(
                fav => {
                    return {
                        x: this.formatDate(fav.date),
                        y: fav.value,
                        color: '#01d6f8'
                    };
                })
        }
        ];
    }

    tweetsPerHour() {
        this.tweetsPerHourData = [{
            key: 'Posts',
            values: this.getValuesChart3()
        }];
    }

    tweetsPerWeekDay() {
        this.tweetsPerWeekDayData = [{
            key: 'Fãs online',
            values: this.getValuesChart4()
        }];
    }

    favoritesByDay() {
        this.favoritesByDayData = [
            {
                key: 'Like',
                values: this.data.favoritesByDay.map(
                    fav => {
                        return {
                            x: fav.date * 1000,
                            y: fav.value
                        };
                    }),
                color: '#01d6f8'
            }
        ];
    }

    retweetsByDay() {
        this.retweetsByDayData = [
            {
                key: 'Retweets',
                values: this.data.retweetsByDay.map(
                    fav => {
                        return {
                            x: fav.date * 1000,
                            y: fav.value
                        };
                    }),
                color: '#01d6f8'
            }
        ];
    }

    repliesByDay() {
        this.repliesByDayData = [
            {
                key: 'Replies',
                values: this.data.repliesByDay.map(
                    fav => {
                        return {
                            x: fav.date * 1000,
                            y: fav.value
                        };
                    }),
                color: '#01d6f8'
            }
        ];
    }

    mentionsByDay() {
        this.mentionsByDayData = [
            {
                key: 'Mentions',
                values: this.data.mentionsByDay.map(
                    fav => {
                        return {
                            x: fav.date * 1000,
                            y: fav.value
                        };
                    }),
                color: '#01d6f8'
            }
        ];
    }

    followersOfFollowers() {
        const labels = this.data.followersOfFollowers;

        this.followersOfFollowersData = [
            {
                key: 'Número de seguidores dos seus seguidores',
                values: [
                    { label: '0-300', value: labels['0-300'] ? labels['0-300'] : 0, color: '#00d6f6' },
                    { label: '300-600', value: labels['300-600'] ? labels['300-600'] : 0, color: '#00d6f6' },
                    { label: '600-1k', value: labels['600-1000'] ? labels['600-1000'] : 0, color: '#00d6f6' },
                    { label: '1k-2k', value: labels['1000-2000'] ? labels['1000-2000'] : 0, color: '#00d6f6' },
                    { label: '2k-5k', value: labels['2000-5000'] ? labels['2000-5000'] : 0, color: '#00d6f6' },
                    { label: '5k+', value: labels['5000+'] ? labels['5000+'] : 0, color: '#00d6f6' },
                ]
            }
        ];
    }

    followersActivity() {
        const labels = this.data.followersActivity;

        this.followersActivityData = [
            {
                key: 'Número de seguidores dos seus seguidores',
                values: [
                    { label: '3 months', value: labels['3 months'] ? labels['3 months'] : 0, color: '#00d6f6' },
                    { label: '1 month', value: labels['1 month'] ? labels['1 month'] : 0, color: '#00d6f6' },
                    { label: '2 weeks', value: labels['2 weeks'] ? labels['2 weeks'] : 0, color: '#00d6f6' },
                    { label: '1 week', value: labels['1 week'] ? labels['1 week'] : 0, color: '#00d6f6' },
                    { label: '4 days', value: labels['4 days'] ? labels['4 days'] : 0, color: '#00d6f6' },
                    { label: '2 days', value: labels['2 days'] ? labels['2 days'] : 0, color: '#00d6f6' },
                    { label: '1 day', value: labels['1 day'] ? labels['1 day'] : 0, color: '#00d6f6' },
                ]
            }
        ];
    }

    // followersActivity() {
    //     const labels = this.data.followersActivity;

    //     this.followersActivityData = [
    //         {
    //         key: 'Número de seguidores dos seus seguidores',
    //         values: [
    //                 {label: '3 meses', value: labels['3 meses'] ? labels['3 meses'] : 0, color: '#00d6f6'},
    //                 {label: '1 mês', value: labels['1 mês'] ? labels['1 mês'] : 0, color: '#00d6f6'},
    //                 {label: '2 sem.', value: labels['2 sem.'] ? labels['2 sem.'] : 0, color: '#00d6f6'},
    //                 {label: '1 sem.', value: labels['1 sem.'] ? labels['1 sem.'] : 0, color: '#00d6f6'},
    //                 {label: '4 dias', value: labels['4 dias'] ? labels['4 dias'] : 0, color: '#00d6f6'},
    //                 {label: '2 dias', value: labels['2 dias'] ? labels['2 dias'] : 0, color: '#00d6f6'},
    //                 {label: '1 dia', value: labels['1 dia'] ? labels['1 dia'] : 0, color: '#00d6f6'},
    //             ]
    //         }
    //     ];
    // }

    citiesWithMoreFollowers() {
        const cities = Object.keys(this.data.citiesWithMoreFollowers);
        const dataCities = [];

        for (let i = 0; i < cities.length && i < 10; i++) {
            dataCities.push([
                { value: cities[i], class: 'left' },
                { value: this.data.citiesWithMoreFollowers[cities[i]], class: 'right' }
            ]);
        }

        this.citiesWithMoreFollowersData = {
            cols: [
                {value: 'City', class: 'left'}, {value: 'Followers', class: 'right', width: '100px'}
            ],
            rows: dataCities
        };
    }

    mostInfluentialFollowers() {
        const influentials: any = this.data.mostInfluentialFollowers;
        const dataInfluentials1 = [];
        const dataInfluentials2 = [];

        const cols = [{ value: 'Usuário', class: 'left' }, { value: 'Seguidores', class: 'right', width: '130px' }];

        for (let i = 0; i < influentials.length && i < 10; i++) {
            dataInfluentials1.push([
                { value: this.createTdValue(influentials[i].avatar, influentials[i].screenName), class: 'left' },
                { value: this.decimalPipe.transform(influentials[i].followersCount, '1.0-0'), class: 'right' }
            ]);
        }

        this.mostInfluentialFollowersData1 = {
            cols: cols,
            rows: dataInfluentials1
        };

        if (influentials.length > 10) {
            for (let i = 10; i < influentials.length && i < 20; i++) {
                dataInfluentials2.push([
                    { value: this.createTdValue(influentials[i].avatar, influentials[i].screenName), class: 'left' },
                    { value: this.decimalPipe.transform(influentials[i].followersCount, '1.0-0'), class: 'right' }
                ]);
            }

            this.mostInfluentialFollowersData2 = {
                cols: cols,
                rows: dataInfluentials2
            };
        } else {
            this.mostInfluentialFollowersData2 = [];
        }

    }

    twitterReportStatus() {
        const twitterData = this.data.twitterReportStatus;
        const length = twitterData.length;
        const dataTwitterReports = [];

        this.postsOffset = length;

        for (let i = 0; i < length; i++) {
            dataTwitterReports.push([
                { value: this.formatDateReportStatus(twitterData[i].createdAt), class: 'left' },
                { value: this.formatTextReportStatus(twitterData[i].text, twitterData[i].mediaUrl), class: 'left' },
                { value: this.formatStatusCount('fa-comments', twitterData[i].repliesCount), class: 'right' },
                { value: this.formatStatusCount('fa-retweet', twitterData[i].retweetsCount), class: 'right' },
                { value: this.formatStatusCount('fa-heart', twitterData[i].favoriteCount), class: 'right' }
            ]);
        }

        this.twitterReportStatusData = {
            cols: [
                { value: 'Data', class: 'p-l-0 left', width: '110px' },
                { value: 'Post', class: 'left' },
                { value: 'Replies', class: 'right', width: '80px' },
                { value: 'Rt\'s', class: 'right', width: '80px' },
                { value: 'Curtir', class: 'right', width: '70px' }
            ],
            rows: dataTwitterReports
        };

        if (length >= this.postsLimitPerCall) {
            this.loadMorePosts = true;
        }
    }

    getMorePosts() {

        this.loadingMorePosts = true;

        this.twitterService.getMorePosts(this.profile.idProfile, this.dateFrom.getTime(), this.dateUntil.getTime(), this.postsOffset)
            .subscribe(
                result => this.updateTwitterReportStatus(result),
                error => console.log(error)
            );
    }

    updateTwitterReportStatus(twitterReportStatus: any) {

        this.postsOffset = this.postsOffset + twitterReportStatus.length;
        const dataTwitterReports = [];

        for (let i = 0; i < twitterReportStatus.length; i++) {
            this.twitterReportStatusData.rows.push([
                { value: this.formatDateReportStatus(twitterReportStatus[i].createdAt), class: 'left' },
                { value: this.formatTextReportStatus(twitterReportStatus[i].text, twitterReportStatus[i].mediaUrl), class: 'left' },
                { value: this.formatStatusCount('fa-comments', twitterReportStatus[i].repliesCount), class: 'right' },
                { value: this.formatStatusCount('fa-retweet', twitterReportStatus[i].retweetsCount), class: 'right' },
                { value: this.formatStatusCount('fa-heart', twitterReportStatus[i].favoriteCount), class: 'right' }
            ]);
        }

        if (twitterReportStatus.length >= this.postsLimitPerCall) {
            this.loadMorePosts = true;
        } else {
            this.loadMorePosts = false;
        }

        this.loadingMorePosts = false;
    }

    totalFollowers() {
        this.totalFollowersAside = {
            icon: {
                icon: 'fa-users',
                color: '#00d6f6'
            },
            title: 'Total followers',
            value: this.data.totalOfFollowers
        };
    }

    bestAmoutOfFollowers() {
        this.bestAmoutOfFollowersAside = {
            icon: {
                icon: 'fa-area-chart',
                color: '#ffd71e'
            },
            title: 'Saldo no período',
            value: this.data.followersBalance,
            text: 'The day with more followers was ' + this.formatDate(this.data.dayWithMoreFollowers.date)
                + ' with '
                + this.abbreviateNumberPipe.transform(this.data.dayWithMoreFollowers.value) + ' new followers.'
        };
    }

    growthRate() {
        this.growthRateAside = {
            icon: {
                icon: 'fa-arrow-circle-up',
                color: '#20ffa3'
            },
            title: 'Índice de crescimento',
            value: this.data.followersGrowthRate + '%',
            text: 'The variation in the period was ' + this.data.followersGrowthRate
                + '% which represents an increase of '
                + this.abbreviateNumberPipe.transform(this.data.followersBalance) + ' followers.'
        };
    }

    averageTweets() {
        this.averageTweetsAside = {
            icon: {
                icon: 'fa-area-chart',
                color: '#ffd71e'
            },
            title: 'Média diária de Tweets',
            value: this.data.averageTweetsSentPerDay,
            text: this.data.averageTweetsSentPerDay + ' tweets were published daily, on an average.'
        };
    }

    // growthRate() {
    //     this.growthRateAside = {
    //         icon: {
    //             icon: 'fa-arrow-circle-up',
    //             color: '#20ffa3'
    //         },
    //         title: 'Índice de crescimento',
    //         value: this.data.followersGrowthRate + '%',
    //         text: 'A variação no período foi de ' + this.data.followersGrowthRate
    //             + '% que representa um aumento de '
    //             + this.abbreviateNumberPipe.transform(this.data.followersBalance) + ' seguidores.'
    //     };
    // }

    // averageTweets() {
    //     this.averageTweetsAside = {
    //         icon: {
    //             icon: 'fa-area-chart',
    //             color: '#ffd71e'
    //         },
    //         title: 'Média diária de Tweets',
    //         value: this.data.averageTweetsSentPerDay,
    //         text: 'Em média, foram publicados ' + this.data.averageTweetsSentPerDay
    //             + ' tweets por dia neste perfil.'
    //     };
    // }

    averageFollowersOfFollowers() {
        this.averageFollowersOfFollowersAside = {
            icon: {
                icon: 'fa-bar-chart',
                color: '#00d6f6'
            },
            title: 'Média de seguidores',
            value: this.abbreviateNumberPipe.transform(this.data.averageFollowersOfFollowers)
        };
    }

    potentialReach() {
        this.potentialReachAside = {
            icon: {
                icon: ' fa-bullhorn',
                color: '#f6cb00'
            },
            title: 'Alcance Potencial',
            value: this.data.potentialReach,
            text: this.data.retweetCount +
                ' retweets were registered by all the audience engaged during the selected time, reaching potentially more than '
                + this.data.potentialReach + ' users.'
            // ' retweets foram gerados pelo total do público envolvido com o seu perfil no período, o que gerou um alcance potencial de '
            // + this.data.potentialReach + ' usuários.'
        };
    }

    followersEngagement() {
        this.followersEngagementAside = {
            icon: {
                icon: 'fa-hand-o-up',
                color: '#00d6f6'
            },
            title: 'Índice de engajamento',
            value: this.data.followersEngagement,
            text: this.data.followersEngagedCount +
                ' of your followers interacted with your content through replies, mentions or retweets on the last ' +
                this.period + ' days, representing a engagement rate of ' + this.data.followersEngagementPercentage + '%.'
            // ' dos seus seguidores interagiram via menções, respostas ou retweets com o seu conteúdo nos últimos ' +
            // this.period + ' dias, o que representa um índice de  ' + this.data.followersEngagementPercentage + '% de engajamento.'
        };
    }

    followersActivityBiggerPeriod() {
        this.followersActivityBiggerPeriodAside = {
            icon: {
                icon: 'fa-calendar',
                color: '#f6cb00'
            },
            title: 'Média de atividades',
            value: this.data.followersActivityBiggerPeriod,
            text: this.data.followersActivityBiggerPercentage +
                '% of your followers tweeted on an average each ' + this.data.followersActivityBiggerPeriod + '.'
            // '% dos seus seguidores tweetam em média a cada ' + this.data.followersActivityBiggerPeriod + '.'
        };
    }

    tweetsPeakHour() {
        const biggerValue = this.data.tweetsPerHour[this.biggestHour];
        const percent: number = biggerValue / (this.totalTweetsPerHour || 1) * 100;

        this.tweetsPeakHourFooter = {
            icon: {
                icon: 'fa-clock-o',
                color: '#f6cb00'
            },
            title: 'Horário com mais Tweets',
            value: this.formatHour(this.biggestHour) + ' at ' + this.formatHour(this.biggestHour + 1),
            smallText: '(' + percent.toFixed(2) + '%)',
            text: percent.toFixed(2) +
                '% of your tweets were posted between ' + this.formatHour(this.biggestHour) + ' and '
                + this.formatHour(this.biggestHour + 1)
        };

        // this.tweetsPeakHourFooter = {
        //     icon: {
        //         icon: 'fa-clock-o',
        //         color: '#f6cb00'
        //     },
        //     title: 'Horário com mais Tweets',
        //     value: this.formatHour(this.biggestHour) + ' às ' + this.formatHour(this.biggestHour + 1),
        //     smallText: '(' + percent.toFixed(2) + '%)',
        //     text: percent.toFixed(2) +
        //         '% dos seus tweets estão concentrados entre ' + this.formatHour(this.biggestHour) + ' e '
        //         + this.formatHour(this.biggestHour + 1) + 'hrs'
        // };
    }

    tweetsPeakDay() {
        const biggerValue = this.data.tweetsPerWeekDay[this.biggestDay];
        const percent: number = biggerValue / (this.totalTweetsPerDay || 1) * 100;

        this.tweetsPeakDayFooter = {
            icon: {
                icon: 'fa-calendar',
                color: '#f6cb00'
            },
            title: 'Dia com maior alcance',
            value: this.weekDaysFull[this.biggestDay],
            smallText: '(' + percent.toFixed(2) + '%)',
            text: percent.toFixed(2) + '% of your tweets were posted on ' + this.weekDaysFull[this.biggestDay]
            // text: percent.toFixed(2) + '% dos seus tweets estão concentrados ' + this.weekDaysFull[this.biggestDay]
        };
    }

    favorites() {
        const c = { value: 0 };
        const total = this.data.favoritesByDay.reduce((a, b) => ({ value: a.value + b.value }), c).value;

        this.favoritesFooter = {
            icon: {
                icon: 'fa-heart',
                color: '#00d6f6'
            },
            title: 'Curtir',
            value: total,
            text: 'Your tweets registered ' + this.abbreviateNumberPipe.transform(total) + ' likes during the selected time.'
            // text: 'Seus tweets foram curtidos ' + this.abbreviateNumberPipe.transform(total) + ' vezes no período.'
        };
    }

    retweets() {
        const c = { value: 0 };
        const total = this.data.retweetsByDay.reduce((a, b) => ({ value: a.value + b.value }), c).value;

        this.retweetsFooter = {
            icon: {
                icon: 'fa-retweet',
                color: '#00d6f6'
            },
            title: 'Retweets',
            value: total,
            text: 'Your tweets were retweeted ' + this.abbreviateNumberPipe.transform(total) + ' times during the selected time.'
            // text: 'Seus tweets foram retweetados ' + this.abbreviateNumberPipe.transform(total) + ' vezes no período.'
        };
    }

    replies() {
        const c = { value: 0 };
        const total = this.data.repliesByDay.reduce((a, b) => ({ value: a.value + b.value }), c).value;

        this.repliesFooter = {
            icon: {
                icon: 'fa-comments',
                color: '#00d6f6'
            },
            title: 'Replies',
            value: total,
            text: 'Your tweets registered ' + this.abbreviateNumberPipe.transform(total) + ' replies during the selected time.'
            // text: 'Seus tweets foram respondidos ' + this.abbreviateNumberPipe.transform(total) + ' vezes no período.'
        };
    }

    mentions() {
        const c = { value: 0 };
        const total = this.data.mentionsByDay.reduce((a, b) => ({ value: a.value + b.value }), c).value;

        this.mentionsFooter = {
            icon: {
                icon: 'fa-clock-o',
                color: '#00d6f6'
            },
            title: 'Mentions',
            value: total,
            text: 'You were mentioned ' + this.abbreviateNumberPipe.transform(total) + ' times during the selected time.'
            // text: 'Você foi mencionado ' + this.abbreviateNumberPipe.transform(total) + ' vezes no período.'
        };
    }

    perTypeOfTweet() {
        const c = { value: 0 };
        let type: string;
        const textTotal = this.data.tweetsSentWithText.reduce((a, b) => ({ value: a.value + b.value }), c);
        const mediaTotal = this.data.tweetsSentWithPhotoVideo.reduce((a, b) => ({ value: a.value + b.value }), c);
        const linkTotal = this.data.tweetsSentWithLink.reduce((a, b) => ({ value: a.value + b.value }), c);
        const total = textTotal.value + mediaTotal.value + linkTotal.value;
        const percent = (Math.max(textTotal.value, mediaTotal.value, linkTotal.value) / (total || 1) * 100).toFixed(2);

        if (textTotal >= mediaTotal && textTotal >= linkTotal) {
            type = 'text';
        } else if (mediaTotal >= textTotal && mediaTotal >= linkTotal) {
            type = 'photo/video';
        } else {
            type = 'link';
        }

        const legend = [
            { icon: { icon: 'fa-circle', color: '#01d6f8' }, legend: 'Text', value: textTotal.value },
            { icon: { icon: 'fa-circle', color: '#01aed9' }, legend: 'Photo/video', value: mediaTotal.value },
            { icon: { icon: 'fa-circle', color: '#0085be' }, legend: 'Link', value: linkTotal.value }
        ];

        this.perTypeOfTweetAsidePie = {
            icon: {
                icon: 'fa-area-chart',
                color: '#00d6f6'
            },
            chart: {
                data: [
                    {
                        'label': 'Text',
                        'value': this.abbreviateNumberPipe.transform(textTotal.value),
                        'color': '#01d6f8'
                    },
                    {
                        'label': 'Photo/video',
                        'value': this.abbreviateNumberPipe.transform(mediaTotal.value),
                        'color': '#01aed9'
                    },
                    {
                        'label': 'Link',
                        'value': this.abbreviateNumberPipe.transform(linkTotal.value),
                        'color': '#0085be'
                    }
                ],
                height: 60,
                width: 60,
                isDonut: true,
                legend: legend
            },
            title: 'Divisão por tipo de Tweet',
            text: 'Tweets with ' + type + 'were the most published ones during the selected time, '
                + 'representing ' + percent + '% of the full amount.',
            // text: 'O tipo de tweets com maior volume durante o período são Tweets com ' + type
            //     + ', que representam ' + percent + '% dos tweets.'
            orientation: 'left'
        };
    }

    perSex() {
        const legend = [
            { icon: { icon: 'fa-male', color: '#00d6f6' }, value: this.data.menFollowersPerc + '%' },
            { icon: { icon: 'fa-female', color: '#f74e7e' }, value: this.data.womenFollowersPerc + '%' }
        ];

        this.perSexAsidePie = {
            icon: {
                icon: 'fa-venus-mars',
                color: '#f6cb00'
            },
            chart: {
                data: [
                    {
                        'label': 'Men',
                        'value': this.data.menFollowersPerc,
                        'color': '#00d6f6'
                    },
                    {
                        'label': 'Women',
                        'value': this.data.womenFollowersPerc,
                        'color': '#f74e7e'
                    }
                ],
                height: 60,
                width: 60,
                isDonut: true,
                legend: legend
            },
            title: 'Divisão do público',
            orientation: 'left'
        };
    }
}
