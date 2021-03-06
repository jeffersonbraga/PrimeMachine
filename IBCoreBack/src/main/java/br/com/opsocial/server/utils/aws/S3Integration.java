package br.com.opsocial.server.utils.aws;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class S3Integration {

	// Amazon S3 credentials
    public static final String S3_ACCESS_KEY = "AKIAIAPSKIZUXV2HKTGA";
    public static final String S3_SECRET_KEY = "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";
    
    // Amazon S3 buckets and folders
    public static final String WOOPSOCIAL_BUCKET_NAME = "woopsocial.com";
    
    public static final String WOOPSOCIAL_IMAGES_POSTS_FOLDER = "images/posts/";
    public static final String WOOPSOCIAL_VIDEOS_POSTS_FOLDER = "videos/posts/";
    public static final String WOOPSOCIAL_IMAGES_THUMBNAILS = "images/thumbnails/";
    public static final String WOOPSOCIAL_AVATAR_FOLDER = "images/avatar/";
    public static final String WOOPSOCIAL_COMMENTS_FOLDER = "images/comments/";
    
    
    public static final String WOOPSOCIAL_REPORTS_XLS = "reports/xls/";
	
    public S3Integration() {
	}
    
    public AmazonS3 getAmazonS3Client() {
    	
    	AWSCredentials credentials = new BasicAWSCredentials(S3_ACCESS_KEY, S3_SECRET_KEY);
	
    	AmazonS3 s3client = AmazonS3ClientBuilder
		  .standard()
		  .withCredentials(new AWSStaticCredentialsProvider(credentials))
		  .withRegion(Regions.US_EAST_1)
		  .build();
    	
    	return s3client;
    }
    
    public Boolean downloadS3Object(String bucketName, String folder, String fileName, String pathToSave) {
    	
    	Boolean downloaded = false;
    	
    	try {
    		
    		File file = new File(pathToSave, fileName);
    		
    		if(!file.exists()) {
    			
    			AmazonS3 s3Client = new S3Integration().getAmazonS3Client();
    			
    			S3Object s3object = s3Client.getObject(bucketName, folder + fileName);
    			S3ObjectInputStream inputStream = s3object.getObjectContent();
    			FileUtils.copyInputStreamToFile(inputStream, file);
    			
    			System.out.println("Download File: " + folder + fileName);
    			
    			downloaded = true;
    		} else {
    			downloaded = true;
    		}
			
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return downloaded;
    }
    
}
