package com.paradeto.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Configuration;

import org.springframework.stereotype.Service;

import com.paradeto.entity.Star;

import java.util.*;
@Service
public class EhcacheService {
	private static Cache starCache = CacheManager.create("src/main/resources/conf/ehcache.xml").getCache("starCache");
	public static Integer limit = 3;
	/*
	 * 得到所有明星
	 */
	public List<Star> getAllStarts(){
		List<Integer> keys = starCache.getKeys();
		List<Star> stars = new ArrayList<Star>();
		for(Integer k:keys){
			Element elem = starCache.get(k);
			Star s = (Star) elem.getValue();
			stars.add(s);
		}
		return stars;
	}

	public Star getOne(Integer id){
		Element elem = starCache.get(id);
		Star s = (Star) elem.getValue();
		return s;
	}

	public void saveOrUpdate(Star star){
		Element elem = new Element(star.getId(),star);
		starCache.put(elem);
	}

	public void saveBatch(List<Star> stars){
		for(Star s:stars){
			saveOrUpdate(s);
		}
	}

	public void del(Integer id){
		starCache.remove(id);
	}

	public boolean isFull(){
		return starCache.getSize()==limit?true:false;
	}

	public boolean isEmpty(){
		return starCache.getSize()==0?true:false;
	}

	public void clearAll(){
		starCache.removeAll();
	}

	public Integer getSize(){
		return starCache.getSize();
	}
}
