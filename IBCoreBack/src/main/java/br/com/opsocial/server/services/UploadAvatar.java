package br.com.opsocial.server.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.aws.S3Integration;

@RestController
@RequestMapping("woopsocial")
public class UploadAvatar extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Path rootLocation;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@CrossOrigin 
	@RequestMapping(value = "/upload_avatar",
	method = RequestMethod.POST,
	produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	protected ResponseEntity<String> doPost(@RequestParam("file") MultipartFile file) throws Exception, ServletException, IOException {
		
		this.rootLocation = Paths.get(OpSocialBackApplication.UPLOAD_DIR_AVATAR);
		
		try {
            
			if (file.isEmpty()) {
                throw new Exception("Failed to store empty file " + file.getOriginalFilename());
            }
            
			String fileName = RandomStringUtils.randomAlphanumeric(12) + "." + UtilFunctions.getImageExtension(file.getContentType());
			
            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));

    		new S3Integration().getAmazonS3Client().putObject(
    				  S3Integration.WOOPSOCIAL_BUCKET_NAME, 
    				  S3Integration.WOOPSOCIAL_AVATAR_FOLDER + fileName, 
    				  new File(this.rootLocation.resolve(fileName).toString()));
            
            return new ResponseEntity<>("[\""+fileName+"\"]", HttpStatus.OK);
        } catch (IOException e) {
            throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which" +
            		" means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            throw new Exception("Failed to store file " + file.getOriginalFilename(), ase);
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means"+
            		" the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
            throw new Exception("Failed to store file " + file.getOriginalFilename(), ace);
        }
	}
}