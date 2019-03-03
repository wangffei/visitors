package com.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.config.Config;
import com.views.Window;

/**
 * 代理爬虫 ， 代理服务器资源来自互联网 ：https://www.xicidaili.com/wn/
 * @author 小王同学
 * @blog   https://blog.csdn.net/weixin_43999566
 */
public class ProxyWrom {
	public static void getProxy(int page , int size){
		Window.getInstance().addColumn(Config.proxyList_header);
		for(int i=0 ; i<size ; i++){
			if(page < 2740){
				Config.pool.execute(new MyThread(++page , Config.proxy_web , i));
			}else{
				Config.page = 0 ;
			}
		}
	}
	
	static class MyThread implements Runnable{
		int page ;
		List<Map<String, String>> list = null ;
		int index = 0 ;
		public MyThread(int page , List<Map<String, String>> list , int index){
			this.page = page ;
			this.list = list ;
			this.index = index ;
		}
		public void run() {
			String proxyUrl = "https://www.kuaidaili.com/free/inha/"+page;		
			BufferedReader reader = null ;
			StringBuffer buf = new StringBuffer() ;
			try {
				Thread.sleep(index*Config.maxSplitTime);
				String ip = "" ;
				int port ;
				URL url = new URL(proxyUrl) ;
				HttpURLConnection con = null ;
				if(list.size() != 0){
					ip = list.size() == 0 ? null : list.get((int)Math.random()*list.size()).get("ip") ;
					port = Integer.parseInt(list.get((int)Math.random()*list.size()).get("port")) ;
					InetSocketAddress addr = new InetSocketAddress(ip , port) ;
					Proxy proxy = new Proxy(Proxy.Type.HTTP , addr);
					con = (HttpURLConnection)url.openConnection(proxy) ;
				}else{
					con = (HttpURLConnection)url.openConnection();
				}
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
				con.setRequestProperty("Referer", "https://www.kuaidaili.com/free/inha/"+(page-1));
				con.setRequestProperty("Connection", "keep-alive");
				con.setRequestProperty("Upgrade-Insecure-Requests", "1");
				con.setConnectTimeout(3000);
				con.setReadTimeout(2000);
//				int statusCode = con.getResponseCode() ;
//				System.out.println(statusCode);
				
				reader = new BufferedReader(new InputStreamReader(con.getInputStream() , "utf-8"));
				String line = "" ;
				while((line = reader.readLine()) != null){
					buf.append(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ;
			} finally{
				if(reader != null){
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			//解析html代码
			Document doc = Jsoup.parse(buf.toString()) ;
			Element ele = doc.getElementById("list").getElementsByTag("table").get(0) ;
			Elements eles = ele.getElementsByTag("tr") ;
			
			for(int i=1 ; i<eles.size() ; i++){
				String ip   = eles.get(i).getElementsByTag("td").get(0).html() ;
				String port = eles.get(i).getElementsByTag("td").get(1).html() ;
				Map<String , String> map = new HashMap() ;
				map.put("ip" , ip) ;
				map.put("port" , port) ;
				map.put("status" , "未知") ;
				if(!Config.cadStatus){
					int row = Window.getInstance().addRow(new String[]{ip , port , "未知"});
					map.put("row", row+"") ;
				}
				Config.putProxy(ip, map);
			}
			Config.pageAdd();
		}		
	}
}
