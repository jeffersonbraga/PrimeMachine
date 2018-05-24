package br.com.opsocial.server.utils.monitorings;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jinstagram.Instagram;
import org.jinstagram.entity.users.basicinfo.Counts;
import org.jinstagram.entity.users.basicinfo.UserInfoData;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;
import br.com.opsocial.server.utils.LanguageIdentifier;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.networksintegrations.InstagramIntegration;
import br.com.opsocial.server.utils.tasks.MonitoringController;
import br.com.opsocial.server.utils.tasks.ProfileTurnController;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringHashTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringInfluentialUserRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringLinkRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringSearchRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringUserRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.idclass.InstagramPostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.MonitoringSearchId;
import br.com.opsocial.ejb.entity.instagram.InstagramPost;
import br.com.opsocial.ejb.entity.instagram.InstagramPostMonitoring;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;
import br.com.opsocial.ejb.entity.monitoring.MonitoringHashTag;
import br.com.opsocial.ejb.entity.monitoring.MonitoringInfluentialUser;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLink;
import br.com.opsocial.ejb.entity.monitoring.MonitoringSearch;
import br.com.opsocial.ejb.entity.monitoring.MonitoringUser;

public class MonitoringInstagram {

	private InstagramIntegration instagramIntegration;
	private Instagram instagram;

	private MaintenanceInstagramPostMonitoringRemote instagramPostMonitoringRemote;

	private MaintenanceMonitoringUserRemote monitoringUserRemote;
	private MaintenanceMonitoringInfluentialUserRemote monitoringInfluentialUserRemote;

	private MaintenanceMonitoringHashTagRemote monitoringHashTagRemote;
	private MaintenanceMonitoringLinkRemote monitoringLinkRemote;

	private MaintenanceMonitoringSearchRemote monitoringSearchRemote;

	private Monitoring monitoring;

	private List<String> femaleNamesSample;
	private List<String> maleNamesSample;

	private Long generateDay;

	public MonitoringInstagram(Monitoring monitoring) {

		this.monitoring = monitoring;
		this.instagramIntegration = new InstagramIntegration();

		this.generateDay = UtilFunctions.getMidnightDateNoDaylightTime(new Date());

		this.monitoringUserRemote = (MaintenanceMonitoringUserRemote) RecoverMaintenance.recoverMaintenance("MonitoringUser");
		this.monitoringInfluentialUserRemote = (MaintenanceMonitoringInfluentialUserRemote) RecoverMaintenance.recoverMaintenance("MonitoringInfluentialUser");

		this.monitoringHashTagRemote = (MaintenanceMonitoringHashTagRemote) RecoverMaintenance.recoverMaintenance("MonitoringHashTag");
		this.monitoringLinkRemote = (MaintenanceMonitoringLinkRemote) RecoverMaintenance.recoverMaintenance("MonitoringLink");

		femaleNamesSample = new ArrayList<String>();
		femaleNamesSample.add("MARIA");
		femaleNamesSample.add("ANA");
		femaleNamesSample.add("FRANCISCA");
		femaleNamesSample.add("ANTONIA");
		femaleNamesSample.add("MÁRCIA");
		femaleNamesSample.add("ADRIANA");
		femaleNamesSample.add("SANDRA");
		femaleNamesSample.add("JOSEFA");
		femaleNamesSample.add("PATRÍCIA");
		femaleNamesSample.add("VERA");
		femaleNamesSample.add("TEREZINHA");
		femaleNamesSample.add("SONIA");
		femaleNamesSample.add("RITA");
		femaleNamesSample.add("LUCIANA");
		femaleNamesSample.add("ROSA");
		femaleNamesSample.add("RAIMUNDA");

		maleNamesSample = new ArrayList<String>();
		maleNamesSample.add("JOSÉ");
		maleNamesSample.add("ANTÔNIO");
		maleNamesSample.add("JOÃO");
		maleNamesSample.add("FRANCISCO");
		maleNamesSample.add("LUIZ");
		maleNamesSample.add("PAULO");
		maleNamesSample.add("CARLOS");
		maleNamesSample.add("MANOEL");
		maleNamesSample.add("PEDRO");
		maleNamesSample.add("MARCOS");
		maleNamesSample.add("RAIMUNDO");
		maleNamesSample.add("SEBASTIÃO");
		maleNamesSample.add("MARCELO");
		maleNamesSample.add("JORGE");
		maleNamesSample.add("GERALDO");
		maleNamesSample.add("LUÍZ");
		maleNamesSample.add("FERNANDO");
		maleNamesSample.add("FÁBIO");
		maleNamesSample.add("ROBERTO");
		maleNamesSample.add("MÁRCIO");
		maleNamesSample.add("EDSON");
		maleNamesSample.add("ANDRÉ");
		maleNamesSample.add("SÉRGIO");
		maleNamesSample.add("DANIEL");
		maleNamesSample.add("RODRIGO");
		maleNamesSample.add("RAFAEL");
		maleNamesSample.add("JOAQUIM");
		maleNamesSample.add("RICARDO");
		maleNamesSample.add("EDUARDO");
		maleNamesSample.add("ALEXANDRE");
		maleNamesSample.add("CLAUDIO");
		maleNamesSample.add("BENEDITO");
		maleNamesSample.add("LEANDRO");
		maleNamesSample.add("MARIO");
	}

	public void saveNewsFeeds(String term) {

		try {

			String termForSearch = term.trim().replace(" or ", "").replace(" and ", "").
												replace(" OR ", "").replace(" AND ", "").
												replace(" ", "").replace("-", "").replace("'", "").replace("\"", "").
											   	replaceAll("#", "");
			
			Long retrievedDate = new Date().getTime() / 1000L;

			Integer newPostsCount = 0;
			LanguageIdentifier languageIdentifier = new LanguageIdentifier();

			Profile profileTurn = ProfileTurnController.INSTAGRAM_PROFILE_TURN;
			
			if(profileTurn != null) {
				instagram = instagramIntegration.getInstagram(profileTurn);
			} else {
				instagram = instagramIntegration.getInstagram();
			}

			List<InstagramPost> instagramPosts = new ArrayList<InstagramPost>();

			try {

				List<MediaFeedData> mediaFeedData = instagram.getRecentMediaTags(termForSearch).getData();

				for (MediaFeedData feed : mediaFeedData) {

					try {

						InstagramPost instagramPost = new InstagramPost();
						instagramPost.setIdMedia(new java.lang.Long(feed.getId().substring(0,feed.getId().indexOf("_"))));
						instagramPost.setCreateTime(feed.getCreatedTime());
						instagramPost.setMediaLink(feed.getLink());
						instagramPost.setIdInstagramUser(Long.valueOf(feed.getUser().getId()));
						instagramPost.setAvatar(feed.getUser().getProfilePictureUrl());
						instagramPost.setUserName(feed.getUser().getUserName());
						instagramPost.setFullName(feed.getUser().getFullName());
						instagramPost.setPictureUrl(feed.getImages().getLowResolution().getImageUrl());
						instagramPost.setPictureWidth(feed.getImages().getLowResolution().getImageWidth());
						instagramPost.setPictureHeight(feed.getImages().getLowResolution().getImageHeight());
						instagramPost.setPictureCaption(feed.getCaption() != null ? feed.getCaption().getText() : null);
						instagramPost.setCountLikes(feed.getLikes().getCount());

						UserInfoData userInfoData = instagram.getUserInfo(feed.getUser().getId()).getData();

						if(userInfoData != null) {
							Counts counts = userInfoData.getCounts();
							instagramPost.setFollowers(counts.getFollowedBy());
							instagramPost.setFollowing(counts.getFollows());
							instagramPost.setMedias(counts.getMedia());
						}

						instagramPost.setTags(feed.getTags());

						instagramPosts.add(instagramPost);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} catch (InstagramException e1) {
				e1.printStackTrace();
			}

			instagramPostMonitoringRemote = (MaintenanceInstagramPostMonitoringRemote) 
					RecoverMaintenance.recoverMaintenance("InstagramPostMonitoring");

			MaintenanceInstagramPostRemote instagramPostRemote = (MaintenanceInstagramPostRemote) 
					RecoverMaintenance.recoverMaintenance("InstagramPost");

			for(InstagramPost instagramPost : instagramPosts) {

				if(validateIgnore(instagramPost)) {

					InstagramPost aux = instagramPostRemote.getById(instagramPost.getIdMedia());

					if(aux == null) {
						try {

							instagramPost = instagramPostRemote.save(instagramPost);

						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						instagramPost = aux;
					}

					if(instagramPostMonitoringRemote.getByComposedId(monitoring.getIdMonitoring(), instagramPost.getIdMedia()) == null) { 

						InstagramPostMonitoringId instagramPostMonitoringId = new InstagramPostMonitoringId();
						instagramPostMonitoringId.setInstagramPost(instagramPost.getIdMedia());
						instagramPostMonitoringId.setMonitoring(monitoring.getIdMonitoring());
						instagramPostMonitoringId.setTerm(term);

						if(instagramPostMonitoringRemote.getById(instagramPostMonitoringId) == null) {

							newPostsCount++;

							InstagramPostMonitoring instagramPostMonitoring = new InstagramPostMonitoring();
							instagramPostMonitoring.setInstagramPost(instagramPost);
							instagramPostMonitoring.setMonitoring(monitoring);
							instagramPostMonitoring.setTerm(term);
							instagramPostMonitoring.setRetrievedDate(retrievedDate);
							instagramPostMonitoring.setCreatedTime(Long.valueOf(instagramPost.getCreateTime()));
							instagramPostMonitoring.setHasHashTags(false);
							instagramPostMonitoring.setHasLinks(false);

							String response = languageIdentifier.recoverLanguage(instagramPost.getPictureCaption());

							if(response == null || response.equals("\"pt\"") || response.isEmpty() || response.equals("[]")) {
								instagramPostMonitoring.setGarbage('F');
							} else {
								instagramPostMonitoring.setGarbage('T');
							}

							executeAutomaticNeuterQualification(instagramPost, instagramPostMonitoring);
							executeAutomaticNegativeQualification(instagramPost, instagramPostMonitoring);
							executeAutomaticPositiveQualification(instagramPost, instagramPostMonitoring);						

							try {

								instagramPostMonitoring = instagramPostMonitoringRemote.save(instagramPostMonitoring);

								if(instagramPostMonitoring.getGarbage().equals('F')) {

									instagramPostMonitoring = saveMonitoringLinks(instagramPostMonitoring);

									if(instagramPost.getTags() != null && !instagramPost.getTags().isEmpty()) {

										saveMonitoringHashTags(instagramPost.getTags());

										instagramPostMonitoring.setHasHashTags(true);
										instagramPostMonitoring = instagramPostMonitoringRemote.merge(instagramPostMonitoring);
									}

									saveMonitoringInfluentialUserStats(instagramPost);
								}

							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
				}
			}

			try {

				MonitoringSearch monitoringSearch = getMonitoringSearch(term);

				if(newPostsCount > 0) {
					monitoringSearch.setLastDateSearchedWithResults(retrievedDate);
					monitoringSearch.setSearchesWithResults(monitoringSearch.getSearchesWithResults() + 1L);
				}

				Long cycles = monitoringSearch.getCycles(); 

				if((monitoringSearch.getReturnedPosts() == 0 && monitoringSearch.getReturnedPosts().equals(newPostsCount)) 
						|| monitoringSearch.getReturnedPosts() > 0 && newPostsCount > 0) {
					cycles++;
				} else {
					cycles = 1L;
				}

				monitoringSearch.setCycles(cycles);
				monitoringSearch.setLastDateSearched(retrievedDate);
				monitoringSearch.setExpectedPosts(MonitoringController.LIMIT_PER_CALL);
				monitoringSearch.setSearches(monitoringSearch.getSearches() + 1L);
				monitoringSearch.setReturnedPosts(newPostsCount);

				monitoringSearchRemote.save(monitoringSearch);

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private boolean validateIgnore(InstagramPost post) {

		if(monitoring.getIgnoreTerms() == null) {
			return true;
		} else {
			String[] ignoreTerms = monitoring.getIgnoreTerms().split(";");

			if(post.getPictureCaption() != null) {
				for(String ignoreTerm : ignoreTerms) {
					if(removeLetterSpecialCharacter(post.getPictureCaption()).toLowerCase()
							.contains(removeLetterSpecialCharacter(ignoreTerm).toLowerCase())) {
						return false;
					}
				}
			}
		}

		return true;
	}

	private InstagramPostMonitoring executeAutomaticPositiveQualification(InstagramPost post, InstagramPostMonitoring instagramPostMonitoring) {

		if(monitoring.getAutoPositiveTerms() != null) {

			String[] terms = monitoring.getAutoPositiveTerms().split(";");

			if(post.getPictureCaption() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getPictureCaption()).toLowerCase()
							.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						instagramPostMonitoring.setQualification(PostMonitoringDTO.POSITIVE);
						return instagramPostMonitoring;
					}
				}
			}
		}

		return instagramPostMonitoring;
	}

	private InstagramPostMonitoring executeAutomaticNegativeQualification(InstagramPost post, InstagramPostMonitoring instagramPostMonitoring) {

		if(monitoring.getAutoNegativeTerms() != null) {

			String[] terms = monitoring.getAutoNegativeTerms().split(";");

			if(post.getPictureCaption() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getPictureCaption()).toLowerCase()
							.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						instagramPostMonitoring.setQualification(PostMonitoringDTO.NEGATIVE);
						return instagramPostMonitoring;
					}
				}
			}
		}

		return instagramPostMonitoring;
	}

	private InstagramPostMonitoring executeAutomaticNeuterQualification(InstagramPost post, InstagramPostMonitoring instagramPostMonitoring) {

		if(monitoring.getAutoNeuterTerms() != null) {

			String[] terms = monitoring.getAutoNeuterTerms().split(";");

			if(post.getPictureCaption() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(post.getPictureCaption()).toLowerCase()
							.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						instagramPostMonitoring.setQualification(PostMonitoringDTO.NEUTER);
						return instagramPostMonitoring;
					}
				}
			}
		}

		return instagramPostMonitoring;
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

	private InstagramPostMonitoring saveMonitoringLinks(InstagramPostMonitoring instagramPostMonitoring) {

		List<URL> links = new ArrayList<URL>();

		try {

			if(instagramPostMonitoring.getInstagramPost().getPictureCaption() != null) {
				links.addAll(UtilFunctions.getHiperLinks(instagramPostMonitoring.getInstagramPost().getPictureCaption()));
			}

			for(URL link : links) {

				try {

					if(monitoringLinkRemote.hasEntity(link.toString(), instagramPostMonitoring.getMonitoring().getIdMonitoring(), 
							Profile.INSTAGRAM, generateDay)) {

						MonitoringLink monitoringLink = monitoringLinkRemote.getEntity(link.toString(), 
								instagramPostMonitoring.getMonitoring().getIdMonitoring(), Profile.INSTAGRAM, generateDay);

						monitoringLink.setLinksCount(monitoringLink.getLinksCount() + 1L);

						try {

							monitoringLinkRemote.save(monitoringLink);

						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {

						MonitoringLink monitoringLink = new MonitoringLink();
						monitoringLink.setLink(link.toString());
						monitoringLink.setMonitoring(monitoring);
						monitoringLink.setNetworkType(Profile.INSTAGRAM);
						monitoringLink.setLinksCount(1L);
						monitoringLink.setDate(generateDay);

						try {

							monitoringLinkRemote.save(monitoringLink);

						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if(!links.isEmpty()) {

				instagramPostMonitoring.setHasLinks(true);
				instagramPostMonitoring = instagramPostMonitoringRemote.merge(instagramPostMonitoring);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return instagramPostMonitoring;
	}

	private void saveMonitoringHashTags(List<String> tags) {

		try {

			for(String tag : tags) {

				try {

					if(monitoringHashTagRemote.hasEntity(tag, monitoring.getIdMonitoring(), 
							Profile.INSTAGRAM, generateDay)) {

						MonitoringHashTag monitoringHashTag = monitoringHashTagRemote.getEntity(tag, monitoring.getIdMonitoring(), 
								Profile.INSTAGRAM, generateDay);

						monitoringHashTag.setHashTagsCount(monitoringHashTag.getHashTagsCount() + 1L);

						try {

							monitoringHashTagRemote.save(monitoringHashTag);

						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {

						MonitoringHashTag monitoringHashTag = new MonitoringHashTag();
						monitoringHashTag.setHashTag(tag);
						monitoringHashTag.setMonitoring(monitoring);
						monitoringHashTag.setNetworkType(Profile.INSTAGRAM);
						monitoringHashTag.setHashTagsCount(1L);
						monitoringHashTag.setDate(generateDay);

						try {

							monitoringHashTagRemote.save(monitoringHashTag);

						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void saveMonitoringInfluentialUserStats(InstagramPost instagramPost) {

		MonitoringInfluentialUser monitoringInfluentialUser;

		try {

			if(monitoringInfluentialUserRemote.hasEntity(String.valueOf(instagramPost.getIdInstagramUser()), 
					monitoring.getIdMonitoring(), Profile.INSTAGRAM, generateDay)) {

				monitoringInfluentialUser = monitoringInfluentialUserRemote.getEntity(
						String.valueOf(instagramPost.getIdInstagramUser()), monitoring.getIdMonitoring(), Profile.INSTAGRAM, generateDay);

				monitoringInfluentialUser.setPostsCount(monitoringInfluentialUser.getPostsCount() + 1L);

				try {

					monitoringInfluentialUserRemote.save(monitoringInfluentialUser);

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {

				try {

					MonitoringUser monitoringUser = new MonitoringUser();
					monitoringUser.setIdUser(String.valueOf(instagramPost.getIdInstagramUser()));
					monitoringUser.setName(instagramPost.getFullName());
					monitoringUser.setNetworkType(Profile.INSTAGRAM);
					monitoringUser.setProfileUrl(UtilFunctions.INSTAGRAM_URL + instagramPost.getUserName());
					monitoringUser.setProfileImageUrl(instagramPost.getAvatar());
					monitoringUser.setSex(discoverGender(instagramPost.getFullName().split(" ")[0].toUpperCase()));

					monitoringUser = monitoringUserRemote.save(monitoringUser);

					monitoringInfluentialUser = new MonitoringInfluentialUser();
					monitoringInfluentialUser.setMonitoring(monitoring);
					monitoringInfluentialUser.setMonitoringUser(monitoringUser);
					monitoringInfluentialUser.setFriends(instagramPost.getFollowing().longValue());
					monitoringInfluentialUser.setFollowers(instagramPost.getFollowers().longValue());
					monitoringInfluentialUser.setPostsCount(1L);
					monitoringInfluentialUser.setDate(generateDay);

					monitoringInfluentialUserRemote.save(monitoringInfluentialUser);

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String discoverGender(String name) {

		String gender = "";

		if(femaleNamesSample.contains(name)) {
			gender = "female";
		} else if (maleNamesSample.contains(name)) {
			gender = "male";
		} else if(name.endsWith("A")) {
			gender = "female";
		}

		return gender;
	}

	private MonitoringSearch getMonitoringSearch(String term) {

		monitoringSearchRemote = (MaintenanceMonitoringSearchRemote) RecoverMaintenance.recoverMaintenance("MonitoringSearch");

		MonitoringSearchId monitoringSearchId = new MonitoringSearchId();
		monitoringSearchId.setMonitoring(monitoring.getIdMonitoring());
		monitoringSearchId.setTerm(term);
		monitoringSearchId.setNetwork(Profile.INSTAGRAM);

		return monitoringSearchRemote.getById(monitoringSearchId);
	}
}

