package br.com.opsocial.server.utils.reports.xls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import br.com.opsocial.client.entity.report.twitter.ReportDetailedTwitterDTO;
import br.com.opsocial.client.entity.report.twitter.ReportTwitterOverviewDTO;
import br.com.opsocial.client.entity.report.twitter.TwitterReportStatusDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.report.TwitterReport;
import br.com.opsocial.server.utils.aws.S3Integration;

public class ReportTwitterOverviewXLS {

	private ReportTwitterOverviewDTO reportTwitterOverview;
	private Profile profile;

	private String reportsPath;
	private String fileName;
	private String formattedFileName;

	private HSSFWorkbook wb;
	private Sheet sheetOverview;
	private Sheet sheetTweets;

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
	private int rowSheetTweetsCount;

	public ReportTwitterOverviewXLS(ReportTwitterOverviewDTO reportTwitterOverview, Profile profile) {
		this.reportTwitterOverview = reportTwitterOverview;
		this.profile = profile;
	}

	public void createXLS() {

		wb = new HSSFWorkbook();

		initiateDefautStyles();

		this.reportsPath = OpSocialBackApplication.GET_DIR_REPORTS_XLS;
		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".xls");

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat dateFormatToFileName = new SimpleDateFormat("MM-dd-yyyy");

		startDate = dateFormat.format(new Date(reportTwitterOverview.getDateFrom() * 1000L));
		endDate = dateFormat.format(new Date(reportTwitterOverview.getDateUntil() * 1000L));

		sheetOverview = wb.createSheet(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.tabname"));
		sheetTweets = wb.createSheet(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.tweets"));

		headerDataRowHeight = 1.2F * sheetOverview.getDefaultRowHeightInPoints();

		addMainHeader(sheetOverview, OpSocialBackApplication.messageByLocaleService.
				getMessage("xls.tw.report.header", new Object[]{profile.getScreenName(), startDate, endDate}), 5);

		rowSheetOverviewCount = 2;
		
		addFollowersStats();
		addTweetsSent();
		addTweetsOverTime();
		addEngagementWithTweets();
		addAboutFollowers();
		addFollowersActivity();
		addFollowersCities();
		addMostRecentFollowers();
		addMostInfluentFollowers();

		sheetOverview.setDefaultColumnWidth(25);
		sheetOverview.setColumnWidth(0, 8000);
		
		addMainHeader(sheetTweets, OpSocialBackApplication.messageByLocaleService.
				getMessage("xls.tw.report.header", new Object[]{profile.getScreenName(), startDate, endDate}), 4);
		
		rowSheetTweetsCount = 2;
		
		addTweetsMoreEngaged();
		
		sheetTweets.setDefaultColumnWidth(25);
		sheetTweets.setColumnWidth(1, 8000);

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
				getMessage("xls.tw.report.doctitle", new Object[]{
						profile.getScreenName(), 
						dateFormatToFileName.format(new Date(reportTwitterOverview.getDateFrom() * 1000L)), 
						dateFormatToFileName.format(new Date(reportTwitterOverview.getDateUntil() * 1000L))
			});
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void addTweetsMoreEngaged() {
		
		Row rowTweetsMoreEngagedTitle = sheetTweets.createRow(rowSheetTweetsCount);
		rowTweetsMoreEngagedTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTweetsMoreEngagedTitle = rowTweetsMoreEngagedTitle.createCell(0);
		cellTweetsMoreEngagedTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.tweetsupper"));
		cellTweetsMoreEngagedTitle.setCellStyle(cellStyleHeaderData);
		
		sheetTweets.addMergedRegion(new CellRangeAddress(rowSheetTweetsCount, rowSheetTweetsCount, 0, 4));
		
		rowSheetTweetsCount++;
		Row rowTweetsMoreEngagedTitleItems = sheetTweets.createRow(rowSheetTweetsCount);
		rowTweetsMoreEngagedTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTweetsMoreEngagedDateTitle = rowTweetsMoreEngagedTitleItems.createCell(0);
		cellTweetsMoreEngagedDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.date"));
		cellTweetsMoreEngagedDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTweetsMoreEngagedTextTitle = rowTweetsMoreEngagedTitleItems.createCell(1);
		cellTweetsMoreEngagedTextTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.content"));
		cellTweetsMoreEngagedTextTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTweetsMoreEngagedRetweetsTitle = rowTweetsMoreEngagedTitleItems.createCell(2);
		cellTweetsMoreEngagedRetweetsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.retweets"));
		cellTweetsMoreEngagedRetweetsTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellTweetsMoreEngagedRepliesTitle = rowTweetsMoreEngagedTitleItems.createCell(3);
		cellTweetsMoreEngagedRepliesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.replies"));
		cellTweetsMoreEngagedRepliesTitle.setCellStyle(cellSecondaryHeaderNumber);

		Cell cellTweetsMoreEngagedFavoritesTitle = rowTweetsMoreEngagedTitleItems.createCell(4);
		cellTweetsMoreEngagedFavoritesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.like"));
		cellTweetsMoreEngagedFavoritesTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetTweetsCount++;
		for(TwitterReportStatusDTO twitterReportStatus : reportTwitterOverview.getTwitterReportStatus()) {
			
			Row rowTweetsMoreEngagedValues = sheetTweets.createRow(rowSheetTweetsCount);
			
			Cell cellDateValue = rowTweetsMoreEngagedValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yyyy").format(twitterReportStatus.getCreatedAt()));
			
			Cell cellTextValue = rowTweetsMoreEngagedValues.createCell(1);
			cellTextValue.setCellValue(twitterReportStatus.getText());
			
			Cell cellRetweetsValue = rowTweetsMoreEngagedValues.createCell(2);
			cellRetweetsValue.setCellValue(twitterReportStatus.getRetweetsCount());
			
			Cell cellRepliesValue = rowTweetsMoreEngagedValues.createCell(3);
			cellRepliesValue.setCellValue(twitterReportStatus.getRepliesCount());
			
			Cell cellFavoritesValue = rowTweetsMoreEngagedValues.createCell(4);
			cellFavoritesValue.setCellValue(twitterReportStatus.getFavoriteCount());

			rowSheetTweetsCount++;
		}
		
	}
	
	private void addMostInfluentFollowers() {
		
		rowSheetOverviewCount++;
		
		Row rowMostInfluentFollowersTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowMostInfluentFollowersTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMostInfluentFollowersTitle = rowMostInfluentFollowersTitle.createCell(0);
		cellMostInfluentFollowersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.mostinfluenteusers"));
		cellMostInfluentFollowersTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 5));
		
		rowSheetOverviewCount++;
		Row rowMostInfluentFollowersTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowMostInfluentFollowersTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMostInfluentFollowersNameTitle = rowMostInfluentFollowersTitleItems.createCell(0);
		cellMostInfluentFollowersNameTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.name"));
		cellMostInfluentFollowersNameTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellMostInfluentFollowersTotalTitle = rowMostInfluentFollowersTitleItems.createCell(1);
		cellMostInfluentFollowersTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.profilelink"));
		cellMostInfluentFollowersTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for (int i = 0; i < reportTwitterOverview.getMostInfluentialFollowers().size(); i++) {
			
			Row rowMostRecentFollowersValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellNameValue = rowMostRecentFollowersValues.createCell(0);
			cellNameValue.setCellValue((i + 1) +". " + reportTwitterOverview.getMostInfluentialFollowers().get(i).getScreenName());
			
			Hyperlink hyperLink = createHelper.createHyperlink(Hyperlink.LINK_URL);
    		hyperLink.setAddress(reportTwitterOverview.getMostInfluentialFollowers().get(i).getProfileUrl());
		
			Cell cellProfileLinkValue = rowMostRecentFollowersValues.createCell(1);
			cellProfileLinkValue.setCellValue(reportTwitterOverview.getMostInfluentialFollowers().get(i).getProfileUrl());
			cellProfileLinkValue.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hyperLink);
			cellProfileLinkValue.setCellStyle(cellStyleHLink);

			rowSheetOverviewCount++;
		}
	}
	
	private void addMostRecentFollowers() {
		
		rowSheetOverviewCount++;
		
		Row rowMostRecentFollowersTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowMostRecentFollowersTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMostRecentFollowersTitle = rowMostRecentFollowersTitle.createCell(0);
		cellMostRecentFollowersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.mostrecentfollowers"));
		cellMostRecentFollowersTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 5));
		
		rowSheetOverviewCount++;
		Row rowMostRecentFollowersTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowMostRecentFollowersTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMostRecentFollowersNameTitle = rowMostRecentFollowersTitleItems.createCell(0);
		cellMostRecentFollowersNameTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.name"));
		cellMostRecentFollowersNameTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellMostRecentFollowersTotalTitle = rowMostRecentFollowersTitleItems.createCell(1);
		cellMostRecentFollowersTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.profilelink"));
		cellMostRecentFollowersTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for (int i = 0; i < reportTwitterOverview.getMostRecentFollowers().size(); i++) {
			
			Row rowMostRecentFollowersValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellNameValue = rowMostRecentFollowersValues.createCell(0);
			cellNameValue.setCellValue((i + 1) +". " + reportTwitterOverview.getMostRecentFollowers().get(i).getScreenName());
			
			Hyperlink hyperLink = createHelper.createHyperlink(Hyperlink.LINK_URL);
    		hyperLink.setAddress(reportTwitterOverview.getMostRecentFollowers().get(i).getProfileUrl());
		
			Cell cellProfileLinkValue = rowMostRecentFollowersValues.createCell(1);
			cellProfileLinkValue.setCellValue(reportTwitterOverview.getMostRecentFollowers().get(i).getProfileUrl());
			cellProfileLinkValue.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hyperLink);
			cellProfileLinkValue.setCellStyle(cellStyleHLink);

			rowSheetOverviewCount++;
		}
		
	}
	
	private void addFollowersCities() {
		
		rowSheetOverviewCount += 2;
		
		Row rowFollowersCitiesTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowFollowersCitiesTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFollowersCitiesTitle = rowFollowersCitiesTitle.createCell(0);
		cellFollowersCitiesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.citiesmorefollowers"));
		cellFollowersCitiesTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 5));
		
		rowSheetOverviewCount++;
		Row rowFollowersCitiesTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowFollowersCitiesTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFollowersCitiesCityTitle = rowFollowersCitiesTitleItems.createCell(0);
		cellFollowersCitiesCityTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.city"));
		cellFollowersCitiesCityTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellFollowersCitiesTotalTitle = rowFollowersCitiesTitleItems.createCell(1);
		cellFollowersCitiesTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.followers"));
		cellFollowersCitiesTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(Map.Entry<String, Long> city : reportTwitterOverview.getCitiesWithMoreFollowers().entrySet()) {
			
			Row rowFollowersCitiesValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellCityValue = rowFollowersCitiesValues.createCell(0);
			cellCityValue.setCellValue(city.getKey());
			
			Cell cellFollowersValue = rowFollowersCitiesValues.createCell(1);
			cellFollowersValue.setCellValue(city.getValue());

			rowSheetOverviewCount++;
		}
		
	}
	
	private void addFollowersActivity() {
		
		rowSheetOverviewCount += 2;
		
		Row rowFollowersActivityTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowFollowersActivityTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFollowersActivityTitle = rowFollowersActivityTitle.createCell(0);
		cellFollowersActivityTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.followersactivity"));
		cellFollowersActivityTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 5));
		
		rowSheetOverviewCount++;
		Row rowFollowersActivityTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowFollowersActivityTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFollowersActivityPeriodTitle = rowFollowersActivityTitleItems.createCell(0);
		cellFollowersActivityPeriodTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.lastactivitytime"));
		cellFollowersActivityPeriodTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellFollowersActivityTotalTitle = rowFollowersActivityTitleItems.createCell(1);
		cellFollowersActivityTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.total"));
		cellFollowersActivityTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		addFollowersActivityValue("1 " + OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.day"));
		addFollowersActivityValue("2 " + OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.days"));
		addFollowersActivityValue("4 " + OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.days"));
		addFollowersActivityValue("1 " + OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.week"));
		addFollowersActivityValue("2 " + OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.weeks"));
		addFollowersActivityValue("1 " + OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.month"));
		addFollowersActivityValue("3 " + OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.months"));	
	}
	
	private void addAboutFollowers() {
		
		rowSheetOverviewCount += 2;
		
		Row rowAboutFollowersTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowAboutFollowersTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAboutFollowersTitle = rowAboutFollowersTitle.createCell(0);
		cellAboutFollowersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.aboutfollowers"));
		cellAboutFollowersTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 5));
		
		rowSheetOverviewCount++;
		Row rowFollowersOfFollowersTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowFollowersOfFollowersTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFollowersOfFollowersRangeTitle = rowFollowersOfFollowersTitleItems.createCell(0);
		cellFollowersOfFollowersRangeTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.totalfollowers"));
		cellFollowersOfFollowersRangeTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellFollowersOfFollowersTotalTitle = rowFollowersOfFollowersTitleItems.createCell(1);
		cellFollowersOfFollowersTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.total"));
		cellFollowersOfFollowersTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		addFollowersRangeValue("0-300");
		addFollowersRangeValue("300-600");
		addFollowersRangeValue("600-1000");
		addFollowersRangeValue("1000-2000");
		addFollowersRangeValue("2000-5000");
		addFollowersRangeValue("5000+");
		
		rowSheetOverviewCount += 2;
		Row rowMenWomenFollowers = sheetOverview.createRow(rowSheetOverviewCount);
		rowMenWomenFollowers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMenWomenFollowersTitle = rowMenWomenFollowers.createCell(0);
		cellMenWomenFollowersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.bysex"));
		cellMenWomenFollowersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMenFollowersValue = rowMenWomenFollowers.createCell(1);
		cellMenFollowersValue.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.men") 
				+ " " + reportTwitterOverview.getMenFollowersPerc() + "%");
		cellMenFollowersValue.setCellStyle(cellStylePreAnalysisResult);
		
		Cell cellWomenFollowersValue = rowMenWomenFollowers.createCell(2);
		cellWomenFollowersValue.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.women") 
				+ " " + reportTwitterOverview.getWomenFollowersPerc() + "%");
		cellWomenFollowersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowAverageFollowersOfFollowers = sheetOverview.createRow(rowSheetOverviewCount);
		rowAverageFollowersOfFollowers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAverageFollowersOfFollowersTitle = rowAverageFollowersOfFollowers.createCell(0);
		cellAverageFollowersOfFollowersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.followersaverage"));
		cellAverageFollowersOfFollowersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellAverageFollowersOfFollowersValue = rowAverageFollowersOfFollowers.createCell(1);
		cellAverageFollowersOfFollowersValue.setCellValue(reportTwitterOverview.getAverageFollowersOfFollowers());
		cellAverageFollowersOfFollowersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowPotentialReach = sheetOverview.createRow(rowSheetOverviewCount);
		rowPotentialReach.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPotentialReachTitle = rowPotentialReach.createCell(0);
		cellPotentialReachTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.potentialreach"));
		cellPotentialReachTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellPotentialReachValue = rowPotentialReach.createCell(1);
		cellPotentialReachValue.setCellValue(reportTwitterOverview.getPotentialReach());
		cellPotentialReachValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addFollowersActivityValue(String period) {
		
		rowSheetOverviewCount++;
		Row rowFollowersActivityValues = sheetOverview.createRow(rowSheetOverviewCount);

		Cell cellPeriodValue = rowFollowersActivityValues.createCell(0);
		cellPeriodValue.setCellValue(period);
		
		Cell cellActivityValue = rowFollowersActivityValues.createCell(1);
		cellActivityValue.setCellValue(reportTwitterOverview.getFollowersActivity().containsKey(period) ? reportTwitterOverview.getFollowersActivity().get(period) : 0L);
	}
	
	private void addFollowersRangeValue(String range) {
		
		rowSheetOverviewCount++;
		Row rowTweetsOverTimeValues = sheetOverview.createRow(rowSheetOverviewCount);

		Cell cellRangeValue = rowTweetsOverTimeValues.createCell(0);
		cellRangeValue.setCellValue(range);
		
		Cell cellFollowersValue = rowTweetsOverTimeValues.createCell(1);
		cellFollowersValue.setCellValue(reportTwitterOverview.getFollowersOfFollowers().containsKey(range) ? reportTwitterOverview.getFollowersOfFollowers().get(range) : 0L);
	}
	
	private void addEngagementWithTweets() {
		
		rowSheetOverviewCount += 2;
		
		Row rowEngagementWithTweetsTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowEngagementWithTweetsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellEngagementWithTweetsTitle = rowEngagementWithTweetsTitle.createCell(0);
		cellEngagementWithTweetsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.tweetsengagement"));
		cellEngagementWithTweetsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 5));
		
		rowSheetOverviewCount++;
		Row rowEngagementWithTweetsTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowEngagementWithTweetsTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellEngagementWithTweetsDateTitle = rowEngagementWithTweetsTitleItems.createCell(0);
		cellEngagementWithTweetsDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.date"));
		cellEngagementWithTweetsDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellEngagementWithTweetsRepliesTitle = rowEngagementWithTweetsTitleItems.createCell(1);
		cellEngagementWithTweetsRepliesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.replies"));
		cellEngagementWithTweetsRepliesTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellEngagementWithTweetsRetweetsTitle = rowEngagementWithTweetsTitleItems.createCell(2);
		cellEngagementWithTweetsRetweetsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.retweets"));
		cellEngagementWithTweetsRetweetsTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellEngagementWithTweetsMentionsTitle = rowEngagementWithTweetsTitleItems.createCell(3);
		cellEngagementWithTweetsMentionsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.mentions"));
		cellEngagementWithTweetsMentionsTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellEngagementWithTweetsFavoritesTitle = rowEngagementWithTweetsTitleItems.createCell(4);
		cellEngagementWithTweetsFavoritesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.like"));
		cellEngagementWithTweetsFavoritesTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellEngagementWithTweetsTotalTitle = rowEngagementWithTweetsTitleItems.createCell(5);
		cellEngagementWithTweetsTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.total"));
		cellEngagementWithTweetsTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		Long engagementWithTweetsTotal = 0L;
		for (int i = 0; i < reportTwitterOverview.getRepliesByDay().size(); i++) {
			
			Row rowTweetsSentValues = sheetOverview.createRow(rowSheetOverviewCount);

			engagementWithTweetsTotal = reportTwitterOverview.getRepliesByDay().get(i).getValue() + 
					reportTwitterOverview.getRetweetsByDay().get(i).getValue() +
					reportTwitterOverview.getMentionsByDay().get(i).getValue();
			
			Cell cellDateValue = rowTweetsSentValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(reportTwitterOverview.getRepliesByDay().get(i).getDate() * 1000L)));
			
			Cell cellRepliesValue = rowTweetsSentValues.createCell(1);
			cellRepliesValue.setCellValue(reportTwitterOverview.getRepliesByDay().get(i).getValue());
			
			Cell cellRetweetsValue = rowTweetsSentValues.createCell(2);
			cellRetweetsValue.setCellValue(reportTwitterOverview.getRetweetsByDay().get(i).getValue());
			
			Cell cellMentionsValue = rowTweetsSentValues.createCell(3);
			cellMentionsValue.setCellValue(reportTwitterOverview.getMentionsByDay().get(i).getValue());
			
			Cell cellFavoritesValue = rowTweetsSentValues.createCell(4);
			if(reportTwitterOverview.getFavoritesByDay().size() > i) {
				cellFavoritesValue.setCellValue(reportTwitterOverview.getFavoritesByDay().get(i).getValue());
				engagementWithTweetsTotal += reportTwitterOverview.getFavoritesByDay().get(i).getValue();
			} else {
				cellFavoritesValue.setCellValue(0L);
			}
			
			Cell cellTotalValue = rowTweetsSentValues.createCell(5);
			cellTotalValue.setCellValue(engagementWithTweetsTotal);

			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowEngagementPeak = sheetOverview.createRow(rowSheetOverviewCount);
		rowEngagementPeak.setHeightInPoints(headerDataRowHeight);
		
		Cell cellEngagementPeakTitle = rowEngagementPeak.createCell(0);
		cellEngagementPeakTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.engagementpeak"));
		cellEngagementPeakTitle.setCellStyle(cellStylePreAnalysis);
		
		String engagementPeakDayFormatted = "-";
		if(reportTwitterOverview.getEngagementPeakDay() > 0L) {
			engagementPeakDayFormatted = new SimpleDateFormat("MM/dd/yy").format(new Date(reportTwitterOverview.getEngagementPeakDay() * 1000L));
		}
		
		Cell cellEngagementPeakValue = rowEngagementPeak.createCell(1);
		cellEngagementPeakValue.setCellValue(engagementPeakDayFormatted);
		cellEngagementPeakValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowReplies = sheetOverview.createRow(rowSheetOverviewCount);
		rowReplies.setHeightInPoints(headerDataRowHeight);
		
		Cell cellRepliesTitle = rowReplies.createCell(0);
		cellRepliesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.repliescolon"));
		cellRepliesTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellRepliesValue = rowReplies.createCell(1);
		cellRepliesValue.setCellValue(reportTwitterOverview.getEngagementPercentages().containsKey(ReportDetailedTwitterDTO.REPLIES_OF_ME) ? reportTwitterOverview.getEngagementPercentages().get(ReportDetailedTwitterDTO.REPLIES_OF_ME) + "%" : "0%");
		cellRepliesValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowRetweets = sheetOverview.createRow(rowSheetOverviewCount);
		rowRetweets.setHeightInPoints(headerDataRowHeight);
		
		Cell cellRetweetsTitle = rowRetweets.createCell(0);
		cellRetweetsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.retweetscolon"));
		cellRetweetsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellRetweetsValue = rowRetweets.createCell(1);
		cellRetweetsValue.setCellValue(reportTwitterOverview.getEngagementPercentages().containsKey(ReportDetailedTwitterDTO.RETWEETS_OF_ME_COUNT) ? reportTwitterOverview.getEngagementPercentages().get(ReportDetailedTwitterDTO.RETWEETS_OF_ME_COUNT) + "%" : "0%");
		cellRetweetsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowMentions = sheetOverview.createRow(rowSheetOverviewCount);
		rowMentions.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMentionsTitle = rowMentions.createCell(0);
		cellMentionsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.mentionscolon"));
		cellMentionsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMentionsValue = rowMentions.createCell(1);
		cellMentionsValue.setCellValue(reportTwitterOverview.getEngagementPercentages().containsKey(ReportDetailedTwitterDTO.MENTIONS_COUNT) ? reportTwitterOverview.getEngagementPercentages().get(ReportDetailedTwitterDTO.MENTIONS_COUNT) + "%" : "0%");
		cellMentionsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowFavorites = sheetOverview.createRow(rowSheetOverviewCount);
		rowFavorites.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFavoritesTitle = rowFavorites.createCell(0);
		cellFavoritesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.likecolon"));
		cellFavoritesTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellFavoritesValue = rowFavorites.createCell(1);
		cellFavoritesValue.setCellValue(reportTwitterOverview.getEngagementPercentages().containsKey(ReportDetailedTwitterDTO.FAVORITE_COUNT) ? reportTwitterOverview.getEngagementPercentages().get(ReportDetailedTwitterDTO.FAVORITE_COUNT) + "%" : "0%");
		cellFavoritesValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addTweetsOverTime() {
		
		rowSheetOverviewCount += 2;
		
		Row rowTweetsOverTimeTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowTweetsOverTimeTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTweetsOverTimeTitle = rowTweetsOverTimeTitle.createCell(0);
		cellTweetsOverTimeTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.tweetsbytimeweekday"));
		cellTweetsOverTimeTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 5));
		
		rowSheetOverviewCount++;
		Row rowTweetsOverTimeTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowTweetsOverTimeTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTweetsOverTimeDateTitle = rowTweetsOverTimeTitleItems.createCell(0);
		cellTweetsOverTimeDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.time"));
		cellTweetsOverTimeDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTweetsOverTimeTotalTitle = rowTweetsOverTimeTitleItems.createCell(1);
		cellTweetsOverTimeTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.totaltweets"));
		cellTweetsOverTimeTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(Map.Entry<Integer, Long> tweetsPerHour : reportTwitterOverview.getTweetsPerHour().entrySet()) {
			
			Row rowTweetsOverTimeValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellDateValue = rowTweetsOverTimeValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, tweetsPerHour.getKey(), 0, 0)));
			
			Cell cellTotalValue = rowTweetsOverTimeValues.createCell(1);
			cellTotalValue.setCellValue(tweetsPerHour.getValue());

			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowTweetsPerWeekDayTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowTweetsPerWeekDayTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTweetsPerWeekDayDateTitle = rowTweetsPerWeekDayTitleItems.createCell(0);
		cellTweetsPerWeekDayDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.weekday"));
		cellTweetsPerWeekDayDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTweetsPerWeekDayTotalTitle = rowTweetsPerWeekDayTitleItems.createCell(1);
		cellTweetsPerWeekDayTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.totaltweets"));
		cellTweetsPerWeekDayTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(Map.Entry<Integer, Long> tweetsPerWeekDay : reportTwitterOverview.getTweetsPerWeekDay().entrySet()) {
			
			Row rowTweetsPerWeekDayValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellWeekDayValue = rowTweetsPerWeekDayValues.createCell(0);
			cellWeekDayValue.setCellValue(OpSocialBackApplication.utilFunctionsService.getFormattedWeekDayShort(tweetsPerWeekDay.getKey()));
			
			Cell cellTotalValue = rowTweetsPerWeekDayValues.createCell(1);
			cellTotalValue.setCellValue(tweetsPerWeekDay.getValue());

			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowTimeMoreTweetsSent = sheetOverview.createRow(rowSheetOverviewCount);
		rowTimeMoreTweetsSent.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTimeMoreTweetsSentTitle = rowTimeMoreTweetsSent.createCell(0);
		cellTimeMoreTweetsSentTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.moretweetstime"));
		cellTimeMoreTweetsSentTitle.setCellStyle(cellStylePreAnalysis);
		
		String timeWithMoreTweetsFrom = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportTwitterOverview.getTimeWithMoreTweets(), 0, 0));
		String timeWithMoreTweetsUntil = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportTwitterOverview.getTimeWithMoreTweets() + 1, 0, 0));
		
		Cell cellTimeMoreTweetsSentHour = rowTimeMoreTweetsSent.createCell(1);
		cellTimeMoreTweetsSentHour.setCellValue(timeWithMoreTweetsFrom + " - " + timeWithMoreTweetsUntil);
		cellTimeMoreTweetsSentHour.setCellStyle(cellStylePreAnalysisResult);
		
		Cell cellTimeMoreTweetsSentValue = rowTimeMoreTweetsSent.createCell(2);
		cellTimeMoreTweetsSentValue.setCellValue(reportTwitterOverview.getTimeWithMoreTweetsPercentage() + "%");
		cellTimeMoreTweetsSentValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowWeekDayMoreTweetsSent = sheetOverview.createRow(rowSheetOverviewCount);
		rowWeekDayMoreTweetsSent.setHeightInPoints(headerDataRowHeight);
		
		Cell cellWeekDayMoreTweetsSentTitle = rowWeekDayMoreTweetsSent.createCell(0);
		cellWeekDayMoreTweetsSentTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.moretweetsday"));
		cellWeekDayMoreTweetsSentTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellWeekDayMoreTweetsSent = rowWeekDayMoreTweetsSent.createCell(1);
		cellWeekDayMoreTweetsSent.setCellValue(OpSocialBackApplication.utilFunctionsService.getFormattedWeekDayShort(reportTwitterOverview.getWeekDayWithMoreTweets()));
		cellWeekDayMoreTweetsSent.setCellStyle(cellStylePreAnalysisResult);
		
		Cell cellWeekDayMoreTweetsSentValue = rowWeekDayMoreTweetsSent.createCell(2);
		cellWeekDayMoreTweetsSentValue.setCellValue(reportTwitterOverview.getWeekDayWithMoreTweetsPercentage() + "%");
		cellWeekDayMoreTweetsSentValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addTweetsSent() {
		
		rowSheetOverviewCount += 2;
		
		Row rowTweetsSentTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowTweetsSentTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTweetsSentTitle = rowTweetsSentTitle.createCell(0);
		cellTweetsSentTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.tweetssenttime"));
		cellTweetsSentTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 5));
		
		rowSheetOverviewCount++;
		Row rowTweetsSentTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowTweetsSentTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTweetsSentDateTitle = rowTweetsSentTitleItems.createCell(0);
		cellTweetsSentDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.date"));
		cellTweetsSentDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTweetsSentTotalTitle = rowTweetsSentTitleItems.createCell(1);
		cellTweetsSentTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellTweetsSentWithTextTitle = rowTweetsSentTitleItems.createCell(2);
		cellTweetsSentTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.total"));
		cellTweetsSentWithTextTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.withtext"));
		cellTweetsSentWithTextTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellTweetsSentWithLinkTitle = rowTweetsSentTitleItems.createCell(3);
		cellTweetsSentWithLinkTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.withlink"));
		cellTweetsSentWithLinkTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellTweetsSentWithPhotoVideoTitle = rowTweetsSentTitleItems.createCell(4);
		cellTweetsSentWithPhotoVideoTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.withfotovideo"));
		cellTweetsSentWithPhotoVideoTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		Long tweetsSentDay = 0L;
		for (int i = 0; i < reportTwitterOverview.getTweetsSentWithText().size(); i++) {	
			
			Row rowTweetsSentValues = sheetOverview.createRow(rowSheetOverviewCount);

			tweetsSentDay = reportTwitterOverview.getTweetsSentWithText().get(i).getValue() +
			reportTwitterOverview.getTweetsSentWithPhotoVideo().get(i).getValue() + 
			reportTwitterOverview.getTweetsSentWithLink().get(i).getValue();
			
			Cell cellDateValue = rowTweetsSentValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(reportTwitterOverview.getTweetsSentWithText().get(i).getDate() * 1000L)));
			
			Cell cellTotalValue = rowTweetsSentValues.createCell(1);
			cellTotalValue.setCellValue(tweetsSentDay);
			
			Cell cellWithTextValue = rowTweetsSentValues.createCell(2);
			cellWithTextValue.setCellValue(reportTwitterOverview.getTweetsSentWithText().get(i).getValue());
			
			Cell cellWithLinkValue = rowTweetsSentValues.createCell(3);
			cellWithLinkValue.setCellValue(reportTwitterOverview.getTweetsSentWithLink().get(i).getValue());
			
			Cell cellWithPhotoVideolValue = rowTweetsSentValues.createCell(4);
			cellWithPhotoVideolValue.setCellValue(reportTwitterOverview.getTweetsSentWithPhotoVideo().get(i).getValue());

			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowAverageTweetsSent = sheetOverview.createRow(rowSheetOverviewCount);
		rowAverageTweetsSent.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAverageTweetsSentTitle = rowAverageTweetsSent.createCell(0);
		cellAverageTweetsSentTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.tweetsdailyaverage"));
		cellAverageTweetsSentTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellAverageTweetsSentValue = rowAverageTweetsSent.createCell(1);
		cellAverageTweetsSentValue.setCellValue(reportTwitterOverview.getAverageTweetsSentPerDay());
		cellAverageTweetsSentValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowTweetsSentByType = sheetOverview.createRow(rowSheetOverviewCount);
		rowTweetsSentByType.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTweetsSentByTypeTitle = rowTweetsSentByType.createCell(0);
		cellTweetsSentByTypeTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.mostvolumetweets"));
		cellTweetsSentByTypeTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTweetsSentByTypeValue = rowTweetsSentByType.createCell(1);
		cellTweetsSentByTypeValue.setCellValue(OpSocialBackApplication.utilFunctionsService.getTweetTypeName(reportTwitterOverview.getTweetTypeWithMoreTweets()) + " " +
				reportTwitterOverview.getTweetTypeWithMoreTweetsPercentage() + "%");
		cellTweetsSentByTypeValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addFollowersStats() {
		
		Row rowFollowersStatsTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowFollowersStatsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFollowersStatsTitle = rowFollowersStatsTitle.createCell(0);
		cellFollowersStatsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.followersbalance"));
		cellFollowersStatsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 5));
		
		rowSheetOverviewCount++;
		Row rowFollowersStatsTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowFollowersStatsTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFollowersDateTitle = rowFollowersStatsTitleItems.createCell(0);
		cellFollowersDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.date"));
		cellFollowersDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellFollowersTotalTitle = rowFollowersStatsTitleItems.createCell(1);
		cellFollowersTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.followers"));
		cellFollowersTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(TwitterReport twitterReport : reportTwitterOverview.getFollowersByDay()) {
			
			Row rowFollowersValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellDateValue = rowFollowersValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(twitterReport.getDate() * 1000L)));
			
			Cell cellTotalValue = rowFollowersValues.createCell(1);
			cellTotalValue.setCellValue(twitterReport.getValue());

			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowTotalFollowers = sheetOverview.createRow(rowSheetOverviewCount);
		rowTotalFollowers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalFollowersTitle = rowTotalFollowers.createCell(0);
		cellTotalFollowersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.totalfollowerscolon"));
		cellTotalFollowersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalFollowersValue = rowTotalFollowers.createCell(1);
		cellTotalFollowersValue.setCellValue(reportTwitterOverview.getTotalOfFollowers());
		cellTotalFollowersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowFollowersBalance = sheetOverview.createRow(rowSheetOverviewCount);
		rowFollowersBalance.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFollowersBalanceTitle = rowFollowersBalance.createCell(0);
		cellFollowersBalanceTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.followersbalanceperiod"));
		cellFollowersBalanceTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellFollowersBalanceValue = rowFollowersBalance.createCell(1);
		cellFollowersBalanceValue.setCellValue(reportTwitterOverview.getFollowersBalance());
		cellFollowersBalanceValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowFollowersGrowthRate = sheetOverview.createRow(rowSheetOverviewCount);
		rowFollowersGrowthRate.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFollowersGrowthRateTitle = rowFollowersGrowthRate.createCell(0);
		cellFollowersGrowthRateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.followersgrowth"));
		cellFollowersGrowthRateTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellFollowersGrowthRateValue = rowFollowersGrowthRate.createCell(1);
		cellFollowersGrowthRateValue.setCellValue(reportTwitterOverview.getFollowersGrowthRate().toString().replace(".", ",") + "%");
		cellFollowersGrowthRateValue.setCellStyle(cellStylePreAnalysisResult);
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
