package br.com.opsocial.client.entity.report.facebook;

import java.util.List;
import java.util.Map;

import br.com.opsocial.client.entity.primitive.Date;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;

public class ReportResumeFacebookDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final Integer FIFTEEN = 15;
	public static final Integer THIRTY = 30;
	public static final Integer FOURTY_FIVE = 45;
	public static final Integer SIXTY = 60;
	
	private Date dateFrom;
	private Date dateUntil;
	
	private Map<String, Long> sumsByPropertyNew;
	private Map<String, Long> sumsByPropertyOld;
	
	//Resume of Facebook
	private Integer percCountLikesOfPages;
	
	private Integer percCountPageViews;
	
	private Integer percCountReachUsersByPages;
	
	//Facebook demography
	private Integer percFemalePageFans;
	private Integer percMalePageFans;
	private Double percPageFans13Among17;
	private Double percPageFans18Among24;
	private Double percPageFans25Among34;
	private Double percPageFans35Among44;
	private Double percPageFans45Among54;
	private Double percPageFans55Among64;
	private Double percPageFans65Plus;
	
	private List<Profile> facePagesDTO;
	
	public ReportResumeFacebookDTO() {
	}

	/**
	 * @return the dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return the dateUntil
	 */
	public Date getDateUntil() {
		return dateUntil;
	}

	/**
	 * @param dateUntil the dateUntil to set
	 */
	public void setDateUntil(Date dateUntil) {
		this.dateUntil = dateUntil;
	}

	public Map<String, Long> getSumsByPropertyNew() {
		return sumsByPropertyNew;
	}

	public void setSumsByPropertyNew(Map<String, Long> sumsByPropertyNew) {
		this.sumsByPropertyNew = sumsByPropertyNew;
	}

	public Map<String, Long> getSumsByPropertyOld() {
		return sumsByPropertyOld;
	}

	public void setSumsByPropertyOld(Map<String, Long> sumsByPropertyOld) {
		this.sumsByPropertyOld = sumsByPropertyOld;
	}

	/**
	 * @return the percCountLikesOfPages
	 */
	public Integer getPercCountLikesOfPages() {
		return percCountLikesOfPages;
	}

	/**
	 * @param percCountLikesOfPages the percCountLikesOfPages to set
	 */
	public void setPercCountLikesOfPages(Integer percCountLikesOfPages) {
		this.percCountLikesOfPages = percCountLikesOfPages;
	}

	public Integer getPercCountPageViews() {
		return percCountPageViews;
	}

	public void setPercCountPageViews(Integer percCountPageViews) {
		this.percCountPageViews = percCountPageViews;
	}

	/**
	 * @return the percCountReachUsersByPages
	 */
	public Integer getPercCountReachUsersByPages() {
		return percCountReachUsersByPages;
	}

	/**
	 * @param percCountReachUsersByPages the percCountReachUsersByPages to set
	 */
	public void setPercCountReachUsersByPages(Integer percCountReachUsersByPages) {
		this.percCountReachUsersByPages = percCountReachUsersByPages;
	}

	/**
	 * @return the percFemalePageFans
	 */
	public Integer getPercFemalePageFans() {
		return percFemalePageFans;
	}

	/**
	 * @param percFemalePageFans the percFemalePageFans to set
	 */
	public void setPercFemalePageFans(Integer percFemalePageFans) {
		this.percFemalePageFans = percFemalePageFans;
	}

	/**
	 * @return the percMalePageFans
	 */
	public Integer getPercMalePageFans() {
		return percMalePageFans;
	}

	/**
	 * @param percMalePageFans the percMalePageFans to set
	 */
	public void setPercMalePageFans(Integer percMalePageFans) {
		this.percMalePageFans = percMalePageFans;
	}

	/**
	 * @return the percPageFans13Among17
	 */
	public Double getPercPageFans13Among17() {
		return percPageFans13Among17;
	}

	/**
	 * @param percPageFans13Among17 the percPageFans13Among17 to set
	 */
	public void setPercPageFans13Among17(Double percPageFans13Among17) {
		this.percPageFans13Among17 = percPageFans13Among17;
	}

	/**
	 * @return the percPageFans18Among24
	 */
	public Double getPercPageFans18Among24() {
		return percPageFans18Among24;
	}

	/**
	 * @param percPageFans18Among24 the percPageFans18Among24 to set
	 */
	public void setPercPageFans18Among24(Double percPageFans18Among24) {
		this.percPageFans18Among24 = percPageFans18Among24;
	}

	/**
	 * @return the percPageFans25Among34
	 */
	public Double getPercPageFans25Among34() {
		return percPageFans25Among34;
	}

	/**
	 * @param percPageFans25Among34 the percPageFans25Among34 to set
	 */
	public void setPercPageFans25Among34(Double percPageFans25Among34) {
		this.percPageFans25Among34 = percPageFans25Among34;
	}

	/**
	 * @return the percPageFans35Among44
	 */
	public Double getPercPageFans35Among44() {
		return percPageFans35Among44;
	}

	/**
	 * @param percPageFans35Among44 the percPageFans35Among44 to set
	 */
	public void setPercPageFans35Among44(Double percPageFans35Among44) {
		this.percPageFans35Among44 = percPageFans35Among44;
	}

	/**
	 * @return the percPageFans45Among54
	 */
	public Double getPercPageFans45Among54() {
		return percPageFans45Among54;
	}

	/**
	 * @param percPageFans45Among54 the percPageFans45Among54 to set
	 */
	public void setPercPageFans45Among54(Double percPageFans45Among54) {
		this.percPageFans45Among54 = percPageFans45Among54;
	}

	/**
	 * @return the percPageFans55Among64
	 */
	public Double getPercPageFans55Among64() {
		return percPageFans55Among64;
	}

	/**
	 * @param percPageFans55Among64 the percPageFans55Among64 to set
	 */
	public void setPercPageFans55Among64(Double percPageFans55Among64) {
		this.percPageFans55Among64 = percPageFans55Among64;
	}

	/**
	 * @return the percPageFans65Plus
	 */
	public Double getPercPageFans65Plus() {
		return percPageFans65Plus;
	}

	/**
	 * @param percPageFans65Plus the percPageFans65Plus to set
	 */
	public void setPercPageFans65Plus(Double percPageFans65Plus) {
		this.percPageFans65Plus = percPageFans65Plus;
	}

	/**
	 * @return the facePagesDTO
	 */
	public List<Profile> getFacePagesDTO() {
		return facePagesDTO;
	}

	/**
	 * @param facePagesDTO the facePagesDTO to set
	 */
	public void setFacePagesDTO(List<Profile> facePagesDTO) {
		this.facePagesDTO = facePagesDTO;
	}
	
}
