package br.com.opsocial.server.utils.reports.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.opsocial.client.entity.report.ReportLogoDTO;
import br.com.opsocial.client.entity.report.facebook.FaceReportPostDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookPostToPostDTO;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.comparators.FaceReportPostDTOComparator;
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
//import com.smartgwt.client.util.StringUtil;

public class ReportFacebookPostToPostPDF {

	private ReportFacebookPostToPostDTO reportFacebookPostToPost;
	private Profile profile;

	private PdfWriter pdfWritter;
	private Document document;
	private String fileName;

	private static BaseFont sourceSansPro;

	private static final BaseColor BASE_COLOR_HEADER_TITLE = new BaseColor(52, 98, 144);

	public ReportFacebookPostToPostPDF(ReportFacebookPostToPostDTO reportFacebookPostToPost, Profile profile) {

		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".pdf");
		this.reportFacebookPostToPost = reportFacebookPostToPost;
		this.profile = profile;
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

		addHeaderFooter();
		this.document.open();

		addBasicInformation();
		addPostsStatsByPostType();
		addPostsMostEngaged();

		document.close();
	}

	private void addPostsMostEngaged() {

//		try {
//			
//			PdfPTable tablePostsMostEngagedTitle = new PdfPTable(1);
//			tablePostsMostEngagedTitle.setTotalWidth(new float[]{523});
//			tablePostsMostEngagedTitle.setLockedWidth(true);
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
//			PdfPTable tablePostsMostEngagedTableHeader = new PdfPTable(3);
//			tablePostsMostEngagedTableHeader.setTotalWidth(new float[]{114, 93, 160});
//			tablePostsMostEngagedTableHeader.setLockedWidth(true);
//			tablePostsMostEngagedTableHeader.setSpacingBefore(5);
//			
//			PdfPCell cellPostContent = new PdfPCell(new Phrase(new Chunk("Post", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
//			cellPostContent.setBorder(Rectangle.NO_BORDER);
//			cellPostContent.setBackgroundColor(new BaseColor(228, 228, 228));
//			cellPostContent.setPadding(3);
//			cellPostContent.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//
//			PdfPCell cellPostReach = new PdfPCell(new Phrase(new Chunk("Alcance", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
//			cellPostReach.setBorder(Rectangle.NO_BORDER);
//			cellPostReach.setBackgroundColor(new BaseColor(215, 215, 215));
//			cellPostReach.setPadding(3);
//			cellPostReach.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//
//			PdfPCell cellPostStories = new PdfPCell(new Phrase(new Chunk("Interações", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96,96,96)))));
//			cellPostStories.setBorder(Rectangle.NO_BORDER);
//			cellPostStories.setBackgroundColor(new BaseColor(228, 228, 228));
//			cellPostStories.setPadding(3);
//			cellPostStories.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//
//			tablePostsMostEngagedTableHeader.addCell(cellPostContent);
//			tablePostsMostEngagedTableHeader.addCell(cellPostReach);
//			tablePostsMostEngagedTableHeader.addCell(cellPostStories);
//
//			PdfPTable tablePostsMostEngagedTableRows = new PdfPTable(3);
//			tablePostsMostEngagedTableRows.setTotalWidth(new float[]{114, 93, 160});
//			tablePostsMostEngagedTableRows.setLockedWidth(true);
//			
//			if(reportFacebookPostToPost.getPostsMostEngagedOrder() != null) {
//				
//				Comparator<FaceReportPostDTO> faceReportPostDTOComparator = reportFacebookPostToPost.getPostsMostEngagedOrder().getAscending() 
//					? new FaceReportPostDTOComparator(reportFacebookPostToPost.getPostsMostEngagedOrder().getColumnNumber()) : 
//						Collections.reverseOrder(new FaceReportPostDTOComparator(reportFacebookPostToPost.getPostsMostEngagedOrder().getColumnNumber()));
//				
//					Collections.sort(reportFacebookPostToPost.getPostsMostEngaged(), faceReportPostDTOComparator);
//			}
//			
//			for(int i = 0; i < reportFacebookPostToPost.getPostsMostEngaged().size(); i++) {
//
//				PdfPCell cellPostContentRow = new PdfPCell();
//				cellPostContentRow.setBorder(Rectangle.BOX);
//				cellPostContentRow.setBorderWidthBottom(0.5f);
//				cellPostContentRow.setBorderColor(new BaseColor(200, 200, 200));
//				cellPostContentRow.setPadding(3);
//				cellPostContentRow.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//				cellPostContentRow.setVerticalAlignment(PdfPCell.ALIGN_TOP);
//				
//				PdfPTable tablePostContentRow = new PdfPTable(1);
//				tablePostContentRow.setTotalWidth(new float[]{114});
//				tablePostContentRow.setLockedWidth(true);
//				
//				PdfPCell cellPostContentDate = new PdfPCell();
//				cellPostContentDate.setPadding(3);
//				cellPostContentDate.setBorder(PdfPCell.NO_BORDER);
//				cellPostContentDate.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//				
//				cellPostContentDate.addElement(new Phrase(8, new Chunk(
//						new SimpleDateFormat("dd/MM/yy - HH'h'mm").format(reportFacebookPostToPost.getPostsMostEngaged().get(i).getCreatedTime()), 
//						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96, 96, 96)))));
//				
//				PdfPCell cellPostContentText = new PdfPCell();
//				cellPostContentText.setPaddingBottom(3);
//				cellPostContentText.setPaddingLeft(3);
//				cellPostContentText.setPaddingRight(3);
//				cellPostContentText.setPaddingTop(0);
//				cellPostContentText.setBorder(PdfPCell.NO_BORDER);
//				cellPostContentText.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
//				
//				Phrase phrPostContentRow = new Phrase();
//				phrPostContentRow.setLeading(8);
//
//				if(reportFacebookPostToPost.getPostsMostEngaged().get(i).getType().equals(FaceReportPostDTO.LINK_POSTED)) {
//
//					Image imgPostType = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_SEE_LINK));
//					imgPostType.scaleAbsolute(5, 5);
//
//					phrPostContentRow.add(new Chunk(imgPostType, 0, 0));
//					phrPostContentRow.add(new Chunk(" "));
//
//				} else if(reportFacebookPostToPost.getPostsMostEngaged().get(i).getType().equals(FaceReportPostDTO.PHOTOS_POSTED)) {
//
//					Image imgPostType = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_SEE_IMAGE));
//					imgPostType.scaleAbsolute(5, 5);
//
//					phrPostContentRow.add(new Chunk(imgPostType, 0, 0));
//					phrPostContentRow.add(new Chunk(" "));
//				}
//				
//				if(reportFacebookPostToPost.getPostsMostEngaged().get(i).getMessage() != null && !reportFacebookPostToPost.getPostsMostEngaged().get(i).getMessage().equals("")) {
//					phrPostContentRow.add(new Chunk(StringUtils.abbreviate(reportFacebookPostToPost.getPostsMostEngaged().get(i).getMessage(), 105), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(96, 96, 96))));
//				}
//				
//				cellPostContentText.addElement(phrPostContentRow);
//				
//				tablePostContentRow.addCell(cellPostContentDate);
//				tablePostContentRow.addCell(cellPostContentText);
//				
//				cellPostContentRow.addElement(tablePostContentRow);
//
//				// Reach.
//				PdfPCell cellPostReachRow = new PdfPCell();
//				cellPostReachRow.setBorder(Rectangle.BOX);
//				cellPostReachRow.setBorderWidthBottom(0.5f);
//				cellPostReachRow.setBorderColor(new BaseColor(200, 200, 200));
//				cellPostReachRow.setPadding(3);
//				cellPostReachRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//				cellPostReachRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);	
//				
//				PdfPTable tablePostReachRow = new PdfPTable(1);
//				tablePostReachRow.setTotalWidth(new float[]{93});
//				tablePostReachRow.setLockedWidth(true);
//				
//				PdfPCell cellPostReachByType = new PdfPCell();
//				cellPostReachByType.setPadding(0);
//				cellPostReachByType.setBorder(PdfPCell.NO_BORDER);
//				cellPostReachByType.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//				
//				PdfPTable tablePostReachByType = new PdfPTable(3);
//				tablePostReachByType.setTotalWidth(new float[]{25, 43, 25});
//				tablePostReachByType.setLockedWidth(true);
//				
//				PdfPCell cellPostReachPaid = new PdfPCell();
//				cellPostReachPaid.setBorder(PdfPCell.NO_BORDER);
//				cellPostReachPaid.setPadding(0);
//				cellPostReachPaid.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//				
//				Paragraph parPostReachPaidValue = new Paragraph(new Chunk(reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_IMPRESSIONS_PAID_UNIQUE).toString(), 
//						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(111, 111, 111))));
//				parPostReachPaidValue.setAlignment(Paragraph.ALIGN_CENTER);
//				parPostReachPaidValue.setLeading(10);
//				
//				Paragraph parPostReachPaid = new Paragraph(new Chunk("PAGO", 
//						new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(86, 144, 202))));
//				parPostReachPaid.setAlignment(Paragraph.ALIGN_CENTER);
//				parPostReachPaid.setLeading(9);
//				
//				cellPostReachPaid.addElement(parPostReachPaidValue);
//				cellPostReachPaid.addElement(parPostReachPaid);
//				
//				PdfPCell cellPostReachOrganic = new PdfPCell();
//				cellPostReachOrganic.setBorder(PdfPCell.NO_BORDER);
//				cellPostReachOrganic.setPadding(0);
//				cellPostReachOrganic.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//				
//				Paragraph parPostReachOrganicValue = new Paragraph(new Chunk(reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_IMPRESSIONS_ORGANIC_UNIQUE).toString(), 
//						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(111, 111, 111))));
//				parPostReachOrganicValue.setAlignment(Paragraph.ALIGN_CENTER);
//				parPostReachOrganicValue.setLeading(10);
//				
//				Paragraph parPostReachOrganic = new Paragraph(new Chunk("ORGÂNICO", 
//						new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(86, 144, 202))));
//				parPostReachOrganic.setAlignment(Paragraph.ALIGN_CENTER);
//				parPostReachOrganic.setLeading(9);
//				
//				cellPostReachOrganic.addElement(parPostReachOrganicValue);
//				cellPostReachOrganic.addElement(parPostReachOrganic);
//				
//				PdfPCell cellPostReachViral = new PdfPCell();
//				cellPostReachViral.setBorder(PdfPCell.NO_BORDER);
//				cellPostReachViral.setPadding(0);
//				cellPostReachViral.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//				
//				Paragraph parPostReachViralValue = new Paragraph(new Chunk(reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_IMPRESSIONS_VIRAL_UNIQUE).toString(), 
//						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(111, 111, 111))));
//				parPostReachViralValue.setAlignment(Paragraph.ALIGN_CENTER);
//				parPostReachViralValue.setLeading(10);
//				
//				Paragraph parPostReachViral = new Paragraph(new Chunk("VIRAL", 
//						new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(86, 144, 202))));
//				parPostReachViral.setAlignment(Paragraph.ALIGN_CENTER);
//				parPostReachViral.setLeading(9);
//				
//				cellPostReachViral.addElement(parPostReachViralValue);
//				cellPostReachViral.addElement(parPostReachViral);
//				
//				tablePostReachByType.addCell(cellPostReachPaid);
//				tablePostReachByType.addCell(cellPostReachOrganic);
//				tablePostReachByType.addCell(cellPostReachViral);
//				
//				cellPostReachByType.addElement(tablePostReachByType);
//				
//				PdfPCell cellPostReachTotal = new PdfPCell();
//				cellPostReachTotal.setBorder(PdfPCell.NO_BORDER);
//				cellPostReachTotal.setPadding(0);
//				
//				Long totalReach = reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_IMPRESSIONS_PAID_UNIQUE)
//						+ reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_IMPRESSIONS_ORGANIC_UNIQUE)
//						+ reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_IMPRESSIONS_VIRAL_UNIQUE);
//				
//				Paragraph parPostReachTotal = new Paragraph(new Chunk("Total " + totalReach, 
//						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(111, 111, 111))));
//				parPostReachTotal.setAlignment(Paragraph.ALIGN_CENTER);
//				parPostReachTotal.setLeading(10);
//				
//				cellPostReachTotal.addElement(parPostReachTotal);
//				
//				tablePostReachRow.addCell(cellPostReachByType);
//				tablePostReachRow.addCell(cellPostReachTotal);
//				
//				cellPostReachRow.addElement(tablePostReachRow);
//
//				// Interactions.
//				PdfPCell cellPostInteractionsRow = new PdfPCell();
//				cellPostInteractionsRow.setBorder(Rectangle.BOX);
//				cellPostInteractionsRow.setBorderWidthBottom(0.5f);
//				cellPostInteractionsRow.setBorderColor(new BaseColor(200, 200, 200));
//				cellPostInteractionsRow.setPadding(3);
//				cellPostInteractionsRow.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//				cellPostInteractionsRow.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//				
//				PdfPTable tablePostInteractionsRow = new PdfPTable(1);
//				tablePostInteractionsRow.setTotalWidth(new float[]{160});
//				tablePostInteractionsRow.setLockedWidth(true);
//				
//				PdfPCell cellPostReactionsByType = new PdfPCell();
//				cellPostReactionsByType.setPadding(0);
//				cellPostReactionsByType.setBorder(PdfPCell.NO_BORDER);
//				cellPostReactionsByType.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//				
//				PdfPTable tablePostReactionsByType = new PdfPTable(5);
//				tablePostReactionsByType.setTotalWidth(new float[]{32, 32, 32, 32, 32});
//				tablePostReactionsByType.setLockedWidth(true);
//				
//				tablePostReactionsByType.addCell(addPostInteractionPost(
//						reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_REACTIONS_LOVE_TOTAL).toString(), 
//						ReportIcons.REPORTS_POST_LOVE));
//				tablePostReactionsByType.addCell(addPostInteractionPost(
//						reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_REACTIONS_HAHA_TOTAL).toString(), 
//						ReportIcons.REPORTS_POST_HAHA));
//				tablePostReactionsByType.addCell(addPostInteractionPost(
//						reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_REACTIONS_WOW_TOTAL).toString(), 
//						ReportIcons.REPORTS_POST_WOW));
//				tablePostReactionsByType.addCell(addPostInteractionPost(
//						reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_REACTIONS_SORRY_TOTAL).toString(), 
//						ReportIcons.REPORTS_POST_SORRY));
//				tablePostReactionsByType.addCell(addPostInteractionPost(
//						reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_REACTIONS_ANGER_TOTAL).toString(), 
//						ReportIcons.REPORTS_POST_ANGER));
//				
//				cellPostReactionsByType.addElement(tablePostReactionsByType);
//				
//				PdfPCell cellPostInteractions = new PdfPCell();
//				cellPostInteractions.setBorder(PdfPCell.NO_BORDER);
//				cellPostInteractions.setPadding(0);
//				
//				PdfPTable tablePostInteractions = new PdfPTable(5);
//				tablePostInteractions.setTotalWidth(new float[]{32, 32, 32, 32, 32});
//				tablePostInteractions.setLockedWidth(true);
//				
//				tablePostInteractions.addCell(addPostInteractionPost(
//						reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_REACTIONS_LIKE_TOTAL).toString(), 
//						ReportIcons.REPORTS_POST_LIKES));
//				tablePostInteractions.addCell(addPostInteractionPost(
//						reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_CONSUMPTIONS).toString(), 
//						ReportIcons.REPORTS_POST_CLICKS));
//				tablePostInteractions.addCell(addPostInteractionPost(
//						reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_COMMENT).toString(), 
//						ReportIcons.REPORTS_POST_COMMENTS));
//				tablePostInteractions.addCell(addPostInteractionPost(
//						reportFacebookPostToPost.getPostsMostEngaged().get(i).getPostInsights().get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_SHARE).toString(), 
//						ReportIcons.REPORTS_POST_SHARES));
//				
//				PdfPCell cellPostInteractionsTotal = new PdfPCell();
//				cellPostInteractionsTotal.setBorder(PdfPCell.NO_BORDER);
//				cellPostInteractionsTotal.setPadding(0);
//				cellPostInteractionsTotal.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//				
//				Paragraph parPostInteractionsTotalTitle = new Paragraph(new Chunk("Total", 
//						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(111, 111, 111))));
//				parPostInteractionsTotalTitle.setAlignment(Paragraph.ALIGN_CENTER);
//				parPostInteractionsTotalTitle.setLeading(10);
//				
//				Paragraph parPostInteractionsTotalValue = new Paragraph(new Chunk(reportFacebookPostToPost.getPostsMostEngaged().get(i).getTotalInteractions().toString(), 
//						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(111, 111, 111))));
//				parPostInteractionsTotalValue.setAlignment(Paragraph.ALIGN_CENTER);
//				parPostInteractionsTotalValue.setLeading(10);
//				
//				cellPostInteractionsTotal.addElement(parPostInteractionsTotalTitle);
//				cellPostInteractionsTotal.addElement(parPostInteractionsTotalValue);
//				
//				tablePostInteractions.addCell(cellPostInteractionsTotal);
//				
//				cellPostInteractions.addElement(tablePostInteractions);
//				
//				tablePostInteractionsRow.addCell(cellPostReactionsByType);
//				tablePostInteractionsRow.addCell(cellPostInteractions);
//				
//				cellPostInteractionsRow.addElement(tablePostInteractionsRow);
//				
//				tablePostsMostEngagedTableRows.addCell(cellPostContentRow);
//				tablePostsMostEngagedTableRows.addCell(cellPostReachRow);
//				tablePostsMostEngagedTableRows.addCell(cellPostInteractionsRow);
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
	
	private PdfPCell addPostInteractionPost(String storyTypeValue, String storyTypeIcon) throws BadElementException, MalformedURLException, IOException {
		
		PdfPCell cellPostStoryType = new PdfPCell();
		cellPostStoryType.setBorder(PdfPCell.NO_BORDER);
		cellPostStoryType.setPadding(0);
		cellPostStoryType.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		
		Paragraph parPostStoryValue = new Paragraph(new Chunk(storyTypeValue, 
				new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(111, 111, 111))));
		parPostStoryValue.setAlignment(Paragraph.ALIGN_CENTER);
		parPostStoryValue.setLeading(10);
		
		Image imgPostStoryType = Image.getInstance(UtilFunctions.getResourcePath(storyTypeIcon));
		imgPostStoryType.scaleAbsolute(8, 8);
		imgPostStoryType.setAlignment(Image.ALIGN_CENTER);
		
		cellPostStoryType.addElement(parPostStoryValue);
		cellPostStoryType.addElement(imgPostStoryType);
		
		return cellPostStoryType;
	}

	private void addPostsStatsByPostType() {

//		try {
//
//			PdfPTable tablePostsStatsByPostType = new PdfPTable(1);
//			tablePostsStatsByPostType.setTotalWidth(new float[]{400});
//			tablePostsStatsByPostType.setLockedWidth(true);
//			tablePostsStatsByPostType.setHorizontalAlignment(Element.ALIGN_CENTER);
//			tablePostsStatsByPostType.setSpacingAfter(22);
//
//			PdfPCell cellPostsStatsByPostTypeTitle = new PdfPCell();
//			cellPostsStatsByPostTypeTitle.setBorder(Rectangle.BOTTOM);
//			cellPostsStatsByPostTypeTitle.setBorderWidthBottom(0.8f);
//			cellPostsStatsByPostTypeTitle.setPaddingBottom(5);
//			cellPostsStatsByPostTypeTitle.setBorderColorBottom(new BaseColor(177, 177, 177));
//
//			Paragraph parPostsStatsByPostTypeTitle = new Paragraph("MÉDIA DE ALCANCE E ENVOLVIMENTO DOS POSTS POR TIPO", new Font(sourceSansPro, 11, Font.BOLD, BASE_COLOR_HEADER_TITLE));
//			parPostsStatsByPostTypeTitle.setAlignment(Element.ALIGN_CENTER);
//
//			cellPostsStatsByPostTypeTitle.addElement(parPostsStatsByPostTypeTitle);
//
//			PdfPCell cellPostsStatsInfo = new PdfPCell();
//			cellPostsStatsInfo.setBorder(PdfPCell.NO_BORDER);
//			cellPostsStatsInfo.setPaddingBottom(0);
//			cellPostsStatsInfo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//
//			PdfPTable tablePostsStatsInfoHeader = new PdfPTable(4);
//			tablePostsStatsInfoHeader.setTotalWidth(new float[]{130, 90, 90, 90});
//			tablePostsStatsInfoHeader.setLockedWidth(true);
//
//			PdfPCell cellPostTypeHeader = new PdfPCell(new Phrase(new Chunk("TIPO DE POST", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(102, 102, 102)))));
//			cellPostTypeHeader.setBorder(Rectangle.BOTTOM);
//			cellPostTypeHeader.setBorderWidthBottom(0.8f);
//			cellPostTypeHeader.setBorderColorBottom(new BaseColor(196, 196, 196));
//			cellPostTypeHeader.setPadding(0);
//			cellPostTypeHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostTypeHeader.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellPostTypeHeader.setFixedHeight(34);
//
//			PdfPCell cellAvgReachHeader = new PdfPCell(new Phrase(new Chunk("ALCANCE MÉDIO", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(18, 139, 201)))));
//			cellAvgReachHeader.setBorder(Rectangle.BOTTOM);
//			cellAvgReachHeader.setBorderWidthBottom(0.8f);
//			cellAvgReachHeader.setBorderColorBottom(new BaseColor(196, 196, 196));
//			cellAvgReachHeader.setPadding(0);
//			cellAvgReachHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellAvgReachHeader.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellAvgReachHeader.setFixedHeight(34);
//
//			PdfPCell cellAvgInteractionsHeader = new PdfPCell(new Phrase(new Chunk("MÉDIA DE INTERAÇÕES", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(18, 139, 201)))));
//			cellAvgInteractionsHeader.setBorder(Rectangle.BOTTOM);
//			cellAvgInteractionsHeader.setBorderWidthBottom(0.8f);
//			cellAvgInteractionsHeader.setBorderColorBottom(new BaseColor(196, 196, 196));
//			cellAvgInteractionsHeader.setPadding(0);
//			cellAvgInteractionsHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellAvgInteractionsHeader.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellAvgInteractionsHeader.setFixedHeight(34);
//
//			PdfPCell cellEngagementHeader = new PdfPCell(new Phrase(new Chunk("ENGAJAMENTO", new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(18, 139, 201)))));
//			cellEngagementHeader.setBorder(Rectangle.BOTTOM);
//			cellEngagementHeader.setBorderWidthBottom(0.8f);
//			cellEngagementHeader.setBorderColorBottom(new BaseColor(196, 196, 196));
//			cellEngagementHeader.setPadding(0);
//			cellEngagementHeader.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellEngagementHeader.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellEngagementHeader.setFixedHeight(34);
//
//			tablePostsStatsInfoHeader.addCell(cellPostTypeHeader);
//			tablePostsStatsInfoHeader.addCell(cellAvgReachHeader);
//			tablePostsStatsInfoHeader.addCell(cellAvgInteractionsHeader);
//			tablePostsStatsInfoHeader.addCell(cellEngagementHeader);
//
//			PdfPTable tablePostsStatsInfoRow = new PdfPTable(4);
//			tablePostsStatsInfoRow.setTotalWidth(new float[]{130, 90, 90, 90});
//			tablePostsStatsInfoRow.setLockedWidth(true);
//
//			// Status.
//			PdfPCell cellTypeStatus = new PdfPCell();
//			cellTypeStatus.setBorder(PdfPCell.NO_BORDER);
//			cellTypeStatus.setBorderWidthBottom(0.8f);
//			cellTypeStatus.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellTypeStatus.setPadding(0);
//			cellTypeStatus.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellTypeStatus.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellTypeStatus.setFixedHeight(34);
//
//			PdfPTable tableTypeStatus = new PdfPTable(2);
//			tableTypeStatus.setTotalWidth(new float[]{21, 109});
//			tableTypeStatus.setLockedWidth(true);
//
//			PdfPCell cellStatusImage = new PdfPCell();
//			cellStatusImage.setBorder(PdfPCell.NO_BORDER);
//			cellStatusImage.setPadding(0);
//
//			Image imgStatus = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_POSTS_STATUS));
//			imgStatus.scaleAbsolute(21, 21);
//
//			cellStatusImage.addElement(imgStatus);
//
//			PdfPCell cellStatusValue = new PdfPCell();
//			cellStatusValue.setBorder(PdfPCell.NO_BORDER);
//			cellStatusValue.setPaddingLeft(3);
//			cellStatusValue.setPaddingRight(0);
//			cellStatusValue.setPaddingTop(0);
//			cellStatusValue.setPaddingBottom(0);
//
//			Phrase parStatus = new Phrase("POSTS DE STATUS", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parStatus.setLeading(7);
//
//			Paragraph parStatusValue = new Paragraph(reportFacebookPostToPost.getNumberOfPostsPerType().containsKey(FaceReportPostDTO.STATUS_UPDATE) ? 
//					reportFacebookPostToPost.getNumberOfPostsPerType().get(FaceReportPostDTO.STATUS_UPDATE).toString() : "0", new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(159, 220, 53)));
//			parStatusValue.setLeading(14);
//
//			cellStatusValue.addElement(parStatus);
//			cellStatusValue.addElement(parStatusValue);
//
//			tableTypeStatus.addCell(cellStatusImage);
//			tableTypeStatus.addCell(cellStatusValue);
//
//			cellTypeStatus.addElement(tableTypeStatus);
//
//			PdfPCell cellPostStatusAvgReach = new PdfPCell(new Phrase(
//					new Chunk(reportFacebookPostToPost.getAveragePostReachByPostType().containsKey(FaceReportPostDTO.STATUS_UPDATE) ? 
//							reportFacebookPostToPost.getAveragePostReachByPostType().get(FaceReportPostDTO.STATUS_UPDATE).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(102, 102, 102)))));
//			cellPostStatusAvgReach.setBorder(Rectangle.NO_BORDER);
//			cellPostStatusAvgReach.setBorderWidthBottom(0.8f);
//			cellPostStatusAvgReach.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellPostStatusAvgReach.setPadding(0);
//			cellPostStatusAvgReach.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostStatusAvgReach.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellPostStatusAvgReach.setFixedHeight(34);
//
//			PdfPCell cellPostStatusAvgInteractions = new PdfPCell(new Phrase(
//					new Chunk(reportFacebookPostToPost.getAveragePostInteractionsByPostType().containsKey(FaceReportPostDTO.STATUS_UPDATE) ? 
//							reportFacebookPostToPost.getAveragePostInteractionsByPostType().get(FaceReportPostDTO.STATUS_UPDATE).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(102, 102, 102)))));
//			cellPostStatusAvgInteractions.setBorder(Rectangle.NO_BORDER);
//			cellPostStatusAvgInteractions.setBorderWidthBottom(0.8f);
//			cellPostStatusAvgInteractions.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellPostStatusAvgInteractions.setPadding(0);
//			cellPostStatusAvgInteractions.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostStatusAvgInteractions.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellPostStatusAvgInteractions.setFixedHeight(34);
//
//			PdfPCell cellPostStatusEngagement = new PdfPCell(new Phrase(
//					new Chunk(reportFacebookPostToPost.getEngagementPercentageByPostType().containsKey(FaceReportPostDTO.STATUS_UPDATE) ? 
//							reportFacebookPostToPost.getEngagementPercentageByPostType().get(FaceReportPostDTO.STATUS_UPDATE).toString() + "%" : "0%", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(102, 102, 102)))));
//			cellPostStatusEngagement.setBorder(Rectangle.NO_BORDER);
//			cellPostStatusEngagement.setBorderWidthBottom(0.8f);
//			cellPostStatusEngagement.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellPostStatusEngagement.setPadding(0);
//			cellPostStatusEngagement.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostStatusEngagement.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellPostStatusEngagement.setFixedHeight(34);
//
//			// Link.
//			PdfPCell cellTypeLink = new PdfPCell();
//			cellTypeLink.setBorder(PdfPCell.NO_BORDER);
//			cellTypeLink.setBorderWidthBottom(0.8f);
//			cellTypeLink.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellTypeLink.setPadding(0);
//			cellTypeLink.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellTypeLink.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellTypeLink.setFixedHeight(34);
//
//			PdfPTable tableTypeLink = new PdfPTable(2);
//			tableTypeLink.setTotalWidth(new float[]{21, 109});
//			tableTypeLink.setLockedWidth(true);
//
//			PdfPCell cellLinkImage = new PdfPCell();
//			cellLinkImage.setBorder(PdfPCell.NO_BORDER);
//			cellLinkImage.setPadding(0);
//
//			Image imgLink = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_POSTS_LINK));
//			imgLink.scaleAbsolute(21, 21);
//
//			cellLinkImage.addElement(imgLink);
//
//			PdfPCell cellLinkValue = new PdfPCell();
//			cellLinkValue.setBorder(PdfPCell.NO_BORDER);
//			cellLinkValue.setPaddingLeft(3);
//			cellLinkValue.setPaddingRight(0);
//			cellLinkValue.setPaddingTop(0);
//			cellLinkValue.setPaddingBottom(0);
//
//			Phrase parLink = new Phrase("POSTS COM LINK", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parLink.setLeading(7);
//
//			Paragraph parLinkValue = new Paragraph(reportFacebookPostToPost.getNumberOfPostsPerType().containsKey(FaceReportPostDTO.LINK_POSTED) ? 
//					reportFacebookPostToPost.getNumberOfPostsPerType().get(FaceReportPostDTO.LINK_POSTED).toString() : "0", new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(243, 105, 36)));
//			parLinkValue.setLeading(14);
//
//			cellLinkValue.addElement(parLink);
//			cellLinkValue.addElement(parLinkValue);
//
//			tableTypeLink.addCell(cellLinkImage);
//			tableTypeLink.addCell(cellLinkValue);
//
//			cellTypeLink.addElement(tableTypeLink);
//
//			PdfPCell cellPostLinkAvgReach = new PdfPCell(new Phrase(
//					new Chunk(reportFacebookPostToPost.getAveragePostReachByPostType().containsKey(FaceReportPostDTO.LINK_POSTED) ? 
//							reportFacebookPostToPost.getAveragePostReachByPostType().get(FaceReportPostDTO.LINK_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(102, 102, 102)))));
//			cellPostLinkAvgReach.setBorder(Rectangle.NO_BORDER);
//			cellPostLinkAvgReach.setBorderWidthBottom(0.8f);
//			cellPostLinkAvgReach.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellPostLinkAvgReach.setPadding(0);
//			cellPostLinkAvgReach.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostLinkAvgReach.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellPostLinkAvgReach.setFixedHeight(34);
//
//			PdfPCell cellPostLinkAvgInteractions = new PdfPCell(new Phrase(
//					new Chunk(reportFacebookPostToPost.getAveragePostInteractionsByPostType().containsKey(FaceReportPostDTO.LINK_POSTED) ? 
//							reportFacebookPostToPost.getAveragePostInteractionsByPostType().get(FaceReportPostDTO.LINK_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(102, 102, 102)))));
//			cellPostLinkAvgInteractions.setBorder(Rectangle.NO_BORDER);
//			cellPostLinkAvgInteractions.setBorderWidthBottom(0.8f);
//			cellPostLinkAvgInteractions.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellPostLinkAvgInteractions.setPadding(0);
//			cellPostLinkAvgInteractions.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostLinkAvgInteractions.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellPostLinkAvgInteractions.setFixedHeight(34);
//
//			PdfPCell cellPostLinkEngagement = new PdfPCell(new Phrase(
//					new Chunk(reportFacebookPostToPost.getEngagementPercentageByPostType().containsKey(FaceReportPostDTO.LINK_POSTED) ? 
//							reportFacebookPostToPost.getEngagementPercentageByPostType().get(FaceReportPostDTO.LINK_POSTED).toString() + "%" : "0%", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(102, 102, 102)))));
//			cellPostLinkEngagement.setBorder(Rectangle.NO_BORDER);
//			cellPostLinkEngagement.setBorderWidthBottom(0.8f);
//			cellPostLinkEngagement.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellPostLinkEngagement.setPadding(0);
//			cellPostLinkEngagement.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostLinkEngagement.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellPostLinkEngagement.setFixedHeight(34);
//
//			// Photo.
//			PdfPCell cellTypePhoto = new PdfPCell();
//			cellTypePhoto.setBorder(PdfPCell.NO_BORDER);
//			cellTypePhoto.setBorderWidthBottom(0.8f);
//			cellTypePhoto.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellTypePhoto.setPadding(0);
//			cellTypePhoto.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellTypePhoto.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellTypePhoto.setFixedHeight(34);
//
//			PdfPTable tableTypePhoto = new PdfPTable(2);
//			tableTypePhoto.setTotalWidth(new float[]{21, 109});
//			tableTypePhoto.setLockedWidth(true);
//
//			PdfPCell cellPhotoImage = new PdfPCell();
//			cellPhotoImage.setBorder(PdfPCell.NO_BORDER);
//			cellPhotoImage.setPadding(0);
//
//			Image imgPhoto = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_POSTS_PHOTO));
//			imgPhoto.scaleAbsolute(21, 21);
//
//			cellPhotoImage.addElement(imgPhoto);
//
//			PdfPCell cellPhotoValue = new PdfPCell();
//			cellPhotoValue.setBorder(PdfPCell.NO_BORDER);
//			cellPhotoValue.setPaddingLeft(3);
//			cellPhotoValue.setPaddingRight(0);
//			cellPhotoValue.setPaddingTop(0);
//			cellPhotoValue.setPaddingBottom(0);
//
//			Phrase parPhoto = new Phrase("POSTS COM FOTO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parPhoto.setLeading(7);
//
//			Paragraph parPhotoValue = new Paragraph(reportFacebookPostToPost.getNumberOfPostsPerType().containsKey(FaceReportPostDTO.PHOTOS_POSTED) ? 
//					reportFacebookPostToPost.getNumberOfPostsPerType().get(FaceReportPostDTO.PHOTOS_POSTED).toString() : "0", new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(238, 197, 4)));
//			parPhotoValue.setLeading(14);
//
//			cellPhotoValue.addElement(parPhoto);
//			cellPhotoValue.addElement(parPhotoValue);
//
//			tableTypePhoto.addCell(cellPhotoImage);
//			tableTypePhoto.addCell(cellPhotoValue);
//
//			cellTypePhoto.addElement(tableTypePhoto);
//
//			PdfPCell cellPostPhotoAvgReach = new PdfPCell(new Phrase(
//					new Chunk(reportFacebookPostToPost.getAveragePostReachByPostType().containsKey(FaceReportPostDTO.PHOTOS_POSTED) ? 
//							reportFacebookPostToPost.getAveragePostReachByPostType().get(FaceReportPostDTO.PHOTOS_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(102, 102, 102)))));
//			cellPostPhotoAvgReach.setBorder(Rectangle.NO_BORDER);
//			cellPostPhotoAvgReach.setBorderWidthBottom(0.8f);
//			cellPostPhotoAvgReach.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellPostPhotoAvgReach.setPadding(0);
//			cellPostPhotoAvgReach.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostPhotoAvgReach.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellPostPhotoAvgReach.setFixedHeight(34);
//
//			PdfPCell cellPostPhotoAvgInteractions = new PdfPCell(new Phrase(
//					new Chunk(reportFacebookPostToPost.getAveragePostInteractionsByPostType().containsKey(FaceReportPostDTO.PHOTOS_POSTED) ? 
//							reportFacebookPostToPost.getAveragePostInteractionsByPostType().get(FaceReportPostDTO.PHOTOS_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(102, 102, 102)))));
//			cellPostPhotoAvgInteractions.setBorder(Rectangle.NO_BORDER);
//			cellPostPhotoAvgInteractions.setBorderWidthBottom(0.8f);
//			cellPostPhotoAvgInteractions.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellPostPhotoAvgInteractions.setPadding(0);
//			cellPostPhotoAvgInteractions.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostPhotoAvgInteractions.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellPostPhotoAvgInteractions.setFixedHeight(34);
//
//			PdfPCell cellPostPhotoEngagement = new PdfPCell(new Phrase(
//					new Chunk(reportFacebookPostToPost.getEngagementPercentageByPostType().containsKey(FaceReportPostDTO.PHOTOS_POSTED) ? 
//							reportFacebookPostToPost.getEngagementPercentageByPostType().get(FaceReportPostDTO.PHOTOS_POSTED).toString() + "%" : "0%", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(102, 102, 102)))));
//			cellPostPhotoEngagement.setBorder(Rectangle.NO_BORDER);
//			cellPostPhotoEngagement.setBorderWidthBottom(0.8f);
//			cellPostPhotoEngagement.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellPostPhotoEngagement.setPadding(0);
//			cellPostPhotoEngagement.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostPhotoEngagement.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellPostPhotoEngagement.setFixedHeight(34);
//
//			// Video.
//			PdfPCell cellTypeVideo = new PdfPCell();
//			cellTypeVideo.setBorder(PdfPCell.NO_BORDER);
//			cellTypeVideo.setBorderWidthBottom(0.8f);
//			cellTypeVideo.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellTypeVideo.setPadding(0);
//			cellTypeVideo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellTypeVideo.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellTypeVideo.setFixedHeight(34);
//
//			PdfPTable tableTypeVideo = new PdfPTable(2);
//			tableTypeVideo.setTotalWidth(new float[]{21, 109});
//			tableTypeVideo.setLockedWidth(true);
//
//			PdfPCell cellVideoImage = new PdfPCell();
//			cellVideoImage.setBorder(PdfPCell.NO_BORDER);
//			cellVideoImage.setPadding(0);
//
//			Image imgVideo = Image.getInstance(UtilFunctions.getResourcePath(ReportIcons.REPORTS_POSTS_VIDEO));
//			imgVideo.scaleAbsolute(21, 21);
//
//			cellVideoImage.addElement(imgVideo);
//
//			PdfPCell cellVideoValue = new PdfPCell();
//			cellVideoValue.setBorder(PdfPCell.NO_BORDER);
//			cellVideoValue.setPaddingLeft(3);
//			cellVideoValue.setPaddingRight(0);
//			cellVideoValue.setPaddingTop(0);
//			cellVideoValue.setPaddingBottom(0);
//
//			Phrase parVideo = new Phrase("POSTS COM VÍDEO", new Font(sourceSansPro, 11, Font.NORMAL, new BaseColor(134, 134, 134)));
//			parVideo.setLeading(7);
//
//			Paragraph parVideoValue = new Paragraph(reportFacebookPostToPost.getNumberOfPostsPerType().containsKey(FaceReportPostDTO.VIDEO_POSTED) ? 
//					reportFacebookPostToPost.getNumberOfPostsPerType().get(FaceReportPostDTO.VIDEO_POSTED).toString() : "0", new Font(sourceSansPro, 15, Font.BOLD, new BaseColor(60, 211, 254)));
//			parVideoValue.setLeading(14);
//
//			cellVideoValue.addElement(parVideo);
//			cellVideoValue.addElement(parVideoValue);
//
//			tableTypeVideo.addCell(cellVideoImage);
//			tableTypeVideo.addCell(cellVideoValue);
//
//			cellTypeVideo.addElement(tableTypeVideo);
//
//			PdfPCell cellPostVideoAvgReach = new PdfPCell(new Phrase(
//					new Chunk(reportFacebookPostToPost.getAveragePostReachByPostType().containsKey(FaceReportPostDTO.VIDEO_POSTED) ? 
//							reportFacebookPostToPost.getAveragePostReachByPostType().get(FaceReportPostDTO.VIDEO_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(102, 102, 102)))));
//			cellPostVideoAvgReach.setBorder(Rectangle.NO_BORDER);
//			cellPostVideoAvgReach.setBorderWidthBottom(0.8f);
//			cellPostVideoAvgReach.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellPostVideoAvgReach.setPadding(0);
//			cellPostVideoAvgReach.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostVideoAvgReach.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellPostVideoAvgReach.setFixedHeight(34);
//
//			PdfPCell cellPostVideoAvgInteractions = new PdfPCell(new Phrase(
//					new Chunk(reportFacebookPostToPost.getAveragePostInteractionsByPostType().containsKey(FaceReportPostDTO.VIDEO_POSTED) ? 
//							reportFacebookPostToPost.getAveragePostInteractionsByPostType().get(FaceReportPostDTO.VIDEO_POSTED).toString() : "0", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(102, 102, 102)))));
//			cellPostVideoAvgInteractions.setBorder(Rectangle.NO_BORDER);
//			cellPostVideoAvgInteractions.setBorderWidthBottom(0.8f);
//			cellPostVideoAvgInteractions.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellPostVideoAvgInteractions.setPadding(0);
//			cellPostVideoAvgInteractions.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostVideoAvgInteractions.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellPostVideoAvgInteractions.setFixedHeight(34);
//
//			PdfPCell cellPostVideoEngagement = new PdfPCell(new Phrase(
//					new Chunk(reportFacebookPostToPost.getEngagementPercentageByPostType().containsKey(FaceReportPostDTO.VIDEO_POSTED) ? 
//							reportFacebookPostToPost.getEngagementPercentageByPostType().get(FaceReportPostDTO.VIDEO_POSTED).toString() + "%" : "0%", 
//							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(102, 102, 102)))));
//			cellPostVideoEngagement.setBorder(Rectangle.NO_BORDER);
//			cellPostVideoEngagement.setBorderWidthBottom(0.8f);
//			cellPostVideoEngagement.setBorderColorBottom(new BaseColor(223, 223, 223));
//			cellPostVideoEngagement.setPadding(0);
//			cellPostVideoEngagement.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			cellPostVideoEngagement.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//			cellPostVideoEngagement.setFixedHeight(34);
//
//			tablePostsStatsInfoRow.addCell(cellTypeStatus);
//			tablePostsStatsInfoRow.addCell(cellPostStatusAvgReach);
//			tablePostsStatsInfoRow.addCell(cellPostStatusAvgInteractions);
//			tablePostsStatsInfoRow.addCell(cellPostStatusEngagement);
//
//			tablePostsStatsInfoRow.addCell(cellTypeLink);
//			tablePostsStatsInfoRow.addCell(cellPostLinkAvgReach);
//			tablePostsStatsInfoRow.addCell(cellPostLinkAvgInteractions);
//			tablePostsStatsInfoRow.addCell(cellPostLinkEngagement);
//
//			tablePostsStatsInfoRow.addCell(cellTypePhoto);
//			tablePostsStatsInfoRow.addCell(cellPostPhotoAvgReach);
//			tablePostsStatsInfoRow.addCell(cellPostPhotoAvgInteractions);
//			tablePostsStatsInfoRow.addCell(cellPostPhotoEngagement);
//
//			tablePostsStatsInfoRow.addCell(cellTypeVideo);
//			tablePostsStatsInfoRow.addCell(cellPostVideoAvgReach);
//			tablePostsStatsInfoRow.addCell(cellPostVideoAvgInteractions);
//			tablePostsStatsInfoRow.addCell(cellPostVideoEngagement);
//
//			cellPostsStatsInfo.addElement(tablePostsStatsInfoHeader);
//			cellPostsStatsInfo.addElement(tablePostsStatsInfoRow);
//
//			tablePostsStatsByPostType.addCell(cellPostsStatsByPostTypeTitle);
//			tablePostsStatsByPostType.addCell(cellPostsStatsInfo);
//
//			document.add(tablePostsStatsByPostType);
//
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

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

			String reportsDescription = "•  Relatório Facebook (Post a Post)  -  " + 
					new SimpleDateFormat("dd/MM/yy").format(new Date(reportFacebookPostToPost.getDateFrom() * 1000L))
					+" a " +
					new SimpleDateFormat("dd/MM/yy").format(new Date(reportFacebookPostToPost.getDateUntil() * 1000L));

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

	private void addHeaderFooter() {
		
		HeaderFooterPDF headerFooterPDF = new HeaderFooterPDF(pdfWritter, "Relatório Facebook Post a Post", (ReportLogoDTO) profile.getReportLogo());
		pdfWritter.setPageEvent(headerFooterPDF);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
