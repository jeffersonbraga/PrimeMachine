package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.blogs.BlogPost;

@Remote
public interface MaintenanceBlogPostRemote extends AbstractDAS<BlogPost> {

	public BlogPost getByUrl(String url);
	
}
