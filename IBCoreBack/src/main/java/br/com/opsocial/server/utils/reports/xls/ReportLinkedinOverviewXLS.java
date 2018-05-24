package br.com.opsocial.server.utils.reports.xls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

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
import br.com.opsocial.client.entity.report.linkedin.ReportLinkedinOverviewDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.report.SumByDate;
import br.com.opsocial.server.utils.aws.S3Integration;
import br.com.opsocial.server.utils.reports.linkedin.LinkedinCompanyImpressionsByDay;
import br.com.opsocial.server.utils.reports.linkedin.LinkedinFollowers;
import br.com.opsocial.server.utils.reports.linkedin.LinkedinObject;
import br.com.opsocial.server.utils.reports.linkedin.LinkedinStatusUpdate;

public class ReportLinkedinOverviewXLS {
	
	private ReportLinkedinOverviewDTO reportLinkedinOverviewDTO;
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
	
	private Long impressionCountSum;
	private Long clickCountSum;
	private Long likeCountSum;
	private Long commentCountSum;
	private Long shareCountSum;
	private Long totalPostsSum;

	public ReportLinkedinOverviewXLS(ReportLinkedinOverviewDTO reportLinkedinOverviewDTO) {
		this.reportLinkedinOverviewDTO = reportLinkedinOverviewDTO;
		this.profile = reportLinkedinOverviewDTO.getProfile();
		
		this.impressionCountSum = reportLinkedinOverviewDTO.getLinkedinStatusUpdate().stream().filter(o -> o.getImpressionCount() > 0).mapToLong(LinkedinStatusUpdate::getImpressionCount).sum();
		this.clickCountSum = reportLinkedinOverviewDTO.getLinkedinStatusUpdate().stream().filter(o -> o.getClickCount() > 0).mapToLong(LinkedinStatusUpdate::getClickCount).sum();
		this.likeCountSum = reportLinkedinOverviewDTO.getLinkedinStatusUpdate().stream().filter(o -> o.getLikeCount() > 0).mapToLong(LinkedinStatusUpdate::getLikeCount).sum();
		this.commentCountSum = reportLinkedinOverviewDTO.getLinkedinStatusUpdate().stream().filter(o -> o.getCommentCount() > 0).mapToLong(LinkedinStatusUpdate::getCommentCount).sum();
		this.shareCountSum = reportLinkedinOverviewDTO.getLinkedinStatusUpdate().stream().filter(o -> o.getShareCount() > 0).mapToLong(LinkedinStatusUpdate::getShareCount).sum();
		this.totalPostsSum = 0L;
	}
	
	public void createXLS() {

		wb = new HSSFWorkbook();

		initiateDefautStyles();

		this.reportsPath = OpSocialBackApplication.GET_DIR_REPORTS_XLS;
		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".xls");

//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		SimpleDateFormat dateFormatToFileName = new SimpleDateFormat("dd-MM-yyyy");

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat dateFormatToFileName = new SimpleDateFormat("MM/dd/yyyy");

		startDate = dateFormat.format(new Date(reportLinkedinOverviewDTO.getDateFrom()));
		endDate = dateFormat.format(new Date(reportLinkedinOverviewDTO.getDateUntil()));

//		sheetOverview = wb.createSheet("LinkedIn Visão Geral");
		sheetOverview = wb.createSheet("LinkedIn Overview");
//		sheetTweets = wb.createSheet("Tweets");

		headerDataRowHeight = 1.2F * sheetOverview.getDefaultRowHeightInPoints();

//		addMainHeader(sheetOverview, profile.getScreenName() + " - Relatório LinkedIn - Visão Geral - Período de " + startDate + " a " + endDate, 4);
		addMainHeader(sheetOverview, profile.getScreenName() + " - LinkedIn Report - Overview - " + startDate + " a " + endDate, 4);

		rowSheetOverviewCount = 2;
		
		addGeneralData();
		addPeriodPageGrowth();
		addPostsOnPeriod();
		addPostsImpression();
		addEngagementByPost();
		addUserPosition();
		addUserArea();
		addUserIndustry();
		addCompanyBySize();
		addTopCountries();

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
			
//			formattedFileName = profile.getScreenName() + "-linkedin-visao-geral-" 
//					+ dateFormatToFileName.format(new Date(reportLinkedinOverviewDTO.getDateFrom())) + "--" 
//					+ dateFormatToFileName.format(new Date(reportLinkedinOverviewDTO.getDateUntil()));

			formattedFileName = profile.getScreenName() + "-linkedin-overview-" 
					+ dateFormatToFileName.format(new Date(reportLinkedinOverviewDTO.getDateFrom())) + "--" 
					+ dateFormatToFileName.format(new Date(reportLinkedinOverviewDTO.getDateUntil()));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addTopCountries() {
		
		rowSheetOverviewCount += 1;
		
		Row rowGeneralDataTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowGeneralDataTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellGeneralDataTitle = rowGeneralDataTitle.createCell(0);
//		cellGeneralDataTitle.setCellValue("TOP PAÍSES");
		cellGeneralDataTitle.setCellValue("TOP COUNTRIES");
		cellGeneralDataTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowGeneralDataTitleItens = sheetOverview.createRow(rowSheetOverviewCount);
		rowGeneralDataTitleItens.setHeightInPoints(headerDataRowHeight);
		
		Cell cellGeneralDataInteractionTitle = rowGeneralDataTitleItens.createCell(0);
//		cellGeneralDataInteractionTitle.setCellValue("País");
		cellGeneralDataInteractionTitle.setCellValue("Country");
		cellGeneralDataInteractionTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellGeneralDataTotalTitle = rowGeneralDataTitleItens.createCell(1);
//		cellGeneralDataTotalTitle.setCellValue("Usuários");
		cellGeneralDataTotalTitle.setCellValue("Total users");
		cellGeneralDataTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		
		for (Entry<String, Integer> entry : reportLinkedinOverviewDTO.getLinkedinFollowersByCountry().entrySet()) {
			
			Row rowGeneralDataValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellInteractionValue = rowGeneralDataValues.createCell(0);
			cellInteractionValue.setCellValue(entry.getKey());
			
			Cell cellTotalValue = rowGeneralDataValues.createCell(1);
			cellTotalValue.setCellValue(entry.getValue());
			cellTotalValue.setCellStyle(cellDataPercentage);
			
			rowSheetOverviewCount++;
		}
	
	}

	private void addCompanyBySize() {
		
		rowSheetOverviewCount += 1;
		
		Row rowGeneralDataTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowGeneralDataTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellGeneralDataTitle = rowGeneralDataTitle.createCell(0);
//		cellGeneralDataTitle.setCellValue("EMPRESAS POR TAMANHO");
		cellGeneralDataTitle.setCellValue("COMPANIES BY SIZE");
		cellGeneralDataTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowGeneralDataTitleItens = sheetOverview.createRow(rowSheetOverviewCount);
		rowGeneralDataTitleItens.setHeightInPoints(headerDataRowHeight);
		
		Cell cellGeneralDataInteractionTitle = rowGeneralDataTitleItens.createCell(0);
//		cellGeneralDataInteractionTitle.setCellValue("Número de empregados");
		cellGeneralDataInteractionTitle.setCellValue("Number of employees");
		cellGeneralDataInteractionTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellGeneralDataTotalTitle = rowGeneralDataTitleItens.createCell(1);
		cellGeneralDataTotalTitle.setCellValue("Total");
		cellGeneralDataTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		
		List<LinkedinObject> linkedinObjectList = reportLinkedinOverviewDTO.getFollowStatistics().getCompanySizes().getValues();
		
		for (LinkedinObject linkedinObject : linkedinObjectList) {
			
			switch (linkedinObject.getEntryKey()) {
				case "A": linkedinObject.setEntryKey("Self"); break;
				case "B": linkedinObject.setEntryKey("1-10"); break;
				case "C": linkedinObject.setEntryKey("11-50"); break;
				case "D": linkedinObject.setEntryKey("51-200"); break;
				case "E": linkedinObject.setEntryKey("201-500"); break;
				case "F": linkedinObject.setEntryKey("501-1K"); break;
				case "G": linkedinObject.setEntryKey("1K-5K"); break;
				case "H": linkedinObject.setEntryKey("5K-10K"); break;
				case "I": linkedinObject.setEntryKey("10K+"); break;
			}
			
			Row rowGeneralDataValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellInteractionValue = rowGeneralDataValues.createCell(0);
			cellInteractionValue.setCellValue(linkedinObject.getEntryKey());
			
			Cell cellTotalValue = rowGeneralDataValues.createCell(1);
			cellTotalValue.setCellValue(linkedinObject.getEntryValue());
			cellTotalValue.setCellStyle(cellDataPercentage);
			
			rowSheetOverviewCount++;
		}
	}

	private void addUserIndustry() {
		
		rowSheetOverviewCount += 1;
		
		Row rowGeneralDataTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowGeneralDataTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellGeneralDataTitle = rowGeneralDataTitle.createCell(0);
//		cellGeneralDataTitle.setCellValue("INDÚSTRIA");
		cellGeneralDataTitle.setCellValue("INDUSTRY");
		cellGeneralDataTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowGeneralDataTitleItens = sheetOverview.createRow(rowSheetOverviewCount);
		rowGeneralDataTitleItens.setHeightInPoints(headerDataRowHeight);
		
		Cell cellGeneralDataInteractionTitle = rowGeneralDataTitleItens.createCell(0);
//		cellGeneralDataInteractionTitle.setCellValue("Tipo de indústria");
		cellGeneralDataInteractionTitle.setCellValue("Industry type");
		cellGeneralDataInteractionTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellGeneralDataTotalTitle = rowGeneralDataTitleItens.createCell(1);
		cellGeneralDataTotalTitle.setCellValue("Total");
		cellGeneralDataTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellGeneralDataPercentTitle = rowGeneralDataTitleItens.createCell(2);
		cellGeneralDataPercentTitle.setCellValue("%");
		cellGeneralDataPercentTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		
		List<LinkedinObject> linkedinObjectList = reportLinkedinOverviewDTO.getFollowStatistics().getIndustries().getValues();
		
		for (LinkedinObject linkedinObject : linkedinObjectList) {
			
			Row rowGeneralDataValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellInteractionValue = rowGeneralDataValues.createCell(0);
			cellInteractionValue.setCellValue(linkedinObject.getEntryKey());
			
			Cell cellTotalValue = rowGeneralDataValues.createCell(1);
			cellTotalValue.setCellValue(linkedinObject.getEntryValue());
			cellTotalValue.setCellStyle(cellDataPercentage);
			
			int total = linkedinObjectList.stream().
					filter(o -> (int) Long.parseLong(o.getEntryValue()) > 0).mapToInt(o -> (int) Long.parseLong(o.getEntryValue())).sum();
			
			Cell cellPercentageValue = rowGeneralDataValues.createCell(2);
			cellPercentageValue.setCellValue(String.format("%.2f", ((double) Long.parseLong(linkedinObject.getEntryValue()) / total * 100)) + "%");
			cellPercentageValue.setCellStyle(cellDataPercentage);
			
			rowSheetOverviewCount++;
		}
	
	}

	private void addUserArea() {
		
		rowSheetOverviewCount += 1;
		
		Row rowGeneralDataTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowGeneralDataTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellGeneralDataTitle = rowGeneralDataTitle.createCell(0);
//		cellGeneralDataTitle.setCellValue("ÁREA DE ATUAÇÃO");
		cellGeneralDataTitle.setCellValue("JOB FUNCTION");
		cellGeneralDataTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowGeneralDataTitleItens = sheetOverview.createRow(rowSheetOverviewCount);
		rowGeneralDataTitleItens.setHeightInPoints(headerDataRowHeight);
		
		Cell cellGeneralDataInteractionTitle = rowGeneralDataTitleItens.createCell(0);
//		cellGeneralDataInteractionTitle.setCellValue("Posição");
		cellGeneralDataInteractionTitle.setCellValue("Area");
		cellGeneralDataInteractionTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellGeneralDataTotalTitle = rowGeneralDataTitleItens.createCell(1);
		cellGeneralDataTotalTitle.setCellValue("Total");
		cellGeneralDataTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellGeneralDataPercentTitle = rowGeneralDataTitleItens.createCell(2);
		cellGeneralDataPercentTitle.setCellValue("%");
		cellGeneralDataPercentTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		
		List<LinkedinObject> linkedinObjectList = reportLinkedinOverviewDTO.getFollowStatistics().getFunctions().getValues();
		
		for (LinkedinObject linkedinObject : linkedinObjectList) {
			switch (linkedinObject.getEntryKey()) {
				case "-1": linkedinObject.setEntryKey("None"); break;
				case "1": linkedinObject.setEntryKey("Accounting"); break;
				case "2": linkedinObject.setEntryKey("Administrative"); break;
				case "3": linkedinObject.setEntryKey("Arts and Design"); break;
				case "4": linkedinObject.setEntryKey("Business Development"); break;
				case "5": linkedinObject.setEntryKey("Community & Social Services"); break;
				case "6": linkedinObject.setEntryKey("Consulting"); break;
				case "7": linkedinObject.setEntryKey("Education"); break;
				case "8": linkedinObject.setEntryKey("Engineering"); break;
				case "9": linkedinObject.setEntryKey("Entrepreneurship"); break;
				case "10": linkedinObject.setEntryKey("Finance"); break;
				case "11": linkedinObject.setEntryKey("Healthcare Services"); break;
				case "12": linkedinObject.setEntryKey("Human Resources"); break;
				case "13": linkedinObject.setEntryKey("Information Technology"); break;
				case "14": linkedinObject.setEntryKey("Legal"); break;
				case "15": linkedinObject.setEntryKey("Marketing"); break;
				case "16": linkedinObject.setEntryKey("Media & Communications"); break;
				case "17": linkedinObject.setEntryKey("Military & Protective Services"); break;
				case "18": linkedinObject.setEntryKey("Operations"); break;
				case "19": linkedinObject.setEntryKey("Product Management"); break;
				case "20": linkedinObject.setEntryKey("Program & Product Management"); break;
				case "21": linkedinObject.setEntryKey("Purchasing"); break;
				case "22": linkedinObject.setEntryKey("Quality Assurance"); break;
				case "23": linkedinObject.setEntryKey("Real Estate"); break;
				case "24": linkedinObject.setEntryKey("Rersearch"); break;
				case "25": linkedinObject.setEntryKey("Sales"); break;
				case "26": linkedinObject.setEntryKey("Support"); break;
			}
			
			Row rowGeneralDataValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellInteractionValue = rowGeneralDataValues.createCell(0);
			cellInteractionValue.setCellValue(linkedinObject.getEntryKey());
			
			Cell cellTotalValue = rowGeneralDataValues.createCell(1);
			cellTotalValue.setCellValue(linkedinObject.getEntryValueTotal());
			cellTotalValue.setCellStyle(cellDataPercentage);
			
			Cell cellPercentageValue = rowGeneralDataValues.createCell(2);
			cellPercentageValue.setCellValue(linkedinObject.getEntryValue());
			cellPercentageValue.setCellStyle(cellDataPercentage);
			
			rowSheetOverviewCount++;
		}
	}

	private void addUserPosition() {
		
		rowSheetOverviewCount += 2;
		
		Row rowGeneralDataTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowGeneralDataTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellGeneralDataTitle = rowGeneralDataTitle.createCell(0);
//		cellGeneralDataTitle.setCellValue("POSIÇÃO DO USUÁRIO");
		cellGeneralDataTitle.setCellValue("SENIORITY LEVEL");
		cellGeneralDataTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowGeneralDataTitleItens = sheetOverview.createRow(rowSheetOverviewCount);
		rowGeneralDataTitleItens.setHeightInPoints(headerDataRowHeight);
		
		Cell cellGeneralDataInteractionTitle = rowGeneralDataTitleItens.createCell(0);
//		cellGeneralDataInteractionTitle.setCellValue("Posição");
		cellGeneralDataInteractionTitle.setCellValue("Level");
		cellGeneralDataInteractionTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellGeneralDataTotalTitle = rowGeneralDataTitleItens.createCell(1);
		cellGeneralDataTotalTitle.setCellValue("Total");
		cellGeneralDataTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellGeneralDataPercentTitle = rowGeneralDataTitleItens.createCell(2);
		cellGeneralDataPercentTitle.setCellValue("%");
		cellGeneralDataPercentTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		
		List<LinkedinObject> linkedinObjectList = reportLinkedinOverviewDTO.getFollowStatistics().getSeniorities().getValues();
		
		for (LinkedinObject linkedinObject : linkedinObjectList) {
			switch (linkedinObject.getEntryKey()) {
				case "1": linkedinObject.setEntryKey("Unpaid"); break;
				case "2": linkedinObject.setEntryKey("Training"); break;
				case "3": linkedinObject.setEntryKey("Entry-level"); break;
				case "4": linkedinObject.setEntryKey("Senior"); break;
				case "5": linkedinObject.setEntryKey("Manager"); break;
				case "6": linkedinObject.setEntryKey("Director"); break;
				case "7": linkedinObject.setEntryKey("Vice President (VP)"); break;
				case "8": linkedinObject.setEntryKey("Chief X Officer (CxO)"); break;
				case "9": linkedinObject.setEntryKey("Partner"); break;
				case "10": linkedinObject.setEntryKey("Owner"); break;
			}
			
			Row rowGeneralDataValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellInteractionValue = rowGeneralDataValues.createCell(0);
			cellInteractionValue.setCellValue(linkedinObject.getEntryKey());
			
			Cell cellTotalValue = rowGeneralDataValues.createCell(1);
			cellTotalValue.setCellValue(linkedinObject.getEntryValueTotal());
			cellTotalValue.setCellStyle(cellDataPercentage);
			
			Cell cellPercentageValue = rowGeneralDataValues.createCell(2);
			cellPercentageValue.setCellValue(linkedinObject.getEntryValue());
			cellPercentageValue.setCellStyle(cellDataPercentage);
			
			rowSheetOverviewCount++;
		}
	}

	private void addEngagementByPost() {
		
		rowSheetOverviewCount += 2;
		
		Row rowPostsEngagementOnPeriodTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowPostsEngagementOnPeriodTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsEngagementOnPeriodTitle = rowPostsEngagementOnPeriodTitle.createCell(0);
//		cellPostsEngagementOnPeriodTitle.setCellValue("ENVOLVIMENTO DO PÚBLICO POR TIPO DE INTERAÇÃO");
		cellPostsEngagementOnPeriodTitle.setCellValue("ENGAGEMENT BY INTERACTION TYPE");
		cellPostsEngagementOnPeriodTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowPostsEngagementOnPeriodTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowPostsEngagementOnPeriodTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsEngagementOnPeriodDateTitle = rowPostsEngagementOnPeriodTitleItems.createCell(0);
//		cellPostsEngagementOnPeriodDateTitle.setCellValue("Data");
		cellPostsEngagementOnPeriodDateTitle.setCellValue("Date");
		cellPostsEngagementOnPeriodDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellLikePostsEngagementOnPeriodTitle = rowPostsEngagementOnPeriodTitleItems.createCell(1);
//		cellLikePostsEngagementOnPeriodTitle.setCellValue("Curtir");
		cellLikePostsEngagementOnPeriodTitle.setCellValue("Like");
		cellLikePostsEngagementOnPeriodTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellCommentPostsEngagementOnPeriodTitle = rowPostsEngagementOnPeriodTitleItems.createCell(2);
//		cellCommentPostsEngagementOnPeriodTitle.setCellValue("Comentário");
		cellCommentPostsEngagementOnPeriodTitle.setCellValue("Comment");
		cellCommentPostsEngagementOnPeriodTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellSharePostsEngagementOnPeriodTitle = rowPostsEngagementOnPeriodTitleItems.createCell(3);
//		cellSharePostsEngagementOnPeriodTitle.setCellValue("Compartilhamento");
		cellSharePostsEngagementOnPeriodTitle.setCellValue("Share");
		cellSharePostsEngagementOnPeriodTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellTotalPostsEngagementOnPeriodTitle = rowPostsEngagementOnPeriodTitleItems.createCell(4);
		cellTotalPostsEngagementOnPeriodTitle.setCellValue("Total");
		cellTotalPostsEngagementOnPeriodTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(LinkedinStatusUpdate linkedinStatusUpdate : reportLinkedinOverviewDTO.getLinkedinStatusUpdate()) {
			
			Row rowPostsOnPeriodValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellDateValue = rowPostsOnPeriodValues.createCell(0);
//			cellDateValue.setCellValue(new SimpleDateFormat("dd/MM/yy").format(new Date(linkedinStatusUpdate.getTime())));
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(linkedinStatusUpdate.getTime())));
			
			Cell cellLikeValue = rowPostsOnPeriodValues.createCell(1);
			cellLikeValue.setCellValue(linkedinStatusUpdate.getLikeCount());
			
			Cell cellCommentValue = rowPostsOnPeriodValues.createCell(2);
			cellCommentValue.setCellValue(linkedinStatusUpdate.getCommentCount());

			Cell cellShareValue = rowPostsOnPeriodValues.createCell(3);
			cellShareValue.setCellValue(linkedinStatusUpdate.getShareCount());
			
			Cell cellTotalValue = rowPostsOnPeriodValues.createCell(4);
			cellTotalValue.setCellValue(linkedinStatusUpdate.getLikeCount() + 
										linkedinStatusUpdate.getCommentCount() +
										linkedinStatusUpdate.getShareCount());
			
			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowTotalLikeEngagement = sheetOverview.createRow(rowSheetOverviewCount);
		rowTotalLikeEngagement.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalLikeEngagement = rowTotalLikeEngagement.createCell(0);
//		cellTotalLikeEngagement.setCellValue("Total curtir");
		cellTotalLikeEngagement.setCellValue("Total likes");
		cellTotalLikeEngagement.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalLikeEngagementValue = rowTotalLikeEngagement.createCell(1);
		cellTotalLikeEngagementValue.setCellValue(this.likeCountSum);
		cellTotalLikeEngagementValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowTotalCommentEngagement = sheetOverview.createRow(rowSheetOverviewCount);
		rowTotalCommentEngagement.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalCommentEngagementTitle = rowTotalCommentEngagement.createCell(0);
//		cellTotalCommentEngagementTitle.setCellValue("Total comentários");
		cellTotalCommentEngagementTitle.setCellValue("Total comments");
		cellTotalCommentEngagementTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalCommentEngagementValue = rowTotalCommentEngagement.createCell(1);
		cellTotalCommentEngagementValue.setCellValue(this.commentCountSum);
		cellTotalCommentEngagementValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowTotalShareEngagement = sheetOverview.createRow(rowSheetOverviewCount);
		rowTotalShareEngagement.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalShareEngagementTitle = rowTotalShareEngagement.createCell(0);
//		cellTotalShareEngagementTitle.setCellValue("Total compartilhamentos");
		cellTotalShareEngagementTitle.setCellValue("Total shares");
		cellTotalShareEngagementTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalShareEngagementValue = rowTotalShareEngagement.createCell(1);
		cellTotalShareEngagementValue.setCellValue(this.shareCountSum);
		cellTotalShareEngagementValue.setCellStyle(cellStylePreAnalysisResult);
	}

	private void addPostsImpression() {
		
		rowSheetOverviewCount += 2;
		
		Row rowPostsImpressionOnPeriodTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowPostsImpressionOnPeriodTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsImpressionOnPeriodTitle = rowPostsImpressionOnPeriodTitle.createCell(0);
//		cellPostsImpressionOnPeriodTitle.setCellValue("IMPRESSÕES DAS PUBLICAÇÕES");
		cellPostsImpressionOnPeriodTitle.setCellValue("IMPRESSIONS PER POST");
		cellPostsImpressionOnPeriodTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowPostsImpressionOnPeriodTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowPostsImpressionOnPeriodTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsImpressionOnPeriodDateTitle = rowPostsImpressionOnPeriodTitleItems.createCell(0);
//		cellPostsImpressionOnPeriodDateTitle.setCellValue("Data");
		cellPostsImpressionOnPeriodDateTitle.setCellValue("Date");
		cellPostsImpressionOnPeriodDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellOrganicPostsImpressionOnPeriodTotalTitle = rowPostsImpressionOnPeriodTitleItems.createCell(1);
//		cellOrganicPostsImpressionOnPeriodTotalTitle.setCellValue("Orgânico");
		cellOrganicPostsImpressionOnPeriodTotalTitle.setCellValue("Organic");
		cellOrganicPostsImpressionOnPeriodTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
//		Cell cellPaidPostsImpressionOnPeriodTotalTitle = rowPostsImpressionOnPeriodTitleItems.createCell(2);
//		cellPaidPostsImpressionOnPeriodTotalTitle.setCellValue("Pago");
//		cellPaidPostsImpressionOnPeriodTotalTitle.setCellValue("Paid");
//		cellPaidPostsImpressionOnPeriodTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
//		
//		Cell cellTotalPostsImpressionOnPeriodTotalTitle = rowPostsImpressionOnPeriodTitleItems.createCell(3);
//		cellTotalPostsImpressionOnPeriodTotalTitle.setCellValue("Total");
//		cellTotalPostsImpressionOnPeriodTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		Long totalImpressionCount = 0L;
		for(LinkedinCompanyImpressionsByDay linkedinCompanyImpressionsByDay : reportLinkedinOverviewDTO.getImpressionsByDay()) {
			
			Row rowPostsOnPeriodValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellDateValue = rowPostsOnPeriodValues.createCell(0);
//			cellDateValue.setCellValue(new SimpleDateFormat("dd/MM/yy").format(new Date(linkedinCompanyImpressionsByDay.getTime())));
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(linkedinCompanyImpressionsByDay.getTime())));
			
			Cell cellOrganicValue = rowPostsOnPeriodValues.createCell(1);
			cellOrganicValue.setCellValue(linkedinCompanyImpressionsByDay.getImpressionCount());

//			Cell cellPaidValue = rowPostsOnPeriodValues.createCell(2);
//			cellPaidValue.setCellValue(sumByDate.getDay() + "/" + sumByDate.getMonth() + "/" + sumByDate.getYear());
//			
//			Cell cellTotalValue = rowPostsOnPeriodValues.createCell(3);
//			cellTotalValue.setCellValue(sumByDate.getSum());
			
			totalImpressionCount++;
			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowTotalPostsImpression = sheetOverview.createRow(rowSheetOverviewCount);
		rowTotalPostsImpression.setHeightInPoints(headerDataRowHeight);
		
		Cell cellOrganicPostsImpression = rowTotalPostsImpression.createCell(0);
//		cellOrganicPostsImpression.setCellValue("Total impressões orgânicas");
		cellOrganicPostsImpression.setCellValue("Total impressions organic");
		cellOrganicPostsImpression.setCellStyle(cellStylePreAnalysis);
		
		Cell cellOrganicPostsImpressionValue = rowTotalPostsImpression.createCell(1);
		cellOrganicPostsImpressionValue.setCellValue(this.impressionCountSum);
		cellOrganicPostsImpressionValue.setCellStyle(cellStylePreAnalysisResult);
		
//		rowSheetOverviewCount++;
//		Row rowPaidPostsImpression = sheetOverview.createRow(rowSheetOverviewCount);
//		rowPaidPostsImpression.setHeightInPoints(headerDataRowHeight);
//		
//		Cell cellPaidPostsImpressionTitle = rowPaidPostsImpression.createCell(0);
//		cellPaidPostsImpressionTitle.setCellValue("Total impressões pagas");
//		cellPaidPostsImpressionTitle.setCellValue("Total impressions paid");
//		cellPaidPostsImpressionTitle.setCellStyle(cellStylePreAnalysis);
//		
//		Cell cellPaidPostsImpressionValue = rowPaidPostsImpression.createCell(1);
//		cellPaidPostsImpressionValue.setCellValue();
//		cellPaidPostsImpressionValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowPostsImpressionAverage = sheetOverview.createRow(rowSheetOverviewCount);
		rowPostsImpressionAverage.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsImpressionAverageTitle = rowPostsImpressionAverage.createCell(0);
//		cellPostsImpressionAverageTitle.setCellValue("Média diária de impressões");
		cellPostsImpressionAverageTitle.setCellValue("Average impressions per day");
		cellPostsImpressionAverageTitle.setCellStyle(cellStylePreAnalysis);
		
		Double impressionAverage = this.impressionCountSum.doubleValue() / totalImpressionCount.doubleValue();
		
		Cell cellPostsImpressionAverageValue = rowPostsImpressionAverage.createCell(1);
		cellPostsImpressionAverageValue.setCellValue(String.format("%.3f", impressionAverage));
		cellPostsImpressionAverageValue.setCellStyle(cellStylePreAnalysisResult);
	}

	private void addPostsOnPeriod() {
		
		rowSheetOverviewCount += 2;
		
		Row rowPostsOnPeriodTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowPostsOnPeriodTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsOnPeriodTitle = rowPostsOnPeriodTitle.createCell(0);
//		cellPostsOnPeriodTitle.setCellValue("PUBLICAÇÕES NO PERÍODO");
		cellPostsOnPeriodTitle.setCellValue("POSTS SENT");
		cellPostsOnPeriodTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowPostsOnPeriodTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowPostsOnPeriodTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsOnPeriodDateTitle = rowPostsOnPeriodTitleItems.createCell(0);
//		cellPostsOnPeriodDateTitle.setCellValue("Data");
		cellPostsOnPeriodDateTitle.setCellValue("Date");
		cellPostsOnPeriodDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellPostsOnPeriodTotalTitle = rowPostsOnPeriodTitleItems.createCell(1);
//		cellPostsOnPeriodTotalTitle.setCellValue("Total de posts");
		cellPostsOnPeriodTotalTitle.setCellValue("Total posts");
		cellPostsOnPeriodTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(SumByDate sumByDate : reportLinkedinOverviewDTO.getLinkedinSharesByDay()) {
			
			Row rowPostsOnPeriodValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellDateValue = rowPostsOnPeriodValues.createCell(0);
			cellDateValue.setCellValue(sumByDate.getDay() + "/" + sumByDate.getMonth() + "/" + sumByDate.getYear());
			
			Cell cellTotalValue = rowPostsOnPeriodValues.createCell(1);
			cellTotalValue.setCellValue(sumByDate.getSum());
			
			totalPostsSum  += sumByDate.getSum();
			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowDailyPostsAverage = sheetOverview.createRow(rowSheetOverviewCount);
		rowDailyPostsAverage.setHeightInPoints(headerDataRowHeight);
		
		Cell cellDailyPostsAverageTitle = rowDailyPostsAverage.createCell(0);
//		cellDailyPostsAverageTitle.setCellValue("Média diária de posts");
		cellDailyPostsAverageTitle.setCellValue("Average posts per day");
		cellDailyPostsAverageTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellDailyPostsAverageValue = rowDailyPostsAverage.createCell(1);
		cellDailyPostsAverageValue.setCellValue(reportLinkedinOverviewDTO.getLinkedinPostAverageByDay());
		cellDailyPostsAverageValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowDailyInteractionsByPost = sheetOverview.createRow(rowSheetOverviewCount);
		rowDailyInteractionsByPost.setHeightInPoints(headerDataRowHeight);
		
		Cell cellDailyInteractionsByPostTitle = rowDailyInteractionsByPost.createCell(0);
//		cellDailyInteractionsByPostTitle.setCellValue("Média de interações por post");
		cellDailyInteractionsByPostTitle.setCellValue("Average interactions per day");
		cellDailyInteractionsByPostTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellOrganicFansValue = rowDailyInteractionsByPost.createCell(1);
		cellOrganicFansValue.setCellValue(getInteractionsByPostAverage().doubleValue());
		cellOrganicFansValue.setCellStyle(cellStylePreAnalysisResult);
	}

	private Double getInteractionsByPostAverage() {
		return (double) (likeCountSum + commentCountSum + shareCountSum) / totalPostsSum;
	}

	private void addPeriodPageGrowth() {
		
		rowSheetOverviewCount += 1;
		
		Row rowFanPageGrowthTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowFanPageGrowthTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFanPageGrowthTitle = rowFanPageGrowthTitle.createCell(0);
//		cellFanPageGrowthTitle.setCellValue("CRESCIMENTO DE FÃS NA PÁGINA");
		cellFanPageGrowthTitle.setCellValue("FAN GROWTH");
		cellFanPageGrowthTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowFanPageGrowthTitleItems = sheetOverview.createRow(rowSheetOverviewCount);
		rowFanPageGrowthTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellFanPageDateTitle = rowFanPageGrowthTitleItems.createCell(0);
//		cellFanPageDateTitle.setCellValue("Data");
		cellFanPageDateTitle.setCellValue("Date");
		cellFanPageDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellFanPageTotalTitle = rowFanPageGrowthTitleItems.createCell(1);
//		cellFanPageTotalTitle.setCellValue("Total de fãs");
		cellFanPageTotalTitle.setCellValue("Total fans");
		cellFanPageTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		for(LinkedinFollowers linkedinFollowers : reportLinkedinOverviewDTO.getLinkedinFollowers()) {
			
			Row rowFanPageGrowthValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellDateValue = rowFanPageGrowthValues.createCell(0);
//			cellDateValue.setCellValue(new SimpleDateFormat("dd/MM/yy").format(new Date(linkedinFollowers.getTime())));
			cellDateValue.setCellValue(new SimpleDateFormat("MM/dd/yy").format(new Date(linkedinFollowers.getTime())));
			
			Cell cellTotalValue = rowFanPageGrowthValues.createCell(1);
			cellTotalValue.setCellValue(linkedinFollowers.getTotalFollowerCount());

			rowSheetOverviewCount++;
		}
		
		rowSheetOverviewCount++;
		Row rowTotalFans = sheetOverview.createRow(rowSheetOverviewCount);
		rowTotalFans.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTotalFansTitle = rowTotalFans.createCell(0);
//		cellTotalFansTitle.setCellValue("Total de seguidores");
		cellTotalFansTitle.setCellValue("Total followers");
		cellTotalFansTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellTotalFansValue = rowTotalFans.createCell(1);
		cellTotalFansValue.setCellValue((Long) reportLinkedinOverviewDTO.getLinkedinFollowers()
				.get(reportLinkedinOverviewDTO.getLinkedinFollowers().size() - 1).getTotalFollowerCount());
		cellTotalFansValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowOrganicFans = sheetOverview.createRow(rowSheetOverviewCount);
		rowOrganicFans.setHeightInPoints(headerDataRowHeight);
		
		Cell cellOrganicFansTitle = rowOrganicFans.createCell(0);
//		cellOrganicFansTitle.setCellValue("Total de seguidores orgânicos");
		cellOrganicFansTitle.setCellValue("Total followers organic");
		cellOrganicFansTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellOrganicFansValue = rowOrganicFans.createCell(1);
		cellOrganicFansValue.setCellValue(reportLinkedinOverviewDTO.getLinkedinFollowers()
				.get(reportLinkedinOverviewDTO.getLinkedinFollowers().size() - 1).getOrganicFollowerCount());
		cellOrganicFansValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetOverviewCount++;
		Row rowPaidFans = sheetOverview.createRow(rowSheetOverviewCount);
		rowPaidFans.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPaidFansTitle = rowPaidFans.createCell(0);
//		cellPaidFansTitle.setCellValue("Total de seguidores pagos");
		cellPaidFansTitle.setCellValue("Total followers paid");
		cellPaidFansTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellPaidFansValue = rowPaidFans.createCell(1);
		cellPaidFansValue.setCellValue(reportLinkedinOverviewDTO.getLinkedinFollowers()
				.get(reportLinkedinOverviewDTO.getLinkedinFollowers().size() - 1).getPaidFollowerCount());
		cellPaidFansValue.setCellStyle(cellStylePreAnalysisResult);
	}

	private void addGeneralData() {
		
		rowSheetOverviewCount = 2;
		
		Row rowGeneralDataTitle = sheetOverview.createRow(rowSheetOverviewCount);
		rowGeneralDataTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellGeneralDataTitle = rowGeneralDataTitle.createCell(0);
//		cellGeneralDataTitle.setCellValue("DADOS GERAIS");
		cellGeneralDataTitle.setCellValue("OVERVIEW");
		cellGeneralDataTitle.setCellStyle(cellStyleHeaderData);
		
		sheetOverview.addMergedRegion(new CellRangeAddress(rowSheetOverviewCount, rowSheetOverviewCount, 0, 4));
		
		rowSheetOverviewCount++;
		Row rowGeneralDataTitleItens = sheetOverview.createRow(rowSheetOverviewCount);
		rowGeneralDataTitleItens.setHeightInPoints(headerDataRowHeight);
		
		Cell cellGeneralDataInteractionTitle = rowGeneralDataTitleItens.createCell(0);
//		cellGeneralDataInteractionTitle.setCellValue("Interação");
		cellGeneralDataInteractionTitle.setCellValue("Interactions");
		cellGeneralDataInteractionTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellGeneralDataTotalTitle = rowGeneralDataTitleItens.createCell(1);
		cellGeneralDataTotalTitle.setCellValue("Total");
		cellGeneralDataTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetOverviewCount++;
		
		for(int i = 0; i < 5; i++) {
			
			String auxInteraction;
			Long auxTotal;
			
//			if(i == 0) { auxTotal = impressionCountSum; auxInteraction = "Impressões"; }
//			else if (i == 1) { auxTotal = likeCountSum; auxInteraction = "Curtir"; }
//			else if (i == 2) { auxTotal = commentCountSum; auxInteraction = "Comentários"; }
//			else if (i == 3) { auxTotal = shareCountSum; auxInteraction = "Compartilhamentos"; }
//			else { auxTotal = clickCountSum; auxInteraction = "Cliques"; }

			if(i == 0) { auxTotal = impressionCountSum; auxInteraction = "Impressions"; }
			else if (i == 1) { auxTotal = likeCountSum; auxInteraction = "Likes"; }
			else if (i == 2) { auxTotal = commentCountSum; auxInteraction = "Comments"; }
			else if (i == 3) { auxTotal = shareCountSum; auxInteraction = "Shares"; }
			else { auxTotal = clickCountSum; auxInteraction = "Clicks"; }
			
			Row rowGeneralDataValues = sheetOverview.createRow(rowSheetOverviewCount);

			Cell cellInteractionValue = rowGeneralDataValues.createCell(0);
			cellInteractionValue.setCellValue(auxInteraction);
			
			Cell cellTotalValue = rowGeneralDataValues.createCell(1);
			cellTotalValue.setCellValue(auxTotal.toString());
			cellTotalValue.setCellStyle(cellDataPercentage);
			
			rowSheetOverviewCount++;
		}

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
//		cellDateStyle.setDataFormat(
//				createHelper.createDataFormat().getFormat("dd/MM/yyyy hh:mm"));

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
