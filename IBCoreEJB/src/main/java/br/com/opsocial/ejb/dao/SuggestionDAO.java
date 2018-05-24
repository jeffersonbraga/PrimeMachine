package br.com.opsocial.ejb.dao;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.application.Suggestion;

@Local
public interface SuggestionDAO extends AbstractDAO<Suggestion> {

}
