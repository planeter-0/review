package com.planeter.review.common.aspect;


import com.planeter.review.common.annotation.Cache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 缓存雪崩之设置随机过期时间
 * 缓存穿透之缓存空值
 * 缓存击穿之加同步锁
 */
@Slf4j
@Component
@Aspect
public class CacheAspect {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Pointcut("@annotation(com.planeter.review.common.annotation.Cache)")
    public void cachePointcut() {
    }

    // 定义相应的事件
    @Around("cachePointcut()")
    public Object doCache(ProceedingJoinPoint joinPoint) {
        Object value = null;
        try {
            // 0-1、 当前方法上注解的内容
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
            Cache cacheAnnotation = method.getAnnotation(Cache.class);
            String keyEl = cacheAnnotation.key();
            // 0-2、 前提条件：拿到作为key的依据  - 解析springEL表达式
            // 创建解析器
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(keyEl);
            EvaluationContext context = new StandardEvaluationContext(); // 参数
            // 添加参数
            Object[] args = joinPoint.getArgs();
            DefaultParameterNameDiscoverer discover = new DefaultParameterNameDiscoverer();
            String[] parameterNames = discover.getParameterNames(method);
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i].toString());
            }
            // 解析springEL表达式
            String key = expression.getValue(context).toString();

            // 尝试从取缓存
            value = redisTemplate.opsForValue().get(key);
            // 无缓存
            if (value == null) {
                // 加同步锁,避免缓存击穿时，大量请求访问数据库
                synchronized (this) {
                    // 访问数据库
                    value = joinPoint.proceed();
                    // 库中没有此数据，存入一个过期时间为1分钟的空对象,防止穿透
                    if (value == null) {
                        redisTemplate.opsForValue().set(key, "", 1, TimeUnit.MINUTES);
                    } else {
                        // 将数据写入缓存，并设置一个随机的过期时间，避免缓存雪崩问题
                        Random random = new Random();
                        redisTemplate.opsForValue().set(key, "", random.nextInt(2) + 1, TimeUnit.HOURS);
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        // 有缓存直接返回缓存
        return value;
    }
}