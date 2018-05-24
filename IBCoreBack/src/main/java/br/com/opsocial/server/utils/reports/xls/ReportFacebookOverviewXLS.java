package br.com.opsocial.server.utils.reports.xls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

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
import br.com.opsocial.client.entity.report.facebook.FacebookInfluentialUserDTO;
import br.com.opsocial.client.entity.report.facebook.ReportDetailedFacebookDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookOverviewDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.report.FanPageGrowth;
import br.com.opsocial.ejb.entity.report.ReportFacebookLocation;
import br.com.opsocial.server.utils.aws.S3Integration;

public class ReportFacebookOverviewXLS {

	private ReportFacebookOverviewDTO reportFacebookOverview;
	private Profile profile;
	
	private String reportsPath;
	private String fileName;
	private String formattedFileName;
	
	private HSSFWorkbook wb;
	private Sheet sheetOverview;
	
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
	
	public ReportFacebookOverviewXLS(ReportFacebookOverviewDTO reportFacebookOverview, Profile profile) {
		this.reportFacebookOverview = reportFacebookOverview;
		this.profile = profile;
	}
	
	public void createXLS() {
		
		wb = new HSSFWorkbook();
		
		initiateDefautStyles();
		
		this.reportsPath = OpSocialBackApplication.GET_DIR_REPORTS_XLS;
		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".xls");

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat dateFormatToFileName = new SimpleDateFormat("MM/dd-yyyy");
		
		startDate = dateFormat.format(new Date(reportFacebookOverview.getDateFrom() * 1000L));
		endDate = dateFormat.format(new Date(reportFacebookOverview.getDateUntil() * 1000L));
		
		sheetOverview = wb.createSheet(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.tabname"));
		
		headerDataRowHeight = 1.2F * sheetOverview.getDefaultRowHeightInPoints();

		addMainHeader(sheetOverview, OpSocialBackApplication.messageByLocaleService.
				getMessage("xls.facebook.report.header", new Object[]{profile.getScreenName(), startDate, endDate}), 4);
		
		addFanPageGrowth();
		addReach();
		addImpressions();
		addEngagement();
		addAboutYourFans();
		addAboutReachedUsers();
		addInfluentFans();
		addFansOnline();
		addCountriesStats();
		addCitiesStats();
		addFansLikeSource();
		addPageStats();
		
		sheetOverview.setDefaultColumnWidth(25);
		sheetOverview.setColumnWidth(0, 8000);
		
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
				getMessage("xls.facebook.report.doctitle", new Object[]{
						profile.getScreenName(), 
						dateFormatToFileName.format(new Date(reportFacebookOverview.getDateFrom() * 1000L)), 
						dateFormatToFileName.format(new Date(reportFacebookOverview.getDateUntil() * 1000L))
			});
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addPageStats() {
		
		rowSheetOverviewCount += 2;
		Row rowOtherActivitiesTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowOtherActivitiesTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellOtherActivitiesTitle = rowOtherActivitiesTitle.createCell(0);
		cellOtherActivitiesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.otheractivitiestitle"));
		cellOtherActivitiesTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowOtherActivitiesSubTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowOtherActivitiesSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellActivityTitle = rowOtherActivitiesSubTitle.createCell(0);
		cellActivityTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.activitiestitle"));
		cellActivityTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellActivityTotalTitle = rowOtherActivitiesSubTitle.createCell(1);
		cellActivityTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.total"));
		cellActivityTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(ReportFacebookLocation storyType : reportFacebookOverview.getOtherActivities()) {
			
			Row rowActivityValues = sheetOverview.createRow(rowSheetOverviewCount);
			
			Cell cellActivityValue = rowActivityValues.createCell(0);
			cellActivityValue.setCellValue(OpSocialBackApplication.utilFunctionsService.getStoryTypeName(storyType.getLocation()));
			
			Cell cellActivityTotalValue = rowActivityValues.createCell(1);
			cellActivityTotalValue.setCellValue(storyType.getValue());
			
			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowExternalReferencesTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowExternalReferencesTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellExternalReferencesTitle = rowExternalReferencesTitle.createCell(0);
		cellExternalReferencesTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.externalreferencestitle"));
		cellExternalReferencesTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowExternalReferencesSubTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowExternalReferencesSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellReferenceTitle = rowExternalReferencesSubTitle.createCell(0);
		cellReferenceTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.referencessubtitle"));
		cellReferenceTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellReferenceTotalTitle = rowExternalReferencesSubTitle.createCell(1);
		cellReferenceTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.total"));
		cellReferenceTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(ReportFacebookLocation externalReference : reportFacebookOverview.getExternalReferences()) {
			
			Row rowReferenceValues = sheetOverview.createRow(rowSheetOverviewCount);
			
			Cell cellReferenceValue = rowReferenceValues.createCell(0);
			cellReferenceValue.setCellValue(externalReference.getLocation());
			
			Cell cellReferenceTotalValue = rowReferenceValues.createCell(1);
			cellReferenceTotalValue.setCellValue(externalReference.getValue());
			
			rowSheetOverviewCount++;
		}
	}
	
	private void addAboutReachedUsers() {
		
		rowSheetOverviewCount += 2;
		Row rowAboutReachedUsersStatsTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowAboutReachedUsersStatsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAboutReachedUsersStatsTitle = rowAboutReachedUsersStatsTitle.createCell(0);
		cellAboutReachedUsersStatsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.reachedusersstatstitle"));
		cellAboutReachedUsersStatsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowAgeGroupUsersTitle = sheetOverview.createRow(rowSheetOverviewCount);
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
		
		rowSheetOverviewCount++;
		Row rowThirteenSeventeenValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellThirteenSeventeenTitle = rowThirteenSeventeenValues.createCell(0);
		cellThirteenSeventeenTitle.setCellValue("13-17");
		
		Cell cellThirteenSeventeenMaleValue = rowThirteenSeventeenValues.createCell(1);
		cellThirteenSeventeenMaleValue.setCellValue(reportFacebookOverview.getAboutReachedUsers().containsKey(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_13_AMONG_17) ? 
				reportFacebookOverview.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_13_AMONG_17) : 0);
		
		Cell cellThirteenSeventeenFemaleValue = rowThirteenSeventeenValues.createCell(2);
		cellThirteenSeventeenFemaleValue.setCellValue(reportFacebookOverview.getAboutReachedUsers().containsKey(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_13_AMONG_17) ? 
				reportFacebookOverview.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_13_AMONG_17): 0);
		
		rowSheetOverviewCount++;
		Row rowEighteenTwentyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellEighteenTwentyFourTitle = rowEighteenTwentyFourValues.createCell(0);
		cellEighteenTwentyFourTitle.setCellValue("18-24");
		
		Cell cellEighteenTwentyFourMaleValue = rowEighteenTwentyFourValues.createCell(1);
		cellEighteenTwentyFourMaleValue.setCellValue(reportFacebookOverview.getAboutReachedUsers().containsKey(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_18_AMONG_24) ? 
				reportFacebookOverview.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_18_AMONG_24) : 0);
		
		Cell cellEighteenTwentyFourFemaleValue = rowEighteenTwentyFourValues.createCell(2);
		cellEighteenTwentyFourFemaleValue.setCellValue(reportFacebookOverview.getAboutReachedUsers().containsKey(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_18_AMONG_24) ? 
				reportFacebookOverview.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_18_AMONG_24): 0);
		
		rowSheetOverviewCount++;
		Row rowTwentyFiveThirtyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellTwentyFiveThirtyFourTitle = rowTwentyFiveThirtyFourValues.createCell(0);
		cellTwentyFiveThirtyFourTitle.setCellValue("25-34");
		
		Cell cellTwentyFiveThirtyFourMaleValue = rowTwentyFiveThirtyFourValues.createCell(1);
		cellTwentyFiveThirtyFourMaleValue.setCellValue(reportFacebookOverview.getAboutReachedUsers().containsKey(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_25_AMONG_34) ? 
				reportFacebookOverview.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_25_AMONG_34) : 0);
		
		Cell cellTwentyFiveThirtyFourFemaleValue = rowTwentyFiveThirtyFourValues.createCell(2);
		cellTwentyFiveThirtyFourFemaleValue.setCellValue(reportFacebookOverview.getAboutReachedUsers().containsKey(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_25_AMONG_34) ? 
				reportFacebookOverview.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_25_AMONG_34): 0);
		
		rowSheetOverviewCount++;
		Row rowThirtyFiveFortyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellThirtyFiveFortyFourTitle = rowThirtyFiveFortyFourValues.createCell(0);
		cellThirtyFiveFortyFourTitle.setCellValue("35-44");
		
		Cell cellThirtyFiveFortyFourMaleValue = rowThirtyFiveFortyFourValues.createCell(1);
		cellThirtyFiveFortyFourMaleValue.setCellValue(reportFacebookOverview.getAboutReachedUsers().containsKey(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_35_AMONG_44) ? 
				reportFacebookOverview.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_35_AMONG_44) : 0);
		
		Cell cellThirtyFiveFortyFourFemaleValue = rowThirtyFiveFortyFourValues.createCell(2);
		cellThirtyFiveFortyFourFemaleValue.setCellValue(reportFacebookOverview.getAboutReachedUsers().containsKey(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_35_AMONG_44) ? 
				reportFacebookOverview.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_35_AMONG_44): 0);
		
		rowSheetOverviewCount++;
		Row rowFortyFiveFiftyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellFortyFiveFiftyFourTitle = rowFortyFiveFiftyFourValues.createCell(0);
		cellFortyFiveFiftyFourTitle.setCellValue("45-54");
		
		Cell cellFortyFiveFiftyFourMaleValue = rowFortyFiveFiftyFourValues.createCell(1);
		cellFortyFiveFiftyFourMaleValue.setCellValue(reportFacebookOverview.getAboutReachedUsers().containsKey(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_45_AMONG_54) ? 
				reportFacebookOverview.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_45_AMONG_54) : 0);
		
		Cell cellFortyFiveFiftyFourFemaleValue = rowFortyFiveFiftyFourValues.createCell(2);
		cellFortyFiveFiftyFourFemaleValue.setCellValue(reportFacebookOverview.getAboutReachedUsers().containsKey(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_45_AMONG_54) ? 
				reportFacebookOverview.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_45_AMONG_54): 0);
		
		rowSheetOverviewCount++;
		Row rowFiftyFiveSixtyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellFiftyFiveSixtyFourTitle = rowFiftyFiveSixtyFourValues.createCell(0);
		cellFiftyFiveSixtyFourTitle.setCellValue("55-64");
		
		Cell cellFiftyFiveSixtyFourMaleValue = rowFiftyFiveSixtyFourValues.createCell(1);
		cellFiftyFiveSixtyFourMaleValue.setCellValue(reportFacebookOverview.getAboutReachedUsers().containsKey(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_55_AMONG_64) ? 
				reportFacebookOverview.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_55_AMONG_64) : 0);
		
		Cell cellFiftyFiveSixtyFourFemaleValue = rowFiftyFiveSixtyFourValues.createCell(2);
		cellFiftyFiveSixtyFourFemaleValue.setCellValue(reportFacebookOverview.getAboutReachedUsers().containsKey(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_55_AMONG_64) ? 
				reportFacebookOverview.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_55_AMONG_64): 0);
		
		rowSheetOverviewCount++;
		Row rowSixtyFourPlusValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellSixtyFivePlusTitle = rowSixtyFourPlusValues.createCell(0);
		cellSixtyFivePlusTitle.setCellValue("65+");
		
		Cell cellSixtyFivePlusMaleValue = rowSixtyFourPlusValues.createCell(1);
		cellSixtyFivePlusMaleValue.setCellValue(reportFacebookOverview.getAboutReachedUsers().containsKey(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_65_PLUS) ? 
				reportFacebookOverview.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_MALE_65_PLUS) : 0);
		
		Cell cellSixtyFivePlusFemaleValue = rowSixtyFourPlusValues.createCell(2);
		cellSixtyFivePlusFemaleValue.setCellValue(reportFacebookOverview.getAboutReachedUsers().containsKey(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_65_PLUS) ? 
				reportFacebookOverview.getAboutReachedUsers().get(ReportDetailedFacebookDTO.PAGE_IMPRESSIONS_FEMALE_65_PLUS): 0);
		
		rowSheetOverviewCount += 2;
		Row rowMajorAgeGroupUsers = sheetOverview.createRow(rowSheetOverviewCount);
		rowMajorAgeGroupUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMajorAgeGroupUsersTitle = rowMajorAgeGroupUsers.createCell(0);
		cellMajorAgeGroupUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.majoragegroupUserstitle"));
		cellMajorAgeGroupUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMajorAgeGroupUsersValue = rowMajorAgeGroupUsers.createCell(1);
		cellMajorAgeGroupUsersValue.setCellValue(reportFacebookOverview.getMajorAgeGroupReachedUsers().getInitialAgeGroup() + "-" +
				reportFacebookOverview.getMajorAgeGroupReachedUsers().getEndAgeGroup() + " / " + reportFacebookOverview.getMajorAgeGroupReachedUsers().getPercentage() + "%");
		cellMajorAgeGroupUsersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowMenUsers = sheetOverview.createRow(rowSheetOverviewCount);
		rowMenUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMenUsersTitle = rowMenUsers.createCell(0);
		cellMenUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.malecolon"));
		cellMenUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMenUsersValue = rowMenUsers.createCell(1);
		cellMenUsersValue.setCellValue(reportFacebookOverview.getMenReachedUsersPerc() + "%");
		cellMenUsersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowWomenUsers = sheetOverview.createRow(rowSheetOverviewCount);
		rowWomenUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellWomenUsersTitle = rowWomenUsers.createCell(0);
		cellWomenUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.femalecolon"));
		cellWomenUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellWomenUsersValue = rowWomenUsers.createCell(1);
		cellWomenUsersValue.setCellValue(reportFacebookOverview.getWomenReachedUsersPerc() + "%");
		cellWomenUsersValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addInfluentFans() {
		
		rowSheetOverviewCount += 2;
		Row rowInfluentFansTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowInfluentFansTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellInfluentFansTitle = rowInfluentFansTitle.createCell(0);
		cellInfluentFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.influentfanstitle"));
		cellInfluentFansTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 6));
		
		rowSheetOverviewCount++;
		Row rowInfluentFansSubTitle = sheetOverview.createRow(rowSheetOverviewCount);
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
		
		rowSheetOverviewCount++;
		for(FacebookInfluentialUserDTO facebookuser : reportFacebookOverview.getMostInfluentialFans()) {
			
			Row rowInfluentFansValues = sheetOverview.createRow(rowSheetOverviewCount);

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
			
			rowSheetOverviewCount++;
		}
	}
	
	private void addFansOnline() {
		
		rowSheetOverviewCount++;
		
		Row rowFansOnlineTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowFansOnlineTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFansOnlineTitle = rowFansOnlineTitle.createCell(0);
		cellFansOnlineTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.fansonlinetitle"));
		cellFansOnlineTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 5));
		
		rowSheetOverviewCount++;
		Row rowFansOnlinePerHourTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowFansOnlinePerHourTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTimeRangeTitle = rowFansOnlinePerHourTitle.createCell(0);
		cellTimeRangeTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.timerangetitle"));
		cellTimeRangeTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTimeRangeTotalTitle = rowFansOnlinePerHourTitle.createCell(1);
		cellTimeRangeTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.totalfans"));
		cellTimeRangeTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellTimeRangeAverageTitle = rowFansOnlinePerHourTitle.createCell(2);
		cellTimeRangeAverageTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.timerangeaveragetitle"));
		cellTimeRangeAverageTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		reportFacebookOverview.getFansOnlinePerHour().sort(Comparator.comparingLong(FanPageGrowth::getDate));
		reportFacebookOverview.getAverageFansOnlinePerHour().sort(Comparator.comparingLong(FanPageGrowth::getDate));
		
		rowSheetOverviewCount++;
		for(Integer hour = 0; hour < 24; hour++) {
			
			Row rowFansOnlinePerHourValues = sheetOverview.createRow(rowSheetOverviewCount);
			
			Cell cellTimeRangeValue = rowFansOnlinePerHourValues.createCell(0);
			cellTimeRangeValue.setCellValue(new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, hour, 0)) + " - " +
					new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, hour + 1, 0)));
			
			Cell cellTimeRangeTotalValue = rowFansOnlinePerHourValues.createCell(1);
			cellTimeRangeTotalValue.setCellValue(0);
			
			for(FanPageGrowth fanPageGrowth : reportFacebookOverview.getFansOnlinePerHour()) {
				
				if(hour.equals(fanPageGrowth.getDate().intValue())) {
					cellTimeRangeTotalValue.setCellValue(fanPageGrowth.getSum());
					break;
				}	
			}
			
			Cell cellTimeRangeAverageValue = rowFansOnlinePerHourValues.createCell(2);
			cellTimeRangeAverageValue.setCellValue(0);
			
			for(FanPageGrowth fanPageGrowth : reportFacebookOverview.getAverageFansOnlinePerHour()) {
				
				if(hour.equals(fanPageGrowth.getDate().intValue())) {
					cellTimeRangeAverageValue.setCellValue(fanPageGrowth.getSum());
					break;
				}
			}
			
			rowSheetOverviewCount++;
		}
		
		addFansOnlinePerWeekDay();
		
		rowSheetOverviewCount++;
		Row rowTimeMoreFansOnline = sheetOverview.createRow(rowSheetOverviewCount);
		rowTimeMoreFansOnline.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTimeMoreFansOnlineTitle = rowTimeMoreFansOnline.createCell(0);
		cellTimeMoreFansOnlineTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.timemorefansonlinetitle"));
		cellTimeMoreFansOnlineTitle.setCellStyle(cellStylePreAnalysis);
		
		String timeMoreFansOnlineFrom = new SimpleDateFormat("HH:mm").format(
				new Date(0, 0, 0, reportFacebookOverview.getTimeWithMoreFansOnline(), 0));
		
		String timeMoreFansOnlineUntil = new SimpleDateFormat("HH:mm").format(
				new Date(0, 0, 0, reportFacebookOverview.getTimeWithMoreFansOnline() + 1, 0));
		
		Cell cellTimeMoreFansOnlineValue = rowTimeMoreFansOnline.createCell(1);
		cellTimeMoreFansOnlineValue.setCellValue(timeMoreFansOnlineFrom + " - " + timeMoreFansOnlineUntil);
		cellTimeMoreFansOnlineValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowWeekDayMoreFansOnline = sheetOverview.createRow(rowSheetOverviewCount);
		rowWeekDayMoreFansOnline.setHeightInPoints(headerDataRowHeight);
		
		Cell cellWeekDayMoreFansOnlineTitle = rowWeekDayMoreFansOnline.createCell(0);
		cellWeekDayMoreFansOnlineTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.weekdaymorefansonlinetitle"));
		cellWeekDayMoreFansOnlineTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellWeekDayMoreFansOnlineValue = rowWeekDayMoreFansOnline.createCell(1);
		cellWeekDayMoreFansOnlineValue.setCellValue(WordUtils.capitalize(OpSocialBackApplication.utilFunctionsService.getFormattedWeekDay(reportFacebookOverview.getWeekDayWithMoreFansOnline())));
		cellWeekDayMoreFansOnlineValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addFansOnlinePerWeekDay() {
		
		rowSheetOverviewCount++;
		Row rowFansOnlinePerHourTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowFansOnlinePerHourTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTimeRangeTitle = rowFansOnlinePerHourTitle.createCell(0);
		cellTimeRangeTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.tw.report.weekday"));
		cellTimeRangeTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTimeRangeTotalTitle = rowFansOnlinePerHourTitle.createCell(1);
		cellTimeRangeTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.totalfans"));
		cellTimeRangeTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		
		for(FanPageGrowth fanPageGrowth : reportFacebookOverview.getFansOnlinePerWeekDay()) {
			
			Row rowTweetsPerWeekDayValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellWeekDayValue = rowTweetsPerWeekDayValues.createCell(0);
			cellWeekDayValue.setCellValue(OpSocialBackApplication.utilFunctionsService.getFormattedWeekDayShort(fanPageGrowth.getDate().intValue()));
			
			Cell cellTotalValue = rowTweetsPerWeekDayValues.createCell(1);
			cellTotalValue.setCellValue(fanPageGrowth.getSum());

			rowSheetOverviewCount++;
		}
	}
	
	private void addCountriesStats() {
		
		rowSheetOverviewCount += 2;
		
		Row rowCountriesMoreFansTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowCountriesMoreFansTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCountriesMoreFansTitle = rowCountriesMoreFansTitle.createCell(0);
		cellCountriesMoreFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.countriesmorefanstitle"));
		cellCountriesMoreFansTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 6));
		
		rowSheetOverviewCount++;
		Row rowCountriesMoreFansSubTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowCountriesMoreFansSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCountriesMoreFansCityTitle = rowCountriesMoreFansSubTitle.createCell(0);
		cellCountriesMoreFansCityTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.country"));
		cellCountriesMoreFansCityTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCountriesMoreFansTotalTitle = rowCountriesMoreFansSubTitle.createCell(1);
		cellCountriesMoreFansTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.fans"));
		cellCountriesMoreFansTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(ReportFacebookLocation reportFacebookLocation : reportFacebookOverview.getCountriesMoreFans()) {
			
			Row rowCountriesMoreFansValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellCountriesMoreFansCityValue = rowCountriesMoreFansValues.createCell(0);
			cellCountriesMoreFansCityValue.setCellValue(reportFacebookLocation.getLocation());
			
			Cell cellCountriesMoreFansTotalValue = rowCountriesMoreFansValues.createCell(1);
			cellCountriesMoreFansTotalValue.setCellValue(reportFacebookLocation.getValue());
			
			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount += 2;
		Row rowCountriesReachedTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowCountriesReachedTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCountriesReachedTitle = rowCountriesReachedTitle.createCell(0);
		cellCountriesReachedTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.countriesreachedtitle"));
		cellCountriesReachedTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 6));
		
		rowSheetOverviewCount++;
		Row rowCountriesReachedSubTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowCountriesReachedSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCountriesReachedCityTitle = rowCountriesReachedSubTitle.createCell(0);
		cellCountriesReachedCityTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.country"));
		cellCountriesReachedCityTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCountriesReachedTotalTitle = rowCountriesReachedSubTitle.createCell(1);
		cellCountriesReachedTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.users"));
		cellCountriesReachedTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(ReportFacebookLocation reportFacebookLocation : reportFacebookOverview.getCountriesMoreReached()) {
			
			Row rowCountriesReachedValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellCountriesReachedCityValue = rowCountriesReachedValues.createCell(0);
			cellCountriesReachedCityValue.setCellValue(reportFacebookLocation.getLocation());
			
			Cell cellCountriesReachedTotalValue = rowCountriesReachedValues.createCell(1);
			cellCountriesReachedTotalValue.setCellValue(reportFacebookLocation.getValue());
			
			rowSheetOverviewCount++;
		}
	}
	
	private void addCitiesStats() {
		
		rowSheetOverviewCount += 2;
		
		Row rowCitiesMoreFansTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowCitiesMoreFansTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCitiesMoreFansTitle = rowCitiesMoreFansTitle.createCell(0);
		cellCitiesMoreFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.citiesmorefanstitle"));
		cellCitiesMoreFansTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 6));
		
		rowSheetOverviewCount++;
		Row rowCitiesMoreFansSubTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowCitiesMoreFansSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCitiesMoreFansCityTitle = rowCitiesMoreFansSubTitle.createCell(0);
		cellCitiesMoreFansCityTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.city"));
		cellCitiesMoreFansCityTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCitiesMoreFansTotalTitle = rowCitiesMoreFansSubTitle.createCell(1);
		cellCitiesMoreFansTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.fans"));
		cellCitiesMoreFansTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(ReportFacebookLocation reportFacebookLocation : reportFacebookOverview.getCitiesMoreFans()) {
			
			Row rowCitiesMoreFansValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellCitiesMoreFansCityValue = rowCitiesMoreFansValues.createCell(0);
			cellCitiesMoreFansCityValue.setCellValue(reportFacebookLocation.getLocation());
			
			Cell cellCitiesMoreFansTotalValue = rowCitiesMoreFansValues.createCell(1);
			cellCitiesMoreFansTotalValue.setCellValue(reportFacebookLocation.getValue());
			
			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount += 2;
		Row rowCitiesReachedTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowCitiesReachedTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCitiesReachedTitle = rowCitiesReachedTitle.createCell(0);
		cellCitiesReachedTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.citiesreachedtitle"));
		cellCitiesReachedTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 6));
		
		rowSheetOverviewCount++;
		Row rowCitiesReachedSubTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowCitiesReachedSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCitiesReachedCityTitle = rowCitiesReachedSubTitle.createCell(0);
		cellCitiesReachedCityTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.city"));
		cellCitiesReachedCityTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCitiesReachedTotalTitle = rowCitiesReachedSubTitle.createCell(1);
		cellCitiesReachedTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.users"));
		cellCitiesReachedTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(ReportFacebookLocation reportFacebookLocation : reportFacebookOverview.getCitiesMoreReached()) {
			
			Row rowCitiesReachedValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellCitiesReachedCityValue = rowCitiesReachedValues.createCell(0);
			cellCitiesReachedCityValue.setCellValue(reportFacebookLocation.getLocation());
			
			Cell cellCitiesReachedTotalValue = rowCitiesReachedValues.createCell(1);
			cellCitiesReachedTotalValue.setCellValue(reportFacebookLocation.getValue());
			
			rowSheetOverviewCount++;
		}
	}
	
	private void addAboutYourFans() {
		
		rowSheetOverviewCount += 2;
		Row rowStatsTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowStatsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellStatsTitle = rowStatsTitle.createCell(0);
		cellStatsTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.statstitle"));
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
		
		rowSheetOverviewCount++;
		Row rowThirteenSeventeenValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellThirteenSeventeenTitle = rowThirteenSeventeenValues.createCell(0);
		cellThirteenSeventeenTitle.setCellValue("13-17");
		
		Cell cellThirteenSeventeenMaleValue = rowThirteenSeventeenValues.createCell(1);
		cellThirteenSeventeenMaleValue.setCellValue(reportFacebookOverview.getAboutYourFans().containsKey(ReportDetailedFacebookDTO.PAGE_FANS_MALE_13_AMONG_17) ? 
				reportFacebookOverview.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_MALE_13_AMONG_17) : 0);
		
		Cell cellThirteenSeventeenFemaleValue = rowThirteenSeventeenValues.createCell(2);
		cellThirteenSeventeenFemaleValue.setCellValue(reportFacebookOverview.getAboutYourFans().containsKey(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_13_AMONG_17) ? 
				reportFacebookOverview.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_13_AMONG_17): 0);
		
		rowSheetOverviewCount++;
		Row rowEighteenTwentyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellEighteenTwentyFourTitle = rowEighteenTwentyFourValues.createCell(0);
		cellEighteenTwentyFourTitle.setCellValue("18-24");
		
		Cell cellEighteenTwentyFourMaleValue = rowEighteenTwentyFourValues.createCell(1);
		cellEighteenTwentyFourMaleValue.setCellValue(reportFacebookOverview.getAboutYourFans().containsKey(ReportDetailedFacebookDTO.PAGE_FANS_MALE_18_AMONG_24) ? 
				reportFacebookOverview.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_MALE_18_AMONG_24) : 0);
		
		Cell cellEighteenTwentyFourFemaleValue = rowEighteenTwentyFourValues.createCell(2);
		cellEighteenTwentyFourFemaleValue.setCellValue(reportFacebookOverview.getAboutYourFans().containsKey(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_18_AMONG_24) ? 
				reportFacebookOverview.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_18_AMONG_24): 0);
		
		rowSheetOverviewCount++;
		Row rowTwentyFiveThirtyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellTwentyFiveThirtyFourTitle = rowTwentyFiveThirtyFourValues.createCell(0);
		cellTwentyFiveThirtyFourTitle.setCellValue("25-34");
		
		Cell cellTwentyFiveThirtyFourMaleValue = rowTwentyFiveThirtyFourValues.createCell(1);
		cellTwentyFiveThirtyFourMaleValue.setCellValue(reportFacebookOverview.getAboutYourFans().containsKey(ReportDetailedFacebookDTO.PAGE_FANS_MALE_25_AMONG_34) ? 
				reportFacebookOverview.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_MALE_25_AMONG_34) : 0);
		
		Cell cellTwentyFiveThirtyFourFemaleValue = rowTwentyFiveThirtyFourValues.createCell(2);
		cellTwentyFiveThirtyFourFemaleValue.setCellValue(reportFacebookOverview.getAboutYourFans().containsKey(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_25_AMONG_34) ? 
				reportFacebookOverview.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_25_AMONG_34): 0);
		
		rowSheetOverviewCount++;
		Row rowThirtyFiveFortyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellThirtyFiveFortyFourTitle = rowThirtyFiveFortyFourValues.createCell(0);
		cellThirtyFiveFortyFourTitle.setCellValue("35-44");
		
		Cell cellThirtyFiveFortyFourMaleValue = rowThirtyFiveFortyFourValues.createCell(1);
		cellThirtyFiveFortyFourMaleValue.setCellValue(reportFacebookOverview.getAboutYourFans().containsKey(ReportDetailedFacebookDTO.PAGE_FANS_MALE_35_AMONG_44) ? 
				reportFacebookOverview.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_MALE_35_AMONG_44) : 0);
		
		Cell cellThirtyFiveFortyFourFemaleValue = rowThirtyFiveFortyFourValues.createCell(2);
		cellThirtyFiveFortyFourFemaleValue.setCellValue(reportFacebookOverview.getAboutYourFans().containsKey(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_35_AMONG_44) ? 
				reportFacebookOverview.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_35_AMONG_44): 0);
		
		rowSheetOverviewCount++;
		Row rowFortyFiveFiftyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellFortyFiveFiftyFourTitle = rowFortyFiveFiftyFourValues.createCell(0);
		cellFortyFiveFiftyFourTitle.setCellValue("45-54");
		
		Cell cellFortyFiveFiftyFourMaleValue = rowFortyFiveFiftyFourValues.createCell(1);
		cellFortyFiveFiftyFourMaleValue.setCellValue(reportFacebookOverview.getAboutYourFans().containsKey(ReportDetailedFacebookDTO.PAGE_FANS_MALE_45_AMONG_54) ? 
				reportFacebookOverview.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_MALE_45_AMONG_54) : 0);
		
		Cell cellFortyFiveFiftyFourFemaleValue = rowFortyFiveFiftyFourValues.createCell(2);
		cellFortyFiveFiftyFourFemaleValue.setCellValue(reportFacebookOverview.getAboutYourFans().containsKey(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_45_AMONG_54) ? 
				reportFacebookOverview.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_45_AMONG_54): 0);
		
		rowSheetOverviewCount++;
		Row rowFiftyFiveSixtyFourValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellFiftyFiveSixtyFourTitle = rowFiftyFiveSixtyFourValues.createCell(0);
		cellFiftyFiveSixtyFourTitle.setCellValue("55-64");
		
		Cell cellFiftyFiveSixtyFourMaleValue = rowFiftyFiveSixtyFourValues.createCell(1);
		cellFiftyFiveSixtyFourMaleValue.setCellValue(reportFacebookOverview.getAboutYourFans().containsKey(ReportDetailedFacebookDTO.PAGE_FANS_MALE_55_AMONG_64) ? 
				reportFacebookOverview.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_MALE_55_AMONG_64) : 0);
		
		Cell cellFiftyFiveSixtyFourFemaleValue = rowFiftyFiveSixtyFourValues.createCell(2);
		cellFiftyFiveSixtyFourFemaleValue.setCellValue(reportFacebookOverview.getAboutYourFans().containsKey(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_55_AMONG_64) ? 
				reportFacebookOverview.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_55_AMONG_64): 0);
		
		rowSheetOverviewCount++;
		Row rowSixtyFourPlusValues = sheetOverview.createRow(rowSheetOverviewCount);
		
		Cell cellSixtyFivePlusTitle = rowSixtyFourPlusValues.createCell(0);
		cellSixtyFivePlusTitle.setCellValue("65+");
		
		Cell cellSixtyFivePlusMaleValue = rowSixtyFourPlusValues.createCell(1);
		cellSixtyFivePlusMaleValue.setCellValue(reportFacebookOverview.getAboutYourFans().containsKey(ReportDetailedFacebookDTO.PAGE_FANS_MALE_65_PLUS) ? 
				reportFacebookOverview.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_MALE_65_PLUS) : 0);
		
		Cell cellSixtyFivePlusFemaleValue = rowSixtyFourPlusValues.createCell(2);
		cellSixtyFivePlusFemaleValue.setCellValue(reportFacebookOverview.getAboutYourFans().containsKey(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_65_PLUS) ? 
				reportFacebookOverview.getAboutYourFans().get(ReportDetailedFacebookDTO.PAGE_FANS_FEMALE_65_PLUS): 0);
		
		rowSheetOverviewCount += 2;
		Row rowMajorAgeGroupUsers = sheetOverview.createRow(rowSheetOverviewCount);
		rowMajorAgeGroupUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMajorAgeGroupUsersTitle = rowMajorAgeGroupUsers.createCell(0);
		cellMajorAgeGroupUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.majoragegroupuserstitle"));
		cellMajorAgeGroupUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMajorAgeGroupUsersValue = rowMajorAgeGroupUsers.createCell(1);
		cellMajorAgeGroupUsersValue.setCellValue(reportFacebookOverview.getMajorAgeGroupFans().getInitialAgeGroup() + "-" +
				reportFacebookOverview.getMajorAgeGroupFans().getEndAgeGroup() + " / " + reportFacebookOverview.getMajorAgeGroupFans().getPercentage() + "%");
		cellMajorAgeGroupUsersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowMenUsers = sheetOverview.createRow(rowSheetOverviewCount);
		rowMenUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMenUsersTitle = rowMenUsers.createCell(0);
		cellMenUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.malecolon"));
		cellMenUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMenUsersValue = rowMenUsers.createCell(1);
		cellMenUsersValue.setCellValue(reportFacebookOverview.getMenFansPerc() + "%");
		cellMenUsersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowWomenUsers = sheetOverview.createRow(rowSheetOverviewCount);
		rowWomenUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellWomenUsersTitle = rowWomenUsers.createCell(0);
		cellWomenUsersTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.femalecolon"));
		cellWomenUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellWomenUsersValue = rowWomenUsers.createCell(1);
		cellWomenUsersValue.setCellValue(reportFacebookOverview.getWomenFansPerc() + "%");
		cellWomenUsersValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addFansLikeSource() {
		
		rowSheetOverviewCount += 2;
		Row rowFansLikeSourceTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowFansLikeSourceTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFansLikeSourceTitle = rowFansLikeSourceTitle.createCell(0);
		cellFansLikeSourceTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.fanslikesourcetitle"));
		cellFansLikeSourceTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowFansLikeSourceSubTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowFansLikeSourceSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFansLikeSourceSubTitle = rowFansLikeSourceSubTitle.createCell(0);
		cellFansLikeSourceSubTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.fanslikesourcesubtitle"));
		cellFansLikeSourceSubTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellFansLikeSourceTotalTitle = rowFansLikeSourceSubTitle.createCell(1);
		cellFansLikeSourceTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.total"));
		cellFansLikeSourceTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(ReportFacebookLocation reportFacebookLocation : reportFacebookOverview.getFansLikeSource()) {
			
			Row rowFansLikeSourceValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellSourceValue = rowFansLikeSourceValues.createCell(0);
			cellSourceValue.setCellValue(OpSocialBackApplication.utilFunctionsService.getLikeSourceName(reportFacebookLocation.getLocation()));
			
			Cell cellSourceTotalValue = rowFansLikeSourceValues.createCell(1);
			cellSourceTotalValue.setCellValue(reportFacebookLocation.getValue());
			
			rowSheetOverviewCount++;
		}
	}
	
	private void addReach() {
		
		rowSheetOverviewCount += 2;
		
		Row rowTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTitle = rowTitle.createCell(0);
		cellTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.reachdatetitle"));
		cellTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellDateTitle = rowTitleItems.createCell(0);
		cellDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.date"));
		cellDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTotalTitle = rowTitleItems.createCell(1);
		cellTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.reachtitle"));
		cellTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(FanPageGrowth fanPageGrowth : reportFacebookOverview.getReachByDay()) {
			
			Row rowFanPageGrowthValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellDateValue = rowFanPageGrowthValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(fanPageGrowth.getDate() * 1000L)));
			
			Cell cellTotalValue = rowFanPageGrowthValues.createCell(1);
			cellTotalValue.setCellValue(fanPageGrowth.getSum());

			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowFirstMetric = sheetOverview.createRow(rowSheetOverviewCount);
		rowFirstMetric.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFirstMetricTitle = rowFirstMetric.createCell(0);
		cellFirstMetricTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.totaluserreached"));
		cellFirstMetricTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellFirstMetricValue = rowFirstMetric.createCell(1);
		cellFirstMetricValue.setCellValue(reportFacebookOverview.getTotalReach());
		cellFirstMetricValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowSecondMetric = sheetOverview.createRow(rowSheetOverviewCount);
		rowSecondMetric.setHeightInPoints(headerDataRowHeight);
		
		Cell cellSecondMetricTitle = rowSecondMetric.createCell(0);
		cellSecondMetricTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.growthcolon"));
		cellSecondMetricTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellSecondMetricValue = rowSecondMetric.createCell(1);
		cellSecondMetricValue.setCellValue(reportFacebookOverview.getTotalReachPerformance() + "%");
		cellSecondMetricValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addImpressions() {
		
		rowSheetOverviewCount += 2;
		
		Row rowTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTitle = rowTitle.createCell(0);
		cellTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.pageimpressions"));
		cellTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellDateTitle = rowTitleItems.createCell(0);
		cellDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.date"));
		cellDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTotalTitle = rowTitleItems.createCell(1);
		cellTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.impressions"));
		cellTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(FanPageGrowth fanPageGrowth : reportFacebookOverview.getImpressionsByDay()) {
			
			Row rowFanPageGrowthValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellDateValue = rowFanPageGrowthValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(fanPageGrowth.getDate() * 1000L)));
			
			Cell cellTotalValue = rowFanPageGrowthValues.createCell(1);
			cellTotalValue.setCellValue(fanPageGrowth.getSum());

			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowFirstMetric = sheetOverview.createRow(rowSheetOverviewCount);
		rowFirstMetric.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFirstMetricTitle = rowFirstMetric.createCell(0);
		cellFirstMetricTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.totalimpressions"));
		cellFirstMetricTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellFirstMetricValue = rowFirstMetric.createCell(1);
		cellFirstMetricValue.setCellValue(reportFacebookOverview.getTotalImpressions());
		cellFirstMetricValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowSecondMetric = sheetOverview.createRow(rowSheetOverviewCount);
		rowSecondMetric.setHeightInPoints(headerDataRowHeight);
		
		Cell cellSecondMetricTitle = rowSecondMetric.createCell(0);
		cellSecondMetricTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.growthcolon"));
		cellSecondMetricTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellSecondMetricValue = rowSecondMetric.createCell(1);
		cellSecondMetricValue.setCellValue(reportFacebookOverview.getTotalImpressionsPerformance() + "%");
		cellSecondMetricValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addEngagement() {
		
		rowSheetOverviewCount += 2;
		
		Row rowTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTitle = rowTitle.createCell(0);
		cellTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.pageengagement"));
		cellTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellDateTitle = rowTitleItems.createCell(0);
		cellDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.date"));
		cellDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTotalTitle = rowTitleItems.createCell(1);
		cellTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.engagement"));
		cellTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(FanPageGrowth fanPageGrowth : reportFacebookOverview.getEngagementByDay()) {
			
			Row rowFanPageGrowthValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellDateValue = rowFanPageGrowthValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(fanPageGrowth.getDate() * 1000L)));
			
			Cell cellTotalValue = rowFanPageGrowthValues.createCell(1);
			cellTotalValue.setCellValue(fanPageGrowth.getSum());

			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowFirstMetric = sheetOverview.createRow(rowSheetOverviewCount);
		rowFirstMetric.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFirstMetricTitle = rowFirstMetric.createCell(0);
		cellFirstMetricTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.totalengagement"));
		cellFirstMetricTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellFirstMetricValue = rowFirstMetric.createCell(1);
		cellFirstMetricValue.setCellValue(reportFacebookOverview.getTotalEngagement());
		cellFirstMetricValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowSecondMetric = sheetOverview.createRow(rowSheetOverviewCount);
		rowSecondMetric.setHeightInPoints(headerDataRowHeight);
		
		Cell cellSecondMetricTitle = rowSecondMetric.createCell(0);
		cellSecondMetricTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.growthcolon"));
		cellSecondMetricTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellSecondMetricValue = rowSecondMetric.createCell(1);
		cellSecondMetricValue.setCellValue(reportFacebookOverview.getTotalEngagementPerformance() + "%");
		cellSecondMetricValue.setCellStyle(cellStylePreAnalysisResult);
	}
	
	private void addFanPageGrowth() {
		
		rowSheetOverviewCount = 2;
		
		Row rowFanPageGrowthTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowFanPageGrowthTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFanPageGrowthTitle = rowFanPageGrowthTitle.createCell(0);
		cellFanPageGrowthTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.fanpagegrowthtitle"));
		cellFanPageGrowthTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowFanPageGrowthTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowFanPageGrowthTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFanPageDateTitle = rowFanPageGrowthTitleItems.createCell(0);
		cellFanPageDateTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.date"));
		cellFanPageDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellFanPageTotalTitle = rowFanPageGrowthTitleItems.createCell(1);
		cellFanPageTotalTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.totalfans"));
		cellFanPageTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(FanPageGrowth fanPageGrowth : reportFacebookOverview.getFanPageGrowth()) {
			
			Row rowFanPageGrowthValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellDateValue = rowFanPageGrowthValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(fanPageGrowth.getDate() * 1000L)));
			
			Cell cellTotalValue = rowFanPageGrowthValues.createCell(1);
			cellTotalValue.setCellValue(fanPageGrowth.getSum());

			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowGrowthFansPeak = sheetOverview.createRow(rowSheetOverviewCount);
		rowGrowthFansPeak.setHeightInPoints(headerDataRowHeight);
		
		Cell cellGrowthFansPeakTitle = rowGrowthFansPeak.createCell(0);
		cellGrowthFansPeakTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.growthfanspeaktitle"));
		cellGrowthFansPeakTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellGrowthFansPeakValue = rowGrowthFansPeak.createCell(1);
		cellGrowthFansPeakValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(reportFacebookOverview.getMajorFanPageGrowth().getDate() * 1000L)));
		cellGrowthFansPeakValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowConqueredFans = sheetOverview.createRow(rowSheetOverviewCount);
		rowConqueredFans.setHeightInPoints(headerDataRowHeight);
		
		Cell cellConqueredFansTitle = rowConqueredFans.createCell(0);
		cellConqueredFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.conqueredfanstitle"));
		cellConqueredFansTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellConqueredFansValue = rowConqueredFans.createCell(1);
		cellConqueredFansValue.setCellValue(reportFacebookOverview.getConqueredFans());
		cellConqueredFansValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowLostFans = sheetOverview.createRow(rowSheetOverviewCount);
		rowLostFans.setHeightInPoints(headerDataRowHeight);
		
		Cell cellLostFansTitle = rowLostFans.createCell(0);
		cellLostFansTitle.setCellValue(OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.lostfanstitle"));
		cellLostFansTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellLostFansValue = rowLostFans.createCell(1);
		cellLostFansValue.setCellValue(reportFacebookOverview.getLostFans());
		cellLostFansValue.setCellStyle(cellStylePreAnalysisResult);
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
	
	public static String getStoryTypeName(String storyType) {
		
		if(storyType.equals(ReportDetailedFacebookDTO.PAGE_STORIES_BY_MENTION)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.storytypemention");
		} else if(storyType.equals(ReportDetailedFacebookDTO.PAGE_STORIES_BY_CHECKIN)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.storytypecheckin");
		} else if(storyType.equals(ReportDetailedFacebookDTO.PAGE_STORIES_BY_USER_POST)) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("xls.facebook.report.storytypepost");
		} else {
			return "";
		}
		
	}
}
