package com.hjs.system.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.lang.reflect.Method;

/**
 * @author 黄继升 16041321
 * @Description: Redis配置类，这里主要是用到2；@EnableCaching注解开启项目缓存支持
 * 1.当Redis当做数据库或者消息队列来操作时，一般使用RedisTemplate来操作
 * 2.当Redis作为项目缓存来使用时，可以作为Spring Cache的具体实现，直接通过注解使用：@CacheConfig，@Cacheable，@CachePut，@CacheEvict
 * @date Created in 2020/3/6 15:02
 * @Modified By:
 */

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        logger.info("生成RedisCacheManager缓存管理器");
        return RedisCacheManager.create(redisConnectionFactory);
    }


    /**
     * 在缓存对象集合中，缓存是以key-value形式保存的。不指定缓存的key时，springboot会默认使用SimpleKeyGenerator来生成key：使用方法参数组合生成key;
     * 但可能存在两个方法的参数相同，但执行逻辑不通过，导致执行第二个方法时命中第一个方法的缓存，此时有三种解决方法：
     * 第一种是在@Cacheable注解参数中指定key
     * 第二种是自己实现一个KeyGenerator，在注解中指定KeyGenerator
     * 第三种是用Spring提供的方案：继承CachingConfigurerSuppport并重写keyGenerator()：类名+方法名+参数列表值
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                //格式化缓存key字符串
                StringBuilder sb = new StringBuilder();
                //追加类名
                sb.append(o.getClass().getName());
                //追加方法名
                sb.append(method.getName());
                //遍历参数并且追加值
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                logger.info("调用Redis缓存key: " + sb.toString());
                return sb.toString();
            }
        };
    }
}
