package br.com.opsocial.server.utils.monitorings.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.opsocial.client.entity.blogs.BlogPostMonitoringDTO;
import br.com.opsocial.client.entity.googleplus.GooglePlusPostMonitoringDTO;
import br.com.opsocial.client.entity.googleplus.ObjectAttachmentDTO;
import br.com.opsocial.client.entity.instagram.InstagramPostMonitoringDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringHistoricFilterDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringPostTagDTO;
import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;
import br.com.opsocial.client.entity.monitoring.TwitterPostMonitoringDTO;
import br.com.opsocial.client.entity.news.NewsPostMonitoringDTO;
import br.com.opsocial.client.entity.primitive.StringList;
import br.com.opsocial.client.entity.reclameaqui.ReclameAquiPostMonitoringDTO;
import br.com.opsocial.client.entity.youtube.YoutubePostMonitoringDTO;
import br.com.opsocial.client.utils.Icons;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.reports.ReportIcons;

import com.google.common.collect.Iterables;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.opsocial.ejb.das.MaintenanceMonitoringTagRemote;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.google.GooglePlusPostMonitoring;
import br.com.opsocial.ejb.entity.monitoring.MonitoringPostTag;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTag;
import br.com.opsocial.ejb.entity.news.NewsPostMonitoring;
import br.com.opsocial.ejb.entity.reclameaqui.ReclameAquiPostMonitoring;
import br.com.opsocial.ejb.entity.twitter.TwitterPost;

public class MonitoringPostsPDF {

	private List<PostMonitoringDTO> postsMonitoring;
	private MonitoringHistoricFilterDTO monitoringHistoricFilter;

	private PdfWriter pdfWritter;
	private Document document;
	private String fileName;

	private static BaseFont sourceSansPro;

	private static final BaseColor BASE_COLOR_HEADER_TITLE = new BaseColor(52, 98, 144);

	public MonitoringPostsPDF() {
	}

	public MonitoringPostsPDF(List<PostMonitoringDTO> postsMonitoring, MonitoringHistoricFilterDTO monitoringHistoricFilter) {

		this.fileName = String.valueOf(RandomStringUtils.randomAlphanumeric(8) + ".pdf");
		this.postsMonitoring = postsMonitoring;
		this.monitoringHistoricFilter = monitoringHistoricFilter;
		this.document = new Document(PageSize.A4, 36, 36, 70, 54);

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

		addMonitoringPosts();

		document.close();
	}

	private void addMonitoringPosts() {

		try {

			// step 4
			ColumnText column = new ColumnText(pdfWritter.getDirectContent());

			// Column definition
			float[][] x = {
					{ document.left(), document.left() + 250 },
					{ document.right() - 250, document.right() }
			};

			// add content to the column
			column.addElement(addMonitoringPostsTable());

			int count = 0;
			float height = 0;
			int status = ColumnText.START_COLUMN;

			// render the column as long as it has content
			while (ColumnText.hasMoreText(status)) {

				// set the dimensions of the current column
				column.setSimpleColumn(
						x[count][0], document.bottom(),
						x[count][1], document.top() - height - 30);

				// render as much content as possible
				status = column.go();

				// go to a new page if you've reached the last column
				if (++count > 1) {
					count = 0;
					document.newPage();
				}
			}
			document.newPage();

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private PdfPTable addMonitoringPostsTable() {

		PdfPTable tableMonitoringPosts = new PdfPTable(1);

		try {

			tableMonitoringPosts.setTotalWidth(new float[]{250});
			tableMonitoringPosts.setLockedWidth(true);

			for(PostMonitoringDTO postMonitoring : postsMonitoring) {

				PdfPCell cellMonitoringPost = new PdfPCell();
				cellMonitoringPost.setBorder(Rectangle.NO_BORDER);
				cellMonitoringPost.setBorderWidthBottom(0.5f);
				cellMonitoringPost.setBorderColorBottom(new BaseColor(188, 188, 188));
				cellMonitoringPost.setPaddingBottom(5);
				cellMonitoringPost.setPaddingTop(5);
				cellMonitoringPost.setPaddingRight(5);
				cellMonitoringPost.setPaddingLeft(0);
				cellMonitoringPost.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				PdfPTable tableMonitoringPost = new PdfPTable(1);
				tableMonitoringPost.setTotalWidth(new float[]{245});
				tableMonitoringPost.setLockedWidth(true);

//				if(postMonitoring instanceof FacePostMonitoringDTO) {
//					addFacebookPostMonitoring(tableMonitoringPost, (FacePostMonitoringDTO) postMonitoring);
//				} else 
				if(postMonitoring instanceof TwitterPostMonitoringDTO) { 
					addTwitterPostMonitoring(tableMonitoringPost, (TwitterPostMonitoringDTO) postMonitoring);	
				} else if(postMonitoring instanceof GooglePlusPostMonitoringDTO){
					addGooglePostMonitoring(tableMonitoringPost, (GooglePlusPostMonitoringDTO) postMonitoring);
				} else if(postMonitoring instanceof YoutubePostMonitoringDTO) {
					addYoutubePostMonitoring(tableMonitoringPost, (YoutubePostMonitoringDTO) postMonitoring);
				} else if(postMonitoring instanceof InstagramPostMonitoringDTO) {
					addInstagramPostMonitoring(tableMonitoringPost, (InstagramPostMonitoringDTO) postMonitoring);
				} else if(postMonitoring instanceof ReclameAquiPostMonitoringDTO) {
					addReclameAquiPostMonitoring(tableMonitoringPost, (ReclameAquiPostMonitoringDTO) postMonitoring);
				} else if(postMonitoring instanceof NewsPostMonitoringDTO) {
					addNewsPostMonitoring(tableMonitoringPost, (NewsPostMonitoringDTO) postMonitoring);
				} else if(postMonitoring instanceof BlogPostMonitoringDTO) {
					addBlogPostMonitoring(tableMonitoringPost, (BlogPostMonitoringDTO) postMonitoring);
				}

				cellMonitoringPost.addElement(tableMonitoringPost);

				tableMonitoringPosts.addCell(cellMonitoringPost);
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return tableMonitoringPosts;
	}
	
	private void addTwitterPostMonitoring(PdfPTable tableMonitoringPost, TwitterPostMonitoringDTO twitterPostMonitoring) {

		tableMonitoringPost.addCell(addPostHeader(ReportIcons.TWITTER_ICON, 
				twitterPostMonitoring.getTwitterPostDTO().getScreenName(), 
				new Date(Long.valueOf(twitterPostMonitoring.getTwitterPostDTO().getCreatedAt()) * 1000), twitterPostMonitoring.getTerm()));

		PdfPCell cellPostContent = new PdfPCell();
		cellPostContent.setBorder(Rectangle.NO_BORDER);
		cellPostContent.setPadding(0);
		cellPostContent.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

		Paragraph parPostContent = new Paragraph(9, StringUtils.abbreviate(
				twitterPostMonitoring.getTwitterPostDTO().getText(), 400), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

		cellPostContent.addElement(parPostContent);

		tableMonitoringPost.addCell(cellPostContent);

		if(twitterPostMonitoring.getTwitterPostDTO().getType() != null && 
				twitterPostMonitoring.getTwitterPostDTO().getType().equals(TwitterPost.PHOTO)) {

			PdfPCell cellPostLink = new PdfPCell();
			cellPostLink.setBorder(Rectangle.NO_BORDER);
			cellPostLink.setPaddingTop(7);
			cellPostLink.setPaddingBottom(0);
			cellPostLink.setPaddingLeft(0);
			cellPostLink.setPaddingRight(0);
			cellPostLink.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			Paragraph parPostLinkType = new Paragraph();
			parPostLinkType.setLeading(8);
			parPostLinkType.setFont(new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

			parPostLinkType.add("LINK DA FOTO:");

			Paragraph parPostLink = new Paragraph(8, twitterPostMonitoring.getTwitterPostDTO().getPictureUrl(), 
					new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

			cellPostLink.addElement(parPostLinkType);
			cellPostLink.addElement(parPostLink);

			tableMonitoringPost.addCell(cellPostLink);	
		}

		tableMonitoringPost.addCell(
				addPostFooter(twitterPostMonitoring.getQualification(), twitterPostMonitoring.getMonitoringPostTags(), 
						monitoringHistoricFilter.getMonitoringTerms(), monitoringHistoricFilter.getMonitoringWords()));

	}

	private void addGooglePostMonitoring(PdfPTable tableMonitoringPost, GooglePlusPostMonitoringDTO googlePlusPostMonitoring) {

		try {

			tableMonitoringPost.addCell(addPostHeader(
					ReportIcons.GOOGLEPLUS_ICON, googlePlusPostMonitoring.getGooglePlusPostDTO().getActor().getDisplayName(), 
					new Date(Long.valueOf(googlePlusPostMonitoring.getGooglePlusPostDTO().getPublished()) * 1000), googlePlusPostMonitoring.getTerm()));

			PdfPCell cellPostContent = new PdfPCell();
			cellPostContent.setBorder(Rectangle.NO_BORDER);
			cellPostContent.setPadding(0);
			cellPostContent.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			Paragraph parPostContent = new Paragraph(9, StringUtils.abbreviate(
					Jsoup.parse(googlePlusPostMonitoring.getGooglePlusPostDTO().getObject().getContent()).text(), 400), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

			cellPostContent.addElement(parPostContent);

			tableMonitoringPost.addCell(cellPostContent);

			if(!googlePlusPostMonitoring.getGooglePlusPostDTO().getObject().getAttachments().isEmpty()) {

				PdfPCell cellPostExtraContent = new PdfPCell();
				cellPostExtraContent.setBorder(Rectangle.NO_BORDER);
				cellPostExtraContent.setPaddingLeft(20);
				cellPostExtraContent.setPaddingRight(0);
				cellPostExtraContent.setPaddingTop(5);
				cellPostExtraContent.setPaddingBottom(0);
				cellPostExtraContent.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				PdfPTable tablePostExtraContent = new PdfPTable(1);
				tablePostExtraContent.setTotalWidth(new float[]{245});
				tablePostExtraContent.setLockedWidth(true);

				for(ObjectAttachmentDTO objectAttachment : googlePlusPostMonitoring.getGooglePlusPostDTO().getObject().getAttachments()) {

					if(objectAttachment.getDisplayName() != null) {

						PdfPCell cellPostName = new PdfPCell();
						cellPostName.setBorder(Rectangle.NO_BORDER);
						cellPostName.setPadding(0);
						cellPostName.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

						Paragraph parPostName = new Paragraph(9, StringUtils.abbreviate(objectAttachment.getDisplayName(), 70), 
								new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(52, 98, 145)));

						cellPostName.addElement(parPostName);

						tablePostExtraContent.addCell(cellPostName);
					}

					if(objectAttachment.getContent() != null) {

						PdfPCell cellPostDescription = new PdfPCell();
						cellPostDescription.setBorder(Rectangle.NO_BORDER);
						cellPostDescription.setPadding(0);
						cellPostDescription.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

						Paragraph parPostDescription = new Paragraph(9, StringUtils.abbreviate(objectAttachment.getContent(), 275), 
								new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(69, 69, 69)));

						cellPostDescription.addElement(parPostDescription);

						tablePostExtraContent.addCell(cellPostDescription);
					}

					break;
				}

				cellPostExtraContent.addElement(tablePostExtraContent);

				tableMonitoringPost.addCell(cellPostExtraContent);

				for(ObjectAttachmentDTO objectAttachment : googlePlusPostMonitoring.getGooglePlusPostDTO().getObject().getAttachments()) {

					if(objectAttachment.getUrl() != null) {

						PdfPCell cellPostLink = new PdfPCell();
						cellPostLink.setBorder(Rectangle.NO_BORDER);
						cellPostLink.setPaddingTop(7);
						cellPostLink.setPaddingBottom(0);
						cellPostLink.setPaddingLeft(0);
						cellPostLink.setPaddingRight(0);
						cellPostLink.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

						Paragraph parPostLinkType = new Paragraph();
						parPostLinkType.setLeading(8);
						parPostLinkType.setFont(new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

						if(objectAttachment.getObjectType().equals(ObjectAttachmentDTO.VIDEO)) {
							parPostLinkType.add("LINK DO VÍDEO:");
						} else if(objectAttachment.getObjectType().equals(ObjectAttachmentDTO.PHOTO)) {
							parPostLinkType.add("LINK DA FOTO:");
						} else if(objectAttachment.getObjectType().equals(ObjectAttachmentDTO.ARTICLE)) {
							parPostLinkType.add("LINK DO RESUMO DE LINK:");
						} else if(objectAttachment.getObjectType().equals(ObjectAttachmentDTO.ALBUM)) {
							parPostLinkType.add("LINK DO ÁLBUM:");
						}

						Paragraph parPostLink = new Paragraph(8, objectAttachment.getUrl(), 
								new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

						cellPostLink.addElement(parPostLinkType);
						cellPostLink.addElement(parPostLink);

						tableMonitoringPost.addCell(cellPostLink);	
					}	

					break;
				}
			}

			tableMonitoringPost.addCell(addPostFooter(googlePlusPostMonitoring.getQualification(), googlePlusPostMonitoring.getMonitoringPostTags(), 
					monitoringHistoricFilter.getMonitoringTerms(), monitoringHistoricFilter.getMonitoringWords()));

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void addYoutubePostMonitoring(PdfPTable tableMonitoringPost, YoutubePostMonitoringDTO youtubePostMonitoring) {

		try {

			tableMonitoringPost.addCell(addPostHeader(ReportIcons.YOUTUBE_ICON, 
					youtubePostMonitoring.getYoutubePostDTO().getSnippet().getChannelTitle(), 
					new Date(Long.valueOf(youtubePostMonitoring.getYoutubePostDTO().getSnippet().getPublishedAt()) * 1000), youtubePostMonitoring.getTerm()));

			PdfPCell cellPostExtraContent = new PdfPCell();
			cellPostExtraContent.setBorder(Rectangle.NO_BORDER);
			cellPostExtraContent.setPaddingLeft(20);
			cellPostExtraContent.setPaddingRight(0);
			cellPostExtraContent.setPaddingTop(5);
			cellPostExtraContent.setPaddingBottom(0);
			cellPostExtraContent.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			PdfPTable tablePostExtraContent = new PdfPTable(1);
			tablePostExtraContent.setTotalWidth(new float[]{245});
			tablePostExtraContent.setLockedWidth(true);

			if(youtubePostMonitoring.getYoutubePostDTO().getSnippet().getTitle() != null) {

				PdfPCell cellPostName = new PdfPCell();
				cellPostName.setBorder(Rectangle.NO_BORDER);
				cellPostName.setPadding(0);
				cellPostName.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				Paragraph parPostName = new Paragraph(9, StringUtils.abbreviate(youtubePostMonitoring.getYoutubePostDTO().getSnippet().getTitle(), 70), 
						new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(52, 98, 145)));

				cellPostName.addElement(parPostName);

				tablePostExtraContent.addCell(cellPostName);
			}

			PdfPCell cellPostCaption = new PdfPCell();
			cellPostCaption.setBorder(Rectangle.NO_BORDER);
			cellPostCaption.setPadding(0);
			cellPostCaption.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			Paragraph parPostCaption = new Paragraph(9, "www.youtube.com", 
					new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(89, 89, 89)));

			cellPostCaption.addElement(parPostCaption);

			tablePostExtraContent.addCell(cellPostCaption);

			if(youtubePostMonitoring.getYoutubePostDTO().getSnippet().getDescription() != null) {

				PdfPCell cellPostDescription = new PdfPCell();
				cellPostDescription.setBorder(Rectangle.NO_BORDER);
				cellPostDescription.setPadding(0);
				cellPostDescription.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				Paragraph parPostDescription = new Paragraph(9, StringUtils.abbreviate(youtubePostMonitoring.getYoutubePostDTO().getSnippet().getDescription(), 275), 
						new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(69, 69, 69)));

				cellPostDescription.addElement(parPostDescription);

				tablePostExtraContent.addCell(cellPostDescription);
			}

			cellPostExtraContent.addElement(tablePostExtraContent);

			tableMonitoringPost.addCell(cellPostExtraContent);

			if(youtubePostMonitoring.getYoutubePostDTO().getSnippet().getUrl() != null) {

				PdfPCell cellPostLink = new PdfPCell();
				cellPostLink.setBorder(Rectangle.NO_BORDER);
				cellPostLink.setPaddingTop(7);
				cellPostLink.setPaddingBottom(0);
				cellPostLink.setPaddingLeft(0);
				cellPostLink.setPaddingRight(0);
				cellPostLink.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				Paragraph parPostLinkType = new Paragraph();
				parPostLinkType.setLeading(8);
				parPostLinkType.setFont(new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

				parPostLinkType.add("LINK DO VÍDEO:");

				Paragraph parPostLink = new Paragraph(8, youtubePostMonitoring.getYoutubePostDTO().getSnippet().getUrl(), 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

				cellPostLink.addElement(parPostLinkType);
				cellPostLink.addElement(parPostLink);

				tableMonitoringPost.addCell(cellPostLink);	
			}

			tableMonitoringPost.addCell(addPostFooter(youtubePostMonitoring.getQualification(), youtubePostMonitoring.getMonitoringPostTags(), 
					monitoringHistoricFilter.getMonitoringTerms(), monitoringHistoricFilter.getMonitoringWords()));

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	private void addInstagramPostMonitoring(PdfPTable tableMonitoringPost, InstagramPostMonitoringDTO instagramPostMonitoring) {

		tableMonitoringPost.addCell(addPostHeader(
				ReportIcons.INSTAGRAM_ICON, instagramPostMonitoring.getInstagramPostDTO().getUserName(), 
				new Date(Long.valueOf(instagramPostMonitoring.getInstagramPostDTO().getCreateTime()) * 1000), 
				instagramPostMonitoring.getTerm()));

		PdfPCell cellPostContent = new PdfPCell();
		cellPostContent.setBorder(Rectangle.NO_BORDER);
		cellPostContent.setPadding(0);
		cellPostContent.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

		Paragraph parPostContent = new Paragraph(9, StringUtils.abbreviate(
				instagramPostMonitoring.getInstagramPostDTO().getPictureCaption(), 400), new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

		cellPostContent.addElement(parPostContent);

		tableMonitoringPost.addCell(cellPostContent);

		if(instagramPostMonitoring.getInstagramPostDTO().getMediaLink() != null) {

			PdfPCell cellPostLink = new PdfPCell();
			cellPostLink.setBorder(Rectangle.NO_BORDER);
			cellPostLink.setPaddingTop(7);
			cellPostLink.setPaddingBottom(0);
			cellPostLink.setPaddingLeft(0);
			cellPostLink.setPaddingRight(0);
			cellPostLink.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			Paragraph parPostLinkType = new Paragraph();
			parPostLinkType.setLeading(8);
			parPostLinkType.setFont(new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

			parPostLinkType.add("LINK DA FOTO:");

			Paragraph parPostLink = new Paragraph(8, instagramPostMonitoring.getInstagramPostDTO().getMediaLink(), 
					new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

			cellPostLink.addElement(parPostLinkType);
			cellPostLink.addElement(parPostLink);

			tableMonitoringPost.addCell(cellPostLink);	
		}

		tableMonitoringPost.addCell(addPostFooter(instagramPostMonitoring.getQualification(), instagramPostMonitoring.getMonitoringPostTags(), 
				monitoringHistoricFilter.getMonitoringTerms(), monitoringHistoricFilter.getMonitoringWords()));
	}

	private void addReclameAquiPostMonitoring(PdfPTable tableMonitoringPost, ReclameAquiPostMonitoringDTO reclameAquiPostMonitoring) {

		try {

			tableMonitoringPost.addCell(addPostHeader(
					ReportIcons.RECLAMEAQUI_ICON, null, 
					new Date(Long.valueOf(reclameAquiPostMonitoring.getReclameAquiPostDTO().getCreatedTime()) * 1000), 
					reclameAquiPostMonitoring.getTerm()));

			PdfPCell cellPostExtraContent = new PdfPCell();
			cellPostExtraContent.setBorder(Rectangle.NO_BORDER);
			cellPostExtraContent.setPaddingLeft(20);
			cellPostExtraContent.setPaddingRight(0);
			cellPostExtraContent.setPaddingTop(5);
			cellPostExtraContent.setPaddingBottom(0);
			cellPostExtraContent.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			PdfPTable tablePostExtraContent = new PdfPTable(1);
			tablePostExtraContent.setTotalWidth(new float[]{245});
			tablePostExtraContent.setLockedWidth(true);

			if(reclameAquiPostMonitoring.getReclameAquiPostDTO().getTitle() != null) {

				PdfPCell cellPostName = new PdfPCell();
				cellPostName.setBorder(Rectangle.NO_BORDER);
				cellPostName.setPadding(0);
				cellPostName.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				Paragraph parPostName = new Paragraph(9, StringUtils.abbreviate(reclameAquiPostMonitoring.getReclameAquiPostDTO().getTitle(), 70), 
						new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(52, 98, 145)));

				cellPostName.addElement(parPostName);

				tablePostExtraContent.addCell(cellPostName);
			}

			if(reclameAquiPostMonitoring.getReclameAquiPostDTO().getLink() != null) {

				PdfPCell cellPostCaption = new PdfPCell();
				cellPostCaption.setBorder(Rectangle.NO_BORDER);
				cellPostCaption.setPadding(0);
				cellPostCaption.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				Paragraph parPostCaption = new Paragraph(9, StringUtils.abbreviate(reclameAquiPostMonitoring.getReclameAquiPostDTO().getLink(), 70), 
						new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(89, 89, 89)));

				cellPostCaption.addElement(parPostCaption);

				tablePostExtraContent.addCell(cellPostCaption);
			}

			if(reclameAquiPostMonitoring.getReclameAquiPostDTO().getSnippet() != null) {

				PdfPCell cellPostDescription = new PdfPCell();
				cellPostDescription.setBorder(Rectangle.NO_BORDER);
				cellPostDescription.setPadding(0);
				cellPostDescription.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				
				Paragraph parPostDescription = new Paragraph(9, 
						StringUtils.abbreviate(reclameAquiPostMonitoring.getReclameAquiPostDTO().getSnippet(), 275), 
						new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(69, 69, 69)));

				cellPostDescription.addElement(parPostDescription);

				tablePostExtraContent.addCell(cellPostDescription);
			}

			cellPostExtraContent.addElement(tablePostExtraContent);

			tableMonitoringPost.addCell(cellPostExtraContent);

			if(reclameAquiPostMonitoring.getReclameAquiPostDTO().getLink() != null) {

				PdfPCell cellPostLink = new PdfPCell();
				cellPostLink.setBorder(Rectangle.NO_BORDER);
				cellPostLink.setPaddingTop(7);
				cellPostLink.setPaddingBottom(0);
				cellPostLink.setPaddingLeft(0);
				cellPostLink.setPaddingRight(0);
				cellPostLink.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				Paragraph parPostLinkType = new Paragraph();
				parPostLinkType.setLeading(8);
				parPostLinkType.setFont(new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

				parPostLinkType.add("LINK DO RECLAME AQUI:");

				Paragraph parPostLink = new Paragraph(8, reclameAquiPostMonitoring.getReclameAquiPostDTO().getLink(), 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

				cellPostLink.addElement(parPostLinkType);
				cellPostLink.addElement(parPostLink);

				tableMonitoringPost.addCell(cellPostLink);	
			}

			tableMonitoringPost.addCell(addPostFooter(reclameAquiPostMonitoring.getQualification(), reclameAquiPostMonitoring.getMonitoringPostTags(), 
					monitoringHistoricFilter.getMonitoringTerms(), monitoringHistoricFilter.getMonitoringWords()));

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void addNewsPostMonitoring(PdfPTable tableMonitoringPost, NewsPostMonitoringDTO newsPostMonitoring) {

		try {

			tableMonitoringPost.addCell(addPostHeader(ReportIcons.NEWS_ICON, 
					newsPostMonitoring.getNewsPostDTO().getPublisher(), 
					new Date(Long.valueOf(newsPostMonitoring.getNewsPostDTO().getCreatedTime()) * 1000), newsPostMonitoring.getTerm()));

			PdfPCell cellPostExtraContent = new PdfPCell();
			cellPostExtraContent.setBorder(Rectangle.NO_BORDER);
			cellPostExtraContent.setPaddingLeft(20);
			cellPostExtraContent.setPaddingRight(0);
			cellPostExtraContent.setPaddingTop(5);
			cellPostExtraContent.setPaddingBottom(0);
			cellPostExtraContent.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			PdfPTable tablePostExtraContent = new PdfPTable(1);
			tablePostExtraContent.setTotalWidth(new float[]{245});
			tablePostExtraContent.setLockedWidth(true);

			if(newsPostMonitoring.getNewsPostDTO().getTitle() != null) {

				PdfPCell cellPostName = new PdfPCell();
				cellPostName.setBorder(Rectangle.NO_BORDER);
				cellPostName.setPadding(0);
				cellPostName.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				Paragraph parPostName = new Paragraph(9, StringUtils.abbreviate(newsPostMonitoring.getNewsPostDTO().getTitle(), 70), 
						new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(52, 98, 145)));

				cellPostName.addElement(parPostName);

				tablePostExtraContent.addCell(cellPostName);
			}

			if(newsPostMonitoring.getNewsPostDTO().getPublisherUrl() != null) {

				PdfPCell cellPostCaption = new PdfPCell();
				cellPostCaption.setBorder(Rectangle.NO_BORDER);
				cellPostCaption.setPadding(0);
				cellPostCaption.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				Paragraph parPostCaption = new Paragraph(9, StringUtils.abbreviate(newsPostMonitoring.getNewsPostDTO().getPublisherUrl(), 70), 
						new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(89, 89, 89)));

				cellPostCaption.addElement(parPostCaption);

				tablePostExtraContent.addCell(cellPostCaption);
			}

			if(newsPostMonitoring.getNewsPostDTO().getDescription() != null) {

				PdfPCell cellPostDescription = new PdfPCell();
				cellPostDescription.setBorder(Rectangle.NO_BORDER);
				cellPostDescription.setPadding(0);
				cellPostDescription.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				Paragraph parPostDescription = new Paragraph(9, 
						StringUtils.abbreviate(newsPostMonitoring.getNewsPostDTO().getDescription(), 275), 
						new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(69, 69, 69)));

				cellPostDescription.addElement(parPostDescription);

				tablePostExtraContent.addCell(cellPostDescription);
			}

			cellPostExtraContent.addElement(tablePostExtraContent);

			tableMonitoringPost.addCell(cellPostExtraContent);

			if(newsPostMonitoring.getNewsPostDTO().getUrl() != null) {

				PdfPCell cellPostLink = new PdfPCell();
				cellPostLink.setBorder(Rectangle.NO_BORDER);
				cellPostLink.setPaddingTop(7);
				cellPostLink.setPaddingBottom(0);
				cellPostLink.setPaddingLeft(0);
				cellPostLink.setPaddingRight(0);
				cellPostLink.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				Paragraph parPostLinkType = new Paragraph();
				parPostLinkType.setLeading(8);
				parPostLinkType.setFont(new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

				parPostLinkType.add("LINK DA NOTÍCIA:");

				Paragraph parPostLink = new Paragraph(8, newsPostMonitoring.getNewsPostDTO().getUrl(), 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

				cellPostLink.addElement(parPostLinkType);
				cellPostLink.addElement(parPostLink);

				tableMonitoringPost.addCell(cellPostLink);	
			}

			tableMonitoringPost.addCell(addPostFooter(newsPostMonitoring.getQualification(), newsPostMonitoring.getMonitoringPostTags(), 
					monitoringHistoricFilter.getMonitoringTerms(), monitoringHistoricFilter.getMonitoringWords()));

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void addBlogPostMonitoring(PdfPTable tableMonitoringPost, BlogPostMonitoringDTO blogPostMonitoring) {

		try {

			tableMonitoringPost.addCell(addPostHeader(ReportIcons.BLOGS_ICON, 
					blogPostMonitoring.getBlogPostDTO().getPublisher(), 
					new Date(Long.valueOf(blogPostMonitoring.getBlogPostDTO().getCreatedTime()) * 1000), blogPostMonitoring.getTerm()));

			PdfPCell cellPostExtraContent = new PdfPCell();
			cellPostExtraContent.setBorder(Rectangle.NO_BORDER);
			cellPostExtraContent.setPaddingLeft(20);
			cellPostExtraContent.setPaddingRight(0);
			cellPostExtraContent.setPaddingTop(5);
			cellPostExtraContent.setPaddingBottom(0);
			cellPostExtraContent.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			PdfPTable tablePostExtraContent = new PdfPTable(1);
			tablePostExtraContent.setTotalWidth(new float[]{245});
			tablePostExtraContent.setLockedWidth(true);

			if(blogPostMonitoring.getBlogPostDTO().getTitle() != null) {

				PdfPCell cellPostName = new PdfPCell();
				cellPostName.setBorder(Rectangle.NO_BORDER);
				cellPostName.setPadding(0);
				cellPostName.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				Paragraph parPostName = new Paragraph(9, StringUtils.abbreviate(blogPostMonitoring.getBlogPostDTO().getTitle(), 70), 
						new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(52, 98, 145)));

				cellPostName.addElement(parPostName);

				tablePostExtraContent.addCell(cellPostName);
			}

			if(blogPostMonitoring.getBlogPostDTO().getPublisherUrl() != null) {

				PdfPCell cellPostCaption = new PdfPCell();
				cellPostCaption.setBorder(Rectangle.NO_BORDER);
				cellPostCaption.setPadding(0);
				cellPostCaption.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				Paragraph parPostCaption = new Paragraph(9, StringUtils.abbreviate(blogPostMonitoring.getBlogPostDTO().getPublisherUrl(), 70), 
						new Font(sourceSansPro, 7, Font.ITALIC, new BaseColor(89, 89, 89)));

				cellPostCaption.addElement(parPostCaption);

				tablePostExtraContent.addCell(cellPostCaption);
			}

			if(blogPostMonitoring.getBlogPostDTO().getDescription() != null) {

				PdfPCell cellPostDescription = new PdfPCell();
				cellPostDescription.setBorder(Rectangle.NO_BORDER);
				cellPostDescription.setPadding(0);
				cellPostDescription.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				Paragraph parPostDescription = new Paragraph(9, 
						StringUtils.abbreviate(blogPostMonitoring.getBlogPostDTO().getDescription(), 275), 
						new Font(sourceSansPro, 7, Font.NORMAL, new BaseColor(69, 69, 69)));

				cellPostDescription.addElement(parPostDescription);

				tablePostExtraContent.addCell(cellPostDescription);
			}

			cellPostExtraContent.addElement(tablePostExtraContent);

			tableMonitoringPost.addCell(cellPostExtraContent);

			if(blogPostMonitoring.getBlogPostDTO().getUrl() != null) {

				PdfPCell cellPostLink = new PdfPCell();
				cellPostLink.setBorder(Rectangle.NO_BORDER);
				cellPostLink.setPaddingTop(7);
				cellPostLink.setPaddingBottom(0);
				cellPostLink.setPaddingLeft(0);
				cellPostLink.setPaddingRight(0);
				cellPostLink.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

				Paragraph parPostLinkType = new Paragraph();
				parPostLinkType.setLeading(8);
				parPostLinkType.setFont(new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

				parPostLinkType.add("LINK DO BLOG:");

				Paragraph parPostLink = new Paragraph(8, blogPostMonitoring.getBlogPostDTO().getUrl(), 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(69, 69, 69)));

				cellPostLink.addElement(parPostLinkType);
				cellPostLink.addElement(parPostLink);

				tableMonitoringPost.addCell(cellPostLink);	
			}

			tableMonitoringPost.addCell(addPostFooter(blogPostMonitoring.getQualification(), blogPostMonitoring.getMonitoringPostTags(), 
					monitoringHistoricFilter.getMonitoringTerms(), monitoringHistoricFilter.getMonitoringWords()));

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	private PdfPCell addPostFooter(Character qualification, List<MonitoringPostTag> monitoringPostTags, List<String> monitoringPostTerms, StringList monitoringPostWords) {

		PdfPCell cellPostFooter = new PdfPCell();
		cellPostFooter.setBorder(Rectangle.NO_BORDER);
		cellPostFooter.setPaddingBottom(0);
		cellPostFooter.setPaddingTop(5);
		cellPostFooter.setPaddingLeft(0);
		cellPostFooter.setPaddingRight(0);
		cellPostFooter.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

		Paragraph parPostFooter = new Paragraph();
		parPostFooter.setLeading(11);

		Chunk cnkQualificationTitle = new Chunk("Classificação: ",
				new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(57, 57, 57)));

		Chunk cnkQualificationValue = new Chunk();
		if(qualification == null) {
			cnkQualificationValue.append("NENHUMA");
			cnkQualificationValue.setFont(new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(57, 57, 57)));
		} else if(qualification.equals(PostMonitoringDTO.POSITIVE)) {
			cnkQualificationValue.append("POSITIVO");
			cnkQualificationValue.setFont(new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(87, 148, 0)));
		} else if(qualification.equals(PostMonitoringDTO.NEUTER)) {
			cnkQualificationValue.append("NEUTRO");
			cnkQualificationValue.setFont(new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(172, 146, 1)));
		} else if(qualification.equals(PostMonitoringDTO.NEGATIVE)) {
			cnkQualificationValue.append("NEGATIVO");
			cnkQualificationValue.setFont(new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(148, 14, 0)));
		}

		Chunk cnkTagsTitle = new Chunk(" • Tags: ",
				new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(57, 57, 57)));

		int i = 1;
		for(MonitoringPostTag monitoringPostTag : monitoringPostTags) {

			String tagName = monitoringPostTag.getMonitoringTag().getName();

			if(monitoringPostTags.size() > i) {;
				tagName += ", ";
			}

			Chunk cnkMonitoringTag = new Chunk(tagName, 
					new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(57, 57, 57)));

			parPostFooter.add(cnkMonitoringTag);

			i++;
		}
		
		//Terms
		
		Chunk cnkTermsTitle = new Chunk(" • Termos: ",
				new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(57, 57, 57)));

		int j = 1;
		if(monitoringPostTerms != null) {

			for(String monitoringPostTerm : monitoringPostTerms) {

				String termName = monitoringPostTerm;

				if(monitoringPostTerms.size() > j) {;
					termName += ", ";
				}

				Chunk cnkMonitoringTerm = new Chunk(termName, 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(57, 57, 57)));

				parPostFooter.add(cnkMonitoringTerm);

				j++;
			}
		}
		
		//Words
		
		Chunk cnkWordsTitle = new Chunk(" • Palavras: ",
				new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(57, 57, 57)));

		parPostFooter.add(cnkQualificationTitle);
		parPostFooter.add(cnkQualificationValue);
		parPostFooter.add(cnkTagsTitle);
		parPostFooter.add(cnkTermsTitle);
		parPostFooter.add(cnkWordsTitle);

		int k = 1;
		if(monitoringPostWords != null) {
			
			for(String monitoringPostWord : monitoringPostWords.getValue()) {

				String word = monitoringPostWord;

				if(monitoringPostWords.getValue().size() > i) {;
					word += ", ";
				}

				Chunk cnkMonitoringWord = new Chunk(word, 
						new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(57, 57, 57)));

				parPostFooter.add(cnkMonitoringWord);

				k++;
			}
		}
		
		cellPostFooter.addElement(parPostFooter);

		return cellPostFooter;
	}

	private PdfPCell addPostHeader(String networkIcon, String profileName, Date postDate, String postTerm) {

		PdfPCell cellPostHeader = new PdfPCell();

		try {

			cellPostHeader.setBorder(Rectangle.NO_BORDER);
			cellPostHeader.setPaddingTop(0);
			cellPostHeader.setPaddingBottom(1);
			cellPostHeader.setPaddingLeft(0);
			cellPostHeader.setPaddingRight(0);
			cellPostHeader.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			Paragraph parPostHeader = new Paragraph();
			parPostHeader.setLeading(11);

			Image imgNetwork = Image.getInstance(UtilFunctions.getResourcePath(networkIcon));
			imgNetwork.scaleAbsolute(7, 7);

			parPostHeader.add(new Chunk(imgNetwork, 0, 0));

			if(profileName != null) {

				Chunk cnkProfile =  new Chunk(" " + profileName,
						new Font(sourceSansPro, 9, Font.BOLD, new BaseColor(52, 98, 145)));

				parPostHeader.add(cnkProfile);
			}

			Chunk cnkCreatedTime =  new Chunk(" em " + 
					new SimpleDateFormat("dd/MM/yyyy HH:mm").format(postDate) + 
					" (" + postTerm + ")",
					new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(99, 100, 100)));

			parPostHeader.add(cnkCreatedTime);

			cellPostHeader.addElement(parPostHeader);

		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cellPostHeader;
	}

	private void addHeaderFooter() {

		TableHeaderFooter headerFooter = new TableHeaderFooter();

		headerFooter.setFooter("Opsocial - Plataforma de Controle de Mídias Sociais | www.opsocial.com.br");

		pdfWritter.setPageEvent(headerFooter);

	}

	class TableHeaderFooter extends PdfPageEventHelper {

		private PdfWriter writer;

		String footer;

		public void setFooter(String footer) {
			this.footer = footer;
		}

		public void onEndPage(PdfWriter writer, Document document) {
			this.writer = writer;
			try {
				createHeader();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			createFooter();
			createVerticalSeparator();
			createHeaderBorder();
		}

		private void createVerticalSeparator() {

			PdfContentByte under = writer.getDirectContentUnder();
			under.saveState();
			under.setRGBColorFill(178,177,177);
			under.rectangle(document.left() + 261, 40, 1, 705);
			under.fill();
			under.restoreState();

		}
		
		private void createHeaderBorder() {
			
			PdfContentByte under = writer.getDirectContentUnder();
			under.saveState();
			under.setRGBColorFill(178,177,177);
			under.rectangle(document.left(), 750, 525, 1);
			under.fill();
			under.restoreState();
		}

		private void createHeader() throws MalformedURLException, IOException {

			try {

				PdfPTable table = new PdfPTable(2);
				table.setTotalWidth(new float[]{55, 475});
				table.setLockedWidth(true);

				PdfPCell cellLogoHeader = new PdfPCell();
				cellLogoHeader.setBorder(Rectangle.RIGHT);
				cellLogoHeader.setBorderColor(new BaseColor(178, 177, 177));
				cellLogoHeader.setBorderWidth(1);
				cellLogoHeader.setPadding(5);
				cellLogoHeader.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

				Image imgLogo = Image.getInstance(Icons.PDF_OP_LOGO);
				imgLogo.scalePercent(30);

				cellLogoHeader.addElement(imgLogo);

				PdfPCell cellMonitoringHeader = new PdfPCell();
				cellMonitoringHeader.setBorder(Rectangle.NO_BORDER);
				cellMonitoringHeader.setBorderColor(new BaseColor(178, 177, 177));
				cellMonitoringHeader.setBorderWidth(1);
				cellMonitoringHeader.setPaddingTop(2);
				cellMonitoringHeader.setPaddingBottom(5);
				cellMonitoringHeader.setPaddingLeft(5);
				cellMonitoringHeader.setPaddingRight(5);
				
				Paragraph parMonitoringTitle = new Paragraph();
				parMonitoringTitle.setLeading(11);

				parMonitoringTitle.add(new Chunk("Monitoramento:",
						new Font(sourceSansPro, 12, Font.BOLD, new BaseColor(52, 98, 145))));

				parMonitoringTitle.add(new Chunk(" " + monitoringHistoricFilter.getMonitoring().getName(),
						new Font(sourceSansPro, 12, Font.NORMAL, new BaseColor(52, 98, 145))));

				Paragraph parMonitoringPeriod = new Paragraph();
				parMonitoringPeriod.setLeading(9);

				parMonitoringPeriod.add(new Chunk("Período: ", 
						new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(94, 95, 95))));

				parMonitoringPeriod.add(new Chunk(
						new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(monitoringHistoricFilter.getStartDate())) + " a " + 
								new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(monitoringHistoricFilter.getEndDate())), 
								new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(127, 127, 127))));
				
				parMonitoringPeriod.add(new Chunk(" • Redes: ", 
						new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(94, 95, 95))));
				
				String networksInFilter = "";
				
				if(monitoringHistoricFilter.getNetworks().getValue().contains("facebook")) {
					networksInFilter += "Facebook, ";
				}

				if(monitoringHistoricFilter.getNetworks().getValue().contains("twitter")) {
					networksInFilter += "Twitter, ";
				}

				if(monitoringHistoricFilter.getNetworks().getValue().contains("google")) {
					networksInFilter += "Google+, ";
				}

				if(monitoringHistoricFilter.getNetworks().getValue().contains("youtube")) {
					networksInFilter += "Youtube, ";
				}

				if(monitoringHistoricFilter.getNetworks().getValue().contains("instagram")) {
					networksInFilter += "Instagram, ";
				}

				if(monitoringHistoricFilter.getNetworks().getValue().contains("reclameaqui")) {
					networksInFilter += "Reclame Aqui, ";
				}

				if(monitoringHistoricFilter.getNetworks().getValue().contains("news")) {
					networksInFilter += "Notícias, ";
				}
				
				if(monitoringHistoricFilter.getNetworks().getValue().contains("blogs")) {
					networksInFilter += "Blogs, ";
				}
				
				if(!networksInFilter.isEmpty()) {
					
					StringBuilder b = new StringBuilder(networksInFilter);
					b.replace(networksInFilter.lastIndexOf(","), networksInFilter.lastIndexOf(" ") + 1, "");
					
					parMonitoringPeriod.add(new Chunk(b.toString(), 
							new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(127, 127, 127))));
				}
				
				Paragraph parMonitoringQualification = new Paragraph();
				parMonitoringQualification.setLeading(9);
				
				parMonitoringQualification.add(new Chunk("Sentimento: ", 
						new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(94, 95, 95))));
				
				if(monitoringHistoricFilter.getQualification() != null) {
					
					String[] arrayQualification = monitoringHistoricFilter.getQualification().getValue().split(";");
					
					String qualificationName = "";
					
					for(String q : arrayQualification) {
						if(!q.equals("null")) {
							
							char qualification = q.charAt(0);
							
							switch (qualification) {
							case 'P':
								qualificationName += "Positivo, ";
								break;
							case 'S':
								qualificationName += "Neutro, ";
								break;
							case 'N':
								qualificationName += "Negativo, ";
								break;
							default:
								break;
							}
						} else {
							qualificationName += "Não qualificados, ";
						}	
					}
					
					if(!qualificationName.isEmpty()) {
						
						StringBuilder b = new StringBuilder(qualificationName);
						b.replace(qualificationName.lastIndexOf(","), qualificationName.lastIndexOf(" ") + 1, "");
						
						parMonitoringQualification.add(new Chunk(b.toString(), 
								new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(127, 127, 127))));
					}
					
				}
				
				//Tags
				
				Paragraph parMonitoringTags = new Paragraph();
				parMonitoringTags.setLeading(9);

				parMonitoringTags.add(new Chunk("Tags: ",
						new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(94, 95, 95))));

				if(monitoringHistoricFilter.getMonitoringTags() != null 
						&& !monitoringHistoricFilter.getMonitoringTags().isEmpty()) {

					MaintenanceMonitoringTagRemote monitoringTagRemote = (MaintenanceMonitoringTagRemote)
							RecoverMaintenance.recoverMaintenance("MonitoringTag");

					List<MonitoringTag> monitoringTags = new ArrayList<MonitoringTag>();
					for(Long idMonitoringTag : monitoringHistoricFilter.getMonitoringTags()) {
						monitoringTags.add(monitoringTagRemote.getById(idMonitoringTag));
					}

					int i = 1;
					for(MonitoringTag monitoringTag : monitoringTags) {

						String tagName = monitoringTag.getName();

						if(monitoringTags.size() > i) {
							tagName += ", ";
						}

						parMonitoringTags.add(new Chunk(tagName, 
								new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(127, 127, 127))));

						i++;
					}
				}
				
				//Terms
				
				Paragraph parMonitoringTerms = new Paragraph();
				parMonitoringTerms.setLeading(9);

				parMonitoringTerms.add(new Chunk("Termos: ",
						new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(94, 95, 95))));

				if(monitoringHistoricFilter.getMonitoringTerms() != null && 
						!monitoringHistoricFilter.getMonitoringTerms().isEmpty()) {

					int i = 0;
					for(String monitoringTerm : monitoringHistoricFilter.getMonitoringTerms()) {

						String termName = monitoringTerm;
						
						if(monitoringHistoricFilter.getMonitoringTerms().size() > i) {
							termName += ", ";
						}

						parMonitoringTerms.add(new Chunk(termName, 
								new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(127, 127, 127))));

						i++;
					}
				}
				
				//Words
				
				Paragraph parMonitoringWords = new Paragraph();
				parMonitoringWords.setLeading(9);

				parMonitoringWords.add(new Chunk("Palavras: ",
						new Font(sourceSansPro, 8, Font.BOLD, new BaseColor(94, 95, 95))));

				if(monitoringHistoricFilter.getMonitoringWords() != null 
						&& !monitoringHistoricFilter.getMonitoringWords().getValue().isEmpty()) {

					int i = 0;
					
					for(String monitoringWord : monitoringHistoricFilter.getMonitoringWords().getValue()) {

						String word = monitoringWord;

						if(monitoringHistoricFilter.getMonitoringWords().getValue().size() > i) {
							word += ", ";
						}

						parMonitoringWords.add(new Chunk(word, 
								new Font(sourceSansPro, 8, Font.NORMAL, new BaseColor(127, 127, 127))));

						i++;
					}
				}
				
				cellMonitoringHeader.addElement(parMonitoringTitle);
				cellMonitoringHeader.addElement(parMonitoringPeriod);
				cellMonitoringHeader.addElement(parMonitoringQualification);
				cellMonitoringHeader.addElement(parMonitoringTags);
				cellMonitoringHeader.addElement(parMonitoringTerms);
				cellMonitoringHeader.addElement(parMonitoringWords);
				
				table.addCell(cellLogoHeader);
				table.addCell(cellMonitoringHeader);

				table.writeSelectedRows(0, -1, 34, 823, writer.getDirectContent());
			}
			catch(DocumentException de) {
				throw new ExceptionConverter(de);
			}
		}

		private void createFooter() {

			try {
				PdfPTable table = new PdfPTable(2);
				table.setWidths(new int[]{300, 25});
				table.setTotalWidth(527);
				table.setLockedWidth(true);
				table.getDefaultCell().setPaddingTop(10);
				table.getDefaultCell().setBorder(Rectangle.TOP);
				table.getDefaultCell().setBorderColor(new BaseColor(178, 177, 177));
				table.getDefaultCell().setBorderWidth(1);

				Font fontFooter = new Font(sourceSansPro, 9, Font.NORMAL, BASE_COLOR_HEADER_TITLE);
				Phrase phraseFooter = new Phrase(footer, fontFooter);
				Phrase phrasePage = new Phrase(String.valueOf(writer.getPageNumber()), fontFooter);

				table.addCell(phraseFooter);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

				table.addCell(phrasePage);

				table.writeSelectedRows(0, -1, 34, 30, writer.getDirectContent());

			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}

	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
