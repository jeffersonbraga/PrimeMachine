package br.com.opsocial.server.utils.pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class ProgressBar {
	
	private PdfWriter writer;
	private Integer progressBarWidth;
	private Integer progressBarPercent;
	private BaseColor progressBarColor;
	private Chunk chunkProgressBar;

	public ProgressBar(PdfWriter writer, Integer progressBarWidth, Integer progressBarPercent, BaseColor progressBarColor) {
		
		this.writer = writer;
		
		this.progressBarWidth = progressBarWidth;
		this.progressBarPercent = progressBarPercent;
		this.progressBarColor = progressBarColor;
	}
	
	public Chunk createProgressBar() {
		
		try {
			
			PdfContentByte cb = writer.getDirectContent();
			
			PdfTemplate template = cb.createTemplate((progressBarWidth * progressBarPercent) / 100, 6f);
			template.setLineWidth(0.5f);
			template.rectangle(0, 0, (progressBarWidth * progressBarPercent) / 100, 6f);
			template.setColorFill(progressBarColor);
			template.fill();
			
			Image img = Image.getInstance(template);        
			chunkProgressBar = new Chunk(img, 1f, 1f);
			
		} catch (BadElementException e) {
			e.printStackTrace();
		}
		
		return chunkProgressBar;
	}
	
}
