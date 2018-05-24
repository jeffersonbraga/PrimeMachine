import { Notification } from 'modelo/notification/Notification';
import { DatePipe, DecimalPipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { AbbreviateNumberPipe } from 'core/pipes/abbreviateNumber.pipe';
import { ControlService } from 'core/services/control/control.service';
import { InstagramService } from 'core/services/instagram/instagram.service';
import { NotificationService } from 'core/services/notification/notification.service';
import { PdfService } from 'core/services/pdf/pdf.service';
import { IAsidePieChart, IAsideTextIcon } from 'modelo/complements/Complements';

@Component({
	selector: 'instagram-report-historical',
	templateUrl: './instagram-report-historical.component.html',
	styleUrls: ['./instagram-report-historical.component.css']
})
export class InstagramReportHistoricalComponent implements OnInit {

	public postsStatsYearMonthSelected: string = 'all';

	showCheckAllBtn: boolean = false;

	@Input() profile;
	@Input() dataHistorical: any;
	@Input() period;
	account;

	bubbleDayColor: string[] = ['5fd6ff'];
	bubbleHourColor: string[] = ['ffcb39'];

	weekDays: string[] = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
	weekDaysFull: string[] = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']
	monthNames : string[] = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

	// weekDays: string[] = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'];
	// weekDaysFull: string[] = ['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'];
	// monthNames: string[] = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];

	colorsReactionsPerDay = ["#00d6f6", "#00aed9", "#f6cb00", "#ad43ff", "#00f6bf", "#ff7800"];
	fansOnlinePerHourTickValues: any = Array.from(Array(24), (_, x) => this.getDateFromHour(x));

	legendsFollowerTrend = {
		legends: true,
		color: ["#20ffa3", "#f7554e", "#ffd71e"]
	}

	legendsPublicAgeGender = {
		legends: true,
		color: ["#00d6f6", "#f74e7e"]
	}

	legendsLikesCommentsPerHour = {
		legends: true,
		color: ["#00d6f6", "#00aed9"]
	}

	instagramFollowerTrend: any = [];
	instagramPublicAgeGender: any = [];
	countriesWithMoreFollowersData: any = [];
	citiesWithMoreFollowersData: any = [];
	onlineFollowersByHour: any = [];
	onlineFollowersByDay: any = [];
	likesPerDay: any = [];
	commentsPerDay: any = [];
	likesCommentsPerHour: any = [];
	instagramTopPostTags: any = [];
	mostEngagedFollowers: any = [];
	/*
		likesPerHour					: any = [];
		commentsPerHour					: any = [];
	*/

	historical_posts_all: any = [];
	historical_posts_first_year: any = [];
	historical_posts_second_year: any = [];
	historical_posts_third_year: any = [];

	historical_likes: any = [];
	historical_likes_first_year: any = [];
	historical_likes_second_year: any = [];
	historical_likes_third_year: any = [];

	historical_comments: any = [];
	historical_comments_first_year: any = [];
	historical_comments_second_year: any = [];
	historical_comments_third_year: any = [];

	historical_likesCommentsPerHour: any = [];
	historical_instagramPublicAgeGender: any = [];
	historical_instagramTopPostTags: any = [];
	historical_postsByDay: any = [];
	historical_postsByHour: any = [];

	/*historical_instagramFollowerTrend 			: any = [];
	historical_onlineFollowersByDay				: any = [];
	historical_onlineFollowersByHour			: any = [];
	historical_likesPerDay						: any = [];
	historical_commentsPerDay					: any = [];
	*/

	//ASIDE VARIABLE
	totalFollowersPeakAside: IAsideTextIcon;
	followersBalancePeakAside: IAsideTextIcon;
	unFollowersPeakAside: IAsideTextIcon;
	newFollowersPeakAside: IAsideTextIcon;
	complementPublicAge: IAsideTextIcon;
	complementPublicGender: IAsidePieChart;
	timeWithMoreFansOnlineFooter: IAsideTextIcon;
	weekDayWithMoreFansOnlineFooter: IAsideTextIcon;

	firstInteractionRanking: IAsideTextIcon;
	secondInteractionRanking: IAsideTextIcon;

	yearMorePostsFooter: IAsideTextIcon;
	yearBetterPostFrequencyFooter: IAsideTextIcon;

	totalLikesFooter: IAsideTextIcon;
	averageLikesPerMonthFooter: IAsideTextIcon;

	totalCommentsFooter: IAsideTextIcon;
	averageCommentsPerMonthFooter: IAsideTextIcon;

	constructor(
		private instagramService: InstagramService,
		private datePipe: DatePipe,
		private decimalPipe: DecimalPipe,
		private controlService: ControlService,
		private abbreviateNumberPipe: AbbreviateNumberPipe,
		private pdfService: PdfService,
		private notificationService: NotificationService
	) { }

	ngOnInit() {
		this.mountHistoricalCharts();
		this.account = this.controlService.account;
	}

	getXLS() {
		this.instagramService.getReportHistoricalXLS(this.profile.idProfile)
			.subscribe(
				result => this.downloadXLS(result),
				error => console.log(error)
			);
	}

	downloadXLS(result) {
		window.open(this.controlService.context_app + 'woopsocial/get_xls?file=' + result.fileName + '&filename=' + result.formattedFileName + '&idAccount=' + result.idAccount);
	}

	getPDF() {

		let usr = this.controlService.getUser();
		let notification: Notification = {
			account: usr.account,
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

	mountHistoricalCharts() {

		this.getValuesHistorical_posts();
		this.yearMorePosts();
		this.yearBetterPostFrequency();

		this.getValuesHistorical_postsByDay();

		this.getValuesHistorical_likes();
		this.totalLikes();
		this.averageLikesPerMonth();

		this.getValuesHistorical_comments();
		this.totalComments();
		this.averageCommentsPerMonth();

		this.getValuesHistoriacalLikesCommentsPerHour();
		this.interactionsRanking();

		this.getValuesInstagramPublicAgeGender();
		this.getComplementPublicAge();
		this.getComplementPublicGender();

		this.getValuesHistorical_instagramTopPostTags();
	}

	//POST POR MES E ANO
	getValuesHistorical_posts(): any {

		this.historical_posts_all = [];

		this.historical_posts_all = [
			{
				"key": 'Posts',
				"values": this.dataHistorical.instagramPostsStatsYearThird.postsPerMonthSimpleData.map(
					fav => {
						return [new Date(this.dataHistorical.instagramPostsStatsYearThird.year, fav.key - 1, 1).getTime(), fav.value]
					})
			}
		];

		this.historical_posts_first_year = [
			{
				"key": 'Posts',
				"values": this.dataHistorical.instagramPostsStatsYearFirst.postsPerMonthSimpleData.map(
					fav => {
						return [new Date(this.dataHistorical.instagramPostsStatsYearFirst.year, fav.key - 1, 1).getTime(), fav.value]
					})
			}
		];

		this.historical_posts_second_year = [
			{
				"key": 'Posts',
				"values": this.dataHistorical.instagramPostsStatsYearSecond.postsPerMonthSimpleData.map(
					fav => {
						return [new Date(this.dataHistorical.instagramPostsStatsYearSecond.year, fav.key - 1, 1).getTime(), fav.value]
					})
			}
		];

		this.historical_posts_third_year = [
			{
				"key": 'Posts',
				"values": this.dataHistorical.instagramPostsStatsYearThird.postsPerMonthSimpleData.map(
					fav => {
						return [new Date(this.dataHistorical.instagramPostsStatsYearThird.year, fav.key - 1, 1).getTime(), fav.value]
					})
			}
		];
	}

	//LIKES POR MES E ANO
	getValuesHistorical_likes(): any {

		this.historical_likes = [
			{
				"key": 'Likes',
				"values": this.dataHistorical.instagramPostsStatsYearThird.likesPerMonthSimpleData.map(
					fav => {
						return [new Date(this.dataHistorical.instagramPostsStatsYearThird.year, fav.key - 1, 1).getTime(), fav.value]
					})
			}
		];

		this.historical_likes_first_year = [
			{
				"key": 'Likes',
				"values": this.dataHistorical.instagramPostsStatsYearFirst.likesPerMonthSimpleData.map(
					fav => {
						return [new Date(this.dataHistorical.instagramPostsStatsYearSecond.year, fav.key - 1, 1).getTime(), fav.value]
					})
			}
		];

		this.historical_likes_second_year = [
			{
				"key": 'Likes',
				"values": this.dataHistorical.instagramPostsStatsYearSecond.likesPerMonthSimpleData.map(
					fav => {
						return [new Date(this.dataHistorical.instagramPostsStatsYearSecond.year, fav.key - 1, 1).getTime(), fav.value]
					})
			}
		];

		this.historical_likes_third_year = [
			{
				"key": 'Likes',
				"values": this.dataHistorical.instagramPostsStatsYearThird.likesPerMonthSimpleData.map(
					fav => {
						return [new Date(this.dataHistorical.instagramPostsStatsYearThird.year, fav.key - 1, 1).getTime(), fav.value]
					})
			}
		];
	}

	//COMMENTS POR MES E ANO
	getValuesHistorical_comments(): any {

		this.historical_comments = [
			{
				"key": 'Comments',
				"values": this.dataHistorical.instagramPostsStatsYearThird.commentsPerMonthSimpleData.map(
					fav => {
						return [new Date(this.dataHistorical.instagramPostsStatsYearThird.year, fav.key - 1, 1).getTime(), fav.value]
					})
			}
		];

		this.historical_comments_first_year = [
			{
				"key": 'Comments',
				"values": this.dataHistorical.instagramPostsStatsYearFirst.commentsPerMonthSimpleData.map(
					fav => {
						return [new Date(this.dataHistorical.instagramPostsStatsYearSecond.year, fav.key - 1, 1).getTime(), fav.value]
					})
			}
		];

		this.historical_comments_second_year = [
			{
				"key": 'Comments',
				"values": this.dataHistorical.instagramPostsStatsYearSecond.commentsPerMonthSimpleData.map(
					fav => {
						return [new Date(this.dataHistorical.instagramPostsStatsYearSecond.year, fav.key - 1, 1).getTime(), fav.value]
					})
			}
		];

		this.historical_comments_third_year = [
			{
				"key": 'Comments',
				"values": this.dataHistorical.instagramPostsStatsYearThird.commentsPerMonthSimpleData.map(
					fav => {
						return [new Date(this.dataHistorical.instagramPostsStatsYearThird.year, fav.key - 1, 1).getTime(), fav.value]
					})
			}
		];
	}

	totalLikes(value?) {
		
		if(!value) {
			value = this.dataHistorical.instagramPostsStatsYearThird.likesTotal;
		}

		this.totalLikesFooter = {
			icon: {
				icon: 'fa-heart',
				color: '#00d6f6'
			},
			title: 'Total de likes',
			value: value,
		};
	}

	getComplementPublicAge() {
		
		this.complementPublicAge = {
			icon: {
				icon: 'fa-arrow-circle-up',
				color: '#ffd71e'
			},
			title: 'Maior faixa etária',
			value: this.dataHistorical.audiencesAveragePublicAge.key.replace("-", " to "),
			text: 'Most of the profile’s audience is between '
			+ this.dataHistorical.audiencesAveragePublicAge.key.replace("-", " and ") + ' years old, '
			+ 'representing ' + Math.round(this.getPercentValue(this.dataHistorical.audiencesAveragePublicAge.value, this.dataHistorical.followers)) + '% of the total.'
		}
	}

	// **Método não traduzido**
	// getComplementPublicAge() {
		
	// 	this.complementPublicAge = {
	// 		icon: {
	// 			icon: 'fa-arrow-circle-up',
	// 			color: '#ffd71e'
	// 		},
	// 		title: 'Maior faixa etária',
	// 		value: this.dataHistorical.audiencesAveragePublicAge.key.replace("-", " a "),
	// 		text: 'A maior parte do público do perfil tem entre '
	// 		+ this.dataHistorical.audiencesAveragePublicAge.key.replace("-", " e ") + ' anos, '
	// 		+ 'representando ' + Math.round(this.getPercentValue(this.dataHistorical.audiencesAveragePublicAge.value, this.dataHistorical.followers)) + '% do total.'
	// 	}
	// }

	getComplementPublicGender() {
		
		let legend = [
			{icon: {icon: 'fa-female', color:'#f74e7e'}, value: Math.round(this.getPercentValue(this.dataHistorical.audiencesAveragePublicGender[1].value, this.dataHistorical.followers))+'%'},
			{icon: {icon: 'fa-male', color:'#00d6f6'}, value: Math.round(this.getPercentValue(this.dataHistorical.audiencesAveragePublicGender[0].value, this.dataHistorical.followers))+'%'}
		];

		// let legend = [
		// 	{icon: {icon: 'fa-female', color:'#f74e7e'}, value: 0+'%'},
		// 	{icon: {icon: 'fa-male', color:'#00d6f6'}, value: 0+'%'}
		// ];

		this.complementPublicGender = {
			icon: {
				icon: 'fa-venus-mars',
				color: '#f6cb00'
			},
			chart: {
				data : [
					{ 
						"label": "Men",
						"value" : Math.round(this.getPercentValue(this.dataHistorical.audiencesAveragePublicGender[0].value, this.dataHistorical.followers)), 
						"color": "#00d6f6"
					}, 
					{ 
						"label": "Women",
						"value" : Math.round(this.getPercentValue(this.dataHistorical.audiencesAveragePublicGender[1].value, this.dataHistorical.followers)), 
						"color": "#f74e7e"
					}
				],
				height: 100,
				width: 100,
				isDonut: true,
				legend: legend
			},
			title: 'Divisão do público',
			orientation: 'center'
		}
	}

	totalComments(value?) {

		if(!value) {
			value = this.dataHistorical.instagramPostsStatsYearThird.commentsTotal;
		}

		this.totalCommentsFooter = {
			icon: {
				icon: 'fa-comment',
				color: '#00d6f6'
			},
			title: 'Total de comentários',
			value: value,
		};
	}

	averageLikesPerMonth(likesTotal?, averageLikes?) {

		if(!likesTotal) {
			likesTotal = this.dataHistorical.instagramPostsStatsYearThird.likesTotal;
		}
		if(!averageLikes) {
			averageLikes = this.dataHistorical.instagramPostsStatsYearThird.averageLikesPerMonth;
		}

		this.averageLikesPerMonthFooter = {
			icon: {
				icon: 'fa-area-chart',
				color: '#f6cb00'
			},
			title: 'Média de likes por mês',
			value: averageLikes,
			text: 'Your posts registered ' + likesTotal + ' likes during the selected time, ' + 
			'with an average of ' + averageLikes + ' likes per month'
		}
	}

	averageCommentsPerMonth(commentsTotal?, averageComments?) {
		
		if(!commentsTotal) {
			commentsTotal = this.dataHistorical.instagramPostsStatsYearThird.commentsTotal;
		}
		if(!averageComments) {
			averageComments = this.dataHistorical.instagramPostsStatsYearThird.averageCommentsPerMonth;
		}

		this.averageCommentsPerMonthFooter = {
			icon: {
				icon: 'fa-area-chart',
				color: '#f6cb00'
			},
			title: 'Média de comentários por mês',
			value: averageComments,
			text: 'Your posts registered ' + commentsTotal + ' comments during the selected time, ' +
				'with an average of ' + averageComments + ' comments per month'
		}
	}

	// averageLikesPerMonth(likesTotal?, averageLikes?) {

	// 	if(!likesTotal) {
	// 		likesTotal = this.dataHistorical.instagramPostsStatsYearFirst.likesTotal;
	// 	}
	// 	if(!averageLikes) {
	// 		averageLikes = this.dataHistorical.instagramPostsStatsYearFirst.averageLikesPerMonth;
	// 	}

	// 	this.averageLikesPerMonthFooter = {
	// 		icon: {
	// 			icon: 'fa-area-chart',
	// 			color: '#f6cb00'
	// 		},
	// 		title: 'Média de likes por mês',
	// 		value: averageLikes,
	// 		text: 'Seus posts tiveram ' + likesTotal + ' likes no período, com ' +
	// 			' uma média de ' + averageLikes + ' likes por mês'
	// 	}
	// }

	// averageCommentsPerMonth(commentsTotal?, averageComments?) {
		
	// 	if(!commentsTotal) {
	// 		commentsTotal = this.dataHistorical.instagramPostsStatsYearFirst.commentsTotal;
	// 	}
	// 	if(!averageComments) {
	// 		averageComments = this.dataHistorical.instagramPostsStatsYearFirst.averageCommentsPerMonth;
	// 	}

	// 	this.averageCommentsPerMonthFooter = {
	// 		icon: {
	// 			icon: 'fa-area-chart',
	// 			color: '#f6cb00'
	// 		},
	// 		title: 'Média de comentários por mês',
	// 		value: averageComments,
	// 		text: 'Seus posts tiveram ' + commentsTotal + ' comentários no período, com ' +
	// 			' uma média de ' + averageComments + ' comentários por mês'
	// 	}
	// }

	yearMorePosts() {

		this.yearMorePostsFooter = {
			icon: {
				icon: 'fa-picture-o',
				color: '#00d6f6'
			},
			title: 'Ano com mais posts',
			value: this.dataHistorical.yearWithMorePosts,
			text: this.dataHistorical.yearWithMorePosts + ' was the year when you posted the most, counting '
				+ this.dataHistorical.yearWithMorePostsValue + ' posts'
		}
	}

	// yearMorePosts() {

	// 	this.yearMorePostsFooter = {
	// 		icon: {
	// 			icon: 'fa-picture-o',
	// 			color: '#00d6f6'
	// 		},
	// 		title: 'Ano com mais posts',
	// 		value: this.dataHistorical.yearWithMorePosts,
	// 		text: this.dataHistorical.yearWithMorePosts + ' foi o ano com mais postagens, com '
	// 			+ this.dataHistorical.yearWithMorePostsValue + ' postagens'
	// 	}
	// }

	yearBetterPostFrequency() {

		this.yearBetterPostFrequencyFooter = {
			icon: {
				icon: 'fa-bar-chart',
				color: '#f6cb00'
			},
			title: 'Year with best post frequency',
			value: this.dataHistorical.yearBetterPostFrequency,
			text: this.dataHistorical.yearBetterPostFrequency + ' was the year with the highest frequency of posts, with an average of '
				+ this.dataHistorical.yearBetterPostFrequencyValue + ' posts per month'
		}
	}

	// yearBetterPostFrequency() {

	// 	this.yearBetterPostFrequencyFooter = {
	// 		icon: {
	// 			icon: 'fa-bar-chart',
	// 			color: '#f6cb00'
	// 		},
	// 		title: 'Ano com melhor frequência',
	// 		value: this.dataHistorical.yearBetterPostFrequency,
	// 		text: this.dataHistorical.yearBetterPostFrequency + ' foi o ano com a maior frequência de postagens, com a média de '
	// 			+ this.dataHistorical.yearBetterPostFrequencyValue + ' postagens por mês'
	// 	}
	// }

	showLikesStatsYearMonthSelected(event: any) {

		if(event.target.value == 'first') {
			this.historical_likes = this.historical_likes_first_year;
			this.totalLikes(this.dataHistorical.instagramPostsStatsYearFirst.likesTotal);
			this.averageLikesPerMonth(this.dataHistorical.instagramPostsStatsYearFirst.likesTotal,
				this.dataHistorical.instagramPostsStatsYearFirst.averageLikesPerMonth);
		} else if(event.target.value == 'second') {
			this.historical_likes = this.historical_likes_second_year;
			this.totalLikes(this.dataHistorical.instagramPostsStatsYearSecond.likesTotal);
			this.averageLikesPerMonth(this.dataHistorical.instagramPostsStatsYearSecond.likesTotal,
				this.dataHistorical.instagramPostsStatsYearSecond.averageLikesPerMonth);
		} else if(event.target.value == 'third') {
			this.historical_likes = this.historical_likes_third_year;
			this.totalLikes(this.dataHistorical.instagramPostsStatsYearThird.likesTotal);
			this.averageLikesPerMonth(this.dataHistorical.instagramPostsStatsYearThird.likesTotal,
				this.dataHistorical.instagramPostsStatsYearThird.averageLikesPerMonth);
		}
	}

	showCommentsStatsYearMonthSelected(event: any) {
		
		if(event.target.value == 'first') {
			this.historical_comments = this.historical_comments_first_year;
			this.totalComments(this.dataHistorical.instagramPostsStatsYearFirst.commentsTotal);
			this.averageCommentsPerMonth(this.dataHistorical.instagramPostsStatsYearFirst.commentsTotal,
				this.dataHistorical.instagramPostsStatsYearFirst.averageCommentsPerMonth);
		} else if(event.target.value == 'second') {
			this.historical_comments = this.historical_comments_second_year;
			this.totalComments(this.dataHistorical.instagramPostsStatsYearSecond.commentsTotal);
			this.averageCommentsPerMonth(this.dataHistorical.instagramPostsStatsYearSecond.commentsTotal,
				this.dataHistorical.instagramPostsStatsYearSecond.averageCommentsPerMonth);
		} else if(event.target.value == 'third') {
			this.historical_comments = this.historical_comments_third_year;
			this.totalComments(this.dataHistorical.instagramPostsStatsYearThird.commentsTotal);
			this.averageCommentsPerMonth(this.dataHistorical.instagramPostsStatsYearThird.commentsTotal,
				this.dataHistorical.instagramPostsStatsYearThird.averageCommentsPerMonth);
		}
	}

	showPostsStatsYearMonthSelected(event: any) {

		if (event.target.value == 'first') {
			this.historical_posts_all = this.historical_posts_first_year;
		} else if (event.target.value == 'second') {
			this.historical_posts_all = this.historical_posts_second_year;
		} else if (event.target.value == 'third') {
			this.historical_posts_all = this.historical_posts_third_year;
		}

	}

	interactionsRanking() {
		
		this.firstInteractionRanking = {
			icon: {
				icon: 'fa-clock-o',
				color: '#f6cb00'
			},
			title: '1° Horário em interações',
			value: this.formatHour(Number(this.dataHistorical.instagramInteractionsRanking[0].hour)) 
				+ ' to ' + this.formatHour((Number(this.dataHistorical.instagramInteractionsRanking[0].hour) + 1)), 
			text: this.dataHistorical.instagramInteractionsRanking[0].percentage + '% of the total.'
		}
		
		// this.firstInteractionRanking = {
		// 	icon: {
		// 		icon: 'fa-clock-o',
		// 		color: '#f6cb00'
		// 	},
		// 	title: '1° Horário em interações',
		// 	value: this.formatHour(Number(this.dataHistorical.instagramInteractionsRanking[0].hour)) 
		// 		+ ' às ' + this.formatHour((Number(this.dataHistorical.instagramInteractionsRanking[0].hour) + 1)), 
		// 	text: this.dataHistorical.instagramInteractionsRanking[0].percentage + '% do total.'
		// }

		this.secondInteractionRanking = {
			icon: {
				icon: 'fa-clock-o',
				color: '#f6cb00'
			},
			title: '2° Horário em interações',
			value: this.formatHour(Number(this.dataHistorical.instagramInteractionsRanking[1].hour)) 
				+ ' to ' + this.formatHour((Number(this.dataHistorical.instagramInteractionsRanking[1].hour) + 1)), 
			text: this.dataHistorical.instagramInteractionsRanking[1].percentage + '% of the total.'
		}

		// this.secondInteractionRanking = {
		// 	icon: {
		// 		icon: 'fa-clock-o',
		// 		color: '#f6cb00'
		// 	},
		// 	title: '2° Horário em interações',
		// 	value: this.formatHour(Number(this.dataHistorical.instagramInteractionsRanking[1].hour)) 
		// 		+ ' às ' + this.formatHour((Number(this.dataHistorical.instagramInteractionsRanking[1].hour) + 1)), 
		// 	text: this.dataHistorical.instagramInteractionsRanking[1].percentage + '% do total.'
		// }
	}

	//POSTS POR DIA E HORA
	getValuesHistorical_postsByDay(): any {
		if ((this.dataHistorical != undefined) && (this.dataHistorical.postsPerWeekDaySimpleData != undefined)) {

			let dataColumnDayWeek: any = [];
			let dataColumnHourDay: any = [];

			this.dataHistorical.postsPerWeekDaySimpleData.map(
				fav => {

					dataColumnDayWeek.push({
						x: fav.key,
						y: "",
						size: (fav.value * 0.5),   //Configure the size of each scatter point
						shape: "circle"  //Configure the shape of each scatter point.
					});
				}
			);

			this.dataHistorical.postsPerHourSimpleData.map(
				fav => {

					dataColumnHourDay.push({
						x: fav.key,
						y: 0,
						size: (fav.value * 0.5),   //Configure the size of each scatter point
						shape: "circle"  //Configure the shape of each scatter point.
					});
				}
			);

			this.historical_postsByDay = [
				{
					key: 'Day',
					values: dataColumnDayWeek
				}
			];

			this.historical_postsByHour = [
				{
					key: 'Hour',
					values: dataColumnHourDay
				}
			];
		}
	}

	//LIKES E COMENTÁRIOS POR HORA
	getValuesHistoriacalLikesCommentsPerHour() {

		let dataLikes: any = [];
		let dataComments: any = [];

		this.dataHistorical.likesPerHourSimpleData.map(
			fav => {
				dataLikes.push([this.getDateFromHour(fav.key), fav.value]);
			}
		);

		this.dataHistorical.commentsPerHourSimpleData.map(
			fav => {
				dataComments.push([this.getDateFromHour(fav.key), fav.value]);
			}
		);

		this.historical_likesCommentsPerHour = [
			{
				"key": "Comments",
				"values": dataComments
			},
			{
				"key": "Likes",
				"values": dataLikes
			}
		];
	}

	//TABELA HASHTAGS
	getValuesHistorical_instagramTopPostTags(): any {

		if ((this.dataHistorical != undefined) && (this.dataHistorical.instagramTopPostTags != undefined)) {
			
			let dataPostTags = [];
			this.dataHistorical.instagramTopPostTags.map(
					fav => {
						dataPostTags.push([
							{value: fav.tag, class: 'left'}, 
							{value: fav.comments, class: 'right'}, 
							{value: fav.likes, class: 'right'}, 
							{value: fav.interactions, class: 'right'}]);
						});

			this.instagramTopPostTags = {
				cols: [
					{value: 'Hashtag', class: 'left'}, {value: 'Comentários', class: 'right', width: '100px'}, {value: 'Likes', class: 'right', width: '100px'}, {value: 'Total', class: 'right', width: '100px'}
				], 
				rows: dataPostTags
			}
		}
	}

	//DEMOGRAFIA DO PÚBLICO
	getValuesInstagramPublicAgeGender(){

		if ((this.dataHistorical != undefined) && (this.dataHistorical.audiencesDemograph != undefined)) {

			let dataMen 	: any = [];
			let dataWomen 	: any = [];
			this.dataHistorical.audiencesDemograph.map(
				fav => {
					if (fav.property == 'audience_age_men') {

						dataMen.push({
							x: fav.label + '', 
							y: fav.value,
							color: '#00d6f6'
						});
					} else if (fav.property == 'audience_age_women') {

						dataWomen.push( {
							x: fav.label + '', 
							y: fav.value,
							color: '#f74e7e'
						});
					}
				}
			);

			this.instagramPublicAgeGender = [ 
				{
					"key": "Male",
					"values": dataMen }, 
				{
					"key": "Female",
					"values": dataWomen
				}
			];
		}
	}

	getDateFromHour(hour) {

		let date = new Date();
		date.setTime(0);
		date.setMinutes(0);
		date.setSeconds(0);
		date.setMilliseconds(0);
		date.setDate(1);
		date.setHours(hour);
		return date.getTime();
	}

	getDateForMonth(month) {

		let date = new Date();
		date.setTime(0);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		date.setMilliseconds(0);
		date.setDate(1);
		date.setMonth(month);
		return date.getTime();
	}

	getPercentValue(value, countTotal) {

		if (countTotal == undefined || countTotal == 0) {
			return 0;
		}

		return (value * 100) / countTotal;
	}

	formatDate(date) {
		date = new Date(date * 1000);
		return this.datePipe.transform(date, 'dd/MM');
	}

	formatHour(hour) {
		hour = hour == 24 ? 0 : hour;

		return (hour < 10 ? '0' + hour : hour) + ':00';
	}

	xAxisTickFunctionMonth(d) {
		let monthNames : string[] = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
		// let monthNames: string[] = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];
		return monthNames[d];
	}

	xAxisTickFunctionDay(d) {
		let weekDays: string[] = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
		// let weekDays: string[] = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'];
		return weekDays[d];
	}

	createTdValue(img, text) {
		return '<span><img height="28" src="' + img + '" /> ' + text + '</span>';
	}

}
