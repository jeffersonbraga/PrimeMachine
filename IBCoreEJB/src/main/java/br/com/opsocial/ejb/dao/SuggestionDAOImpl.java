package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.Suggestion;

@Stateless
public class SuggestionDAOImpl extends AbstractDAOImpl<Suggestion> implements SuggestionDAO{

}
