package br.com.opsocial.server.utils.reports;

import java.util.ArrayList;
import java.util.List;

public class ReportGenerateThread extends Thread {

	private List<ReportGenerateRunnable> reportGenerateRunnables;
	
	public ReportGenerateThread() {
		this.reportGenerateRunnables = new ArrayList<ReportGenerateRunnable>();
	}
	
	public ReportGenerateThread(List<ReportGenerateRunnable> reportGenerateRunnables) {
		this.reportGenerateRunnables = reportGenerateRunnables;
	}
	
	public void addReportGenerateRunnable(ReportGenerateRunnable reportGenerateRunnable) {
		this.reportGenerateRunnables.add(reportGenerateRunnable);
	}
	
	@Override
	public void run() {
		
		for(ReportGenerateRunnable reportGenerateRunnable : reportGenerateRunnables) {
			reportGenerateRunnable.run();
		}
		
	}
	
}
