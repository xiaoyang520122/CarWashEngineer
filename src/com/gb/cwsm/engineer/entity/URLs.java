package com.gb.cwsm.engineer.entity;

import java.io.Serializable;

/**
 * 接口URL实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21-
 */
public class URLs implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public final static String HOST = "http://202.170.139.223:8044/app";
	/**发送验证码**/
	public final static String GET_DXYZ_CODE = HOST + "/common/send_sms.jhtml";
	/**手机号检测**/
	public final static String GET_CHECK_MOBILE = HOST + "/register/check_mobile.jhtml";
	/**用户短信登陆验证**/
	public final static String POST_DX_LOGING = HOST + "/login/sms.jhtml";
	/**用户普通登陆**/
	public final static String LOGING_BY_PASS = HOST + "/login/submit.jhtml";
	/**用户短信注册**/
	public final static String POST_DX_REGISTER = HOST + "/register/submit_mobile.jhtml";
	/**用户信息修改**/
	public final static String MODIFY_USER_MSG = HOST + "/member/profile/update.jhtml";
	/**订单信息查询**/
	public final static String REQUEST_ORDER_INFO = HOST + "/member/order/view.jhtml";
	
	
	/**http://202.170.139.223:8044/wap/engineer/get_engineers.jhtml?latitude=22.557473&longitude=114.019824&radius=3
	/**
	 * 验证码类型： 
	 * 会员登录:memberLogin, 
	 * 会员注册:memberRegister, 
	 * 后台登录:adminLogin, 
	 * 找回密码:findPassword,
	 * 重置密码 :resetPassword,
	 * 修改手机号 :modifyMobile,
	 * 其它:other
	 */
	public final static String  PARAME_REGISTER="memberRegister";//请求验证码类型-注册
	public final static String  PARAME_LOGING="memberLogin";//请求验证码类型-登陆

	// public final static String HOST =
	// "carlife.online4s.com:7071";//online4s.baoxiansoft.com:7008";//192.168.1.213
	// www.oschina.net
	// public final static String HTTP = "http://";
	// public final static String HTTPS = "https://";
	//
	// private final static String URL_SPLITTER = "/";
	// private final static String URL_UNDERLINE = "_";
	//
	// private final static String URL_SUFFIX = ".jhtml";
	//
	// public final static String URL_API_HOST = HTTP + HOST + URL_SPLITTER;
	//
	// public final static String API_TOKEN = URL_API_HOST +
	// "wap/login/check"+URL_SUFFIX;
	// public final static String LOGIN_HTTP = URL_API_HOST +
	// "wap/login/submit"+URL_SUFFIX;
	//
	// public final static String GET_ENGINEERS = URL_API_HOST +
	// "wap/engineer/get_engineers"+URL_SUFFIX;
	// public final static String GET_ENGINEER = URL_API_HOST +
	// "wap/engineer/get_engineer"+URL_SUFFIX;

	public final static int URL_OBJ_TYPE_OTHER = 0x000;
	public final static int URL_OBJ_TYPE_NEWS = 0x001;
	public final static int URL_OBJ_TYPE_SOFTWARE = 0x002;
	public final static int URL_OBJ_TYPE_QUESTION = 0x003;
	public final static int URL_OBJ_TYPE_ZONE = 0x004;
	public final static int URL_OBJ_TYPE_BLOG = 0x005;
	public final static int URL_OBJ_TYPE_TWEET = 0x006;
	public final static int URL_OBJ_TYPE_QUESTION_TAG = 0x007;

}
