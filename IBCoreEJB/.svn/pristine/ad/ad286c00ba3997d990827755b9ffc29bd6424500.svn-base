package br.com.opsocial.ejb.entity.generic;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntityImpl implements AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	@Version
    @Column(nullable = false)
    private Long version;
	    
    @Override
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

	public Boolean isActive() {
		return true;
	}
	
	@Override
    public Boolean isPersistent() {
        return (getId() != null);
    }	
}
