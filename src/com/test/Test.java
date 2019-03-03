package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
	static ExecutorService pool = Executors.newFixedThreadPool(5) ;
	public static void main(String[] args) {
		for(int i = 0 ; i < 1000 ; i++){
			pool.execute(new MyThread(i));
		}
		pool.shutdown();
		System.out.println("laile");
	}
	static class MyThread implements Runnable{
		Integer i = 0 ;
		
		public MyThread(Integer i){
			this.i = i ;
		}
		
		@Override
		public void run() {
			System.out.println(i);
		}	
	}
}	
