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
import org.apache.commons.lang3.text.WordUtils;
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
import br.com.opsocial.client.entity.report.instagram.ReportInstagramOverviewDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.instagram.InstagramEngagedFollower;
import br.com.opsocial.ejb.entity.instagram.InstagramFollowerTrend;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;
import br.com.opsocial.ejb.entity.instagram.InstagramReportPost;
import br.com.opsocial.ejb.entity.instagram.InstagramTopPostTag;
import br.com.opsocial.server.utils.Calc;
import br.com.opsocial.server.utils.aws.S3Integration;
import br.com.opsocial.server.utils.reports.ReportInstagram;

public class ReportInstagramOverviewXLS {

	private ReportInstagramOverviewDTO reportInstagramOverview;
	private Profile profile;

	private String reportsPath;
	private String fileName;
	private String formattedFileName;

	private HSSFWorkbook wb;
	private Sheet sheetOverview;
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

	private String startDate;
	private String endDate;

	private int rowSheetOverviewCount;
	private int rowSheetMetricsPerHourCount;
	
	private HashMap<String, Row> demographicRows;
	
	public ReportInstagramOverviewXLS(ReportInstagramOverviewDTO reportInstagramOverview, Profile profile) {
		this.reportInstagramOverview = reportInstagramOverview;
		this.profile = profile;
	}
	
	public void createXLS() {

		wb = new HSSFWorkbook();

		initiateDefautStyles();

		this.reportsPath = OpSocialBackApplication.GET_DIR_REPORTS_XLS;
		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".xls");

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat dateFormatToFileName = new SimpleDateFormat("MM-dd-yyyy");

		startDate = dateFormat.format(new Date(reportInstagramOverview.getDateFrom() * 1000L));
		endDate = dateFormat.format(new Date(reportInstagramOverview.getDateUntil() * 1000L));

		sheetOverview = wb.createSheet(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.tabname"));
		sheetMetricsPerHour = wb.createSheet(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.hours"));

		headerDataRowHeight = 1.2F * sheetOverview.getDefaultRowHeightInPoints();

		addMainHeader(sheetOverview, OpSocialBackApplication.messageByLocaleService.
				getMessage("xls.ig.report.header", new Object[]{profile.getScreenName(), startDate, endDate}), 4);

		rowSheetOverviewCount = 2;
		
		addBasicPostsStats();
		addFollowersStats();
		addAudiencesDemograph();
		addCountriesStats();
		addCitiesStats();
		addFollowersOnline();
		addLikesStats();
		addCommentsStats();
		addPostsWithMoreInteractions();
		addTopPostTags();

		sheetOverview.setDefaultColumnWidth(25);
		sheetOverview.setColumnWidth(0, 10000);
		
		addMainHeader(sheetMetricsPerHour, OpSocialBackApplication.messageByLocaleService.
				getMessage("xls.ig.report.header", new Object[]{profile.getScreenName(), startDate, endDate}), 4);
		
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
				getMessage("xls.ig.report.doctitle", new Object[]{
						profile.getScreenName(), 
						dateFormatToFileName.format(new Date(reportInstagramOverview.getDateFrom() * 1000L)), 
						dateFormatToFileName.format(new Date(reportInstagramOverview.getDateUntil() * 1000L))
			});

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
		cellInteractionsPerHourTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.interactionsperhour"));
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
		for(Map.Entry<Integer, Long> interactionsPerHour : reportInstagramOverview.getInteractionsPerHour().entrySet()) {
			
			Row rowInteractionsPerHourValues = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);

			Cell cellInteractionsHourValue = rowInteractionsPerHourValues.createCell(0);
			cellInteractionsHourValue.setCellValue(new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, interactionsPerHour.getKey(), 0)) + " - " +
					new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, interactionsPerHour.getKey() + 1, 0)));
			
			Cell cellInteractionsTotalValue = rowInteractionsPerHourValues.createCell(1);
			cellInteractionsTotalValue.setCellValue(interactionsPerHour.getValue());

			rowSheetMetricsPerHourCount++;
		}
		
		int i = 1;
		for(InstagramInteractionRankingDTO instagramInteractionRanking : reportInstagramOverview.getInstagramInteractionsRanking()) {

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
		for(Map.Entry<Integer, Long> commentsPerHour : reportInstagramOverview.getCommentsPerHour().entrySet()) {
			
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
		
		String timeMoreCommentsFrom = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramOverview.getTimeWithMoreComments(), 0, 0));
		String timeMoreCommentsUntil = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramOverview.getTimeWithMoreComments() + 1, 0, 0));
		
		Cell cellTimeMoreCommentsValue = rowTimeMoreComments.createCell(1);
		cellTimeMoreCommentsValue.setCellValue(timeMoreCommentsFrom + " - " + timeMoreCommentsUntil);
		cellTimeMoreCommentsValue.setCellStyle(cellStylePreAnalysisResult);
		
		Cell cellTimeMoreCommentsPerc = rowTimeMoreComments.createCell(2);
		cellTimeMoreCommentsPerc.setCellValue(reportInstagramOverview.getTimeWithMoreCommentsPerc() + "%");
		cellTimeMoreCommentsPerc.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetMetricsPerHourCount++;
		Row rowTimeLessComments = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);
		rowTimeLessComments.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTimeLessCommentsTitle = rowTimeLessComments.createCell(0);
		cellTimeLessCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.timelesscomments"));
		cellTimeLessCommentsTitle.setCellStyle(cellStylePreAnalysis);
		
		String timeLessCommentsFrom = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramOverview.getTimeWithLessComments(), 0, 0));
		String timeLessCommentsUntil = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramOverview.getTimeWithLessComments() + 1, 0, 0));
		
		Cell cellTimeLessCommentsValue = rowTimeLessComments.createCell(1);
		cellTimeLessCommentsValue.setCellValue(timeLessCommentsFrom + " - " + timeLessCommentsUntil);
		cellTimeLessCommentsValue.setCellStyle(cellStylePreAnalysisResult);
		
		Cell cellTimeLessCommentsPerc = rowTimeLessComments.createCell(2);
		cellTimeLessCommentsPerc.setCellValue(reportInstagramOverview.getTimeWithLessCommentsPerc() + "%");
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
		for(Map.Entry<Integer, Long> likesPerHour : reportInstagramOverview.getLikesPerHour().entrySet()) {
			
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
		
		String timeMoreLikesFrom = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramOverview.getTimeWithMoreLikes(), 0, 0));
		String timeMoreLikesUntil = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramOverview.getTimeWithMoreLikes() + 1, 0, 0));
		
		Cell cellTimeMoreLikesValue = rowTimeMoreLikes.createCell(1);
		cellTimeMoreLikesValue.setCellValue(timeMoreLikesFrom + " - " + timeMoreLikesUntil);
		cellTimeMoreLikesValue.setCellStyle(cellStylePreAnalysisResult);
		
		Cell cellTimeMoreLikesPerc = rowTimeMoreLikes.createCell(2);
		cellTimeMoreLikesPerc.setCellValue(reportInstagramOverview.getTimeWithMoreLikesPerc() + "%");
		cellTimeMoreLikesPerc.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetMetricsPerHourCount++;
		Row rowTimeLessLikes = sheetMetricsPerHour.createRow(rowSheetMetricsPerHourCount);
		rowTimeLessLikes.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTimeLessLikesTitle = rowTimeLessLikes.createCell(0);
		cellTimeLessLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.timelesslikes"));
		cellTimeLessLikesTitle.setCellStyle(cellStylePreAnalysis);
		
		String timeLessLikesFrom = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramOverview.getTimeWithLessLikes(), 0, 0));
		String timeLessLikesUntil = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportInstagramOverview.getTimeWithLessLikes() + 1, 0, 0));
		
		Cell cellTimeLessLikesValue = rowTimeLessLikes.createCell(1);
		cellTimeLessLikesValue.setCellValue(timeLessLikesFrom + " - " + timeLessLikesUntil);
		cellTimeLessLikesValue.setCellStyle(cellStylePreAnalysisResult);
		
		Cell cellTimeLessLikesPerc = rowTimeLessLikes.createCell(2);
		cellTimeLessLikesPerc.setCellValue(reportInstagramOverview.getTimeWithLessLikesPerc() + "%");
		cellTimeLessLikesPerc.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addTopPostTags() {
		
		rowSheetOverviewCount++;
		
		Row rowTopPostTagsTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowTopPostTagsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTopPostTagsTitle = rowTopPostTagsTitle.createCell(0);
		cellTopPostTagsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.topposttags"));
		cellTopPostTagsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowTopPostTagsTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
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
		
		rowSheetOverviewCount++;
		for(InstagramTopPostTag instagramTopPostTag : reportInstagramOverview.getInstagramTopPostTags()) {
			
			Row rowTagValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellTagName = rowTagValues.createCell(0);
			cellTagName.setCellValue("#" + instagramTopPostTag.getTag().toUpperCase());
			
			Cell cellInteractions = rowTagValues.createCell(1);
			cellInteractions.setCellValue(instagramTopPostTag.getInteractions());
			
			Cell cellLikes = rowTagValues.createCell(2);
			cellLikes.setCellValue(instagramTopPostTag.getLikes());
			
			Cell cellComments = rowTagValues.createCell(3);
			cellComments.setCellValue(instagramTopPostTag.getComments());
			
//			Cell cellInteractionsPerc = rowTagValues.createCell(4);
//			cellInteractionsPerc.setCellValue(instagramTopPostTag.getInteractionsPercentage().toString().replace(".", ",") + "%");
//			cellInteractionsPerc.setCellStyle(cellDataPercentage);
			
			rowSheetOverviewCount++;
		}
	}
	
	private void addCommentsStats() {
		
		rowSheetOverviewCount += 2;
		
		Row rowCommentsStatsTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowCommentsStatsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCommentsStatsTitle = rowCommentsStatsTitle.createCell(0);
		cellCommentsStatsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.commentsonperiod"));
		cellCommentsStatsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowCommentsStatsTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowCommentsStatsTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCommentsDateTitle = rowCommentsStatsTitleItems.createCell(0);
		cellCommentsDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.date"));
		cellCommentsDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCommentsTotalTitle = rowCommentsStatsTitleItems.createCell(1);
		cellCommentsTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totalcomments"));
		cellCommentsTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(InstagramReport instagramReport : reportInstagramOverview.getCommentsPerDay()) {
			
			Row rowLikesValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellDateValue = rowLikesValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yyyy").format(new Date(instagramReport.getDate() * 1000L)));
			
			Cell cellTotalValue = rowLikesValues.createCell(1);
			cellTotalValue.setCellValue(instagramReport.getValue());

			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowTotalComments = sheetOverview.createRow(rowSheetOverviewCount);
		rowTotalComments.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalCommentsTitle = rowTotalComments.createCell(0);
		cellTotalCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totalcommentscolon"));
		cellTotalCommentsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalCommentsValue = rowTotalComments.createCell(1);
		cellTotalCommentsValue.setCellValue(reportInstagramOverview.getComments());
		cellTotalCommentsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowAverageComments = sheetOverview.createRow(rowSheetOverviewCount);
		rowAverageComments.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAverageCommentsTitle = rowAverageComments.createCell(0);
		cellAverageCommentsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.commentsaverage"));
		cellAverageCommentsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellAverageCommentsValue = rowAverageComments.createCell(1);
		cellAverageCommentsValue.setCellValue(reportInstagramOverview.getCommentsAverage());
		cellAverageCommentsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
	}
	
	private void addPostsWithMoreInteractions() {
		
		rowSheetOverviewCount += 2;
		
		Row rowPostsWithMoreLikesTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowPostsWithMoreLikesTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsWithMoreLikesTitle = rowPostsWithMoreLikesTitle.createCell(0);
		cellPostsWithMoreLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.mostinteractionspostsonperiod"));
		cellPostsWithMoreLikesTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowPostsWithMoreLikesTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
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
		
		rowSheetOverviewCount++;
		for(InstagramReportPost instagramReportPost : reportInstagramOverview.getPostsWithMoreInteractions()) {
			
			Row rowPostValues = sheetOverview.createRow(rowSheetOverviewCount);
			
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
			
			rowSheetOverviewCount++;
		}
	}
	
	private void addLikesStats() {
		
		rowSheetOverviewCount += 2;
		
		Row rowLikesStatsTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowLikesStatsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellLikesStatsTitle = rowLikesStatsTitle.createCell(0);
		cellLikesStatsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.likesonperiod"));
		cellLikesStatsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowLikesStatsTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowLikesStatsTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellLikesDateTitle = rowLikesStatsTitleItems.createCell(0);
		cellLikesDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.date"));
		cellLikesDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellLikesTotalTitle = rowLikesStatsTitleItems.createCell(1);
		cellLikesTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totallikes"));
		cellLikesTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		
		for(InstagramReport instagramReport : reportInstagramOverview.getLikesPerDay()) {
			
			Row rowLikesValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellDateValue = rowLikesValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yyyy").format(new Date(instagramReport.getDate() * 1000L)));
			
			Cell cellTotalValue = rowLikesValues.createCell(1);
			cellTotalValue.setCellValue(instagramReport.getValue());

			rowSheetOverviewCount++;
		}
			
		rowSheetOverviewCount++;
		Row rowTotalLikes = sheetOverview.createRow(rowSheetOverviewCount);
		rowTotalLikes.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalLikesTitle = rowTotalLikes.createCell(0);
		cellTotalLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totallikescolon"));
		cellTotalLikesTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalLikesValue = rowTotalLikes.createCell(1);
		cellTotalLikesValue.setCellValue(reportInstagramOverview.getLikes());
		cellTotalLikesValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowAverageLikes = sheetOverview.createRow(rowSheetOverviewCount);
		rowAverageLikes.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAverageLikesTitle = rowAverageLikes.createCell(0);
		cellAverageLikesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.dailyaveragelikes"));
		cellAverageLikesTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellAverageLikesValue = rowAverageLikes.createCell(1);
		cellAverageLikesValue.setCellValue(reportInstagramOverview.getLikesAverage());
		cellAverageLikesValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addBasicPreAnalysisRow(String title, String value) {
		
		rowSheetOverviewCount++;
		Row row = sheetOverview.createRow(rowSheetOverviewCount);
		row.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTitle = row.createCell(0);
		cellTitle.setCellValue(title);
		cellTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellValue = row.createCell(1);
		cellValue.setCellValue(value);
		cellValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addBasicPostsStats() {
		
		Row rowStatsTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowStatsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellStatsTitle = rowStatsTitle.createCell(0);
		cellStatsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.accountsummary"));
		cellStatsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		addBasicPreAnalysisRow(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totalposts"), 
				reportInstagramOverview.getTotalPosts().toString());
		
		addBasicPreAnalysisRow(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totalpostsimage"), 
				reportInstagramOverview.getTotalPostsImage().toString());
		
		addBasicPreAnalysisRow(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totalpostsvideo"), 
				reportInstagramOverview.getTotalPostsVideo().toString());
		
		addBasicPreAnalysisRow(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.totalpostscarousel"), 
				reportInstagramOverview.getTotalPostsCarousel().toString());
		
		addBasicPreAnalysisRow(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.postsinteractionaverage"), 
				reportInstagramOverview.getAverageInteractionsPosts().toString());
		
		addBasicPreAnalysisRow(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.followersinteractionaverage"),
				reportInstagramOverview.getAverageInteractionsFollower().toString());
		
		addBasicPreAnalysisRow(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.impressions"), 
				reportInstagramOverview.getImpressions().toString());
		
		addBasicPreAnalysisRow(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.reach"), 
				reportInstagramOverview.getReach().toString());
		
		addBasicPreAnalysisRow(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.profileviews"), 
				reportInstagramOverview.getProfileViews().toString());
		
		addBasicPreAnalysisRow(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.websiteclicks"), 
				reportInstagramOverview.getWebsiteClicks().toString());
		
		rowSheetOverviewCount += 2;
	}
	
	private void createDemographicRows() {
		
		demographicRows = new HashMap<String, Row>();
		
		rowSheetOverviewCount++;
		Row rowThirteenSeventeenValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellThirteenSeventeenTitle = rowThirteenSeventeenValues.createCell(0);
		cellThirteenSeventeenTitle.setCellValue("13-17");
		
		rowSheetOverviewCount++;
		Row rowEighteenTwentyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellEighteenTwentyFourTitle = rowEighteenTwentyFourValues.createCell(0);
		cellEighteenTwentyFourTitle.setCellValue("18-24");
		
		rowSheetOverviewCount++;
		Row rowTwentyFiveThirtyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellTwentyFiveThirtyFourTitle = rowTwentyFiveThirtyFourValues.createCell(0);
		cellTwentyFiveThirtyFourTitle.setCellValue("25-34");
		
		rowSheetOverviewCount++;
		Row rowThirtyFiveFortyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellThirtyFiveFortyFourTitle = rowThirtyFiveFortyFourValues.createCell(0);
		cellThirtyFiveFortyFourTitle.setCellValue("35-44");
		
		rowSheetOverviewCount++;
		Row rowFortyFiveFiftyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellFortyFiveFiftyFourTitle = rowFortyFiveFiftyFourValues.createCell(0);
		cellFortyFiveFiftyFourTitle.setCellValue("45-54");
		
		rowSheetOverviewCount++;
		Row rowFiftyFiveSixtyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellFiftyFiveSixtyFourTitle = rowFiftyFiveSixtyFourValues.createCell(0);
		cellFiftyFiveSixtyFourTitle.setCellValue("55-64");
		
		rowSheetOverviewCount++;
		Row rowSixtyFourPlusValues = sheetOverview.createRow(rowSheetOverviewCount);
		
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
	
	private void addDemographicGenderValue(String ageGroup, Long value, int cellIndex) {
		
		Cell cellValue = demographicRows.get(ageGroup).createCell(cellIndex);
		cellValue.setCellValue(value);
	}
	
	private void addAudiencesDemograph() {
		
		rowSheetOverviewCount += 2;
		Row rowStatsTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowStatsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellStatsTitle = rowStatsTitle.createCell(0);
		cellStatsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.audiencesdemograph"));
		cellStatsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowAgeGroupUsersTitle = sheetOverview.createRow(rowSheetOverviewCount);
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
		
		for (InstagramReport instagramReport : reportInstagramOverview.getAudiencesDemograph()) {
			
			if(instagramReport.getProperty().equals(ReportInstagram.AUDIENCE_AGE_MEN)) {
				addDemographicGenderValue(instagramReport.getLabel(), instagramReport.getValue(), 1);
			} else if(instagramReport.getProperty().equals(ReportInstagram.AUDIENCE_AGE_WOMEN)) {
				addDemographicGenderValue(instagramReport.getLabel(), instagramReport.getValue(), 2);
			}
		}
		
		rowSheetOverviewCount += 2;
		Row rowMajorAgeGroupUsers = sheetOverview.createRow(rowSheetOverviewCount);
		rowMajorAgeGroupUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMajorAgeGroupUsersTitle = rowMajorAgeGroupUsers.createCell(0);
		cellMajorAgeGroupUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.majoragegroupuserstitle"));
		cellMajorAgeGroupUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMajorAgeGroupUsersValue = rowMajorAgeGroupUsers.createCell(1);
		cellMajorAgeGroupUsersValue.setCellValue(reportInstagramOverview.getAudiencesAveragePublicAge().getKey().toString() + 
				" / " + Calc.calcPercentageNormal(Double.valueOf(reportInstagramOverview.getAudiencesAveragePublicAge().getValue().toString()), 
							reportInstagramOverview.getFollowers().doubleValue(), 0).intValue() + "%");
		cellMajorAgeGroupUsersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowMenUsers = sheetOverview.createRow(rowSheetOverviewCount);
		rowMenUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMenUsersTitle = rowMenUsers.createCell(0);
		cellMenUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.malecolon"));
		cellMenUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMenUsersValue = rowMenUsers.createCell(1);
		cellMenUsersValue.setCellValue(Calc.calcPercentageNormal(Double.valueOf(reportInstagramOverview.getAudiencesAveragePublicGender().get(0).getValue().toString()), 
							reportInstagramOverview.getFollowers().doubleValue(), 0).intValue() + "%");
		cellMenUsersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowWomenUsers = sheetOverview.createRow(rowSheetOverviewCount);
		rowWomenUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellWomenUsersTitle = rowWomenUsers.createCell(0);
		cellWomenUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.femalecolon"));
		cellWomenUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellWomenUsersValue = rowWomenUsers.createCell(1);
		cellWomenUsersValue.setCellValue(Calc.calcPercentageNormal(Double.valueOf(reportInstagramOverview.getAudiencesAveragePublicGender().get(1).getValue().toString()), 
				reportInstagramOverview.getFollowers().doubleValue(), 0).intValue() + "%");
		cellWomenUsersValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addCountriesStats() {
		
		rowSheetOverviewCount += 2;
		
		Row rowCountriesMoreFansTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowCountriesMoreFansTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCountriesMoreFansTitle = rowCountriesMoreFansTitle.createCell(0);
		cellCountriesMoreFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.topcountriestitle"));
		cellCountriesMoreFansTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowCountriesMoreFansSubTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowCountriesMoreFansSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCountriesMoreFansCityTitle = rowCountriesMoreFansSubTitle.createCell(0);
		cellCountriesMoreFansCityTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.country"));
		cellCountriesMoreFansCityTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCountriesMoreFansTotalTitle = rowCountriesMoreFansSubTitle.createCell(1);
		cellCountriesMoreFansTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.followers"));
		cellCountriesMoreFansTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(InstagramReport instagramReport : reportInstagramOverview.getAudiencesTopCountry()) {
			
			Row rowCountriesMoreFansValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellCountriesMoreFansCityValue = rowCountriesMoreFansValues.createCell(0);
			cellCountriesMoreFansCityValue.setCellValue(instagramReport.getLabel());
			
			Cell cellCountriesMoreFansTotalValue = rowCountriesMoreFansValues.createCell(1);
			cellCountriesMoreFansTotalValue.setCellValue(instagramReport.getValue());
			
			rowSheetOverviewCount++;
		}
	}
	
	private void addCitiesStats() {
		
		rowSheetOverviewCount++;
		
		Row rowCitiesMoreFansTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowCitiesMoreFansTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCitiesMoreFansTitle = rowCitiesMoreFansTitle.createCell(0);
		cellCitiesMoreFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.topcitiestitle"));
		cellCitiesMoreFansTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 6));
		
		rowSheetOverviewCount++;
		Row rowCitiesMoreFansSubTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowCitiesMoreFansSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCitiesMoreFansCityTitle = rowCitiesMoreFansSubTitle.createCell(0);
		cellCitiesMoreFansCityTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.city"));
		cellCitiesMoreFansCityTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCitiesMoreFansTotalTitle = rowCitiesMoreFansSubTitle.createCell(1);
		cellCitiesMoreFansTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.followers"));
		cellCitiesMoreFansTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(InstagramReport instagramReport : reportInstagramOverview.getAudiencesTopCities()) {
			
			Row rowCitiesMoreFansValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellCitiesMoreFansCityValue = rowCitiesMoreFansValues.createCell(0);
			cellCitiesMoreFansCityValue.setCellValue(instagramReport.getLabel());
			
			Cell cellCitiesMoreFansTotalValue = rowCitiesMoreFansValues.createCell(1);
			cellCitiesMoreFansTotalValue.setCellValue(instagramReport.getValue());
			
			rowSheetOverviewCount++;
		}
	}
	
	private void addFollowersOnline() {
		
		rowSheetOverviewCount++;
		
		Row rowFansOnlineTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowFansOnlineTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFansOnlineTitle = rowFansOnlineTitle.createCell(0);
		cellFansOnlineTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.followersonlinetitle"));
		cellFansOnlineTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowFansOnlinePerHourTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowFansOnlinePerHourTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTimeRangeTitle = rowFansOnlinePerHourTitle.createCell(0);
		cellTimeRangeTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.timerangetitle"));
		cellTimeRangeTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTimeRangeTotalTitle = rowFansOnlinePerHourTitle.createCell(1);
		cellTimeRangeTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.followers"));
		cellTimeRangeTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(Integer hour = 0; hour < 24; hour++) {
			
			Row rowFansOnlinePerHourValues = sheetOverview.createRow(rowSheetOverviewCount);
			
			Cell cellTimeRangeValue = rowFansOnlinePerHourValues.createCell(0);
			cellTimeRangeValue.setCellValue(new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, hour, 0)) + " - " +
					new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, hour + 1, 0)));
			
			Cell cellTimeRangeTotalValue = rowFansOnlinePerHourValues.createCell(1);
			cellTimeRangeTotalValue.setCellValue(0);
			
			for(InstagramReport instagramReport : reportInstagramOverview.getAudiencesOnlineFollowersByHour()) {
				
				if(hour.equals(Integer.valueOf(instagramReport.getLabel()))) {
					cellTimeRangeTotalValue.setCellValue(instagramReport.getValue());
					break;
				}	
			}
			
			rowSheetOverviewCount++;
		}
		
		addFollowersOnlinePerWeekDay();
		
		Long mostOnlineFollowers = 0L;
		Integer hourMostOnline = 0;
		for(InstagramReport instagramReport : reportInstagramOverview.getAudiencesOnlineFollowersByHour()) {
			
			if (instagramReport.getValue() > mostOnlineFollowers) {
				mostOnlineFollowers = instagramReport.getValue();
				hourMostOnline = Integer.valueOf(instagramReport.getLabel());
			}	
		}
		
		rowSheetOverviewCount++;
		Row rowTimeMoreFansOnline = sheetOverview.createRow(rowSheetOverviewCount);
		rowTimeMoreFansOnline.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTimeMoreFansOnlineTitle = rowTimeMoreFansOnline.createCell(0);
		cellTimeMoreFansOnlineTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.timemorefollowersonlinetitle"));
		cellTimeMoreFansOnlineTitle.setCellStyle(cellStylePreAnalysis);
		
		String timeMoreFansOnlineFrom = new SimpleDateFormat("HH:mm").format(
				new Date(0, 0, 0, hourMostOnline, 0));
		
		String timeMoreFansOnlineUntil = new SimpleDateFormat("HH:mm").format(
				new Date(0, 0, 0, hourMostOnline + 1, 0));
		
		Cell cellTimeMoreFansOnlineValue = rowTimeMoreFansOnline.createCell(1);
		cellTimeMoreFansOnlineValue.setCellValue(timeMoreFansOnlineFrom + " - " + timeMoreFansOnlineUntil);
		cellTimeMoreFansOnlineValue.setCellStyle(cellStylePreAnalysisResult);
		
		Long mostOnlineFollowersDay = 0L;
		Integer dayMostOnline = 0;
		for(InstagramReport instagramReport : reportInstagramOverview.getAudiencesOnlineFollowersByDay()) {
			
			if (instagramReport.getValue() > mostOnlineFollowersDay) {
				mostOnlineFollowersDay = instagramReport.getValue();
				dayMostOnline = Integer.valueOf(instagramReport.getLabel());
			}	
		}
		
		rowSheetOverviewCount++;
		Row rowWeekDayMoreFansOnline = sheetOverview.createRow(rowSheetOverviewCount);
		rowWeekDayMoreFansOnline.setHeightInPoints(headerDataRowHeight);
		
		Cell cellWeekDayMoreFansOnlineTitle = rowWeekDayMoreFansOnline.createCell(0);
		cellWeekDayMoreFansOnlineTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.weekdaymorefansonlinetitle"));
		cellWeekDayMoreFansOnlineTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellWeekDayMoreFansOnlineValue = rowWeekDayMoreFansOnline.createCell(1);
		cellWeekDayMoreFansOnlineValue.setCellValue(WordUtils.capitalize(OpSocialBackApplication.utilFunctionsService.getFormattedWeekDay(dayMostOnline)));
		cellWeekDayMoreFansOnlineValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addFollowersOnlinePerWeekDay() {
		
		rowSheetOverviewCount++;
		Row rowFansOnlinePerHourTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowFansOnlinePerHourTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTimeRangeTitle = rowFansOnlinePerHourTitle.createCell(0);
		cellTimeRangeTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.weekday"));
		cellTimeRangeTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTimeRangeTotalTitle = rowFansOnlinePerHourTitle.createCell(1);
		cellTimeRangeTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.followers"));
		cellTimeRangeTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		
		for(InstagramReport instagramReport : reportInstagramOverview.getAudiencesOnlineFollowersByDay()) {
			
			Row rowTweetsPerWeekDayValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellWeekDayValue = rowTweetsPerWeekDayValues.createCell(0);
			cellWeekDayValue.setCellValue(OpSocialBackApplication.utilFunctionsService.getFormattedWeekDayShort(Integer.valueOf(instagramReport.getLabel())));
			
			Cell cellTotalValue = rowTweetsPerWeekDayValues.createCell(1);
			cellTotalValue.setCellValue(instagramReport.getValue());

			rowSheetOverviewCount++;
		}
	}
	
	private void addFollowersStats() {
		
		Row rowFollowersStatsTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowFollowersStatsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFollowersStatsTitle = rowFollowersStatsTitle.createCell(0);
		cellFollowersStatsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.followerscountchange"));
		cellFollowersStatsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowFollowersStatsTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowFollowersStatsTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFollowersDateTitle = rowFollowersStatsTitleItems.createCell(0);
		cellFollowersDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.date"));
		cellFollowersDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellFollowersNewTitle = rowFollowersStatsTitleItems.createCell(1);
		cellFollowersNewTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.newfollowers"));
		cellFollowersNewTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellFollowersLostTitle = rowFollowersStatsTitleItems.createCell(2);
		cellFollowersLostTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.lostfollowers"));
		cellFollowersLostTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellFollowersBalanceTitle = rowFollowersStatsTitleItems.createCell(3);
		cellFollowersBalanceTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.followersbalance"));
		cellFollowersBalanceTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(InstagramFollowerTrend instagramFollowerTrend : reportInstagramOverview.getInstagramFollowerTrends()) {
			
			Row rowFollowersValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellDateValue = rowFollowersValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yyyy").format(new Date(instagramFollowerTrend.getDate() * 1000L)));
			
			Cell cellNewFollowersValue = rowFollowersValues.createCell(1);
			cellNewFollowersValue.setCellValue(instagramFollowerTrend.getNewFollowers());
			
			Cell cellLostFollowersValue = rowFollowersValues.createCell(2);
			cellLostFollowersValue.setCellValue(instagramFollowerTrend.getUnfollowers());
			
			Cell cellBalanceFollowersValue = rowFollowersValues.createCell(3);
			cellBalanceFollowersValue.setCellValue(instagramFollowerTrend.getBalanceFollowers());

			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowTotalFollowers = sheetOverview.createRow(rowSheetOverviewCount);
		rowTotalFollowers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalFollowersTitle = rowTotalFollowers.createCell(0);
		cellTotalFollowersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.generaltotalfollowers"));
		cellTotalFollowersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalFollowersValue = rowTotalFollowers.createCell(1);
		cellTotalFollowersValue.setCellValue(reportInstagramOverview.getFollowers().toString());
		cellTotalFollowersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowNewFollowers = sheetOverview.createRow(rowSheetOverviewCount);
		rowNewFollowers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellNewFollowersTitle = rowNewFollowers.createCell(0);
		cellNewFollowersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.newfollowers"));
		cellNewFollowersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellNewFollowersValue = rowNewFollowers.createCell(1);
		cellNewFollowersValue.setCellValue(reportInstagramOverview.getInstagramFollowerTrend().getNewFollowers());
		cellNewFollowersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowLostFollowers = sheetOverview.createRow(rowSheetOverviewCount);
		rowLostFollowers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellLostFollowersTitle = rowLostFollowers.createCell(0);
		cellLostFollowersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.lostfollowers"));
		cellLostFollowersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellLostFollowersValue = rowLostFollowers.createCell(1);
		cellLostFollowersValue.setCellValue(reportInstagramOverview.getInstagramFollowerTrend().getUnfollowers());
		cellLostFollowersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowFollowersBalance = sheetOverview.createRow(rowSheetOverviewCount);
		rowFollowersBalance.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFollowersBalanceTotalTitle = rowFollowersBalance.createCell(0);
		cellFollowersBalanceTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.ig.report.followersbalance"));
		cellFollowersBalanceTotalTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellFollowersBalanceValue = rowFollowersBalance.createCell(1);
		cellFollowersBalanceValue.setCellValue(reportInstagramOverview.getInstagramFollowerTrend().getBalanceFollowers());
		cellFollowersBalanceValue.setCellStyle(cellStylePreAnalysisResult);
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
