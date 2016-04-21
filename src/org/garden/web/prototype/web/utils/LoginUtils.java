/*
 * Copyright (c) 2004, 2015, Garden Lee. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 
package org.garden.web.prototype.web.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.garden.sysadmin.dao.model.SysDepartment;
import org.garden.sysadmin.dao.model.SysUser;
import org.garden.sysadmin.service.SystemService;
import org.garden.web.prototype.service.utils.SpringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * LoginUtils.java
 *
 * @author Garden
 * create on 2015年4月8日 下午4:16:39
 */
public class LoginUtils {
	private static Log log = LogFactory.getLog(LoginUtils.class);
	
	private static final String USER_SESSION = "LOGIN_USER_SESSION";
	private static final String DEPART_SESSION = "LOGIN_USER_DEPART_SESSION";
	private static final String ALL_DEPART_SESSION = "LOGIN_USER_ALL_DEPART_SESSION";
	
	
	/**
	 * 获取登录用户的用户信息
	 * 
	 * @param request
	 * @param systemService
	 * @return
	 */
	public static SysUser getLoginUser( HttpServletRequest request) {
		SysUser user = null;
		
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		
		Object obj = request.getSession().getAttribute(USER_SESSION);
		
		if ( obj == null) {
			SystemService systemService = (SystemService) SpringUtils.getBean("systemService");
			
			user = systemService.getSysUserByUserCode(username);
			
			if ( user != null) {
				request.getSession().setAttribute(USER_SESSION, user);
				log.debug("缓存用户信息[" + username + "]到session");
			} else{
				log.warn("不存在用户[" + username + "]的信息");
			}
		} else {
			user = (SysUser) obj;
			log.debug("取缓存用户[" + username + "]的信息");
		}
		
		return user;
	}
	
	/**
	 * 获取登录用户的部门信息
	 * @param request
	 * @param systemService
	 * @return
	 */
	public static List<SysDepartment> getUserDeparts( HttpServletRequest request) {
		List<SysDepartment> departs = new ArrayList<SysDepartment>();
		
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		
		Object obj = request.getSession().getAttribute(DEPART_SESSION);
		
		if ( obj == null) {		
			SystemService systemService = (SystemService) SpringUtils.getBean("systemService");
			
			departs = systemService.getDepartmentByUserCode(username);
			
			if ( departs != null
					&& !departs.isEmpty()) {
				request.getSession().setAttribute(DEPART_SESSION, departs);
				log.debug("缓存用户[" + username + "]的机构信息到session");
			} else{
				log.warn("不存在用户[" + username + "]的机构信息");
			}
		} else {
			departs = (List<SysDepartment>) obj;
			log.debug("取缓存用户[" + username + "]的机构信息");
		}
		
		return departs;
	}
	
	/**
	 * 获取用户本部门ID
	 * 
	 * @param request
	 * @param systemService
	 * @return
	 */
	public static Long[] getUserDepartIds( HttpServletRequest request) {
		List<Long> rlt = new ArrayList<Long>();
		List<SysDepartment> depts = getUserDeparts(request);
		
		for ( SysDepartment dept : depts) {
			rlt.add(dept.getDepartId());
		}
		
		return rlt.toArray(new Long[rlt.size()]);
	}
	
	/**
	 * 获取用户本级及下级的所有部门
	 * 
	 * @param request
	 * @param systemService
	 * @return
	 */
	public static List<SysDepartment> getUserDepartTree( HttpServletRequest request) {
		List<SysDepartment> departs = new ArrayList<SysDepartment>();
		
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		
		Object obj = request.getSession().getAttribute(ALL_DEPART_SESSION);
		
		if ( obj == null) {		
			SystemService systemService = (SystemService) SpringUtils.getBean("systemService");
			
			departs = systemService.getDepartmentByUserCode(username);
			
			getLowerDepartments(departs, systemService);
			
			if ( departs != null
					&& !departs.isEmpty()) {
				request.getSession().setAttribute(ALL_DEPART_SESSION, departs);
				log.debug("缓存用户[" + username + "]的下级机构信息到session");
			} else{
				log.warn("不存在用户[" + username + "]的下级机构信息");
			}
		} else {
			departs = (List<SysDepartment>) obj;
			log.debug("取缓存用户[" + username + "]的下级机构信息");
		}
		
		return departs;
	}

	public static void refreshtUserDepartTree( HttpServletRequest request) {
		request.getSession().removeAttribute(ALL_DEPART_SESSION);
		getUserDepartTree(request);
	}
	/**
	 * 获取用户本级及下级的所有部门ID
	 * 
	 * @param request
	 * @param systemService
	 * @return
	 */
	public static Long[] getUserDepartIdTree( HttpServletRequest request) {
		List<Long> rlt = new ArrayList<Long>();
		List<SysDepartment> depts = getUserDepartTree(request);
		
		for ( SysDepartment dept : depts) {
			rlt.add(dept.getDepartId());
		}
		
		return rlt.toArray(new Long[rlt.size()]);
	}
	
	/**
	 * @param departs
	 */
	private static void getLowerDepartments(List<SysDepartment> departs, SystemService systemService) {
		if ( departs != null
				&& !departs.isEmpty()) {
			List<SysDepartment> temp = new ArrayList<SysDepartment>();
			
			for ( SysDepartment depart : departs) {
				Long deptId = depart.getDepartId();
				
				List<SysDepartment> lowerDepts = systemService.getDepartmentByParentId(deptId);
				
				if ( lowerDepts != null
						&& !lowerDepts.isEmpty()) {
//					departs.addAll(lowerDepts);
					getLowerDepartments(lowerDepts, systemService);
					temp.addAll(lowerDepts);
				}
			}
			
			if ( !temp.isEmpty())
				departs.addAll(temp);
		}
	}
}
