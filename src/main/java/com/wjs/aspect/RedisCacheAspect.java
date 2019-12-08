package com.wjs.aspect;

import com.wjs.util.ApplicationContextUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by HIAPAD on 2019/11/28.
 */
@Aspect
@Configuration
public class RedisCacheAspect {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(com.wjs.annotation.AddRedisCache)")
    public Object addSearchCache(ProceedingJoinPoint point) {
        logger.info("<====== 进入 redisCache 环绕通知 ======>");
        try {
            //全类名
            String Key = point.getSignature().getDeclaringTypeName();
            //获取方法名
            MethodSignature signature = (MethodSignature) point.getSignature();
            String methodName = signature.getMethod().getName();
            String[] strings = signature.getParameterNames();
            String key = methodName + "_" + Arrays.toString(strings);

            // 查询操作
            Object obj = getObject(Key, key);

            if (obj == null) {
                // Redis 中不存在，则从数据库中查找，并保存到 Redis
                logger.info("<====== Redis 中不存在该记录，从数据库查找 ======>");
                obj = point.proceed();
                redisTemplate.setKeySerializer(new StringRedisSerializer());
                redisTemplate.setHashKeySerializer(new StringRedisSerializer());
                redisTemplate.opsForHash().put(Key, key.toString(), obj);
            }
            logger.info("Key:  " + Key);
            logger.info("key:  " + key);
            logger.info(obj.toString());

            return obj;
        } catch (Throwable throwable) {
            logger.error("<====== RedisCache 执行异常: {} ======>", throwable);
        }
        return null;
    }

    @Around("@annotation(com.wjs.annotation.DelRedisCache)")
    public Object deleteCache(ProceedingJoinPoint point) throws Throwable {
        logger.info("<====== 进入 redisCache 环绕通知  删除缓存 ======>");
        //全类名
        String Key = point.getSignature().getDeclaringTypeName();

        logger.info("清空缓存");
        //key的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //清空当前Key对应map
        redisTemplate.delete(Key);
        Object obj = point.proceed();

        return obj;
    }

    public Object getObject(String Key, Object key) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate.opsForHash().get(Key, key.toString());
    }

    public int getSize(String Key) {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        //key的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //获取当前namespace中缓存数据
        return redisTemplate.opsForHash().size(Key).intValue();
    }

    public ReadWriteLock getReadWriteLock() {
        return new ReentrantReadWriteLock();
    }


}
