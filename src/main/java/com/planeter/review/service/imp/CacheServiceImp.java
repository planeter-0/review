package com.planeter.review.service.imp;

import com.planeter.review.service.CacheService;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
@Service
public class CacheServiceImp implements CacheService {
    @Resource
    private RedisTemplate<String, Object> template;

    public void cachePut(String key, Object toBeCached, long ttlMinutes) {
        template.opsForValue().set(key, toBeCached, ttlMinutes, TimeUnit.MINUTES);
    }

    public void cachePut(String key, Object toBeCached) {
        if (toBeCached == null)
            return;
        cachePut(key, toBeCached, -1);
    }

    public <T> T cacheGet(String key, Class<T> type) {
        return (T) template.opsForValue().get(key);
    }
}
