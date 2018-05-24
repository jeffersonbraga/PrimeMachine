package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.instagram.InstagramPost;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;

@Local
public interface InstagramPostDAO extends AbstractDAO<InstagramPost> {

	public InstagramPost getByIdMedia(Long idMedia);
}
