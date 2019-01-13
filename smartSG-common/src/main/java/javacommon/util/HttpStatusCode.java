package javacommon.util;

/**
 * Created by Andy on 2016/6/21.
 */
public enum HttpStatusCode {

	SUCCESS(200), // 成功
	FAIL(400), // 失败
	INTERNAL_SERVER_ERROR(500), // 服务器错误
	NOT_EXIST_ERROR(501), // 记录不存在
	EXIST_ERROR(502), // 已存在记录
//	LOGIN_ERROR(503), // 登陆错误
	VALIDATOR_ERROR(504), // 参数不合法
	TIME_OUT_ERROR(505), // 参数超时
//	BUSYNESS_ERROR(506), // 请求过于频繁
//	IM_SERVER_ERROR(507), // IM服务器错误
	NOT_MATCH_ERROR(508),// 数据不匹配
	LOGIN_OVER_ERROR(509),//登录超时
	TOKEN_ERROR(510), //token参数不存在
	NOT_INITIALIZATION(511),//不能初始化
	CAN_INITIALIZATION(201),//可以初始化

	ILLEGAL_ACTION(403),//非法操作，拒绝执行
	
	PAY_FAIL(512);//订单支付失败
	private int value;

	HttpStatusCode(int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}
}
