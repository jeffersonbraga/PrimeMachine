import { Component, ViewChild } from '@angular/core';
import { TopReportComponent } from 'core/componentes/top-report/top-report.component';
import { Profile } from 'modelo/profile/Profile';

@Component({
    moduleId: module.id,
    selector: 'report',
    templateUrl: './report.component.html',
    styleUrls: ['./report.component.css']
})
export class ReportComponent {
    dateFrom: Date;
    dateUntil: Date;
    type = '';
    profile;
    activeModuleName = 'reports';
    groupType: any = 'P';
    static: boolean;
    previewProfile: Profile = new Profile();

    reportConfigs = {
        dateFrom: null,
        dateUntil: null,
        profile: null
    };

    public showCheckAllBtn = false;

    @ViewChild('topReport')
    topReport: TopReportComponent;

    updateDate(event) {
        this.dateFrom = event.dateFrom;
        this.dateUntil = event.dateUntil;

        this.reportConfigs.dateFrom = this.dateFrom;
        this.reportConfigs.dateUntil = this.dateUntil;
        this.reportConfigs.profile = this.profile;
    }

    checkSocialMedia(event) {
        if (event[0]) {
            const profile: Profile = event[0];
            if (this.previewProfile.idProfile !== profile.idProfile) {
                this.type = profile.type;
                this.profile = profile;

                this.topReport.setInitialDates();
                this.previewProfile = profile;
            }
        } else {
            this.type = '';
        }
    }

    setToStatic(event) {
        this.static = event;
    }
}
