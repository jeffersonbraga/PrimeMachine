package br.com.primum.dialect;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;

public class MySQL5Dialect_ext extends MySQL5Dialect {

	public MySQL5Dialect_ext() {
		super();
		//registerHibernateType(-1, Hibernate.STRING.getName());
	}

}
