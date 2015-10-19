package com.paradeto.actor;

import akka.actor.Actor;
import akka.actor.UntypedActor;
import com.paradeto.actor.message.ClearCache;
import com.paradeto.service.EhcacheService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Ayou on 2015/10/14.
 */
public class CacheActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        // 收到清空缓存的消息
        if(message instanceof ClearCache) {
            EhcacheService ehcacheService = new EhcacheService();
            System.out.println(ehcacheService);
            ehcacheService.clearAll();
        }else if(message instanceof String)
            System.out.println(message);
    }
}
