package dealerstat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final ValueOperations valueOperations;

    @Value("${redis.token.expired}")
    private long validityInMilliseconds;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        valueOperations = redisTemplate.opsForValue();
    }


    public void putToken(String token, Object object){
        valueOperations.set(token, object, validityInMilliseconds, TimeUnit.MILLISECONDS);
    }

    public Object getToken(String token){
        return valueOperations.get(token);
    }

    public void deleteToken(String token){
        redisTemplate.delete(token);
    }
}
