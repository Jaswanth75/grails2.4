dataSource {
    configClass = GrailsAnnotationConfiguration.class
   pooled = false
   driverClassName = "com.mysql.jdbc.Driver"
   username = "mdlog"
   password =  "Immdops@2012"
   dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
}
hibernate {
	
	cache.use_second_level_cache = false
	cache.use_query_cache = false
	cache.provider_class = 'grails.plugin.multitenant.ehcache.cache.MultiTenantEhCacheProvider'

//    cache.use_second_level_cache = true
//    cache.use_query_cache = false
//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
//    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
//    singleSession = true // configure OSIV singleSession mode
//    flush.mode = 'manual' // OSIV session flush mode outside of transactional context
}

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update"
			pooled = true
            url = "jdbc:mysql://localhost/test"
			properties {
			   maxActive = 30
			   minIdle = 1
			   initialSize = 10
			   maxWait = 10000
			   testOnBorrow = true
			   testWhileIdle = true
			   testOnReturn = false
			   validationQuery = "SELECT 1"			   
			}
        }
		
    }
    test {
        dataSource {
             dbCreate = "update"
			pooled = true
            url = "jdbc:mysql://localhost/test"
			properties {
			   maxActive = 30
			   minIdle = 1
			   initialSize = 10
			   maxWait = 10000
			   testOnBorrow = true
			   testWhileIdle = true
			   testOnReturn = false
			   validationQuery = "SELECT 1"			   
			}
        }
    }
    
}
