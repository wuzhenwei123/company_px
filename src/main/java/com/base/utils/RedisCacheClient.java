package com.base.utils;

import org.apache.commons.lang.SerializationUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <desc> </desc>
 *
 * @version V1.0
 * @date 2013-3-20
 */
public class RedisCacheClient {

    private final static Logger logger = Logger.getLogger(RedisCacheClient.class);

    private JedisPool jedisPool;

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


    public void set(String key, Serializable value) {
        this.set(key, value, null);
    }

    public void set(String key, Serializable value, Integer expireSeconds) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.set(key.getBytes(), SerializationUtils.serialize(value));
            if (expireSeconds != null) {
                jedis.expire(key.getBytes(), expireSeconds);
            }
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public void hset(String key, String fieid, Serializable value) {
        this.hset(key, fieid, value, null);
    }

    public void hset(String key, String fieid, Serializable value, Integer expireSeconds) {
        Jedis jedis = jedisPool.getResource();
        Object o = "";

        try {
            jedis.hset(key.getBytes(), fieid.getBytes(), SerializationUtils.serialize(value));
            if (expireSeconds != null) {
                jedis.expire(key.getBytes(), expireSeconds);
            }
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public void hset(String key, String fieid, Object value, Integer expireSeconds) {
        Jedis jedis = jedisPool.getResource();
        Object o = "";

        try {
            jedis.hset(key.getBytes(), fieid.getBytes(), this.toByteArray(value));
            if (expireSeconds != null) {
                jedis.expire(key.getBytes(), expireSeconds);
            }
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 返回hash中指定存储位置的值
     *
     * @param key
     * @param fieid 存储的名字
     * @return 存储对应的值
     */
    public <T> T hget(String key, String fieid) {
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] value = jedis.hget(key.getBytes(), fieid.getBytes());
            if (value != null) {
                return (T) SerializationUtils.deserialize(value);
            }
        } finally {
            jedisPool.returnResource(jedis);
        }
        return null;
    }

    /**
     * 从Hash中删除对象
     */

    public void hdel(String key, String... field) {
        Jedis jedis = jedisPool.getResource();
        jedis.hdel(key, field);
        jedisPool.returnResource(jedis);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] value = jedis.get(key.getBytes());
            if (value != null) {
                return (T) SerializationUtils.deserialize(value);
            }
            return null;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public void delete(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.del(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public boolean exists(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.exists(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public Set<String> keys(String pattern) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.keys(pattern);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public long lLength(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.llen(key);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> lRangeAll(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            List<T> relist = new ArrayList<T>();
            List<byte[]> list = jedis.lrange(key.getBytes(), 0, -1);
            for (byte[] b : list) {
                if (b != null) {
                    relist.add((T) SerializationUtils.deserialize(b));
                }
            }
            return relist;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public long lAdd(String key, Serializable value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.lpush(key.getBytes(), SerializationUtils.serialize(value));
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 对象转数组
     * @param obj
     * @return
     */
    private synchronized static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
}
