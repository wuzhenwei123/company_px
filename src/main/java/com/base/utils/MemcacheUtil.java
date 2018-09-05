package com.base.utils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

public class MemcacheUtil {

	private static MemcachedClient memcachedClient;
	
	public static MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}
	public void setMemcachedClient(MemcachedClient memcachedClient) {
		MemcacheUtil.memcachedClient = memcachedClient;
	}
	/**
	 * 没有的时候增加，有的覆盖。
	 * @time : 2015年7月10日 下午2:43:25
	 * @param key
	 * @param exp 有效时间（秒）,0：无限有效
	 * @param value
	 * @throws MemcachedException 
	 * @throws InterruptedException 
	 * @throws TimeoutException 
	 */
	public static boolean set(String key, int exp, Object value) throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.set(key, exp, value);
	}
	/**
	 * 但是如果该<key>已经存在则会操作失败
	 * @time : 2015年7月10日 下午2:51:04
	 * @param key
	 * @param exp
	 * @param value
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public static boolean add(String key,int exp,Object value) throws TimeoutException, InterruptedException, MemcachedException{
		return memcachedClient.add(key, exp, value);
	}
	/**
	 * 获取值
	 * @time : 2015年7月10日 下午2:55:26
	 * @param key
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public static Object get(String key) throws TimeoutException, InterruptedException, MemcachedException{
		return memcachedClient.get(key);
	}
	/**
	 * 
	 * @time : 2015年7月12日 下午4:03:53
	 * @param key
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 * @Description: TODO
	 */
	public static String getString(String key) throws TimeoutException, InterruptedException, MemcachedException{
		Object obj = get(key);
		return obj==null?"":obj.toString();
	}
	/**
	 * 替换,该<key>不存在则操作失败
	 * @time : 2015年7月10日 下午2:56:42
	 * @param key
	 * @param exp 有效时间（秒）,0：无限有效
	 * @param value
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public static boolean replace(String key,int exp,Object value) throws TimeoutException, InterruptedException, MemcachedException{
		return memcachedClient.replace(key, exp, value);
	}
	/**
	 * 
	 * @time : 2015年7月12日 下午3:59:23
	 * @param key
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public static boolean delete(String key) throws TimeoutException, InterruptedException, MemcachedException{
		return memcachedClient.delete(key);
	}
	/**
	 * 清空所有缓存（谨慎操作）
	 * @time : 2015年7月10日 下午2:58:43
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public static void flushAll() throws TimeoutException, InterruptedException, MemcachedException{
		memcachedClient.flushAll();
	}
	/**
	 * 关闭缓存，谨慎操作
	 * @time : 2015年7月10日 下午3:02:45
	 * @throws IOException
	 */
	public static void shutdown() throws IOException{
		memcachedClient.shutdown();
	}
}
