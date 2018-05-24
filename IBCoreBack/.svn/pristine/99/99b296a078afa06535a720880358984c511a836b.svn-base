package br.com.opsocial.server.utils.reports;

import br.com.opsocial.ejb.das.MaintenanceReportGenerateRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.report.ReportGenerate;
import br.com.opsocial.server.utils.RecoverMaintenance;

public class ReportGenerateRunnable implements Runnable {

	private Profile profile;
	private Integer reportDays;
	
	public static final int REPORT_DAYS = 120;
	
	public ReportGenerateRunnable(Profile profile, Integer reportDays) {
		this.profile = profile;
		this.reportDays = reportDays;
	}
	
	@Override
	public void run() {
	
		try {
			
			if(reportDays > 0) {
				
				if(profile.getType().equals(Profile.FACEBOOK_PAGE)) {
					new ReportFacebook(profile.getNetworkId(), profile.getToken()).retrieveInsightsForPeriod(reportDays);
				} else if(profile.getType().equals(Profile.INSTAGRAM)) {
					new ReportInstagram(profile, true).retrieveInsightsForPeriod(reportDays);
				}
				
			}

			MaintenanceReportGenerateRemote reportGenerateRemote = (MaintenanceReportGenerateRemote) RecoverMaintenance.recoverMaintenance("ReportGenerate");
			
			ReportGenerate reportGenerate = reportGenerateRemote.getEntity(profile.getNetworkId(), profile.getType());
			
			reportGenerate.setState(ReportFacebook.FINALIZED);
			reportGenerateRemote.save(reportGenerate);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}	
}
