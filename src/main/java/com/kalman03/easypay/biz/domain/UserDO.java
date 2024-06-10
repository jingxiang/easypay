package com.kalman03.easypay.biz.domain;

import java.util.Date;
import lombok.Data;

/**
 * @since 2023-05-20
 * @author kalman03
 */
@Data
public class UserDO{

	/** 
	 * 自增主键 
	 */
	private Long id;
	/** 
	 * 用户名 
	 */
	private String userName;
	/** 
	 * 头像地址 
	 */
	private String avatar;
	/** 
	 * 微信openId 
	 */
	private String openId;
	/** 
	 * 最后一次登录时间 
	 */
	private Date lastLoginTime;
	/** 
	 * 用户Tag 
	 */
	private Long userTag;
	/** 
	 * 邀请人 
	 */
	private Long inviteUserId;
	/** 
	 * 用户属性 
	 */
	private String attribute;
	/** 
	 * 创建时间 
	 */
	private Date createTime;
	/** 
	 * 修改时间 
	 */
	private Date updateTime;

}