package br.com.opsocial.server.utils.reports.pdf;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SyncFailedException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.report.ReportLogoDTO;
import br.com.opsocial.client.entity.report.instagram.InstagramInteractionRankingDTO;
import br.com.opsocial.client.entity.report.instagram.InstagramPostsStatsYearDTO;
import br.com.opsocial.client.entity.report.instagram.InstagramReportPostDTO;
import br.com.opsocial.client.entity.report.instagram.InstagramTopPostTagDTO;
import br.com.opsocial.client.entity.report.instagram.ReportInstagramHistoricalDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.instagram.InstagramTopPostTag;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.pdf.HeaderFooterPDF;
import br.com.opsocial.server.utils.reports.ReportIcons;

public class ReportInstagramHistoricalPDF {

	private ReportInstagramHistoricalDTO reportInstagramHistorical;
	private Profile profile;

	private PdfWriter pdfWritter;
	private Document document;
	private String fileName;

	private HashMap<String, String> chartsImages;
	private java.util.List<File> imagesTemp;

	private static BaseFont sourceSansPro;

	private static final BaseColor BASE_COLOR_HEADER_TITLE = new BaseColor(52, 98, 144);

	public ReportInstagramHistoricalPDF(ReportInstagramHistoricalDTO reportInstagramHistorical, Profile profile, HashMap<String, String> chartsImages) {

		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".pdf");
		this.reportInstagramHistorical = reportInstagramHistorical;
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
		addAccountSummary();
		addPostsThroughoutTime();
		addPostsDensity();
		addLikesStats();
		addPostsWithMoreLikes();
		addCommentsStats();
		addPostsWithMoreComments();
		addFilterPostsStats();
		addFilterLikesComments();
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

			String reportsDescription = "•  Relatório Instagram (Histórico)";

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

	private void addAccountSummary() {
		
		try {
			
			PdfPTable tableAccountSummary = new PdfPTable(1);
			tableAccountSummary.setTotalWidth(new float[]{523});
			tableAccountSummary.setLockedWidth(true);
			tableAccountSummary.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableAccountSummary.setSpacingAfter(22);
			
			PdfPCell cellAccountSummaryTitle = new PdfPCell();
			cellAccountSummaryTitle.setBorder(Rectangle.BOTTOM);
			cellAccountSummaryTitle.setBorderWidthBottom(0.8f);
			cellAccountSummaryTitle.setPaddingBottom(5);
			cellAccountSummaryTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parFollowersStatsTitle = new Paragraph("RESUMO DA CONTA", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parFollowersStatsTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellAccountSummaryTitle.addElement(parFollowersStatsTitle);
			
			// Engagement Statstics.
			PdfPCell cellEngagementStats = new PdfPCell();
			cellEngagementStats.setBorder(PdfPCell.NO_BORDER);
			cellEngagementStats.setPadding(0);
			cellEngagementStats.setHorizontalAlignment(Element.ALIGN_CENTER);
			
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
			
			// Posts Actions.
			PdfPCell cellPostsActions = new PdfPCell();
			cellPostsActions.setBorder(PdfPCell.NO_BORDER);
			cellPostsActions.setPaddingLeft(0);
			cellPostsActions.setPaddingRight(0);
			cellPostsActions.setPaddingTop(3);
			cellPostsActions.setPaddingBottom(0);
			
			PdfPTable tablePostsActions = new PdfPTable(2);
			tablePostsActions.setTotalWidth(new float[]{21, 85});
			tablePostsActions.setLockedWidth(true);
			
			PdfPCell cellPostsActionsImage = new PdfPCell();
			cellPostsActionsImage.setBorder(PdfPCell.NO_BORDER);
			cellPostsActionsImage.setPadding(0);
			
			Image imgPostsActions = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_POSTS));
			imgPostsActions.scaleAbsolute(21, 21);
			
			cellPostsActionsImage.addElement(imgPostsActions);
			
			PdfPCell cellPostsActionsValue = new PdfPCell();
			cellPostsActionsValue.setBorder(PdfPCell.NO_BORDER);
			cellPostsActionsValue.setPaddingLeft(3);
			cellPostsActionsValue.setPaddingRight(0);
			cellPostsActionsValue.setPaddingTop(0);
			cellPostsActionsValue.setPaddingBottom(0);
			
			Phrase parPostsActions = new Phrase("POSTS", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parPostsActions.setLeading(7);
			
			Paragraph parPostsActionsValue = new Paragraph(reportInstagramHistorical.getPosts().toString(), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(146, 218, 21)));
			parPostsActionsValue.setLeading(14);
			
			cellPostsActionsValue.addElement(parPostsActions);
			cellPostsActionsValue.addElement(parPostsActionsValue);
			
			tablePostsActions.addCell(cellPostsActionsImage);
			tablePostsActions.addCell(cellPostsActionsValue);
			
			cellPostsActions.addElement(tablePostsActions);
			
			// Likes.
			PdfPCell cellLikesActions = new PdfPCell();
			cellLikesActions.setBorder(PdfPCell.NO_BORDER);
			cellLikesActions.setPaddingLeft(0);
			cellLikesActions.setPaddingRight(0);
			cellLikesActions.setPaddingTop(3);
			cellLikesActions.setPaddingBottom(0);
			
			PdfPTable tableLikesActions = new PdfPTable(2);
			tableLikesActions.setTotalWidth(new float[]{21, 85});
			tableLikesActions.setLockedWidth(true);
			
			PdfPCell cellLikesActionsImage = new PdfPCell();
			cellLikesActionsImage.setBorder(PdfPCell.NO_BORDER);
			cellLikesActionsImage.setPadding(0);
			
			Image imgLikesActions = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_LIKES));
			imgLikesActions.scaleAbsolute(21, 21);
			
			cellLikesActionsImage.addElement(imgLikesActions);
			
			PdfPCell cellLikesActionsValue = new PdfPCell();
			cellLikesActionsValue.setBorder(PdfPCell.NO_BORDER);
			cellLikesActionsValue.setPaddingLeft(3);
			cellLikesActionsValue.setPaddingRight(0);
			cellLikesActionsValue.setPaddingTop(0);
			cellLikesActionsValue.setPaddingBottom(0);
			
			Phrase parLikesActions = new Phrase("CURTIR", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parLikesActions.setLeading(7);
			
			Paragraph parLikesActionsValue = new Paragraph(reportInstagramHistorical.getLikes().toString(), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(243, 72, 72)));
			parLikesActionsValue.setLeading(14);
			
			cellLikesActionsValue.addElement(parLikesActions);
			cellLikesActionsValue.addElement(parLikesActionsValue);
			
			tableLikesActions.addCell(cellLikesActionsImage);
			tableLikesActions.addCell(cellLikesActionsValue);
			
			cellLikesActions.addElement(tableLikesActions);
			
			// Filter.
			PdfPCell cellFilter = new PdfPCell();
			cellFilter.setBorder(PdfPCell.NO_BORDER);
			cellFilter.setPaddingLeft(0);
			cellFilter.setPaddingRight(0);
			cellFilter.setPaddingTop(14);
			cellFilter.setPaddingBottom(3);

			PdfPTable tableFilter = new PdfPTable(2);
			tableFilter.setTotalWidth(new float[]{21, 85});
			tableFilter.setLockedWidth(true);

			PdfPCell cellFilterImage = new PdfPCell();
			cellFilterImage.setBorder(PdfPCell.NO_BORDER);
			cellFilterImage.setPadding(0);

			Image imgFilter = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_FILTER_USED));
			imgFilter.scaleAbsolute(21, 21);

			cellFilterImage.addElement(imgFilter);

			PdfPCell cellFilterValue = new PdfPCell();
			cellFilterValue.setBorder(PdfPCell.NO_BORDER);
			cellFilterValue.setPaddingLeft(3);
			cellFilterValue.setPaddingRight(0);
			cellFilterValue.setPaddingTop(0);
			cellFilterValue.setPaddingBottom(0);

			Phrase parFilter = new Phrase("FILTRO MAIS USADO", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parFilter.setLeading(7);

//			Paragraph parFilterValue = new Paragraph(reportInstagramHistorical.getFilterMostUsed().toUpperCase(), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(255, 211, 4)));
//			parFilterValue.setLeading(14);

			cellFilterValue.addElement(parFilter);
//			cellFilterValue.addElement(parFilterValue);

			tableFilter.addCell(cellFilterImage);
			tableFilter.addCell(cellFilterValue);

			cellFilter.addElement(tableFilter);
			
			// Comments.
			PdfPCell cellCommentsActions = new PdfPCell();
			cellCommentsActions.setBorder(PdfPCell.NO_BORDER);
			cellCommentsActions.setPaddingLeft(0);
			cellCommentsActions.setPaddingRight(0);
			cellCommentsActions.setPaddingTop(14);
			cellCommentsActions.setPaddingBottom(3);

			PdfPTable tableCommentsActions = new PdfPTable(2);
			tableCommentsActions.setTotalWidth(new float[]{21, 85});
			tableCommentsActions.setLockedWidth(true);

			PdfPCell cellCommentsImage = new PdfPCell();
			cellCommentsImage.setBorder(PdfPCell.NO_BORDER);
			cellCommentsImage.setPadding(0);

			Image imgComments = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_COMMENTS));
			imgComments.scaleAbsolute(21, 21);

			cellCommentsImage.addElement(imgComments);

			PdfPCell cellCommentsValue = new PdfPCell();
			cellCommentsValue.setBorder(PdfPCell.NO_BORDER);
			cellCommentsValue.setPaddingLeft(3);
			cellCommentsValue.setPaddingRight(0);
			cellCommentsValue.setPaddingTop(0);
			cellCommentsValue.setPaddingBottom(0);

			Phrase parComments = new Phrase("COMENTÁRIOS", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parComments.setLeading(7);

			Paragraph parCommentsValue = new Paragraph(reportInstagramHistorical.getComments().toString(), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(34, 201, 237)));
			parCommentsValue.setLeading(14);

			cellCommentsValue.addElement(parComments);
			cellCommentsValue.addElement(parCommentsValue);

			tableCommentsActions.addCell(cellCommentsImage);
			tableCommentsActions.addCell(cellCommentsValue);

			cellCommentsActions.addElement(tableCommentsActions);

			tableEngagementActions.addCell(cellPostsActions);
			tableEngagementActions.addCell(cellLikesActions);
			tableEngagementActions.addCell(cellFilter);
			tableEngagementActions.addCell(cellCommentsActions);
			
			cellEngagementActions.addElement(tableEngagementActions);
			
			// Relationship stats.
			PdfPCell cellRelationshipStats = new PdfPCell();
			cellRelationshipStats.setBorder(PdfPCell.NO_BORDER);
			cellRelationshipStats.setPaddingTop(0);
			cellRelationshipStats.setPaddingBottom(0);
			cellRelationshipStats.setPaddingRight(0);
			cellRelationshipStats.setPaddingLeft(38);

			PdfPTable tableRelationshipStats = new PdfPTable(1);
			tableRelationshipStats.setTotalWidth(new float[]{148});
			tableRelationshipStats.setLockedWidth(true);
			
			// Followers.
			PdfPCell cellFollowers = new PdfPCell();
			cellFollowers.setBorder(PdfPCell.NO_BORDER);
			cellFollowers.setPaddingLeft(0);
			cellFollowers.setPaddingRight(0);
			cellFollowers.setPaddingTop(3);
			cellFollowers.setPaddingBottom(12);
			
			Paragraph parFollowersTitle = new Paragraph("SEGUIDORES", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parFollowersTitle.setLeading(7);
			
			Paragraph parFollowers = new Paragraph();
			parFollowers.setLeading(15);
			
			Image imgFollowers = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_FOLLOWERS));
			imgFollowers.scaleAbsolute(10, 10);

			parFollowers.add(new Chunk(imgFollowers, 0, 0));
			parFollowers.add(new Chunk(" " + reportInstagramHistorical.getFollowers(), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(156, 221, 53))));
			
			cellFollowers.addElement(parFollowersTitle);
			cellFollowers.addElement(parFollowers);
			
			// Followings.
			PdfPCell cellFollowings = new PdfPCell();
			cellFollowings.setBorder(PdfPCell.NO_BORDER);
			cellFollowings.setPadding(0);

			Paragraph parFollowingsTitle = new Paragraph("SEGUINDO", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parFollowingsTitle.setLeading(7);

			Paragraph parFollowings = new Paragraph();
			parFollowings.setLeading(15);

			Image imgFollowings = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_FOLLOWINGS));
			imgFollowings.scaleAbsolute(10, 10);

			parFollowings.add(new Chunk(imgFollowings, 0, 0));
			parFollowings.add(new Chunk(" " + profile.getFollows(), new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(255, 163, 54))));

			cellFollowings.addElement(parFollowingsTitle);
			cellFollowings.addElement(parFollowings);

			tableRelationshipStats.addCell(cellFollowers);
			tableRelationshipStats.addCell(cellFollowings);
			
			cellRelationshipStats.addElement(tableRelationshipStats);
			
			tableEngagementStats.addCell(cellEngagementActions);
			tableEngagementStats.addCell(cellRelationshipStats);
			
			cellEngagementStats.addElement(tableEngagementStats);
			
			tableAccountSummary.addCell(cellAccountSummaryTitle);
			tableAccountSummary.addCell(cellEngagementStats);
			
			document.add(tableAccountSummary);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addPostsThroughoutTime() {
		
		try {
			
			PdfPTable tablePostsThroughoutTime = new PdfPTable(1);
			tablePostsThroughoutTime.setTotalWidth(new float[]{523});
			tablePostsThroughoutTime.setLockedWidth(true);
			tablePostsThroughoutTime.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablePostsThroughoutTime.setSpacingAfter(22);
			
			PdfPCell cellPostsThroughoutTimeTitle = new PdfPCell();
			cellPostsThroughoutTimeTitle.setBorder(Rectangle.BOTTOM);
			cellPostsThroughoutTimeTitle.setBorderWidthBottom(0.8f);
			cellPostsThroughoutTimeTitle.setPaddingBottom(5);
			cellPostsThroughoutTimeTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parPostsThroughoutTimeTitle = new Paragraph("POSTS POR MÊS E ANO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parPostsThroughoutTimeTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellPostsThroughoutTimeTitle.addElement(parPostsThroughoutTimeTitle);

			PdfPCell cellPostsThroughoutTimeInfo = new PdfPCell();
			cellPostsThroughoutTimeInfo.setBorder(PdfPCell.NO_BORDER);
			cellPostsThroughoutTimeInfo.setPaddingBottom(0);
			
			PdfPTable tablePostsThroughoutTimeInfo = new PdfPTable(2);
			tablePostsThroughoutTimeInfo.setTotalWidth(new float[]{200, 160});
			tablePostsThroughoutTimeInfo.setLockedWidth(true);
			
			// Chart number of posts.
			PdfPCell cellChartNumberOfPosts = new PdfPCell();
			cellChartNumberOfPosts.setBorder(PdfPCell.NO_BORDER);
			cellChartNumberOfPosts.setPadding(0);
			
			cellChartNumberOfPosts.addElement(addChartNumberOfPosts());

			// General Posts Stats Info.
			PdfPCell cellGeneralPostsStatsInfo = new PdfPCell();
			cellGeneralPostsStatsInfo.setBorder(PdfPCell.NO_BORDER); 
			cellGeneralPostsStatsInfo.setPaddingTop(8);
			cellGeneralPostsStatsInfo.setPaddingBottom(0);
			cellGeneralPostsStatsInfo.setPaddingRight(0);
			cellGeneralPostsStatsInfo.setPaddingLeft(30);

			PdfPTable tableGeneralPostsStatsInfo = new PdfPTable(1);
			tableGeneralPostsStatsInfo.setTotalWidth(new float[]{160});
			tableGeneralPostsStatsInfo.setLockedWidth(true);
			tableGeneralPostsStatsInfo.getDefaultCell().setPadding(0);
			tableGeneralPostsStatsInfo.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

			if(reportInstagramHistorical.getInstagramPostsStatsYearFirst().getPostsTotal() > 0) {
				addInstagramPostsStatsYear(reportInstagramHistorical.getInstagramPostsStatsYearFirst(), tableGeneralPostsStatsInfo, 1);
			}
			if(reportInstagramHistorical.getInstagramPostsStatsYearSecond().getPostsTotal() > 0) {
				addInstagramPostsStatsYear(reportInstagramHistorical.getInstagramPostsStatsYearSecond(), tableGeneralPostsStatsInfo, 2);
			}
			if(reportInstagramHistorical.getInstagramPostsStatsYearThird().getPostsTotal() > 0) {
				addInstagramPostsStatsYear(reportInstagramHistorical.getInstagramPostsStatsYearThird(), tableGeneralPostsStatsInfo, 3);
			}
			
			cellGeneralPostsStatsInfo.addElement(tableGeneralPostsStatsInfo);
			
			tablePostsThroughoutTimeInfo.addCell(cellChartNumberOfPosts);
			tablePostsThroughoutTimeInfo.addCell(cellGeneralPostsStatsInfo);
			
			cellPostsThroughoutTimeInfo.addElement(tablePostsThroughoutTimeInfo);

			tablePostsThroughoutTime.addCell(cellPostsThroughoutTimeTitle);
			tablePostsThroughoutTime.addCell(cellPostsThroughoutTimeInfo);
			
			document.add(tablePostsThroughoutTime);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addInstagramPostsStatsYear(InstagramPostsStatsYearDTO instagramPostsStatsYear, PdfPTable tableGeneralPostsStatsInfo, Integer position) {
		
		try {
			String imgIconYear;
			BaseColor baseColorYear;
			if(position.equals(1)) {
				imgIconYear = br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_FIRST_YEAR);
				baseColorYear = new BaseColor(112, 72, 243);
			} else if(position.equals(2)) {
				imgIconYear = br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_SECOND_YEAR);
				baseColorYear = new BaseColor(243, 193, 72);
			} else {
				imgIconYear = br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_THIRD_YEAR);
				baseColorYear = new BaseColor(72, 205, 243);
			}
			
			PdfPCell cellPostsStatsYearTitle = new PdfPCell();
			cellPostsStatsYearTitle.setBorder(PdfPCell.NO_BORDER);
			cellPostsStatsYearTitle.setPaddingRight(0);
			cellPostsStatsYearTitle.setPaddingLeft(0);
			cellPostsStatsYearTitle.setPaddingTop(0);
			cellPostsStatsYearTitle.setPaddingBottom(2);

			Paragraph parPostsStatsYearTitle = new Paragraph("TOTAL DE " + instagramPostsStatsYear.getYear(), new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parPostsStatsYearTitle.setLeading(9);

			cellPostsStatsYearTitle.addElement(parPostsStatsYearTitle);

			PdfPCell cellPostsStatsYear = new PdfPCell();
			cellPostsStatsYear.setBorder(PdfPCell.NO_BORDER);
			cellPostsStatsYear.setPaddingRight(0);
			cellPostsStatsYear.setPaddingLeft(0);
			cellPostsStatsYear.setPaddingTop(0);
			cellPostsStatsYear.setPaddingBottom(2);

			Paragraph parPostsStatsYear = new Paragraph();
			parPostsStatsYear.setLeading(11);

			Image imgPostsStatsYear = Image.getInstance(imgIconYear);
			imgPostsStatsYear.scaleAbsolute(10, 10);

			parPostsStatsYear.add(new Chunk(imgPostsStatsYear, 0, 0));

			parPostsStatsYear.add(new Chunk(" " + instagramPostsStatsYear.getPostsTotal(),
					new Font(sourceSansPro, 15, Font.NORMAL, baseColorYear)));

			cellPostsStatsYear.addElement(parPostsStatsYear);

			PdfPCell cellPostsStatsYearDesc = new PdfPCell();
			cellPostsStatsYearDesc.setBorder(PdfPCell.NO_BORDER);
			cellPostsStatsYearDesc.setPaddingRight(0);
			cellPostsStatsYearDesc.setPaddingLeft(0);
			cellPostsStatsYearDesc.setPaddingTop(0);
			cellPostsStatsYearDesc.setPaddingBottom(8);

			Paragraph parPostsStatsYearDesc = new Paragraph();
			parPostsStatsYearDesc.setLeading(7);

//			String postsStatsYearDesc = "Seu perfil fez " + instagramPostsStatsYear.getPostsTotal() + " postagens em " + 
//					instagramPostsStatsYear.getYear() + ". O mês com maior " +
//					"atividade foi " + OpSocialBackApplication.utilFunctionsService.getFormattedMonth(instagramPostsStatsYear.getMonthWithMorePosts() - 1) + 
//					" com " + instagramPostsStatsYear.getMonthWithMorePostsValue() + " posts.";
//
//			parPostsStatsYearDesc.add(postsStatsYearDesc);
			parPostsStatsYearDesc.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellPostsStatsYearDesc.addElement(parPostsStatsYearDesc);
			
			tableGeneralPostsStatsInfo.addCell(cellPostsStatsYearTitle);
			tableGeneralPostsStatsInfo.addCell(cellPostsStatsYear);
			tableGeneralPostsStatsInfo.addCell(cellPostsStatsYearDesc);
			
		} catch (BadElementException e) {
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

			Paragraph parCommentsStatsTitle = new Paragraph("COMENTÁRIOS POR MÊS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
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

			// Comments Stats.
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

			parTotalComments.add(new Chunk(" " + reportInstagramHistorical.getComments(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(34, 201, 237))));

			cellTotalComments.addElement(parTotalComments);
			
			// Average Comments.
			PdfPCell cellAverageCommentsTitle = new PdfPCell();
			cellAverageCommentsTitle.setBorder(PdfPCell.NO_BORDER);
			cellAverageCommentsTitle.setPaddingRight(0);
			cellAverageCommentsTitle.setPaddingLeft(0);
			cellAverageCommentsTitle.setPaddingTop(0);
			cellAverageCommentsTitle.setPaddingBottom(2);

			Paragraph parAverageCommentsTitle = new Paragraph("MÉDIA DE COMENTÁRIOS POR POST", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
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

			parAverageComments.add(new Chunk(" " + reportInstagramHistorical.getCommentsAverage().toString().replace(".", ","),
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

			String averageLikesDescription = "Seu perfil fez " + reportInstagramHistorical.getPosts() + " postagens desde sua criação, com uma média de " + 
					reportInstagramHistorical.getCommentsAverage().toString().replace(".", ",") + " comentários " + 
					"por post.";

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

			Paragraph parOrganicGrowthOriginPerc = new Paragraph(reportInstagramHistorical.getCommentsFollowers() + "%", new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(156, 221, 53)));
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

			Paragraph parNoFollowersOriginPerc = new Paragraph(reportInstagramHistorical.getCommentsNoFollowers() + "%", new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(233, 114, 43)));
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

			Paragraph parLikesStatsTitle = new Paragraph("LIKES POR MÊS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
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

			parTotalLikes.add(new Chunk(" " + reportInstagramHistorical.getLikes(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(243, 72, 72))));

			cellTotalLikes.addElement(parTotalLikes);
			
			// Average Likes.
			PdfPCell cellAverageLikesTitle = new PdfPCell();
			cellAverageLikesTitle.setBorder(PdfPCell.NO_BORDER);
			cellAverageLikesTitle.setPaddingRight(0);
			cellAverageLikesTitle.setPaddingLeft(0);
			cellAverageLikesTitle.setPaddingTop(0);
			cellAverageLikesTitle.setPaddingBottom(2);

			Paragraph parAverageLikesTitle = new Paragraph("MÉDIA DE LIKES POR POST", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
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

			parAverageLikes.add(new Chunk(" " + reportInstagramHistorical.getLikesAverage().toString().replace(".", ","),
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

			String averageLikesDescription = "Seu perfil fez " + reportInstagramHistorical.getPosts() + " postagens desde sua criação, com uma média de " + 
					reportInstagramHistorical.getLikesAverage().toString().replace(".", ",") + " likes " + 
					"por post.";

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

			Paragraph parOrganicGrowthOriginPerc = new Paragraph(reportInstagramHistorical.getLikesFollowers() + "%", new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(156, 221, 53)));
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

			Paragraph parNoFollowersOriginPerc = new Paragraph(reportInstagramHistorical.getLikesNoFollowers() + "%", new Font(sourceSansPro, 13, Font.NORMAL, new BaseColor(233, 114, 43)));
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

			Paragraph parPostsWithMoreLikesTitle = new Paragraph("POSTS COM MAIS LIKES", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parPostsWithMoreLikesTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellPostsWithMoreLikesTitle.addElement(parPostsWithMoreLikesTitle);
			
			int size = reportInstagramHistorical.getPostsWithMoreLikes().size();
			
			if(size > 0) {
				
				addPostWithMoreLikes(reportInstagramHistorical.getPostsWithMoreLikes().get(0));
				
				if(size > 1) {
					
					float tableTotalWidth = 0.0F;
					float imageTop = 0.0F;
					float imageLeft = 0.0F;
					float likesTop = 0.0F;
					float likesLeft = 0.0F;
					
					for(int i = 1; i < size; i++) {
						
						float extraWidth = 0F;
						if(reportInstagramHistorical.getPostsWithMoreLikes().get(i).getLikes() < 10) {
							extraWidth = 3F;
						}
						
						switch (i) {
						case 1:
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramHistorical.getPostsWithMoreLikes().get(i).getLikes().toString()).getWidthPoint() + extraWidth;
							imageTop = 654;
							imageLeft = 316;
							likesTop = 673;
							likesLeft = 316 + (69 / 2);
							
							break;
						case 2: 
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramHistorical.getPostsWithMoreLikes().get(i).getLikes().toString()).getWidthPoint() + extraWidth;
							imageTop = 654;
							imageLeft = 316 + 79;
							likesTop = 673;
							likesLeft = 316 + 79 + (69 / 2);
							
							break;
						case 3: 
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramHistorical.getPostsWithMoreLikes().get(i).getLikes().toString()).getWidthPoint() + extraWidth;
							imageTop = 654 - 83;
							imageLeft = 316;
							likesTop = 670 - 80;
							likesLeft = 316 + (69 / 2);
							
							break;
						case 4: 
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramHistorical.getPostsWithMoreLikes().get(i).getLikes().toString()).getWidthPoint() + extraWidth;
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
							Image img = Image.getInstance(reportInstagramHistorical.getPostsWithMoreLikes().get(i).getPictureUrl());
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

							Chunk cnkTotalLikes = new Chunk(" " + reportInstagramHistorical.getPostsWithMoreLikes().get(i).getLikes(),
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

			Paragraph parPostsWithMoreLikesTitle = new Paragraph("POSTS COM MAIS COMENTÁRIOS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parPostsWithMoreLikesTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellPostsWithMoreCommentsTitle.addElement(parPostsWithMoreLikesTitle);
			
			int size = reportInstagramHistorical.getPostsWithMoreComments().size();
			
			if(size > 0) {
				
				addPostWithMoreComments(reportInstagramHistorical.getPostsWithMoreComments().get(0));
				
				if(size > 1) {
					
					float tableTotalWidth = 0.0F;
					float imageTop = 0.0F;
					float imageLeft = 0.0F;
					float likesTop = 0.0F;
					float likesLeft = 0.0F;
					
					for(int i = 1; i < size; i++) {
						
						float extraWidth = 0F;
						if(reportInstagramHistorical.getPostsWithMoreComments().get(i).getComments() < 10) {
							extraWidth = 3F;
						}
						
						switch (i) {
						case 1:
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramHistorical.getPostsWithMoreComments().get(i).getComments().toString()).getWidthPoint() + extraWidth;
							imageTop = 229;
							imageLeft = 316;
							likesTop = 248;
							likesLeft = 316 + (69 / 2);
							
							break;
						case 2: 
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramHistorical.getPostsWithMoreComments().get(i).getComments().toString()).getWidthPoint() + extraWidth;
							imageTop = 229;
							imageLeft = 316 + 79;
							likesTop = 248;
							likesLeft = 316 + 79 + (69 / 2);
							
							break;
						case 3: 
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramHistorical.getPostsWithMoreComments().get(i).getComments().toString()).getWidthPoint() + extraWidth;
							imageTop = 229 - 83;
							imageLeft = 316;
							likesTop = 245 - 80;
							likesLeft = 316 + (69 / 2);
							
							break;
						case 4: 
							
							tableTotalWidth = 8 + new Chunk(" " + reportInstagramHistorical.getPostsWithMoreComments().get(i).getComments().toString()).getWidthPoint() + extraWidth;
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
							Image img = Image.getInstance(reportInstagramHistorical.getPostsWithMoreComments().get(i).getPictureUrl());
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

							Chunk cnkTotalComments = new Chunk(" " + reportInstagramHistorical.getPostsWithMoreComments().get(i).getComments(),
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
	
	private void addPostsDensity() {
		
		try {
			
			PdfPTable tablePostsDensity = new PdfPTable(1);
			tablePostsDensity.setTotalWidth(new float[]{523});
			tablePostsDensity.setLockedWidth(true);
			tablePostsDensity.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablePostsDensity.setSpacingAfter(22);
			
			PdfPCell cellPostsDensityTitle = new PdfPCell();
			cellPostsDensityTitle.setBorder(Rectangle.BOTTOM);
			cellPostsDensityTitle.setBorderWidthBottom(0.8f);
			cellPostsDensityTitle.setPaddingBottom(5);
			cellPostsDensityTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parPostsDensityTitle = new Paragraph("DENSIDADE DE POSTS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parPostsDensityTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellPostsDensityTitle.addElement(parPostsDensityTitle);

			PdfPCell cellPostsDensityInfo = new PdfPCell();
			cellPostsDensityInfo.setBorder(PdfPCell.NO_BORDER);
			cellPostsDensityInfo.setPaddingBottom(0);
			
			PdfPTable tablePostsDensityInfo = new PdfPTable(2);
			tablePostsDensityInfo.setTotalWidth(new float[]{180, 180});
			tablePostsDensityInfo.setLockedWidth(true);
			
			PdfPCell cellPostsDensityDay = new PdfPCell();
			cellPostsDensityDay.setBorder(PdfPCell.NO_BORDER);
			cellPostsDensityDay.setPaddingBottom(0);
			cellPostsDensityDay.setPaddingTop(7.5F);
			cellPostsDensityDay.setPaddingLeft(0);
			cellPostsDensityDay.setPaddingRight(0);
			
			Paragraph parPostsDensityDayTitle = new Paragraph("POR DIA", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parPostsDensityDayTitle.setAlignment(Element.ALIGN_LEFT);
			
			cellPostsDensityDay.addElement(parPostsDensityDayTitle);
			
			addPostsDensityDay();
			
			PdfPCell cellPostsDensityHour = new PdfPCell();
			cellPostsDensityHour.setBorder(PdfPCell.NO_BORDER);
			cellPostsDensityHour.setPaddingBottom(0);
			cellPostsDensityHour.setPaddingTop(7.5F);
			cellPostsDensityHour.setPaddingLeft(0);
			cellPostsDensityHour.setPaddingRight(0);
			
			Paragraph parPostsDensityHourTitle = new Paragraph("POR HORA", new Font(sourceSansPro, 9, Font.NORMAL, new BaseColor(134, 134, 134)));
			parPostsDensityHourTitle.setAlignment(Element.ALIGN_LEFT);
			
			cellPostsDensityHour.addElement(parPostsDensityHourTitle);
			
			addPostsDensityHour();
			
			tablePostsDensityInfo.addCell(cellPostsDensityDay);
			tablePostsDensityInfo.addCell(cellPostsDensityHour);
			
			cellPostsDensityInfo.addElement(tablePostsDensityInfo);

			tablePostsDensity.addCell(cellPostsDensityTitle);
			tablePostsDensity.addCell(cellPostsDensityInfo);
			
			PdfPTable tablePostsDensityLegend = new PdfPTable(2);
			tablePostsDensityLegend.setTotalWidth(new float[]{180, 180});
			tablePostsDensityLegend.setLockedWidth(true);
			tablePostsDensityLegend.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablePostsDensityLegend.setSpacingBefore(10);
			tablePostsDensityLegend.setSpacingAfter(22);
			tablePostsDensityLegend.getDefaultCell().setPadding(0);
			tablePostsDensityLegend.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			
			// Week Day Legend.
			PdfPTable tablePostsDensityDayLegend = new PdfPTable(7);
			tablePostsDensityDayLegend.setTotalWidth(new float[]{22, 22, 22, 22, 22, 22, 22});
			tablePostsDensityDayLegend.setLockedWidth(true);
			tablePostsDensityDayLegend.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			for(int i = 0; i <= 6; i++) {
				
				PdfPCell cellPostsDensityWeekDay = new PdfPCell();
				cellPostsDensityWeekDay.setBorder(PdfPCell.NO_BORDER);
				cellPostsDensityWeekDay.setPadding(0);
				cellPostsDensityWeekDay.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				String tinyWeekDay = OpSocialBackApplication.utilFunctionsService.getFormattedTinyWeekDay(i);
				
				Paragraph parPostsDensityWeekDay = new Paragraph(
						tinyWeekDay.substring(0, 1).toUpperCase() + tinyWeekDay.substring(1), new Font(sourceSansPro, 6, Font.NORMAL, new BaseColor(134, 134, 134)));
				parPostsDensityWeekDay.setAlignment(Element.ALIGN_CENTER);
				
				cellPostsDensityWeekDay.addElement(parPostsDensityWeekDay);
				
				tablePostsDensityDayLegend.addCell(cellPostsDensityWeekDay);
			}
			
			// Hour Legend.
			PdfPTable tablePostsDensityHourLegend = new PdfPTable(7);
			tablePostsDensityHourLegend.setTotalWidth(new float[]{25, 25, 25, 25, 25, 25, 25});
			tablePostsDensityHourLegend.setLockedWidth(true);
			tablePostsDensityHourLegend.setHorizontalAlignment(Element.ALIGN_LEFT);

			for(int i = 0; i <= 24; i++) {
				
				if(i % 4 == 0) {
					
					PdfPCell cellPostsDensityLegendHour = new PdfPCell();
					cellPostsDensityLegendHour.setBorder(PdfPCell.NO_BORDER);
					cellPostsDensityLegendHour.setPadding(0);
					
					Paragraph parPostsDensityHour = new Paragraph(i + "h", new Font(sourceSansPro, 6, Font.NORMAL, new BaseColor(134, 134, 134)));
					parPostsDensityHour.setAlignment(Element.ALIGN_LEFT);
					
					cellPostsDensityLegendHour.addElement(parPostsDensityHour);
					
					tablePostsDensityHourLegend.addCell(cellPostsDensityLegendHour);
				}
				
			}
			
			tablePostsDensityLegend.addCell(tablePostsDensityDayLegend);
			tablePostsDensityLegend.addCell(tablePostsDensityHourLegend);
			
			document.add(tablePostsDensity);
			document.add(tablePostsDensityLegend);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addPostsDensityDay() {
		
		Map<Integer, Float> densityPercentSize = new TreeMap<Integer, Float>();
		densityPercentSize.put(10, 4.5F);
		densityPercentSize.put(20, 5.0F);
		densityPercentSize.put(30, 5.5F);
		densityPercentSize.put(40, 6.0F);
		densityPercentSize.put(50, 6.5F);
		densityPercentSize.put(60, 7.0F);
		densityPercentSize.put(70, 7.5F);
		densityPercentSize.put(80, 8.0F);
		densityPercentSize.put(90, 8.5F);
		densityPercentSize.put(100, 9.0F);
		
		int xPosition = 128;
		for(Map.Entry<Integer, Long> postsPerWeekDay : reportInstagramHistorical.getPostsPerWeekDay().entrySet()) {

			Integer densityPercent = 0;
			if(reportInstagramHistorical.getWeekDayWithMorePostsValue() > 0) {
				densityPercent = new BigDecimal((100 * postsPerWeekDay.getValue()) / reportInstagramHistorical.getWeekDayWithMorePostsValue()).setScale(0, BigDecimal.ROUND_HALF_DOWN).intValue();
			}
			
			Float densitySize = getDensitySize(densityPercentSize, densityPercent);
			
			drawDensityCircle(xPosition, 320, densitySize, new BaseColor(101, 249, 239));
			xPosition = xPosition + 22;
		}
		
	}
	
	private void addPostsDensityHour() {
		
		// Mapa que defini a faixa de tamanhos das zonas de densidade, de acordo com a porcentagem em relação a hora com mais posts.
		Map<Integer, Float> densityPercentSize = new TreeMap<Integer, Float>();
		densityPercentSize.put(5, 0.25F);
		densityPercentSize.put(10, 0.5F);
		densityPercentSize.put(15, 0.75F);
		densityPercentSize.put(20, 1.0F);
		densityPercentSize.put(25, 1.25F);
		densityPercentSize.put(30, 1.5F);
		densityPercentSize.put(35, 1.75F);
		densityPercentSize.put(40, 2.0F);
		densityPercentSize.put(45, 2.25F);
		densityPercentSize.put(50, 2.5F);
		densityPercentSize.put(55, 2.75F);
		densityPercentSize.put(60, 3.0F);
		densityPercentSize.put(65, 3.25F);
		densityPercentSize.put(70, 3.5F);
		densityPercentSize.put(75, 3.75F);
		densityPercentSize.put(80, 4.0F);
		densityPercentSize.put(85, 4.25F);
		densityPercentSize.put(90, 4.5F);
		densityPercentSize.put(95, 4.75F);
		densityPercentSize.put(100, 5.0F);
		
		Float xPosition = 128F + 170F;
		int fourHours = 1;
		Float widthEveryFourHours = 26.5F;
		Float widthEveryFourHoursCount = 0F;
		Float densityPreviousSize = 0F;
		for(Map.Entry<Integer, Long> postsPerHour : reportInstagramHistorical.getPostsPerHour().entrySet()) {

			Float xPreviousPosition = xPosition.floatValue();
			Integer densityPercent = 0;
			if(reportInstagramHistorical.getHourWithMorePostsValue() > 0) {
				densityPercent = new BigDecimal((100 * postsPerHour.getValue()) / reportInstagramHistorical.getHourWithMorePostsValue()).setScale(0, BigDecimal.ROUND_HALF_DOWN).intValue();
			}
			
			// Aplica lógica de posicionamento do Eixo X, apenas quando há algum valor a ser mostrado.
			Float densitySize;
			if(densityPercent > 0) {
				
				densitySize = getDensitySize(densityPercentSize, densityPercent);
				
				// Define posição do próximo círculo de densidade de acorso com últimos valores.
				xPosition = densitySize > densityPreviousSize ? xPosition + (densitySize - densityPreviousSize) + 0.5F : xPosition; 
				
				drawDensityCircle(xPosition, 320, densitySize, new BaseColor(255, 151, 57));
				xPosition = xPosition + (densitySize * 2F) + 0.5F;
			} else {
				densitySize = 0.0F;
			}
		
			// Ajustar horário que vai de 4 em 4 horas com a legenda.
			widthEveryFourHoursCount = widthEveryFourHoursCount + (xPosition - xPreviousPosition);
			if(fourHours > 3 && widthEveryFourHours > widthEveryFourHoursCount) {
				xPosition = xPosition + (widthEveryFourHours - widthEveryFourHoursCount);
				fourHours = 0;
				widthEveryFourHoursCount = 0F;
			} 
			
			densityPreviousSize = densitySize;
			fourHours++;
		}
		
	}
	
	private Float getDensitySize(Map<Integer, Float> densityPercentSize, Integer densityPercent) {
		
		Float densitySize = 0F;
		
		if(densityPercent > 0) {
			for(Map.Entry<Integer, Float> densityPercSize : densityPercentSize.entrySet()) {
				if(densityPercent <= densityPercSize.getKey()) {
					densitySize = densityPercSize.getValue();
					break;
				}
			}
		}
		
		return densitySize;
	}
	
	private void drawDensityCircle(float x, float y, float r, BaseColor circleColor) {
		
	    PdfContentByte canvas = pdfWritter.getDirectContent();

	    canvas.saveState();
	    canvas.setLineWidth(0F);
	    canvas.setColorStroke(circleColor);
	    canvas.setColorFill(circleColor);
	    canvas.circle(x, y, r);
	    canvas.fillStroke();

	    canvas.restoreState();
	}
	
	private void addFilterPostsStats() {
		
		try {
			
			document.newPage();
			
			PdfPTable tableFilterPostsStats = new PdfPTable(1);
			tableFilterPostsStats.setTotalWidth(new float[]{523});
			tableFilterPostsStats.setLockedWidth(true);
			tableFilterPostsStats.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFilterPostsStats.setSpacingAfter(22);
			
			PdfPCell cellFilterPostsStatsTitle = new PdfPCell();
			cellFilterPostsStatsTitle.setBorder(Rectangle.BOTTOM);
			cellFilterPostsStatsTitle.setBorderWidthBottom(0.8f);
			cellFilterPostsStatsTitle.setPaddingBottom(5);
			cellFilterPostsStatsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parFilterPostsStatsTitle = new Paragraph("FILTROS MAIS USADOS", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parFilterPostsStatsTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellFilterPostsStatsTitle.addElement(parFilterPostsStatsTitle);

			PdfPCell cellFilterPostsStatsInfo = new PdfPCell();
			cellFilterPostsStatsInfo.setBorder(PdfPCell.NO_BORDER);
			cellFilterPostsStatsInfo.setPaddingBottom(0);
			
			PdfPTable tableFilterPostsStatsInfo = new PdfPTable(2);
			tableFilterPostsStatsInfo.setTotalWidth(new float[]{200, 160});
			tableFilterPostsStatsInfo.setLockedWidth(true);
			
			// Chart Filters Per Posts.
			PdfPCell cellChartFiltersPerPosts = new PdfPCell();
			cellChartFiltersPerPosts.setBorder(PdfPCell.NO_BORDER);
			cellChartFiltersPerPosts.setPadding(0);
			
			cellChartFiltersPerPosts.addElement(addChartFiltersPerPosts());

			// Filter Stats.
			PdfPCell cellFiltersStats = new PdfPCell();
			cellFiltersStats.setBorder(PdfPCell.NO_BORDER); 
			cellFiltersStats.setPaddingTop(8);
			cellFiltersStats.setPaddingBottom(0);
			cellFiltersStats.setPaddingRight(0);
			cellFiltersStats.setPaddingLeft(30);

			PdfPTable tableFiltersStats = new PdfPTable(1);
			tableFiltersStats.setTotalWidth(new float[]{160});
			tableFiltersStats.setLockedWidth(true);
			tableFiltersStats.getDefaultCell().setPadding(0);
			tableFiltersStats.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			
			// Filter Most Used.
			PdfPCell cellFilterMostUsedTitle = new PdfPCell();
			cellFilterMostUsedTitle.setBorder(PdfPCell.NO_BORDER);
			cellFilterMostUsedTitle.setPaddingRight(0);
			cellFilterMostUsedTitle.setPaddingLeft(0);
			cellFilterMostUsedTitle.setPaddingTop(0);
			cellFilterMostUsedTitle.setPaddingBottom(2);

			Paragraph parFilterMostUsedTitle = new Paragraph("FILTRO MAIS USADO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parFilterMostUsedTitle.setLeading(9);

			cellFilterMostUsedTitle.addElement(parFilterMostUsedTitle);

			PdfPCell cellFilterMostUsed = new PdfPCell();
			cellFilterMostUsed.setBorder(PdfPCell.NO_BORDER);
			cellFilterMostUsed.setPaddingRight(0);
			cellFilterMostUsed.setPaddingLeft(0);
			cellFilterMostUsed.setPaddingTop(0);
			cellFilterMostUsed.setPaddingBottom(2);

			Paragraph parFilterMostUsed = new Paragraph();
			parFilterMostUsed.setLeading(11);

			Image imgFilterMostUsed = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_FILTER_MOST_USED));
			imgFilterMostUsed.scaleAbsolute(10, 10);

			parFilterMostUsed.add(new Chunk(imgFilterMostUsed, 0, 0));
//
//			parFilterMostUsed.add(new Chunk(" " + reportInstagramHistorical.getFilterMostUsed().toUpperCase(),
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(30, 226, 182))));

			cellFilterMostUsed.addElement(parFilterMostUsed);

			PdfPCell cellFilterMostUsedDesc = new PdfPCell();
			cellFilterMostUsedDesc.setBorder(PdfPCell.NO_BORDER);
			cellFilterMostUsedDesc.setPaddingRight(0);
			cellFilterMostUsedDesc.setPaddingLeft(0);
			cellFilterMostUsedDesc.setPaddingTop(0);
			cellFilterMostUsedDesc.setPaddingBottom(8);

			Paragraph parFilterMostUsedDesc = new Paragraph();
			parFilterMostUsedDesc.setLeading(7);

//			String filterMostUsedDesc = "O filtro " + reportInstagramHistorical.getFilterMostUsed() + " é o mais utlizado em seu perfil, " +
//					"o efeito foi aplicado em " + reportInstagramHistorical.getFilterMostUsedValue() + " fotos até hoje.";
//
//			parFilterMostUsedDesc.add(filterMostUsedDesc);
			parFilterMostUsedDesc.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellFilterMostUsedDesc.addElement(parFilterMostUsedDesc);
			
			// Filter Less Used.
			PdfPCell cellFilterLessUsedTitle = new PdfPCell();
			cellFilterLessUsedTitle.setBorder(PdfPCell.NO_BORDER);
			cellFilterLessUsedTitle.setPaddingRight(0);
			cellFilterLessUsedTitle.setPaddingLeft(0);
			cellFilterLessUsedTitle.setPaddingTop(0);
			cellFilterLessUsedTitle.setPaddingBottom(2);

			Paragraph parFilterLessUsedTitle = new Paragraph("FILTRO MENOS USADO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parFilterLessUsedTitle.setLeading(9);

			cellFilterLessUsedTitle.addElement(parFilterLessUsedTitle);

			PdfPCell cellFilterLessUsed = new PdfPCell();
			cellFilterLessUsed.setBorder(PdfPCell.NO_BORDER);
			cellFilterLessUsed.setPaddingRight(0);
			cellFilterLessUsed.setPaddingLeft(0);
			cellFilterLessUsed.setPaddingTop(0);
			cellFilterLessUsed.setPaddingBottom(2);

			Paragraph parFilterLessUsed = new Paragraph();
			parFilterLessUsed.setLeading(11);

			Image imgFilterLessUsed = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_FILTER_LESS_USED));
			imgFilterLessUsed.scaleAbsolute(10, 10);

			parFilterLessUsed.add(new Chunk(imgFilterLessUsed, 0, 0));
//
//			parFilterLessUsed.add(new Chunk(" " + reportInstagramHistorical.getFilterLessUsed().toUpperCase(),
//					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(255, 211, 4))));

			cellFilterLessUsed.addElement(parFilterLessUsed);

			PdfPCell cellFilterLessUsedDesc = new PdfPCell();
			cellFilterLessUsedDesc.setBorder(PdfPCell.NO_BORDER);
			cellFilterLessUsedDesc.setPaddingRight(0);
			cellFilterLessUsedDesc.setPaddingLeft(0);
			cellFilterLessUsedDesc.setPaddingTop(0);
			cellFilterLessUsedDesc.setPaddingBottom(8);

			Paragraph parFilterLessUsedDesc = new Paragraph();
			parFilterLessUsedDesc.setLeading(7);
//
//			String filterLessUsedDesc = "O filtro " + reportInstagramHistorical.getFilterLessUsed() + " é o menos utlizado em seu perfil, " +
//					"o efeito foi aplicado em " + reportInstagramHistorical.getFilterLessUsedValue() + " fotos até hoje.";
//
//			parFilterLessUsedDesc.add(filterLessUsedDesc);
			parFilterLessUsedDesc.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellFilterLessUsedDesc.addElement(parFilterLessUsedDesc);
			
			tableFiltersStats.addCell(cellFilterMostUsedTitle);
			tableFiltersStats.addCell(cellFilterMostUsed);
			tableFiltersStats.addCell(cellFilterMostUsedDesc);
			tableFiltersStats.addCell(cellFilterLessUsedTitle);
			tableFiltersStats.addCell(cellFilterLessUsed);
			tableFiltersStats.addCell(cellFilterLessUsedDesc);
			
			cellFiltersStats.addElement(tableFiltersStats);
			
			tableFilterPostsStatsInfo.addCell(cellChartFiltersPerPosts);
			tableFilterPostsStatsInfo.addCell(cellFiltersStats);
			
			cellFilterPostsStatsInfo.addElement(tableFilterPostsStatsInfo);

			tableFilterPostsStats.addCell(cellFilterPostsStatsTitle);
			tableFilterPostsStats.addCell(cellFilterPostsStatsInfo);
			
			document.add(tableFilterPostsStats);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addFilterLikesComments() {
		
		try {
			
			PdfPTable tableFilterLikesComments = new PdfPTable(1);
			tableFilterLikesComments.setTotalWidth(new float[]{523});
			tableFilterLikesComments.setLockedWidth(true);
			tableFilterLikesComments.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFilterLikesComments.setSpacingAfter(22);
			
			PdfPCell cellFilterLikesCommentsTitle = new PdfPCell();
			cellFilterLikesCommentsTitle.setBorder(Rectangle.BOTTOM);
			cellFilterLikesCommentsTitle.setBorderWidthBottom(0.8f);
			cellFilterLikesCommentsTitle.setPaddingBottom(5);
			cellFilterLikesCommentsTitle.setBorderColorBottom(new BaseColor(177, 177, 177));

			Paragraph parFilterLikesCommentsTitle = new Paragraph("NÚMEROS DE LIKES E COMENTÁRIOS POR FILTRO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
			parFilterLikesCommentsTitle.setAlignment(Element.ALIGN_CENTER);
			
			cellFilterLikesCommentsTitle.addElement(parFilterLikesCommentsTitle);

			PdfPCell cellFilterLikesCommentsInfo = new PdfPCell();
			cellFilterLikesCommentsInfo.setBorder(PdfPCell.NO_BORDER);
			cellFilterLikesCommentsInfo.setPaddingBottom(0);
			
			PdfPTable tableFilterLikesCommentsInfo = new PdfPTable(2);
			tableFilterLikesCommentsInfo.setTotalWidth(new float[]{200, 160});
			tableFilterLikesCommentsInfo.setLockedWidth(true);
			
			// Chart Filters Likes/Comments.
			PdfPCell cellChartFiltersLikesComments = new PdfPCell();
			cellChartFiltersLikesComments.setBorder(PdfPCell.NO_BORDER);
			cellChartFiltersLikesComments.setPadding(0);
			
			cellChartFiltersLikesComments.addElement(addChartFiltersLikesComments());

			// Filter Likes/Comments Stats.
			PdfPCell cellFiltersStats = new PdfPCell();
			cellFiltersStats.setBorder(PdfPCell.NO_BORDER); 
			cellFiltersStats.setPaddingTop(8);
			cellFiltersStats.setPaddingBottom(0);
			cellFiltersStats.setPaddingRight(0);
			cellFiltersStats.setPaddingLeft(30);

			PdfPTable tableFiltersStats = new PdfPTable(1);
			tableFiltersStats.setTotalWidth(new float[]{160});
			tableFiltersStats.setLockedWidth(true);
			tableFiltersStats.getDefaultCell().setPadding(0);
			tableFiltersStats.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			
			// Filter with more likes.
			PdfPCell cellFilterWithMoreLikesTitle = new PdfPCell();
			cellFilterWithMoreLikesTitle.setBorder(PdfPCell.NO_BORDER);
			cellFilterWithMoreLikesTitle.setPaddingRight(0);
			cellFilterWithMoreLikesTitle.setPaddingLeft(0);
			cellFilterWithMoreLikesTitle.setPaddingTop(0);
			cellFilterWithMoreLikesTitle.setPaddingBottom(2);

			Paragraph parFilterWithMoreLikesTitle = new Paragraph("FILTRO COM MAIS LIKES", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parFilterWithMoreLikesTitle.setLeading(9);

			cellFilterWithMoreLikesTitle.addElement(parFilterWithMoreLikesTitle);

			PdfPCell cellFilterWithMoreLikes = new PdfPCell();
			cellFilterWithMoreLikes.setBorder(PdfPCell.NO_BORDER);
			cellFilterWithMoreLikes.setPaddingRight(0);
			cellFilterWithMoreLikes.setPaddingLeft(0);
			cellFilterWithMoreLikes.setPaddingTop(0);
			cellFilterWithMoreLikes.setPaddingBottom(2);

			Paragraph parFilterWithMoreLikes = new Paragraph();
			parFilterWithMoreLikes.setLeading(11);

			Image imgFilterWithMoreLikes = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_TOTAL_LIKES));
			imgFilterWithMoreLikes.scaleAbsolute(10, 10);

			parFilterWithMoreLikes.add(new Chunk(imgFilterWithMoreLikes, 0, 0));

			parFilterWithMoreLikes.add(new Chunk(" " + reportInstagramHistorical.getFilterWithMoreLikes().toUpperCase(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(243, 72, 72))));

			cellFilterWithMoreLikes.addElement(parFilterWithMoreLikes);

			PdfPCell cellFilterWithMoreLikesDesc = new PdfPCell();
			cellFilterWithMoreLikesDesc.setBorder(PdfPCell.NO_BORDER);
			cellFilterWithMoreLikesDesc.setPaddingRight(0);
			cellFilterWithMoreLikesDesc.setPaddingLeft(0);
			cellFilterWithMoreLikesDesc.setPaddingTop(0);
			cellFilterWithMoreLikesDesc.setPaddingBottom(8);

			Paragraph parFilterWithMoreLikesDesc = new Paragraph();
			parFilterWithMoreLikesDesc.setLeading(7);

			String filterWithMoreLikesDesc = "O filtro " + reportInstagramHistorical.getFilterWithMoreLikes() + " é o que possui mais likes " +
					"entre todas suas fotos, somando " + reportInstagramHistorical.getFilterWithMoreLikesValue() + " likes.";

			parFilterWithMoreLikesDesc.add(filterWithMoreLikesDesc);
			parFilterWithMoreLikesDesc.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellFilterWithMoreLikesDesc.addElement(parFilterWithMoreLikesDesc);
			
			// Filter with more comments.
			PdfPCell cellFilterWithMoreCommentsTitle = new PdfPCell();
			cellFilterWithMoreCommentsTitle.setBorder(PdfPCell.NO_BORDER);
			cellFilterWithMoreCommentsTitle.setPaddingRight(0);
			cellFilterWithMoreCommentsTitle.setPaddingLeft(0);
			cellFilterWithMoreCommentsTitle.setPaddingTop(0);
			cellFilterWithMoreCommentsTitle.setPaddingBottom(2);

			Paragraph parFilterWithMoreCommentsTitle = new Paragraph("FILTRO COM MAIS COMENTÁRIOS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
			parFilterWithMoreCommentsTitle.setLeading(9);

			cellFilterWithMoreCommentsTitle.addElement(parFilterWithMoreCommentsTitle);

			PdfPCell cellFilterWithMoreComments = new PdfPCell();
			cellFilterWithMoreComments.setBorder(PdfPCell.NO_BORDER);
			cellFilterWithMoreComments.setPaddingRight(0);
			cellFilterWithMoreComments.setPaddingLeft(0);
			cellFilterWithMoreComments.setPaddingTop(0);
			cellFilterWithMoreComments.setPaddingBottom(2);

			Paragraph parFilterWithMoreComments = new Paragraph();
			parFilterWithMoreComments.setLeading(11);

			Image imgFilterWithMoreComments = Image.getInstance(br.com.opsocial.server.utils.UtilFunctions.getResourcePath(ReportIcons.REPORTS_INSTAGRAM_TOTAL_COMMENTS));
			imgFilterWithMoreComments.scaleAbsolute(10, 10);

			parFilterWithMoreComments.add(new Chunk(imgFilterWithMoreComments, 0, 0));

			parFilterWithMoreComments.add(new Chunk(" " + reportInstagramHistorical.getFilterWithMoreComments().toUpperCase(),
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(34, 201, 237))));

			cellFilterWithMoreComments.addElement(parFilterWithMoreComments);

			PdfPCell cellFilterWithMoreCommentsDesc = new PdfPCell();
			cellFilterWithMoreCommentsDesc.setBorder(PdfPCell.NO_BORDER);
			cellFilterWithMoreCommentsDesc.setPaddingRight(0);
			cellFilterWithMoreCommentsDesc.setPaddingLeft(0);
			cellFilterWithMoreCommentsDesc.setPaddingTop(0);
			cellFilterWithMoreCommentsDesc.setPaddingBottom(8);

			Paragraph parFilterWithMoreCommentsDesc = new Paragraph();
			parFilterWithMoreCommentsDesc.setLeading(7);

			String filterWithMoreCommentsDesc = "O filtro " + reportInstagramHistorical.getFilterWithMoreComments() + " é o que possui mais comentários " +
					"entre todas suas fotos, somando " + reportInstagramHistorical.getFilterWithMoreCommentsValue() + " comentários.";

			parFilterWithMoreCommentsDesc.add(filterWithMoreCommentsDesc);
			parFilterWithMoreCommentsDesc.setFont(new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(136, 136, 136)));

			cellFilterWithMoreCommentsDesc.addElement(parFilterWithMoreCommentsDesc);
			
			tableFiltersStats.addCell(cellFilterWithMoreLikesTitle);
			tableFiltersStats.addCell(cellFilterWithMoreLikes);
			tableFiltersStats.addCell(cellFilterWithMoreLikesDesc);
			tableFiltersStats.addCell(cellFilterWithMoreCommentsTitle);
			tableFiltersStats.addCell(cellFilterWithMoreComments);
			tableFiltersStats.addCell(cellFilterWithMoreCommentsDesc);
			
			cellFiltersStats.addElement(tableFiltersStats);
			
			tableFilterLikesCommentsInfo.addCell(cellChartFiltersLikesComments);
			tableFilterLikesCommentsInfo.addCell(cellFiltersStats);
			
			cellFilterLikesCommentsInfo.addElement(tableFilterLikesCommentsInfo);

			tableFilterLikesComments.addCell(cellFilterLikesCommentsTitle);
			tableFilterLikesComments.addCell(cellFilterLikesCommentsInfo);
			
			document.add(tableFilterLikesComments);

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
			
			for (int i = 0; i < reportInstagramHistorical.getInstagramTopPostTags().size(); i++) {
				addInstagramTopPostTag(reportInstagramHistorical.getInstagramTopPostTags().get(i), tableTopPostTagsTable, i+1);
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
			
			document.newPage();
			
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

			String timeMoreLikesFrom = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramHistorical.getTimeWithMoreLikes(), 0, 0));
			String timeMoreLikesUntil = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramHistorical.getTimeWithMoreLikes() + 1, 0, 0));

			parTimeMoreLikes.add(new Chunk(" " + timeMoreLikesFrom + " - " + timeMoreLikesUntil,
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(158, 218, 30))));

			cellTimeMoreLikes.addElement(parTimeMoreLikes);

			PdfPCell cellTimeMoreLikesDescription = new PdfPCell();
			cellTimeMoreLikesDescription.setBorder(PdfPCell.NO_BORDER);
			cellTimeMoreLikesDescription.setPaddingRight(0);
			cellTimeMoreLikesDescription.setPaddingLeft(0);
			cellTimeMoreLikesDescription.setPaddingTop(0);
			cellTimeMoreLikesDescription.setPaddingBottom(8);

			Paragraph parTimeMoreLikesDescription = new Paragraph(7, reportInstagramHistorical.getTimeWithMoreLikesPerc() + "% " +
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

			String timeLessLikesFrom = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramHistorical.getTimeWithLessLikes(), 0, 0));
			String timeLessLikesUntil = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramHistorical.getTimeWithLessLikes() + 1, 0, 0));

			parTimeLessLikes.add(new Chunk(" " + timeLessLikesFrom + " - " + timeLessLikesUntil,
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(215, 54, 49))));

			cellTimeLessLikes.addElement(parTimeLessLikes);

			PdfPCell cellTimeLessLikesDescription = new PdfPCell();
			cellTimeLessLikesDescription.setBorder(PdfPCell.NO_BORDER);
			cellTimeLessLikesDescription.setPaddingRight(0);
			cellTimeLessLikesDescription.setPaddingLeft(0);
			cellTimeLessLikesDescription.setPaddingTop(0);
			cellTimeLessLikesDescription.setPaddingBottom(8);

			Paragraph parTimeLessLikesDescription = new Paragraph(7, reportInstagramHistorical.getTimeWithLessLikesPerc() + "% " +
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

			String timeMoreCommentsFrom = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramHistorical.getTimeWithMoreComments(), 0, 0));
			String timeMoreCommentsUntil = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramHistorical.getTimeWithMoreComments() + 1, 0, 0));

			parTimeMoreComments.add(new Chunk(" " + timeMoreCommentsFrom + " - " + timeMoreCommentsUntil,
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(158, 218, 30))));

			cellTimeMoreComments.addElement(parTimeMoreComments);

			PdfPCell cellTimeMoreCommentsDescription = new PdfPCell();
			cellTimeMoreCommentsDescription.setBorder(PdfPCell.NO_BORDER);
			cellTimeMoreCommentsDescription.setPaddingRight(0);
			cellTimeMoreCommentsDescription.setPaddingLeft(0);
			cellTimeMoreCommentsDescription.setPaddingTop(0);
			cellTimeMoreCommentsDescription.setPaddingBottom(8);

			Paragraph parTimeMoreCommentsDescription = new Paragraph(7, reportInstagramHistorical.getTimeWithMoreCommentsPerc() + "% " +
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

			String timeLessCommentsFrom = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramHistorical.getTimeWithLessComments(), 0, 0));
			String timeLessCommentsUntil = new SimpleDateFormat("HH:mm").format( new Date(0, 0, 0, reportInstagramHistorical.getTimeWithLessComments() + 1, 0, 0));

			parTimeLessComments.add(new Chunk(" " + timeLessCommentsFrom + " - " + timeLessCommentsUntil,
					new Font(sourceSansPro, 15, Font.NORMAL, new BaseColor(215, 54, 49))));

			cellTimeLessComments.addElement(parTimeLessComments);

			PdfPCell cellTimeLessCommentsDescription = new PdfPCell();
			cellTimeLessCommentsDescription.setBorder(PdfPCell.NO_BORDER);
			cellTimeLessCommentsDescription.setPaddingRight(0);
			cellTimeLessCommentsDescription.setPaddingLeft(0);
			cellTimeLessCommentsDescription.setPaddingTop(0);
			cellTimeLessCommentsDescription.setPaddingBottom(8);

			Paragraph parTimeLessCommentsDescription = new Paragraph(7, reportInstagramHistorical.getTimeWithLessCommentsPerc() + "% " +
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
			
			document.newPage();

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
			for(InstagramInteractionRankingDTO instagramInteractionRanking : reportInstagramHistorical.getInstagramInteractionsRanking()) {
				
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
		
		String img = chartsImages.get("chartInteractionsPerHourHist").substring(chartsImages.get("chartInteractionsPerHourHist").indexOf("base64,") + 7);

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
	
	private Image addChartCommentsPerHour() {
		
		String img = chartsImages.get("chartCommentsPerHourHist").substring(chartsImages.get("chartCommentsPerHourHist").indexOf("base64,") + 7);

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
	
	private Image addChartLikesPerHour() {
		
		String img = chartsImages.get("chartLikesPerHourHist").substring(chartsImages.get("chartLikesPerHourHist").indexOf("base64,") + 7);

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
	
	private Image addChartNumberOfPosts() {
		
		String img = chartsImages.get("chartPostsPerMonthYear").substring(chartsImages.get("chartPostsPerMonthYear").indexOf("base64,") + 7);

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
	
	private Image addChartFiltersPerPosts() {
		
		String img = chartsImages.get("chartFiltersCounts").substring(chartsImages.get("chartFiltersCounts").indexOf("base64,") + 7);

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
			imageChart.scaleAbsoluteHeight(112);
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
	
	private Image addChartFiltersLikesComments() {
	
		String img = chartsImages.get("chartFiltersLikesComments").substring(chartsImages.get("chartFiltersLikesComments").indexOf("base64,") + 7);

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
	
	private Image addChartNumberOfLikes() {
		
		String img = chartsImages.get("chartLikesPerMonthYear").substring(chartsImages.get("chartLikesPerMonthYear").indexOf("base64,") + 7);

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
	
	private Image addChartNumberOfComments() {
		
		String img = chartsImages.get("chartCommentsPerMonthYear").substring(chartsImages.get("chartCommentsPerMonthYear").indexOf("base64,") + 7);

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

		HeaderFooterPDF headerFooterPDF = new HeaderFooterPDF(pdfWritter, "Relatório Instagram Histórico", (ReportLogoDTO) profile.getReportLogo());
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