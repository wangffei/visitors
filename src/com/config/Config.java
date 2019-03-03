package com.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Config {
	//代理列表标题
	public static final String[] proxyList_header = new String[]{"代理ip","端口","状态"} ;
	//博客列表标题
	public static final String[] blogList_header = new String[]{"序号","名字","次数"};
	//默认显示代理列表
	public static boolean cadStatus = false ;
	//保存博客列表
	public static Map<String , Map<String , String>> blogList = new HashMap();
	//保存代理信息
	public static Map<String , Map<String , String>> proxyList = new HashMap();
	//最大线程数
	public static Integer threadCount = 80 ;
	//当前所爬取代理页码
	public static Integer page = 0 ;
	//每次爬取代理页数
	public static Integer size = 10 ;
	//是否可以开始刷访客了
	public static boolean canStart = false ;
	//每轮最长等待时长
	public static Integer maxWaitTime = 30000 ;
	//请求代理页间隔时长
	public static Integer maxSplitTime = 1800 ;
	//进入代理网站所需要的代理
	public static List<Map<String, String>> proxy_web = new ArrayList<>() ;
	//可用代理缓存
	public static Set<String> cache = new HashSet<>();
	//当前活跃线程数
	public static Integer activeThread = 0 ;
	//没个多久进行调用缓存刷（合理的次数课大大提高效率）
	public static int spaceCount = 4 ;
	//读取配置文件
	static{
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("./config.conf"));
			String line = "";
			int i = 1 ;
			while((line = reader.readLine()) != null){
				line = line.trim();
				if(!line.equals("") && !line.startsWith("#")){
					if(line.startsWith("blog")){
						String[] strs = line.split("==>")[1].trim().split("=");
						Map<String, String> map = new HashMap() ;
						map.put("name" , strs[0].trim()) ;
						map.put("count" , 0+"") ;
						blogList.put(strs[1].trim(), map) ;
					}else if(line.startsWith("threadCount")){
						String[] strs = line.split("=");
						threadCount = Integer.parseInt(strs[1].trim());
					}else if(line.startsWith("proxy")){
						String[] strs = line.split("==>")[1].trim().split(":");
						Map<String, String> map = new HashMap() ;
						map.put("ip" , strs[0].trim()) ;
						map.put("port" , 0+"") ;
						proxy_web.add(map) ;
					}else if(line.startsWith("maxWaitTime")){
						String[] strs = line.split("=") ;
						maxWaitTime = Integer.parseInt(strs[1].trim()) ;
					}else if(line.startsWith("maxSplitTime")){
						String[] strs = line.split("=") ;
						maxSplitTime = Integer.parseInt(strs[1].trim()) ;
					}else if(line.startsWith("size")){
						String[] strs = line.split("=");
						size = Integer.parseInt(strs[1].trim()) ;
					}else if(line.startsWith("spaceCount")){
						String[] strs = line.split("=");
						spaceCount = Integer.parseInt(strs[1].trim()) ;
					}
				}
			}
		} catch (Exception e) {
			System.exit(-1);
		} finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	static{
		BufferedReader reader = null ;
		try {
			reader = new BufferedReader(new FileReader("./page.mem")) ;
			String line = "" ;
			while((line = reader.readLine()) != null){
				line = line.trim() ;
				if(line.startsWith("page")){
					String[] strs = line.split("=");
					page = Integer.parseInt(strs[1].trim()) ;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//线程池初始化
	public static ExecutorService pool =  Executors.newFixedThreadPool(threadCount);
	//page自增
	public synchronized static void pageAdd(){
		page ++ ;
	}
	//活跃线程自增
	public synchronized static void activeAdd(){
		activeThread++ ;
	}
	//博客访问量自增
	public synchronized static void blogAdd(String key){
		int count = Integer.parseInt(blogList.get(key).get("count")) ;
		blogList.get(key).put("count", (++count)+"") ;
	}
	public synchronized static void putProxy(String key , Map<String , String> value){
		proxyList.put(key, value) ;
	} 
}
