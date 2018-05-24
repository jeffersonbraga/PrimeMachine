package br.com.primum.dialect;

import org.hibernate.dialect.Oracle9iDialect;

public class OracleDialect_ext extends Oracle9iDialect{

	public OracleDialect_ext() {
		super();
		//registerHibernateType(-1, Hibernate.STRING.getName());
	}

}
