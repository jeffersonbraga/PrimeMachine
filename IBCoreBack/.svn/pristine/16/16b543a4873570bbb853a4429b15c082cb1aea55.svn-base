package br.com.opsocial.server.utils.tasks;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.networksintegrations.TwitterIntegration;
import br.com.opsocial.ejb.das.MaintenanceTrendingTopicRemote;
import br.com.opsocial.ejb.entity.twitter.TrendingTopic;

public class TwitterTrendingTopicsController {
	
	private Timer timer;
	
	public static final Long REFRESH_TIME = 300000L; // Tempo(5 minutos) que o Twitter guarda a informação em cache. 
	public static final Integer WOEID_PT_BR = 23424768; // Parâmetro é o WOEID brasileiro
	
	public TwitterTrendingTopicsController() {
		timer = new Timer();
		timer.schedule(new Task(), 0, REFRESH_TIME);
	}
	
	class Task extends TimerTask {

		@Override
		public void run() {
			
			MaintenanceTrendingTopicRemote trendingTopicRemote = (MaintenanceTrendingTopicRemote) 
					RecoverMaintenance.recoverMaintenance("TrendingTopic");
			
			TwitterIntegration twitterIntegration = new TwitterIntegration();
			
			try {
				Twitter twitter = twitterIntegration.getTwitterApp();
				
				Trends trends = twitter.getPlaceTrends(WOEID_PT_BR);
				
				int position = 1;
				for(Trend trend : trends.getTrends()) {
					
					TrendingTopic trendingTopic = new TrendingTopic();
					trendingTopic.setName(trend.getName());
					trendingTopic.setQuery(trend.getQuery());
					trendingTopic.setUrl(trend.getURL());
					trendingTopic.setTrendingDate(new Date().getTime() / 1000L);
					trendingTopic.setPosition(position);
					
					try {
						
						trendingTopicRemote.save(trendingTopic);
						
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					position++;
				}
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
		
	}

}
