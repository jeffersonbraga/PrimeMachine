package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.AudienceContact;

@Stateless
public class AudienceContactDAOImpl extends AbstractDAOImpl<AudienceContact> implements AudienceContactDAO { 

}
