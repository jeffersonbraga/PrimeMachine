package br.com.opsocial.client.entity.report.facebook;

import java.util.List;
import java.util.Map;

import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.report.AgeGroup;
import br.com.opsocial.ejb.entity.report.FacebookInfluentialUser;
import br.com.opsocial.ejb.entity.report.FanPageGrowth;
import br.com.opsocial.ejb.entity.report.ReportFacebookLocation;
import br.com.opsocial.ejb.entity.report.SumByDate;

public class ReportFacebookHistoricalDTO extends Persistent {

	private static final long serialVersionUID = 1L;
 
	private List<br.com.opsocial.ejb.entity.report.FanPageGrowth> fanPageGrowth;
	private Long fansUntilOneYearAgo;
	
	private br.com.opsocial.ejb.entity.report.FanPageGrowth majorFanPageGrowth;
	private br.com.opsocial.ejb.entity.report.FanPageGrowth minorFanPageGrowth;
	
	private Map<String, Long> aboutYourFans;
	private List<AgeGroup> aboutYourMaleFans;
	private List<AgeGroup> aboutYourFemaleFans;
	private AgeGroup majorAgeGroupFans;
	private Integer womenFansPerc;
	private Integer menFansPerc;
	
	private Map<String, Long> aboutReachedUsers;
	private List<AgeGroup> aboutReachedMaleUsers;
	private List<AgeGroup> aboutReachedFemaleUsers;
	private AgeGroup majorAgeGroupReachedUsers;
	private Integer womenReachedUsersPerc;
	private Integer menReachedUsersPerc;
	
	private List<ReportFacebookLocation> citiesMoreFans;
	private List<ReportFacebookLocation> citiesMoreReached;
	
	private List<FacebookInfluentialUserDTO> mostInfluentialFans;
	
	private List<ReportFacebookLocation> fansLikeSource;
	private List<ReportFacebookLocation> externalReferences;
	
	private List<SumByDate> likesPerMonth;
	private Long totalLikes;
	private Double likesPercentage;
	private List<SumByDate> lovePerMonth;
	private Long totalLove;
	private Double lovePercentage;
	private List<SumByDate> wowPerMonth;
	private Long totalWow;
	private Double wowPercentage;
	private List<SumByDate> hahaPerMonth;
	private Long totalHaha;
	private Double hahaPercentage;
	private List<SumByDate> sorryPerMonth;
	private Long totalSorry;
	private Double sorryPercentage;
	private List<SumByDate> angerPerMonth;
	private Long totalAnger;
	private Double angerPercentage;
	
	private List<SumByDate> commentsPerMonth;
	private Long totalComments;
	private SumByDate majorCommentsMonth;
	
	private List<SumByDate> sharesPerMonth;
	private Long totalShares;
	private SumByDate majorSharesMonth;
	
	private List<SumByDate> clicksPerMonth;
	private Long totalClicks;
	private SumByDate majorClicksMonth;

	public ReportFacebookHistoricalDTO() {
		
	}

	public List<br.com.opsocial.ejb.entity.report.FanPageGrowth> getFanPageGrowth() {
		return fanPageGrowth;
	}

	public void setFanPageGrowth(List<br.com.opsocial.ejb.entity.report.FanPageGrowth> fanPageGrowth) {
		this.fanPageGrowth = fanPageGrowth;
	}

	public Long getFansUntilOneYearAgo() {
		return fansUntilOneYearAgo;
	}

	public void setFansUntilOneYearAgo(Long fansUntilOneYearAgo) {
		this.fansUntilOneYearAgo = fansUntilOneYearAgo;
	}

	public br.com.opsocial.ejb.entity.report.FanPageGrowth getMajorFanPageGrowth() {
		return majorFanPageGrowth;
	}

	public void setMajorFanPageGrowth(br.com.opsocial.ejb.entity.report.FanPageGrowth majorFanPageGrowth) {
		this.majorFanPageGrowth = majorFanPageGrowth;
	}

	public br.com.opsocial.ejb.entity.report.FanPageGrowth getMinorFanPageGrowth() {
		return minorFanPageGrowth;
	}

	public void setMinorFanPageGrowth(br.com.opsocial.ejb.entity.report.FanPageGrowth minorFanPageGrowth) {
		this.minorFanPageGrowth = minorFanPageGrowth;
	}

	public Map<String, Long> getAboutYourFans() {
		return aboutYourFans;
	}

	public void setAboutYourFans(Map<String, Long> aboutYourFans) {
		this.aboutYourFans = aboutYourFans;
	}

	public AgeGroup getMajorAgeGroupFans() {
		return majorAgeGroupFans;
	}

	public void setMajorAgeGroupFans(AgeGroup majorAgeGroupFans) {
		this.majorAgeGroupFans = majorAgeGroupFans;
	}

	public Integer getWomenFansPerc() {
		return womenFansPerc;
	}

	public void setWomenFansPerc(Integer womenFansPerc) {
		this.womenFansPerc = womenFansPerc;
	}

	public Integer getMenFansPerc() {
		return menFansPerc;
	}

	public void setMenFansPerc(Integer menFansPerc) {
		this.menFansPerc = menFansPerc;
	}

	public Map<String, Long> getAboutReachedUsers() {
		return aboutReachedUsers;
	}

	public void setAboutReachedUsers(Map<String, Long> aboutReachedUsers) {
		this.aboutReachedUsers = aboutReachedUsers;
	}

	public List<AgeGroup> getAboutReachedMaleUsers() {
		return aboutReachedMaleUsers;
	}

	public void setAboutReachedMaleUsers(List<AgeGroup> aboutReachedMaleUsers) {
		this.aboutReachedMaleUsers = aboutReachedMaleUsers;
	}

	public List<AgeGroup> getAboutReachedFemaleUsers() {
		return aboutReachedFemaleUsers;
	}

	public void setAboutReachedFemaleUsers(List<AgeGroup> aboutReachedFemaleUsers) {
		this.aboutReachedFemaleUsers = aboutReachedFemaleUsers;
	}

	public Integer getWomenReachedUsersPerc() {
		return womenReachedUsersPerc;
	}

	public void setWomenReachedUsersPerc(Integer womenReachedUsersPerc) {
		this.womenReachedUsersPerc = womenReachedUsersPerc;
	}

	public Integer getMenReachedUsersPerc() {
		return menReachedUsersPerc;
	}

	public void setMenReachedUsersPerc(Integer menReachedUsersPerc) {
		this.menReachedUsersPerc = menReachedUsersPerc;
	}

	public List<AgeGroup> getAboutYourMaleFans() {
		return aboutYourMaleFans;
	}

	public void setAboutYourMaleFans(List<AgeGroup> aboutYourMaleFans) {
		this.aboutYourMaleFans = aboutYourMaleFans;
	}

	public List<AgeGroup> getAboutYourFemaleFans() {
		return aboutYourFemaleFans;
	}

	public void setAboutYourFemaleFans(List<AgeGroup> aboutYourFemaleFans) {
		this.aboutYourFemaleFans = aboutYourFemaleFans;
	}

	public AgeGroup getMajorAgeGroupReachedUsers() {
		return majorAgeGroupReachedUsers;
	}

	public void setMajorAgeGroupReachedUsers(AgeGroup majorAgeGroupReachedUsers) {
		this.majorAgeGroupReachedUsers = majorAgeGroupReachedUsers;
	}

	public List<ReportFacebookLocation> getCitiesMoreFans() {
		return citiesMoreFans;
	}

	public void setCitiesMoreFans(List<ReportFacebookLocation> citiesMoreFans) {
		this.citiesMoreFans = citiesMoreFans;
	}

	public List<ReportFacebookLocation> getCitiesMoreReached() {
		return citiesMoreReached;
	}

	public void setCitiesMoreReached(List<ReportFacebookLocation> citiesMoreReached) {
		this.citiesMoreReached = citiesMoreReached;
	}

	public List<ReportFacebookLocation> getFansLikeSource() {
		return fansLikeSource;
	}

	public void setFansLikeSource(List<ReportFacebookLocation> fansLikeSource) {
		this.fansLikeSource = fansLikeSource;
	}

	public List<ReportFacebookLocation> getExternalReferences() {
		return externalReferences;
	}

	public void setExternalReferences(List<ReportFacebookLocation> externalReferences) {
		this.externalReferences = externalReferences;
	}

	public List<FacebookInfluentialUserDTO> getMostInfluentialFans() {
		return mostInfluentialFans;
	}

	public void setMostInfluentialFans(List<FacebookInfluentialUserDTO> mostInfluentialFans) {
		this.mostInfluentialFans = mostInfluentialFans;
	}

	public List<SumByDate> getLikesPerMonth() {
		return likesPerMonth;
	}

	public void setLikesPerMonth(List<SumByDate> likesPerMonth) {
		this.likesPerMonth = likesPerMonth;
	}

	public Long getTotalLikes() {
		return totalLikes;
	}

	public void setTotalLikes(Long totalLikes) {
		this.totalLikes = totalLikes;
	}

	public Double getLikesPercentage() {
		return likesPercentage;
	}

	public void setLikesPercentage(Double likesPercentage) {
		this.likesPercentage = likesPercentage;
	}

	public List<SumByDate> getLovePerMonth() {
		return lovePerMonth;
	}

	public void setLovePerMonth(List<SumByDate> lovePerMonth) {
		this.lovePerMonth = lovePerMonth;
	}

	public Long getTotalLove() {
		return totalLove;
	}

	public void setTotalLove(Long totalLove) {
		this.totalLove = totalLove;
	}

	public Double getLovePercentage() {
		return lovePercentage;
	}

	public void setLovePercentage(Double lovePercentage) {
		this.lovePercentage = lovePercentage;
	}

	public List<SumByDate> getWowPerMonth() {
		return wowPerMonth;
	}

	public void setWowPerMonth(List<SumByDate> wowPerMonth) {
		this.wowPerMonth = wowPerMonth;
	}

	public Long getTotalWow() {
		return totalWow;
	}

	public void setTotalWow(Long totalWow) {
		this.totalWow = totalWow;
	}

	public Double getWowPercentage() {
		return wowPercentage;
	}

	public void setWowPercentage(Double wowPercentage) {
		this.wowPercentage = wowPercentage;
	}

	public List<SumByDate> getHahaPerMonth() {
		return hahaPerMonth;
	}

	public void setHahaPerMonth(List<SumByDate> hahaPerMonth) {
		this.hahaPerMonth = hahaPerMonth;
	}

	public Long getTotalHaha() {
		return totalHaha;
	}

	public void setTotalHaha(Long totalHaha) {
		this.totalHaha = totalHaha;
	}

	public Double getHahaPercentage() {
		return hahaPercentage;
	}

	public void setHahaPercentage(Double hahaPercentage) {
		this.hahaPercentage = hahaPercentage;
	}

	public List<SumByDate> getSorryPerMonth() {
		return sorryPerMonth;
	}

	public void setSorryPerMonth(List<SumByDate> sorryPerMonth) {
		this.sorryPerMonth = sorryPerMonth;
	}

	public Long getTotalSorry() {
		return totalSorry;
	}

	public void setTotalSorry(Long totalSorry) {
		this.totalSorry = totalSorry;
	}

	public Double getSorryPercentage() {
		return sorryPercentage;
	}

	public void setSorryPercentage(Double sorryPercentage) {
		this.sorryPercentage = sorryPercentage;
	}

	public List<SumByDate> getAngerPerMonth() {
		return angerPerMonth;
	}

	public void setAngerPerMonth(List<SumByDate> angerPerMonth) {
		this.angerPerMonth = angerPerMonth;
	}

	public Long getTotalAnger() {
		return totalAnger;
	}

	public void setTotalAnger(Long totalAnger) {
		this.totalAnger = totalAnger;
	}

	public Double getAngerPercentage() {
		return angerPercentage;
	}

	public void setAngerPercentage(Double angerPercentage) {
		this.angerPercentage = angerPercentage;
	}

	public List<SumByDate> getCommentsPerMonth() {
		return commentsPerMonth;
	}

	public void setCommentsPerMonth(List<SumByDate> commentsPerMonth) {
		this.commentsPerMonth = commentsPerMonth;
	}

	public Long getTotalComments() {
		return totalComments;
	}

	public void setTotalComments(Long totalComments) {
		this.totalComments = totalComments;
	}

	public SumByDate getMajorCommentsMonth() {
		return majorCommentsMonth;
	}

	public void setMajorCommentsMonth(SumByDate majorCommentsMonth) {
		this.majorCommentsMonth = majorCommentsMonth;
	}

	public List<SumByDate> getSharesPerMonth() {
		return sharesPerMonth;
	}

	public void setSharesPerMonth(List<SumByDate> sharesPerMonth) {
		this.sharesPerMonth = sharesPerMonth;
	}

	public Long getTotalShares() {
		return totalShares;
	}

	public void setTotalShares(Long totalShares) {
		this.totalShares = totalShares;
	}

	public SumByDate getMajorSharesMonth() {
		return majorSharesMonth;
	}

	public void setMajorSharesMonth(SumByDate majorSharesMonth) {
		this.majorSharesMonth = majorSharesMonth;
	}

	public List<SumByDate> getClicksPerMonth() {
		return clicksPerMonth;
	}

	public void setClicksPerMonth(List<SumByDate> clicksPerMonth) {
		this.clicksPerMonth = clicksPerMonth;
	}

	public Long getTotalClicks() {
		return totalClicks;
	}

	public void setTotalClicks(Long totalClicks) {
		this.totalClicks = totalClicks;
	}

	public SumByDate getMajorClicksMonth() {
		return majorClicksMonth;
	}

	public void setMajorClicksMonth(SumByDate majorClicksMonth) {
		this.majorClicksMonth = majorClicksMonth;
	}
	
}
