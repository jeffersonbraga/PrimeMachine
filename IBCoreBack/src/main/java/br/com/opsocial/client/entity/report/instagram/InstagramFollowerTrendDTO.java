package br.com.opsocial.client.entity.report.instagram;

import java.util.Date;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class InstagramFollowerTrendDTO extends Persistent {
	
	private static final long serialVersionUID = 1L;

	private Integer year;
	private Integer month;
	private Integer day;
	private Date date;
	private Long newFollowers;
	private Long unfollowers;
	private Long balanceFollowers;
	
	public InstagramFollowerTrendDTO() {
		
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Long getNewFollowers() {
		return newFollowers;
	}

	public void setNewFollowers(Long newFollowers) {
		this.newFollowers = newFollowers;
	}

	public Long getUnfollowers() {
		return unfollowers;
	}

	public void setUnfollowers(Long unfollowers) {
		this.unfollowers = unfollowers;
	}

	public Long getBalanceFollowers() {
		return balanceFollowers;
	}

	public void setBalanceFollowers(Long balanceFollowers) {
		this.balanceFollowers = balanceFollowers;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
