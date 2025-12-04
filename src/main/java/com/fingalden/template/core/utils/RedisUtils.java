package com.fingalden.template.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类，提供常见的Redis操作功能
 * 基于Spring Data Redis的RedisTemplate实现
 */
@Component
public class RedisUtils {

    /**
     * RedisTemplate实例，用于操作Redis
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 构造函数注入RedisTemplate
     *
     * @param redisTemplate RedisTemplate实例
     */
    @Autowired
    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // ============================== 字符串操作 ==============================

    /**
     * 设置字符串值
     *
     * @param key   键
     * @param value 值
     * @return boolean 操作是否成功
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置字符串值，并指定过期时间
     *
     * @param key      键
     * @param value    值
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @return boolean 操作是否成功
     */
    public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取字符串值
     *
     * @param key 键
     * @return Object 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除键
     *
     * @param key 键
     * @return boolean 操作是否成功
     */
    public boolean delete(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量删除键
     *
     * @param keys 键集合
     * @return long 删除的键数量
     */
    public long delete(Collection<String> keys) {
        try {
            return redisTemplate.delete(keys);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 设置键的过期时间
     *
     * @param key      键
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @return boolean 操作是否成功
     */
    public boolean expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取键的过期时间
     *
     * @param key      键
     * @param timeUnit 时间单位
     * @return long 过期时间，-1表示永久有效，-2表示键不存在
     */
    public long getExpire(String key, TimeUnit timeUnit) {
        try {
            return redisTemplate.getExpire(key, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
            return -2;
        }
    }

    /**
     * 判断键是否存在
     *
     * @param key 键
     * @return boolean 键是否存在
     */
    public boolean hasKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增操作
     *
     * @param key   键
     * @param delta 递增步长
     * @return long 递增后的值
     */
    public long increment(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增步长必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减操作
     *
     * @param key   键
     * @param delta 递减步长
     * @return long 递减后的值
     */
    public long decrement(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减步长必须大于0");
        }
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    // ============================== 哈希操作 ==============================

    /**
     * 设置哈希值
     *
     * @param key     键
     * @param hashKey 哈希键
     * @param value   值
     * @return boolean 操作是否成功
     */
    public boolean hSet(String key, String hashKey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置哈希值，并指定过期时间
     *
     * @param key      键
     * @param hashKey  哈希键
     * @param value    值
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @return boolean 操作是否成功
     */
    public boolean hSet(String key, String hashKey, Object value, long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            expire(key, time, timeUnit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置整个哈希表
     *
     * @param key 键
     * @param map 哈希表
     * @return boolean 操作是否成功
     */
    public boolean hSetAll(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置整个哈希表，并指定过期时间
     *
     * @param key      键
     * @param map      哈希表
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @return boolean 操作是否成功
     */
    public boolean hSetAll(String key, Map<String, Object> map, long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            expire(key, time, timeUnit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取哈希值
     *
     * @param key     键
     * @param hashKey 哈希键
     * @return Object 值
     */
    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取整个哈希表
     *
     * @param key 键
     * @return Map<String, Object> 哈希表
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除哈希表中的多个键
     *
     * @param key      键
     * @param hashKeys 哈希键数组
     * @return long 删除的键数量
     */
    public long hDelete(String key, Object... hashKeys) {
        try {
            return redisTemplate.opsForHash().delete(key, hashKeys);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 判断哈希键是否存在
     *
     * @param key     键
     * @param hashKey 哈希键
     * @return boolean 哈希键是否存在
     */
    public boolean hHasKey(String key, String hashKey) {
        try {
            return redisTemplate.opsForHash().hasKey(key, hashKey);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 哈希值递增
     *
     * @param key     键
     * @param hashKey 哈希键
     * @param delta   递增步长
     * @return double 递增后的值
     */
    public double hIncrement(String key, String hashKey, double delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * 哈希值递减
     *
     * @param key     键
     * @param hashKey 哈希键
     * @param delta   递减步长
     * @return double 递减后的值
     */
    public double hDecrement(String key, String hashKey, double delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    // ============================== 列表操作 ==============================

    /**
     * 向列表左侧添加元素
     *
     * @param key   键
     * @param value 值
     * @return long 列表长度
     */
    public long lLeftPush(String key, Object value) {
        try {
            return redisTemplate.opsForList().leftPush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 向列表左侧添加多个元素
     *
     * @param key    键
     * @param values 值集合
     * @return long 列表长度
     */
    public long lLeftPushAll(String key, Collection<Object> values) {
        try {
            return redisTemplate.opsForList().leftPushAll(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 向列表右侧添加元素
     *
     * @param key   键
     * @param value 值
     * @return long 列表长度
     */
    public long lRightPush(String key, Object value) {
        try {
            return redisTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 向列表右侧添加多个元素
     *
     * @param key    键
     * @param values 值集合
     * @return long 列表长度
     */
    public long lRightPushAll(String key, Collection<Object> values) {
        try {
            return redisTemplate.opsForList().rightPushAll(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param key   键
     * @param start 开始索引（0表示第一个元素）
     * @param end   结束索引（-1表示最后一个元素）
     * @return List<Object> 元素列表
     */
    public List<Object> lRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 截取列表，只保留指定范围内的元素
     *
     * @param key   键
     * @param start 开始索引
     * @param end   结束索引
     * @return boolean 操作是否成功
     */
    public boolean lTrim(String key, long start, long end) {
        try {
            redisTemplate.opsForList().trim(key, start, end);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取列表指定索引处的元素
     *
     * @param key   键
     * @param index 索引
     * @return Object 元素
     */
    public Object lIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取列表长度
     *
     * @param key 键
     * @return long 列表长度
     */
    public long lSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除列表中与指定值相等的元素
     *
     * @param key   键
     * @param count 移除数量：
     *              count > 0：从表头开始向表尾搜索，移除与VALUE相等的元素，数量为COUNT
     *              count < 0：从表尾开始向表头搜索，移除与VALUE相等的元素，数量为COUNT的绝对值
     *              count = 0：移除表中所有与VALUE相等的元素
     * @param value 值
     * @return long 移除的元素数量
     */
    public long lRemove(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ============================== 集合操作 ==============================

    /**
     * 向集合添加元素
     *
     * @param key    键
     * @param values 值数组
     * @return long 添加的元素数量
     */
    public long sAdd(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取集合所有元素
     *
     * @param key 键
     * @return Set<Object> 元素集合
     */
    public Set<Object> sMembers(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    /**
     * 判断集合中是否包含指定元素
     *
     * @param key   键
     * @param value 值
     * @return boolean 是否包含
     */
    public boolean sIsMember(String key, Object value) {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取集合大小
     *
     * @param key 键
     * @return long 集合大小
     */
    public long sSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除集合中的元素
     *
     * @param key    键
     * @param values 值数组
     * @return long 移除的元素数量
     */
    public long sRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ============================== 有序集合操作 ==============================

    /**
     * 向有序集合添加元素
     *
     * @param key   键
     * @param value 值
     * @param score 分数
     * @return boolean 操作是否成功
     */
    public boolean zAdd(String key, Object value, double score) {
        try {
            redisTemplate.opsForZSet().add(key, value, score);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取有序集合指定范围内的元素（从小到大）
     *
     * @param key   键
     * @param start 开始索引
     * @param end   结束索引
     * @return Set<Object> 元素集合
     */
    public Set<Object> zRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    /**
     * 获取有序集合指定分数范围内的元素（从小到大）
     *
     * @param key 键
     * @param min 最小分数
     * @param max 最大分数
     * @return Set<Object> 元素集合
     */
    public Set<Object> zRangeByScore(String key, double min, double max) {
        try {
            return redisTemplate.opsForZSet().rangeByScore(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    /**
     * 获取有序集合元素数量
     *
     * @param key 键
     * @return long 元素数量
     */
    public long zSize(String key) {
        try {
            return redisTemplate.opsForZSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取有序集合指定分数范围内的元素数量
     *
     * @param key 键
     * @param min 最小分数
     * @param max 最大分数
     * @return long 元素数量
     */
    public long zCountByScore(String key, double min, double max) {
        try {
            return redisTemplate.opsForZSet().count(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 删除有序集合中的元素
     *
     * @param key    键
     * @param values 值数组
     * @return long 移除的元素数量
     */
    public long zRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForZSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取有序集合中指定元素的分数
     *
     * @param key   键
     * @param value 值
     * @return Double 分数，null表示元素不存在
     */
    public Double zScore(String key, Object value) {
        try {
            return redisTemplate.opsForZSet().score(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 有序集合元素递增
     *
     * @param key   键
     * @param value 值
     * @param delta 递增步长
     * @return double 递增后的分数
     */
    public double zIncrementScore(String key, Object value, double delta) {
        try {
            return redisTemplate.opsForZSet().incrementScore(key, value, delta);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}