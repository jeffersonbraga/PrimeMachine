import { DatePipe, DecimalPipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { AbbreviateNumberPipe } from 'core/pipes/abbreviateNumber.pipe';
import { ControlService } from 'core/services/control/control.service';
import { FacebookService } from 'core/services/facebook/facebook.service';
import { NotificationService } from 'core/services/notification/notification.service';
import { PdfService } from 'core/services/pdf/pdf.service';
import { IAsidePieChart, IAsidePieChartDouble, IAsideProgressBar, IAsideTextIcon } from 'modelo/complements/Complements';
import { Notification } from 'modelo/notification/Notification';

@Component({
	selector: 'facebook-report-content',
	templateUrl: './facebook-report-content.component.html',
	styleUrls: ['./facebook-report-content.component.css']
})
export class FacebookReportContentComponent implements OnInit {

	@Input() dateFrom: Date;
	@Input() dateUntil: Date;
	@Input() profile;
	@Input() reportFacebookContent: any;
	@Input() period;
	account;

	numberOfPostsData;
	reactionsPerDayData;
	commentsPerDayData;
	sharesPerDayData;
	postsReachData;
	postsImpressionsData;
	postsConsumptionsTypeData;
	pageNegativeFeedbacksTypeData;
	videoViewsTypeData;
	videoViewsData;
	videoViewTimeByAgeGenderData;

	averagePostsPerDayAside: IAsideTextIcon;
	averagePostsReachAside: IAsideTextIcon;
	averagePostsInteractionsAside: IAsideTextIcon;
	postsConsumptionsTotalAside: IAsideTextIcon;

	likesFooter: IAsideTextIcon;
	loveFooter: IAsideTextIcon;
	hahaFooter: IAsideTextIcon;
	wowFooter: IAsideTextIcon;
	sorryFooter: IAsideTextIcon;
	angerFooter: IAsideTextIcon;
	commentsFooter: IAsideTextIcon;
	sharesFooter: IAsideTextIcon;
	averageReachFooter: IAsideTextIcon;
	averageImpressionsFooter: IAsideTextIcon;
	majorTotalViewsDayFooter: IAsideTextIcon;
	majorAgeGroupVideoViewTimeFooter: IAsideTextIcon;

	totalReachPieDouble: IAsidePieChartDouble;
	totalImpressionsPieDouble: IAsidePieChartDouble;
	videoViewsByTypePieDouble: IAsidePieChartDouble;

	videoTotalViewsProgressBar: IAsideProgressBar;
	videoCompleteViewsProgressBar: IAsideProgressBar;
	videoPartialViewsProgressBar: IAsideProgressBar;

	videoViewTimeGrowth: IAsideTextIcon;
	videoRepeatViewsGrowth: IAsideTextIcon;

	womenMenVideoViewsTimePie: IAsidePieChart;

	legendsReactionsPerDay = {
		legends: true,
		color: ["#00d6f6", "#f82d4e", "#f6cb00", "#ad43ff", "#00f6bf", "#ff7800"]
	};

	legendsType1 = {
		legends: true,
		color: ["#00d6f6"]
	};

	legendsType2 = {
		legends: true,
		color: ["#00d6f6", "#00aed9"]
	};

	legendsAboutYourFans = {
		legends: true,
		color: ['#00d6f6', '#f74e7e', '#c4c4c4']
	};

	colorsReactionsPerDay = ["#00d6f6", "#f82d4e", "#f6cb00", "#ad43ff", "#00f6bf", "#ff7800"];

	constructor(private facebookService: FacebookService,
				private controlService: ControlService,
				private datePipe: DatePipe,
				private decimalPipe: DecimalPipe,
				private abbreviateNumberPipe: AbbreviateNumberPipe,
				private pdfService: PdfService,
				private notificationService: NotificationService) { }

	ngOnInit() {
		this.mountCharts();
		this.account = this.controlService.account;
	}

	mountCharts() {
		
		this.numberOfPosts();
		this.averagePostsPerDay();
		this.averagePostsReach();
		this.averagePostsInteractions();
		this.reactionsPerDay();
		this.likesTotal();
		this.loveTotal();
		this.hahaTotal();
		this.wowTotal();
		this.sorryTotal();
		this.angerTotal();
		this.commentsPerDay();
		this.commentsTotal();
		this.sharesPerDay();
		this.sharesTotal();
		this.postsReach();
		this.totalReach();
		this.averageReach();
		this.postsImpressions();
		this.totalImpressions();
		this.averageImpressions();
		this.postsConsumptionsType();
		this.postsConsumptionsTotal();
		this.pageNegativeFeedbacksType();
		this.videoTotalViews();
		this.videoCompleteViews();
		this.videoPartialViews();
		this.videoViewTime();
		this.videoRepeatViews();
		this.videoViews();
		this.videoViewsByType();
		this.majorTotalViewsDay();
		this.videoViewTimeByAgeGender();
		this.majorAgeGroupVideoViewTime();
		this.womenMenVideoViewsTime();
	}

	getXLS(){
		this.facebookService.getContentReportXLS(this.profile.idProfile, this.dateFrom.getTime(), this.dateUntil.getTime())
			.subscribe(
				result => this.downloadXLS(result),
				error => console.log(error)
			);
	}

	downloadXLS(result){
		window.open(this.controlService.context_app + 'woopsocial/get_xls?file=' + result.fileName + '&filename=' + result.formattedFileName + '&idAccount=' + result.idAccount);
	}

	getPDF() {
		
		let notification: Notification = {
			account: this.account,
			profile: this.profile,
			date: new Date(),
			message: this.account.name + " created a PDF report for channel " + this.profile.screenName + ".",
			// message: this.account.name + " gerou um relatório em PDF do canal " + this.profile.screenName + ".",
			color: this.notificationService.COLOR_SUCCESS,
			action: '',
			unread: true
		};

		this.notificationService.addNotifications(notification)
		.subscribe(
			result => console.log(result),
			error => console.log(error)
		);;
		window.print();
	}

	fromMilisecondsToHours(milisecondsTime) {
		return Number(((milisecondsTime / 1000) / 60) / 60);
	}

	fromMilisecondsToMinutes(milisecondsTime) {
		return Number(((milisecondsTime / 1000) / 60));
	}

	formatDate(date, format) {
		date = new Date(date * 1000);
		return this.datePipe.transform(date, format);
	}

	getIconUrl(type) {
		
		if (type == 'love') {
			return '/assets/img/reactions/reaction-love.svg';
		} else if (type == 'likes') {
			return '/assets/img/reactions/reaction-like.svg';
		} else if (type == 'haha') {
			return '/assets/img/reactions/reaction-haha.svg';
		} else if (type == 'wow') {
			return '/assets/img/reactions/reaction-wow.svg';
		} else if (type == 'sorry') {
			return '/assets/img/reactions/reaction-sad.svg';
		} else if (type == 'anger') {
			return '/assets/img/reactions/reaction-angry.svg';
		}

	}

	getPageNegativeFeedbackName(negativeFeedbackType) {

		if(negativeFeedbackType == 'page_negative_feedback_hide_clicks')  {
			return 'Hide post';
			// return 'Ocultar publicação';
		} else if(negativeFeedbackType == 'page_negative_feedback_hide_all_clicks') {
			return 'Hide all from this page';
			// return 'Ocultar tudo dessa página';
		} else if(negativeFeedbackType == 'page_negative_feedback_report_spam_clicks') {
			return 'Report spam';
			// return 'Denunciar como spam';
		} else {
			return '';
		}

	}

	getPostsConsumptionsName(consumptionType) {

		if(consumptionType == 'pageConsumptionsPhotoView') {
			return "Photo click";
			// return "Clique em foto";
		} else if(consumptionType == 'pageConsumptionsLinkClicks') {
			return "Links click";
			// return "Cliques em links";
		} else if(consumptionType == 'pageConsumptionsVideoPlay') {
			return "Video views";
			// return "Visualizações em vídeos";
		} else if(consumptionType == 'pageConsumptionsOtherClicks') {
			return "Others";
			// return "Outros";
		} else {
			return "";
		}
	}

	formatAgeGroupDisplay(initialAgeGroup, endAgeGroup) {
		if (Number(initialAgeGroup) < 65) {
			return initialAgeGroup + '-' + endAgeGroup;
		} else {
			return initialAgeGroup + '+';
		}
	}

	womenMenVideoViewsTime() {
		
		let legend = [
			{icon: {icon: 'fa-male', color:'#00d6f6'}, value: this.reportFacebookContent.maleVideoViewTimeUsersPerc +'%'},
			{icon: {icon: 'fa-female', color:'#f74e7e'}, value: this.reportFacebookContent.femaleVideoViewTimeUsersPerc +'%'}
		];

		this.womenMenVideoViewsTimePie = {
			icon: {
				icon: 'fa-venus-mars',
				color: '#f6cb00'
			},
			chart: {
				data : [
					{ 
						"label": "Men",
						"value" : this.reportFacebookContent.maleVideoViewTimeUsersPerc, 
						"color": "#00d6f6"
					}, 
					{ 
						"label": "Women",
						"value" : this.reportFacebookContent.femaleVideoViewTimeUsersPerc, 
						"color": "#f74e7e"
					}
				],
				height: 60,
				width: 60,
				isDonut: true,
				legend: legend
			},
			title: 'Divisão do público',
			orientation: 'center'
		}
	}

	majorAgeGroupVideoViewTime() {

		this.majorAgeGroupVideoViewTimeFooter = {
			icon: {
				icon: 'fa-arrow-circle-up',
				color: '#ffd71e'
			},
			title: 'Maior faixa etária',
			value: this.reportFacebookContent.majorAgeGroupVideoViewTimeUsers.initialAgeGroup + ' a ' + this.reportFacebookContent.majorAgeGroupVideoViewTimeUsers.endAgeGroup,
			text: 'Most of your video’s audience is between ' +
			this.reportFacebookContent.majorAgeGroupVideoViewTimeUsers.initialAgeGroup + ' and '
			+ this.reportFacebookContent.majorAgeGroupVideoViewTimeUsers.endAgeGroup + ' years old, '
			+ 'representing ' + this.reportFacebookContent.majorAgeGroupVideoViewTimeUsers.percentage + '% of the full amount.'
		}
	}

	// majorAgeGroupVideoViewTime() {

	// 	this.majorAgeGroupVideoViewTimeFooter = {
	// 		icon: {
	// 			icon: 'fa-arrow-circle-up',
	// 			color: '#ffd71e'
	// 		},
	// 		title: 'Maior faixa etária',
	// 		value: this.reportFacebookContent.majorAgeGroupVideoViewTimeUsers.initialAgeGroup + ' a ' + this.reportFacebookContent.majorAgeGroupVideoViewTimeUsers.endAgeGroup,
	// 		text: 'A maior parte do público que consumiu seus vídeos tem entre ' +
	// 		this.reportFacebookContent.majorAgeGroupVideoViewTimeUsers.initialAgeGroup + ' e '
	// 		+ this.reportFacebookContent.majorAgeGroupVideoViewTimeUsers.endAgeGroup + ' anos, '
	// 		+ 'representando ' + this.reportFacebookContent.majorAgeGroupVideoViewTimeUsers.percentage + '% do total.'
	// 	}
	// }

	videoViewTimeByAgeGender() {
		let male = this.reportFacebookContent.videoViewTimeByMaleUsers.map(
			fav => {
				return {
					x: this.formatAgeGroupDisplay(fav.initialAgeGroup, fav.endAgeGroup),
					y: Math.round(this.fromMilisecondsToMinutes(fav.value)),
					color: '#00d6f6'
				}
			});

		male.sort((a, b) => a.x > b.x ? 1 : 0);

		let female = this.reportFacebookContent.videoViewTimeByFemaleUsers.map(
			fav => {
				return {
					x: this.formatAgeGroupDisplay(fav.initialAgeGroup, fav.endAgeGroup),
					y: Math.round(this.fromMilisecondsToMinutes(fav.value)),
					color: '#f74e7e'
				}
			});

		female.sort((a, b) => a.x > b.x ? 1 : 0);

		let unidentified = this.reportFacebookContent.videoViewTimeByUnidentifiedUsers.map(
			fav => {
				return {
					x: this.formatAgeGroupDisplay(fav.initialAgeGroup, fav.endAgeGroup),
					y: Math.round(this.fromMilisecondsToMinutes(fav.value)),
					color: '#c4c4c4'
				}
			});

		unidentified.sort((a, b) => a.x > b.x ? 1 : 0);

		this.videoViewTimeByAgeGenderData = [{
			"key": "Male",
			"values": male
		}, {
			"key": "Female",
			"values": female
		}, {
			"key": "Unidentified",
			"values": unidentified
		}
		];
	}

	majorTotalViewsDay() {

		this.majorTotalViewsDayFooter = {
			icon: {
				icon: 'fa-arrow-circle-up',
				color: '#00d6f6'
			},
			title: 'Dia com mais visualizações',
			value: this.formatDate(this.reportFacebookContent.majorTotalViewsDay.date, 'MM/dd'),
			text: 'On ' + 
				this.formatDate(this.reportFacebookContent.majorTotalViewsDay.date, 'MM/dd') 
				+ ' the higher number of views was registered: ' 
				+ this.abbreviateNumberPipe.transform(this.reportFacebookContent.majorTotalViewsDay.sum)
				+ ' views.'
		}
	}

	// majorTotalViewsDay() {

	// 	this.majorTotalViewsDayFooter = {
	// 		icon: {
	// 			icon: 'fa-arrow-circle-up',
	// 			color: '#00d6f6'
	// 		},
	// 		title: 'Dia com mais visualizações',
	// 		value: this.formatDate(this.reportFacebookContent.majorTotalViewsDay.date, 'dd/MM'),
	// 		text: 'Dia ' + 
	// 			this.formatDate(this.reportFacebookContent.majorTotalViewsDay.date, 'dd/MM') 
	// 			+ ' registrou o maior número de visualizações, com ' 
	// 			+ this.abbreviateNumberPipe.transform(this.reportFacebookContent.majorTotalViewsDay.sum)
	// 			+ ' visualizações.'
	// 	}
	// }

	videoRepeatViews() {

		this.videoRepeatViewsGrowth = {
			icon: {
				icon: 'fa-repeat',
				color: '#c4c4c4'
			},
			title: 'total repeats',
			value: this.reportFacebookContent.videoRepeatViews,
			smallText: this.reportFacebookContent.videoRepeatViewsPerformance + '%',
			hasGrown: this.reportFacebookContent.videoRepeatViewsPerformance >= 0 ? true : false
		}
	}

	videoViewTime() {

		this.videoViewTimeGrowth = {
			icon: {
				icon: 'fa-clock-o',
				color: '#c4c4c4'
			},
			title: 'total view time',
			value: Math.round(this.fromMilisecondsToHours(this.reportFacebookContent.videoViewTime)) + "H",
			smallText: this.reportFacebookContent.videoViewTimePerformance + '%',
			hasGrown: this.reportFacebookContent.videoViewTimePerformance >= 0 ? true : false
		}
	}

	videoPartialViews() {

		this.videoPartialViewsProgressBar = {
			title: 'Partial views',
			smallText: this.reportFacebookContent.videoPartialViewsPerformance + '%',
			value: this.reportFacebookContent.videoPartialViews,
			hasGrown: this.reportFacebookContent.videoPartialViewsPerformance >= 0 ? true : false,
			leftContent: {
				title: 'Organic',
				value: this.reportFacebookContent.videoPartialOrganicViews,
				percentage: this.reportFacebookContent.videoPartialOrganicViewsPercentage,
				smallText: this.reportFacebookContent.videoPartialOrganicViewsPercentage + '%',
				color: '#00d6f6'
			},
			rightContent: {
				title: 'Paid',
				value: this.reportFacebookContent.videoPartialPaidViews,
				percentage: this.reportFacebookContent.videoPartialPaidViewsPercentage,
				smallText: this.reportFacebookContent.videoPartialPaidViewsPercentage+ '%',
				color: '#00aed9'
			}
		}
	}

	videoCompleteViews() {

		this.videoCompleteViewsProgressBar = {
			title: 'Total view',
			// title: 'Visualizações totais',
			smallText: this.reportFacebookContent.videoCompleteViewsPerformance + '%',
			value: this.reportFacebookContent.videoCompleteViews,
			hasGrown: this.reportFacebookContent.videoCompleteViewsPerformance >= 0 ? true : false,
			leftContent: {
				title: 'Organic',
				value: this.reportFacebookContent.videoCompleteOrganicViews,
				percentage: this.reportFacebookContent.videoCompleteOrganicViewsPercentage,
				smallText: this.reportFacebookContent.videoCompleteOrganicViewsPercentage + '%',
				color: '#00d6f6'
			},
			rightContent: {
				title: 'Paid',
				value: this.reportFacebookContent.videoCompletePaidViews,
				percentage: this.reportFacebookContent.videoCompletePaidViewsPercentage,
				smallText: this.reportFacebookContent.videoCompletePaidViewsPercentage+ '%',
				color: '#00aed9'
			}
		}

	}

	videoTotalViews() {

		this.videoTotalViewsProgressBar = {
			title: 'Total view',
			// title: 'Total de visualizações',
			smallText: this.reportFacebookContent.videoTotalViewsPerformance + '%',
			value: this.reportFacebookContent.videoTotalViews,
			hasGrown: this.reportFacebookContent.videoTotalViewsPerformance >= 0 ? true : false,
			leftContent: {
				title: 'Organic',
				value: this.reportFacebookContent.videoTotalOrganicViews,
				percentage: this.reportFacebookContent.videoTotalOrganicViewsPercentage,
				smallText: this.reportFacebookContent.videoTotalOrganicViewsPercentage + '%',
				color: '#00d6f6'
			},
			rightContent: {
				title: 'Paid',
				value: this.reportFacebookContent.videoTotalPaidViews,
				percentage: this.reportFacebookContent.videoTotalPaidViewsPercentage,
				smallText: this.reportFacebookContent.videoTotalPaidViewsPercentage+ '%',
				color: '#00aed9'
			}
		}
	}

	totalImpressions() {

		this.totalImpressionsPieDouble = {
			icon: {
				icon: 'fa-eye',
				color: '#00d6f6'
			},
			chart: {
				data : [
					{ 
						"label": "Organic",
						"value" : this.reportFacebookContent.postsImpressionsOrganicCount, 
						"color": "#00d6f6"
					}, 
					{ 
						"label": "Paid",
						"value" : this.reportFacebookContent.postsImpressionsPaidCount, 
						"color": "#00aed9"
					}
				],
				height: 70,
				width: 70,
				isDonut: true,
			},
			title: 'Total de impressões',
			leftContent: {
				title: 'Organic',
				value: this.reportFacebookContent.postsImpressionsOrganicCount,
				smallText: '(' + this.reportFacebookContent.postsImpressionsOrganicPercentage + '%)'
			},
			rightContent: {
				title: 'Paid',
				value: this.reportFacebookContent.postsImpressionsPaidCount,
				smallText: '(' + this.reportFacebookContent.postsImpressionsPaidPercentage + '%)'
			}
		}

	}

	videoViewsByType() {
		
		this.videoViewsByTypePieDouble = {
			icon: {
				icon: 'fa-play-circle',
				color: '#00d6f6'
			},
			chart: {
				data : [
					{ 
						"label": "Autoplay",
						"value" : this.reportFacebookContent.videoAutoPlayedViews, 
						"color": "#00d6f6"
					}, 
					{ 
						"label": "Click",
						"value" : this.reportFacebookContent.videoClickToPlayViews, 
						"color": "#f6cb00"
					}
				],
				height: 70,
				width: 70,
				isDonut: true,
			},
			title: 'Tipo de visualização',
			leftContent: {
				title: 'Autoplay',
				value: this.reportFacebookContent.videoAutoPlayedViews,
				smallText: '(' + this.reportFacebookContent.videoAutoPlayedViewsPercentage + '%)'
			},
			rightContent: {
				title: 'Clique',
				value: this.reportFacebookContent.videoClickToPlayViews,
				smallText: '(' + this.reportFacebookContent.videoClickToPlayViewsPercentage + '%)'
			}
		}
	}

	totalReach() {
		
		this.totalReachPieDouble = {
			icon: {
				icon: 'fa-users',
				color: '#00d6f6'
			},
			chart: {
				data : [
					{ 
						"label": "Organic",
						"value" : this.reportFacebookContent.postsReachOrganicCount, 
						"color": "#00d6f6"
					}, 
					{ 
						"label": "Paid",
						"value" : this.reportFacebookContent.postsReachPaidCount, 
						"color": "#00aed9"
					}
				],
				height: 70,
				width: 70,
				isDonut: true,
			},
			title: 'Total de alcance',
			leftContent: {
				title: 'Organic',
				value: this.reportFacebookContent.postsReachOrganicCount,
				smallText: '(' + this.reportFacebookContent.postsReachOrganicPercentage + '%)'
			},
			rightContent: {
				title: 'Paid',
				value: this.reportFacebookContent.postsReachPaidCount,
				smallText: '(' + this.reportFacebookContent.postsReachPaidPercentage + '%)'
			}
		}
	}

	postsImpressions() {

		this.postsImpressionsData = [{
			"key": "Organic",
			"values": this.reportFacebookContent.postsImpressionsOrganicByDay.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
			}, {
			"key": "Paid",
			"values": this.reportFacebookContent.postsImpressionsPaidByDay.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
			}

		];
	}

	videoViews() {

		this.videoViewsData = [{
			"key": "Organic",
			"values": this.reportFacebookContent.videoTotalOrganicViewsPerDay.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
			}, {
			"key": "Paid",
			"values": this.reportFacebookContent.videoTotalPaidViewsPerDay.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
			}
		];
	}

	postsReach() {

		this.postsReachData = [{
			"key": "Organic",
			"values": this.reportFacebookContent.postsReachOrganicByDay.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
			}, {
			"key": "Paid",
			"values": this.reportFacebookContent.postsReachPaidByDay.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
			}

		];
	}

	sharesPerDay() {
		
		this.sharesPerDayData = [{
			"key": 'Shares',
			"values": this.reportFacebookContent.sharesPerDay.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
		}];
	}

	commentsPerDay() {

		this.commentsPerDayData = [{
			"key": 'Comments',
			"values": this.reportFacebookContent.commentsPerDay.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
		}];
	}

	averageImpressions() {

		this.averageImpressionsFooter = {
			icon: {
				icon: 'fa-bar-chart',
				color: '#00d6f6'
			},
			title: 'média diária de impressões',
			value: this.reportFacebookContent.averageImpressionsOfPostsPerDay
		}
	}

	averageReach() {

		this.averageReachFooter = {
			icon: {
				icon: 'fa-bar-chart',
				color: '#00d6f6'
			},
			title: 'média diária de alcance',
			value: this.reportFacebookContent.averageReachOfPostsPerDay
		}
	}
	
	sharesTotal() {

		this.sharesFooter = {
			icon: {
				icon: 'fa-share',
				color: '#00d6f6'
			},
			title: 'shares',
			value: this.reportFacebookContent.totalShares,
			text: 'Your posts registered more shares on ' + this.formatDate(this.reportFacebookContent.majorSharesDay.date, 'MM/dd') 
				+ ' with ' 
				+ this.abbreviateNumberPipe.transform(this.reportFacebookContent.majorSharesDay.sum) 
				+ ' shares.'
		}
	}

	// sharesTotal() {

	// 	this.sharesFooter = {
	// 		icon: {
	// 			icon: 'fa-share',
	// 			color: '#00d6f6'
	// 		},
	// 		title: 'compartilhamentos',
	// 		value: this.reportFacebookContent.totalShares,
	// 		text: 'Dia ' + this.formatDate(this.reportFacebookContent.majorSharesDay.date, 'dd/MM') 
	// 			+ ' registrou o maior índice de compartilhamentos, com ' 
	// 			+ this.abbreviateNumberPipe.transform(this.reportFacebookContent.majorSharesDay.sum) 
	// 			+ ' compartilhamentos de suas postagens.'
	// 	}
	// }

	commentsTotal() {

		this.commentsFooter = {
			icon: {
				icon: 'fa-comments',
				color: '#00d6f6'
			},
			title: 'comentários',
			value: this.reportFacebookContent.totalComments,
			text: 'Your posts registered more comments on ' + this.formatDate(this.reportFacebookContent.majorCommentsDay.date, 'MM/dd') 
				+ ' with ' 
				+ this.abbreviateNumberPipe.transform(this.reportFacebookContent.majorCommentsDay.sum) 
				+ ' comments.'
		}
	}

	// commentsTotal() {

	// 	this.commentsFooter = {
	// 		icon: {
	// 			icon: 'fa-comments',
	// 			color: '#00d6f6'
	// 		},
	// 		title: 'comentários',
	// 		value: this.reportFacebookContent.totalComments,
	// 		text: 'Dia ' + this.formatDate(this.reportFacebookContent.majorCommentsDay.date, 'dd/MM') 
	// 			+ ' registrou o maior índice de comentários, com ' 
	// 			+ this.abbreviateNumberPipe.transform(this.reportFacebookContent.majorCommentsDay.sum) 
	// 			+ ' comentários feitos em suas postagens.'
	// 	}
	// }

	angerTotal() {

		this.angerFooter = {
			title: 'grr',
			value: this.reportFacebookContent.totalAnger, 
			smallText: '(' + this.reportFacebookContent.angerPercentage + '%)',
			iconUrl: this.getIconUrl('anger')
		}
	}

	sorryTotal() {

		this.sorryFooter = {
			title: 'triste',
			value: this.reportFacebookContent.totalSorry, 
			smallText: '(' + this.reportFacebookContent.sorryPercentage + '%)',
			iconUrl: this.getIconUrl('sorry')
		}
	}

	wowTotal() {

		this.wowFooter = {
			title: 'uau',
			value: this.reportFacebookContent.totalWow, 
			smallText: '(' + this.reportFacebookContent.wowPercentage + '%)',
			iconUrl: this.getIconUrl('wow')
		}
	}

	hahaTotal() {

		this.hahaFooter = {
			title: 'haha',
			value: this.reportFacebookContent.totalHaha, 
			smallText: '(' + this.reportFacebookContent.hahaPercentage + '%)',
			iconUrl: this.getIconUrl('haha')
		}
	}

	loveTotal() {

		this.loveFooter = {
			title: 'amei',
			value: this.reportFacebookContent.totalLove, 
			smallText: '(' + this.reportFacebookContent.lovePercentage + '%)',
			iconUrl: this.getIconUrl('love')
		}
	}

	likesTotal() {

		this.likesFooter = {
			title: 'curtir',
			value: this.reportFacebookContent.totalLikes, 
			smallText: '(' + this.reportFacebookContent.likesPercentage + '%)',
			iconUrl: this.getIconUrl('likes')
		}
	}

	reactionsPerDay() {

		this.reactionsPerDayData = [{
			"key": "Like",
			"values": this.reportFacebookContent.likesPerDay.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
		}, {
			"key": "Love",
			"values": this.reportFacebookContent.lovePerDay.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
		}, {
			"key": "Haha",
			"values": this.reportFacebookContent.hahaPerDay.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
		}, {
			"key": "Wow",
			"values": this.reportFacebookContent.wowPerDay.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
		}, {
			"key": "Sad",
			"values": this.reportFacebookContent.sorryPerDay.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
		}, {
			"key": "Angry",
			"values": this.reportFacebookContent.angerPerDay.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
		}
		];
	}

	pageNegativeFeedbacksType() {

		this.pageNegativeFeedbacksTypeData = [
			{
				key: 'Interações negativas',
				values: this.reportFacebookContent.pageNegativeFeedbacksType.map(
					fav => {
						return {
							label: this.getPageNegativeFeedbackName(fav.property),
							value: fav.value,
							color: '#f7554e'
						}
					})
			}
		];
	}

	postsConsumptionsType() {

		this.postsConsumptionsTypeData = [
			{
				key: 'Interações',
				values: this.reportFacebookContent.postsConsumptionsType.map(
					fav => {
						return {
							label: this.getPostsConsumptionsName(fav.property),
							value: fav.value,
							color: '#00d6f6'
						}
					})
			}
		];
	}

	numberOfPosts() {

		this.numberOfPostsData = [
			{
				key: 'Número de posts',
				values: this.reportFacebookContent.numberOfPosts.map(
					fav => {
						return {
							label: fav.month + '/' + fav.day,
							value: fav.sum,
							color: '#00d6f6'
						}
					})
			}
		];
	}

	averagePostsPerDay() {

		this.averagePostsPerDayAside = {
			icon: {
				icon: 'fa-calendar-o',
				color: '#00d6f6'
			},
			title: 'média de posts por dia',
			value: this.reportFacebookContent.averagePostsPerDay,
			text: ''
		}
	}

	averagePostsReach() {

		this.averagePostsReachAside = {
			icon: {
				icon: 'fa-user',
				color: '#00d6f6'
			},
			title: 'média de alcance por post',
			value: this.reportFacebookContent.averagePostsReach,
			text: 'The reach was higher on ' + this.reportFacebookContent.majorPostsReachDay.month
				+ "/" + this.reportFacebookContent.majorPostsReachDay.day
				+ ' when your posts reached ' 
				+ this.abbreviateNumberPipe.transform(this.reportFacebookContent.majorPostsReachDay.sum) 
				+ ' people.'
		}
	}

	// averagePostsReach() {

	// 	this.averagePostsReachAside = {
	// 		icon: {
	// 			icon: 'fa-user',
	// 			color: '#00d6f6'
	// 		},
	// 		title: 'média de alcance por post',
	// 		value: this.reportFacebookContent.averagePostsReach,
	// 		text: 'Dia ' + this.reportFacebookContent.majorPostsReachDay.day 
	// 			+ "/" + this.reportFacebookContent.majorPostsReachDay.month 
	// 			+ ' registrou o maior índice de alcance, com ' 
	// 			+ this.abbreviateNumberPipe.transform(this.reportFacebookContent.majorPostsReachDay.sum) 
	// 			+ ' pessoas alcançadas através da sua página.'
	// 	}
	// }

	postsConsumptionsTotal() {

		this.postsConsumptionsTotalAside = {
			icon: {
				icon: 'fa-user',
				color: '#00d6f6'
			},
			title: 'interações sem gerar história',
			value: this.reportFacebookContent.postsConsumptionsCount,
			text: 'The page registered ' 
				+ this.abbreviateNumberPipe.transform(this.reportFacebookContent.postsConsumptionsCount)
				+ ' clicks on the last ' + this.period + ' days. ' 
				+ this.reportFacebookContent.postsPhotoViewPercentage + '% of those on photos, ' 
				+ this.reportFacebookContent.postsLinkClicksPercentage + '% on links, ' 
				+ this.reportFacebookContent.postsVideoPlaysPercentage + '% on videos and ' 
				+ this.reportFacebookContent.postsOtherClicksPercentage + '% on others.'
		}
	}

	// postsConsumptionsTotal() {

	// 	this.postsConsumptionsTotalAside = {
	// 		icon: {
	// 			icon: 'fa-user',
	// 			color: '#00d6f6'
	// 		},
	// 		title: 'interações sem gerar história',
	// 		value: this.reportFacebookContent.postsConsumptionsCount,
	// 		text: 'A página registrou ' 
	// 			+ this.abbreviateNumberPipe.transform(this.reportFacebookContent.postsConsumptionsCount)
	// 			+ ' cliques nos últimos ' + this.period + ' dias. Sendo ' 
	// 			+ this.reportFacebookContent.postsPhotoViewPercentage + '% de fotos, ' 
	// 			+ this.reportFacebookContent.postsLinkClicksPercentage + '% de links, ' 
	// 			+ this.reportFacebookContent.postsVideoPlaysPercentage + '% de vídeos e ' 
	// 			+ this.reportFacebookContent.postsOtherClicksPercentage + '% de outros.'
	// 	}
	// }

	averagePostsInteractions() {

		this.averagePostsInteractionsAside = {
			icon: {
				icon: 'fa-user',
				color: '#00d6f6'
			},
			title: 'média de interações por post',
			value: this.reportFacebookContent.averagePostsInteractions,
			text: 'Your posts registered more interactions on ' + this.reportFacebookContent.majorPostsInteractionsDay.month
			+ '/' + this.reportFacebookContent.majorPostsInteractionsDay.day
			+ ' with ' 
			+ this.abbreviateNumberPipe.transform(this.reportFacebookContent.majorPostsInteractionsDay.sum) 
			+ ' reactions, comments and shares.'
		}
	}

	// averagePostsInteractions() {

	// 	this.averagePostsInteractionsAside = {
	// 		icon: {
	// 			icon: 'fa-user',
	// 			color: '#00d6f6'
	// 		},
	// 		title: 'média de interações por post',
	// 		value: this.reportFacebookContent.averagePostsInteractions,
	// 		text: 'Dia ' + this.reportFacebookContent.majorPostsInteractionsDay.day 
	// 		+ '/' + this.reportFacebookContent.majorPostsInteractionsDay.month 
	// 		+ ' registrou o maior número de interações, com ' 
	// 		+ this.abbreviateNumberPipe.transform(this.reportFacebookContent.majorPostsInteractionsDay.sum) 
	// 		+ ' através de reações, comentários e compartilhamentos.'
	// 	}
	// }
}
