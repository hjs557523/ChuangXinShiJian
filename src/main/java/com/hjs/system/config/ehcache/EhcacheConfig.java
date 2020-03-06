package com.hjs.system.config.ehcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/5 20:54
 * @Modified By:
 */

@Configuration
@EnableCaching
public class EhcacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(EhcacheConfig.class);

    @Bean
    public EhCacheCacheManager ehCacheCacheManager(CacheManager cm) {
        logger.warn("初始化EhCacheCacheManager");

        //cm就是下面的 EhcacheManagerFactoryBean
        EhCacheCacheManager manager = new EhCacheCacheManager();
        manager.setCacheManager(cm);
        return manager;
    }

//    方式2 ?
//    @Bean
//    public EhCacheCacheManager ehCacheCacheManager2(EhCacheManagerFactoryBean bean) {
//        logger.warn("初始化EhCacheCacheManager");
//        return new EhCacheCacheManager(bean.getObject());
//    }

    @Bean
    public EhCacheManagerFactoryBean cacheManager() {
        logger.warn("初始化EhCacheManagerFactoryBean");
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("/ehcache/ehcache.xml"));
        //根据shared与否的设置,Spring分别通过CacheManager.create()或new CacheManager()方式来创建一个ehcache基地.
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }
}
