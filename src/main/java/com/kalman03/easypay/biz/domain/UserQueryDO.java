package com.kalman03.easypay.biz.domain;

import com.kalman03.common.lang.PageQueryDO;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @since 2023-05-20
 * @author kalman03
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class UserQueryDO extends PageQueryDO{
	private static final long serialVersionUID = 1L;
	/** 
	 *	自增主键 
	 */
	private Long id;
	/** 
	 *	用户名 
	 */
	private String userName;
	/** 
	 *	头像地址 
	 */
	private String avatar;
	/** 
	 *	微信openId 
	 */
	private String openId;
	/** 
	 *	最后一次登录时间 
	 */
	private Date lastLoginTime;
	/** 
	 *	用户Tag 
	 */
	private Long userTag;
	/** 
	 *	邀请人 
	 */
	private Long inviteUserId;
	/** 
	 *	用户属性 
	 */
	private String attribute;
	/** 
	 *	创建时间 
	 */
	private Date createTime;
	/** 
	 *	修改时间 
	 */
	private Date updateTime;
	/**
	 * 排序，如：create_time desc
	 */
	private String orderBy;
}