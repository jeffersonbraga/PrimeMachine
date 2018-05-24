package br.com.opsocial.ejb.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.application.Anniversarie;

@Local
public interface AnniversarieDAO extends AbstractDAO<Anniversarie> {
	
	public List<Anniversarie> listByDate(Date initDate, Date endDate);
}