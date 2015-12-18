/*
 * Copyright (c) 2004, 2014, Garden Lee. All rights reserved.
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
package org.garden.web.prototype.web.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.garden.sysadmin.dao.model.SysDepartment;
import org.garden.sysadmin.dao.model.SysRole;
import org.garden.sysadmin.dao.model.SysUser;
import org.garden.sysadmin.service.SystemService;
import org.garden.web.prototype.web.vo.JSONResponse;
import org.garden.web.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * UserController.java
 *
 * @author Garden
 * create on 2014年11月11日 下午3:37:49
 */
@Controller
@RequestMapping("/admin/system")
public class UserController {
	private static Log log = LogFactory.getLog(UserController.class);
	private SystemService systemService;
	
	/**
	 * @param systemService the systemService to set
	 */
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	@RequestMapping(value="/user/list.do", method = RequestMethod.GET)
	public String listUser(Model model) {
		List<SysUser> users = systemService.getSysUsers();
		
		for ( SysUser user : users) {
			List<SysDepartment> departments = systemService.getDepartmentByUserId(user.getUserId());
			user.setDepartments(departments);
			
			List<SysRole> roles = systemService.getSysRoleByUserId(user.getUserId());
			user.setRoles(roles);
		}
		
		model.addAttribute("users", users);
		
		return "system/listUser";
	}
	
	@RequestMapping(value="/user/edit.do", method = RequestMethod.GET)
	public String editUser(Model model) {
		List<SysUser> users = systemService.getSysUsers();
		
		for ( SysUser user : users) {
			List<SysDepartment> departments = systemService.getDepartmentByUserId(user.getUserId());
			user.setDepartments(departments);
			
			List<SysRole> roles = systemService.getSysRoleByUserId(user.getUserId());
			user.setRoles(roles);
		}
		
		model.addAttribute("users", users);
		
		return "system/editUser";
	}
	
	@RequestMapping(value="/user/edit.do", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<SysUser> editUser(@RequestBody SysUser sysUser) throws Exception {
		JSONResponse<SysUser> jsonResponse = new JSONResponse<SysUser>();
		
		if ( StringUtils.isNotEmpty(sysUser.getUserName())) {
			try {
				SysUser oldUser = systemService.getUserById(sysUser.getUserId());
				
				if ( !oldUser.getPassword().equals(sysUser.getPassword())) {
					oldUser.setPassword(SecurityUtils.encodeViaSHA(sysUser.getPassword(), oldUser.getUserCode()));
				}
				
				oldUser.setUserName(sysUser.getUserName());
				oldUser.setStatus(sysUser.getStatus());
				
				systemService.saveSysUser(oldUser);
				
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
			} catch( Exception e) {
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_ERROR);
				jsonResponse.setMsg(e.getCause() + ":" + e.getMessage());
			}
		}
		jsonResponse.setObject(sysUser);
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/user/add.do", method = RequestMethod.GET)
	public String addUser(@ModelAttribute SysUser sysUser, Model model) {
		
		if ( StringUtils.isNotEmpty(sysUser.getUserCode())) {
			
			SysUser existUser = systemService.getSysUserByUserCode(sysUser.getUserCode());
			
			if ( existUser == null) {
				sysUser.setPassword(SecurityUtils.encodeViaSHA(sysUser.getPassword(), sysUser.getUserCode()));
				systemService.saveSysUser(sysUser);
				
				return "redirect:/admin/system/user/list.do";
			} else {
				model.addAttribute("message", "已存在相同编号的用户");
			}
		}
		
		return "system/addUser";
	}
	
	@RequestMapping(value="/user/delete.do", method = RequestMethod.DELETE,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<SysUser> deleteUser(@RequestBody SysUser sysUser) {
		JSONResponse<SysUser> jsonResponse = new JSONResponse<SysUser>();
		
		if ( sysUser.getUserId() != null) {
			try {
				Long[] userIds = new Long[] {sysUser.getUserId()};
				systemService.removeSysUsers(userIds);
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
			} catch( Exception e) {
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_ERROR);
				jsonResponse.setMsg(e.getCause() + ":" + e.getMessage());
			}
		} else {
			jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_ERROR);
			jsonResponse.setMsg("用户ID为空");
		}
		
		jsonResponse.setObject(sysUser);
		
		return jsonResponse;
	}
}
