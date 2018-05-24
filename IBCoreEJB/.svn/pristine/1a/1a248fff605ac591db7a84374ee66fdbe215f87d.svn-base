package br.com.opsocial.ejb.entity.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="samplesreports", schema="opsocial")
public class SampleReport extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	public static final byte START_DATE= 0;
	public static final byte END_DATE = 1;
	public static final byte POSTS_BY_NETWORK = 2;
	public static final byte MOST_VOLUME = 3;
	public static final byte TOTAL_POSTS = 4;
	public static final byte POSTS_BY_HOUR = 5;
	public static final byte POSTS_BY_DAY = 6;
	public static final byte POSTS_BY_DATE = 7;
	public static final byte GREATER_TIME = 8;
	public static final byte GREATER_DAY = 9;
	public static final byte DAILY_AVERAGE = 10;
	
	@Id
	@Column(name = "idsamplereport")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "samplesreports_idsamplereport_seq")
	private Long idSampleReport;
	
	@JoinColumn(name = "idsample", referencedColumnName = "idsample")
	@ManyToOne()
	private Sample sample;

	@Column(name = "property")
	private Byte property;
	
	@Column(name = "key")
	private Integer key;
	
	@Column(name = "value")
	private Long value;
	
	@Column(name = "network")
	private Character network;
	
	
	public SampleReport() {
	}

	public Long getIdSampleReport() {
		return idSampleReport;
	}

	public void setIdSampleReport(Long idSampleReport) {
		this.idSampleReport = idSampleReport;
	}

	public Byte getProperty() {
		return property;
	}

	public void setProperty(Byte property) {
		this.property = property;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
	}

	public Character getNetwork() {
		return network;
	}

	public void setNetwork(Character network) {
		this.network = network;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}
}
