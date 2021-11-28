package DealerStat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final StringRedisTemplate stringRedisTemplate;

    private final ValueOperations valueOperations;


    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        valueOperations = stringRedisTemplate.opsForValue();
    }


    public void putToken(String token, String email){
        valueOperations.set(token, email, 86400000, TimeUnit.MILLISECONDS);
    }

    public String getToken(String token){
        String tok = (String) valueOperations.get(token);
        stringRedisTemplate.delete(token);
        return tok;
    }
}
