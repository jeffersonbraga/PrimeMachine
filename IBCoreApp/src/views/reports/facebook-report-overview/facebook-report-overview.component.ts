import { DatePipe, DecimalPipe } from '@angular/common';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { PieChartComponent } from 'core/componentes/charts/pie-chart/pie-chart.component';
import { StackedColumnChartComponent } from 'core/componentes/charts/stacked-column-chart/stacked-column-chart.component';
import { AbbreviateNumberPipe } from 'core/pipes/abbreviateNumber.pipe';
import { ControlService } from 'core/services/control/control.service';
import { FacebookService } from 'core/services/facebook/facebook.service';
import { NotificationService } from 'core/services/notification/notification.service';
import { PdfService } from 'core/services/pdf/pdf.service';
import { IAsidePieChart, IAsideSmallLineChart, IAsideTextIcon } from 'modelo/complements/Complements';
import { Notification } from 'modelo/notification/Notification';

declare let $;

@Component({
	selector: 'facebook-report-overview',
	templateUrl: './facebook-report-overview.component.html',
	styleUrls: ['./facebook-report-overview.component.css']
})
export class FacebookReportOverviewComponent implements OnInit {

	@Input() dateFrom: Date;
	@Input() dateUntil: Date;
	@Input() profile;
	@Input() reportFacebookOverview: any;
	@Input() period;
	account;

	@ViewChild('aboutReachedUsersChart')
	aboutReachedUsersChart: StackedColumnChartComponent;

	@ViewChild('womenMenReachedUsersPieChart')
	womenMenReachedUsersPieChart: PieChartComponent;

	weekDays: string[] = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
	weekDaysFull: string[] = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']

	// weekDays: string[] = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'];
	// weekDaysFull: string[] = ['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'];
	
	showCountriesMoreFans: boolean = true;
	showCitiesMoreFans: boolean = true;
	showAboutYourFans: boolean = true;

	aboutReachedUsersChartRendered: boolean = false;

	fanPageGrowthData;
	fanPageGrowthForceY;
	fanPageGrowthTickValuesY;

	likeUnlikeData;
	aboutReachedUsersData;
	aboutYourFansData;
	mostInfluentialFansData1;
	mostInfluentialFansData2;
	fansOnlinePerHourData;
	fansOnlinePerWeekDayData;
	countriesMoreReachedData;
	countriesMoreFansData;
	citiesMoreReachedData;
	citiesMoreFansData;
	otherActivitiesData;
	fansLikeSourceData1;
	fansLikeSourceData2;
	externalReferencesData;

	fansOnlinePerHourTickValues;

	growthFansPeakAside: IAsideTextIcon;
	conqueredFansAside: IAsideTextIcon;
	lostFansAside: IAsideTextIcon;
	balanceFansAside: IAsideTextIcon;

	majorAgeGroupReachedUsersFooter: IAsideTextIcon;
	majorAgeGroupFansFooter: IAsideTextIcon;
	timeWithMoreFansOnlineFooter: IAsideTextIcon;
	weekDayWithMoreFansOnlineFooter: IAsideTextIcon;

	womenMenReachedUsersPie: IAsidePieChart;
	womenMenFansPie: IAsidePieChart;

	pageTotalFansSmallLine: IAsideSmallLineChart;
	pageTotalReachSmallLine: IAsideSmallLineChart;
	pageTotalImpressionsSmallLine: IAsideSmallLineChart;
	pageTotalEngagementSmallLine: IAsideSmallLineChart;

	legendsLikeUnlike = {
		legends: true,
		color: ["#20ffa3", "#f7554e", "#ffd71e"]
	}

	legendsAboutReachedUsers = {
		legends: true,
		color: ["#00d6f6", "#f74e7e"]
	}

	legendsAboutYourFans = {
		legends: true,
		color: ["#00d6f6", "#f74e7e"]
	}

	legendsFansOnlinerPerHour = {
		legends: true,
		color: ["#00d6f6", "#00aed9"]
	}

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

	ngAfterViewChecked() {

		if(!this.showAboutYourFans && !this.aboutReachedUsersChartRendered) {
			this.aboutReachedUsersChartRendered = true;
			this.aboutReachedUsersChart.chart.ngNvD3.chart.update();
			this.womenMenReachedUsersPieChart.chart.chart.ngNvD3.chart.update()
		}
	}

	mountCharts() {
		
		this.pageTotalFans();
		this.pageTotalImpressions();
		this.pageTotalReach();
		this.pageTotalEngagement();
		this.fanPageGrowth();
		this.likeUnlike();
		this.growthFansPeak();
		this.conqueredFans();
		this.lostFans();
		this.balanceFans();
		this.aboutReachedUsers();
		this.majorAgeGroupReachedUsers();
		this.womenMenReachedUsers();
		this.aboutYourFans();
		this.majorAgeGroupFans();
		this.womenMenFans();
		this.mostInfluentialFans();
		this.fansOnlinePerHour();
		this.timeWithMoreFansOnline();
		this.fansOnlinePerWeekDay();
		this.weekDayWithMoreFansOnline();
		this.countriesMoreReached();
		this.countriesMoreFans();
		this.citiesMoreReached();
		this.citiesMoreFans();
		this.otherActivities();
		this.fansLikeSource();
		this.externalReferences();
	}

	getXLS(){
		this.facebookService.getOverviewReportXLS(this.profile.idProfile, this.dateFrom.getTime(), this.dateUntil.getTime())
			.subscribe(
				result => this.downloadXLS(result),
				error => console.log(error)
			);
	}

	downloadXLS(result){
		window.open(this.controlService.context_app + 'woopsocial/get_xls?file=' + result.fileName + '&filename=' + result.formattedFileName + '&idAccount=' + result.idAccount);
	}

	getPDF(){
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

	formatDate(date, format) {
		date = new Date(date * 1000);
		return this.datePipe.transform(date, format);
	}

	formatHour(hour) {
		hour = hour == 24 ? 0 : hour;
		
		return (hour < 10 ? '0' + hour : hour) + ':00'; 
	}

	getDateFromHour(hour) {
		
		let date = new Date();
		date.setHours(hour);
		date.setMinutes(0);
		date.setSeconds(0);
		date.setMilliseconds(0);

		return date.getTime();
	}

	getStoryTypeName(storyType) {

		if(storyType == 'pageStoriesByMention') {
			return "Mentions";
		} else if(storyType == 'pageStoriesByCheckin') {
			return "Check in";
		} else if(storyType == 'pageStoriesByUserPost') {
			return "Posts from others";
		} else {
			return "";
		}
	}

	// getLikeSourceName(likeSource) {
		
	// 	if(likeSource == 'pageFansPageProfile') {
	// 		return "Na página";
	// 	} else if(likeSource == 'pageFansHovercard') {
	// 		return "No pop-up";
	// 	} else if(likeSource == 'pageFansPhotoSnowlift') {
	// 		return "Na foto";
	// 	} else if(likeSource == 'pageFansMobile'){
	// 		return "Mobile";
	// 	} else if(likeSource == 'pageFansExternalConnect') {
	// 		return "Caixa \"curtir\" e botão \"curtir\"";
	// 	} else if(likeSource == 'pageFansAds') {
	// 		return "Anúncios e histórias patrocinadas Desktop";
	// 	} else if(likeSource == 'pageFansLikeStory') {
	// 		return "Indicação de amigos";
	// 	} else if(likeSource == 'pageFansFromPages') {
	// 		return "Páginas que curtiram outras páginas";
	// 	} else if(likeSource == 'pageFansSearch') {
	// 		return "Resultados da busca";
	// 	} else if(likeSource == 'pageFansFromRecommendations') {
	// 		return "Recomendações do Facebook";
	// 	} else if(likeSource == 'pageFansMobileAds') {
	// 		return "Anúncios e histórias patrocinadas Mobile";
	// 	} else if(likeSource == 'pageFansApi') {
	// 		return "Aplicações externas";
	// 	} else if(likeSource == 'pageFansPageInviteEscapeHatchFinch') {
	// 		return "Convite";
	// 	} else if(likeSource == 'pageFansPageSuggestion') {
	// 		return "Convite dos administradores";
	// 	} else if(likeSource == 'pageFansFanContextStory') {
	// 		return "Histórias de interações de amigos";
	// 	} else if(likeSource == 'pageFansRegistration') {
	// 		return "Pessoas adicionadas como administradores";
	// 	} else if(likeSource == 'pageFansPageTimeline') {
	// 		return "Recomendações na Timeline";
	// 	} else if(likeSource == 'pageFansWizardSuggestion') {
	// 		return "Recomendações para novos usuários";
	// 	} else if(likeSource == 'pageFansFromLikeSuggestions') {
	// 		return "Sugestões ao curtir novas páginas";
	// 	} else if(likeSource == 'pageFansSponsoredStory') {
	// 		return "Posts impulsionados";
	// 	} else {
	// 		return "";
	// 	}
		
	// }

	formatAgeGroupDisplay(initialAgeGroup, endAgeGroup) {
		if (Number(initialAgeGroup) < 65) {
			return initialAgeGroup + '-' + endAgeGroup;
		} else {
			return initialAgeGroup + '+';
		}
	}

	showCountriesList(event: any) {

		if(event.target.value == 'yes') {
			this.showCountriesMoreFans = true;
		} else {
			this.showCountriesMoreFans = false;
		}

	}

	showCitiesList(event: any) {

		if(event.target.value == 'fans') {
			this.showCitiesMoreFans = true;
		} else {
			this.showCitiesMoreFans = false;
		}

	}

	showPublicDemographicInfo(event: any) {

		if(event.target.value == 'yes') {
			this.showAboutYourFans = true;
		} else {
			this.showAboutYourFans = false;
		}
	}

	createTdValue(img, text, profileUrl) {
		return '<a href="' + profileUrl + '" target="_blank"><span><img height="28" src="' + img + '" /> '+ text +'</span></a>';
	}

	fansOnlinePerWeekDay() {

		this.fansOnlinePerWeekDayData = [
			{
			key: 'Fãs online',
			values: this.reportFacebookOverview.fansOnlinePerWeekDay.map(
				fav => {
					return {
						label: this.weekDays[fav.date],
						value: fav.sum,
						color: '#00d6f6'
					}
				})
			}
		];
	}

	fansOnlinePerHour() {

		let fansOnlinerPerHourList = this.reportFacebookOverview.fansOnlinePerHour.map(
			fav => {
				return [this.getDateFromHour(fav.date), fav.sum]
			});

		fansOnlinerPerHourList = fansOnlinerPerHourList.sort((a, b) => a[0] - b[0]);

		let averageFansOnlinePerHourList = this.reportFacebookOverview.averageFansOnlinePerHour.map(
			fav => {
				return [this.getDateFromHour(fav.date), fav.sum]
			});

		averageFansOnlinePerHourList = averageFansOnlinePerHourList.sort((a, b) => a[0] - b[0]);

		this.fansOnlinePerHourData = [{
			"key": "Total online fans",
			"values": fansOnlinerPerHourList
			},{
			"key": "Online fans on an average",
			"values": averageFansOnlinePerHourList 
			}
		];

		this.fansOnlinePerHourTickValues = Array.from(Array(24), (_,x) => this.getDateFromHour(x));
	}

	fansLikeSource() {

		let fansLikeSource: any = this.reportFacebookOverview.fansLikeSource;
		let dataFansLikeSource1 = [];
		let dataFansLikeSource2 = [];

		let cols = [{value: 'Origem', class: 'left'}, {value: 'Curtir', class: 'right', width: '130px'}];

		for (let i = 0; i < fansLikeSource.length && i < 10; i++) {
			dataFansLikeSource1.push([
				{value: fansLikeSource[i].location, class: 'left'}, 
				{value: fansLikeSource[i].value, class: 'right'}
			]);
		}

		this.fansLikeSourceData1 = {
			cols: cols, 
			rows: dataFansLikeSource1
		}

		if(fansLikeSource.length > 10){
			for (let i = 10; i < fansLikeSource.length && i < 20; i++){
				dataFansLikeSource2.push([
					{value: fansLikeSource[i].location, class: 'left'}, 
					{value: fansLikeSource[i].value, class: 'right'}
				]);
			}

			this.fansLikeSourceData2 = {
				cols: cols, 
				rows: dataFansLikeSource2
			}
		}else {
			this.fansLikeSourceData2 = [];
		}

	}

	externalReferences() {

		let externalReferences: any = this.reportFacebookOverview.externalReferences;
		let dataExternalReferences = [];

		for (let i = 0; i < externalReferences.length && i < 10; i++) {
			dataExternalReferences.push([
				{value: '<a href="' + externalReferences[i].location + '" target="_blank">' + externalReferences[i].location + '</a>', class: 'left'}, 
				{value: externalReferences[i].value, class: 'right'}
			]);
		}

		this.externalReferencesData = {
			cols: [
				{value: 'Referência', class: 'left'}, {value: 'Número', class: 'right', width: '100px'}
			], 
			rows: dataExternalReferences
		}
	}

	otherActivities() {
		
		let otherActivities: any = this.reportFacebookOverview.otherActivities;
		let dataOtherActivities = [];

		for (let i = 0; i < otherActivities.length && i < 10; i++) {
			dataOtherActivities.push([
				{value: this.getStoryTypeName(otherActivities[i].location), class: 'left'}, 
				{value: otherActivities[i].value, class: 'right'}
			]);
		}

		this.otherActivitiesData = {
			cols: [
				{value: 'Atividade', class: 'left'}, {value: 'Número', class: 'right', width: '100px'}
			], 
			rows: dataOtherActivities
		}
	}

	countriesMoreFans() {
		
		let countries: any = this.reportFacebookOverview.countriesMoreFans;
		let dataCountries = [];

		for (let i = 0; i < countries.length && i < 10; i++) {
			dataCountries.push([
				{value: countries[i].location, class: 'left'}, 
				{value: countries[i].value, class: 'right'}
			]);
		}

		this.countriesMoreFansData = {
			cols: [
				{value: 'País', class: 'left'}, {value: 'Curtir', class: 'right', width: '100px'}
			], 
			rows: dataCountries
		}
	}

	countriesMoreReached() {
		
		let countries: any = this.reportFacebookOverview.countriesMoreReached;
		let dataCountries = [];

		for (let i = 0; i < countries.length && i < 10; i++) {
			dataCountries.push([
				{value: countries[i].location, class: 'left'}, 
				{value: countries[i].value, class: 'right'}
			]);
		}

		this.countriesMoreReachedData = {
			cols: [
				{value: 'País', class: 'left'}, {value: 'Curtir', class: 'right', width: '100px'}
			], 
			rows: dataCountries
		}
	}

	citiesMoreFans() {
		
		let cities: any = this.reportFacebookOverview.citiesMoreFans;
		let dataCities = [];

		for (let i = 0; i < cities.length && i < 10; i++) {
			dataCities.push([
				{value: cities[i].location, class: 'left'}, 
				{value: cities[i].value, class: 'right'}
			]);
		}

		this.citiesMoreFansData = {
			cols: [
				{value: 'Cidade', class: 'left'}, {value: 'Curtir', class: 'right', width: '100px'}
			], 
			rows: dataCities
		}
	}

	citiesMoreReached() {
		
		let cities: any = this.reportFacebookOverview.citiesMoreReached;
		let dataCities = [];

		for (let i = 0; i < cities.length && i < 10; i++) {
			dataCities.push([
				{value: cities[i].location, class: 'left'}, 
				{value: cities[i].value, class: 'right'}
			]);
		}

		this.citiesMoreReachedData = {
			cols: [
				{value: 'Cidade', class: 'left'}, {value: 'Curtir', class: 'right', width: '100px'}
			], 
			rows: dataCities
		}
	}

	mostInfluentialFans() {

		let influentials: any = this.reportFacebookOverview.mostInfluentialFans;
		let dataInfluentials1 = [];
		let dataInfluentials2 = [];

		let cols = [{value: 'Usuário', class: 'left'}, {value: 'Interações', class: 'right', width: '130px'}];

		for (let i = 0; i < influentials.length && i < 10; i++){
			dataInfluentials1.push([
				{value: this.createTdValue(influentials[i].imageProfile, influentials[i].name, influentials[i].profileUrl), class: 'left'}, 
				{value: this.decimalPipe.transform(influentials[i].comments + influentials[i].likes, '1.0-0'), class: 'right'}
			]);
		}

		this.mostInfluentialFansData1 = {
			cols: cols, 
			rows: dataInfluentials1
		}

		if(influentials.length > 10){
			for (let i = 10; i < influentials.length && i < 20; i++){
				dataInfluentials2.push([
					{value: this.createTdValue(influentials[i].imageProfile, influentials[i].name, influentials[i].profileUrl), class: 'left'}, 
					{value: this.decimalPipe.transform(influentials[i].comments + influentials[i].likes, '1.0-0'), class: 'right'}
				]);
			}

			this.mostInfluentialFansData2 = {
				cols: cols, 
				rows: dataInfluentials2
			}
		}else{
			this.mostInfluentialFansData2 = [];
		}

	}

	womenMenFans() {
		
		let legend = [
			{icon: {icon: 'fa-male', color:'#00d6f6'}, value: this.reportFacebookOverview.menFansPerc+'%'},
			{icon: {icon: 'fa-female', color:'#f74e7e'}, value: this.reportFacebookOverview.womenFansPerc+'%'}
		];

		this.womenMenFansPie = {
			icon: {
				icon: 'fa-venus-mars',
				color: '#f6cb00'
			},
			chart: {
				data : [
					{ 
						"label": "Men",
						"value" : this.reportFacebookOverview.menFansPerc, 
						"color": "#00d6f6"
					}, 
					{ 
						"label": "Women",
						"value" : this.reportFacebookOverview.womenFansPerc, 
						"color": "#f74e7e"
					}
				],
				height: 68,
				width: 68,
				isDonut: true,
				legend: legend
			},
			title: 'Divisão dos fãs',
			orientation: 'center'
		}
	}

	pageTotalEngagement() {

		this.pageTotalEngagementSmallLine = {
			icon: {
				icon: 'fa-thumbs-up',
				color: '#989898'
			},
			chart: {
				data: this.reportFacebookOverview.engagementByDay.map(
				fav => {
					return fav.sum
				}),
				height: 35
			},
			title: 'total engagement',
    		value: this.reportFacebookOverview.totalEngagement,
			smallText: this.reportFacebookOverview.totalEngagementPerformance + '%',
			hasGrown: this.reportFacebookOverview.totalEngagementPerformance >= 0 ? true : false
		}
	}

	pageTotalFans() {
		
		this.pageTotalFansSmallLine = {
			icon: {
				icon: 'fa-facebook-official',
				color: '#989898'
			},
			chart: {
				data: this.reportFacebookOverview.fanPageGrowth.map(
				fav => {
					return fav.sum
				}),
				height: 35
			},
			title: 'total likes',
			// title: 'total de likes na página',
    		value: this.reportFacebookOverview.totalOfFans,
			smallText: this.reportFacebookOverview.balanceFansPerformance + '%',
			hasGrown: this.reportFacebookOverview.balanceFansPerformance >= 0 ? true : false
		}
	}

	pageTotalImpressions() {

		this.pageTotalImpressionsSmallLine = {
			icon: {
				icon: 'fa-eye',
				color: '#989898'
			},
			chart: {
				data: this.reportFacebookOverview.impressionsByDay.map(
				fav => {
					return fav.sum
				}),
				height: 35
			},
			title: 'Total impressions',
    		value: this.reportFacebookOverview.totalImpressions,
			smallText: this.reportFacebookOverview.totalImpressionsPerformance + '%',
			hasGrown: this.reportFacebookOverview.totalImpressionsPerformance >= 0 ? true : false
		}
	}

	pageTotalReach() {

		this.pageTotalReachSmallLine = {
			icon: {
				icon: 'fa-user',
				color: '#989898'
			},
			chart: {
				data: this.reportFacebookOverview.reachByDay.map(
				fav => {
					return fav.sum
				}),
				height: 35
			},
			title: 'Total reach',
    		value: this.reportFacebookOverview.totalReach,
			smallText: this.reportFacebookOverview.totalReachPerformance + '%',
			hasGrown: this.reportFacebookOverview.totalReachPerformance >= 0 ? true : false
		}
	}

	womenMenReachedUsers() {

		let legend = [
			{icon: {icon: 'fa-male', color:'#00d6f6'}, value: this.reportFacebookOverview.menReachedUsersPerc+'%'},
			{icon: {icon: 'fa-female', color:'#f74e7e'}, value: this.reportFacebookOverview.womenReachedUsersPerc+'%'}
		];

		this.womenMenReachedUsersPie = {
			icon: {
				icon: 'fa-venus-mars',
				color: '#f6cb00'
			},
			chart: {
				data : [
					{ 
						"label": "Men",
						"value" : this.reportFacebookOverview.menReachedUsersPerc, 
						"color": "#00d6f6"
					}, 
					{ 
						"label": "Women",
						"value" : this.reportFacebookOverview.womenReachedUsersPerc, 
						"color": "#f74e7e"
					}
				],
				height: 68,
				width: 68,
				isDonut: true,
				legend: legend
			},
			title: 'Divisão do público',
			orientation: 'center'
		}
	}

	aboutYourFans() {

		let male = this.reportFacebookOverview.aboutYourMaleFans.map(
			fav => {
				return {
					x: this.formatAgeGroupDisplay(fav.initialAgeGroup, fav.endAgeGroup),
					y: fav.value,
					color: '#00d6f6'
				}
			});

		male.sort((a, b) => a.x > b.x ? 1 : 0);

		let female = this.reportFacebookOverview.aboutYourMaleFans.map(
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
		let male = this.reportFacebookOverview.aboutReachedMaleUsers.map(
			fav => {
				return {
					x: this.formatAgeGroupDisplay(fav.initialAgeGroup, fav.endAgeGroup),
					y: fav.value,
					color: '#00d6f6'
				}
			});

		male.sort((a, b) => a.x > b.x ? 1 : 0);

		let female = this.reportFacebookOverview.aboutReachedFemaleUsers.map(
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

		this.fanPageGrowthData = [{
			"key": "Fans",
			"values": this.reportFacebookOverview.fanPageGrowth.map(
				fav => {
					return [fav.date * 1000, fav.sum]
				})
		}
		];

		this.fanPageGrowthTickValuesY = this.getTickValuesY(this.getMaxMinFanPageGrowthSum(this.reportFacebookOverview.fanPageGrowth));
	}

	getMaxMinFanPageGrowthSum(fanPageGrowth) {

		let maxValue: Number = 0;
		let minValue: Number = 9999999999;		

		$.each(fanPageGrowth, function(index, fanPageGrowth) {
			if(fanPageGrowth.sum > maxValue) {
				maxValue = fanPageGrowth.sum;
			}
			if(fanPageGrowth.sum < minValue) {
				minValue = fanPageGrowth.sum;
			}
		});

		return [minValue, maxValue];		
	}

	getTickValuesY(forceYValues) {

		let minValue : number = forceYValues[0];
		let maxValue : number = forceYValues[1];
		let scaleReference : number;
		let numberOfTicks: number = 6;
		let tickValues: any = [];
		let maxTickValue: number;
		let scaleDifference : any = [2,4,5,10,20,50,100,200,300,400,500,1000,5000,10000,50000,100000];

		scaleReference = (maxValue - minValue) / (numberOfTicks - 1); // scaleReference : By Samuca.

		for (let i = 0; i < scaleDifference.length; i++) {
			if(scaleReference < scaleDifference[i]) {
				scaleReference = scaleDifference[i];
				maxTickValue = (maxValue + scaleReference) - (maxValue % scaleReference);
				break;
			}
		}

		for (let i = numberOfTicks - 1; i > -1; i--) {
			tickValues.push(maxTickValue - (scaleReference * i));
		}

		this.fanPageGrowthForceY = [tickValues[0], tickValues[tickValues.length - 1]];
		
		return tickValues;
	}

	likeUnlike() {

		this.likeUnlikeData = [{
			"key": "New fans",
			"type": "bar",
			"yAxis": 1,
			"values": this.reportFacebookOverview.likesByDay.map(
				fav => {
					return {
						x: fav.date * 1000,
						y: fav.sum
					}
				})
		}, {
			"key": "Fan lost",
			"type": "bar",
			"yAxis": 1,
			"values": this.reportFacebookOverview.unlikesByDay.map(
				fav => {
					return {
						x: fav.date * 1000,
						y: -fav.sum
					}
				})
		}
		];
	}

	growthFansPeak() {
		this.growthFansPeakAside = {
			icon: {
				icon: 'fa-arrow-circle-up',
				color: '#ffd71e'
			},
			title: 'Pico de crescimento',
			value: this.formatDate(this.reportFacebookOverview.majorFanPageGrowth.date, 'MM/dd/yy'),
			text: 'Day ' + this.formatDate(this.reportFacebookOverview.majorFanPageGrowth.date, 'MM/dd') 
				+ ' registered the higher page growth , which was ' 
				+ this.abbreviateNumberPipe.transform(this.reportFacebookOverview.majorFanPageGrowth.sum) 
				+ ' new followers.'
		}
	}

	// growthFansPeak() {
	// 	this.growthFansPeakAside = {
	// 		icon: {
	// 			icon: 'fa-arrow-circle-up',
	// 			color: '#ffd71e'
	// 		},
	// 		title: 'Pico de crescimento',
	// 		value: this.formatDate(this.reportFacebookOverview.majorFanPageGrowth.date, 'dd/MM/yy'),
	// 		text: 'Dia ' + this.formatDate(this.reportFacebookOverview.majorFanPageGrowth.date, 'dd/MM') 
	// 			+ ' registrou o maior índice de crescimento, com um aumento de ' 
	// 			+ this.abbreviateNumberPipe.transform(this.reportFacebookOverview.majorFanPageGrowth.sum) 
	// 			+ ' pessoas na fanbase.'
	// 	}
	// }

	conqueredFans() {
		this.conqueredFansAside = {
			icon: {
				icon: 'fa-plus-circle',
				color: '#20ffa3'
			},
			title: 'Fãs conquistados',
			value: this.reportFacebookOverview.conqueredFans,
		}
	}

	weekDayWithMoreFansOnline() {

		this.weekDayWithMoreFansOnlineFooter = {
			icon: {
				icon: 'fa-calendar',
				color: '#f6cb00'
			},
			title: 'Dia com maior alcance',
			value: this.weekDaysFull[this.reportFacebookOverview.weekDayWithMoreFansOnline], 
			smallText: '(' + this.reportFacebookOverview.weekDayWithMoreFansOnlinePercentage + '%)', 
			text: this.reportFacebookOverview.weekDayWithMoreFansOnlinePercentage + '% of the audience\page fans were online on ' + this.weekDaysFull[this.reportFacebookOverview.weekDayWithMoreFansOnline]
		}
	}

	// weekDayWithMoreFansOnline() {

	// 	this.weekDayWithMoreFansOnlineFooter = {
	// 		icon: {
	// 			icon: 'fa-calendar',
	// 			color: '#f6cb00'
	// 		},
	// 		title: 'Dia com maior alcance',
	// 		value: this.weekDaysFull[this.reportFacebookOverview.weekDayWithMoreFansOnline], 
	// 		smallText: '(' + this.reportFacebookOverview.weekDayWithMoreFansOnlinePercentage + '%)', 
	// 		text: this.reportFacebookOverview.weekDayWithMoreFansOnlinePercentage + '% dos seus fãs estiveram conectados na ' + this.weekDaysFull[this.reportFacebookOverview.weekDayWithMoreFansOnline]
	// 	}
	// }

	timeWithMoreFansOnline() {

		this.timeWithMoreFansOnlineFooter = {
			icon: {
				icon: 'fa-clock-o',
				color: '#f6cb00'
			},
			title: 'Horário com mais fãs online',
			value: this.formatHour(this.reportFacebookOverview.timeWithMoreFansOnline) + ' at ' + this.formatHour(this.reportFacebookOverview.timeWithMoreFansOnline + 1), 
			smallText: '(' + this.reportFacebookOverview.timeWithMoreFansOnlinePercentage + '%)', 
			text: this.reportFacebookOverview.timeWithMoreFansOnlinePercentage + '% of the audience\page fans were online.'
		}
	}

	// timeWithMoreFansOnline() {

	// 	this.timeWithMoreFansOnlineFooter = {
	// 		icon: {
	// 			icon: 'fa-clock-o',
	// 			color: '#f6cb00'
	// 		},
	// 		title: 'Horário com mais fãs online',
	// 		value: this.formatHour(this.reportFacebookOverview.timeWithMoreFansOnline) + ' às ' + this.formatHour(this.reportFacebookOverview.timeWithMoreFansOnline + 1), 
	// 		smallText: '(' + this.reportFacebookOverview.timeWithMoreFansOnlinePercentage + '%)', 
	// 		text: this.reportFacebookOverview.timeWithMoreFansOnlinePercentage + '% dos fãs estiveram conectados nesta faixa.'
	// 	}
	// }

	majorAgeGroupFans() {

		this.majorAgeGroupFansFooter = {
			icon: {
				icon: 'fa-arrow-circle-up',
				color: '#ffd71e'
			},
			title: 'Maior faixa etária',
			value: this.reportFacebookOverview.majorAgeGroupFans.initialAgeGroup + ' to ' + this.reportFacebookOverview.majorAgeGroupFans.endAgeGroup,
			text: 'Most of the page’s audience is between '
			+ this.reportFacebookOverview.majorAgeGroupFans.initialAgeGroup + ' and '
			+ this.reportFacebookOverview.majorAgeGroupFans.endAgeGroup + ' years old, '
			+ 'representing ' + this.reportFacebookOverview.majorAgeGroupFans.percentage + '% of the total.'
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
	// 		value: this.reportFacebookOverview.majorAgeGroupFans.initialAgeGroup + ' a ' + this.reportFacebookOverview.majorAgeGroupFans.endAgeGroup,
	// 		text: 'A maior parte dos fãs da página tem entre '
	// 		+ this.reportFacebookOverview.majorAgeGroupFans.initialAgeGroup + ' e '
	// 		+ this.reportFacebookOverview.majorAgeGroupFans.endAgeGroup + ' anos, '
	// 		+ 'representando ' + this.reportFacebookOverview.majorAgeGroupFans.percentage + '% do total.'
	// 	}
	// }

	majorAgeGroupReachedUsers() {

		this.majorAgeGroupReachedUsersFooter = {
			icon: {
				icon: 'fa-arrow-circle-up',
				color: '#ffd71e'
			},
			title: 'Maior faixa etária',
			value: this.reportFacebookOverview.majorAgeGroupReachedUsers.initialAgeGroup + ' a ' + this.reportFacebookOverview.majorAgeGroupReachedUsers.endAgeGroup,
			text: 'Most of the page’s audience is between '
			+ this.reportFacebookOverview.majorAgeGroupReachedUsers.initialAgeGroup + ' and '
			+ this.reportFacebookOverview.majorAgeGroupReachedUsers.endAgeGroup + ' years old, '
			+ 'representing ' + this.reportFacebookOverview.majorAgeGroupReachedUsers.percentage + '% of the total.'
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
	// 		value: this.reportFacebookOverview.majorAgeGroupReachedUsers.initialAgeGroup + ' a ' + this.reportFacebookOverview.majorAgeGroupReachedUsers.endAgeGroup,
	// 		text: 'A maior parte do público da página tem entre '
	// 		+ this.reportFacebookOverview.majorAgeGroupReachedUsers.initialAgeGroup + ' e '
	// 		+ this.reportFacebookOverview.majorAgeGroupReachedUsers.endAgeGroup + ' anos, '
	// 		+ 'representando ' + this.reportFacebookOverview.majorAgeGroupReachedUsers.percentage + '% do total.'
	// 	}
	// }

	lostFans() {
		this.lostFansAside = {
			icon: {
				icon: 'fa-minus-circle',
				color: '#f7554e'
			},
			title: 'Fãs perdidos',
			value: this.reportFacebookOverview.lostFans,
		}
	}

	balanceFans() {
		this.balanceFansAside = {
			icon: {
				icon: 'fa-area-chart',
				color: '#ffd71e'
			},
			title: 'Saldo de fãs',
			value: this.reportFacebookOverview.balanceFans,
		}
	}

}
