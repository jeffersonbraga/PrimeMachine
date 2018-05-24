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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.opsocial.client.entity.report.ReportLogoDTO;
import br.com.opsocial.client.entity.report.instagram.InstagramEngagedFollowerDTO;
import br.com.opsocial.client.entity.report.instagram.InstagramInteractionRankingDTO;
import br.com.opsocial.client.entity.report.instagram.InstagramReportPostDTO;
import br.com.opsocial.client.entity.report.instagram.InstagramTopPostTagDTO;
import br.com.opsocial.client.entity.report.instagram.ReportInstagramOverviewDTO;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.pdf.HeaderFooterPDF;
import br.com.opsocial.server.utils.reports.ReportIcons;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.instagram.InstagramEngagedFollower;
import br.com.opsocial.ejb.entity.instagram.InstagramTopPostTag;

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

public class ReportInstagramOverviewPDF {

	private ReportInstagramOverviewDTO reportInstagramOverview;
	private Profile profile;

	private PdfWriter pdfWritter;
	private Document document;
	private String fileName;

	private HashMap<String, String> chartsImages;
	private java.util.List<File> tempImages;

	private static BaseFont sourceSansPro;

	private static final BaseColor BASE_COLOR_HEADER_TITLE = new BaseColor(52, 98, 144);

	public ReportInstagramOverviewPDF(ReportInstagramOverviewDTO reportInstagramOverview, Profile profile, HashMap<String, String> chartsImages) {

		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".pdf");
		this.reportInstagramOverview = reportInstagramOverview;
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

		this.tempImages = new ArrayList<File>();

		addHeaderFooter();
		this.document.open();

		addBasicInformation();
		addFollowersStats();
		addLikesStats();
		addPostsWithMoreLikes();
		addCommentsStats();
		addPostsWithMoreComments();
		addEngagedFollowers();
		addTopPostTags();
		addLikesPerHour();
		addCommentsPerHour();
		addInteractionsPerHour();

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

			String reportsDescription = "•  Relatório Instagram (Visão Geral)  -  " + 
					new SimpleDateFormat("dd/MM/yy").format(new Date(reportInstagramOverview.getDateFrom() * 1000L))
					+" a " +
					new SimpleDateFormat("dd/MM/yy").format(new Date(reportInstagramOverview.getDateUntil() * 1000L));

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

	private void addFollowersStats() {
		
		try {
			
			PdfPTable tableFollowersStats = new PdfPTable(1);
			tableFollowersStats.setTotalWidth(new float[]{523});
			tableFollowersStats.setLockedWidth(true);
			tableFollowersStats.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFollowersStats.setSpacingAfter(22);
			
			PdfPCell cellFollowersStatsTitle = new PdfPCell();
			cellFollowersStatsTitle.setBorder(Rectangle.BOTTOM);
			cellFollowersStatsTitle.setBorderWidthBottom(0.8f);
			cellFollowersStatsTitle.setPaddingBottom(5);
			cellFollowersStatsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parFollowersStatsTitle = new Paragraph("MUDANÇA DE FOLLOWERS NO PERÍODO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parFollowersStatsTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellFollowersStatsTitle.addElement(parFollowersStatsTitle);

			PdfPCell cellFollowersStatsContent = new PdfPCell();
			cellFollowersStatsContent.setBorder(PdfPCell.NO_BORDER);
			cellFollowersStatsContent.setPaddingBottom(0);
			
			PdfPTable tableFollowersStatsContent = new PdfPTable(2);
			tableFollowersStatsContent.setTotalWidth(new float[]{200, 160});
			tableFollowersStatsContent.setLockedWidth(true);
			
			// Chart Followers Trend.
			PdfPCell cellChartFollowersTrend = new PdfPCell();
			cellChartFollowersTrend.setBorder(PdfPCell.NO_BORDER);
			cellChartFollowersTrend.setPadding(0);
			
			cellChartFollowersTrend.addElement(addChartFollowersTrend());

			// Followers Stats.
			PdfPCell cellFollowersStats = new PdfPCell();
			cellFollowersStats.setBorder(PdfPCell.NO_BORDER); 
			cellFollowersStats.setPaddingTop(8);
			cellFollowersStats.setPaddingBottom(0);
			cellFollowersStats.setPaddingRight(0);
			cellFollowersStats.setPaddingLeft(30);

			PdfPTable tableFollowersStatsDesc = new PdfPTable(1);
			tableFollowersStatsDesc.setTotalWidth(new float[]{160});
			tableFollowersStatsDesc.setLockedWidth(true);

			// Total of followers.
			PdfPCell cellTotalFollowersTitle = new PdfPCell();
			cellTotalFollowersTitle.setBorder(PdfPCell.NO_BORDER);
			cellTotalFollowersTitle.setPaddingRight(0);
			cellTotalFollowersTitle.setPaddingLeft(0);
			cellTotalFollowersTitle.setPaddingTop(0);
			cellTotalFollowersTitle.setPaddingBottom(2);

			Paragraph parTotalFollowersTitle = new Paragraph("TOTAL DE SEGUIDORES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parTotalFollowersTitle.setLeading(9);

			cellTotalFollowersTitle.addElement(parTotalFollowersTitle);

			PdfPCell cellTotalFollowers = new PdfPCell();
			cellTotalFollowers.setBorder(PdfPCell.NO_BORDER);
			cellTotalFollowers.setPaddingRight(0);
			cellTotalFollowers.setPaddingLeft(0);
			cellTotalFollowers.setPaddingTop(0);
			cellTotalFollowers.setPaddingBottom(8);

			Paragraph parTotalFollowers = new Paragraph();
			parTotalFollowers.setLeading(11);

			Image imgTotalFollowers = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_FANS));
			imgTotalFollowers.scaleAbsolute(10, 10);

			parTotalFollowers.add(new Chunk(imgTotalFollowers, 0, 0));

			parTotalFollowers.add(new Chunk(" " + reportInstagramOverview.getFollowers().toString(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(53,187,221))));

			cellTotalFollowers.addElement(parTotalFollowers);
			
			// New followers.
			PdfPCell cellNewFollowersTitle = new PdfPCell();
			cellNewFollowersTitle.setBorder(PdfPCell.NO_BORDER);
			cellNewFollowersTitle.setPaddingRight(0);
			cellNewFollowersTitle.setPaddingLeft(0);
			cellNewFollowersTitle.setPaddingTop(0);
			cellNewFollowersTitle.setPaddingBottom(2);

			Paragraph parNewFollowersTitle = new Paragraph("NOVOS SEGUIDORES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parNewFollowersTitle.setLeading(9);

			cellNewFollowersTitle.addElement(parNewFollowersTitle);

			PdfPCell cellNewFollowers = new PdfPCell();
			cellNewFollowers.setBorder(PdfPCell.NO_BORDER);
			cellNewFollowers.setPaddingRight(0);
			cellNewFollowers.setPaddingLeft(0);
			cellNewFollowers.setPaddingTop(0);
			cellNewFollowers.setPaddingBottom(8);

			Paragraph parNewFollowers = new Paragraph();
			parNewFollowers.setLeading(11);

			Image imgNewFollowers = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_MAJOR_GROWTH));
			imgNewFollowers.scaleAbsolute(10, 10);

			parNewFollowers.add(new Chunk(imgNewFollowers, 0, 0));

			parNewFollowers.add(new Chunk(" " + reportInstagramOverview.getInstagramFollowerTrend().getNewFollowers(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(158, 218, 30))));

			cellNewFollowers.addElement(parNewFollowers);
			
			// Lost followers.
			PdfPCell cellLostFollowersTitle = new PdfPCell();
			cellLostFollowersTitle.setBorder(PdfPCell.NO_BORDER);
			cellLostFollowersTitle.setPaddingRight(0);
			cellLostFollowersTitle.setPaddingLeft(0);
			cellLostFollowersTitle.setPaddingTop(0);
			cellLostFollowersTitle.setPaddingBottom(2);

			Paragraph parLostFollowersTitle = new Paragraph("SEGUIDORES PERDIDOS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parLostFollowersTitle.setLeading(9);

			cellLostFollowersTitle.addElement(parLostFollowersTitle);

			PdfPCell cellLostFollowers = new PdfPCell();
			cellLostFollowers.setBorder(PdfPCell.NO_BORDER);
			cellLostFollowers.setPaddingRight(0);
			cellLostFollowers.setPaddingLeft(0);
			cellLostFollowers.setPaddingTop(0);
			cellLostFollowers.setPaddingBottom(8);

			Paragraph parLostFollowers = new Paragraph();
			parLostFollowers.setLeading(11);

			Image imgLostFollowers = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_MINOR_GROWTH));
			imgLostFollowers.scaleAbsolute(10, 10);

			parLostFollowers.add(new Chunk(imgLostFollowers, 0, 0));

			parLostFollowers.add(new Chunk(" " + reportInstagramOverview.getInstagramFollowerTrend().getUnfollowers(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(215, 54, 49))));

			cellLostFollowers.addElement(parLostFollowers);
			
			// Followers balance.
			PdfPCell cellFollowersBalanceTitle = new PdfPCell();
			cellFollowersBalanceTitle.setBorder(PdfPCell.NO_BORDER);
			cellFollowersBalanceTitle.setPaddingRight(0);
			cellFollowersBalanceTitle.setPaddingLeft(0);
			cellFollowersBalanceTitle.setPaddingTop(0);
			cellFollowersBalanceTitle.setPaddingBottom(2);

			Paragraph parFollowersBalanceTitle = new Paragraph("SALDO DE SEGUIDORES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parFollowersBalanceTitle.setLeading(9);

			cellFollowersBalanceTitle.addElement(parFollowersBalanceTitle);

			PdfPCell cellFollowersBalance = new PdfPCell();
			cellFollowersBalance.setBorder(PdfPCell.NO_BORDER);
			cellFollowersBalance.setPaddingRight(0);
			cellFollowersBalance.setPaddingLeft(0);
			cellFollowersBalance.setPaddingTop(0);
			cellFollowersBalance.setPaddingBottom(8);

			Paragraph parFollowersBalance = new Paragraph();
			parFollowersBalance.setLeading(11);

			Image imgFollowersBalance = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTA_FOLLOWERS_BALANCE));
			imgFollowersBalance.scaleAbsolute(10, 10);

			parFollowersBalance.add(new Chunk(imgFollowersBalance, 0, 0));

			parFollowersBalance.add(new Chunk(" " + reportInstagramOverview.getInstagramFollowerTrend().getBalanceFollowers(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(255, 201, 54))));

			cellFollowersBalance.addElement(parFollowersBalance);
			
			tableFollowersStatsDesc.addCell(cellTotalFollowersTitle);
			tableFollowersStatsDesc.addCell(cellTotalFollowers);
			tableFollowersStatsDesc.addCell(cellNewFollowersTitle);
			tableFollowersStatsDesc.addCell(cellNewFollowers);
			tableFollowersStatsDesc.addCell(cellLostFollowersTitle);
			tableFollowersStatsDesc.addCell(cellLostFollowers);
			tableFollowersStatsDesc.addCell(cellFollowersBalanceTitle);
			tableFollowersStatsDesc.addCell(cellFollowersBalance);
			
			cellFollowersStats.addElement(tableFollowersStatsDesc);
			
			tableFollowersStatsContent.addCell(cellChartFollowersTrend);
			tableFollowersStatsContent.addCell(cellFollowersStats);
			
			cellFollowersStatsContent.addElement(tableFollowersStatsContent);

			tableFollowersStats.addCell(cellFollowersStatsTitle);
			tableFollowersStats.addCell(cellFollowersStatsContent);
			
			document.add(tableFollowersStats);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addCommentsStats() {
		
		try {	
			
			PdfPTable tableCommentsStats = new PdfPTable(1);
			tableCommentsStats.setTotalWidth(new float[]{523});
			tableCommentsStats.setLockedWidth(true);
			tableCommentsStats.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableCommentsStats.setSpacingAfter(22);
			
			PdfPCell cellCommentsStatsTitle = new PdfPCell();
			cellCommentsStatsTitle.setBorder(Rectangle.BOTTOM);
			cellCommentsStatsTitle.setBorderWidthBottom(0.8f);
			cellCommentsStatsTitle.setPaddingBottom(5);
			cellCommentsStatsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parCommentsStatsTitle = new Paragraph("COMENTÁRIOS NO PERÍODO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parCommentsStatsTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellCommentsStatsTitle.addElement(parCommentsStatsTitle);

			PdfPCell cellCommentsStatsInfo = new PdfPCell();
			cellCommentsStatsInfo.setBorder(PdfPCell.NO_BORDER);
			cellCommentsStatsInfo.setPaddingBottom(0);
			
			PdfPTable tableCommentsStatsInfo = new PdfPTable(2);
			tableCommentsStatsInfo.setTotalWidth(new float[]{200, 160});
			tableCommentsStatsInfo.setLockedWidth(true);
			
			// Chart Comments Per Day.
			PdfPCell cellChartNumberOfComments = new PdfPCell();
			cellChartNumberOfComments.setBorder(PdfPCell.NO_BORDER);
			cellChartNumberOfComments.setPadding(0);
			
			cellChartNumberOfComments.addElement(addChartNumberOfComments());

			// Likes Stats.
			PdfPCell cellCommentsStats = new PdfPCell();
			cellCommentsStats.setBorder(PdfPCell.NO_BORDER); 
			cellCommentsStats.setPaddingTop(8);
			cellCommentsStats.setPaddingBottom(0);
			cellCommentsStats.setPaddingRight(0);
			cellCommentsStats.setPaddingLeft(30);

			PdfPTable tableCommentsStatsDesc = new PdfPTable(1);
			tableCommentsStatsDesc.setTotalWidth(new float[]{160});
			tableCommentsStatsDesc.setLockedWidth(true);
			tableCommentsStatsDesc.getDefaultCell().setPadding(0);
			tableCommentsStatsDesc.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

			// Total of comments.
			PdfPCell cellTotalCommentsTitle = new PdfPCell();
			cellTotalCommentsTitle.setBorder(PdfPCell.NO_BORDER);
			cellTotalCommentsTitle.setPaddingRight(0);
			cellTotalCommentsTitle.setPaddingLeft(0);
			cellTotalCommentsTitle.setPaddingTop(0);
			cellTotalCommentsTitle.setPaddingBottom(2);

			Paragraph parTotalCommentsTitle = new Paragraph("TOTAL DE COMENTÁRIOS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parTotalCommentsTitle.setLeading(9);

			cellTotalCommentsTitle.addElement(parTotalCommentsTitle);

			PdfPCell cellTotalComments = new PdfPCell();
			cellTotalComments.setBorder(PdfPCell.NO_BORDER);
			cellTotalComments.setPaddingRight(0);
			cellTotalComments.setPaddingLeft(0);
			cellTotalComments.setPaddingTop(0);
			cellTotalComments.setPaddingBottom(8);

			Paragraph parTotalComments = new Paragraph();
			parTotalComments.setLeading(11);

			Image imgTotalComments = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_TOTAL_COMMENTS));
			imgTotalComments.scaleAbsolute(10, 10);

			parTotalComments.add(new Chunk(imgTotalComments, 0, 0));

			parTotalComments.add(new Chunk(" " + reportInstagramOverview.getComments(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(34, 201, 237))));

			cellTotalComments.addElement(parTotalComments);
			
			// Average Comments.
			PdfPCell cellAverageCommentsTitle = new PdfPCell();
			cellAverageCommentsTitle.setBorder(PdfPCell.NO_BORDER);
			cellAverageCommentsTitle.setPaddingRight(0);
			cellAverageCommentsTitle.setPaddingLeft(0);
			cellAverageCommentsTitle.setPaddingTop(0);
			cellAverageCommentsTitle.setPaddingBottom(2);

			Paragraph parAverageCommentsTitle = new Paragraph("MÉDIA DE COMENTÁRIOS POR DIA", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parAverageCommentsTitle.setLeading(9);

			cellAverageCommentsTitle.addElement(parAverageCommentsTitle);

			PdfPCell cellAverageComments = new PdfPCell();
			cellAverageComments.setBorder(PdfPCell.NO_BORDER);
			cellAverageComments.setPaddingRight(0);
			cellAverageComments.setPaddingLeft(0);
			cellAverageComments.setPaddingTop(0);
			cellAverageComments.setPaddingBottom(2);

			Paragraph parAverageComments = new Paragraph();
			parAverageComments.setLeading(11);

			Image imgAverageComments = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_AVERAGE));
			imgAverageComments.scaleAbsolute(10, 10);

			parAverageComments.add(new Chunk(imgAverageComments, 0, 0));

			parAverageComments.add(new Chunk(" " + reportInstagramOverview.getCommentsAverage().toString().replace(".", ","),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(255, 201, 54))));

			cellAverageComments.addElement(parAverageComments);

			PdfPCell cellAverageCommentsDesc = new PdfPCell();
			cellAverageCommentsDesc.setBorder(PdfPCell.NO_BORDER);
			cellAverageCommentsDesc.setPaddingRight(0);
			cellAverageCommentsDesc.setPaddingLeft(0);
			cellAverageCommentsDesc.setPaddingTop(0);
			cellAverageCommentsDesc.setPaddingBottom(8);

			Paragraph parAverageCommentsDescription = new Paragraph();
			parAverageCommentsDescription.setLeading(7);

			String averageLikesDescription = "Seus posts tiveram " + reportInstagramOverview.getComments() + " comentários no período, com uma média de " + 
					reportInstagramOverview.getCommentsAverage().toString().replace(".", ",") + " comentários " + 
					" por dia.";

			parAverageCommentsDescription.add(averageLikesDescription);
			parAverageCommentsDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellAverageCommentsDesc.addElement(parAverageCommentsDescription);
			
			// Comments Origin.
			PdfPCell cellCommentsOriginTitle = new PdfPCell();
			cellCommentsOriginTitle.setBorder(PdfPCell.NO_BORDER);
			cellCommentsOriginTitle.setPadding(0);

			Paragraph parGrowthOriginTitle = new Paragraph("ORIGEM DOS COMENTÁRIOS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parGrowthOriginTitle.setLeading(9);

			cellCommentsOriginTitle.addElement(parGrowthOriginTitle);

			// Followers Origin.
			PdfPTable tableFollowersOrigin = new PdfPTable(2);
			tableFollowersOrigin.setTotalWidth(new float[]{115, 45});
			tableFollowersOrigin.setLockedWidth(true);
			tableFollowersOrigin.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableFollowersOrigin.setSpacingBefore(3);

			PdfPCell cellFollowersOrigin = new PdfPCell();
			cellFollowersOrigin.setBorder(PdfPCell.NO_BORDER);
			cellFollowersOrigin.setPadding(0);

			Paragraph parFollowersOrigin = new Paragraph();
			parFollowersOrigin.setLeading(11);

			Image imgFollowersOrigin = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_BY_FOLLOWERS));
			imgFollowersOrigin.scaleAbsolute(10, 10);

			parFollowersOrigin.add(new Chunk(imgFollowersOrigin, 0, 0));

			parFollowersOrigin.add(new Chunk(" SEGUIDORES",
					new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(156, 221, 53))));

			cellFollowersOrigin.addElement(parFollowersOrigin);

			PdfPCell cellFollowersOriginPerc = new PdfPCell();
			cellFollowersOriginPerc.setBorder(PdfPCell.NO_BORDER);
			cellFollowersOriginPerc.setPadding(0);
			cellFollowersOriginPerc.setHorizontalAlignment(Element.ALIGN_RIGHT);

			Paragraph parOrganicGrowthOriginPerc = new Paragraph("%", new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(156, 221, 53)));
			parOrganicGrowthOriginPerc.setLeading(11);
			parOrganicGrowthOriginPerc.setAlignment(Element.ALIGN_RIGHT);

			cellFollowersOriginPerc.addElement(parOrganicGrowthOriginPerc);

			tableFollowersOrigin.addCell(cellFollowersOrigin);
			tableFollowersOrigin.addCell(cellFollowersOriginPerc);

			// No Followers Origin.
			PdfPTable tableNoFollowersOrigin = new PdfPTable(2);
			tableNoFollowersOrigin.setTotalWidth(new float[]{115, 45});
			tableNoFollowersOrigin.setLockedWidth(true);
			tableNoFollowersOrigin.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableNoFollowersOrigin.setSpacingBefore(3);

			PdfPCell cellNoFollowersOrigin = new PdfPCell();
			cellNoFollowersOrigin.setBorder(PdfPCell.NO_BORDER);
			cellNoFollowersOrigin.setPadding(0);

			Paragraph parNoFollowersOrigin = new Paragraph();
			parNoFollowersOrigin.setLeading(11);

			Image imgPaidGrowthOrigin = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_BY_NO_FOLLOWERS));
			imgPaidGrowthOrigin.scaleAbsolute(10, 10);

			parNoFollowersOrigin.add(new Chunk(imgPaidGrowthOrigin, 0, 0));

			parNoFollowersOrigin.add(new Chunk(" NÃO SEGUIDORES",
					new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(233, 114, 43))));

			cellNoFollowersOrigin.addElement(parNoFollowersOrigin);

			PdfPCell cellNoFollowersOriginPerc = new PdfPCell();
			cellNoFollowersOriginPerc.setBorder(PdfPCell.NO_BORDER);
			cellNoFollowersOriginPerc.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellNoFollowersOriginPerc.setPadding(0);

			Paragraph parNoFollowersOriginPerc = new Paragraph("%", new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(233, 114, 43)));
			parNoFollowersOriginPerc.setLeading(11);
			parNoFollowersOriginPerc.setAlignment(Element.ALIGN_RIGHT);

			cellNoFollowersOriginPerc.addElement(parNoFollowersOriginPerc);

			tableNoFollowersOrigin.addCell(cellNoFollowersOrigin);
			tableNoFollowersOrigin.addCell(cellNoFollowersOriginPerc);

			tableCommentsStatsDesc.addCell(cellTotalCommentsTitle);
			tableCommentsStatsDesc.addCell(cellTotalComments);
			tableCommentsStatsDesc.addCell(cellAverageCommentsTitle);
			tableCommentsStatsDesc.addCell(cellAverageComments);
			tableCommentsStatsDesc.addCell(cellAverageCommentsDesc);
			tableCommentsStatsDesc.addCell(cellCommentsOriginTitle);
			tableCommentsStatsDesc.addCell(tableFollowersOrigin);
			tableCommentsStatsDesc.addCell(tableNoFollowersOrigin);
			
			cellCommentsStats.addElement(tableCommentsStatsDesc);
			
			tableCommentsStatsInfo.addCell(cellChartNumberOfComments);
			tableCommentsStatsInfo.addCell(cellCommentsStats);
			
			cellCommentsStatsInfo.addElement(tableCommentsStatsInfo);

			tableCommentsStats.addCell(cellCommentsStatsTitle);
			tableCommentsStats.addCell(cellCommentsStatsInfo);
			
			document.add(tableCommentsStats);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addLikesStats() {
		
		try {
			
			PdfPTable tableLikesStats = new PdfPTable(1);
			tableLikesStats.setTotalWidth(new float[]{523});
			tableLikesStats.setLockedWidth(true);
			tableLikesStats.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableLikesStats.setSpacingAfter(22);
			
			PdfPCell cellLikesStatsTitle = new PdfPCell();
			cellLikesStatsTitle.setBorder(Rectangle.BOTTOM);
			cellLikesStatsTitle.setBorderWidthBottom(0.8f);
			cellLikesStatsTitle.setPaddingBottom(5);
			cellLikesStatsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parLikesStatsTitle = new Paragraph("LIKES NO PERÍODO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parLikesStatsTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellLikesStatsTitle.addElement(parLikesStatsTitle);

			PdfPCell cellLikesStatsInfo = new PdfPCell();
			cellLikesStatsInfo.setBorder(PdfPCell.NO_BORDER);
			cellLikesStatsInfo.setPaddingBottom(0);
			
			PdfPTable tableLikesStatsInfo = new PdfPTable(2);
			tableLikesStatsInfo.setTotalWidth(new float[]{200, 160});
			tableLikesStatsInfo.setLockedWidth(true);
			
			// Chart Likes Per Day.
			PdfPCell cellChartNumberOfLikes = new PdfPCell();
			cellChartNumberOfLikes.setBorder(PdfPCell.NO_BORDER);
			cellChartNumberOfLikes.setPadding(0);
			
			cellChartNumberOfLikes.addElement(addChartNumberOfLikes());

			// Likes Stats.
			PdfPCell cellLikesStats = new PdfPCell();
			cellLikesStats.setBorder(PdfPCell.NO_BORDER); 
			cellLikesStats.setPaddingTop(8);
			cellLikesStats.setPaddingBottom(0);
			cellLikesStats.setPaddingRight(0);
			cellLikesStats.setPaddingLeft(30);

			PdfPTable tableLikesStatsDesc = new PdfPTable(1);
			tableLikesStatsDesc.setTotalWidth(new float[]{160});
			tableLikesStatsDesc.setLockedWidth(true);
			tableLikesStatsDesc.getDefaultCell().setPadding(0);
			tableLikesStatsDesc.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

			// Total of likes.
			PdfPCell cellTotalLikesTitle = new PdfPCell();
			cellTotalLikesTitle.setBorder(PdfPCell.NO_BORDER);
			cellTotalLikesTitle.setPaddingRight(0);
			cellTotalLikesTitle.setPaddingLeft(0);
			cellTotalLikesTitle.setPaddingTop(0);
			cellTotalLikesTitle.setPaddingBottom(2);

			Paragraph parTotalLikesTitle = new Paragraph("TOTAL DE LIKES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parTotalLikesTitle.setLeading(9);

			cellTotalLikesTitle.addElement(parTotalLikesTitle);

			PdfPCell cellTotalLikes = new PdfPCell();
			cellTotalLikes.setBorder(PdfPCell.NO_BORDER);
			cellTotalLikes.setPaddingRight(0);
			cellTotalLikes.setPaddingLeft(0);
			cellTotalLikes.setPaddingTop(0);
			cellTotalLikes.setPaddingBottom(8);

			Paragraph parTotalLikes = new Paragraph();
			parTotalLikes.setLeading(11);

			Image imgTotalLikes = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_TOTAL_LIKES));
			imgTotalLikes.scaleAbsolute(10, 10);

			parTotalLikes.add(new Chunk(imgTotalLikes, 0, 0));

			parTotalLikes.add(new Chunk(" " + reportInstagramOverview.getLikes(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(243, 72, 72))));

			cellTotalLikes.addElement(parTotalLikes);
			
			// Average Likes.
			PdfPCell cellAverageLikesTitle = new PdfPCell();
			cellAverageLikesTitle.setBorder(PdfPCell.NO_BORDER);
			cellAverageLikesTitle.setPaddingRight(0);
			cellAverageLikesTitle.setPaddingLeft(0);
			cellAverageLikesTitle.setPaddingTop(0);
			cellAverageLikesTitle.setPaddingBottom(2);

			Paragraph parAverageLikesTitle = new Paragraph("MÉDIA DE LIKES POR DIA", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parAverageLikesTitle.setLeading(9);

			cellAverageLikesTitle.addElement(parAverageLikesTitle);

			PdfPCell cellAverageLikes = new PdfPCell();
			cellAverageLikes.setBorder(PdfPCell.NO_BORDER);
			cellAverageLikes.setPaddingRight(0);
			cellAverageLikes.setPaddingLeft(0);
			cellAverageLikes.setPaddingTop(0);
			cellAverageLikes.setPaddingBottom(2);

			Paragraph parAverageLikes = new Paragraph();
			parAverageLikes.setLeading(11);

			Image imgAverageLikes = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_AVERAGE));
			imgAverageLikes.scaleAbsolute(10, 10);

			parAverageLikes.add(new Chunk(imgAverageLikes, 0, 0));

			parAverageLikes.add(new Chunk(" " + reportInstagramOverview.getLikesAverage().toString().replace(".", ","),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(255, 201, 54))));

			cellAverageLikes.addElement(parAverageLikes);

			PdfPCell cellAverageLikesDesc = new PdfPCell();
			cellAverageLikesDesc.setBorder(PdfPCell.NO_BORDER);
			cellAverageLikesDesc.setPaddingRight(0);
			cellAverageLikesDesc.setPaddingLeft(0);
			cellAverageLikesDesc.setPaddingTop(0);
			cellAverageLikesDesc.setPaddingBottom(8);

			Paragraph parAverageLikesDescription = new Paragraph();
			parAverageLikesDescription.setLeading(7);

			String averageLikesDescription = "Seus posts tiveram " + reportInstagramOverview.getLikes() + " likes no período, com uma média de " + 
					reportInstagramOverview.getLikesAverage().toString().replace(".", ",") + " likes" + 
					" por dia.";

			parAverageLikesDescription.add(averageLikesDescription);
			parAverageLikesDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellAverageLikesDesc.addElement(parAverageLikesDescription);
			
			// Likes Origin.
			PdfPCell cellLikesOriginTitle = new PdfPCell();
			cellLikesOriginTitle.setBorder(PdfPCell.NO_BORDER);
			cellLikesOriginTitle.setPadding(0);

			Paragraph parGrowthOriginTitle = new Paragraph("ORIGEM DOS LIKES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parGrowthOriginTitle.setLeading(9);

			cellLikesOriginTitle.addElement(parGrowthOriginTitle);

			// Followers Origin.
			PdfPTable tableFollowersOrigin = new PdfPTable(2);
			tableFollowersOrigin.setTotalWidth(new float[]{115, 45});
			tableFollowersOrigin.setLockedWidth(true);
			tableFollowersOrigin.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableFollowersOrigin.setSpacingBefore(3);

			PdfPCell cellFollowersOrigin = new PdfPCell();
			cellFollowersOrigin.setBorder(PdfPCell.NO_BORDER);
			cellFollowersOrigin.setPadding(0);

			Paragraph parFollowersOrigin = new Paragraph();
			parFollowersOrigin.setLeading(11);

			Image imgFollowersOrigin = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_BY_FOLLOWERS));
			imgFollowersOrigin.scaleAbsolute(10, 10);

			parFollowersOrigin.add(new Chunk(imgFollowersOrigin, 0, 0));

			parFollowersOrigin.add(new Chunk(" SEGUIDORES",
					new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(156, 221, 53))));

			cellFollowersOrigin.addElement(parFollowersOrigin);

			PdfPCell cellFollowersOriginPerc = new PdfPCell();
			cellFollowersOriginPerc.setBorder(PdfPCell.NO_BORDER);
			cellFollowersOriginPerc.setPadding(0);
			cellFollowersOriginPerc.setHorizontalAlignment(Element.ALIGN_RIGHT);

			Paragraph parOrganicGrowthOriginPerc = new Paragraph("%", new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(156, 221, 53)));
			parOrganicGrowthOriginPerc.setLeading(11);
			parOrganicGrowthOriginPerc.setAlignment(Element.ALIGN_RIGHT);

			cellFollowersOriginPerc.addElement(parOrganicGrowthOriginPerc);

			tableFollowersOrigin.addCell(cellFollowersOrigin);
			tableFollowersOrigin.addCell(cellFollowersOriginPerc);

			// No Followers Origin.
			PdfPTable tableNoFollowersOrigin = new PdfPTable(2);
			tableNoFollowersOrigin.setTotalWidth(new float[]{115, 45});
			tableNoFollowersOrigin.setLockedWidth(true);
			tableNoFollowersOrigin.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableNoFollowersOrigin.setSpacingBefore(3);

			PdfPCell cellNoFollowersOrigin = new PdfPCell();
			cellNoFollowersOrigin.setBorder(PdfPCell.NO_BORDER);
			cellNoFollowersOrigin.setPadding(0);

			Paragraph parNoFollowersOrigin = new Paragraph();
			parNoFollowersOrigin.setLeading(11);

			Image imgPaidGrowthOrigin = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_BY_NO_FOLLOWERS));
			imgPaidGrowthOrigin.scaleAbsolute(10, 10);

			parNoFollowersOrigin.add(new Chunk(imgPaidGrowthOrigin, 0, 0));

			parNoFollowersOrigin.add(new Chunk(" NÃO SEGUIDORES",
					new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(233, 114, 43))));

			cellNoFollowersOrigin.addElement(parNoFollowersOrigin);

			PdfPCell cellNoFollowersOriginPerc = new PdfPCell();
			cellNoFollowersOriginPerc.setBorder(PdfPCell.NO_BORDER);
			cellNoFollowersOriginPerc.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellNoFollowersOriginPerc.setPadding(0);

			Paragraph parNoFollowersOriginPerc = new Paragraph("%", new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(233, 114, 43)));
			parNoFollowersOriginPerc.setLeading(11);
			parNoFollowersOriginPerc.setAlignment(Element.ALIGN_RIGHT);

			cellNoFollowersOriginPerc.addElement(parNoFollowersOriginPerc);

			tableNoFollowersOrigin.addCell(cellNoFollowersOrigin);
			tableNoFollowersOrigin.addCell(cellNoFollowersOriginPerc);

			tableLikesStatsDesc.addCell(cellTotalLikesTitle);
			tableLikesStatsDesc.addCell(cellTotalLikes);
			tableLikesStatsDesc.addCell(cellAverageLikesTitle);
			tableLikesStatsDesc.addCell(cellAverageLikes);
			tableLikesStatsDesc.addCell(cellAverageLikesDesc);
			tableLikesStatsDesc.addCell(cellLikesOriginTitle);
			tableLikesStatsDesc.addCell(tableFollowersOrigin);
			tableLikesStatsDesc.addCell(tableNoFollowersOrigin);
			
			cellLikesStats.addElement(tableLikesStatsDesc);
			
			tableLikesStatsInfo.addCell(cellChartNumberOfLikes);
			tableLikesStatsInfo.addCell(cellLikesStats);
			
			cellLikesStatsInfo.addElement(tableLikesStatsInfo);

			tableLikesStats.addCell(cellLikesStatsTitle);
			tableLikesStats.addCell(cellLikesStatsInfo);
			
			document.add(tableLikesStats);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addPostsWithMoreLikes() {
		
		try {
			
			PdfPTable tablePostsWithMoreLikes = new PdfPTable(1);
			tablePostsWithMoreLikes.setTotalWidth(new float[]{523});
			tablePostsWithMoreLikes.setLockedWidth(true);
			tablePostsWithMoreLikes.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablePostsWithMoreLikes.setSpacingAfter(200);
			
			PdfPCell cellPostsWithMoreLikesTitle = new PdfPCell();
			cellPostsWithMoreLikesTitle.setBorder(Rectangle.BOTTOM);
			cellPostsWithMoreLikesTitle.setBorderWidthBottom(0.8f);
			cellPostsWithMoreLikesTitle.setPaddingBottom(5);
			cellPostsWithMoreLikesTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parPostsWithMoreLikesTitle = new Paragraph("POSTS COM MAIS LIKES NO PERÍODO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parPostsWithMoreLikesTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellPostsWithMoreLikesTitle.addElement(parPostsWithMoreLikesTitle);
			
			int size = reportInstagramOverview.getPostsWithMoreLikes().size();
			
			if(size > 0) {
				
				addPostWithMoreLikes(reportInstagramOverview.getPostsWithMoreLikes().get(0));
				
				if(size > 1) {
					
					float tableTotalWidth = 0.0F;
					float imageTop = 0.0F;
					float imageLeft = 0.0F;
					float likesTop = 0.0F;
					float likesLeft = 0.0F;
					
					for(int i = 1; i < size; i++) {
						
						float extraWidth = 0F;
						if(reportInstagramOverview.getPostsWithMoreLikes().get(i).getLikes() < 10) {
							extraWidth = 3F;
						}
						
						switch (i) {
						case 1:
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramOverview.getPostsWithMoreLikes().get(i).getLikes().toString()).getWidthPoint() + extraWidth;
							imageTop = 654;
							imageLeft = 316;
							likesTop = 673;
							likesLeft = 316 + (69 / 2);
							
							break;
						case 2: 
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramOverview.getPostsWithMoreLikes().get(i).getLikes().toString()).getWidthPoint() + extraWidth;
							imageTop = 654;
							imageLeft = 316 + 79;
							likesTop = 673;
							likesLeft = 316 + 79 + (69 / 2);
							
							break;
						case 3: 
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramOverview.getPostsWithMoreLikes().get(i).getLikes().toString()).getWidthPoint() + extraWidth;
							imageTop = 654 - 83;
							imageLeft = 316;
							likesTop = 670 - 80;
							likesLeft = 316 + (69 / 2);
							
							break;
						case 4: 
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramOverview.getPostsWithMoreLikes().get(i).getLikes().toString()).getWidthPoint() + extraWidth;
							imageTop = 654 - 83;
							imageLeft = 316 + 79;
							likesTop = 670 - 80;
							likesLeft = 316 + 79 + (69 / 2);
							
							break;
						default:
							
							break;
						}
						
						try {
							
							PdfPTable tablePostsLikes = new PdfPTable(1);
							tablePostsLikes.setTotalWidth(new float[]{tableTotalWidth});
							tablePostsLikes.setLockedWidth(true);
							
							// Create and add an Image
							Image img = Image.getInstance(reportInstagramOverview.getPostsWithMoreLikes().get(i).getPictureUrl());
							img.scaleAbsolute(69, 69);
							img.setAbsolutePosition(imageLeft, imageTop);
							document.add(img);
							
							PdfPCell cellTotalLikes = new PdfPCell();
							cellTotalLikes.setBorder(PdfPCell.NO_BORDER);
							cellTotalLikes.setPadding(3);
							cellTotalLikes.setBackgroundColor(new BaseColor(255, 255, 255));
							cellTotalLikes.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

							Paragraph parTotalLikes = new Paragraph();
							parTotalLikes.setLeading(9);

							Image imgTotalLikes = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_POSTS_LIKES));
							imgTotalLikes.scaleAbsolute(8, 8);

							parTotalLikes.add(new Chunk(imgTotalLikes, 0, 0));

							Chunk cnkTotalLikes = new Chunk(" " + reportInstagramOverview.getPostsWithMoreLikes().get(i).getLikes(),
									new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(93, 93, 94)));
							cnkTotalLikes.setTextRise(1.3F);
							
							parTotalLikes.add(cnkTotalLikes);

							cellTotalLikes.addElement(parTotalLikes);
							
							tablePostsLikes.addCell(cellTotalLikes);
							
							tablePostsLikes.writeSelectedRows(0, -1, likesLeft, likesTop, pdfWritter.getDirectContent());
							
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
				}
				
			}
			
			tablePostsWithMoreLikes.addCell(cellPostsWithMoreLikesTitle);
			
			document.add(tablePostsWithMoreLikes);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addPostWithMoreLikes(InstagramReportPostDTO instagramReportPost) {
		
        try {
        	
        	float extraWidth = 3;
			if(instagramReportPost.getComments() < 10) {
				extraWidth = extraWidth + 3;
			}
        	
        	PdfPTable tablePostsWithMoreLikes = new PdfPTable(1);
			tablePostsWithMoreLikes.setTotalWidth(new float[]{8 + new Chunk(instagramReportPost.getLikes().toString()).getWidthPoint() + extraWidth});
			tablePostsWithMoreLikes.setLockedWidth(true);
        	
        	document.newPage();
        	
			// Create and add an Image
			Image img = Image.getInstance(instagramReportPost.getPictureUrl());
			img.scaleAbsolute(153, 153);
			img.setAbsolutePosition(
			    img.getScaledWidth(),
			    570);
			document.add(img);
	        
			PdfPCell cellTotalLikes = new PdfPCell();
			cellTotalLikes.setBorder(PdfPCell.NO_BORDER);
			cellTotalLikes.setPadding(3);
			cellTotalLikes.setBackgroundColor(new BaseColor(255, 255, 255));
			cellTotalLikes.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

			Paragraph parTotalLikes = new Paragraph();
			parTotalLikes.setLeading(9);

			Image imgTotalLikes = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_POSTS_LIKES));
			imgTotalLikes.scaleAbsolute(8, 8);

			parTotalLikes.add(new Chunk(imgTotalLikes, 0, 0));

			Chunk cnkTotalLikes = new Chunk(" " + instagramReportPost.getLikes(),
					new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(93, 93, 94)));
			cnkTotalLikes.setTextRise(1.3F);
			
			parTotalLikes.add(cnkTotalLikes);

			cellTotalLikes.addElement(parTotalLikes);
			
			tablePostsWithMoreLikes.addCell(cellTotalLikes);
	        
	        tablePostsWithMoreLikes.writeSelectedRows(0, -1, img.getScaledWidth() + (img.getScaledWidth() / 2), 590, pdfWritter.getDirectContent());
			
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addPostsWithMoreComments() {
		
		try {
			
			PdfPTable tablePostsWithMoreComments = new PdfPTable(1);
			tablePostsWithMoreComments.setTotalWidth(new float[]{523});
			tablePostsWithMoreComments.setLockedWidth(true);
			tablePostsWithMoreComments.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell cellPostsWithMoreCommentsTitle = new PdfPCell();
			cellPostsWithMoreCommentsTitle.setBorder(Rectangle.BOTTOM);
			cellPostsWithMoreCommentsTitle.setBorderWidthBottom(0.8f);
			cellPostsWithMoreCommentsTitle.setPaddingBottom(5);
			cellPostsWithMoreCommentsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parPostsWithMoreLikesTitle = new Paragraph("POSTS COM MAIS COMENTÁRIOS NO PERÍODO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parPostsWithMoreLikesTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellPostsWithMoreCommentsTitle.addElement(parPostsWithMoreLikesTitle);
			
			int size = reportInstagramOverview.getPostsWithMoreComments().size();
			
			if(size > 0) {
				
				addPostWithMoreComments(reportInstagramOverview.getPostsWithMoreComments().get(0));
				
				if(size > 1) {
					
					float tableTotalWidth = 0.0F;
					float imageTop = 0.0F;
					float imageLeft = 0.0F;
					float likesTop = 0.0F;
					float likesLeft = 0.0F;
					
					for(int i = 1; i < size; i++) {
						
						float extraWidth = 0F;
						if(reportInstagramOverview.getPostsWithMoreComments().get(i).getComments() < 10) {
							extraWidth = 3F;
						}
						
						switch (i) {
						case 1:
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramOverview.getPostsWithMoreComments().get(i).getComments().toString()).getWidthPoint() + extraWidth;
							imageTop = 229;
							imageLeft = 316;
							likesTop = 248;
							likesLeft = 316 + (69 / 2);
							
							break;
						case 2: 
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramOverview.getPostsWithMoreComments().get(i).getComments().toString()).getWidthPoint() + extraWidth;
							imageTop = 229;
							imageLeft = 316 + 79;
							likesTop = 248;
							likesLeft = 316 + 79 + (69 / 2);
							
							break;
						case 3: 
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramOverview.getPostsWithMoreComments().get(i).getComments().toString()).getWidthPoint() + extraWidth;
							imageTop = 229 - 83;
							imageLeft = 316;
							likesTop = 245 - 80;
							likesLeft = 316 + (69 / 2);
							
							break;
						case 4: 
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramOverview.getPostsWithMoreComments().get(i).getComments().toString()).getWidthPoint() + extraWidth;
							imageTop = 229 - 83;
							imageLeft = 316 + 79;
							likesTop = 245 - 80;
							likesLeft = 316 + 79 + (69 / 2);
							
							break;
						default:
							
							break;
						}
						
						try {
							
							PdfPTable tablePostsComments = new PdfPTable(1);
							tablePostsComments.setTotalWidth(new float[]{tableTotalWidth});
							tablePostsComments.setLockedWidth(true);
							
							// Create and add an Image
							Image img = Image.getInstance(reportInstagramOverview.getPostsWithMoreComments().get(i).getPictureUrl());
							img.scaleAbsolute(69, 69);
							img.setAbsolutePosition(imageLeft, imageTop);
							document.add(img);
							
							PdfPCell cellTotalComments = new PdfPCell();
							cellTotalComments.setBorder(PdfPCell.NO_BORDER);
							cellTotalComments.setPadding(3);
							cellTotalComments.setBackgroundColor(new BaseColor(255, 255, 255));
							cellTotalComments.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

							Paragraph parTotalComments = new Paragraph();
							parTotalComments.setLeading(9);

							Image imgTotalComments = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_POSTS_COMMENTS));
							imgTotalComments.scaleAbsolute(8, 8);

							parTotalComments.add(new Chunk(imgTotalComments, 0, 0));

							Chunk cnkTotalComments = new Chunk(" " + reportInstagramOverview.getPostsWithMoreComments().get(i).getComments(),
									new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(93, 93, 94)));
							cnkTotalComments.setTextRise(0.8F);
							
							parTotalComments.add(cnkTotalComments);

							cellTotalComments.addElement(parTotalComments);
							
							tablePostsComments.addCell(cellTotalComments);
							
							tablePostsComments.writeSelectedRows(0, -1, likesLeft, likesTop, pdfWritter.getDirectContent());
							
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
				}
				
			}
			
			tablePostsWithMoreComments.addCell(cellPostsWithMoreCommentsTitle);
			
			document.add(tablePostsWithMoreComments);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addPostWithMoreComments(InstagramReportPostDTO instagramReportPost) {
		
		try {
			
			float extraWidth = 3;
			if(instagramReportPost.getComments() < 10) {
				extraWidth = extraWidth + 3;
			}
        	
        	PdfPTable tablePostsWithMoreComments = new PdfPTable(1);
			tablePostsWithMoreComments.setTotalWidth(new float[]{8 + new Chunk(instagramReportPost.getComments().toString()).getWidthPoint() + extraWidth});
			tablePostsWithMoreComments.setLockedWidth(true);
        	
			// Create and add an Image
			Image img = Image.getInstance(instagramReportPost.getPictureUrl());
			img.scaleAbsolute(153, 153);
			img.setAbsolutePosition(
			    img.getScaledWidth(),
			    145);
			document.add(img);
	        
			PdfPCell cellTotalComments = new PdfPCell();
			cellTotalComments.setBorder(PdfPCell.NO_BORDER);
			cellTotalComments.setPadding(3);
			cellTotalComments.setBackgroundColor(new BaseColor(255, 255, 255));
			cellTotalComments.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

			Paragraph parTotalComments = new Paragraph();
			parTotalComments.setLeading(9);

			Image imgTotalComments = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_POSTS_COMMENTS));
			imgTotalComments.scaleAbsolute(8, 8);

			parTotalComments.add(new Chunk(imgTotalComments, 0, 0));

			Chunk cnkTotalComments = new Chunk(" " + instagramReportPost.getComments(),
					new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(93, 93, 94)));
			cnkTotalComments.setTextRise(0.8F);
			
			parTotalComments.add(cnkTotalComments);

			cellTotalComments.addElement(parTotalComments);
			
			tablePostsWithMoreComments.addCell(cellTotalComments);
	        
	        tablePostsWithMoreComments.writeSelectedRows(0, -1, img.getScaledWidth() + (img.getScaledWidth() / 2), 165, pdfWritter.getDirectContent());
			
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addEngagedFollowers() {
		
		try {
			
			document.newPage();
			
			PdfPTable tableEngagedFollowersTitle = new PdfPTable(1);
			tableEngagedFollowersTitle.setTotalWidth(new float[]{523});
			tableEngagedFollowersTitle.setLockedWidth(true);
			tableEngagedFollowersTitle.setHorizontalAlignment(Element.ALIGN_CENTER);

			tableEngagedFollowersTitle.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableEngagedFollowersTitle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			tableEngagedFollowersTitle.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableEngagedFollowersTitle.getDefaultCell().setBorderWidthBottom(0.8f);
			tableEngagedFollowersTitle.getDefaultCell().setPaddingBottom(5);
			tableEngagedFollowersTitle.getDefaultCell().setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parEngagedFollowersTitle = new Paragraph("SEGUIDORES MAIS ENGAJADOS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parEngagedFollowersTitle.setAlignment(Element.ALIGN_LEFT);

			tableEngagedFollowersTitle.addCell(parEngagedFollowersTitle);

			PdfPTable tableEngagedFollowers = new PdfPTable(2);
			tableEngagedFollowers.setTotalWidth(new float[]{ 170, 170});
			tableEngagedFollowers.setLockedWidth(true);
			tableEngagedFollowers.setSpacingBefore(8);
			tableEngagedFollowers.setSpacingAfter(22);
			
			// Most engaged followers first column.
			PdfPCell cellEngagedFollowersFirst = new PdfPCell();
			cellEngagedFollowersFirst.setBorder(PdfPCell.NO_BORDER);
			cellEngagedFollowersFirst.setPaddingRight(20);
			cellEngagedFollowersFirst.setPaddingLeft(0);
			cellEngagedFollowersFirst.setPaddingTop(0);
			cellEngagedFollowersFirst.setPaddingBottom(0);
			
			PdfPTable tableEngagedFollowersFirst = new PdfPTable(1);
			tableEngagedFollowersFirst.setTotalWidth(new float[]{170});
			tableEngagedFollowersFirst.setLockedWidth(true);
			
			PdfPCell cellEngagedFollowersFirstTable = new PdfPCell();
			cellEngagedFollowersFirstTable.setBorder(PdfPCell.NO_BORDER);
			cellEngagedFollowersFirstTable.setPadding(0);
			
			PdfPTable tableEngagedFollowersFirstTable = new PdfPTable(3);
			tableEngagedFollowersFirstTable.setTotalWidth(new float[]{19, 101, 50});
			tableEngagedFollowersFirstTable.setLockedWidth(true);
			tableEngagedFollowersFirstTable.setSpacingBefore(3);
			tableEngagedFollowersFirstTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableEngagedFollowersFirstTable.getDefaultCell().setPadding(0);
			
			for (int i = 0; i < reportInstagramOverview.getMostEngagedFollowers().size(); i++) {
				
				if(i == 50) {
					break;
				}
				
				addEngagedFollower(reportInstagramOverview.getMostEngagedFollowers().get(i), tableEngagedFollowersFirstTable, i+1);
			}
			
			cellEngagedFollowersFirstTable.addElement(tableEngagedFollowersFirstTable);
			
			tableEngagedFollowersFirst.addCell(cellEngagedFollowersFirstTable);
			
			cellEngagedFollowersFirst.addElement(tableEngagedFollowersFirst);
			
			// Most engaged followers second column.
			PdfPCell cellEngagedFollowersSecond = new PdfPCell();
			cellEngagedFollowersSecond.setBorder(PdfPCell.NO_BORDER);
			cellEngagedFollowersSecond.setPadding(0);

			PdfPTable tableEngagedFollowersSecond = new PdfPTable(1);
			tableEngagedFollowersSecond.setTotalWidth(new float[]{170});
			tableEngagedFollowersSecond.setLockedWidth(true);

			PdfPCell cellEngagedFollowersSecondTable = new PdfPCell();
			cellEngagedFollowersSecondTable.setBorder(PdfPCell.NO_BORDER);
			cellEngagedFollowersSecondTable.setPadding(0);

			PdfPTable tableEngagedFollowersSecondTable = new PdfPTable(3);
			tableEngagedFollowersSecondTable.setTotalWidth(new float[]{19, 101, 50});
			tableEngagedFollowersSecondTable.setLockedWidth(true);
			tableEngagedFollowersSecondTable.setSpacingBefore(3);
			tableEngagedFollowersSecondTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableEngagedFollowersSecondTable.getDefaultCell().setPadding(0);

			for (int i = 50; i < reportInstagramOverview.getMostEngagedFollowers().size(); i++) {
				addEngagedFollower(reportInstagramOverview.getMostEngagedFollowers().get(i), tableEngagedFollowersSecondTable, i+1);
			}

			cellEngagedFollowersSecondTable.addElement(tableEngagedFollowersSecondTable);

			tableEngagedFollowersSecond.addCell(cellEngagedFollowersSecondTable);

			cellEngagedFollowersSecond.addElement(tableEngagedFollowersSecond);
			
			tableEngagedFollowers.addCell(cellEngagedFollowersFirst);
			tableEngagedFollowers.addCell(cellEngagedFollowersSecond);
			
			document.add(tableEngagedFollowersTitle);
			document.add(tableEngagedFollowers);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
private void addEngagedFollower(InstagramEngagedFollower instagramEngagedFollower, PdfPTable tableEngagedFollowers, int position) {
		
		try {
			
			PdfPCell cellEngagedFollower = new PdfPCell();
			cellEngagedFollower.setBorder(Rectangle.BOTTOM);
			cellEngagedFollower.setBorderWidthBottom(0.8f);
			cellEngagedFollower.setBorderColorBottom(new BaseColor(239, 239, 240));
			cellEngagedFollower.setPadding(3);
			cellEngagedFollower.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			URL url;
			Image imgEngagedFollower;
			
			try {
				
				url = new URL(instagramEngagedFollower.getInstagramFollower().getProfilePicture().replace("https", "http"));
				imgEngagedFollower = Image.getInstance(url);
				
			} catch (Exception e) {
				imgEngagedFollower = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.AVATAR));
				e.printStackTrace();
			}
	
			imgEngagedFollower.scaleAbsolute(16, 16);
			
			cellEngagedFollower.addElement(imgEngagedFollower);
			
			tableEngagedFollowers.addCell(cellEngagedFollower);
			
			PdfPCell cellEngagedFollowerName = new PdfPCell();
			cellEngagedFollowerName.setBorder(Rectangle.BOTTOM);
			cellEngagedFollowerName.setBorderWidthBottom(0.8f);
			cellEngagedFollowerName.setBorderColorBottom(new BaseColor(239, 239, 240));
			cellEngagedFollowerName.setPaddingRight(3);
			cellEngagedFollowerName.setPaddingTop(3);
			cellEngagedFollowerName.setPaddingBottom(3);
			cellEngagedFollowerName.setPaddingLeft(5);
			cellEngagedFollowerName.setVerticalAlignment(Element.ALIGN_MIDDLE);

			Phrase phrEngagedFollowerName = new Phrase(new Chunk((position) +". " + StringUtils.abbreviate(instagramEngagedFollower.getInstagramFollower().getUserName().toUpperCase(), 15), 
					new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(121, 121, 121))));
			phrEngagedFollowerName.setLeading(8);
			
			cellEngagedFollowerName.addElement(phrEngagedFollowerName);
			
			tableEngagedFollowers.addCell(cellEngagedFollowerName);
			
			PdfPTable tableEngagedFollowerStats = new PdfPTable(2);
			tableEngagedFollowerStats.setTotalWidth(new float[]{25f, 25f});
			tableEngagedFollowerStats.setLockedWidth(true);
			
			PdfPCell cellEngagedFollowerComments = new PdfPCell();
			cellEngagedFollowerComments.setBorder(Rectangle.BOTTOM);
			cellEngagedFollowerComments.setBorderWidthBottom(0.8f);
			cellEngagedFollowerComments.setBorderColorBottom(new BaseColor(239, 239, 240));
			cellEngagedFollowerComments.setPadding(3);
			cellEngagedFollowerComments.setVerticalAlignment(Element.ALIGN_MIDDLE);

			Paragraph parTotalComments = new Paragraph();
			parTotalComments.setLeading(9);

			Image imgTotalComments = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_POSTS_COMMENTS_ENG));
			imgTotalComments.scaleAbsolute(8, 8);

			parTotalComments.add(new Chunk(imgTotalComments, 0, 0));

			Chunk cnkTotalComments = new Chunk(" " + instagramEngagedFollower.getComments() + "  ",
					new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(93, 93, 94)));
			cnkTotalComments.setTextRise(1.3F);
			
			parTotalComments.add(cnkTotalComments);

			cellEngagedFollowerComments.addElement(parTotalComments);
			
			PdfPCell cellEngagedFollowerLikes = new PdfPCell();
			cellEngagedFollowerLikes.setBorder(Rectangle.BOTTOM);
			cellEngagedFollowerLikes.setBorderWidthBottom(0.8f);
			cellEngagedFollowerLikes.setBorderColorBottom(new BaseColor(239, 239, 240));
			cellEngagedFollowerLikes.setPadding(3);
			cellEngagedFollowerLikes.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			Paragraph parTotalLikes = new Paragraph();
			parTotalLikes.setLeading(9);

			Image imgTotalLikes = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_POSTS_LIKES));
			imgTotalLikes.scaleAbsolute(8, 8);

			parTotalLikes.add(new Chunk(imgTotalLikes, 0, 0));

			Chunk cnkTotalLikes = new Chunk(" " + instagramEngagedFollower.getLikes(),
					new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(93, 93, 94)));
			cnkTotalLikes.setTextRise(1.3F);
			
			parTotalLikes.add(cnkTotalLikes);

			cellEngagedFollowerLikes.addElement(parTotalLikes);
			
			tableEngagedFollowerStats.addCell(cellEngagedFollowerComments);
			tableEngagedFollowerStats.addCell(cellEngagedFollowerLikes);
			
			tableEngagedFollowers.addCell(tableEngagedFollowerStats);
			
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}	
		
	}
	
	private void addTopPostTags() {
		
		try {
			
			PdfPTable tableTopPostTagsTitle = new PdfPTable(1);
			tableTopPostTagsTitle.setTotalWidth(new float[]{523});
			tableTopPostTagsTitle.setLockedWidth(true);
			tableTopPostTagsTitle.setHorizontalAlignment(Element.ALIGN_CENTER);

			tableTopPostTagsTitle.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableTopPostTagsTitle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			tableTopPostTagsTitle.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableTopPostTagsTitle.getDefaultCell().setBorderWidthBottom(0.8f);
			tableTopPostTagsTitle.getDefaultCell().setPaddingBottom(5);
			tableTopPostTagsTitle.getDefaultCell().setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parTopPostTagsTitle = new Paragraph("HASHTAGS COM MAIS INTERAÇÕES", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parTopPostTagsTitle.setAlignment(Element.ALIGN_LEFT);

			tableTopPostTagsTitle.addCell(parTopPostTagsTitle);

			PdfPTable tableTopPostTags = new PdfPTable(1);
			tableTopPostTags.setTotalWidth(new float[]{397});
			tableTopPostTags.setLockedWidth(true);
			tableTopPostTags.setSpacingBefore(8);
			tableTopPostTags.setSpacingAfter(22);
			
			PdfPCell cellTopPostTags = new PdfPCell();
			cellTopPostTags.setBorder(PdfPCell.NO_BORDER);
			cellTopPostTags.setPadding(0);
			
			PdfPTable tableTopPostTagsTable = new PdfPTable(5);
			tableTopPostTagsTable.setTotalWidth(new float[]{139, 44, 44, 105, 65});
			tableTopPostTagsTable.setLockedWidth(true);
			tableTopPostTagsTable.setSpacingBefore(3);
			
			for (int i = 0; i < reportInstagramOverview.getInstagramTopPostTags().size(); i++) {
				addInstagramTopPostTag(reportInstagramOverview.getInstagramTopPostTags().get(i), tableTopPostTagsTable, i+1);
			}
			
			cellTopPostTags.addElement(tableTopPostTagsTable);
			
			tableTopPostTags.addCell(cellTopPostTags);
			
			document.add(tableTopPostTagsTitle);
			document.add(tableTopPostTags);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	private void addInstagramTopPostTag(InstagramTopPostTag instagramTopPostTag, PdfPTable tableTopPostTags, int position) {
		
		try {
			
			PdfPCell cellInstagramTopPostTag = new PdfPCell();
			cellInstagramTopPostTag.setBorder(Rectangle.BOTTOM);
			cellInstagramTopPostTag.setBorderWidthBottom(0.8f);
			cellInstagramTopPostTag.setBorderColorBottom(new BaseColor(239, 239, 240));
			cellInstagramTopPostTag.setPadding(3);
			cellInstagramTopPostTag.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			Phrase phrInstagramTopPostTag = new Phrase(new Chunk(position + ". #" + instagramTopPostTag.getTag().toUpperCase(), 
					new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(121, 121, 121))));
			phrInstagramTopPostTag.setLeading(8);
			
			cellInstagramTopPostTag.addElement(phrInstagramTopPostTag);
			
			PdfPCell cellTopPostTagComments = new PdfPCell();
			cellTopPostTagComments.setBorder(Rectangle.BOTTOM);
			cellTopPostTagComments.setBorderWidthBottom(0.8f);
			cellTopPostTagComments.setBorderColorBottom(new BaseColor(239, 239, 240));
			cellTopPostTagComments.setPadding(3);
			cellTopPostTagComments.setVerticalAlignment(Element.ALIGN_MIDDLE);

			Paragraph parTopPostTagComments = new Paragraph();
			parTopPostTagComments.setLeading(9);
			parTopPostTagComments.setAlignment(Paragraph.ALIGN_RIGHT);

			Image imgTopPostTagComments = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_POSTS_COMMENTS_ENG));
			imgTopPostTagComments.scaleAbsolute(8, 8);

			parTopPostTagComments.add(new Chunk(imgTopPostTagComments, 0, 0));

			Chunk cnkTopPostTagComments = new Chunk(" " + instagramTopPostTag.getComments() + "  ",
					new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(93, 93, 94)));
			cnkTopPostTagComments.setTextRise(1.3F);
			
			parTopPostTagComments.add(cnkTopPostTagComments);

			cellTopPostTagComments.addElement(parTopPostTagComments);
			
			PdfPCell cellTopPostTagLikes = new PdfPCell();
			cellTopPostTagLikes.setBorder(Rectangle.BOTTOM);
			cellTopPostTagLikes.setBorderWidthBottom(0.8f);
			cellTopPostTagLikes.setBorderColorBottom(new BaseColor(239, 239, 240));
			cellTopPostTagLikes.setPadding(3);
			cellTopPostTagLikes.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			Paragraph parTopPostTagLikes = new Paragraph();
			parTopPostTagLikes.setLeading(9);
			parTopPostTagLikes.setAlignment(Paragraph.ALIGN_RIGHT);

			Image imgTopPostTagLikes = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_POSTS_LIKES));
			imgTopPostTagLikes.scaleAbsolute(8, 8);

			parTopPostTagLikes.add(new Chunk(imgTopPostTagLikes, 0, 0));

			Chunk cnkTopPostTagLikes = new Chunk(" " + instagramTopPostTag.getLikes(),
					new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(93, 93, 94)));
			cnkTopPostTagLikes.setTextRise(1.3F);
			
			parTopPostTagLikes.add(cnkTopPostTagLikes);

			cellTopPostTagLikes.addElement(parTopPostTagLikes);
			
			PdfPCell cellTopPostTagInteractions = new PdfPCell();
			cellTopPostTagInteractions.setBorder(Rectangle.BOTTOM);
			cellTopPostTagInteractions.setBorderWidthBottom(0.8f);
			cellTopPostTagInteractions.setBorderColorBottom(new BaseColor(239, 239, 240));
			cellTopPostTagInteractions.setPadding(3);
			cellTopPostTagInteractions.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			Paragraph phrTopPostTagInteractions = new Paragraph(new Chunk(instagramTopPostTag.getInteractions().toString() + " interações totais", 
					new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(93, 93, 94))));
			phrTopPostTagInteractions.setLeading(8);
			phrTopPostTagInteractions.setAlignment(Paragraph.ALIGN_RIGHT);
			
			cellTopPostTagInteractions.addElement(phrTopPostTagInteractions);
			
			PdfPCell cellTopPostTagInteractionsPerc = new PdfPCell();
			cellTopPostTagInteractionsPerc.setBorder(Rectangle.BOTTOM);
			cellTopPostTagInteractionsPerc.setBorderWidthBottom(0.8f);
			cellTopPostTagInteractionsPerc.setBorderColorBottom(new BaseColor(239, 239, 240));
			cellTopPostTagInteractionsPerc.setPadding(3);
			cellTopPostTagInteractionsPerc.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			Paragraph phrTopPostTagInteractionsPerc = new Paragraph(new Chunk(instagramTopPostTag.getInteractionsPercentage().toString().replace(".", ",") + "% do total", 
					new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(93, 93, 94))));
			phrTopPostTagInteractionsPerc.setLeading(8);
			phrTopPostTagInteractionsPerc.setAlignment(Paragraph.ALIGN_RIGHT);
			
			cellTopPostTagInteractionsPerc.addElement(phrTopPostTagInteractionsPerc);
			
			tableTopPostTags.addCell(cellInstagramTopPostTag);
			tableTopPostTags.addCell(cellTopPostTagComments);
			tableTopPostTags.addCell(cellTopPostTagLikes);
			tableTopPostTags.addCell(cellTopPostTagInteractions);
			tableTopPostTags.addCell(cellTopPostTagInteractionsPerc);
			
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addLikesPerHour() {
		
		try {
			
			PdfPTable tableLikesPerHour = new PdfPTable(1);
			tableLikesPerHour.setTotalWidth(new float[]{523});
			tableLikesPerHour.setLockedWidth(true);
			tableLikesPerHour.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableLikesPerHour.setSpacingAfter(22);
			
			PdfPCell cellLikesPerHourTitle = new PdfPCell();
			cellLikesPerHourTitle.setBorder(Rectangle.BOTTOM);
			cellLikesPerHourTitle.setBorderWidthBottom(0.8f);
			cellLikesPerHourTitle.setPaddingBottom(5);
			cellLikesPerHourTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parLikesPerHourTitle = new Paragraph("LIKES POR HORÁRIO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parLikesPerHourTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellLikesPerHourTitle.addElement(parLikesPerHourTitle);
			
			PdfPCell cellChartLikesPerHour = new PdfPCell();
			cellChartLikesPerHour.setBorder(PdfPCell.NO_BORDER);
			cellChartLikesPerHour.setPadding(1);

			cellChartLikesPerHour.addElement(addChartLikesPerHour());
			
			PdfPCell cellLikesPerHourEdges = new PdfPCell();
			cellLikesPerHourEdges.setBorder(PdfPCell.NO_BORDER);
			cellLikesPerHourEdges.setPaddingTop(10);
			cellLikesPerHourEdges.setPaddingBottom(0);
			cellLikesPerHourEdges.setPaddingLeft(0);
			cellLikesPerHourEdges.setPaddingRight(0);
			
			PdfPTable tableLikesPerHourEdges = new PdfPTable(2);
			tableLikesPerHourEdges.setTotalWidth(new float[]{180, 180});
			tableLikesPerHourEdges.setLockedWidth(true);
			tableLikesPerHourEdges.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell cellTimeMoreLikesStats = new PdfPCell();
			cellTimeMoreLikesStats.setBorder(PdfPCell.NO_BORDER);
			cellTimeMoreLikesStats.setPadding(0);

			PdfPTable tableTimeMoreLikes = new PdfPTable(1);
			tableTimeMoreLikes.setTotalWidth(new float[]{180});
			tableTimeMoreLikes.setLockedWidth(true);
			
			PdfPCell cellTimeMoreLikesTitle = new PdfPCell();
			cellTimeMoreLikesTitle.setBorder(PdfPCell.NO_BORDER);
			cellTimeMoreLikesTitle.setPaddingRight(0);
			cellTimeMoreLikesTitle.setPaddingLeft(0);
			cellTimeMoreLikesTitle.setPaddingTop(0);
			cellTimeMoreLikesTitle.setPaddingBottom(4);

			Paragraph parTimeMoreLikesTitle = new Paragraph("HORÁRIO COM MAIS LIKES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parTimeMoreLikesTitle.setLeading(9);
			parTimeMoreLikesTitle.setAlignment(Element.ALIGN_CENTER);

			cellTimeMoreLikesTitle.addElement(parTimeMoreLikesTitle);

			PdfPCell cellTimeMoreLikes = new PdfPCell();
			cellTimeMoreLikes.setBorder(PdfPCell.NO_BORDER);
			cellTimeMoreLikes.setPaddingRight(0);
			cellTimeMoreLikes.setPaddingLeft(0);
			cellTimeMoreLikes.setPaddingTop(0);
			cellTimeMoreLikes.setPaddingBottom(2);

			Paragraph parTimeMoreLikes = new Paragraph();
			parTimeMoreLikes.setLeading(11);
			parTimeMoreLikes.setAlignment(Element.ALIGN_CENTER);

			Image imgTimeMoreLikes = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_CONQUERED_FANS));
			imgTimeMoreLikes.scaleAbsolute(10, 10);

			parTimeMoreLikes.add(new Chunk(imgTimeMoreLikes, 0, 0));

			String timeMoreLikesFrom = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramOverview.getTimeWithMoreLikes(), 0, 0));
			String timeMoreLikesUntil = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramOverview.getTimeWithMoreLikes() + 1, 0, 0));

			parTimeMoreLikes.add(new Chunk(" " + timeMoreLikesFrom + " - " + timeMoreLikesUntil,
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(158, 218, 30))));

			cellTimeMoreLikes.addElement(parTimeMoreLikes);

			PdfPCell cellTimeMoreLikesDescription = new PdfPCell();
			cellTimeMoreLikesDescription.setBorder(PdfPCell.NO_BORDER);
			cellTimeMoreLikesDescription.setPaddingRight(0);
			cellTimeMoreLikesDescription.setPaddingLeft(0);
			cellTimeMoreLikesDescription.setPaddingTop(0);
			cellTimeMoreLikesDescription.setPaddingBottom(8);

			Paragraph parTimeMoreLikesDescription = new Paragraph(7, reportInstagramOverview.getTimeWithMoreLikesPerc() + "% " +
					"dos likes estão concentrados \n neste período.", 
					new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
			parTimeMoreLikesDescription.setAlignment(Element.ALIGN_CENTER);

			cellTimeMoreLikesDescription.addElement(parTimeMoreLikesDescription);
			
			tableTimeMoreLikes.addCell(cellTimeMoreLikesTitle);
			tableTimeMoreLikes.addCell(cellTimeMoreLikes);
			tableTimeMoreLikes.addCell(cellTimeMoreLikesDescription);
			
			cellTimeMoreLikesStats.addElement(tableTimeMoreLikes);
			
			PdfPCell cellTimeLessLikesStats = new PdfPCell();
			cellTimeLessLikesStats.setBorder(PdfPCell.NO_BORDER);
			cellTimeLessLikesStats.setPadding(0);

			PdfPTable tableTimeLessLikes = new PdfPTable(1);
			tableTimeLessLikes.setTotalWidth(new float[]{180});
			tableTimeLessLikes.setLockedWidth(true);
			
			PdfPCell cellTimeLessLikesTitle = new PdfPCell();
			cellTimeLessLikesTitle.setBorder(PdfPCell.NO_BORDER);
			cellTimeLessLikesTitle.setPaddingRight(0);
			cellTimeLessLikesTitle.setPaddingLeft(0);
			cellTimeLessLikesTitle.setPaddingTop(0);
			cellTimeLessLikesTitle.setPaddingBottom(4);

			Paragraph parTimeLessLikesTitle = new Paragraph("HORÁRIO COM MENOS LIKES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parTimeLessLikesTitle.setLeading(9);
			parTimeLessLikesTitle.setAlignment(Element.ALIGN_CENTER);

			cellTimeLessLikesTitle.addElement(parTimeLessLikesTitle);

			PdfPCell cellTimeLessLikes = new PdfPCell();
			cellTimeLessLikes.setBorder(PdfPCell.NO_BORDER);
			cellTimeLessLikes.setPaddingRight(0);
			cellTimeLessLikes.setPaddingLeft(0);
			cellTimeLessLikes.setPaddingTop(0);
			cellTimeLessLikes.setPaddingBottom(2);

			Paragraph parTimeLessLikes = new Paragraph();
			parTimeLessLikes.setLeading(11);
			parTimeLessLikes.setAlignment(Element.ALIGN_CENTER);

			Image imgTimeLessLikes = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_LOST_FANS));
			imgTimeLessLikes.scaleAbsolute(10, 10);

			parTimeLessLikes.add(new Chunk(imgTimeLessLikes, 0, 0));

			String timeLessLikesFrom = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramOverview.getTimeWithLessLikes(), 0, 0));
			String timeLessLikesUntil = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramOverview.getTimeWithLessLikes() + 1, 0, 0));

			parTimeLessLikes.add(new Chunk(" " + timeLessLikesFrom + " - " + timeLessLikesUntil,
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(215, 54, 49))));

			cellTimeLessLikes.addElement(parTimeLessLikes);

			PdfPCell cellTimeLessLikesDescription = new PdfPCell();
			cellTimeLessLikesDescription.setBorder(PdfPCell.NO_BORDER);
			cellTimeLessLikesDescription.setPaddingRight(0);
			cellTimeLessLikesDescription.setPaddingLeft(0);
			cellTimeLessLikesDescription.setPaddingTop(0);
			cellTimeLessLikesDescription.setPaddingBottom(8);

			Paragraph parTimeLessLikesDescription = new Paragraph(7, reportInstagramOverview.getTimeWithLessLikesPerc() + "% " +
					"dos likes estão concentrados \n neste período.", 
					new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
			parTimeLessLikesDescription.setAlignment(Element.ALIGN_CENTER);

			cellTimeLessLikesDescription.addElement(parTimeLessLikesDescription);
			
			tableTimeLessLikes.addCell(cellTimeLessLikesTitle);
			tableTimeLessLikes.addCell(cellTimeLessLikes);
			tableTimeLessLikes.addCell(cellTimeLessLikesDescription);
			
			cellTimeLessLikesStats.addElement(tableTimeLessLikes);
			
			tableLikesPerHourEdges.addCell(cellTimeMoreLikesStats);
			tableLikesPerHourEdges.addCell(cellTimeLessLikesStats);
			
			cellLikesPerHourEdges.addElement(tableLikesPerHourEdges);
			
			tableLikesPerHour.addCell(cellLikesPerHourTitle);
			tableLikesPerHour.addCell(cellChartLikesPerHour);
			tableLikesPerHour.addCell(cellLikesPerHourEdges);
			
			document.add(tableLikesPerHour);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addCommentsPerHour() {
		
		try {
			
			document.newPage();
			
			PdfPTable tableCommentsPerHour = new PdfPTable(1);
			tableCommentsPerHour.setTotalWidth(new float[]{523});
			tableCommentsPerHour.setLockedWidth(true);
			tableCommentsPerHour.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableCommentsPerHour.setSpacingAfter(22);
			
			PdfPCell cellCommentsPerHourTitle = new PdfPCell();
			cellCommentsPerHourTitle.setBorder(Rectangle.BOTTOM);
			cellCommentsPerHourTitle.setBorderWidthBottom(0.8f);
			cellCommentsPerHourTitle.setPaddingBottom(5);
			cellCommentsPerHourTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parCommentsPerHourTitle = new Paragraph("COMENTÁRIOS POR HORÁRIO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parCommentsPerHourTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellCommentsPerHourTitle.addElement(parCommentsPerHourTitle);
			
			PdfPCell cellChartCommentsPerHour = new PdfPCell();
			cellChartCommentsPerHour.setBorder(PdfPCell.NO_BORDER);
			cellChartCommentsPerHour.setPadding(1);

			cellChartCommentsPerHour.addElement(addChartCommentsPerHour());
			
			PdfPCell cellCommentsPerHourEdges = new PdfPCell();
			cellCommentsPerHourEdges.setBorder(PdfPCell.NO_BORDER);
			cellCommentsPerHourEdges.setPaddingTop(10);
			cellCommentsPerHourEdges.setPaddingBottom(0);
			cellCommentsPerHourEdges.setPaddingLeft(0);
			cellCommentsPerHourEdges.setPaddingRight(0);
			
			PdfPTable tableCommentsPerHourEdges = new PdfPTable(2);
			tableCommentsPerHourEdges.setTotalWidth(new float[]{200, 200});
			tableCommentsPerHourEdges.setLockedWidth(true);
			tableCommentsPerHourEdges.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell cellTimeMoreCommentsStats = new PdfPCell();
			cellTimeMoreCommentsStats.setBorder(PdfPCell.NO_BORDER);
			cellTimeMoreCommentsStats.setPadding(0);

			PdfPTable tableTimeMoreComments = new PdfPTable(1);
			tableTimeMoreComments.setTotalWidth(new float[]{200});
			tableTimeMoreComments.setLockedWidth(true);
			
			PdfPCell cellTimeMoreCommentsTitle = new PdfPCell();
			cellTimeMoreCommentsTitle.setBorder(PdfPCell.NO_BORDER);
			cellTimeMoreCommentsTitle.setPaddingRight(0);
			cellTimeMoreCommentsTitle.setPaddingLeft(0);
			cellTimeMoreCommentsTitle.setPaddingTop(0);
			cellTimeMoreCommentsTitle.setPaddingBottom(4);

			Paragraph parTimeMoreCommentsTitle = new Paragraph("HORÁRIO COM MAIS COMENTÁRIOS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parTimeMoreCommentsTitle.setLeading(9);
			parTimeMoreCommentsTitle.setAlignment(Element.ALIGN_CENTER);

			cellTimeMoreCommentsTitle.addElement(parTimeMoreCommentsTitle);

			PdfPCell cellTimeMoreComments = new PdfPCell();
			cellTimeMoreComments.setBorder(PdfPCell.NO_BORDER);
			cellTimeMoreComments.setPaddingRight(0);
			cellTimeMoreComments.setPaddingLeft(0);
			cellTimeMoreComments.setPaddingTop(0);
			cellTimeMoreComments.setPaddingBottom(2);

			Paragraph parTimeMoreComments = new Paragraph();
			parTimeMoreComments.setLeading(11);
			parTimeMoreComments.setAlignment(Element.ALIGN_CENTER);

			Image imgTimeMoreComments = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_CONQUERED_FANS));
			imgTimeMoreComments.scaleAbsolute(10, 10);

			parTimeMoreComments.add(new Chunk(imgTimeMoreComments, 0, 0));

			String timeMoreCommentsFrom = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramOverview.getTimeWithMoreComments(), 0, 0));
			String timeMoreCommentsUntil = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramOverview.getTimeWithMoreComments() + 1, 0, 0));

			parTimeMoreComments.add(new Chunk(" " + timeMoreCommentsFrom + " - " + timeMoreCommentsUntil,
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(158, 218, 30))));

			cellTimeMoreComments.addElement(parTimeMoreComments);

			PdfPCell cellTimeMoreCommentsDescription = new PdfPCell();
			cellTimeMoreCommentsDescription.setBorder(PdfPCell.NO_BORDER);
			cellTimeMoreCommentsDescription.setPaddingRight(0);
			cellTimeMoreCommentsDescription.setPaddingLeft(0);
			cellTimeMoreCommentsDescription.setPaddingTop(0);
			cellTimeMoreCommentsDescription.setPaddingBottom(8);

			Paragraph parTimeMoreCommentsDescription = new Paragraph(7, reportInstagramOverview.getTimeWithMoreCommentsPerc() + "% " +
					"dos comentários estão concentrados \n neste período.", 
					new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
			parTimeMoreCommentsDescription.setAlignment(Element.ALIGN_CENTER);

			cellTimeMoreCommentsDescription.addElement(parTimeMoreCommentsDescription);
			
			tableTimeMoreComments.addCell(cellTimeMoreCommentsTitle);
			tableTimeMoreComments.addCell(cellTimeMoreComments);
			tableTimeMoreComments.addCell(cellTimeMoreCommentsDescription);
			
			cellTimeMoreCommentsStats.addElement(tableTimeMoreComments);
			
			PdfPCell cellTimeLessCommentsStats = new PdfPCell();
			cellTimeLessCommentsStats.setBorder(PdfPCell.NO_BORDER);
			cellTimeLessCommentsStats.setPadding(0);

			PdfPTable tableTimeLessComments = new PdfPTable(1);
			tableTimeLessComments.setTotalWidth(new float[]{200});
			tableTimeLessComments.setLockedWidth(true);
			
			PdfPCell cellTimeLessCommentsTitle = new PdfPCell();
			cellTimeLessCommentsTitle.setBorder(PdfPCell.NO_BORDER);
			cellTimeLessCommentsTitle.setPaddingRight(0);
			cellTimeLessCommentsTitle.setPaddingLeft(0);
			cellTimeLessCommentsTitle.setPaddingTop(0);
			cellTimeLessCommentsTitle.setPaddingBottom(4);

			Paragraph parTimeLessCommentsTitle = new Paragraph("HORÁRIO COM MENOS COMENTÁRIOS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parTimeLessCommentsTitle.setLeading(9);
			parTimeLessCommentsTitle.setAlignment(Element.ALIGN_CENTER);

			cellTimeLessCommentsTitle.addElement(parTimeLessCommentsTitle);

			PdfPCell cellTimeLessComments = new PdfPCell();
			cellTimeLessComments.setBorder(PdfPCell.NO_BORDER);
			cellTimeLessComments.setPaddingRight(0);
			cellTimeLessComments.setPaddingLeft(0);
			cellTimeLessComments.setPaddingTop(0);
			cellTimeLessComments.setPaddingBottom(2);

			Paragraph parTimeLessComments = new Paragraph();
			parTimeLessComments.setLeading(11);
			parTimeLessComments.setAlignment(Element.ALIGN_CENTER);

			Image imgTimeLessComments = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_LOST_FANS));
			imgTimeLessComments.scaleAbsolute(10, 10);

			parTimeLessComments.add(new Chunk(imgTimeLessComments, 0, 0));

			String timeLessCommentsFrom = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramOverview.getTimeWithLessComments(), 0, 0));
			String timeLessCommentsUntil = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramOverview.getTimeWithLessComments() + 1, 0, 0));

			parTimeLessComments.add(new Chunk(" " + timeLessCommentsFrom + " - " + timeLessCommentsUntil,
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(215, 54, 49))));

			cellTimeLessComments.addElement(parTimeLessComments);

			PdfPCell cellTimeLessCommentsDescription = new PdfPCell();
			cellTimeLessCommentsDescription.setBorder(PdfPCell.NO_BORDER);
			cellTimeLessCommentsDescription.setPaddingRight(0);
			cellTimeLessCommentsDescription.setPaddingLeft(0);
			cellTimeLessCommentsDescription.setPaddingTop(0);
			cellTimeLessCommentsDescription.setPaddingBottom(8);

			Paragraph parTimeLessCommentsDescription = new Paragraph(7, reportInstagramOverview.getTimeWithLessCommentsPerc() + "% " +
					"dos comentários estão concentrados \n neste período.", 
					new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
			parTimeLessCommentsDescription.setAlignment(Element.ALIGN_CENTER);

			cellTimeLessCommentsDescription.addElement(parTimeLessCommentsDescription);
			
			tableTimeLessComments.addCell(cellTimeLessCommentsTitle);
			tableTimeLessComments.addCell(cellTimeLessComments);
			tableTimeLessComments.addCell(cellTimeLessCommentsDescription);
			
			cellTimeLessCommentsStats.addElement(tableTimeLessComments);
			
			tableCommentsPerHourEdges.addCell(cellTimeMoreCommentsStats);
			tableCommentsPerHourEdges.addCell(cellTimeLessCommentsStats);
			
			cellCommentsPerHourEdges.addElement(tableCommentsPerHourEdges);
			
			tableCommentsPerHour.addCell(cellCommentsPerHourTitle);
			tableCommentsPerHour.addCell(cellChartCommentsPerHour);
			tableCommentsPerHour.addCell(cellCommentsPerHourEdges);
			
			document.add(tableCommentsPerHour);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addInteractionsPerHour() {
		
		try {

			PdfPTable tableInteractionsPerHour = new PdfPTable(1);
			tableInteractionsPerHour.setTotalWidth(new float[]{523});
			tableInteractionsPerHour.setLockedWidth(true);
			tableInteractionsPerHour.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableInteractionsPerHour.setSpacingAfter(22);

			PdfPCell cellInteractionsPerHourTitle = new PdfPCell();
			cellInteractionsPerHourTitle.setBorder(Rectangle.BOTTOM);
			cellInteractionsPerHourTitle.setBorderWidthBottom(0.8f);
			cellInteractionsPerHourTitle.setPaddingBottom(5);
			cellInteractionsPerHourTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parInteractionsPerHourTitle = new Paragraph("INTERAÇÕES POR HORÁRIO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parInteractionsPerHourTitle.setAlignment(Element.ALIGN_CENTER);

			cellInteractionsPerHourTitle.addElement(parInteractionsPerHourTitle);

			PdfPCell cellChartInteractionsPerHour = new PdfPCell();
			cellChartInteractionsPerHour.setBorder(PdfPCell.NO_BORDER);
			cellChartInteractionsPerHour.setPadding(1);

			cellChartInteractionsPerHour.addElement(addChartInteractionsPerHour());
			
			PdfPCell cellCommentsPerHourEdges = new PdfPCell();
			cellCommentsPerHourEdges.setBorder(PdfPCell.NO_BORDER);
			cellCommentsPerHourEdges.setPaddingTop(10);
			cellCommentsPerHourEdges.setPaddingBottom(0);
			cellCommentsPerHourEdges.setPaddingLeft(0);
			cellCommentsPerHourEdges.setPaddingRight(0);

			PdfPTable tableCommentsPerHourEdges = new PdfPTable(3);
			tableCommentsPerHourEdges.setTotalWidth(new float[]{130, 130, 130});
			tableCommentsPerHourEdges.setLockedWidth(true);
			tableCommentsPerHourEdges.setHorizontalAlignment(Element.ALIGN_CENTER);

			int i = 1;
			for(InstagramInteractionRankingDTO instagramInteractionRanking : reportInstagramOverview.getInstagramInteractionsRanking()) {
				
				if(i < 4) {
					
					PdfPCell cellTimeMoreCommentsStats = new PdfPCell();
					cellTimeMoreCommentsStats.setBorder(PdfPCell.NO_BORDER);
					cellTimeMoreCommentsStats.setPadding(0);
					
					PdfPTable tableTimeMoreInteractions = new PdfPTable(1);
					tableTimeMoreInteractions.setTotalWidth(new float[]{130});
					tableTimeMoreInteractions.setLockedWidth(true);

					PdfPCell cellTimeMoreInteractionsTitle = new PdfPCell();
					cellTimeMoreInteractionsTitle.setBorder(PdfPCell.NO_BORDER);
					cellTimeMoreInteractionsTitle.setPaddingRight(0);
					cellTimeMoreInteractionsTitle.setPaddingLeft(0);
					cellTimeMoreInteractionsTitle.setPaddingTop(0);
					cellTimeMoreInteractionsTitle.setPaddingBottom(4);

					Paragraph parTimeMoreInteractionsTitle = new Paragraph(i + "° HORÁRIO \n EM INTERAÇÕES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
					parTimeMoreInteractionsTitle.setLeading(9);
					parTimeMoreInteractionsTitle.setAlignment(Element.ALIGN_CENTER);

					cellTimeMoreInteractionsTitle.addElement(parTimeMoreInteractionsTitle);
					
					Paragraph parTimeMoreInteractions = new Paragraph();
					parTimeMoreInteractions.setLeading(11);
					parTimeMoreInteractions.setAlignment(Element.ALIGN_CENTER);

					String timeMoreInteractionsFrom = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, instagramInteractionRanking.getHour(), 0, 0));
					String timeMoreInteractionsUntil = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, instagramInteractionRanking.getHour() + 1, 0, 0));
					
					switch (i) {
					case 1: {
						Image imgTimeMoreInteractions = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTA_INTERACTIONS_FIRST));
						imgTimeMoreInteractions.scaleAbsolute(10, 10);
						
						parTimeMoreInteractions.add(new Chunk(imgTimeMoreInteractions, 0, 0));
						
						BaseColor baseColorInteractions = new BaseColor(21, 243, 125); 
						
						parTimeMoreInteractions.add(new Chunk(" " + timeMoreInteractionsFrom + " - " + timeMoreInteractionsUntil,
								new Font(sourceSansPro, 15, Font.NORMAL, baseColorInteractions)));
						
						break;
					}
					case 2: {
						Image imgTimeMoreInteractions = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTA_INTERACTIONS_SECOND));
						imgTimeMoreInteractions.scaleAbsolute(10, 10);
						
						parTimeMoreInteractions.add(new Chunk(imgTimeMoreInteractions, 0, 0));
						
						BaseColor baseColorInteractions = new BaseColor(21, 243, 204);
						
						parTimeMoreInteractions.add(new Chunk(" " + timeMoreInteractionsFrom + " - " + timeMoreInteractionsUntil,
								new Font(sourceSansPro, 15, Font.NORMAL, baseColorInteractions)));
						
						break;
					}	
					case 3: {
						Image imgTimeMoreInteractions = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTA_INTERACTIONS_THIRD));
						imgTimeMoreInteractions.scaleAbsolute(10, 10);
						
						parTimeMoreInteractions.add(new Chunk(imgTimeMoreInteractions, 0, 0));
						
						BaseColor baseColorInteractions = new BaseColor(21, 167, 243);
						
						parTimeMoreInteractions.add(new Chunk(" " + timeMoreInteractionsFrom + " - " + timeMoreInteractionsUntil,
								new Font(sourceSansPro, 15, Font.NORMAL, baseColorInteractions)));
						
						break;
					}
					default:
						break;
					}

					PdfPCell cellTimeMoreInteractions = new PdfPCell();
					cellTimeMoreInteractions.setBorder(PdfPCell.NO_BORDER);
					cellTimeMoreInteractions.setPaddingRight(0);
					cellTimeMoreInteractions.setPaddingLeft(0);
					cellTimeMoreInteractions.setPaddingTop(0);
					cellTimeMoreInteractions.setPaddingBottom(2);

					cellTimeMoreInteractions.addElement(parTimeMoreInteractions);

					PdfPCell cellTimeMoreInteractionsDescription = new PdfPCell();
					cellTimeMoreInteractionsDescription.setBorder(PdfPCell.NO_BORDER);
					cellTimeMoreInteractionsDescription.setPaddingRight(0);
					cellTimeMoreInteractionsDescription.setPaddingLeft(0);
					cellTimeMoreInteractionsDescription.setPaddingTop(0);
					cellTimeMoreInteractionsDescription.setPaddingBottom(8);

					Paragraph parTimeMoreInteractionsDescription = new Paragraph(7, instagramInteractionRanking.getCount() +
							" interações estão concentradas \n neste período, " + 
							instagramInteractionRanking.getPercentage().toString().replace(".", ",") + "% do total.", 
							new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
					parTimeMoreInteractionsDescription.setAlignment(Element.ALIGN_CENTER);

					cellTimeMoreInteractionsDescription.addElement(parTimeMoreInteractionsDescription);

					tableTimeMoreInteractions.addCell(cellTimeMoreInteractionsTitle);
					tableTimeMoreInteractions.addCell(cellTimeMoreInteractions);
					tableTimeMoreInteractions.addCell(cellTimeMoreInteractionsDescription);
					
					cellTimeMoreCommentsStats.addElement(tableTimeMoreInteractions);
					
					tableCommentsPerHourEdges.addCell(cellTimeMoreCommentsStats);
					
					i++;
					
				} else {
					break;
				}
			}

			cellCommentsPerHourEdges.addElement(tableCommentsPerHourEdges);

			tableInteractionsPerHour.addCell(cellInteractionsPerHourTitle);
			tableInteractionsPerHour.addCell(cellChartInteractionsPerHour);
			tableInteractionsPerHour.addCell(cellCommentsPerHourEdges);

			document.add(tableInteractionsPerHour);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private Image addChartInteractionsPerHour() {
		
		String img = chartsImages.get("chartInteractionsPerHourOverv").substring(chartsImages.get("chartInteractionsPerHourOverv").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteWidth(360);
			imageChart.scaleAbsoluteHeight(151);
			imageChart.setAlignment(Element.ALIGN_CENTER);

			tempImages.add(file);

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
	
	private Image addChartCommentsPerHour() {
		
		String img = chartsImages.get("chartCommentsPerHourOverv").substring(chartsImages.get("chartCommentsPerHourOverv").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteWidth(360);
			imageChart.scaleAbsoluteHeight(151);
			imageChart.setAlignment(Element.ALIGN_CENTER);

			tempImages.add(file);

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
	
	private Image addChartLikesPerHour() {
		
		String img = chartsImages.get("chartLikesPerHourOverv").substring(chartsImages.get("chartLikesPerHourOverv").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteWidth(360);
			imageChart.scaleAbsoluteHeight(151);
			imageChart.setAlignment(Element.ALIGN_CENTER);

			tempImages.add(file);

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
	
	
	private Image addChartFollowersTrend() {
		
		String img = chartsImages.get("chartFollowersTrend").substring(chartsImages.get("chartFollowersTrend").indexOf("base64,") + 7);

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

			tempImages.add(file);

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
	
	private Image addChartNumberOfLikes() {
		
		String img = chartsImages.get("chartLikesPerDay").substring(chartsImages.get("chartLikesPerDay").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(122);
			imageChart.scaleAbsoluteWidth(200);
			imageChart.setAlignment(Element.ALIGN_CENTER);

			tempImages.add(file);

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
	
	private Image addChartNumberOfComments() {
		
		String img = chartsImages.get("chartCommentsPerDay").substring(chartsImages.get("chartCommentsPerDay").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(122);
			imageChart.scaleAbsoluteWidth(200);
			imageChart.setAlignment(Element.ALIGN_CENTER);

			tempImages.add(file);

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

		HeaderFooterPDF headerFooterPDF = new HeaderFooterPDF(pdfWritter, "Relatório Instagram Visão Geral", (ReportLogoDTO) profile.getReportLogo());
		pdfWritter.setPageEvent(headerFooterPDF);
	}

	private void removeImagesTemp() {
		for(File fileToDelete : tempImages) {
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