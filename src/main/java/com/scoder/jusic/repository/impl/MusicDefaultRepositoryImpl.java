package com.scoder.jusic.repository.impl;

import com.scoder.jusic.configuration.JusicProperties;
import com.scoder.jusic.repository.MusicDefaultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author H
 */
@Repository
public class MusicDefaultRepositoryImpl implements MusicDefaultRepository {

    @Autowired
    private JusicProperties jusicProperties;
    @Autowired
    private JusicProperties.RedisKeys redisKeys;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long destroy() {
        Set members = redisTemplate.opsForSet()
                .members(redisKeys.getDefaultSet());
        return members != null && members.size() > 0 ? redisTemplate.opsForSet()
                .remove(redisKeys.getDefaultSet(), members.toArray()) : 0;
    }

    @Override
    public Long initialize() {
        return redisTemplate.opsForSet()
                .add(redisKeys.getDefaultSet(), jusicProperties.getDefaultList().toArray());
    }

    /**
     * set
     *
     * @return long
     */
    @Override
    public Long size() {
        return redisTemplate.opsForSet()
                .size(redisKeys.getDefaultSet());
    }

    @Override
    public String randomMember() {
        return (String) redisTemplate.opsForSet()
                .randomMember(redisKeys.getDefaultSet());
    }

    @Override
    public Long add(String[] value) {
        return redisTemplate.opsForSet()
                .add(redisKeys.getDefaultSet(), value);
    }

    @Override
    public Long set(List<String> list) {
        return redisTemplate.opsForSet()
            .add(redisKeys.getDefaultSet(), list.toArray());
    }
}
