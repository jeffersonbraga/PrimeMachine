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
  selector: 'instagram-report-overview',
  templateUrl: './instagram-report-overview.component.html',
  styleUrls: ['./instagram-report-overview.component.css']
})
export class InstagramReportOverviewComponent implements OnInit {

  	public postsStatsYearFirst	: boolean = true;
	public postsStatsYearSecond	: boolean = false;
	public postsStatsYearThird	: boolean = false;

	showCheckAllBtn: boolean = false;

  	@Input() dateFrom: Date;
	@Input() dateUntil: Date;
	@Input() profile;
	@Input() dataOverview: any;
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
	fansOnlinePerHourTickValues		: any = Array.from(Array(24), (_,x) => this.getDateFromHour(x));

	legendsFollowerTrend = {
		legends: true,
		color: ["#20ffa3", "#f7554e", "#ffd71e"]
	}

	legendsPublicAgeGender = {
		legends: true,
		color: ["#00d6f6", "#f74e7e"]
	}

	// dataOverview	: any;
	// dataHistorical	: any;

	instagramFollowerTrend 			: any = [];
	instagramPublicAgeGender 		: any = [];
	countriesWithMoreFollowersData	: any = [];
	citiesWithMoreFollowersData		: any = [];
	onlineFollowersByHour			: any = [];
	onlineFollowersByDay			: any = [];
	likesPerDay						: any = []; 
	commentsPerDay					: any = [];
	likesCommentsPerHour			: any = [];
	instagramTopPostTags			: any = [];
	mostEngagedFollowers            : any = [];

	//ASIDE VARIABLE
	totalFollowersPeakAside 		: IAsideTextIcon;
	followersBalancePeakAside 		: IAsideTextIcon;
	unFollowersPeakAside 			: IAsideTextIcon;
	newFollowersPeakAside 			: IAsideTextIcon;
	complementPublicAge 			: IAsideTextIcon;
	complementPublicGender 			: IAsidePieChart;
	timeWithMoreFansOnlineFooter	: IAsideTextIcon;
	weekDayWithMoreFansOnlineFooter	: IAsideTextIcon;

	firstInteractionRanking : IAsideTextIcon;
	secondInteractionRanking : IAsideTextIcon;
	thirdInteractionRanking : IAsideTextIcon;

	totalLikesFooter: IAsideTextIcon;
	averageLikesPerDayFooter: IAsideTextIcon;

	totalCommentsFooter: IAsideTextIcon;
	averageCommentsPerDayFooter: IAsideTextIcon;

	constructor(
		private instagramService: InstagramService, 
		private datePipe: DatePipe, 
    private decimalPipe: DecimalPipe,
    private controlService: ControlService,
    private abbreviateNumberPipe: AbbreviateNumberPipe,
    private pdfService: PdfService,
    private notificationService: NotificationService
	) {}

  	ngOnInit() {
		this.mountOverviewCharts();
		this.account = this.controlService.account;
	}

	setPeriod(){
		let timeDiff = this.dateUntil.getTime() - this.dateFrom.getTime();
		this.period = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
	}

	validateForm():boolean{
		if(this.dateFrom && this.dateUntil && this.profile){
			return true;
		} else return false; 
	}

	getXLS() {
		this.instagramService.getOverviewReportXLS(this.profile.idProfile, this.dateFrom.getTime(), this.dateUntil.getTime())
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
 
	mountOverviewCharts() {

		console.log('Instagram Report Overview Data...');
		console.log(this.dataOverview);

		this.getValuesInstagramFollowerTrend();
		this.getTotalFollowers();
		this.getNewFollowers();
		this.getUnFollowers();
		this.getFollowersBalance();

		this.getValuesInstagramPublicAgeGender();
		this.getComplementPublicAge();
		this.getComplementPublicGender();
		
		this.getValuesInstagramTopCountries();
		this.getValuesInstagramTopCities();
		
		this.getValuesOnlineFollowersByHour();
		this.timeWithMoreFansOnline();

		this.getValuesOnlineFollowersByDay();
		this.weekDayWithMoreFansOnline();

		this.getValuesLikesPerDay();
		this.totalLikes();
		this.averageLikesPerDay();
		
		this.getValuesCommentsPerDay();		
		this.totalComments();
		this.averageCommentsPerDay();
		
		this.getValuesLikesCommentsPerHour();
		this.interactionsRanking();

		this.getValuesInstagramTopPostTags();
	}

	//MUDANÇA DE FOLLOWERS
	getValuesInstagramFollowerTrend() {
		if ((this.dataOverview != undefined) && (this.dataOverview.instagramFollowerTrends != undefined)) {
			this.instagramFollowerTrend = [ {
				"key": "Follow",
				"values": this.dataOverview.instagramFollowerTrends.map(
					fav => {
						return {
							x: this.formatDate(fav.date), 
							y: fav.newFollowers,
							color: '#20ffa3'
						}
					})
				}, {
				"key": "Unfollow",
				"values": this.dataOverview.instagramFollowerTrends.map(
					fav => {
						return {
							x: this.formatDate(fav.date), 
							y: -fav.unfollowers,
							color: '#f7554e'
						}
					})
				},
			]
		}
	}

	//DEMOGRAFIA DO PÚBLICO
	getValuesInstagramPublicAgeGender(){

		if ((this.dataOverview != undefined) && (this.dataOverview.audiencesDemograph != undefined)) {

			let dataMen 	: any = [];
			let dataWomen 	: any = [];
			this.dataOverview.audiencesDemograph.map(
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

	//TOP 10 PAÍSES
	getValuesInstagramTopCountries(){
		if ((this.dataOverview != undefined) && (this.dataOverview.audiencesTopCountry != undefined)) {

			let dataCountries = [];
			this.dataOverview.audiencesTopCountry.map(
					fav => {
						dataCountries.push([
							{value: fav.label, class: 'left'}, 
							{value: fav.value, class: 'right'}]);
						});

			this.countriesWithMoreFollowersData = {
				cols: [
					{value: 'Pais', class: 'left'}, {value: 'Followers', class: 'right', width: '100px'}
				], 
				rows: dataCountries
			}
		}
	}

	//TOP 10 CIDADES
	getValuesInstagramTopCities(){
		if ((this.dataOverview != undefined) && (this.dataOverview.audiencesTopCities != undefined)) {

			let dataCities = [];
			this.dataOverview.audiencesTopCities.map(
					fav => {
						dataCities.push([
							{value: fav.label, class: 'left'}, 
							{value: fav.value, class: 'right'}]);
						});

			this.citiesWithMoreFollowersData = {
				cols: [
					{value: 'Cidade', class: 'left'}, {value: 'Followers', class: 'right', width: '100px'}
				], 
				rows: dataCities
			}
		}
	}

	//SEGUIDORES ONLINE POR HORÁRIO
	getValuesOnlineFollowersByHour() {
		if ((this.dataOverview != undefined) && (this.dataOverview.audiencesOnlineFollowersByHour != undefined)) {

			let onlineFollowersByHourList = this.dataOverview.audiencesOnlineFollowersByHour.map(
				fav => {
					return [this.getDateFromHour(fav.label), fav.value]
				});

			onlineFollowersByHourList = onlineFollowersByHourList.sort((a, b) => a[0] - b[0]);

			this.onlineFollowersByHour = [{
				"key": "Online",
				"values": onlineFollowersByHourList
				}
			];


		}
	}

	//SEGUIDORES ONLINE POR DIA DA SEMANA
	getValuesOnlineFollowersByDay() {
		if ((this.dataOverview != undefined) && (this.dataOverview.audiencesOnlineFollowersByDay != undefined)) {

			let dataColumn 	: any = [];
			this.dataOverview.audiencesOnlineFollowersByDay.map(
				fav => {
					dataColumn.push({
						x: this.weekDays[fav.label], 
						y: Math.round(fav.value),
						color: '#00d6f6'
					});
				}
			);

			this.onlineFollowersByDay = [
				{
					key: 'Online',
					values: dataColumn
				}
			];
		}
	}

	//LIKES DD/MM
	getValuesLikesPerDay() {
		if ((this.dataOverview != undefined) && (this.dataOverview.likesPerDay != undefined)) {

			let dataColumnFollowers 	: any = [];

			this.dataOverview.likesPerDay.map(
				fav => {

					dataColumnFollowers.push({
						x: this.formatDate(fav.date), 
						y: fav.value,
						color: '#00d6f6'
					});
				}
			);

			this.likesPerDay = [
				{
					key: 'Likes',
					values: dataColumnFollowers
				}
			];
		}
	}

	totalComments() {
		
		this.totalCommentsFooter = {
			icon: {
				icon: 'fa-comment',
				color: '#00d6f6'
			},
			title: 'Total de comentários',
			value: this.dataOverview.comments
		}

	}

	totalLikes() {
		
		this.totalLikesFooter = {
			icon: {
				icon: 'fa-heart',
				color: '#00d6f6'
			},
			title: 'Total de likes',
			value: this.dataOverview.likes
		}

	}

	averageCommentsPerDay() {
		
		this.averageCommentsPerDayFooter = {
			icon: {
				icon: 'fa-area-chart',
				color: '#ffd71e'
			},
			title: 'Média de comentários por dia',
			value: this.dataOverview.commentsAverage,
			text: 'Your posts registered ' + this.dataOverview.comments + ' comments during the selected time, ' +
					'with an average of ' + this.dataOverview.commentsAverage + ' comments per day'
		}

	}

	averageLikesPerDay() {

		this.averageLikesPerDayFooter = {
			icon: {
				icon: 'fa-area-chart',
				color: '#ffd71e'
			},
			title: 'Média de likes por dia',
			value: this.dataOverview.likesAverage,
			text: 'Your posts registered ' + this.dataOverview.likes + ' likes during the selected time, ' +
					'with an average of ' + this.dataOverview.likesAverage + ' likes per day'
		}

	}

	// averageCommentsPerDay() {
		
	// 	this.averageCommentsPerDayFooter = {
	// 		icon: {
	// 			icon: 'fa-area-chart',
	// 			color: '#ffd71e'
	// 		},
	// 		title: 'Média de comentários por dia',
	// 		value: this.dataOverview.commentsAverage,
	// 		text: 'Seus posts tiveram ' + this.dataOverview.comments + ' comentários no período, ' +
	// 				'com uma média de ' + this.dataOverview.commentsAverage + ' comentários por dia'
	// 	}

	// }

	// averageLikesPerDay() {

	// 	this.averageLikesPerDayFooter = {
	// 		icon: {
	// 			icon: 'fa-area-chart',
	// 			color: '#ffd71e'
	// 		},
	// 		title: 'Média de likes por dia',
	// 		value: this.dataOverview.likesAverage,
	// 		text: 'Seus posts tiveram ' + this.dataOverview.likes + ' likes no período, ' +
	// 				'com uma média de ' + this.dataOverview.likesAverage + ' likes por dia'
	// 	}

	// }

	//COMENTÁRIOS DD/MM
	getValuesCommentsPerDay() {
		if ((this.dataOverview != undefined) && (this.dataOverview.commentsPerDay != undefined)) {

			let dataColumnCommentsFollowers 	: any = [];

			this.dataOverview.commentsPerDay.map(
				fav => {

					dataColumnCommentsFollowers.push({
						x: this.formatDate(fav.date), 
						y: fav.value,
						color: '#00d6f6'
					});
				}
			);

			this.commentsPerDay = [
				{
					key: 'Comments',
					values: dataColumnCommentsFollowers
				},
			];
		}
	}

	//LIKES E COMENTÁRIOS POR HORA
	getValuesLikesCommentsPerHour() {
		let dataLikes 		: any = [];
		let dataComments 	: any = [];

		this.dataOverview.likesPerHourSimpleData.map(
			fav => {
				dataLikes.push([this.getDateFromHour(fav.key), fav.value]);
			}
		);

		this.dataOverview.commentsPerHourSimpleData.map(
			fav => {
				dataComments.push([this.getDateFromHour(fav.key), fav.value]);
			}
		);

		this.likesCommentsPerHour = [
			{
				"key": "Comments",
				"values": dataComments
			},
			{
				"key": "Likes",
				"values": dataLikes }
		];
	}

	//HASHTAGS
	getValuesInstagramTopPostTags() {

		if ((this.dataOverview != undefined) && (this.dataOverview.instagramTopPostTags != undefined)) {

			let dataPostTags = [];
			this.dataOverview.instagramTopPostTags.map(
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

	//SIDE TEXT FOR CHARTS
	getTotalFollowers() {
		this.totalFollowersPeakAside = {
			icon: {
				icon: 'fa-user',
				color: '#00d6f6'
			},
			title: 'TOTAL DE SEGUIDORES',
			value: this.dataOverview.followers
		}
	}

	getNewFollowers() {
		this.newFollowersPeakAside = {
			icon: {
				icon: 'fa-plus-circle',
				color: '#20ffa3'
			},
			title: 'NOVOS SEGUIDORES',
			value: this.dataOverview.instagramFollowerTrend.newFollowers
		}
	}

	getUnFollowers() {
		this.unFollowersPeakAside = {
			icon: {
				icon: 'fa-minus-circle',
				color: '#f7554e'
			},
			title: 'SEGUIDORES PERDIDOS',
			value: this.dataOverview.instagramFollowerTrend.unfollowers
		}
	}

	getFollowersBalance() {
		this.followersBalancePeakAside = {
			icon: {
				icon: 'fa-area-chart',
				color: '#ffd71e'
			},
			title: 'SALDO DE SEGUIDORES',
			value: this.dataOverview.instagramFollowerTrend.balanceFollowers
		}
	}

	getComplementPublicAge() {

		this.complementPublicAge = {
			icon: {
				icon: 'fa-arrow-circle-up',
				color: '#ffd71e'
			},
			title: 'Maior faixa etária',
			value: this.dataOverview.audiencesAveragePublicAge.key.replace("-", " to "),
			text: 'Most of the page’s audience is between '
			+ this.dataOverview.audiencesAveragePublicAge.key.replace("-", " and ") + ' years old, '
			+ 'representing ' + Math.round(this.getPercentValue(this.dataOverview.audiencesAveragePublicAge.value, this.dataOverview.followers)) + '% of the total.'
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
	// 		value: this.dataOverview.audiencesAveragePublicAge.key.replace("-", " a "),
	// 		text: 'A maior parte do público do perfil tem entre '
	// 		+ this.dataOverview.audiencesAveragePublicAge.key.replace("-", " e ") + ' anos, '
	// 		+ 'representando ' + Math.round(this.getPercentValue(this.dataOverview.audiencesAveragePublicAge.value, this.dataOverview.followers)) + '% do total.'
	// 	}
	// }

	getComplementPublicGender() {
		
		let legend = [
			{icon: {icon: 'fa-female', color:'#f74e7e'}, value: Math.round(this.getPercentValue(this.dataOverview.audiencesAveragePublicGender[1].value, this.dataOverview.followers))+'%'},
			{icon: {icon: 'fa-male', color:'#00d6f6'}, value: Math.round(this.getPercentValue(this.dataOverview.audiencesAveragePublicGender[0].value, this.dataOverview.followers))+'%'}
		];

		this.complementPublicGender = {
			icon: {
				icon: 'fa-venus-mars',
				color: '#f6cb00'
			},
			chart: {
				data : [
					{ 
						"label": "Men",
						"value" : Math.round(this.getPercentValue(this.dataOverview.audiencesAveragePublicGender[0].value, this.dataOverview.followers)), 
						"color": "#00d6f6"
					}, 
					{ 
						"label": "Women",
						"value" : Math.round(this.getPercentValue(this.dataOverview.audiencesAveragePublicGender[1].value, this.dataOverview.followers)), 
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

	interactionsRanking() {

		this.firstInteractionRanking = {
			icon: {
				icon: 'fa-clock-o',
				color: '#f6cb00'
			},
			title: '1° Horário em interações',
			value: this.formatHour(Number(this.dataOverview.instagramInteractionsRanking[0].hour)) 
				+ ' at ' + this.formatHour((Number(this.dataOverview.instagramInteractionsRanking[0].hour) + 1)), 
			text: this.dataOverview.instagramInteractionsRanking[0].percentage + '% of the total.'
		}

		// this.firstInteractionRanking = {
		// 	icon: {
		// 		icon: 'fa-clock-o',
		// 		color: '#f6cb00'
		// 	},
		// 	title: '1° Horário em interações',
		// 	value: this.formatHour(Number(this.dataOverview.instagramInteractionsRanking[0].hour)) 
		// 		+ ' às ' + this.formatHour((Number(this.dataOverview.instagramInteractionsRanking[0].hour) + 1)), 
		// 	text: this.dataOverview.instagramInteractionsRanking[0].percentage + '% do total.'
		// }

		this.secondInteractionRanking = {
			icon: {
				icon: 'fa-clock-o',
				color: '#f6cb00'
			},
			title: '2° Horário em interações',
			value: this.formatHour(Number(this.dataOverview.instagramInteractionsRanking[1].hour)) 
				+ ' at ' + this.formatHour((Number(this.dataOverview.instagramInteractionsRanking[1].hour) + 1)), 
			text: this.dataOverview.instagramInteractionsRanking[1].percentage + '% of the total.'
		}

		// this.secondInteractionRanking = {
		// 	icon: {
		// 		icon: 'fa-clock-o',
		// 		color: '#f6cb00'
		// 	},
		// 	title: '2° Horário em interações',
		// 	value: this.formatHour(Number(this.dataOverview.instagramInteractionsRanking[1].hour)) 
		// 		+ ' às ' + this.formatHour((Number(this.dataOverview.instagramInteractionsRanking[1].hour) + 1)), 
		// 	text: this.dataOverview.instagramInteractionsRanking[1].percentage + '% do total.'
		// }

		this.thirdInteractionRanking = {
			icon: {
				icon: 'fa-clock-o',
				color: '#f6cb00'
			},
			title: '3° Horário em interações',
			value: this.formatHour(Number(this.dataOverview.instagramInteractionsRanking[2].hour)) 
				+ ' to ' + this.formatHour((Number(this.dataOverview.instagramInteractionsRanking[2].hour) + 1)), 
			text: this.dataOverview.instagramInteractionsRanking[2].percentage + '% of the total.'
		}

		// this.thirdInteractionRanking = {
		// 	icon: {
		// 		icon: 'fa-clock-o',
		// 		color: '#f6cb00'
		// 	},
		// 	title: '3° Horário em interações',
		// 	value: this.formatHour(Number(this.dataOverview.instagramInteractionsRanking[2].hour)) 
		// 		+ ' às ' + this.formatHour((Number(this.dataOverview.instagramInteractionsRanking[2].hour) + 1)), 
		// 	text: this.dataOverview.instagramInteractionsRanking[2].percentage + '% do total.'
		// }
	}

	timeWithMoreFansOnline() {

		let onlinePercentage = 0;
		let hourMostOnline = 0;
		this.dataOverview.audiencesOnlineFollowersByHour.map(
			fav => {
				if (this.getPercentValue(fav.value, this.dataOverview.followers) > onlinePercentage) {

					onlinePercentage 	= Math.round(this.getPercentValue(fav.value, this.dataOverview.followers));
					hourMostOnline 		= fav.label;
				}
			}
		)

		this.timeWithMoreFansOnlineFooter = {
			icon: {
				icon: 'fa-clock-o',
				color: '#f6cb00'
			},
			title: 'Horário com mais seguidores online',
			value: this.formatHour(Number(hourMostOnline)) + ' at ' + this.formatHour((Number(hourMostOnline) + 1)), 
			smallText: '(' + onlinePercentage + '%)', 
			text: onlinePercentage + '% of the audience\page fans were online.'
		}

		// this.timeWithMoreFansOnlineFooter = {
		// 	icon: {
		// 		icon: 'fa-clock-o',
		// 		color: '#f6cb00'
		// 	},
		// 	title: 'Horário com mais seguidores online',
		// 	value: this.formatHour(Number(hourMostOnline)) + ' às ' + this.formatHour((Number(hourMostOnline) + 1)), 
		// 	smallText: '(' + onlinePercentage + '%)', 
		// 	text: onlinePercentage + '% dos seguidores estiveram conectados nesta faixa.'
		// }
	}

	weekDayWithMoreFansOnline() {

		let onlinePercentage : number = 0;
		let dayMostOnline = "";
		this.dataOverview.audiencesOnlineFollowersByDay.map(
			fav => {
				if (this.getPercentValue(fav.value, this.dataOverview.followers) > onlinePercentage) {

					onlinePercentage 	= Math.round(this.getPercentValue(fav.value, this.dataOverview.followers));
					dayMostOnline 		= fav.label;
				}
			}
		);

		this.weekDayWithMoreFansOnlineFooter = {
			icon: {
				icon: 'fa-calendar',
				color: '#f6cb00'
			},
			title: 'Dia com maior alcance',
			value: this.weekDaysFull[dayMostOnline], 
			smallText: '(' + onlinePercentage + '%)', 
			text: (onlinePercentage + '% of the audience\page fans were online on ' + this.weekDaysFull[dayMostOnline])
		}

		// this.weekDayWithMoreFansOnlineFooter = {
		// 	icon: {
		// 		icon: 'fa-calendar',
		// 		color: '#f6cb00'
		// 	},
		// 	title: 'Dia com maior alcance',
		// 	value: this.weekDaysFull[dayMostOnline], 
		// 	smallText: '(' + onlinePercentage + '%)', 
		// 	text: (onlinePercentage + '% dos seus seguidores estiveram conectados na ' + this.weekDaysFull[dayMostOnline])
		// }
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

	formatDate(date){
		date = new Date(date * 1000);
		return this.datePipe.transform(date, 'MM/dd');
	}

	formatHour(hour) {
		hour = hour == 24 ? 0 : hour;
		
		return (hour < 10 ? '0' + hour : hour) + ':00'; 
	}

	xAxisTickFunctionMonth(d){
		let monthNames : string[] = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
		// let monthNames : string[] = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];
		return monthNames[d];
	}

	xAxisTickFunctionDay(d){
		let weekDays: string[] = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
		// let weekDays: string[] = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'];
		return weekDays[d];
	}

	createTdValue(img, text) {
		return '<span><img height="28" src="' + img + '" /> '+ text +'</span>';
	}

}
