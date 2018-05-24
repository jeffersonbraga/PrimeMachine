package br.com.opsocial.ejb.entity.application;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="internalanalytics", schema="opsocial")
public class InternalAnalytic extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final String REPORTS_MODULE = "reports";
	public static final String CHANNELS_MODULE = "channels";
	
	public static final String GEN_FACEBOOK_OVERVIEW_WEB = "gen_facebook_overview_web";
	public static final String GEN_FACEBOOK_OVERVIEW_XLS = "gen_facebook_overview_xls";
	public static final String GEN_FACEBOOK_CONTENT_WEB = "gen_facebook_content_web";
	public static final String GEN_FACEBOOK_CONTENT_XLS = "gen_facebook_content_xls";
	public static final String GEN_FACEBOOK_POST_TO_POST_WEB = "gen_facebook_post_to_post_web";
	public static final String GEN_FACEBOOK_POST_TO_POST_XLS = "gen_facebook_post_to_post_xls";
	public static final String GEN_FACEBOOK_HISTORICAL_WEB = "gen_facebook_historical_web";
	public static final String GEN_FACEBOOK_HISTORICAL_XLS = "gen_facebook_historical_xls";
	
	public static final String GEN_INSTAGRAM_OVERVIEW_WEB = "gen_instagram_overview_web";
	public static final String GEN_INSTAGRAM_OVERVIEW_XLS = "gen_instagram_overview_xls";
	public static final String GEN_INSTAGRAM_HISTORICAL_WEB = "gen_instagram_historical_web";
	public static final String GEN_INSTAGRAM_HISTORICAL_XLS = "gen_instagram_historical_xls";
	
	public static final String GEN_TWITTER_OVERVIEW_WEB = "gen_twitter_overview_web";
	public static final String GEN_TWITTER_OVERVIEW_XLS = "gen_twitter_overview_xls";
	
	public static final String GEN_LINKEDIN_OVERVIEW_WEB = "gen_linkedin_overview_web";
	public static final String GEN_LINKEDIN_OVERVIEW_XLS = "gen_linkedin_overview_xls";
	
	public static final String ADD_INSTAGRAM_CHANNEL = "add_instagram_channel";
	public static final String ADD_FACEBOOK_PROFILE_CHANNEL = "add_facebook_profile_channel";
	public static final String ADD_FACEBOOK_PAGE_CHANNEL = "add_facebook_page_channel";
	public static final String ADD_TWITTER_CHANNEL = "add_twitter_channel";
	public static final String ADD_LINKEDIN_PROFILE_CHANNEL = "add_linkedin_profile_channel";
	public static final String ADD_LINKEDIN_PAGE_CHANNEL = "add_linkedin_page_channel";
	
	public static final String ADD_INSTAGRAM_SCHEDULING_OK = "add_instagram_scheduling_ok";
	public static final String EDIT_INSTAGRAM_SCHEDULING_OK = "edit_instagram_scheduling_ok";
	public static final String ADD_INSTAGRAM_SCHEDULING_NOT_OK = "add_instagram_scheduling_not_ok";
	public static final String EDIT_INSTAGRAM_SCHEDULING_NOT_OK = "edit_instagram_scheduling_not_ok";
	
	@Id
	@Column(name = "idinternalanalytic")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "internalanalytics_idinternalanalytic_seq")
	private Long idInternalAnalytic;
	
    @Column(name = "module")
	private String module;
    
    @Column(name = "action")
    private String action;
    
    @Column(name="actiondate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionDate;
    
    @JoinColumn(name = "idaccount", referencedColumnName = "idaccount")
	@ManyToOne()
	private Account account;
    
    public InternalAnalytic() {
	}

	public Long getIdInternalAnalytic() {
		return idInternalAnalytic;
	}

	public void setIdInternalAnalytic(Long idInternalAnalytic) {
		this.idInternalAnalytic = idInternalAnalytic;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	@Override
	public Long getId() {		
		return idInternalAnalytic;
	}

	@Override
	public void setId(Long id) {
		this.idInternalAnalytic = id;		
	}
}	
