package br.com.opsocial.server.utils.reports.xls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
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
import br.com.opsocial.client.entity.report.facebook.ReportFacebookContentDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.report.FanPageGrowth;
import br.com.opsocial.ejb.entity.report.SumByDate;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.aws.S3Integration;

public class ReportFacebookContentXLS {

	private ReportFacebookContentDTO reportFacebookContent;
	private Profile profile;
	
	private String reportsPath;
	private String fileName;
	private String formattedFileName;
	
	private HSSFWorkbook wb;
	private Sheet sheetContent;
	
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
	
	private String startDate;
	private String endDate;
	
	private int rowSheetContentCount;
	
	public ReportFacebookContentXLS(ReportFacebookContentDTO reportFacebookContent, Profile profile) {
		this.reportFacebookContent = reportFacebookContent;
		this.profile = profile;
	}
	
	public void createXLS() {
			
		wb = new HSSFWorkbook();
		
		initiateDefautStyles();

		this.reportsPath = OpSocialBackApplication.GET_DIR_REPORTS_XLS;
		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".xls");

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat dateFormatToFileName = new SimpleDateFormat("MM-dd-yyyy");
		
		startDate = dateFormat.format(new Date(reportFacebookContent.getDateFrom() * 1000L));
		endDate = dateFormat.format(new Date(reportFacebookContent.getDateUntil() * 1000L));
		
		sheetContent = wb.createSheet(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.tabname"));
		
		headerDataRowHeight = 1.2F * sheetContent.getDefaultRowHeightInPoints();
		
		addMainHeader(sheetContent, OpSocialBackApplication.messageByLocaleService.
				getMessage("xls.facebook.report.content.header", new Object[]{profile.getScreenName(), startDate, endDate}), 5);
		
		addGeneralPostsStats();
		addReactions();
		addComments();
		addShares();
		addReachOfPosts();
		addImpressionsOfPosts();
		addConsumptionOfPosts();
		addPageNegativeFeedbacksType();
		addVideoViews();
		addVideoViewsByDay();
		addVideoViewTimeByAgeGender();
		
		sheetContent.setDefaultColumnWidth(25);
		sheetContent.setColumnWidth(0, 8000);
		
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
				getMessage("xls.facebook.report.content.doctitle", new Object[]{
						profile.getScreenName(), 
						dateFormatToFileName.format(new Date(reportFacebookContent.getDateFrom() * 1000L)), 
						dateFormatToFileName.format(new Date(reportFacebookContent.getDateUntil() * 1000L))
			});
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addVideoViewTimeByAgeGender() {
		
		rowSheetContentCount += 2;
		Row rowStatsTitle = sheetContent.createRow(rowSheetContentCount);
		rowStatsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellStatsTitle = rowStatsTitle.createCell(0);
		cellStatsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.statstitle"));
		cellStatsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetContent.addMergedRegion(new CellRangeAddress(rowSheetContentCount, rowSheetContentCount, 0, 6));
		
		rowSheetContentCount++;
		Row rowAgeGroupUsersTitle = sheetContent.createRow(rowSheetContentCount);
		rowAgeGroupUsersTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAgeGroupUsersTitle = rowAgeGroupUsersTitle.createCell(0);
		cellAgeGroupUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.agerange"));
		cellAgeGroupUsersTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellMaleUsersTitle = rowAgeGroupUsersTitle.createCell(1);
		cellMaleUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.male"));
		cellMaleUsersTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellFemaleUsersTitle = rowAgeGroupUsersTitle.createCell(2);
		cellFemaleUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.female"));
		cellFemaleUsersTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellUnidentifiedUsersTitle = rowAgeGroupUsersTitle.createCell(3);
		cellUnidentifiedUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.unindentified"));
		cellUnidentifiedUsersTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		for(int i = 0; i < reportFacebookContent.getVideoViewTimeByFemaleUsers().size(); i++) {
		
			rowSheetContentCount++;
			Row rowValues = sheetContent.createRow(rowSheetContentCount);
			
			Cell cellAgeGroup = rowValues.createCell(0);
			cellAgeGroup.setCellValue(UtilFunctions.formatAgeGroupDisplay(reportFacebookContent.getVideoViewTimeByFemaleUsers().get(i).getInitialAgeGroup(), 
					reportFacebookContent.getVideoViewTimeByFemaleUsers().get(i).getEndAgeGroup()));
			
			Cell cellMaleUsers = rowValues.createCell(1);
			cellMaleUsers.setCellValue(UtilFunctions.fromMilisecondsToMinutes(reportFacebookContent.getVideoViewTimeByMaleUsers().get(i).getValue()));
			
			Cell cellFemaleUsers = rowValues.createCell(2);
			cellFemaleUsers.setCellValue(UtilFunctions.fromMilisecondsToMinutes(reportFacebookContent.getVideoViewTimeByFemaleUsers().get(i).getValue()));
			
			Cell cellUnidentifiedUsers = rowValues.createCell(3);
			cellUnidentifiedUsers.setCellValue(UtilFunctions.fromMilisecondsToMinutes(reportFacebookContent.getVideoViewTimeByUnidentifiedUsers().get(i).getValue()));
		}
		
		rowSheetContentCount += 2;
		Row rowMajorAgeGroupUsers = sheetContent.createRow(rowSheetContentCount);
		rowMajorAgeGroupUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMajorAgeGroupUsersTitle = rowMajorAgeGroupUsers.createCell(0);
		cellMajorAgeGroupUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.majoragegroupuserstitle"));
		cellMajorAgeGroupUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMajorAgeGroupUsersValue = rowMajorAgeGroupUsers.createCell(1);
		
		String majorAgeGroupUsersValue;
		
		if(reportFacebookContent.getMajorAgeGroupVideoViewTimeUsers().getInitialAgeGroup() != null) {
			majorAgeGroupUsersValue = UtilFunctions.formatAgeGroupDisplay(reportFacebookContent.getMajorAgeGroupVideoViewTimeUsers().getInitialAgeGroup(), 
					reportFacebookContent.getMajorAgeGroupVideoViewTimeUsers().getEndAgeGroup()) + " / " + 
					reportFacebookContent.getMajorAgeGroupVideoViewTimeUsers().getPercentage() + "%";
		} else {
			majorAgeGroupUsersValue = OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.novalue");
		}
		
		cellMajorAgeGroupUsersValue.setCellValue(majorAgeGroupUsersValue);
		cellMajorAgeGroupUsersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowMenUsers = sheetContent.createRow(rowSheetContentCount);
		rowMenUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMenUsersTitle = rowMenUsers.createCell(0);
		cellMenUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.malecolon"));
		cellMenUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMenUsersValue = rowMenUsers.createCell(1);
		cellMenUsersValue.setCellValue(reportFacebookContent.getMaleVideoViewTimeUsersPerc() + "%");
		cellMenUsersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowWomenUsers = sheetContent.createRow(rowSheetContentCount);
		rowWomenUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellWomenUsersTitle = rowWomenUsers.createCell(0);
		cellWomenUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.femalecolon"));
		cellWomenUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellWomenUsersValue = rowWomenUsers.createCell(1);
		cellWomenUsersValue.setCellValue(reportFacebookContent.getFemaleVideoViewTimeUsersPerc() + "%");
		cellWomenUsersValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addVideoViewsByDay() {
		
		rowSheetContentCount += 2;
		
		Row rowVideoViewsByDayTitle = sheetContent.createRow(rowSheetContentCount);
		rowVideoViewsByDayTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellVideoViewsByDayTitle = rowVideoViewsByDayTitle.createCell(0);
		cellVideoViewsByDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.videoviewsbydaytitle"));
		cellVideoViewsByDayTitle.setCellStyle(cellStyleHeaderData);
		
		sheetContent.addMergedRegion(new CellRangeAddress(rowSheetContentCount, rowSheetContentCount, 0, 6));
		
		rowSheetContentCount++;
		Row rowVideoViewsTitle = sheetContent.createRow(rowSheetContentCount);
		rowVideoViewsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellVideoViewsDateTitle = rowVideoViewsTitle.createCell(0);
		cellVideoViewsDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.date"));
		cellVideoViewsDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellVideoViewsTotalTitle = rowVideoViewsTitle.createCell(1);
		cellVideoViewsTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.videoviewstotaltitle"));
		cellVideoViewsTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellOrganicVideoViewsTitle = rowVideoViewsTitle.createCell(2);
		cellOrganicVideoViewsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.organic"));
		cellOrganicVideoViewsTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellPaidVideoViewsTitle = rowVideoViewsTitle.createCell(3);
		cellPaidVideoViewsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.paid"));
		cellPaidVideoViewsTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetContentCount++;
		for(int i = 0; i < reportFacebookContent.getVideoTotalOrganicViewsPerDay().size(); i++) {
		
			Row rowVideoViewsValues = sheetContent.createRow(rowSheetContentCount);

			Cell cellDateValue = rowVideoViewsValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(reportFacebookContent.getVideoTotalOrganicViewsPerDay().get(i).getDate() * 1000L)));
			
			Long totalViews = reportFacebookContent.getVideoTotalOrganicViewsPerDay().get(i).getSum() + 
					reportFacebookContent.getVideoTotalPaidViewsPerDay().get(i).getSum();
			
			Cell cellVideoViewsTotalValue = rowVideoViewsValues.createCell(1);
			cellVideoViewsTotalValue.setCellValue(totalViews);
			
			Cell cellOrganicVideoViewsValue = rowVideoViewsValues.createCell(2);
			cellOrganicVideoViewsValue.setCellValue(reportFacebookContent.getVideoTotalOrganicViewsPerDay().get(i).getSum());
			
			Cell cellPaidVideoViewsValue = rowVideoViewsValues.createCell(3);
			cellPaidVideoViewsValue.setCellValue(reportFacebookContent.getVideoTotalPaidViewsPerDay().get(i).getSum());
			
			rowSheetContentCount++;
		}
		
		rowSheetContentCount++;
		Row rowTotalViews = sheetContent.createRow(rowSheetContentCount);
		rowTotalViews.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalViewsTitle = rowTotalViews.createCell(0);
		cellTotalViewsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalviewstitle"));
		cellTotalViewsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalViewsValue = rowTotalViews.createCell(1);
		cellTotalViewsValue.setCellValue(reportFacebookContent.getVideoTotalViews());
		cellTotalViewsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowMajorTotalViewsDay = sheetContent.createRow(rowSheetContentCount);
		rowMajorTotalViewsDay.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMajorTotalViewsDayTitle = rowMajorTotalViewsDay.createCell(0);
		cellMajorTotalViewsDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.majortotalviewsdaytitle"));
		cellMajorTotalViewsDayTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMajorTotalViewsDayValue = rowMajorTotalViewsDay.createCell(1);
		cellMajorTotalViewsDayValue.setCellValue(new SimpleDateFormat("MM/dd").format(new Date(reportFacebookContent.getMajorTotalViewsDay().getDate() * 1000L)) +
				" " + OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.had") 
				+ " " + reportFacebookContent.getMajorTotalViewsDay().getSum() 
				+ " " + OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.views"));
		cellMajorTotalViewsDayValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowVideoAutoPlayedViews = sheetContent.createRow(rowSheetContentCount);
		rowVideoAutoPlayedViews.setHeightInPoints(headerDataRowHeight);
		
		Cell cellVideoAutoPlayedViews = rowVideoAutoPlayedViews.createCell(0);
		cellVideoAutoPlayedViews.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.autoplay"));
		cellVideoAutoPlayedViews.setCellStyle(cellStylePreAnalysis);
		
		Cell cellVideoAutoPlayedViewsValue = rowVideoAutoPlayedViews.createCell(1);
		cellVideoAutoPlayedViewsValue.setCellValue(reportFacebookContent.getVideoAutoPlayedViews());
		cellVideoAutoPlayedViewsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowVideoClickToPlayViews = sheetContent.createRow(rowSheetContentCount);
		rowVideoClickToPlayViews.setHeightInPoints(headerDataRowHeight);
		
		Cell cellVideoClickToPlayViewsTitle = rowVideoClickToPlayViews.createCell(0);
		cellVideoClickToPlayViewsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.clickcolon"));
		cellVideoClickToPlayViewsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellVideoClickToPlayViewsValue = rowVideoClickToPlayViews.createCell(1);
		cellVideoClickToPlayViewsValue.setCellValue(reportFacebookContent.getVideoClickToPlayViews());
		cellVideoClickToPlayViewsValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addVideoViews() {
		
		rowSheetContentCount++;
		
		Row rowVideoViewsTitle = sheetContent.createRow(rowSheetContentCount);
		rowVideoViewsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellVideoViewsTitle = rowVideoViewsTitle.createCell(0);
		cellVideoViewsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.videoviewstitle"));
		cellVideoViewsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetContent.addMergedRegion(new CellRangeAddress(rowSheetContentCount, rowSheetContentCount, 0, 6));
		
		rowSheetContentCount++;
		Row rowVideoViewsTypeTitle = sheetContent.createRow(rowSheetContentCount);
		rowVideoViewsTypeTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellVideoViewsTypeTitle = rowVideoViewsTypeTitle.createCell(0);
		cellVideoViewsTypeTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.videoviewstype"));
		cellVideoViewsTypeTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellVideoViewsOrganicTitle = rowVideoViewsTypeTitle.createCell(1);
		cellVideoViewsOrganicTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.organic"));
		cellVideoViewsOrganicTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellVideoViewsPaidTitle = rowVideoViewsTypeTitle.createCell(2);
		cellVideoViewsPaidTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.paid"));
		cellVideoViewsPaidTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellVideoViewsTotalTitle = rowVideoViewsTypeTitle.createCell(3);
		cellVideoViewsTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.total"));
		cellVideoViewsTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellVideoViewsPerformanceTitle = rowVideoViewsTypeTitle.createCell(4);
		cellVideoViewsPerformanceTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.performance"));
		cellVideoViewsPerformanceTitle.setCellStyle(cellSecondaryHeaderData);
		
		createVideoViewsTypeCells(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalvideoviews"), 
				reportFacebookContent.getVideoTotalOrganicViews(), reportFacebookContent.getVideoTotalPaidViews(),
				reportFacebookContent.getVideoTotalViews(), reportFacebookContent.getVideoTotalViewsPerformance());
		
		createVideoViewsTypeCells(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalviews"), 
				reportFacebookContent.getVideoCompleteOrganicViews(), reportFacebookContent.getVideoCompletePaidViews(),
				reportFacebookContent.getVideoCompleteViews(), reportFacebookContent.getVideoCompleteViewsPerformance());
		
		createVideoViewsTypeCells(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.partialviews"), 
				reportFacebookContent.getVideoPartialOrganicViews(), reportFacebookContent.getVideoPartialPaidViews(), 
				reportFacebookContent.getVideoPartialViews(), reportFacebookContent.getVideoPartialViewsPerformance());
		
		rowSheetContentCount +=2;
		Row rowVideoViewTime = sheetContent.createRow(rowSheetContentCount);
		rowVideoViewTime.setHeightInPoints(headerDataRowHeight);
		
		Cell cellVideoViewTimeTitle = rowVideoViewTime.createCell(0);
		cellVideoViewTimeTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalvideoviewtime"));
		cellVideoViewTimeTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellVideoViewTimeValue = rowVideoViewTime.createCell(1);
		cellVideoViewTimeValue.setCellValue(UtilFunctions.fromMilisecondsToHours(reportFacebookContent.getVideoViewTime()) + "H");
		cellVideoViewTimeValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowVideoRepeatViews = sheetContent.createRow(rowSheetContentCount);
		rowVideoRepeatViews.setHeightInPoints(headerDataRowHeight);
		
		Cell cellVideoRepeatViewsTitle = rowVideoRepeatViews.createCell(0);
		cellVideoRepeatViewsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalvideorepeat"));
		cellVideoRepeatViewsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellVideoRepeatViewsValue = rowVideoRepeatViews.createCell(1);
		cellVideoRepeatViewsValue.setCellValue(reportFacebookContent.getVideoRepeatViews());
		cellVideoRepeatViewsValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void createVideoViewsTypeCells(String title, Long organicViews, Long paidViews, Long totalViews, Integer viewsPerformance) {
		
		rowSheetContentCount++;
		Row rowVideoViewsValues = sheetContent.createRow(rowSheetContentCount);
		
		Cell cellViewsTitle = rowVideoViewsValues.createCell(0); 
		cellViewsTitle.setCellValue(title);
		
		Cell cellOrganicViews = rowVideoViewsValues.createCell(1); 
		cellOrganicViews.setCellValue(organicViews);
		
		Cell cellPaidViews = rowVideoViewsValues.createCell(2);
		cellPaidViews.setCellValue(paidViews);
		
		Cell cellTotalViews = rowVideoViewsValues.createCell(3);
		cellTotalViews.setCellValue(totalViews);
		
		Cell cellViewsPerformance = rowVideoViewsValues.createCell(4);
		cellViewsPerformance.setCellValue(viewsPerformance + "%");
	}
	
	private void addPageNegativeFeedbacksType() {
		
		rowSheetContentCount++;
		
		Row rowPageNegativeFeedbacksTitle = sheetContent.createRow(rowSheetContentCount);
		rowPageNegativeFeedbacksTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPageNegativeFeedbacksTitle = rowPageNegativeFeedbacksTitle.createCell(0);
		cellPageNegativeFeedbacksTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.pagenegativefeedback"));
		cellPageNegativeFeedbacksTitle.setCellStyle(cellStyleHeaderData);
		
		sheetContent.addMergedRegion(new CellRangeAddress(rowSheetContentCount, rowSheetContentCount, 0, 6));
		
		rowSheetContentCount++;
		Row rowPageNegativeFeedbacksTypeTitle = sheetContent.createRow(rowSheetContentCount);
		rowPageNegativeFeedbacksTypeTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPageNegativeFeedbacksTypeTitle = rowPageNegativeFeedbacksTypeTitle.createCell(0);
		cellPageNegativeFeedbacksTypeTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.interaction"));
		cellPageNegativeFeedbacksTypeTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellPageNegativeFeedbacksTypeTotalTitle = rowPageNegativeFeedbacksTypeTitle.createCell(1);
		cellPageNegativeFeedbacksTypeTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.total"));
		cellPageNegativeFeedbacksTypeTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetContentCount++;
		for(int i = 0; i < reportFacebookContent.getPageNegativeFeedbacksType().size(); i++) {
			
			Row rowPageNegativeFeedbacksTypeValues = sheetContent.createRow(rowSheetContentCount);
			
			Cell cellTitle = rowPageNegativeFeedbacksTypeValues.createCell(0); 
			cellTitle.setCellValue(OpSocialBackApplication.utilFunctionsService.getPageNegativeFeedbackName(reportFacebookContent.getPageNegativeFeedbacksType().get(i).getProperty()));
			
			Cell cellValue = rowPageNegativeFeedbacksTypeValues.createCell(1);
			cellValue.setCellValue(reportFacebookContent.getPageNegativeFeedbacksType().get(i).getValue());
			
			rowSheetContentCount++;
		}
	}
	
	private void addConsumptionOfPosts() {
		
		rowSheetContentCount += 2;
		
		Row rowConsumptionOfPostsTitle = sheetContent.createRow(rowSheetContentCount);
		rowConsumptionOfPostsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellConsumptionOfPostsTitle = rowConsumptionOfPostsTitle.createCell(0);
		cellConsumptionOfPostsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.consumptionofpoststitle"));
		cellConsumptionOfPostsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetContent.addMergedRegion(new CellRangeAddress(rowSheetContentCount, rowSheetContentCount, 0, 6));
		
		rowSheetContentCount++;
		Row rowConsumptionOfPostsPerDayTitle = sheetContent.createRow(rowSheetContentCount);
		rowConsumptionOfPostsPerDayTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellConsumptionOfPostsPerDayTitle = rowConsumptionOfPostsPerDayTitle.createCell(0);
		cellConsumptionOfPostsPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.interaction"));
		cellConsumptionOfPostsPerDayTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellConsumptionOfPostsTotalTitle = rowConsumptionOfPostsPerDayTitle.createCell(1);
		cellConsumptionOfPostsTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.total"));
		cellConsumptionOfPostsTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetContentCount++;
		for(int i = 0; i < reportFacebookContent.getPostsConsumptionsType().size(); i++) {
			
			Row rowConsumptionsOfPostsValues = sheetContent.createRow(rowSheetContentCount);
			
			Cell cellTitle = rowConsumptionsOfPostsValues.createCell(0); 
			cellTitle.setCellValue(OpSocialBackApplication.utilFunctionsService.getConsumptionTypeName(reportFacebookContent.getPostsConsumptionsType().get(i).getProperty()));
			
			Cell cellValue = rowConsumptionsOfPostsValues.createCell(1);
			cellValue.setCellValue(reportFacebookContent.getPostsConsumptionsType().get(i).getValue());
			
			rowSheetContentCount++;
		}
	}
	
	private void addImpressionsOfPosts() {
		
		rowSheetContentCount += 2;
		
		Row rowImpressionsOfPostsTitle = sheetContent.createRow(rowSheetContentCount);
		rowImpressionsOfPostsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellImpressionsOfPostsTitle = rowImpressionsOfPostsTitle.createCell(0);
		cellImpressionsOfPostsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.impressionsofposts"));
		cellImpressionsOfPostsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetContent.addMergedRegion(new CellRangeAddress(rowSheetContentCount, rowSheetContentCount, 0, 6));
		
		rowSheetContentCount++;
		Row rowImpOfPostsPerDayTitle = sheetContent.createRow(rowSheetContentCount);
		rowImpOfPostsPerDayTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellImpOfPostsDateTitle = rowImpOfPostsPerDayTitle.createCell(0);
		cellImpOfPostsDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.date"));
		cellImpOfPostsDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellImpOfPostsPerDayTitle = rowImpOfPostsPerDayTitle.createCell(1);
		cellImpOfPostsPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.totalimpressions"));
		cellImpOfPostsPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellOrganicImpPerDayTitle = rowImpOfPostsPerDayTitle.createCell(2);
		cellOrganicImpPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.organic"));
		cellOrganicImpPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellPaidImpPerDayTitle = rowImpOfPostsPerDayTitle.createCell(3);
		cellPaidImpPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.paid"));
		cellPaidImpPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetContentCount++;
		for(int i = 0; i < reportFacebookContent.getPostsImpressionsOrganicByDay().size(); i++) {
		
			Row rowImpOfPostsValues = sheetContent.createRow(rowSheetContentCount);

			Cell cellDateValue = rowImpOfPostsValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(reportFacebookContent.getPostsImpressionsOrganicByDay().get(i).getDate() * 1000L)));
			
			Long impressionsOfPosts = reportFacebookContent.getPostsImpressionsOrganicByDay().get(i).getSum() + 
					reportFacebookContent.getPostsImpressionsPaidByDay().get(i).getSum();
			
			Cell cellImpressionsOfPostsValue = rowImpOfPostsValues.createCell(1);
			cellImpressionsOfPostsValue.setCellValue(impressionsOfPosts);
			
			Cell cellOrganicImpressionsValue = rowImpOfPostsValues.createCell(2);
			cellOrganicImpressionsValue.setCellValue(reportFacebookContent.getPostsImpressionsOrganicByDay().get(i).getSum());
			
			Cell cellPaidImpressionsValue = rowImpOfPostsValues.createCell(3);
			cellPaidImpressionsValue.setCellValue(reportFacebookContent.getPostsImpressionsPaidByDay().get(i).getSum());
			
			rowSheetContentCount++;
		}
		
		rowSheetContentCount++;
		Row rowTotalImpressions = sheetContent.createRow(rowSheetContentCount);
		rowTotalImpressions.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalImpressionsTitle = rowTotalImpressions.createCell(0);
		cellTotalImpressionsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.totalimpressions"));
		cellTotalImpressionsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalImpressionsValue = rowTotalImpressions.createCell(1);
		cellTotalImpressionsValue.setCellValue(reportFacebookContent.getPostsImpressionsOrganicCount() + reportFacebookContent.getPostsImpressionsPaidCount());
		cellTotalImpressionsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowAverageImpressions = sheetContent.createRow(rowSheetContentCount);
		rowAverageImpressions.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAverageImpressionsTitle = rowAverageImpressions.createCell(0);
		cellAverageImpressionsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.impressionsdailyaverage"));
		cellAverageImpressionsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellAverageImpressionsValue = rowAverageImpressions.createCell(1);
		cellAverageImpressionsValue.setCellValue(reportFacebookContent.getAverageImpressionsOfPostsPerDay());
		cellAverageImpressionsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowOrganicImpressionsOfPosts = sheetContent.createRow(rowSheetContentCount);
		rowOrganicImpressionsOfPosts.setHeightInPoints(headerDataRowHeight);
		
		Cell cellOrganicImpressionsOfPostsTitle = rowOrganicImpressionsOfPosts.createCell(0);
		cellOrganicImpressionsOfPostsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.organicimpressionsonperiod"));
		cellOrganicImpressionsOfPostsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellOrganicImpressionsOfPostsValue = rowOrganicImpressionsOfPosts.createCell(1);
		cellOrganicImpressionsOfPostsValue.setCellValue(reportFacebookContent.getPostsImpressionsOrganicCount() + " / " + reportFacebookContent.getPostsImpressionsOrganicPercentage() + "%");
		cellOrganicImpressionsOfPostsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowPaidImpressionsOfPosts = sheetContent.createRow(rowSheetContentCount);
		rowPaidImpressionsOfPosts.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPaidImpressionsOfPostsTitle = rowPaidImpressionsOfPosts.createCell(0);
		cellPaidImpressionsOfPostsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.paidimpressionsonperiod"));
		cellPaidImpressionsOfPostsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellPaidImpressionsOfPostsValue = rowPaidImpressionsOfPosts.createCell(1);
		cellPaidImpressionsOfPostsValue.setCellValue(reportFacebookContent.getPostsImpressionsPaidCount() + " / " + reportFacebookContent.getPostsImpressionsPaidPercentage() + "%");
		cellPaidImpressionsOfPostsValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addShares() {
		
		rowSheetContentCount += 2;
		
		Row rowSharesTitle = sheetContent.createRow(rowSheetContentCount);
		rowSharesTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellSharesTitle = rowSharesTitle.createCell(0);
		cellSharesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.shares"));
		cellSharesTitle.setCellStyle(cellStyleHeaderData);
		
		sheetContent.addMergedRegion(new CellRangeAddress(rowSheetContentCount, rowSheetContentCount, 0, 6));
		
		rowSheetContentCount++;
		Row rowSharesPerDayTitle = sheetContent.createRow(rowSheetContentCount);
		rowSharesPerDayTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellSharesDateTitle = rowSharesPerDayTitle.createCell(0);
		cellSharesDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.date"));
		cellSharesDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellSharesPerDayTitle = rowSharesPerDayTitle.createCell(1);
		cellSharesPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.shares"));
		cellSharesPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetContentCount++;
		for(FanPageGrowth sharesDay : reportFacebookContent.getSharesPerDay()) {
		
			Row rowSharesValues = sheetContent.createRow(rowSheetContentCount);

			Cell cellDateValue = rowSharesValues.createCell(0);
				cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(sharesDay.getDate() * 1000L)));
			
			Cell cellSharesValue = rowSharesValues.createCell(1);
			cellSharesValue.setCellValue(sharesDay.getSum());
			
			rowSheetContentCount++;
		}
		
		rowSheetContentCount++;
		Row rowTotalShares = sheetContent.createRow(rowSheetContentCount);
		rowTotalShares.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalSharesTitle = rowTotalShares.createCell(0);
		cellTotalSharesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.sharesonperiod"));
		cellTotalSharesTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalSharesValue = rowTotalShares.createCell(1);
		cellTotalSharesValue.setCellValue(reportFacebookContent.getTotalShares());
		cellTotalSharesValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowMajorSharesDay = sheetContent.createRow(rowSheetContentCount);
		rowMajorSharesDay.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMajorSharesDayTitle = rowMajorSharesDay.createCell(0);
		cellMajorSharesDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.mostsharesday"));
		cellMajorSharesDayTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMajorSharesDayValue = rowMajorSharesDay.createCell(1);
		cellMajorSharesDayValue.setCellValue(new SimpleDateFormat("MM/dd").format(new Date(reportFacebookContent.getMajorSharesDay().getDate() * 1000L)) +
				" " + OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.had") + " " + reportFacebookContent.getMajorSharesDay().getSum() 
				+ " " + OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.shares"));
		cellMajorSharesDayValue.setCellStyle(cellStylePreAnalysisResult);
		
	}
	
	private void addReactions() {
		
		rowSheetContentCount += 2;
		
		Row rowReactionsTitle = sheetContent.createRow(rowSheetContentCount);
		rowReactionsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellReactionsTitle = rowReactionsTitle.createCell(0);
		cellReactionsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.likesreactions"));
		cellReactionsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetContent.addMergedRegion(new CellRangeAddress(rowSheetContentCount, rowSheetContentCount, 0, 6));
		
		rowSheetContentCount++;
		Row rowReactionsPerDayTitle = sheetContent.createRow(rowSheetContentCount);
		rowReactionsPerDayTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellReactionsDateTitle = rowReactionsPerDayTitle.createCell(0);
		cellReactionsDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.date"));
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
		
		rowSheetContentCount++;
		for(int i = 0; i < reportFacebookContent.getLikesPerDay().size(); i++) {
		
			Row rowReactionsValues = sheetContent.createRow(rowSheetContentCount);

			Cell cellDateValue = rowReactionsValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(reportFacebookContent.getLikesPerDay().get(i).getDate() * 1000L)));
			
			Cell cellLikesValue = rowReactionsValues.createCell(1);
			cellLikesValue.setCellValue(reportFacebookContent.getLikesPerDay().get(i).getSum());
			
			Cell cellLoveValue = rowReactionsValues.createCell(2);
			cellLoveValue.setCellValue(reportFacebookContent.getLovePerDay().get(i).getSum());
			
			Cell cellHahaValue = rowReactionsValues.createCell(3);
			cellHahaValue.setCellValue(reportFacebookContent.getHahaPerDay().get(i).getSum());
			
			Cell cellWowValue = rowReactionsValues.createCell(4);
			cellWowValue.setCellValue(reportFacebookContent.getWowPerDay().get(i).getSum());
			
			Cell cellSorryValue = rowReactionsValues.createCell(5);
			cellSorryValue.setCellValue(reportFacebookContent.getSorryPerDay().get(i).getSum());
			
			Cell cellAngerValue = rowReactionsValues.createCell(6);
			cellAngerValue.setCellValue(reportFacebookContent.getAngerPerDay().get(i).getSum());
			
			rowSheetContentCount++;
		}
		
		rowSheetContentCount++;
		Row rowTotalReactions = sheetContent.createRow(rowSheetContentCount);
		rowTotalReactions.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalReactionsTitle = rowTotalReactions.createCell(0);
		cellTotalReactionsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalreactions"));
		cellTotalReactionsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalReactionsValue = rowTotalReactions.createCell(1);
		cellTotalReactionsValue.setCellValue(
				reportFacebookContent.getTotalLikes() + reportFacebookContent.getTotalAnger() + reportFacebookContent.getTotalHaha() +
				reportFacebookContent.getTotalLove() + reportFacebookContent.getTotalSorry() + reportFacebookContent.getTotalWow());
		cellTotalReactionsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowTotalLikes = sheetContent.createRow(rowSheetContentCount);
		rowTotalLikes.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalLikesTitle = rowTotalLikes.createCell(0);
		cellTotalLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.like") + ":");
		cellTotalLikesTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalLikesValue = rowTotalLikes.createCell(1);
		cellTotalLikesValue.setCellValue(reportFacebookContent.getTotalLikes());
		cellTotalLikesValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowTotalLove = sheetContent.createRow(rowSheetContentCount);
		rowTotalLove.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalLoveTitle = rowTotalLove.createCell(0);
		cellTotalLoveTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.love") + ":");
		cellTotalLoveTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalLoveValue = rowTotalLove.createCell(1);
		cellTotalLoveValue.setCellValue(reportFacebookContent.getTotalLove());
		cellTotalLoveValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowTotalHaha = sheetContent.createRow(rowSheetContentCount);
		rowTotalHaha.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalHahaTitle = rowTotalHaha.createCell(0);
		cellTotalHahaTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.haha") + ":");
		cellTotalHahaTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalHahaValue = rowTotalHaha.createCell(1);
		cellTotalHahaValue.setCellValue(reportFacebookContent.getTotalHaha());
		cellTotalHahaValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowTotalWow = sheetContent.createRow(rowSheetContentCount);
		rowTotalWow.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalWowTitle = rowTotalWow.createCell(0);
		cellTotalWowTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.wow") + ":");
		cellTotalWowTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalWowValue = rowTotalWow.createCell(1);
		cellTotalWowValue.setCellValue(reportFacebookContent.getTotalWow());
		cellTotalWowValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowTotalSorry = sheetContent.createRow(rowSheetContentCount);
		rowTotalSorry.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalSorryTitle = rowTotalSorry.createCell(0);
		cellTotalSorryTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.sad") + ":");
		cellTotalSorryTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalSorryValue = rowTotalSorry.createCell(1);
		cellTotalSorryValue.setCellValue(reportFacebookContent.getTotalSorry());
		cellTotalSorryValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowTotalAnger = sheetContent.createRow(rowSheetContentCount);
		rowTotalAnger.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalAngerTitle = rowTotalAnger.createCell(0);
		cellTotalAngerTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.grr") + ":");
		cellTotalAngerTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalAngerValue = rowTotalAnger.createCell(1);
		cellTotalAngerValue.setCellValue(reportFacebookContent.getTotalAnger());
		cellTotalAngerValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addComments() {
		
		rowSheetContentCount += 2;
		
		Row rowCommentsTitle = sheetContent.createRow(rowSheetContentCount);
		rowCommentsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCommentsTitle = rowCommentsTitle.createCell(0);
		cellCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.commentsupper"));
		cellCommentsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetContent.addMergedRegion(new CellRangeAddress(rowSheetContentCount, rowSheetContentCount, 0, 6));
		
		rowSheetContentCount++;
		Row rowCommentsPerDayTitle = sheetContent.createRow(rowSheetContentCount);
		rowCommentsPerDayTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCommentsDateTitle = rowCommentsPerDayTitle.createCell(0);
		cellCommentsDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.date"));
		cellCommentsDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCommentsPerDayTitle = rowCommentsPerDayTitle.createCell(1);
		cellCommentsPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.comments"));
		cellCommentsPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetContentCount++;
		for(FanPageGrowth commentsDay : reportFacebookContent.getCommentsPerDay()) {
		
			Row rowCommentsValues = sheetContent.createRow(rowSheetContentCount);

			Cell cellDateValue = rowCommentsValues.createCell(0);
				cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(commentsDay.getDate() * 1000L)));
			
			Cell cellCommentsValue = rowCommentsValues.createCell(1);
			cellCommentsValue.setCellValue(commentsDay.getSum());
			
			rowSheetContentCount++;
		}
		
		rowSheetContentCount++;
		Row rowTotalComments = sheetContent.createRow(rowSheetContentCount);
		rowTotalComments.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalCommentsTitle = rowTotalComments.createCell(0);
		cellTotalCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalcommentsperiod"));
		cellTotalCommentsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalCommentsValue = rowTotalComments.createCell(1);
		cellTotalCommentsValue.setCellValue(reportFacebookContent.getTotalComments());
		cellTotalCommentsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowMajorCommentsDay = sheetContent.createRow(rowSheetContentCount);
		rowMajorCommentsDay.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMajorCommentsDayTitle = rowMajorCommentsDay.createCell(0);
		cellMajorCommentsDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.majorcommentsday"));
		cellMajorCommentsDayTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMajorCommentsDayValue = rowMajorCommentsDay.createCell(1);
		cellMajorCommentsDayValue.setCellValue(new SimpleDateFormat("MM/dd").format(new Date(reportFacebookContent.getMajorCommentsDay().getDate() * 1000L)) +
				" " + OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.had") + " " + reportFacebookContent.getMajorCommentsDay().getSum() 
				+ " " + OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.comments"));
		cellMajorCommentsDayValue.setCellStyle(cellStylePreAnalysisResult);
		
	}
	
	private void addReachOfPosts() {
		
		rowSheetContentCount += 2;
		
		Row rowReachOfPostsTitle = sheetContent.createRow(rowSheetContentCount);
		rowReachOfPostsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellReachOfPostsTitle = rowReachOfPostsTitle.createCell(0);
		cellReachOfPostsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.postreach"));
		cellReachOfPostsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetContent.addMergedRegion(new CellRangeAddress(rowSheetContentCount, rowSheetContentCount, 0, 6));
		
		rowSheetContentCount++;
		Row rowReachOfPostsPerDayTitle = sheetContent.createRow(rowSheetContentCount);
		rowReachOfPostsPerDayTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellReachOfPostsDateTitle = rowReachOfPostsPerDayTitle.createCell(0);
		cellReachOfPostsDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.date"));
		cellReachOfPostsDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellReachOfPostsPerDayTitle = rowReachOfPostsPerDayTitle.createCell(1);
		cellReachOfPostsPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalreach"));
		cellReachOfPostsPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellOrganicReachPerDayTitle = rowReachOfPostsPerDayTitle.createCell(2);
		cellOrganicReachPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.organic"));
		cellOrganicReachPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellPaidReachPerDayTitle = rowReachOfPostsPerDayTitle.createCell(3);
		cellPaidReachPerDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.paid"));
		cellPaidReachPerDayTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetContentCount++;
		for(int i = 0; i < reportFacebookContent.getPostsReachOrganicByDay().size(); i++) {
		
			Row rowReachOfPostsValues = sheetContent.createRow(rowSheetContentCount);

			Cell cellDateValue = rowReachOfPostsValues.createCell(0);
				cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(reportFacebookContent.getPostsReachOrganicByDay().get(i).getDate() * 1000L)));
			
			Long reachOfPosts = reportFacebookContent.getPostsReachOrganicByDay().get(i).getSum() + 
					reportFacebookContent.getPostsReachPaidByDay().get(i).getSum();
			
			Cell cellReachOfPostsValue = rowReachOfPostsValues.createCell(1);
			cellReachOfPostsValue.setCellValue(reachOfPosts);
			
			Cell cellOrganicReachValue = rowReachOfPostsValues.createCell(2);
			cellOrganicReachValue.setCellValue(reportFacebookContent.getPostsReachOrganicByDay().get(i).getSum());
			
			Cell cellPaidReachValue = rowReachOfPostsValues.createCell(3);
			cellPaidReachValue.setCellValue(reportFacebookContent.getPostsReachPaidByDay().get(i).getSum());
			
			rowSheetContentCount++;
		}
		
		rowSheetContentCount++;
		Row rowTotalReachOfPosts = sheetContent.createRow(rowSheetContentCount);
		rowTotalReachOfPosts.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAveragePostsReachTitle = rowTotalReachOfPosts.createCell(0);
		cellAveragePostsReachTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.periodtotalreach"));
		cellAveragePostsReachTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellAveragePostsReachValue = rowTotalReachOfPosts.createCell(1);
		cellAveragePostsReachValue.setCellValue(reportFacebookContent.getPostsReachOrganicCount() + reportFacebookContent.getPostsReachPaidCount());
		cellAveragePostsReachValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowAverageReachOfPosts = sheetContent.createRow(rowSheetContentCount);
		rowAverageReachOfPosts.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAverageReachOfPostsTitle = rowAverageReachOfPosts.createCell(0);
		cellAverageReachOfPostsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.dailyaveragereach"));
		cellAverageReachOfPostsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellAverageReachOfPostsValue = rowAverageReachOfPosts.createCell(1);
		cellAverageReachOfPostsValue.setCellValue(reportFacebookContent.getAverageReachOfPostsPerDay());
		cellAverageReachOfPostsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowOrganicReachOfPosts = sheetContent.createRow(rowSheetContentCount);
		rowOrganicReachOfPosts.setHeightInPoints(headerDataRowHeight);
		
		Cell cellOrganicReachOfPostsTitle = rowOrganicReachOfPosts.createCell(0);
		cellOrganicReachOfPostsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.organicreach"));
		cellOrganicReachOfPostsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellOrganicReachOfPostsValue = rowOrganicReachOfPosts.createCell(1);
		cellOrganicReachOfPostsValue.setCellValue(reportFacebookContent.getPostsReachOrganicCount() + " / " + reportFacebookContent.getPostsReachOrganicPercentage() + "%");
		cellOrganicReachOfPostsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowPaidReachOfPosts = sheetContent.createRow(rowSheetContentCount);
		rowPaidReachOfPosts.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPaidReachOfPostsTitle = rowPaidReachOfPosts.createCell(0);
		cellPaidReachOfPostsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.paidreach"));
		cellPaidReachOfPostsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellPaidReachOfPostsValue = rowPaidReachOfPosts.createCell(1);
		cellPaidReachOfPostsValue.setCellValue(reportFacebookContent.getPostsReachPaidCount() + " / " + reportFacebookContent.getPostsReachPaidPercentage() + "%");
		cellPaidReachOfPostsValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addGeneralPostsStats() {
		
		rowSheetContentCount = 2;
		
		Row rowGeneralPostsStatsTitle = sheetContent.createRow(rowSheetContentCount);
		rowGeneralPostsStatsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellGeneralPostsStatsTitle = rowGeneralPostsStatsTitle.createCell(0);
		cellGeneralPostsStatsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.postsonperiod"));
		cellGeneralPostsStatsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetContent.addMergedRegion(new CellRangeAddress(rowSheetContentCount, rowSheetContentCount, 0, 6));
		
		rowSheetContentCount++;
		Row rowPostsPerDayTitle = sheetContent.createRow(rowSheetContentCount);
		rowPostsPerDayTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsDateTitle = rowPostsPerDayTitle.createCell(0);
		cellPostsDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.date"));
		cellPostsDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellPostsTotalTitle = rowPostsPerDayTitle.createCell(1);
		cellPostsTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.totalposts"));
		cellPostsTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetContentCount++;
		for (SumByDate postsPerDay : reportFacebookContent.getNumberOfPosts()) {

			Row rowPostsTotalValues = sheetContent.createRow(rowSheetContentCount);

			Cell cellDateValue = rowPostsTotalValues.createCell(0);
			cellDateValue.setCellValue(postsPerDay.getDay() + "/" + postsPerDay.getMonth() + "/" + postsPerDay.getYear());
			
			Cell cellTotalValue = rowPostsTotalValues.createCell(1);
			cellTotalValue.setCellValue(postsPerDay.getSum());

			rowSheetContentCount++;
		}
		
		rowSheetContentCount++;
		Row rowAveragePostsReach = sheetContent.createRow(rowSheetContentCount);
		rowAveragePostsReach.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAveragePostsReachTitle = rowAveragePostsReach.createCell(0);
		cellAveragePostsReachTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.postsreachaverage"));
		cellAveragePostsReachTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellAveragePostsReachValue = rowAveragePostsReach.createCell(1);
		cellAveragePostsReachValue.setCellValue(reportFacebookContent.getAveragePostsReach());
		cellAveragePostsReachValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetContentCount++;
		Row rowAveragePostsInteractions = sheetContent.createRow(rowSheetContentCount);
		rowAveragePostsInteractions.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAveragePostsInteractionsTitle = rowAveragePostsInteractions.createCell(0);
		cellAveragePostsInteractionsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.postsinteractionaverage"));
		cellAveragePostsInteractionsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellAveragePostsInteractionsValue = rowAveragePostsInteractions.createCell(1);
		cellAveragePostsInteractionsValue.setCellValue(reportFacebookContent.getAveragePostsInteractions());
		cellAveragePostsInteractionsValue.setCellStyle(cellStylePreAnalysisResult);
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
	        createHelper.createDataFormat().getFormat("mm/dd/yyyy hh:mm"));
	        
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
