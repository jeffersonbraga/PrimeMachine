package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;

@Remote
public interface MaintenanceExceptionRemote extends AbstractDAS<br.com.opsocial.ejb.entity.application.Exception> {

}
