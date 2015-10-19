package com.paradeto.controller;

import com.paradeto.actor.message.ClearCache;
import com.paradeto.service.ActorService;
import com.paradeto.service.EhcacheService;
import com.paradeto.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paradeto.entity.Star;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class TestCache {
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private ActorService actorService;
	/*
	 * 默认页面，列出数据表
	 */
	@RequestMapping(value={"/","/list"},method=RequestMethod.GET)
	public String list(ModelMap map){
		List<Star> stars=new ArrayList<Star>();
		if(!ehcacheService.isFull()){
			stars = redisService.getStarts4Ehcache(ehcacheService.limit);
			if(stars!=null)
				ehcacheService.saveBatch(stars);
		}else
			stars = ehcacheService.getAllStarts();
		map.addAttribute("stars", stars);
		map.addAttribute("nEhcache", ehcacheService.getSize());
		map.addAttribute("nRedis", redisService.getSize());
		return "view/list";
	}

	@RequestMapping(value = "/addPage",method=RequestMethod.GET)
	public String addPage(){
		return "view/add";
	}

	@RequestMapping(value = "/updatePage",method=RequestMethod.GET)
	public String updatePage(Integer id,ModelMap map){
		Star star = ehcacheService.getOne(id);
		map.addAttribute("star", star);
		return "view/add";
	}

	@RequestMapping(value = "/add",method=RequestMethod.POST)
	public String add(Star star,ModelMap map){
		redisService.saveOrUpdate(star);
		List<Star> stars=new ArrayList<Star>();
		if(ehcacheService.isFull()){
			ehcacheService.clearAll();
			stars = redisService.getStarts4Ehcache(ehcacheService.limit);
			ehcacheService.saveBatch(stars);
		}else{
			ehcacheService.saveOrUpdate(star);
			stars = ehcacheService.getAllStarts();
		}
		// 通知另一服务器需要清空二级缓存
		actorService.sendMessage(new ClearCache());
		map.addAttribute("stars", stars);
		map.addAttribute("nEhcache", ehcacheService.getSize());
		map.addAttribute("nRedis", redisService.getSize());
		return "view/list";
	}

	@RequestMapping(value = "/update",method=RequestMethod.GET)
	public String update(Integer id,ModelMap map){
		Star s = ehcacheService.getOne(id);
		// 更新一级缓存
		redisService.saveOrUpdate(s);
		// 更新二级缓存
		List<Star> stars=new ArrayList<Star>();
		stars = redisService.getStarts4Ehcache(ehcacheService.limit);
		ehcacheService.saveBatch(stars);
		// 通知另一服务器需要清空二级缓存
		actorService.sendMessage(new ClearCache());
		map.addAttribute("stars", stars);
		map.addAttribute("nEhcache", ehcacheService.getSize());
		map.addAttribute("nRedis", redisService.getSize());
		return "view/list";
	}

	@RequestMapping(value = "/del",method=RequestMethod.GET)
	public String del(Integer id,ModelMap map){
		redisService.del(id);
		ehcacheService.clearAll();
		List<Star> stars=new ArrayList<Star>();
		stars = redisService.getStarts4Ehcache(ehcacheService.limit);
		ehcacheService.saveBatch(stars);
		// 通知另一服务器需要清空二级缓存
		actorService.sendMessage(new ClearCache());
		map.addAttribute("stars", stars);
		map.addAttribute("nEhcache", ehcacheService.getSize());
		map.addAttribute("nRedis", redisService.getSize());
		return "view/list";
	}

	@RequestMapping(value = "/testActor",method=RequestMethod.GET)
	public String testActor(){
		actorService.sendMessage("hello");
		return "view/list";
	}
}
