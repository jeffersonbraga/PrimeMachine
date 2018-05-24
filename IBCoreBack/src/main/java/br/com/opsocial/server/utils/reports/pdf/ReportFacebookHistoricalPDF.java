package br.com.opsocial.server.utils.reports.pdf;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SyncFailedException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

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
//import com.smartgwt.client.util.StringUtil;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.report.ReportLogoDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookHistoricalDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.pdf.HeaderFooterPDF;
import br.com.opsocial.server.utils.reports.ReportIcons;

public class ReportFacebookHistoricalPDF {

	private ReportFacebookHistoricalDTO reportFacebookHistorical;
	private Profile profile;

	private PdfWriter pdfWritter;
	private Document document;
	private String fileName;

	private HashMap<String, String> chartsImages;
	private java.util.List<File> imagesTemp;

	private static BaseFont sourceSansPro;

	private static final BaseColor BASE_COLOR_HEADER_TITLE = new BaseColor(52, 98, 144);

	public ReportFacebookHistoricalPDF() {

	}

	public ReportFacebookHistoricalPDF(ReportFacebookHistoricalDTO reportFacebookHistorical, Profile profile, HashMap<String, String> chartsImages) {

		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".pdf");
		this.reportFacebookHistorical = reportFacebookHistorical;
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
		addAboutFansUsers();
		addCitiesStats();
		addInfluentialFans();
		addEngagementWithContent();
		addPostsMostEngaged();

		document.close();

		removeImagesTemp();
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

			String reportsDescription = "•  Relatório Facebook (Histórico)";

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

	private void addFanPageGrowth() {

		try {

			PdfPTable tableFanPageGrowthTitle = new PdfPTable(1);
			tableFanPageGrowthTitle.setTotalWidth(new float[]{523});
			tableFanPageGrowthTitle.setLockedWidth(true);
			tableFanPageGrowthTitle.setHorizontalAlignment(Element.ALIGN_CENTER);

			tableFanPageGrowthTitle.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableFanPageGrowthTitle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFanPageGrowthTitle.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableFanPageGrowthTitle.getDefaultCell().setBorderWidthBottom(0.8f);
			tableFanPageGrowthTitle.getDefaultCell().setPaddingBottom(5);
			tableFanPageGrowthTitle.getDefaultCell().setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parResumeHeader = new Paragraph("CRESCIMENTO DE FÃS NA PÁGINA", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parResumeHeader.setAlignment(Element.ALIGN_LEFT);

			tableFanPageGrowthTitle.addCell(parResumeHeader);

			document.add(tableFanPageGrowthTitle);

			PdfPTable tableFanPageGrowthStats = new PdfPTable(2);
			tableFanPageGrowthStats.setTotalWidth(new float[]{ 213, 150});
			tableFanPageGrowthStats.setLockedWidth(true);
			tableFanPageGrowthStats.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFanPageGrowthStats.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tableFanPageGrowthStats.getDefaultCell().setPaddingTop(5);
			tableFanPageGrowthStats.setSpacingAfter(20);

			PdfPTable tableChartFanPageGrowth = new PdfPTable(1);
			tableChartFanPageGrowth.setTotalWidth(new float[]{213});
			tableChartFanPageGrowth.setLockedWidth(true);
			tableChartFanPageGrowth.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell cellChartNumberOfPosts = new PdfPCell();
			cellChartNumberOfPosts.setBorder(PdfPCell.NO_BORDER);

			cellChartNumberOfPosts.addElement(addChartFanPageGrowth());

			tableChartFanPageGrowth.addCell(cellChartNumberOfPosts);

			tableFanPageGrowthStats.addCell(tableChartFanPageGrowth);

			PdfPCell cellReportOfNetworksStats = new PdfPCell();
			cellReportOfNetworksStats.setBorder(PdfPCell.NO_BORDER);
			cellReportOfNetworksStats.setPaddingLeft(15);

			cellReportOfNetworksStats.addElement(createFanPageGrowthStatsTable());

			tableFanPageGrowthStats.addCell(cellReportOfNetworksStats);

			document.add(tableFanPageGrowthStats);

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	private void addAboutFansUsers() {

		try {
			
			PdfPTable tableAboutFansUsers = new PdfPTable(2);
			tableAboutFansUsers.setTotalWidth(new float[]{ 190, 170});
			tableAboutFansUsers.setLockedWidth(true);
			tableAboutFansUsers.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableAboutFansUsers.setSpacingAfter(30);

			// About Your Fans.
			tableAboutFansUsers.addCell(addAboutYourFans());

			// About Reached Users.
			tableAboutFansUsers.addCell(addAboutReachedUsers());

			document.add(tableAboutFansUsers);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void addCitiesStats() {

		try {
			
			PdfPTable tableCitiesStats = new PdfPTable(2);
			tableCitiesStats.setTotalWidth(new float[]{ 190, 170});
			tableCitiesStats.setLockedWidth(true);
			tableCitiesStats.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableCitiesStats.setSpacingAfter(20);

			// Cities with more fans.
			PdfPCell cellCitiesMoreFans = new PdfPCell();
			cellCitiesMoreFans.setBorder(PdfPCell.NO_BORDER);
			cellCitiesMoreFans.setPaddingBottom(0);
			cellCitiesMoreFans.setPaddingTop(0);
			cellCitiesMoreFans.setPaddingLeft(0);
			cellCitiesMoreFans.setPaddingRight(20);

			PdfPTable tableCitiesMoreFans = new PdfPTable(1);
			tableCitiesMoreFans.setTotalWidth(new float[]{170});
			tableCitiesMoreFans.setLockedWidth(true);
			tableCitiesMoreFans.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellCitiesMoreFansTitle = new PdfPCell();
			cellCitiesMoreFansTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellCitiesMoreFansTitle.setBorder(Rectangle.BOTTOM);
			cellCitiesMoreFansTitle.setBorderWidthBottom(0.8f);
			cellCitiesMoreFansTitle.setPaddingTop(0);
			cellCitiesMoreFansTitle.setPaddingLeft(0);
			cellCitiesMoreFansTitle.setPaddingRight(0);
			cellCitiesMoreFansTitle.setPaddingBottom(5);
			cellCitiesMoreFansTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parCitiesMoreFansTitle = new Paragraph("CIDADES COM MAIS FÃS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parCitiesMoreFansTitle.setAlignment(Element.ALIGN_CENTER);

			cellCitiesMoreFansTitle.addElement(parCitiesMoreFansTitle);

			tableCitiesMoreFans.addCell(cellCitiesMoreFansTitle);
			
			// Cities with more fans table.
			PdfPCell cellCitiesMoreFansTable = new PdfPCell();
			cellCitiesMoreFansTable.setPadding(0);
			cellCitiesMoreFansTable.setBorder(PdfPCell.NO_BORDER);

			PdfPTable tableCitiesMoreFansTableHeader = new PdfPTable(3);
			tableCitiesMoreFansTableHeader.setTotalWidth(new float[]{15, 127, 28});
			tableCitiesMoreFansTableHeader.setLockedWidth(true);
			tableCitiesMoreFansTableHeader.setSpacingBefore(5);
			tableCitiesMoreFansTableHeader.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableCitiesMoreFansTableHeader.getDefaultCell().setBorderWidthBottom(0.8f);
			tableCitiesMoreFansTableHeader.getDefaultCell().setBorderColorBottom(new BaseColor(227, 227, 228));
			tableCitiesMoreFansTableHeader.getDefaultCell().setBackgroundColor(new BaseColor(242, 242, 242));
			tableCitiesMoreFansTableHeader.getDefaultCell().setPadding(3);
			
			tableCitiesMoreFansTableHeader.addCell(new Phrase(new Chunk("#", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableCitiesMoreFansTableHeader.addCell(new Phrase(new Chunk("Cidade", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableCitiesMoreFansTableHeader.addCell(new Phrase(new Chunk("Fãs", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			
			cellCitiesMoreFansTable.addElement(tableCitiesMoreFansTableHeader);
			
			PdfPTable tableCitiesMoreFansTableRows = new PdfPTable(3);
			tableCitiesMoreFansTableRows.setTotalWidth(new float[]{15, 127, 28});
			tableCitiesMoreFansTableRows.setLockedWidth(true);
			tableCitiesMoreFansTableRows.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableCitiesMoreFansTableRows.getDefaultCell().setBorderWidthBottom(0.8f);
			tableCitiesMoreFansTableRows.getDefaultCell().setBorderColorBottom(new BaseColor(239, 239, 240));
			tableCitiesMoreFansTableRows.getDefaultCell().setPadding(3);
			
//			for(int i = 0; i < reportFacebookHistorical.getCitiesMoreFans().size(); i++) {
//
//				tableCitiesMoreFansTableRows.addCell(new Phrase(new Chunk(String.valueOf(i + 1), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//				tableCitiesMoreFansTableRows.addCell(new Phrase(new Chunk(reportFacebookHistorical.getCitiesMoreFans().get(i).getCity(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//				tableCitiesMoreFansTableRows.addCell(new Phrase(new Chunk(reportFacebookHistorical.getCitiesMoreFans().get(i).getValue().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//
//			}
			
			cellCitiesMoreFansTable.addElement(tableCitiesMoreFansTableRows);
			
			tableCitiesMoreFans.addCell(cellCitiesMoreFansTable);
			
			cellCitiesMoreFans.addElement(tableCitiesMoreFans);
			
			tableCitiesStats.addCell(cellCitiesMoreFans);
			
			// Cities reached.
			PdfPCell cellCitiesReached = new PdfPCell();
			cellCitiesReached.setBorder(PdfPCell.NO_BORDER);
			cellCitiesReached.setPadding(0);

			PdfPTable tableCitiesReached = new PdfPTable(1);
			tableCitiesReached.setTotalWidth(new float[]{170});
			tableCitiesReached.setLockedWidth(true);
			tableCitiesReached.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellCitiesReachedTitle = new PdfPCell();
			cellCitiesReachedTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellCitiesReachedTitle.setBorder(Rectangle.BOTTOM);
			cellCitiesReachedTitle.setBorderWidthBottom(0.8f);
			cellCitiesReachedTitle.setPaddingTop(0);
			cellCitiesReachedTitle.setPaddingLeft(0);
			cellCitiesReachedTitle.setPaddingRight(0);
			cellCitiesReachedTitle.setPaddingBottom(5);
			cellCitiesReachedTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parCitiesReachedTitle = new Paragraph("CIDADES MAIS ALCANÇADAS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parCitiesReachedTitle.setAlignment(Element.ALIGN_CENTER);

			cellCitiesReachedTitle.addElement(parCitiesReachedTitle);

			tableCitiesReached.addCell(cellCitiesReachedTitle);
			
			// Cities reached Table.
			PdfPCell cellCitiesReachedTable = new PdfPCell();
			cellCitiesReachedTable.setPadding(0);
			cellCitiesReachedTable.setBorder(PdfPCell.NO_BORDER);

			PdfPTable tableCitiesReachedTableHeader = new PdfPTable(3);
			tableCitiesReachedTableHeader.setTotalWidth(new float[]{15, 115, 40});
			tableCitiesReachedTableHeader.setLockedWidth(true);
			tableCitiesReachedTableHeader.setSpacingBefore(5);
			tableCitiesReachedTableHeader.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableCitiesReachedTableHeader.getDefaultCell().setBorderWidthBottom(0.8f);
			tableCitiesReachedTableHeader.getDefaultCell().setBorderColorBottom(new BaseColor(227, 227, 228));
			tableCitiesReachedTableHeader.getDefaultCell().setBackgroundColor(new BaseColor(242, 242, 242));
			tableCitiesReachedTableHeader.getDefaultCell().setPadding(3);
			
			tableCitiesReachedTableHeader.addCell(new Phrase(new Chunk("#", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableCitiesReachedTableHeader.addCell(new Phrase(new Chunk("Cidade", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableCitiesReachedTableHeader.addCell(new Phrase(new Chunk("Usuários", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			
			cellCitiesReachedTable.addElement(tableCitiesReachedTableHeader);
			
			PdfPTable tableCitiesReachedTableRows = new PdfPTable(3);
			tableCitiesReachedTableRows.setTotalWidth(new float[]{15, 115, 40});
			tableCitiesReachedTableRows.setLockedWidth(true);
			tableCitiesReachedTableRows.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableCitiesReachedTableRows.getDefaultCell().setBorderWidthBottom(0.8f);
			tableCitiesReachedTableRows.getDefaultCell().setBorderColorBottom(new BaseColor(239, 239, 240));
			tableCitiesReachedTableRows.getDefaultCell().setPadding(3);
			
//			for(int i = 0; i < reportFacebookHistorical.getCitiesMoreReached().size(); i++) {
//				
//				tableCitiesReachedTableRows.addCell(new Phrase(new Chunk(String.valueOf(i + 1), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//				tableCitiesReachedTableRows.addCell(new Phrase(new Chunk(reportFacebookHistorical.getCitiesMoreReached().get(i).getCity(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//				tableCitiesReachedTableRows.addCell(new Phrase(new Chunk(reportFacebookHistorical.getCitiesMoreReached().get(i).getValue().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
//				
//			}
			
			cellCitiesReachedTable.addElement(tableCitiesReachedTableRows);
			
			tableCitiesReached.addCell(cellCitiesReachedTable);
			
			cellCitiesReached.addElement(tableCitiesReached);
			
			tableCitiesStats.addCell(cellCitiesReached);
			
			document.add(tableCitiesStats);

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	private void addInfluentialFans() {
		
		try {
			
			PdfPTable tableInfluentialFansTitle = new PdfPTable(1);
			tableInfluentialFansTitle.setTotalWidth(new float[]{523});
			tableInfluentialFansTitle.setLockedWidth(true);
			tableInfluentialFansTitle.setHorizontalAlignment(Element.ALIGN_CENTER);

			tableInfluentialFansTitle.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableInfluentialFansTitle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			tableInfluentialFansTitle.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableInfluentialFansTitle.getDefaultCell().setBorderWidthBottom(0.8f);
			tableInfluentialFansTitle.getDefaultCell().setPaddingBottom(5);
			tableInfluentialFansTitle.getDefaultCell().setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parInfluentialFansTitle = new Paragraph("FÃS MAIS INFLUENTES", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parInfluentialFansTitle.setAlignment(Element.ALIGN_LEFT);

			tableInfluentialFansTitle.addCell(parInfluentialFansTitle);

			document.add(tableInfluentialFansTitle);
			
			// Most Influential Fans.
			PdfPTable tableInfluentialFans = new PdfPTable(2);
			tableInfluentialFans.setTotalWidth(new float[]{ 190, 170});
			tableInfluentialFans.setLockedWidth(true);
			tableInfluentialFans.setSpacingBefore(8);
			
			PdfPCell cellInfluentialFansOne = new PdfPCell();
			cellInfluentialFansOne.setBorder(PdfPCell.NO_BORDER);
			cellInfluentialFansOne.setPaddingBottom(0);
			cellInfluentialFansOne.setPaddingTop(0);
			cellInfluentialFansOne.setPaddingLeft(0);
			cellInfluentialFansOne.setPaddingRight(20);

			PdfPTable tableInfluentialFansOneTable = new PdfPTable(2);
			tableInfluentialFansOneTable.setTotalWidth(new float[]{19, 151});
			tableInfluentialFansOneTable.setLockedWidth(true);
			
			for (int i = 0; i < reportFacebookHistorical.getMostInfluentialFans().size(); i++) {
				
				if(i == 50) {
					break;
				}
				
				PdfPCell cellImageFan = new PdfPCell();
				cellImageFan.setBorder(Rectangle.BOTTOM);
				cellImageFan.setBorderWidthBottom(0.8f);
				cellImageFan.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellImageFan.setPadding(3);
				cellImageFan.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				Image imgFan;
				try {
					imgFan = Image.getInstance(new URL(reportFacebookHistorical.getMostInfluentialFans().get(i).getImageProfile()));	
				} catch (Exception e) {
					imgFan = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.AVATAR));
					e.printStackTrace();
				}
				imgFan.scaleAbsolute(16, 16);
				
				cellImageFan.addElement(imgFan);
				
				PdfPCell cellFanInfo = new PdfPCell();
				cellFanInfo.setBorder(Rectangle.NO_BORDER);
				cellFanInfo.setBorderWidthBottom(0.8f);
				cellFanInfo.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellFanInfo.setPadding(0);
				cellFanInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				PdfPTable tableFanInfo = new PdfPTable(1);
				tableFanInfo.setTotalWidth(new float[]{151});
				tableFanInfo.setLockedWidth(true);

				PdfPCell cellFanName = new PdfPCell();
				cellFanName.setBorder(Rectangle.NO_BORDER);
				cellFanName.setPaddingLeft(5);
				cellFanName.setPaddingRight(0);
				cellFanName.setPaddingTop(0);
				cellFanName.setPaddingBottom(2);
				
				Phrase phrFanName = new Phrase(new Chunk((i + 1) +". " + StringUtils.abbreviate(reportFacebookHistorical.getMostInfluentialFans().get(i).getName().toUpperCase(), 28), 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65))));
				phrFanName.setLeading(8);
				
				cellFanName.addElement(phrFanName);
				
				PdfPCell cellFanStats = new PdfPCell();
				cellFanStats.setBorder(PdfPCell.NO_BORDER);
				cellFanStats.setPaddingLeft(5);
				cellFanStats.setPaddingRight(0);
				cellFanStats.setPaddingTop(0);
				cellFanStats.setPaddingBottom(0);
				
				Paragraph parFanStats = new Paragraph();
				parFanStats.setLeading(6);
				
				Image imgLikes = Image.getInstance(UtilFunctions.getResourcePath("/images/reports/historical/reports_likes_inf.png"));
				imgLikes.scaleAbsolute(5, 5);
				
				Image imgComments = Image.getInstance(UtilFunctions.getResourcePath("/images/reports/historical/reports_comments_inf.png"));
				imgComments.scaleAbsolute(5, 5);
				
				parFanStats.add(new Chunk(imgLikes, 0, 0));
				parFanStats.add(new Chunk(" " + reportFacebookHistorical.getMostInfluentialFans().get(i).getLikes().toString() + "   ", new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(135, 135, 135))));
				parFanStats.add(new Chunk(imgComments, 0, 0));
				parFanStats.add(new Chunk(" " + reportFacebookHistorical.getMostInfluentialFans().get(i).getComments().toString() + "   ", new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(135, 135, 135))));
				
				cellFanStats.addElement(parFanStats);
				
				tableFanInfo.addCell(cellFanName);
				tableFanInfo.addCell(cellFanStats);
				
				cellFanInfo.addElement(tableFanInfo);
				
				tableInfluentialFansOneTable.addCell(cellImageFan);	
				tableInfluentialFansOneTable.addCell(cellFanInfo);
			}
			
			cellInfluentialFansOne.addElement(tableInfluentialFansOneTable);
			
			tableInfluentialFans.addCell(cellInfluentialFansOne);
			
			PdfPCell cellInfluentialFansSecond = new PdfPCell();
			cellInfluentialFansSecond.setBorder(PdfPCell.NO_BORDER);
			cellInfluentialFansSecond.setPadding(0);

			PdfPTable tableInfluentialFansSecondTable = new PdfPTable(2);
			tableInfluentialFansSecondTable.setTotalWidth(new float[]{19, 151});
			tableInfluentialFansSecondTable.setLockedWidth(true);
			
			if(reportFacebookHistorical.getMostInfluentialFans().size() > 50) {
				
				for (int i = 50; i < reportFacebookHistorical.getMostInfluentialFans().size(); i++) {
					
					PdfPCell cellImageFan = new PdfPCell();
					cellImageFan.setBorder(Rectangle.BOTTOM);
					cellImageFan.setBorderWidthBottom(0.8f);
					cellImageFan.setBorderColorBottom(new BaseColor(239, 239, 240));
					cellImageFan.setPadding(3);
					cellImageFan.setVerticalAlignment(Element.ALIGN_MIDDLE);
					
					Image imgFan = Image.getInstance(new URL(reportFacebookHistorical.getMostInfluentialFans().get(i).getImageProfile()));
					imgFan.scaleAbsolute(16, 16);
					
					cellImageFan.addElement(imgFan);
					
					PdfPCell cellFanInfo = new PdfPCell();
					cellFanInfo.setBorder(Rectangle.NO_BORDER);
					cellFanInfo.setBorderWidthBottom(0.8f);
					cellFanInfo.setBorderColorBottom(new BaseColor(239, 239, 240));
					cellFanInfo.setPadding(0);
					cellFanInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
					
					PdfPTable tableFanInfo = new PdfPTable(1);
					tableFanInfo.setTotalWidth(new float[]{151});
					tableFanInfo.setLockedWidth(true);

					PdfPCell cellFanName = new PdfPCell();
					cellFanName.setBorder(Rectangle.NO_BORDER);
					cellFanName.setPaddingLeft(5);
					cellFanName.setPaddingRight(0);
					cellFanName.setPaddingTop(0);
					cellFanName.setPaddingBottom(2);
					
					Phrase phrFanName = new Phrase(new Chunk((i + 1) +". " + StringUtils.abbreviate(reportFacebookHistorical.getMostInfluentialFans().get(i).getName().toUpperCase(), 28), 
							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65))));
					phrFanName.setLeading(8);
					
					cellFanName.addElement(phrFanName);
					
					PdfPCell cellFanStats = new PdfPCell();
					cellFanStats.setBorder(PdfPCell.NO_BORDER);
					cellFanStats.setPaddingLeft(5);
					cellFanStats.setPaddingRight(0);
					cellFanStats.setPaddingTop(0);
					cellFanStats.setPaddingBottom(0);
					
					Paragraph parFanStats = new Paragraph();
					parFanStats.setLeading(6);
					
					Image imgLikes = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_LIKES_INFLUENTIALS));
					imgLikes.scaleAbsolute(5, 5);
					
					Image imgComments = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_COMMENTS_INFLUENTIALS));
					imgComments.scaleAbsolute(5, 5);
					
					parFanStats.add(new Chunk(imgLikes, 0, 0));
					parFanStats.add(new Chunk(" " + reportFacebookHistorical.getMostInfluentialFans().get(i).getLikes().toString() + "   ", new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(135, 135, 135))));
					parFanStats.add(new Chunk(imgComments, 0, 0));
					parFanStats.add(new Chunk(" " + reportFacebookHistorical.getMostInfluentialFans().get(i).getComments().toString() + "   ", new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(135, 135, 135))));
					
					cellFanStats.addElement(parFanStats);
					
					tableFanInfo.addCell(cellFanName);
					tableFanInfo.addCell(cellFanStats);
					
					cellFanInfo.addElement(tableFanInfo);
					
					tableInfluentialFansSecondTable.addCell(cellImageFan);
					tableInfluentialFansSecondTable.addCell(cellFanInfo);	
				}
				
			}
			
			cellInfluentialFansSecond.addElement(tableInfluentialFansSecondTable);
			
			tableInfluentialFans.addCell(cellInfluentialFansSecond);
			
			document.add(tableInfluentialFans);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addEngagementWithContent() {
		
		try {
			
			PdfPTable tableEngagementWithContent = new PdfPTable(1);
			tableEngagementWithContent.setTotalWidth(new float[]{523});
			tableEngagementWithContent.setLockedWidth(true);
			tableEngagementWithContent.setSpacingBefore(18);

			PdfPCell cellEngagementWithContentTitle = new PdfPCell();
			cellEngagementWithContentTitle.setBorder(Rectangle.BOTTOM);
			cellEngagementWithContentTitle.setBorderWidthBottom(0.8f);
			cellEngagementWithContentTitle.setPaddingBottom(5);
			cellEngagementWithContentTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
			
			Paragraph parEngagementWithContentTitle = new Paragraph("ENVOLVIMENTO COM O CONTEÚDO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parEngagementWithContentTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellEngagementWithContentTitle.addElement(parEngagementWithContentTitle);

			tableEngagementWithContent.addCell(cellEngagementWithContentTitle);
			
			PdfPCell cellEngagementContentChart = new PdfPCell();
			cellEngagementContentChart.setBorder(PdfPCell.NO_BORDER);
			cellEngagementContentChart.setPadding(1);

			cellEngagementContentChart.addElement(addChartEngagementContent());
			
			tableEngagementWithContent.addCell(cellEngagementContentChart);
			
			// Engagement Statstics.
			PdfPCell cellEngagementWithContent = new PdfPCell();
			cellEngagementWithContent.setBorder(PdfPCell.NO_BORDER);
			cellEngagementWithContent.setPadding(0);
			cellEngagementWithContent.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPTable tableEngagementStats = new PdfPTable(2);
			tableEngagementStats.setTotalWidth(new float[]{212, 148});
			tableEngagementStats.setLockedWidth(true);
			tableEngagementStats.setSpacingBefore(15);
			
			// Engagement Actions Statstics.
			PdfPCell cellEngagementActions = new PdfPCell();
			cellEngagementActions.setBorder(Rectangle.RIGHT);
			cellEngagementActions.setBorderWidthRight(0.8f);
			cellEngagementActions.setBorderColorRight(new BaseColor(177, 177, 177));
			cellEngagementActions.setPadding(0);
			
			PdfPTable tableEngagementActions = new PdfPTable(2);
			tableEngagementActions.setTotalWidth(new float[]{106, 106});
			tableEngagementActions.setLockedWidth(true);
			
			// Likes Actions.
			PdfPCell cellLikesActions = new PdfPCell();
			cellLikesActions.setBorder(PdfPCell.NO_BORDER);
			cellLikesActions.setPaddingLeft(0);
			cellLikesActions.setPaddingRight(0);
			cellLikesActions.setPaddingTop(3);
			cellLikesActions.setPaddingBottom(0);
			
			PdfPTable tableLikesActions = new PdfPTable(2);
			tableLikesActions.setTotalWidth(new float[]{21, 85});
			tableLikesActions.setLockedWidth(true);
			
			PdfPCell cellLikesImage = new PdfPCell();
			cellLikesImage.setBorder(PdfPCell.NO_BORDER);
			cellLikesImage.setPadding(0);
			
			Image imgLikes = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_LIKES));
			imgLikes.scaleAbsolute(21, 21);
			
			cellLikesImage.addElement(imgLikes);
			
			PdfPCell cellLikesPerc = new PdfPCell();
			cellLikesPerc.setBorder(PdfPCell.NO_BORDER);
			cellLikesPerc.setPaddingLeft(3);
			cellLikesPerc.setPaddingRight(0);
			cellLikesPerc.setPaddingTop(0);
			cellLikesPerc.setPaddingBottom(0);
			
			Phrase parLikes = new Phrase("CURTIR", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parLikes.setLeading(7);
			
			Paragraph parLikesPercentage = new Paragraph(reportFacebookHistorical.getLikesPercentage() + "%", new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(159, 220, 53)));
			parLikesPercentage.setLeading(14);
			
			cellLikesPerc.addElement(parLikes);
			cellLikesPerc.addElement(parLikesPercentage);
			
			tableLikesActions.addCell(cellLikesImage);
			tableLikesActions.addCell(cellLikesPerc);
			
			cellLikesActions.addElement(tableLikesActions);
			
			// Comments.
			PdfPCell cellCommentsActions = new PdfPCell();
			cellCommentsActions.setBorder(PdfPCell.NO_BORDER);
			cellCommentsActions.setPaddingLeft(0);
			cellCommentsActions.setPaddingRight(0);
			cellCommentsActions.setPaddingTop(3);
			cellCommentsActions.setPaddingBottom(0);
			
			PdfPTable tableCommentsActions = new PdfPTable(2);
			tableCommentsActions.setTotalWidth(new float[]{21, 85});
			tableCommentsActions.setLockedWidth(true);
			
			PdfPCell cellCommentsImage = new PdfPCell();
			cellCommentsImage.setBorder(PdfPCell.NO_BORDER);
			cellCommentsImage.setPadding(0);
			
			Image imgComments = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_COMMENTARIES));
			imgComments.scaleAbsolute(21, 21);
			
			cellCommentsImage.addElement(imgComments);
			
			PdfPCell cellCommentsPerc = new PdfPCell();
			cellCommentsPerc.setBorder(PdfPCell.NO_BORDER);
			cellCommentsPerc.setPaddingLeft(3);
			cellCommentsPerc.setPaddingRight(0);
			cellCommentsPerc.setPaddingTop(0);
			cellCommentsPerc.setPaddingBottom(0);
			
			Phrase parComments = new Phrase("COMENTÁRIOS", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parComments.setLeading(7);
			
//			Paragraph parCommentsPercentage = new Paragraph(reportFacebookHistorical.getCommentsPercentage() + "%", new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(243, 105, 36)));
//			parCommentsPercentage.setLeading(14);
//			
//			cellCommentsPerc.addElement(parComments);
//			cellCommentsPerc.addElement(parCommentsPercentage);
			
			tableCommentsActions.addCell(cellCommentsImage);
			tableCommentsActions.addCell(cellCommentsPerc);
			
			cellCommentsActions.addElement(tableCommentsActions);
			
			// Shares.
			PdfPCell cellSharesActions = new PdfPCell();
			cellSharesActions.setBorder(PdfPCell.NO_BORDER);
			cellSharesActions.setPaddingLeft(0);
			cellSharesActions.setPaddingRight(0);
			cellSharesActions.setPaddingTop(14);
			cellSharesActions.setPaddingBottom(3);

			PdfPTable tableSharesActions = new PdfPTable(2);
			tableSharesActions.setTotalWidth(new float[]{21, 85});
			tableSharesActions.setLockedWidth(true);

			PdfPCell cellSharesImage = new PdfPCell();
			cellSharesImage.setBorder(PdfPCell.NO_BORDER);
			cellSharesImage.setPadding(0);

			Image imgShares = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_SHARES));
			imgShares.scaleAbsolute(21, 21);

			cellSharesImage.addElement(imgShares);

			PdfPCell cellSharesPerc = new PdfPCell();
			cellSharesPerc.setBorder(PdfPCell.NO_BORDER);
			cellSharesPerc.setPaddingLeft(3);
			cellSharesPerc.setPaddingRight(0);
			cellSharesPerc.setPaddingTop(0);
			cellSharesPerc.setPaddingBottom(0);

			Phrase parShares = new Phrase("COMPARTILHAR", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parShares.setLeading(7);

//			Paragraph parSharesPercentage = new Paragraph(reportFacebookHistorical.getSharesPercentage() + "%", new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(238, 197, 4)));
//			parSharesPercentage.setLeading(14);
//
//			cellSharesPerc.addElement(parShares);
//			cellSharesPerc.addElement(parSharesPercentage);

			tableSharesActions.addCell(cellSharesImage);
			tableSharesActions.addCell(cellSharesPerc);

			cellSharesActions.addElement(tableSharesActions);
			
			// Clicks.
			PdfPCell cellClicksActions = new PdfPCell();
			cellClicksActions.setBorder(PdfPCell.NO_BORDER);
			cellClicksActions.setPaddingLeft(0);
			cellClicksActions.setPaddingRight(0);
			cellClicksActions.setPaddingTop(14);
			cellClicksActions.setPaddingBottom(3);

			PdfPTable tableClicksActions = new PdfPTable(2);
			tableClicksActions.setTotalWidth(new float[]{21, 85});
			tableClicksActions.setLockedWidth(true);

			PdfPCell cellClicksImage = new PdfPCell();
			cellClicksImage.setBorder(PdfPCell.NO_BORDER);
			cellClicksImage.setPadding(0);

			Image imgClicks = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_CLICKS));
			imgClicks.scaleAbsolute(21, 21);

			cellClicksImage.addElement(imgClicks);

			PdfPCell cellClicksPerc = new PdfPCell();
			cellClicksPerc.setBorder(PdfPCell.NO_BORDER);
			cellClicksPerc.setPaddingLeft(3);
			cellClicksPerc.setPaddingRight(0);
			cellClicksPerc.setPaddingTop(0);
			cellClicksPerc.setPaddingBottom(0);

			Phrase parClicks = new Phrase("CLIQUES", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parClicks.setLeading(7);

//			Paragraph parClicksPercentage = new Paragraph(reportFacebookHistorical.getClicksPercentage() + "%", new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(109, 202, 225)));
//			parClicksPercentage.setLeading(14);
//
//			cellClicksPerc.addElement(parClicks);
//			cellClicksPerc.addElement(parClicksPercentage);

			tableClicksActions.addCell(cellClicksImage);
			tableClicksActions.addCell(cellClicksPerc);

			cellClicksActions.addElement(tableClicksActions);

			tableEngagementActions.addCell(cellLikesActions);
			tableEngagementActions.addCell(cellCommentsActions);
			tableEngagementActions.addCell(cellSharesActions);
			tableEngagementActions.addCell(cellClicksActions);
			
			cellEngagementActions.addElement(tableEngagementActions);
			
			// Per Month Engagement.
			PdfPCell cellEngagementMonthStats = new PdfPCell();
			cellEngagementMonthStats.setBorder(PdfPCell.NO_BORDER);
			cellEngagementMonthStats.setPaddingTop(0);
			cellEngagementMonthStats.setPaddingBottom(0);
			cellEngagementMonthStats.setPaddingRight(0);
			cellEngagementMonthStats.setPaddingLeft(18);

			PdfPTable tableEngagementMonthStats = new PdfPTable(1);
			tableEngagementMonthStats.setTotalWidth(new float[]{148});
			tableEngagementMonthStats.setLockedWidth(true);
			
			// Most Engagement Month.
			PdfPCell cellMostEngagementMonth = new PdfPCell();
			cellMostEngagementMonth.setBorder(PdfPCell.NO_BORDER);
			cellMostEngagementMonth.setPaddingLeft(0);
			cellMostEngagementMonth.setPaddingRight(0);
			cellMostEngagementMonth.setPaddingTop(3);
			cellMostEngagementMonth.setPaddingBottom(12);
			
			Paragraph parMostEngagementMonthTitle = new Paragraph("MÊS COM MAIOR ENVOLVIMENTO", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parMostEngagementMonthTitle.setLeading(7);
			
			Paragraph parMostEngagementMonth = new Paragraph();
			parMostEngagementMonth.setLeading(15);
			
			Image imgMostEngagementMonth = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_USERS));
			imgMostEngagementMonth.scaleAbsolute(10, 10);

//			parMostEngagementMonth.add(new Chunk(imgMostEngagementMonth, 0, 0));
//			parMostEngagementMonth.add(new Chunk(" " + UtilFunctions.getFormattedMonth(reportFacebookHistorical.getMostEngagementMonth().getMonth() - 1).toUpperCase(), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(69, 135, 201))));
//			
			cellMostEngagementMonth.addElement(parMostEngagementMonthTitle);
			cellMostEngagementMonth.addElement(parMostEngagementMonth);
			
			// Most Reached Month.
			PdfPCell cellMostReachedMonth = new PdfPCell();
			cellMostReachedMonth.setBorder(PdfPCell.NO_BORDER);
			cellMostReachedMonth.setPadding(0);

			Paragraph parMostReachedMonthTitle = new Paragraph("MÊS COM MAIOR ALCANCE", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parMostReachedMonthTitle.setLeading(7);

			Paragraph parMostReachedMonth = new Paragraph();
			parMostReachedMonth.setLeading(15);

			Image imgMostReachedMonth = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_REACHED_USERS));
			imgMostReachedMonth.scaleAbsolute(10, 10);

//			parMostReachedMonth.add(new Chunk(imgMostReachedMonth, 0, 0));
//			parMostReachedMonth.add(new Chunk(" " + UtilFunctions.getFormattedMonth(reportFacebookHistorical.getMostReachedMonth().getMonth() - 1).toUpperCase(), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(255, 201, 54))));
//
//			cellMostReachedMonth.addElement(parMostReachedMonthTitle);
//			cellMostReachedMonth.addElement(parMostReachedMonth);

			tableEngagementMonthStats.addCell(cellMostEngagementMonth);
			tableEngagementMonthStats.addCell(cellMostReachedMonth);
			
			cellEngagementMonthStats.addElement(tableEngagementMonthStats);
			
			tableEngagementStats.addCell(cellEngagementActions);
			tableEngagementStats.addCell(cellEngagementMonthStats);
			
			cellEngagementWithContent.addElement(tableEngagementStats);
			
			tableEngagementWithContent.addCell(cellEngagementWithContent);
			
			document.add(tableEngagementWithContent);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addPostsMostEngaged() {
		
//		try {
//			
//			document.newPage();
//			
//			PdfPTable tablePostsMostEngagedTitle = new PdfPTable(1);
//			tablePostsMostEngagedTitle.setTotalWidth(new float[]{523});
//			tablePostsMostEngagedTitle.setLockedWidth(true);
//			tablePostsMostEngagedTitle.setSpacingBefore(18);
//
//			PdfPCell cellPostsMostEngagedTitle = new PdfPCell();
//			cellPostsMostEngagedTitle.setBorder(Rectangle.BOTTOM);
//			cellPostsMostEngagedTitle.setBorderWidthBottom(0.8f);
//			cellPostsMostEngagedTitle.setPaddingBottom(5);
//			cellPostsMostEngagedTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
//			
//			Paragraph parPostsMostEngagedTitle = new Paragraph("POSTS COM MAIOR ENGAJAMENTO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
//			parPostsMostEngagedTitle.setAlignment(Element.ALIGN_CENTER);
//			
//			cellPostsMostEngagedTitle.addElement(parPostsMostEngagedTitle);
//			
//			tablePostsMostEngagedTitle.addCell(cellPostsMostEngagedTitle);
//			
//			// Cities with more fans table.
//			PdfPTable tablePostsMostEngagedTableHeader = new PdfPTable(7);
//			tablePostsMostEngagedTableHeader.setTotalWidth(new float[]{15, 149, 36, 35, 33, 42, 55});
//			tablePostsMostEngagedTableHeader.setLockedWidth(true);
//			tablePostsMostEngagedTableHeader.setSpacingBefore(5);
//			
//			PdfPCell cellPostPosition = new PdfPCell(new Phrase(new Chunk("#", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
//			cellPostPosition.setBorder(Rectangle.BOTTOM);
//			cellPostPosition.setBorderWidthBottom(0.8f);
//			cellPostPosition.setBorderColorBottom(new BaseColor(227, 227, 228));
//			cellPostPosition.setBackgroundColor(new BaseColor(217, 217, 217));
//			cellPostPosition.setPadding(3);
//			cellPostPosition.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			
//			PdfPCell cellPostContent = new PdfPCell(new Phrase(new Chunk("Post", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
//			cellPostContent.setBorder(Rectangle.BOTTOM);
//			cellPostContent.setBorderWidthBottom(0.8f);
//			cellPostContent.setBorderColorBottom(new BaseColor(227, 227, 228));
//			cellPostContent.setBackgroundColor(new BaseColor(228, 228, 228));
//			cellPostContent.setPadding(3);
//			cellPostContent.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//			
//			PdfPCell cellPostDate = new PdfPCell(new Phrase(new Chunk("Data", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
//			cellPostDate.setBorder(Rectangle.BOTTOM);
//			cellPostDate.setBorderWidthBottom(0.8f);
//			cellPostDate.setBorderColorBottom(new BaseColor(227, 227, 228));
//			cellPostDate.setBackgroundColor(new BaseColor(217, 217, 217));
//			cellPostDate.setPadding(3);
//			cellPostDate.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			
//			PdfPCell cellPostReach = new PdfPCell(new Phrase(new Chunk("Alcance", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
//			cellPostReach.setBorder(Rectangle.BOTTOM);
//			cellPostReach.setBorderWidthBottom(0.8f);
//			cellPostReach.setBorderColorBottom(new BaseColor(227, 227, 228));
//			cellPostReach.setBackgroundColor(new BaseColor(228, 228, 228));
//			cellPostReach.setPadding(3);
//			cellPostReach.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			
//			PdfPCell cellPostClicks = new PdfPCell(new Phrase(new Chunk("Cliques", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
//			cellPostClicks.setBorder(Rectangle.BOTTOM);
//			cellPostClicks.setBorderWidthBottom(0.8f);
//			cellPostClicks.setBorderColorBottom(new BaseColor(227, 227, 228));
//			cellPostClicks.setBackgroundColor(new BaseColor(217, 217, 217));
//			cellPostClicks.setPadding(3);
//			cellPostClicks.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			
//			PdfPCell cellPostStories = new PdfPCell(new Phrase(new Chunk("Interações", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
//			cellPostStories.setBorder(Rectangle.BOTTOM);
//			cellPostStories.setBorderWidthBottom(0.8f);
//			cellPostStories.setBorderColorBottom(new BaseColor(227, 227, 228));
//			cellPostStories.setBackgroundColor(new BaseColor(228, 228, 228));
//			cellPostStories.setPadding(3);
//			cellPostStories.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			
//			PdfPCell cellPostEngagement = new PdfPCell(new Phrase(new Chunk("Engajamento", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
//			cellPostEngagement.setBorder(Rectangle.BOTTOM);
//			cellPostEngagement.setBorderWidthBottom(0.8f);
//			cellPostEngagement.setBorderColorBottom(new BaseColor(227, 227, 228));
//			cellPostEngagement.setBackgroundColor(new BaseColor(217, 217, 217));
//			cellPostEngagement.setPadding(3);
//			cellPostEngagement.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			
//			tablePostsMostEngagedTableHeader.addCell(cellPostPosition);
//			tablePostsMostEngagedTableHeader.addCell(cellPostContent);
//			tablePostsMostEngagedTableHeader.addCell(cellPostDate);
//			tablePostsMostEngagedTableHeader.addCell(cellPostReach);
//			tablePostsMostEngagedTableHeader.addCell(cellPostClicks);
//			tablePostsMostEngagedTableHeader.addCell(cellPostStories);
//			tablePostsMostEngagedTableHeader.addCell(cellPostEngagement);
//
//			PdfPTable tablePostsMostEngagedTableRows = new PdfPTable(7);
//			tablePostsMostEngagedTableRows.setTotalWidth(new float[]{15, 149, 36, 35, 33, 42, 55});
//			tablePostsMostEngagedTableRows.setLockedWidth(true);
//			tablePostsMostEngagedTableRows.getDefaultCell().setBorder(Rectangle.BOTTOM);
//			tablePostsMostEngagedTableRows.getDefaultCell().setBorderWidthBottom(0.8f);
//			tablePostsMostEngagedTableRows.getDefaultCell().setBorderColorBottom(new BaseColor(239, 239, 240));
//			tablePostsMostEngagedTableRows.getDefaultCell().setPadding(3);
//
//			for(int i = 0; i < reportFacebookHistorical.getPostsMostEngaged().size(); i++) {
//				
//				PdfPCell cellPostPositionRow = new PdfPCell(new Phrase(new Chunk(String.valueOf(i+1), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//				cellPostPositionRow.setBorder(Rectangle.BOTTOM);
//				cellPostPositionRow.setBorderWidthBottom(0.8f);
//				cellPostPositionRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//				cellPostPositionRow.setBackgroundColor(new BaseColor(242, 242, 242));
//				cellPostPositionRow.setPadding(3);
//				cellPostPositionRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//				cellPostPositionRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//				
//				Phrase phrPostContentRow;
//				
//				if(reportFacebookHistorical.getPostsMostEngaged().get(i).getMessage() != null && !reportFacebookHistorical.getPostsMostEngaged().get(i).getMessage().equals("")) {
//					phrPostContentRow = new Phrase(new Chunk(StringUtils.abbreviate(reportFacebookHistorical.getPostsMostEngaged().get(i).getMessage(), 250), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134))));
//				} else if(reportFacebookHistorical.getPostsMostEngaged().get(i).getType().equals(FaceReportPostDTO.LINK_POSTED)){
//					
//					Image imgPostType = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_SEE_LINK));
//					imgPostType.scaleAbsolute(5, 5);
//					
//					phrPostContentRow = new Phrase(new Chunk(imgPostType, 0, 0));
//					phrPostContentRow.setLeading(5);
//					
//				} else if(reportFacebookHistorical.getPostsMostEngaged().get(i).getType().equals(FaceReportPostDTO.PHOTOS_POSTED)) {
//					
//					Image imgPostType = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_SEE_IMAGE));
//					imgPostType.scaleAbsolute(5, 5);
//					
//					phrPostContentRow = new Phrase(new Chunk(imgPostType, 0, 0));
//					phrPostContentRow.setLeading(5);
//					
//				} else {
//					phrPostContentRow = new Phrase("");
//				}
//				
//				PdfPCell cellPostContentRow = new PdfPCell(phrPostContentRow);
//				cellPostContentRow.setBorder(Rectangle.BOTTOM);
//				cellPostContentRow.setBorderWidthBottom(0.8f);
//				cellPostContentRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//				cellPostContentRow.setPadding(3);
//				cellPostContentRow.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//				cellPostContentRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//				
//				PdfPCell cellPostDateRow = new PdfPCell(new Phrase(new Chunk(new SimpleDateFormat("dd/MM/yy").format(reportFacebookHistorical.getPostsMostEngaged().get(i).getCreatedTime()), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//				cellPostDateRow.setBorder(Rectangle.BOTTOM);
//				cellPostDateRow.setBorderWidthBottom(0.8f);
//				cellPostDateRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//				cellPostDateRow.setBackgroundColor(new BaseColor(242, 242, 242));
//				cellPostDateRow.setPadding(3);
//				cellPostDateRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//				cellPostDateRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//				
//				PdfPCell cellPostReachRow = new PdfPCell(new Phrase(
//						new Chunk(reportFacebookHistorical.getPostsMostEngaged().get(i).getPostInsights().get(ReportFacebookPosts.POST_IMPRESSIONS_UNIQUE).toString(), 
//								new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//				cellPostReachRow.setBorder(Rectangle.BOTTOM);
//				cellPostReachRow.setBorderWidthBottom(0.8f);
//				cellPostReachRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//				cellPostReachRow.setPadding(3);
//				cellPostReachRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//				cellPostReachRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//				
//				PdfPCell cellPostClicksRow = new PdfPCell(new Phrase(
//						new Chunk(reportFacebookHistorical.getPostsMostEngaged().get(i).getPostInsights().get(ReportFacebookPosts.POST_CONSUMPTIONS).toString(), 
//								new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//				cellPostClicksRow.setBorder(Rectangle.BOTTOM);
//				cellPostClicksRow.setBorderWidthBottom(0.8f);
//				cellPostClicksRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//				cellPostClicksRow.setBackgroundColor(new BaseColor(242, 242, 242));
//				cellPostClicksRow.setPadding(3);
//				cellPostClicksRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//				cellPostClicksRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//				
//				PdfPCell cellPostStoriesRow = new PdfPCell(new Phrase(
//						new Chunk(reportFacebookHistorical.getPostsMostEngaged().get(i).getPostInsights().get(ReportFacebookPosts.POST_STORIES).toString(), 
//								new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//				cellPostStoriesRow.setBorder(Rectangle.BOTTOM);
//				cellPostStoriesRow.setBorderWidthBottom(0.8f);
//				cellPostStoriesRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//				cellPostStoriesRow.setPadding(3);
//				cellPostStoriesRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//				cellPostStoriesRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//				
//				PdfPCell cellPostEngagementRow = new PdfPCell(new Phrase(
//						new Chunk(reportFacebookHistorical.getPostsMostEngaged().get(i).getEngagement().toString() + "%",
//								new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
//				cellPostEngagementRow.setBorder(Rectangle.BOTTOM);
//				cellPostEngagementRow.setBorderWidthBottom(0.8f);
//				cellPostEngagementRow.setBorderColorBottom(new BaseColor(239, 239, 240));
//				cellPostEngagementRow.setBackgroundColor(new BaseColor(242, 242, 242));
//				cellPostEngagementRow.setPadding(3);
//				cellPostEngagementRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//				cellPostEngagementRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//
//				tablePostsMostEngagedTableRows.addCell(cellPostPositionRow);
//				tablePostsMostEngagedTableRows.addCell(cellPostContentRow);
//				tablePostsMostEngagedTableRows.addCell(cellPostDateRow);
//				tablePostsMostEngagedTableRows.addCell(cellPostReachRow);
//				tablePostsMostEngagedTableRows.addCell(cellPostClicksRow);
//				tablePostsMostEngagedTableRows.addCell(cellPostStoriesRow);
//				tablePostsMostEngagedTableRows.addCell(cellPostEngagementRow);
//			}
//
//			document.add(tablePostsMostEngagedTitle);
//			document.add(tablePostsMostEngagedTableHeader);
//			document.add(tablePostsMostEngagedTableRows);
//			
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	private PdfPCell addAboutYourFans() throws DocumentException, MalformedURLException, IOException {

		PdfPCell cellAboutYourFans = new PdfPCell();
		cellAboutYourFans.setBorder(PdfPCell.NO_BORDER);
		cellAboutYourFans.setPaddingBottom(0);
		cellAboutYourFans.setPaddingTop(0);
		cellAboutYourFans.setPaddingLeft(0);
		cellAboutYourFans.setPaddingRight(20);

		PdfPTable tableAboutYourFans = new PdfPTable(1);
		tableAboutYourFans.setTotalWidth(new float[]{170});
		tableAboutYourFans.setLockedWidth(true);
		tableAboutYourFans.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell cellAboutYourFansTitle = new PdfPCell();
		cellAboutYourFansTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellAboutYourFansTitle.setBorder(Rectangle.BOTTOM);
		cellAboutYourFansTitle.setBorderWidthBottom(0.8f);
		cellAboutYourFansTitle.setPaddingTop(0);
		cellAboutYourFansTitle.setPaddingLeft(0);
		cellAboutYourFansTitle.setPaddingRight(0);
		cellAboutYourFansTitle.setPaddingBottom(5);
		cellAboutYourFansTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

		Paragraph parAboutYourFansTitle = new Paragraph("SOBRE SEUS FÃS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
		parAboutYourFansTitle.setAlignment(Element.ALIGN_CENTER);

		cellAboutYourFansTitle.addElement(parAboutYourFansTitle);

		tableAboutYourFans.addCell(cellAboutYourFansTitle);

		PdfPCell cellChartAboutYourFans = new PdfPCell();
		cellChartAboutYourFans.setBorder(PdfPCell.NO_BORDER);
		cellChartAboutYourFans.setPadding(1);

		cellChartAboutYourFans.addElement(addChartAboutYourFans());

		tableAboutYourFans.addCell(cellChartAboutYourFans);

		// Major Age Group Fans.
		PdfPCell cellMajorAgeGroupFansTitle = new PdfPCell();
		cellMajorAgeGroupFansTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellMajorAgeGroupFansTitle.setBorder(PdfPCell.NO_BORDER);
		cellMajorAgeGroupFansTitle.setPaddingTop(13);
		cellMajorAgeGroupFansTitle.setPaddingLeft(0);
		cellMajorAgeGroupFansTitle.setPaddingRight(0);
		cellMajorAgeGroupFansTitle.setPaddingBottom(0);

		Paragraph parMajorAgeGroupFansTitle = new Paragraph("MAIOR FAIXA ETÁRIA DA FANBASE", new Font(sourceSansPro, 10, Font.NORMAL, new BaseColor(134, 134, 134)));
		parMajorAgeGroupFansTitle.setAlignment(Element.ALIGN_CENTER);
		parMajorAgeGroupFansTitle.setLeading(9);

		cellMajorAgeGroupFansTitle.addElement(parMajorAgeGroupFansTitle);

		tableAboutYourFans.addCell(cellMajorAgeGroupFansTitle);

		PdfPCell cellMajorAgeGroupFans = new PdfPCell();
		cellMajorAgeGroupFans.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellMajorAgeGroupFans.setBorder(PdfPCell.NO_BORDER);
		cellMajorAgeGroupFans.setPaddingTop(0);
		cellMajorAgeGroupFans.setPaddingLeft(0);
		cellMajorAgeGroupFans.setPaddingRight(0);
		cellMajorAgeGroupFans.setPaddingBottom(2);

		Paragraph parMajorAgeGroupFans = new Paragraph(reportFacebookHistorical.getMajorAgeGroupFans().getInitialAgeGroup() + "-" + 
				reportFacebookHistorical.getMajorAgeGroupFans().getEndAgeGroup(), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(231, 151, 25)));
		parMajorAgeGroupFans.setAlignment(Element.ALIGN_CENTER);
		parMajorAgeGroupFans.setLeading(15);

		cellMajorAgeGroupFans.addElement(parMajorAgeGroupFans);

		tableAboutYourFans.addCell(cellMajorAgeGroupFans);

		PdfPCell cellMajorAgeGroupFansDesc = new PdfPCell();
		cellMajorAgeGroupFansDesc.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellMajorAgeGroupFansDesc.setBorder(PdfPCell.NO_BORDER);
		cellMajorAgeGroupFansDesc.setPaddingTop(0);
		cellMajorAgeGroupFansDesc.setPaddingLeft(0);
		cellMajorAgeGroupFansDesc.setPaddingRight(0);
		cellMajorAgeGroupFansDesc.setPaddingBottom(0);

		Paragraph parMajorAgeGroupFansDescFirst = new Paragraph("A maior parte da sua fanbase está entre", new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
		parMajorAgeGroupFansDescFirst.setAlignment(Element.ALIGN_CENTER);
		parMajorAgeGroupFansDescFirst.setLeading(7);

		Paragraph parMajorAgeGroupFansDescSecond = new Paragraph(reportFacebookHistorical.getMajorAgeGroupFans().getInitialAgeGroup() + 
				" e " + reportFacebookHistorical.getMajorAgeGroupFans().getEndAgeGroup() + " anos, " + 
				"representando " + reportFacebookHistorical.getMajorAgeGroupFans().getPercentage() + "% do total.", new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
		parMajorAgeGroupFansDescSecond.setAlignment(Element.ALIGN_CENTER);
		parMajorAgeGroupFansDescSecond.setLeading(7);

		cellMajorAgeGroupFansDesc.addElement(parMajorAgeGroupFansDescFirst);
		cellMajorAgeGroupFansDesc.addElement(parMajorAgeGroupFansDescSecond);

		tableAboutYourFans.addCell(cellMajorAgeGroupFansDesc);

		// Men Women Fans.
		PdfPCell cellMenWomenFansTitle = new PdfPCell();
		cellMenWomenFansTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellMenWomenFansTitle.setBorder(PdfPCell.NO_BORDER);
		cellMenWomenFansTitle.setPaddingTop(13);
		cellMenWomenFansTitle.setPaddingLeft(0);
		cellMenWomenFansTitle.setPaddingRight(0);
		cellMenWomenFansTitle.setPaddingBottom(5);

		Paragraph parMenWomenFansTitle = new Paragraph("DIVISÃO ENTRE HOMENS E MULHERES", new Font(sourceSansPro, 10, Font.NORMAL, new BaseColor(134, 134, 134)));
		parMenWomenFansTitle.setAlignment(Element.ALIGN_CENTER);
		parMenWomenFansTitle.setLeading(9);

		cellMenWomenFansTitle.addElement(parMenWomenFansTitle);

		tableAboutYourFans.addCell(cellMenWomenFansTitle);

		PdfPCell cellMenWomenFans = new PdfPCell();
		cellMenWomenFans.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellMenWomenFans.setBorder(PdfPCell.NO_BORDER);
		cellMenWomenFans.setPaddingTop(0);
		cellMenWomenFans.setPaddingLeft(20);
		cellMenWomenFans.setPaddingRight(0);
		cellMenWomenFans.setPaddingBottom(0);

		Paragraph parMenWomenFans = new Paragraph();
		parMenWomenFans.setLeading(24);

		Image imgMenFans = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_MEN_FANS));
		imgMenFans.scaleAbsolute(8, 24);

		parMenWomenFans.add(new Chunk(imgMenFans, 0, 0));

		Chunk chkMenFansPerc = new Chunk(" " + reportFacebookHistorical.getMenFansPerc() + "%",
				new Font(sourceSansPro, 25, Font.BOLD, new BaseColor(62, 179, 209)));
		chkMenFansPerc.setTextRise(3);

		parMenWomenFans.add(chkMenFansPerc);

		Chunk chkWomenFansPerc = new Chunk("    " + reportFacebookHistorical.getWomenFansPerc() + "% ",
				new Font(sourceSansPro, 25, Font.BOLD, new BaseColor(226, 43, 79)));
		chkWomenFansPerc.setTextRise(3);

		parMenWomenFans.add(chkWomenFansPerc);

		Image imgWomenFans = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_WOMEN_FANS));
		imgWomenFans.scaleAbsolute(12, 24);

		parMenWomenFans.add(new Chunk(imgWomenFans, 0, 0));

		cellMenWomenFans.addElement(parMenWomenFans);

		tableAboutYourFans.addCell(cellMenWomenFans);

		cellAboutYourFans.addElement(tableAboutYourFans);

		return cellAboutYourFans;
	}

	private PdfPCell addAboutReachedUsers() throws DocumentException, MalformedURLException, IOException {

		PdfPCell cellAboutReachedUsers = new PdfPCell();
		cellAboutReachedUsers.setBorder(PdfPCell.NO_BORDER);
		cellAboutReachedUsers.setPadding(0);

		PdfPTable tableAboutReachedUsers = new PdfPTable(1);
		tableAboutReachedUsers.setTotalWidth(new float[]{170});
		tableAboutReachedUsers.setLockedWidth(true);
		tableAboutReachedUsers.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell cellAboutReachedUsersTitle = new PdfPCell();
		cellAboutReachedUsersTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellAboutReachedUsersTitle.setBorder(Rectangle.BOTTOM);
		cellAboutReachedUsersTitle.setBorderWidthBottom(0.8f);
		cellAboutReachedUsersTitle.setPaddingTop(0);
		cellAboutReachedUsersTitle.setPaddingLeft(0);
		cellAboutReachedUsersTitle.setPaddingRight(0);
		cellAboutReachedUsersTitle.setPaddingBottom(5);
		cellAboutReachedUsersTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

		Paragraph parAboutReachedUsersTitle = new Paragraph("SOBRE USUÁRIOS ALCANÇADOS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
		parAboutReachedUsersTitle.setAlignment(Element.ALIGN_CENTER);

		cellAboutReachedUsersTitle.addElement(parAboutReachedUsersTitle);

		tableAboutReachedUsers.addCell(cellAboutReachedUsersTitle);

		PdfPCell cellChartAboutReachedUsers = new PdfPCell();
		cellChartAboutReachedUsers.setBorder(PdfPCell.NO_BORDER);
		cellChartAboutReachedUsers.setPadding(1);

		cellChartAboutReachedUsers.addElement(addChartAboutReachedUsers());

		tableAboutReachedUsers.addCell(cellChartAboutReachedUsers);

		// Major Age Group Users.
		PdfPCell cellMajorAgeGroupUsersTitle = new PdfPCell();
		cellMajorAgeGroupUsersTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellMajorAgeGroupUsersTitle.setBorder(PdfPCell.NO_BORDER);
		cellMajorAgeGroupUsersTitle.setPaddingTop(13);
		cellMajorAgeGroupUsersTitle.setPaddingLeft(0);
		cellMajorAgeGroupUsersTitle.setPaddingRight(0);
		cellMajorAgeGroupUsersTitle.setPaddingBottom(0);

		Paragraph parMajorAgeGroupUsersTitle = new Paragraph("MAIOR FAIXA ETÁRIA DO PÚBLICO", new Font(sourceSansPro, 10, Font.NORMAL, new BaseColor(134, 134, 134)));
		parMajorAgeGroupUsersTitle.setAlignment(Element.ALIGN_CENTER);
		parMajorAgeGroupUsersTitle.setLeading(9);

		cellMajorAgeGroupUsersTitle.addElement(parMajorAgeGroupUsersTitle);

		tableAboutReachedUsers.addCell(cellMajorAgeGroupUsersTitle);

		PdfPCell cellMajorAgeGroupUsers = new PdfPCell();
		cellMajorAgeGroupUsers.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellMajorAgeGroupUsers.setBorder(PdfPCell.NO_BORDER);
		cellMajorAgeGroupUsers.setPaddingTop(0);
		cellMajorAgeGroupUsers.setPaddingLeft(0);
		cellMajorAgeGroupUsers.setPaddingRight(0);
		cellMajorAgeGroupUsers.setPaddingBottom(2);

//		Paragraph parMajorAgeGroupUsers = new Paragraph(reportFacebookHistorical.getMajorAgeGroupReacheUsers().getInitialAgeGroup() + "-" +
//				reportFacebookHistorical.getMajorAgeGroupReacheUsers().getEndAgeGroup(), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(231, 151, 25)));
//		parMajorAgeGroupUsers.setAlignment(Element.ALIGN_CENTER);
//		parMajorAgeGroupUsers.setLeading(15);
//
//		cellMajorAgeGroupUsers.addElement(parMajorAgeGroupUsers);

		tableAboutReachedUsers.addCell(cellMajorAgeGroupUsers);

		PdfPCell cellMajorAgeGroupUsersDesc = new PdfPCell();
		cellMajorAgeGroupUsersDesc.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellMajorAgeGroupUsersDesc.setBorder(PdfPCell.NO_BORDER);
		cellMajorAgeGroupUsersDesc.setPaddingTop(0);
		cellMajorAgeGroupUsersDesc.setPaddingLeft(0);
		cellMajorAgeGroupUsersDesc.setPaddingRight(0);
		cellMajorAgeGroupUsersDesc.setPaddingBottom(0);

		Paragraph parMajorAgeGroupUsersDescFirst = new Paragraph("A maior parte do público alcançado está entre", new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
		parMajorAgeGroupUsersDescFirst.setAlignment(Element.ALIGN_CENTER);
		parMajorAgeGroupUsersDescFirst.setLeading(7);
//
//		Paragraph parMajorAgeGroupUsersDescSecond = new Paragraph(reportFacebookHistorical.getMajorAgeGroupReacheUsers().getInitialAgeGroup() + " e " +
//				reportFacebookHistorical.getMajorAgeGroupReacheUsers().getEndAgeGroup() + " anos, representando " + 
//				reportFacebookHistorical.getMajorAgeGroupReacheUsers().getPercentage() + "% do total.", new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
//		parMajorAgeGroupUsersDescSecond.setAlignment(Element.ALIGN_CENTER);
//		parMajorAgeGroupUsersDescSecond.setLeading(7);
//
//		cellMajorAgeGroupUsersDesc.addElement(parMajorAgeGroupUsersDescFirst);
//		cellMajorAgeGroupUsersDesc.addElement(parMajorAgeGroupUsersDescSecond);

		tableAboutReachedUsers.addCell(cellMajorAgeGroupUsersDesc);

		// Men Women Users.
		PdfPCell cellMenWomenUsersTitle = new PdfPCell();
		cellMenWomenUsersTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellMenWomenUsersTitle.setBorder(PdfPCell.NO_BORDER);
		cellMenWomenUsersTitle.setPaddingTop(13);
		cellMenWomenUsersTitle.setPaddingLeft(0);
		cellMenWomenUsersTitle.setPaddingRight(0);
		cellMenWomenUsersTitle.setPaddingBottom(5);

		Paragraph parMenWomenUsersTitle = new Paragraph("DIVISÃO ENTRE HOMENS E MULHERES", new Font(sourceSansPro, 10, Font.NORMAL, new BaseColor(134, 134, 134)));
		parMenWomenUsersTitle.setAlignment(Element.ALIGN_CENTER);
		parMenWomenUsersTitle.setLeading(9);

		cellMenWomenUsersTitle.addElement(parMenWomenUsersTitle);

		tableAboutReachedUsers.addCell(cellMenWomenUsersTitle);

		PdfPCell cellMenWomenUsers = new PdfPCell();
		cellMenWomenUsers.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellMenWomenUsers.setBorder(PdfPCell.NO_BORDER);
		cellMenWomenUsers.setPaddingTop(0);
		cellMenWomenUsers.setPaddingLeft(20);
		cellMenWomenUsers.setPaddingRight(0);
		cellMenWomenUsers.setPaddingBottom(0);

		Paragraph parMenWomenUsers = new Paragraph();
		parMenWomenUsers.setLeading(24);

		Image imgMenUsers = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_MEN_FANS));
		imgMenUsers.scaleAbsolute(8, 24);

		parMenWomenUsers.add(new Chunk(imgMenUsers, 0, 0));

		Chunk chkMenUsersPerc = new Chunk(" " + reportFacebookHistorical.getMenReachedUsersPerc() + "%",
				new Font(sourceSansPro, 25, Font.BOLD, new BaseColor(62, 179, 209)));
		chkMenUsersPerc.setTextRise(3);

		parMenWomenUsers.add(chkMenUsersPerc);

		Chunk chkWomenUsersPerc = new Chunk("    " + reportFacebookHistorical.getWomenReachedUsersPerc() + "% ",
				new Font(sourceSansPro, 25, Font.BOLD, new BaseColor(226, 43, 79)));
		chkWomenUsersPerc.setTextRise(3);

		parMenWomenUsers.add(chkWomenUsersPerc);

		Image imgWomenUsers = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_WOMEN_FANS));
		imgWomenUsers.scaleAbsolute(12, 24);

		parMenWomenUsers.add(new Chunk(imgWomenUsers, 0, 0));

		cellMenWomenUsers.addElement(parMenWomenUsers);

		tableAboutReachedUsers.addCell(cellMenWomenUsers);

		cellAboutReachedUsers.addElement(tableAboutReachedUsers);

		return cellAboutReachedUsers;
	}

	private PdfPTable createFanPageGrowthStatsTable() {

		PdfPTable tableFanPageGrowthStats = null;

		try {
			tableFanPageGrowthStats = new PdfPTable(1);
			tableFanPageGrowthStats.setTotalWidth(new float[]{150});
			tableFanPageGrowthStats.setLockedWidth(true);
			tableFanPageGrowthStats.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFanPageGrowthStats.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableFanPageGrowthStats.setSpacingBefore(5);

			// Major and Minor Growth
			PdfPCell cellMajorMinorGrowthTitle = new PdfPCell();
			cellMajorMinorGrowthTitle.setBorder(PdfPCell.NO_BORDER);

			Paragraph parMajorMinorGrowthTitle = new Paragraph("MAIOR E MENOR CRESCIMENTO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parMajorMinorGrowthTitle.setLeading(9);

			cellMajorMinorGrowthTitle.addElement(parMajorMinorGrowthTitle);

			tableFanPageGrowthStats.addCell(cellMajorMinorGrowthTitle);

			PdfPCell cellMajorGrowth = new PdfPCell();
			cellMajorGrowth.setBorder(PdfPCell.NO_BORDER);

			Paragraph parMajorGrowth = new Paragraph();
			parMajorGrowth.setLeading(11);

			Image imgMajorGrowth = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_MAJOR_GROWTH));
			imgMajorGrowth.scaleAbsolute(10, 10);

			parMajorGrowth.add(new Chunk(imgMajorGrowth, 0, 0));

			parMajorGrowth.add(new Chunk(" " + OpSocialBackApplication.utilFunctionsService.getFormattedMonth(reportFacebookHistorical.getMajorFanPageGrowth().getMonth() - 1).toUpperCase(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(159,220,53))));

			cellMajorGrowth.addElement(parMajorGrowth);

			tableFanPageGrowthStats.addCell(cellMajorGrowth);

			PdfPCell cellMajorGrowthDescription = new PdfPCell();
			cellMajorGrowthDescription.setBorder(PdfPCell.NO_BORDER);

			Paragraph parMajorGrowthDescription = new Paragraph();
			parMajorGrowthDescription.setLeading(7);

			parMajorGrowthDescription.add(OpSocialBackApplication.utilFunctionsService.getFormattedMonth(reportFacebookHistorical.getMajorFanPageGrowth().getMonth() - 1) + 
					" de " + reportFacebookHistorical.getMajorFanPageGrowth().getYear() + " foi o mês com o maior índice de crescimento, " + 
					"com " + reportFacebookHistorical.getMajorFanPageGrowth().getGrowthRate() + "% de aumento na fanbase.");
			parMajorGrowthDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellMajorGrowthDescription.addElement(parMajorGrowthDescription);

			tableFanPageGrowthStats.addCell(cellMajorGrowthDescription);

			PdfPCell cellMinorGrowth = new PdfPCell();
			cellMinorGrowth.setBorder(PdfPCell.NO_BORDER);
			cellMinorGrowth.setPaddingTop(5);

			Paragraph parMinorGrowth = new Paragraph();
			parMinorGrowth.setLeading(11);

			Image imgMinorGrowth = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_MINOR_GROWTH));
			imgMinorGrowth.scaleAbsolute(10, 10);

			parMinorGrowth.add(new Chunk(imgMinorGrowth, 0, 0));

			parMinorGrowth.add(new Chunk(" " + OpSocialBackApplication.utilFunctionsService.getFormattedMonth(reportFacebookHistorical.getMinorFanPageGrowth().getMonth() - 1).toUpperCase(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(209,30,30))));

			cellMinorGrowth.addElement(parMinorGrowth);

			tableFanPageGrowthStats.addCell(cellMinorGrowth);

			PdfPCell cellMinorGrowthDescription = new PdfPCell();
			cellMinorGrowthDescription.setBorder(PdfPCell.NO_BORDER);

			Paragraph parMinorGrowthDescription = new Paragraph();
			parMinorGrowthDescription.setLeading(7);

			parMinorGrowthDescription.add(OpSocialBackApplication.utilFunctionsService.getFormattedMonth(reportFacebookHistorical.getMinorFanPageGrowth().getMonth() - 1) + 
					" de " + reportFacebookHistorical.getMinorFanPageGrowth().getYear() + " foi o mês com o menor índice de crescimento, " + 
					"com " + reportFacebookHistorical.getMinorFanPageGrowth().getGrowthRate() + "% de aumento na fanbase.");
			parMinorGrowthDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellMinorGrowthDescription.addElement(parMinorGrowthDescription);

			tableFanPageGrowthStats.addCell(cellMinorGrowthDescription);

			// Growth Origin.
			PdfPCell cellGrowthOriginTitle = new PdfPCell();
			cellGrowthOriginTitle.setBorder(PdfPCell.NO_BORDER);
			cellGrowthOriginTitle.setPaddingTop(10);

			Paragraph parGrowthOriginTitle = new Paragraph("ORIGEM DO CRESCIMENTO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parGrowthOriginTitle.setLeading(9);

			cellGrowthOriginTitle.addElement(parGrowthOriginTitle);

			tableFanPageGrowthStats.addCell(cellGrowthOriginTitle);

			// Organic Growth Origin.
			PdfPTable tableOrganicGrowthOrigin = new PdfPTable(2);
			tableOrganicGrowthOrigin.setTotalWidth(new float[]{95, 55});
			tableOrganicGrowthOrigin.setLockedWidth(true);
			tableOrganicGrowthOrigin.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellOrganicGrowthOrigin = new PdfPCell();
			cellOrganicGrowthOrigin.setBorder(PdfPCell.NO_BORDER);

			Paragraph parOrganicGrowthOrigin = new Paragraph();
			parOrganicGrowthOrigin.setLeading(11);

			Image imgOrganicGrowthOrigin = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_ORGANIC_GROWTH));
			imgOrganicGrowthOrigin.scaleAbsolute(10, 10);

			parOrganicGrowthOrigin.add(new Chunk(imgOrganicGrowthOrigin, 0, 0));

			parOrganicGrowthOrigin.add(new Chunk(" ORGÂNICO",
					new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(53, 187, 221))));

			cellOrganicGrowthOrigin.addElement(parOrganicGrowthOrigin);

			PdfPCell cellOrganicGrowthOriginPerc = new PdfPCell();
			cellOrganicGrowthOriginPerc.setBorder(PdfPCell.NO_BORDER);
			cellOrganicGrowthOriginPerc.setHorizontalAlignment(Element.ALIGN_RIGHT);
//
//			Paragraph parOrganicGrowthOriginPerc = new Paragraph(reportFacebookHistorical.getOrganicGrowthOrigin() + "%", new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(53,187,221)));
//			parOrganicGrowthOriginPerc.setLeading(11);
//			parOrganicGrowthOriginPerc.setAlignment(Element.ALIGN_RIGHT);
//
//			cellOrganicGrowthOriginPerc.addElement(parOrganicGrowthOriginPerc);

			tableOrganicGrowthOrigin.addCell(cellOrganicGrowthOrigin);
			tableOrganicGrowthOrigin.addCell(cellOrganicGrowthOriginPerc);

			tableFanPageGrowthStats.addCell(tableOrganicGrowthOrigin);

			// Paid Growth Origin.
			PdfPTable tablePaidGrowthOrigin = new PdfPTable(2);
			tablePaidGrowthOrigin.setTotalWidth(new float[]{95, 55});
			tablePaidGrowthOrigin.setLockedWidth(true);
			tablePaidGrowthOrigin.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellPaidGrowthOrigin = new PdfPCell();
			cellPaidGrowthOrigin.setBorder(PdfPCell.NO_BORDER);
			cellPaidGrowthOrigin.setPaddingTop(0);

			Paragraph parPaidGrowthOrigin = new Paragraph();
			parPaidGrowthOrigin.setLeading(11);

			Image imgPaidGrowthOrigin = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_PAID_GROWTH));
			imgPaidGrowthOrigin.scaleAbsolute(10, 10);

			parPaidGrowthOrigin.add(new Chunk(imgPaidGrowthOrigin, 0, 0));

			parPaidGrowthOrigin.add(new Chunk(" PAGO",
					new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(135, 216, 236))));

			cellPaidGrowthOrigin.addElement(parPaidGrowthOrigin);

			PdfPCell cellPaidGrowthOriginPerc = new PdfPCell();
			cellPaidGrowthOriginPerc.setBorder(PdfPCell.NO_BORDER);
			cellPaidGrowthOriginPerc.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellPaidGrowthOriginPerc.setPaddingTop(0);

//			Paragraph parPaidGrowthOriginPerc = new Paragraph(reportFacebookHistorical.getPaidGrowthOrigin() + "%", new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(135, 216, 236)));
//			parPaidGrowthOriginPerc.setLeading(11);
//			parPaidGrowthOriginPerc.setAlignment(Element.ALIGN_RIGHT);
//
//			cellPaidGrowthOriginPerc.addElement(parPaidGrowthOriginPerc);

			tablePaidGrowthOrigin.addCell(cellPaidGrowthOrigin);
			tablePaidGrowthOrigin.addCell(cellPaidGrowthOriginPerc);

			tableFanPageGrowthStats.addCell(tablePaidGrowthOrigin);

		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tableFanPageGrowthStats;
	}

	private Image addChartAboutReachedUsers() {

		String img = chartsImages.get("chartAboutReachedUsers").substring(chartsImages.get("chartAboutReachedUsers").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(137);
			imageChart.scaleAbsoluteWidth(165);

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
		
		String img = chartsImages.get("chartEngagementContent").substring(chartsImages.get("chartEngagementContent").indexOf("base64,") + 7);

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

	private Image addChartAboutYourFans() {

		String img = chartsImages.get("chartAboutYourFans").substring(chartsImages.get("chartAboutYourFans").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(137);
			imageChart.scaleAbsoluteWidth(165);

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

		String img = chartsImages.get("chartFanPageGrowth").substring(chartsImages.get("chartFanPageGrowth").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(114);
			imageChart.scaleAbsoluteWidth(208);

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
		
		HeaderFooterPDF headerFooterPDF = new HeaderFooterPDF(pdfWritter, "Relatório Facebook Histórico", (ReportLogoDTO) profile.getReportLogo());
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
