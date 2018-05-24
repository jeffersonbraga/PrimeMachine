import { DatePipe, DecimalPipe } from '@angular/common';
import { Component, DoCheck, Input, KeyValueDiffers } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { HHourPipe } from 'core/pipes/hHour.pipe';
import { MonthNamePipe } from 'core/pipes/monthName.pipe';
import { ControlService } from 'core/services/control/control.service';
import { InstagramService } from 'core/services/instagram/instagram.service';
import { PdfService } from 'core/services/pdf/pdf.service';
import { Profile } from 'modelo/profile/Profile';
import { ProfilesService } from 'core/services/profiles/profiles.service';

@Component({
  selector: 'instagram-report-main',
  templateUrl: './instagram-report-main.component.html',
  styleUrls: ['./instagram-report-main.component.css']
})
export class InstagramReportMainComponent implements DoCheck {

  isLoading: boolean = true;
	reportType: string = 'O';
	
	@Input() reportConfigs = {
		dateFrom: null,
		dateUntil: null,
		profile: null
	};

	differ: any;
	dateFrom: Date;
	dateUntil: Date;
	profile: Profile;

	reportInstagramOverview: any;
	reportInstagramHistorical: any;

	reportState: string = Profile.REPORT_FINALIZED;

	period;

	constructor(
		private instagramService: InstagramService,
		private datePipe: DatePipe,
		private decimalPipe: DecimalPipe,
		private monthNamePipe: MonthNamePipe,
		private hHourPipe: HHourPipe,
		private sanitizer: DomSanitizer,
		private controService: ControlService,
		private pdfService: PdfService,
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

		if (this.reportConfigs && this.reportConfigs.profile && this.profilesService && this.profilesService.singleCheckedProfile && this.reportConfigs.profile.idProfile === this.profilesService.singleCheckedProfile.idProfile) {

			this.dateFrom = this.reportConfigs.dateFrom;
			this.dateUntil = this.reportConfigs.dateUntil;
			this.profile = this.reportConfigs.profile;
			
			if(this.dateFrom && this.dateUntil && this.profile) {
				this.getReport();
				this.setPeriod();
			}
		}
	}

	setPeriod() {
		let timeDiff = this.dateUntil.getTime() - this.dateFrom.getTime();
		this.period = Math.ceil(timeDiff / (1000 * 3600 * 24));
	}

	changeReportTab(reportType) {
		
		if(this.reportType != reportType) {
			this.reportType = reportType;
			this.getReport();
		}
	}

	getReport() {
		this.isLoading = true;

		if (this.validateForm()) {

			if (this.reportType == 'O') {
				this.instagramService.getReportOverview(this.profile, this.dateFrom.getTime(), this.dateUntil.getTime())
					.subscribe(
					result => this.prepareCharts(result),
					error => console.log(error)
					);
			} else {
				this.instagramService.getReportHistorical(this.profile)
					.subscribe(
					result => this.prepareCharts(result),
					error => console.log(error)
					);
			}
		}
	}

	validateForm(): boolean {
		if (this.dateFrom && this.dateUntil && this.profile) {
			return true;
		} else return false;
	}

	prepareCharts(report) {

		this.isLoading = false;

		if (this.reportType == 'O') {
			this.reportInstagramOverview = report.reportInstagramOverview;
			this.reportState = report.reportState;
		} else {
			this.reportInstagramHistorical = report.reportInstagramHistorical;
			this.reportState = report.reportState;
		}
	}

}
