package br.com.opsocial.server.validation;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.Profile;

public class ReportsValidation {

	private Boolean canGenerateReport;
	
	private static final Long FREEMIUM_REPORT_lIMIT_DAYS = 10L;
	
	public ReportsValidation() {
		this.canGenerateReport = true;
	}
	
	public Boolean canGenerateReport(Profile profile) {
		
		LocalDate today = LocalDate.now(ZoneId.of("UTC"));
		LocalDate insertionDate = LocalDate.from(Instant.ofEpochSecond(profile.getInsertionDate()).atZone(ZoneId.of("UTC")));
		
		long periodInDays = ChronoUnit.DAYS.between(insertionDate, today);
		
		// Verify if user can generate report.
		if(profile.getAccount().getPlanType().equals(Account.FREEMIUM) 
				&& periodInDays > FREEMIUM_REPORT_lIMIT_DAYS) {
			this.canGenerateReport = false;
		}
		
		return this.canGenerateReport;
	}
	
}
