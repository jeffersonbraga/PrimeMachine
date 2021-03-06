package br.com.opsocial.server.actions.upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.server.utils.UtilFunctions;

public class UploadVideoAction {

	private static final long serialVersionUID = 5676663031738075046L;
	
	private static final long SIZE_LIMIT = 1073741824L; // 1GB per video at most.
	private String contentType;

	private final static String VIDEO_AVI = "video/avi";
	private final static String VIDEO_ASF = "video/x-ms-asf";
	private final static String VIDEO_DV = "video/x-dv";
	private final static String VIDEO_MOD = "audio/mod";
	private final static String VIDEO_MOV = "video/quicktime";
	private final static String VIDEO_MP4 = "video/mp4";
	private final static String VIDEO_MPEG = "video/mpeg";
	private final static String VIDEO_XFLV = "video/x-flv";
	private final static String VIDEO_3GP = "video/3gpp";
	private final static String VIDEO_MKV = "video/x-matroska";

    private Path rootLocation;

	public void uploadVideo(@RequestParam("file") MultipartFile file) throws Exception, ServletException, IOException {

		this.rootLocation = Paths.get(OpSocialBackApplication.UPLOAD_DIR_VIDEO_POSTS);
        try {
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
        }
	}

	/*@CrossOrigin
	@RequestMapping(value = "/get_thumnails",
	    method = RequestMethod.POST,
	    produces = MediaType.APPLICATION_JSON_VALUE,
	    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)*/
	public ResponseEntity<List<String>> getVideoThumbnails(@RequestParam("file") String file) throws Exception, ServletException, IOException {

		this.rootLocation = Paths.get(OpSocialBackApplication.UPLOAD_DIR_VIDEO_POSTS);
		List<String> listUrlFiles;
        try {
            listUrlFiles = new VideoThumbnailAction().createListVideoIcon(new File(OpSocialBackApplication.UPLOAD_DIR_IMAGES_POSTS + "\\" + file));
        } catch (Exception e) {
            throw new Exception("Failed to retrieve thumbnail"); 
        }
        return new ResponseEntity<>(listUrlFiles, HttpStatus.OK);
	}

	private Boolean isVideoExtensionAllowed(String contentType) {
		return Arrays.asList(VIDEO_AVI,VIDEO_ASF,VIDEO_DV,VIDEO_MOD,VIDEO_MOV,VIDEO_MP4,VIDEO_MPEG,VIDEO_XFLV,VIDEO_3GP,VIDEO_MKV).contains(contentType);
	}
	
	private String getVideoExtensions() {
		
		StringBuilder builder = new StringBuilder();
		
		for(String extension : Arrays.asList(VIDEO_AVI,VIDEO_ASF,VIDEO_DV,VIDEO_MOD,VIDEO_MOV,VIDEO_MP4,VIDEO_MPEG,VIDEO_XFLV,VIDEO_3GP,VIDEO_MKV)) {
			builder.append(getVideoExtension(extension) + ", ");
		}
		
		builder.replace(builder.lastIndexOf(", "), builder.length(), ".");
		
		return builder.toString();
	}
	
	private String getVideoExtension(String contentType) {
		
		String videoExtension = "";
		
		if(contentType.equals(VIDEO_AVI)) {
			videoExtension = "avi";
		} else if(contentType.equals(VIDEO_ASF)) {
			videoExtension = "asf";
		} else if(contentType.equals(VIDEO_DV)) {
			videoExtension = "dv";
		} else if(contentType.equals(VIDEO_MOD)) {
			videoExtension = "mod";
		} else if(contentType.equals(VIDEO_MOV)) {
			videoExtension = "mov";
		} else if(contentType.equals(VIDEO_MP4)) {
			videoExtension = "mp4";
		} else if(contentType.equals(VIDEO_MPEG)) {
			videoExtension = "mpeg";
		} else if(contentType.equals(VIDEO_XFLV)) {
			videoExtension = "flv";
		} else if(contentType.equals(VIDEO_3GP)) {
			videoExtension = "3gp";
		} else if(contentType.equals(VIDEO_MKV)) {
			videoExtension = "mkv";
		}
		
		return videoExtension;
	}
}
