package eu.vrtime.bootsoap.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories("eu.vrtime.bootsoap.repositories")
@EnableJpaAuditing
@EnableTransactionManagement
public class PersistenceConfig {

	private static final String[] ENTITY_PACKAGES = { "eu.vrtime.bootsoap.gen" };

	/**
	 * DataSource properties
	 */

	private static final String PROPERTY_NAME_DB_DRIVER_CLASS = "db.driver";
	private static final String PROPERTY_NAME_DB_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DB_URL = "db.url";
	private static final String PROPERTY_NAME_DB_USER = "db.username";

	/**
	 * Hibernate properties
	 */

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

	/**
	 * JPA properties
	 */

	private static final String PROPERTY_NAME_JPA_SCRIPTS_ACTION = "javax.persistence.schema-generation.scripts.action";
	private static final String PROPERTY_NAME_JPA_SCRIPTS_CREATE_TARGET = "javax.persistence.schema-generation.scripts.create-target";
	private static final String PROPERTY_NAME_JPA_SCRIPTS_DROP_TARGET = "javax.persistence.schema-generation.scripts.drop-target";

	/**
	 * Creates and configures the HikariCP datasource bean.
	 * 
	 * @param env The runtime environment of our application.
	 * @return
	 */

	@Bean(destroyMethod = "close")
	DataSource dataSource(Environment env) {
		HikariConfig dataSourceConfig = new HikariConfig();
		dataSourceConfig.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DB_DRIVER_CLASS));
		dataSourceConfig.setJdbcUrl(env.getRequiredProperty(PROPERTY_NAME_DB_URL));
		dataSourceConfig.setUsername(env.getRequiredProperty(PROPERTY_NAME_DB_USER));
		dataSourceConfig.setPassword(env.getRequiredProperty(PROPERTY_NAME_DB_PASSWORD));
		dataSourceConfig.setMaximumPoolSize(10);

		return new HikariDataSource(dataSourceConfig);
	}

	/**
	 * Creates the bean that creates the JPA entity manager factory.
	 * 
	 * @param dataSource The datasource that provides the database connections.
	 * @param env        The runtime environment of our application.
	 * @return
	 */

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(ENTITY_PACKAGES);

		Properties jpaProperties = new Properties();

		/**
		 * Configures the used database dialect. This allows Hibernate to create SQL
		 * that is optimized for the used database.
		 */

		jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));

		/**
		 * Specifies the action that is invoked to the database when the Hibernate
		 * SessionFactory is created or closed.
		 */

		jpaProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO,
				env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));

		/**
		 * Configures the naming strategy that is used when Hibernate creates new
		 * database objects and schema elements.
		 */

		jpaProperties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY,
				env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY));

		/**
		 * If the value of this property is true, Hibernate writes all SQL statements to
		 * the console.
		 */

		jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));

		/**
		 * If the value of this property is true, Hibernate will use prettyprint when it
		 * writes SQL to the console.
		 */

		jpaProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL,
				env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

	/**
	 * Creates the transaction manager bean that integrates the used JPA provider
	 * with the Spring transaction mechanism.
	 * 
	 * @param entityManagerFactory The used JPA entity manager factory.
	 * @return
	 */
	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

}

