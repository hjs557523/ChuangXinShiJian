package com.hjs.system.config.redis;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/6 15:02
 * @Modified By:
 */

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
}
