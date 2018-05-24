import { ProfilesService } from './../../../core/services/profiles/profiles.service';
import { DatePipe } from '@angular/common';
import { Component, DoCheck, Input, KeyValueDiffers, ViewChild } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { AbbreviateNumberPipe } from 'core/pipes/abbreviateNumber.pipe';
import { HHourPipe } from 'core/pipes/hHour.pipe';
import { MonthNamePipe } from 'core/pipes/monthName.pipe';
import { ControlService } from 'core/services/control/control.service';
import { LinkedinService } from 'core/services/linkedin/linkedin.service';
import { IAsideTextIcon } from 'modelo/complements/Complements';
import { Profile } from 'modelo/profile/Profile';
import { ReportLinkedinOverviewDTO } from 'modelo/reports/ReportLinkedinOverviewDTO';

declare let $;

@Component({
	moduleId: module.id,
	selector: 'linkedin-report', 
	templateUrl: './linkedin-report.component.html',
	styleUrls: ['./linkedin-report.component.css']
}) 

export class LinkedinReportComponent implements DoCheck {

	allowPersist : boolean = false;
	isLoading : boolean = true;
	checkedProfile: Profile;
	
	@ViewChild('mychannels')
	public mychannels: any;

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
	data: ReportLinkedinOverviewDTO;

	companyImpressionsData;
	companyLikesData;
	companyCommentsData;
	companySharesData;
	companyClicksData;

	followersGrowthByDayData;
	followersGrowthAside : IAsideTextIcon;
	followersGrowthAside1 : IAsideTextIcon;
	followersGrowthAside2 : IAsideTextIcon;
	followersGrowthAside3 : IAsideTextIcon;

	impressionsByPeriodFooter;
	companySizeMostFollowersFooter;

	dailyPostAverage;
	interactionsAverageByPost;
	companiesSizeAverage;
	dailyImpressionAverage;

	numPostsByDayData;
	impressionsByDayData;

	likesByPeriod = 0;
	commentsByPeriod = 0;
	sharesByPeriod = 0;
	impressionsByPeriod = 0;
	postsByPeriod = 0;
	
	likesByDayData;
	commentsByDayData;
	sharesByDayData;
	totalLikesByPeriod;
	totalCommentsByPeriod;
	totalSharesByPeriod;

	followStatisticsSenioritiesData = [];
	followStatisticsFunctionsData1 = [];
	followStatisticsFunctionsData2 = [];
	followStatisticsCompanySizeData = [];
	followStatisticsRegionsData;
	followStatisticsIndustriesData;

	companyUpdates;

	// weekDays: string[] = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'];
	// weekDaysFull: string[] = ['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado']

	weekDays: string[] = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
	weekDaysFull: string[] = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']

	legendsType1 = {
		legends: true, 
		color: ['#20ffa2']
	}

	legendsType2 = {
		legends: true,
		color: ['#0085be', '#01aed9', '#01d6f8']
	}

	legendsType3 = {
		legends: true, 
		color: ['#01d6f8']
	}

	constructor(
		private linkedinService: LinkedinService, 
		private datePipe: DatePipe, 
		private monthNamePipe: MonthNamePipe,
		private hHourPipe: HHourPipe, 
		private sanitizer: DomSanitizer,
		private abbreviateNumberPipe: AbbreviateNumberPipe,
		private controlService: ControlService,
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
				this.getReport();
			}
		}
	}

	getXLS() {
		this.linkedinService.getLinkedinReportXLS(this.data, this.profile.idProfile)
			.subscribe(
				result => this.downloadXLS(result),
				error => console.log(error)
			);
	}

	downloadXLS(result) {
		window.open(this.controlService.context_app + 'woopsocial/get_xls?file=' + 
		result.fileName + '&filename=' + result.formattedFileName + '&idAccount=' + result.idAccount);
		// window.open("D:\\Documentos\\Documents\\SettingsIntelligence\\DONE\\Reports\\" + result.fileName);
		// this.saveXLSFile(result);
	}

	// saveXLSFile(result) {
	// 	var a = document.createElement("a");
	// 	a.href = "D:\\Documentos\\Documents\\SettingsIntelligence\\DONE\\Reports\\" + result.fileName;
	// 	a['download'] = result.fileName;
	// 	var clickEvent = document.createEvent("MouseEvents");
	// 	// Use of deprecated function to satisfy TypeScript.
	// 	clickEvent.initMouseEvent("click", true, false,
	// 		document.defaultView, 0, 0, 0, 0, 0,
	// 		false, false, false, false, 0, null);
	// 	a.dispatchEvent(clickEvent);
	// 	document.body.removeChild(a);
	// }

 	// saveXLSFile(result) {
	
	// 	var oldLocation = window.location.href;
	// 	// var url = this.controlService.context_app + 'opsocial/get_xls?file=' + 
	// 	// result.fileName + '&filename=' + result.formattedFileName + '&idAccount=' + result.idAccount;
	// 	var url = "D:\\Documentos\\Documents\\SettingsIntelligence\\DONE\\Reports\\" + result.fileName;

	// 	var a = document.createElement('a');
	// 	a.id = 'xlsLinkedinLink'
	// 	a.href = url;
	// 	a.className += "no-adress";
	// 	a.style.display = 'none';
	// 	document.body.appendChild(a);
	// 	a.click();
	// 	document.body.removeChild(a);
		
	// 	window.history.pushState("", "", oldLocation);
	// }

	getReport() {
		if(this.validateForm()) {
			this.linkedinService.getReport(this.profile.idProfile, this.dateFrom.getTime(), this.dateUntil.getTime())
			.subscribe(
				result => this.mountCharts(result),
				error => console.log(error)
			);
		}
	}

	validateForm():boolean {
		if(this.dateFrom && this.dateUntil && this.profile) {
			return true;
		} else return false; 
	}

	mountCharts(report) { //
		this.isLoading = false;

		this.data = report;

		this.companyGeneralInfo();
		this.followersGrowthByDay(); // Graph 2
		this.sharesByDay(); // Graph 3
		this.impressionsByDay(); // Graph 4
		this.likesCommentsSharesByDay(); // Graph 5, 6 e 7
		this.followStatistics(); // Graph 8, 9, 10, 11, 12
		this.mountCompanyUpdates(); // Graph 13+
		
		this.isLoading = false;
	}

	companyGeneralInfo() {
		var index = 0;
		this.data.linkedinStatusUpdate.map(
		fav => {
			if(index > 0) {
				this.companyImpressionsData += fav.impressionCount;
				this.companyLikesData += fav.likeCount;
				this.companyCommentsData += fav.commentCount;
				this.companySharesData += fav.shareCount;
				this.companyClicksData += fav.clickCount; 
				index++;
			} else {
				this.companyImpressionsData = fav.impressionCount;
				this.companyLikesData = fav.likeCount;
				this.companyCommentsData = fav.commentCount;
				this.companySharesData = fav.shareCount;
				this.companyClicksData = fav.clickCount; 
				index++;
			}
		})
	}

	followersGrowthByDay() {
		this.followersGrowthByDayData = 
		[
			{
				"key": 'Organic',
				"values": this.data.linkedinFollowers.map(
				fav => {
					return [
						fav.time,
						fav.organicFollowerCount
					]
				}),
				"color": "#00deff"
			},
			{
				"key": 'Paid',
				"values": this.data.linkedinFollowers.map(
				fav => {
					return [
						fav.time,
						fav.paidFollowerCount
					]
				}),
				"color": "#00aed9"
			},
			{
				"key": 'Network',
				"values": this.data.linkedinFollowers.map(
				fav => {
					return [
						fav.time,
						fav.totalFollowerCount
					]
				}),
				"color": "#008ab7"
			}
		];
		
		let bigger = 0;
		let index = 0;
		
		this.data.linkedinFollowers.reduce((a,b,c) => {
			index = (b.totalFollowerCount - a.totalFollowerCount) > bigger ? c : index;
			bigger = (b.totalFollowerCount - a.totalFollowerCount) > bigger ? (b.totalFollowerCount - a.totalFollowerCount) : bigger;

			return b;
		}, 0);

		this.followersGrowthAside = {
			icon: {
				icon: 'fa-users',
				color: '#00d6f6'
			},
			title: 'Total de seguidores',
			value: this.data.followStatistics.count,
			text: 'Day ' + this.formatDate(this.data.linkedinFollowers[index].time) 
			+ ' registered the higher page growth , which was ' 
			+ this.abbreviateNumberPipe.transform(bigger) + ' followers.'
		}
		
		// this.followersGrowthAside = {
		// 	icon: {
		// 		icon: 'fa-users',
		// 		color: '#00d6f6'
		// 	},
		// 	title: 'Total de seguidores',
		// 	value: this.data.followStatistics.count,
		// 	text: 'Dia ' + this.formatDate(this.data.linkedinFollowers[index].time) 
		// 	+ ' registrou o maior índice de crescimento, com um aumento de ' 
		// 	+ this.abbreviateNumberPipe.transform(bigger) + ' seguidores.'
		// }
		
		this.followersGrowthAside1 = {
			icon: {
				icon: 'fa-user',
				color: '#00d6f6'
			},
			title: 'Total de seguidores orgânicos',
			value: this.abbreviateNumberPipe.transform(this.data.linkedinFollowers[this.data.linkedinFollowers.length-1].organicFollowerCount)
		}
		
		this.followersGrowthAside2 = {
			icon: {
				icon: 'fa-user',
				color: '#00aed9'
			},
			title: 'Total de seguidores pagos',
			value: this.abbreviateNumberPipe.transform(this.data.linkedinFollowers[this.data.linkedinFollowers.length-1].paidFollowerCount)
		}
		
		this.followersGrowthAside3 = {
			icon: {
				icon: 'fa-user',
				color: '#008ab7'
			},
			title: 'Total de seguidores rede',
			value: this.abbreviateNumberPipe.transform(this.data.linkedinFollowers[this.data.linkedinFollowers.length-1].totalFollowerCount)
		}
	}

	impressionsByDay() {
		let total = 0;
		this.impressionsByDayData = 
		[
			{
				"key": "Organic",
				"values": this.data.impressionsByDay.map(
					fav => {
						total++;
						this.impressionsByPeriod += fav.impressionCount;
						return {
							x: fav.time, 
							y: fav.impressionCount
						}
				}),
				"color": "#00deff"
				}
		]

		this.impressionsByPeriodFooter = {
			icon: {
				icon: 'fa fa-eye fa-1',
				color: '#00deff'
			},
			title: 'Total de Impressões',
			value: this.abbreviateNumberPipe.transform(this.impressionsByPeriod), 
			text: "Lorem ipsum dolor sit amet, consectetur Aenean interdum vestibulum sollicitudin."
		}

		this.dailyImpressionAverage = {
			icon: {
				icon: 'fa fa-bar-chart',
				color: '#00deff'
			},
			title: 'Média Diária de Impressões',
			value: (this.impressionsByPeriod / total).toFixed(2), 
			text: "Lorem ipsum dolor sit amet, consectetur Aenean interdum vestibulum sollicitudin."
		}
	}

	likesCommentsSharesByDay() {
		let dayWithMoreLikes = 0;
		let numberOfLikes = 0;

		this.likesByDayData = 
		[
			{
				key: "Like",
				values: this.data.linkedinStatusUpdate.map(
					fav => {
						if(fav.likeCount > numberOfLikes) {
							numberOfLikes = fav.likeCount;
							dayWithMoreLikes = fav.time;
						}

						this.likesByPeriod += fav.likeCount;
						return {
							x: fav.time, 
							y: fav.likeCount
						}
				}),
				color: "#00deff",
				margin : {
					right: 45,
					left: 0
				}
			}
		];

		this.totalLikesByPeriod = {
			icon: {
				icon: 'fa fa-thumbs-up',
				color: '#00deff'
			},
			title: 'Curtir',
			value: this.abbreviateNumberPipe.transform(this.likesByPeriod), 
			text: "On the day " + this.formatDate(dayWithMoreLikes) + " you had the higher number of likes," + 
			" which represents " + (numberOfLikes/this.likesByPeriod)*100 + "% of all the likes during the selected period."
		}

		let dayWithMoreComments = 0;
		let numberOfComments = 0;

		this.commentsByDayData = 
		[
			{
				key: "Comments",
				values: this.data.linkedinStatusUpdate.map(
					fav => {
						if(fav.commentCount > numberOfComments) {
							numberOfComments = fav.commentCount;
							dayWithMoreComments = fav.time;
						}
						this.commentsByPeriod += fav.commentCount;
						return {
							x: fav.time, 
							y: fav.commentCount
						}
				}),
				color: "#00deff",
				margin : {
					right: 45,
					left: 0
				}
			}
		];

		this.totalCommentsByPeriod = {
			icon: {
				icon: 'fa fa-comments',
				color: '#00deff'
			},
			title: 'Shares',
			value: this.abbreviateNumberPipe.transform(this.commentsByPeriod), 
			text: "On the day " + this.formatDate(dayWithMoreComments) + " you had the higher number of comments," + 
			" which represents " + (numberOfComments/this.commentsByPeriod)*100 + "% of all the likes during the selected period."
		}

		let dayWithMoreShares = 0;
		let numberOfShares = 0;

		this.sharesByDayData = 
		[
			{
				key: "Shares",
				values: this.data.linkedinStatusUpdate.map(
					fav => {
						if(fav.shareCount > numberOfShares) {
							numberOfShares = fav.shareCount;
							dayWithMoreShares = fav.time;
						}
						this.sharesByPeriod += fav.shareCount;
						return {
							x: fav.time, 
							y: fav.shareCount
						}
				}),
				color: "#00deff"
			}
		];

		this.totalSharesByPeriod = {
			icon: {
				icon: 'fa fa-share',
				color: '#00deff'
			},
			title: 'Shares',
			value: this.abbreviateNumberPipe.transform(this.sharesByPeriod), 
			text: "On the day " + this.formatDate(dayWithMoreShares) + " you had the higher number of shares," + 
			" which represents " + (numberOfShares/this.sharesByPeriod)*100 + "% of all the likes during the selected period."
		}
	}

	sharesByDay() {

		let dayWithMorePosts = '-';
		let numberOfPosts = 0;

		this.numPostsByDayData = [{
			key: 'Publicações',
			values: this.data.linkedinSharesByDay.map(
				fav => {
					if(fav.sum > numberOfPosts) {
						numberOfPosts = fav.sum;
						dayWithMorePosts = fav.month + '/' + fav.day;
					}
					this.postsByPeriod += fav.sum;
					if(fav.day < 10) {
						fav.day = '0' + fav.day;
					}
					if(fav.month < 10) {
						fav.month = '0' + fav.month;
					}
					return {
						"label": fav.month  + '/' + fav.day,
						"value": fav.sum,
						"color": "#00deff"
					}
				})
		}];

		this.dailyPostAverage = {
			icon: {
				icon: 'fa fa-bar-chart',
				color: '#00deff'
			},
			title: 'Média diária de posts',
			value: this.data.linkedinPostAverageByDay.toFixed(2), 
			text: "The day " + dayWithMorePosts + " is when you posted the most, representing " 
			+ (numberOfPosts/this.postsByPeriod)*100 + "% of the full amount on the selected time. "
		}

		let interactionsAverageByPostValue = 0;
		if(this.postsByPeriod > 0) {
			interactionsAverageByPostValue = (this.likesByPeriod + this.commentsByPeriod + this.sharesByPeriod) / this.postsByPeriod;
		}

		this.interactionsAverageByPost = {
			icon: {
				icon: 'fa fa-hand-o-up fa-1',
				color: '#00deff'
			},
			title: 'Média Interações por post',
			value: interactionsAverageByPostValue.toFixed(2),
			text: "Your posts had " + (this.likesByPeriod + this.commentsByPeriod + this.sharesByPeriod) 
			+ " interactions on the selected time."
		}
	}

	followStatistics() {
		// https://developer.linkedin.com/docs/reference/seniority-codes
		var total = 0;

		this.data.followStatistics.seniorities.values.map(
		fav => {
			switch (fav.entryKey) {
				case '1': this.followStatisticsSenioritiesData.push({'key': 'Unpaid', 'value': fav.entryValue}); break;
				case '2': this.followStatisticsSenioritiesData.push({'key': 'Training', 'value': fav.entryValue}); break;
				case '3': this.followStatisticsSenioritiesData.push({'key': 'Entry-level', 'value': fav.entryValue}); break;
				case '4': this.followStatisticsSenioritiesData.push({'key': 'Senior', 'value': fav.entryValue}); break;
				case '5': this.followStatisticsSenioritiesData.push({'key': 'Manager', 'value': fav.entryValue}); break;
				case '6': this.followStatisticsSenioritiesData.push({'key': 'Director', 'value': fav.entryValue}); break;
				case '7': this.followStatisticsSenioritiesData.push({'key': 'Vice President (VP)', 'value': fav.entryValue}); break;
				case '8': this.followStatisticsSenioritiesData.push({'key': 'Chief X Officer (CxO)', 'value': fav.entryValue}); break;
				case '9': this.followStatisticsSenioritiesData.push({'key': 'Partner', 'value': fav.entryValue}); break;
				case '10': this.followStatisticsSenioritiesData.push({'key': 'Owner', 'value': fav.entryValue}); break;
			}
		})

		// https://developer.linkedin.com/docs/reference/job-function-codes
		this.data.followStatistics.functions.values.map(
		fav => {
			switch (fav.entryKey) {
				// Otimizar essa divisão dos arrays
				case '-1': this.followStatisticsFunctionsData1.push({'key': 'None', 'value': fav.entryValue}); break;
				case '1': this.followStatisticsFunctionsData2.push({'key': 'Accounting', 'value': fav.entryValue}); break;
				case '2': this.followStatisticsFunctionsData1.push({'key': 'Administrative', 'value': fav.entryValue}); break;
				case '3': this.followStatisticsFunctionsData2.push({'key': 'Arts and Design', 'value': fav.entryValue}); break;
				case '4': this.followStatisticsFunctionsData1.push({'key': 'Business Development', 'value': fav.entryValue}); break;
				case '5': this.followStatisticsFunctionsData2.push({'key': 'Community & Social Services', 'value': fav.entryValue}); break;
				case '6': this.followStatisticsFunctionsData1.push({'key': 'Consulting', 'value': fav.entryValue}); break;
				case '7': this.followStatisticsFunctionsData2.push({'key': 'Education', 'value': fav.entryValue}); break;
				case '8': this.followStatisticsFunctionsData1.push({'key': 'Engineering', 'value': fav.entryValue}); break;
				case '9': this.followStatisticsFunctionsData2.push({'key': 'Entrepreneurship', 'value': fav.entryValue}); break;
				case '10': this.followStatisticsFunctionsData1.push({'key': 'Finance', 'value': fav.entryValue}); break;
				case '11': this.followStatisticsFunctionsData2.push({'key': 'Healthcare Services', 'value': fav.entryValue}); break;
				case '12': this.followStatisticsFunctionsData1.push({'key': 'Human Resources', 'value': fav.entryValue}); break;
				case '13': this.followStatisticsFunctionsData2.push({'key': 'Information Technology', 'value': fav.entryValue}); break;
				case '14': this.followStatisticsFunctionsData1.push({'key': 'Legal', 'value': fav.entryValue}); break;
				case '15': this.followStatisticsFunctionsData2.push({'key': 'Marketing', 'value': fav.entryValue}); break;
				case '16': this.followStatisticsFunctionsData1.push({'key': 'Media & Communications', 'value': fav.entryValue}); break;
				case '17': this.followStatisticsFunctionsData2.push({'key': 'Military & Protective Services', 'value': fav.entryValue}); break;
				case '18': this.followStatisticsFunctionsData1.push({'key': 'Operations', 'value': fav.entryValue}); break;
				case '19': this.followStatisticsFunctionsData2.push({'key': 'Product Management', 'value': fav.entryValue}); break;
				case '20': this.followStatisticsFunctionsData1.push({'key': 'Program & Product Management', 'value': fav.entryValue}); break;
				case '21': this.followStatisticsFunctionsData2.push({'key': 'Purchasing', 'value': fav.entryValue}); break;
				case '22': this.followStatisticsFunctionsData1.push({'key': 'Quality Assurance', 'value': fav.entryValue}); break;
				case '23': this.followStatisticsFunctionsData2.push({'key': 'Real Estate', 'value': fav.entryValue}); break;
				case '24': this.followStatisticsFunctionsData1.push({'key': 'Research', 'value': fav.entryValue}); break;
				case '25': this.followStatisticsFunctionsData2.push({'key': 'Sales', 'value': fav.entryValue}); break;
				case '26': this.followStatisticsFunctionsData1.push({'key': 'Support', 'value': fav.entryValue}); break;
			}
		})

		// https://developer.linkedin.com/docs/reference/company-size-codes
		this.data.followStatistics.companySizes.values.map(
		fav => {
			fav.entryValue = parseInt(fav.entryValue);
			switch (fav.entryKey) {
				case 'A': this.followStatisticsCompanySizeData.push({'label': 'Self', 'value': fav.entryValue, 'color': '#00deff'}); break;
				case 'B': this.followStatisticsCompanySizeData.push({'label': '1-10', 'value': fav.entryValue, 'color': '#00deff'}); break;
				case 'C': this.followStatisticsCompanySizeData.push({'label': '11-50', 'value': fav.entryValue, 'color': '#00deff'}); break;
				case 'D': this.followStatisticsCompanySizeData.push({'label': '51-200', 'value': fav.entryValue, 'color': '#00deff'}); break;
				case 'E': this.followStatisticsCompanySizeData.push({'label': '201-500', 'value': fav.entryValue, 'color': '#00deff'}); break;
				case 'F': this.followStatisticsCompanySizeData.push({'label': '501-1K', 'value': fav.entryValue, 'color': '#00deff'}); break;
				case 'G': this.followStatisticsCompanySizeData.push({'label': '1K-5K', 'value': fav.entryValue, 'color': '#00deff'}); break;
				case 'H': this.followStatisticsCompanySizeData.push({'label': '5K-10K', 'value': fav.entryValue, 'color': '#00deff'}); break;
				case 'I': this.followStatisticsCompanySizeData.push({'label': '10K+', 'value': fav.entryValue, 'color': '#00deff'}); break;
			}
		})

		this.followersByCompanySize(); // Graph 10

		// https://developer.linkedin.com/docs/reference/geography-codes
		let dataCities = [];
		let countries = Object.keys(this.data.linkedinFollowersByCountry);

		for(let i=0; i < countries.length; i++) {
			dataCities.push([
				{value: countries[i], class: 'linkedin-table-row'},
				{value: this.data.linkedinFollowersByCountry[countries[i]], class: 'linkedin-table-row pull-right'}
			]);
			
		}

		this.followStatisticsRegionsData = {
			cols: [
				{value: 'País', class: 'left'}, 
				{value: 'Usuários', class: 'right p-r-0', width: '100px'}
			], 
			rows: dataCities
		}
		
		// https://developer.linkedin.com/docs/reference/industry-codes
		let dataIndustries = [];

		this.data.followStatistics.industries.values.map(
		fav => {
			dataIndustries.push([
				{value: fav.entryKey, class: 'linkedin-table-row'},
				{value: fav.entryValue, class: 'linkedin-table-row pull-right'}
			]);
		})

		this.followStatisticsIndustriesData = {
			cols: [
				{value: 'Tipo de Indústria', class: 'left'}, 
				{value: 'Usuários', class: 'right p-r-0', width: '100px'}
			], 
			rows: dataIndustries
		}
	}

	followersByCompanySize() {
		this.followStatisticsCompanySizeData = [{
			key: 'Company Size',
			values: this.followStatisticsCompanySizeData
		}];

		this.companySizeMostFollowersFooter = {
			icon: {
				icon: 'fa fa-bar-chart fa-1',
				color: '#00deff'
			},
			title: 'Tamanho médio das empresas',
			value:  this.followStatisticsCompanySizeData[0].values[0].label,
			text: "Most of the companies following you are between " + 
				   this.followStatisticsCompanySizeData[0].values[0].label + " employees."
				//    , which represents x% of your audience. 
		}
	}

	mountCompanyUpdates() {

		let companyUpdatesData = [];

		this.data.linkedinPosts.map(
		fav => {
			companyUpdatesData.push([
				{value: this.formatDateReportStatus(fav.postTime*1000), class: 'left'},
				{value: this.formatPostImageAndOrComment(fav.postSubmittedImageUrl, fav.postSubmittedUrl, fav.postComment), class: 'left'},
				{value: this.formatStatusCount('fa fa-thumbs-up fa-1',  fav.numLikes), class: 'right'},
				{value: this.formatStatusCount('fa-comments', fav.totalComments), class: 'right'}

			]);
		})

		this.companyUpdates = {
			cols: [
				{value: 'Data', class: 'left', width: '60px'}, 
				{value: 'Post', class: 'left'},
				{value: 'Curtir', class: 'right', width: '120px'},
				{value: 'Comentários', class: 'right', width: '120px'}
			], 
			rows: companyUpdatesData
		}
	}

	addRemoveObjectChecked(event) {
		if(event.checked) {
			this.checkedProfile = event.profile;
		} else{
			this.checkedProfile = null;
		}

		if(this.checkedProfile != null) {
			this.allowPersist = false;
		} else {
			this.allowPersist = true;
		}
	}

	checkSocialMedia(event) {
		this.mychannels.checkedProfiles = event;
	}

	formatDate(date) {
		date = new Date(date);
		return this.datePipe.transform(date, 'MM/dd');
	}

	formatDateReportStatus(date) {
		return this.sanitizer.bypassSecurityTrustHtml(`<div>
					<p class="fa fa-calendar m-b-0 m-l-12 l-h-12"></p>
					<p class="font-montserrat date m-b-0 l-h-12"><strong>` + this.monthNamePipe.transform(this.datePipe.transform(date, 'MM')) + ` ` + this.datePipe.transform(date, 'dd') + `</strong></p>
					<p class="font-montserrat year l-h-12 m-l-4 m-b-0">` + this.datePipe.transform(date, 'yyyy') + `</p>
				</div>
				<div class="time">
					<p class="fa fa-clock-o m-b-0 m-l-13 l-h-12"></p>
					<p class="font-montserrat hour l-h-12 m-b-0"><strong>` + this.hHourPipe.transform(this.datePipe.transform(date, 'HH:mm')) + `</strong></p>
				</div>`);
	}

	formatStatusCount(icon, label) {
		return this.sanitizer.bypassSecurityTrustHtml(`<i class="cyan fa ` + icon + `" aria-hidden="true"></i> <strong>` + label + `</strong>`);
	}
	
	formatPostImageAndOrComment(submittedImageUrl, postSubmittedUrl, postComment) {
		if(submittedImageUrl) {
			return this.sanitizer.bypassSecurityTrustHtml(
				`<div class="linkedin-share-img pull-left"><a href="` + postSubmittedUrl + `">
					<img class="" src="` + submittedImageUrl + `">
				</a></div>` 
				+ postComment
			);
		} else {
			return postComment; 
		}
	}

	getPDF() {
		window.print();
	}
}