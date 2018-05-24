package br.com.opsocial.server.utils.reports.xls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import br.com.opsocial.client.entity.report.instagram.InstagramInteractionRankingDTO;
import br.com.opsocial.client.entity.report.instagram.ReportInstagramHistoricalDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;
import br.com.opsocial.ejb.entity.instagram.InstagramReportPost;
import br.com.opsocial.ejb.entity.instagram.InstagramSimpleDataDTO;
import br.com.opsocial.ejb.entity.instagram.InstagramTopPostTag;
import br.com.opsocial.server.utils.Calc;
import br.com.opsocial.server.utils.aws.S3Integration;
import br.com.opsocial.server.utils.reports.ReportInstagram;

public class ReportInstagramHistoricalXLS {

	private ReportInstagramHistoricalDTO reportInstagramHistorical;
	private Profile profile;

	private String reportsPath;
	private String fileName;
	private String formattedFileName;

	private HSSFWorkbook wb;
	private Sheet sheetHistorical;
	private Sheet sheetMetricsPerHour;

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
	private int rowSheetMetricsPerHourCount;

	private HashMap<String, Row> demographicRows;
	private HashMap<Integer, Row> postsThroughoutTimeRows;
	private HashMap<Integer, Row> likesThroughoutTimeRows;
	private HashMap<Integer, Row> commentsThroughoutTimeRows;
	
	public ReportInstagramHistoricalXLS(ReportInstagramHistoricalDTO reportInstagramHistorical, Profile profile) {
		this.reportInstagramHistorical = reportInstagramHistorical;
		this.profile = profile;
	}
	
	public void createXLS() {
		
		wb = new HSSFWorkbook();
		
		initiateDefautStyles();
		
		this.reportsPath = OpSocialBackApplication.GET_DIR_REPORTS_XLS;
		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".xls");
		
		sheetHistorical = wb.createSheet(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.historical.tabname"));
		sheetMetricsPerHour = wb.createSheet(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.hours"));
		
		headerDataRowHeight = 1.2F * sheetHistorical.getDefaultRowHeightInPoints();
		
		addMainHeader(sheetHistorical, OpSocialBackApplication.messageByLocaleService.
				getMessage("xls.ig.report.historical.header", new Object[]{profile.getScreenName()}), 4);
		
		rowSheetHistoricalCount = 2;
		
		addAccountSummary();
		addPostsThroughoutTime();
		addPostsDensityDay();
		addPostsDensityHour();
		addLikesStats();
		addCommentsStats();
		addAudiencesDemograph();
		addPostsWithMoreInteractions();
		addTopPostTags();
		
		sheetHistorical.setDefaultColumnWidth(25);
		sheetHistorical.setColumnWidth(0, 10000);
		
		addMainHeader(sheetMetricsPerHour, OpSocialBackApplication.messageByLocaleService.
				getMessage("xls.ig.report.historical.header", new Object[]{profile.getScreenName()}), 6);
		
		rowSheetMetricsPerHourCount = 2;
		
		addLikesPerHour();
		addCommentsPerHour();
		addInteractionsPerHour();
		
		sheetMetricsPerHour.setDefaultColumnWidth(25);
		sheetMetricsPerHour.setColumnWidth(0, 10000);
		
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
				getMessage("xls.ig.report.historical.doctitle", new Object[]{profile.getScreenName()});
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addInteractionsPerHour() {
		
		rowSheetMetricsPerHourCount += 2;
		
		Row rowInteractionsPerHourTitle = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);
		rowInteractionsPerHourTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellInteractionsPerHourTitle = rowInteractionsPerHourTitle.createCell(0);
		cellInteractionsPerHourTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totalinteractionsbytimeupper"));
		cellInteractionsPerHourTitle.setCellStyle(cellStyleHeaderData);
		
		sheetMetricsPerHour.addMergedRegion(new CellRangeAddress(rowSheetMetricsPerHourCount, rowSheetMetricsPerHourCount, 0, 4));
		
		rowSheetMetricsPerHourCount++;
		Row rowInteractionsPerHourTitleItems = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);
		rowInteractionsPerHourTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellInteractionsHourTitle = rowInteractionsPerHourTitleItems.createCell(0);
		cellInteractionsHourTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.time"));
		cellInteractionsHourTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellInteractionsTotalTitle = rowInteractionsPerHourTitleItems.createCell(1);
		cellInteractionsTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totalinteraction"));
		cellInteractionsTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetMetricsPerHourCount++;
		for(Map.Entry<Integer, Long> interactionsPerHour : reportInstagramHistorical.getInteractionsPerHour().entrySet()) {
			
			Row rowInteractionsPerHourValues = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);

			Cell cellInteractionsHourValue = rowInteractionsPerHourValues.createCell(0);
			cellInteractionsHourValue.setCellValue(new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, interactionsPerHour.getKey(), 0)) + " - " +
					new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, interactionsPerHour.getKey() + 1, 0)));
			
			Cell cellInteractionsTotalValue = rowInteractionsPerHourValues.createCell(1);
			cellInteractionsTotalValue.setCellValue(interactionsPerHour.getValue());

			rowSheetMetricsPerHourCount++;
		}
		
		int i = 1;
		for(InstagramInteractionRankingDTO instagramInteractionRanking : reportInstagramHistorical.getInstagramInteractionsRanking()) {

			if(i < 4) {
				
				rowSheetMetricsPerHourCount++;
				Row rowTimeMoreInteractions = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);
				rowTimeMoreInteractions.setHeightInPoints(headerDataRowHeight);
				
				Cell cellTimeMoreInteractionsTitle = rowTimeMoreInteractions.createCell(0);
				cellTimeMoreInteractionsTitle.setCellValue(i + OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.timeofinteraction"));
				cellTimeMoreInteractionsTitle.setCellStyle(cellStylePreAnalysis);
				
				String timeMoreInteractionsFrom = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, instagramInteractionRanking.getHour(), 0, 0));
				String timeMoreInteractionsUntil = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, instagramInteractionRanking.getHour() + 1, 0, 0));
				
				Cell cellTimeMoreInteractionsValue = rowTimeMoreInteractions.createCell(1);
				cellTimeMoreInteractionsValue.setCellValue(timeMoreInteractionsFrom + " - " + timeMoreInteractionsUntil);
				cellTimeMoreInteractionsValue.setCellStyle(cellStylePreAnalysisResult);
				
				Cell cellTimeMoreInteractionsPerc = rowTimeMoreInteractions.createCell(2);
				cellTimeMoreInteractionsPerc.setCellValue(instagramInteractionRanking.getPercentage().toString().replace(".", ",") + "%");
				cellTimeMoreInteractionsPerc.setCellStyle(cellStylePreAnalysisResult);
				
			} else {
				break;
			}
			
			i++;
		}
		
	}
	
	private void addCommentsPerHour() {
		
		rowSheetMetricsPerHourCount += 2;
		
		Row rowCommentsPerHourTitle = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);
		rowCommentsPerHourTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCommentsPerHourTitle = rowCommentsPerHourTitle.createCell(0);
		cellCommentsPerHourTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.commentsbytime"));
		cellCommentsPerHourTitle.setCellStyle(cellStyleHeaderData);
		
		sheetMetricsPerHour.addMergedRegion(new CellRangeAddress(rowSheetMetricsPerHourCount, rowSheetMetricsPerHourCount, 0, 4));
		
		rowSheetMetricsPerHourCount++;
		Row rowCommentsPerHourTitleItems = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);
		rowCommentsPerHourTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCommentsHourTitle = rowCommentsPerHourTitleItems.createCell(0);
		cellCommentsHourTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.time"));
		cellCommentsHourTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCommentsTotalTitle = rowCommentsPerHourTitleItems.createCell(1);
		cellCommentsTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.comments"));
		cellCommentsTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetMetricsPerHourCount++;
		for(Map.Entry<Integer, Long> commentsPerHour : reportInstagramHistorical.getCommentsPerHour().entrySet()) {
			
			Row rowCommentsPerHourValues = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);

			Cell cellCommentsHourValue = rowCommentsPerHourValues.createCell(0);
			cellCommentsHourValue.setCellValue(new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, commentsPerHour.getKey(), 0)) + " - " +
					new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, commentsPerHour.getKey() + 1, 0)));
			
			Cell cellCommentsTotalValue = rowCommentsPerHourValues.createCell(1);
			cellCommentsTotalValue.setCellValue(commentsPerHour.getValue());

			rowSheetMetricsPerHourCount++;
		}
		
		rowSheetMetricsPerHourCount++;
		Row rowTimeMoreComments = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);
		rowTimeMoreComments.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTimeMoreCommentsTitle = rowTimeMoreComments.createCell(0);
		cellTimeMoreCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.timemorecomments"));
		cellTimeMoreCommentsTitle.setCellStyle(cellStylePreAnalysis);
		
		String timeMoreCommentsFrom = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramHistorical.getTimeWithMoreComments(), 0, 0));
		String timeMoreCommentsUntil = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramHistorical.getTimeWithMoreComments() + 1, 0, 0));
		
		Cell cellTimeMoreCommentsValue = rowTimeMoreComments.createCell(1);
		cellTimeMoreCommentsValue.setCellValue(timeMoreCommentsFrom + " - " + timeMoreCommentsUntil);
		cellTimeMoreCommentsValue.setCellStyle(cellStylePreAnalysisResult);
		
		Cell cellTimeMoreCommentsPerc = rowTimeMoreComments.createCell(2);
		cellTimeMoreCommentsPerc.setCellValue(reportInstagramHistorical.getTimeWithMoreCommentsPerc() + "%");
		cellTimeMoreCommentsPerc.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetMetricsPerHourCount++;
		Row rowTimeLessComments = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);
		rowTimeLessComments.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTimeLessCommentsTitle = rowTimeLessComments.createCell(0);
		cellTimeLessCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.timelesscomments"));
		cellTimeLessCommentsTitle.setCellStyle(cellStylePreAnalysis);
		
		String timeLessCommentsFrom = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramHistorical.getTimeWithLessComments(), 0, 0));
		String timeLessCommentsUntil = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramHistorical.getTimeWithLessComments() + 1, 0, 0));
		
		Cell cellTimeLessCommentsValue = rowTimeLessComments.createCell(1);
		cellTimeLessCommentsValue.setCellValue(timeLessCommentsFrom + " - " + timeLessCommentsUntil);
		cellTimeLessCommentsValue.setCellStyle(cellStylePreAnalysisResult);
		
		Cell cellTimeLessCommentsPerc = rowTimeLessComments.createCell(2);
		cellTimeLessCommentsPerc.setCellValue(reportInstagramHistorical.getTimeWithLessCommentsPerc() + "%");
		cellTimeLessCommentsPerc.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addLikesPerHour() {
		
		Row rowLikesPerHourTitle = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);
		rowLikesPerHourTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellLikesPerHourTitle = rowLikesPerHourTitle.createCell(0);
		cellLikesPerHourTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.likesbytime"));
		cellLikesPerHourTitle.setCellStyle(cellStyleHeaderData);
		
		sheetMetricsPerHour.addMergedRegion(new CellRangeAddress(rowSheetMetricsPerHourCount, rowSheetMetricsPerHourCount, 0, 4));
		
		rowSheetMetricsPerHourCount++;
		Row rowLikesPerHourTitleItems = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);
		rowLikesPerHourTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellLikesHourTitle = rowLikesPerHourTitleItems.createCell(0);
		cellLikesHourTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.time"));
		cellLikesHourTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellLikesTotalTitle = rowLikesPerHourTitleItems.createCell(1);
		cellLikesTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.likes"));
		cellLikesTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetMetricsPerHourCount++;
		for(Map.Entry<Integer, Long> likesPerHour : reportInstagramHistorical.getLikesPerHour().entrySet()) {
			
			Row rowLikesPerHourValues = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);

			Cell cellLikesHourValue = rowLikesPerHourValues.createCell(0);
			cellLikesHourValue.setCellValue(new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, likesPerHour.getKey(), 0)) + " - " +
					new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, likesPerHour.getKey() + 1, 0)));
			
			Cell cellLikesTotalValue = rowLikesPerHourValues.createCell(1);
			cellLikesTotalValue.setCellValue(likesPerHour.getValue());

			rowSheetMetricsPerHourCount++;
		}
		
		rowSheetMetricsPerHourCount++;
		Row rowTimeMoreLikes = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);
		rowTimeMoreLikes.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTimeMoreLikesTitle = rowTimeMoreLikes.createCell(0);
		cellTimeMoreLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.timemorelikes"));
		cellTimeMoreLikesTitle.setCellStyle(cellStylePreAnalysis);
		
		String timeMoreLikesFrom = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramHistorical.getTimeWithMoreLikes(), 0, 0));
		String timeMoreLikesUntil = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramHistorical.getTimeWithMoreLikes() + 1, 0, 0));
		
		Cell cellTimeMoreLikesValue = rowTimeMoreLikes.createCell(1);
		cellTimeMoreLikesValue.setCellValue(timeMoreLikesFrom + " - " + timeMoreLikesUntil);
		cellTimeMoreLikesValue.setCellStyle(cellStylePreAnalysisResult);
		
		Cell cellTimeMoreLikesPerc = rowTimeMoreLikes.createCell(2);
		cellTimeMoreLikesPerc.setCellValue(reportInstagramHistorical.getTimeWithMoreLikesPerc() + "%");
		cellTimeMoreLikesPerc.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetMetricsPerHourCount++;
		Row rowTimeLessLikes = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);
		rowTimeLessLikes.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTimeLessLikesTitle = rowTimeLessLikes.createCell(0);
		cellTimeLessLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.timelesslikes"));
		cellTimeLessLikesTitle.setCellStyle(cellStylePreAnalysis);
		
		String timeLessLikesFrom = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramHistorical.getTimeWithLessLikes(), 0, 0));
		String timeLessLikesUntil = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramHistorical.getTimeWithLessLikes() + 1, 0, 0));
		
		Cell cellTimeLessLikesValue = rowTimeLessLikes.createCell(1);
		cellTimeLessLikesValue.setCellValue(timeLessLikesFrom + " - " + timeLessLikesUntil);
		cellTimeLessLikesValue.setCellStyle(cellStylePreAnalysisResult);
		
		Cell cellTimeLessLikesPerc = rowTimeLessLikes.createCell(2);
		cellTimeLessLikesPerc.setCellValue(reportInstagramHistorical.getTimeWithLessLikesPerc() + "%");
		cellTimeLessLikesPerc.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addTopPostTags() {
		
		rowSheetHistoricalCount++;
		
		Row rowTopPostTagsTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowTopPostTagsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTopPostTagsTitle = rowTopPostTagsTitle.createCell(0);
		cellTopPostTagsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.topposttags"));
		cellTopPostTagsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 4));
		
		rowSheetHistoricalCount++;
		Row rowTopPostTagsTitleItems = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowTopPostTagsTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTagNameTitle = rowTopPostTagsTitleItems.createCell(0);
		cellTagNameTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.hashtag"));
		cellTagNameTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellInteractionsTitle = rowTopPostTagsTitleItems.createCell(1);
		cellInteractionsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totalinteraction"));
		cellInteractionsTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellTagLikesTitle = rowTopPostTagsTitleItems.createCell(2);
		cellTagLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.likes"));
		cellTagLikesTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellTagCommentsTitle = rowTopPostTagsTitleItems.createCell(3);
		cellTagCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.comments"));
		cellTagCommentsTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		for(InstagramTopPostTag instagramTopPostTag : reportInstagramHistorical.getInstagramTopPostTags()) {
			
			Row rowTagValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellTagName = rowTagValues.createCell(0);
			cellTagName.setCellValue("#" + instagramTopPostTag.getTag().toUpperCase());
			
			Cell cellInteractions = rowTagValues.createCell(1);
			cellInteractions.setCellValue(instagramTopPostTag.getInteractions());
			
			Cell cellLikes = rowTagValues.createCell(2);
			cellLikes.setCellValue(instagramTopPostTag.getLikes());
			
			Cell cellComments = rowTagValues.createCell(3);
			cellComments.setCellValue(instagramTopPostTag.getComments());
			
			rowSheetHistoricalCount++;
		}
	}
	
	private void addPostsWithMoreInteractions() {
		
		rowSheetHistoricalCount += 2;
		
		Row rowPostsWithMoreLikesTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowPostsWithMoreLikesTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsWithMoreLikesTitle = rowPostsWithMoreLikesTitle.createCell(0);
		cellPostsWithMoreLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.mostinteractionspostsonperiod"));
		cellPostsWithMoreLikesTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 4));
		
		rowSheetHistoricalCount++;
		Row rowPostsWithMoreLikesTitleItems = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowPostsWithMoreLikesTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsLinkTitle = rowPostsWithMoreLikesTitleItems.createCell(0);
		cellPostsLinkTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.post"));
		cellPostsLinkTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellPostLikesTitle = rowPostsWithMoreLikesTitleItems.createCell(1);
		cellPostLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totallikes"));
		cellPostLikesTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellPostCommentsTitle = rowPostsWithMoreLikesTitleItems.createCell(2);
		cellPostCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totalcomments"));
		cellPostCommentsTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		for(InstagramReportPost instagramReportPost : reportInstagramHistorical.getPostsWithMoreInteractions()) {
			
			Row rowPostValues = sheetHistorical.createRow(rowSheetHistoricalCount);
			
			Hyperlink hyperLink = createHelper.createHyperlink(Hyperlink.LINK_URL);
    		hyperLink.setAddress(instagramReportPost.getLink());
		
			Cell cellProfileLink = rowPostValues.createCell(0);
			cellProfileLink.setCellValue(instagramReportPost.getLink());
			cellProfileLink.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hyperLink);
			cellProfileLink.setCellStyle(cellStyleHLink);
			
			Cell cellLikesTotal = rowPostValues.createCell(1);
			cellLikesTotal.setCellValue(instagramReportPost.getLikes());

			Cell cellCommentsTotal = rowPostValues.createCell(2);
			cellCommentsTotal.setCellValue(instagramReportPost.getComments());
			
			rowSheetHistoricalCount++;
		}
	}
	
	private void addCommentsStats() {
		
		rowSheetHistoricalCount++;
		
		Row rowCommentsStatsTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowCommentsStatsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCommentsStatsTitle = rowCommentsStatsTitle.createCell(0);
		cellCommentsStatsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.commentsbymonth"));
		cellCommentsStatsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 4));
		
		rowSheetHistoricalCount++;
		Row rowCommentsStatsSubTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowCommentsStatsSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCommentsStatsMonthTitle = rowCommentsStatsSubTitle.createCell(0);
		cellCommentsStatsMonthTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.month"));
		cellCommentsStatsMonthTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCommentsStatsYearFirstTitle = rowCommentsStatsSubTitle.createCell(1);
		cellCommentsStatsYearFirstTitle.setCellValue(reportInstagramHistorical.getInstagramPostsStatsYearFirst().getYear());
		cellCommentsStatsYearFirstTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellCommentsStatsYearSecondTitle = rowCommentsStatsSubTitle.createCell(2);
		cellCommentsStatsYearSecondTitle.setCellValue(reportInstagramHistorical.getInstagramPostsStatsYearSecond().getYear());
		cellCommentsStatsYearSecondTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellCommentsStatsYearThirdTitle = rowCommentsStatsSubTitle.createCell(3);
		cellCommentsStatsYearThirdTitle.setCellValue(reportInstagramHistorical.getInstagramPostsStatsYearThird().getYear());
		cellCommentsStatsYearThirdTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		createCommentsThroughoutTimeRows();
		
		rowSheetHistoricalCount++;
		for(InstagramSimpleDataDTO instagramSimpleData : reportInstagramHistorical.getInstagramPostsStatsYearFirst().getCommentsPerMonthSimpleData()) {
			
			Cell cellInstaPostsStatsYearFirstValue = commentsThroughoutTimeRows.get(Integer.valueOf(instagramSimpleData.getKey().toString())).createCell(1);
			cellInstaPostsStatsYearFirstValue.setCellValue(Long.valueOf(instagramSimpleData.getValue().toString()));
		}
			
		for(InstagramSimpleDataDTO instagramSimpleData : reportInstagramHistorical.getInstagramPostsStatsYearSecond().getCommentsPerMonthSimpleData()) {
			
			Cell cellInstaPostsStatsYearSecondValue = commentsThroughoutTimeRows.get(Integer.valueOf(instagramSimpleData.getKey().toString())).createCell(2);
			cellInstaPostsStatsYearSecondValue.setCellValue(Long.valueOf(instagramSimpleData.getValue().toString()));
		}
			
		for(InstagramSimpleDataDTO instagramSimpleData : reportInstagramHistorical.getInstagramPostsStatsYearThird().getCommentsPerMonthSimpleData()) {
			
			Cell cellInstaPostsStatsYearThirdValue = commentsThroughoutTimeRows.get(Integer.valueOf(instagramSimpleData.getKey().toString())).createCell(3);
			cellInstaPostsStatsYearThirdValue.setCellValue(Long.valueOf(instagramSimpleData.getValue().toString()));
		}
		
		rowSheetHistoricalCount++;
		Row rowTotalComments = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowTotalComments.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalCommentsTitle = rowTotalComments.createCell(0);
		cellTotalCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totalcommentscolon"));
		cellTotalCommentsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalCommentsValue = rowTotalComments.createCell(1);
		cellTotalCommentsValue.setCellValue(reportInstagramHistorical.getComments());
		cellTotalCommentsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowAverageComments = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowAverageComments.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAverageCommentsTitle = rowAverageComments.createCell(0);
		cellAverageCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.commentsbypostaverage"));
		cellAverageCommentsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellAverageCommentsValue = rowAverageComments.createCell(1);
		cellAverageCommentsValue.setCellValue(reportInstagramHistorical.getCommentsAverage());
		cellAverageCommentsValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addLikesStats() {
		
		rowSheetHistoricalCount++;
		
		Row rowLikesStatsTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowLikesStatsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellLikesStatsTitle = rowLikesStatsTitle.createCell(0);
		cellLikesStatsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.likesbymonth"));
		cellLikesStatsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 4));
		
		rowSheetHistoricalCount++;
		Row rowLikesStatsSubTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowLikesStatsSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellLikesStatsMonthTitle = rowLikesStatsSubTitle.createCell(0);
		cellLikesStatsMonthTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.month"));
		cellLikesStatsMonthTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellLikesStatsYearFirstTitle = rowLikesStatsSubTitle.createCell(1);
		cellLikesStatsYearFirstTitle.setCellValue(reportInstagramHistorical.getInstagramPostsStatsYearFirst().getYear());
		cellLikesStatsYearFirstTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellLikesStatsYearSecondTitle = rowLikesStatsSubTitle.createCell(2);
		cellLikesStatsYearSecondTitle.setCellValue(reportInstagramHistorical.getInstagramPostsStatsYearSecond().getYear());
		cellLikesStatsYearSecondTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellLikesStatsYearThirdTitle = rowLikesStatsSubTitle.createCell(3);
		cellLikesStatsYearThirdTitle.setCellValue(reportInstagramHistorical.getInstagramPostsStatsYearThird().getYear());
		cellLikesStatsYearThirdTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		createLikesThroughoutTimeRows();
		
		rowSheetHistoricalCount++;
				
		for(InstagramSimpleDataDTO instagramSimpleData : reportInstagramHistorical.getInstagramPostsStatsYearFirst().getLikesPerMonthSimpleData()) {
			
			Cell cellInstaPostsStatsYearFirstValue = likesThroughoutTimeRows.get(Integer.valueOf(instagramSimpleData.getKey().toString())).createCell(1);
			cellInstaPostsStatsYearFirstValue.setCellValue(Long.valueOf(instagramSimpleData.getValue().toString()));
		}
			
		for(InstagramSimpleDataDTO instagramSimpleData : reportInstagramHistorical.getInstagramPostsStatsYearSecond().getLikesPerMonthSimpleData()) {
			
			Cell cellInstaPostsStatsYearSecondValue = likesThroughoutTimeRows.get(Integer.valueOf(instagramSimpleData.getKey().toString())).createCell(2);
			cellInstaPostsStatsYearSecondValue.setCellValue(Long.valueOf(instagramSimpleData.getValue().toString()));
		}
			
		for(InstagramSimpleDataDTO instagramSimpleData : reportInstagramHistorical.getInstagramPostsStatsYearThird().getLikesPerMonthSimpleData()) {
			
			Cell cellInstaPostsStatsYearThirdValue = likesThroughoutTimeRows.get(Integer.valueOf(instagramSimpleData.getKey().toString())).createCell(3);
			cellInstaPostsStatsYearThirdValue.setCellValue(Long.valueOf(instagramSimpleData.getValue().toString()));
		}
		
		Row rowTotalLikes = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowTotalLikes.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalLikesTitle = rowTotalLikes.createCell(0);
		cellTotalLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totallikescolon"));
		cellTotalLikesTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalLikesValue = rowTotalLikes.createCell(1);
		cellTotalLikesValue.setCellValue(reportInstagramHistorical.getLikes());
		cellTotalLikesValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowAverageLikes = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowAverageLikes.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAverageLikesTitle = rowAverageLikes.createCell(0);
		cellAverageLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.postlikesaverage"));
		cellAverageLikesTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellAverageLikesValue = rowAverageLikes.createCell(1);
		cellAverageLikesValue.setCellValue(reportInstagramHistorical.getLikesAverage());
		cellAverageLikesValue.setCellStyle(cellStylePreAnalysisResult);

		rowSheetHistoricalCount++;
	}
	
	private void addPostsDensityHour() {
		
		rowSheetHistoricalCount++;
		
		Row rowPostsDensityHourTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowPostsDensityHourTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsDensityHourTitle = rowPostsDensityHourTitle.createCell(0);
		cellPostsDensityHourTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.postdensitybytime"));
		cellPostsDensityHourTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 4));
		
		rowSheetHistoricalCount++;
		Row rowPostsDensityHourSubTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowPostsDensityHourSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostDensityHourTitle = rowPostsDensityHourSubTitle.createCell(0);
		cellPostDensityHourTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.time"));
		cellPostDensityHourTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellPostsDensityHourTotalTitle = rowPostsDensityHourSubTitle.createCell(1);
		cellPostsDensityHourTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totalposts"));
		cellPostsDensityHourTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		for(InstagramSimpleDataDTO postsPerHour : reportInstagramHistorical.getPostsPerHourSimpleData()) {

			Row rowPostsDensityHourValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellPostsDensityHour = rowPostsDensityHourValues.createCell(0);
			cellPostsDensityHour.setCellValue(new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, Integer.valueOf(postsPerHour.getKey().toString()), 0)) + " - " +
					new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, Integer.valueOf(postsPerHour.getKey().toString()) + 1, 0)));
			
			Cell cellPostsDensityHourValue = rowPostsDensityHourValues.createCell(1);
			cellPostsDensityHourValue.setCellValue(postsPerHour.getValue().toString());
			
			rowSheetHistoricalCount++;
		}
	}
	
	private void addPostsDensityDay() {
		
		Row rowPostsDensityDayTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowPostsDensityDayTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsDensityDayTitle = rowPostsDensityDayTitle.createCell(0);
		cellPostsDensityDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.postsdensityweekday"));
		cellPostsDensityDayTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 4));
		
		rowSheetHistoricalCount++;
		Row rowPostsDensityDaySubTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowPostsDensityDaySubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsDensityWeekDayTitle = rowPostsDensityDaySubTitle.createCell(0);
		cellPostsDensityWeekDayTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.weekday"));
		cellPostsDensityWeekDayTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellPostsDensityWeekDayTotalTitle = rowPostsDensityDaySubTitle.createCell(1);
		cellPostsDensityWeekDayTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totalposts"));
		cellPostsDensityWeekDayTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetHistoricalCount++;
		for(InstagramSimpleDataDTO postsPerWeekDay : reportInstagramHistorical.getPostsPerWeekDaySimpleData()) {

			Row rowPostsDensityDayValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellPostsDensityWeekDayValue = rowPostsDensityDayValues.createCell(0);
			cellPostsDensityWeekDayValue.setCellValue(OpSocialBackApplication.utilFunctionsService.getFormattedWeekDayShort(Integer.valueOf(postsPerWeekDay.getKey().toString())));
			
			Cell cellPostsDensityDayValue = rowPostsDensityDayValues.createCell(1);
			cellPostsDensityDayValue.setCellValue(postsPerWeekDay.getValue().toString());
			
			rowSheetHistoricalCount++;
		}
	}
	
	private void addDemographicGenderValue(String ageGroup, Long value, int cellIndex) {
		
		Cell cellValue = demographicRows.get(ageGroup).createCell(cellIndex);
		cellValue.setCellValue(value);
	}
	
	private void addAudiencesDemograph() {
		
		rowSheetHistoricalCount += 2;
		Row rowStatsTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowStatsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellStatsTitle = rowStatsTitle.createCell(0);
		cellStatsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.audiencesdemograph"));
		cellStatsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 4));
		
		rowSheetHistoricalCount++;
		Row rowAgeGroupUsersTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowAgeGroupUsersTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAgeGroupUsersTitle = rowAgeGroupUsersTitle.createCell(0);
		cellAgeGroupUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.agegroup"));
		cellAgeGroupUsersTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellMaleUsersTitle = rowAgeGroupUsersTitle.createCell(1);
		cellMaleUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.male"));
		cellMaleUsersTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellFemaleUsersTitle = rowAgeGroupUsersTitle.createCell(2);
		cellFemaleUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.female"));
		cellFemaleUsersTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		createDemographicRows();
		
		for (InstagramReport instagramReport : reportInstagramHistorical.getAudiencesDemograph()) {
			
			if(instagramReport.getProperty().equals(ReportInstagram.AUDIENCE_AGE_MEN)) {
				addDemographicGenderValue(instagramReport.getLabel(), instagramReport.getValue(), 1);
			} else if(instagramReport.getProperty().equals(ReportInstagram.AUDIENCE_AGE_WOMEN)) {
				addDemographicGenderValue(instagramReport.getLabel(), instagramReport.getValue(), 2);
			}
		}
		
		rowSheetHistoricalCount += 2;
		Row rowMajorAgeGroupUsers = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowMajorAgeGroupUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMajorAgeGroupUsersTitle = rowMajorAgeGroupUsers.createCell(0);
		cellMajorAgeGroupUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.majoragegroupuserstitle"));
		cellMajorAgeGroupUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMajorAgeGroupUsersValue = rowMajorAgeGroupUsers.createCell(1);
		cellMajorAgeGroupUsersValue.setCellValue(reportInstagramHistorical.getAudiencesAveragePublicAge().getKey().toString() + 
				" / " + Calc.calcPercentageNormal(Double.valueOf(reportInstagramHistorical.getAudiencesAveragePublicAge().getValue().toString()), 
							reportInstagramHistorical.getFollowers().doubleValue(), 0).intValue() + "%");
		cellMajorAgeGroupUsersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowMenUsers = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowMenUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMenUsersTitle = rowMenUsers.createCell(0);
		cellMenUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.malecolon"));
		cellMenUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMenUsersValue = rowMenUsers.createCell(1);
		cellMenUsersValue.setCellValue(Calc.calcPercentageNormal(Double.valueOf(reportInstagramHistorical.getAudiencesAveragePublicGender().get(0).getValue().toString()), 
							reportInstagramHistorical.getFollowers().doubleValue(), 0).intValue() + "%");
		cellMenUsersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetHistoricalCount++;
		Row rowWomenUsers = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowWomenUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellWomenUsersTitle = rowWomenUsers.createCell(0);
		cellWomenUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.femalecolon"));
		cellWomenUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellWomenUsersValue = rowWomenUsers.createCell(1);
		cellWomenUsersValue.setCellValue(Calc.calcPercentageNormal(Double.valueOf(reportInstagramHistorical.getAudiencesAveragePublicGender().get(1).getValue().toString()), 
				reportInstagramHistorical.getFollowers().doubleValue(), 0).intValue() + "%");
		cellWomenUsersValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void createDemographicRows() {
		
		demographicRows = new HashMap<String, Row>();
		
		rowSheetHistoricalCount++;
		Row rowThirteenSeventeenValues = sheetHistorical.createRow(rowSheetHistoricalCount);
		
		Cell cellThirteenSeventeenTitle = rowThirteenSeventeenValues.createCell(0);
		cellThirteenSeventeenTitle.setCellValue("13-17");
		
		rowSheetHistoricalCount++;
		Row rowEighteenTwentyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);
		
		Cell cellEighteenTwentyFourTitle = rowEighteenTwentyFourValues.createCell(0);
		cellEighteenTwentyFourTitle.setCellValue("18-24");
		
		rowSheetHistoricalCount++;
		Row rowTwentyFiveThirtyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);
		
		Cell cellTwentyFiveThirtyFourTitle = rowTwentyFiveThirtyFourValues.createCell(0);
		cellTwentyFiveThirtyFourTitle.setCellValue("25-34");
		
		rowSheetHistoricalCount++;
		Row rowThirtyFiveFortyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);
		
		Cell cellThirtyFiveFortyFourTitle = rowThirtyFiveFortyFourValues.createCell(0);
		cellThirtyFiveFortyFourTitle.setCellValue("35-44");
		
		rowSheetHistoricalCount++;
		Row rowFortyFiveFiftyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);
		
		Cell cellFortyFiveFiftyFourTitle = rowFortyFiveFiftyFourValues.createCell(0);
		cellFortyFiveFiftyFourTitle.setCellValue("45-54");
		
		rowSheetHistoricalCount++;
		Row rowFiftyFiveSixtyFourValues = sheetHistorical.createRow(rowSheetHistoricalCount);
		
		Cell cellFiftyFiveSixtyFourTitle = rowFiftyFiveSixtyFourValues.createCell(0);
		cellFiftyFiveSixtyFourTitle.setCellValue("55-64");
		
		rowSheetHistoricalCount++;
		Row rowSixtyFourPlusValues = sheetHistorical.createRow(rowSheetHistoricalCount);
		
		Cell cellSixtyFivePlusTitle = rowSixtyFourPlusValues.createCell(0);
		cellSixtyFivePlusTitle.setCellValue("65+");
		
		demographicRows.put("13-17", rowThirteenSeventeenValues);
		demographicRows.put("18-24", rowEighteenTwentyFourValues);
		demographicRows.put("25-34", rowTwentyFiveThirtyFourValues);
		demographicRows.put("35-44", rowThirtyFiveFortyFourValues);
		demographicRows.put("45-54", rowFortyFiveFiftyFourValues);
		demographicRows.put("55-64", rowFiftyFiveSixtyFourValues);
		demographicRows.put("65+", rowSixtyFourPlusValues);
	}
	
	private void createCommentsThroughoutTimeRows() {
		
		commentsThroughoutTimeRows = new HashMap<Integer, Row>();
		
		rowSheetHistoricalCount++;
		for (int i = 1; i <= 12; i++) {
			
			Row rowValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellMonthValue = rowValues.createCell(0);
			cellMonthValue.setCellValue(OpSocialBackApplication.utilFunctionsService.getFormattedMonth(i - 1));
			
			commentsThroughoutTimeRows.put(i, rowValues);
			rowSheetHistoricalCount++;
		}
	}
	
	private void createLikesThroughoutTimeRows() {
		
		likesThroughoutTimeRows = new HashMap<Integer, Row>();
		
		rowSheetHistoricalCount++;
		for (int i = 1; i <= 12; i++) {
			
			Row rowValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellMonthValue = rowValues.createCell(0);
			cellMonthValue.setCellValue(OpSocialBackApplication.utilFunctionsService.getFormattedMonth(i - 1));
			
			likesThroughoutTimeRows.put(i, rowValues);
			rowSheetHistoricalCount++;
		}
	}
	
	private void createPostsThroughoutTimeRows() {
		
		postsThroughoutTimeRows = new HashMap<Integer, Row>();
		
		rowSheetHistoricalCount++;
		for (int i = 1; i <= 12; i++) {
			
			Row rowValues = sheetHistorical.createRow(rowSheetHistoricalCount);

			Cell cellMonthValue = rowValues.createCell(0);
			cellMonthValue.setCellValue(OpSocialBackApplication.utilFunctionsService.getFormattedMonth(i - 1));
			
			postsThroughoutTimeRows.put(i, rowValues);
			rowSheetHistoricalCount++;
		}
	}
	
	private void addPostsThroughoutTime() {
		
		rowSheetHistoricalCount += 2;
		
		Row rowPostsThroughoutTimeTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowPostsThroughoutTimeTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsThroughoutTimeTitle = rowPostsThroughoutTimeTitle.createCell(0);
		cellPostsThroughoutTimeTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.postsmonthyear"));
		cellPostsThroughoutTimeTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 4));
		
		rowSheetHistoricalCount++;
		Row rowPostsThroughoutTimeSubTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowPostsThroughoutTimeSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellInstaPostsStatsMonthTitle = rowPostsThroughoutTimeSubTitle.createCell(0);
		cellInstaPostsStatsMonthTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.month"));
		cellInstaPostsStatsMonthTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellInstaPostsStatsYearFirstTitle = rowPostsThroughoutTimeSubTitle.createCell(1);
		cellInstaPostsStatsYearFirstTitle.setCellValue(reportInstagramHistorical.getInstagramPostsStatsYearFirst().getYear());
		cellInstaPostsStatsYearFirstTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellInstaPostsStatsYearSecondTitle = rowPostsThroughoutTimeSubTitle.createCell(2);
		cellInstaPostsStatsYearSecondTitle.setCellValue(reportInstagramHistorical.getInstagramPostsStatsYearSecond().getYear());
		cellInstaPostsStatsYearSecondTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellInstaPostsStatsYearThirdTitle = rowPostsThroughoutTimeSubTitle.createCell(3);
		cellInstaPostsStatsYearThirdTitle.setCellValue(reportInstagramHistorical.getInstagramPostsStatsYearThird().getYear());
		cellInstaPostsStatsYearThirdTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellInstaPostsStatsTotalTitle = rowPostsThroughoutTimeSubTitle.createCell(4);
		cellInstaPostsStatsTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.total"));
		cellInstaPostsStatsTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		createPostsThroughoutTimeRows();
		
		rowSheetHistoricalCount++;
		for (int i = 1; i <= 12; i++) {
			
			Long instaPostsYearFirstValue = 0L;
			Long instaPostsYearSecondValue = 0L;
			Long instaPostsYearThirdValue = 0L;	
					
			for(InstagramSimpleDataDTO instagramSimpleData : reportInstagramHistorical.getInstagramPostsStatsYearFirst().getPostsPerMonthSimpleData()) {
				
				if(Integer.valueOf(instagramSimpleData.getKey().toString()).equals(i)) {
					
					instaPostsYearFirstValue = Long.valueOf(instagramSimpleData.getValue().toString());
					
					Cell cellInstaPostsStatsYearFirstValue = postsThroughoutTimeRows.get(i).createCell(1);
					cellInstaPostsStatsYearFirstValue.setCellValue(instaPostsYearFirstValue);
					break;
				}
			}
			
			for(InstagramSimpleDataDTO instagramSimpleData : reportInstagramHistorical.getInstagramPostsStatsYearSecond().getPostsPerMonthSimpleData()) {
				
				if(Integer.valueOf(instagramSimpleData.getKey().toString()).equals(i)) {
					
					instaPostsYearSecondValue = Long.valueOf(instagramSimpleData.getValue().toString());
					
					Cell cellInstaPostsStatsYearSecondValue = postsThroughoutTimeRows.get(i).createCell(2);
					cellInstaPostsStatsYearSecondValue.setCellValue(instaPostsYearSecondValue);
					break;
				}
			}
			
			for(InstagramSimpleDataDTO instagramSimpleData : reportInstagramHistorical.getInstagramPostsStatsYearThird().getPostsPerMonthSimpleData()) {
				
				if(Integer.valueOf(instagramSimpleData.getKey().toString()).equals(i)) {
					
					instaPostsYearThirdValue = Long.valueOf(instagramSimpleData.getValue().toString());
					
					Cell cellInstaPostsStatsYearThirdValue = postsThroughoutTimeRows.get(i).createCell(3);
					cellInstaPostsStatsYearThirdValue.setCellValue(instaPostsYearThirdValue);
					break;
				}
			}
			
			Long instaPostsStatsTotal = instaPostsYearFirstValue + instaPostsYearSecondValue + instaPostsYearThirdValue;
			
			Cell cellInstaPostsStatsTotalValue = postsThroughoutTimeRows.get(i).createCell(4);
			cellInstaPostsStatsTotalValue.setCellValue(instaPostsStatsTotal);
		}
		
	}
	
	private void addAccountSummary() {
		
		Row rowAccountSummaryTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowAccountSummaryTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAccountSummaryTitle = rowAccountSummaryTitle.createCell(0);
		cellAccountSummaryTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.accountsummary"));
		cellAccountSummaryTitle.setCellStyle(cellStyleHeaderData);
		
		sheetHistorical.addMergedRegion(new CellRangeAddress(rowSheetHistoricalCount, rowSheetHistoricalCount, 0, 4));
		
		rowSheetHistoricalCount++;
		Row rowPostsActionsTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowPostsActionsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsActionsTitle = rowPostsActionsTitle.createCell(0);
		cellPostsActionsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totalposts"));
		cellPostsActionsTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellPostsActionsValue = rowPostsActionsTitle.createCell(1);
		cellPostsActionsValue.setCellValue(reportInstagramHistorical.getPosts());
		
		rowSheetHistoricalCount++;
		Row rowCommentsTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowCommentsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCommentsTitle = rowCommentsTitle.createCell(0);
		cellCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.comments"));
		cellCommentsTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCommentsValue = rowCommentsTitle.createCell(1);
		cellCommentsValue.setCellValue(reportInstagramHistorical.getComments());
		
		rowSheetHistoricalCount++;
		Row rowLikesTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowLikesTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellLikesTitle = rowLikesTitle.createCell(0);
		cellLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.like"));
		cellLikesTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellLikesValue = rowLikesTitle.createCell(1);
		cellLikesValue.setCellValue(reportInstagramHistorical.getLikes());
		
		rowSheetHistoricalCount++;
		Row rowFollowersTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowFollowersTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFollowersTitle = rowFollowersTitle.createCell(0);
		cellFollowersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.followers"));
		cellFollowersTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellFollowersValue = rowFollowersTitle.createCell(1);
		cellFollowersValue.setCellValue(reportInstagramHistorical.getFollowers());
		
		rowSheetHistoricalCount++;
		Row rowFollowingsTitle = sheetHistorical.createRow(rowSheetHistoricalCount);
		rowFollowingsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFollowingsTitle = rowFollowingsTitle.createCell(0);
		cellFollowingsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.following"));
		cellFollowingsTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellFollowingsValue = rowFollowingsTitle.createCell(1);
		cellFollowingsValue.setCellValue(profile.getFollows() != null ? profile.getFollows() : 0);	
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
