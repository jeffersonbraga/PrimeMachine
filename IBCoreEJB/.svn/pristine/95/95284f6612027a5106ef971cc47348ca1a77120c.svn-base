package br.com.opsocial.ejb.entity.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="timezone", schema="opsocial")
public class Timezone extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idtimezone")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "timezone_idtimezone_seq")
	private Long idTimezone;
	
	@Column(name="usedaylighttime")
	private Integer useDayLightTime;

	@Column(name="value")
	private Float value;

	@Column(name="description")
	private String description;

	@Column(name="name")
	private String name;
	
	public Timezone() {
	}

	public Long getIdTimezone() {
		return idTimezone;
	}

	public void setIdTimezone(Long idTimezone) {
		this.idTimezone = idTimezone;
	}

	public Integer getUseDayLightTime() {
		return useDayLightTime;
	}

	public void setUseDayLightTime(Integer useDayLightTime) {
		this.useDayLightTime = useDayLightTime;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Long getId() {
		return idTimezone;
	}
	
	@Override
	public void setId(Long id) {
		idTimezone = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}