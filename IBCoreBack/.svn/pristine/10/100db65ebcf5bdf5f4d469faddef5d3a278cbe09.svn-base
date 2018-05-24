package br.com.opsocial.server.utils.reports.pdf;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SyncFailedException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

import br.com.opsocial.client.entity.report.ReportLogoDTO;
import br.com.opsocial.client.entity.report.facebook.FaceReportPostDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookContentDTO;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.pdf.HeaderFooterPDF;
import br.com.opsocial.server.utils.reports.ReportIcons;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ReportFacebookContentPDF {

	private ReportFacebookContentDTO reportFacebookContent;
	private Profile profile;

	private PdfWriter pdfWritter;
	private Document document;
	private String fileName;

	private HashMap<String, String> chartsImages;
	private java.util.List<File> imagesTemp;

	private static BaseFont sourceSansPro;

	private static final BaseColor BASE_COLOR_HEADER_TITLE = new BaseColor(52, 98, 144);

	public ReportFacebookContentPDF(ReportFacebookContentDTO reportFacebookContent, Profile profile, HashMap<String, String> chartsImages) {
		
		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".pdf");
		this.reportFacebookContent = reportFacebookContent;
		this.profile = profile;
		this.chartsImages = chartsImages;
		this.document = new Document(PageSize.A4, 36, 36, 84, 54);

		try {

			this.pdfWritter = PdfWriter.getInstance(document, new FileOutputStream("" + fileName));
			this.pdfWritter.setStrictImageSequence(true);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	static {

		sourceSansPro = null;

		try {
			sourceSansPro = BaseFont.createFont(
				"" + "SourceSansPro-R.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createPDF() {

		this.imagesTemp = new ArrayList<File>();

		addHeaderFooter();
		this.document.open();

//		addBasicInformation();
//		addGeneralPostsStats();
//		addReachOfPosts();
//		addImpressionsOfPosts();
//		addStoriesOfPosts();
//		addConsumptionOfPosts();
//		addFansOnline();

		document.close();

		removeImagesTemp();
	}
	
//	private void addFansOnline() {
//		
//		try {
//			
//			PdfPTable tableFansOnline = new PdfPTable(1);
//			tableFansOnline.setTotalWidth(new float[]{523});
//			tableFansOnline.setLockedWidth(true);
//			tableFansOnline.setSpacingAfter(22);
//
//			PdfPCell cellFansOnlineTitle = new PdfPCell();
//			cellFansOnlineTitle.setBorder(Rectangle.BOTTOM);
//			cellFansOnlineTitle.setBorderWidthBottom(0.8f);
//			cellFansOnlineTitle.setPaddingBottom(5);
//			cellFansOnlineTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
//			
//			Paragraph parFansOnlineTitle = new Paragraph("FÃS ONLINE POR HORÁRIO E DIA DA SEMANA", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
//			parFansOnlineTitle.setAlignment(Element.ALIGN_CENTER);
//			
//			cellFansOnlineTitle.addElement(parFansOnlineTitle);
//			
//			PdfPCell cellChartFansOnlinePerHour = new PdfPCell();
//			cellChartFansOnlinePerHour.setBorder(PdfPCell.NO_BORDER);
//			cellChartFansOnlinePerHour.setPadding(1);
//
//			cellChartFansOnlinePerHour.addElement(addChartFansOnlinePerHour());
//			
//			PdfPCell cellFansOnlineStats = new PdfPCell();
//			cellFansOnlineStats.setBorder(PdfPCell.NO_BORDER);
//			cellFansOnlineStats.setPadding(1);
//			
//			PdfPTable tableFansOnlineStats = new PdfPTable(2);
//			tableFansOnlineStats.setTotalWidth(new float[]{210, 170});
//			tableFansOnlineStats.setLockedWidth(true);
//			tableFansOnlineStats.setSpacingBefore(10);
//			
//			PdfPCell cellChartFansOnlinePerWeekDay = new PdfPCell();
//			cellChartFansOnlinePerWeekDay.setBorder(PdfPCell.NO_BORDER);
//			cellChartFansOnlinePerWeekDay.setPadding(0);
//			
//			cellChartFansOnlinePerWeekDay.addElement(addChartFansOnlinePerWeekDay());
//			
//			PdfPCell cellFansOnlineStatsInfo = new PdfPCell();
//			cellFansOnlineStatsInfo.setBorder(PdfPCell.NO_BORDER);
//			cellFansOnlineStatsInfo.setPaddingTop(8);
//			cellFansOnlineStatsInfo.setPaddingBottom(0);
//			cellFansOnlineStatsInfo.setPaddingRight(0);
//			cellFansOnlineStatsInfo.setPaddingLeft(30);
//
//			PdfPTable tableFansOnlineStatsInfo = new PdfPTable(1);
//			tableFansOnlineStatsInfo.setTotalWidth(new float[]{170});
//			tableFansOnlineStatsInfo.setLockedWidth(true);
//
//			// Time with more fans online.
//			PdfPCell cellTimeMoreFansOnlineTitle = new PdfPCell();
//			cellTimeMoreFansOnlineTitle.setBorder(PdfPCell.NO_BORDER);
//			cellTimeMoreFansOnlineTitle.setPaddingRight(0);
//			cellTimeMoreFansOnlineTitle.setPaddingLeft(0);
//			cellTimeMoreFansOnlineTitle.setPaddingTop(0);
//			cellTimeMoreFansOnlineTitle.setPaddingBottom(4);
//
//			Paragraph parTimeMoreFansOnlineTitle = new Paragraph("MAIOR NÚMERO DE FÃS ONLINE", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parTimeMoreFansOnlineTitle.setLeading(9);
//
//			cellTimeMoreFansOnlineTitle.addElement(parTimeMoreFansOnlineTitle);
//
//			PdfPCell cellTimeMoreFansOnline = new PdfPCell();
//			cellTimeMoreFansOnline.setBorder(PdfPCell.NO_BORDER);
//			cellTimeMoreFansOnline.setPaddingRight(0);
//			cellTimeMoreFansOnline.setPaddingLeft(0);
//			cellTimeMoreFansOnline.setPaddingTop(0);
//			cellTimeMoreFansOnline.setPaddingBottom(2);
//
//			Paragraph parTimeMoreFansOnline = new Paragraph();
//			parTimeMoreFansOnline.setLeading(11);
//
//			Image imgTimeMoreFansOnline = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_POSTS_HOUR));
//			imgTimeMoreFansOnline.scaleAbsolute(10, 10);
//
//			parTimeMoreFansOnline.add(new Chunk(imgTimeMoreFansOnline, 0, 0));
//
//			String timeMoreFansOnlineFrom = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, 
//					Integer.parseInt(reportFacebookContent.getTimeWithMoreFansOnline().substring(reportFacebookContent.getTimeWithMoreFansOnline().lastIndexOf('_') + 1)), 0, 0));
//			String timeMoreFansOnlineUntil = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, 
//					Integer.parseInt(reportFacebookContent.getTimeWithMoreFansOnline().substring(reportFacebookContent.getTimeWithMoreFansOnline().lastIndexOf('_') + 1)) + 1, 0, 0));
//			
//			parTimeMoreFansOnline.add(new Chunk(" " + timeMoreFansOnlineFrom + " - " + timeMoreFansOnlineUntil,
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(145, 205, 51))));
//
//			cellTimeMoreFansOnline.addElement(parTimeMoreFansOnline);
//
//			PdfPCell cellTimeMoreFansOnlineDescription = new PdfPCell();
//			cellTimeMoreFansOnlineDescription.setBorder(PdfPCell.NO_BORDER);
//			cellTimeMoreFansOnlineDescription.setPaddingRight(0);
//			cellTimeMoreFansOnlineDescription.setPaddingLeft(0);
//			cellTimeMoreFansOnlineDescription.setPaddingTop(0);
//			cellTimeMoreFansOnlineDescription.setPaddingBottom(8);
//
//			Paragraph parTimeMoreFansOnlineDescription = new Paragraph(7, reportFacebookContent.getTimeWithMoreFansOnlinePercentage() + "% " +
//					"dos fãs estiveram conectados entre as " + timeMoreFansOnlineFrom + " e " + timeMoreFansOnlineUntil + "hrs.", 
//					new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
//			
//			cellTimeMoreFansOnlineDescription.addElement(parTimeMoreFansOnlineDescription);
//			
//			// Week day with more fans online.
//			PdfPCell cellWeekDayMoreFansOnlineTitle = new PdfPCell();
//			cellWeekDayMoreFansOnlineTitle.setBorder(PdfPCell.NO_BORDER);
//			cellWeekDayMoreFansOnlineTitle.setPaddingRight(0);
//			cellWeekDayMoreFansOnlineTitle.setPaddingLeft(0);
//			cellWeekDayMoreFansOnlineTitle.setPaddingTop(0);
//			cellWeekDayMoreFansOnlineTitle.setPaddingBottom(4);
//
//			Paragraph parWeekDayMoreFansOnlineTitle = new Paragraph("DIA COM MAIOR ALCANCE", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parWeekDayMoreFansOnlineTitle.setLeading(9);
//
//			cellWeekDayMoreFansOnlineTitle.addElement(parWeekDayMoreFansOnlineTitle);
//
//			PdfPCell cellWeekDayMoreFansOnline = new PdfPCell();
//			cellWeekDayMoreFansOnline.setBorder(PdfPCell.NO_BORDER);
//			cellWeekDayMoreFansOnline.setPaddingRight(0);
//			cellWeekDayMoreFansOnline.setPaddingLeft(0);
//			cellWeekDayMoreFansOnline.setPaddingTop(0);
//			cellWeekDayMoreFansOnline.setPaddingBottom(2);
//
//			Paragraph parWeekDayMoreFansOnline = new Paragraph();
//			parWeekDayMoreFansOnline.setLeading(11);
//
//			Image imgWeekDayMoreFansOnline = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_POSTS_WEEK_DAY));
//			imgWeekDayMoreFansOnline.scaleAbsolute(10, 10);
//
//			parWeekDayMoreFansOnline.add(new Chunk(imgWeekDayMoreFansOnline, 0, 0));
//
//			parWeekDayMoreFansOnline.add(new Chunk(" " + UtilFunctions.getFormattedWeekDayShort(reportFacebookContent.getWeekDayWithMoreFansOnline()).toUpperCase(),
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(61, 129, 197))));
//
//			cellWeekDayMoreFansOnline.addElement(parWeekDayMoreFansOnline);
//
//			PdfPCell cellWeekDayMoreFansOnlineDescription = new PdfPCell();
//			cellWeekDayMoreFansOnlineDescription.setBorder(PdfPCell.NO_BORDER);
//			cellWeekDayMoreFansOnlineDescription.setPaddingRight(0);
//			cellWeekDayMoreFansOnlineDescription.setPaddingLeft(0);
//			cellWeekDayMoreFansOnlineDescription.setPaddingTop(0);
//			cellWeekDayMoreFansOnlineDescription.setPaddingBottom(8);
//
//			Paragraph parWeekDayMoreFansOnlineDescription = new Paragraph(7, reportFacebookContent.getWeekDayWithMoreFansOnlinePercentage() + "% " +
//					"dos fãs estiveram conectados " + UtilFunctions.getFormattedWeekDay(reportFacebookContent.getWeekDayWithMoreFansOnline()) + ".", 
//					new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
//
//			cellWeekDayMoreFansOnlineDescription.addElement(parWeekDayMoreFansOnlineDescription);
//			
//			tableFansOnlineStatsInfo.addCell(cellTimeMoreFansOnlineTitle);
//			tableFansOnlineStatsInfo.addCell(cellTimeMoreFansOnline);
//			tableFansOnlineStatsInfo.addCell(cellTimeMoreFansOnlineDescription);
//			tableFansOnlineStatsInfo.addCell(cellWeekDayMoreFansOnlineTitle);
//			tableFansOnlineStatsInfo.addCell(cellWeekDayMoreFansOnline);
//			tableFansOnlineStatsInfo.addCell(cellWeekDayMoreFansOnlineDescription);
//			
//			cellFansOnlineStatsInfo.addElement(tableFansOnlineStatsInfo);
//			
//			tableFansOnlineStats.addCell(cellChartFansOnlinePerWeekDay);
//			tableFansOnlineStats.addCell(cellFansOnlineStatsInfo);
//			
//			cellFansOnlineStats.addElement(tableFansOnlineStats);
//			
//			tableFansOnline.addCell(cellFansOnlineTitle);
//			tableFansOnline.addCell(cellChartFansOnlinePerHour);
//			tableFansOnline.addCell(cellFansOnlineStats);
//			
//			document.add(tableFansOnline);
//			
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	private void addConsumptionOfPosts() {
//		
//		try {
//
//			PdfPTable tableConsumptionOfPosts = new PdfPTable(1);
//			tableConsumptionOfPosts.setTotalWidth(new float[]{523});
//			tableConsumptionOfPosts.setLockedWidth(true);
//			tableConsumptionOfPosts.setHorizontalAlignment(Element.ALIGN_CENTER);
//			tableConsumptionOfPosts.setSpacingAfter(22);
//
//			PdfPCell cellConsumptionOfPostsTitle = new PdfPCell();
//			cellConsumptionOfPostsTitle.setBorder(Rectangle.BOTTOM);
//			cellConsumptionOfPostsTitle.setBorderWidthBottom(0.8f);
//			cellConsumptionOfPostsTitle.setPaddingBottom(5);
//			cellConsumptionOfPostsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
//
//			Paragraph parConsumptionOfPostsTitle = new Paragraph("CONSUMO DE CONTEÚDO QUE NÃO GERARAM HISTÓRIAS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
//			parConsumptionOfPostsTitle.setAlignment(Element.ALIGN_CENTER);
//
//			cellConsumptionOfPostsTitle.addElement(parConsumptionOfPostsTitle);
//
//			PdfPCell cellConsumptionOfPosts = new PdfPCell();
//			cellConsumptionOfPosts.setBorder(PdfPCell.NO_BORDER);
//			cellConsumptionOfPosts.setPaddingBottom(0);
//
//			PdfPTable tableConsumptionOfPostsContent = new PdfPTable(2);
//			tableConsumptionOfPostsContent.setTotalWidth(new float[]{200, 170});
//			tableConsumptionOfPostsContent.setLockedWidth(true);
//
//			PdfPCell cellChartConsumptionOfPosts = new PdfPCell();
//			cellChartConsumptionOfPosts.setBorder(PdfPCell.NO_BORDER);
//			cellChartConsumptionOfPosts.setPadding(0);
//
//			cellChartConsumptionOfPosts.addElement(addChartConsumptionOfPosts());
//
//			PdfPCell cellConsumptionOfPostsInfo = new PdfPCell();
//			cellConsumptionOfPostsInfo.setBorder(PdfPCell.NO_BORDER);
//			cellConsumptionOfPostsInfo.setPaddingTop(8);
//			cellConsumptionOfPostsInfo.setPaddingBottom(0);
//			cellConsumptionOfPostsInfo.setPaddingRight(0);
//			cellConsumptionOfPostsInfo.setPaddingLeft(30);
//
//			PdfPTable tableConsumptionOfPostsInfo = new PdfPTable(1);
//			tableConsumptionOfPostsInfo.setTotalWidth(new float[]{170});
//			tableConsumptionOfPostsInfo.setLockedWidth(true);
//
//			// Posts Consumptions.
//			PdfPCell cellTotalOfClicksTitle = new PdfPCell();
//			cellTotalOfClicksTitle.setBorder(PdfPCell.NO_BORDER);
//			cellTotalOfClicksTitle.setPaddingRight(0);
//			cellTotalOfClicksTitle.setPaddingLeft(0);
//			cellTotalOfClicksTitle.setPaddingTop(0);
//			cellTotalOfClicksTitle.setPaddingBottom(4);
//
//			Paragraph parTotalOfClicksTitle = new Paragraph("CLIQUES SEM GERAR HISTÓRIAS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parTotalOfClicksTitle.setLeading(9);
//
//			cellTotalOfClicksTitle.addElement(parTotalOfClicksTitle);
//
//			PdfPCell cellTotalOfClicks = new PdfPCell();
//			cellTotalOfClicks.setBorder(PdfPCell.NO_BORDER);
//			cellTotalOfClicks.setPaddingRight(0);
//			cellTotalOfClicks.setPaddingLeft(0);
//			cellTotalOfClicks.setPaddingTop(0);
//			cellTotalOfClicks.setPaddingBottom(2);
//
//			Paragraph parTotalOfClicks = new Paragraph();
//			parTotalOfClicks.setLeading(11);
//
//			Image imgTotalOfClicks = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_TOTAL_POSTS_CLICKS));
//			imgTotalOfClicks.scaleAbsolute(10, 10);
//
//			parTotalOfClicks.add(new Chunk(imgTotalOfClicks, 0, 0));
//
//			parTotalOfClicks.add(new Chunk(" " + reportFacebookContent.getPostsConsumptionsCount().toString(),
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(52, 98, 144))));
//
//			cellTotalOfClicks.addElement(parTotalOfClicks);
//
//			PdfPCell cellTotalOfClicksDescription = new PdfPCell();
//			cellTotalOfClicksDescription.setBorder(PdfPCell.NO_BORDER);
//			cellTotalOfClicksDescription.setPaddingRight(0);
//			cellTotalOfClicksDescription.setPaddingLeft(0);
//			cellTotalOfClicksDescription.setPaddingTop(0);
//			cellTotalOfClicksDescription.setPaddingBottom(8);
//
//			Paragraph parTotalOfClicksDescription = new Paragraph(7, "A página registrou " + reportFacebookContent.getPostsConsumptionsCount() + " cliques nos últimos " +
//					UtilFunctions.getDaysBetween(reportFacebookContent.getDateFrom(), reportFacebookContent.getDateUntil()) + " dias. " + 
//					"Sendo " + reportFacebookContent.getPostsConsumptionsPhotoViewPercentage() + "% de fotos, " +
//					+ reportFacebookContent.getPostsConsumptionsLinkClicksPercentage() + "% de links, " +
//					+ reportFacebookContent.getPostsConsumptionsVideoPlaysPercentage() + "% de vídeos e " +
//					+ reportFacebookContent.getPostsConsumptionsOtherClicksPercentage() + "% de outros.", 
//					new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
//			
//			cellTotalOfClicksDescription.addElement(parTotalOfClicksDescription);
//
//			// Posts Consumptions By Consumption Type.
//			PdfPCell cellPostsConsumptionsByTypeTable = new PdfPCell();
//			cellPostsConsumptionsByTypeTable.setPadding(0);
//			cellPostsConsumptionsByTypeTable.setBorder(PdfPCell.NO_BORDER);
//
//			PdfPTable tablePostsConsumptionsByTypeHeader = new PdfPTable(2);
//			tablePostsConsumptionsByTypeHeader.setTotalWidth(new float[]{82, 64});
//			tablePostsConsumptionsByTypeHeader.setLockedWidth(true);
//			tablePostsConsumptionsByTypeHeader.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
//			tablePostsConsumptionsByTypeHeader.setSpacingBefore(3);
//
//			PdfPCell cellPostConsumptionsType = new PdfPCell(new Phrase(new Chunk("Tipo de Consumo", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
//			cellPostConsumptionsType.setBorder(Rectangle.BOTTOM);
//			cellPostConsumptionsType.setBorderWidthBottom(0.8f);
//			cellPostConsumptionsType.setBorderColorBottom(new BaseColor(227, 227, 228));
//			cellPostConsumptionsType.setBackgroundColor(new BaseColor(228, 228, 228));
//			cellPostConsumptionsType.setPadding(3);
//			cellPostConsumptionsType.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//			cellPostConsumptionsType.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			
//			PdfPCell cellPostConsumptionsAverage = new PdfPCell(new Phrase(new Chunk("Média por post", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
//			cellPostConsumptionsAverage.setBorder(Rectangle.BOTTOM);
//			cellPostConsumptionsAverage.setBorderWidthBottom(0.8f);
//			cellPostConsumptionsAverage.setBorderColorBottom(new BaseColor(227, 227, 228));
//			cellPostConsumptionsAverage.setBackgroundColor(new BaseColor(217, 217, 217));
//			cellPostConsumptionsAverage.setPadding(3);
//			cellPostConsumptionsAverage.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostConsumptionsAverage.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			tablePostsConsumptionsByTypeHeader.addCell(cellPostConsumptionsType);
//			tablePostsConsumptionsByTypeHeader.addCell(cellPostConsumptionsAverage);
//
//			PdfPTable tablePostsStoriesByTypePostRows = new PdfPTable(2);
//			tablePostsStoriesByTypePostRows.setTotalWidth(new float[]{82, 64});
//			tablePostsStoriesByTypePostRows.setLockedWidth(true);
//			tablePostsStoriesByTypePostRows.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
//
//			// Post Photo View Row.
//			PdfPCell cellPostPhotoViewRow = new PdfPCell(new Phrase(new Chunk("Fotos clicadas", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostPhotoViewRow.setBorder(Rectangle.BOTTOM);
//			cellPostPhotoViewRow.setBorderWidthBottom(0.8f);
//			cellPostPhotoViewRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostPhotoViewRow.setPadding(3);
//			cellPostPhotoViewRow.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//			cellPostPhotoViewRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			
//			PdfPCell cellPostPhotoViewAverageRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getAverageConsumptionsPhotoViewPerPost().toString(), 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostPhotoViewAverageRow.setBorder(Rectangle.BOTTOM);
//			cellPostPhotoViewAverageRow.setBorderWidthBottom(0.8f);
//			cellPostPhotoViewAverageRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostPhotoViewAverageRow.setBackgroundColor(new BaseColor(242, 242, 242));
//			cellPostPhotoViewAverageRow.setPadding(3);
//			cellPostPhotoViewAverageRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostPhotoViewAverageRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			
//			// Post Link Clicks Row.
//			PdfPCell cellPostLinkClicksRow = new PdfPCell(new Phrase(new Chunk("Links clicados", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostLinkClicksRow.setBorder(Rectangle.BOTTOM);
//			cellPostLinkClicksRow.setBorderWidthBottom(0.8f);
//			cellPostLinkClicksRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostLinkClicksRow.setPadding(3);
//			cellPostLinkClicksRow.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//			cellPostLinkClicksRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			PdfPCell cellPostLinkClicksAverageRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getAverageConsumptionsLinkClicksPerPost().toString(), 
//					new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostLinkClicksAverageRow.setBorder(Rectangle.BOTTOM);
//			cellPostLinkClicksAverageRow.setBorderWidthBottom(0.8f);
//			cellPostLinkClicksAverageRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostLinkClicksAverageRow.setBackgroundColor(new BaseColor(242, 242, 242));
//			cellPostLinkClicksAverageRow.setPadding(3);
//			cellPostLinkClicksAverageRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostLinkClicksAverageRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			
//			// Post Video Plays Row.
//			PdfPCell cellPostVideoPlaysRow = new PdfPCell(new Phrase(new Chunk("Vídeos reproduzidos", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostVideoPlaysRow.setBorder(Rectangle.BOTTOM);
//			cellPostVideoPlaysRow.setBorderWidthBottom(0.8f);
//			cellPostVideoPlaysRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostVideoPlaysRow.setPadding(3);
//			cellPostVideoPlaysRow.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//			cellPostVideoPlaysRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			PdfPCell cellPostVideoPlaysAverageRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getAverageConsumptionsVideoPlaysPerPost().toString(), 
//					new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostVideoPlaysAverageRow.setBorder(Rectangle.BOTTOM);
//			cellPostVideoPlaysAverageRow.setBorderWidthBottom(0.8f);
//			cellPostVideoPlaysAverageRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostVideoPlaysAverageRow.setBackgroundColor(new BaseColor(242, 242, 242));
//			cellPostVideoPlaysAverageRow.setPadding(3);
//			cellPostVideoPlaysAverageRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostVideoPlaysAverageRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			
//			// Post Other Clicks Row.
//			PdfPCell cellPostOtherClicksRow = new PdfPCell(new Phrase(new Chunk("Outros", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostOtherClicksRow.setBorder(Rectangle.BOTTOM);
//			cellPostOtherClicksRow.setBorderWidthBottom(0.8f);
//			cellPostOtherClicksRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostOtherClicksRow.setPadding(3);
//			cellPostOtherClicksRow.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//			cellPostOtherClicksRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			PdfPCell cellPostOtherClicksAverageRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getAverageConsumptionsOtherClicksPerPost().toString(), 
//					new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostOtherClicksAverageRow.setBorder(Rectangle.BOTTOM);
//			cellPostOtherClicksAverageRow.setBorderWidthBottom(0.8f);
//			cellPostOtherClicksAverageRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostOtherClicksAverageRow.setBackgroundColor(new BaseColor(242, 242, 242));
//			cellPostOtherClicksAverageRow.setPadding(3);
//			cellPostOtherClicksAverageRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostOtherClicksAverageRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			
//			tablePostsStoriesByTypePostRows.addCell(cellPostPhotoViewRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostPhotoViewAverageRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostLinkClicksRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostLinkClicksAverageRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostVideoPlaysRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostVideoPlaysAverageRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostOtherClicksRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostOtherClicksAverageRow);
//
//			cellPostsConsumptionsByTypeTable.addElement(tablePostsConsumptionsByTypeHeader);
//			cellPostsConsumptionsByTypeTable.addElement(tablePostsStoriesByTypePostRows);
//
//			tableConsumptionOfPostsInfo.addCell(cellTotalOfClicksTitle);
//			tableConsumptionOfPostsInfo.addCell(cellTotalOfClicks);
//			tableConsumptionOfPostsInfo.addCell(cellTotalOfClicksDescription);
//			tableConsumptionOfPostsInfo.addCell(cellPostsConsumptionsByTypeTable);
//			
//			cellConsumptionOfPostsInfo.addElement(tableConsumptionOfPostsInfo);
//			
//			tableConsumptionOfPostsContent.addCell(cellChartConsumptionOfPosts);
//			tableConsumptionOfPostsContent.addCell(cellConsumptionOfPostsInfo);
//
//			cellConsumptionOfPosts.addElement(tableConsumptionOfPostsContent);
//
//			tableConsumptionOfPosts.addCell(cellConsumptionOfPostsTitle);
//			tableConsumptionOfPosts.addCell(cellConsumptionOfPosts);
//
//			document.add(tableConsumptionOfPosts);
//
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
//
//	private void addStoriesOfPosts() {
//
//		try {
//
//			document.newPage();
//
//			PdfPTable tableStoriesOfPosts = new PdfPTable(1);
//			tableStoriesOfPosts.setTotalWidth(new float[]{523});
//			tableStoriesOfPosts.setLockedWidth(true);
//			tableStoriesOfPosts.setHorizontalAlignment(Element.ALIGN_CENTER);
//			tableStoriesOfPosts.setSpacingAfter(22);
//
//			PdfPCell cellStoriesOfPostsTitle = new PdfPCell();
//			cellStoriesOfPostsTitle.setBorder(Rectangle.BOTTOM);
//			cellStoriesOfPostsTitle.setBorderWidthBottom(0.8f);
//			cellStoriesOfPostsTitle.setPaddingBottom(5);
//			cellStoriesOfPostsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
//
//			Paragraph parStoriesOfPostsTitle = new Paragraph("ENVOLVIMENTO DO PÚBLICO POR TIPO DE INTERAÇÃO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
//			parStoriesOfPostsTitle.setAlignment(Element.ALIGN_CENTER);
//
//			cellStoriesOfPostsTitle.addElement(parStoriesOfPostsTitle);
//
//			PdfPCell cellStoriesOfPosts = new PdfPCell();
//			cellStoriesOfPosts.setBorder(PdfPCell.NO_BORDER);
//			cellStoriesOfPosts.setPaddingBottom(0);
//
//			PdfPTable tableStoriesOfPostsContent = new PdfPTable(2);
//			tableStoriesOfPostsContent.setTotalWidth(new float[]{200, 170});
//			tableStoriesOfPostsContent.setLockedWidth(true);
//
//			PdfPCell cellChartStoriesOfPosts = new PdfPCell();
//			cellChartStoriesOfPosts.setBorder(PdfPCell.NO_BORDER);
//			cellChartStoriesOfPosts.setPadding(0);
//
//			cellChartStoriesOfPosts.addElement(addChartStoriesOfPosts());
//
//			PdfPCell cellStoriesOfPostsInfo = new PdfPCell();
//			cellStoriesOfPostsInfo.setBorder(PdfPCell.NO_BORDER);
//			cellStoriesOfPostsInfo.setPaddingTop(8);
//			cellStoriesOfPostsInfo.setPaddingBottom(0);
//			cellStoriesOfPostsInfo.setPaddingRight(0);
//			cellStoriesOfPostsInfo.setPaddingLeft(30);
//
//			PdfPTable tableStoriesOfPostsInfo = new PdfPTable(1);
//			tableStoriesOfPostsInfo.setTotalWidth(new float[]{170});
//			tableStoriesOfPostsInfo.setLockedWidth(true);
//
//			// Posts stories and interactions in the period.
//			PdfPCell cellTotalOfStoriesTitle = new PdfPCell();
//			cellTotalOfStoriesTitle.setBorder(PdfPCell.NO_BORDER);
//			cellTotalOfStoriesTitle.setPaddingRight(0);
//			cellTotalOfStoriesTitle.setPaddingLeft(0);
//			cellTotalOfStoriesTitle.setPaddingTop(0);
//			cellTotalOfStoriesTitle.setPaddingBottom(2);
//
//			Paragraph parTotalOfStoriesTitle = new Paragraph("TOTAL DE INTERAÇÕES NO PERÍODO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parTotalOfStoriesTitle.setLeading(9);
//
//			cellTotalOfStoriesTitle.addElement(parTotalOfStoriesTitle);
//
//			PdfPCell cellTotalOfStories = new PdfPCell();
//			cellTotalOfStories.setBorder(PdfPCell.NO_BORDER);
//			cellTotalOfStories.setPaddingRight(0);
//			cellTotalOfStories.setPaddingLeft(0);
//			cellTotalOfStories.setPaddingTop(0);
//			cellTotalOfStories.setPaddingBottom(2);
//
//			Paragraph parTotalOfStories = new Paragraph();
//			parTotalOfStories.setLeading(11);
//
//			Image imgTotalOfStories = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_TOTAL_STORIES));
//			imgTotalOfStories.scaleAbsolute(10, 10);
//
//			parTotalOfStories.add(new Chunk(imgTotalOfStories, 0, 0));
//
//			parTotalOfStories.add(new Chunk(" " + reportFacebookContent.getPostsStoriesCount().toString(),
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(52, 98, 144))));
//
//			cellTotalOfStories.addElement(parTotalOfStories);
//
//			PdfPCell cellTotalOfStoriesDescription = new PdfPCell();
//			cellTotalOfStoriesDescription.setBorder(PdfPCell.NO_BORDER);
//			cellTotalOfStoriesDescription.setPaddingRight(0);
//			cellTotalOfStoriesDescription.setPaddingLeft(0);
//			cellTotalOfStoriesDescription.setPaddingTop(0);
//			cellTotalOfStoriesDescription.setPaddingBottom(8);
//
//			Paragraph parTotalOfStoriesDescription = new Paragraph();
//			parTotalOfStoriesDescription.setLeading(7);
//
//			parTotalOfStoriesDescription.add("A página registrou " + reportFacebookContent.getPostsStoriesCount() + " interações nos últimos " + 
//					+ UtilFunctions.getDaysBetween(reportFacebookContent.getDateFrom(), reportFacebookContent.getDateUntil()) + " dias. " + 
//					"Sendo " + reportFacebookContent.getPostsStoriesLikePercentage() + "% de likes, " +
//					+ reportFacebookContent.getPostsStoriesCommentPercentage() + "% de comentários e " +
//					+ reportFacebookContent.getPostsStoriesSharePercentage() + "% compartilhamentos.");
//			parTotalOfStoriesDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
//
//			cellTotalOfStoriesDescription.addElement(parTotalOfStoriesDescription);
//
//			// Posts Stories By Post Type.
//			PdfPCell cellPostsStoriesByTypePostTable = new PdfPCell();
//			cellPostsStoriesByTypePostTable.setPadding(0);
//			cellPostsStoriesByTypePostTable.setBorder(PdfPCell.NO_BORDER);
//
//			PdfPTable tablePostsStoriesByTypePostHeader = new PdfPTable(4);
//			tablePostsStoriesByTypePostHeader.setTotalWidth(new float[]{50, 32, 32, 32});
//			tablePostsStoriesByTypePostHeader.setLockedWidth(true);
//			tablePostsStoriesByTypePostHeader.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
//			tablePostsStoriesByTypePostHeader.setSpacingBefore(3);
//
//			PdfPCell cellPostType = new PdfPCell(new Phrase(new Chunk("Tipo de Post", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
//			cellPostType.setBorder(Rectangle.BOTTOM);
//			cellPostType.setBorderWidthBottom(0.8f);
//			cellPostType.setBorderColorBottom(new BaseColor(227, 227, 228));
//			cellPostType.setBackgroundColor(new BaseColor(228, 228, 228));
//			cellPostType.setPadding(3);
//			cellPostType.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//			cellPostType.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			PdfPCell cellPostsLikes = new PdfPCell();
//			cellPostsLikes.setBorder(Rectangle.BOTTOM);
//			cellPostsLikes.setBorderWidthBottom(0.8f);
//			cellPostsLikes.setBorderColorBottom(new BaseColor(227, 227, 228));
//			cellPostsLikes.setBackgroundColor(new BaseColor(217, 217, 217));
//			cellPostsLikes.setPadding(3);
//			cellPostsLikes.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostsLikes.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			Image imgPostsLikes = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_POSTS_LIKES));
//			imgPostsLikes.scaleAbsolute(7, 7);
//			imgPostsLikes.setAlignment(Image.ALIGN_CENTER);
//
//			cellPostsLikes.addElement(imgPostsLikes);
//
//			PdfPCell cellPostsComments = new PdfPCell();
//			cellPostsComments.setBorder(Rectangle.BOTTOM);
//			cellPostsComments.setBorderWidthBottom(0.8f);
//			cellPostsComments.setBorderColorBottom(new BaseColor(227, 227, 228));
//			cellPostsComments.setBackgroundColor(new BaseColor(228, 228, 228));
//			cellPostsComments.setPadding(3);
//			cellPostsComments.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostsComments.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			Image imgPostsComments = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_POSTS_COMMENTS));
//			imgPostsComments.scaleAbsolute(7, 7);
//			imgPostsComments.setAlignment(PdfPCell.ALIGN_CENTER);
//
//			cellPostsComments.addElement(imgPostsComments);
//
//			PdfPCell cellPostsShares = new PdfPCell();
//			cellPostsShares.setBorder(Rectangle.BOTTOM);
//			cellPostsShares.setBorderWidthBottom(0.8f);
//			cellPostsShares.setBorderColorBottom(new BaseColor(227, 227, 228));
//			cellPostsShares.setBackgroundColor(new BaseColor(217, 217, 217));
//			cellPostsShares.setPadding(3);
//			cellPostsShares.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostsShares.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			Image imgPostsShares = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_POSTS_SHARES));
//			imgPostsShares.scaleAbsolute(5, 7);
//			imgPostsShares.setAlignment(Image.ALIGN_CENTER);
//
//			cellPostsShares.addElement(imgPostsShares);
//
//			tablePostsStoriesByTypePostHeader.addCell(cellPostType);
//			tablePostsStoriesByTypePostHeader.addCell(cellPostsLikes);
//			tablePostsStoriesByTypePostHeader.addCell(cellPostsComments);
//			tablePostsStoriesByTypePostHeader.addCell(cellPostsShares);
//
//			PdfPTable tablePostsStoriesByTypePostRows = new PdfPTable(4);
//			tablePostsStoriesByTypePostRows.setTotalWidth(new float[]{50, 32, 32, 32});
//			tablePostsStoriesByTypePostRows.setLockedWidth(true);
//			tablePostsStoriesByTypePostRows.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
//
//			// Post Type Status Row.
//			PdfPCell cellPostTypeStatusRow = new PdfPCell(new Phrase(new Chunk("Status", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostTypeStatusRow.setBorder(Rectangle.BOTTOM);
//			cellPostTypeStatusRow.setBorderWidthBottom(0.8f);
//			cellPostTypeStatusRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostTypeStatusRow.setPadding(3);
//			cellPostTypeStatusRow.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//			cellPostTypeStatusRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			
//			PdfPCell cellPostLikesStatusRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getPostsStoriesLikeByStatusType().containsKey(FaceReportPostDTO.STATUS_UPDATE) ? 
//						reportFacebookContent.getPostsStoriesLikeByStatusType().get(FaceReportPostDTO.STATUS_UPDATE).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostLikesStatusRow.setBorder(Rectangle.BOTTOM);
//			cellPostLikesStatusRow.setBorderWidthBottom(0.8f);
//			cellPostLikesStatusRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostLikesStatusRow.setBackgroundColor(new BaseColor(242, 242, 242));
//			cellPostLikesStatusRow.setPadding(3);
//			cellPostLikesStatusRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostLikesStatusRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			
//			PdfPCell cellPostCommentsStatusRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getPostsStoriesCommentByStatusType().containsKey(FaceReportPostDTO.STATUS_UPDATE) ? 
//							reportFacebookContent.getPostsStoriesCommentByStatusType().get(FaceReportPostDTO.STATUS_UPDATE).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostCommentsStatusRow.setBorder(Rectangle.BOTTOM);
//			cellPostCommentsStatusRow.setBorderWidthBottom(0.8f);
//			cellPostCommentsStatusRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostCommentsStatusRow.setPadding(3);
//			cellPostCommentsStatusRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostCommentsStatusRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			
//			PdfPCell cellPostSharesStatusRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getPostsStoriesShareByStatusType().containsKey(FaceReportPostDTO.STATUS_UPDATE) ? 
//							reportFacebookContent.getPostsStoriesShareByStatusType().get(FaceReportPostDTO.STATUS_UPDATE).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostSharesStatusRow.setBorder(Rectangle.BOTTOM);
//			cellPostSharesStatusRow.setBorderWidthBottom(0.8f);
//			cellPostSharesStatusRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostSharesStatusRow.setBackgroundColor(new BaseColor(242, 242, 242));
//			cellPostSharesStatusRow.setPadding(3);
//			cellPostSharesStatusRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostSharesStatusRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			
//			// Post Type Link Row.
//			PdfPCell cellPostTypeLinksRow = new PdfPCell(new Phrase(new Chunk("Link", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostTypeLinksRow.setBorder(Rectangle.BOTTOM);
//			cellPostTypeLinksRow.setBorderWidthBottom(0.8f);
//			cellPostTypeLinksRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostTypeLinksRow.setPadding(3);
//			cellPostTypeLinksRow.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//			cellPostTypeLinksRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			PdfPCell cellPostLikesLinksRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getPostsStoriesLikeByStatusType().containsKey(FaceReportPostDTO.LINK_POSTED) ? 
//							reportFacebookContent.getPostsStoriesLikeByStatusType().get(FaceReportPostDTO.LINK_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostLikesLinksRow.setBorder(Rectangle.BOTTOM);
//			cellPostLikesLinksRow.setBorderWidthBottom(0.8f);
//			cellPostLikesLinksRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostLikesLinksRow.setBackgroundColor(new BaseColor(242, 242, 242));
//			cellPostLikesLinksRow.setPadding(3);
//			cellPostLikesLinksRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostLikesLinksRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			PdfPCell cellPostCommentsLinksRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getPostsStoriesCommentByStatusType().containsKey(FaceReportPostDTO.LINK_POSTED) ? 
//							reportFacebookContent.getPostsStoriesCommentByStatusType().get(FaceReportPostDTO.LINK_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostCommentsLinksRow.setBorder(Rectangle.BOTTOM);
//			cellPostCommentsLinksRow.setBorderWidthBottom(0.8f);
//			cellPostCommentsLinksRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostCommentsLinksRow.setPadding(3);
//			cellPostCommentsLinksRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostCommentsLinksRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			PdfPCell cellPostSharesLinksRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getPostsStoriesShareByStatusType().containsKey(FaceReportPostDTO.LINK_POSTED) ? 
//							reportFacebookContent.getPostsStoriesShareByStatusType().get(FaceReportPostDTO.LINK_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostSharesLinksRow.setBorder(Rectangle.BOTTOM);
//			cellPostSharesLinksRow.setBorderWidthBottom(0.8f);
//			cellPostSharesLinksRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostSharesLinksRow.setBackgroundColor(new BaseColor(242, 242, 242));
//			cellPostSharesLinksRow.setPadding(3);
//			cellPostSharesLinksRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostSharesLinksRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			
//			// Post Type Photo Row.
//			PdfPCell cellPostTypePhotosRow = new PdfPCell(new Phrase(new Chunk("Foto", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostTypePhotosRow.setBorder(Rectangle.BOTTOM);
//			cellPostTypePhotosRow.setBorderWidthBottom(0.8f);
//			cellPostTypePhotosRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostTypePhotosRow.setPadding(3);
//			cellPostTypePhotosRow.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//			cellPostTypePhotosRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			PdfPCell cellPostLikesPhotosRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getPostsStoriesLikeByStatusType().containsKey(FaceReportPostDTO.PHOTOS_POSTED) ? 
//							reportFacebookContent.getPostsStoriesLikeByStatusType().get(FaceReportPostDTO.PHOTOS_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostLikesPhotosRow.setBorder(Rectangle.BOTTOM);
//			cellPostLikesPhotosRow.setBorderWidthBottom(0.8f);
//			cellPostLikesPhotosRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostLikesPhotosRow.setBackgroundColor(new BaseColor(242, 242, 242));
//			cellPostLikesPhotosRow.setPadding(3);
//			cellPostLikesPhotosRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostLikesPhotosRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			PdfPCell cellPostCommentsPhotosRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getPostsStoriesCommentByStatusType().containsKey(FaceReportPostDTO.PHOTOS_POSTED) ? 
//							reportFacebookContent.getPostsStoriesCommentByStatusType().get(FaceReportPostDTO.PHOTOS_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostCommentsPhotosRow.setBorder(Rectangle.BOTTOM);
//			cellPostCommentsPhotosRow.setBorderWidthBottom(0.8f);
//			cellPostCommentsPhotosRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostCommentsPhotosRow.setPadding(3);
//			cellPostCommentsPhotosRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostCommentsPhotosRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			PdfPCell cellPostSharesPhotosRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getPostsStoriesShareByStatusType().containsKey(FaceReportPostDTO.PHOTOS_POSTED) ? 
//							reportFacebookContent.getPostsStoriesShareByStatusType().get(FaceReportPostDTO.PHOTOS_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostSharesPhotosRow.setBorder(Rectangle.BOTTOM);
//			cellPostSharesPhotosRow.setBorderWidthBottom(0.8f);
//			cellPostSharesPhotosRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostSharesPhotosRow.setBackgroundColor(new BaseColor(242, 242, 242));
//			cellPostSharesPhotosRow.setPadding(3);
//			cellPostSharesPhotosRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostSharesPhotosRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			
//			// Post Type Video Row.
//			PdfPCell cellPostTypeVideosRow = new PdfPCell(new Phrase(new Chunk("Vídeo", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostTypeVideosRow.setBorder(Rectangle.BOTTOM);
//			cellPostTypeVideosRow.setBorderWidthBottom(0.8f);
//			cellPostTypeVideosRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostTypeVideosRow.setPadding(3);
//			cellPostTypeVideosRow.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//			cellPostTypeVideosRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			PdfPCell cellPostLikesVideosRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getPostsStoriesLikeByStatusType().containsKey(FaceReportPostDTO.VIDEO_POSTED) ? 
//							reportFacebookContent.getPostsStoriesLikeByStatusType().get(FaceReportPostDTO.VIDEO_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostLikesVideosRow.setBorder(Rectangle.BOTTOM);
//			cellPostLikesVideosRow.setBorderWidthBottom(0.8f);
//			cellPostLikesVideosRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostLikesVideosRow.setBackgroundColor(new BaseColor(242, 242, 242));
//			cellPostLikesVideosRow.setPadding(3);
//			cellPostLikesVideosRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostLikesVideosRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			PdfPCell cellPostCommentsVideosRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getPostsStoriesCommentByStatusType().containsKey(FaceReportPostDTO.VIDEO_POSTED) ? 
//							reportFacebookContent.getPostsStoriesCommentByStatusType().get(FaceReportPostDTO.VIDEO_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostCommentsVideosRow.setBorder(Rectangle.BOTTOM);
//			cellPostCommentsVideosRow.setBorderWidthBottom(0.8f);
//			cellPostCommentsVideosRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostCommentsVideosRow.setPadding(3);
//			cellPostCommentsVideosRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostCommentsVideosRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//			PdfPCell cellPostSharesVideosRow = new PdfPCell(new Phrase(new Chunk(
//					reportFacebookContent.getPostsStoriesShareByStatusType().containsKey(FaceReportPostDTO.VIDEO_POSTED) ? 
//							reportFacebookContent.getPostsStoriesShareByStatusType().get(FaceReportPostDTO.VIDEO_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//			cellPostSharesVideosRow.setBorder(Rectangle.BOTTOM);
//			cellPostSharesVideosRow.setBorderWidthBottom(0.8f);
//			cellPostSharesVideosRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//			cellPostSharesVideosRow.setBackgroundColor(new BaseColor(242, 242, 242));
//			cellPostSharesVideosRow.setPadding(3);
//			cellPostSharesVideosRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostSharesVideosRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			
//			tablePostsStoriesByTypePostRows.addCell(cellPostTypeStatusRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostLikesStatusRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostCommentsStatusRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostSharesStatusRow);
//			
//			tablePostsStoriesByTypePostRows.addCell(cellPostTypeLinksRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostLikesLinksRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostCommentsLinksRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostSharesLinksRow);
//			
//			tablePostsStoriesByTypePostRows.addCell(cellPostTypePhotosRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostLikesPhotosRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostCommentsPhotosRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostSharesPhotosRow);
//			
//			tablePostsStoriesByTypePostRows.addCell(cellPostTypeVideosRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostLikesVideosRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostCommentsVideosRow);
//			tablePostsStoriesByTypePostRows.addCell(cellPostSharesVideosRow);
//
//			cellPostsStoriesByTypePostTable.addElement(tablePostsStoriesByTypePostHeader);
//			cellPostsStoriesByTypePostTable.addElement(tablePostsStoriesByTypePostRows);
//
//			tableStoriesOfPostsInfo.addCell(cellTotalOfStoriesTitle);
//			tableStoriesOfPostsInfo.addCell(cellTotalOfStories);
//			tableStoriesOfPostsInfo.addCell(cellTotalOfStoriesDescription);
//			tableStoriesOfPostsInfo.addCell(cellPostsStoriesByTypePostTable);
//			
//			cellStoriesOfPostsInfo.addElement(tableStoriesOfPostsInfo);
//			
//			tableStoriesOfPostsContent.addCell(cellChartStoriesOfPosts);
//			tableStoriesOfPostsContent.addCell(cellStoriesOfPostsInfo);
//
//			cellStoriesOfPosts.addElement(tableStoriesOfPostsContent);
//
//			tableStoriesOfPosts.addCell(cellStoriesOfPostsTitle);
//			tableStoriesOfPosts.addCell(cellStoriesOfPosts);
//
//			document.add(tableStoriesOfPosts);
//
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private void addImpressionsOfPosts() {
//
//		try {
//
//			PdfPTable tableImpressionsOfPosts = new PdfPTable(1);
//			tableImpressionsOfPosts.setTotalWidth(new float[]{523});
//			tableImpressionsOfPosts.setLockedWidth(true);
//			tableImpressionsOfPosts.setHorizontalAlignment(Element.ALIGN_CENTER);
//			tableImpressionsOfPosts.setSpacingAfter(22);
//
//			PdfPCell cellImpressionsOfPostsTitle = new PdfPCell();
//			cellImpressionsOfPostsTitle.setBorder(Rectangle.BOTTOM);
//			cellImpressionsOfPostsTitle.setBorderWidthBottom(0.8f);
//			cellImpressionsOfPostsTitle.setPaddingBottom(5);
//			cellImpressionsOfPostsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
//
//			Paragraph parImpressionsOfPostsTitle = new Paragraph("IMPRESSÕES DURANTE O PERÍODO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
//			parImpressionsOfPostsTitle.setAlignment(Element.ALIGN_CENTER);
//
//			cellImpressionsOfPostsTitle.addElement(parImpressionsOfPostsTitle);
//
//			PdfPCell cellImpressionsOfPosts = new PdfPCell();
//			cellImpressionsOfPosts.setBorder(PdfPCell.NO_BORDER);
//			cellImpressionsOfPosts.setPaddingBottom(0);
//
//			PdfPTable tableImpressionsOfPostsContent = new PdfPTable(2);
//			tableImpressionsOfPostsContent.setTotalWidth(new float[]{200, 165});
//			tableImpressionsOfPostsContent.setLockedWidth(true);
//
//			PdfPCell cellChartImpressionsOfPosts = new PdfPCell();
//			cellChartImpressionsOfPosts.setBorder(PdfPCell.NO_BORDER);
//			cellChartImpressionsOfPosts.setPadding(0);
//
//			cellChartImpressionsOfPosts.addElement(addChartImpressionsOfPosts());
//
//			PdfPCell cellImpressionsOfPostsInfo = new PdfPCell();
//			cellImpressionsOfPostsInfo.setBorder(PdfPCell.NO_BORDER);
//			cellImpressionsOfPostsInfo.setPaddingTop(8);
//			cellImpressionsOfPostsInfo.setPaddingBottom(0);
//			cellImpressionsOfPostsInfo.setPaddingRight(0);
//			cellImpressionsOfPostsInfo.setPaddingLeft(30);
//
//			PdfPTable tableImpressionsOfPostsInfo = new PdfPTable(1);
//			tableImpressionsOfPostsInfo.setTotalWidth(new float[]{165});
//			tableImpressionsOfPostsInfo.setLockedWidth(true);
//
//			// Origin of the Impressions.
//			PdfPCell cellOriginImpressionsTitle = new PdfPCell();
//			cellOriginImpressionsTitle.setBorder(PdfPCell.NO_BORDER);
//			cellOriginImpressionsTitle.setPaddingRight(0);
//			cellOriginImpressionsTitle.setPaddingLeft(0);
//			cellOriginImpressionsTitle.setPaddingTop(0);
//			cellOriginImpressionsTitle.setPaddingBottom(2);
//
//			Paragraph parOriginImpressionsTitle = new Paragraph("ORIGEM DAS IMPRESSÕES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parOriginImpressionsTitle.setLeading(9);
//
//			cellOriginImpressionsTitle.addElement(parOriginImpressionsTitle);
//
//			PdfPCell cellChartOriginImpressions = new PdfPCell();
//			cellChartOriginImpressions.setBorder(PdfPCell.NO_BORDER);
//			cellChartOriginImpressions.setPaddingLeft(0);
//			cellChartOriginImpressions.setPaddingRight(0);
//			cellChartOriginImpressions.setPaddingTop(0);
//			cellChartOriginImpressions.setPaddingBottom(4);
//
//			cellChartOriginImpressions.addElement(addChartOriginImpressions());
//
//			// Total impressions of posts.
//			PdfPCell cellTotalImpressionsOfPostsTitle = new PdfPCell();
//			cellTotalImpressionsOfPostsTitle.setBorder(PdfPCell.NO_BORDER);
//			cellTotalImpressionsOfPostsTitle.setPaddingRight(0);
//			cellTotalImpressionsOfPostsTitle.setPaddingLeft(0);
//			cellTotalImpressionsOfPostsTitle.setPaddingTop(0);
//			cellTotalImpressionsOfPostsTitle.setPaddingBottom(2);
//
//			Paragraph parTotalImpressionsOfPostsTitle = new Paragraph("IMPRESSÕES TOTAL", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parTotalImpressionsOfPostsTitle.setLeading(9);
//
//			cellTotalImpressionsOfPostsTitle.addElement(parTotalImpressionsOfPostsTitle);
//
//			PdfPCell cellTotalImpressionsOfPosts = new PdfPCell();
//			cellTotalImpressionsOfPosts.setBorder(PdfPCell.NO_BORDER);
//			cellTotalImpressionsOfPosts.setPaddingRight(0);
//			cellTotalImpressionsOfPosts.setPaddingLeft(0);
//			cellTotalImpressionsOfPosts.setPaddingTop(0);
//			cellTotalImpressionsOfPosts.setPaddingBottom(8);
//
//			Paragraph parTotalImpressionsOfPosts = new Paragraph();
//			parTotalImpressionsOfPosts.setLeading(11);
//
//			Image imgTotalImpressionsOfPosts = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_TOTAL_POSTS_IMPRESSIONS));
//			imgTotalImpressionsOfPosts.scaleAbsolute(10, 10);
//
//			parTotalImpressionsOfPosts.add(new Chunk(imgTotalImpressionsOfPosts, 0, 0));
//
//			parTotalImpressionsOfPosts.add(new Chunk(" " + reportFacebookContent.getPostsImpressionsCount().toString(),
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(121, 201, 224))));
//
//			cellTotalImpressionsOfPosts.addElement(parTotalImpressionsOfPosts);
//
//			// Average impressions of posts per day.
//			PdfPCell cellAverageImpressionsOfPostsPerDayTitle = new PdfPCell();
//			cellAverageImpressionsOfPostsPerDayTitle.setBorder(PdfPCell.NO_BORDER);
//			cellAverageImpressionsOfPostsPerDayTitle.setPaddingRight(0);
//			cellAverageImpressionsOfPostsPerDayTitle.setPaddingLeft(0);
//			cellAverageImpressionsOfPostsPerDayTitle.setPaddingTop(0);
//			cellAverageImpressionsOfPostsPerDayTitle.setPaddingBottom(2);
//
//			Paragraph parAverageImpressionsOfPostsPerDayTitle = new Paragraph("MÉDIA DIÁRIA DE IMPRESSÕES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parAverageImpressionsOfPostsPerDayTitle.setLeading(9);
//
//			cellAverageImpressionsOfPostsPerDayTitle.addElement(parAverageImpressionsOfPostsPerDayTitle);
//
//			PdfPCell cellAverageImpressionsOfPostsPerDay = new PdfPCell();
//			cellAverageImpressionsOfPostsPerDay.setBorder(PdfPCell.NO_BORDER);
//			cellAverageImpressionsOfPostsPerDay.setPaddingRight(0);
//			cellAverageImpressionsOfPostsPerDay.setPaddingLeft(0);
//			cellAverageImpressionsOfPostsPerDay.setPaddingTop(0);
//			cellAverageImpressionsOfPostsPerDay.setPaddingBottom(8);
//
//			Paragraph parAverageImpressionsOfPostsPerDay = new Paragraph();
//			parAverageImpressionsOfPostsPerDay.setLeading(11);
//
//			Image imgAverageImpressionsOfPostsPerDay = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_AVERAGE_POSTS_IMPRESSIONS));
//			imgAverageImpressionsOfPostsPerDay.scaleAbsolute(10, 10);
//
//			parAverageImpressionsOfPostsPerDay.add(new Chunk(imgAverageImpressionsOfPostsPerDay, 0, 0));
//
//			parAverageImpressionsOfPostsPerDay.add(new Chunk(" " + reportFacebookContent.getAverageImpressionsOfPostsPerDay().toString(),
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(219, 62, 0))));
//
//			cellAverageImpressionsOfPostsPerDay.addElement(parAverageImpressionsOfPostsPerDay);
//
//			tableImpressionsOfPostsInfo.addCell(cellOriginImpressionsTitle);
//			tableImpressionsOfPostsInfo.addCell(cellChartOriginImpressions);
//			tableImpressionsOfPostsInfo.addCell(cellTotalImpressionsOfPostsTitle);
//			tableImpressionsOfPostsInfo.addCell(cellTotalImpressionsOfPosts);
//			tableImpressionsOfPostsInfo.addCell(cellAverageImpressionsOfPostsPerDayTitle);
//			tableImpressionsOfPostsInfo.addCell(cellAverageImpressionsOfPostsPerDay);
//
//			cellImpressionsOfPostsInfo.addElement(tableImpressionsOfPostsInfo);
//
//			tableImpressionsOfPostsContent.addCell(cellChartImpressionsOfPosts);
//			tableImpressionsOfPostsContent.addCell(cellImpressionsOfPostsInfo);
//
//			cellImpressionsOfPosts.addElement(tableImpressionsOfPostsContent);
//
//			tableImpressionsOfPosts.addCell(cellImpressionsOfPostsTitle);
//			tableImpressionsOfPosts.addCell(cellImpressionsOfPosts);
//
//			document.add(tableImpressionsOfPosts);
//
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private void addReachOfPosts() {
//
//		try {
//
//			PdfPTable tableReachOfPosts = new PdfPTable(1);
//			tableReachOfPosts.setTotalWidth(new float[]{523});
//			tableReachOfPosts.setLockedWidth(true);
//			tableReachOfPosts.setHorizontalAlignment(Element.ALIGN_CENTER);
//			tableReachOfPosts.setSpacingAfter(22);
//
//			PdfPCell cellReachOfPostsTitle = new PdfPCell();
//			cellReachOfPostsTitle.setBorder(Rectangle.BOTTOM);
//			cellReachOfPostsTitle.setBorderWidthBottom(0.8f);
//			cellReachOfPostsTitle.setPaddingBottom(5);
//			cellReachOfPostsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
//
//			Paragraph parReachOfPostsTitle = new Paragraph("ALCANCE DURANTE O PERÍODO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
//			parReachOfPostsTitle.setAlignment(Element.ALIGN_CENTER);
//
//			cellReachOfPostsTitle.addElement(parReachOfPostsTitle);
//
//			PdfPCell cellReachOfPosts = new PdfPCell();
//			cellReachOfPosts.setBorder(PdfPCell.NO_BORDER);
//			cellReachOfPosts.setPaddingBottom(0);
//
//			PdfPTable tableReachOfPostsContent = new PdfPTable(2);
//			tableReachOfPostsContent.setTotalWidth(new float[]{200, 165});
//			tableReachOfPostsContent.setLockedWidth(true);
//
//			PdfPCell cellChartReachOfPosts = new PdfPCell();
//			cellChartReachOfPosts.setBorder(PdfPCell.NO_BORDER);
//			cellChartReachOfPosts.setPadding(0);
//
//			cellChartReachOfPosts.addElement(addChartReachOfPosts());
//
//			PdfPCell cellReachOfPostsInfo = new PdfPCell();
//			cellReachOfPostsInfo.setBorder(PdfPCell.NO_BORDER);
//			cellReachOfPostsInfo.setPaddingTop(8);
//			cellReachOfPostsInfo.setPaddingBottom(0);
//			cellReachOfPostsInfo.setPaddingRight(0);
//			cellReachOfPostsInfo.setPaddingLeft(30);
//
//			PdfPTable tableReachOfPostsInfo = new PdfPTable(1);
//			tableReachOfPostsInfo.setTotalWidth(new float[]{165});
//			tableReachOfPostsInfo.setLockedWidth(true);
//
//			// Origin of the Reach.
//			PdfPCell cellOriginReachTitle = new PdfPCell();
//			cellOriginReachTitle.setBorder(PdfPCell.NO_BORDER);
//			cellOriginReachTitle.setPaddingRight(0);
//			cellOriginReachTitle.setPaddingLeft(0);
//			cellOriginReachTitle.setPaddingTop(0);
//			cellOriginReachTitle.setPaddingBottom(2);
//
//			Paragraph parOriginReachTitle = new Paragraph("ORIGEM DO ALCANCE", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parOriginReachTitle.setLeading(9);
//
//			cellOriginReachTitle.addElement(parOriginReachTitle);
//
//			PdfPCell cellChartOriginReach = new PdfPCell();
//			cellChartOriginReach.setBorder(PdfPCell.NO_BORDER);
//			cellChartOriginReach.setPaddingLeft(0);
//			cellChartOriginReach.setPaddingRight(0);
//			cellChartOriginReach.setPaddingTop(0);
//			cellChartOriginReach.setPaddingBottom(4);
//
//			cellChartOriginReach.addElement(addChartOriginReach());
//
//			// Total reach of posts.
//			PdfPCell cellTotalReachOfPostsTitle = new PdfPCell();
//			cellTotalReachOfPostsTitle.setBorder(PdfPCell.NO_BORDER);
//			cellTotalReachOfPostsTitle.setPaddingRight(0);
//			cellTotalReachOfPostsTitle.setPaddingLeft(0);
//			cellTotalReachOfPostsTitle.setPaddingTop(0);
//			cellTotalReachOfPostsTitle.setPaddingBottom(2);
//
//			Paragraph parTotalReachOfPostsTitle = new Paragraph("ALCANCE TOTAL", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parTotalReachOfPostsTitle.setLeading(9);
//
//			cellTotalReachOfPostsTitle.addElement(parTotalReachOfPostsTitle);
//
//			PdfPCell cellTotalReachOfPosts = new PdfPCell();
//			cellTotalReachOfPosts.setBorder(PdfPCell.NO_BORDER);
//			cellTotalReachOfPosts.setPaddingRight(0);
//			cellTotalReachOfPosts.setPaddingLeft(0);
//			cellTotalReachOfPosts.setPaddingTop(0);
//			cellTotalReachOfPosts.setPaddingBottom(8);
//
//			Paragraph parTotalReachOfPosts = new Paragraph();
//			parTotalReachOfPosts.setLeading(11);
//
//			Image imgTotalReachOfPosts = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_TOTAL_POSTS_REACH));
//			imgTotalReachOfPosts.scaleAbsolute(10, 10);
//
//			parTotalReachOfPosts.add(new Chunk(imgTotalReachOfPosts, 0, 0));
//
//			parTotalReachOfPosts.add(new Chunk(" " + reportFacebookContent.getPostsReachCount().toString(),
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(174, 217 ,0))));
//
//			cellTotalReachOfPosts.addElement(parTotalReachOfPosts);
//
//			// Average reach of posts per day.
//			PdfPCell cellAverageReachOfPostsPerDayTitle = new PdfPCell();
//			cellAverageReachOfPostsPerDayTitle.setBorder(PdfPCell.NO_BORDER);
//			cellAverageReachOfPostsPerDayTitle.setPaddingRight(0);
//			cellAverageReachOfPostsPerDayTitle.setPaddingLeft(0);
//			cellAverageReachOfPostsPerDayTitle.setPaddingTop(0);
//			cellAverageReachOfPostsPerDayTitle.setPaddingBottom(2);
//
//			Paragraph parAverageReachOfPostsPerDayTitle = new Paragraph("MÉDIA DIÁRIA DE ALCANCE", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parAverageReachOfPostsPerDayTitle.setLeading(9);
//
//			cellAverageReachOfPostsPerDayTitle.addElement(parAverageReachOfPostsPerDayTitle);
//
//			PdfPCell cellAverageReachOfPostsPerDay = new PdfPCell();
//			cellAverageReachOfPostsPerDay.setBorder(PdfPCell.NO_BORDER);
//			cellAverageReachOfPostsPerDay.setPaddingRight(0);
//			cellAverageReachOfPostsPerDay.setPaddingLeft(0);
//			cellAverageReachOfPostsPerDay.setPaddingTop(0);
//			cellAverageReachOfPostsPerDay.setPaddingBottom(8);
//
//			Paragraph parAverageReachOfPostsPerDay = new Paragraph();
//			parAverageReachOfPostsPerDay.setLeading(11);
//
//			Image imgAverageReachOfPostsPerDay = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_AVERAGE_POSTS_REACH));
//			imgAverageReachOfPostsPerDay.scaleAbsolute(10, 10);
//
//			parAverageReachOfPostsPerDay.add(new Chunk(imgAverageReachOfPostsPerDay, 0, 0));
//
//			parAverageReachOfPostsPerDay.add(new Chunk(" " + reportFacebookContent.getAverageReachOfPostsPerDay().toString(),
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(253, 140, 6))));
//
//			cellAverageReachOfPostsPerDay.addElement(parAverageReachOfPostsPerDay);
//
//			tableReachOfPostsInfo.addCell(cellOriginReachTitle);
//			tableReachOfPostsInfo.addCell(cellChartOriginReach);
//			tableReachOfPostsInfo.addCell(cellTotalReachOfPostsTitle);
//			tableReachOfPostsInfo.addCell(cellTotalReachOfPosts);
//			tableReachOfPostsInfo.addCell(cellAverageReachOfPostsPerDayTitle);
//			tableReachOfPostsInfo.addCell(cellAverageReachOfPostsPerDay);
//
//			cellReachOfPostsInfo.addElement(tableReachOfPostsInfo);
//
//			tableReachOfPostsContent.addCell(cellChartReachOfPosts);
//			tableReachOfPostsContent.addCell(cellReachOfPostsInfo);
//
//			cellReachOfPosts.addElement(tableReachOfPostsContent);
//
//			tableReachOfPosts.addCell(cellReachOfPostsTitle);
//			tableReachOfPosts.addCell(cellReachOfPosts);
//
//			document.add(tableReachOfPosts);
//
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private void addGeneralPostsStats() {
//
//		try {
//
//			PdfPTable tableGeneralPostsStats = new PdfPTable(1);
//			tableGeneralPostsStats.setTotalWidth(new float[]{523});
//			tableGeneralPostsStats.setLockedWidth(true);
//			tableGeneralPostsStats.setHorizontalAlignment(Element.ALIGN_CENTER);
//			tableGeneralPostsStats.setSpacingAfter(22);
//
//			PdfPCell cellGeneralPostsStatsTitle = new PdfPCell();
//			cellGeneralPostsStatsTitle.setBorder(Rectangle.BOTTOM);
//			cellGeneralPostsStatsTitle.setBorderWidthBottom(0.8f);
//			cellGeneralPostsStatsTitle.setPaddingBottom(5);
//			cellGeneralPostsStatsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
//
//			Paragraph parGeneralPostsStatsTitle = new Paragraph("POSTS DURANTE O PERÍODO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
//			parGeneralPostsStatsTitle.setAlignment(Element.ALIGN_CENTER);
//
//			cellGeneralPostsStatsTitle.addElement(parGeneralPostsStatsTitle);
//
//			PdfPCell cellGeneralPostsStats = new PdfPCell();
//			cellGeneralPostsStats.setBorder(PdfPCell.NO_BORDER);
//			cellGeneralPostsStats.setPaddingBottom(0);
//
//			PdfPTable tableGeneralPostsStatsContent = new PdfPTable(2);
//			tableGeneralPostsStatsContent.setTotalWidth(new float[]{200, 165});
//			tableGeneralPostsStatsContent.setLockedWidth(true);
//
//			PdfPCell cellChartNumberOfPosts = new PdfPCell();
//			cellChartNumberOfPosts.setBorder(PdfPCell.NO_BORDER);
//			cellChartNumberOfPosts.setPadding(0);
//
//			cellChartNumberOfPosts.addElement(addChartNumberOfPosts());
//
//			PdfPCell cellGeneralPostsStatsInfo = new PdfPCell();
//			cellGeneralPostsStatsInfo.setBorder(PdfPCell.NO_BORDER);
//			cellGeneralPostsStatsInfo.setPaddingTop(8);
//			cellGeneralPostsStatsInfo.setPaddingBottom(0);
//			cellGeneralPostsStatsInfo.setPaddingRight(0);
//			cellGeneralPostsStatsInfo.setPaddingLeft(30);
//
//			PdfPTable tableGeneralPostsStatsInfo = new PdfPTable(1);
//			tableGeneralPostsStatsInfo.setTotalWidth(new float[]{165});
//			tableGeneralPostsStatsInfo.setLockedWidth(true);
//
//			// Average number of posts per day.
//			PdfPCell cellAveragePostsPerDayTitle = new PdfPCell();
//			cellAveragePostsPerDayTitle.setBorder(PdfPCell.NO_BORDER);
//			cellAveragePostsPerDayTitle.setPaddingRight(0);
//			cellAveragePostsPerDayTitle.setPaddingLeft(0);
//			cellAveragePostsPerDayTitle.setPaddingTop(0);
//			cellAveragePostsPerDayTitle.setPaddingBottom(2);
//
//			Paragraph parAveragePostsPerDayTitle = new Paragraph("MÉDIA DE POSTS POR DIA", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parAveragePostsPerDayTitle.setLeading(9);
//
//			cellAveragePostsPerDayTitle.addElement(parAveragePostsPerDayTitle);
//
//			PdfPCell cellAveragePostsPerDay = new PdfPCell();
//			cellAveragePostsPerDay.setBorder(PdfPCell.NO_BORDER);
//			cellAveragePostsPerDay.setPaddingRight(0);
//			cellAveragePostsPerDay.setPaddingLeft(0);
//			cellAveragePostsPerDay.setPaddingTop(0);
//			cellAveragePostsPerDay.setPaddingBottom(8);
//
//			Paragraph parAveragePostsPerDay = new Paragraph();
//			parAveragePostsPerDay.setLeading(11);
//
//			Image imgAveragePostsPerDay = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_POSTS_DAY));
//			imgAveragePostsPerDay.scaleAbsolute(10, 10);
//
//			parAveragePostsPerDay.add(new Chunk(imgAveragePostsPerDay, 0, 0));
//
//			String averagePostsPerDayText = reportFacebookContent.getAveragePostsPerDay() != 1 ? " posts" : " post";
//
//			parAveragePostsPerDay.add(new Chunk(" " + reportFacebookContent.getAveragePostsPerDay() + averagePostsPerDayText,
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(74, 200, 232))));
//
//			cellAveragePostsPerDay.addElement(parAveragePostsPerDay);
//
//			// Average reach of posts.
//			PdfPCell cellAveragePostsReachTitle = new PdfPCell();
//			cellAveragePostsReachTitle.setBorder(PdfPCell.NO_BORDER);
//			cellAveragePostsReachTitle.setPaddingRight(0);
//			cellAveragePostsReachTitle.setPaddingLeft(0);
//			cellAveragePostsReachTitle.setPaddingTop(0);
//			cellAveragePostsReachTitle.setPaddingBottom(2);
//
//			Paragraph parAveragePostsReachTitle = new Paragraph("ALCANCE MÉDIO DAS PUBLICAÇÕES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parAveragePostsReachTitle.setLeading(9);
//
//			cellAveragePostsReachTitle.addElement(parAveragePostsReachTitle);
//
//			PdfPCell cellAveragePostsReach = new PdfPCell();
//			cellAveragePostsReach.setBorder(PdfPCell.NO_BORDER);
//			cellAveragePostsReach.setPaddingRight(0);
//			cellAveragePostsReach.setPaddingLeft(0);
//			cellAveragePostsReach.setPaddingTop(0);
//			cellAveragePostsReach.setPaddingBottom(2);
//
//			Paragraph parAveragePostsReach = new Paragraph();
//			parAveragePostsReach.setLeading(11);
//
//			Image imgAveragePostsReach = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_POSTS_REACH));
//			imgAveragePostsReach.scaleAbsolute(10, 10);
//
//			parAveragePostsReach.add(new Chunk(imgAveragePostsReach, 0, 0));
//
//			parAveragePostsReach.add(new Chunk(" " + reportFacebookContent.getAveragePostsReach().toString(),
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(174, 217, 0))));
//
//			cellAveragePostsReach.addElement(parAveragePostsReach);
//
//			PdfPCell cellAveragePostsReachDescription = new PdfPCell();
//			cellAveragePostsReachDescription.setBorder(PdfPCell.NO_BORDER);
//			cellAveragePostsReachDescription.setPaddingRight(0);
//			cellAveragePostsReachDescription.setPaddingLeft(0);
//			cellAveragePostsReachDescription.setPaddingTop(0);
//			cellAveragePostsReachDescription.setPaddingBottom(8);
//
//			Paragraph parAveragePostsReachDescription = new Paragraph();
//			parAveragePostsReachDescription.setLeading(7);
//
//			String averagePostsReachDesc = "Dia " + new SimpleDateFormat("dd/MM").format( 
//					new Date(reportFacebookContent.getMajorPostsReachDay().getYear() + 1900, 
//							reportFacebookContent.getMajorPostsReachDay().getMonth() - 1, reportFacebookContent.getMajorPostsReachDay().getDay())) + " registrou o maior índice de alcance, " +
//							"com " + reportFacebookContent.getMajorPostsReachDay().getSum() + " pessoas alcançadas através da sua página.";
//
//			parAveragePostsReachDescription.add(averagePostsReachDesc);
//			parAveragePostsReachDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
//
//			cellAveragePostsReachDescription.addElement(parAveragePostsReachDescription);
//
//			// Average posts interactions.
//			PdfPCell cellAveragePostsInteractionsTitle = new PdfPCell();
//			cellAveragePostsInteractionsTitle.setBorder(PdfPCell.NO_BORDER);
//			cellAveragePostsInteractionsTitle.setPaddingRight(0);
//			cellAveragePostsInteractionsTitle.setPaddingLeft(0);
//			cellAveragePostsInteractionsTitle.setPaddingTop(0);
//			cellAveragePostsInteractionsTitle.setPaddingBottom(2);
//
//			Paragraph parAveragePostsInteractionsTitle = new Paragraph("MÉDIA DE INTERAÇÕES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parAveragePostsInteractionsTitle.setLeading(9);
//
//			cellAveragePostsInteractionsTitle.addElement(parAveragePostsInteractionsTitle);
//
//			PdfPCell cellAveragePostsInteractions = new PdfPCell();
//			cellAveragePostsInteractions.setBorder(PdfPCell.NO_BORDER);
//			cellAveragePostsInteractions.setPaddingRight(0);
//			cellAveragePostsInteractions.setPaddingLeft(0);
//			cellAveragePostsInteractions.setPaddingTop(0);
//			cellAveragePostsInteractions.setPaddingBottom(2);
//
//			Paragraph parAveragePostsInteractions = new Paragraph();
//			parAveragePostsInteractions.setLeading(11);
//
//			Image imgAveragePostsInteractions = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_POSTS_INTERACTIONS));
//			imgAveragePostsInteractions.scaleAbsolute(10, 10);
//
//			parAveragePostsInteractions.add(new Chunk(imgAveragePostsInteractions, 0, 0));
//
//			parAveragePostsInteractions.add(new Chunk(" " + reportFacebookContent.getAveragePostsInteractions().toString(),
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(255, 170, 21))));
//
//			cellAveragePostsInteractions.addElement(parAveragePostsInteractions);
//
//			PdfPCell cellAveragePostsInteractionsDesc = new PdfPCell();
//			cellAveragePostsInteractionsDesc.setBorder(PdfPCell.NO_BORDER);
//			cellAveragePostsInteractionsDesc.setPaddingRight(0);
//			cellAveragePostsInteractionsDesc.setPaddingLeft(0);
//			cellAveragePostsInteractionsDesc.setPaddingTop(0);
//
//			Paragraph parAveragePostsInteractionsDesc = new Paragraph();
//			parAveragePostsInteractionsDesc.setLeading(7);
//
//			String averagePostsInteractionsDesc = "Dia " + new SimpleDateFormat("dd/MM").format( 
//					new Date(reportFacebookContent.getMajorPostsInteractionsDay().getYear() + 1900, 
//							reportFacebookContent.getMajorPostsInteractionsDay().getMonth() - 1, reportFacebookContent.getMajorPostsInteractionsDay().getDay())) + " registrou o maior número de interações, " +
//							"com " + reportFacebookContent.getMajorPostsInteractionsDay().getSum() + " através de curtir, comentar e compartilhar.";
//
//			parAveragePostsInteractionsDesc.add(averagePostsInteractionsDesc);
//			parAveragePostsInteractionsDesc.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
//
//			cellAveragePostsInteractionsDesc.addElement(parAveragePostsInteractionsDesc);
//
//			tableGeneralPostsStatsInfo.addCell(cellAveragePostsPerDayTitle);
//			tableGeneralPostsStatsInfo.addCell(cellAveragePostsPerDay);
//			tableGeneralPostsStatsInfo.addCell(cellAveragePostsReachTitle);
//			tableGeneralPostsStatsInfo.addCell(cellAveragePostsReach);
//			tableGeneralPostsStatsInfo.addCell(cellAveragePostsReachDescription);
//			tableGeneralPostsStatsInfo.addCell(cellAveragePostsInteractionsTitle);
//			tableGeneralPostsStatsInfo.addCell(cellAveragePostsInteractions);
//			tableGeneralPostsStatsInfo.addCell(cellAveragePostsInteractionsDesc);
//
//			cellGeneralPostsStatsInfo.addElement(tableGeneralPostsStatsInfo);
//
//			tableGeneralPostsStatsContent.addCell(cellChartNumberOfPosts);
//			tableGeneralPostsStatsContent.addCell(cellGeneralPostsStatsInfo);
//
//			cellGeneralPostsStats.addElement(tableGeneralPostsStatsContent);
//
//			tableGeneralPostsStats.addCell(cellGeneralPostsStatsTitle);
//			tableGeneralPostsStats.addCell(cellGeneralPostsStats);
//
//			document.add(tableGeneralPostsStats);
//
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private void addBasicInformation() {
//
//		try {
//
//			PdfPTable tableProfile = new PdfPTable(1);
//			tableProfile.setTotalWidth(new float[]{523f});
//			tableProfile.setLockedWidth(true);
//
//			PdfPCell cellProfile = new PdfPCell();
//			cellProfile.setHorizontalAlignment(Element.ALIGN_LEFT);
//			cellProfile.setVerticalAlignment(Element.ALIGN_MIDDLE);
//			cellProfile.setBorder(Rectangle.NO_BORDER);
//			cellProfile.setFixedHeight(25f);
//
//			Paragraph parProfile = new Paragraph();
//			parProfile.setLeading(11f);
//
//			parProfile.add(new Chunk(profile.getScreenName(), new Font(sourceSansPro, 13, Font.NORMAL, BASE_COLOR_HEADER_TITLE)));
//			parProfile.add(new Chunk("  "));
//
//			Phrase phrReportsDate = new Phrase();
//
//			String reportsDescription = "•  Relatório Facebook (Conteúdo)  -  " + 
//					new SimpleDateFormat("dd/MM/yy").format(new Date(reportFacebookContent.getDateFrom() * 1000L))
//					+" a " +
//					new SimpleDateFormat("dd/MM/yy").format(new Date(reportFacebookContent.getDateUntil() * 1000L));
//
//			Chunk cnkReportsDate = new Chunk(reportsDescription, new Font(sourceSansPro, 9, Font.NORMAL, BASE_COLOR_HEADER_TITLE));
//
//			phrReportsDate.add(cnkReportsDate);
//
//			parProfile.add(phrReportsDate);
//
//			cellProfile.addElement(parProfile);
//
//			tableProfile.addCell(cellProfile);
//
//			document.add(tableProfile);
//			document.add(new Paragraph(" "));
//
//		} catch (BadElementException e) {
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//	private Image addChartFansOnlinePerWeekDay() {
//
//		String img = chartsImages.get("chartReachOfPostsPerWeekDay").substring(chartsImages.get("chartReachOfPostsPerWeekDay").indexOf("base64,") + 7);
//
//		com.itextpdf.text.Image imageChart = null;
//
//		try {
//
//			byte[] array = Base64.decodeBase64(img.getBytes("UTF-8"));
//			String imageName = Persistent.REPORTS + RandomStringUtils.randomAlphanumeric(8) + ".jpg";
//			File file = new File(imageName);
//			file.createNewFile();
//			FileOutputStream fos = new FileOutputStream(file);  
//			fos.write(array);  
//			FileDescriptor fd = fos.getFD();  
//			fos.flush();  
//			fd.sync();   
//			fos.close();
//
//			imageChart = com.itextpdf.text.Image.getInstance(imageName);
//			imageChart.scaleAbsoluteHeight(127);
//			imageChart.scaleAbsoluteWidth(210);
//
//			imagesTemp.add(file);
//
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (SyncFailedException e) {
//			e.printStackTrace();
//		} catch (BadElementException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return imageChart;
//	}
//
//	private Image addChartFansOnlinePerHour() {
//		
//		String img = chartsImages.get("chartFansOnlinePerHour").substring(chartsImages.get("chartFansOnlinePerHour").indexOf("base64,") + 7);
//
//		com.itextpdf.text.Image imageChart = null;
//
//		try {
//
//			byte[] array = Base64.decodeBase64(img.getBytes("UTF-8"));
//			String imageName = Persistent.REPORTS + RandomStringUtils.randomAlphanumeric(8) + ".jpg";
//			File file = new File(imageName);
//			file.createNewFile();
//			FileOutputStream fos = new FileOutputStream(file);  
//			fos.write(array);  
//			FileDescriptor fd = fos.getFD();  
//			fos.flush();  
//			fd.sync();   
//			fos.close();
//
//			imageChart = com.itextpdf.text.Image.getInstance(imageName);
//			imageChart.scaleAbsoluteWidth(360);
//			imageChart.scaleAbsoluteHeight(151);
//			imageChart.setAlignment(Element.ALIGN_CENTER);
//
//			imagesTemp.add(file);
//
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (SyncFailedException e) {
//			e.printStackTrace();
//		} catch (BadElementException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return imageChart;
//	}
//	
//	private Image addChartOriginImpressions() {
//
//		String img = chartsImages.get("chartOriginImpressions").substring(chartsImages.get("chartOriginImpressions").indexOf("base64,") + 7);
//
//		com.itextpdf.text.Image imageChart = null;
//
//		try {
//
//			byte[] array = Base64.decodeBase64(img.getBytes("UTF-8"));
//			String imageName = Persistent.REPORTS + RandomStringUtils.randomAlphanumeric(8) + ".jpg";
//			File file = new File(imageName);
//			file.createNewFile();
//			FileOutputStream fos = new FileOutputStream(file);  
//			fos.write(array);  
//			FileDescriptor fd = fos.getFD();  
//			fos.flush();  
//			fd.sync();   
//			fos.close();
//
//			imageChart = com.itextpdf.text.Image.getInstance(imageName);
//			imageChart.scaleAbsoluteHeight(61);
//			imageChart.scaleAbsoluteWidth(106);
//
//			imagesTemp.add(file);
//
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (SyncFailedException e) {
//			e.printStackTrace();
//		} catch (BadElementException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return imageChart;
//	}
//
//	private Image addChartOriginReach() {
//
//		String img = chartsImages.get("chartOriginReach").substring(chartsImages.get("chartOriginReach").indexOf("base64,") + 7);
//
//		com.itextpdf.text.Image imageChart = null;
//
//		try {
//
//			byte[] array = Base64.decodeBase64(img.getBytes("UTF-8"));
//			String imageName = Persistent.REPORTS + RandomStringUtils.randomAlphanumeric(8) + ".jpg";
//			File file = new File(imageName);
//			file.createNewFile();
//			FileOutputStream fos = new FileOutputStream(file);  
//			fos.write(array);  
//			FileDescriptor fd = fos.getFD();  
//			fos.flush();  
//			fd.sync();   
//			fos.close();
//
//			imageChart = com.itextpdf.text.Image.getInstance(imageName);
//			imageChart.scaleAbsoluteHeight(61);
//			imageChart.scaleAbsoluteWidth(106);
//
//			imagesTemp.add(file);
//
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (SyncFailedException e) {
//			e.printStackTrace();
//		} catch (BadElementException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return imageChart;
//	}
//
//	private Image addChartImpressionsOfPosts() {
//
//		String img = chartsImages.get("chartImpressionsOfPosts").substring(chartsImages.get("chartImpressionsOfPosts").indexOf("base64,") + 7);
//
//		com.itextpdf.text.Image imageChart = null;
//
//		try {
//
//			byte[] array = Base64.decodeBase64(img.getBytes("UTF-8"));
//			String imageName = Persistent.REPORTS + RandomStringUtils.randomAlphanumeric(8) + ".jpg";
//			File file = new File(imageName);
//			file.createNewFile();
//			FileOutputStream fos = new FileOutputStream(file);  
//			fos.write(array);  
//			FileDescriptor fd = fos.getFD();  
//			fos.flush();  
//			fd.sync();   
//			fos.close();
//
//			imageChart = com.itextpdf.text.Image.getInstance(imageName);
//			imageChart.scaleAbsoluteHeight(143);
//			imageChart.scaleAbsoluteWidth(200);
//
//			imagesTemp.add(file);
//
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (SyncFailedException e) {
//			e.printStackTrace();
//		} catch (BadElementException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return imageChart;
//	}
//
//	private Image addChartReachOfPosts() {
//
//		String img = chartsImages.get("chartReachOfPosts").substring(chartsImages.get("chartReachOfPosts").indexOf("base64,") + 7);
//
//		com.itextpdf.text.Image imageChart = null;
//
//		try {
//
//			byte[] array = Base64.decodeBase64(img.getBytes("UTF-8"));
//			String imageName = Persistent.REPORTS + RandomStringUtils.randomAlphanumeric(8) + ".jpg";
//			File file = new File(imageName);
//			file.createNewFile();
//			FileOutputStream fos = new FileOutputStream(file);  
//			fos.write(array);  
//			FileDescriptor fd = fos.getFD();  
//			fos.flush();  
//			fd.sync();   
//			fos.close();
//
//			imageChart = com.itextpdf.text.Image.getInstance(imageName);
//			imageChart.scaleAbsoluteHeight(143);
//			imageChart.scaleAbsoluteWidth(200);
//
//			imagesTemp.add(file);
//
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (SyncFailedException e) {
//			e.printStackTrace();
//		} catch (BadElementException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return imageChart;
//	}
//	
//	private Image addChartConsumptionOfPosts() {
//
//		String img = chartsImages.get("chartConsumptionsOfPosts").substring(chartsImages.get("chartConsumptionsOfPosts").indexOf("base64,") + 7);
//
//		com.itextpdf.text.Image imageChart = null;
//
//		try {
//
//			byte[] array = Base64.decodeBase64(img.getBytes("UTF-8"));
//			String imageName = Persistent.REPORTS + RandomStringUtils.randomAlphanumeric(8) + ".jpg";
//			File file = new File(imageName);
//			file.createNewFile();
//			FileOutputStream fos = new FileOutputStream(file);  
//			fos.write(array);  
//			FileDescriptor fd = fos.getFD();  
//			fos.flush();  
//			fd.sync();   
//			fos.close();
//
//			imageChart = com.itextpdf.text.Image.getInstance(imageName);
//			imageChart.scaleAbsoluteHeight(143);
//			imageChart.scaleAbsoluteWidth(200);
//
//			imagesTemp.add(file);
//
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (SyncFailedException e) {
//			e.printStackTrace();
//		} catch (BadElementException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return imageChart;
//	}
//
//	private Image addChartStoriesOfPosts() {
//
//		String img = chartsImages.get("chartStoriesOfPosts").substring(chartsImages.get("chartStoriesOfPosts").indexOf("base64,") + 7);
//
//		com.itextpdf.text.Image imageChart = null;
//
//		try {
//
//			byte[] array = Base64.decodeBase64(img.getBytes("UTF-8"));
//			String imageName = Persistent.REPORTS + RandomStringUtils.randomAlphanumeric(8) + ".jpg";
//			File file = new File(imageName);
//			file.createNewFile();
//			FileOutputStream fos = new FileOutputStream(file);  
//			fos.write(array);  
//			FileDescriptor fd = fos.getFD();  
//			fos.flush();  
//			fd.sync();   
//			fos.close();
//
//			imageChart = com.itextpdf.text.Image.getInstance(imageName);
//			imageChart.scaleAbsoluteHeight(143);
//			imageChart.scaleAbsoluteWidth(200);
//
//			imagesTemp.add(file);
//
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (SyncFailedException e) {
//			e.printStackTrace();
//		} catch (BadElementException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return imageChart;
//	}
//
//	private Image addChartNumberOfPosts() {
//
//		String img = chartsImages.get("chartNumberOfPosts").substring(chartsImages.get("chartNumberOfPosts").indexOf("base64,") + 7);
//
//		com.itextpdf.text.Image imageChart = null;
//
//		try {
//
//			byte[] array = Base64.decodeBase64(img.getBytes("UTF-8"));
//			String imageName = Persistent.REPORTS + RandomStringUtils.randomAlphanumeric(8) + ".jpg";
//			File file = new File(imageName);
//			file.createNewFile();
//			FileOutputStream fos = new FileOutputStream(file);  
//			fos.write(array);  
//			FileDescriptor fd = fos.getFD();  
//			fos.flush();  
//			fd.sync();   
//			fos.close();
//
//			imageChart = com.itextpdf.text.Image.getInstance(imageName);
//			imageChart.scaleAbsoluteHeight(143);
//			imageChart.scaleAbsoluteWidth(200);
//
//			imagesTemp.add(file);
//
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (SyncFailedException e) {
//			e.printStackTrace();
//		} catch (BadElementException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return imageChart;
//	}

	private void addHeaderFooter() {
		
		HeaderFooterPDF headerFooterPDF = new HeaderFooterPDF(pdfWritter, "Relatório Facebook Conteúdo", (ReportLogoDTO) profile.getReportLogo());
		pdfWritter.setPageEvent(headerFooterPDF);
	}

	private void removeImagesTemp() {
		for(File fileToDelete : imagesTemp) {
			fileToDelete.delete();
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
