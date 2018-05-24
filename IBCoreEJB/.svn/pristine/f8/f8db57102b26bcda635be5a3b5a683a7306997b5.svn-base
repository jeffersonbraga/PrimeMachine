package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.SampleReport;

@Stateless
public class SampleReportDAOImpl extends AbstractDAOImpl<SampleReport> implements SampleReportDAO {

	@Override
	public List<Object[]> getPostsPerHoursNetworks(Long idSample) {
		sql = "select key,network,value from opsocial.samplesreports where idsample=" + idSample + " and property=" + SampleReport.POSTS_BY_HOUR + " group by key,network,value order by key";
		query = em.createNativeQuery(sql);
		return query.getResultList();

	}
	
	@Override
	public List<Object[]> getPostsPerWeekNetworks(Long idSample) {
		sql = "select key,network,value from opsocial.samplesreports where idsample=" + idSample + " and property=" + SampleReport.POSTS_BY_DAY + " group by key,network,value order by key";
		query = em.createNativeQuery(sql);
		return query.getResultList();

	}
	
	@Override
	public List<Object[]> getPostsByDate(Long idSample) {
		sql = "select key,value from opsocial.samplesreports where idsample=" + idSample + " and property=" + SampleReport.POSTS_BY_DATE + " group by key,value order by key";
		query = em.createNativeQuery(sql);
		return query.getResultList();

	}
	
	@Override
	public Object[] getTimeWithMorePosts(Long idSample) {
		sql = "select key,sum(value) as s from opsocial.samplesreports where idsample=" + idSample + " and property=" + SampleReport.POSTS_BY_HOUR + " group by key order by s desc limit 1";
		query = em.createNativeQuery(sql);
		return (Object[]) query.getSingleResult();
	}

	@Override
	public Object[] getWeekDayWithMorePosts(Long idSample) {
		sql = "select key,sum(value) as s from opsocial.samplesreports where idsample=" + idSample + " and property=" + SampleReport.POSTS_BY_DAY + " group by key order by s desc limit 1";
		query = em.createNativeQuery(sql);
		return (Object[]) query.getSingleResult();
	}
}
