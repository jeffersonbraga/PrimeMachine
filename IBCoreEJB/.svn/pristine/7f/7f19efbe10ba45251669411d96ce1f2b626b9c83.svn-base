package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.news.NewsPost;

@Remote
public interface MaintenanceNewsPostRemote extends AbstractDAS<NewsPost> {

	public NewsPost getByUrl(String url);
	
}
