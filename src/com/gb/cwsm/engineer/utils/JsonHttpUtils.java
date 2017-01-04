package com.gb.cwsm.engineer.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.greenrobot.eventbus.EventBus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.gb.cwsm.engineer.AppApplication;
import com.gb.cwsm.engineer.entity.PersistentCookieStore;

public class JsonHttpUtils {

	/**验证手机号*/
	public final static int GET_CHECK_MOBILE = 1000; 
	/**登陆*/
	public final static int LOGING_FLAG = 1001; 
	/**订单信息（优惠券等）*/
	public final static int REQUEST_COUPON_FLAG = 1002; 
	/**用户密码登陆（注册成功后系统自动后天登陆）*/
	public final static int LOGING_BY_PASS = 1003;
	/**获取短信验证码*/
	public final static int GET_DXYZ_CODE = 1004;
	/**用户短信登陆*/
	public final static int LOGING_BY_DX = 1005;
	/**用户短信注册*/
	public final static int REGISTER_BY_DX = 1006;
	/**用户信息修改*/
	public final static int MODIFY_USER_MSG = 1007;
	/**注册验证手机号*/
	public final static int R_GET_CHECK_MOBILE = 1008;
	/**新订单透传信息*/
	public final static int NEW_ORDER_PAYLOAD=1009;
	/**订单信息查询*/
	public final static int REQUEST_ORDER_INFO=1010;
	
	
	
	/**
	 * 下载html源文件
	 * @param path
	 * @return
	 */
	@SuppressLint("NewApi")
	public static String getHtmlString(String minterface, List<String> parametes, int typecode) {
		String result = "";
		BufferedReader in = null;
		String path = minterface;
		/** 拼接请求地址 **/
		if (parametes != null) {
			path = minterface + "?";

			for (int i = 0; i < parametes.size(); i++) {
				path = path + parametes.get(i);
				if (i % 2 != 1) {
					path = path + "=";
				} else if (i < (parametes.size() - 1)) {
					path = path + "&";
				}
			}
		}
		Log.i("requst_code", "提交地址：" + path);
		try {
			 // 定义HttpClient  
            HttpClient client = new DefaultHttpClient();  
            // 实例化HTTP方法  
            HttpGet request = new HttpGet();  
            request.setURI(new URI(path));  
            HttpResponse response;
            Log.i("requst_code", "开始添加头："+typecode);
//            if (GET_COUPON==typecode) {
//            	PersistentCookieStore cookieStore = new PersistentCookieStore(AppApplication.getInstance().getApplicationContext());  
//            	((AbstractHttpClient) client).setCookieStore(cookieStore);  
//            	 Log.i("requst_code", "请求内容头："+cookieStore);
//            	response = client.execute(request); 
//			}
//            
           response = client.execute(request);  
            
            in = new BufferedReader(new InputStreamReader(response.getEntity()  
                    .getContent()));  
            StringBuffer sb = new StringBuffer("");  
            String line = "";  
            String NL = System.getProperty("line.separator");  
            while ((line = in.readLine()) != null) {  
                sb.append(line + NL);  
            }  
            in.close();  
            result = sb.toString();  
			
		} catch (Exception e) {
			NameValuePair valuePair=new BasicNameValuePair(typecode+"", result);
			EventBus.getDefault().post(valuePair);
			e.printStackTrace();
		}
		if (!result.isEmpty()) {
			NameValuePair valuePair=new BasicNameValuePair(typecode+"", result);
			EventBus.getDefault().post(valuePair);
		}
		return result;
	}

	/**
	 * 读取服务器数据
	 * 
	 * @param inputStream
	 * @return
	 */
	private static String readHtmlData(InputStream inputStream) throws Exception {
		String htmlString = "";
		// 缓冲区
		byte buffer[] = new byte[1024];
		int temp = 0;
		// 内存输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((temp = inputStream.read(buffer)) != -1) {
			out.write(buffer, 0, temp);
		}
		out.close();
		inputStream.close();
		// 从内存中得到下载好的数据
		byte data[] = out.toByteArray();
		htmlString = new String(data, 0, data.length);
		return htmlString;
	}


	@SuppressLint("NewApi")
	public static void doPost(String minterface, List<NameValuePair> params, int typecode) {
		// HttpClientTool.network();
		String result = null;
		HttpPost httpPost = new HttpPost(minterface);
		// httpPost.setHeaders(HttpClientTool.getHeader());
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		String str ="";
		for (NameValuePair nv:params) {
			str+=nv.getName()+"="+nv.getValue()+":";
		}
		Log.i("JsonHttpUtils", typecode+"请求参数为："+str);
		
		try {
			Log.i("requst_code", "开始请求！");
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			/** 保持会话Session **/
			/** 设置Cookie **/
			// MyHttpCookies li = new MyHttpCookies(context);
			// Constants.li = new MyHttpCookies(context);
			// li.AddCookies(httpPost);
			// Constants.li.AddCookies(httpPost);
			/** 保持会话Session end **/

			HttpResponse httpResp ;
			if (typecode == GET_DXYZ_CODE || typecode == LOGING_BY_PASS ||typecode == GET_CHECK_MOBILE||typecode == LOGING_BY_DX) { }
			else {
				PersistentCookieStore cookieStore = new PersistentCookieStore(AppApplication.getInstance().getApplicationContext());  
            	((AbstractHttpClient) httpClient).setCookieStore(cookieStore);  
            	 Log.i("requst_code", "请求内容头："+cookieStore);
			}
			httpResp=httpClient.execute(httpPost);
			saveCookie(httpClient, typecode);
			if (httpResp.getStatusLine().getStatusCode() == 200) {
				// li.saveCookies(httpResp);
				// Constants.li.saveCookies(httpResp);
				result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
				Log.i("requst_code", "HttpPost方式请求成功，返回数据如下：");
				Log.i("requst_code", result);
				/** 执行成功之后得到 **/
				/** 成功之后把返回成功的Cookis保存APP中 **/
				// 请求成功之后，每次都设置Cookis。保证每次请求都是最新的Cookis
				// li.setuCookie(httpClient.getCookieStore());
				// Constants.li.setuCookie(httpClient.getCookieStore());
				/** 设置Cookie end **/
				// doPost(params,url);
			} else {
				Log.i("HttpPost", "HttpPost方式请求失败");
				System.out.println("0000===>" + EntityUtils.toString(httpResp.getEntity(), "UTF-8"));
				result = "{success:false,msg:'请求失败'}";
			}
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			result = "{success:false,msg:'TIME_OUT' }";
			// doPost(params,minterface);
		} catch (UnsupportedEncodingException e) {
			result = "{success:false,msg:'请求失败'}";
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			result = "{success:false,msg:'请求失败'}";
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpPost.abort();
			httpClient.getConnectionManager().shutdown();
		}
		Log.i("JsonHttpUtils", typecode+"请求相应数据为："+result);
		if (!TextUtils.isEmpty(result)) { 
			NameValuePair valuePair=new BasicNameValuePair(typecode+"", result);
			EventBus.getDefault().post(valuePair);
		}
	}

	private static void saveCookie(DefaultHttpClient httpClient, int typecode) {
		if (typecode == LOGING_BY_DX || typecode == LOGING_BY_PASS ) {
			PersistentCookieStore myCookieStore = AppApplication.getInstance().getPersistentCookieStore();  
	        List<Cookie> cookies = httpClient.getCookieStore().getCookies();  
	        for (Cookie cookie:cookies){  
	            myCookieStore.addCookie(cookie); 
	            Log.i("requst_code", "登陆成功,保存cookies"+cookies);
	        } 
		}
	}
}
