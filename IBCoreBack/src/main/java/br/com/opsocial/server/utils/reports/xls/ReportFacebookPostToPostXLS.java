package br.com.opsocial.server.utils.reports.xls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import javax.servlet.ServletContext;

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
import org.springframework.beans.factory.annotation.Autowired;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.report.facebook.FaceReportPostDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookPostToPostDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.report.PropertyValue;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.aws.S3Integration;

public class ReportFacebookPostToPostXLS {
	
	private ReportFacebookPostToPostDTO reportFacebookPostToPost;
	private Profile profile;
	
	private String reportsPath;
	private String fileName;
	private String formattedFileName;
	
	private HSSFWorkbook wb;
	private Sheet sheetPosts;
	
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
	
	private int rowSheetPostsMostEngaged;
	
	public ReportFacebookPostToPostXLS(ReportFacebookPostToPostDTO reportFacebookPostToPost, Profile profile) {
		this.reportFacebookPostToPost = reportFacebookPostToPost;
		this.profile = profile;
	}
	
	private void addMainHeader(Sheet sheetDataPosts, String text, int untilColumn) {
		
		Row row = sheetDataPosts.createRow(0);
		row.setHeightInPoints((2 * sheetDataPosts.getDefaultRowHeightInPoints()));
		
		Cell cellURL = row.createCell(0);
		cellURL.setCellValue(text);
		cellURL.setCellStyle(cellStyleHeader);
		
		sheetDataPosts.addMergedRegion(new CellRangeAddress(0, 0, 0, untilColumn));
	}
	
	public void createXLS() {
		
		wb = new HSSFWorkbook();
		
		initiateDefautStyles();
		
		this.reportsPath = OpSocialBackApplication.GET_DIR_REPORTS_XLS;
		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".xls");

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat dateFormatToFileName = new SimpleDateFormat("MM-dd-yyyy");
		
		startDate = dateFormat.format(new Date(reportFacebookPostToPost.getDateFrom() * 1000L));
		endDate = dateFormat.format(new Date(reportFacebookPostToPost.getDateUntil() * 1000L));
		
		sheetPosts = wb.createSheet(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.posttopost.tabname"));
		
		headerDataRowHeight = 1.2F * sheetPosts.getDefaultRowHeightInPoints();
		
		addMainHeader(sheetPosts, OpSocialBackApplication.messageByLocaleService.
				getMessage("xls.facebook.report.posttopost.header", new Object[]{profile.getScreenName(), startDate, endDate}), 16);
		
		addPosts();
		addVideoPosts();
		
		sheetPosts.setDefaultColumnWidth(15);
		sheetPosts.setColumnWidth(0, 7000);
		sheetPosts.setColumnWidth(1, 7000);
		sheetPosts.setColumnWidth(2, 7000);
		sheetPosts.setColumnWidth(3, 7000);
		
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
				getMessage("xls.facebook.report.posttopost.doctitle", new Object[]{
						profile.getScreenName(), 
						dateFormatToFileName.format(new Date(reportFacebookPostToPost.getDateFrom() * 1000L)), 
						dateFormatToFileName.format(new Date(reportFacebookPostToPost.getDateUntil() * 1000L))
			});
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addVideoPosts() {
		
		rowSheetPostsMostEngaged += 2;
		Row rowPostsMostEngagedTitle = sheetPosts.createRow(rowSheetPostsMostEngaged);
		rowPostsMostEngagedTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsMostEngagedTitle = rowPostsMostEngagedTitle.createCell(0);
		cellPostsMostEngagedTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.posttopost.pagevideos"));
		cellPostsMostEngagedTitle.setCellStyle(cellStyleHeaderData);
		
		sheetPosts.addMergedRegion(new CellRangeAddress(rowSheetPostsMostEngaged, rowSheetPostsMostEngaged, 0, 16));
		
		for(FaceReportPostDTO faceReportPost : reportFacebookPostToPost.getPosts()) {
			
			if(faceReportPost.getType().equals(FaceReportPostDTO.VIDEO_POSTED)) {
				
				rowSheetPostsMostEngaged++;
				Row rowPostsMostEngagedHeader = sheetPosts.createRow(rowSheetPostsMostEngaged);
				rowPostsMostEngagedHeader.setHeightInPoints(headerDataRowHeight);
				
				Cell cellDateHeader = rowPostsMostEngagedHeader.createCell(0);
				cellDateHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.date"));
				cellDateHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellTimeHeader = rowPostsMostEngagedHeader.createCell(1);
				cellTimeHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.hour"));
				cellTimeHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellPostHeader = rowPostsMostEngagedHeader.createCell(2);
				cellPostHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.post"));
				cellPostHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellReachHeader = rowPostsMostEngagedHeader.createCell(3);
				cellReachHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalreach"));
				cellReachHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellOrganicReachHeader = rowPostsMostEngagedHeader.createCell(4);
				cellOrganicReachHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.organic"));
				cellOrganicReachHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellPaidReachHeader = rowPostsMostEngagedHeader.createCell(5);
				cellPaidReachHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.paid"));
				cellPaidReachHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellInteractionsHeader = rowPostsMostEngagedHeader.createCell(6);
				cellInteractionsHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.totalinteraction"));
				cellInteractionsHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellLoveHeader = rowPostsMostEngagedHeader.createCell(7);
				cellLoveHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.love"));
				cellLoveHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellHahaHeader = rowPostsMostEngagedHeader.createCell(8);
				cellHahaHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.haha"));
				cellHahaHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellWowHeader = rowPostsMostEngagedHeader.createCell(9);
				cellWowHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.wow"));
				cellWowHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellSadHeader = rowPostsMostEngagedHeader.createCell(10);
				cellSadHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.sad"));
				cellSadHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellAngryHeader = rowPostsMostEngagedHeader.createCell(11);
				cellAngryHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.grr"));
				cellAngryHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellLikesHeader = rowPostsMostEngagedHeader.createCell(12);
				cellLikesHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.like"));
				cellLikesHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellCommentsHeader = rowPostsMostEngagedHeader.createCell(13);
				cellCommentsHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.comments"));
				cellCommentsHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellSharesHeader = rowPostsMostEngagedHeader.createCell(14);
				cellSharesHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.shares"));
				cellSharesHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellClicksHeader = rowPostsMostEngagedHeader.createCell(15);
				cellClicksHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.clicks"));
				cellClicksHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellVideoViewsHeader = rowPostsMostEngagedHeader.createCell(16);
				cellVideoViewsHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.totalview"));
				cellVideoViewsHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellOrganicVideoViewsHeader = rowPostsMostEngagedHeader.createCell(17);
				cellOrganicVideoViewsHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.organicviews"));
				cellOrganicVideoViewsHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellPaidVideoViewsHeader = rowPostsMostEngagedHeader.createCell(18);
				cellPaidVideoViewsHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.paidviews"));
				cellPaidVideoViewsHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellAutoPlayedViewsHeader = rowPostsMostEngagedHeader.createCell(19);
				cellAutoPlayedViewsHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.autoplay"));
				cellAutoPlayedViewsHeader.setCellStyle(cellStylePreAnalysis);
				
				Cell cellClickToPlayViewsHeader = rowPostsMostEngagedHeader.createCell(20);
				cellClickToPlayViewsHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.click"));
				cellClickToPlayViewsHeader.setCellStyle(cellStylePreAnalysis);
				
				rowSheetPostsMostEngaged++;
				Row rowPostMostEngaged = sheetPosts.createRow(rowSheetPostsMostEngaged);
				
				Cell cellDate = rowPostMostEngaged.createCell(0);
				cellDate.setCellValue(new SimpleDateFormat("MM/dd/yyyy").format(faceReportPost.getCreatedTime()));
				
				Cell cellTime = rowPostMostEngaged.createCell(1);
				cellTime.setCellValue(new SimpleDateFormat("HH:mm").format(faceReportPost.getCreatedTime()));
				
				Cell cellPost = rowPostMostEngaged.createCell(2);
				cellPost.setCellValue(faceReportPost.getMessage());
				
				Cell cellReach = rowPostMostEngaged.createCell(3);
				cellReach.setCellValue(faceReportPost.getReach());
				
				Cell cellOrganicReach = rowPostMostEngaged.createCell(4);
				cellOrganicReach.setCellValue(faceReportPost.getOrganicReach());
				
				Cell cellPaidReach = rowPostMostEngaged.createCell(5);
				cellPaidReach.setCellValue(faceReportPost.getPaidReach());
				
				Cell cellInteractions = rowPostMostEngaged.createCell(6);
				cellInteractions.setCellValue(faceReportPost.getInteractions());
				
				Cell cellLove = rowPostMostEngaged.createCell(7);
				cellLove.setCellValue(faceReportPost.getLove());
				
				Cell cellHaha = rowPostMostEngaged.createCell(8);
				cellHaha.setCellValue(faceReportPost.getHaha());
				
				Cell cellWow = rowPostMostEngaged.createCell(9);
				cellWow.setCellValue(faceReportPost.getWow());
				
				Cell cellSad = rowPostMostEngaged.createCell(10);
				cellSad.setCellValue(faceReportPost.getSorry());
				
				Cell cellAngry = rowPostMostEngaged.createCell(11);
				cellAngry.setCellValue(faceReportPost.getAnger());
				
				Cell cellLikes = rowPostMostEngaged.createCell(12);
				cellLikes.setCellValue(faceReportPost.getLikes());
				
				Cell cellComments = rowPostMostEngaged.createCell(13);
				cellComments.setCellValue(faceReportPost.getComments());
				
				Cell cellShares = rowPostMostEngaged.createCell(14);
				cellShares.setCellValue(faceReportPost.getShares());
				
				Cell cellClicks = rowPostMostEngaged.createCell(15);
				cellClicks.setCellValue(faceReportPost.getClicks());
				
				Cell cellVideoViews = rowPostMostEngaged.createCell(16);
				cellVideoViews.setCellValue(faceReportPost.getVideoViews());
				
				Cell cellOrganicVideoViews = rowPostMostEngaged.createCell(17);
				cellOrganicVideoViews.setCellValue(faceReportPost.getVideoOrganicViews());
				
				Cell cellPaidVideoViews = rowPostMostEngaged.createCell(18);
				cellPaidVideoViews.setCellValue(faceReportPost.getVideoPaidViews());
				
				Cell cellAutoPlayedViews = rowPostMostEngaged.createCell(19);
				cellAutoPlayedViews.setCellValue(faceReportPost.getVideoAutoPlayedViews());
				
				Cell cellClickToPlayViews = rowPostMostEngaged.createCell(20);
				cellClickToPlayViews.setCellValue(faceReportPost.getVideoClickToPlayViews());
				
				rowSheetPostsMostEngaged++;
				
				faceReportPost.getVideoRetention().sort(Comparator.comparingLong(PropertyValue::getPropertyAsLong));
				
				Row rowVideoRetentionTitle = sheetPosts.createRow(rowSheetPostsMostEngaged);
				rowVideoRetentionTitle.setHeightInPoints(headerDataRowHeight);	
				
				Cell cellVideoRetentionTitle = rowVideoRetentionTitle.createCell(0);
				cellVideoRetentionTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.posttopost.publicretention"));
				cellVideoRetentionTitle.setCellStyle(cellSecondaryHeaderData);
				
				int i = 1;
				for(PropertyValue propertyValue : faceReportPost.getVideoRetention()) {
					
					Cell cellVideoRetentionTitleValue = rowVideoRetentionTitle.createCell(i);
					cellVideoRetentionTitleValue.setCellValue(propertyValue.getProperty());
					cellVideoRetentionTitleValue.setCellStyle(cellSecondaryHeaderData);
					
					i++;
				}
				
				rowSheetPostsMostEngaged++;
				
				Row rowVideoRetention = sheetPosts.createRow(rowSheetPostsMostEngaged);
				rowVideoRetention.setHeightInPoints(headerDataRowHeight);	
				
				int j = 1;
				for(PropertyValue propertyValue : faceReportPost.getVideoRetention()) {
					
					Cell cellMajorAgeGroupUsersValue = rowVideoRetention.createCell(j);
					cellMajorAgeGroupUsersValue.setCellValue(new Double(propertyValue.getValue().doubleValue() / 100).toString().replace('.', ',') + "%");
					
					j++;
				}
				
				rowSheetPostsMostEngaged++;
				Row rowStatsTitle = sheetPosts.createRow(rowSheetPostsMostEngaged);
				rowStatsTitle.setHeightInPoints(headerDataRowHeight);
				
				Cell cellStatsTitle = rowStatsTitle.createCell(0);
				cellStatsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.posttopost.videoviewtimeagegender"));
				cellStatsTitle.setCellStyle(cellSecondaryHeaderData);
				
				sheetPosts.addMergedRegion(new CellRangeAddress(rowSheetPostsMostEngaged, rowSheetPostsMostEngaged, 0, 6));
				
				rowSheetPostsMostEngaged++;
				Row rowAgeGroupUsersTitle = sheetPosts.createRow(rowSheetPostsMostEngaged);
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
				
				for(int y = 0; y < faceReportPost.getVideoViewTimeByFemaleUsers().size(); y++) {
				
					rowSheetPostsMostEngaged++;
					Row rowValues = sheetPosts.createRow(rowSheetPostsMostEngaged);
					
					Cell cellAgeGroup = rowValues.createCell(0);
					cellAgeGroup.setCellValue(UtilFunctions.formatAgeGroupDisplay(faceReportPost.getVideoViewTimeByFemaleUsers().get(y).getInitialAgeGroup(), 
							faceReportPost.getVideoViewTimeByFemaleUsers().get(y).getEndAgeGroup()));
					
					Cell cellMaleUsers = rowValues.createCell(1);
					cellMaleUsers.setCellValue(UtilFunctions.fromMilisecondsToMinutes(faceReportPost.getVideoViewTimeByMaleUsers().get(y).getValue()));
					
					Cell cellFemaleUsers = rowValues.createCell(2);
					cellFemaleUsers.setCellValue(UtilFunctions.fromMilisecondsToMinutes(faceReportPost.getVideoViewTimeByFemaleUsers().get(y).getValue()));
					
					Cell cellUnidentifiedUsers = rowValues.createCell(3);
					cellUnidentifiedUsers.setCellValue(UtilFunctions.fromMilisecondsToMinutes(faceReportPost.getVideoViewTimeByUnidentifiedUsers().get(y).getValue()));
				}
				
				rowSheetPostsMostEngaged++;
			}
		}
	}
	
	private void addPosts() {
	
		rowSheetPostsMostEngaged += 2;
		Row rowPostsMostEngagedTitle = sheetPosts.createRow(rowSheetPostsMostEngaged);
		rowPostsMostEngagedTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsMostEngagedTitle = rowPostsMostEngagedTitle.createCell(0);
		cellPostsMostEngagedTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.posttopost.pageposts"));
		cellPostsMostEngagedTitle.setCellStyle(cellStyleHeaderData);
		
		sheetPosts.addMergedRegion(new CellRangeAddress(rowSheetPostsMostEngaged, rowSheetPostsMostEngaged, 0, 16));
		
		rowSheetPostsMostEngaged++;
		Row rowPostsMostEngagedHeader = sheetPosts.createRow(rowSheetPostsMostEngaged);
		rowPostsMostEngagedHeader.setHeightInPoints(headerDataRowHeight);
		
		Cell cellDateHeader = rowPostsMostEngagedHeader.createCell(0);
		cellDateHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.date"));
		cellDateHeader.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTimeHeader = rowPostsMostEngagedHeader.createCell(1);
		cellTimeHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.hour"));
		cellTimeHeader.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellPostHeader = rowPostsMostEngagedHeader.createCell(2);
		cellPostHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.post"));
		cellPostHeader.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellReachHeader = rowPostsMostEngagedHeader.createCell(3);
		cellReachHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.content.totalreach"));
		cellReachHeader.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellOrganicReachHeader = rowPostsMostEngagedHeader.createCell(4);
		cellOrganicReachHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.organic"));
		cellOrganicReachHeader.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellPaidReachHeader = rowPostsMostEngagedHeader.createCell(5);
		cellPaidReachHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.paid"));
		cellPaidReachHeader.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellInteractionsHeader = rowPostsMostEngagedHeader.createCell(6);
		cellInteractionsHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.totalinteraction"));
		cellInteractionsHeader.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellLoveHeader = rowPostsMostEngagedHeader.createCell(7);
		cellLoveHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.love"));
		cellLoveHeader.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellHahaHeader = rowPostsMostEngagedHeader.createCell(8);
		cellHahaHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.haha"));
		cellHahaHeader.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellWowHeader = rowPostsMostEngagedHeader.createCell(9);
		cellWowHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.wow"));
		cellWowHeader.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellSadHeader = rowPostsMostEngagedHeader.createCell(10);
		cellSadHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.sad"));
		cellSadHeader.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellAngryHeader = rowPostsMostEngagedHeader.createCell(11);
		cellAngryHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.grr"));
		cellAngryHeader.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellLikesHeader = rowPostsMostEngagedHeader.createCell(12);
		cellLikesHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.like"));
		cellLikesHeader.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellCommentsHeader = rowPostsMostEngagedHeader.createCell(13);
		cellCommentsHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.comments"));
		cellCommentsHeader.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellSharesHeader = rowPostsMostEngagedHeader.createCell(14);
		cellSharesHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.shares"));
		cellSharesHeader.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellClicksHeader = rowPostsMostEngagedHeader.createCell(15);
		cellClicksHeader.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.clicks"));
		cellClicksHeader.setCellStyle(cellSecondaryHeaderNumber);

		rowSheetPostsMostEngaged++;
		for(FaceReportPostDTO faceReportPost : reportFacebookPostToPost.getPosts()) {
			
			if(!faceReportPost.getType().equals(FaceReportPostDTO.VIDEO_POSTED)) {
				
				Row rowPostMostEngaged = sheetPosts.createRow(rowSheetPostsMostEngaged);
				
				Cell cellDate = rowPostMostEngaged.createCell(0);
				cellDate.setCellValue(new SimpleDateFormat("MM/dd/yyyy").format(faceReportPost.getCreatedTime()));
				
				Cell cellTime = rowPostMostEngaged.createCell(1);
				cellTime.setCellValue(new SimpleDateFormat("HH:mm").format(faceReportPost.getCreatedTime()));
				
				Cell cellPost = rowPostMostEngaged.createCell(2);
				cellPost.setCellValue(faceReportPost.getMessage());
				
				Cell cellReach = rowPostMostEngaged.createCell(3);
				cellReach.setCellValue(faceReportPost.getReach());
				
				Cell cellOrganicReach = rowPostMostEngaged.createCell(4);
				cellOrganicReach.setCellValue(faceReportPost.getOrganicReach());
				
				Cell cellPaidReach = rowPostMostEngaged.createCell(5);
				cellPaidReach.setCellValue(faceReportPost.getPaidReach());
				
				Cell cellInteractions = rowPostMostEngaged.createCell(6);
				cellInteractions.setCellValue(faceReportPost.getInteractions());
				
				Cell cellLove = rowPostMostEngaged.createCell(7);
				cellLove.setCellValue(faceReportPost.getLove());
				
				Cell cellHaha = rowPostMostEngaged.createCell(8);
				cellHaha.setCellValue(faceReportPost.getHaha());
				
				Cell cellWow = rowPostMostEngaged.createCell(9);
				cellWow.setCellValue(faceReportPost.getWow());
				
				Cell cellSad = rowPostMostEngaged.createCell(10);
				cellSad.setCellValue(faceReportPost.getSorry());
				
				Cell cellAngry = rowPostMostEngaged.createCell(11);
				cellAngry.setCellValue(faceReportPost.getAnger());
				
				Cell cellLikes = rowPostMostEngaged.createCell(12);
				cellLikes.setCellValue(faceReportPost.getLikes());
				
				Cell cellComments = rowPostMostEngaged.createCell(13);
				cellComments.setCellValue(faceReportPost.getComments());
				
				Cell cellShares = rowPostMostEngaged.createCell(14);
				cellShares.setCellValue(faceReportPost.getShares());
				
				Cell cellClicks = rowPostMostEngaged.createCell(15);
				cellClicks.setCellValue(faceReportPost.getClicks());
				
				rowSheetPostsMostEngaged++;
			}
		}
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
		palette.setColorAtIndex(IndexedColors.GREY_25_PERCENT.index, (byte) 238, (byte) 238, (byte) 238);
		
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
