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
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

import br.com.opsocial.client.entity.report.ReportLogoDTO;
import br.com.opsocial.client.entity.report.facebook.FaceReportPostDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookOverviewDTO;
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

public class ReportFacebookOverviewPDF {

	private ReportFacebookOverviewDTO reportFacebookOverview;
	private Profile profile;

	private PdfWriter pdfWritter;
	private Document document;
	private String fileName;

	private HashMap<String, String> chartsImages;
	private java.util.List<File> imagesTemp;

	private static BaseFont sourceSansPro;

	private static final BaseColor BASE_COLOR_HEADER_TITLE = new BaseColor(52, 98, 144);

	public ReportFacebookOverviewPDF(ReportFacebookOverviewDTO reportFacebookOverview, Profile profile, HashMap<String, String> chartsImages) {

		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".pdf");
		this.reportFacebookOverview = reportFacebookOverview;
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

		addBasicInformation();
		addFanPageGrowth();
		addFansLikeStats();
		addFansLikeSource();
		addAboutReachedUsers();
		addEngagementWithContent();
		addPageStats();

		document.close();

		removeImagesTemp();
	}
	
	private void addFanPageGrowth() {
		
		try {
			
			PdfPTable tableFanPageGrowth = new PdfPTable(1);
			tableFanPageGrowth.setTotalWidth(new float[]{523});
			tableFanPageGrowth.setLockedWidth(true);
			tableFanPageGrowth.setSpacingAfter(22);

			PdfPCell cellFanPageGrowthTitle = new PdfPCell();
			cellFanPageGrowthTitle.setBorder(Rectangle.BOTTOM);
			cellFanPageGrowthTitle.setBorderWidthBottom(0.8f);
			cellFanPageGrowthTitle.setPaddingBottom(5);
			cellFanPageGrowthTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
			
			Paragraph parEngagementWithContentTitle = new Paragraph("CRESCIMENTO DE FÃS NA PÁGINA", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parEngagementWithContentTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellFanPageGrowthTitle.addElement(parEngagementWithContentTitle);

			tableFanPageGrowth.addCell(cellFanPageGrowthTitle);
			
			PdfPCell cellFanPageGrowthChart = new PdfPCell();
			cellFanPageGrowthChart.setBorder(PdfPCell.NO_BORDER);
			cellFanPageGrowthChart.setPadding(1);

			cellFanPageGrowthChart.addElement(addChartFanPageGrowth());
			
			tableFanPageGrowth.addCell(cellFanPageGrowthChart);
			
			document.add(tableFanPageGrowth);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}

	private void addFansLikeStats() {
	
		try {
			
			PdfPTable tableFanPageLikeStats = new PdfPTable(2);
			tableFanPageLikeStats.setTotalWidth(new float[]{ 200, 160});
			tableFanPageLikeStats.setLockedWidth(true);
			tableFanPageLikeStats.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFanPageLikeStats.setSpacingAfter(22);

			// Chart Fan Page Like Unlike.
			PdfPCell cellFanPageLikeStats = new PdfPCell();
			cellFanPageLikeStats.setBorder(PdfPCell.NO_BORDER);
			cellFanPageLikeStats.setPaddingBottom(0);
			cellFanPageLikeStats.setPaddingTop(21);
			cellFanPageLikeStats.setPaddingRight(0);
			cellFanPageLikeStats.setPaddingLeft(0);

			PdfPTable tableFanPageLikeStatsChart = new PdfPTable(1);
			tableFanPageLikeStatsChart.setTotalWidth(new float[]{200});
			tableFanPageLikeStatsChart.setLockedWidth(true);

			PdfPCell cellFanPageLikeStatsChartTitle = new PdfPCell();
			cellFanPageLikeStatsChartTitle.setBorder(Rectangle.BOTTOM);
			cellFanPageLikeStatsChartTitle.setBorderWidthBottom(0.8f);
			cellFanPageLikeStatsChartTitle.setPaddingTop(0);
			cellFanPageLikeStatsChartTitle.setPaddingLeft(0);
			cellFanPageLikeStatsChartTitle.setPaddingRight(0);
			cellFanPageLikeStatsChartTitle.setPaddingBottom(5);
			cellFanPageLikeStatsChartTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parFanPageLikeStatsChartTitle = new Paragraph("CURTIR E CURTIR (DESFAZER)", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parFanPageLikeStatsChartTitle.setAlignment(Element.ALIGN_CENTER);

			cellFanPageLikeStatsChartTitle.addElement(parFanPageLikeStatsChartTitle);

			PdfPCell cellFanPageLikeStatsChart = new PdfPCell();
			cellFanPageLikeStatsChart.setBorder(PdfPCell.NO_BORDER);
			cellFanPageLikeStatsChart.setPadding(0);

			cellFanPageLikeStatsChart.addElement(addChartFanPageLikeUnlike());

			tableFanPageLikeStatsChart.addCell(cellFanPageLikeStatsChartTitle);
			tableFanPageLikeStatsChart.addCell(cellFanPageLikeStatsChart);
			
			cellFanPageLikeStats.addElement(tableFanPageLikeStatsChart);

			// Fan Page Stats.
			PdfPCell cellFanPageStats = new PdfPCell();
			cellFanPageStats.setBorder(PdfPCell.NO_BORDER);
			cellFanPageStats.setPaddingBottom(0);
			cellFanPageStats.setPaddingTop(35);
			cellFanPageStats.setPaddingRight(0);
			cellFanPageStats.setPaddingLeft(30);

			PdfPTable tableFanPageStats = new PdfPTable(1);
			tableFanPageStats.setTotalWidth(new float[]{160});
			tableFanPageStats.setLockedWidth(true);

			// Total of Fans.
			PdfPCell cellTotalFansTitle = new PdfPCell();
			cellTotalFansTitle.setBorder(PdfPCell.NO_BORDER);
			cellTotalFansTitle.setPaddingRight(0);
			cellTotalFansTitle.setPaddingLeft(0);
			cellTotalFansTitle.setPaddingTop(0);
			cellTotalFansTitle.setPaddingBottom(2);

			Paragraph parTotalFansTitle = new Paragraph("TOTAL DE FÃS NA PÁGINA", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parTotalFansTitle.setLeading(9);

			cellTotalFansTitle.addElement(parTotalFansTitle);

			PdfPCell cellTotalFans = new PdfPCell();
			cellTotalFans.setBorder(PdfPCell.NO_BORDER);
			cellTotalFans.setPaddingRight(0);
			cellTotalFans.setPaddingLeft(0);
			cellTotalFans.setPaddingTop(0);
			cellTotalFans.setPaddingBottom(8);

			Paragraph parTotalFans = new Paragraph();
			parTotalFans.setLeading(11);

			Image imgTotalFans = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_FANS));
			imgTotalFans.scaleAbsolute(10, 10);

			parTotalFans.add(new Chunk(imgTotalFans, 0, 0));

			parTotalFans.add(new Chunk(" " + reportFacebookOverview.getTotalOfFans().toString(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(53,187,221))));

			cellTotalFans.addElement(parTotalFans);
			
			// Growth Peak of Fans.
			PdfPCell cellGrowthFansPeakTitle = new PdfPCell();
			cellGrowthFansPeakTitle.setBorder(PdfPCell.NO_BORDER);
			cellGrowthFansPeakTitle.setPaddingRight(0);
			cellGrowthFansPeakTitle.setPaddingLeft(0);
			cellGrowthFansPeakTitle.setPaddingTop(0);
			cellGrowthFansPeakTitle.setPaddingBottom(2);

			Paragraph parGrowthFansPeakTitle = new Paragraph("PICO DE CRESCIMENTO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parGrowthFansPeakTitle.setLeading(9);

			cellGrowthFansPeakTitle.addElement(parGrowthFansPeakTitle);

			PdfPCell cellGrowthFansPeak = new PdfPCell();
			cellGrowthFansPeak.setBorder(PdfPCell.NO_BORDER);
			cellGrowthFansPeak.setPaddingRight(0);
			cellGrowthFansPeak.setPaddingLeft(0);
			cellGrowthFansPeak.setPaddingTop(0);
			cellGrowthFansPeak.setPaddingBottom(2);

			Paragraph parGrowthFansPeak = new Paragraph();
			parGrowthFansPeak.setLeading(11);

			Image imgGrowthFansPeak = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_GROWTH_PEAK));
			imgGrowthFansPeak.scaleAbsolute(10, 10);

			parGrowthFansPeak.add(new Chunk(imgGrowthFansPeak, 0, 0));

			parGrowthFansPeak.add(new Chunk(" " + new SimpleDateFormat("dd/MM/yy").format(reportFacebookOverview.getMajorFanPageGrowth().getDate()),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(250, 182, 0))));

			cellGrowthFansPeak.addElement(parGrowthFansPeak);
			
			PdfPCell cellGrowthFansPeakDescription = new PdfPCell();
			cellGrowthFansPeakDescription.setBorder(PdfPCell.NO_BORDER);
			cellGrowthFansPeakDescription.setPaddingRight(0);
			cellGrowthFansPeakDescription.setPaddingLeft(0);
			cellGrowthFansPeakDescription.setPaddingTop(0);
			cellGrowthFansPeakDescription.setPaddingBottom(8);

			Paragraph parGrowthFansPeakDescription = new Paragraph();
			parGrowthFansPeakDescription.setLeading(7);
			
			String growthFansPeakDescription = "Dia " + new SimpleDateFormat("dd/MM").format(reportFacebookOverview.getMajorFanPageGrowth().getDate()) 
					+ " registrou o maior índice de crescimento, com um aumento de " 
					+ reportFacebookOverview.getMajorFanPageGrowth().getSum() + " pessoas na fanbase.";

			parGrowthFansPeakDescription.add(growthFansPeakDescription);
			parGrowthFansPeakDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellGrowthFansPeakDescription.addElement(parGrowthFansPeakDescription);

			// Conquered Fans.
			PdfPCell cellConqueredFansTitle = new PdfPCell();
			cellConqueredFansTitle.setBorder(PdfPCell.NO_BORDER);
			cellConqueredFansTitle.setPaddingRight(0);
			cellConqueredFansTitle.setPaddingLeft(0);
			cellConqueredFansTitle.setPaddingTop(0);
			cellConqueredFansTitle.setPaddingBottom(2);

			Paragraph parConqueredFansTitle = new Paragraph("FÃS CONQUISTADOS NO PERÍODO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parConqueredFansTitle.setLeading(9);

			cellConqueredFansTitle.addElement(parConqueredFansTitle);

			PdfPCell cellConqueredFans = new PdfPCell();
			cellConqueredFans.setBorder(PdfPCell.NO_BORDER);
			cellConqueredFans.setPaddingRight(0);
			cellConqueredFans.setPaddingLeft(0);
			cellConqueredFans.setPaddingTop(0);
			cellConqueredFans.setPaddingBottom(8);

			Paragraph parConqueredFans = new Paragraph();
			parConqueredFans.setLeading(11);

			Image imgConqueredFans = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_CONQUERED_FANS));
			imgConqueredFans.scaleAbsolute(10, 10);

			parConqueredFans.add(new Chunk(imgConqueredFans, 0, 0));

			parConqueredFans.add(new Chunk(" " + reportFacebookOverview.getConqueredFans().toString(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(158, 218, 30))));

			cellConqueredFans.addElement(parConqueredFans);
			
			// Lost Fans.
			PdfPCell cellLostFansTitle = new PdfPCell();
			cellLostFansTitle.setBorder(PdfPCell.NO_BORDER);
			cellLostFansTitle.setPaddingRight(0);
			cellLostFansTitle.setPaddingLeft(0);
			cellLostFansTitle.setPaddingTop(0);
			cellLostFansTitle.setPaddingBottom(2);

			Paragraph parLostFansTitle = new Paragraph("FÃS PERDIDOS NO PERÍODO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parLostFansTitle.setLeading(9);

			cellLostFansTitle.addElement(parLostFansTitle);

			PdfPCell cellLostFans = new PdfPCell();
			cellLostFans.setBorder(PdfPCell.NO_BORDER);
			cellLostFans.setPaddingRight(0);
			cellLostFans.setPaddingLeft(0);
			cellLostFans.setPaddingTop(0);
			cellLostFans.setPaddingBottom(8);

			Paragraph parLostFans = new Paragraph();
			parLostFans.setLeading(11);

			Image imgLostFans = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_LOST_FANS));
			imgLostFans.scaleAbsolute(10, 10);

			parLostFans.add(new Chunk(imgLostFans, 0, 0));

			parLostFans.add(new Chunk(" " + reportFacebookOverview.getLostFans().toString(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(215, 54, 49))));

			cellLostFans.addElement(parLostFans);
			
			// Balance fans.
			PdfPCell cellBalanceFansTitle = new PdfPCell();
			cellBalanceFansTitle.setBorder(PdfPCell.NO_BORDER);
			cellBalanceFansTitle.setPaddingRight(0);
			cellBalanceFansTitle.setPaddingLeft(0);
			cellBalanceFansTitle.setPaddingTop(0);
			cellBalanceFansTitle.setPaddingBottom(2);

			Paragraph parBalanceFansTitle = new Paragraph("SALDO DO NÚMERO DE FÃS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parBalanceFansTitle.setLeading(9);

			cellBalanceFansTitle.addElement(parBalanceFansTitle);

			PdfPCell cellBalanceFans = new PdfPCell();
			cellBalanceFans.setBorder(PdfPCell.NO_BORDER);
			cellBalanceFans.setPaddingRight(0);
			cellBalanceFans.setPaddingLeft(0);
			cellBalanceFans.setPaddingTop(0);
			cellBalanceFans.setPaddingBottom(8);

			Paragraph parBalanceFans = new Paragraph();
			parBalanceFans.setLeading(11);

			Image imgBalanceFans = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_BALANCE_FANS));
			imgBalanceFans.scaleAbsolute(10, 10);

			parBalanceFans.add(new Chunk(imgBalanceFans, 0, 0));

			parBalanceFans.add(new Chunk(" " + reportFacebookOverview.getBalanceFans().toString(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(65, 191, 223))));

			cellBalanceFans.addElement(parBalanceFans);
			
			tableFanPageStats.addCell(cellTotalFansTitle);
			tableFanPageStats.addCell(cellTotalFans);
			tableFanPageStats.addCell(cellGrowthFansPeakTitle);
			tableFanPageStats.addCell(cellGrowthFansPeak);
			tableFanPageStats.addCell(cellGrowthFansPeakDescription);
			tableFanPageStats.addCell(cellConqueredFansTitle);
			tableFanPageStats.addCell(cellConqueredFans);
			tableFanPageStats.addCell(cellLostFansTitle);
			tableFanPageStats.addCell(cellLostFans);
			tableFanPageStats.addCell(cellBalanceFansTitle);
			tableFanPageStats.addCell(cellBalanceFans);

			cellFanPageStats.addElement(tableFanPageStats);

			tableFanPageLikeStats.addCell(cellFanPageLikeStats);
			tableFanPageLikeStats.addCell(cellFanPageStats);
			
			document.add(tableFanPageLikeStats);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addFansLikeSource() {
		
		try {
			
			PdfPTable tableFansLikeSource = new PdfPTable(1);
			tableFansLikeSource.setTotalWidth(new float[]{523});
			tableFansLikeSource.setLockedWidth(true);
			tableFansLikeSource.setSpacingAfter(22);

			PdfPCell cellFansLikeSourceTitle = new PdfPCell();
			cellFansLikeSourceTitle.setBorder(Rectangle.BOTTOM);
			cellFansLikeSourceTitle.setBorderWidthBottom(0.8f);
			cellFansLikeSourceTitle.setPaddingBottom(5);
			cellFansLikeSourceTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
			
			Paragraph parFansLikeSourceTitle = new Paragraph("ORIGEM DAS OPÇÕES CURTIR", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parFansLikeSourceTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellFansLikeSourceTitle.addElement(parFansLikeSourceTitle);

			// Fans Like Source Tables.
			PdfPCell cellFansLikeSourceTables = new PdfPCell();
			cellFansLikeSourceTables.setBorder(PdfPCell.NO_BORDER);
			cellFansLikeSourceTables.setPadding(0);

			PdfPTable tableFansLikeSourceTables = new PdfPTable(2);
			tableFansLikeSourceTables.setTotalWidth(new float[]{210, 215});
			tableFansLikeSourceTables.setLockedWidth(true);
			tableFansLikeSourceTables.setSpacingBefore(5);
			
			PdfPCell cellFansLikeSourceFirstFive = new PdfPCell();
			cellFansLikeSourceFirstFive.setBorder(PdfPCell.NO_BORDER);
			cellFansLikeSourceFirstFive.setPaddingBottom(0);
			cellFansLikeSourceFirstFive.setPaddingTop(0);
			cellFansLikeSourceFirstFive.setPaddingLeft(0);
			cellFansLikeSourceFirstFive.setPaddingRight(20);
			
			PdfPTable tableFansLikeSourceFirstFiveHeader = new PdfPTable(3);
			tableFansLikeSourceFirstFiveHeader.setTotalWidth(new float[]{15, 147, 38});
			tableFansLikeSourceFirstFiveHeader.setLockedWidth(true);
			tableFansLikeSourceFirstFiveHeader.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableFansLikeSourceFirstFiveHeader.getDefaultCell().setBorderWidthBottom(0.8f);
			tableFansLikeSourceFirstFiveHeader.getDefaultCell().setBorderColorBottom(new BaseColor(227, 227, 228));
			tableFansLikeSourceFirstFiveHeader.getDefaultCell().setBackgroundColor(new BaseColor(242, 242, 242));
			tableFansLikeSourceFirstFiveHeader.getDefaultCell().setPadding(3);
			
			tableFansLikeSourceFirstFiveHeader.addCell(new Phrase(new Chunk("#", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableFansLikeSourceFirstFiveHeader.addCell(new Phrase(new Chunk("Origem", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableFansLikeSourceFirstFiveHeader.addCell(new Phrase(new Chunk("Número", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			
			PdfPCell cellFansLikeSourceLastFive = new PdfPCell();
			cellFansLikeSourceLastFive.setBorder(PdfPCell.NO_BORDER);
			cellFansLikeSourceLastFive.setPadding(0);
			
			PdfPTable tableFansLikeSourceLastFiveHeader = new PdfPTable(3);
			tableFansLikeSourceLastFiveHeader.setTotalWidth(new float[]{15, 152, 38});
			tableFansLikeSourceLastFiveHeader.setLockedWidth(true);
			tableFansLikeSourceLastFiveHeader.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableFansLikeSourceLastFiveHeader.getDefaultCell().setBorderWidthBottom(0.8f);
			tableFansLikeSourceLastFiveHeader.getDefaultCell().setBorderColorBottom(new BaseColor(227, 227, 228));
			tableFansLikeSourceLastFiveHeader.getDefaultCell().setBackgroundColor(new BaseColor(242, 242, 242));
			tableFansLikeSourceLastFiveHeader.getDefaultCell().setPadding(3);
			
			tableFansLikeSourceLastFiveHeader.addCell(new Phrase(new Chunk("#", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableFansLikeSourceLastFiveHeader.addCell(new Phrase(new Chunk("Origem", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableFansLikeSourceLastFiveHeader.addCell(new Phrase(new Chunk("Número", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			
			PdfPTable tableFansLikeSourceFirstFiveRows = new PdfPTable(3);
			tableFansLikeSourceFirstFiveRows.setTotalWidth(new float[]{15, 147, 38});
			tableFansLikeSourceFirstFiveRows.setLockedWidth(true);
			tableFansLikeSourceFirstFiveRows.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableFansLikeSourceFirstFiveRows.getDefaultCell().setBorderWidthBottom(0.8f);
			tableFansLikeSourceFirstFiveRows.getDefaultCell().setBorderColorBottom(new BaseColor(239, 239, 240));
			tableFansLikeSourceFirstFiveRows.getDefaultCell().setPadding(3);
			
			PdfPTable tableFansLikeSourceLastFiveRows = new PdfPTable(3);
			tableFansLikeSourceLastFiveRows.setTotalWidth(new float[]{15, 152, 38});
			tableFansLikeSourceLastFiveRows.setLockedWidth(true);
			tableFansLikeSourceLastFiveRows.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableFansLikeSourceLastFiveRows.getDefaultCell().setBorderWidthBottom(0.8f);
			tableFansLikeSourceLastFiveRows.getDefaultCell().setBorderColorBottom(new BaseColor(239, 239, 240));
			tableFansLikeSourceLastFiveRows.getDefaultCell().setPadding(3);
			
//			int i = 1;
//			for(Map.Entry<String, Long> fansLikeSource : reportFacebookOverview.getFansLikeSource().entrySet()) {
//				
//				if(i < 11) {
//					
//					tableFansLikeSourceFirstFiveRows.addCell(new Phrase(new Chunk(String.valueOf(i), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//					tableFansLikeSourceFirstFiveRows.addCell(new Phrase(new Chunk(UtilFunctions.getLikeSourceName(fansLikeSource.getKey()), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//					tableFansLikeSourceFirstFiveRows.addCell(new Phrase(new Chunk(fansLikeSource.getValue().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//					
//				} else {
//					
//					tableFansLikeSourceLastFiveRows.addCell(new Phrase(new Chunk(String.valueOf(i), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//					tableFansLikeSourceLastFiveRows.addCell(new Phrase(new Chunk(UtilFunctions.getLikeSourceName(fansLikeSource.getKey()), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//					tableFansLikeSourceLastFiveRows.addCell(new Phrase(new Chunk(fansLikeSource.getValue().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//					
//				}
//
//				i++;
//			}
			
			cellFansLikeSourceFirstFive.addElement(tableFansLikeSourceFirstFiveHeader);
			cellFansLikeSourceFirstFive.addElement(tableFansLikeSourceFirstFiveRows);
			
			cellFansLikeSourceLastFive.addElement(tableFansLikeSourceLastFiveHeader);
			cellFansLikeSourceLastFive.addElement(tableFansLikeSourceLastFiveRows);
			
			tableFansLikeSourceTables.addCell(cellFansLikeSourceFirstFive);
			tableFansLikeSourceTables.addCell(cellFansLikeSourceLastFive);
			
			cellFansLikeSourceTables.addElement(tableFansLikeSourceTables);
			 
			tableFansLikeSource.addCell(cellFansLikeSourceTitle);
			tableFansLikeSource.addCell(cellFansLikeSourceTables);
			
			document.add(tableFansLikeSource);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	private void addAboutReachedUsers() {
		
		try {
			
			document.newPage();
			
			PdfPTable tableAboutReachedUsers = new PdfPTable(1);
			tableAboutReachedUsers.setTotalWidth(new float[]{523});
			tableAboutReachedUsers.setLockedWidth(true);
			tableAboutReachedUsers.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableAboutReachedUsers.setSpacingAfter(22);

			PdfPCell cellAboutReachedUsersTitle = new PdfPCell();
			cellAboutReachedUsersTitle.setBorder(Rectangle.BOTTOM);
			cellAboutReachedUsersTitle.setBorderWidthBottom(0.8f);
			cellAboutReachedUsersTitle.setPaddingBottom(5);
			cellAboutReachedUsersTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parAboutReachedUsersTitle = new Paragraph("DIVISÃO DEMOGRÁFICA DO PÚBLICO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parAboutReachedUsersTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellAboutReachedUsersTitle.addElement(parAboutReachedUsersTitle);

			PdfPCell cellAboutReachedUsers = new PdfPCell();
			cellAboutReachedUsers.setBorder(PdfPCell.NO_BORDER);
			cellAboutReachedUsers.setPaddingBottom(0);
			
			PdfPTable tableAboutReachedUsersContent = new PdfPTable(2);
			tableAboutReachedUsersContent.setTotalWidth(new float[]{200, 160});
			tableAboutReachedUsersContent.setLockedWidth(true);
			
			PdfPCell cellChartAboutReachedUsers = new PdfPCell();
			cellChartAboutReachedUsers.setBorder(PdfPCell.NO_BORDER);
			cellChartAboutReachedUsers.setPadding(0);
			
			cellChartAboutReachedUsers.addElement(addChartAboutReachedUsers());
			
			PdfPCell cellAboutReachedUsersStats = new PdfPCell();
			cellAboutReachedUsersStats.setBorder(PdfPCell.NO_BORDER);
			cellAboutReachedUsersStats.setPaddingTop(8);
			cellAboutReachedUsersStats.setPaddingBottom(0);
			cellAboutReachedUsersStats.setPaddingRight(0);
			cellAboutReachedUsersStats.setPaddingLeft(30);
			
			PdfPTable tableAboutReachedUsersStats = new PdfPTable(1);
			tableAboutReachedUsersStats.setTotalWidth(new float[]{160});
			tableAboutReachedUsersStats.setLockedWidth(true);
			
			// Major Age Group of Reached Users.
			PdfPCell cellMajorAgeGroupUsersTitle = new PdfPCell();
			cellMajorAgeGroupUsersTitle.setBorder(PdfPCell.NO_BORDER);
			cellMajorAgeGroupUsersTitle.setPaddingRight(0);
			cellMajorAgeGroupUsersTitle.setPaddingLeft(0);
			cellMajorAgeGroupUsersTitle.setPaddingTop(0);
			cellMajorAgeGroupUsersTitle.setPaddingBottom(2);

			Paragraph parMajorAgeGroupUsersTitle = new Paragraph("MAIOR FAIXA ETÁRIA DO PÚBLICO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parMajorAgeGroupUsersTitle.setLeading(9);

			cellMajorAgeGroupUsersTitle.addElement(parMajorAgeGroupUsersTitle);

			PdfPCell cellMajorAgeGroupUsers = new PdfPCell();
			cellMajorAgeGroupUsers.setBorder(PdfPCell.NO_BORDER);
			cellMajorAgeGroupUsers.setPaddingRight(0);
			cellMajorAgeGroupUsers.setPaddingLeft(0);
			cellMajorAgeGroupUsers.setPaddingTop(0);
			cellMajorAgeGroupUsers.setPaddingBottom(2);

			Paragraph parMajorAgeGroupUsers = new Paragraph();
			parMajorAgeGroupUsers.setLeading(11);

			Image imgMajorAgeGroupUsers = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_MAJOR_GROUP));
			imgMajorAgeGroupUsers.scaleAbsolute(10, 10);

			parMajorAgeGroupUsers.add(new Chunk(imgMajorAgeGroupUsers, 0, 0));

			parMajorAgeGroupUsers.add(new Chunk(" " + reportFacebookOverview.getMajorAgeGroupReachedUsers().getInitialAgeGroup() + "-" +
					reportFacebookOverview.getMajorAgeGroupReachedUsers().getEndAgeGroup(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(231, 151, 25))));

			cellMajorAgeGroupUsers.addElement(parMajorAgeGroupUsers);

			PdfPCell cellMajorAgeGroupUsersDescription = new PdfPCell();
			cellMajorAgeGroupUsersDescription.setBorder(PdfPCell.NO_BORDER);
			cellMajorAgeGroupUsersDescription.setPaddingRight(0);
			cellMajorAgeGroupUsersDescription.setPaddingLeft(0);
			cellMajorAgeGroupUsersDescription.setPaddingTop(0);
			cellMajorAgeGroupUsersDescription.setPaddingBottom(8);

			Paragraph parMajorAgeGroupUsersDescription = new Paragraph();
			parMajorAgeGroupUsersDescription.setLeading(7);

			parMajorAgeGroupUsersDescription.add("A maior parte do público alcançado está entre " + 
					reportFacebookOverview.getMajorAgeGroupReachedUsers().getInitialAgeGroup() + " e " +
					reportFacebookOverview.getMajorAgeGroupReachedUsers().getEndAgeGroup() + " anos, representando " + 
					reportFacebookOverview.getMajorAgeGroupReachedUsers().getPercentage() + "% do total.");
			parMajorAgeGroupUsersDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellMajorAgeGroupUsersDescription.addElement(parMajorAgeGroupUsersDescription);
			
			// Men and Women Reached Users Division.
			PdfPCell cellMenWomenUsersTitle = new PdfPCell();
			cellMenWomenUsersTitle.setBorder(PdfPCell.NO_BORDER);
			cellMenWomenUsersTitle.setPaddingRight(0);
			cellMenWomenUsersTitle.setPaddingLeft(0);
			cellMenWomenUsersTitle.setPaddingTop(0);
			cellMenWomenUsersTitle.setPaddingBottom(2);

			Paragraph parMenWomenUsersTitle = new Paragraph("DIVISÃO DO PÚBLICO POR SEXO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parMenWomenUsersTitle.setLeading(9);

			cellMenWomenUsersTitle.addElement(parMenWomenUsersTitle);

			PdfPCell cellMenWomenUsers = new PdfPCell();
			cellMenWomenUsers.setBorder(PdfPCell.NO_BORDER);
			cellMenWomenUsers.setPaddingRight(0);
			cellMenWomenUsers.setPaddingLeft(0);
			cellMenWomenUsers.setPaddingTop(0);
			cellMenWomenUsers.setPaddingBottom(2);

			Paragraph parMenWomenUsers = new Paragraph();
			parMenWomenUsers.setLeading(11);

			Image imgMenUsers = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_MEN_FANS_OVERVIEW));
			imgMenUsers.scaleAbsolute(10, 10);

			parMenWomenUsers.add(new Chunk(imgMenUsers, 0, 0));

			parMenWomenUsers.add(new Chunk(" " + reportFacebookOverview.getMenReachedUsersPerc() + "%   ",
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(62, 179, 209))));
			
			Image imgWomenUsers = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_WOMEN_FANS_OVERVIEW));
			imgWomenUsers.scaleAbsolute(10, 10);

			parMenWomenUsers.add(new Chunk(imgWomenUsers, 0, 0));

			parMenWomenUsers.add(new Chunk(" " + reportFacebookOverview.getWomenReachedUsersPerc() + "%",
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(226, 43, 79))));

			cellMenWomenUsers.addElement(parMenWomenUsers);
			
			tableAboutReachedUsersStats.addCell(cellMajorAgeGroupUsersTitle);
			tableAboutReachedUsersStats.addCell(cellMajorAgeGroupUsers);
			tableAboutReachedUsersStats.addCell(cellMajorAgeGroupUsersDescription);
			tableAboutReachedUsersStats.addCell(cellMenWomenUsersTitle);
			tableAboutReachedUsersStats.addCell(cellMenWomenUsers);
			
			cellAboutReachedUsersStats.addElement(tableAboutReachedUsersStats);
			
			tableAboutReachedUsersContent.addCell(cellChartAboutReachedUsers);
			tableAboutReachedUsersContent.addCell(cellAboutReachedUsersStats);
			
			cellAboutReachedUsers.addElement(tableAboutReachedUsersContent);
			
			tableAboutReachedUsers.addCell(cellAboutReachedUsersTitle);
			tableAboutReachedUsers.addCell(cellAboutReachedUsers);

			document.add(tableAboutReachedUsers);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addEngagementWithContent() {
		
//		try {
//			
//			PdfPTable tableEngagementWithContent = new PdfPTable(1);
//			tableEngagementWithContent.setTotalWidth(new float[]{523});
//			tableEngagementWithContent.setLockedWidth(true);
//			tableEngagementWithContent.setSpacingAfter(22);
//
//			PdfPCell cellEngagementWithContentTitle = new PdfPCell();
//			cellEngagementWithContentTitle.setBorder(Rectangle.BOTTOM);
//			cellEngagementWithContentTitle.setBorderWidthBottom(0.8f);
//			cellEngagementWithContentTitle.setPaddingBottom(5);
//			cellEngagementWithContentTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
//			
//			Paragraph parEngagementWithContentTitle = new Paragraph("ENVOLVIMENTO DO PÚBLICO POR TIPO DE INTERAÇÃO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
//			parEngagementWithContentTitle.setAlignment(Element.ALIGN_CENTER);
//			
//			cellEngagementWithContentTitle.addElement(parEngagementWithContentTitle);
//
//			PdfPCell cellChartEngagementContent = new PdfPCell();
//			cellChartEngagementContent.setBorder(PdfPCell.NO_BORDER);
//			cellChartEngagementContent.setPadding(1);
//
//			cellChartEngagementContent.addElement(addChartEngagementContent());
//
//			PdfPCell cellEngagementWithContentStats = new PdfPCell();
//			cellEngagementWithContentStats.setBorder(PdfPCell.NO_BORDER);
//			cellEngagementWithContentStats.setPadding(0);
//			
//			PdfPTable tableEngagementWithContentStats = new PdfPTable(4);
//			tableEngagementWithContentStats.setTotalWidth(new float[]{122, 81, 81, 81});
//			tableEngagementWithContentStats.setLockedWidth(true);
//			tableEngagementWithContentStats.setSpacingBefore(15);
//			
//			// Interactions in the period.
//			PdfPCell cellInteractionsByPeriod = new PdfPCell();
//			cellInteractionsByPeriod.setBorder(Rectangle.RIGHT);
//			cellInteractionsByPeriod.setBorderWidthRight(0.8f);
//			cellInteractionsByPeriod.setBorderColorRight(new BaseColor(177, 177, 177));
//			cellInteractionsByPeriod.setPaddingLeft(0);
//			cellInteractionsByPeriod.setPaddingRight(0);
//			cellInteractionsByPeriod.setPaddingTop(3);
//			cellInteractionsByPeriod.setPaddingBottom(3);
//			
//			PdfPTable tableInteractionsByPeriod = new PdfPTable(1);
//			tableInteractionsByPeriod.setTotalWidth(new float[]{122});
//			tableInteractionsByPeriod.setLockedWidth(true);
//			
//			PdfPCell cellInteractionsByPeriodTitle = new PdfPCell();
//			cellInteractionsByPeriodTitle.setBorder(PdfPCell.NO_BORDER);
//			cellInteractionsByPeriodTitle.setPaddingRight(0);
//			cellInteractionsByPeriodTitle.setPaddingLeft(0);
//			cellInteractionsByPeriodTitle.setPaddingTop(0);
//			cellInteractionsByPeriodTitle.setPaddingBottom(2);
//
//			Paragraph parInteractionsByPeriodTitle = new Paragraph("INTERAÇÕES NO PERÍODO", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parInteractionsByPeriodTitle.setLeading(9);
//
//			cellInteractionsByPeriodTitle.addElement(parInteractionsByPeriodTitle);
//			
//			PdfPCell cellInteractionsByPeriodIMage = new PdfPCell();
//			cellInteractionsByPeriodIMage.setBorder(PdfPCell.NO_BORDER);
//			cellInteractionsByPeriodIMage.setPadding(0);
//			
//			Paragraph parInteractionsByPeriod = new Paragraph();
//			parInteractionsByPeriod.setLeading(11);
//			
//			Image imgInteractionsByPeriod = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INTERACTIONS));
//			imgInteractionsByPeriod.scaleAbsolute(10, 10);
//
//			parInteractionsByPeriod.add(new Chunk(imgInteractionsByPeriod, 0, 0));
//			
//			Long postsLikes = reportFacebookOverview.getPostsEngagementTotalStats().containsKey(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_LIKE) ? 
//					reportFacebookOverview.getPostsEngagementTotalStats().get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_LIKE) : 0L;
//			
//			Long postsComments = reportFacebookOverview.getPostsEngagementTotalStats().containsKey(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_COMMENT) ? 
//					reportFacebookOverview.getPostsEngagementTotalStats().get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_COMMENT) : 0L;
//					
//			Long postsShares = reportFacebookOverview.getPostsEngagementTotalStats().containsKey(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_SHARE) ? 
//					reportFacebookOverview.getPostsEngagementTotalStats().get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_SHARE) : 0L;
//
//			parInteractionsByPeriod.add(new Chunk(" " + String.valueOf(postsLikes + postsComments + postsShares),
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(62, 179, 209))));
//
//			cellInteractionsByPeriodIMage.addElement(parInteractionsByPeriod);
//			
//			tableInteractionsByPeriod.addCell(cellInteractionsByPeriodTitle);
//			tableInteractionsByPeriod.addCell(cellInteractionsByPeriodIMage);
//			
//			cellInteractionsByPeriod.addElement(tableInteractionsByPeriod);
//			
//			// Likes in the period.
//			PdfPCell cellLikesByPeriod = new PdfPCell();
//			cellLikesByPeriod.setBorder(Rectangle.NO_BORDER);
//			cellLikesByPeriod.setPaddingLeft(11);
//			cellLikesByPeriod.setPaddingRight(0);
//			cellLikesByPeriod.setPaddingTop(3);
//			cellLikesByPeriod.setPaddingBottom(3);
//
//			PdfPTable tableLikesByPeriod = new PdfPTable(1);
//
//			PdfPCell cellLikesByPeriodTitle = new PdfPCell();
//			cellLikesByPeriodTitle.setBorder(PdfPCell.NO_BORDER);
//			cellLikesByPeriodTitle.setPaddingRight(0);
//			cellLikesByPeriodTitle.setPaddingLeft(0);
//			cellLikesByPeriodTitle.setPaddingTop(0);
//			cellLikesByPeriodTitle.setPaddingBottom(2);
//
//			Paragraph parLikesByPeriodTitle = new Paragraph("CURTIR", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parLikesByPeriodTitle.setLeading(9);
//
//			cellLikesByPeriodTitle.addElement(parLikesByPeriodTitle);
//
//			PdfPCell cellLikesByPeriodIMage = new PdfPCell();
//			cellLikesByPeriodIMage.setBorder(PdfPCell.NO_BORDER);
//			cellLikesByPeriodIMage.setPadding(0);
//
//			Paragraph parLikesByPeriod = new Paragraph();
//			parLikesByPeriod.setLeading(11);
//
//			Image imgLikesByPeriod = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_LIKES_OVERVIEW));
//			imgLikesByPeriod.scaleAbsolute(10, 10);
//
//			parLikesByPeriod.add(new Chunk(imgLikesByPeriod, 0, 0));
//
//			parLikesByPeriod.add(new Chunk(" " + postsLikes,
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(159, 220, 53))));
//
//			cellLikesByPeriodIMage.addElement(parLikesByPeriod);
//
//			tableLikesByPeriod.addCell(cellLikesByPeriodTitle);
//			tableLikesByPeriod.addCell(cellLikesByPeriodIMage);
//
//			cellLikesByPeriod.addElement(tableLikesByPeriod);
//			
//			// Comments in the period.
//			PdfPCell cellCommentsByPeriod = new PdfPCell();
//			cellCommentsByPeriod.setBorder(Rectangle.NO_BORDER);
//			cellCommentsByPeriod.setPaddingLeft(0);
//			cellCommentsByPeriod.setPaddingRight(0);
//			cellCommentsByPeriod.setPaddingTop(3);
//			cellCommentsByPeriod.setPaddingBottom(3);
//
//			PdfPTable tableCommentsByPeriod = new PdfPTable(1);
//
//			PdfPCell cellCommentsByPeriodTitle = new PdfPCell();
//			cellCommentsByPeriodTitle.setBorder(PdfPCell.NO_BORDER);
//			cellCommentsByPeriodTitle.setPaddingRight(0);
//			cellCommentsByPeriodTitle.setPaddingLeft(0);
//			cellCommentsByPeriodTitle.setPaddingTop(0);
//			cellCommentsByPeriodTitle.setPaddingBottom(2);
//
//			Paragraph parCommentsByPeriodTitle = new Paragraph("COMENTÁRIOS", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parCommentsByPeriodTitle.setLeading(9);
//
//			cellCommentsByPeriodTitle.addElement(parCommentsByPeriodTitle);
//
//			PdfPCell cellCommentsByPeriodIMage = new PdfPCell();
//			cellCommentsByPeriodIMage.setBorder(PdfPCell.NO_BORDER);
//			cellCommentsByPeriodIMage.setPadding(0);
//
//			Paragraph parCommentsByPeriod = new Paragraph();
//			parCommentsByPeriod.setLeading(11);
//
//			Image imgCommentsByPeriod = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_COMMENTS_OVERVIEW));
//			imgCommentsByPeriod.scaleAbsolute(10, 10);
//
//			parCommentsByPeriod.add(new Chunk(imgCommentsByPeriod, 0, 0));
//
//			parCommentsByPeriod.add(new Chunk(" " + postsComments,
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(243, 105, 36))));
//
//			cellCommentsByPeriodIMage.addElement(parCommentsByPeriod);
//
//			tableCommentsByPeriod.addCell(cellCommentsByPeriodTitle);
//			tableCommentsByPeriod.addCell(cellCommentsByPeriodIMage);
//
//			cellCommentsByPeriod.addElement(tableCommentsByPeriod);
//			
//			// Shares in the period.
//			PdfPCell cellSharesByPeriod = new PdfPCell();
//			cellSharesByPeriod.setBorder(Rectangle.NO_BORDER);
//			cellSharesByPeriod.setPaddingLeft(0);
//			cellSharesByPeriod.setPaddingRight(0);
//			cellSharesByPeriod.setPaddingTop(3);
//			cellSharesByPeriod.setPaddingBottom(3);
//
//			PdfPTable tableSharesByPeriod = new PdfPTable(1);
//
//			PdfPCell cellSharesByPeriodTitle = new PdfPCell();
//			cellSharesByPeriodTitle.setBorder(PdfPCell.NO_BORDER);
//			cellSharesByPeriodTitle.setPaddingRight(0);
//			cellSharesByPeriodTitle.setPaddingLeft(0);
//			cellSharesByPeriodTitle.setPaddingTop(0);
//			cellSharesByPeriodTitle.setPaddingBottom(2);
//
//			Paragraph parSharesByPeriodTitle = new Paragraph("COMPARTILHAR", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parSharesByPeriodTitle.setLeading(9);
//
//			cellSharesByPeriodTitle.addElement(parSharesByPeriodTitle);
//
//			PdfPCell cellSharesByPeriodIMage = new PdfPCell();
//			cellSharesByPeriodIMage.setBorder(PdfPCell.NO_BORDER);
//			cellSharesByPeriodIMage.setPadding(0);
//
//			Paragraph parSharesByPeriod = new Paragraph();
//			parSharesByPeriod.setLeading(11);
//
//			Image imgSharesByPeriod = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_SHARES_OVERVIEW));
//			imgSharesByPeriod.scaleAbsolute(10, 10);
//
//			parSharesByPeriod.add(new Chunk(imgSharesByPeriod, 0, 0));
//
//			parSharesByPeriod.add(new Chunk(" " + postsShares,
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(238, 197, 4))));
//
//			cellSharesByPeriodIMage.addElement(parSharesByPeriod);
//
//			tableSharesByPeriod.addCell(cellSharesByPeriodTitle);
//			tableSharesByPeriod.addCell(cellSharesByPeriodIMage);
//
//			cellSharesByPeriod.addElement(tableSharesByPeriod);
//			
//			tableEngagementWithContentStats.addCell(cellInteractionsByPeriod);
//			tableEngagementWithContentStats.addCell(cellLikesByPeriod);
//			tableEngagementWithContentStats.addCell(cellCommentsByPeriod);
//			tableEngagementWithContentStats.addCell(cellSharesByPeriod);
//			
//			cellEngagementWithContentStats.addElement(tableEngagementWithContentStats);
//			
//			tableEngagementWithContent.addCell(cellEngagementWithContentTitle);
//			tableEngagementWithContent.addCell(cellChartEngagementContent);
//			tableEngagementWithContent.addCell(cellEngagementWithContentStats);
//			
//			document.add(tableEngagementWithContent);
//			
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
	
	private void addPageStats() {

		try {
			
			PdfPTable tablePageStats = new PdfPTable(2);
			tablePageStats.setTotalWidth(new float[]{ 190, 170});
			tablePageStats.setLockedWidth(true);
			tablePageStats.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablePageStats.setSpacingAfter(20);

			// Other activities.
			PdfPCell cellOtherActivities = new PdfPCell();
			cellOtherActivities.setBorder(PdfPCell.NO_BORDER);
			cellOtherActivities.setPaddingBottom(0);
			cellOtherActivities.setPaddingTop(0);
			cellOtherActivities.setPaddingLeft(0);
			cellOtherActivities.setPaddingRight(20);

			PdfPTable tableOtherActivities = new PdfPTable(1);
			tableOtherActivities.setTotalWidth(new float[]{170});
			tableOtherActivities.setLockedWidth(true);
			tableOtherActivities.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellOtherActivitiesTitle = new PdfPCell();
			cellOtherActivitiesTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellOtherActivitiesTitle.setBorder(Rectangle.BOTTOM);
			cellOtherActivitiesTitle.setBorderWidthBottom(0.8f);
			cellOtherActivitiesTitle.setPaddingTop(0);
			cellOtherActivitiesTitle.setPaddingLeft(0);
			cellOtherActivitiesTitle.setPaddingRight(0);
			cellOtherActivitiesTitle.setPaddingBottom(5);
			cellOtherActivitiesTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parOtherActivitiesTitle = new Paragraph("OUTRAS ATIVIDADES NA PÁGINA", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parOtherActivitiesTitle.setAlignment(Element.ALIGN_CENTER);

			cellOtherActivitiesTitle.addElement(parOtherActivitiesTitle);

			tableOtherActivities.addCell(cellOtherActivitiesTitle);
			
			// Other activities table.
			PdfPCell cellOtherActivitiesTable = new PdfPCell();
			cellOtherActivitiesTable.setPadding(0);
			cellOtherActivitiesTable.setBorder(PdfPCell.NO_BORDER);

			PdfPTable tableOtherActivitiesTableHeader = new PdfPTable(3);
			tableOtherActivitiesTableHeader.setTotalWidth(new float[]{15, 115, 40});
			tableOtherActivitiesTableHeader.setLockedWidth(true);
			tableOtherActivitiesTableHeader.setSpacingBefore(5);
			tableOtherActivitiesTableHeader.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableOtherActivitiesTableHeader.getDefaultCell().setBorderWidthBottom(0.8f);
			tableOtherActivitiesTableHeader.getDefaultCell().setBorderColorBottom(new BaseColor(227, 227, 228));
			tableOtherActivitiesTableHeader.getDefaultCell().setBackgroundColor(new BaseColor(242, 242, 242));
			tableOtherActivitiesTableHeader.getDefaultCell().setPadding(3);
			
			tableOtherActivitiesTableHeader.addCell(new Phrase(new Chunk("#", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableOtherActivitiesTableHeader.addCell(new Phrase(new Chunk("Atividade", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableOtherActivitiesTableHeader.addCell(new Phrase(new Chunk("Número", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			
			cellOtherActivitiesTable.addElement(tableOtherActivitiesTableHeader);
			
			PdfPTable tableOtherActivitiesTableRows = new PdfPTable(3);
			tableOtherActivitiesTableRows.setTotalWidth(new float[]{15, 115, 40});
			tableOtherActivitiesTableRows.setLockedWidth(true);
			tableOtherActivitiesTableRows.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableOtherActivitiesTableRows.getDefaultCell().setBorderWidthBottom(0.8f);
			tableOtherActivitiesTableRows.getDefaultCell().setBorderColorBottom(new BaseColor(239, 239, 240));
			tableOtherActivitiesTableRows.getDefaultCell().setPadding(3);
			
//			int i = 1;
//			for(Map.Entry<String, Long> storyType : reportFacebookOverview.getOtherActivities().entrySet()) {
//
//				tableOtherActivitiesTableRows.addCell(new Phrase(new Chunk(String.valueOf(i), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//				tableOtherActivitiesTableRows.addCell(new Phrase(new Chunk(UtilFunctions.getStoryTypeName(storyType.getKey()), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//				tableOtherActivitiesTableRows.addCell(new Phrase(new Chunk(storyType.getValue().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//
//				i++;
//			}
			
			cellOtherActivitiesTable.addElement(tableOtherActivitiesTableRows);
			
			tableOtherActivities.addCell(cellOtherActivitiesTable);
			
			cellOtherActivities.addElement(tableOtherActivities);
			
			tablePageStats.addCell(cellOtherActivities);
			
			// External references.
			PdfPCell cellExternalReferences = new PdfPCell();
			cellExternalReferences.setBorder(PdfPCell.NO_BORDER);
			cellExternalReferences.setPadding(0);

			PdfPTable tableExternalReferences = new PdfPTable(1);
			tableExternalReferences.setTotalWidth(new float[]{170});
			tableExternalReferences.setLockedWidth(true);
			tableExternalReferences.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellExternalReferencesTitle = new PdfPCell();
			cellExternalReferencesTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellExternalReferencesTitle.setBorder(Rectangle.BOTTOM);
			cellExternalReferencesTitle.setBorderWidthBottom(0.8f);
			cellExternalReferencesTitle.setPaddingTop(0);
			cellExternalReferencesTitle.setPaddingLeft(0);
			cellExternalReferencesTitle.setPaddingRight(0);
			cellExternalReferencesTitle.setPaddingBottom(5);
			cellExternalReferencesTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parExternalReferencesTitle = new Paragraph("REFERÊNCIAS EXTERNAS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parExternalReferencesTitle.setAlignment(Element.ALIGN_CENTER);

			cellExternalReferencesTitle.addElement(parExternalReferencesTitle);

			tableExternalReferences.addCell(cellExternalReferencesTitle);
			
			// External references table.
			PdfPCell cellExternalReferencesTable = new PdfPCell();
			cellExternalReferencesTable.setPadding(0);
			cellExternalReferencesTable.setBorder(PdfPCell.NO_BORDER);

			PdfPTable tableExternalReferencesTableHeader = new PdfPTable(3);
			tableExternalReferencesTableHeader.setTotalWidth(new float[]{15, 115, 40});
			tableExternalReferencesTableHeader.setLockedWidth(true);
			tableExternalReferencesTableHeader.setSpacingBefore(5);
			tableExternalReferencesTableHeader.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableExternalReferencesTableHeader.getDefaultCell().setBorderWidthBottom(0.8f);
			tableExternalReferencesTableHeader.getDefaultCell().setBorderColorBottom(new BaseColor(227, 227, 228));
			tableExternalReferencesTableHeader.getDefaultCell().setBackgroundColor(new BaseColor(242, 242, 242));
			tableExternalReferencesTableHeader.getDefaultCell().setPadding(3);
			
			tableExternalReferencesTableHeader.addCell(new Phrase(new Chunk("#", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableExternalReferencesTableHeader.addCell(new Phrase(new Chunk("Referência", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableExternalReferencesTableHeader.addCell(new Phrase(new Chunk("Número", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			
			cellExternalReferencesTable.addElement(tableExternalReferencesTableHeader);
			
			PdfPTable tableExternalReferencesTableRows = new PdfPTable(3);
			tableExternalReferencesTableRows.setTotalWidth(new float[]{15, 115, 40});
			tableExternalReferencesTableRows.setLockedWidth(true);
			tableExternalReferencesTableRows.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableExternalReferencesTableRows.getDefaultCell().setBorderWidthBottom(0.8f);
			tableExternalReferencesTableRows.getDefaultCell().setBorderColorBottom(new BaseColor(239, 239, 240));
			tableExternalReferencesTableRows.getDefaultCell().setPadding(3);
			
//			int j = 1;
//			for(Map.Entry<String, Long> externalReference : reportFacebookOverview.getExternalReferences().entrySet()) {
//				
//				tableExternalReferencesTableRows.addCell(new Phrase(new Chunk(String.valueOf(j), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//				tableExternalReferencesTableRows.addCell(new Phrase(new Chunk(externalReference.getKey(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//				tableExternalReferencesTableRows.addCell(new Phrase(new Chunk(externalReference.getValue().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//
//				j++;
//			}
			
			cellExternalReferencesTable.addElement(tableExternalReferencesTableRows);
			
			tableExternalReferences.addCell(cellExternalReferencesTable);
			
			cellExternalReferences.addElement(tableExternalReferences);
			
			tablePageStats.addCell(cellExternalReferences);
			
			document.add(tablePageStats);

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}
	
	private void addBasicInformation() {

		try {

			PdfPTable tableProfile = new PdfPTable(1);
			tableProfile.setTotalWidth(new float[]{523f});
			tableProfile.setLockedWidth(true);

			PdfPCell cellProfile = new PdfPCell();
			cellProfile.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellProfile.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellProfile.setBorder(Rectangle.NO_BORDER);
			cellProfile.setFixedHeight(25f);

			Paragraph parProfile = new Paragraph();
			parProfile.setLeading(11f);

			parProfile.add(new Chunk(profile.getScreenName(), new Font(sourceSansPro, 13, Font.NORMAL, BASE_COLOR_HEADER_TITLE)));
			parProfile.add(new Chunk("  "));

			Phrase phrReportsDate = new Phrase();

			String reportsDescription = "•  Relatório Facebook (Visão Geral)  -  " + 
					new SimpleDateFormat("dd/MM/yy").format(new Date(reportFacebookOverview.getDateFrom() * 1000L))
					+" a " +
					new SimpleDateFormat("dd/MM/yy").format(new Date(reportFacebookOverview.getDateUntil() * 1000L));

			Chunk cnkReportsDate = new Chunk(reportsDescription, new Font(sourceSansPro, 9, Font.NORMAL, BASE_COLOR_HEADER_TITLE));

			phrReportsDate.add(cnkReportsDate);

			parProfile.add(phrReportsDate);

			cellProfile.addElement(parProfile);

			tableProfile.addCell(cellProfile);

			document.add(tableProfile);
			document.add(new Paragraph(" "));

		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	private Image addChartAboutReachedUsers() {
		
		String img = chartsImages.get("chartAboutReachedUsersOverview").substring(chartsImages.get("chartAboutReachedUsersOverview").indexOf("base64,") + 7);

		com.itextpdf.text.Image imageChart = null;

		try {

			byte[] array = Base64.decodeBase64(img.getBytes("UTF-8"));
			String imageName = "" + RandomStringUtils.randomAlphanumeric(8) + ".jpg";
			File file = new File(imageName);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);  
			fos.write(array);  
			FileDescriptor fd = fos.getFD();  
			fos.flush();  
			fd.sync();   
			fos.close();

			imageChart = com.itextpdf.text.Image.getInstance(imageName);
			imageChart.scaleAbsoluteHeight(140);
			imageChart.scaleAbsoluteWidth(200);

			imagesTemp.add(file);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SyncFailedException e) {
			e.printStackTrace();
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageChart;
	}
	
	private Image addChartFanPageLikeUnlike() {
		
		String img = chartsImages.get("chartLikeUnlike").substring(chartsImages.get("chartLikeUnlike").indexOf("base64,") + 7);

		com.itextpdf.text.Image imageChart = null;

		try {

			byte[] array = Base64.decodeBase64(img.getBytes("UTF-8"));
			String imageName = "" + RandomStringUtils.randomAlphanumeric(8) + ".jpg";
			File file = new File(imageName);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);  
			fos.write(array);  
			FileDescriptor fd = fos.getFD();  
			fos.flush();  
			fd.sync();   
			fos.close();

			imageChart = com.itextpdf.text.Image.getInstance(imageName);
			imageChart.scaleAbsoluteHeight(170);
			imageChart.scaleAbsoluteWidth(200);
			imageChart.setAlignment(Element.ALIGN_CENTER);

			imagesTemp.add(file);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SyncFailedException e) {
			e.printStackTrace();
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageChart;
	}
	
	private Image addChartEngagementContent() {
		
		String img = chartsImages.get("chartEngagementContentOverview").substring(chartsImages.get("chartEngagementContentOverview").indexOf("base64,") + 7);

		com.itextpdf.text.Image imageChart = null;

		try {

			byte[] array = Base64.decodeBase64(img.getBytes("UTF-8"));
			String imageName = "" + RandomStringUtils.randomAlphanumeric(8) + ".jpg";
			File file = new File(imageName);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);  
			fos.write(array);  
			FileDescriptor fd = fos.getFD();  
			fos.flush();  
			fd.sync();   
			fos.close();

			imageChart = com.itextpdf.text.Image.getInstance(imageName);
			imageChart.scaleAbsoluteHeight(143);
			imageChart.scaleAbsoluteWidth(365);
			imageChart.setAlignment(Element.ALIGN_CENTER);

			imagesTemp.add(file);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SyncFailedException e) {
			e.printStackTrace();
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageChart;
	}
	
	private Image addChartFanPageGrowth() {
		
		String img = chartsImages.get("chartFanPageGrowthOverview").substring(chartsImages.get("chartFanPageGrowthOverview").indexOf("base64,") + 7);

		com.itextpdf.text.Image imageChart = null;

		try {

			byte[] array = Base64.decodeBase64(img.getBytes("UTF-8"));
			String imageName = "" + RandomStringUtils.randomAlphanumeric(8) + ".jpg";
			File file = new File(imageName);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);  
			fos.write(array);  
			FileDescriptor fd = fos.getFD();  
			fos.flush();  
			fd.sync();   
			fos.close();

			imageChart = com.itextpdf.text.Image.getInstance(imageName);
			imageChart.scaleAbsoluteHeight(143);
			imageChart.scaleAbsoluteWidth(360);
			imageChart.setAlignment(Element.ALIGN_CENTER);

			imagesTemp.add(file);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SyncFailedException e) {
			e.printStackTrace();
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageChart;
	}
	
	private void addHeaderFooter() {
		
		HeaderFooterPDF headerFooterPDF = new HeaderFooterPDF(pdfWritter, "Relatório Facebook Visão Geral", (ReportLogoDTO) profile.getReportLogo());
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