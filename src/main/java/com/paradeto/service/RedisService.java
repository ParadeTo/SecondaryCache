package com.paradeto.service;

import com.paradeto.entity.Star;
import com.paradeto.util.SerializeUtil;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Ayou on 2015/10/14.
 */
@Service
public class RedisService {
    private static final String HOST = "localhost";
    private static final Integer PORT = 6379;
    private static final Jedis jedis = new Jedis(HOST, PORT);

    public void saveOrUpdate(Star star) {
        jedis.set(star.getId().toString().getBytes(), SerializeUtil.serialize(star));
        List<String> keys = jedis.lrange("keys", 0, jedis.llen("keys"));
        if(!keys.contains(star.getId().toString())){
            keys.add(star.getId().toString());
            updateKeys(keys);
        }
    }

    private void updateKeys(List<String> keys){
        jedis.del("keys");
        for(String k:keys)
            jedis.lpush("keys",k);
    }

    public List<Star> getStarts4Ehcache(Integer limit) {
        SortingParams sp = new SortingParams();
        sp.desc();
        sp.limit(0,limit);
        List<String> keys = jedis.sort("keys", sp);
        List<Star> stars = new ArrayList<Star>();
        for (String k:keys){
            byte[] data= jedis.get(k.getBytes());
            Star start = (Star)SerializeUtil.unserialize(data);
            stars.add(start);
        }
        return stars;
    }

    public void del(Integer id){
        jedis.del(id.toString().getBytes());
        List<String> keys = jedis.lrange("keys", 0, jedis.llen("keys"));
        keys.remove(id.toString());
        updateKeys(keys);
    }

    public Integer getSize(){
        return  Integer.valueOf(jedis.llen("keys").toString());
    }

}
