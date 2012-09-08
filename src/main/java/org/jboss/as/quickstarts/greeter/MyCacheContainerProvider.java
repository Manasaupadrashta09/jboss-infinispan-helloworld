package org.jboss.as.quickstarts.greeter;

import javax.inject.Inject;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.util.concurrent.IsolationLevel;

@ApplicationScoped
public class MyCacheContainerProvider {
	@Inject
	private Logger log;

	private DefaultCacheManager manager;

	public DefaultCacheManager getCacheManager() {
		if (manager == null) {
			log.info("\n\n manager is null - constructing a new one\n\n");
			// Note: added the allowDuplicateDomains
			// addresses org.infinispan.jmx.JmxDomainConflictException: Domain
			// already registered

			GlobalConfiguration glob = new GlobalConfigurationBuilder()
					.nonClusteredDefault().globalJmxStatistics()
					.allowDuplicateDomains(true).enable().build();
			Configuration loc = new ConfigurationBuilder().jmxStatistics()
					.enable().clustering().cacheMode(CacheMode.LOCAL).locking()
					.isolationLevel(IsolationLevel.REPEATABLE_READ).eviction()
					.maxEntries(4).strategy(EvictionStrategy.LIRS).loaders()
					.passivation(false).addFileCacheStore()
					.purgeOnStartup(true).build();
			manager = new DefaultCacheManager(glob, loc, true);
			log.info("\n\n=== Using DefaultCacheManager (library mode) ===\n\n");
		}
		return manager;
	}

	@PreDestroy
	public void cleanUp() {
		manager.stop();
		manager = null;
	}

}
