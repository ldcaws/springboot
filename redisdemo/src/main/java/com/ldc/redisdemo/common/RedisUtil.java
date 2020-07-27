package com.ldc.redisdemo.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.*;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/27 20:01
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String OK = "OK";
    private static final Long RELEASE_SUCCESS = 1L;

    public static final String SET_NX = "NX";//Only set the key if it does not already exist.
    public static final String SET_XX = "XX";//Only set the key if it already exist.
    public static final String SET_EX = "EX";//seconds
    public static final String c = "PX";//milliseconds

    /**
     * 键值对设值
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public <V> Boolean set(String key, V value) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            connection.set(key.getBytes(), JSON.toJSONBytes(value));
            return true;
        });
    }

    /**
     * 键值对设值和有效时间
     *
     * @param key   键
     * @param value 值
     * @param time  有效时间(单位：秒)
     * @return
     */
    public <V> Boolean setNX(String key, V value, long time) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            connection.setEx(key.getBytes(), time, JSON.toJSONBytes(value));
            return true;
        });
    }

    /**
     * 设置有效时间
     *
     * @param key   键
     * @param value 值
     * @param nxxx  NX或者XX
     * @param expx  EX或者PX
     * @param time  有效时间
     * @return
     */
    public <V> Boolean set(String key, V value, String nxxx, String expx, long time) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            JedisCommands commands = (JedisCommands) connection.getNativeConnection();
            String v = commands.set(key, JSON.toJSONString(value), nxxx, expx, time);
            return OK.equals(v);
        });
    }

    /**
     * 查询键值对
     *
     * @param key           键
     * @param typeReference 返回类型
     * @param <R>           返回类型
     * @return
     */
    public <R> R get(String key, TypeReference<R> typeReference) {
        byte[] redisValue = redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.get(key.getBytes()));
        if (redisValue == null) return null;
        return JSONObject.parseObject(new String(redisValue), typeReference);
    }

    /**
     * 明确值为String的请求方式
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return get(key, new TypeReference<String>() {
        });
    }

    /**
     * 存储Hash结构数据(批量)
     *
     * @param outerKey 外键
     * @param map      内键-内值
     * @return
     */
    public <V> Boolean hSetMap(String outerKey, Map<String, V> map) {
        if (map == null || map.isEmpty()) return false;
        Map<byte[], byte[]> byteMap = new HashMap<>();
        map.forEach((innerKey, innerValue) -> byteMap.put(innerKey.getBytes(), JSON.toJSONBytes(innerValue)));
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            connection.hMSet(outerKey.getBytes(), byteMap);
            return true;
        });
    }

    /**
     * 存储Hash结构数据
     *
     * @param outerKey   外键
     * @param innerKey   内键
     * @param innerValue 值
     * @return
     */
    public <V> Boolean hSet(String outerKey, String innerKey, V innerValue) {
        Map<String, V> map = new HashMap<>();
        map.put(innerKey, innerValue);
        return this.hSetMap(outerKey, map);
    }

    /**
     * 获取Hash结构Map集合，内键和内值键值对封装成Map集合
     *
     * @param outerKey       外键
     * @param
     * @param innerValueType 值类型
     * @return
     */
    public <V> Map<String, V> hGetMap(String outerKey, String innerKey, TypeReference<V> innerValueType) {
        Map<byte[], byte[]> redisMap = redisTemplate.execute
                ((RedisCallback<Map<byte[], byte[]>>) connection -> connection.hGetAll(outerKey.getBytes()));
        if (redisMap == null) return null;
        Map<String, V> resultMap = new HashMap<>();
        redisMap.forEach((key, value) -> resultMap.put(JSONObject.toJSONString(key),
                JSONObject.parseObject(JSON.toJSONString(value), innerValueType)));
        return resultMap;
    }

    /**
     * 查询Hash结构的值
     *
     * @param outerKey      外键
     * @param innerKey      内键
     * @param typeReference 值类型
     * @return
     */
    public <V> V hGet(String outerKey, String innerKey, TypeReference<V> typeReference) {
        byte[] redisResult = redisTemplate.execute((RedisCallback<byte[]>)
                connection -> connection.hGet(outerKey.getBytes(), JSON.toJSONBytes(innerKey)));
        if (redisResult == null) return null;
        return JSONObject.parseObject(JSON.toJSONString(redisResult), typeReference);

    }

    /**
     * 删除键值对
     *
     * @param keys 键
     * @return
     */
    public Long del(List<String> keys) {
        if (keys == null || keys.isEmpty()) return 0L;
        byte[][] keyBytes = new byte[keys.size()][];
        int index = 0;
        for (String key : keys) {
            keyBytes[index] = key.getBytes();
            index++;
        }
        return redisTemplate.execute((RedisCallback<Long>) connection -> connection.del(keyBytes));
    }

    /**
     * 删除Hash结构内键和值(批量)
     *
     * @param outerKey  外键
     * @param innerKeys 内键
     * @return
     */
    public <I> Long hDel(String outerKey, List<I> innerKeys) {
        if (innerKeys == null || innerKeys.isEmpty()) return 0L;
        byte[][] innerKeyBytes = new byte[innerKeys.size()][];
        int index = 0;
        for (I key : innerKeys) {
            innerKeyBytes[index] = JSON.toJSONBytes(key);
            index++;
        }
        return redisTemplate.execute((RedisCallback<Long>) connection ->
                connection.hDel(outerKey.getBytes(), innerKeyBytes));
    }

    /**
     * 删除Hash结构内键和值
     *
     * @param outerKey  外键
     * @param innerKeys 内键
     * @return
     */
    public <I> Long hDel(String outerKey, I innerKeys) {
        List<I> l = new ArrayList<>();
        l.add(innerKeys);
        return hDel(outerKey, l);
    }

    /**
     * 设置有效时间
     *
     * @param key  键
     * @param time 值
     * @return
     */
    public Boolean expire(String key, long time) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection ->
                connection.expire(key.getBytes(), time)
        );
    }

    /**
     * 分布式锁，防止误删其他线程创建的锁
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean unLock(String key, String value) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        // 使用Lua脚本删除Redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
        // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
        Long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            Object nativeConnection = connection.getNativeConnection();
            // 集群模式
            if (nativeConnection instanceof JedisCluster) {
                return (Long) ((JedisCluster) nativeConnection).eval(script, Collections.singletonList(key),
                        Collections.singletonList(value));
            }
            // 单机模式
            else if (nativeConnection instanceof Jedis) {
                return (Long) ((Jedis) nativeConnection).eval(script, Collections.singletonList(key),
                        Collections.singletonList(value));
            }
            return 0L;
        });
        return RELEASE_SUCCESS.equals(result);
    }
}