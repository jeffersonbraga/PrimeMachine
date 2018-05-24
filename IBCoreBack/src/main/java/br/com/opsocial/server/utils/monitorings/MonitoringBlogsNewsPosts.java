package br.com.opsocial.server.utils.monitorings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;
import br.com.opsocial.server.utils.RecoverMaintenance;

import com.buzzilla.webhose.client.WebhoseClient;
import com.buzzilla.webhose.client.WebhosePost;
import com.buzzilla.webhose.client.WebhoseQuery;
import com.buzzilla.webhose.client.WebhoseQuery.SiteType;
import com.buzzilla.webhose.client.WebhoseResponse;

import br.com.opsocial.ejb.das.MaintenanceBlogPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceBlogPostRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringSearchRemote;
import br.com.opsocial.ejb.das.MaintenanceNewsPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceNewsPostRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.idclass.BlogPostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.MonitoringSearchId;
import br.com.opsocial.ejb.entity.application.idclass.NewsPostMonitoringId;
import br.com.opsocial.ejb.entity.blogs.BlogPost;
import br.com.opsocial.ejb.entity.blogs.BlogPostMonitoring;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;
import br.com.opsocial.ejb.entity.monitoring.MonitoringSearch;
import br.com.opsocial.ejb.entity.news.NewsPost;
import br.com.opsocial.ejb.entity.news.NewsPostMonitoring;

public class MonitoringBlogsNewsPosts {
	
	private Monitoring monitoring;
	
	private MaintenanceNewsPostRemote newsPostRemote;
	private MaintenanceNewsPostMonitoringRemote newsPostMonitoringRemote;
	private MaintenanceBlogPostRemote blogPostRemote;
	private MaintenanceBlogPostMonitoringRemote blogPostMonitoringRemote;
	private MaintenanceMonitoringSearchRemote monitoringSearchRemote;
	
	public static final String SITE_TYPE_BLOG = "blogs";
	public static final String SITE_TYPE_NEWS = "news";
	
	public final static Integer LIMIT_PER_CALL = 100;
	public final static Long SEARCH_CYCLE_TIME = 86400L;
	public final static Integer SEARCH_CYCLE_THRESHOLD = 2;
	
	public MonitoringBlogsNewsPosts(Monitoring monitoring) {
		
		this.monitoring = monitoring;
		
		newsPostRemote = (MaintenanceNewsPostRemote) RecoverMaintenance.recoverMaintenance("NewsPost");
		newsPostMonitoringRemote = (MaintenanceNewsPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("NewsPostMonitoring");
		
		blogPostRemote = (MaintenanceBlogPostRemote) RecoverMaintenance.recoverMaintenance("BlogPost");
		blogPostMonitoringRemote = (MaintenanceBlogPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("BlogPostMonitoring");
		
		monitoringSearchRemote = (MaintenanceMonitoringSearchRemote) RecoverMaintenance.recoverMaintenance("MonitoringSearch");
	}

	public void savePostsWebHoseAPI() {
		
		String[] terms = monitoring.getTerms().split(";");
		
		for(String term : terms) {
			
			try {
				
				if(isToSearch(term)) {
					
					MonitoringSearch monitoringSearch = getMonitoringSearch(monitoring.getIdMonitoring(), term);
					
					Long retrievedDate = new Date().getTime() / 1000L;	
					String termForSearch = term.trim();

					Calendar taskDate = Calendar.getInstance(); 
					taskDate.set(Calendar.DAY_OF_MONTH, taskDate.get(Calendar.DAY_OF_MONTH) - 1);
					
					Long searchAfterTS = taskDate.getTimeInMillis();
					
					WebhoseClient client = new WebhoseClient("fcd711bc-aa1a-493a-8552-da75af69c31d");
					
					WebhoseQuery query = new WebhoseQuery();
					query.siteTypes.add(SiteType.blogs);
					query.siteTypes.add(SiteType.news);
					query.language.add("portuguese");
					
					String queryToSend = query.toString() + " AND ((" + termForSearch + ") OR title:(" + termForSearch + "))";
					
					WebhoseResponse webhoseResponse = client.search(queryToSend, searchAfterTS);
					
					Long searchedDate = new Date().getTime() / 1000L;
					
					if(!webhoseResponse.posts.isEmpty()) {
						
						monitoringSearch.setLastDateSearchedWithResults(searchedDate);
						monitoringSearch.setSearchesWithResults(monitoringSearch.getSearchesWithResults() + 1L);
						
						// Save NewsPosts.
						List<NewsPost> newsPosts = getNewsPostFromWebhose(webhoseResponse.posts);
						saveNewsPosts(newsPosts, term, retrievedDate);
						
						// Save BlogsPosts.
						List<BlogPost> blogPosts = getBlogsPostFromWebhose(webhoseResponse.posts);
						saveBlogsPosts(blogPosts, term, retrievedDate);
					}
					
					Long cycles = monitoringSearch.getCycles(); 
					
					if((monitoringSearch.getReturnedPosts() == 0 && monitoringSearch.getReturnedPosts().equals(webhoseResponse.posts.size())) 
							|| monitoringSearch.getReturnedPosts() > 0 && webhoseResponse.posts.size() > 0) {
						cycles++;
					} else {
						cycles = 1L;
					}
					
					monitoringSearch.setCycles(cycles);
					monitoringSearch.setLastDateSearched(searchedDate);
					monitoringSearch.setExpectedPosts(LIMIT_PER_CALL);
					monitoringSearch.setSearches(monitoringSearch.getSearches() + 1L);
					monitoringSearch.setReturnedPosts(webhoseResponse.posts.size());
					
					monitoringSearchRemote.save(monitoringSearch);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void saveNewsPosts(List<NewsPost> newsPosts, String term, Long retrievedDate) {
		
		for(NewsPost newsPost : newsPosts) {

			try {

				if(validateIgnore(newsPost, monitoring)) {

					NewsPost aux = newsPostRemote.getByUrl(newsPost.getUrl());

					if(aux == null) {
						newsPost = newsPostRemote.save(newsPost);
					} else {
						newsPost = aux;
					}

					if(newsPostMonitoringRemote.getByComposedId(monitoring.getIdMonitoring(), newsPost.getIdNewsPost()) == null) {

						NewsPostMonitoringId newsPostMonitoringId = new NewsPostMonitoringId();
						newsPostMonitoringId.setNewsPost(newsPost.getIdNewsPost());
						newsPostMonitoringId.setMonitoring(monitoring.getIdMonitoring());
						newsPostMonitoringId.setTerm(term);

						if(newsPostMonitoringRemote.getById(newsPostMonitoringId) == null) { 

							NewsPostMonitoring newsPostMonitoring = new NewsPostMonitoring();
							newsPostMonitoring.setNewsPost(newsPost);
							newsPostMonitoring.setMonitoring(monitoring);
							newsPostMonitoring.setTerm(term);
							newsPostMonitoring.setRetrievedDate(retrievedDate);
							newsPostMonitoring.setGarbage('F');

							executeAutomaticNeuterQualification(newsPost, newsPostMonitoring, monitoring);
							executeAutomaticNegativeQualification(newsPost, newsPostMonitoring, monitoring);
							executeAutomaticPositiveQualification(newsPost, newsPostMonitoring, monitoring);						

							newsPostMonitoringRemote.save(newsPostMonitoring);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
	}

	private List<NewsPost> getNewsPostFromWebhose(List<WebhosePost> webhosePosts) {
		
		List<NewsPost> newsPosts = new ArrayList<NewsPost>();
		
		for (WebhosePost webhosePost : webhosePosts) {
			
			try {
				
				if(webhosePost.thread.siteType.equals(SITE_TYPE_NEWS)) {
					
					NewsPost newsPost = new NewsPost();
					newsPost.setTitle(webhosePost.title);
					newsPost.setDescription(webhosePost.text);
					
					newsPost.setImageUrl(webhosePost.thread.mainImage);
					newsPost.setCreatedTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(webhosePost.published).getTime() / 1000L);
					newsPost.setPublisher(webhosePost.thread.site);
					newsPost.setPublisherUrl("http://" + webhosePost.thread.siteFull);
					newsPost.setPublisherPicture(newsPost.getPublisherUrl() + "/favicon.ico");
					newsPost.setUrl(webhosePost.url);
					
					newsPosts.add(newsPost);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return newsPosts;
	}
	
	private void saveBlogsPosts(List<BlogPost> blogPosts, String term, Long retrievedDate) {
		
		for(BlogPost blogPost : blogPosts) {

			try {
				
				if(validateIgnore(blogPost, monitoring)) {

					BlogPost aux = blogPostRemote.getByUrl(blogPost.getUrl());

					if(aux == null) {
						blogPost = blogPostRemote.save(blogPost);
					} else {
						blogPost = aux;
					}

					if(blogPostMonitoringRemote.getByComposedId(monitoring.getIdMonitoring(), blogPost.getIdBlogPost()) == null) {

						BlogPostMonitoringId blogPostMonitoringId = new BlogPostMonitoringId();
						blogPostMonitoringId.setBlogPost(blogPost.getIdBlogPost());
						blogPostMonitoringId.setMonitoring(monitoring.getIdMonitoring());
						blogPostMonitoringId.setTerm(term);

						if(blogPostMonitoringRemote.getById(blogPostMonitoringId) == null) { 

							BlogPostMonitoring blogPostMonitoring = new BlogPostMonitoring();
							blogPostMonitoring.setBlogPost(blogPost);
							blogPostMonitoring.setMonitoring(monitoring);
							blogPostMonitoring.setTerm(term);
							blogPostMonitoring.setRetrievedDate(retrievedDate);
							blogPostMonitoring.setGarbage('F');

							executeAutomaticNeuterQualification(blogPost, blogPostMonitoring, monitoring);
							executeAutomaticNegativeQualification(blogPost, blogPostMonitoring, monitoring);
							executeAutomaticPositiveQualification(blogPost, blogPostMonitoring, monitoring);						

							blogPostMonitoringRemote.save(blogPostMonitoring);
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		
	}
	
	private List<BlogPost> getBlogsPostFromWebhose(List<WebhosePost> webhosePosts) {
		
		List<BlogPost> blogPosts = new ArrayList<BlogPost>();
		
		for (WebhosePost webhosePost : webhosePosts) {
			
			try {
				
				if(webhosePost.thread.siteType.equals(SITE_TYPE_BLOG)) {
					
					BlogPost blogPost = new BlogPost();
					
					blogPost.setTitle(webhosePost.title);
					blogPost.setDescription(webhosePost.text);
					
					blogPost.setCreatedTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(webhosePost.published).getTime() / 1000L);	
					blogPost.setPublisher(webhosePost.thread.site);
					blogPost.setPublisherUrl("http://" + webhosePost.thread.siteFull);
					blogPost.setPublisherPicture(blogPost.getPublisherUrl() + "/favicon.ico");
					blogPost.setUrl(webhosePost.url);
					
					blogPosts.add(blogPost);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return blogPosts;
	}
	
	private boolean validateIgnore(NewsPost post, Monitoring monitoring) {
	
		if(monitoring.getIgnoreTerms() == null) {
			return true;
		} else {
			String[] ignoreTerms = monitoring.getIgnoreTerms().split(";");
			
			if(post.getDescription() != null) {
				for(String ignoreTerm : ignoreTerms) {
					if(removeLetterSpecialCharacter(post.getDescription()).toLowerCase()
						.contains(removeLetterSpecialCharacter(ignoreTerm).toLowerCase())) {
						return false;
					}
				}
			}
			
			if(post.getTitle() != null) {
				for(String ignoreTerm : ignoreTerms) {
					if(removeLetterSpecialCharacter(post.getTitle()).toLowerCase()
						.contains(removeLetterSpecialCharacter(ignoreTerm).toLowerCase())) {
						return false;
					}
				}
			}
		}
		
		return true;
	}

	private NewsPostMonitoring executeAutomaticPositiveQualification(NewsPost post, NewsPostMonitoring newsPostMonitoring, Monitoring monitoring) {
		
		if(monitoring.getAutoPositiveTerms() != null) {
			
			String[] terms = monitoring.getAutoPositiveTerms().split(";");
			
			if(post.getDescription() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getDescription()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						newsPostMonitoring.setQualification(PostMonitoringDTO.POSITIVE);
						return newsPostMonitoring;
					}
				}
			}
			
			if(post.getTitle() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getTitle()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						newsPostMonitoring.setQualification(PostMonitoringDTO.POSITIVE);
						return newsPostMonitoring;
					}
				}
			}
		}
		
		return newsPostMonitoring;
	}
	
	private NewsPostMonitoring executeAutomaticNegativeQualification(NewsPost post, NewsPostMonitoring newsPostMonitoring, Monitoring monitoring) {
		
		if(monitoring.getAutoNegativeTerms() != null) {
			
			String[] terms = monitoring.getAutoNegativeTerms().split(";");
			
			if(post.getDescription() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getDescription()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						newsPostMonitoring.setQualification(PostMonitoringDTO.NEGATIVE);
						return newsPostMonitoring;
					}
				}
			}
			
			if(post.getTitle() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getTitle()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						newsPostMonitoring.setQualification(PostMonitoringDTO.NEGATIVE);
						return newsPostMonitoring;
					}
				}
			}
		}
		
		return newsPostMonitoring;
	}
	
	private NewsPostMonitoring executeAutomaticNeuterQualification(NewsPost post, NewsPostMonitoring newsPostMonitoring, Monitoring monitoring) {
	
		if(monitoring.getAutoNeuterTerms() != null) {
			
			String[] terms = monitoring.getAutoNeuterTerms().split(";");
			
			if(post.getDescription() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getDescription()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						newsPostMonitoring.setQualification(PostMonitoringDTO.NEUTER);
						return newsPostMonitoring;
					}
				}
			}
			
			if(post.getTitle() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getTitle()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						newsPostMonitoring.setQualification(PostMonitoringDTO.NEUTER);
						return newsPostMonitoring;
					}
				}
			}
		}
		
		return newsPostMonitoring;
	}
	
	private boolean validateIgnore(BlogPost post, Monitoring monitoring) {
		
		if(monitoring.getIgnoreTerms() == null) {
			return true;
		} else {
			String[] ignoreTerms = monitoring.getIgnoreTerms().split(";");
			
			if(post.getDescription() != null) {
				for(String ignoreTerm : ignoreTerms) {
					if(removeLetterSpecialCharacter(post.getDescription()).toLowerCase()
						.contains(removeLetterSpecialCharacter(ignoreTerm).toLowerCase())) {
						return false;
					}
				}
			}
			
			if(post.getTitle() != null) {
				for(String ignoreTerm : ignoreTerms) {
					if(removeLetterSpecialCharacter(post.getTitle()).toLowerCase()
						.contains(removeLetterSpecialCharacter(ignoreTerm).toLowerCase())) {
						return false;
					}
				}
			}
		}
		
		return true;
	}

	private BlogPostMonitoring executeAutomaticPositiveQualification(BlogPost post, BlogPostMonitoring blogPostMonitoring, Monitoring monitoring) {
		
		if(monitoring.getAutoPositiveTerms() != null) {
			
			String[] terms = monitoring.getAutoPositiveTerms().split(";");
			
			if(post.getDescription() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getDescription()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						blogPostMonitoring.setQualification(PostMonitoringDTO.POSITIVE);
						return blogPostMonitoring;
					}
				}
			}
			
			if(post.getTitle() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getTitle()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						blogPostMonitoring.setQualification(PostMonitoringDTO.POSITIVE);
						return blogPostMonitoring;
					}
				}
			}
		}
		
		return blogPostMonitoring;
	}
	
	private BlogPostMonitoring executeAutomaticNegativeQualification(BlogPost post, BlogPostMonitoring blogPostMonitoring, Monitoring monitoring) {
		
		if(monitoring.getAutoNegativeTerms() != null) {
			
			String[] terms = monitoring.getAutoNegativeTerms().split(";");
			
			if(post.getDescription() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getDescription()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						blogPostMonitoring.setQualification(PostMonitoringDTO.NEGATIVE);
						return blogPostMonitoring;
					}
				}
			}
			
			if(post.getTitle() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getTitle()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						blogPostMonitoring.setQualification(PostMonitoringDTO.NEGATIVE);
						return blogPostMonitoring;
					}
				}
			}
		}
		
		return blogPostMonitoring;
	}
	
	private BlogPostMonitoring executeAutomaticNeuterQualification(BlogPost post, BlogPostMonitoring blogPostMonitoring, Monitoring monitoring) {
	
		if(monitoring.getAutoNeuterTerms() != null) {
			
			String[] terms = monitoring.getAutoNeuterTerms().split(";");
			
			if(post.getDescription() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getDescription()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						blogPostMonitoring.setQualification(PostMonitoringDTO.NEUTER);
						return blogPostMonitoring;
					}
				}
			}
			
			if(post.getTitle() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getTitle()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						blogPostMonitoring.setQualification(PostMonitoringDTO.NEUTER);
						return blogPostMonitoring;
					}
				}
			}
		}
		
		return blogPostMonitoring;
	}
	
	public String removeLetterSpecialCharacter(String value) {
		
		value = value.replaceAll("[ÂÀÁÄÃ]", "A");
		value = value.replaceAll("[âãàáä]", "a");
		value = value.replaceAll("[ÊÈÉË]", "E");
		value = value.replaceAll("[êèéë]", "e");
		value = value.replaceAll("ÎÍÌÏ", "I");
		value = value.replaceAll("îíìï", "i");
		value = value.replaceAll("[ÔÕÒÓÖ]", "O");
		value = value.replaceAll("[ôõòóö]", "o");
		value = value.replaceAll("[ÛÙÚÜ]", "U");
		value = value.replaceAll("[ûúùü]", "u");
		value = value.replaceAll("Ç", "C");
		value = value.replaceAll("ç", "c");
		
		return value;
	}
	
	public MonitoringSearch getMonitoringSearch(Long idMonitoring, String term) {
		
		MonitoringSearchId monitoringSearchId = new MonitoringSearchId();
		monitoringSearchId.setMonitoring(idMonitoring);
		monitoringSearchId.setTerm(term);
		monitoringSearchId.setNetwork(Profile.NEWS);
		
		return monitoringSearchRemote.getById(monitoringSearchId);
	}
	
	/**
	 * 
	 * Método responsável por verificar a relevância da pesquisa do termo e 
	 * decidir se a pesquisa deve ser feita ou não.
	 * 
	 * @param term
	 * @return
	 */
	private Boolean isToSearch(String term) {
		
		Boolean isToSearch = true;
		
		MonitoringSearchId monitoringSearchId = new MonitoringSearchId();
		monitoringSearchId.setMonitoring(monitoring.getIdMonitoring());
		monitoringSearchId.setTerm(term);
		monitoringSearchId.setNetwork(Profile.NEWS);
		
		MonitoringSearch monitoringSearch = monitoringSearchRemote.getById(monitoringSearchId);
		
		if(monitoringSearch == null) {
			
			monitoringSearch = new MonitoringSearch();
			monitoringSearch.setMonitoring(monitoring);
			monitoringSearch.setTerm(term);
			monitoringSearch.setNetwork(Profile.NEWS);
			monitoringSearch.setExpectedPosts(LIMIT_PER_CALL);
			monitoringSearch.setReturnedPosts(-1);
			monitoringSearch.setCycles(0L);
			monitoringSearch.setSearches(0L);
			monitoringSearch.setSearchesWithResults(0L);
			monitoringSearch.setLastDateSearched(new Date().getTime() / 1000L);
			monitoringSearch.setLastDateSearchedWithResults(new Date().getTime() / 1000L);
			
			try {

				monitoringSearchRemote.save(monitoringSearch);

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			
			if(monitoringSearch.getReturnedPosts() < 1 && monitoringSearch.getCycles() > 1L) {
				
				Long nowDate = new Date().getTime() / 1000L;
				
				// Se o intervalo de tempo for menor do que o máximo definido para que uma busca 
				// volte a fazer chamadas(SEARCH_CYCLE_TIME * SEARCH_CYCLE_THRESHOLD), as chamadas serão canceladas até que 
				// o tempo máximo limite sem chamadas seja ultrapassado.
				if((nowDate - monitoringSearch.getLastDateSearched()) < (SEARCH_CYCLE_TIME * SEARCH_CYCLE_THRESHOLD)) {
					isToSearch = false;
				}
				
			}
			
		}
		
		return isToSearch;
	}
	
}
