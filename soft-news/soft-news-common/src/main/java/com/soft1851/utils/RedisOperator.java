package com.soft1851.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description: Redis宸ュ叿绫�
 * @author: mqxu
 * @create: 2020-11-15 19:30
 **/
@Component
public class RedisOperator {

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 鍒ゆ柇key鏄惁瀛樺湪
     *
     * @param key 閿�
     * @return Boolean
     */
    public Boolean keyIsExist(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 瀹炵幇鍛戒护锛歍TL key锛屼互绉掍负鍗曚綅锛岃繑鍥炵粰瀹� key鐨勫墿浣欑敓瀛樻椂闂�(TTL, time to live)銆�
     *
     * @param key 閿�
     * @return Long
     */
    public Long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 瀹炵幇鍛戒护锛歟xpire 璁剧疆杩囨湡鏃堕棿锛屽崟浣嶇
     *
     * @param key 閿�
     */
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 瀹炵幇鍛戒护锛歩ncrement key锛屽鍔爇ey涓€娆�
     *
     * @param key 閿�
     * @return Long
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 瀹炵幇鍛戒护锛歞ecrement key锛屽噺灏慿ey涓€娆�
     *
     * @param key 閿�
     * @return Long
     */
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * 瀹炵幇鍛戒护锛欿EYS pattern锛屾煡鎵炬墍鏈夌鍚堢粰瀹氭ā寮� pattern鐨� key
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 瀹炵幇鍛戒护锛欴EL key锛屽垹闄や竴涓猭ey
     *
     * @param key 閿�
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }


    /**
     * 瀹炵幇鍛戒护锛歋ET key value锛岃缃竴涓猭ey-value锛堝皢瀛楃涓插€� value鍏宠仈鍒� key锛�
     *
     * @param key   閿�
     * @param value 鍊�
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 瀹炵幇鍛戒护锛歋ET key value EX seconds锛岃缃甼ey-value鍜岃秴鏃舵椂闂达紙绉掞級
     *
     * @param key     閿�
     * @param value   鍊�
     * @param timeout 锛堜互绉掍负鍗曚綅锛�
     */
    public void set(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 濡傛灉key涓嶅瓨鍦紝鍒欒缃紝濡傛灉瀛樺湪锛屽垯鎶ラ敊
     *
     * @param key   閿�
     * @param value 鍊�
     */
    public void setnx60s(String key, String value) {
        redisTemplate.opsForValue().setIfAbsent(key, value, 60, TimeUnit.SECONDS);
    }

    /**
     * 濡傛灉key涓嶅瓨鍦紝鍒欒缃紝濡傛灉瀛樺湪锛屽垯鎶ラ敊
     *
     * @param key   閿�
     * @param value 鍊�
     */
    public void setnx(String key, String value) {
        redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 瀹炵幇鍛戒护锛欸ET key锛岃繑鍥� key鎵€鍏宠仈鐨勫瓧绗︿覆鍊笺€�
     *
     * @param key 閿�
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 鎵归噺鏌ヨ锛屽搴攎get
     *
     * @param keys
     * @return
     */
    public List<String> mget(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 鎵归噺鏌ヨ锛岀閬損ipeline
     *
     * @param keys
     * @return
     */
    public List<Object> batchGet(List<String> keys) {

        //	nginx -> keepalive
        //  redis -> pipeline

        return redisTemplate.executePipelined((RedisCallback<String>) connection -> {
            StringRedisConnection src = (StringRedisConnection) connection;

            for (String k : keys) {
                src.get(k);
            }
            return null;
        });
    }



    /**
     * 瀹炵幇鍛戒护锛欻SET key field value锛屽皢鍝堝笇琛� key涓殑鍩� field鐨勫€艰涓� value
     *
     * @param key
     * @param field
     * @param value
     */
    public void hset(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 瀹炵幇鍛戒护锛欻GET key field锛岃繑鍥炲搱甯岃〃 key涓粰瀹氬煙 field鐨勫€�
     *
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 瀹炵幇鍛戒护锛欻DEL key field [field ...]锛屽垹闄ゅ搱甯岃〃 key 涓殑涓€涓垨澶氫釜鎸囧畾鍩燂紝涓嶅瓨鍦ㄧ殑鍩熷皢琚拷鐣ャ€�
     *
     * @param key
     * @param fields
     */
    public void hdel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 瀹炵幇鍛戒护锛欻GETALL key锛岃繑鍥炲搱甯岃〃 key涓紝鎵€鏈夌殑鍩熷拰鍊笺€�
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hgetall(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // List锛堝垪琛級

    /**
     * 瀹炵幇鍛戒护锛歀PUSH key value锛屽皢涓€涓€� value鎻掑叆鍒板垪琛� key鐨勮〃澶�
     *
     * @param key
     * @param value
     * @return 鎵ц LPUSH鍛戒护鍚庯紝鍒楄〃鐨勯暱搴︺€�
     */
    public long lpush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 瀹炵幇鍛戒护锛歀POP key锛岀Щ闄ゅ苟杩斿洖鍒楄〃 key鐨勫ご鍏冪礌銆�
     *
     * @param key
     * @return 鍒楄〃key鐨勫ご鍏冪礌銆�
     */
    public String lpop(String key) {
        return (String) redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 瀹炵幇鍛戒护锛歊PUSH key value锛屽皢涓€涓€� value鎻掑叆鍒板垪琛� key鐨勮〃灏�(鏈€鍙宠竟)銆�
     *
     * @param key
     * @param value
     * @return 鎵ц LPUSH鍛戒护鍚庯紝鍒楄〃鐨勯暱搴︺€�
     */
    public long rpush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

}