package dealerstat.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
@Component
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private ValueOperations<String, Object> valueOperations;


    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void init(){
        valueOperations = redisTemplate.opsForValue();
    }


    public void putToken(String token, Object object) {
        long validityInMilliseconds = 86400000;
        valueOperations.set(token, object, validityInMilliseconds, TimeUnit.MILLISECONDS);
    }

    public Object getToken(String token) {
        return valueOperations.get(token);
    }

    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }
}
