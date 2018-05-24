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
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.report.ReportLogoDTO;
import br.com.opsocial.client.entity.report.twitter.ReportTwitterOverviewDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.pdf.HeaderFooterPDF;
import br.com.opsocial.server.utils.reports.ReportIcons;
import br.com.opsocial.server.utils.reports.ReportTwitter;

public class ReportTwitterOverviewPDF {

	private ReportTwitterOverviewDTO reportTwitterOverview;
	private Profile profile;

	private PdfWriter pdfWritter;
	private Document document;
	private String fileName;

	private HashMap<String, String> chartsImages;
	private java.util.List<File> imagesTemp;

	private static BaseFont sourceSansPro;

	private static final BaseColor BASE_COLOR_HEADER_TITLE = new BaseColor(52, 98, 144);

	public ReportTwitterOverviewPDF(ReportTwitterOverviewDTO reportTwitterOverview, Profile profile, HashMap<String, String> chartsImages) {
		
		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".pdf");
		this.reportTwitterOverview = reportTwitterOverview;
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
		addFollowersStats();
		addTweetsSent();
		addTweetsOverTime();
		addEngagementWithTweets();
		addAboutFollowers();
		addFollowersActivity();
		addFollowersCities();
		addRecentInfluentFollowers();
		addTweetsMoreEngaged();

		document.close();

		removeImagesTemp();
	}
	
	private void addTweetsMoreEngaged() {

		try {

			document.newPage();

			PdfPTable tableTweetsMoreEngagedTitle = new PdfPTable(1);
			tableTweetsMoreEngagedTitle.setTotalWidth(new float[]{523});
			tableTweetsMoreEngagedTitle.setLockedWidth(true);

			PdfPCell cellTweetsMoreEngagedTitle = new PdfPCell();
			cellTweetsMoreEngagedTitle.setBorder(Rectangle.BOTTOM);
			cellTweetsMoreEngagedTitle.setBorderWidthBottom(0.8f);
			cellTweetsMoreEngagedTitle.setPaddingBottom(5);
			cellTweetsMoreEngagedTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parTweetsMoreEngagedTitle = new Paragraph("TWEETS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parTweetsMoreEngagedTitle.setAlignment(Element.ALIGN_CENTER);

			cellTweetsMoreEngagedTitle.addElement(parTweetsMoreEngagedTitle);
			
			tableTweetsMoreEngagedTitle.addCell(cellTweetsMoreEngagedTitle);
			
			// Tweets more engaged table.
			PdfPTable tableTweetsMoreEngagedTableHeader = new PdfPTable(6);
			tableTweetsMoreEngagedTableHeader.setTotalWidth(new float[]{15, 228, 47, 42, 37, 42});
			tableTweetsMoreEngagedTableHeader.setLockedWidth(true);
			tableTweetsMoreEngagedTableHeader.setSpacingBefore(5);

			PdfPCell cellPostPosition = new PdfPCell(new Phrase(new Chunk("#", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
			cellPostPosition.setBorder(Rectangle.BOTTOM);
			cellPostPosition.setBorderWidthBottom(0.8f);
			cellPostPosition.setBorderColorBottom(new BaseColor(227, 227, 228));
			cellPostPosition.setBackgroundColor(new BaseColor(217, 217, 217));
			cellPostPosition.setPadding(3);
			cellPostPosition.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			PdfPCell cellPostContent = new PdfPCell(new Phrase(new Chunk("Post", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
			cellPostContent.setBorder(Rectangle.BOTTOM);
			cellPostContent.setBorderWidthBottom(0.8f);
			cellPostContent.setBorderColorBottom(new BaseColor(227, 227, 228));
			cellPostContent.setBackgroundColor(new BaseColor(228, 228, 228));
			cellPostContent.setPadding(3);
			cellPostContent.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			PdfPCell cellPostDate = new PdfPCell(new Phrase(new Chunk("Data", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
			cellPostDate.setBorder(Rectangle.BOTTOM);
			cellPostDate.setBorderWidthBottom(0.8f);
			cellPostDate.setBorderColorBottom(new BaseColor(227, 227, 228));
			cellPostDate.setBackgroundColor(new BaseColor(217, 217, 217));
			cellPostDate.setPadding(3);
			cellPostDate.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			PdfPCell cellPostRetweets = new PdfPCell(new Phrase(new Chunk("Retweets", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
			cellPostRetweets.setBorder(Rectangle.BOTTOM);
			cellPostRetweets.setBorderWidthBottom(0.8f);
			cellPostRetweets.setBorderColorBottom(new BaseColor(227, 227, 228));
			cellPostRetweets.setBackgroundColor(new BaseColor(228, 228, 228));
			cellPostRetweets.setPadding(3);
			cellPostRetweets.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			PdfPCell cellPostReplies = new PdfPCell(new Phrase(new Chunk("Replies", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
			cellPostReplies.setBorder(Rectangle.BOTTOM);
			cellPostReplies.setBorderWidthBottom(0.8f);
			cellPostReplies.setBorderColorBottom(new BaseColor(227, 227, 228));
			cellPostReplies.setBackgroundColor(new BaseColor(217, 217, 217));
			cellPostReplies.setPadding(3);
			cellPostReplies.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			PdfPCell cellPostFavorites = new PdfPCell(new Phrase(new Chunk("Curtir", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
			cellPostFavorites.setBorder(Rectangle.BOTTOM);
			cellPostFavorites.setBorderWidthBottom(0.8f);
			cellPostFavorites.setBorderColorBottom(new BaseColor(227, 227, 228));
			cellPostFavorites.setBackgroundColor(new BaseColor(228, 228, 228));
			cellPostFavorites.setPadding(3);
			cellPostFavorites.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

			tableTweetsMoreEngagedTableHeader.addCell(cellPostPosition);
			tableTweetsMoreEngagedTableHeader.addCell(cellPostContent);
			tableTweetsMoreEngagedTableHeader.addCell(cellPostDate);
			tableTweetsMoreEngagedTableHeader.addCell(cellPostRetweets);
			tableTweetsMoreEngagedTableHeader.addCell(cellPostReplies);
			tableTweetsMoreEngagedTableHeader.addCell(cellPostFavorites);

			PdfPTable tablePostsMostEngagedTableRows = new PdfPTable(6);
			tablePostsMostEngagedTableRows.setTotalWidth(new float[]{15, 228, 47, 42, 37, 42});
			tablePostsMostEngagedTableRows.setLockedWidth(true);
			tablePostsMostEngagedTableRows.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tablePostsMostEngagedTableRows.getDefaultCell().setBorderWidthBottom(0.8f);
			tablePostsMostEngagedTableRows.getDefaultCell().setBorderColorBottom(new BaseColor(239, 239, 240));
			tablePostsMostEngagedTableRows.getDefaultCell().setPadding(3);

			for(int i = 0; i < reportTwitterOverview.getTwitterReportStatus().size(); i++) {

				PdfPCell cellPostPositionRow = new PdfPCell(new Phrase(new Chunk(String.valueOf(i+1), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
				cellPostPositionRow.setBorder(Rectangle.BOTTOM);
				cellPostPositionRow.setBorderWidthBottom(0.8f);
				cellPostPositionRow.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellPostPositionRow.setBackgroundColor(new BaseColor(242, 242, 242));
				cellPostPositionRow.setPadding(3);
				cellPostPositionRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellPostPositionRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

				PdfPCell cellPostContentRow = new PdfPCell(new Phrase(new Chunk(reportTwitterOverview.getTwitterReportStatus().get(i).getText(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
				cellPostContentRow.setBorder(Rectangle.BOTTOM);
				cellPostContentRow.setBorderWidthBottom(0.8f);
				cellPostContentRow.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellPostContentRow.setPadding(3);
				cellPostContentRow.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				cellPostContentRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

				PdfPCell cellPostDateRow = new PdfPCell(new Phrase(new Chunk(new SimpleDateFormat("dd/MM/yyyy").format(
						reportTwitterOverview.getTwitterReportStatus().get(i).getCreatedAt()), 
							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
				cellPostDateRow.setBorder(Rectangle.BOTTOM);
				cellPostDateRow.setBorderWidthBottom(0.8f);
				cellPostDateRow.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellPostDateRow.setBackgroundColor(new BaseColor(242, 242, 242));
				cellPostDateRow.setPadding(3);
				cellPostDateRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellPostDateRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

				PdfPCell cellPostRetweetsRow = new PdfPCell(new Phrase(
						new Chunk(reportTwitterOverview.getTwitterReportStatus().get(i).getRetweetsCount().toString(), 
								new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
				cellPostRetweetsRow.setBorder(Rectangle.BOTTOM);
				cellPostRetweetsRow.setBorderWidthBottom(0.8f);
				cellPostRetweetsRow.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellPostRetweetsRow.setPadding(3);
				cellPostRetweetsRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellPostRetweetsRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

				PdfPCell cellPostRepliesRow = new PdfPCell(new Phrase(
						new Chunk(reportTwitterOverview.getTwitterReportStatus().get(i).getRepliesCount().toString(), 
								new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
				cellPostRepliesRow.setBorder(Rectangle.BOTTOM);
				cellPostRepliesRow.setBorderWidthBottom(0.8f);
				cellPostRepliesRow.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellPostRepliesRow.setBackgroundColor(new BaseColor(242, 242, 242));
				cellPostRepliesRow.setPadding(3);
				cellPostRepliesRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellPostRepliesRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
				
				PdfPCell cellPostFavoritesRow = new PdfPCell(new Phrase(
						new Chunk(reportTwitterOverview.getTwitterReportStatus().get(i).getFavoriteCount().toString(), 
								new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134,134,134)))));
				cellPostFavoritesRow.setBorder(Rectangle.BOTTOM);
				cellPostFavoritesRow.setBorderWidthBottom(0.8f);
				cellPostFavoritesRow.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellPostFavoritesRow.setPadding(3);
				cellPostFavoritesRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cellPostFavoritesRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

				tablePostsMostEngagedTableRows.addCell(cellPostPositionRow);
				tablePostsMostEngagedTableRows.addCell(cellPostContentRow);
				tablePostsMostEngagedTableRows.addCell(cellPostDateRow);
				tablePostsMostEngagedTableRows.addCell(cellPostRetweetsRow);
				tablePostsMostEngagedTableRows.addCell(cellPostRepliesRow);
				tablePostsMostEngagedTableRows.addCell(cellPostFavoritesRow);
			}

			document.add(tableTweetsMoreEngagedTitle);
			document.add(tableTweetsMoreEngagedTableHeader);
			document.add(tablePostsMostEngagedTableRows);

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	private void addRecentInfluentFollowers() {
		
		try {
			
			PdfPTable tableRecentInfluentFollowersTitle = new PdfPTable(1);
			tableRecentInfluentFollowersTitle.setTotalWidth(new float[]{523});
			tableRecentInfluentFollowersTitle.setLockedWidth(true);
			tableRecentInfluentFollowersTitle.setHorizontalAlignment(Element.ALIGN_CENTER);

			tableRecentInfluentFollowersTitle.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			tableRecentInfluentFollowersTitle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			tableRecentInfluentFollowersTitle.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableRecentInfluentFollowersTitle.getDefaultCell().setBorderWidthBottom(0.8f);
			tableRecentInfluentFollowersTitle.getDefaultCell().setPaddingBottom(5);
			tableRecentInfluentFollowersTitle.getDefaultCell().setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parRecentInfluentFollowersTitle = new Paragraph("NOVOS SEGUIDORES E SEGUIDORES MAIS INFLUENTES", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parRecentInfluentFollowersTitle.setAlignment(Element.ALIGN_LEFT);

			tableRecentInfluentFollowersTitle.addCell(parRecentInfluentFollowersTitle);

			PdfPTable tableRecentInfluentFollowers = new PdfPTable(2);
			tableRecentInfluentFollowers.setTotalWidth(new float[]{ 170, 170});
			tableRecentInfluentFollowers.setLockedWidth(true);
			tableRecentInfluentFollowers.setSpacingBefore(8);
			tableRecentInfluentFollowers.setSpacingAfter(22);
			
			// Most recent followers.
			PdfPCell cellMostRecentFollowers = new PdfPCell();
			cellMostRecentFollowers.setBorder(PdfPCell.NO_BORDER);
			cellMostRecentFollowers.setPaddingRight(20);
			cellMostRecentFollowers.setPaddingLeft(0);
			cellMostRecentFollowers.setPaddingTop(0);
			cellMostRecentFollowers.setPaddingBottom(0);
			
			PdfPTable tableMostRecentFollowers = new PdfPTable(1);
			tableMostRecentFollowers.setTotalWidth(new float[]{170});
			tableMostRecentFollowers.setLockedWidth(true);
			
			PdfPCell cellMostRecentFollowersTitle = new PdfPCell();
			cellMostRecentFollowersTitle.setBorder(PdfPCell.NO_BORDER);
			cellMostRecentFollowersTitle.setPadding(0);
			
			Paragraph parMostRecentFollowersTitle = new Paragraph("SEGUIDORES MAIS RECENTES", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(112, 112, 112)));
			parMostRecentFollowersTitle.setAlignment(Element.ALIGN_LEFT);
			parMostRecentFollowersTitle.setLeading(11);
			
			cellMostRecentFollowersTitle.addElement(parMostRecentFollowersTitle);
			
			PdfPCell cellMostRecentFollowersTable = new PdfPCell();
			cellMostRecentFollowersTable.setBorder(PdfPCell.NO_BORDER);
			cellMostRecentFollowersTable.setPadding(0);
			
			PdfPTable tableMostRecentFollowersTable = new PdfPTable(2);
			tableMostRecentFollowersTable.setTotalWidth(new float[]{19, 151});
			tableMostRecentFollowersTable.setLockedWidth(true);
			tableMostRecentFollowersTable.setSpacingBefore(3);
			
			for (int i = 0; i < reportTwitterOverview.getMostRecentFollowers().size(); i++) {
				
				PdfPCell cellMostRecentFollowerRanking = new PdfPCell();
				cellMostRecentFollowerRanking.setBorder(Rectangle.BOTTOM);
				cellMostRecentFollowerRanking.setBorderWidthBottom(0.8f);
				cellMostRecentFollowerRanking.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellMostRecentFollowerRanking.setPadding(3);
				cellMostRecentFollowerRanking.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				Image imgMostRecentFollowerRanking = Image.getInstance(new URL(reportTwitterOverview.getMostRecentFollowers().get(i).getAvatar().replace("https", "http")));
				imgMostRecentFollowerRanking.scaleAbsolute(16, 16);
				
				cellMostRecentFollowerRanking.addElement(imgMostRecentFollowerRanking);
				
				tableMostRecentFollowersTable.addCell(cellMostRecentFollowerRanking);
				
				PdfPCell cellMostRecentFollowerRankingName = new PdfPCell();
				cellMostRecentFollowerRankingName.setBorder(Rectangle.BOTTOM);
				cellMostRecentFollowerRankingName.setBorderWidthBottom(0.8f);
				cellMostRecentFollowerRankingName.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellMostRecentFollowerRankingName.setPaddingRight(3);
				cellMostRecentFollowerRankingName.setPaddingTop(3);
				cellMostRecentFollowerRankingName.setPaddingBottom(3);
				cellMostRecentFollowerRankingName.setPaddingLeft(5);
				cellMostRecentFollowerRankingName.setVerticalAlignment(Element.ALIGN_MIDDLE);

				Phrase phrMostRecentFollowerRankingName = new Phrase(new Chunk((i + 1) +". " + reportTwitterOverview.getMostRecentFollowers().get(i).getScreenName().toUpperCase(), 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(121, 121, 121))));
				phrMostRecentFollowerRankingName.setLeading(8);
				
				cellMostRecentFollowerRankingName.addElement(phrMostRecentFollowerRankingName);
				
				tableMostRecentFollowersTable.addCell(cellMostRecentFollowerRankingName);	
			}
			
			cellMostRecentFollowersTable.addElement(tableMostRecentFollowersTable);
			
			tableMostRecentFollowers.addCell(cellMostRecentFollowersTitle);
			tableMostRecentFollowers.addCell(cellMostRecentFollowersTable);
			
			cellMostRecentFollowers.addElement(tableMostRecentFollowers);
			
			// Most influent followers.
			PdfPCell cellMostInfluentFollowers = new PdfPCell();
			cellMostInfluentFollowers.setBorder(PdfPCell.NO_BORDER);
			cellMostInfluentFollowers.setPadding(0);

			PdfPTable tableMostInfluentFollowers = new PdfPTable(1);
			tableMostInfluentFollowers.setTotalWidth(new float[]{170});
			tableMostInfluentFollowers.setLockedWidth(true);

			PdfPCell cellMostInfluentFollowersTitle = new PdfPCell();
			cellMostInfluentFollowersTitle.setBorder(PdfPCell.NO_BORDER);
			cellMostInfluentFollowersTitle.setPadding(0);

			Paragraph parMostInfluentFollowersTitle = new Paragraph("SEGUIDORES MAIS INFLUENTES", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(112, 112, 112)));
			parMostInfluentFollowersTitle.setAlignment(Element.ALIGN_LEFT);
			parMostInfluentFollowersTitle.setLeading(11);

			cellMostInfluentFollowersTitle.addElement(parMostInfluentFollowersTitle);

			PdfPCell cellMostInfluentFollowersTable = new PdfPCell();
			cellMostInfluentFollowersTable.setBorder(PdfPCell.NO_BORDER);
			cellMostInfluentFollowersTable.setPadding(0);

			PdfPTable tableMostInfluentFollowersTable = new PdfPTable(2);
			tableMostInfluentFollowersTable.setTotalWidth(new float[]{19, 151});
			tableMostInfluentFollowersTable.setLockedWidth(true);
			tableMostInfluentFollowersTable.setSpacingBefore(3);

			for (int i = 0; i < reportTwitterOverview.getMostInfluentialFollowers().size(); i++) {

				PdfPCell cellMostInfluentFollowerRanking = new PdfPCell();
				cellMostInfluentFollowerRanking.setBorder(Rectangle.BOTTOM);
				cellMostInfluentFollowerRanking.setBorderWidthBottom(0.8f);
				cellMostInfluentFollowerRanking.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellMostInfluentFollowerRanking.setPadding(3);
				cellMostInfluentFollowerRanking.setVerticalAlignment(Element.ALIGN_MIDDLE);

				Image imgMostInfluentFollowerRanking = Image.getInstance(new URL(reportTwitterOverview.getMostInfluentialFollowers().get(i).getAvatar().replace("https", "http")));
				imgMostInfluentFollowerRanking.scaleAbsolute(16, 16);

				cellMostInfluentFollowerRanking.addElement(imgMostInfluentFollowerRanking);

				PdfPCell cellMostInfluentFollowerRankingName = new PdfPCell();
				cellMostInfluentFollowerRankingName.setBorder(Rectangle.BOTTOM);
				cellMostInfluentFollowerRankingName.setBorderWidthBottom(0.8f);
				cellMostInfluentFollowerRankingName.setBorderColorBottom(new BaseColor(239, 239, 240));
				cellMostInfluentFollowerRankingName.setPaddingRight(3);
				cellMostInfluentFollowerRankingName.setPaddingTop(3);
				cellMostInfluentFollowerRankingName.setPaddingBottom(3);
				cellMostInfluentFollowerRankingName.setPaddingLeft(5);
				cellMostInfluentFollowerRankingName.setVerticalAlignment(Element.ALIGN_MIDDLE);

				Phrase phrMostInfluentFollowerRankingName = new Phrase(new Chunk((i + 1) +". " + reportTwitterOverview.getMostInfluentialFollowers().get(i).getScreenName().toUpperCase(), 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(121, 121, 121))));
				phrMostInfluentFollowerRankingName.setLeading(8);

				cellMostInfluentFollowerRankingName.addElement(phrMostInfluentFollowerRankingName);

				tableMostInfluentFollowersTable.addCell(cellMostInfluentFollowerRanking);
				tableMostInfluentFollowersTable.addCell(cellMostInfluentFollowerRankingName);	
			}

			cellMostInfluentFollowersTable.addElement(tableMostInfluentFollowersTable);

			tableMostInfluentFollowers.addCell(cellMostInfluentFollowersTitle);
			tableMostInfluentFollowers.addCell(cellMostInfluentFollowersTable);

			cellMostInfluentFollowers.addElement(tableMostInfluentFollowers);
			
			tableRecentInfluentFollowers.addCell(cellMostRecentFollowers);
			tableRecentInfluentFollowers.addCell(cellMostInfluentFollowers);
			
			document.add(tableRecentInfluentFollowersTitle);
			document.add(tableRecentInfluentFollowers);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addFollowersCities() {
		
		try {
			
			PdfPTable tableFollowersCitiesTitle = new PdfPTable(1);
			tableFollowersCitiesTitle.setTotalWidth(new float[]{523});
			tableFollowersCitiesTitle.setLockedWidth(true);
			tableFollowersCitiesTitle.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cellFollowersCitiesTitle = new PdfPCell();
			cellFollowersCitiesTitle.setBorder(Rectangle.BOTTOM);
			cellFollowersCitiesTitle.setBorderWidthBottom(0.8f);
			cellFollowersCitiesTitle.setPaddingBottom(5);
			cellFollowersCitiesTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parFollowersCitiesTitle = new Paragraph("CIDADES COM MAIS SEGUIDORES", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parFollowersCitiesTitle.setAlignment(Element.ALIGN_CENTER);

			cellFollowersCitiesTitle.addElement(parFollowersCitiesTitle);
			
			tableFollowersCitiesTitle.addCell(cellFollowersCitiesTitle);
			
			// Cities with more fans table.
			PdfPTable tableFollowersCitiesTableHeader = new PdfPTable(3);
			tableFollowersCitiesTableHeader.setTotalWidth(new float[]{16, 302, 47});
			tableFollowersCitiesTableHeader.setLockedWidth(true);
			tableFollowersCitiesTableHeader.setSpacingBefore(5);
			tableFollowersCitiesTableHeader.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableFollowersCitiesTableHeader.getDefaultCell().setBorderWidthBottom(0.8f);
			tableFollowersCitiesTableHeader.getDefaultCell().setBorderColorBottom(new BaseColor(227, 227, 228));
			tableFollowersCitiesTableHeader.getDefaultCell().setBackgroundColor(new BaseColor(242, 242, 242));
			tableFollowersCitiesTableHeader.getDefaultCell().setPadding(3);
			
			tableFollowersCitiesTableHeader.addCell(new Phrase(new Chunk("#", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableFollowersCitiesTableHeader.addCell(new Phrase(new Chunk("Cidade", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			tableFollowersCitiesTableHeader.addCell(new Phrase(new Chunk("Seguidores", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(65, 65, 65)))));
			
			PdfPTable tableFollowersCitiesTableRows = new PdfPTable(3);
			tableFollowersCitiesTableRows.setTotalWidth(new float[]{16, 302, 47});
			tableFollowersCitiesTableRows.setLockedWidth(true);
			tableFollowersCitiesTableRows.getDefaultCell().setBorder(Rectangle.BOTTOM);
			tableFollowersCitiesTableRows.getDefaultCell().setBorderWidthBottom(0.8f);
			tableFollowersCitiesTableRows.getDefaultCell().setBorderColorBottom(new BaseColor(239, 239, 240));
			tableFollowersCitiesTableRows.getDefaultCell().setPadding(3);
			tableFollowersCitiesTableRows.setSpacingAfter(22);
			
			int i = 1;
			for(Map.Entry<String, Long> city : reportTwitterOverview.getCitiesWithMoreFollowers().entrySet()) {
				
				tableFollowersCitiesTableRows.addCell(new Phrase(new Chunk(String.valueOf(i), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
				tableFollowersCitiesTableRows.addCell(new Phrase(new Chunk(city.getKey(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));
				tableFollowersCitiesTableRows.addCell(new Phrase(new Chunk(city.getValue().toString(), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(134, 134, 134)))));

				i++;
			}
			
			document.add(tableFollowersCitiesTitle);
			document.add(tableFollowersCitiesTableHeader);
			document.add(tableFollowersCitiesTableRows);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addFollowersActivity() {
		
		try {
			
			document.newPage();

			PdfPTable tableFollowersActivity = new PdfPTable(1);
			tableFollowersActivity.setTotalWidth(new float[]{523});
			tableFollowersActivity.setLockedWidth(true);
			tableFollowersActivity.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFollowersActivity.setSpacingAfter(22);

			PdfPCell cellFollowersActivityTitle = new PdfPCell();
			cellFollowersActivityTitle.setBorder(Rectangle.BOTTOM);
			cellFollowersActivityTitle.setBorderWidthBottom(0.8f);
			cellFollowersActivityTitle.setPaddingBottom(5);
			cellFollowersActivityTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parFollowersActivityTitle = new Paragraph("ATIVIDADE DOS SEUS SEGUIDORES", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parFollowersActivityTitle.setAlignment(Element.ALIGN_CENTER);

			cellFollowersActivityTitle.addElement(parFollowersActivityTitle);

			PdfPCell cellFollowersActivity = new PdfPCell();
			cellFollowersActivity.setBorder(PdfPCell.NO_BORDER);
			cellFollowersActivity.setPaddingBottom(0);

			PdfPTable tableFollowersActivityContent = new PdfPTable(2);
			tableFollowersActivityContent.setTotalWidth(new float[]{200, 165});
			tableFollowersActivityContent.setLockedWidth(true);

			PdfPCell cellChartFollowersActivity = new PdfPCell();
			cellChartFollowersActivity.setBorder(PdfPCell.NO_BORDER);
			cellChartFollowersActivity.setPadding(0);

			cellChartFollowersActivity.addElement(addChartFollowersActivity());

			PdfPCell cellFollowersActivityInfo = new PdfPCell();
			cellFollowersActivityInfo.setBorder(PdfPCell.NO_BORDER);
			cellFollowersActivityInfo.setPaddingTop(8);
			cellFollowersActivityInfo.setPaddingBottom(0);
			cellFollowersActivityInfo.setPaddingRight(0);
			cellFollowersActivityInfo.setPaddingLeft(30);

			PdfPTable tableFollowersActivityInfo = new PdfPTable(1);
			tableFollowersActivityInfo.setTotalWidth(new float[]{165});
			tableFollowersActivityInfo.setLockedWidth(true);

			// Engagement rate.
			PdfPCell cellEngagementRateTitle = new PdfPCell();
			cellEngagementRateTitle.setBorder(PdfPCell.NO_BORDER);
			cellEngagementRateTitle.setPaddingRight(0);
			cellEngagementRateTitle.setPaddingLeft(0);
			cellEngagementRateTitle.setPaddingTop(0);
			cellEngagementRateTitle.setPaddingBottom(2);

			Paragraph parEngagementRateTitle = new Paragraph("ÍNDICE DE ENGAJAMENTO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parEngagementRateTitle.setLeading(9);

			cellEngagementRateTitle.addElement(parEngagementRateTitle);

			PdfPCell cellEngagementRate = new PdfPCell();
			cellEngagementRate.setBorder(PdfPCell.NO_BORDER);
			cellEngagementRate.setPaddingRight(0);
			cellEngagementRate.setPaddingLeft(0);
			cellEngagementRate.setPaddingTop(0);
			cellEngagementRate.setPaddingBottom(2);

			Paragraph parEngagementRate = new Paragraph();
			parEngagementRate.setLeading(11);

			Image imgEngagementRate = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_ENGAGEMENT_RATE));
			imgEngagementRate.scaleAbsolute(10, 10);

			parEngagementRate.add(new Chunk(imgEngagementRate, 0, 0));

			parEngagementRate.add(new Chunk(" " + reportTwitterOverview.getFollowersEngagement().toString(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(182, 237, 30))));

			cellEngagementRate.addElement(parEngagementRate);
			
			PdfPCell cellEngagementRateDescription = new PdfPCell();
			cellEngagementRateDescription.setBorder(PdfPCell.NO_BORDER);
			cellEngagementRateDescription.setPaddingRight(0);
			cellEngagementRateDescription.setPaddingLeft(0);
			cellEngagementRateDescription.setPaddingTop(0);
			cellEngagementRateDescription.setPaddingBottom(8);

			Paragraph parEngagementRateDescription = new Paragraph();
			parEngagementRateDescription.setLeading(7);
			
			String engagementRateDesc = reportTwitterOverview.getFollowersEngagedCount() + " dos seus seguidores interagiram via menções, " +
					"respostas ou retweets com o seu conteúdo nos últimos " +
					UtilFunctions.getDaysBetween(reportTwitterOverview.getDateFrom(), reportTwitterOverview.getDateUntil()) + " dias, o que representa um indíce de " + 
					reportTwitterOverview.getFollowersEngagementPercentage() + "% de engajamento.";

			parEngagementRateDescription.add(engagementRateDesc);
			parEngagementRateDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellEngagementRateDescription.addElement(parEngagementRateDescription);
			
			// Average activity.
			PdfPCell cellAverageActivityTitle = new PdfPCell();
			cellAverageActivityTitle.setBorder(PdfPCell.NO_BORDER);
			cellAverageActivityTitle.setPaddingRight(0);
			cellAverageActivityTitle.setPaddingLeft(0);
			cellAverageActivityTitle.setPaddingTop(0);
			cellAverageActivityTitle.setPaddingBottom(2);

			Paragraph parAverageActivityTitle = new Paragraph("MÉDIA DE ATIVIDADE", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parAverageActivityTitle.setLeading(9);

			cellAverageActivityTitle.addElement(parAverageActivityTitle);

			PdfPCell cellAverageActivity = new PdfPCell();
			cellAverageActivity.setBorder(PdfPCell.NO_BORDER);
			cellAverageActivity.setPaddingRight(0);
			cellAverageActivity.setPaddingLeft(0);
			cellAverageActivity.setPaddingTop(0);
			cellAverageActivity.setPaddingBottom(2);

			Paragraph parAverageActivity = new Paragraph();
			parAverageActivity.setLeading(11);

			Image imgAverageActivity = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_AVERAGE_ACTIVITY));
			imgAverageActivity.scaleAbsolute(10, 10);

			parAverageActivity.add(new Chunk(imgAverageActivity, 0, 0));

			parAverageActivity.add(new Chunk(" " + UtilFunctions.getActivityPeriodName(reportTwitterOverview.getFollowersActivityBiggerPeriod()).toUpperCase(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(255, 170, 21))));

			cellAverageActivity.addElement(parAverageActivity);

			PdfPCell cellAverageActivityDescription = new PdfPCell();
			cellAverageActivityDescription.setBorder(PdfPCell.NO_BORDER);
			cellAverageActivityDescription.setPaddingRight(0);
			cellAverageActivityDescription.setPaddingLeft(0);
			cellAverageActivityDescription.setPaddingTop(0);
			cellAverageActivityDescription.setPaddingBottom(8);

			Paragraph parAverageActivityDescription = new Paragraph();
			parAverageActivityDescription.setLeading(7);

			String averageActivityDesc = reportTwitterOverview.getFollowersActivityBiggerPercentage() + "% dos seus seguidores tweetam em média a cada " + 
					UtilFunctions.getActivityPeriodName(reportTwitterOverview.getFollowersActivityBiggerPeriod()) + ".";

			parAverageActivityDescription.add(averageActivityDesc);
			parAverageActivityDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellAverageActivityDescription.addElement(parAverageActivityDescription);

			tableFollowersActivityInfo.addCell(cellEngagementRateTitle);
			tableFollowersActivityInfo.addCell(cellEngagementRate);
			tableFollowersActivityInfo.addCell(cellEngagementRateDescription);
			tableFollowersActivityInfo.addCell(cellAverageActivityTitle);
			tableFollowersActivityInfo.addCell(cellAverageActivity);
			tableFollowersActivityInfo.addCell(cellAverageActivityDescription);

			cellFollowersActivityInfo.addElement(tableFollowersActivityInfo);

			tableFollowersActivityContent.addCell(cellChartFollowersActivity);
			tableFollowersActivityContent.addCell(cellFollowersActivityInfo);

			cellFollowersActivity.addElement(tableFollowersActivityContent);

			tableFollowersActivity.addCell(cellFollowersActivityTitle);
			tableFollowersActivity.addCell(cellFollowersActivity);

			document.add(tableFollowersActivity);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addAboutFollowers() {
		
		try {

			PdfPTable tableAboutFollowers = new PdfPTable(1);
			tableAboutFollowers.setTotalWidth(new float[]{523});
			tableAboutFollowers.setLockedWidth(true);
			tableAboutFollowers.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableAboutFollowers.setSpacingAfter(22);

			PdfPCell cellAboutFollowersTitle = new PdfPCell();
			cellAboutFollowersTitle.setBorder(Rectangle.BOTTOM);
			cellAboutFollowersTitle.setBorderWidthBottom(0.8f);
			cellAboutFollowersTitle.setPaddingBottom(5);
			cellAboutFollowersTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parAboutFollowersTitle = new Paragraph("SOBRE OS SEGUIDORES E PÚBLICO POTENCIAL", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parAboutFollowersTitle.setAlignment(Element.ALIGN_CENTER);

			cellAboutFollowersTitle.addElement(parAboutFollowersTitle);

			PdfPCell cellAboutFollowers = new PdfPCell();
			cellAboutFollowers.setBorder(PdfPCell.NO_BORDER);
			cellAboutFollowers.setPaddingBottom(0);

			PdfPTable tableAboutFollowersContent = new PdfPTable(2);
			tableAboutFollowersContent.setTotalWidth(new float[]{200, 175});
			tableAboutFollowersContent.setLockedWidth(true);

			PdfPCell cellChartFollowersOfFollowers = new PdfPCell();
			cellChartFollowersOfFollowers.setBorder(PdfPCell.NO_BORDER);
			cellChartFollowersOfFollowers.setPadding(0);

			cellChartFollowersOfFollowers.addElement(addChartFollowersOfFollowers());

			PdfPCell cellAboutFollowersInfo = new PdfPCell();
			cellAboutFollowersInfo.setBorder(PdfPCell.NO_BORDER);
			cellAboutFollowersInfo.setPaddingTop(8);
			cellAboutFollowersInfo.setPaddingBottom(0);
			cellAboutFollowersInfo.setPaddingRight(0);
			cellAboutFollowersInfo.setPaddingLeft(30);

			PdfPTable tableAboutFollowersInfo = new PdfPTable(1);
			tableAboutFollowersInfo.setTotalWidth(new float[]{175});
			tableAboutFollowersInfo.setLockedWidth(true);
			tableAboutFollowersInfo.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			tableAboutFollowersInfo.getDefaultCell().setPadding(0);

			// Division between men and women.
			PdfPCell cellMenWomenFollowersTitle = new PdfPCell();
			cellMenWomenFollowersTitle.setBorder(PdfPCell.NO_BORDER);
			cellMenWomenFollowersTitle.setPaddingRight(0);
			cellMenWomenFollowersTitle.setPaddingLeft(0);
			cellMenWomenFollowersTitle.setPaddingTop(0);
			cellMenWomenFollowersTitle.setPaddingBottom(2);

			Paragraph parMenWomenFollowersTitle = new Paragraph("DIVISÃO POR SEXO DOS SEGUIDORES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parMenWomenFollowersTitle.setLeading(9);

			cellMenWomenFollowersTitle.addElement(parMenWomenFollowersTitle);
			
			PdfPTable tableMenWomenFollowersData = new PdfPTable(2);

			PdfPCell cellMenFollowers = new PdfPCell();
			cellMenFollowers.setBorder(PdfPCell.NO_BORDER);
			cellMenFollowers.setPaddingRight(0);
			cellMenFollowers.setPaddingLeft(0);
			cellMenFollowers.setPaddingTop(0);
			cellMenFollowers.setPaddingBottom(8);

			Image imgMenFollowers = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_MEN_FANS));
			imgMenFollowers.scaleAbsolute(9, 24);
			
			Paragraph parMenFollowers = new Paragraph();
			parMenFollowers.setLeading(24);
			parMenFollowers.setAlignment(Paragraph.ALIGN_MIDDLE);

			Chunk chkMenFansPerc = new Chunk(reportTwitterOverview.getMenFollowersPerc() + "% ",
					new Font(sourceSansPro, 25, Font.NORMAL, new BaseColor(62, 179, 209))); 
			chkMenFansPerc.setTextRise(3);
			
			parMenFollowers.add(chkMenFansPerc);
			parMenFollowers.add(new Chunk(imgMenFollowers, 0, 0));

			cellMenFollowers.addElement(parMenFollowers);
			
			PdfPCell cellWomenFollowers = new PdfPCell();
			cellWomenFollowers.setBorder(PdfPCell.NO_BORDER);
			cellWomenFollowers.setPaddingRight(0);
			cellWomenFollowers.setPaddingLeft(0);
			cellWomenFollowers.setPaddingTop(0);
			cellWomenFollowers.setPaddingBottom(8);

			Paragraph parWomenFollowers = new Paragraph();
			parWomenFollowers.setLeading(24);
			parWomenFollowers.setAlignment(Paragraph.ALIGN_MIDDLE);
			
			Image imgWomenFollowers = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_WOMEN_FANS));
			imgWomenFollowers.scaleAbsolute(12, 24);

			Chunk chkWomenFansPerc = new Chunk(reportTwitterOverview.getWomenFollowersPerc() + "% ",
					new Font(sourceSansPro, 25, Font.NORMAL, new BaseColor(226, 43, 79)));
			chkWomenFansPerc.setTextRise(3);
			
			parWomenFollowers.add(chkWomenFansPerc);
			parWomenFollowers.add(new Chunk(imgWomenFollowers, 0, 0));

			cellWomenFollowers.addElement(parWomenFollowers);
			
			tableMenWomenFollowersData.addCell(cellMenFollowers);
			tableMenWomenFollowersData.addCell(cellWomenFollowers);

			// Average followers of followers.
			PdfPCell cellAverageFollowersOfFollowersTitle = new PdfPCell();
			cellAverageFollowersOfFollowersTitle.setBorder(PdfPCell.NO_BORDER);
			cellAverageFollowersOfFollowersTitle.setPaddingRight(0);
			cellAverageFollowersOfFollowersTitle.setPaddingLeft(0);
			cellAverageFollowersOfFollowersTitle.setPaddingTop(0);
			cellAverageFollowersOfFollowersTitle.setPaddingBottom(2);

			Paragraph parAverageFollowersOfFollowersTitle = new Paragraph("MÉDIA DE SEGUIDORES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parAverageFollowersOfFollowersTitle.setLeading(9);

			cellAverageFollowersOfFollowersTitle.addElement(parAverageFollowersOfFollowersTitle);

			PdfPCell cellAverageFollowersOfFollowers = new PdfPCell();
			cellAverageFollowersOfFollowers.setBorder(PdfPCell.NO_BORDER);
			cellAverageFollowersOfFollowers.setPaddingRight(0);
			cellAverageFollowersOfFollowers.setPaddingLeft(0);
			cellAverageFollowersOfFollowers.setPaddingTop(0);
			cellAverageFollowersOfFollowers.setPaddingBottom(8);

			Paragraph parAverageFollowersOfFollowers = new Paragraph();
			parAverageFollowersOfFollowers.setLeading(11);

			Image imgAverageFollowersOfFollowers = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_AVERAGE_FOLLOWERS_OF_FOLLOWERS));
			imgAverageFollowersOfFollowers.scaleAbsolute(10, 10);

			parAverageFollowersOfFollowers.add(new Chunk(imgAverageFollowersOfFollowers, 0, 0));

			parAverageFollowersOfFollowers.add(new Chunk(" " + reportTwitterOverview.getAverageFollowersOfFollowers().toString(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(243, 105, 36))));

			cellAverageFollowersOfFollowers.addElement(parAverageFollowersOfFollowers);

			// Potential reach.
			PdfPCell cellPotentialReachTitle = new PdfPCell();
			cellPotentialReachTitle.setBorder(PdfPCell.NO_BORDER);
			cellPotentialReachTitle.setPaddingRight(0);
			cellPotentialReachTitle.setPaddingLeft(0);
			cellPotentialReachTitle.setPaddingTop(0);
			cellPotentialReachTitle.setPaddingBottom(2);

			Paragraph parPotentialReachTitle = new Paragraph("ALCANCE POTENCIAL", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parPotentialReachTitle.setLeading(9);

			cellPotentialReachTitle.addElement(parPotentialReachTitle);

			PdfPCell cellPotentialReach = new PdfPCell();
			cellPotentialReach.setBorder(PdfPCell.NO_BORDER);
			cellPotentialReach.setPaddingRight(0);
			cellPotentialReach.setPaddingLeft(0);
			cellPotentialReach.setPaddingTop(0);
			cellPotentialReach.setPaddingBottom(2);

			Paragraph parPotentialReach = new Paragraph();
			parPotentialReach.setLeading(11);

			Image imgPotentialReach = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_POTENTIAL_REACH));
			imgPotentialReach.scaleAbsolute(10, 10);

			parPotentialReach.add(new Chunk(imgPotentialReach, 0, 0));

			parPotentialReach.add(new Chunk(" " + reportTwitterOverview.getPotentialReach().toString(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(74, 200, 232))));

			cellPotentialReach.addElement(parPotentialReach);
			
			PdfPCell cellPotentialReachDesc = new PdfPCell();
			cellPotentialReachDesc.setBorder(PdfPCell.NO_BORDER);
			cellPotentialReachDesc.setPaddingRight(0);
			cellPotentialReachDesc.setPaddingLeft(0);
			cellPotentialReachDesc.setPaddingTop(0);

			Paragraph parPotentialReachDesc = new Paragraph();
			parPotentialReachDesc.setLeading(7);
			
			String potentialReachDesc = reportTwitterOverview.getRetweetCount() + " retweets foram gerados pelo total do público envolvido com o seu perfil no período, o que gerou " +
					"um alcance potencial de " + reportTwitterOverview.getPotentialReach() + " usuários.";
			
			parPotentialReachDesc.add(potentialReachDesc);
			parPotentialReachDesc.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellPotentialReachDesc.addElement(parPotentialReachDesc);

			tableAboutFollowersInfo.addCell(cellMenWomenFollowersTitle);
			tableAboutFollowersInfo.addCell(tableMenWomenFollowersData);
			tableAboutFollowersInfo.addCell(cellAverageFollowersOfFollowersTitle);
			tableAboutFollowersInfo.addCell(cellAverageFollowersOfFollowers);
			tableAboutFollowersInfo.addCell(cellPotentialReachTitle);
			tableAboutFollowersInfo.addCell(cellPotentialReach);
			tableAboutFollowersInfo.addCell(cellPotentialReachDesc);

			cellAboutFollowersInfo.addElement(tableAboutFollowersInfo);

			tableAboutFollowersContent.addCell(cellChartFollowersOfFollowers);
			tableAboutFollowersContent.addCell(cellAboutFollowersInfo);

			cellAboutFollowers.addElement(tableAboutFollowersContent);

			tableAboutFollowers.addCell(cellAboutFollowersTitle);
			tableAboutFollowers.addCell(cellAboutFollowers);

			document.add(tableAboutFollowers);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addEngagementWithTweets() {
		
		try {
			
			PdfPTable tableEngagementWithTweets = new PdfPTable(1);
			tableEngagementWithTweets.setTotalWidth(new float[]{523});
			tableEngagementWithTweets.setLockedWidth(true);
			tableEngagementWithTweets.setSpacingAfter(22);

			PdfPCell cellEngagementWithTweetsTitle = new PdfPCell();
			cellEngagementWithTweetsTitle.setBorder(Rectangle.BOTTOM);
			cellEngagementWithTweetsTitle.setBorderWidthBottom(0.8f);
			cellEngagementWithTweetsTitle.setPaddingBottom(5);
			cellEngagementWithTweetsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
			
			Paragraph parEngagementWithTweetsTitle = new Paragraph("ENGAJAMENTO COM OS TWEETS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parEngagementWithTweetsTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellEngagementWithTweetsTitle.addElement(parEngagementWithTweetsTitle);
			
			PdfPCell cellChartEngagementWithTweets = new PdfPCell();
			cellChartEngagementWithTweets.setBorder(PdfPCell.NO_BORDER);
			cellChartEngagementWithTweets.setPadding(1);

			cellChartEngagementWithTweets.addElement(addChartEngagementWithTweets());
			
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
			
			// Replies.
			PdfPCell cellReplies = new PdfPCell();
			cellReplies.setBorder(PdfPCell.NO_BORDER);
			cellReplies.setPaddingLeft(0);
			cellReplies.setPaddingRight(0);
			cellReplies.setPaddingTop(3);
			cellReplies.setPaddingBottom(0);

			PdfPTable tableReplies = new PdfPTable(2);
			tableReplies.setTotalWidth(new float[]{20, 68});
			tableReplies.setLockedWidth(true);

			PdfPCell cellRepliesImage = new PdfPCell();
			cellRepliesImage.setBorder(PdfPCell.NO_BORDER);
			cellRepliesImage.setPadding(0);

			Image imgReplies = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_REPLIES));
			imgReplies.scaleAbsolute(20, 20);

			cellRepliesImage.addElement(imgReplies);

			PdfPCell cellRepliesPerc = new PdfPCell();
			cellRepliesPerc.setBorder(PdfPCell.NO_BORDER);
			cellRepliesPerc.setPaddingLeft(3);
			cellRepliesPerc.setPaddingRight(0);
			cellRepliesPerc.setPaddingTop(0);
			cellRepliesPerc.setPaddingBottom(0);

			Phrase parReplies = new Phrase("REPLIES", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parReplies.setLeading(7);

			Paragraph parRepliesPercentage = new Paragraph(reportTwitterOverview.getEngagementPercentages().containsKey(ReportTwitter.REPLIES_OF_ME) 
					? reportTwitterOverview.getEngagementPercentages().get(ReportTwitter.REPLIES_OF_ME) + "%" : "0%", 
							new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(159, 220, 53)));
			parRepliesPercentage.setLeading(14);

			cellRepliesPerc.addElement(parReplies);
			cellRepliesPerc.addElement(parRepliesPercentage);

			tableReplies.addCell(cellRepliesImage);
			tableReplies.addCell(cellRepliesPerc);

			cellReplies.addElement(tableReplies);
			
			// Retweets.
			PdfPCell cellRetweets = new PdfPCell();
			cellRetweets.setBorder(PdfPCell.NO_BORDER);
			cellRetweets.setPaddingLeft(0);
			cellRetweets.setPaddingRight(0);
			cellRetweets.setPaddingTop(3);
			cellRetweets.setPaddingBottom(0);

			PdfPTable tableRetweets = new PdfPTable(2);
			tableRetweets.setTotalWidth(new float[]{20, 68});
			tableRetweets.setLockedWidth(true);

			PdfPCell cellRetweetsImage = new PdfPCell();
			cellRetweetsImage.setBorder(PdfPCell.NO_BORDER);
			cellRetweetsImage.setPadding(0);

			Image imgRetweets = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_RETWEETS));
			imgRetweets.scaleAbsolute(20, 20);

			cellRetweetsImage.addElement(imgRetweets);

			PdfPCell cellRetweetsPerc = new PdfPCell();
			cellRetweetsPerc.setBorder(PdfPCell.NO_BORDER);
			cellRetweetsPerc.setPaddingLeft(3);
			cellRetweetsPerc.setPaddingRight(0);
			cellRetweetsPerc.setPaddingTop(0);
			cellRetweetsPerc.setPaddingBottom(0);

			Phrase parRetweets = new Phrase("RETWEETS", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parRetweets.setLeading(7);

			Paragraph parRetweetsPercentage = new Paragraph(reportTwitterOverview.getEngagementPercentages().containsKey(ReportTwitter.RETWEETS_OF_ME) 
					? reportTwitterOverview.getEngagementPercentages().get(ReportTwitter.RETWEETS_OF_ME) + "%" : "0%", 
							new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(243, 105, 36)));
			parRetweetsPercentage.setLeading(14);

			cellRetweetsPerc.addElement(parRetweets);
			cellRetweetsPerc.addElement(parRetweetsPercentage);

			tableRetweets.addCell(cellRetweetsImage);
			tableRetweets.addCell(cellRetweetsPerc);

			cellRetweets.addElement(tableRetweets);
			
			// Mentions.
			PdfPCell cellMentions = new PdfPCell();
			cellMentions.setBorder(PdfPCell.NO_BORDER);
			cellMentions.setPaddingLeft(0);
			cellMentions.setPaddingRight(0);
			cellMentions.setPaddingTop(14);
			cellMentions.setPaddingBottom(0);

			PdfPTable tableMentions = new PdfPTable(2);
			tableMentions.setTotalWidth(new float[]{20, 68});
			tableMentions.setLockedWidth(true);

			PdfPCell cellMentionsImage = new PdfPCell();
			cellMentionsImage.setBorder(PdfPCell.NO_BORDER);
			cellMentionsImage.setPadding(0);

			Image imgMentions = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_MENTIONS));
			imgMentions.scaleAbsolute(20, 20);

			cellMentionsImage.addElement(imgMentions);

			PdfPCell cellMentionsPerc = new PdfPCell();
			cellMentionsPerc.setBorder(PdfPCell.NO_BORDER);
			cellMentionsPerc.setPaddingLeft(3);
			cellMentionsPerc.setPaddingRight(0);
			cellMentionsPerc.setPaddingTop(0);
			cellMentionsPerc.setPaddingBottom(0);

			Phrase parMentions = new Phrase("MENTIONS", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parMentions.setLeading(7);

			Paragraph parMentionsPercentage = new Paragraph(reportTwitterOverview.getEngagementPercentages().containsKey(ReportTwitter.MENTIONS) 
					? reportTwitterOverview.getEngagementPercentages().get(ReportTwitter.MENTIONS) + "%" : "0%", 
							new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(109, 202, 225)));
			parMentionsPercentage.setLeading(14);

			cellMentionsPerc.addElement(parMentions);
			cellMentionsPerc.addElement(parMentionsPercentage);

			tableMentions.addCell(cellMentionsImage);
			tableMentions.addCell(cellMentionsPerc);

			cellMentions.addElement(tableMentions);
			
			// Favorites.
			PdfPCell cellFavorites = new PdfPCell();
			cellFavorites.setBorder(PdfPCell.NO_BORDER);
			cellFavorites.setPaddingLeft(0);
			cellFavorites.setPaddingRight(0);
			cellFavorites.setPaddingTop(14);
			cellFavorites.setPaddingBottom(0);

			PdfPTable tableFavorites = new PdfPTable(2);
			tableFavorites.setTotalWidth(new float[]{20, 68});
			tableFavorites.setLockedWidth(true);

			PdfPCell cellFavoritesImage = new PdfPCell();
			cellFavoritesImage.setBorder(PdfPCell.NO_BORDER);
			cellFavoritesImage.setPadding(0);

			Image imgFavorites = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_FAVORITES));
			imgFavorites.scaleAbsolute(20, 20);

			cellFavoritesImage.addElement(imgFavorites);

			PdfPCell cellFavoritesPerc = new PdfPCell();
			cellFavoritesPerc.setBorder(PdfPCell.NO_BORDER);
			cellFavoritesPerc.setPaddingLeft(3);
			cellFavoritesPerc.setPaddingRight(0);
			cellFavoritesPerc.setPaddingTop(0);
			cellFavoritesPerc.setPaddingBottom(0);

			Phrase parFavorites = new Phrase("CURTIR", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parFavorites.setLeading(7);

			Paragraph parFavoritesPercentage = new Paragraph(reportTwitterOverview.getEngagementPercentages().containsKey(ReportTwitter.FAVORITE_COUNT) 
					? reportTwitterOverview.getEngagementPercentages().get(ReportTwitter.FAVORITE_COUNT) + "%" : "0%", 
							new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(243, 206, 36)));
			parFavoritesPercentage.setLeading(14);

			cellFavoritesPerc.addElement(parFavorites);
			cellFavoritesPerc.addElement(parFavoritesPercentage);

			tableFavorites.addCell(cellFavoritesImage);
			tableFavorites.addCell(cellFavoritesPerc);

			cellFavorites.addElement(tableFavorites);
			
			tableEngagementActions.addCell(cellReplies);
			tableEngagementActions.addCell(cellRetweets);
			tableEngagementActions.addCell(cellMentions);
			tableEngagementActions.addCell(cellFavorites);
			
			cellEngagementActions.addElement(tableEngagementActions);
			
			// Engagement peak.
			PdfPCell cellEngagementPeak = new PdfPCell();
			cellEngagementPeak.setBorder(PdfPCell.NO_BORDER);
			cellEngagementPeak.setPaddingLeft(18);
			cellEngagementPeak.setPaddingRight(0);
			cellEngagementPeak.setPaddingTop(0);
			cellEngagementPeak.setPaddingBottom(0);
			cellEngagementPeak.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

			Paragraph parEngagementPeakTitle = new Paragraph("PICO DE ENVOLVIMENTO", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parEngagementPeakTitle.setLeading(7);

			Paragraph parEngagementPeak = new Paragraph();
			parEngagementPeak.setLeading(15);

			Image imgMostEngagementMonth = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_ENGAGEMENT_PEAK));
			imgMostEngagementMonth.scaleAbsolute(10, 10);
			
			String engagementPeakDayFormatted = "-";
			if(reportTwitterOverview.getEngagementPeakDay() > 0L) {
				engagementPeakDayFormatted = new SimpleDateFormat("dd/MM/yyyy").format(new Date(reportTwitterOverview.getEngagementPeakDay() * 1000L));
			}

			parEngagementPeak.add(new Chunk(imgMostEngagementMonth, 0, 0));
			parEngagementPeak.add(new Chunk(" " + engagementPeakDayFormatted, new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(69, 135, 201))));

			cellEngagementPeak.addElement(parEngagementPeakTitle);
			cellEngagementPeak.addElement(parEngagementPeak);
			
			tableEngagementStats.addCell(cellEngagementActions);
			tableEngagementStats.addCell(cellEngagementPeak);
			
			cellEngagementWithContent.addElement(tableEngagementStats);
			
			tableEngagementWithTweets.addCell(cellEngagementWithTweetsTitle);
			tableEngagementWithTweets.addCell(cellChartEngagementWithTweets);
			tableEngagementWithTweets.addCell(cellEngagementWithContent);
			
			document.add(tableEngagementWithTweets);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	private void addTweetsOverTime() {
		
		try {
			
			PdfPTable tableTweetsOverTime = new PdfPTable(1);
			tableTweetsOverTime.setTotalWidth(new float[]{523});
			tableTweetsOverTime.setLockedWidth(true);
			tableTweetsOverTime.setSpacingAfter(22);

			PdfPCell cellTweetsOverTimeTitle = new PdfPCell();
			cellTweetsOverTimeTitle.setBorder(Rectangle.BOTTOM);
			cellTweetsOverTimeTitle.setBorderWidthBottom(0.8f);
			cellTweetsOverTimeTitle.setPaddingBottom(5);
			cellTweetsOverTimeTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
			
			Paragraph parTweetsOverTimeTitle = new Paragraph("TWEETS PUBLICADOS DIVIDIDOS POR HORÁRIO E DIA DA SEMANA", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parTweetsOverTimeTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellTweetsOverTimeTitle.addElement(parTweetsOverTimeTitle);
			
			PdfPCell cellChartTweetsSentPerHour = new PdfPCell();
			cellChartTweetsSentPerHour.setBorder(PdfPCell.NO_BORDER);
			cellChartTweetsSentPerHour.setPadding(1);

			cellChartTweetsSentPerHour.addElement(addChartTweetsSentPerHour());
			
			PdfPCell cellTweetsOverTimeStats = new PdfPCell();
			cellTweetsOverTimeStats.setBorder(PdfPCell.NO_BORDER);
			cellTweetsOverTimeStats.setPadding(1);
			
			PdfPTable tableTweetsOverTimeStats = new PdfPTable(2);
			tableTweetsOverTimeStats.setTotalWidth(new float[]{210, 170});
			tableTweetsOverTimeStats.setLockedWidth(true);
			tableTweetsOverTimeStats.setSpacingBefore(10);
			
			PdfPCell cellChartTweetsSentPerWeekDay = new PdfPCell();
			cellChartTweetsSentPerWeekDay.setBorder(PdfPCell.NO_BORDER);
			cellChartTweetsSentPerWeekDay.setPadding(0);
			
			cellChartTweetsSentPerWeekDay.addElement(addChartTweetsSentPerWeekDay());
			
			PdfPCell cellTweetsOverTimeStatsInfo = new PdfPCell();
			cellTweetsOverTimeStatsInfo.setBorder(PdfPCell.NO_BORDER);
			cellTweetsOverTimeStatsInfo.setPaddingTop(8);
			cellTweetsOverTimeStatsInfo.setPaddingBottom(0);
			cellTweetsOverTimeStatsInfo.setPaddingRight(0);
			cellTweetsOverTimeStatsInfo.setPaddingLeft(30);

			PdfPTable tableTweetsOverTimeStatsInfo = new PdfPTable(1);
			tableTweetsOverTimeStatsInfo.setTotalWidth(new float[]{170});
			tableTweetsOverTimeStatsInfo.setLockedWidth(true);

			// Time with more tweets sent.
			PdfPCell cellTimeMoreTweetsSentTitle = new PdfPCell();
			cellTimeMoreTweetsSentTitle.setBorder(PdfPCell.NO_BORDER);
			cellTimeMoreTweetsSentTitle.setPaddingRight(0);
			cellTimeMoreTweetsSentTitle.setPaddingLeft(0);
			cellTimeMoreTweetsSentTitle.setPaddingTop(0);
			cellTimeMoreTweetsSentTitle.setPaddingBottom(4);

			Paragraph parTimeMoreTweetsSentTitle = new Paragraph("HORÁRIO COM MAIS TWEETS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parTimeMoreTweetsSentTitle.setLeading(9);

			cellTimeMoreTweetsSentTitle.addElement(parTimeMoreTweetsSentTitle);

			PdfPCell cellTimeMoreTweetsSent = new PdfPCell();
			cellTimeMoreTweetsSent.setBorder(PdfPCell.NO_BORDER);
			cellTimeMoreTweetsSent.setPaddingRight(0);
			cellTimeMoreTweetsSent.setPaddingLeft(0);
			cellTimeMoreTweetsSent.setPaddingTop(0);
			cellTimeMoreTweetsSent.setPaddingBottom(2);

			Paragraph parTimeMoreTweetsSent = new Paragraph();
			parTimeMoreTweetsSent.setLeading(11);

			Image imgTimeMoreTweetsSent = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_TIME_MORE_TWEETS_SENT));
			imgTimeMoreTweetsSent.scaleAbsolute(10, 10);

			parTimeMoreTweetsSent.add(new Chunk(imgTimeMoreTweetsSent, 0, 0));
			
			String timeWithMoreTweetsFrom = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportTwitterOverview.getTimeWithMoreTweets(), 0, 0));
			String timeWithMoreTweetsUntil = new SimpleDateFormat("HH:mm").format(new Date(0, 0, 0, reportTwitterOverview.getTimeWithMoreTweets() + 1, 0, 0));
			
			parTimeMoreTweetsSent.add(new Chunk(" " + timeWithMoreTweetsFrom + " - " + timeWithMoreTweetsUntil,
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(145, 205, 51))));

			cellTimeMoreTweetsSent.addElement(parTimeMoreTweetsSent);

			PdfPCell cellTimeMoreTweetsSentDescription = new PdfPCell();
			cellTimeMoreTweetsSentDescription.setBorder(PdfPCell.NO_BORDER);
			cellTimeMoreTweetsSentDescription.setPaddingRight(0);
			cellTimeMoreTweetsSentDescription.setPaddingLeft(0);
			cellTimeMoreTweetsSentDescription.setPaddingTop(0);
			cellTimeMoreTweetsSentDescription.setPaddingBottom(8);

			Paragraph parTimeMoreTweetsSentDescription = new Paragraph(7, reportTwitterOverview.getTimeWithMoreTweetsPercentage() + "% " +
					" dos seu tweets estão concentrados entre " + timeWithMoreTweetsFrom + " e " + timeWithMoreTweetsUntil + "hrs.", 
					new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));
			
			cellTimeMoreTweetsSentDescription.addElement(parTimeMoreTweetsSentDescription);
			
			// Week day with more tweets sent.
			PdfPCell cellWeekDayMoreTweetsSentTitle = new PdfPCell();
			cellWeekDayMoreTweetsSentTitle.setBorder(PdfPCell.NO_BORDER);
			cellWeekDayMoreTweetsSentTitle.setPaddingRight(0);
			cellWeekDayMoreTweetsSentTitle.setPaddingLeft(0);
			cellWeekDayMoreTweetsSentTitle.setPaddingTop(0);
			cellWeekDayMoreTweetsSentTitle.setPaddingBottom(4);

			Paragraph parWeekDayMoreTweetsSentTitle = new Paragraph("DIA COM MAIS TWEETS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parWeekDayMoreTweetsSentTitle.setLeading(9);

			cellWeekDayMoreTweetsSentTitle.addElement(parWeekDayMoreTweetsSentTitle);

			PdfPCell cellWeekDayMoreTweetsSent = new PdfPCell();
			cellWeekDayMoreTweetsSent.setBorder(PdfPCell.NO_BORDER);
			cellWeekDayMoreTweetsSent.setPaddingRight(0);
			cellWeekDayMoreTweetsSent.setPaddingLeft(0);
			cellWeekDayMoreTweetsSent.setPaddingTop(0);
			cellWeekDayMoreTweetsSent.setPaddingBottom(2);

			Paragraph parWeekDayMoreTweetsSent = new Paragraph();
			parWeekDayMoreTweetsSent.setLeading(11);

			Image imgWeekDayMoreTweetsSent = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_WEEK_DAY_MORE_TWEETS_SENT));
			imgWeekDayMoreTweetsSent.scaleAbsolute(10, 10);

			parWeekDayMoreTweetsSent.add(new Chunk(imgWeekDayMoreTweetsSent, 0, 0));

			parWeekDayMoreTweetsSent.add(new Chunk(" " + OpSocialBackApplication.utilFunctionsService.getFormattedWeekDayShort(reportTwitterOverview.getWeekDayWithMoreTweets()).toUpperCase(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(255, 168, 17))));

			cellWeekDayMoreTweetsSent.addElement(parWeekDayMoreTweetsSent);

			PdfPCell cellWeekDayMoreTweetsSentDescription = new PdfPCell();
			cellWeekDayMoreTweetsSentDescription.setBorder(PdfPCell.NO_BORDER);
			cellWeekDayMoreTweetsSentDescription.setPaddingRight(0);
			cellWeekDayMoreTweetsSentDescription.setPaddingLeft(0);
			cellWeekDayMoreTweetsSentDescription.setPaddingTop(0);
			cellWeekDayMoreTweetsSentDescription.setPaddingBottom(8);

			Paragraph parWeekDayMoreTweetsSentDescription = new Paragraph(7, reportTwitterOverview.getWeekDayWithMoreTweetsPercentage() + "% " +
					"dos seu tweets estão concentrados " + OpSocialBackApplication.utilFunctionsService.getFormattedWeekDay(reportTwitterOverview.getWeekDayWithMoreTweets()) + ".", 
					new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellWeekDayMoreTweetsSentDescription.addElement(parWeekDayMoreTweetsSentDescription);
			
			tableTweetsOverTimeStatsInfo.addCell(cellTimeMoreTweetsSentTitle);
			tableTweetsOverTimeStatsInfo.addCell(cellTimeMoreTweetsSent);
			tableTweetsOverTimeStatsInfo.addCell(cellTimeMoreTweetsSentDescription);
			tableTweetsOverTimeStatsInfo.addCell(cellWeekDayMoreTweetsSentTitle);
			tableTweetsOverTimeStatsInfo.addCell(cellWeekDayMoreTweetsSent);
			tableTweetsOverTimeStatsInfo.addCell(cellWeekDayMoreTweetsSentDescription);
			
			cellTweetsOverTimeStatsInfo.addElement(tableTweetsOverTimeStatsInfo);
			
			tableTweetsOverTimeStats.addCell(cellChartTweetsSentPerWeekDay);
			tableTweetsOverTimeStats.addCell(cellTweetsOverTimeStatsInfo);
			
			cellTweetsOverTimeStats.addElement(tableTweetsOverTimeStats);
			
			tableTweetsOverTime.addCell(cellTweetsOverTimeTitle);
			tableTweetsOverTime.addCell(cellChartTweetsSentPerHour);
			tableTweetsOverTime.addCell(cellTweetsOverTimeStats);
			
			document.add(tableTweetsOverTime);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addTweetsSent() {
		
		try {

			PdfPTable tableTweetsSent = new PdfPTable(1);
			tableTweetsSent.setTotalWidth(new float[]{523});
			tableTweetsSent.setLockedWidth(true);
			tableTweetsSent.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableTweetsSent.setSpacingAfter(22);

			PdfPCell cellTweetsSentTitle = new PdfPCell();
			cellTweetsSentTitle.setBorder(Rectangle.BOTTOM);
			cellTweetsSentTitle.setBorderWidthBottom(0.8f);
			cellTweetsSentTitle.setPaddingBottom(5);
			cellTweetsSentTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parTweetsSentTitle = new Paragraph("TWEETS ENVIADOS NO PERÍODO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parTweetsSentTitle.setAlignment(Element.ALIGN_CENTER);

			cellTweetsSentTitle.addElement(parTweetsSentTitle);

			PdfPCell cellTweetsSentInfo = new PdfPCell();
			cellTweetsSentInfo.setBorder(PdfPCell.NO_BORDER);
			cellTweetsSentInfo.setPaddingBottom(0);

			PdfPTable tableTweetsSentInfoContent = new PdfPTable(2);
			tableTweetsSentInfoContent.setTotalWidth(new float[]{200, 165});
			tableTweetsSentInfoContent.setLockedWidth(true);

			PdfPCell cellChartTweetsSent = new PdfPCell();
			cellChartTweetsSent.setBorder(PdfPCell.NO_BORDER);
			cellChartTweetsSent.setPadding(0);

			cellChartTweetsSent.addElement(addChartTweetsSent());

			PdfPCell cellTweetsSentStatsInfo = new PdfPCell();
			cellTweetsSentStatsInfo.setBorder(PdfPCell.NO_BORDER);
			cellTweetsSentStatsInfo.setPaddingTop(8);
			cellTweetsSentStatsInfo.setPaddingBottom(0);
			cellTweetsSentStatsInfo.setPaddingRight(0);
			cellTweetsSentStatsInfo.setPaddingLeft(30);

			PdfPTable tableTweetsSentStatsInfo = new PdfPTable(1);
			tableTweetsSentStatsInfo.setTotalWidth(new float[]{165});
			tableTweetsSentStatsInfo.setLockedWidth(true);

			// Average of tweets sent per day.
			PdfPCell cellAverageTweetsSentPerDayTitle = new PdfPCell();
			cellAverageTweetsSentPerDayTitle.setBorder(PdfPCell.NO_BORDER);
			cellAverageTweetsSentPerDayTitle.setPaddingRight(0);
			cellAverageTweetsSentPerDayTitle.setPaddingLeft(0);
			cellAverageTweetsSentPerDayTitle.setPaddingTop(0);
			cellAverageTweetsSentPerDayTitle.setPaddingBottom(2);

			Paragraph parAverageTweetsSentPerDayTitle = new Paragraph("MÉDIA DIÁRIA DE TWEETS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parAverageTweetsSentPerDayTitle.setLeading(9);

			cellAverageTweetsSentPerDayTitle.addElement(parAverageTweetsSentPerDayTitle);

			PdfPCell cellAverageTweetsSentPerDay = new PdfPCell();
			cellAverageTweetsSentPerDay.setBorder(PdfPCell.NO_BORDER);
			cellAverageTweetsSentPerDay.setPaddingRight(0);
			cellAverageTweetsSentPerDay.setPaddingLeft(0);
			cellAverageTweetsSentPerDay.setPaddingTop(0);
			cellAverageTweetsSentPerDay.setPaddingBottom(8);

			Paragraph parAverageTweetsSentPerDay = new Paragraph();
			parAverageTweetsSentPerDay.setLeading(11);

			Image imgAverageTweetsSentPerDay = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_AVERAGE_TWEETS_SENT_PER_DAY));
			imgAverageTweetsSentPerDay.scaleAbsolute(10, 10);

			parAverageTweetsSentPerDay.add(new Chunk(imgAverageTweetsSentPerDay, 0, 0));

			parAverageTweetsSentPerDay.add(new Chunk(" " + reportTwitterOverview.getAverageTweetsSentPerDay().toString(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(248, 222, 8))));

			cellAverageTweetsSentPerDay.addElement(parAverageTweetsSentPerDay);

			// Division by type of tweets sent.
			PdfPCell cellTweetsSentByTypeTitle = new PdfPCell();
			cellTweetsSentByTypeTitle.setBorder(PdfPCell.NO_BORDER);
			cellTweetsSentByTypeTitle.setPaddingRight(0);
			cellTweetsSentByTypeTitle.setPaddingLeft(0);
			cellTweetsSentByTypeTitle.setPaddingTop(0);
			cellTweetsSentByTypeTitle.setPaddingBottom(2);

			Paragraph parTweetsSentByTypeTitle = new Paragraph("DIVISÃO POR TIPO DE TWEET", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parTweetsSentByTypeTitle.setLeading(9);

			cellTweetsSentByTypeTitle.addElement(parTweetsSentByTypeTitle);

			PdfPCell cellChartTweetsSentByType = new PdfPCell();
			cellChartTweetsSentByType.setBorder(PdfPCell.NO_BORDER);
			cellChartTweetsSentByType.setPaddingLeft(0);
			cellChartTweetsSentByType.setPaddingRight(0);
			cellChartTweetsSentByType.setPaddingTop(0);
			cellChartTweetsSentByType.setPaddingBottom(4);

			cellChartTweetsSentByType.addElement(addChartTweetsSentByType());
			
			PdfPCell cellTweetsSentByTypeDescription = new PdfPCell();
			cellTweetsSentByTypeDescription.setBorder(PdfPCell.NO_BORDER);
			cellTweetsSentByTypeDescription.setPaddingRight(0);
			cellTweetsSentByTypeDescription.setPaddingLeft(0);
			cellTweetsSentByTypeDescription.setPaddingTop(0);
			cellTweetsSentByTypeDescription.setPaddingBottom(8);

			Paragraph parTweetsSentByTypeDescription = new Paragraph();
			parTweetsSentByTypeDescription.setLeading(7);
			
			String tweetsSentByTypeDescription = "O tipo de tweets com maior volume durante o período são " + OpSocialBackApplication.utilFunctionsService.getTweetTypeName(reportTwitterOverview.getTweetTypeWithMoreTweets()) + ", que representam " +
					reportTwitterOverview.getTweetTypeWithMoreTweetsPercentage() +"% dos tweets.";

			parTweetsSentByTypeDescription.add(tweetsSentByTypeDescription);
			parTweetsSentByTypeDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellTweetsSentByTypeDescription.addElement(parTweetsSentByTypeDescription);

			tableTweetsSentStatsInfo.addCell(cellAverageTweetsSentPerDayTitle);
			tableTweetsSentStatsInfo.addCell(cellAverageTweetsSentPerDay);
			tableTweetsSentStatsInfo.addCell(cellTweetsSentByTypeTitle);
			tableTweetsSentStatsInfo.addCell(cellChartTweetsSentByType);
			tableTweetsSentStatsInfo.addCell(cellTweetsSentByTypeDescription);

			cellTweetsSentStatsInfo.addElement(tableTweetsSentStatsInfo);

			tableTweetsSentInfoContent.addCell(cellChartTweetsSent);
			tableTweetsSentInfoContent.addCell(cellTweetsSentStatsInfo);

			cellTweetsSentInfo.addElement(tableTweetsSentInfoContent);

			tableTweetsSent.addCell(cellTweetsSentTitle);
			tableTweetsSent.addCell(cellTweetsSentInfo);

			document.add(tableTweetsSent);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

			Paragraph parFollowersStatsTitle = new Paragraph("SALDO DE SEGUIDORES", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parFollowersStatsTitle.setAlignment(Element.ALIGN_CENTER);

			cellFollowersStatsTitle.addElement(parFollowersStatsTitle);

			PdfPCell cellFollowersStats = new PdfPCell();
			cellFollowersStats.setBorder(PdfPCell.NO_BORDER);
			cellFollowersStats.setPaddingBottom(0);

			PdfPTable tableFollowersStatsContent = new PdfPTable(2);
			tableFollowersStatsContent.setTotalWidth(new float[]{200, 165});
			tableFollowersStatsContent.setLockedWidth(true);

			PdfPCell cellChartFollowers = new PdfPCell();
			cellChartFollowers.setBorder(PdfPCell.NO_BORDER);
			cellChartFollowers.setPadding(0);

			cellChartFollowers.addElement(addChartFollowers());

			PdfPCell cellFollowersStatsInfo = new PdfPCell();
			cellFollowersStatsInfo.setBorder(PdfPCell.NO_BORDER);
			cellFollowersStatsInfo.setPaddingTop(8);
			cellFollowersStatsInfo.setPaddingBottom(0);
			cellFollowersStatsInfo.setPaddingRight(0);
			cellFollowersStatsInfo.setPaddingLeft(30);

			PdfPTable tableFollowersStatsInfo = new PdfPTable(1);
			tableFollowersStatsInfo.setTotalWidth(new float[]{165});
			tableFollowersStatsInfo.setLockedWidth(true);

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

			Image imgTotalFollowers = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_TOTAL_FOLLOWERS));
			imgTotalFollowers.scaleAbsolute(10, 10);

			parTotalFollowers.add(new Chunk(imgTotalFollowers, 0, 0));

			parTotalFollowers.add(new Chunk(" " + reportTwitterOverview.getTotalOfFollowers().toString(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(74, 200, 232))));

			cellTotalFollowers.addElement(parTotalFollowers);

			// Followers balance.
			PdfPCell cellFollowersBalanceTitle = new PdfPCell();
			cellFollowersBalanceTitle.setBorder(PdfPCell.NO_BORDER);
			cellFollowersBalanceTitle.setPaddingRight(0);
			cellFollowersBalanceTitle.setPaddingLeft(0);
			cellFollowersBalanceTitle.setPaddingTop(0);
			cellFollowersBalanceTitle.setPaddingBottom(2);

			Paragraph parFollowersBalanceTitle = new Paragraph("SALDO NO PERÍODO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parFollowersBalanceTitle.setLeading(9);

			cellFollowersBalanceTitle.addElement(parFollowersBalanceTitle);

			PdfPCell cellFollowersBalance = new PdfPCell();
			cellFollowersBalance.setBorder(PdfPCell.NO_BORDER);
			cellFollowersBalance.setPaddingRight(0);
			cellFollowersBalance.setPaddingLeft(0);
			cellFollowersBalance.setPaddingTop(0);
			cellFollowersBalance.setPaddingBottom(2);

			Paragraph parFollowersBalance = new Paragraph();
			parFollowersBalance.setLeading(11);

			Image imgFollowersBalance = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_FOLLOWERS_BALANCE));
			imgFollowersBalance.scaleAbsolute(10, 10);

			parFollowersBalance.add(new Chunk(imgFollowersBalance, 0, 0));

			parFollowersBalance.add(new Chunk(" " + reportTwitterOverview.getFollowersBalance().toString(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(248, 222, 8))));

			cellFollowersBalance.addElement(parFollowersBalance);

			PdfPCell cellFollowersBalanceDescription = new PdfPCell();
			cellFollowersBalanceDescription.setBorder(PdfPCell.NO_BORDER);
			cellFollowersBalanceDescription.setPaddingRight(0);
			cellFollowersBalanceDescription.setPaddingLeft(0);
			cellFollowersBalanceDescription.setPaddingTop(0);
			cellFollowersBalanceDescription.setPaddingBottom(8);

			Paragraph parFollowersBalanceDescription = new Paragraph();
			parFollowersBalanceDescription.setLeading(7);
			
			String followersBalanceDescription = "Dia " + new SimpleDateFormat("dd/MM").format( 
					new Date(reportTwitterOverview.getDayWithMoreFollowers().getDate() * 1000L)) + " registrou o melhor saldo, com um aumento de " +
							+ reportTwitterOverview.getDayWithMoreFollowers().getValue() + " seguidores.";

			parFollowersBalanceDescription.add(followersBalanceDescription);
			parFollowersBalanceDescription.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellFollowersBalanceDescription.addElement(parFollowersBalanceDescription);

			// Followers growth rate.
			PdfPCell cellFollowersGrowthRateTitle = new PdfPCell();
			cellFollowersGrowthRateTitle.setBorder(PdfPCell.NO_BORDER);
			cellFollowersGrowthRateTitle.setPaddingRight(0);
			cellFollowersGrowthRateTitle.setPaddingLeft(0);
			cellFollowersGrowthRateTitle.setPaddingTop(0);
			cellFollowersGrowthRateTitle.setPaddingBottom(2);

			Paragraph parFollowersGrowthRateTitle = new Paragraph("ÍNDICE DE CRESCIMENTO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parFollowersGrowthRateTitle.setLeading(9);

			cellFollowersGrowthRateTitle.addElement(parFollowersGrowthRateTitle);

			PdfPCell cellFollowersGrowthRate = new PdfPCell();
			cellFollowersGrowthRate.setBorder(PdfPCell.NO_BORDER);
			cellFollowersGrowthRate.setPaddingRight(0);
			cellFollowersGrowthRate.setPaddingLeft(0);
			cellFollowersGrowthRate.setPaddingTop(0);
			cellFollowersGrowthRate.setPaddingBottom(2);

			Paragraph parFollowersGrowthRate = new Paragraph();
			parFollowersGrowthRate.setLeading(11);

			Image imgFollowersGrowthRate;
			BaseColor baseColorFollowersGrowthRate;
			String followersGrowthRateDescription;
			if(reportTwitterOverview.getFollowersGrowthRate() >= 0) {
				
				imgFollowersGrowthRate = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_FOLLOWERS_GROWTH_RATE));
				baseColorFollowersGrowthRate = new BaseColor(174, 217, 0);
				followersGrowthRateDescription = "A variação no período foi de " 
						+ reportTwitterOverview.getFollowersGrowthRate().toString().replace(".", ",") 
						+ "% que representa um aumento de " + reportTwitterOverview.getFollowersBalance() + " seguidores.";
				
			} else {
				
				imgFollowersGrowthRate = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_LOST_FANS));
				baseColorFollowersGrowthRate = new BaseColor(215, 54, 49);
				followersGrowthRateDescription = "A variação no período foi de " 
						+ reportTwitterOverview.getFollowersGrowthRate().toString().replace(".", ",") 
						+ "% que representa uma diminuição de " + reportTwitterOverview.getFollowersBalance() + " seguidores.";
				
			}
			
			imgFollowersGrowthRate.scaleAbsolute(10, 10);

			parFollowersGrowthRate.add(new Chunk(imgFollowersGrowthRate, 0, 0));

			parFollowersGrowthRate.add(new Chunk(" " + reportTwitterOverview.getFollowersGrowthRate().toString().replace(".", ",") + "%",
					new Font(sourceSansPro, 15, Font.NORMAL, baseColorFollowersGrowthRate)));

			cellFollowersGrowthRate.addElement(parFollowersGrowthRate);

			PdfPCell cellFollowersGrowthRateDesc = new PdfPCell();
			cellFollowersGrowthRateDesc.setBorder(PdfPCell.NO_BORDER);
			cellFollowersGrowthRateDesc.setPaddingRight(0);
			cellFollowersGrowthRateDesc.setPaddingLeft(0);
			cellFollowersGrowthRateDesc.setPaddingTop(0);

			Paragraph parFollowersGrowthRateDesc = new Paragraph();
			parFollowersGrowthRateDesc.setLeading(7);
			
			parFollowersGrowthRateDesc.add(followersGrowthRateDescription);
			parFollowersGrowthRateDesc.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellFollowersGrowthRateDesc.addElement(parFollowersGrowthRateDesc);

			tableFollowersStatsInfo.addCell(cellTotalFollowersTitle);
			tableFollowersStatsInfo.addCell(cellTotalFollowers);
			tableFollowersStatsInfo.addCell(cellFollowersBalanceTitle);
			tableFollowersStatsInfo.addCell(cellFollowersBalance);
			tableFollowersStatsInfo.addCell(cellFollowersBalanceDescription);
			tableFollowersStatsInfo.addCell(cellFollowersGrowthRateTitle);
			tableFollowersStatsInfo.addCell(cellFollowersGrowthRate);
			tableFollowersStatsInfo.addCell(cellFollowersGrowthRateDesc);

			cellFollowersStatsInfo.addElement(tableFollowersStatsInfo);

			tableFollowersStatsContent.addCell(cellChartFollowers);
			tableFollowersStatsContent.addCell(cellFollowersStatsInfo);

			cellFollowersStats.addElement(tableFollowersStatsContent);

			tableFollowersStats.addCell(cellFollowersStatsTitle);
			tableFollowersStats.addCell(cellFollowersStats);

			document.add(tableFollowersStats);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

			String reportsDescription = "•  Relatório Twitter (Visão Geral)  -  " + 
					new SimpleDateFormat("dd/MM/yy").format(new Date(reportTwitterOverview.getDateFrom() * 1000L))
					+" a " +
					new SimpleDateFormat("dd/MM/yy").format(new Date(reportTwitterOverview.getDateUntil() * 1000L));

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
	
	private Image addChartTweetsSentPerWeekDay() {

		String img = chartsImages.get("chartTweetsSentPerWeekDay").substring(chartsImages.get("chartTweetsSentPerWeekDay").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(127);
			imageChart.scaleAbsoluteWidth(210);

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
	
	private Image addChartEngagementWithTweets() {
		
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
			imageChart.scaleAbsoluteWidth(365);
			imageChart.scaleAbsoluteHeight(143);
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
	
	private Image addChartTweetsSentPerHour() {
		
		String img = chartsImages.get("chartTweetsSentPerHour").substring(chartsImages.get("chartTweetsSentPerHour").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteWidth(358);
			imageChart.scaleAbsoluteHeight(151);
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
	
	private Image addChartTweetsSentByType() {

		String img = chartsImages.get("chartTweetsSentByType").substring(chartsImages.get("chartTweetsSentByType").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(61);
			imageChart.scaleAbsoluteWidth(138);

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
	
	private Image addChartTweetsSent() {

		String img = chartsImages.get("chartTweetsSentByDay").substring(chartsImages.get("chartTweetsSentByDay").indexOf("base64,") + 7);

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
	
	private Image addChartFollowersOfFollowers() {

		String img = chartsImages.get("chartFollowersOfFollowers").substring(chartsImages.get("chartFollowersOfFollowers").indexOf("base64,") + 7);

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
	
	private Image addChartFollowersActivity() {

		String img = chartsImages.get("chartFollowersActivity").substring(chartsImages.get("chartFollowersActivity").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(148);
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
	
	private Image addChartFollowers() {

		String img = chartsImages.get("chartFollowers").substring(chartsImages.get("chartFollowers").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(148);
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
	
	private void addHeaderFooter() {
		
		HeaderFooterPDF headerFooterPDF = new HeaderFooterPDF(pdfWritter, "Relatório Twitter Visão Geral", (ReportLogoDTO) profile.getReportLogo());
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
