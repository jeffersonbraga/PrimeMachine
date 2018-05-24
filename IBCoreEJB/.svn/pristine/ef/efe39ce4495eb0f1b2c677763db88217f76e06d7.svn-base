package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.SamplePost;

@Stateless
public class SamplePostDAOImpl extends AbstractDAOImpl<SamplePost> implements SamplePostDAO {

	@Override
	public List<SamplePost> listBySample(Long idSample, Integer offset) {

		sql = "select * from opsocial.samplesposts s where s.idsample=" + idSample + " offset " + offset + " limit 50" ;
		query = em.createNativeQuery(sql,SamplePost.class);
		return query.getResultList();
	}

	@Override
	public List<Object[]> getPostsByNetwork(Long idSample) {
		sql = "select network,count(postid) from opsocial.samplesposts where idsample=" + idSample + " group by network";
		query = em.createNativeQuery(sql);
		return query.getResultList();
	}

	@Override
	public Object[] getMostVolume(Long idSample) {
		sql = "select network,count(postid) maximium from opsocial.samplesposts where idsample=" + idSample + " group by network limit 1";
		query = em.createNativeQuery(sql);
		return (Object[]) query.getSingleResult();
	}

	@Override
	public List<Object[]> listPostsGroupByNetworks(Long idSample) {
		sql = "select network,postid from opsocial.samplesposts where idsample=" + idSample;
		query = em.createNativeQuery(sql);
		return (List<Object[]>) query.getResultList();
	}
}
