package br.com.opsocial.server.utils.monitorings.xls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import br.com.opsocial.client.entity.application.MonitoringDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringInfluentialUserDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringReportDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringTagReportDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringTermReportDTO;
import br.com.opsocial.server.utils.Calc;
import br.com.opsocial.server.utils.UtilFunctions;

public class MonitoringReportXLS {
	
	private MonitoringReportDTO monitoringReport;
	private MonitoringDTO monitoring;
	
	private String reportsPath;
	private String fileName;
	private String formattedFileName;
	
	private HSSFWorkbook wb;
	
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
	
	private int rowSheetRepSentUsers;
	private int rowSheetTermsTags;
	private int rowSheetCities;

	public MonitoringReportXLS() {
		
	}
	
	public MonitoringReportXLS(MonitoringReportDTO monitoringReport, MonitoringDTO monitoring) {
		this.monitoringReport = monitoringReport;
		this.monitoring = monitoring;
	}
	
	public void createXLS() {
		
		wb = new HSSFWorkbook();
		
		initiateDefautStyles();
		
		this.reportsPath = OpSocialBackApplication.opSocialContext.getInitParameter("getDirReporstXLS");
		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".xls");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormatToFileName = new SimpleDateFormat("dd-MM-yyyy");
		
		startDate = dateFormat.format(new Date(monitoringReport.getDateFrom() * 1000L));
		endDate = dateFormat.format(new Date(monitoringReport.getDateUntil() * 1000L));
		
		addReportOfNetworks();
		addReportOverTimeDays();
		addReportSentimentUsers();
		addReportTermsTags();
		addReportCities();
		
		try {
			
			File reportFile = new File(reportsPath, fileName);
			FileOutputStream fileOut = new FileOutputStream(reportFile);
			wb.write(fileOut);
			fileOut.close();
			
			formattedFileName = monitoring.getName() + "-rel-monitoramento-" 
					+ dateFormatToFileName.format(new Date(monitoringReport.getDateFrom() * 1000L)) + "--" 
					+ dateFormatToFileName.format(new Date(monitoringReport.getDateUntil() * 1000L));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addReportCities() {
		
		rowSheetCities = 2;
		
		Sheet sheetReportCities = wb.createSheet("Cidades");
		
		addMainHeader(sheetReportCities, monitoring.getName() + " - Relatório de Monitoramento - Período de " + startDate + " a " + endDate, 6);
		
		Row rowCitiesTitle = sheetReportCities.createRow(rowSheetCities);
		rowCitiesTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCitiesTitle = rowCitiesTitle.createCell(0);
		cellCitiesTitle.setCellValue("CIDADES COM MAIS CITAÇÕES");
		cellCitiesTitle.setCellStyle(cellStyleHeaderData);
		
		sheetReportCities.addMergedRegion(new CellRangeAddress(rowSheetCities, rowSheetCities, 0, 6));
		
		rowSheetCities++;
		Row rowCitiesSubTitle = sheetReportCities.createRow(rowSheetCities);
		rowCitiesSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellCityNameTitle = rowCitiesSubTitle.createCell(0);
		cellCityNameTitle.setCellValue("Cidade");
		cellCityNameTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellCityTotalTitle = rowCitiesSubTitle.createCell(1);
		cellCityTotalTitle.setCellValue("Total");
		cellCityTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetCities++;
		for(Map.Entry<String, Long> postsCity : monitoringReport.getPostsCities().entrySet()) {
			
			Row rowCityValues = sheetReportCities.createRow(rowSheetCities);
			
			Cell cellCityName = rowCityValues.createCell(0);
			cellCityName.setCellValue(postsCity.getKey());
			
			Cell cellCityTotal = rowCityValues.createCell(1);
			cellCityTotal.setCellValue(postsCity.getValue());
			
			rowSheetCities++;
		}
		
		sheetReportCities.setDefaultColumnWidth(20);
	}
	
	private void addReportTermsTags() {
		
		rowSheetTermsTags = 2;
		
		Sheet sheetReportTermsTags = wb.createSheet("Termos e Tags");
		
		addMainHeader(sheetReportTermsTags, monitoring.getName() + " - Relatório de Monitoramento - Período de " + startDate + " a " + endDate, 16);
		
		Row rowTermsTitle = sheetReportTermsTags.createRow(rowSheetTermsTags);
		rowTermsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTermsTitle = rowTermsTitle.createCell(0);
		cellTermsTitle.setCellValue("NÚMERO E QUALIFICAÇÃO DE POSTS POR TERMO MONITORADO");
		cellTermsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 0, 16));
		
		rowSheetTermsTags++;
		Row rowTermsSubTitle = sheetReportTermsTags.createRow(rowSheetTermsTags);
		rowTermsSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTermTitle = rowTermsSubTitle.createCell(0);
		cellTermTitle.setCellValue("Termo");
		cellTermTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTermTotalTitle = rowTermsSubTitle.createCell(1);
		cellTermTotalTitle.setCellValue("Total de posts");
		cellTermTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellTermPostitiveTitle = rowTermsSubTitle.createCell(2);
		cellTermPostitiveTitle.setCellValue("Positivo");
		cellTermPostitiveTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellTermNeuterTitle = rowTermsSubTitle.createCell(3);
		cellTermNeuterTitle.setCellValue("Neutro");
		cellTermNeuterTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellTermNegativeTitle = rowTermsSubTitle.createCell(4);
		cellTermNegativeTitle.setCellValue("Negativo");
		cellTermNegativeTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetTermsTags++;
		for (MonitoringTermReportDTO monitoringTermReport : monitoringReport.getMonitoringTermReports()) {
			
			Row rowTermsValues = sheetReportTermsTags.createRow(rowSheetTermsTags);
			
			Cell cellTermValue = rowTermsValues.createCell(0);
			cellTermValue.setCellValue(monitoringTermReport.getTerm());
			
			Cell cellTermTotalValue = rowTermsValues.createCell(1);
			cellTermTotalValue.setCellValue(monitoringTermReport.getTotalPosts());
			
			Cell cellTermPostitiveValue = rowTermsValues.createCell(2);
			cellTermPostitiveValue.setCellValue(monitoringTermReport.getSentimentAnalysis().containsKey('P') 
					? monitoringTermReport.getSentimentAnalysis().get('P')
				    : 0L);
			
			Cell cellTermNeuterValue = rowTermsValues.createCell(3);
			cellTermNeuterValue.setCellValue(monitoringTermReport.getSentimentAnalysis().containsKey('S') 
					? monitoringTermReport.getSentimentAnalysis().get('S')
						    : 0L);
			
			Cell cellTermNegativeValue = rowTermsValues.createCell(4);
			cellTermNegativeValue.setCellValue(monitoringTermReport.getSentimentAnalysis().containsKey('N') 
					? monitoringTermReport.getSentimentAnalysis().get('N')
						    : 0L);
			
			rowSheetTermsTags++;
		}
		
		rowSheetTermsTags++;
		Row rowTagsTitle = sheetReportTermsTags.createRow(rowSheetTermsTags);
		rowTagsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTagsTitle = rowTagsTitle.createCell(0);
		cellTagsTitle.setCellValue("TAGS MAIS MARCADAS POR REDE");
		cellTagsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 0, 16));
		
		rowSheetTermsTags++;
		Row rowTagsSubTitle = sheetReportTermsTags.createRow(rowSheetTermsTags);
		rowTagsSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellTagTitle = rowTagsSubTitle.createCell(0);
		cellTagTitle.setCellValue("Tag");
		cellTagTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTagTwitterTitle = rowTagsSubTitle.createCell(1);
		cellTagTwitterTitle.setCellValue("Twitter");
		cellTagTwitterTitle.setCellStyle(cellSecondaryHeaderCenter);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 1, 2));
		
		Cell cellTagInstagramTitle = rowTagsSubTitle.createCell(3);
		cellTagInstagramTitle.setCellValue("Instagram");
		cellTagInstagramTitle.setCellStyle(cellSecondaryHeaderCenter);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 3, 4));

		Cell cellTagGooglePlusTitle = rowTagsSubTitle.createCell(5);
		cellTagGooglePlusTitle.setCellValue("Google+");
		cellTagGooglePlusTitle.setCellStyle(cellSecondaryHeaderCenter);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 5, 6));
		
		Cell cellTagYoutubeTitle = rowTagsSubTitle.createCell(7);
		cellTagYoutubeTitle.setCellValue("Youtube");
		cellTagYoutubeTitle.setCellStyle(cellSecondaryHeaderCenter);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 7, 8));
		
		Cell cellTagBlogsTitle = rowTagsSubTitle.createCell(9);
		cellTagBlogsTitle.setCellValue("Blogs");
		cellTagBlogsTitle.setCellStyle(cellSecondaryHeaderCenter);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 9, 10));
		
		Cell cellTagNewsTitle = rowTagsSubTitle.createCell(11);
		cellTagNewsTitle.setCellValue("Notícias");
		cellTagNewsTitle.setCellStyle(cellSecondaryHeaderCenter);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 11, 12));
		
		Cell cellTagReclameAquiTitle = rowTagsSubTitle.createCell(13);
		cellTagReclameAquiTitle.setCellValue("Reclame Aqui");
		cellTagReclameAquiTitle.setCellStyle(cellSecondaryHeaderCenter);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 13, 14));
		
		Cell cellTagTotalTitle = rowTagsSubTitle.createCell(15);
		cellTagTotalTitle.setCellValue("Total");
		cellTagTotalTitle.setCellStyle(cellSecondaryHeaderCenter);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 15, 16));
		
		rowSheetTermsTags++;
		Long totalTaggedPosts = getTotalTaggedPosts(monitoringReport.getMonitoringTagReports());
		for(MonitoringTagReportDTO monitoringTagReport : monitoringReport.getMonitoringTagReports()) {	
			
			Row rowTagsValues = sheetReportTermsTags.createRow(rowSheetTermsTags);
			
			Cell cellTagValue = rowTagsValues.createCell(0);
			cellTagValue.setCellValue(monitoringTagReport.getMonitoringTag().getName());
			
			Long twitterValue = monitoringTagReport.getTaggedPostsPerNetwork().containsKey('T') 
					? monitoringTagReport.getTaggedPostsPerNetwork().get('T')
					: 0L;
	
			Cell cellTagTwitterValue = rowTagsValues.createCell(1);
			cellTagTwitterValue.setCellValue(twitterValue);
	
			Cell cellTagTwitterPerc = rowTagsValues.createCell(2);
			cellTagTwitterPerc.setCellValue(
					Calc.calcPercentageNormal(twitterValue.doubleValue(), 
							monitoringTagReport.getTotalTaggedPosts().doubleValue(), 2).toString().replace(".", ",") + "%");
			cellTagTwitterPerc.setCellStyle(cellDataPercentage);
			
			Long instagramValue = monitoringTagReport.getTaggedPostsPerNetwork().containsKey('I') 
					? monitoringTagReport.getTaggedPostsPerNetwork().get('I')
					: 0L;
	
			Cell cellTagInstagramValue = rowTagsValues.createCell(3);
			cellTagInstagramValue.setCellValue(instagramValue);
	
			Cell cellTagInstagramPerc = rowTagsValues.createCell(4);
			cellTagInstagramPerc.setCellValue(
					Calc.calcPercentageNormal(instagramValue.doubleValue(), 
							monitoringTagReport.getTotalTaggedPosts().doubleValue(), 2).toString().replace(".", ",") + "%");
			cellTagInstagramPerc.setCellStyle(cellDataPercentage);
			
			Long googlePlusValue = monitoringTagReport.getTaggedPostsPerNetwork().containsKey('G') 
					? monitoringTagReport.getTaggedPostsPerNetwork().get('G')
					: 0L;
	
			Cell cellTagGooglePlusValue = rowTagsValues.createCell(5);
			cellTagGooglePlusValue.setCellValue(googlePlusValue);
	
			Cell cellTagGooglePlusPerc = rowTagsValues.createCell(6);
			cellTagGooglePlusPerc.setCellValue(
					Calc.calcPercentageNormal(googlePlusValue.doubleValue(), 
							monitoringTagReport.getTotalTaggedPosts().doubleValue(), 2).toString().replace(".", ",") + "%");
			cellTagGooglePlusPerc.setCellStyle(cellDataPercentage);
			
			Long youtubeValue = monitoringTagReport.getTaggedPostsPerNetwork().containsKey('Y') 
					? monitoringTagReport.getTaggedPostsPerNetwork().get('Y')
					: 0L;
	
			Cell cellTagYoutubeValue = rowTagsValues.createCell(7);
			cellTagYoutubeValue.setCellValue(youtubeValue);
	
			Cell cellTagYoutubePerc = rowTagsValues.createCell(8);
			cellTagYoutubePerc.setCellValue(
					Calc.calcPercentageNormal(youtubeValue.doubleValue(), 
							monitoringTagReport.getTotalTaggedPosts().doubleValue(), 2).toString().replace(".", ",") + "%");
			cellTagYoutubePerc.setCellStyle(cellDataPercentage);
			
			Long blogsValue = monitoringTagReport.getTaggedPostsPerNetwork().containsKey('B') 
					? monitoringTagReport.getTaggedPostsPerNetwork().get('B')
					: 0L;
	
			Cell cellTagBlogsValue = rowTagsValues.createCell(9);
			cellTagBlogsValue.setCellValue(blogsValue);
	
			Cell cellTagBlogsPerc = rowTagsValues.createCell(10);
			cellTagBlogsPerc.setCellValue(
					Calc.calcPercentageNormal(blogsValue.doubleValue(), 
							monitoringTagReport.getTotalTaggedPosts().doubleValue(), 2).toString().replace(".", ",") + "%");
			cellTagBlogsPerc.setCellStyle(cellDataPercentage);
			
			Long newsValue = monitoringTagReport.getTaggedPostsPerNetwork().containsKey('N') 
					? monitoringTagReport.getTaggedPostsPerNetwork().get('N')
					: 0L;
	
			Cell cellTagNewsValue = rowTagsValues.createCell(11);
			cellTagNewsValue.setCellValue(newsValue);
	
			Cell cellTagNewsPerc = rowTagsValues.createCell(12);
			cellTagNewsPerc.setCellValue(
					Calc.calcPercentageNormal(newsValue.doubleValue(), 
							monitoringTagReport.getTotalTaggedPosts().doubleValue(), 2).toString().replace(".", ",") + "%");
			cellTagNewsPerc.setCellStyle(cellDataPercentage);
			
			Long reclameAquiValue = monitoringTagReport.getTaggedPostsPerNetwork().containsKey('R') 
					? monitoringTagReport.getTaggedPostsPerNetwork().get('R')
					: 0L;
	
			Cell cellTagReclameAquiValue = rowTagsValues.createCell(13);
			cellTagReclameAquiValue.setCellValue(reclameAquiValue);
	
			Cell cellTagReclameAquiPerc = rowTagsValues.createCell(14);
			cellTagReclameAquiPerc.setCellValue(
					Calc.calcPercentageNormal(reclameAquiValue.doubleValue(), 
							monitoringTagReport.getTotalTaggedPosts().doubleValue(), 2).toString().replace(".", ",") + "%");
			cellTagReclameAquiPerc.setCellStyle(cellDataPercentage);
						
			Long tagTotalValue = monitoringTagReport.getTotalTaggedPosts();
		
			Cell cellTagTotalValue = rowTagsValues.createCell(15);
			cellTagTotalValue.setCellValue(tagTotalValue);
		
			Cell cellTagTotalPerc = rowTagsValues.createCell(16);
			cellTagTotalPerc.setCellValue(
					Calc.calcPercentageNormal(tagTotalValue.doubleValue(), 
							totalTaggedPosts.doubleValue(), 2).toString().replace(".", ",") + "%");
			cellTagTotalPerc.setCellStyle(cellDataPercentage);
	
			rowSheetTermsTags++;
		}
		
		rowSheetTermsTags++;
		Row rowHashTagsTitle = sheetReportTermsTags.createRow(rowSheetTermsTags);
		rowHashTagsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellHashTagsTitle = rowHashTagsTitle.createCell(0);
		cellHashTagsTitle.setCellValue("TOP HASHTAGS");
		cellHashTagsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 0, 16));
		
		rowSheetTermsTags++;
		Row rowHashTagsSubTitle = sheetReportTermsTags.createRow(rowSheetTermsTags);
		rowHashTagsSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellHashTagTitle = rowHashTagsSubTitle.createCell(0);
		cellHashTagTitle.setCellValue("Hashtag");
		cellHashTagTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellHashTagTotalTitle = rowHashTagsSubTitle.createCell(1);
		cellHashTagTotalTitle.setCellValue("Total");
		cellHashTagTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetTermsTags++;
		for(Map.Entry<String, Long> hashTag : monitoringReport.getHashTags().entrySet()) {
			
			Row rowHashTagsValues = sheetReportTermsTags.createRow(rowSheetTermsTags);
			
			Cell cellHashTagValue = rowHashTagsValues.createCell(0);
			cellHashTagValue.setCellValue("#" + hashTag.getKey());
			
			Cell cellHashTagTotalValue = rowHashTagsValues.createCell(1);
			cellHashTagTotalValue.setCellValue(hashTag.getValue());
			
			rowSheetTermsTags++;
		}
		
		rowSheetTermsTags++;
		Row rowLinksTitle = sheetReportTermsTags.createRow(rowSheetTermsTags);
		rowLinksTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellLinksTitle = rowLinksTitle.createCell(0);
		cellLinksTitle.setCellValue("TOP LINKS");
		cellLinksTitle.setCellStyle(cellStyleHeaderData);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 0, 16));
		
		rowSheetTermsTags++;
		Row rowLinksSubTitle = sheetReportTermsTags.createRow(rowSheetTermsTags);
		rowLinksSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellLinkTitle = rowLinksSubTitle.createCell(0);
		cellLinkTitle.setCellValue("Link");
		cellLinkTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellLinkTotalTitle = rowLinksSubTitle.createCell(1);
		cellLinkTotalTitle.setCellValue("Total");
		cellLinkTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetTermsTags++;
		for(Map.Entry<String, Long> link : monitoringReport.getLinks().entrySet()) {

			Row rowLinkValues = sheetReportTermsTags.createRow(rowSheetTermsTags);
			
			Hyperlink hyperLink = createHelper.createHyperlink(Hyperlink.LINK_URL);
    		hyperLink.setAddress(link.getKey());
			
			Cell cellProfileLink = rowLinkValues.createCell(0);
			cellProfileLink.setCellValue(link.getKey());
			cellProfileLink.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) hyperLink);
			cellProfileLink.setCellStyle(cellStyleHLink);
		
			Cell cellLinkTotalValue = rowLinkValues.createCell(1);
			cellLinkTotalValue.setCellValue(link.getValue());
			
			rowSheetTermsTags++;
		}
		
		rowSheetTermsTags++;
		Row rowMostFrequentWordsTitle = sheetReportTermsTags.createRow(rowSheetTermsTags);
		rowMostFrequentWordsTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMostFrequentWordsTitle = rowMostFrequentWordsTitle.createCell(0);
		cellMostFrequentWordsTitle.setCellValue("PALAVRAS MAIS ENCONTRADAS");
		cellMostFrequentWordsTitle.setCellStyle(cellStyleHeaderData);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 0, 16));
		
		rowSheetTermsTags++;
		for (String word : monitoringReport.getMostFrequentWords()) {
			
			Row rowMostFrequentWordsValues = sheetReportTermsTags.createRow(rowSheetTermsTags);
			
			Cell cellMostFrequentWord = rowMostFrequentWordsValues.createCell(0);
			cellMostFrequentWord.setCellValue(word);
			
			rowSheetTermsTags++;
		}
		
		rowSheetTermsTags++;
		Row rowMostFrequentWordsPosTitle = sheetReportTermsTags.createRow(rowSheetTermsTags);
		rowMostFrequentWordsPosTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMostFrequentWordsPosTitle = rowMostFrequentWordsPosTitle.createCell(0);
		cellMostFrequentWordsPosTitle.setCellValue("PALAVRAS MAIS ENCONTRADAS EM POSTS POSITIVOS");
		cellMostFrequentWordsPosTitle.setCellStyle(cellStyleHeaderDataPositive);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 0, 16));
		
		rowSheetTermsTags++;
		for (String word : monitoringReport.getMostFrequentWordsPositive()) {
			
			Row rowMostFrequentWordsPostValues = sheetReportTermsTags.createRow(rowSheetTermsTags);
			
			Cell cellMostFrequentPosWord = rowMostFrequentWordsPostValues.createCell(0);
			cellMostFrequentPosWord.setCellValue(word);
			
			rowSheetTermsTags++;
		}
		
		rowSheetTermsTags++;
		Row rowMostFrequentWordsNegTitle = sheetReportTermsTags.createRow(rowSheetTermsTags);
		rowMostFrequentWordsNegTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMostFrequentWordsNegTitle = rowMostFrequentWordsNegTitle.createCell(0);
		cellMostFrequentWordsNegTitle.setCellValue("PALAVRAS MAIS ENCONTRADAS EM POSTS NEGATIVOS");
		cellMostFrequentWordsNegTitle.setCellStyle(cellStyleHeaderDataNegative);
		
		sheetReportTermsTags.addMergedRegion(new CellRangeAddress(rowSheetTermsTags, rowSheetTermsTags, 0, 16));
		
		rowSheetTermsTags++;
		for (String word : monitoringReport.getMostFrequentWordsNegative()) {
			
			Row rowMostFrequentWordsNegValues = sheetReportTermsTags.createRow(rowSheetTermsTags);
			
			Cell cellMostFrequentNegWord = rowMostFrequentWordsNegValues.createCell(0);
			cellMostFrequentNegWord.setCellValue(word);
			
			rowSheetTermsTags++;
		}
		
		sheetReportTermsTags.setDefaultColumnWidth(8);
		sheetReportTermsTags.setColumnWidth(0, 5000);
	}
	
	private void addReportSentimentUsers() {
		
		Sheet sheetReportSentimentUsers = wb.createSheet("Sentimento e Usuários");
		
		addMainHeader(sheetReportSentimentUsers, monitoring.getName() + " - Relatório de Monitoramento - Período de " + startDate + " a " + endDate, 6);
		
		Row rowSentimentAnalysisTitle = sheetReportSentimentUsers.createRow(2);
		rowSentimentAnalysisTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellSentimentAnalysisTitle = rowSentimentAnalysisTitle.createCell(0);
		cellSentimentAnalysisTitle.setCellValue("ANÁLISE DE SENTIMENTO");
		cellSentimentAnalysisTitle.setCellStyle(cellStyleHeaderData);
		
		sheetReportSentimentUsers.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
		
		Row rowSentimentAnalysisSubTitle = sheetReportSentimentUsers.createRow(3);
		rowSentimentAnalysisSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellSentimentTitle = rowSentimentAnalysisSubTitle.createCell(0);
		cellSentimentTitle.setCellValue("Sentimento");
		cellSentimentTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellSentimentTotalTitle = rowSentimentAnalysisSubTitle.createCell(1);
		cellSentimentTotalTitle.setCellValue("Total");
		cellSentimentTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellSentimentTotalPercTitle = rowSentimentAnalysisSubTitle.createCell(2);
		cellSentimentTotalPercTitle.setCellValue("%");
		cellSentimentTotalPercTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Long positiveValue = monitoringReport.getSentimentAnalysis().containsKey('P') ? 
				monitoringReport.getSentimentAnalysis().get('P') : 0L;
		Long neuterValue = monitoringReport.getSentimentAnalysis().containsKey('S') ? 
								monitoringReport.getSentimentAnalysis().get('S') : 0L;
		Long negativeValue = monitoringReport.getSentimentAnalysis().containsKey('N') ? 
										monitoringReport.getSentimentAnalysis().get('N') : 0L;
								
		Long qualifiedPostsTotal = positiveValue + neuterValue + negativeValue;						
		
		Row rowPositive = sheetReportSentimentUsers.createRow(4);
		
		Cell cellPositive = rowPositive.createCell(0);
		cellPositive.setCellValue("Positivo");
		
		Cell cellPositiveValue = rowPositive.createCell(1);
		cellPositiveValue.setCellValue(positiveValue);
		
		Cell cellPositivePercValue = rowPositive.createCell(2);
		cellPositivePercValue.setCellValue(Calc.calcPercentageNormal(positiveValue.doubleValue(), qualifiedPostsTotal.doubleValue(), 1));
		
		Row rowNeuter = sheetReportSentimentUsers.createRow(5);
		
		Cell cellNeuter = rowNeuter.createCell(0);
		cellNeuter.setCellValue("Neutro");
		
		Cell cellNeuterValue = rowNeuter.createCell(1);
		cellNeuterValue.setCellValue(neuterValue);
		
		Cell cellNeuterPercValue = rowNeuter.createCell(2);
		cellNeuterPercValue.setCellValue(Calc.calcPercentageNormal(neuterValue.doubleValue(), qualifiedPostsTotal.doubleValue(), 1));
		
		Row rowNegative = sheetReportSentimentUsers.createRow(6);
		
		Cell cellNegative = rowNegative.createCell(0);
		cellNegative.setCellValue("Negativo");
		
		Cell cellNegativeValue = rowNegative.createCell(1);
		cellNegativeValue.setCellValue(negativeValue);
		
		Cell cellNegativePercValue = rowNegative.createCell(2);
		cellNegativePercValue.setCellValue(Calc.calcPercentageNormal(negativeValue.doubleValue(), qualifiedPostsTotal.doubleValue(), 1));
		
		rowSheetRepSentUsers = 8;
		Row rowInfluentUsersTitle = sheetReportSentimentUsers.createRow(rowSheetRepSentUsers);
		rowInfluentUsersTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellInfluentUsersTitle = rowInfluentUsersTitle.createCell(0);
		cellInfluentUsersTitle.setCellValue("USUÁRIOS MAIS INFLUENTES");
		cellInfluentUsersTitle.setCellStyle(cellStyleHeaderData);
		
		sheetReportSentimentUsers.addMergedRegion(new CellRangeAddress(rowSheetRepSentUsers, rowSheetRepSentUsers, 0, 6));
		
		addInfluentialUsers(monitoringReport.getMonitoringInfluentialUsers(), sheetReportSentimentUsers);
		
		rowSheetRepSentUsers++;
		Row rowPositiveUsersTitle = sheetReportSentimentUsers.createRow(rowSheetRepSentUsers);
		rowPositiveUsersTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPositiveUsersTitle = rowPositiveUsersTitle.createCell(0);
		cellPositiveUsersTitle.setCellValue("USUÁRIOS COM MAIS POSTS POSITIVOS");
		cellPositiveUsersTitle.setCellStyle(cellStyleHeaderDataPositive);
		
		sheetReportSentimentUsers.addMergedRegion(new CellRangeAddress(rowSheetRepSentUsers, rowSheetRepSentUsers, 0, 6));
		
		addInfluentialUsers(monitoringReport.getMonitoringPositiveUsers(), sheetReportSentimentUsers);
		
		rowSheetRepSentUsers++;
		Row rowNegativeUsersTitle = sheetReportSentimentUsers.createRow(rowSheetRepSentUsers);
		rowNegativeUsersTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellNegativeUsersTitle = rowNegativeUsersTitle.createCell(0);
		cellNegativeUsersTitle.setCellValue("USUÁRIOS COM MAIS POSTS NEGATIVOS");
		cellNegativeUsersTitle.setCellStyle(cellStyleHeaderDataNegative);
		
		sheetReportSentimentUsers.addMergedRegion(new CellRangeAddress(rowSheetRepSentUsers, rowSheetRepSentUsers, 0, 6));
		
		addInfluentialUsers(monitoringReport.getMonitoringNegativeUsers(), sheetReportSentimentUsers);
		
		rowSheetRepSentUsers++;
		Row rowPostsUsersTitle = sheetReportSentimentUsers.createRow(rowSheetRepSentUsers);
		rowPostsUsersTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsUsersTitle = rowPostsUsersTitle.createCell(0);
		cellPostsUsersTitle.setCellValue("TOTAL DE AUTORES POR DIA");
		cellPostsUsersTitle.setCellStyle(cellStyleHeaderData);
		
		sheetReportSentimentUsers.addMergedRegion(new CellRangeAddress(rowSheetRepSentUsers, rowSheetRepSentUsers, 0, 6));
		
		rowSheetRepSentUsers++;
		Row rowPostsUsersSubTitle = sheetReportSentimentUsers.createRow(rowSheetRepSentUsers);
		rowPostsUsersSubTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsUsersDateTitle = rowPostsUsersSubTitle.createCell(0);
		cellPostsUsersDateTitle.setCellValue("Data");
		cellPostsUsersDateTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellPostsUsersTotalTitle = rowPostsUsersSubTitle.createCell(1);
		cellPostsUsersTotalTitle.setCellValue("Total de autores");
		cellPostsUsersTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		rowSheetRepSentUsers++;
		for(Map.Entry<Date, Long> postsUsersPerDay : monitoringReport.getPostsUsersPerDay().entrySet()) {
			
			Row rowReportPostsUsersValues = sheetReportSentimentUsers.createRow(rowSheetRepSentUsers);
			
			Cell cellDateValue = rowReportPostsUsersValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("dd/MM/yy").format(postsUsersPerDay.getKey()));
			
			Cell cellPostsUsersValue = rowReportPostsUsersValues.createCell(1);
			cellPostsUsersValue.setCellValue(postsUsersPerDay.getValue());
			
			rowSheetRepSentUsers++;
		}
		
		rowSheetRepSentUsers++;
		Row rowPostsUsersTotal = sheetReportSentimentUsers.createRow(rowSheetRepSentUsers);
		rowPostsUsersTotal.setHeightInPoints(headerDataRowHeight);
		
		Cell cellUsersTotalTitle = rowPostsUsersTotal.createCell(0);
		cellUsersTotalTitle.setCellValue("Total de autores:");
		cellUsersTotalTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellUsersTotalValue = rowPostsUsersTotal.createCell(1);
		cellUsersTotalValue.setCellValue(monitoringReport.getPostsUsersTotal());
		cellUsersTotalValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetRepSentUsers++;
		Row rowPostsUsersAverage = sheetReportSentimentUsers.createRow(rowSheetRepSentUsers);
		rowPostsUsersAverage.setHeightInPoints(headerDataRowHeight);
		
		Cell cellUsersAverageTitle = rowPostsUsersAverage.createCell(0);
		cellUsersAverageTitle.setCellValue("Média diária:");
		cellUsersAverageTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellUsersAverageValue = rowPostsUsersAverage.createCell(1);
		cellUsersAverageValue.setCellValue(monitoringReport.getPostsUsersAverage());
		cellUsersAverageValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetRepSentUsers++;
		Row rowPostsUsersRecurrent = sheetReportSentimentUsers.createRow(rowSheetRepSentUsers);
		rowPostsUsersRecurrent.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsUsersRecurrentTitle = rowPostsUsersRecurrent.createCell(0);
		cellPostsUsersRecurrentTitle.setCellValue("Taxa de recorrência:");
		cellPostsUsersRecurrentTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellPostsUsersRecurrentValue = rowPostsUsersRecurrent.createCell(1);
		cellPostsUsersRecurrentValue.setCellValue(monitoringReport.getPostsUsersRecurrentPerc().toString().replace(".", ",") + "%");
		cellPostsUsersRecurrentValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetRepSentUsers++;
		Row rowMenUsers = sheetReportSentimentUsers.createRow(rowSheetRepSentUsers);
		rowMenUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellMenUsersTitle = rowMenUsers.createCell(0);
		cellMenUsersTitle.setCellValue("Homens:");
		cellMenUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellMenUsersValue = rowMenUsers.createCell(1);
		cellMenUsersValue.setCellValue(monitoringReport.getMenPostsUsersPerc() + "%");
		cellMenUsersValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowSheetRepSentUsers++;
		Row rowWomenUsers = sheetReportSentimentUsers.createRow(rowSheetRepSentUsers);
		rowWomenUsers.setHeightInPoints(headerDataRowHeight);
		
		Cell cellWomenUsersTitle = rowWomenUsers.createCell(0);
		cellWomenUsersTitle.setCellValue("Mulheres:");
		cellWomenUsersTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellWomenUsersValue = rowWomenUsers.createCell(1);
		cellWomenUsersValue.setCellValue(monitoringReport.getWomenPostsUsersPerc() + "%");
		cellWomenUsersValue.setCellStyle(cellStylePreAnalysisResult);
		
		sheetReportSentimentUsers.setDefaultColumnWidth(20);
	}
	
	private void addInfluentialUsers(List<MonitoringInfluentialUserDTO> monitoringInfluentialUsers, Sheet sheet) {
	
		rowSheetRepSentUsers++;
		Row rowUsersTitle = sheet.createRow(rowSheetRepSentUsers);
		rowUsersTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellUserNameTitle = rowUsersTitle.createCell(0);
		cellUserNameTitle.setCellValue("Nome");
		cellUserNameTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellUserFriendsTitle = rowUsersTitle.createCell(1);
		cellUserFriendsTitle.setCellValue("Seguindo");
		cellUserFriendsTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellUserFollowersTitle = rowUsersTitle.createCell(2);
		cellUserFollowersTitle.setCellValue("Seguidores");
		cellUserFollowersTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellUserPostsTitle = rowUsersTitle.createCell(3);
		cellUserPostsTitle.setCellValue("Menções");
		cellUserPostsTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellUserLinkTitle = rowUsersTitle.createCell(4);
		cellUserLinkTitle.setCellValue("Perfil (link)");
		cellUserLinkTitle.setCellStyle(cellSecondaryHeaderData);
		
		rowSheetRepSentUsers++;
		for (MonitoringInfluentialUserDTO monitoringInfluentialUser : monitoringInfluentialUsers) {
			
			Row rowUser = sheet.createRow(rowSheetRepSentUsers);
			
			Cell cellUserName = rowUser.createCell(0);
			cellUserName.setCellValue(monitoringInfluentialUser.getMonitoringUser().getName());
			
			Cell cellFriends = rowUser.createCell(1);
			cellFriends.setCellValue(monitoringInfluentialUser.getFriends());
			
			Cell cellFollowers = rowUser.createCell(2);
			cellFollowers.setCellValue(monitoringInfluentialUser.getFollowers());
			
			Cell cellPosts = rowUser.createCell(3);
			cellPosts.setCellValue(monitoringInfluentialUser.getPostsCount());
			
			String profileLink = monitoringInfluentialUser.getMonitoringUser().getProfileUrl(); 
			
			if(profileLink == null || profileLink.equals("")) {
				profileLink = br.com.opsocial.server.utils.UtilFunctions.FACEBOOK_URL + monitoringInfluentialUser.getMonitoringUser().getIdUser();
			}
			
			Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
    		link.setAddress(profileLink);
			
			Cell cellProfileLink = rowUser.createCell(4);
			cellProfileLink.setCellValue(profileLink);
			cellProfileLink.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) link);
			cellProfileLink.setCellStyle(cellStyleHLink);
			
			rowSheetRepSentUsers++;
		}
	}
	
	private void addReportOverTimeDays() {
		
		Sheet sheetReportOverTimeDays = wb.createSheet("Dias e horários");
		
		addMainHeader(sheetReportOverTimeDays, monitoring.getName() + " - Relatório de Monitoramento - Período de " + startDate + " a " + endDate, 6);
		
		Row rowReportOverTimeTitle = sheetReportOverTimeDays.createRow(2);
		rowReportOverTimeTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellReportOverTimeTitle = rowReportOverTimeTitle.createCell(0);
		cellReportOverTimeTitle.setCellValue("RESULTADOS ENCONTRADOS POR HORÁRIO");
		cellReportOverTimeTitle.setCellStyle(cellStyleHeaderData);
		
		sheetReportOverTimeDays.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
		
		Row rowReportOverTimeSubTitle = sheetReportOverTimeDays.createRow(3);
		rowReportOverTimeSubTitle.setHeightInPoints(headerDataRowHeight);
		
		addNetworkTitleCells(rowReportOverTimeSubTitle, "Faixa de horário");
		
		int rowNumber = 4;
		for(Map.Entry<Integer, Map<Character, Long>> postsPerHourNetworks : monitoringReport.getPostsPerHoursNetworks().entrySet()) {
			
			Row rowReportOverTimeValues = sheetReportOverTimeDays.createRow(rowNumber);
			
			long postsPerHour = postsPerHourNetworks.getValue().get('T') + postsPerHourNetworks.getValue().get('G') 
					+ postsPerHourNetworks.getValue().get('Y') + postsPerHourNetworks.getValue().get('I');
			
			String timeRange = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, postsPerHourNetworks.getKey(), 0, 0)) + " - " + 
					new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, postsPerHourNetworks.getKey() + 1, 0, 0));
			
			Cell cellTimeRangeValue = rowReportOverTimeValues.createCell(0);
			cellTimeRangeValue.setCellValue(timeRange);
			
			Cell cellTwitterValue = rowReportOverTimeValues.createCell(1);
			cellTwitterValue.setCellValue(postsPerHourNetworks.getValue().get('T'));
			
			Cell cellInstagramValue = rowReportOverTimeValues.createCell(2);
			cellInstagramValue.setCellValue(postsPerHourNetworks.getValue().get('I'));
			
			Cell cellGooglePlusValue = rowReportOverTimeValues.createCell(3);
			cellGooglePlusValue.setCellValue(postsPerHourNetworks.getValue().get('G'));
			
			Cell cellYoutubeValue = rowReportOverTimeValues.createCell(4);
			cellYoutubeValue.setCellValue(postsPerHourNetworks.getValue().get('Y'));
			
			Cell cellTotalValue = rowReportOverTimeValues.createCell(5);
			cellTotalValue.setCellValue(postsPerHour);
			
			rowNumber++;
		}
		
		rowNumber++;
		Row rowReportWeekTitle = sheetReportOverTimeDays.createRow(rowNumber);
		rowReportWeekTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellReportWeekTitle = rowReportWeekTitle.createCell(0);
		cellReportWeekTitle.setCellValue("RESULTADOS ENCONTRADOS POR DIA DA SEMANA");
		cellReportWeekTitle.setCellStyle(cellStyleHeaderData);
		
		sheetReportOverTimeDays.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 0, 5));
		
		rowNumber++;
		Row rowReportWeekSubTitle = sheetReportOverTimeDays.createRow(rowNumber);
		rowReportWeekSubTitle.setHeightInPoints(headerDataRowHeight);
		
		addNetworkTitleCells(rowReportWeekSubTitle, "Dia");
		
		rowNumber++;
		for(Map.Entry<Integer, Map<Character, Long>> postsPerWeekDaysNetworks : monitoringReport.getPostsPerWeekDaysNetworks().entrySet()) {
			
			Row rowReportWeekValues = sheetReportOverTimeDays.createRow(rowNumber);
			
			long twitterValue = postsPerWeekDaysNetworks.getValue().containsKey('T') ? postsPerWeekDaysNetworks.getValue().get('T') : 0L;
			long instagramValue = postsPerWeekDaysNetworks.getValue().containsKey('I') ? postsPerWeekDaysNetworks.getValue().get('I') : 0L;
			long googlePlusValue = postsPerWeekDaysNetworks.getValue().containsKey('G') ? postsPerWeekDaysNetworks.getValue().get('G') : 0L;
			long youtubeValue = postsPerWeekDaysNetworks.getValue().containsKey('Y') ? postsPerWeekDaysNetworks.getValue().get('Y') : 0L;
			
			Cell cellTimeRangeValue = rowReportWeekValues.createCell(0);
			cellTimeRangeValue.setCellValue(OpSocialBackApplication.utilFunctionsService.getFormattedWeekDayShort(postsPerWeekDaysNetworks.getKey()));
			
			Cell cellTwitterValue = rowReportWeekValues.createCell(1);
			cellTwitterValue.setCellValue(twitterValue);
			
			Cell cellInstagramValue = rowReportWeekValues.createCell(2);
			cellInstagramValue.setCellValue(instagramValue);
			
			Cell cellGooglePlusValue = rowReportWeekValues.createCell(3);
			cellGooglePlusValue.setCellValue(googlePlusValue);
			
			Cell cellYoutubeValue = rowReportWeekValues.createCell(4);
			cellYoutubeValue.setCellValue(youtubeValue);
			
			Cell cellTotalValue = rowReportWeekValues.createCell(5);
			cellTotalValue.setCellValue(twitterValue + instagramValue + googlePlusValue + youtubeValue);
			
			rowNumber++;
		}
		
		rowNumber++;
		Row rowReportPerDayTitle = sheetReportOverTimeDays.createRow(rowNumber);
		rowReportPerDayTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellReportPerDayTitle = rowReportPerDayTitle.createCell(0);
		cellReportPerDayTitle.setCellValue("RESULTADOS ENCONTRADOS POR DIA");
		cellReportPerDayTitle.setCellStyle(cellStyleHeaderData);
		
		sheetReportOverTimeDays.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 0, 5));
		
		rowNumber++;
		Row rowReportPerDaySubTitle = sheetReportOverTimeDays.createRow(rowNumber);
		rowReportPerDaySubTitle.setHeightInPoints(headerDataRowHeight);
		
		addNetworkTitleCells(rowReportPerDaySubTitle, "Data");
		
		rowNumber++;
		for(Map.Entry<Date, Map<Character, Long>> postsPerDayNetworks : monitoringReport.getPostsPerDayNetworks().entrySet()) {
			
			Row rowReportPerDayValues = sheetReportOverTimeDays.createRow(rowNumber);
			
			long postsPerDay = postsPerDayNetworks.getValue().get('T') + postsPerDayNetworks.getValue().get('G') + 
					postsPerDayNetworks.getValue().get('Y') + postsPerDayNetworks.getValue().get('I');
			
			Cell cellDateValue = rowReportPerDayValues.createCell(0);
			cellDateValue.setCellValue(new SimpleDateFormat("dd/MM/yy").format(postsPerDayNetworks.getKey()));
			
			Cell cellTwitterValue = rowReportPerDayValues.createCell(1);
			cellTwitterValue.setCellValue(postsPerDayNetworks.getValue().get('T'));
			
			Cell cellInstagramValue = rowReportPerDayValues.createCell(2);
			cellInstagramValue.setCellValue(postsPerDayNetworks.getValue().get('I'));
			
			Cell cellGooglePlusValue = rowReportPerDayValues.createCell(3);
			cellGooglePlusValue.setCellValue(postsPerDayNetworks.getValue().get('G'));
			
			Cell cellYoutubeValue = rowReportPerDayValues.createCell(4);
			cellYoutubeValue.setCellValue(postsPerDayNetworks.getValue().get('Y'));
			
			Cell cellTotalValue = rowReportPerDayValues.createCell(5);
			cellTotalValue.setCellValue(postsPerDay);
			
			rowNumber++;
		}
		
		rowNumber++;
		Row rowAveragePosts = sheetReportOverTimeDays.createRow(rowNumber);
		rowAveragePosts.setHeightInPoints(headerDataRowHeight);
		
		Cell cellAveragePostsTitle = rowAveragePosts.createCell(0);
		cellAveragePostsTitle.setCellValue("Média diária:");
		cellAveragePostsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellAveragePostsValue = rowAveragePosts.createCell(1);
		cellAveragePostsValue.setCellValue(monitoringReport.getDailyAverage());
		cellAveragePostsValue.setCellStyle(cellStylePreAnalysisResult);
		
		rowNumber++;
		Row rowDayMorePosts = sheetReportOverTimeDays.createRow(rowNumber);
		rowDayMorePosts.setHeightInPoints(headerDataRowHeight);
		
		Cell cellDayMorePostsTitle = rowDayMorePosts.createCell(0);
		cellDayMorePostsTitle.setCellValue("Dia com mais posts:");
		cellDayMorePostsTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellDayMorePostsValue = rowDayMorePosts.createCell(1);
		cellDayMorePostsValue.setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(monitoringReport.getDayWithMorePosts()));
		cellDayMorePostsValue.setCellStyle(cellStylePreAnalysisResult);
		
		sheetReportOverTimeDays.setDefaultColumnWidth(20);
	}
	
	private void addNetworkTitleCells(Row row, String firstCellTitle) {
		
		Cell cellTimeRangeTitle = row.createCell(0);
		cellTimeRangeTitle.setCellValue(firstCellTitle);
		cellTimeRangeTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTimeRangeTwitterTitle = row.createCell(1);
		cellTimeRangeTwitterTitle.setCellValue("Twitter");
		cellTimeRangeTwitterTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellTimeRangeInstagramTitle = row.createCell(2);
		cellTimeRangeInstagramTitle.setCellValue("Instagram");
		cellTimeRangeInstagramTitle.setCellStyle(cellSecondaryHeaderNumber);

		Cell cellTimeRangeGooglePlusTitle = row.createCell(3);
		cellTimeRangeGooglePlusTitle.setCellValue("Google+");
		cellTimeRangeGooglePlusTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellTimeRangeYoutubeTitle = row.createCell(4);
		cellTimeRangeYoutubeTitle.setCellValue("Youtube");
		cellTimeRangeYoutubeTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Cell cellTimeRangeTotalTitle = row.createCell(5);
		cellTimeRangeTotalTitle.setCellValue("Total");
		cellTimeRangeTotalTitle.setCellStyle(cellSecondaryHeaderNumber);
	}
	
	private void addReportOfNetworks() {
		
		Sheet sheetReportOfNetworks = wb.createSheet("Volume por rede");
		
		headerDataRowHeight = 1.2F * sheetReportOfNetworks.getDefaultRowHeightInPoints();
		
		addMainHeader(sheetReportOfNetworks, monitoring.getName() + " - Relatório de Monitoramento - Período de " + startDate + " a " + endDate, 6);
		
		Row rowReportOfNetworksTitle = sheetReportOfNetworks.createRow(2);
		rowReportOfNetworksTitle.setHeightInPoints(headerDataRowHeight);
		
		Cell cellReportOfNetworksTitle = rowReportOfNetworksTitle.createCell(0);
		cellReportOfNetworksTitle.setCellValue("RESULTADOS RECUPERADOS POR REDE");
		cellReportOfNetworksTitle.setCellStyle(cellStyleHeaderData);
		
		sheetReportOfNetworks.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
		
		Row rowNetworksTitleItems = sheetReportOfNetworks.createRow(3);
		rowNetworksTitleItems.setHeightInPoints(headerDataRowHeight);
		
		Cell cellNetworkTitle = rowNetworksTitleItems.createCell(0);
		cellNetworkTitle.setCellValue("Rede");
		cellNetworkTitle.setCellStyle(cellSecondaryHeaderData);
		
		Cell cellTotalPerNetworkTitle = rowNetworksTitleItems.createCell(1);
		cellTotalPerNetworkTitle.setCellValue("Total");
		cellTotalPerNetworkTitle.setCellStyle(cellSecondaryHeaderNumber);
		
		Row rowTwitter = sheetReportOfNetworks.createRow(4);
		
		Cell cellTwitter = rowTwitter.createCell(0);
		cellTwitter.setCellValue("Twitter");
		
		Cell cellTwitterValue = rowTwitter.createCell(1);
		cellTwitterValue.setCellValue(monitoringReport
				.getPostsPerDaysNetworks().containsKey('T') ? monitoringReport
				.getPostsPerDaysNetworks().get('T')
				: 0D);
		
		Row rowInstagram = sheetReportOfNetworks.createRow(5);
		
		Cell cellInstagram = rowInstagram.createCell(0);
		cellInstagram.setCellValue("Instagram");
		
		Cell cellInstagramValue = rowInstagram.createCell(1);
		cellInstagramValue.setCellValue(monitoringReport
				.getPostsPerDaysNetworks().containsKey('I') ? monitoringReport
				.getPostsPerDaysNetworks().get('I')
				: 0D);
		
		Row rowGooglePlus = sheetReportOfNetworks.createRow(6);
		
		Cell cellGooglePlus = rowGooglePlus.createCell(0);
		cellGooglePlus.setCellValue("Google+");
		
		Cell cellGooglePlusValue = rowGooglePlus.createCell(1);
		cellGooglePlusValue.setCellValue(monitoringReport
				.getPostsPerDaysNetworks().containsKey('G') ? monitoringReport
				.getPostsPerDaysNetworks().get('G')
				: 0D);
		
		Row rowYoutube = sheetReportOfNetworks.createRow(7);
		
		Cell cellYoutube = rowYoutube.createCell(0);
		cellYoutube.setCellValue("Youtube");
		
		Cell cellYoutubeValue = rowYoutube.createCell(1);
		cellYoutubeValue.setCellValue(monitoringReport
				.getPostsPerDaysNetworks().containsKey('Y') ? monitoringReport
				.getPostsPerDaysNetworks().get('Y')
				: 0D);
		
		Row rowNews = sheetReportOfNetworks.createRow(8);
		
		Cell cellNews = rowNews.createCell(0);
		cellNews.setCellValue("Notícias");
		
		Cell cellNewsValue = rowNews.createCell(1);
		cellNewsValue.setCellValue(monitoringReport
				.getPostsPerDaysNetworks().containsKey('N') ? monitoringReport
				.getPostsPerDaysNetworks().get('N')
				: 0D);
		
		Row rowBlogs = sheetReportOfNetworks.createRow(9);
		
		Cell cellBlogs = rowBlogs.createCell(0);
		cellBlogs.setCellValue("Blogs");
		
		Cell cellBlogsValue = rowBlogs.createCell(1);
		cellBlogsValue.setCellValue(monitoringReport
				.getPostsPerDaysNetworks().containsKey('B') ? monitoringReport
				.getPostsPerDaysNetworks().get('B')
				: 0D);
		
		Row rowReclameAqui = sheetReportOfNetworks.createRow(10);
		
		Cell cellReclameAqui = rowReclameAqui.createCell(0);
		cellReclameAqui.setCellValue("Reclame Aqui");
		
		Cell cellReclameAquiValue = rowReclameAqui.createCell(1);
		cellReclameAquiValue.setCellValue(monitoringReport
				.getPostsPerDaysNetworks().containsKey('R') ? monitoringReport
				.getPostsPerDaysNetworks().get('R')
				: 0D);
		
		Row rowPostsTrashed = sheetReportOfNetworks.createRow(11);
		
		Cell cellPostsTrashed = rowPostsTrashed.createCell(0);
		cellPostsTrashed.setCellValue("Lixo removido");
		
		Cell cellPostsTrashedValue = rowPostsTrashed.createCell(1);
		cellPostsTrashedValue.setCellValue(monitoringReport.getGarbagePostsTotal());
		
		Row rowPostsTotal = sheetReportOfNetworks.createRow(13);
		rowPostsTotal.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsTotalTitle = rowPostsTotal.createCell(0);
		cellPostsTotalTitle.setCellValue("Total de posts:");
		cellPostsTotalTitle.setCellStyle(cellStylePreAnalysis);
		
		Cell cellPostsTotalValue = rowPostsTotal.createCell(1);
		cellPostsTotalValue.setCellValue(monitoringReport.getPostsTotal());
		cellPostsTotalValue.setCellStyle(cellStylePreAnalysisResult);
		
		Row rowPostsVolume = sheetReportOfNetworks.createRow(14);
		rowPostsVolume.setHeightInPoints(headerDataRowHeight);
		
		Cell cellPostsVolumeTitle = rowPostsVolume.createCell(0);
		cellPostsVolumeTitle.setCellValue("Maior volume:");
		cellPostsVolumeTitle.setCellStyle(cellStylePreAnalysis);
		
		String highestPostsVolumeNetwork = UtilFunctions
				.getNetworkName(monitoringReport.getHighestPostsVolumeNetwork());
		
		Cell cellPostsVolumeValue = rowPostsVolume.createCell(1);
		cellPostsVolumeValue.setCellValue(highestPostsVolumeNetwork + " (" + monitoringReport.getHighestPostsVolumePercentage() + "%)");
		cellPostsVolumeValue.setCellStyle(cellStylePreAnalysisResult);
		
		sheetReportOfNetworks.setDefaultColumnWidth(25);
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
	        createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));
	        
    	//by default hyperlinks are blue and underlined
    	cellStyleHLink = wb.createCellStyle();
    	
    	Font fontHLink = wb.createFont();
    	fontHLink.setUnderline(Font.U_SINGLE);
    	fontHLink.setColor(IndexedColors.BLUE.getIndex());
    	
    	cellStyleHLink.setFont(fontHLink);
    	
    	cellDataPercentage = wb.createCellStyle();
    	cellDataPercentage.setAlignment(CellStyle.ALIGN_RIGHT);
	}
	
	private Long getTotalTaggedPosts(List<MonitoringTagReportDTO> monitoringTagReports) {
		
		Long taggedPostsTotal = 0L;
		
		for (MonitoringTagReportDTO monitoringTagReportDTO : monitoringTagReports) {
			taggedPostsTotal += monitoringTagReportDTO.getTotalTaggedPosts();
		}
		
		return taggedPostsTotal;
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
