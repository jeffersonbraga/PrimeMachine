package br.com.opsocial.server.utils.monitorings.pdf;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.SyncFailedException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

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
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.application.MonitoringDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringReportDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringTagReportDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringTermReportDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.pdf.HeaderFooterPDF;
import br.com.opsocial.server.utils.pdf.ProgressBar;
import br.com.opsocial.server.utils.reports.ReportIcons;

public class MonitoringReportPDF {

	private MonitoringReportDTO monitoringReport;
	private MonitoringDTO monitoring;

	private PdfWriter pdfWritter;
	private Document document;
	private String fileName;

	private HashMap<String, String> chartsImages;
	private java.util.List<File> imagesTemp;

	private static BaseFont sourceSansPro;

	private static final BaseColor BASE_COLOR_HEADER_TITLE = new BaseColor(52, 98, 144);
	
	public MonitoringReportPDF() {
	}

	public MonitoringReportPDF(MonitoringReportDTO monitoringReport, MonitoringDTO monitoring, HashMap<String, String> chartsImages) {

		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".pdf");
		this.monitoringReport = monitoringReport;
		this.monitoring = monitoring;
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
		addReportOfNetworks();
		addReportOverTime();
		addReportOverDays();
		addReportOfInteractions();
		addReportUsersQualifications();
		addReportPostsUsers();
		addPostsCities();
		addMonitoringTerms();
		addMostInfluentUsers();
		addTagsPerNetwork();
		addTopHashTags();
		addTopLinks();
		addTagsCloud();
		addTagsCloudByQualification();

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

			parProfile.add(new Chunk(monitoring.getName(), new Font(sourceSansPro, 13, Font.NORMAL, BASE_COLOR_HEADER_TITLE)));
			parProfile.add(new Chunk("  "));

			Phrase phrReportsDate = new Phrase();

			String reportsDescription = "•  Relatório Monitoramento  -  " + 
					new SimpleDateFormat("dd/MM/yy").format(new Date(monitoringReport.getDateFrom() * 1000L))
					+" a " +
					new SimpleDateFormat("dd/MM/yy").format(new Date(monitoringReport.getDateUntil() * 1000L));

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
	
	private void addTagsCloudByQualification() {
		
		try {
			
			PdfPTable tableTagsCloud = new PdfPTable(2);
			tableTagsCloud.setTotalWidth(new float[]{210, 190});
			tableTagsCloud.setLockedWidth(true);
			tableTagsCloud.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell cellTagsPositivePosts = new PdfPCell();
			cellTagsPositivePosts.setBorder(Rectangle.NO_BORDER);
			cellTagsPositivePosts.setPaddingRight(20);
			cellTagsPositivePosts.setPaddingLeft(0);
			cellTagsPositivePosts.setPaddingTop(0);
			cellTagsPositivePosts.setPaddingBottom(0);
			
			PdfPTable tableTagsPositivePosts = new PdfPTable(1);
			tableTagsPositivePosts.setTotalWidth(new float[]{200});
			tableTagsPositivePosts.setLockedWidth(true);
			tableTagsPositivePosts.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableTagsPositivePosts.getDefaultCell().setPaddingTop(10);

			PdfPCell cellTagsPositivePostsTitle = new PdfPCell();
			cellTagsPositivePostsTitle.setBorder(Rectangle.BOTTOM);
			cellTagsPositivePostsTitle.setBorderWidthBottom(0.8f);
			cellTagsPositivePostsTitle.setPaddingBottom(5);
			cellTagsPositivePostsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parTagsPositivePostsTitle = new Paragraph("NUVEM DE TAGS POSITIVA", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parTagsPositivePostsTitle.setAlignment(Element.ALIGN_CENTER);

			cellTagsPositivePostsTitle.addElement(parTagsPositivePostsTitle);
			
			tableTagsPositivePosts.addCell(cellTagsPositivePostsTitle);
			
			List<Element> postitiveTags = HTMLWorker.parseToList(new StringReader(monitoringReport.getCloudTagsPositivePosts()), null);
			for (Element element : postitiveTags) {
				
				Paragraph parCloud = new Paragraph();
				
				for(Chunk chunk : element.getChunks()) {
					
					chunk.setFont(new Font(sourceSansPro, chunk.getFont().getSize() * 0.53F, Font.NORMAL, chunk.getFont().getColor()));
					
					parCloud.add(chunk);
				}
				
				tableTagsPositivePosts.addCell(parCloud);
			}
			
			cellTagsPositivePosts.addElement(tableTagsPositivePosts);
			
			tableTagsCloud.addCell(cellTagsPositivePosts);
			
			PdfPCell cellTagsNegativePosts = new PdfPCell();
			cellTagsNegativePosts.setBorder(Rectangle.NO_BORDER);
			cellTagsNegativePosts.setPadding(0);
			
			PdfPTable tableTagsNegativePosts = new PdfPTable(1);
			tableTagsNegativePosts.setTotalWidth(new float[]{200});
			tableTagsNegativePosts.setLockedWidth(true);
			tableTagsNegativePosts.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableTagsNegativePosts.getDefaultCell().setPaddingTop(10);

			PdfPCell cellTagsNegativePostsTitle = new PdfPCell();
			cellTagsNegativePostsTitle.setBorder(Rectangle.BOTTOM);
			cellTagsNegativePostsTitle.setBorderWidthBottom(0.8f);
			cellTagsNegativePostsTitle.setPaddingBottom(5);
			cellTagsNegativePostsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parTagsNegativePostsTitle = new Paragraph("NUVEM DE TAGS NEGATIVA", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parTagsNegativePostsTitle.setAlignment(Element.ALIGN_CENTER);

			cellTagsNegativePostsTitle.addElement(parTagsNegativePostsTitle);
			
			tableTagsNegativePosts.addCell(cellTagsNegativePostsTitle);
			
			List<Element> negativeTags = HTMLWorker.parseToList(new StringReader(monitoringReport.getCloudTagsNegativePosts()), null);
			for (Element element : negativeTags) {
				
				Paragraph parCloud = new Paragraph();
				
				for(Chunk chunk : element.getChunks()) {
					
					chunk.setFont(new Font(sourceSansPro, chunk.getFont().getSize() * 0.53F, Font.NORMAL, chunk.getFont().getColor()));
					
					parCloud.add(chunk);
				}
				
				tableTagsNegativePosts.addCell(parCloud);
			}
			
			cellTagsNegativePosts.addElement(tableTagsNegativePosts);
			
			tableTagsCloud.addCell(cellTagsNegativePosts);
			
			document.add(tableTagsCloud);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addTagsCloud() {
		
		try {
			
			document.newPage();
			
			PdfPTable tableTagsCloud = new PdfPTable(1);
			tableTagsCloud.setTotalWidth(new float[]{403});
			tableTagsCloud.setLockedWidth(true);
			tableTagsCloud.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableTagsCloud.setSpacingAfter(20);
			tableTagsCloud.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableTagsCloud.getDefaultCell().setPaddingTop(10);

			PdfPCell cellTagsCloudTitle = new PdfPCell();
			cellTagsCloudTitle.setBorder(Rectangle.BOTTOM);
			cellTagsCloudTitle.setBorderWidthBottom(0.8f);
			cellTagsCloudTitle.setPaddingBottom(5);
			cellTagsCloudTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parGeneralPostsStatsTitle = new Paragraph("PALAVRAS MAIS ENCONTRADAS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parGeneralPostsStatsTitle.setAlignment(Element.ALIGN_CENTER);

			cellTagsCloudTitle.addElement(parGeneralPostsStatsTitle);
			
			tableTagsCloud.addCell(cellTagsCloudTitle);
			
			List<Element> objects = HTMLWorker.parseToList(new StringReader(monitoringReport.getCloud()), null);
			for (Element element : objects) {
				
				Paragraph parCloud = new Paragraph();
				
				for(Chunk chunk : element.getChunks()) {
					
					chunk.setFont(new Font(sourceSansPro, chunk.getFont().getSize() * 0.53F, Font.NORMAL, chunk.getFont().getColor()));
					
					parCloud.add(chunk);
				}
				
				tableTagsCloud.addCell(parCloud);
			}
			
			document.add(tableTagsCloud);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addMostInfluentUsers() {
		
		try {
			
			PdfPTable tableMostInfluentUsersTitle = new PdfPTable(1);
			tableMostInfluentUsersTitle.setTotalWidth(new float[]{523});
			tableMostInfluentUsersTitle.setLockedWidth(true);
			tableMostInfluentUsersTitle.setHorizontalAlignment(Element.ALIGN_CENTER);

			tableMostInfluentUsersTitle.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableMostInfluentUsersTitle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			tableMostInfluentUsersTitle.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableMostInfluentUsersTitle.getDefaultCell().setBorderWidthBottom(0.8f);
			tableMostInfluentUsersTitle.getDefaultCell().setPaddingBottom(5);
			tableMostInfluentUsersTitle.getDefaultCell().setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parMostInfluentUsersTitle = new Paragraph("USUÁRIOS MAIS INFLUENTES", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parMostInfluentUsersTitle.setAlignment(Element.ALIGN_LEFT);

			tableMostInfluentUsersTitle.addCell(parMostInfluentUsersTitle);

			// Most Influent Users.
			PdfPTable tableInfluentUsers = new PdfPTable(2);
			tableInfluentUsers.setTotalWidth(new float[]{ 190, 170});
			tableInfluentUsers.setLockedWidth(true);
			tableInfluentUsers.setSpacingBefore(8);
			tableInfluentUsers.setSpacingAfter(20);
			
			PdfPCell cellInfluentUsersOne = new PdfPCell();
			cellInfluentUsersOne.setBorder(PdfPCell.NO_BORDER);
			cellInfluentUsersOne.setPaddingBottom(0);
			cellInfluentUsersOne.setPaddingTop(0);
			cellInfluentUsersOne.setPaddingLeft(0);
			cellInfluentUsersOne.setPaddingRight(20);

			PdfPTable tableInfluentUsersOneTable = new PdfPTable(2);
			tableInfluentUsersOneTable.setTotalWidth(new float[]{19, 151});
			tableInfluentUsersOneTable.setLockedWidth(true);
			
			for (int i = 0; i < monitoringReport.getMonitoringInfluentialUsers().size(); i++) {
				
				if(i == 50) {
					break;
				}
				
				PdfPCell cellImageUser = new PdfPCell();
				cellImageUser.setBorder(Rectangle.BOTTOM);
				cellImageUser.setBorderWidthBottom(0.8f);
				cellImageUser.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellImageUser.setPadding(3);
				cellImageUser.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				Image imgUser = Image.getInstance(new URL(monitoringReport.getMonitoringInfluentialUsers().get(i).getMonitoringUser().getImageProfile()));
				imgUser.scaleAbsolute(16, 16);
				
				cellImageUser.addElement(imgUser);
				
				PdfPCell cellUserInfo = new PdfPCell();
				cellUserInfo.setBorder(Rectangle.NO_BORDER);
				cellUserInfo.setBorderWidthBottom(0.8f);
				cellUserInfo.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellUserInfo.setPadding(0);
				cellUserInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				PdfPTable tableUserInfo = new PdfPTable(1);
				tableUserInfo.setTotalWidth(new float[]{151});
				tableUserInfo.setLockedWidth(true);

				PdfPCell cellUserName = new PdfPCell();
				cellUserName.setBorder(Rectangle.NO_BORDER);
				cellUserName.setPaddingLeft(5);
				cellUserName.setPaddingRight(0);
				cellUserName.setPaddingTop(0);
				cellUserName.setPaddingBottom(2);
				
				Phrase phrUserName = new Phrase(new Chunk((i + 1) +". " + monitoringReport.getMonitoringInfluentialUsers().get(i).getMonitoringUser().getName().toUpperCase(), 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65))));
				phrUserName.setLeading(8);
				
				cellUserName.addElement(phrUserName);
				
				PdfPCell cellUserStats = new PdfPCell();
				cellUserStats.setBorder(PdfPCell.NO_BORDER);
				cellUserStats.setPaddingLeft(5);
				cellUserStats.setPaddingRight(0);
				cellUserStats.setPaddingTop(0);
				cellUserStats.setPaddingBottom(0);
				
				Paragraph parUserStats = new Paragraph();
				parUserStats.setLeading(6);
				
				parUserStats.add(new Chunk("SEGUINDO", new Font(sourceSansPro, 6, Font.NORMAL, new BaseColor(16, 182, 236))));
				parUserStats.add(new Chunk(" " + monitoringReport.getMonitoringInfluentialUsers().get(i).getFriends().toString() + "   ", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(135, 135, 135))));
				parUserStats.add(new Chunk("SEGUIDORES", new Font(sourceSansPro, 6, Font.NORMAL, new BaseColor(16, 182, 236))));
				parUserStats.add(new Chunk(" " + monitoringReport.getMonitoringInfluentialUsers().get(i).getFollowers().toString() + "   ", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(135, 135, 135))));
				parUserStats.add(new Chunk("MENÇÕES", new Font(sourceSansPro, 6, Font.NORMAL, new BaseColor(16, 182, 236))));
				parUserStats.add(new Chunk(" " + monitoringReport.getMonitoringInfluentialUsers().get(i).getPostsCount().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(135, 135, 135))));
				
				cellUserStats.addElement(parUserStats);
				
				tableUserInfo.addCell(cellUserName);
				tableUserInfo.addCell(cellUserStats);
				
				cellUserInfo.addElement(tableUserInfo);
				
				tableInfluentUsersOneTable.addCell(cellImageUser);	
				tableInfluentUsersOneTable.addCell(cellUserInfo);
			}
			
			cellInfluentUsersOne.addElement(tableInfluentUsersOneTable);
			
			PdfPCell cellInfluentUsersSecond = new PdfPCell();
			cellInfluentUsersSecond.setBorder(PdfPCell.NO_BORDER);
			cellInfluentUsersSecond.setPadding(0);

			PdfPTable tableInfluentUsersSecondTable = new PdfPTable(2);
			tableInfluentUsersSecondTable.setTotalWidth(new float[]{19, 151});
			tableInfluentUsersSecondTable.setLockedWidth(true);
			
			if(monitoringReport.getMonitoringInfluentialUsers().size() > 50) {
				
				for (int i = 50; i < monitoringReport.getMonitoringInfluentialUsers().size(); i++) {
					
					PdfPCell cellImageUser = new PdfPCell();
					cellImageUser.setBorder(Rectangle.BOTTOM);
					cellImageUser.setBorderWidthBottom(0.8f);
					cellImageUser.setBorderColorBottom(new BaseColor(239, 239, 240));
					cellImageUser.setPadding(3);
					cellImageUser.setVerticalAlignment(Element.ALIGN_MIDDLE);
					
					Image imgUser = Image.getInstance(new URL(monitoringReport.getMonitoringInfluentialUsers().get(i).getMonitoringUser().getImageProfile()));
					imgUser.scaleAbsolute(16, 16);
					
					cellImageUser.addElement(imgUser);
					
					PdfPCell cellUserInfo = new PdfPCell();
					cellUserInfo.setBorder(Rectangle.NO_BORDER);
					cellUserInfo.setBorderWidthBottom(0.8f);
					cellUserInfo.setBorderColorBottom(new BaseColor(239, 239, 240));
					cellUserInfo.setPadding(0);
					cellUserInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
					
					PdfPTable tableUserInfo = new PdfPTable(1);
					tableUserInfo.setTotalWidth(new float[]{151});
					tableUserInfo.setLockedWidth(true);

					PdfPCell cellUserName = new PdfPCell();
					cellUserName.setBorder(Rectangle.NO_BORDER);
					cellUserName.setPaddingLeft(5);
					cellUserName.setPaddingRight(0);
					cellUserName.setPaddingTop(0);
					cellUserName.setPaddingBottom(2);
					
					Phrase phrUserName = new Phrase(new Chunk((i + 1) +". " + monitoringReport.getMonitoringInfluentialUsers().get(i).getMonitoringUser().getName().toUpperCase(), 
							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65))));
					phrUserName.setLeading(8);
					
					cellUserName.addElement(phrUserName);
					
					PdfPCell cellUserStats = new PdfPCell();
					cellUserStats.setBorder(PdfPCell.NO_BORDER);
					cellUserStats.setPaddingLeft(5);
					cellUserStats.setPaddingRight(0);
					cellUserStats.setPaddingTop(0);
					cellUserStats.setPaddingBottom(0);
					
					Paragraph parUserStats = new Paragraph();
					parUserStats.setLeading(6);
					
					parUserStats.add(new Chunk("SEGUINDO", new Font(sourceSansPro, 6, Font.NORMAL, new BaseColor(16, 182, 236))));
					parUserStats.add(new Chunk(" " + monitoringReport.getMonitoringInfluentialUsers().get(i).getFriends().toString() + "   ", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(135, 135, 135))));
					parUserStats.add(new Chunk("SEGUIDORES", new Font(sourceSansPro, 6, Font.NORMAL, new BaseColor(16, 182, 236))));
					parUserStats.add(new Chunk(" " + monitoringReport.getMonitoringInfluentialUsers().get(i).getFollowers().toString() + "   ", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(135, 135, 135))));
					parUserStats.add(new Chunk("MENÇÕES", new Font(sourceSansPro, 6, Font.NORMAL, new BaseColor(16, 182, 236))));
					parUserStats.add(new Chunk(" " + monitoringReport.getMonitoringInfluentialUsers().get(i).getPostsCount().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(135, 135, 135))));
					
					cellUserStats.addElement(parUserStats);
					
					tableUserInfo.addCell(cellUserName);
					tableUserInfo.addCell(cellUserStats);
					
					cellUserInfo.addElement(tableUserInfo);
					
					tableInfluentUsersSecondTable.addCell(cellImageUser);
					tableInfluentUsersSecondTable.addCell(cellUserInfo);	
				}
				
			}
			
			cellInfluentUsersSecond.addElement(tableInfluentUsersSecondTable);
			
			tableInfluentUsers.addCell(cellInfluentUsersOne);
			tableInfluentUsers.addCell(cellInfluentUsersSecond);
			
			document.add(tableMostInfluentUsersTitle);
			document.add(tableInfluentUsers);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void addTagsPerNetwork() {
		
		try {

			PdfPTable tableTagsPerNetworkTitle = new PdfPTable(1);
			tableTagsPerNetworkTitle.setTotalWidth(new float[]{523});
			tableTagsPerNetworkTitle.setLockedWidth(true);

			PdfPCell cellTagsPerNetworkTitle = new PdfPCell();
			cellTagsPerNetworkTitle.setBorder(Rectangle.BOTTOM);
			cellTagsPerNetworkTitle.setBorderWidthBottom(0.8f);
			cellTagsPerNetworkTitle.setPaddingBottom(5);
			cellTagsPerNetworkTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parTagsPerNetworkTitle = new Paragraph("TAGS MAIS MARCADAS POR REDE", 
					new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parTagsPerNetworkTitle.setAlignment(Element.ALIGN_CENTER);

			cellTagsPerNetworkTitle.addElement(parTagsPerNetworkTitle);

			tableTagsPerNetworkTitle.addCell(cellTagsPerNetworkTitle);

			PdfPTable tableTagsPerNetworkTableHeader = new PdfPTable(9);
			tableTagsPerNetworkTableHeader.setTotalWidth(new float[]{78, 36, 36, 36, 36, 36, 36, 36, 36});
			tableTagsPerNetworkTableHeader.setLockedWidth(true);
			tableTagsPerNetworkTableHeader.setSpacingBefore(5);

			PdfPCell cellTagHeader = new PdfPCell(new Phrase(new Chunk("Tag", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
			cellTagHeader.setBorder(Rectangle.BOTTOM);
			cellTagHeader.setBorderWidthBottom(0.8f);
			cellTagHeader.setBorderColorBottom(new BaseColor(188, 188, 188));
			cellTagHeader.setBackgroundColor(new BaseColor(228, 228, 227));
			cellTagHeader.setPadding(3);
			cellTagHeader.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			
			PdfPCell cellTwitterHeader = new PdfPCell();
			cellTwitterHeader.setBorder(Rectangle.BOTTOM);
			cellTwitterHeader.setBorderWidthBottom(0.8f);
			cellTwitterHeader.setBorderColorBottom(new BaseColor(188, 188, 188));
			cellTwitterHeader.setBackgroundColor(new BaseColor(228, 228, 227));
			cellTwitterHeader.setPadding(3);
			cellTwitterHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			
			Image imgTwitterLegend = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.TWITTER_ICON));
			imgTwitterLegend.scaleAbsolute(7, 7);
			imgTwitterLegend.setAlignment(Element.ALIGN_CENTER);
			
			cellTwitterHeader.addElement(imgTwitterLegend);
			
			PdfPCell cellGooglePlusHeader = new PdfPCell();
			cellGooglePlusHeader.setBorder(Rectangle.BOTTOM);
			cellGooglePlusHeader.setBorderWidthBottom(0.8f);
			cellGooglePlusHeader.setBorderColorBottom(new BaseColor(188, 188, 188));
			cellGooglePlusHeader.setBackgroundColor(new BaseColor(215, 215, 215));
			cellGooglePlusHeader.setPadding(3);
			cellGooglePlusHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			
			Image imgGooglePlusLegend = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.GOOGLEPLUS_ICON));
			imgGooglePlusLegend.scaleAbsolute(7, 7);
			imgGooglePlusLegend.setAlignment(Element.ALIGN_CENTER);
			
			cellGooglePlusHeader.addElement(imgGooglePlusLegend);
			
			PdfPCell cellYoutubeHeader = new PdfPCell();
			cellYoutubeHeader.setBorder(Rectangle.BOTTOM);
			cellYoutubeHeader.setBorderWidthBottom(0.8f);
			cellYoutubeHeader.setBorderColorBottom(new BaseColor(188, 188, 188));
			cellYoutubeHeader.setBackgroundColor(new BaseColor(228, 228, 227));
			cellYoutubeHeader.setPadding(3);
			cellYoutubeHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			
			Image imgYoutubeLegend = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.YOUTUBE_ICON));
			imgYoutubeLegend.scaleAbsolute(7, 7);
			imgYoutubeLegend.setAlignment(Element.ALIGN_CENTER);
			
			cellYoutubeHeader.addElement(imgYoutubeLegend);
			
			PdfPCell cellInstagramHeader = new PdfPCell();
			cellInstagramHeader.setBorder(Rectangle.BOTTOM);
			cellInstagramHeader.setBorderWidthBottom(0.8f);
			cellInstagramHeader.setBorderColorBottom(new BaseColor(188, 188, 188));
			cellInstagramHeader.setBackgroundColor(new BaseColor(215, 215, 215));
			cellInstagramHeader.setPadding(3);
			cellInstagramHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			
			Image imgInstagramLegend = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.INSTAGRAM_ICON));
			imgInstagramLegend.scaleAbsolute(7, 7);
			imgInstagramLegend.setAlignment(Element.ALIGN_CENTER);
			
			cellInstagramHeader.addElement(imgInstagramLegend);
			
			PdfPCell cellReclameAquiHeader = new PdfPCell();
			cellReclameAquiHeader.setBorder(Rectangle.BOTTOM);
			cellReclameAquiHeader.setBorderWidthBottom(0.8f);
			cellReclameAquiHeader.setBorderColorBottom(new BaseColor(188, 188, 188));
			cellReclameAquiHeader.setBackgroundColor(new BaseColor(228, 228, 227));
			cellReclameAquiHeader.setPadding(3);
			cellReclameAquiHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			
			Image imgReclameAquiLegend = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.RECLAMEAQUI_ICON));
			imgReclameAquiLegend.scaleAbsolute(7, 7);
			imgReclameAquiLegend.setAlignment(Element.ALIGN_CENTER);
			
			cellReclameAquiHeader.addElement(imgReclameAquiLegend);
			
			PdfPCell cellNewsPostsHeader = new PdfPCell();
			cellNewsPostsHeader.setBorder(Rectangle.BOTTOM);
			cellNewsPostsHeader.setBorderWidthBottom(0.8f);
			cellNewsPostsHeader.setBorderColorBottom(new BaseColor(188, 188, 188));
			cellNewsPostsHeader.setBackgroundColor(new BaseColor(215, 215, 215));
			cellNewsPostsHeader.setPadding(3);
			cellNewsPostsHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			
			Image imgNewsPostsLegend = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.NEWS_ICON));
			imgNewsPostsLegend.scaleAbsolute(7, 7);
			imgNewsPostsLegend.setAlignment(Element.ALIGN_CENTER);
			
			cellNewsPostsHeader.addElement(imgNewsPostsLegend);

			PdfPCell cellBlogsPostsHeader = new PdfPCell();
			cellBlogsPostsHeader.setBorder(Rectangle.BOTTOM);
			cellBlogsPostsHeader.setBorderWidthBottom(0.8f);
			cellBlogsPostsHeader.setBorderColorBottom(new BaseColor(188, 188, 188));
			cellBlogsPostsHeader.setBackgroundColor(new BaseColor(228, 228, 227));
			cellBlogsPostsHeader.setPadding(3);
			cellBlogsPostsHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			
			Image imgBlogsPostsLegend = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.BLOGS_ICON));
			imgBlogsPostsLegend.scaleAbsolute(7, 7);
			imgBlogsPostsLegend.setAlignment(Element.ALIGN_CENTER);
			
			cellBlogsPostsHeader.addElement(imgBlogsPostsLegend);
			
			PdfPCell cellTotalPostsHeader = new PdfPCell(new Phrase(new Chunk("Total", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
			cellTotalPostsHeader.setBorder(Rectangle.BOTTOM);
			cellTotalPostsHeader.setBorderWidthBottom(0.8f);
			cellTotalPostsHeader.setBorderColorBottom(new BaseColor(188, 188, 188));
			cellTotalPostsHeader.setBackgroundColor(new BaseColor(215, 215, 215));
			cellTotalPostsHeader.setPadding(3);
			cellTotalPostsHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			
			tableTagsPerNetworkTableHeader.addCell(cellTagHeader);
			tableTagsPerNetworkTableHeader.addCell(cellTwitterHeader);
			tableTagsPerNetworkTableHeader.addCell(cellGooglePlusHeader);
			tableTagsPerNetworkTableHeader.addCell(cellYoutubeHeader);
			tableTagsPerNetworkTableHeader.addCell(cellInstagramHeader);
			tableTagsPerNetworkTableHeader.addCell(cellReclameAquiHeader);
			tableTagsPerNetworkTableHeader.addCell(cellNewsPostsHeader);
			tableTagsPerNetworkTableHeader.addCell(cellBlogsPostsHeader);
			tableTagsPerNetworkTableHeader.addCell(cellTotalPostsHeader);

			PdfPTable tableTagsPerNetworkTableRows = new PdfPTable(9);
			tableTagsPerNetworkTableRows.setTotalWidth(new float[]{78, 36, 36, 36, 36, 36, 36, 36, 36});
			tableTagsPerNetworkTableRows.setLockedWidth(true);
			tableTagsPerNetworkTableRows.setSpacingAfter(20);
			
			for(MonitoringTagReportDTO monitoringTagReport : monitoringReport.getMonitoringTagReports()) {
				
				PdfPCell cellMonitoringTagRow = new PdfPCell(new Phrase(new Chunk(monitoringTagReport.getMonitoringTag().getName(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134 ,134)))));
				cellMonitoringTagRow.setBackgroundColor(new BaseColor(255, 254, 252));
				cellMonitoringTagRow.setBorder(Rectangle.BOTTOM);
				cellMonitoringTagRow.setBorderWidthBottom(0.8f);
				cellMonitoringTagRow.setBorderColor(new BaseColor(239, 239, 238));
				cellMonitoringTagRow.setPadding(3);
				cellMonitoringTagRow.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				cellMonitoringTagRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				
				PdfPCell cellTwitterPostsTaggedRow = new PdfPCell(new Phrase(
						new Chunk(monitoringTagReport.getTaggedPostsPerNetwork().containsKey(Profile.TWITTER) ? 
								monitoringTagReport.getTaggedPostsPerNetwork().get(Profile.TWITTER).toString() : "-", 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134 ,134)))));
//				cellTwitterPostsTaggedRow.setBackgroundColor(new BaseColor(255, 254, 252));
				cellTwitterPostsTaggedRow.setBackgroundColor(new BaseColor(240, 240, 240));
				cellTwitterPostsTaggedRow.setBorder(Rectangle.BOTTOM);
				cellTwitterPostsTaggedRow.setBorderWidthBottom(0.8f);
				cellTwitterPostsTaggedRow.setBorderColor(new BaseColor(239, 239, 238));
				cellTwitterPostsTaggedRow.setPadding(3);
				cellTwitterPostsTaggedRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellTwitterPostsTaggedRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				
				PdfPCell cellGooglePlusPostsTaggedRow = new PdfPCell(new Phrase(
						new Chunk(monitoringTagReport.getTaggedPostsPerNetwork().containsKey(Profile.GOOGLE) ? 
								monitoringTagReport.getTaggedPostsPerNetwork().get(Profile.GOOGLE).toString() : "-", 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134 ,134)))));
//				cellGooglePlusPostsTaggedRow.setBackgroundColor(new BaseColor(240, 240, 240));
				cellGooglePlusPostsTaggedRow.setBackgroundColor(new BaseColor(255, 254, 252));
				cellGooglePlusPostsTaggedRow.setBorder(Rectangle.BOTTOM);
				cellGooglePlusPostsTaggedRow.setBorderWidthBottom(0.8f);
				cellGooglePlusPostsTaggedRow.setBorderColor(new BaseColor(239, 239, 238));
				cellGooglePlusPostsTaggedRow.setPadding(3);
				cellGooglePlusPostsTaggedRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellGooglePlusPostsTaggedRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				
				PdfPCell cellYoutubePostsTaggedRow = new PdfPCell(new Phrase(
						new Chunk(monitoringTagReport.getTaggedPostsPerNetwork().containsKey(Profile.YOUTUBE) ? 
								monitoringTagReport.getTaggedPostsPerNetwork().get(Profile.YOUTUBE).toString() : "-", 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134 ,134)))));
//				cellYoutubePostsTaggedRow.setBackgroundColor(new BaseColor(255, 254, 252));
				cellYoutubePostsTaggedRow.setBackgroundColor(new BaseColor(240, 240, 240));
				cellYoutubePostsTaggedRow.setBorder(Rectangle.BOTTOM);
				cellYoutubePostsTaggedRow.setBorderWidthBottom(0.8f);
				cellYoutubePostsTaggedRow.setBorderColor(new BaseColor(239, 239, 238));
				cellYoutubePostsTaggedRow.setPadding(3);
				cellYoutubePostsTaggedRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellYoutubePostsTaggedRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				
				PdfPCell cellInstagramPostsTaggedRow = new PdfPCell(new Phrase(
						new Chunk(monitoringTagReport.getTaggedPostsPerNetwork().containsKey(Profile.INSTAGRAM) ? 
								monitoringTagReport.getTaggedPostsPerNetwork().get(Profile.INSTAGRAM).toString() : "-", 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134 ,134)))));
//				cellInstagramPostsTaggedRow.setBackgroundColor(new BaseColor(240, 240, 240));
				cellInstagramPostsTaggedRow.setBackgroundColor(new BaseColor(255, 254, 252));
				cellInstagramPostsTaggedRow.setBorder(Rectangle.BOTTOM);
				cellInstagramPostsTaggedRow.setBorderWidthBottom(0.8f);
				cellInstagramPostsTaggedRow.setBorderColor(new BaseColor(239, 239, 238));
				cellInstagramPostsTaggedRow.setPadding(3);
				cellInstagramPostsTaggedRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellInstagramPostsTaggedRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				
				PdfPCell cellReclameAquiPostsTaggedRow = new PdfPCell(new Phrase(
						new Chunk(monitoringTagReport.getTaggedPostsPerNetwork().containsKey(Profile.RECLAMEAQUI) ? 
								monitoringTagReport.getTaggedPostsPerNetwork().get(Profile.RECLAMEAQUI).toString() : "-", 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134 ,134)))));
//				cellReclameAquiPostsTaggedRow.setBackgroundColor(new BaseColor(255, 254, 252));
				cellReclameAquiPostsTaggedRow.setBackgroundColor(new BaseColor(240, 240, 240));
				cellReclameAquiPostsTaggedRow.setBorder(Rectangle.BOTTOM);
				cellReclameAquiPostsTaggedRow.setBorderWidthBottom(0.8f);
				cellReclameAquiPostsTaggedRow.setBorderColor(new BaseColor(239, 239, 238));
				cellReclameAquiPostsTaggedRow.setPadding(3);
				cellReclameAquiPostsTaggedRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellReclameAquiPostsTaggedRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				
				PdfPCell cellNewsPostsTaggedRow = new PdfPCell(new Phrase(
						new Chunk(monitoringTagReport.getTaggedPostsPerNetwork().containsKey(Profile.NEWS) ? 
								monitoringTagReport.getTaggedPostsPerNetwork().get(Profile.NEWS).toString() : "-", 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134 ,134)))));
//				cellNewsPostsTaggedRow.setBackgroundColor(new BaseColor(240, 240, 240));
				cellNewsPostsTaggedRow.setBackgroundColor(new BaseColor(255, 254, 252));
				cellNewsPostsTaggedRow.setBorder(Rectangle.BOTTOM);
				cellNewsPostsTaggedRow.setBorderWidthBottom(0.8f);
				cellNewsPostsTaggedRow.setBorderColor(new BaseColor(239, 239, 238));
				cellNewsPostsTaggedRow.setPadding(3);
				cellNewsPostsTaggedRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellNewsPostsTaggedRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				
				PdfPCell cellBlogsPostsTaggedRow = new PdfPCell(new Phrase(
						new Chunk(monitoringTagReport.getTaggedPostsPerNetwork().containsKey(Profile.BLOGS) ? 
								monitoringTagReport.getTaggedPostsPerNetwork().get(Profile.BLOGS).toString() : "-", 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134 ,134)))));
//				cellBlogsPostsTaggedRow.setBackgroundColor(new BaseColor(255, 254, 252));
				cellBlogsPostsTaggedRow.setBackgroundColor(new BaseColor(240, 240, 240));
				cellBlogsPostsTaggedRow.setBorder(Rectangle.BOTTOM);
				cellBlogsPostsTaggedRow.setBorderWidthBottom(0.8f);
				cellBlogsPostsTaggedRow.setBorderColor(new BaseColor(239, 239, 238));
				cellBlogsPostsTaggedRow.setPadding(3);
				cellBlogsPostsTaggedRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellBlogsPostsTaggedRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				
				PdfPCell cellTotalPostsTaggedRow = new PdfPCell(new Phrase(
						new Chunk(monitoringTagReport.getTotalTaggedPosts() > 0 
								? monitoringTagReport.getTotalTaggedPosts().toString() : "-", 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134 ,134)))));
//				cellTotalPostsTaggedRow.setBackgroundColor(new BaseColor(240, 240, 240));
				cellTotalPostsTaggedRow.setBackgroundColor(new BaseColor(255, 254, 252));
				cellTotalPostsTaggedRow.setBorder(Rectangle.BOTTOM);
				cellTotalPostsTaggedRow.setBorderWidthBottom(0.8f);
				cellTotalPostsTaggedRow.setBorderColor(new BaseColor(239, 239, 238));
				cellTotalPostsTaggedRow.setPadding(3);
				cellTotalPostsTaggedRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellTotalPostsTaggedRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				
				tableTagsPerNetworkTableRows.addCell(cellMonitoringTagRow);
				tableTagsPerNetworkTableRows.addCell(cellTwitterPostsTaggedRow);
				tableTagsPerNetworkTableRows.addCell(cellGooglePlusPostsTaggedRow);
				tableTagsPerNetworkTableRows.addCell(cellYoutubePostsTaggedRow);
				tableTagsPerNetworkTableRows.addCell(cellInstagramPostsTaggedRow);
				tableTagsPerNetworkTableRows.addCell(cellReclameAquiPostsTaggedRow);
				tableTagsPerNetworkTableRows.addCell(cellNewsPostsTaggedRow);
				tableTagsPerNetworkTableRows.addCell(cellBlogsPostsTaggedRow);
				tableTagsPerNetworkTableRows.addCell(cellTotalPostsTaggedRow);
			}

			document.add(tableTagsPerNetworkTitle);
			document.add(tableTagsPerNetworkTableHeader);
			document.add(tableTagsPerNetworkTableRows);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addMonitoringTerms() {

		try {

			PdfPTable tableMonitoringTermsTitle = new PdfPTable(1);
			tableMonitoringTermsTitle.setTotalWidth(new float[]{523});
			tableMonitoringTermsTitle.setLockedWidth(true);

			PdfPCell cellMonitoringTermsTitle = new PdfPCell();
			cellMonitoringTermsTitle.setBorder(Rectangle.BOTTOM);
			cellMonitoringTermsTitle.setBorderWidthBottom(0.8f);
			cellMonitoringTermsTitle.setPaddingBottom(5);
			cellMonitoringTermsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parMonitoringTermsTitle = new Paragraph("NÚMERO E QUALIFICAÇÃO DE POSTS POR TERMO MONITORADO", 
					new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parMonitoringTermsTitle.setAlignment(Element.ALIGN_CENTER);

			cellMonitoringTermsTitle.addElement(parMonitoringTermsTitle);

			tableMonitoringTermsTitle.addCell(cellMonitoringTermsTitle);

			PdfPTable tableMonitoringTermsTableHeader = new PdfPTable(4);
			tableMonitoringTermsTableHeader.setTotalWidth(new float[]{87, 55, 112, 112});
			tableMonitoringTermsTableHeader.setLockedWidth(true);
			tableMonitoringTermsTableHeader.setSpacingBefore(5);

			PdfPCell cellTermHeader = new PdfPCell(new Phrase(new Chunk("Termo", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
			cellTermHeader.setBorder(Rectangle.BOTTOM);
			cellTermHeader.setBorderWidthBottom(0.8f);
			cellTermHeader.setBorderColorBottom(new BaseColor(188, 188, 188));
			cellTermHeader.setBackgroundColor(new BaseColor(228, 228, 227));
			cellTermHeader.setPadding(3);
			cellTermHeader.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			PdfPCell cellPostsTotalHeader = new PdfPCell(new Phrase(new Chunk("Total de posts", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
			cellPostsTotalHeader.setBorder(Rectangle.BOTTOM);
			cellPostsTotalHeader.setBorderWidthBottom(0.8f);
			cellPostsTotalHeader.setBorderColorBottom(new BaseColor(188, 188, 188));
			cellPostsTotalHeader.setBackgroundColor(new BaseColor(215, 215, 215));
			cellPostsTotalHeader.setPadding(3);
			cellPostsTotalHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			PdfPCell cellQualifiedPostsHeader = new PdfPCell(new Phrase(new Chunk("Posts qualificados", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
			cellQualifiedPostsHeader.setBorder(Rectangle.BOTTOM);
			cellQualifiedPostsHeader.setBorderWidthBottom(0.8f);
			cellQualifiedPostsHeader.setBorderColorBottom(new BaseColor(188, 188, 188));
			cellQualifiedPostsHeader.setBackgroundColor(new BaseColor(228, 228, 227));
			cellQualifiedPostsHeader.setPadding(3);
			cellQualifiedPostsHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			PdfPCell cellQualifiedPostsPercHeader = new PdfPCell(new Phrase(new Chunk("Qualificação dos posts em %", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
			cellQualifiedPostsPercHeader.setBorder(Rectangle.BOTTOM);
			cellQualifiedPostsPercHeader.setBorderWidthBottom(0.8f);
			cellQualifiedPostsPercHeader.setBorderColorBottom(new BaseColor(188, 188, 188));
			cellQualifiedPostsPercHeader.setBackgroundColor(new BaseColor(215, 215, 215));
			cellQualifiedPostsPercHeader.setPadding(3);
			cellQualifiedPostsPercHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			tableMonitoringTermsTableHeader.addCell(cellTermHeader);
			tableMonitoringTermsTableHeader.addCell(cellPostsTotalHeader);
			tableMonitoringTermsTableHeader.addCell(cellQualifiedPostsHeader);
			tableMonitoringTermsTableHeader.addCell(cellQualifiedPostsPercHeader);

			PdfPTable tableMonitoringTermsTableRows = new PdfPTable(4);
			tableMonitoringTermsTableRows.setTotalWidth(new float[]{87, 55, 112, 112});
			tableMonitoringTermsTableRows.setLockedWidth(true);
			tableMonitoringTermsTableRows.setSpacingAfter(20);
			
			for(MonitoringTermReportDTO monitoringTermReport : monitoringReport.getMonitoringTermReports()) {
				
				PdfPCell cellMonitoringTermRow = new PdfPCell(new Phrase(new Chunk(monitoringTermReport.getTerm(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134 ,134)))));
				cellMonitoringTermRow.setBackgroundColor(new BaseColor(255, 254, 252));
				cellMonitoringTermRow.setBorder(Rectangle.BOTTOM);
				cellMonitoringTermRow.setBorderWidthBottom(0.8f);
				cellMonitoringTermRow.setBorderColor(new BaseColor(239, 239, 238));
				cellMonitoringTermRow.setPadding(3);
				cellMonitoringTermRow.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				cellMonitoringTermRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				
				PdfPCell cellPostsTotalRow = new PdfPCell(new Phrase(new Chunk(monitoringTermReport.getTotalPosts().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134 ,134)))));
				cellPostsTotalRow.setBackgroundColor(new BaseColor(240, 240, 240));
				cellPostsTotalRow.setBorder(Rectangle.BOTTOM);
				cellPostsTotalRow.setBorderWidthBottom(0.8f);
				cellPostsTotalRow.setBorderColor(new BaseColor(239, 239, 238));
				cellPostsTotalRow.setPadding(3);
				cellPostsTotalRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellPostsTotalRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				
				// Qualified Posts.
				PdfPCell cellQualifiedPostsRow = new PdfPCell();
				cellQualifiedPostsRow.setBackgroundColor(new BaseColor(255, 254, 252));
				cellQualifiedPostsRow.setBorder(Rectangle.BOTTOM);
				cellQualifiedPostsRow.setBorderWidthBottom(0.8f);
				cellQualifiedPostsRow.setBorderColor(new BaseColor(239, 239, 238));
				cellQualifiedPostsRow.setPadding(3);
				cellQualifiedPostsRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellQualifiedPostsRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

				PdfPTable tableQualifiedPostsRow = new PdfPTable(1);
				tableQualifiedPostsRow.setTotalWidth(new float[]{112});
				tableQualifiedPostsRow.setLockedWidth(true);

				PdfPCell cellQualifiedPostsByType = new PdfPCell();
				cellQualifiedPostsByType.setPadding(0);
				cellQualifiedPostsByType.setBorder(PdfPCell.NO_BORDER);
				cellQualifiedPostsByType.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

				PdfPTable tableQualifiedPostsByType = new PdfPTable(3);
				tableQualifiedPostsByType.setTotalWidth(new float[]{37, 38, 37});
				tableQualifiedPostsByType.setLockedWidth(true);

				PdfPCell cellPositivePosts = new PdfPCell();
				cellPositivePosts.setBorder(PdfPCell.NO_BORDER);
				cellPositivePosts.setPadding(0);
				cellPositivePosts.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

				Paragraph parPositivePostsValue = new Paragraph(new Chunk(monitoringTermReport.getSentimentAnalysis().containsKey('P') 
						? monitoringTermReport.getSentimentAnalysis().get('P').toString() : String.valueOf(0L), 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(111, 111, 111))));
				parPositivePostsValue.setAlignment(Paragraph.ALIGN_CENTER);
				parPositivePostsValue.setLeading(10);

				Paragraph parPositivePosts = new Paragraph(new Chunk("POSITIVO", 
						new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(171, 219, 7))));
				parPositivePosts.setAlignment(Paragraph.ALIGN_CENTER);
				parPositivePosts.setLeading(9);

				cellPositivePosts.addElement(parPositivePostsValue);
				cellPositivePosts.addElement(parPositivePosts);

				PdfPCell cellIndifferentPosts = new PdfPCell();
				cellIndifferentPosts.setBorder(PdfPCell.NO_BORDER);
				cellIndifferentPosts.setPadding(0);
				cellIndifferentPosts.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

				Paragraph parIndifferentPostsValue = new Paragraph(new Chunk(monitoringTermReport.getSentimentAnalysis().containsKey('S') 
						? monitoringTermReport.getSentimentAnalysis().get('S').toString() : String.valueOf(0L), 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(111, 111, 111))));
				parIndifferentPostsValue.setAlignment(Paragraph.ALIGN_CENTER);
				parIndifferentPostsValue.setLeading(10);

				Paragraph parIndifferentPosts = new Paragraph(new Chunk("NEUTRO", 
						new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(248, 183, 6))));
				parIndifferentPosts.setAlignment(Paragraph.ALIGN_CENTER);
				parIndifferentPosts.setLeading(9);

				cellIndifferentPosts.addElement(parIndifferentPostsValue);
				cellIndifferentPosts.addElement(parIndifferentPosts);

				PdfPCell cellNegativePosts = new PdfPCell();
				cellNegativePosts.setBorder(PdfPCell.NO_BORDER);
				cellNegativePosts.setPadding(0);
				cellNegativePosts.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

				Paragraph parNegativePostsValue = new Paragraph(new Chunk(monitoringTermReport.getSentimentAnalysis().containsKey('N') 
						? monitoringTermReport.getSentimentAnalysis().get('N').toString() : String.valueOf(0L), 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(111, 111, 111))));
				parNegativePostsValue.setAlignment(Paragraph.ALIGN_CENTER);
				parNegativePostsValue.setLeading(10);

				Paragraph parNegativePosts = new Paragraph(new Chunk("NEGATIVO", 
						new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(235, 79, 40))));
				parNegativePosts.setAlignment(Paragraph.ALIGN_CENTER);
				parNegativePosts.setLeading(9);

				cellNegativePosts.addElement(parNegativePostsValue);
				cellNegativePosts.addElement(parNegativePosts);

				tableQualifiedPostsByType.addCell(cellPositivePosts);
				tableQualifiedPostsByType.addCell(cellIndifferentPosts);
				tableQualifiedPostsByType.addCell(cellNegativePosts);

				cellQualifiedPostsByType.addElement(tableQualifiedPostsByType);

				tableQualifiedPostsRow.addCell(cellQualifiedPostsByType);

				cellQualifiedPostsRow.addElement(tableQualifiedPostsRow);
				
				// Qualified Posts Percentage.
				PdfPCell cellQualifiedPostsPercRow = new PdfPCell();
				cellQualifiedPostsPercRow.setBackgroundColor(new BaseColor(240, 240, 240));
				cellQualifiedPostsPercRow.setBorder(Rectangle.BOTTOM);
				cellQualifiedPostsPercRow.setBorderWidthBottom(0.8f);
				cellQualifiedPostsPercRow.setBorderColor(new BaseColor(239, 239, 238));
				cellQualifiedPostsPercRow.setPadding(3);
				cellQualifiedPostsPercRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellQualifiedPostsPercRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				
				PdfPTable tableQualifiedPostsPercRow = new PdfPTable(1);
				tableQualifiedPostsPercRow.setTotalWidth(new float[]{112});
				tableQualifiedPostsPercRow.setLockedWidth(true);
				
				PdfPCell cellPositivePostsPerc = new PdfPCell();
				cellPositivePostsPerc.setPadding(0);
				cellPositivePostsPerc.setBorder(PdfPCell.NO_BORDER);
				
				PdfPTable tablePositivePostsPerc = new PdfPTable(2);
				tablePositivePostsPerc.setTotalWidth(new float[]{31, 81});
				tablePositivePostsPerc.setLockedWidth(true);
				
				PdfPCell cellPositivePostsPercValue = new PdfPCell();
				cellPositivePostsPercValue.setPadding(0);
				cellPositivePostsPercValue.setBorder(PdfPCell.NO_BORDER);
				
				Integer positivePostsPerc = monitoringTermReport.getSentimentAnalysisPerc().containsKey('P') 
						? monitoringTermReport.getSentimentAnalysisPerc().get('P') : 0; 
				
				Paragraph parPositivePostsPercValue = new Paragraph(positivePostsPerc + "%", 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(111, 111, 111)));
				parPositivePostsPercValue.setAlignment(Paragraph.ALIGN_RIGHT);
				parPositivePostsPercValue.setLeading(10);
				
				cellPositivePostsPercValue.addElement(parPositivePostsPercValue);
				
				PdfPCell cellPositivePostsPercProgress = new PdfPCell();
				cellPositivePostsPercProgress.setPadding(0);
				cellPositivePostsPercProgress.setBorder(PdfPCell.NO_BORDER);
				cellPositivePostsPercProgress.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				
				Paragraph parPositivePostsPercProgress = new Paragraph(new ProgressBar(pdfWritter, 72, positivePostsPerc, new BaseColor(189, 234, 35)).createProgressBar());
				parPositivePostsPercProgress.setAlignment(Paragraph.ALIGN_LEFT);
				parPositivePostsPercProgress.setLeading(11);
				
				cellPositivePostsPercProgress.addElement(parPositivePostsPercProgress);
				
				tablePositivePostsPerc.addCell(cellPositivePostsPercValue);
				tablePositivePostsPerc.addCell(cellPositivePostsPercProgress);
				
				cellPositivePostsPerc.addElement(tablePositivePostsPerc);

				PdfPCell cellIndifferentPostsPerc = new PdfPCell();
				cellIndifferentPostsPerc.setPadding(0);
				cellIndifferentPostsPerc.setBorder(PdfPCell.NO_BORDER);
				
				PdfPTable tableIndifferentPostsPerc = new PdfPTable(2);
				tableIndifferentPostsPerc.setTotalWidth(new float[]{31, 81});
				tableIndifferentPostsPerc.setLockedWidth(true);
				
				PdfPCell cellIndifferentPostsPercValue = new PdfPCell();
				cellIndifferentPostsPercValue.setPadding(0);
				cellIndifferentPostsPercValue.setBorder(PdfPCell.NO_BORDER);
				
				Integer indifferentPostsPerc = monitoringTermReport.getSentimentAnalysisPerc().containsKey('S') 
						? monitoringTermReport.getSentimentAnalysisPerc().get('S') : 0; 
				
				Paragraph parIndifferentPostsPercValue = new Paragraph(indifferentPostsPerc + "%", 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(111, 111, 111)));
				parIndifferentPostsPercValue.setAlignment(Paragraph.ALIGN_RIGHT);
				parIndifferentPostsPercValue.setLeading(10);
				
				cellIndifferentPostsPercValue.addElement(parIndifferentPostsPercValue);
				
				PdfPCell cellIndifferentPostsPercProgress = new PdfPCell();
				cellIndifferentPostsPercProgress.setPadding(0);
				cellIndifferentPostsPercProgress.setBorder(PdfPCell.NO_BORDER);
				
				Paragraph parIndifferentPostsPercProgress = new Paragraph(new ProgressBar(pdfWritter, 72, indifferentPostsPerc, new BaseColor(255, 200, 50)).createProgressBar());
				parIndifferentPostsPercProgress.setAlignment(Paragraph.ALIGN_LEFT);
				parIndifferentPostsPercProgress.setLeading(11);
				
				cellIndifferentPostsPercProgress.addElement(parIndifferentPostsPercProgress);
				
				tableIndifferentPostsPerc.addCell(cellIndifferentPostsPercValue);
				tableIndifferentPostsPerc.addCell(cellIndifferentPostsPercProgress);
				
				cellIndifferentPostsPerc.addElement(tableIndifferentPostsPerc);
				
				PdfPCell cellNegativePostsPerc = new PdfPCell();
				cellNegativePostsPerc.setPadding(0);
				cellNegativePostsPerc.setBorder(PdfPCell.NO_BORDER);
				
				PdfPTable tableNegativePostsPerc = new PdfPTable(2);
				tableNegativePostsPerc.setTotalWidth(new float[]{31, 81});
				tableNegativePostsPerc.setLockedWidth(true);
				
				PdfPCell cellNegativePostsPercValue = new PdfPCell();
				cellNegativePostsPercValue.setPaddingBottom(3);
				cellNegativePostsPercValue.setPaddingTop(0);
				cellNegativePostsPercValue.setPaddingRight(0);
				cellNegativePostsPercValue.setPaddingLeft(0);
				cellNegativePostsPercValue.setBorder(PdfPCell.NO_BORDER);
				
				Integer negativePostsPerc = monitoringTermReport.getSentimentAnalysisPerc().containsKey('N') 
						? monitoringTermReport.getSentimentAnalysisPerc().get('N') : 0; 
				
				Paragraph parNegativePostsPercValue = new Paragraph(negativePostsPerc + "%", 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(111, 111, 111)));
				parNegativePostsPercValue.setAlignment(Paragraph.ALIGN_RIGHT);
				parNegativePostsPercValue.setLeading(10);
				
				cellNegativePostsPercValue.addElement(parNegativePostsPercValue);
				
				PdfPCell cellNegativePostsPercProgress = new PdfPCell();
				cellNegativePostsPercProgress.setPadding(0);
				cellNegativePostsPercProgress.setBorder(PdfPCell.NO_BORDER);
				
				Paragraph parNegativePostsPercProgress = new Paragraph(new ProgressBar(pdfWritter, 72, negativePostsPerc, new BaseColor(243, 49, 0)).createProgressBar());
				parNegativePostsPercProgress.setAlignment(Paragraph.ALIGN_LEFT);
				parNegativePostsPercProgress.setLeading(11);
				
				cellNegativePostsPercProgress.addElement(parNegativePostsPercProgress);
				
				tableNegativePostsPerc.addCell(cellNegativePostsPercValue);
				tableNegativePostsPerc.addCell(cellNegativePostsPercProgress);
				
				cellNegativePostsPerc.addElement(tableNegativePostsPerc);
				
				tableQualifiedPostsPercRow.addCell(cellPositivePostsPerc);
				tableQualifiedPostsPercRow.addCell(cellIndifferentPostsPerc);
				tableQualifiedPostsPercRow.addCell(cellNegativePostsPerc);
				
				cellQualifiedPostsPercRow.addElement(tableQualifiedPostsPercRow);
				
				tableMonitoringTermsTableRows.addCell(cellMonitoringTermRow);
				tableMonitoringTermsTableRows.addCell(cellPostsTotalRow);
				tableMonitoringTermsTableRows.addCell(cellQualifiedPostsRow);
				tableMonitoringTermsTableRows.addCell(cellQualifiedPostsPercRow);
			}

			document.add(tableMonitoringTermsTitle);
			document.add(tableMonitoringTermsTableHeader);
			document.add(tableMonitoringTermsTableRows);

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}
	
	private void addReportPostsUsers() {
		
		try {

			document.newPage();

			PdfPTable tableReportPostsUsersTitle = new PdfPTable(1);
			tableReportPostsUsersTitle.setTotalWidth(new float[]{523});
			tableReportPostsUsersTitle.setLockedWidth(true);
			tableReportPostsUsersTitle.setHorizontalAlignment(Element.ALIGN_CENTER);

			tableReportPostsUsersTitle.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableReportPostsUsersTitle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			tableReportPostsUsersTitle.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableReportPostsUsersTitle.getDefaultCell().setBorderWidthBottom(0.8f);
			tableReportPostsUsersTitle.getDefaultCell().setPaddingBottom(5);
			tableReportPostsUsersTitle.getDefaultCell().setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parReportPostsUsersTitle = new Paragraph("AUTORES DAS POSTAGENS POR DIA", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parReportPostsUsersTitle.setAlignment(Element.ALIGN_LEFT);

			tableReportPostsUsersTitle.addCell(parReportPostsUsersTitle);

			document.add(tableReportPostsUsersTitle);

			document.add(addChartPostsUsersPerDay());
			
			PdfPTable tablePostsUsersInfo = new PdfPTable(3);
			tablePostsUsersInfo.setTotalWidth(new float[]{120, 100, 140});
			tablePostsUsersInfo.setLockedWidth(true);
			tablePostsUsersInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablePostsUsersInfo.setSpacingAfter(10);
			
			// Posts users total.
			PdfPCell cellPostsUsersTotal = new PdfPCell();
			cellPostsUsersTotal.setBorder(PdfPCell.NO_BORDER);
			cellPostsUsersTotal.setPaddingLeft(5);
			cellPostsUsersTotal.setPaddingRight(0);
			cellPostsUsersTotal.setPaddingBottom(0);
			cellPostsUsersTotal.setPaddingTop(0);
			cellPostsUsersTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cellPostsUsersTotal.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cellPostsUsersTotal.setFixedHeight(34);

			PdfPTable tablePostsUsersTotal = new PdfPTable(2);
			tablePostsUsersTotal.setTotalWidth(new float[]{21, 99});
			tablePostsUsersTotal.setLockedWidth(true);

			PdfPCell cellPostsUsersTotalImage = new PdfPCell();
			cellPostsUsersTotalImage.setBorder(PdfPCell.NO_BORDER);
			cellPostsUsersTotalImage.setPadding(0);

			Image imgPostsUsersTotal = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_POSTS_USERS));
			imgPostsUsersTotal.scaleAbsolute(21, 21);

			cellPostsUsersTotalImage.addElement(imgPostsUsersTotal);

			PdfPCell cellPostsUsersTotalValue = new PdfPCell();
			cellPostsUsersTotalValue.setBorder(PdfPCell.NO_BORDER);
			cellPostsUsersTotalValue.setPaddingLeft(3);
			cellPostsUsersTotalValue.setPaddingRight(0);
			cellPostsUsersTotalValue.setPaddingTop(0);
			cellPostsUsersTotalValue.setPaddingBottom(0);

			Phrase parPostsUsersTotal = new Phrase("TOTAL DE AUTORES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parPostsUsersTotal.setLeading(7);

			Paragraph parPostsUsersTotalValue = new Paragraph(monitoringReport.getPostsUsersTotal().toString(), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(65, 191, 223)));
			parPostsUsersTotalValue.setLeading(14);

			cellPostsUsersTotalValue.addElement(parPostsUsersTotal);
			cellPostsUsersTotalValue.addElement(parPostsUsersTotalValue);

			tablePostsUsersTotal.addCell(cellPostsUsersTotalImage);
			tablePostsUsersTotal.addCell(cellPostsUsersTotalValue);

			cellPostsUsersTotal.addElement(tablePostsUsersTotal);
			
			// Posts users average.
			PdfPCell cellPostsUsersAverage = new PdfPCell();
			cellPostsUsersAverage.setBorder(PdfPCell.NO_BORDER);
			cellPostsUsersAverage.setPaddingLeft(30);
			cellPostsUsersAverage.setPaddingRight(0);
			cellPostsUsersAverage.setPaddingBottom(0);
			cellPostsUsersAverage.setPaddingTop(0);
			cellPostsUsersAverage.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cellPostsUsersAverage.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cellPostsUsersAverage.setFixedHeight(34);

			PdfPTable tablePostsUsersAverage = new PdfPTable(2);
			tablePostsUsersAverage.setTotalWidth(new float[]{21, 79});
			tablePostsUsersAverage.setLockedWidth(true);

			PdfPCell cellPostsUsersAverageImage = new PdfPCell();
			cellPostsUsersAverageImage.setBorder(PdfPCell.NO_BORDER);
			cellPostsUsersAverageImage.setPadding(0);

			Image imgPostsUsersAverage = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_POSTS_AVERAGE));
			imgPostsUsersAverage.scaleAbsolute(21, 21);

			cellPostsUsersAverageImage.addElement(imgPostsUsersAverage);

			PdfPCell cellPostsUsersAverageValue = new PdfPCell();
			cellPostsUsersAverageValue.setBorder(PdfPCell.NO_BORDER);
			cellPostsUsersAverageValue.setPaddingLeft(3);
			cellPostsUsersAverageValue.setPaddingRight(0);
			cellPostsUsersAverageValue.setPaddingTop(0);
			cellPostsUsersAverageValue.setPaddingBottom(0);

			Phrase parPostsUsersAverage = new Phrase("MÉDIA DIÁRIA", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parPostsUsersAverage.setLeading(7);
			
			Paragraph parPostsUsersAverageValue = new Paragraph(monitoringReport.getPostsUsersAverage().toString(), 
					new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(255, 173 ,30)));
			parPostsUsersAverageValue.setLeading(14);

			cellPostsUsersAverageValue.addElement(parPostsUsersAverage);
			cellPostsUsersAverageValue.addElement(parPostsUsersAverageValue);

			tablePostsUsersAverage.addCell(cellPostsUsersAverageImage);
			tablePostsUsersAverage.addCell(cellPostsUsersAverageValue);

			cellPostsUsersAverage.addElement(tablePostsUsersAverage);
			
			// Posts users recurrent.
			PdfPCell cellPostsUsersRecurrent = new PdfPCell();
			cellPostsUsersRecurrent.setBorder(PdfPCell.NO_BORDER);
			cellPostsUsersRecurrent.setPaddingLeft(30);
			cellPostsUsersRecurrent.setPaddingRight(0);
			cellPostsUsersRecurrent.setPaddingBottom(0);
			cellPostsUsersRecurrent.setPaddingTop(0);
			cellPostsUsersRecurrent.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cellPostsUsersRecurrent.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cellPostsUsersRecurrent.setFixedHeight(34);

			PdfPTable tablePostsUsersRecurrent = new PdfPTable(2);
			tablePostsUsersRecurrent.setTotalWidth(new float[]{21, 119});
			tablePostsUsersRecurrent.setLockedWidth(true);

			PdfPCell cellPostsUsersRecurrentImage = new PdfPCell();
			cellPostsUsersRecurrentImage.setBorder(PdfPCell.NO_BORDER);
			cellPostsUsersRecurrentImage.setPadding(0);

			Image imgPostsUsersRecurrent = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_POSTS_USERS_RECURRENT));
			imgPostsUsersRecurrent.scaleAbsolute(21, 21);

			cellPostsUsersRecurrentImage.addElement(imgPostsUsersRecurrent);

			PdfPCell cellPostsUsersRecurrentValue = new PdfPCell();
			cellPostsUsersRecurrentValue.setBorder(PdfPCell.NO_BORDER);
			cellPostsUsersRecurrentValue.setPaddingLeft(3);
			cellPostsUsersRecurrentValue.setPaddingRight(0);
			cellPostsUsersRecurrentValue.setPaddingTop(0);
			cellPostsUsersRecurrentValue.setPaddingBottom(0);

			Phrase parPostsUsersRecurrent = new Phrase("TAXA DE RECORRÊNCIA", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parPostsUsersRecurrent.setLeading(7);

			Paragraph parPostsUsersRecurrentValue = new Paragraph(monitoringReport.getPostsUsersRecurrentPerc().toString().replace(".", ",") + "%", 
					new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(19,221,152)));
			parPostsUsersRecurrentValue.setLeading(14);

			cellPostsUsersRecurrentValue.addElement(parPostsUsersRecurrent);
			cellPostsUsersRecurrentValue.addElement(parPostsUsersRecurrentValue);

			tablePostsUsersRecurrent.addCell(cellPostsUsersRecurrentImage);
			tablePostsUsersRecurrent.addCell(cellPostsUsersRecurrentValue);

			cellPostsUsersRecurrent.addElement(tablePostsUsersRecurrent);
			
			tablePostsUsersInfo.addCell(cellPostsUsersTotal);
			tablePostsUsersInfo.addCell(cellPostsUsersAverage);
			tablePostsUsersInfo.addCell(cellPostsUsersRecurrent);
			
			document.add(tablePostsUsersInfo);
			
			// Men Women Posts Users.
			PdfPTable tableMenWomenPostsUsers = new PdfPTable(1);
			tableMenWomenPostsUsers.setTotalWidth(new float[]{170});
			tableMenWomenPostsUsers.setLockedWidth(true);
			tableMenWomenPostsUsers.setHorizontalAlignment(Element.ALIGN_CENTER);
//			tableMenWomenPostsUsers.setSpacingBefore(15);
			tableMenWomenPostsUsers.setSpacingAfter(30);
			
			PdfPCell cellMenWomenPostsUsersTitle = new PdfPCell();
			cellMenWomenPostsUsersTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellMenWomenPostsUsersTitle.setBorder(PdfPCell.NO_BORDER);
			cellMenWomenPostsUsersTitle.setPaddingTop(13);
			cellMenWomenPostsUsersTitle.setPaddingLeft(0);
			cellMenWomenPostsUsersTitle.setPaddingRight(0);
			cellMenWomenPostsUsersTitle.setPaddingBottom(5);

			Paragraph parMenWomenPostsUsersTitle = new Paragraph("DIVISÃO ENTRE HOMENS E MULHERES", new Font(sourceSansPro, 10, Font.NORMAL, new BaseColor(134, 134, 134)));
			parMenWomenPostsUsersTitle.setAlignment(Element.ALIGN_CENTER);
			parMenWomenPostsUsersTitle.setLeading(9);

			cellMenWomenPostsUsersTitle.addElement(parMenWomenPostsUsersTitle);

			tableMenWomenPostsUsers.addCell(cellMenWomenPostsUsersTitle);

			PdfPCell cellMenWomenPostsUsers = new PdfPCell();
			cellMenWomenPostsUsers.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellMenWomenPostsUsers.setBorder(PdfPCell.NO_BORDER);
			cellMenWomenPostsUsers.setPaddingTop(0);
			cellMenWomenPostsUsers.setPaddingLeft(20);
			cellMenWomenPostsUsers.setPaddingRight(0);
			cellMenWomenPostsUsers.setPaddingBottom(0);

			Paragraph parMenWomenPostsUsers = new Paragraph();
			parMenWomenPostsUsers.setLeading(24);

			Image imgMenPostsUsers = Image.getInstance(
					br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_MEN_FANS));
			imgMenPostsUsers.scaleAbsolute(8, 24);

			parMenWomenPostsUsers.add(new Chunk(imgMenPostsUsers, 0, 0));

			Chunk chkMenPostsUsersPerc = new Chunk(" " + monitoringReport.getMenPostsUsersPerc() + "%",
					new Font(sourceSansPro, 25, Font.BOLD, new BaseColor(62, 179, 209)));
			chkMenPostsUsersPerc.setTextRise(3);

			parMenWomenPostsUsers.add(chkMenPostsUsersPerc);

			Chunk chkWomenPostsUsersPerc = new Chunk("    " + monitoringReport.getWomenPostsUsersPerc() + "% ",
					new Font(sourceSansPro, 25, Font.BOLD, new BaseColor(226, 43, 79)));
			chkWomenPostsUsersPerc.setTextRise(3);

			parMenWomenPostsUsers.add(chkWomenPostsUsersPerc);

			Image imgWomenPostsUsers = Image.getInstance(
					br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_WOMEN_FANS));
			imgWomenPostsUsers.scaleAbsolute(12, 24);

			parMenWomenPostsUsers.add(new Chunk(imgWomenPostsUsers, 0, 0));

			cellMenWomenPostsUsers.addElement(parMenWomenPostsUsers);

			tableMenWomenPostsUsers.addCell(cellMenWomenPostsUsers);

			document.add(tableMenWomenPostsUsers);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addTopLinks() {
		
		try {
			
			PdfPTable tableTopLinksTitle = new PdfPTable(1);
			tableTopLinksTitle.setTotalWidth(new float[]{523});
			tableTopLinksTitle.setLockedWidth(true);
			tableTopLinksTitle.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellTopLinksTitle = new PdfPCell();
			cellTopLinksTitle.setBorder(Rectangle.BOTTOM);
			cellTopLinksTitle.setBorderWidthBottom(0.8f);
			cellTopLinksTitle.setPaddingBottom(5);
			cellTopLinksTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parTopLinksTitle = new Paragraph("TOP LINKS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parTopLinksTitle.setAlignment(Element.ALIGN_CENTER);

			cellTopLinksTitle.addElement(parTopLinksTitle);
			
			tableTopLinksTitle.addCell(cellTopLinksTitle);
			
			PdfPTable tableTopLinksTableHeader = new PdfPTable(3);
			tableTopLinksTableHeader.setTotalWidth(new float[]{16, 302, 47});
			tableTopLinksTableHeader.setLockedWidth(true);
			tableTopLinksTableHeader.setSpacingBefore(5);
			tableTopLinksTableHeader.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableTopLinksTableHeader.getDefaultCell().setBorderWidthBottom(0.8f);
			tableTopLinksTableHeader.getDefaultCell().setBorderColorBottom(new BaseColor(227, 227, 228));
			tableTopLinksTableHeader.getDefaultCell().setBackgroundColor(new BaseColor(242, 242, 242));
			tableTopLinksTableHeader.getDefaultCell().setPadding(3);
			
			tableTopLinksTableHeader.addCell(new Phrase(new Chunk("#", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableTopLinksTableHeader.addCell(new Phrase(new Chunk("Link", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableTopLinksTableHeader.addCell(new Phrase(new Chunk("Número", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			
			PdfPTable tableTopLinksTableRows = new PdfPTable(3);
			tableTopLinksTableRows.setTotalWidth(new float[]{16, 302, 47});
			tableTopLinksTableRows.setLockedWidth(true);
			tableTopLinksTableRows.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableTopLinksTableRows.getDefaultCell().setBorderWidthBottom(0.8f);
			tableTopLinksTableRows.getDefaultCell().setBorderColorBottom(new BaseColor(239, 239, 240));
			tableTopLinksTableRows.getDefaultCell().setPadding(3);
			tableTopLinksTableRows.setSpacingAfter(22);
			
			int i = 1;
			for(Map.Entry<String, Long> link : monitoringReport.getLinks().entrySet()) {
				
				tableTopLinksTableRows.addCell(new Phrase(new Chunk(String.valueOf(i), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
				tableTopLinksTableRows.addCell(new Phrase(new Chunk(link.getKey(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
				tableTopLinksTableRows.addCell(new Phrase(new Chunk(link.getValue().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));

				i++;
			}
			
			document.add(tableTopLinksTitle);
			document.add(tableTopLinksTableHeader);
			document.add(tableTopLinksTableRows);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addTopHashTags() {
		
		try {
			
			PdfPTable tableTopHashTags = new PdfPTable(1);
			tableTopHashTags.setTotalWidth(new float[]{523});
			tableTopHashTags.setLockedWidth(true);
			tableTopHashTags.setSpacingAfter(30);

			PdfPCell cellTopHashTagsTitle = new PdfPCell();
			cellTopHashTagsTitle.setBorder(Rectangle.BOTTOM);
			cellTopHashTagsTitle.setBorderWidthBottom(0.8f);
			cellTopHashTagsTitle.setPaddingBottom(5);
			cellTopHashTagsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
			
			Paragraph parTopHashTagsTitle = new Paragraph("TOP HASHTAGS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parTopHashTagsTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellTopHashTagsTitle.addElement(parTopHashTagsTitle);

			PdfPCell cellTopHashTagsTables = new PdfPCell();
			cellTopHashTagsTables.setBorder(PdfPCell.NO_BORDER);
			cellTopHashTagsTables.setPadding(0);

			PdfPTable tableTopHashTagsTables = new PdfPTable(2);
			tableTopHashTagsTables.setTotalWidth(new float[]{190, 195});
			tableTopHashTagsTables.setLockedWidth(true);
			tableTopHashTagsTables.setSpacingBefore(5);
			
			PdfPCell cellTopHashTagsFirstFive = new PdfPCell();
			cellTopHashTagsFirstFive.setBorder(PdfPCell.NO_BORDER);
			cellTopHashTagsFirstFive.setPaddingBottom(0);
			cellTopHashTagsFirstFive.setPaddingTop(0);
			cellTopHashTagsFirstFive.setPaddingLeft(0);
			cellTopHashTagsFirstFive.setPaddingRight(20);
			
			PdfPTable tableTopHashTagsFiveHeader = new PdfPTable(3);
			tableTopHashTagsFiveHeader.setTotalWidth(new float[]{15, 127, 38});
			tableTopHashTagsFiveHeader.setLockedWidth(true);
			tableTopHashTagsFiveHeader.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableTopHashTagsFiveHeader.getDefaultCell().setBorderWidthBottom(0.8f);
			tableTopHashTagsFiveHeader.getDefaultCell().setBorderColorBottom(new BaseColor(227, 227, 228));
			tableTopHashTagsFiveHeader.getDefaultCell().setBackgroundColor(new BaseColor(242, 242, 242));
			tableTopHashTagsFiveHeader.getDefaultCell().setPadding(3);
			
			tableTopHashTagsFiveHeader.addCell(new Phrase(new Chunk("#", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableTopHashTagsFiveHeader.addCell(new Phrase(new Chunk("Hashtag", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableTopHashTagsFiveHeader.addCell(new Phrase(new Chunk("Número", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			
			PdfPCell cellTopHashTagsLastFive = new PdfPCell();
			cellTopHashTagsLastFive.setBorder(PdfPCell.NO_BORDER);
			cellTopHashTagsLastFive.setPadding(0);
			
			PdfPTable tableTopHashTagsLastFiveHeader = new PdfPTable(3);
			tableTopHashTagsLastFiveHeader.setTotalWidth(new float[]{15, 132, 38});
			tableTopHashTagsLastFiveHeader.setLockedWidth(true);
			tableTopHashTagsLastFiveHeader.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableTopHashTagsLastFiveHeader.getDefaultCell().setBorderWidthBottom(0.8f);
			tableTopHashTagsLastFiveHeader.getDefaultCell().setBorderColorBottom(new BaseColor(227, 227, 228));
			tableTopHashTagsLastFiveHeader.getDefaultCell().setBackgroundColor(new BaseColor(242, 242, 242));
			tableTopHashTagsLastFiveHeader.getDefaultCell().setPadding(3);
			
			tableTopHashTagsLastFiveHeader.addCell(new Phrase(new Chunk("#", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableTopHashTagsLastFiveHeader.addCell(new Phrase(new Chunk("Hashtag", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableTopHashTagsLastFiveHeader.addCell(new Phrase(new Chunk("Número", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			
			PdfPTable tableTopHashTagsFirstFiveRows = new PdfPTable(3);
			tableTopHashTagsFirstFiveRows.setTotalWidth(new float[]{15, 127, 38});
			tableTopHashTagsFirstFiveRows.setLockedWidth(true);
			tableTopHashTagsFirstFiveRows.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableTopHashTagsFirstFiveRows.getDefaultCell().setBorderWidthBottom(0.8f);
			tableTopHashTagsFirstFiveRows.getDefaultCell().setBorderColorBottom(new BaseColor(239, 239, 240));
			tableTopHashTagsFirstFiveRows.getDefaultCell().setPadding(3);
			
			PdfPTable tableTopHashTagsLastFiveRows = new PdfPTable(3);
			tableTopHashTagsLastFiveRows.setTotalWidth(new float[]{15, 132, 38});
			tableTopHashTagsLastFiveRows.setLockedWidth(true);
			tableTopHashTagsLastFiveRows.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableTopHashTagsLastFiveRows.getDefaultCell().setBorderWidthBottom(0.8f);
			tableTopHashTagsLastFiveRows.getDefaultCell().setBorderColorBottom(new BaseColor(239, 239, 240));
			tableTopHashTagsLastFiveRows.getDefaultCell().setPadding(3);
			
			int i = 1;
			for(Map.Entry<String, Long> hashTag : monitoringReport.getHashTags().entrySet()) {
				
				if(i < 6) {
					
					tableTopHashTagsFirstFiveRows.addCell(new Phrase(new Chunk(String.valueOf(i), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
					tableTopHashTagsFirstFiveRows.addCell(new Phrase(new Chunk(hashTag.getKey(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
					tableTopHashTagsFirstFiveRows.addCell(new Phrase(new Chunk(hashTag.getValue().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
					
				} else {
					
					tableTopHashTagsLastFiveRows.addCell(new Phrase(new Chunk(String.valueOf(i), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
					tableTopHashTagsLastFiveRows.addCell(new Phrase(new Chunk(hashTag.getKey(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
					tableTopHashTagsLastFiveRows.addCell(new Phrase(new Chunk(hashTag.getValue().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
					
				}

				i++;
			}
			
			cellTopHashTagsFirstFive.addElement(tableTopHashTagsFiveHeader);
			cellTopHashTagsFirstFive.addElement(tableTopHashTagsFirstFiveRows);
			
			cellTopHashTagsLastFive.addElement(tableTopHashTagsLastFiveHeader);
			cellTopHashTagsLastFive.addElement(tableTopHashTagsLastFiveRows);
			
			tableTopHashTagsTables.addCell(cellTopHashTagsFirstFive);
			tableTopHashTagsTables.addCell(cellTopHashTagsLastFive);
			
			cellTopHashTagsTables.addElement(tableTopHashTagsTables);
			 
			tableTopHashTags.addCell(cellTopHashTagsTitle);
			tableTopHashTags.addCell(cellTopHashTagsTables);
			
			document.add(tableTopHashTags);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	private void addPostsCities() {
		
		try {
			
			PdfPTable tablePostsCities = new PdfPTable(1);
			tablePostsCities.setTotalWidth(new float[]{523});
			tablePostsCities.setLockedWidth(true);
			tablePostsCities.setSpacingAfter(30);

			PdfPCell cellPostsCitiesTitle = new PdfPCell();
			cellPostsCitiesTitle.setBorder(Rectangle.BOTTOM);
			cellPostsCitiesTitle.setBorderWidthBottom(0.8f);
			cellPostsCitiesTitle.setPaddingBottom(5);
			cellPostsCitiesTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
			
			Paragraph parPostsCitiesTitle = new Paragraph("CIDADES COM MAIS CITAÇÕES", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parPostsCitiesTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellPostsCitiesTitle.addElement(parPostsCitiesTitle);

			PdfPCell cellPostsCitiesTables = new PdfPCell();
			cellPostsCitiesTables.setBorder(PdfPCell.NO_BORDER);
			cellPostsCitiesTables.setPadding(0);

			PdfPTable tablePostsCitiesTables = new PdfPTable(2);
			tablePostsCitiesTables.setTotalWidth(new float[]{190, 195});
			tablePostsCitiesTables.setLockedWidth(true);
			tablePostsCitiesTables.setSpacingBefore(5);
			
			PdfPCell cellPostsCitiesFirstFive = new PdfPCell();
			cellPostsCitiesFirstFive.setBorder(PdfPCell.NO_BORDER);
			cellPostsCitiesFirstFive.setPaddingBottom(0);
			cellPostsCitiesFirstFive.setPaddingTop(0);
			cellPostsCitiesFirstFive.setPaddingLeft(0);
			cellPostsCitiesFirstFive.setPaddingRight(20);
			
			PdfPTable tablePostsCitiesFiveHeader = new PdfPTable(3);
			tablePostsCitiesFiveHeader.setTotalWidth(new float[]{15, 127, 38});
			tablePostsCitiesFiveHeader.setLockedWidth(true);
			tablePostsCitiesFiveHeader.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tablePostsCitiesFiveHeader.getDefaultCell().setBorderWidthBottom(0.8f);
			tablePostsCitiesFiveHeader.getDefaultCell().setBorderColorBottom(new BaseColor(227, 227, 228));
			tablePostsCitiesFiveHeader.getDefaultCell().setBackgroundColor(new BaseColor(242, 242, 242));
			tablePostsCitiesFiveHeader.getDefaultCell().setPadding(3);
			
			tablePostsCitiesFiveHeader.addCell(new Phrase(new Chunk("#", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tablePostsCitiesFiveHeader.addCell(new Phrase(new Chunk("Cidade", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tablePostsCitiesFiveHeader.addCell(new Phrase(new Chunk("Número", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			
			PdfPCell cellPostsCitiesLastFive = new PdfPCell();
			cellPostsCitiesLastFive.setBorder(PdfPCell.NO_BORDER);
			cellPostsCitiesLastFive.setPadding(0);
			
			PdfPTable tablePostsCitiesLastFiveHeader = new PdfPTable(3);
			tablePostsCitiesLastFiveHeader.setTotalWidth(new float[]{15, 132, 38});
			tablePostsCitiesLastFiveHeader.setLockedWidth(true);
			tablePostsCitiesLastFiveHeader.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tablePostsCitiesLastFiveHeader.getDefaultCell().setBorderWidthBottom(0.8f);
			tablePostsCitiesLastFiveHeader.getDefaultCell().setBorderColorBottom(new BaseColor(227, 227, 228));
			tablePostsCitiesLastFiveHeader.getDefaultCell().setBackgroundColor(new BaseColor(242, 242, 242));
			tablePostsCitiesLastFiveHeader.getDefaultCell().setPadding(3);
			
			tablePostsCitiesLastFiveHeader.addCell(new Phrase(new Chunk("#", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tablePostsCitiesLastFiveHeader.addCell(new Phrase(new Chunk("Cidade", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tablePostsCitiesLastFiveHeader.addCell(new Phrase(new Chunk("Número", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			
			PdfPTable tablePostsCitiesFirstFiveRows = new PdfPTable(3);
			tablePostsCitiesFirstFiveRows.setTotalWidth(new float[]{15, 127, 38});
			tablePostsCitiesFirstFiveRows.setLockedWidth(true);
			tablePostsCitiesFirstFiveRows.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tablePostsCitiesFirstFiveRows.getDefaultCell().setBorderWidthBottom(0.8f);
			tablePostsCitiesFirstFiveRows.getDefaultCell().setBorderColorBottom(new BaseColor(239, 239, 240));
			tablePostsCitiesFirstFiveRows.getDefaultCell().setPadding(3);
			
			PdfPTable tablePostsCitiesLastFiveRows = new PdfPTable(3);
			tablePostsCitiesLastFiveRows.setTotalWidth(new float[]{15, 132, 38});
			tablePostsCitiesLastFiveRows.setLockedWidth(true);
			tablePostsCitiesLastFiveRows.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tablePostsCitiesLastFiveRows.getDefaultCell().setBorderWidthBottom(0.8f);
			tablePostsCitiesLastFiveRows.getDefaultCell().setBorderColorBottom(new BaseColor(239, 239, 240));
			tablePostsCitiesLastFiveRows.getDefaultCell().setPadding(3);
			
			int i = 1;
			for(Map.Entry<String, Long> postsCity : monitoringReport.getPostsCities().entrySet()) {
				
				if(i < 6) {
					
					tablePostsCitiesFirstFiveRows.addCell(new Phrase(new Chunk(String.valueOf(i), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
					tablePostsCitiesFirstFiveRows.addCell(new Phrase(new Chunk(postsCity.getKey(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
					tablePostsCitiesFirstFiveRows.addCell(new Phrase(new Chunk(postsCity.getValue().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
					
				} else {
					
					tablePostsCitiesLastFiveRows.addCell(new Phrase(new Chunk(String.valueOf(i), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
					tablePostsCitiesLastFiveRows.addCell(new Phrase(new Chunk(postsCity.getKey(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
					tablePostsCitiesLastFiveRows.addCell(new Phrase(new Chunk(postsCity.getValue().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
					
				}

				i++;
			}
			
			cellPostsCitiesFirstFive.addElement(tablePostsCitiesFiveHeader);
			cellPostsCitiesFirstFive.addElement(tablePostsCitiesFirstFiveRows);
			
			cellPostsCitiesLastFive.addElement(tablePostsCitiesLastFiveHeader);
			cellPostsCitiesLastFive.addElement(tablePostsCitiesLastFiveRows);
			
			tablePostsCitiesTables.addCell(cellPostsCitiesFirstFive);
			tablePostsCitiesTables.addCell(cellPostsCitiesLastFive);
			
			cellPostsCitiesTables.addElement(tablePostsCitiesTables);
			 
			tablePostsCities.addCell(cellPostsCitiesTitle);
			tablePostsCities.addCell(cellPostsCitiesTables);
			
			document.add(tablePostsCities);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	private void addReportUsersQualifications() {

		try {

			PdfPTable tableUsersQualifications = new PdfPTable(2);
			tableUsersQualifications.setTotalWidth(new float[]{ 220, 200});
			tableUsersQualifications.setLockedWidth(true);
			tableUsersQualifications.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableUsersQualifications.setSpacingAfter(30);

			// Positive qualifications.
			PdfPCell cellPositiveQualifications = new PdfPCell();
			cellPositiveQualifications.setBorder(PdfPCell.NO_BORDER);
			cellPositiveQualifications.setPaddingBottom(0);
			cellPositiveQualifications.setPaddingTop(0);
			cellPositiveQualifications.setPaddingLeft(0);
			cellPositiveQualifications.setPaddingRight(20);

			PdfPTable tablePositiveQualifications = new PdfPTable(1);
			tablePositiveQualifications.setTotalWidth(new float[]{200});
			tablePositiveQualifications.setLockedWidth(true);
			tablePositiveQualifications.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellPositiveQualificationsTitle = new PdfPCell();
			cellPositiveQualificationsTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellPositiveQualificationsTitle.setBorder(Rectangle.BOTTOM);
			cellPositiveQualificationsTitle.setBorderWidthBottom(0.8f);
			cellPositiveQualificationsTitle.setPaddingTop(0);
			cellPositiveQualificationsTitle.setPaddingLeft(0);
			cellPositiveQualificationsTitle.setPaddingRight(0);
			cellPositiveQualificationsTitle.setPaddingBottom(5);
			cellPositiveQualificationsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parPositiveQualificationsTitle = new Paragraph("USUÁRIOS COM MAIS POSTS POSITIVOS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parPositiveQualificationsTitle.setAlignment(Element.ALIGN_LEFT);

			cellPositiveQualificationsTitle.addElement(parPositiveQualificationsTitle);
			
			PdfPCell cellPositiveQualificationsRanking = new PdfPCell();
			cellPositiveQualificationsRanking.setBorder(PdfPCell.NO_BORDER);
			cellPositiveQualificationsRanking.setPaddingBottom(0);
			cellPositiveQualificationsRanking.setPaddingTop(5);
			cellPositiveQualificationsRanking.setPaddingLeft(0);
			cellPositiveQualificationsRanking.setPaddingRight(0);

			PdfPTable tablePositiveQualificationsRanking = new PdfPTable(2);
			tablePositiveQualificationsRanking.setTotalWidth(new float[]{19, 181});
			tablePositiveQualificationsRanking.setLockedWidth(true);
			
			for (int i = 0; i < monitoringReport.getMonitoringPositiveUsers().size(); i++) {
				
				PdfPCell cellImageUser = new PdfPCell();
				cellImageUser.setBorder(Rectangle.BOTTOM);
				cellImageUser.setBorderWidthBottom(0.8f);
				cellImageUser.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellImageUser.setPadding(3);
				cellImageUser.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				try {
					
					Image imgUser = Image.getInstance(new URL(monitoringReport.getMonitoringPositiveUsers().get(i).getMonitoringUser().getImageProfile()));
					imgUser.scaleAbsolute(16, 16);
					
					cellImageUser.addElement(imgUser);
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					try {
						
						Image imgUser = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.AVATAR));
						imgUser.scaleAbsolute(16, 16);
						
						cellImageUser.addElement(imgUser);
						
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
				PdfPCell cellUserInfo = new PdfPCell();
				cellUserInfo.setBorder(Rectangle.NO_BORDER);
				cellUserInfo.setBorderWidthBottom(0.8f);
				cellUserInfo.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellUserInfo.setPadding(0);
				cellUserInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				PdfPTable tableUserInfo = new PdfPTable(1);
				tableUserInfo.setTotalWidth(new float[]{181});
				tableUserInfo.setLockedWidth(true);

				PdfPCell cellUserName = new PdfPCell();
				cellUserName.setBorder(Rectangle.NO_BORDER);
				cellUserName.setPaddingLeft(5);
				cellUserName.setPaddingRight(0);
				cellUserName.setPaddingTop(0);
				cellUserName.setPaddingBottom(2);
				
				Phrase phrUserName = new Phrase(new Chunk((i + 1) +". " + monitoringReport.getMonitoringPositiveUsers().get(i).getMonitoringUser().getName().toUpperCase(), 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65))));
				phrUserName.setLeading(8);
				
				cellUserName.addElement(phrUserName);
				
				PdfPCell cellUserStats = new PdfPCell();
				cellUserStats.setBorder(PdfPCell.NO_BORDER);
				cellUserStats.setPaddingLeft(5);
				cellUserStats.setPaddingRight(0);
				cellUserStats.setPaddingTop(0);
				cellUserStats.setPaddingBottom(0);
				
				Paragraph parUserStats = new Paragraph();
				parUserStats.setLeading(6);
				
				parUserStats.add(new Chunk("SEGUINDO", new Font(sourceSansPro, 6, Font.NORMAL, new BaseColor(16, 182, 236))));
				parUserStats.add(new Chunk(" " + monitoringReport.getMonitoringPositiveUsers().get(i).getFriends().toString() + "   ", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(135, 135, 135))));
				parUserStats.add(new Chunk("SEGUIDORES", new Font(sourceSansPro, 6, Font.NORMAL, new BaseColor(16, 182, 236))));
				parUserStats.add(new Chunk(" " + monitoringReport.getMonitoringPositiveUsers().get(i).getFollowers().toString() + "   ", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(135, 135, 135))));
				parUserStats.add(new Chunk("MENÇÕES", new Font(sourceSansPro, 6, Font.NORMAL, new BaseColor(16, 182, 236))));
				parUserStats.add(new Chunk(" " + monitoringReport.getMonitoringPositiveUsers().get(i).getPostsCount().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(135, 135, 135))));
				
				cellUserStats.addElement(parUserStats);
				
				tableUserInfo.addCell(cellUserName);
				tableUserInfo.addCell(cellUserStats);
				
				cellUserInfo.addElement(tableUserInfo);
				
				tablePositiveQualificationsRanking.addCell(cellImageUser);	
				tablePositiveQualificationsRanking.addCell(cellUserInfo);
			}
			
			cellPositiveQualificationsRanking.addElement(tablePositiveQualificationsRanking);

			tablePositiveQualifications.addCell(cellPositiveQualificationsTitle);
			tablePositiveQualifications.addCell(cellPositiveQualificationsRanking);
			
			cellPositiveQualifications.addElement(tablePositiveQualifications);
			
			// Negative qualifications.
			PdfPCell cellNegativeQualifications = new PdfPCell();
			cellNegativeQualifications.setBorder(PdfPCell.NO_BORDER);
			cellNegativeQualifications.setPaddingBottom(0);
			cellNegativeQualifications.setPaddingTop(0);
			cellNegativeQualifications.setPaddingLeft(0);
			cellNegativeQualifications.setPaddingRight(0);

			PdfPTable tableNegativeQualifications = new PdfPTable(1);
			tableNegativeQualifications.setTotalWidth(new float[]{200});
			tableNegativeQualifications.setLockedWidth(true);
			tableNegativeQualifications.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellNegativeQualificationsTitle = new PdfPCell();
			cellNegativeQualificationsTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellNegativeQualificationsTitle.setBorder(Rectangle.BOTTOM);
			cellNegativeQualificationsTitle.setBorderWidthBottom(0.8f);
			cellNegativeQualificationsTitle.setPaddingTop(0);
			cellNegativeQualificationsTitle.setPaddingLeft(0);
			cellNegativeQualificationsTitle.setPaddingRight(0);
			cellNegativeQualificationsTitle.setPaddingBottom(5);
			cellNegativeQualificationsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parNegativeQualificationsTitle = new Paragraph("USUÁRIOS COM MAIS POSTS NEGATIVOS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parNegativeQualificationsTitle.setAlignment(Element.ALIGN_LEFT);

			cellNegativeQualificationsTitle.addElement(parNegativeQualificationsTitle);
			
			PdfPCell cellNegativeQualificationsRanking = new PdfPCell();
			cellNegativeQualificationsRanking.setBorder(PdfPCell.NO_BORDER);
			cellNegativeQualificationsRanking.setPaddingBottom(0);
			cellNegativeQualificationsRanking.setPaddingTop(5);
			cellNegativeQualificationsRanking.setPaddingLeft(0);
			cellNegativeQualificationsRanking.setPaddingRight(0);

			PdfPTable tableNegativeQualificationsRanking = new PdfPTable(2);
			tableNegativeQualificationsRanking.setTotalWidth(new float[]{19, 181});
			tableNegativeQualificationsRanking.setLockedWidth(true);
			
			for (int i = 0; i < monitoringReport.getMonitoringNegativeUsers().size(); i++) {
				
				PdfPCell cellImageUser = new PdfPCell();
				cellImageUser.setBorder(Rectangle.BOTTOM);
				cellImageUser.setBorderWidthBottom(0.8f);
				cellImageUser.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellImageUser.setPadding(3);
				cellImageUser.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				try {
					
					Image imgUser = Image.getInstance(new URL(monitoringReport.getMonitoringNegativeUsers().get(i).getMonitoringUser().getImageProfile()));
					imgUser.scaleAbsolute(16, 16);
					
					cellImageUser.addElement(imgUser);
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					try {
						
						Image imgUser = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.AVATAR));
						imgUser.scaleAbsolute(16, 16);
						
						cellImageUser.addElement(imgUser);
						
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
				PdfPCell cellUserInfo = new PdfPCell();
				cellUserInfo.setBorder(Rectangle.NO_BORDER);
				cellUserInfo.setBorderWidthBottom(0.8f);
				cellUserInfo.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellUserInfo.setPadding(0);
				cellUserInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				PdfPTable tableUserInfo = new PdfPTable(1);
				tableUserInfo.setTotalWidth(new float[]{181});
				tableUserInfo.setLockedWidth(true);

				PdfPCell cellUserName = new PdfPCell();
				cellUserName.setBorder(Rectangle.NO_BORDER);
				cellUserName.setPaddingLeft(5);
				cellUserName.setPaddingRight(0);
				cellUserName.setPaddingTop(0);
				cellUserName.setPaddingBottom(2);
				
				Phrase phrUserName = new Phrase(new Chunk((i + 1) +". " + monitoringReport.getMonitoringNegativeUsers().get(i).getMonitoringUser().getName().toUpperCase(), 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65))));
				phrUserName.setLeading(8);
				
				cellUserName.addElement(phrUserName);
				
				PdfPCell cellUserStats = new PdfPCell();
				cellUserStats.setBorder(PdfPCell.NO_BORDER);
				cellUserStats.setPaddingLeft(5);
				cellUserStats.setPaddingRight(0);
				cellUserStats.setPaddingTop(0);
				cellUserStats.setPaddingBottom(0);
				
				Paragraph parUserStats = new Paragraph();
				parUserStats.setLeading(6);
				
				parUserStats.add(new Chunk("SEGUINDO", new Font(sourceSansPro, 6, Font.NORMAL, new BaseColor(16, 182, 236))));
				parUserStats.add(new Chunk(" " + monitoringReport.getMonitoringNegativeUsers().get(i).getFriends().toString() + "   ", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(135, 135, 135))));
				parUserStats.add(new Chunk("SEGUIDORES", new Font(sourceSansPro, 6, Font.NORMAL, new BaseColor(16, 182, 236))));
				parUserStats.add(new Chunk(" " + monitoringReport.getMonitoringNegativeUsers().get(i).getFollowers().toString() + "   ", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(135, 135, 135))));
				parUserStats.add(new Chunk("MENÇÕES", new Font(sourceSansPro, 6, Font.NORMAL, new BaseColor(16, 182, 236))));
				parUserStats.add(new Chunk(" " + monitoringReport.getMonitoringNegativeUsers().get(i).getPostsCount().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(135, 135, 135))));
				
				cellUserStats.addElement(parUserStats);
				
				tableUserInfo.addCell(cellUserName);
				tableUserInfo.addCell(cellUserStats);
				
				cellUserInfo.addElement(tableUserInfo);
				
				tableNegativeQualificationsRanking.addCell(cellImageUser);	
				tableNegativeQualificationsRanking.addCell(cellUserInfo);
			}
			
			cellNegativeQualificationsRanking.addElement(tableNegativeQualificationsRanking);

			tableNegativeQualifications.addCell(cellNegativeQualificationsTitle);
			tableNegativeQualifications.addCell(cellNegativeQualificationsRanking);

			cellNegativeQualifications.addElement(tableNegativeQualifications);
			
			tableUsersQualifications.addCell(cellPositiveQualifications);
			tableUsersQualifications.addCell(cellNegativeQualifications);
			
			document.add(tableUsersQualifications);

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}
	
	private void addReportOfInteractions() {

		try {

			PdfPTable tableReportOfInteractions = new PdfPTable(2);
			tableReportOfInteractions.setTotalWidth(new float[]{ 190, 190});
			tableReportOfInteractions.setLockedWidth(true);
			tableReportOfInteractions.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableReportOfInteractions.getDefaultCell().setBorder(Rectangle.BOX);
			tableReportOfInteractions.getDefaultCell().setPaddingBottom(0);
			tableReportOfInteractions.getDefaultCell().setPaddingLeft(0);
			tableReportOfInteractions.getDefaultCell().setPaddingRight(0);
			tableReportOfInteractions.getDefaultCell().setPaddingTop(5);
			tableReportOfInteractions.setSpacingAfter(20);

			// Sentiment Analysis Chart
			PdfPCell cellChartSentimentAnalysis = new PdfPCell();
			cellChartSentimentAnalysis.setBorder(PdfPCell.NO_BORDER);
			cellChartSentimentAnalysis.setPaddingBottom(0);
			cellChartSentimentAnalysis.setPaddingTop(0);
			cellChartSentimentAnalysis.setPaddingLeft(0);
			cellChartSentimentAnalysis.setPaddingRight(20);

			PdfPTable tableSentimentAnalysis = new PdfPTable(1);
			tableSentimentAnalysis.setTotalWidth(new float[]{170});
			tableSentimentAnalysis.setLockedWidth(true);
			tableSentimentAnalysis.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellSentimentAnalysisTitle = new PdfPCell();
			cellSentimentAnalysisTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellSentimentAnalysisTitle.setBorder(Rectangle.BOTTOM);
			cellSentimentAnalysisTitle.setBorderWidthBottom(0.8f);
			cellSentimentAnalysisTitle.setPaddingTop(0);
			cellSentimentAnalysisTitle.setPaddingLeft(0);
			cellSentimentAnalysisTitle.setPaddingRight(0);
			cellSentimentAnalysisTitle.setPaddingBottom(5);
			cellSentimentAnalysisTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parSentimentAnalysisTitle = new Paragraph("ANÁLISE DE SENTIMENTO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parSentimentAnalysisTitle.setAlignment(Element.ALIGN_LEFT);

			cellSentimentAnalysisTitle.addElement(parSentimentAnalysisTitle);

			tableSentimentAnalysis.addCell(cellSentimentAnalysisTitle);

			PdfPCell cellSentimentAnalysis = new PdfPCell();
			cellSentimentAnalysis.setBorder(PdfPCell.NO_BORDER);
			cellSentimentAnalysis.setPadding(0);

			cellSentimentAnalysis.addElement(addChartSentimentAnalysis());

			tableSentimentAnalysis.addCell(cellSentimentAnalysis);

			cellChartSentimentAnalysis.addElement(tableSentimentAnalysis);

			tableReportOfInteractions.addCell(cellChartSentimentAnalysis);

			// Approval Rate.
			PdfPCell cellApprovalRate = new PdfPCell();
			cellApprovalRate.setBorder(PdfPCell.NO_BORDER);
			cellApprovalRate.setPadding(0);

			PdfPTable tableApprovalRate = new PdfPTable(1);
			tableApprovalRate.setTotalWidth(new float[]{190});
			tableApprovalRate.setLockedWidth(true);
			tableApprovalRate.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellApprovalRateTitle = new PdfPCell();
			cellApprovalRateTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellApprovalRateTitle.setBorder(Rectangle.BOTTOM);
			cellApprovalRateTitle.setBorderWidthBottom(0.8f);
			cellApprovalRateTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
			cellApprovalRateTitle.setPaddingTop(0);
			cellApprovalRateTitle.setPaddingLeft(0);
			cellApprovalRateTitle.setPaddingRight(0);
			cellApprovalRateTitle.setPaddingBottom(5);

			Paragraph parApprovalRateTitle = new Paragraph("ÍNDICE DE APROVAÇÃO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parApprovalRateTitle.setAlignment(Element.ALIGN_LEFT);

			cellApprovalRateTitle.addElement(parApprovalRateTitle);

			PdfPCell cellApprovalRatePerc = new PdfPCell();
			cellApprovalRatePerc.setBorder(PdfPCell.BOTTOM);
			cellApprovalRatePerc.setBorderWidthBottom(0.8f);
			cellApprovalRatePerc.setBorderColorBottom(new BaseColor(177, 177, 177));
			cellApprovalRatePerc.setPaddingTop(12);
			cellApprovalRatePerc.setPaddingBottom(12);
			cellApprovalRatePerc.setPaddingLeft(0);
			cellApprovalRatePerc.setPaddingRight(0);

			PdfPTable tableApprovalRatePerc = new PdfPTable(2);
			tableApprovalRatePerc.setTotalWidth(new float[]{85, 105});
			tableApprovalRatePerc.setLockedWidth(true);
			tableApprovalRatePerc.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellApprovalRatePercValue = new PdfPCell();
			cellApprovalRatePercValue.setBorder(PdfPCell.NO_BORDER);
			cellApprovalRatePercValue.setPadding(0);

			Paragraph parApprovalRatePercValueTitle = new Paragraph(monitoringReport.getApprovalRatePerc() + "%", new Font(sourceSansPro, 35, Font.BOLD, 
					monitoringReport.getApprovalRatePerc() >= 50 ? new BaseColor(255, 173 ,30) : new BaseColor(243, 49, 0)));
			parApprovalRatePercValueTitle.setAlignment(Element.ALIGN_CENTER);
			parApprovalRatePercValueTitle.setLeading(30);

			cellApprovalRatePercValue.addElement(parApprovalRatePercValueTitle);

			PdfPCell cellApprovalRatePercDesc = new PdfPCell();
			cellApprovalRatePercDesc.setBorder(PdfPCell.NO_BORDER);
			cellApprovalRatePercDesc.setPadding(0);

			String publicPerception = monitoringReport.getApprovalRatePerc() >= 50 ? "positiva" : "negativa";

			Paragraph parApprovalRatePercDescTitle = new Paragraph("De acordo com as qualificações este monitoramento tem uma percepção " + publicPerception + " do público.", new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
			parApprovalRatePercDescTitle.setAlignment(Element.ALIGN_LEFT);	

			cellApprovalRatePercDesc.addElement(parApprovalRatePercDescTitle);

			tableApprovalRatePerc.addCell(cellApprovalRatePercValue);
			tableApprovalRatePerc.addCell(cellApprovalRatePercDesc);

			cellApprovalRatePerc.addElement(tableApprovalRatePerc);

			PdfPCell cellQualifiedPostsContainer = new PdfPCell();
			cellQualifiedPostsContainer.setBorder(PdfPCell.NO_BORDER);
			cellQualifiedPostsContainer.setPaddingTop(13);
			cellQualifiedPostsContainer.setPaddingBottom(0);
			cellQualifiedPostsContainer.setPaddingLeft(0);
			cellQualifiedPostsContainer.setPaddingRight(0);

			PdfPTable tableQualifiedPosts = new PdfPTable(2);
			tableQualifiedPosts.setTotalWidth(new float[]{50, 140});
			tableQualifiedPosts.setLockedWidth(true);
			tableQualifiedPosts.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellQualifiedPostsValue = new PdfPCell();
			cellQualifiedPostsValue.setBorder(PdfPCell.NO_BORDER);
			cellQualifiedPostsValue.setPaddingRight(7);
			cellQualifiedPostsValue.setPaddingLeft(0);
			cellQualifiedPostsValue.setPaddingTop(0);
			cellQualifiedPostsValue.setPaddingBottom(5);

			Paragraph parQualifiedPostsValue = new Paragraph(monitoringReport.getQualifiedPostsTotal().toString(), new Font(sourceSansPro, 11, Font.BOLD, new BaseColor(31, 203, 223)));
			parQualifiedPostsValue.setAlignment(Element.ALIGN_RIGHT);
			parQualifiedPostsValue.setLeading(10);

			cellQualifiedPostsValue.addElement(parQualifiedPostsValue);

			PdfPCell cellQualifiedPostsDesc = new PdfPCell();
			cellQualifiedPostsDesc.setBorder(PdfPCell.NO_BORDER);
			cellQualifiedPostsDesc.setPadding(0);

			Paragraph parQualifiedPostsDesc = new Paragraph(monitoringReport.getQualifiedPostsTotal() != 1 ? "POSTS QUALIFICADOS" : "POST QUALIFICADO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parQualifiedPostsDesc.setAlignment(Element.ALIGN_LEFT);
			parQualifiedPostsDesc.setLeading(10);

			cellQualifiedPostsDesc.addElement(parQualifiedPostsDesc);

			tableQualifiedPosts.addCell(cellQualifiedPostsValue);
			tableQualifiedPosts.addCell(cellQualifiedPostsDesc);

			PdfPTable tablePositivePosts = new PdfPTable(2);
			tablePositivePosts.setTotalWidth(new float[]{50, 140});
			tablePositivePosts.setLockedWidth(true);
			tablePositivePosts.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellPositivePostsValue = new PdfPCell();
			cellPositivePostsValue.setBorder(PdfPCell.NO_BORDER);
			cellPositivePostsValue.setPaddingRight(7);
			cellPositivePostsValue.setPaddingLeft(0);
			cellPositivePostsValue.setPaddingTop(0);
			cellPositivePostsValue.setPaddingBottom(5);

			Long positivePosts = monitoringReport.getSentimentAnalysis().containsKey('P') ? monitoringReport.getSentimentAnalysis().get('P') : 0L;

			Paragraph parPositivePostsValue = new Paragraph(positivePosts.toString(), new Font(sourceSansPro, 11, Font.BOLD, new BaseColor(163, 222, 6)));
			parPositivePostsValue.setAlignment(Element.ALIGN_RIGHT);
			parPositivePostsValue.setLeading(10);

			cellPositivePostsValue.addElement(parPositivePostsValue);

			PdfPCell cellPositivePostsDesc = new PdfPCell();
			cellPositivePostsDesc.setBorder(PdfPCell.NO_BORDER);
			cellPositivePostsDesc.setPadding(0);

			Paragraph parPositivePostsDesc = new Paragraph(positivePosts != 1 ? "POSTS POSITIVOS" : "POST POSITIVO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parPositivePostsDesc.setAlignment(Element.ALIGN_LEFT);
			parPositivePostsDesc.setLeading(10);

			cellPositivePostsDesc.addElement(parPositivePostsDesc);

			tablePositivePosts.addCell(cellPositivePostsValue);
			tablePositivePosts.addCell(cellPositivePostsDesc);

			PdfPTable tableIndifferentPosts = new PdfPTable(2);
			tableIndifferentPosts.setTotalWidth(new float[]{50, 140});
			tableIndifferentPosts.setLockedWidth(true);
			tableIndifferentPosts.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellIndifferentPostsValue = new PdfPCell();
			cellIndifferentPostsValue.setBorder(PdfPCell.NO_BORDER);
			cellIndifferentPostsValue.setPaddingRight(7);
			cellIndifferentPostsValue.setPaddingLeft(0);
			cellIndifferentPostsValue.setPaddingTop(0);
			cellIndifferentPostsValue.setPaddingBottom(5);

			Long indifferentPosts = monitoringReport.getSentimentAnalysis().containsKey('S') ? monitoringReport.getSentimentAnalysis().get('S') : 0L;

			Paragraph parIndifferentPostsValue = new Paragraph(indifferentPosts.toString(), new Font(sourceSansPro, 11, Font.BOLD, new BaseColor(255, 200, 50)));
			parIndifferentPostsValue.setAlignment(Element.ALIGN_RIGHT);
			parIndifferentPostsValue.setLeading(10);

			cellIndifferentPostsValue.addElement(parIndifferentPostsValue);

			PdfPCell cellIndifferentPostsDesc = new PdfPCell();
			cellIndifferentPostsDesc.setBorder(PdfPCell.NO_BORDER);
			cellIndifferentPostsDesc.setPadding(0);

			Paragraph parIndifferentPostsDesc = new Paragraph(indifferentPosts != 1 ? "POSTS NEUTROS" : "POST NEUTRO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parIndifferentPostsDesc.setAlignment(Element.ALIGN_LEFT);
			parIndifferentPostsDesc.setLeading(10);

			cellIndifferentPostsDesc.addElement(parIndifferentPostsDesc);

			tableIndifferentPosts.addCell(cellIndifferentPostsValue);
			tableIndifferentPosts.addCell(cellIndifferentPostsDesc);
			
			PdfPTable tableNegativePosts = new PdfPTable(2);
			tableNegativePosts.setTotalWidth(new float[]{50, 140});
			tableNegativePosts.setLockedWidth(true);
			tableNegativePosts.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellNegativePostsValue = new PdfPCell();
			cellNegativePostsValue.setBorder(PdfPCell.NO_BORDER);
			cellNegativePostsValue.setPaddingRight(7);
			cellNegativePostsValue.setPaddingLeft(0);
			cellNegativePostsValue.setPaddingTop(0);
			cellNegativePostsValue.setPaddingBottom(5);

			Long negativePosts = monitoringReport.getSentimentAnalysis().containsKey('N') ? monitoringReport.getSentimentAnalysis().get('N') : 0L;

			Paragraph parNegativePostsValue = new Paragraph(negativePosts.toString(), new Font(sourceSansPro, 11, Font.BOLD, new BaseColor(243, 49, 0)));
			parNegativePostsValue.setAlignment(Element.ALIGN_RIGHT);
			parNegativePostsValue.setLeading(10);

			cellNegativePostsValue.addElement(parNegativePostsValue);

			PdfPCell cellNegativePostsDesc = new PdfPCell();
			cellNegativePostsDesc.setBorder(PdfPCell.NO_BORDER);
			cellNegativePostsDesc.setPadding(0);

			Paragraph parNegativePostsDesc = new Paragraph(negativePosts != 1 ? "POSTS NEGATIVOS" : "POST NEGATIVO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parNegativePostsDesc.setAlignment(Element.ALIGN_LEFT);
			parNegativePostsDesc.setLeading(10);

			cellNegativePostsDesc.addElement(parNegativePostsDesc);

			tableNegativePosts.addCell(cellNegativePostsValue);
			tableNegativePosts.addCell(cellNegativePostsDesc);

			cellQualifiedPostsContainer.addElement(tableQualifiedPosts);
			cellQualifiedPostsContainer.addElement(tablePositivePosts);
			cellQualifiedPostsContainer.addElement(tableIndifferentPosts);
			cellQualifiedPostsContainer.addElement(tableNegativePosts);

			tableApprovalRate.addCell(cellApprovalRateTitle);
			tableApprovalRate.addCell(cellApprovalRatePerc);
			tableApprovalRate.addCell(cellQualifiedPostsContainer);

			cellApprovalRate.addElement(tableApprovalRate);

			tableReportOfInteractions.addCell(cellApprovalRate);

			document.add(tableReportOfInteractions);

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	private void addReportOfNetworks() {

		try {

			PdfPTable tableReportOfNetworksTitle = new PdfPTable(1);
			tableReportOfNetworksTitle.setTotalWidth(new float[]{523});
			tableReportOfNetworksTitle.setLockedWidth(true);
			tableReportOfNetworksTitle.setHorizontalAlignment(Element.ALIGN_CENTER);

			tableReportOfNetworksTitle.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableReportOfNetworksTitle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			tableReportOfNetworksTitle.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableReportOfNetworksTitle.getDefaultCell().setBorderWidthBottom(0.8f);
			tableReportOfNetworksTitle.getDefaultCell().setPaddingBottom(5);
			tableReportOfNetworksTitle.getDefaultCell().setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parResumeHeader = new Paragraph("RESULTADOS RECUPERADOS POR REDE", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parResumeHeader.setAlignment(Element.ALIGN_LEFT);

			tableReportOfNetworksTitle.addCell(parResumeHeader);

			document.add(tableReportOfNetworksTitle);

			PdfPTable tableReportOfNetworksStats = new PdfPTable(2);
			tableReportOfNetworksStats.setTotalWidth(new float[]{ 213, 150});
			tableReportOfNetworksStats.setLockedWidth(true);
			tableReportOfNetworksStats.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableReportOfNetworksStats.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tableReportOfNetworksStats.getDefaultCell().setPaddingTop(5);
			tableReportOfNetworksStats.setSpacingAfter(20);

			PdfPTable tableNumberOfPostsNetwork = new PdfPTable(2);
			tableNumberOfPostsNetwork.setTotalWidth(new float[]{106, 106});
			tableNumberOfPostsNetwork.setLockedWidth(true);
			tableNumberOfPostsNetwork.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			// Youtube.
			PdfPCell cellYoutube = new PdfPCell();
//			cellYoutube.setBorder(Rectangle.RIGHT);
//			cellYoutube.setBorderWidthRight(0.8f);
//			cellYoutube.setBorderColorRight(new BaseColor(177, 177, 177));
			cellYoutube.setBorder(PdfPCell.NO_BORDER);
			cellYoutube.setPadding(0);
			cellYoutube.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cellYoutube.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cellYoutube.setFixedHeight(34);

			PdfPTable tableYoutube = new PdfPTable(2);
			tableYoutube.setTotalWidth(new float[]{21, 109});
			tableYoutube.setLockedWidth(true);

			PdfPCell cellYoutubeImage = new PdfPCell();
			cellYoutubeImage.setBorder(PdfPCell.NO_BORDER);
			cellYoutubeImage.setPadding(0);

			Image imgYoutube = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_YOUTUBE_PER_DAYS));
			imgYoutube.scaleAbsolute(21, 21);

			cellYoutubeImage.addElement(imgYoutube);

			PdfPCell cellYoutubeValue = new PdfPCell();
			cellYoutubeValue.setBorder(PdfPCell.NO_BORDER);
			cellYoutubeValue.setPaddingLeft(3);
			cellYoutubeValue.setPaddingRight(0);
			cellYoutubeValue.setPaddingTop(0);
			cellYoutubeValue.setPaddingBottom(0);

			Phrase parYoutubeTitleValue = new Phrase("YOUTUBE", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parYoutubeTitleValue.setLeading(7);

			Paragraph parYoutubeValue = new Paragraph(monitoringReport.getPostsPerDaysNetworks().containsKey('Y') ? 
					monitoringReport.getPostsPerDaysNetworks().get('Y').toString() : String.valueOf(0L), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(201, 22, 15)));
			parYoutubeValue.setLeading(14);

			cellYoutubeValue.addElement(parYoutubeTitleValue);
			cellYoutubeValue.addElement(parYoutubeValue);

			tableYoutube.addCell(cellYoutubeImage);
			tableYoutube.addCell(cellYoutubeValue);

			cellYoutube.addElement(tableYoutube);
			
			// Twitter.
			PdfPCell cellTwitter = new PdfPCell();
//			cellTwitter.setBorder(PdfPCell.NO_BORDER);
			cellTwitter.setBorder(Rectangle.RIGHT);
			cellTwitter.setBorderWidthRight(0.8f);
			cellTwitter.setBorderColorRight(new BaseColor(177, 177, 177));
			cellTwitter.setPadding(0);
			cellTwitter.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cellTwitter.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cellTwitter.setFixedHeight(34);

			PdfPTable tableTwitter = new PdfPTable(2);
			tableTwitter.setTotalWidth(new float[]{21, 109});
			tableTwitter.setLockedWidth(true);

			PdfPCell cellTwitterImage = new PdfPCell();
			cellTwitterImage.setBorder(PdfPCell.NO_BORDER);
			cellTwitterImage.setPadding(0);

			Image imgTwitter = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_TWITTER_PER_DAYS));
			imgTwitter.scaleAbsolute(21, 21);

			cellTwitterImage.addElement(imgTwitter);

			PdfPCell cellTwitterValue = new PdfPCell();
			cellTwitterValue.setBorder(PdfPCell.NO_BORDER);
			cellTwitterValue.setPaddingLeft(3);
			cellTwitterValue.setPaddingRight(0);
			cellTwitterValue.setPaddingTop(0);
			cellTwitterValue.setPaddingBottom(0);

			Phrase parTwitterTitleValue = new Phrase("TWITTER", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parTwitterTitleValue.setLeading(7);

			Paragraph parTwitterValue = new Paragraph(monitoringReport.getPostsPerDaysNetworks().containsKey('T') ? 
					monitoringReport.getPostsPerDaysNetworks().get('T').toString() : String.valueOf(0L), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(99, 218, 238)));
			parTwitterValue.setLeading(14);

			cellTwitterValue.addElement(parTwitterTitleValue);
			cellTwitterValue.addElement(parTwitterValue);

			tableTwitter.addCell(cellTwitterImage);
			tableTwitter.addCell(cellTwitterValue);

			cellTwitter.addElement(tableTwitter);
			
			// Reclame Aqui.
			PdfPCell cellReclameAqui = new PdfPCell();
//			cellReclameAqui.setBorder(Rectangle.RIGHT);
//			cellReclameAqui.setBorderWidthRight(0.8f);
//			cellReclameAqui.setBorderColorRight(new BaseColor(177, 177, 177));
			cellReclameAqui.setBorder(PdfPCell.NO_BORDER);
			cellReclameAqui.setPadding(0);
			cellReclameAqui.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cellReclameAqui.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cellReclameAqui.setFixedHeight(34);

			PdfPTable tableReclameAqui = new PdfPTable(2);
			tableReclameAqui.setTotalWidth(new float[]{21, 109});
			tableReclameAqui.setLockedWidth(true);

			PdfPCell cellReclameAquiImage = new PdfPCell();
			cellReclameAquiImage.setBorder(PdfPCell.NO_BORDER);
			cellReclameAquiImage.setPadding(0);

			Image imgReclameAqui = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_RECLAME_AQUI_PER_DAYS));
			imgReclameAqui.scaleAbsolute(21, 21);

			cellReclameAquiImage.addElement(imgReclameAqui);

			PdfPCell cellReclameAquiValue = new PdfPCell();
			cellReclameAquiValue.setBorder(PdfPCell.NO_BORDER);
			cellReclameAquiValue.setPaddingLeft(3);
			cellReclameAquiValue.setPaddingRight(0);
			cellReclameAquiValue.setPaddingTop(0);
			cellReclameAquiValue.setPaddingBottom(0);

			Phrase parReclameAquiTitleValue = new Phrase("RECLAME AQUI", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parReclameAquiTitleValue.setLeading(7);

			Paragraph parReclameAquiValue = new Paragraph(monitoringReport.getPostsPerDaysNetworks().containsKey('R') ? 
					monitoringReport.getPostsPerDaysNetworks().get('R').toString() : String.valueOf(0L), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(110, 196, 52)));
			parReclameAquiValue.setLeading(14);

			cellReclameAquiValue.addElement(parReclameAquiTitleValue);
			cellReclameAquiValue.addElement(parReclameAquiValue);

			tableReclameAqui.addCell(cellReclameAquiImage);
			tableReclameAqui.addCell(cellReclameAquiValue);

			cellReclameAqui.addElement(tableReclameAqui);
			
			// Google Plus.
			PdfPCell cellGooglePlus = new PdfPCell();
//			cellGooglePlus.setBorder(PdfPCell.NO_BORDER);
			cellGooglePlus.setBorder(Rectangle.RIGHT);
			cellGooglePlus.setBorderWidthRight(0.8f);
			cellGooglePlus.setBorderColorRight(new BaseColor(177, 177, 177));
			cellGooglePlus.setPadding(0);
			cellGooglePlus.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cellGooglePlus.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cellGooglePlus.setFixedHeight(34);

			PdfPTable tableGooglePlus = new PdfPTable(2);
			tableGooglePlus.setTotalWidth(new float[]{21, 109});
			tableGooglePlus.setLockedWidth(true);

			PdfPCell cellGooglePlusImage = new PdfPCell();
			cellGooglePlusImage.setBorder(PdfPCell.NO_BORDER);
			cellGooglePlusImage.setPadding(0);

			Image imgGooglePlus = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_GOOGLE_PLUS_PER_DAYS));
			imgGooglePlus.scaleAbsolute(21, 21);

			cellGooglePlusImage.addElement(imgGooglePlus);

			PdfPCell cellGooglePlusValue = new PdfPCell();
			cellGooglePlusValue.setBorder(PdfPCell.NO_BORDER);
			cellGooglePlusValue.setPaddingLeft(3);
			cellGooglePlusValue.setPaddingRight(0);
			cellGooglePlusValue.setPaddingTop(0);
			cellGooglePlusValue.setPaddingBottom(0);

			Phrase parGooglePlusTitleValue = new Phrase("GOOGLE+", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parGooglePlusTitleValue.setLeading(7);

			Paragraph parGooglePlusValue = new Paragraph(monitoringReport.getPostsPerDaysNetworks().containsKey('G') ? 
					monitoringReport.getPostsPerDaysNetworks().get('G').toString() : String.valueOf(0L), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(221, 75, 57)));
			parGooglePlusValue.setLeading(14);

			cellGooglePlusValue.addElement(parGooglePlusTitleValue);
			cellGooglePlusValue.addElement(parGooglePlusValue);

			tableGooglePlus.addCell(cellGooglePlusImage);
			tableGooglePlus.addCell(cellGooglePlusValue);

			cellGooglePlus.addElement(tableGooglePlus);
			
			// Instagram.
			PdfPCell cellInstagram = new PdfPCell();
			cellInstagram.setBorder(PdfPCell.NO_BORDER);
//			cellInstagram.setBorder(Rectangle.RIGHT);
//			cellInstagram.setBorderWidthRight(0.8f);
//			cellInstagram.setBorderColorRight(new BaseColor(177, 177, 177));
			cellInstagram.setPadding(0);
			cellInstagram.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cellInstagram.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cellInstagram.setFixedHeight(34);

			PdfPTable tableInstagram = new PdfPTable(2);
			tableInstagram.setTotalWidth(new float[]{21, 109});
			tableInstagram.setLockedWidth(true);

			PdfPCell cellInstagramImage = new PdfPCell();
			cellInstagramImage.setBorder(PdfPCell.NO_BORDER);
			cellInstagramImage.setPadding(0);

			Image imgInstagram = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_INSTAGRAM_PER_DAYS));
			imgInstagram.scaleAbsolute(21, 21);

			cellInstagramImage.addElement(imgInstagram);

			PdfPCell cellInstagramValue = new PdfPCell();
			cellInstagramValue.setBorder(PdfPCell.NO_BORDER);
			cellInstagramValue.setPaddingLeft(3);
			cellInstagramValue.setPaddingRight(0);
			cellInstagramValue.setPaddingTop(0);
			cellInstagramValue.setPaddingBottom(0);

			Phrase parInstagramTitleValue = new Phrase("INSTAGRAM", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parInstagramTitleValue.setLeading(7);

			Paragraph parInstagramValue = new Paragraph(monitoringReport.getPostsPerDaysNetworks().containsKey('I') ? 
					monitoringReport.getPostsPerDaysNetworks().get('I').toString() : String.valueOf(0L), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(230, 202, 104)));
			parInstagramValue.setLeading(14);

			cellInstagramValue.addElement(parInstagramTitleValue);
			cellInstagramValue.addElement(parInstagramValue);

			tableInstagram.addCell(cellInstagramImage);
			tableInstagram.addCell(cellInstagramValue);

			cellInstagram.addElement(tableInstagram);
			
			// News.
			PdfPCell cellNews = new PdfPCell();
//			cellNews.setBorder(PdfPCell.NO_BORDER);
			cellNews.setBorder(Rectangle.RIGHT);
			cellNews.setBorderWidthRight(0.8f);
			cellNews.setBorderColorRight(new BaseColor(177, 177, 177));
			cellNews.setPadding(0);
			cellNews.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cellNews.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cellNews.setFixedHeight(34);

			PdfPTable tableNews = new PdfPTable(2);
			tableNews.setTotalWidth(new float[]{21, 109});
			tableNews.setLockedWidth(true);

			PdfPCell cellNewsImage = new PdfPCell();
			cellNewsImage.setBorder(PdfPCell.NO_BORDER);
			cellNewsImage.setPadding(0);

			Image imgNews = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_NEWS_PER_DAYS));
			imgNews.scaleAbsolute(21, 21);

			cellNewsImage.addElement(imgNews);

			PdfPCell cellNewsValue = new PdfPCell();
			cellNewsValue.setBorder(PdfPCell.NO_BORDER);
			cellNewsValue.setPaddingLeft(3);
			cellNewsValue.setPaddingRight(0);
			cellNewsValue.setPaddingTop(0);
			cellNewsValue.setPaddingBottom(0);

			Phrase parNewsTitleValue = new Phrase("NOTÍCIAS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parNewsTitleValue.setLeading(7);

			Paragraph parNewsValue = new Paragraph(monitoringReport.getPostsPerDaysNetworks().containsKey('N') ? 
					monitoringReport.getPostsPerDaysNetworks().get('N').toString() : String.valueOf(0L), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(162, 162, 162)));
			parNewsValue.setLeading(14);

			cellNewsValue.addElement(parNewsTitleValue);
			cellNewsValue.addElement(parNewsValue);

			tableNews.addCell(cellNewsImage);
			tableNews.addCell(cellNewsValue);

			cellNews.addElement(tableNews);
			
			// Blogs.
			PdfPCell cellBlogs = new PdfPCell();
			cellBlogs.setBorder(PdfPCell.NO_BORDER);
//			cellBlogs.setBorder(Rectangle.RIGHT);
//			cellBlogs.setBorderWidthRight(0.8f);
//			cellBlogs.setBorderColorRight(new BaseColor(177, 177, 177));
			cellBlogs.setPadding(0);
			cellBlogs.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cellBlogs.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cellBlogs.setFixedHeight(34);

			PdfPTable tableBlogs = new PdfPTable(2);
			tableBlogs.setTotalWidth(new float[]{21, 109});
			tableBlogs.setLockedWidth(true);

			PdfPCell cellBlogsImage = new PdfPCell();
			cellBlogsImage.setBorder(PdfPCell.NO_BORDER);
			cellBlogsImage.setPadding(0);

			Image imgBlogs = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_BLOGS_PER_DAYS));
			imgBlogs.scaleAbsolute(21, 21);

			cellBlogsImage.addElement(imgBlogs);

			PdfPCell cellBlogsValue = new PdfPCell();
			cellBlogsValue.setBorder(PdfPCell.NO_BORDER);
			cellBlogsValue.setPaddingLeft(3);
			cellBlogsValue.setPaddingRight(0);
			cellBlogsValue.setPaddingTop(0);
			cellBlogsValue.setPaddingBottom(0);

			Phrase parBlogsTitleValue = new Phrase("BLOGS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parBlogsTitleValue.setLeading(7);

			Paragraph parBlogsValue = new Paragraph(monitoringReport.getPostsPerDaysNetworks().containsKey('B') ? 
					monitoringReport.getPostsPerDaysNetworks().get('B').toString() : String.valueOf(0L), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(39, 126, 193)));
			parBlogsValue.setLeading(14);

			cellBlogsValue.addElement(parBlogsTitleValue);
			cellBlogsValue.addElement(parBlogsValue);

			tableBlogs.addCell(cellBlogsImage);
			tableBlogs.addCell(cellBlogsValue);

			cellBlogs.addElement(tableBlogs);
			
			// Ghost Network.
			PdfPCell cellGhostNetwork = new PdfPCell();
			cellGhostNetwork.setBorder(Rectangle.RIGHT);
			cellGhostNetwork.setBorderWidthRight(0.8f);
			cellGhostNetwork.setBorderColorRight(new BaseColor(177, 177, 177));
			cellGhostNetwork.setPadding(0);
			cellGhostNetwork.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cellGhostNetwork.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cellGhostNetwork.setFixedHeight(34);

			PdfPTable tableGhostNetwork = new PdfPTable(2);
			tableGhostNetwork.setTotalWidth(new float[]{21, 109});
			tableGhostNetwork.setLockedWidth(true);
			
			cellGhostNetwork.addElement(tableGhostNetwork);
			
			tableNumberOfPostsNetwork.addCell(cellYoutube);
			tableNumberOfPostsNetwork.addCell(cellTwitter);
			tableNumberOfPostsNetwork.addCell(cellReclameAqui);
			tableNumberOfPostsNetwork.addCell(cellGooglePlus);
			tableNumberOfPostsNetwork.addCell(cellInstagram);
			tableNumberOfPostsNetwork.addCell(cellNews);
			tableNumberOfPostsNetwork.addCell(cellBlogs);
			tableNumberOfPostsNetwork.addCell(cellGhostNetwork);
			
			tableReportOfNetworksStats.addCell(tableNumberOfPostsNetwork);

			PdfPCell cellReportOfNetworksStats = new PdfPCell();
			cellReportOfNetworksStats.setBorder(PdfPCell.NO_BORDER);
			cellReportOfNetworksStats.setPaddingLeft(15);

			cellReportOfNetworksStats.addElement(createReportOfNetworksStatsTable());

			tableReportOfNetworksStats.addCell(cellReportOfNetworksStats);

			document.add(tableReportOfNetworksStats);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private PdfPTable createReportOfNetworksStatsTable() {

		PdfPTable tableReportOfNetworksStats = null;

		try {
			tableReportOfNetworksStats = new PdfPTable(1);
			tableReportOfNetworksStats.setTotalWidth(new float[]{135});
			tableReportOfNetworksStats.setLockedWidth(true);
			tableReportOfNetworksStats.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableReportOfNetworksStats.setSpacingBefore(5);

			// Posts volume
			PdfPCell cellPostsVolumeTitle = new PdfPCell();
			cellPostsVolumeTitle.setBorder(PdfPCell.NO_BORDER);

			Paragraph parPostsVolumeTitle = new Paragraph("MAIOR VOLUME DE POSTS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parPostsVolumeTitle.setLeading(9);

			cellPostsVolumeTitle.addElement(parPostsVolumeTitle);

			tableReportOfNetworksStats.addCell(cellPostsVolumeTitle);

			PdfPCell cellPostsVolumeNetwork = new PdfPCell();
			cellPostsVolumeNetwork.setBorder(PdfPCell.NO_BORDER);

			String highestPostsVolumeNetwork = UtilFunctions.getNetworkName(monitoringReport.getHighestPostsVolumeNetwork());

			Paragraph parPostsVolumeNetwork = new Paragraph();
			parPostsVolumeNetwork.setLeading(11);

			String imgPostsVolumeNetworkPath;
			BaseColor baseColorNetwork;	

			switch (monitoringReport.getHighestPostsVolumeNetwork()) {
			case 'G':
				imgPostsVolumeNetworkPath = br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.GOOGLEPLUS_ICON);
				baseColorNetwork = new BaseColor(50, 154 ,56);
				break;
			case 'Y':
				imgPostsVolumeNetworkPath = br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.YOUTUBE_ICON);
				baseColorNetwork = new BaseColor(190, 25, 19);
				break;
			case 'I':
				imgPostsVolumeNetworkPath = br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.INSTAGRAM_ICON);
				baseColorNetwork = new BaseColor(255, 199, 50);
				break;
			case 'R':
				imgPostsVolumeNetworkPath = br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.RECLAMEAQUI_ICON);
				baseColorNetwork = new BaseColor(198, 245 ,71);
				break;
			default:
				imgPostsVolumeNetworkPath = br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.TWITTER_ICON);
				baseColorNetwork = new BaseColor(99, 218, 238);
				break;
			}

			Image imgPostsVolumeNetwork = Image.getInstance(imgPostsVolumeNetworkPath);
			imgPostsVolumeNetwork.scaleAbsolute(7, 7);

			parPostsVolumeNetwork.add(new Chunk(imgPostsVolumeNetwork, 0, 0));

			parPostsVolumeNetwork.add(new Chunk(" " + highestPostsVolumeNetwork.toUpperCase(),
					new Font(sourceSansPro, 13, Font.NORMAL, baseColorNetwork)));

			parPostsVolumeNetwork.add(new Chunk("  " + monitoringReport.getHighestPostsVolumePercentage() + "%", 
					new Font(sourceSansPro, 13, Font.NORMAL, baseColorNetwork)));

			cellPostsVolumeNetwork.addElement(parPostsVolumeNetwork);

			tableReportOfNetworksStats.addCell(cellPostsVolumeNetwork);

			PdfPCell cellPostsVolumeNetworkDescription = new PdfPCell();
			cellPostsVolumeNetworkDescription.setBorder(PdfPCell.NO_BORDER);

			Paragraph parPostsVolumeNetworkDescription = new Paragraph();
			parPostsVolumeNetworkDescription.setLeading(7);

			parPostsVolumeNetworkDescription.add("O " + highestPostsVolumeNetwork + " é a rede com mais resultados recuperados durante o monitoramento.");
			parPostsVolumeNetworkDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellPostsVolumeNetworkDescription.addElement(parPostsVolumeNetworkDescription);

			tableReportOfNetworksStats.addCell(cellPostsVolumeNetworkDescription);

			// Posts total
			PdfPCell cellPostsTotalTitle = new PdfPCell();
			cellPostsTotalTitle.setBorder(PdfPCell.NO_BORDER);
			cellPostsTotalTitle.setNoWrap(true);
			cellPostsTotalTitle.setPaddingTop(5);

			Paragraph parPostsTotalTitle = new Paragraph("TOTAL DE POSTS ENCONTRADOS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parPostsTotalTitle.setLeading(13);

			cellPostsTotalTitle.addElement(parPostsTotalTitle);

			tableReportOfNetworksStats.addCell(cellPostsTotalTitle);

			PdfPCell cellPostsTotal = new PdfPCell();
			cellPostsTotal.setBorder(PdfPCell.NO_BORDER);

			Paragraph parPostsTotal = new Paragraph();
			parPostsTotal.setLeading(11);

			Image imgPostsTotal = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_POSTS_TOTAL));
			imgPostsTotal.scaleAbsolute(10, 10);

			parPostsTotal.add(new Chunk(imgPostsTotal, 0, 0));

			parPostsTotal.add(new Chunk(" " + monitoringReport.getPostsTotal(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(62, 129, 197))));

			cellPostsTotal.addElement(parPostsTotal);

			tableReportOfNetworksStats.addCell(cellPostsTotal);

			// Posts classified as trash.
			PdfPCell cellPostsTrashedTitle = new PdfPCell();
			cellPostsTrashedTitle.setBorder(PdfPCell.NO_BORDER);
			cellPostsTrashedTitle.setPaddingTop(5);

			Paragraph parPostsTrashedTitle = new Paragraph("TOTAL DE LIXO REMOVIDO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parPostsTrashedTitle.setLeading(13);

			cellPostsTrashedTitle.addElement(parPostsTrashedTitle);

			tableReportOfNetworksStats.addCell(cellPostsTrashedTitle);

			PdfPCell cellPostsTrashed = new PdfPCell();
			cellPostsTrashed.setBorder(PdfPCell.NO_BORDER);

			Paragraph parPostsTrashed = new Paragraph();
			parPostsTrashed.setLeading(11);

			Image imgPostsTrashed = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_POSTS_TRASHED));
			imgPostsTotal.scaleAbsolute(10, 10);

			parPostsTrashed.add(new Chunk(imgPostsTrashed, 0, 0));

			parPostsTrashed.add(new Chunk(" " + monitoringReport.getGarbagePostsTotalPercentage() + "%", 
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(209, 30, 30))));

			cellPostsTrashed.addElement(parPostsTrashed);

			tableReportOfNetworksStats.addCell(cellPostsTrashed);

			PdfPCell cellPostsTrashedDescription = new PdfPCell();
			cellPostsTrashedDescription.setBorder(PdfPCell.NO_BORDER);

			Paragraph parPostsTrashedDescription = new Paragraph();
			parPostsTrashedDescription.setLeading(7);

			parPostsTrashedDescription.add(monitoringReport.getGarbagePostsTotal() + " posts foram identificados como lixo e removidos da sua busca.");
			parPostsTrashedDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellPostsTrashedDescription.addElement(parPostsTrashedDescription);

			tableReportOfNetworksStats.addCell(cellPostsTrashedDescription);

		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tableReportOfNetworksStats;
	}

	private PdfPTable createReportOverTimeStatsTable() {

		PdfPTable tableReportOverTimeStats = null;

		try {
			tableReportOverTimeStats = new PdfPTable(1);
			tableReportOverTimeStats.setTotalWidth(new float[]{135});
			tableReportOverTimeStats.setLockedWidth(true);
			tableReportOverTimeStats.setHorizontalAlignment(Element.ALIGN_CENTER);

			// Time with more posts.
			PdfPCell cellTimeMorePostsTitle = new PdfPCell();
			cellTimeMorePostsTitle.setBorder(PdfPCell.NO_BORDER);

			Paragraph parTimeMorePostsTitle = new Paragraph("HORÁRIO COM MAIS POSTS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parTimeMorePostsTitle.setLeading(9);

			cellTimeMorePostsTitle.addElement(parTimeMorePostsTitle);

			tableReportOverTimeStats.addCell(cellTimeMorePostsTitle);

			PdfPCell cellTimeMorePosts = new PdfPCell();
			cellTimeMorePosts.setBorder(PdfPCell.NO_BORDER);

			Paragraph parTimeMorePosts = new Paragraph();
			parTimeMorePosts.setLeading(11);

			Image imgTimeMorePosts = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_POSTS_HOUR));
			imgTimeMorePosts.scaleAbsolute(10, 10);

			parTimeMorePosts.add(new Chunk(imgTimeMorePosts, 0, 0));

			String timeWithMorePostsFrom = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, monitoringReport.getTimeWithMorePosts(), 0, 0));
			String timeWithMorePostsUntil = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, monitoringReport.getTimeWithMorePosts() + 1, 0, 0));

			parTimeMorePosts.add(new Chunk(" " + timeWithMorePostsFrom + " - " + timeWithMorePostsUntil,
					new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(145, 205, 51))));

			cellTimeMorePosts.addElement(parTimeMorePosts);

			tableReportOverTimeStats.addCell(cellTimeMorePosts);

			PdfPCell cellTimeMorePostsDescription = new PdfPCell();
			cellTimeMorePostsDescription.setBorder(PdfPCell.NO_BORDER);

			Paragraph parTimeMorePostsDescription = new Paragraph();
			parTimeMorePostsDescription.setLeading(7);

			parTimeMorePostsDescription.add(monitoringReport.getTimeWithMorePostsPercentage() + "% dos posts do seu monitoramento foram encontrados entre as " + timeWithMorePostsFrom + " e " + timeWithMorePostsUntil+ "hrs.");
			parTimeMorePostsDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellTimeMorePostsDescription.addElement(parTimeMorePostsDescription);

			tableReportOverTimeStats.addCell(cellTimeMorePostsDescription);

			// Week day with more posts.
			PdfPCell cellWeekDayMorePostsTitle = new PdfPCell();
			cellWeekDayMorePostsTitle.setBorder(PdfPCell.NO_BORDER);
			cellWeekDayMorePostsTitle.setPaddingTop(5);

			Paragraph parWeekDayMorePostsTitle = new Paragraph("DIA COM MAIS POSTS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parWeekDayMorePostsTitle.setLeading(13);

			cellWeekDayMorePostsTitle.addElement(parWeekDayMorePostsTitle);

			tableReportOverTimeStats.addCell(cellWeekDayMorePostsTitle);

			PdfPCell cellWeekDayMorePosts = new PdfPCell();
			cellWeekDayMorePosts.setBorder(PdfPCell.NO_BORDER);

			Paragraph parWeekDayMorePosts = new Paragraph();
			parWeekDayMorePosts.setLeading(11);

			Image imgWeekDayMorePosts = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_POSTS_WEEK_DAY));
			imgWeekDayMorePosts.scaleAbsolute(10, 10);

			parWeekDayMorePosts.add(new Chunk(imgWeekDayMorePosts, 0, 0));

			parWeekDayMorePosts.add(new Chunk(" " + OpSocialBackApplication.utilFunctionsService.getFormattedWeekDayShort(monitoringReport.getWeekDayWithMorePosts()).toUpperCase(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(62, 129, 197))));

			cellWeekDayMorePosts.addElement(parWeekDayMorePosts);

			tableReportOverTimeStats.addCell(cellWeekDayMorePosts);

			PdfPCell cellWeekDayMorePostsDescription = new PdfPCell();
			cellWeekDayMorePostsDescription.setBorder(PdfPCell.NO_BORDER);

			Paragraph parWeekDayMorePostsDescription = new Paragraph();
			parWeekDayMorePostsDescription.setLeading(7);

			parWeekDayMorePostsDescription.add(monitoringReport.getWeekDayWithMorePostsPercentage() + "% dos posts do seu monitoramento foram encontrados na " + OpSocialBackApplication.utilFunctionsService.getFormattedWeekDay(monitoringReport.getWeekDayWithMorePosts()) + ".");
			parWeekDayMorePostsDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellWeekDayMorePostsDescription.addElement(parWeekDayMorePostsDescription);

			tableReportOverTimeStats.addCell(cellWeekDayMorePostsDescription);

		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tableReportOverTimeStats;
	}

	private Image addChartNumberOfPosts() {

		String img = chartsImages.get("chartNumberOfPosts").substring(chartsImages.get("chartNumberOfPosts").indexOf("base64,") + 7);

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

	private Image addChartSentimentAnalysis() {

		String img = chartsImages.get("chartSentimentAnalysis").substring(chartsImages.get("chartSentimentAnalysis").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(130);
			imageChart.scaleAbsoluteWidth(170);

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
	
	private Image addChartPostsUsersPerDay() {
		
		String img = chartsImages.get("chartPostsUsersPerDay").substring(chartsImages.get("chartPostsUsersPerDay").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(151);
			imageChart.scaleAbsoluteWidth(358);
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
	
	private Image addChartPostsPerDay() {
		
		String img = chartsImages.get("chartPostsPerDay").substring(chartsImages.get("chartPostsPerDay").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(151);
			imageChart.scaleAbsoluteWidth(358);
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

	private Image addChartPostsPerHour() {

		String img = chartsImages.get("chartPostsPerHour").substring(chartsImages.get("chartPostsPerHour").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(151);
			imageChart.scaleAbsoluteWidth(358);
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

	private Image addChartPostsPerWeekDay() {

		String img = chartsImages.get("chartPostsPerWeekDay").substring(chartsImages.get("chartPostsPerWeekDay").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(129);
			imageChart.scaleAbsoluteWidth(209);
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

	private void addReportOverTime() {

		try {

			PdfPTable tableReportOverTimeTitle = new PdfPTable(1);
			tableReportOverTimeTitle.setTotalWidth(new float[]{523});
			tableReportOverTimeTitle.setLockedWidth(true);
			tableReportOverTimeTitle.setHorizontalAlignment(Element.ALIGN_CENTER);

			tableReportOverTimeTitle.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableReportOverTimeTitle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			tableReportOverTimeTitle.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableReportOverTimeTitle.getDefaultCell().setBorderWidthBottom(0.8f);
			tableReportOverTimeTitle.getDefaultCell().setPaddingBottom(5);
			tableReportOverTimeTitle.getDefaultCell().setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parReportOverTimeTitle = new Paragraph("RESULTADOS ENCONTRADOS POR HORÁRIO E DIA DA SEMANA", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parReportOverTimeTitle.setAlignment(Element.ALIGN_LEFT);

			tableReportOverTimeTitle.addCell(parReportOverTimeTitle);

			document.add(tableReportOverTimeTitle);

			document.add(addChartPostsPerHour());

			PdfPTable tableReportOverTimeStats = new PdfPTable(2);
			tableReportOverTimeStats.setTotalWidth(new float[]{ 209, 150});
			tableReportOverTimeStats.setLockedWidth(true);
			tableReportOverTimeStats.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableReportOverTimeStats.getDefaultCell().setBorder(Rectangle.BOX);
			tableReportOverTimeStats.getDefaultCell().setPadding(0);
			tableReportOverTimeStats.setSpacingAfter(20);
			tableReportOverTimeStats.setSpacingBefore(10);

			PdfPCell cellChartPostsPerWeekDay = new PdfPCell();
			cellChartPostsPerWeekDay.setBorder(PdfPCell.NO_BORDER);
			cellChartPostsPerWeekDay.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellChartPostsPerWeekDay.setPaddingTop(0);

			cellChartPostsPerWeekDay.addElement(addChartPostsPerWeekDay());

			tableReportOverTimeStats.addCell(cellChartPostsPerWeekDay);

			PdfPCell cellReportOverTimeStats = new PdfPCell();
			cellReportOverTimeStats.setBorder(PdfPCell.NO_BORDER);
			cellReportOverTimeStats.setPaddingLeft(15);

			cellReportOverTimeStats.addElement(createReportOverTimeStatsTable());

			tableReportOverTimeStats.addCell(cellReportOverTimeStats);

			document.add(tableReportOverTimeStats);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void addReportOverDays() {
		
		try {
			
			document.newPage();
			
			PdfPTable tableReportOverDaysTitle = new PdfPTable(1);
			tableReportOverDaysTitle.setTotalWidth(new float[]{523});
			tableReportOverDaysTitle.setLockedWidth(true);
			tableReportOverDaysTitle.setHorizontalAlignment(Element.ALIGN_CENTER);

			tableReportOverDaysTitle.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableReportOverDaysTitle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			tableReportOverDaysTitle.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableReportOverDaysTitle.getDefaultCell().setBorderWidthBottom(0.8f);
			tableReportOverDaysTitle.getDefaultCell().setPaddingBottom(5);
			tableReportOverDaysTitle.getDefaultCell().setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parReportOverTimeTitle = new Paragraph("RESULTADOS ENCONTRADOS POR DIA", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parReportOverTimeTitle.setAlignment(Element.ALIGN_LEFT);

			tableReportOverDaysTitle.addCell(parReportOverTimeTitle);

			document.add(tableReportOverDaysTitle);

			document.add(addChartPostsPerDay());
			
			PdfPTable tableReportOverDaysInfo = new PdfPTable(2);
			tableReportOverDaysInfo.setTotalWidth(new float[]{179, 179});
			tableReportOverDaysInfo.setLockedWidth(true);
			tableReportOverDaysInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableReportOverDaysInfo.setSpacingAfter(30);
			
			// Average posts
			PdfPCell cellAveragePosts = new PdfPCell();
			cellAveragePosts.setBorder(PdfPCell.NO_BORDER);
			cellAveragePosts.setPaddingLeft(5);
			cellAveragePosts.setPaddingRight(0);
			cellAveragePosts.setPaddingBottom(0);
			cellAveragePosts.setPaddingTop(0);
			cellAveragePosts.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cellAveragePosts.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cellAveragePosts.setFixedHeight(34);

			PdfPTable tableAveragePosts = new PdfPTable(2);
			tableAveragePosts.setTotalWidth(new float[]{21, 158});
			tableAveragePosts.setLockedWidth(true);

			PdfPCell cellAveragePostsImage = new PdfPCell();
			cellAveragePostsImage.setBorder(PdfPCell.NO_BORDER);
			cellAveragePostsImage.setPadding(0);

			Image imgStatus = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_POSTS_AVERAGE));
			imgStatus.scaleAbsolute(21, 21);

			cellAveragePostsImage.addElement(imgStatus);

			PdfPCell cellAveragePostsValue = new PdfPCell();
			cellAveragePostsValue.setBorder(PdfPCell.NO_BORDER);
			cellAveragePostsValue.setPaddingLeft(3);
			cellAveragePostsValue.setPaddingRight(0);
			cellAveragePostsValue.setPaddingTop(0);
			cellAveragePostsValue.setPaddingBottom(0);

			Phrase parAveragePosts = new Phrase("MÉDIA DIÁRIA DE RESULTADOS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parAveragePosts.setLeading(7);

			Paragraph parAveragePostsValue = new Paragraph(monitoringReport.getDailyAverage().toString(), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(255, 173 ,30)));
			parAveragePostsValue.setLeading(14);

			cellAveragePostsValue.addElement(parAveragePosts);
			cellAveragePostsValue.addElement(parAveragePostsValue);

			tableAveragePosts.addCell(cellAveragePostsImage);
			tableAveragePosts.addCell(cellAveragePostsValue);

			cellAveragePosts.addElement(tableAveragePosts);
			
			// Day more posts
			PdfPCell cellDayMorePosts = new PdfPCell();
			cellDayMorePosts.setBorder(PdfPCell.NO_BORDER);
			cellDayMorePosts.setPaddingLeft(30);
			cellDayMorePosts.setPaddingRight(0);
			cellDayMorePosts.setPaddingBottom(0);
			cellDayMorePosts.setPaddingTop(0);
			cellDayMorePosts.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cellDayMorePosts.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cellDayMorePosts.setFixedHeight(34);

			PdfPTable tableDayMorePosts = new PdfPTable(2);
			tableDayMorePosts.setTotalWidth(new float[]{21, 158});
			tableDayMorePosts.setLockedWidth(true);

			PdfPCell cellDayMorePostsImage = new PdfPCell();
			cellDayMorePostsImage.setBorder(PdfPCell.NO_BORDER);
			cellDayMorePostsImage.setPadding(0);

			Image imgDayMorePosts = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.MONITORINGS_MORE_POSTS));
			imgDayMorePosts.scaleAbsolute(21, 21);

			cellDayMorePostsImage.addElement(imgDayMorePosts);

			PdfPCell cellDayMorePostsValue = new PdfPCell();
			cellDayMorePostsValue.setBorder(PdfPCell.NO_BORDER);
			cellDayMorePostsValue.setPaddingLeft(3);
			cellDayMorePostsValue.setPaddingRight(0);
			cellDayMorePostsValue.setPaddingTop(0);
			cellDayMorePostsValue.setPaddingBottom(0);

			Phrase parDayMorePosts = new Phrase("DIA COM MAIS RESULTADOS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parDayMorePosts.setLeading(7);
			
			Paragraph parDayMorePostsValue = new Paragraph(new SimpleDateFormat("dd/MM/yy").format(monitoringReport.getDayWithMorePosts()), 
					new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(159, 220, 53)));
			parDayMorePostsValue.setLeading(14);

			cellDayMorePostsValue.addElement(parDayMorePosts);
			cellDayMorePostsValue.addElement(parDayMorePostsValue);

			tableDayMorePosts.addCell(cellDayMorePostsImage);
			tableDayMorePosts.addCell(cellDayMorePostsValue);

			cellDayMorePosts.addElement(tableDayMorePosts);
			
			tableReportOverDaysInfo.addCell(cellAveragePosts);
			tableReportOverDaysInfo.addCell(cellDayMorePosts);
			
			document.add(tableReportOverDaysInfo);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addHeaderFooter() {

		HeaderFooterPDF headerFooterPDF = new HeaderFooterPDF(pdfWritter, "Relatório Monitoramento", monitoring.getReportLogo());
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
