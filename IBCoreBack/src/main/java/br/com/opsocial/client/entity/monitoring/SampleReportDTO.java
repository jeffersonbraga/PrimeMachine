package br.com.opsocial.client.entity.monitoring;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class SampleReportDTO extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	private Long idSampleReport;
	private Byte property;
	private Integer key;
	private Long value;
	private Character network;
	private SampleDTO sampleDTO;

	public SampleReportDTO() {
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

	public Character getNetwork() {
		return network;
	}

	public void setNetwork(Character network) {
		this.network = network;
	}

	public SampleDTO getSampleDTO() {
		return sampleDTO;
	}

	public void setSampleDTO(SampleDTO sampleDTO) {
		this.sampleDTO = sampleDTO;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}
}
