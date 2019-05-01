package com.wucq.webdemo.dao;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisCache implements Cache {

    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final String id;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    private static final long EXPIRE_TIME_IN_MINUTES = 30;

    public RedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("cache instance require an ID");
        }
        this.id = id;
    }

    @Override
    public void clear() {
        RedisTemplate<Object, Object> redisTemplate = getRedisTemplate();
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushDb();
            return null;
        });
        logger.debug("clear all the cache query result from redis");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Object getObject(Object key) {
        try {
            RedisTemplate<Object, Object> redisTemplate = getRedisTemplate();
            ValueOperations<Object, Object> opsForValue = redisTemplate.opsForValue();
            logger.debug("Get cache query result from resdis.");
            return opsForValue.get(key);

        } catch (Throwable t) {
            // TODO: handle exception
            logger.error("redis get failed,fail over to db", t);
            return null;
        }
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void putObject(Object key, Object value) {
        try {
            RedisTemplate<Object, Object> redisTemplate = getRedisTemplate();
            ValueOperations<Object, Object> opsForValue = redisTemplate.opsForValue();
            opsForValue.set(key, value, EXPIRE_TIME_IN_MINUTES);
            logger.debug("Put query result to redis.");
        } catch (Throwable t) {
            // TODO: handle exception
            logger.error("Redis put failed", t);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object removeObject(Object key) {
        try {
            RedisTemplate<Object, Object> redisTemplate = getRedisTemplate();
            redisTemplate.delete(key);
            logger.debug("Remove cache query result to redis.");
        } catch (Throwable t) {
            // TODO: handle exception
            logger.error("Redis remove failed", t);
        }
        return null;
    }

    private RedisTemplate<Object, Object> getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = ApplicationContextHolder.getBean("redisTemplate");
        }
        return redisTemplate;
    }
}