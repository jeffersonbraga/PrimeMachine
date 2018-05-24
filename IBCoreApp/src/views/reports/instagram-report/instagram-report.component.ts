import { ProfilesService } from 'core/services/profiles/profiles.service';
import { DatePipe, DecimalPipe } from '@angular/common';
import { Component, DoCheck, Input, KeyValueDiffers } from '@angular/core';
import { InstagramService } from 'core/services/instagram/instagram.service';
import { IAsidePieChart, IAsideTextIcon } from 'modelo/complements/Complements';
import { Profile } from 'modelo/profile/Profile';

@Component({
	moduleId: module.id,
	selector: 'instagram-report', 
	templateUrl: './instagram-report.component.html', 
	styleUrls: ['./instagram-report.component.css']
}) 

export class InstagramReportComponent implements DoCheck {

	public postsStatsYearMonthSelected : string = 'all';

	public postsStatsYearFirst	: boolean = true;
	public postsStatsYearSecond	: boolean = false;
	public postsStatsYearThird	: boolean = false;
	public viewHistorical		: boolean = true;

	showCheckAllBtn: boolean = false;

	isLoading : boolean = true;
	
	@Input() reportConfigs = {
		dateFrom: null,
		dateUntil: null,
		profile: null
	};

	differ: any;
	dateFrom: Date;
	dateUntil: Date;
	profile: Profile;

	period;

	reportState: string = Profile.REPORT_FINALIZED;

	bubbleDayColor: string[] = ['5fd6ff'];
	bubbleHourColor: string[] = ['ffcb39'];

	weekDays: string[] = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
	monthNames : string[] = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
	// weekDays: string[] = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'];
	// monthNames: string[] = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];
	colorsReactionsPerDay = ["#00d6f6", "#00aed9", "#f6cb00", "#ad43ff", "#00f6bf", "#ff7800"];
	fansOnlinePerHourTickValues		: any = Array.from(Array(24), (_,x) => this.getDateFromHour(x));

	dataOverview	: any;
	dataHistorical	: any;

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
	postsWithMoreInteractions		: any = [];
/*
	likesPerHour					: any = [];
	commentsPerHour					: any = [];
*/

	historical_posts							: any = [];
	historical_likes							: any = [];
	historical_comments							: any = [];
	historical_likesCommentsPerHour				: any = [];
	historical_instagramPublicAgeGender 		: any = [];
	historical_instagramTopPostTags				: any = [];
	historical_postsByDay						: any = [];
	historical_postsByHour						: any = [];

	/*historical_instagramFollowerTrend 			: any = [];
	historical_onlineFollowersByDay				: any = [];
	historical_onlineFollowersByHour			: any = [];
	historical_likesPerDay						: any = [];
	historical_commentsPerDay					: any = [];
	*/

	//ASIDE VARIABLE
	totalFollowersPeakAside 		: IAsideTextIcon;
	followersBalancePeakAside 		: IAsideTextIcon;
	unFollowersPeakAside 			: IAsideTextIcon;
	newFollowersPeakAside 			: IAsideTextIcon;
	complementPublicAge 			: IAsideTextIcon;
	complementPublicGender 			: IAsidePieChart;
	timeWithMoreFansOnlineFooter	: IAsideTextIcon;
	weekDayWithMoreFansOnlineFooter	: IAsideTextIcon;

	constructor(
		private instagramService: InstagramService, 
		private datePipe: DatePipe, 
		private decimalPipe: DecimalPipe,
		private differs: KeyValueDiffers,
		private profilesService: ProfilesService
	) {
		this.differ = differs.find({}).create(null);
	}
	
	ngDoCheck() {
		var changes = this.differ.diff(this.reportConfigs);

		if(changes) {
			this.initValues();
		}
	}

	initValues() {
        if (this.reportConfigs.profile.idProfile === this.profilesService.singleCheckedProfile.idProfile) {
			this.dateFrom = this.reportConfigs.dateFrom;
			this.dateUntil = this.reportConfigs.dateUntil;
			this.profile = this.reportConfigs.profile;
			
			if(this.dateFrom && this.dateUntil && this.profile) {
				this.setPeriod();
				this.getReportOverview();
				// this.getReportHistorical();
			}
		}
	}

	// ngOnChanges(){
	// 	this.setPeriod();
	// 	this.getReportOverview();
	// 	//this.getReportHistorical();
	// }

	setPeriod(){
		let timeDiff = this.dateUntil.getTime() - this.dateFrom.getTime();
		this.period = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
	}

	getReportOverview(){
		this.isLoading = true;

		if(this.validateForm()){
			this.instagramService.getReportOverview(this.profile, this.dateFrom.getTime(), this.dateUntil.getTime())
			.subscribe(
				result => this.mountOverviewCharts(result),
				error => console.log(error)
			);
		}		
	}

	getReportHistorical(){
		this.isLoading = true;

		if(this.validateForm()){
			this.instagramService.getReportHistorical(this.profile)
			.subscribe(
				result => this.mountHistoricalCharts(result),
				error => console.log(error)
			);
		}		
	}

	validateForm():boolean{
		if(this.dateFrom && this.dateUntil && this.profile){
			return true;
		} else return false; 
	}

	mountHistoricalCharts(report) {

		this.isLoading = false;

		this.dataHistorical = report.reportInstagramHistorical;
		this.reportState = report.reportState;

		console.log("Historical: ");
		console.log(report);

		if(this.reportState == Profile.REPORT_FINALIZED) {

			this.getValuesHistorical_posts();
			this.getValuesHistorical_postsByDay();
			this.getValuesHistorical_likes();
			this.getValuesHistorical_comments();
			this.getValuesHistorical_instagramPublicAgeGender();
			this.getValuesHistoriacalLikesCommentsPerHour();
			this.getValuesHistorical_instagramTopPostTags();
			this.getValuesHistorical_postsWithMoreInteractions();
			/*
			this.getValuesHistorical_onlineFollowersByDay();
			this.getValuesHistorical_onlineFollowersByHour();*/
		}

	}

	//POST POR MES E ANO
	getValuesHistorical_posts(): any {
		if ((this.dataHistorical != undefined) && (this.dataHistorical.instagramPostsStatsYearFirst != undefined)) {

			this.historical_posts = [];
			let parameters;

			if (this.postsStatsYearFirst) {
				parameters = this.dataHistorical.instagramPostsStatsYearFirst;
			} else if (this.postsStatsYearSecond) {
				parameters = this.dataHistorical.instagramPostsStatsYearSecond;
			} else if (this.postsStatsYearThird) {
				parameters = this.dataHistorical.instagramPostsStatsYearThird;
			}

			this.historical_posts = [
				{
					key: 'Posts',
					values: parameters.postsPerMonthSimpleData.map(
					fav => {
						return {
							x: this.getDateForMonth(fav.key), 
							y: this.getPercentValue(fav.value, parameters.postsTotal)
						}
					}),
					color: '#00d6f6'
				}
			];
		}
	}

	showPostsStatsYearMonthSelected(event: any) {
		this.postsStatsYearMonthSelected = event.target.value;
		// if(event.target.value == 'yes') {
		// 	this.showAboutYourFans = true;
		// } else {
		// 	this.showAboutYourFans = false;
		// }
	}

	//POSTS POR DIA E HORA
	getValuesHistorical_postsByDay(): any {
		if ((this.dataHistorical != undefined) && (this.dataHistorical.postsPerWeekDaySimpleData != undefined)) {

			let dataColumnDayWeek 	: any = [];
			let dataColumnHourDay 	: any = [];

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
					key: 'Dia',
					values: dataColumnDayWeek
				}
			];

			this.historical_postsByHour = [
				{
					key: 'Hora',
					values: dataColumnHourDay
				}
			];
		}
	}

	//LIKES POR MES E ANO
	getValuesHistorical_likes(): any {
		if ((this.dataHistorical != undefined) && (this.dataHistorical.instagramPostsStatsYearFirst != undefined)) {

			this.historical_likes = [];
			let values = [];
			let parameters;

			if (this.postsStatsYearFirst) {
				parameters = this.dataHistorical.instagramPostsStatsYearFirst;
			} else if (this.postsStatsYearSecond) {
				parameters = this.dataHistorical.instagramPostsStatsYearSecond;
			} else if (this.postsStatsYearThird) {
				parameters = this.dataHistorical.instagramPostsStatsYearThird;
			}

			let likesTotal = 0;
			parameters.likesPerMonthSimpleData.map(
				fav => {
					likesTotal = likesTotal + fav.value;
				}
			);

			parameters.likesPerMonthSimpleData.map(
				fav => {
					values.push([this.getDateForMonth((fav.key - 1)), this.getPercentValue(fav.value, likesTotal) ]);
				}
			);

			this.historical_likes = [
				{
					"key": "Likes",
					"values": values
				}
			];
		}
	}

	//COMMENTS POR MES E ANO
	getValuesHistorical_comments(): any {
		if ((this.dataHistorical != undefined) && (this.dataHistorical.instagramPostsStatsYearFirst != undefined)) {

			this.historical_comments = [];
			let parameters;

			if (this.postsStatsYearFirst) {
				parameters = this.dataHistorical.instagramPostsStatsYearFirst;
			} else if (this.postsStatsYearSecond) {
				parameters = this.dataHistorical.instagramPostsStatsYearSecond;
			} else if (this.postsStatsYearThird) {
				parameters = this.dataHistorical.instagramPostsStatsYearThird;
			}

			let commentsTotal = 0;
			parameters.commentsPerMonthSimpleData.map(
				fav => {
					commentsTotal = commentsTotal + fav.value;
				}
			);

			let values = [];
			parameters.commentsPerMonthSimpleData.map(
				fav => {
					values.push([this.getDateForMonth(fav.key - 1), this.getPercentValue(fav.value, commentsTotal) ]);
				}
			);

			this.historical_comments = [
				{
					"key": "Comments",
					"values": values
				}
			];
		}
	}

	//LIKES E COMENTÁRIOS POR HORA
	getValuesHistoriacalLikesCommentsPerHour() {
		let dataLikes 		: any = [];
		let dataComments 	: any = [];

		this.dataHistorical.likesPerHourSimpleData.map(
			fav => {
				dataLikes.push([this.getDateFromHour(fav.key), this.getPercentValue(fav.value, this.dataHistorical.likes)]);
			}
		);

		this.dataHistorical.commentsPerHourSimpleData.map(
			fav => {
				dataComments.push([this.getDateFromHour(fav.key), this.getPercentValue(fav.value, this.dataHistorical.comments) ]);
			}
		);

		this.historical_likesCommentsPerHour = [
			{
				"key": "Comments",
				"values": dataComments
			},
			{
				"key": "Likes",
				"values": dataLikes }
		];
	}

	//TABELA HASHTAGS
	getValuesHistorical_instagramTopPostTags(): any {

		if ((this.dataHistorical != undefined) && (this.dataHistorical.historical_instagramTopPostTags != undefined)) {

			let dataPostTags = [];
			this.dataHistorical.historical_instagramTopPostTags.map(
					fav => {
						dataPostTags.push([
							{value: fav.tag, class: 'left'}, 
							{value: fav.comments, class: 'right'}, 
							{value: fav.likes, class: 'right'}, 
							{value: fav.interactions, class: 'right'}]);
						});

			this.historical_instagramTopPostTags = {
				cols: [
					{value: 'Hashtag', class: 'left'}, {value: 'Comentários', class: 'right', width: '100px'}, {value: 'Likes', class: 'right', width: '100px'}, {value: 'Total', class: 'right', width: '100px'}
				], 
				rows: dataPostTags
			}
		}
	}

	//DEMOGRAFIA DO PÚBLICO
	getValuesHistorical_instagramPublicAgeGender(): any {
		if ((this.dataHistorical != undefined) && (this.dataHistorical.audiencesDemograph != undefined)) {

			let dataMen 	: any = [];
			let dataWomen 	: any = [];
			this.dataHistorical.audiencesDemograph.map(
				fav => {
					if (fav.property == 'age_men') {

						dataMen.push({
							x: fav.label + '', 
							y: fav.value,
							color: '#00d6f6'
						});
					} else if (fav.property == 'age_women') {

						dataWomen.push( {
							x: fav.label + '', 
							y: fav.value,
							color: '#f74e7e'
						});
					}
				}
			);

			this.historical_instagramPublicAgeGender = [ 
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

	getValuesHistorical_postsWithMoreInteractions() {

		if ((this.dataHistorical != undefined) && (this.dataHistorical.postsWithMoreInteractions != undefined)) {

			this.postsWithMoreInteractions = this.dataHistorical.postsWithMoreInteractions;
		}
	}

	mountOverviewCharts(report){

		this.getReportHistorical();
		this.isLoading = false;

		this.dataOverview = report.reportInstagramOverview;
		this.reportState = report.reportState;

		console.log("Overview: ");
		console.log(report);

		if(this.reportState == Profile.REPORT_FINALIZED) {
			
			this.getValuesInstagramFollowerTrend();
			this.getValuesInstagramPublicAgeGender();
			this.getValuesInstagramTopCountries();
			this.getValuesInstagramTopCities();
			this.getValuesOnlineFollowersByHour();
			this.getValuesOnlineFollowersByDay();
			this.getValuesLikesPerDay();		
			this.getValuesCommentsPerDay();		
			this.getValuesLikesCommentsPerHour();
			this.getValuesInstagramTopPostTags();
		} 
		
	}

	//MUDANÇA DE FOLLOWERS
	getValuesInstagramFollowerTrend() {
		if ((this.dataOverview != undefined) && (this.dataOverview.instagramFollowerTrends != undefined)) {
			this.instagramFollowerTrend = [ {
				"key": "Unfollow",
				"values": this.dataOverview.instagramFollowerTrends.map(
					fav => {
						return {
							x: this.formatDate(fav.date), 
							y: fav.unfollowers,
							color: '#f7554e'
						}
					})
				}, {
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
				"key": "Balanço",
				"values": this.dataOverview.instagramFollowerTrends.map(
					fav => {
						return {
							x: this.formatDate(fav.date), 
							y: fav.balanceFollowers,
							color: '#3b55f5'
						}
					})
				}
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
					if (fav.property == 'age_men') {

						dataMen.push({
							x: fav.label + '', 
							y: fav.value,
							color: '#00d6f6'
						});
					} else if (fav.property == 'age_women') {

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
							{value: fav.value + ' %', class: 'right'}]);
						});

			this.countriesWithMoreFollowersData = {
				cols: [
					{value: 'Pais', class: 'left'}, {value: 'Curtir', class: 'right', width: '100px'}
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
							{value: fav.value + ' %', class: 'right'}]);
						});

			this.citiesWithMoreFollowersData = {
				cols: [
					{value: 'Cidade', class: 'left'}, {value: 'Curtir', class: 'right', width: '100px'}
				], 
				rows: dataCities
			}
		}
	}

	//SEGUIDORES ONLINE POR HORÁRIO
	getValuesOnlineFollowersByHour() {
		if ((this.dataOverview != undefined) && (this.dataOverview.audiencesOnlineFollowersByHour != undefined)) {

			this.onlineFollowersByHour = [
				{
					key: 'Online',
					values: this.dataOverview.audiencesOnlineFollowersByHour.map(
					fav => {
						return {
							x: fav.date, 
							y: this.getPercentValue(fav.value, this.dataOverview.followers)
						}
					}),
					color: '#00d6f6'
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
						x: fav.label + '', 
						y: this.getPercentValue(fav.value, this.dataOverview.followers),
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
		if ((this.dataOverview != undefined) && (this.dataOverview.likesPerDayFollowers != undefined)) {

			let dataColumnFollowers 	: any = [];
			let dataColumnNOFollowers 	: any = [];

			this.dataOverview.likesPerDayFollowers.map(
				fav => {

					dataColumnFollowers.push({
						x: this.formatDate(fav.date), 
						y: fav.value,
						color: '#00d6f6'
					});
				}
			);

			this.dataOverview.likesPerDayNoFollowers.map(
				fav => {
					dataColumnNOFollowers.push({
						x: this.formatDate(fav.date), 
						y: fav.value,
						color: '#b8b8b8'
					});
				}
			);

			/*this.likesPerDay = [
				{
					key: 'Followers',
					values: dataColumnFollowers
				},
				{
					key: 'No Followers',
					values: dataColumnNOFollowers
				}
			];*/
		}
	}

	//COMENTÁRIOS DD/MM
	getValuesCommentsPerDay() {
		if ((this.dataOverview != undefined) && (this.dataOverview.commentsPerDayCompare != undefined)) {

			let dataColumnCommentsFollowers 	: any = [];
			let dataColumnNOCommentsFollowers 	: any = [];

			this.dataOverview.commentsPerDayCompare.map(
				fav => {

					dataColumnCommentsFollowers.push({
						x: this.formatDate(fav.dateCompare), 
						y: fav.followerComments,
						color: '#00d6f6'
					});
				}
			);

			this.dataOverview.commentsPerDayCompare.map(
				fav => {
					dataColumnNOCommentsFollowers.push({
						x: this.formatDate(fav.dateCompare), 
						y: fav.noFollowerComments,
						color: '#b8b8b8'
					});
				}
			);

			this.commentsPerDay = [
				{
					key: 'Followers',
					values: dataColumnCommentsFollowers
				},
				{
					key: 'No Followers',
					values: dataColumnNOCommentsFollowers
				}
			];
		}
	}

	//LIKES E COMENTÁRIOS POR HORA
	getValuesLikesCommentsPerHour() {
		let dataLikes 		: any = [];
		let dataComments 	: any = [];

		this.dataOverview.likesPerHourSimpleData.map(
			fav => {
				dataLikes.push([this.getDateFromHour(fav.key), this.getPercentValue(fav.value, this.dataOverview.likes)]);
			}
		);

		this.dataOverview.commentsPerHourSimpleData.map(
			fav => {
				dataComments.push([this.getDateFromHour(fav.key), this.getPercentValue(fav.value, this.dataOverview.comments) ]);
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
			value: this.dataOverview.audiencesAveragePublicAge.key.replace("-", "to"),
			text: 'Most of the page’s audience is between '
			+ this.dataOverview.audiencesAveragePublicAge.key.replace("-", "and") + ' years old, '
			+ 'representing ' + this.dataOverview.audiencesAveragePublicAge.value + '% of the total.'
		}
	}

	// getComplementPublicAge() {

	// 	this.complementPublicAge = {
	// 		icon: {
	// 			icon: 'fa-arrow-circle-up',
	// 			color: '#ffd71e'
	// 		},
	// 		title: 'Maior faixa etária',
	// 		value: this.dataOverview.audiencesAveragePublicAge.key.replace("-", "a"),
	// 		text: 'A maior parte dos fãs da página tem entre '
	// 		+ this.dataOverview.audiencesAveragePublicAge.key.replace("-", "e") + ' anos, '
	// 		+ 'representando ' + this.dataOverview.audiencesAveragePublicAge.value + '% do total.'
	// 	}
	// }

	getComplementPublicGender() {
		
		let legend = [
			{icon: {icon: 'fa-female', color:'#f74e7e'}, value: this.getPercentValue(this.dataOverview.audiencesAveragePublicGender[1].value, this.dataOverview.followers)+'%'},
			{icon: {icon: 'fa-male', color:'#00d6f6'}, value: this.getPercentValue(this.dataOverview.audiencesAveragePublicGender[0].value, this.dataOverview.followers)+'%'}
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
						"value" : this.getPercentValue(this.dataOverview.audiencesAveragePublicGender[0].value, this.dataOverview.followers), 
						"color": "#00d6f6"
					}, 
					{ 
						"label": "Women",
						"value" : this.getPercentValue(this.dataOverview.audiencesAveragePublicGender[1].value, this.dataOverview.followers), 
						"color": "#f74e7e"
					}
				],
				height: 100,
				isDonut: true,
				legend: legend
			},
			title: 'Divisão do público',
			orientation: 'center'
		}
	}

	timeWithMoreFansOnline() {

		let onlinePercentage = 0;
		let hourMostOnline = 0;
		this.dataOverview.audiencesOnlineFollowersByHour.map(
			fav => {
				if (this.getPercentValue(fav.value, this.dataOverview.followers) > onlinePercentage) {

					onlinePercentage 	= this.getPercentValue(fav.value, this.dataOverview.followers);
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
			value: hourMostOnline + ' at ' + ( (hourMostOnline as number) + 1), 
			smallText: '(' + onlinePercentage + '%)', 
			text: onlinePercentage + '% of the audience\page fans were online.'
		}

		// this.timeWithMoreFansOnlineFooter = {
		// 	icon: {
		// 		icon: 'fa-clock-o',
		// 		color: '#f6cb00'
		// 	},
		// 	title: 'Horário com mais seguidores online',
		// 	value: hourMostOnline + ' às ' + ( (hourMostOnline as number) + 1), 
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

					onlinePercentage 	= this.getPercentValue(fav.value, this.dataOverview.followers);
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
			value: dayMostOnline, 
			smallText: '(' + onlinePercentage + '%)', 
			text: (onlinePercentage + '% of the audience\page fans were online on ' + dayMostOnline)
		}

		// this.weekDayWithMoreFansOnlineFooter = {
		// 	icon: {
		// 		icon: 'fa-calendar',
		// 		color: '#f6cb00'
		// 	},
		// 	title: 'Dia com maior alcance',
		// 	value: dayMostOnline, 
		// 	smallText: '(' + onlinePercentage + '%)', 
		// 	text: (onlinePercentage + '% dos seus seguidores estiveram conectados na ' + dayMostOnline)
		// }
	}
/*
	getValuesHistorical_onlineFollowersByHour(): any {
		if ((this.dataHistorical != undefined) && (this.dataHistorical.historical_onlineFollowersByHour != undefined)) {

			this.historical_onlineFollowersByHour = [
				{
					key: 'Online',
					values: this.dataHistorical.historical_onlineFollowersByHour.map(
					fav => {
						return {
							x: fav.date, 
							y: fav.value
						}
					}),
					color: '#00d6f6'
				}
			];
		}
	}

	getValuesHistorical_commentsPerDay(): any {
		if ((this.dataHistorical != undefined) && (this.dataHistorical.historical_commentsPerDay != undefined)) {
			
			let dataColumnCommentsFollowers 	: any = [];
			let dataColumnNOCommentsFollowers 	: any = [];

			this.dataHistorical.historical_commentsPerDayFollowers.map(
				fav => {

					dataColumnCommentsFollowers.push({
						x: this.formatDate(fav.dateCompare), 
						y: fav.followerComments,
						color: '#00d6f6'
					});
				}
			);

			this.dataHistorical.historical_commentsPerDayNoFollowers.map(
				fav => {
					dataColumnNOCommentsFollowers.push({
						x: this.formatDate(fav.dateCompare), 
						y: fav.noFollowerComments,
						color: '#b8b8b8'
					});
				}
			);

			this.historical_commentsPerDay = [
				{
					key: 'Followers',
					values: dataColumnCommentsFollowers
				},
				{
					key: 'No Followers',
					values: dataColumnNOCommentsFollowers
				}
			];
		}
	}

	//LIKES POR DIA
	getValuesHistorical_likesPerDay(): any {
		if ((this.dataHistorical != undefined) && (this.dataHistorical.historical_likesPerDayFollowers != undefined)) {

			let dataColumnFollowers 	: any = [];
			let dataColumnNOFollowers 	: any = [];

			this.dataHistorical.historical_likesPerDayFollowers.map(
				fav => {

					dataColumnFollowers.push({
						x: this.formatDate(fav.date), 
						y: fav.value,
						color: '#00d6f6'
					});
				}
			);

			this.dataHistorical.historical_likesPerDayNoFollowers.map(
				fav => {
					dataColumnNOFollowers.push({
						x: this.formatDate(fav.date), 
						y: fav.value,
						color: '#b8b8b8'
					});
				}
			);

			this.historical_likesPerDay = [
				{
					key: 'Followers',
					values: dataColumnFollowers
				},
				{
					key: 'No Followers',
					values: dataColumnNOFollowers
				}
			];
		}
	}

	getValuesHistorical_onlineFollowersByDay(): any {
		if ((this.dataHistorical != undefined) && (this.dataHistorical.historical_onlineFollowersByDay != undefined)) {

			let dataColumn 	: any = [];
			this.dataHistorical.historical_onlineFollowersByDay.map(
				fav => {
					dataColumn.push({
						x: fav.label + '', 
						y: fav.value,
						color: '#00d6f6'
					});
				}
			);

			this.historical_onlineFollowersByDay = [
				{
					key: 'Online',
					values: dataColumn
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

	formatDate(date){
		date = new Date(date * 1000);
		return this.datePipe.transform(date, 'dd/MM');
	}

	formatHour(hour){
		return '' + (hour < 10 ? '0' + hour : hour) + ':00';  
	}
*/
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
		console.log(this.datePipe.transform(date, 'MM/dd'));
		return this.datePipe.transform(date, 'MM/dd');
	}

	formatHour(hour){
		//return '' + (hour < 10 ? '0' + hour : hour);
		return hour;
	}

	xAxisTickFunctionMonth(d){
		let monthNames : string[] = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
		// let monthNames : string[] = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];
		return monthNames[d];
	}

	xAxisTickFunctionDay(d){
		// let weekDays: string[] = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'];

		let weekDays: string[] = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
		return weekDays[d];
	}
}