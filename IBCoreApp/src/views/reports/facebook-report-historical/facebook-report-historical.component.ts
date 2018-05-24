import { DatePipe, DecimalPipe } from '@angular/common';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { PieChartComponent } from 'core/componentes/charts/pie-chart/pie-chart.component';
import { StackedColumnChartComponent } from 'core/componentes/charts/stacked-column-chart/stacked-column-chart.component';
import { AbbreviateNumberPipe } from 'core/pipes/abbreviateNumber.pipe';
import { ControlService } from 'core/services/control/control.service';
import { FacebookService } from 'core/services/facebook/facebook.service';
import { NotificationService } from 'core/services/notification/notification.service';
import { IAsidePieChart, IAsideTextIcon } from 'modelo/complements/Complements';
import { Notification } from 'modelo/notification/Notification';

@Component({
	selector: 'facebook-report-historical',
	templateUrl: './facebook-report-historical.component.html',
	styleUrls: ['./facebook-report-historical.component.css']
})
export class FacebookReportHistoricalComponent implements OnInit {

	@Input() profile;
	@Input() reportFacebookHistorical: any;
	@Input() period;
	account;

	@ViewChild('aboutReachedUsersChart')
	aboutReachedUsersChart: StackedColumnChartComponent;

	@ViewChild('womenMenReachedUsersPieChart')
	womenMenReachedUsersPieChart: PieChartComponent;

	fanPageGrowthData;
	aboutReachedUsersData;
	aboutYourFansData;
	reactionsPerMonthData;
	commentsPerMonthData;
	sharesPerMonthData;
	clicksPerMonthData;
	citiesMoreReachedData;
	citiesMoreFansData;
	mostInfluentialFansData1;
	mostInfluentialFansData2;
	fansLikeSourceData1;
	fansLikeSourceData2;
	externalReferencesData;

	majorAgeGroupReachedUsersFooter: IAsideTextIcon;
	majorAgeGroupFansFooter: IAsideTextIcon;

	majorFanPageGrowthFooter: IAsideTextIcon;
	minorFanPageGrowthFooter: IAsideTextIcon;

	likesFooter: IAsideTextIcon;
	loveFooter: IAsideTextIcon;
	hahaFooter: IAsideTextIcon;
	wowFooter: IAsideTextIcon;
	sorryFooter: IAsideTextIcon;
	angerFooter: IAsideTextIcon;
	commentsFooter: IAsideTextIcon;
	sharesFooter: IAsideTextIcon;
	clicksFooter: IAsideTextIcon;

	womenMenReachedUsersPie: IAsidePieChart;
	womenMenFansPie: IAsidePieChart;

	legendsReactionsPerMonth = {
		legends: true,
		color: ["#00d6f6", "#f82d4e", "#f6cb00", "#ad43ff", "#00f6bf", "#ff7800"]
	}

	legendsAboutYourFans = {
		legends: true,
		color: ['#00d6f6', '#f74e7e', '#c4c4c4']
	};
	
	legendsAboutReachedUsers = {
		legend: true,
		color: ['#00d6f6', '#f74e7e']
	}

	colorsReactionsPerMonth = ["#00d6f6", "#f82d4e", "#f6cb00", "#ad43ff", "#00f6bf", "#ff7800"];

	// months: string[] = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
	months: string[] = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

	showAboutYourFans: boolean = true;
	showCitiesMoreFans: boolean = true;

	aboutReachedUsersChartRendered: boolean = false;

	constructor(private facebookService: FacebookService,
		private controlService: ControlService,
		private datePipe: DatePipe,
		private decimalPipe: DecimalPipe,
		private abbreviateNumberPipe: AbbreviateNumberPipe,
		private notificationService: NotificationService) { }

	ngOnInit() {
		this.mountCharts();
		this.account = this.controlService.account;
	}

	ngAfterViewChecked() {

		if (!this.showAboutYourFans && !this.aboutReachedUsersChartRendered) {
			this.aboutReachedUsersChartRendered = true;
			this.aboutReachedUsersChart.chart.ngNvD3.chart.update();
			this.womenMenReachedUsersPieChart.chart.chart.ngNvD3.chart.update()
		}
	}

	mountCharts() {
		
		this.fanPageGrowth();
		this.majorFanPageGrowth();
		this.minorFanPageGrowth();
		this.aboutReachedUsers();
		this.majorAgeGroupReachedUsers();
		this.womenMenReachedUsers();
		this.aboutYourFans();
		this.majorAgeGroupFans();
		this.womenMenFans();
		this.reactionsPerMonth();
		this.likesTotal();
		this.loveTotal();
		this.hahaTotal();
		this.wowTotal();
		this.sorryTotal();
		this.angerTotal();
		this.commentsPerMonth();
		this.commentsTotal();
		this.sharesPerMonth();
		this.sharesTotal();
		this.clicksPerMonth();
		this.clicksTotal();
		this.citiesMoreFans();
		this.citiesMoreReached();
		this.mostInfluentialFans();
		this.fansLikeSource();
		this.externalReferences();
	}

	getXLS() {
		this.facebookService.getHistoricalReportXLS(this.profile.idProfile)
			.subscribe(
			result => this.downloadXLS(result),
			error => console.log(error)
			);
	}

	downloadXLS(result) {
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

	formatAgeGroupDisplay(initialAgeGroup, endAgeGroup) {
		if (Number(initialAgeGroup) < 65) {
			return initialAgeGroup + '-' + endAgeGroup;
		} else {
			return initialAgeGroup + '+';
		}
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

	showPublicDemographicInfo(event: any) {

		if (event.target.value == 'yes') {
			this.showAboutYourFans = true;
		} else {
			this.showAboutYourFans = false;
		}
	}

	showCitiesList(event: any) {

		if (event.target.value == 'fans') {
			this.showCitiesMoreFans = true;
		} else {
			this.showCitiesMoreFans = false;
		}
	}

	createTdValue(img, text, profileUrl) {
		return '<a href="' + profileUrl + '" target="_blank"><span><img height="28" src="' + img + '" /> '+ text +'</span></a>';
	}

	// getLikeSourceName(likeSource) {

	// 	if (likeSource == 'pageFansPageProfile') {
	// 		return "Na página";
	// 	} else if (likeSource == 'pageFansHovercard') {
	// 		return "No pop-up";
	// 	} else if (likeSource == 'pageFansPhotoSnowlift') {
	// 		return "Na foto";
	// 	} else if (likeSource == 'pageFansMobile') {
	// 		return "Mobile";
	// 	} else if (likeSource == 'pageFansExternalConnect') {
	// 		return "Caixa \"curtir\" e botão \"curtir\"";
	// 	} else if (likeSource == 'pageFansAds') {
	// 		return "Anúncios e histórias patrocinadas Desktop";
	// 	} else if (likeSource == 'pageFansLikeStory') {
	// 		return "Indicação de amigos";
	// 	} else if (likeSource == 'pageFansFromPages') {
	// 		return "Páginas que curtiram outras páginas";
	// 	} else if (likeSource == 'pageFansSearch') {
	// 		return "Resultados da busca";
	// 	} else if (likeSource == 'pageFansFromRecommendations') {
	// 		return "Recomendações do Facebook";
	// 	} else if (likeSource == 'pageFansMobileAds') {
	// 		return "Anúncios e histórias patrocinadas Mobile";
	// 	} else if (likeSource == 'pageFansApi') {
	// 		return "Aplicações externas";
	// 	} else if (likeSource == 'pageFansPageInviteEscapeHatchFinch') {
	// 		return "Convite";
	// 	} else if (likeSource == 'pageFansPageSuggestion') {
	// 		return "Convite dos administradores";
	// 	} else if (likeSource == 'pageFansFanContextStory') {
	// 		return "Histórias de interações de amigos";
	// 	} else if (likeSource == 'pageFansRegistration') {
	// 		return "Pessoas adicionadas como administradores";
	// 	} else if (likeSource == 'pageFansPageTimeline') {
	// 		return "Recomendações na Timeline";
	// 	} else if (likeSource == 'pageFansWizardSuggestion') {
	// 		return "Recomendações para novos usuários";
	// 	} else if (likeSource == 'pageFansFromLikeSuggestions') {
	// 		return "Sugestões ao curtir novas páginas";
	// 	} else if (likeSource == 'pageFansSponsoredStory') {
	// 		return "Posts impulsionados";
	// 	} else {
	// 		return "";
	// 	}

	// }

	fansLikeSource() {

		let fansLikeSource: any = this.reportFacebookHistorical.fansLikeSource;
		let dataFansLikeSource1 = [];
		let dataFansLikeSource2 = [];

		let cols = [{ value: 'Origem', class: 'left' }, { value: 'Curtir', class: 'right', width: '130px' }];

		for (let i = 0; i < fansLikeSource.length && i < 10; i++) {
			dataFansLikeSource1.push([
				{ value: fansLikeSource[i].location, class: 'left' },
				{ value: fansLikeSource[i].value, class: 'right' }
			]);
		}

		this.fansLikeSourceData1 = {
			cols: cols,
			rows: dataFansLikeSource1
		}

		if (fansLikeSource.length > 10) {
			for (let i = 10; i < fansLikeSource.length && i < 20; i++) {
				dataFansLikeSource2.push([
					{ value: fansLikeSource[i].location, class: 'left' },
					{ value: fansLikeSource[i].value, class: 'right' }
				]);
			}

			this.fansLikeSourceData2 = {
				cols: cols,
				rows: dataFansLikeSource2
			}
		} else {
			this.fansLikeSourceData2 = [];
		}

	}

	externalReferences() {

		let externalReferences: any = this.reportFacebookHistorical.externalReferences;
		let dataExternalReferences = [];

		for (let i = 0; i < externalReferences.length && i < 10; i++) {
			dataExternalReferences.push([
				{ value: '<a href="' + externalReferences[i].location + '" target="_blank">' + externalReferences[i].location + '</a>', class: 'left' },
				{ value: externalReferences[i].value, class: 'right' }
			]);
		}

		this.externalReferencesData = {
			cols: [
				{ value: 'Referência', class: 'left' }, { value: 'Número', class: 'right', width: '100px' }
			],
			rows: dataExternalReferences
		}
	}

	mostInfluentialFans() {

		let influentials: any = this.reportFacebookHistorical.mostInfluentialFans;
		let dataInfluentials1 = [];
		let dataInfluentials2 = [];

		let cols = [{ value: 'Usuário', class: 'left' }, { value: 'Interações', class: 'right', width: '130px' }];

		for (let i = 0; i < influentials.length && i < 10; i++) {
			dataInfluentials1.push([
				{ value: this.createTdValue(influentials[i].imageProfile, influentials[i].name, influentials[i].profileUrl), class: 'left' },
				{ value: this.decimalPipe.transform(influentials[i].comments + influentials[i].likes, '1.0-0'), class: 'right' }
			]);
		}

		this.mostInfluentialFansData1 = {
			cols: cols,
			rows: dataInfluentials1
		}

		if (influentials.length > 10) {
			for (let i = 10; i < influentials.length && i < 20; i++) {
				dataInfluentials2.push([
					{ value: this.createTdValue(influentials[i].imageProfile, influentials[i].name, influentials[i].profileUrl), class: 'left' },
					{ value: this.decimalPipe.transform(influentials[i].comments + influentials[i].likes, '1.0-0'), class: 'right' }
				]);
			}

			this.mostInfluentialFansData2 = {
				cols: cols,
				rows: dataInfluentials2
			}
		} else {
			this.mostInfluentialFansData2 = [];
		}

	}

	citiesMoreFans() {

		let cities: any = this.reportFacebookHistorical.citiesMoreFans;
		let dataCities = [];

		for (let i = 0; i < cities.length && i < 10; i++) {
			dataCities.push([
				{ value: cities[i].location, class: 'left' },
				{ value: cities[i].value, class: 'right' }
			]);
		}

		this.citiesMoreFansData = {
			cols: [
				{ value: 'Cidade', class: 'left' }, { value: 'Curtir', class: 'right', width: '100px' }
			],
			rows: dataCities
		}
	}

	citiesMoreReached() {

		let cities: any = this.reportFacebookHistorical.citiesMoreReached;
		let dataCities = [];

		for (let i = 0; i < cities.length && i < 10; i++) {
			dataCities.push([
				{ value: cities[i].location, class: 'left' },
				{ value: cities[i].value, class: 'right' }
			]);
		}

		this.citiesMoreReachedData = {
			cols: [
				{ value: 'Cidade', class: 'left' }, { value: 'Curtir', class: 'right', width: '100px' }
			],
			rows: dataCities
		}
	}

	clicksPerMonth() {

		this.clicksPerMonthData = [
			{
				"key": 'Clicks',
				"values": this.reportFacebookHistorical.clicksPerMonth.map(
					fav => {
						return [new Date(fav.year, fav.month - 1, 1).getTime(), fav.sum]
					})
			}
		];
	}

	sharesPerMonth() {

		this.sharesPerMonthData = [
			{
				"key": 'Shares',
				"values": this.reportFacebookHistorical.sharesPerMonth.map(
					fav => {
						return [new Date(fav.year, fav.month - 1, 1).getTime(), fav.sum]
					})
			}
		];
	}

	commentsPerMonth() {

		this.commentsPerMonthData = [
			{
				"key": 'Comments',
				"values": this.reportFacebookHistorical.commentsPerMonth.map(
					fav => {
						return [new Date(fav.year, fav.month - 1, 1).getTime(), fav.sum]
					})
			}
		];
	}

	clicksTotal() {

		this.clicksFooter = {
			icon: {
				icon: 'fa-click',
				color: '#00d6f6'
			},
			title: 'cliques',
			value: this.reportFacebookHistorical.totalClicks,
			text: 'Your posts registered more clicks on '
			+ this.months[this.reportFacebookHistorical.majorClicksMonth.month - 1] + '/'
			+ this.reportFacebookHistorical.majorClicksMonth.year
			+ ' with ' 
			+ this.abbreviateNumberPipe.transform(this.reportFacebookHistorical.majorClicksMonth.sum)
			+ ' clicks.'
		}
	}

	// clicksTotal() {

	// 	this.clicksFooter = {
	// 		icon: {
	// 			icon: 'fa-click',
	// 			color: '#00d6f6'
	// 		},
	// 		title: 'cliques',
	// 		value: this.reportFacebookHistorical.totalClicks,
	// 		text: this.months[this.reportFacebookHistorical.majorClicksMonth.month - 1] + ' de '
	// 		+ this.reportFacebookHistorical.majorClicksMonth.year
	// 		+ ' registrou o maior índice de cliques, com ' 
	// 		+ this.abbreviateNumberPipe.transform(this.reportFacebookHistorical.majorClicksMonth.sum)
	// 		+ ' cliques em suas postagens.'
	// 	}
	// }

	sharesTotal() {

		this.sharesFooter = {
			icon: {
				icon: 'fa-share',
				color: '#00d6f6'
			},
			title: 'compartilhamentos',
			value: this.reportFacebookHistorical.totalShares,
			text: 'Your posts registered more shares on ' 
			+ this.months[this.reportFacebookHistorical.majorSharesMonth.month - 1] + '/'
			+ this.reportFacebookHistorical.majorSharesMonth.year
			+ ' with ' 
			+ this.abbreviateNumberPipe.transform(this.reportFacebookHistorical.majorSharesMonth.sum)
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
	// 		value: this.reportFacebookHistorical.totalShares,
	// 		text: this.months[this.reportFacebookHistorical.majorSharesMonth.month - 1] + ' de '
	// 		+ this.reportFacebookHistorical.majorSharesMonth.year
	// 		+ ' registrou o maior índice de compartilhamentos, com ' 
	// 		+ this.abbreviateNumberPipe.transform(this.reportFacebookHistorical.majorSharesMonth.sum)
	// 		+ ' compartilhamentos de suas postagens.'
	// 	}
	// }

	commentsTotal() {

		this.commentsFooter = {
			icon: {
				icon: 'fa-comments',
				color: '#00d6f6'
			},
			title: 'comentários',
			value: this.reportFacebookHistorical.totalComments,
			text: 'Your posts registered more comments on ' 
			+ this.months[this.reportFacebookHistorical.majorCommentsMonth.month - 1] + '/'
			+ this.reportFacebookHistorical.majorCommentsMonth.year
			+ ' with ' 
			+ this.abbreviateNumberPipe.transform(this.reportFacebookHistorical.majorCommentsMonth.sum)
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
	// 		value: this.reportFacebookHistorical.totalComments,
	// 		text: this.months[this.reportFacebookHistorical.majorCommentsMonth.month - 1] + ' de '
	// 		+ this.reportFacebookHistorical.majorCommentsMonth.year
	// 		+ ' registrou o maior índice de comentários, com ' 
	// 		+ this.abbreviateNumberPipe.transform(this.reportFacebookHistorical.majorCommentsMonth.sum)
	// 		+ ' comentários feitos em suas postagens.'
	// 	}
	// }

	angerTotal() {

		this.angerFooter = {
			title: 'grr',
			value: this.reportFacebookHistorical.totalAnger,
			smallText: '(' + this.reportFacebookHistorical.angerPercentage + '%)',
			iconUrl: this.getIconUrl('anger')
		}
	}

	sorryTotal() {

		this.sorryFooter = {
			title: 'triste',
			value: this.reportFacebookHistorical.totalSorry,
			smallText: '(' + this.reportFacebookHistorical.sorryPercentage + '%)',
			iconUrl: this.getIconUrl('sorry')
		}
	}

	wowTotal() {

		this.wowFooter = {
			title: 'uau',
			value: this.reportFacebookHistorical.totalWow,
			smallText: '(' + this.reportFacebookHistorical.wowPercentage + '%)',
			iconUrl: this.getIconUrl('wow')
		}
	}

	hahaTotal() {

		this.hahaFooter = {
			title: 'haha',
			value: this.reportFacebookHistorical.totalHaha,
			smallText: '(' + this.reportFacebookHistorical.hahaPercentage + '%)',
			iconUrl: this.getIconUrl('haha')
		}
	}

	loveTotal() {

		this.loveFooter = {
			title: 'amei',
			value: this.reportFacebookHistorical.totalLove,
			smallText: '(' + this.reportFacebookHistorical.lovePercentage + '%)',
			iconUrl: this.getIconUrl('love')
		}
	}

	likesTotal() {

		this.likesFooter = {
			title: 'curtir',
			value: this.reportFacebookHistorical.totalLikes,
			smallText: '(' + this.reportFacebookHistorical.likesPercentage + '%)',
			iconUrl: this.getIconUrl('likes')
		}
	}

	reactionsPerMonth() {

		this.reactionsPerMonthData = [{
			"key": "Like",
			"values": this.reportFacebookHistorical.likesPerMonth.map(
				fav => {
					return [new Date(fav.year, fav.month - 1, 1).getTime(), fav.sum]
				})
		}, {
			"key": "Love",
			"values": this.reportFacebookHistorical.lovePerMonth.map(
				fav => {
					return [new Date(fav.year, fav.month - 1, 1).getTime(), fav.sum]
				})
		}, {
			"key": "Haha",
			"values": this.reportFacebookHistorical.hahaPerMonth.map(
				fav => {
					return [new Date(fav.year, fav.month - 1, 1).getTime(), fav.sum]
				})
		}, {
			"key": "Wow",
			"values": this.reportFacebookHistorical.wowPerMonth.map(
				fav => {
					return [new Date(fav.year, fav.month - 1, 1).getTime(), fav.sum]
				})
		}, {
			"key": "Sad",
			"values": this.reportFacebookHistorical.sorryPerMonth.map(
				fav => {
					return [new Date(fav.year, fav.month - 1, 1).getTime(), fav.sum]
				})
		}, {
			"key": "Angry",
			"values": this.reportFacebookHistorical.angerPerMonth.map(
				fav => {
					return [new Date(fav.year, fav.month - 1, 1).getTime(), fav.sum]
				})
		}
		];
	}

	womenMenFans() {

		let legend = [
			{ icon: { icon: 'fa-male', color: '#00d6f6' }, value: this.reportFacebookHistorical.menFansPerc + '%' },
			{ icon: { icon: 'fa-female', color: '#f74e7e' }, value: this.reportFacebookHistorical.womenFansPerc + '%' }
		];

		this.womenMenFansPie = {
			icon: {
				icon: 'fa-venus-mars',
				color: '#f6cb00'
			},
			chart: {
				data: [
					{
						"label": "Men",
						"value": this.reportFacebookHistorical.menFansPerc,
						"color": "#00d6f6"
					},
					{
						"label": "Women",
						"value": this.reportFacebookHistorical.womenFansPerc,
						"color": "#f74e7e"
					}
				],
				height: 70,
				width: 70,
				isDonut: true,
				legend: legend
			},
			title: 'Divisão dos fãs',
			orientation: 'center'
		}
	}

	womenMenReachedUsers() {

		let legend = [
			{ icon: { icon: 'fa-male', color: '#00d6f6' }, value: this.reportFacebookHistorical.menReachedUsersPerc + '%' },
			{ icon: { icon: 'fa-female', color: '#f74e7e' }, value: this.reportFacebookHistorical.womenReachedUsersPerc + '%' }
		];

		this.womenMenReachedUsersPie = {
			icon: {
				icon: 'fa-venus-mars',
				color: '#f6cb00'
			},
			chart: {
				data: [
					{
						"label": "Men",
						"value": this.reportFacebookHistorical.menReachedUsersPerc,
						"color": "#00d6f6"
					},
					{
						"label": "Women",
						"value": this.reportFacebookHistorical.womenReachedUsersPerc,
						"color": "#f74e7e"
					}
				],
				height: 70,
				width: 70,
				isDonut: true,
				legend: legend
			},
			title: 'Divisão do público',
			orientation: 'center'
		}
	}

	majorAgeGroupFans() {

		this.majorAgeGroupFansFooter = {
			icon: {
				icon: 'fa-arrow-circle-up',
				color: '#ffd71e'
			},
			title: 'Larger age range',
			value: this.reportFacebookHistorical.majorAgeGroupFans.initialAgeGroup + ' to ' + this.reportFacebookHistorical.majorAgeGroupFans.endAgeGroup,
			text: 'Most of the page’s audience is between '
			+ this.reportFacebookHistorical.majorAgeGroupFans.initialAgeGroup + ' and '
			+ this.reportFacebookHistorical.majorAgeGroupFans.endAgeGroup + ' years old, '
			+ 'representing ' + this.reportFacebookHistorical.majorAgeGroupFans.percentage + '% of the total.'
		}
	}

	// **Método não traduzido**
	// majorAgeGroupFans() {

	// 	this.majorAgeGroupFansFooter = {
	// 		icon: {
	// 			icon: 'fa-arrow-circle-up',
	// 			color: '#ffd71e'
	// 		},
	// 		title: 'Maior faixa etária',
	// 		value: this.reportFacebookHistorical.majorAgeGroupFans.initialAgeGroup + ' a ' + this.reportFacebookHistorical.majorAgeGroupFans.endAgeGroup,
	// 		text: 'A maior parte dos fãs da página tem entre '
	// 		+ this.reportFacebookHistorical.majorAgeGroupFans.initialAgeGroup + ' e '
	// 		+ this.reportFacebookHistorical.majorAgeGroupFans.endAgeGroup + ' anos, '
	// 		+ 'representando ' + this.reportFacebookHistorical.majorAgeGroupFans.percentage + '% do total.'
	// 	}
	// }

	majorAgeGroupReachedUsers() {

		this.majorAgeGroupReachedUsersFooter = {
			icon: {
				icon: 'fa-arrow-circle-up',
				color: '#ffd71e'
			},
			title: 'Larger age range',
			value: this.reportFacebookHistorical.majorAgeGroupReachedUsers.initialAgeGroup + ' to ' + this.reportFacebookHistorical.majorAgeGroupReachedUsers.endAgeGroup,
			text: 'Most of the page’s audience is between '
			+ this.reportFacebookHistorical.majorAgeGroupReachedUsers.initialAgeGroup + ' and '
			+ this.reportFacebookHistorical.majorAgeGroupReachedUsers.endAgeGroup + ' years old, '
			+ 'representing ' + this.reportFacebookHistorical.majorAgeGroupReachedUsers.percentage + '% of the total.'
		}
	}

	// **Método não traduzido**
	// majorAgeGroupReachedUsers() {

	// 	this.majorAgeGroupReachedUsersFooter = {
	// 		icon: {
	// 			icon: 'fa-arrow-circle-up',
	// 			color: '#ffd71e'
	// 		},
	// 		title: 'Maior faixa etária',
	// 		value: this.reportFacebookHistorical.majorAgeGroupReachedUsers.initialAgeGroup + ' a ' + this.reportFacebookHistorical.majorAgeGroupReachedUsers.endAgeGroup,
	// 		text: 'A maior parte do público da página tem entre '
	// 		+ this.reportFacebookHistorical.majorAgeGroupReachedUsers.initialAgeGroup + ' e '
	// 		+ this.reportFacebookHistorical.majorAgeGroupReachedUsers.endAgeGroup + ' anos, '
	// 		+ 'representando ' + this.reportFacebookHistorical.majorAgeGroupReachedUsers.percentage + '% do total.'
	// 	}
	// }

	aboutYourFans() {

		let male = this.reportFacebookHistorical.aboutYourMaleFans.map(
			fav => {
				return {
					x: this.formatAgeGroupDisplay(fav.initialAgeGroup, fav.endAgeGroup),
					y: fav.value,
					color: '#00d6f6'
				}
			});

		male.sort((a, b) => a.x > b.x ? 1 : 0);

		let female = this.reportFacebookHistorical.aboutYourMaleFans.map(
			fav => {
				return {
					x: this.formatAgeGroupDisplay(fav.initialAgeGroup, fav.endAgeGroup),
					y: fav.value,
					color: '#f74e7e'
				}
			})

		female.sort((a, b) => a.x > b.x ? 1 : 0);

		this.aboutYourFansData = [{
			"key": "Male",
			"values": male
		}, {
			"key": "Female",
			"values": female
		}
		];
	}

	aboutReachedUsers() {
		let male = this.reportFacebookHistorical.aboutReachedMaleUsers.map(
			fav => {
				return {
					x: this.formatAgeGroupDisplay(fav.initialAgeGroup, fav.endAgeGroup),
					y: fav.value,
					color: '#00d6f6'
				}
			});

		male.sort((a, b) => a.x > b.x ? 1 : 0);

		let female = this.reportFacebookHistorical.aboutReachedFemaleUsers.map(
			fav => {
				return {
					x: this.formatAgeGroupDisplay(fav.initialAgeGroup, fav.endAgeGroup),
					y: fav.value,
					color: '#f74e7e'
				}
			})

		female.sort((a, b) => a.x > b.x ? 1 : 0);

		this.aboutReachedUsersData = [{
			"key": "Male",
			"values": male
		}, {
			"key": "Female",
			"values": female
		}
		];
	}

	fanPageGrowth() {

		let accumulatedFanPageGrowth = this.reportFacebookHistorical.fansUntilOneYearAgo;

		this.fanPageGrowthData = [{
			"key": "Fans",
			"values": this.reportFacebookHistorical.fanPageGrowth.map(
				fav => {
					accumulatedFanPageGrowth += fav.sum;
					return [new Date(fav.year, fav.month - 1, 1).getTime(), accumulatedFanPageGrowth]
				})
		}
		];
	}

	majorFanPageGrowth() {

		let majorMonth = this.months[this.reportFacebookHistorical.majorFanPageGrowth.month - 1];

		this.majorFanPageGrowthFooter = {
			icon: {
				icon: 'fa-arrow-circle-up',
				color: '#00d6f6'
			},
			title: 'Mês com maior crescimento',
			value: majorMonth,
			text: majorMonth + ' of ' + this.reportFacebookHistorical.majorFanPageGrowth.year +
			'  was the month when your audience increased the most, with ' +
			this.reportFacebookHistorical.majorFanPageGrowth.growthRate + '% more fans on your base.'
		}
	}

	// majorFanPageGrowth() {

	// 	let majorMonth = this.months[this.reportFacebookHistorical.majorFanPageGrowth.month - 1];

	// 	this.majorFanPageGrowthFooter = {
	// 		icon: {
	// 			icon: 'fa-arrow-circle-up',
	// 			color: '#00d6f6'
	// 		},
	// 		title: 'Mês com maior crescimento',
	// 		value: majorMonth,
	// 		text: majorMonth + ' de ' + this.reportFacebookHistorical.majorFanPageGrowth.year +
	// 		' foi o mês com o maior índice de crescimento, com ' +
	// 		this.reportFacebookHistorical.majorFanPageGrowth.growthRate + '% de aumento na base.'
	// 	}
	// }

	minorFanPageGrowth() {

		let minorMonth = this.months[this.reportFacebookHistorical.minorFanPageGrowth.month - 1];

		this.minorFanPageGrowthFooter = {
			icon: {
				icon: 'fa-arrow-circle-down',
				color: '#f7554e'
			},
			title: 'Mês com menor crescimento',
			value: minorMonth,
			text: minorMonth + ' of ' + this.reportFacebookHistorical.minorFanPageGrowth.year +
			' was the month when your audience increased the less, with ' +
			this.reportFacebookHistorical.minorFanPageGrowth.growthRate + '% less fans on your base.'
		}
	}

	// minorFanPageGrowth() {

	// 	let minorMonth = this.months[this.reportFacebookHistorical.minorFanPageGrowth.month - 1];

	// 	this.minorFanPageGrowthFooter = {
	// 		icon: {
	// 			icon: 'fa-arrow-circle-down',
	// 			color: '#f7554e'
	// 		},
	// 		title: 'Mês com menor crescimento',
	// 		value: minorMonth,
	// 		text: minorMonth + ' de ' + this.reportFacebookHistorical.minorFanPageGrowth.year +
	// 		' foi o mês com o menor índice de crescimento, com ' +
	// 		this.reportFacebookHistorical.minorFanPageGrowth.growthRate + '% de aumento na base.'
	// 	}
	// }
}
