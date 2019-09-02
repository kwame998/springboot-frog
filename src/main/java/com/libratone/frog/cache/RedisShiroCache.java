package com.libratone.frog.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 使用redis 作为缓存器 
 */
public class RedisShiroCache<K,V> implements Cache<K, V> {

    private RedisTemplate<K, V> redisTemplate;

    private Long EXPTIMES;

    private String name;

    public RedisShiroCache(RedisTemplate redisTemplate){
        this.name="default";
        this.redisTemplate = redisTemplate;
    }

    public RedisShiroCache(String name,RedisTemplate redisTemplate){
        this.name =name;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public V get(K k) throws CacheException {
        return redisTemplate.opsForValue().get(k);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        if(this.EXPTIMES!=null){
            redisTemplate.opsForValue().set(k, v, this.EXPTIMES, TimeUnit.SECONDS);
        }else {
            redisTemplate.opsForValue().set(k, v);
        }
        return v;
    }

    public V put(K k, V v,Long EXPTIMES) throws CacheException {
        redisTemplate.opsForValue().set(k, v, EXPTIMES, TimeUnit.SECONDS);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V v=redisTemplate.opsForValue().get(k);
        redisTemplate.delete(k);
        return v;
    }

    @Override
    public void clear() throws CacheException {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }


    public Long getEXPTIMES() {
        return EXPTIMES;
    }

    public void setEXPTIMES(Long EXPTIMES) {
        this.EXPTIMES = EXPTIMES;
    }

    public RedisTemplate<K, V> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
