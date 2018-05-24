package br.com.primum.dialect;

import org.hibernate.Hibernate;
import org.hibernate.dialect.PostgreSQLDialect;

public class PostgresqlDialect_ext extends PostgreSQLDialect {

	public PostgresqlDialect_ext() {
		super();
		//registerHibernateType(-1, Hibernate.STRING.getName());
	}

}
