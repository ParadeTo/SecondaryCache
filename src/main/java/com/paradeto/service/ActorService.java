package com.paradeto.service;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.ActorSystem;
import com.paradeto.actor.CacheActor;
import com.paradeto.actor.message.ClearCache;
import com.typesafe.config.ConfigFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Ayou on 2015/10/14.
 */
@Service
public class ActorService {
    public static ActorSystem system = ActorSystem.create("Server", ConfigFactory.load().getConfig("Server"));
    public static ActorRef localActor = system.actorOf(Props.create(CacheActor.class), "cacheActor");
    public static ActorRef remoteActor = system.actorFor("akka.tcp://Server@127.0.0.1:5102/user/cacheActor");

    public void sendMessage(Object o){
        remoteActor.tell(o,localActor);
    }

//    public static void main(String[] args) {
//        system = ActorSystem.create("Server", ConfigFactory.load().getConfig("Server"));
//        localActor = system.actorOf(Props.create(CacheActor.class), "cacheActor");
//    }
}
