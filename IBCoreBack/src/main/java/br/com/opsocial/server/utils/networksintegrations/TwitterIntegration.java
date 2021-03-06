package br.com.opsocial.server.utils.networksintegrations;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterIntegration {
	
	// OpSocial Production
	public static final String CONSUMER_KEY = "yr9j6dXBCdkvyFk4fet8cA";
	public static final String CONSUMER_SECRET = "6h2pO2WKRjQAc2X9j6oTTdBPVwOiBLoNLdHUodfjtNY";
	public static final String BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAAMPKFwAAAAAAXir1JwVEn6sz0Ad8gX5eGax/zTU=4T270JXWIoGlUFPwiDEaMgHecN8OCX7ZlhGERyFNkk";
	public static final String ACCESS_TOKEN = "474405937-U4wqoJCgjaLa2xV9AeZ2EikUm2JT7aBrp5lGfGUA";
	public static final String ACCESS_TOKEN_SECRET = "IdEr4x7Tfc9tQK3K9XbuaXNnH7d5ptoHqJtGZzLDY";
	public static final String TWITPIC_API_KEY = "e9aaa5a6085273957152bc82a6179419";
	
	// WoopSocial Production
//	public static final String CONSUMER_KEY = "NmnfLYQHUk1AexwdmCIomCaZV";
//	public static final String CONSUMER_SECRET = "1gZ7Mb02Pzxnb9r5C8tXIWZCQPHnrg6SaX2PF0Bpz86Aw1jjw5";
//	public static final String BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAAPG22gAAAAAAKt3xF7gTL5B%2FXLVkZ5of5A6hHlk%3DA3VfmOwMeLfphYwarW30IR13nvyTv3lqwHozuWs4ApbTd6Vnjg";
//	public static final String ACCESS_TOKEN = "876859095868952578-aABLKb6ltBozRFt3CI5hocuo1lEebqQ";
//	public static final String ACCESS_TOKEN_SECRET = "YZyQL9hlBnTtephqoZlS2sFPTmyZp5ShtNX2r4kqjvvMl";
//	public static final String TWITPIC_API_KEY = "e9aaa5a6085273957152bc82a6179419"; // Verificar se irá funcionar (mesma chave do OpSocial)
	
	public TwitterIntegration() {
	}
	
	public Twitter getTwitterApp() throws TwitterException {
		
		ConfigurationBuilder configurationBuilder = getConfigurationBuilder();
		configurationBuilder.setApplicationOnlyAuthEnabled(true);
		
		TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
		Twitter twitter = twitterFactory.getInstance();
		twitter.setOAuth2Token(new OAuth2Token("bearer", BEARER_TOKEN));
		
		return twitter;
	}
	
	public Twitter getTwitter(String token, String tokenScret) {
		
		ConfigurationBuilder configurationBuilder = getConfigurationBuilder();
		
		configurationBuilder.setOAuthAccessToken(token);
		configurationBuilder.setOAuthAccessTokenSecret(tokenScret);
		
		TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
		Twitter twitter = twitterFactory.getInstance(); 
		
		return twitter;
	}
	
	public Twitter getSystemTwitter() {
		
		TwitterFactory twitterFactory = new TwitterFactory(getConfigurationBuilder().build());
		Twitter twitter = twitterFactory.getInstance();
		
		return twitter;
	}
	
	public ConfigurationBuilder getConfigurationBuilder() {
		
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setDebugEnabled(true)
		  .setOAuthConsumerKey(CONSUMER_KEY)
		  .setOAuthConsumerSecret(CONSUMER_SECRET);
		
		return configurationBuilder;
	}
	
	public Configuration getConfigurationBuilderForMedia(String token, String tokenSecret) {
		
		Configuration configurationBuilder = new ConfigurationBuilder()
		 	.setMediaProviderAPIKey(TWITPIC_API_KEY)
		 	.setOAuthAccessToken(token)
		 	.setOAuthAccessTokenSecret(tokenSecret)
		 	.setOAuthConsumerKey(CONSUMER_KEY)
		 	.setOAuthConsumerSecret(CONSUMER_SECRET)
		  	.build();
		
		return configurationBuilder;
	}
}
