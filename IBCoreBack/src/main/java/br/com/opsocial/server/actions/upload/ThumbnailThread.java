package br.com.opsocial.server.actions.upload;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ThumbnailThread {

	private String videoFileSource;

	private String thumbnailFile;

	private String previewThumbnailFile;

	private String timeOfThumbnail;

	public ThumbnailThread(String vdFileSource, String time, String previewTFile, String thumbFile) {
		this.videoFileSource = vdFileSource;
		this.timeOfThumbnail = time;
		this.previewThumbnailFile = previewTFile;
		this.thumbnailFile = thumbFile;
	}

	public void run() {

		try {
			String command = "ffmpeg -i " + videoFileSource + " -f mjpeg -ss " + timeOfThumbnail + " -vframes 1 " + thumbnailFile;
			Process p = Runtime.getRuntime().exec(command);
			//p.waitFor();
			p.getInputStream().close();

			BufferedImage imgThumbnail = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
			imgThumbnail.createGraphics().drawImage(ImageIO.read(new File(thumbnailFile)).getScaledInstance(100, 100, Image.SCALE_SMOOTH),0,0,null);
			ImageIO.write(imgThumbnail, "jpg", new File(previewThumbnailFile));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}