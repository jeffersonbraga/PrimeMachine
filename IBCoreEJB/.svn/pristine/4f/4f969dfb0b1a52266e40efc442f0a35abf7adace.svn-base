package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;
import javax.persistence.OptimisticLockException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.idclass.ReportLogoId;
import br.com.opsocial.ejb.entity.report.ReportLogo;

@Stateless
public class ReportLogoDAOImpl extends AbstractDAOImpl<ReportLogo> implements ReportLogoDAO {

	@Override
	public ReportLogo save(ReportLogo reportLogo) throws Exception {
		
		try {
			
			ReportLogoId reportLogoId = new ReportLogoId();
			reportLogoId.setObjectId(reportLogo.getObjectId());
			reportLogoId.setType(reportLogo.getType());
			
			ReportLogo objTmp = getById(reportLogoId);

			if(objTmp != null) {

				try {
					validateVersion(objTmp, reportLogo);
				} catch (IllegalStateException ex) {
					throw new OptimisticLockException();
				}

				em.merge(reportLogo);

				this.flush();

			} else {

				em.persist(reportLogo);

				this.flush();
			}

		} catch (Exception e) {
			throw new Exception(e);
		}

		return reportLogo;	
	}
	
	@Override
	public void delete(ReportLogo reportLogo) throws IllegalArgumentException, IllegalStateException, Exception {
		
		ReportLogoId reportLogoId = new ReportLogoId();
		reportLogoId.setObjectId(reportLogo.getObjectId());
		reportLogoId.setType(reportLogo.getType());
		
		try {
			
			ReportLogo objTmp = getById(reportLogoId);
			
			try {
				validateVersion(objTmp, reportLogo);
			} catch (IllegalStateException ex) {
				throw new Exception(ex.getMessage(), ex);
			}
			
			try {
				
				reportLogo = em.merge(reportLogo);
				em.remove(reportLogo);
				
				this.flush();
					
			} catch (IllegalStateException ex) {				
				throw new Exception(ex.getMessage(), ex);
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}
	
}
