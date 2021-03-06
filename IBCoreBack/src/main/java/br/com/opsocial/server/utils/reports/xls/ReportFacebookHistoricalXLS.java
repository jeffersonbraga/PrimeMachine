package br.com.opsocial.server.utils.reports.xls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.report.facebook.FacebookInfluentialUserDTO;
import br.com.opsocial.client.entity.report.facebook.ReportDetailedFacebookDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookHistoricalDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.report.ReportFacebookLocation;
import br.com.opsocial.ejb.entity.report.SumByDate;
import br.com.opsocial.server.utils.aws.S3Integration;

public class ReportFacebookHistoricalXLS {

	private ReportFacebookHistoricalDTO reportFacebookHistorical;
	private Profile profile;

	private String reportsPath;
	private String fileName;
	private String formattedFileName;

	private HSSFWorkbook wb;
	private Sheet sheetHistorical;

	private CreationHelper createHelper;

	private CellStyle cellStyleHeader;
	private CellStyle cellStyleHeaderData;
	private CellStyle cellStyleHeaderDataPositive;
	private CellStyle cellStyleHeaderDataNegative;
	private CellStyle cellSecondaryHeaderData;
	private CellStyle cellSecondaryHeaderNumber;
	private CellStyle cellSecondaryHeaderCenter;
	private CellStyle cellDateStyle;
	private CellStyle cellStylePreAnalysis;
	private CellStyle cellStylePreAnalysisResult;
	private CellStyle cellStyleHLink;
	private CellStyle cellDataPercentage;

	private Font fontHeader;
	private Font fontHeaderData;

	private Float headerDataRowHeight;
	
	private int rowSheetHistoricalCount;
	
	public ReportFacebookHistoricalXLS(ReportFacebookHistoricalDTO reportFacebookHistorical, Profile profile) {
		this.reportFacebookHistorical = reportFacebookHistorical;
		this.profile = profile;
	}
	
	public void createXLS() {
		
		wb = new HSSFWorkbook();
		
		initiateDefautStyles();

		this.reportsPath = OpSocialBackApplication.GET_DIR_REPORTS_XLS;
		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".xls");
		
		sheetHistorical = wb.createSheet(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.historical.tabname"));
		
		headerDataRowHeight = 1.2F * sheetHistorical.getDefaultRowHeightInPoints();
		
		addMainHeader(sheetHistorical, OpSocialBackApplication.messageByLocaleService.
				getMessage("xls.facebook.report.historical.header", new Object[]{profile.getScreenName()}), 6);
		
		addFanPageGrowth();
		addAboutYourFans();
		addAboutReachedUsers();
		addReactions();
		addComments();
		addShares();
		addClicks();
		addCitiesStats();
		addInfluentFans();
		addFansLikeSource();
		addExternalReferences();
		
		sheetHistorical.setDefaultColumnWidth(25);
		sheetHistorical.setColumnWidth(0, 8000);
		
		try {
			
			File reportFile = new File(reportsPath, fileName);
			FileOutputStream fileOut = new FileOutputStream(reportFile);
			wb.write(fileOut);
			fileOut.close();
			
			new S3Integration().getAmazonS3Client().putObject(
	  				  S3Integration.WOOPSOCIAL_BUCKET_NAME, 
	  				  S3Integration.WOOPSOCIAL_REPORTS_XLS + fileName, 
	  				  new File(reportsPath, fileName));
			
			formattedFileName =  
				OpSocialBackApplication.messageByLocaleService.
				getMessage("xls.facebook.report.historical.doctitle", new Object[]{
						profile.getScreenName(), 
			});
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addClicks() {
		
		rowSheetHistoricalCount += 2;
		
		Row rowClicksTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowClicksTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellClicksTitle = rowClicksTitle.createCell(0);
		cellClicksTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.clicksupper"));
		cellClicksTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 6));
		
		rowSheetHistoricalCount++;
		Row rowClicksPerDayTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowClicksPerDayTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellClicksDateTitle = rowClicksPerDayTitle.createCell(0);
		cellClicksDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.monthyear"));
		cellClicksDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellClicksPerMonthTitle = rowClicksPerDayTitle.createCell(1);
		cellClicksPerMonthTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.clicks"));
		cellClicksPerMonthTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		for(SumByDate clicksMonth : reportFacebookHistorical.getClicksPerMonth()) {
		
			Row rowClicksValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellDateValue = rowClicksValues.createCell(0);
				cellDateValue.setCellValue(clicksMonth.getMonth() + "/" + clicksMonth.getYear());
			
			Cell cellClicksValue = rowClicksValues.createCell(1);
			cellClicksValue.setCellValue(clicksMonth.getSum());
			
			rowSheetHistoricalCount++;
		}
		
		rowSheetHistoricalCount++;
		Row rowTotalClicks = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowTotalClicks.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalClicksTitle = rowTotalClicks.createCell(0);
		cellTotalClicksTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.historical.clicksonperiod"));
		cellTotalClicksTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalClicksValue = rowTotalClicks.createCell(1);
		cellTotalClicksValue.setCellValue(reportFacebookHistorical.getTotalClicks());
		cellTotalClicksValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowMajorClicksDay = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowMajorClicksDay.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMajorClicksMonthTitle = rowMajorClicksDay.createCell(0);
		cellMajorClicksMonthTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.historical.majorclicksmonth"));
		cellMajorClicksMonthTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMajorClicksMonthValue = rowMajorClicksDay.createCell(1);
		cellMajorClicksMonthValue.setCellValue(reportFacebookHistorical.getMajorClicksMonth().getMonth() + "/" +
												reportFacebookHistorical.getMajorClicksMonth().getYear());
		cellMajorClicksMonthValue.setCellStyle(cellStylePreAnalysisResult);
	}

	private void addReactions() {
		
		rowSheetHistoricalCount += 2;
		
		Row rowReactionsTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowReactionsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellReactionsTitle = rowReactionsTitle.createCell(0);
		cellReactionsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.historical.likesreactions"));
		cellReactionsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 6));
		
		rowSheetHistoricalCount++;
		Row rowReactionsPerDayTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowReactionsPerDayTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellReactionsDateTitle = rowReactionsPerDayTitle.createCell(0);
		cellReactionsDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.monthyear"));
		cellReactionsDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellLikesPerDayTitle = rowReactionsPerDayTitle.createCell(1);
		cellLikesPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.like"));
		cellLikesPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellLovePerDayTitle = rowReactionsPerDayTitle.createCell(2);
		cellLovePerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.love"));
		cellLovePerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellHahaPerDayTitle = rowReactionsPerDayTitle.createCell(3);
		cellHahaPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.haha"));
		cellHahaPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellWowPerDayTitle = rowReactionsPerDayTitle.createCell(4);
		cellWowPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.wow"));
		cellWowPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellSorryPerDayTitle = rowReactionsPerDayTitle.createCell(5);
		cellSorryPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.sad"));
		cellSorryPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellAngerPerDayTitle = rowReactionsPerDayTitle.createCell(6);
		cellAngerPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.grr"));
		cellAngerPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		for(int i = 0; i < reportFacebookHistorical.getLikesPerMonth().size(); i++) {
		
			Row rowReactionsValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellDateValue = rowReactionsValues.createCell(0);
			cellDateValue.setCellValue(reportFacebookHistorical.getLikesPerMonth().get(i).getMonth() + "/" + 
										reportFacebookHistorical.getLikesPerMonth().get(i).getYear());
			
			Cell cellLikesValue = rowReactionsValues.createCell(1);
			cellLikesValue.setCellValue(reportFacebookHistorical.getLikesPerMonth().get(i).getSum());
			
			Cell cellLoveValue = rowReactionsValues.createCell(2);
			cellLoveValue.setCellValue(reportFacebookHistorical.getLovePerMonth().get(i).getSum());
			
			Cell cellHahaValue = rowReactionsValues.createCell(3);
			cellHahaValue.setCellValue(reportFacebookHistorical.getHahaPerMonth().get(i).getSum());
			
			Cell cellWowValue = rowReactionsValues.createCell(4);
			cellWowValue.setCellValue(reportFacebookHistorical.getWowPerMonth().get(i).getSum());
			
			Cell cellSorryValue = rowReactionsValues.createCell(5);
			cellSorryValue.setCellValue(reportFacebookHistorical.getSorryPerMonth().get(i).getSum());
			
			Cell cellAngerValue = rowReactionsValues.createCell(6);
			cellAngerValue.setCellValue(reportFacebookHistorical.getAngerPerMonth().get(i).getSum());
			
			rowSheetHistoricalCount++;
		}
		
		rowSheetHistoricalCount++;
		Row rowTotalReactions = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowTotalReactions.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalReactionsTitle = rowTotalReactions.createCell(0);
		cellTotalReactionsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalreactionscolon"));
		cellTotalReactionsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalReactionsValue = rowTotalReactions.createCell(1);
		cellTotalReactionsValue.setCellValue(
				reportFacebookHistorical.getTotalLikes() + reportFacebookHistorical.getTotalAnger() + reportFacebookHistorical.getTotalHaha() +
				reportFacebookHistorical.getTotalLove() + reportFacebookHistorical.getTotalSorry() + reportFacebookHistorical.getTotalWow());
		cellTotalReactionsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowTotalLikes = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowTotalLikes.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalLikesTitle = rowTotalLikes.createCell(0);
		cellTotalLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totallikes"));
		cellTotalLikesTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalLikesValue = rowTotalLikes.createCell(1);
		cellTotalLikesValue.setCellValue(reportFacebookHistorical.getTotalLikes());
		cellTotalLikesValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowTotalLove = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowTotalLove.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalLoveTitle = rowTotalLove.createCell(0);
		cellTotalLoveTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totallove"));
		cellTotalLoveTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalLoveValue = rowTotalLove.createCell(1);
		cellTotalLoveValue.setCellValue(reportFacebookHistorical.getTotalLove());
		cellTotalLoveValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowTotalHaha = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowTotalHaha.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalHahaTitle = rowTotalHaha.createCell(0);
		cellTotalHahaTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalhaha"));
		cellTotalHahaTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalHahaValue = rowTotalHaha.createCell(1);
		cellTotalHahaValue.setCellValue(reportFacebookHistorical.getTotalHaha());
		cellTotalHahaValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowTotalWow = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowTotalWow.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalWowTitle = rowTotalWow.createCell(0);
		cellTotalWowTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalwow"));
		cellTotalWowTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalWowValue = rowTotalWow.createCell(1);
		cellTotalWowValue.setCellValue(reportFacebookHistorical.getTotalWow());
		cellTotalWowValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowTotalSorry = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowTotalSorry.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalSorryTitle = rowTotalSorry.createCell(0);
		cellTotalSorryTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalsad"));
		cellTotalSorryTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalSorryValue = rowTotalSorry.createCell(1);
		cellTotalSorryValue.setCellValue(reportFacebookHistorical.getTotalSorry());
		cellTotalSorryValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowTotalAnger = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowTotalAnger.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalAngerTitle = rowTotalAnger.createCell(0);
		cellTotalAngerTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalgrr"));
		cellTotalAngerTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalAngerValue = rowTotalAnger.createCell(1);
		cellTotalAngerValue.setCellValue(reportFacebookHistorical.getTotalAnger());
		cellTotalAngerValue.setCellStyle(cellStylePreAnalysisResult);
		
	}
	
	private void addComments() {
		
		rowSheetHistoricalCount += 2;
		
		Row rowCommentsTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowCommentsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCommentsTitle = rowCommentsTitle.createCell(0);
		cellCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.sharesupper"));
		cellCommentsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 6));
		
		rowSheetHistoricalCount++;
		Row rowCommentsPerDayTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowCommentsPerDayTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCommentsDateTitle = rowCommentsPerDayTitle.createCell(0);
		cellCommentsDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.monthyear"));
		cellCommentsDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCommentsPerDayTitle = rowCommentsPerDayTitle.createCell(1);
		cellCommentsPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.comments"));
		cellCommentsPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		for(SumByDate commentsMonth : reportFacebookHistorical.getCommentsPerMonth()) {
		
			Row rowCommentsValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellDateValue = rowCommentsValues.createCell(0);
				cellDateValue.setCellValue(commentsMonth.getMonth() + "/" + commentsMonth.getYear());
			
			Cell cellCommentsValue = rowCommentsValues.createCell(1);
			cellCommentsValue.setCellValue(commentsMonth.getSum());
			
			rowSheetHistoricalCount++;
		}
		
		rowSheetHistoricalCount++;
		Row rowTotalComments = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowTotalComments.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalCommentsTitle = rowTotalComments.createCell(0);
		cellTotalCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalcommentsperiod"));
		cellTotalCommentsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalCommentsValue = rowTotalComments.createCell(1);
		cellTotalCommentsValue.setCellValue(reportFacebookHistorical.getTotalComments());
		cellTotalCommentsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowMajorCommentsDay = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowMajorCommentsDay.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMajorCommentsDayTitle = rowMajorCommentsDay.createCell(0);
		cellMajorCommentsDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.historical.majorcommentsmonth"));
		cellMajorCommentsDayTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMajorCommentsDayValue = rowMajorCommentsDay.createCell(1);
		cellMajorCommentsDayValue.setCellValue(reportFacebookHistorical.getMajorCommentsMonth().getMonth() + "/" +
												reportFacebookHistorical.getMajorCommentsMonth().getYear());
		cellMajorCommentsDayValue.setCellStyle(cellStylePreAnalysisResult);
		
	}
	
	private void addShares() {
		
		rowSheetHistoricalCount += 2;
		
		Row rowSharesTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowSharesTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellSharesTitle = rowSharesTitle.createCell(0);
		cellSharesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.sharesupper"));
		cellSharesTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 6));
		
		rowSheetHistoricalCount++;
		Row rowSharesPerDayTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowSharesPerDayTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellSharesDateTitle = rowSharesPerDayTitle.createCell(0);
		cellSharesDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.monthyear"));
		cellSharesDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellSharesPerDayTitle = rowSharesPerDayTitle.createCell(1);
		cellSharesPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.shares"));
		cellSharesPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		for(SumByDate sharesMonth : reportFacebookHistorical.getSharesPerMonth()) {
		
			Row rowSharesValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellDateValue = rowSharesValues.createCell(0);
				cellDateValue.setCellValue(sharesMonth.getMonth() + "/" + sharesMonth.getYear());
			
			Cell cellSharesValue = rowSharesValues.createCell(1);
			cellSharesValue.setCellValue(sharesMonth.getSum());
			
			rowSheetHistoricalCount++;
		}
		
		rowSheetHistoricalCount++;
		Row rowTotalShares = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowTotalShares.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalSharesTitle = rowTotalShares.createCell(0);
		cellTotalSharesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.sharesonperiod"));
		cellTotalSharesTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalSharesValue = rowTotalShares.createCell(1);
		cellTotalSharesValue.setCellValue(reportFacebookHistorical.getTotalShares());
		cellTotalSharesValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowMajorSharesDay = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowMajorSharesDay.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMajorSharesDayTitle = rowMajorSharesDay.createCell(0);
		cellMajorSharesDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.historical.majorsharesmonth"));
		cellMajorSharesDayTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMajorSharesDayValue = rowMajorSharesDay.createCell(1);
		cellMajorSharesDayValue.setCellValue(reportFacebookHistorical.getMajorSharesMonth().getMonth() + "/" +
											reportFacebookHistorical.getMajorSharesMonth().getYear());
		cellMajorSharesDayValue.setCellStyle(cellStylePreAnalysisResult);	
	}
	
	private void addExternalReferences() {
		
		rowSheetHistoricalCount++;
		Row rowExternalReferencesTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowExternalReferencesTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellExternalReferencesTitle = rowExternalReferencesTitle.createCell(0);
		cellExternalReferencesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.externalreferencestitle"));
		cellExternalReferencesTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 6));
		
		rowSheetHistoricalCount++;
		Row rowExternalReferencesSubTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowExternalReferencesSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellReferenceTitle = rowExternalReferencesSubTitle.createCell(0);
		cellReferenceTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.referencessubtitle"));
		cellReferenceTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellReferenceTotalTitle = rowExternalReferencesSubTitle.createCell(1);
		cellReferenceTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.total"));
		cellReferenceTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		for(ReportFacebookLocation externalReference : reportFacebookHistorical.getExternalReferences()) {
			
			Row rowReferenceValues = sheetHistorical.createRow(rowSheetHistoricalCount);
			
			Cell cellReferenceValue = rowReferenceValues.createCell(0);
			cellReferenceValue.setCellValue(externalReference.getLocation());
			
			Cell cellReferenceTotalValue = rowReferenceValues.createCell(1);
			cellReferenceTotalValue.setCellValue(externalReference.getValue());
			
			rowSheetHistoricalCount++;
		}
	}
	
	private void addFansLikeSource() {
		
		rowSheetHistoricalCount += 2;
		Row rowFansLikeSourceTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowFansLikeSourceTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFansLikeSourceTitle = rowFansLikeSourceTitle.createCell(0);
		cellFansLikeSourceTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.fanslikesourcetitle"));
		cellFansLikeSourceTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 6));
		
		rowSheetHistoricalCount++;
		Row rowFansLikeSourceSubTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowFansLikeSourceSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFansLikeSourceSubTitle = rowFansLikeSourceSubTitle.createCell(0);
		cellFansLikeSourceSubTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.fanslikesourcesubtitle"));
		cellFansLikeSourceSubTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellFansLikeSourceTotalTitle = rowFansLikeSourceSubTitle.createCell(1);
		cellFansLikeSourceTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.total"));
		cellFansLikeSourceTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		for(ReportFacebookLocation reportFacebookLocation : reportFacebookHistorical.getFansLikeSource()) {
			
			Row rowFansLikeSourceValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellSourceValue = rowFansLikeSourceValues.createCell(0);
			cellSourceValue.setCellValue(OpSocialBackApplication.utilFunctionsService.getLikeSourceName(reportFacebookLocation.getLocation()));
			
			Cell cellSourceTotalValue = rowFansLikeSourceValues.createCell(1);
			cellSourceTotalValue.setCellValue(reportFacebookLocation.getValue());
			
			rowSheetHistoricalCount++;
		}
	}
	
	private void addInfluentFans() {
		
		rowSheetHistoricalCount++;
		Row rowInfluentFansTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowInfluentFansTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellInfluentFansTitle = rowInfluentFansTitle.createCell(0);
		cellInfluentFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.influentfanstitle"));
		cellInfluentFansTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 6));
		
		rowSheetHistoricalCount++;
		Row rowInfluentFansSubTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowInfluentFansSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellInfluentFansNameTitle = rowInfluentFansSubTitle.createCell(0);
		cellInfluentFansNameTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.name"));
		cellInfluentFansNameTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellInfluentFansProfileTitle = rowInfluentFansSubTitle.createCell(1);
		cellInfluentFansProfileTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.influentfansprofiletitle"));
		cellInfluentFansProfileTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellInfluentFansLikesTitle = rowInfluentFansSubTitle.createCell(2);
		cellInfluentFansLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.influentfanslikestitle"));
		cellInfluentFansLikesTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellInfluentFansCommentsTitle = rowInfluentFansSubTitle.createCell(3);
		cellInfluentFansCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.influentfanscommentstitle"));
		cellInfluentFansCommentsTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		for(FacebookInfluentialUserDTO facebookuser : reportFacebookHistorical.getMostInfluentialFans()) {
			
			Row rowInfluentFansValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellInfluentFansNameValue = rowInfluentFansValues.createCell(0);
			cellInfluentFansNameValue.setCellValue(facebookuser.getName());
			
			Hyperlink hyperLink = createHelper.createHyperlink(Hyperlink.LINK_URL);
    		hyperLink.setAddress(facebookuser.getProfileUrl());
			
			Cell cellInfluentFansProfileValue = rowInfluentFansValues.createCell(1);
			cellInfluentFansProfileValue.setCellValue(facebookuser.getProfileUrl());
			cellInfluentFansProfileValue.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hyperLink);
			cellInfluentFansProfileValue.setCellStyle(cellStyleHLink);
			
			Cell cellInfluentFansLikesValue = rowInfluentFansValues.createCell(2);
			cellInfluentFansLikesValue.setCellValue(facebookuser.getLikes());
			
			Cell cellInfluentFansCommentsValue = rowInfluentFansValues.createCell(3);
			cellInfluentFansCommentsValue.setCellValue(facebookuser.getComments());
			
			rowSheetHistoricalCount++;
		}
	}
	
	private void addCitiesStats() {
	
		rowSheetHistoricalCount += 2;
		
		Row rowCitiesMoreFansTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowCitiesMoreFansTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCitiesMoreFansTitle = rowCitiesMoreFansTitle.createCell(0);
		cellCitiesMoreFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.citiesmorefanstitle"));
		cellCitiesMoreFansTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 6));
		
		rowSheetHistoricalCount++;
		Row rowCitiesMoreFansSubTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowCitiesMoreFansSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCitiesMoreFansCityTitle = rowCitiesMoreFansSubTitle.createCell(0);
		cellCitiesMoreFansCityTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.city"));
		cellCitiesMoreFansCityTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCitiesMoreFansTotalTitle = rowCitiesMoreFansSubTitle.createCell(1);
		cellCitiesMoreFansTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.fans"));
		cellCitiesMoreFansTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		for(ReportFacebookLocation reportFacebookLocation : reportFacebookHistorical.getCitiesMoreFans()) {
			
			Row rowCitiesMoreFansValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellCitiesMoreFansCityValue = rowCitiesMoreFansValues.createCell(0);
			cellCitiesMoreFansCityValue.setCellValue(reportFacebookLocation.getLocation());
			
			Cell cellCitiesMoreFansTotalValue = rowCitiesMoreFansValues.createCell(1);
			cellCitiesMoreFansTotalValue.setCellValue(reportFacebookLocation.getValue());
			
			rowSheetHistoricalCount++;
		}
		
		rowSheetHistoricalCount += 2;
		Row rowCitiesReachedTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowCitiesReachedTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCitiesReachedTitle = rowCitiesReachedTitle.createCell(0);
		cellCitiesReachedTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.citiesreachedtitle"));
		cellCitiesReachedTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 6));
		
		rowSheetHistoricalCount++;
		Row rowCitiesReachedSubTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowCitiesReachedSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCitiesReachedCityTitle = rowCitiesReachedSubTitle.createCell(0);
		cellCitiesReachedCityTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.city"));
		cellCitiesReachedCityTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCitiesReachedTotalTitle = rowCitiesReachedSubTitle.createCell(1);
		cellCitiesReachedTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.users"));
		cellCitiesReachedTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		for(ReportFacebookLocation reportFacebookLocation : reportFacebookHistorical.getCitiesMoreReached()) {
			
			Row rowCitiesReachedValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellCitiesReachedCityValue = rowCitiesReachedValues.createCell(0);
			cellCitiesReachedCityValue.setCellValue(reportFacebookLocation.getLocation());
			
			Cell cellCitiesReachedTotalValue = rowCitiesReachedValues.createCell(1);
			cellCitiesReachedTotalValue.setCellValue(reportFacebookLocation.getValue());
			
			rowSheetHistoricalCount++;
		}
	}
	
	private void addAboutReachedUsers() {
		
		rowSheetHistoricalCount += 2;
		
		Row rowAboutReachedUsersTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowAboutReachedUsersTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAboutReachedUsersTitle = rowAboutReachedUsersTitle.createCell(0);
		cellAboutReachedUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.reachedusersstatstitle"));
		cellAboutReachedUsersTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 6));
		
		rowSheetHistoricalCount++;
		Row rowAboutReachedUsersSubTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowAboutReachedUsersSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAgeGroupTitle = rowAboutReachedUsersSubTitle.createCell(0);
		cellAgeGroupTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.agegroup"));
		cellAgeGroupTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellAgeGroupMaleTitle = rowAboutReachedUsersSubTitle.createCell(1);
		cellAgeGroupMaleTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.male"));
		cellAgeGroupMaleTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellAgeGroupFemaleTitle = rowAboutReachedUsersSubTitle.createCell(2);
		cellAgeGroupFemaleTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.female"));
		cellAgeGroupFemaleTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		Row rowAgeThirteenSeventeenValues = sheetHistorical.createRow(rowSheetHistoricalCount);

		Cell cellAgeThirteenSeventeenValue = rowAgeThirteenSeventeenValues.createCell(0);
		cellAgeThirteenSeventeenValue.setCellValue("13-17");
		
		Cell cellAgeThirteenSeventeenMale = rowAgeThirteenSeventeenValues.createCell(1);
		cellAgeThirteenSeventeenMale.setCellValue(reportFacebookHistorical.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_13_AMONG_17));
		
		Cell cellAgeThirteenSeventeenFemale = rowAgeThirteenSeventeenValues.createCell(2);
		cellAgeThirteenSeventeenFemale.setCellValue(reportFacebookHistorical.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_13_AMONG_17));
		
		rowSheetHistoricalCount++;
		Row rowAgeEighteenTwentyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);

		Cell cellAgeEighteenTwentyFourValue = rowAgeEighteenTwentyFourValues.createCell(0);
		cellAgeEighteenTwentyFourValue.setCellValue("18-24");
		
		Cell cellAgeEighteenTwentyFourMale = rowAgeEighteenTwentyFourValues.createCell(1);
		cellAgeEighteenTwentyFourMale.setCellValue(reportFacebookHistorical.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_18_AMONG_24));
		
		Cell cellAgeEighteenTwentyFourFemale = rowAgeEighteenTwentyFourValues.createCell(2);
		cellAgeEighteenTwentyFourFemale.setCellValue(reportFacebookHistorical.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_18_AMONG_24));
		
		rowSheetHistoricalCount++;
		Row rowAgeTwentyFiveThirtyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);

		Cell cellAgeTwentyFiveThirtyFourValue = rowAgeTwentyFiveThirtyFourValues.createCell(0);
		cellAgeTwentyFiveThirtyFourValue.setCellValue("25-34");
		
		Cell cellAgeTwentyFiveThirtyFourMale = rowAgeTwentyFiveThirtyFourValues.createCell(1);
		cellAgeTwentyFiveThirtyFourMale.setCellValue(reportFacebookHistorical.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_25_AMONG_34));
		
		Cell cellAgeTwentyFiveThirtyFourFemale = rowAgeTwentyFiveThirtyFourValues.createCell(2);
		cellAgeTwentyFiveThirtyFourFemale.setCellValue(reportFacebookHistorical.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_25_AMONG_34));
		
		rowSheetHistoricalCount++;
		Row rowAgeThirtyFiveFortyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);

		Cell cellAgeThirtyFiveFortyFourValue = rowAgeThirtyFiveFortyFourValues.createCell(0);
		cellAgeThirtyFiveFortyFourValue.setCellValue("35-44");
		
		Cell cellAgeThirtyFiveFortyFourMale = rowAgeThirtyFiveFortyFourValues.createCell(1);
		cellAgeThirtyFiveFortyFourMale.setCellValue(reportFacebookHistorical.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_35_AMONG_44));
		
		Cell cellAgeThirtyFiveFortyFourFemale = rowAgeThirtyFiveFortyFourValues.createCell(2);
		cellAgeThirtyFiveFortyFourFemale.setCellValue(reportFacebookHistorical.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_35_AMONG_44));
		
		rowSheetHistoricalCount++;
		Row rowAgeFortyFiveFiftyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);

		Cell cellAgeFortyFiveFiftyFourValue = rowAgeFortyFiveFiftyFourValues.createCell(0);
		cellAgeFortyFiveFiftyFourValue.setCellValue("45-54");
		
		Cell cellAgeFortyFiveFiftyFourMale = rowAgeFortyFiveFiftyFourValues.createCell(1);
		cellAgeFortyFiveFiftyFourMale.setCellValue(reportFacebookHistorical.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_45_AMONG_54));
		
		Cell cellAgeFortyFiveFiftyFourFemale = rowAgeFortyFiveFiftyFourValues.createCell(2);
		cellAgeFortyFiveFiftyFourFemale.setCellValue(reportFacebookHistorical.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_45_AMONG_54));
		
		rowSheetHistoricalCount++;
		Row rowAgeFiftyFiveSixtyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);

		Cell cellAgeFiftyFiveSixtyFourValue = rowAgeFiftyFiveSixtyFourValues.createCell(0);
		cellAgeFiftyFiveSixtyFourValue.setCellValue("55-64");
		
		Cell cellAgeFiftyFiveSixtyFourMale = rowAgeFiftyFiveSixtyFourValues.createCell(1);
		cellAgeFiftyFiveSixtyFourMale.setCellValue(reportFacebookHistorical.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_55_AMONG_64));
		
		Cell cellAgeFiftyFiveSixtyFourFemale = rowAgeFiftyFiveSixtyFourValues.createCell(2);
		cellAgeFiftyFiveSixtyFourFemale.setCellValue(reportFacebookHistorical.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_55_AMONG_64));
		
		rowSheetHistoricalCount++;
		Row rowAgeSixtyFivePlusValues = sheetHistorical.createRow(rowSheetHistoricalCount);

		Cell cellAgeSixtyFivePlusValue = rowAgeSixtyFivePlusValues.createCell(0);
		cellAgeSixtyFivePlusValue.setCellValue("65+");
		
		Cell cellAgeSixtyFivePlusMale = rowAgeSixtyFivePlusValues.createCell(1);
		cellAgeSixtyFivePlusMale.setCellValue(reportFacebookHistorical.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_65_PLUS));
		
		Cell cellAgeSixtyFivePlusFemale = rowAgeSixtyFivePlusValues.createCell(2);
		cellAgeSixtyFivePlusFemale.setCellValue(reportFacebookHistorical.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_65_PLUS));
		
		rowSheetHistoricalCount += 2;
		Row rowMajorAgeGroupFans = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowMajorAgeGroupFans.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMajorAgeGroupFansTitle = rowMajorAgeGroupFans.createCell(0);
		cellMajorAgeGroupFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.majoragegroupuserstitle"));
		cellMajorAgeGroupFansTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMajorAgeGroupFansValue = rowMajorAgeGroupFans.createCell(1);
		cellMajorAgeGroupFansValue.setCellValue(reportFacebookHistorical.getMajorAgeGroupReachedUsers().getInitialAgeGroup() + "-" + 
				reportFacebookHistorical.getMajorAgeGroupReachedUsers().getEndAgeGroup() + " / " + 
				reportFacebookHistorical.getMajorAgeGroupReachedUsers().getPercentage() + "%");
		cellMajorAgeGroupFansValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowMenFans = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowMenFans.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMenFansTitle = rowMenFans.createCell(0);
		cellMenFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.malecolon"));
		cellMenFansTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMenFansValue = rowMenFans.createCell(1);
		cellMenFansValue.setCellValue(reportFacebookHistorical.getMenReachedUsersPerc() + "%");
		cellMenFansValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowWomenFans = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowWomenFans.setHeightInPoints(headerDataRowHeight);
		
		Cell cellWomenFansTitle = rowWomenFans.createCell(0);
		cellWomenFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.femalecolon"));
		cellWomenFansTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellWomenFansValue = rowWomenFans.createCell(1);
		cellWomenFansValue.setCellValue(reportFacebookHistorical.getWomenReachedUsersPerc() + "%");
		cellWomenFansValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addAboutYourFans() {
		
		rowSheetHistoricalCount += 2;
		
		Row rowAboutYourFansTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowAboutYourFansTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAboutYourFansTitle = rowAboutYourFansTitle.createCell(0);
		cellAboutYourFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.statstitle"));
		cellAboutYourFansTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 6));
		
		rowSheetHistoricalCount++;
		Row rowAboutYourFansSubTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowAboutYourFansSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAgeGroupTitle = rowAboutYourFansSubTitle.createCell(0);
		cellAgeGroupTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.agegroup"));
		cellAgeGroupTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellAgeGroupMaleTitle = rowAboutYourFansSubTitle.createCell(1);
		cellAgeGroupMaleTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.male"));
		cellAgeGroupMaleTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellAgeGroupFemaleTitle = rowAboutYourFansSubTitle.createCell(2);
		cellAgeGroupFemaleTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.female"));
		cellAgeGroupFemaleTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		Row rowAgeThirteenSeventeenValues = sheetHistorical.createRow(rowSheetHistoricalCount);

		Cell cellAgeThirteenSeventeenValue = rowAgeThirteenSeventeenValues.createCell(0);
		cellAgeThirteenSeventeenValue.setCellValue("13-17");
		
		Cell cellAgeThirteenSeventeenMale = rowAgeThirteenSeventeenValues.createCell(1);
		cellAgeThirteenSeventeenMale.setCellValue(reportFacebookHistorical.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_MALE_13_AMONG_17));
		
		Cell cellAgeThirteenSeventeenFemale = rowAgeThirteenSeventeenValues.createCell(2);
		cellAgeThirteenSeventeenFemale.setCellValue(reportFacebookHistorical.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_13_AMONG_17));
		
		rowSheetHistoricalCount++;
		Row rowAgeEighteenTwentyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);

		Cell cellAgeEighteenTwentyFourValue = rowAgeEighteenTwentyFourValues.createCell(0);
		cellAgeEighteenTwentyFourValue.setCellValue("18-24");
		
		Cell cellAgeEighteenTwentyFourMale = rowAgeEighteenTwentyFourValues.createCell(1);
		cellAgeEighteenTwentyFourMale.setCellValue(reportFacebookHistorical.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_MALE_18_AMONG_24));
		
		Cell cellAgeEighteenTwentyFourFemale = rowAgeEighteenTwentyFourValues.createCell(2);
		cellAgeEighteenTwentyFourFemale.setCellValue(reportFacebookHistorical.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_18_AMONG_24));
		
		rowSheetHistoricalCount++;
		Row rowAgeTwentyFiveThirtyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);

		Cell cellAgeTwentyFiveThirtyFourValue = rowAgeTwentyFiveThirtyFourValues.createCell(0);
		cellAgeTwentyFiveThirtyFourValue.setCellValue("25-34");
		
		Cell cellAgeTwentyFiveThirtyFourMale = rowAgeTwentyFiveThirtyFourValues.createCell(1);
		cellAgeTwentyFiveThirtyFourMale.setCellValue(reportFacebookHistorical.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_MALE_25_AMONG_34));
		
		Cell cellAgeTwentyFiveThirtyFourFemale = rowAgeTwentyFiveThirtyFourValues.createCell(2);
		cellAgeTwentyFiveThirtyFourFemale.setCellValue(reportFacebookHistorical.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_25_AMONG_34));
		
		rowSheetHistoricalCount++;
		Row rowAgeThirtyFiveFortyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);

		Cell cellAgeThirtyFiveFortyFourValue = rowAgeThirtyFiveFortyFourValues.createCell(0);
		cellAgeThirtyFiveFortyFourValue.setCellValue("35-44");
		
		Cell cellAgeThirtyFiveFortyFourMale = rowAgeThirtyFiveFortyFourValues.createCell(1);
		cellAgeThirtyFiveFortyFourMale.setCellValue(reportFacebookHistorical.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_MALE_35_AMONG_44));
		
		Cell cellAgeThirtyFiveFortyFourFemale = rowAgeThirtyFiveFortyFourValues.createCell(2);
		cellAgeThirtyFiveFortyFourFemale.setCellValue(reportFacebookHistorical.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_35_AMONG_44));
		
		rowSheetHistoricalCount++;
		Row rowAgeFortyFiveFiftyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);

		Cell cellAgeFortyFiveFiftyFourValue = rowAgeFortyFiveFiftyFourValues.createCell(0);
		cellAgeFortyFiveFiftyFourValue.setCellValue("45-54");
		
		Cell cellAgeFortyFiveFiftyFourMale = rowAgeFortyFiveFiftyFourValues.createCell(1);
		cellAgeFortyFiveFiftyFourMale.setCellValue(reportFacebookHistorical.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_MALE_45_AMONG_54));
		
		Cell cellAgeFortyFiveFiftyFourFemale = rowAgeFortyFiveFiftyFourValues.createCell(2);
		cellAgeFortyFiveFiftyFourFemale.setCellValue(reportFacebookHistorical.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_45_AMONG_54));
		
		rowSheetHistoricalCount++;
		Row rowAgeFiftyFiveSixtyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);

		Cell cellAgeFiftyFiveSixtyFourValue = rowAgeFiftyFiveSixtyFourValues.createCell(0);
		cellAgeFiftyFiveSixtyFourValue.setCellValue("55-64");
		
		Cell cellAgeFiftyFiveSixtyFourMale = rowAgeFiftyFiveSixtyFourValues.createCell(1);
		cellAgeFiftyFiveSixtyFourMale.setCellValue(reportFacebookHistorical.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_MALE_55_AMONG_64));
		
		Cell cellAgeFiftyFiveSixtyFourFemale = rowAgeFiftyFiveSixtyFourValues.createCell(2);
		cellAgeFiftyFiveSixtyFourFemale.setCellValue(reportFacebookHistorical.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_55_AMONG_64));
		
		rowSheetHistoricalCount++;
		Row rowAgeSixtyFivePlusValues = sheetHistorical.createRow(rowSheetHistoricalCount);

		Cell cellAgeSixtyFivePlusValue = rowAgeSixtyFivePlusValues.createCell(0);
		cellAgeSixtyFivePlusValue.setCellValue("65+");
		
		Cell cellAgeSixtyFivePlusMale = rowAgeSixtyFivePlusValues.createCell(1);
		cellAgeSixtyFivePlusMale.setCellValue(reportFacebookHistorical.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_MALE_65_PLUS));
		
		Cell cellAgeSixtyFivePlusFemale = rowAgeSixtyFivePlusValues.createCell(2);
		cellAgeSixtyFivePlusFemale.setCellValue(reportFacebookHistorical.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_65_PLUS));
		
		rowSheetHistoricalCount += 2;
		Row rowMajorAgeGroupFans = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowMajorAgeGroupFans.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMajorAgeGroupFansTitle = rowMajorAgeGroupFans.createCell(0);
		cellMajorAgeGroupFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.majoragegroupuserstitle"));
		cellMajorAgeGroupFansTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMajorAgeGroupFansValue = rowMajorAgeGroupFans.createCell(1);
		cellMajorAgeGroupFansValue.setCellValue(reportFacebookHistorical.getMajorAgeGroupFans().getInitialAgeGroup() + "-" + 
				reportFacebookHistorical.getMajorAgeGroupFans().getEndAgeGroup() + " / " + 
				reportFacebookHistorical.getMajorAgeGroupFans().getPercentage() + "%");
		cellMajorAgeGroupFansValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowMenFans = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowMenFans.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMenFansTitle = rowMenFans.createCell(0);
		cellMenFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.malecolon"));
		cellMenFansTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMenFansValue = rowMenFans.createCell(1);
		cellMenFansValue.setCellValue(reportFacebookHistorical.getMenFansPerc() + "%");
		cellMenFansValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowWomenFans = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowWomenFans.setHeightInPoints(headerDataRowHeight);
		
		Cell cellWomenFansTitle = rowWomenFans.createCell(0);
		cellWomenFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.femalecolon"));
		cellWomenFansTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellWomenFansValue = rowWomenFans.createCell(1);
		cellWomenFansValue.setCellValue(reportFacebookHistorical.getWomenFansPerc() + "%");
		cellWomenFansValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addFanPageGrowth() {
		
		rowSheetHistoricalCount = 2;
		
		Row rowFanPageGrowthTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowFanPageGrowthTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFanPageGrowthTitle = rowFanPageGrowthTitle.createCell(0);
		cellFanPageGrowthTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.fanpagegrowthtitle"));
		cellFanPageGrowthTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 6));
		
		rowSheetHistoricalCount++;
		Row rowFanPageGrowthSubTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowFanPageGrowthSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFanPageGrowthDateTitle = rowFanPageGrowthSubTitle.createCell(0);
		cellFanPageGrowthDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.monthyear"));
		cellFanPageGrowthDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellFanPageGrowthTotalTitle = rowFanPageGrowthSubTitle.createCell(1);
		cellFanPageGrowthTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.totalfans"));
		cellFanPageGrowthTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Long accumulatedFanPageGrowth = reportFacebookHistorical.getFansUntilOneYearAgo();
		rowSheetHistoricalCount++;
		for (br.com.opsocial.ejb.entity.report.FanPageGrowth fanPageGrowth : reportFacebookHistorical.getFanPageGrowth()) {

			accumulatedFanPageGrowth = accumulatedFanPageGrowth + fanPageGrowth.getSum();
			
			Row rowFanPageGrowthValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellFanPageGrowthDateValue = rowFanPageGrowthValues.createCell(0);
			cellFanPageGrowthDateValue.setCellValue(fanPageGrowth.getMonth() + "/" + fanPageGrowth.getYear());
			
			Cell cellFanPageGrowthTotalValue = rowFanPageGrowthValues.createCell(1);
			cellFanPageGrowthTotalValue.setCellValue(accumulatedFanPageGrowth);
			
			rowSheetHistoricalCount++;
		}
		
		rowSheetHistoricalCount++;
		Row rowMajorGrowth = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowMajorGrowth.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMajorGrowthTitle = rowMajorGrowth.createCell(0);
		cellMajorGrowthTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.historical.majorgrowth"));
		cellMajorGrowthTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMajorGrowthValue = rowMajorGrowth.createCell(1);
		cellMajorGrowthValue.setCellValue(reportFacebookHistorical.getMajorFanPageGrowth().getMonth() + "/" + 
				reportFacebookHistorical.getMajorFanPageGrowth().getYear());
		cellMajorGrowthValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowMinorGrowth = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowMinorGrowth.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMinorGrowthTitle = rowMinorGrowth.createCell(0);
		cellMinorGrowthTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.historical.minorgrowth"));
		cellMinorGrowthTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMinorGrowthValue = rowMinorGrowth.createCell(1);
		cellMinorGrowthValue.setCellValue(reportFacebookHistorical.getMinorFanPageGrowth().getMonth() + "/" + 
				reportFacebookHistorical.getMinorFanPageGrowth().getYear());
		cellMinorGrowthValue.setCellStyle(cellStylePreAnalysisResult);
	}

	private void addMainHeader(Sheet sheetDataPosts, String text, int untilColumn) {

		Row row = sheetDataPosts.createRow(0);
		row.setHeightInPoints((2 * sheetDataPosts.getDefaultRowHeightInPoints()));

		Cell cellURL = row.createCell(0);
		cellURL.setCellValue(text);
		cellURL.setCellStyle(cellStyleHeader);

		sheetDataPosts.addMergedRegion(new CellRangeAddress(0, 0, 0, untilColumn));
	}

	private void initiateDefautStyles() {

		fontHeader = wb.createFont();
		fontHeader.setFontHeightInPoints((short) 12);
		fontHeader.setBold(true);
		fontHeader.setColor(IndexedColors.WHITE.index);

		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(IndexedColors.SKY_BLUE.index, (byte) 102, (byte) 153, (byte) 204);
		palette.setColorAtIndex(IndexedColors.YELLOW.index, (byte) 255, (byte) 211, (byte) 32);
		palette.setColorAtIndex(IndexedColors.GREEN.index, (byte) 174, (byte) 207, (byte) 0);

		cellStyleHeader = wb.createCellStyle();
		cellStyleHeader.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
		cellStyleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyleHeader.setFont(fontHeader);
		cellStyleHeader.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleHeader.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleHeader.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		fontHeaderData = wb.createFont();
		fontHeaderData.setFontHeightInPoints((short) 11);
		fontHeaderData.setBold(true);

		cellStyleHeaderData = wb.createCellStyle();
		cellStyleHeaderData.setFillForegroundColor(IndexedColors.YELLOW.index);
		cellStyleHeaderData.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyleHeaderData.setFont(fontHeaderData);

		cellStyleHeaderDataPositive = wb.createCellStyle();
		cellStyleHeaderDataPositive.setFillForegroundColor(IndexedColors.GREEN.index);
		cellStyleHeaderDataPositive.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyleHeaderDataPositive.setFont(fontHeaderData);

		cellStyleHeaderDataNegative = wb.createCellStyle();
		cellStyleHeaderDataNegative.setFillForegroundColor(IndexedColors.RED.index);
		cellStyleHeaderDataNegative.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyleHeaderDataNegative.setFont(fontHeaderData);

		cellSecondaryHeaderData = wb.createCellStyle();
		cellSecondaryHeaderData.setFont(fontHeaderData);

		cellSecondaryHeaderNumber = wb.createCellStyle();
		cellSecondaryHeaderNumber.setFont(fontHeaderData);
		cellSecondaryHeaderNumber.setAlignment(CellStyle.ALIGN_RIGHT);

		cellSecondaryHeaderCenter = wb.createCellStyle();
		cellSecondaryHeaderCenter.setFont(fontHeaderData);
		cellSecondaryHeaderCenter.setAlignment(CellStyle.ALIGN_CENTER);

		cellStylePreAnalysis = wb.createCellStyle();
		cellStylePreAnalysis.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		cellStylePreAnalysis.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStylePreAnalysis.setFont(fontHeaderData);

		cellStylePreAnalysisResult = wb.createCellStyle();
		cellStylePreAnalysisResult.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		cellStylePreAnalysisResult.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStylePreAnalysisResult.setAlignment(CellStyle.ALIGN_RIGHT);

		createHelper = wb.getCreationHelper();

		cellDateStyle = wb.createCellStyle();
		cellDateStyle.setDataFormat(
				createHelper.createDataFormat().getFormat("MM/dd/yyyy hh:mm"));

		//by default hyperlinks are blue and underlined
		cellStyleHLink = wb.createCellStyle();

		Font fontHLink = wb.createFont();
		fontHLink.setUnderline(Font.U_SINGLE);
		fontHLink.setColor(IndexedColors.BLUE.getIndex());

		cellStyleHLink.setFont(fontHLink);

		cellDataPercentage = wb.createCellStyle();
		cellDataPercentage.setAlignment(CellStyle.ALIGN_RIGHT);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFormattedFileName() {
		return formattedFileName;
	}

	public void setFormattedFileName(String formattedFileName) {
		this.formattedFileName = formattedFileName;
	}

}
