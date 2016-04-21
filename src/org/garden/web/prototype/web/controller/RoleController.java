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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.garden.sysadmin.dao.model.SysRole;
import org.garden.sysadmin.dao.model.SysRoleResOper;
import org.garden.sysadmin.dao.model.SysRoleResOperItem;
import org.garden.sysadmin.dao.model.SysUserRole;
import org.garden.sysadmin.dao.model.SysUserRoleItem;
import org.garden.sysadmin.service.SystemService;
import org.garden.web.prototype.web.utils.FormUtils;
import org.garden.web.prototype.web.utils.LoginUtils;
import org.garden.web.prototype.web.vo.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * RoleController.java
 *
 * @author Garden
 * create on 2014年11月11日 下午8:28:43
 */
@Controller
@RequestMapping("/admin/system")
public class RoleController {
	private static Log log = LogFactory.getLog(RoleController.class);
	private SystemService systemService;
	
	/**
	 * @param systemService the systemService to set
	 */
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	@RequestMapping(value="/role/list.do", method = RequestMethod.GET)
	public String listRole(Model model, HttpServletRequest request) {
		List<SysRole> roles = systemService.getSysRolesByDepartIds(LoginUtils.getUserDepartIdTree(request));
				
		model.addAttribute("roles", roles);
		
		return "system/listRole";
	}
	
	@RequestMapping(value="/role/edit.do", method = RequestMethod.GET)
	public String editRole(Model model) {
		List<SysRole> roles = systemService.getSysRoles();
		
		model.addAttribute("roles", roles);
		
		return "system/editRole";
	}
	
	@RequestMapping(value="/role/edit.do", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<SysRole> editRole(@RequestBody SysRole sysRole) throws Exception {
		JSONResponse<SysRole> jsonResponse = new JSONResponse<SysRole>();
		
		if ( StringUtils.isNotEmpty(sysRole.getRoleCode())) {
			try {
				systemService.saveSysRole(sysRole);
				
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
			} catch( Exception e) {
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_ERROR);
				jsonResponse.setMsg(e.getCause() + ":" + e.getMessage());
			}
		}
		jsonResponse.setObject(sysRole);
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/role/add.do", method = {RequestMethod.POST,RequestMethod.GET})
	public String addRole(@ModelAttribute SysRole sysRole, Model model) {
		
		if ( StringUtils.isNotEmpty(sysRole.getRoleCode())) {
			systemService.saveSysRole(sysRole);
			
			return "redirect:/admin/system/role/list.do";
		}
		
		return "system/addRole";
	}
	
	@RequestMapping(value="/role/delete.do", method = RequestMethod.DELETE,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<SysRole> deleteRole(@RequestBody SysRole sysRole) {
		JSONResponse<SysRole> jsonResponse = new JSONResponse<SysRole>();
		
		if ( sysRole.getRoleId() != null) {
			try {
				Long[] roleIds = new Long[] {sysRole.getRoleId()};
				systemService.removeSysRoles(roleIds);
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
			} catch( Exception e) {
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_ERROR);
				jsonResponse.setMsg(e.getCause() + ":" + e.getMessage());
			}
		} else {
			jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_ERROR);
			jsonResponse.setMsg("角色ID为空");
		}
		
		jsonResponse.setObject(sysRole);
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/role/popup/pickRole.do", method = RequestMethod.GET)
	public String pickRole(Model model, HttpServletRequest request) {
		String resourceId = request.getParameter("resourceId");
		String userId = request.getParameter("userId");
		
		List<SysRole> selectedRole = null;
		List<SysRole> allRole = null;
		Map<Long, SysRole> selectetRoleMap = null;
		
		try {
			allRole = systemService.getSysRoles();
			
			if(StringUtils.isNotEmpty(userId)) {
				selectedRole = systemService.getSysRoleByUserId(Long.parseLong(userId));
			} else if(StringUtils.isNotEmpty(resourceId)) {
				selectedRole = systemService.getSysRoleByResourceId(Long.parseLong(resourceId));
			}
			
			selectetRoleMap = sysRole2Map(selectedRole);
		} catch( Exception e) {
			FormUtils.reponseError(e.getMessage(), model);
			log.error(e);
		}
		
		model.addAttribute("resourceId", resourceId);
		model.addAttribute("userId", userId);
		
		model.addAttribute("roles", allRole);
		model.addAttribute("selectetRole", selectetRoleMap);
		
		return "system/pickRole";
	}
	
	@RequestMapping(value="/role/popup/updatePickRole.do", method = RequestMethod.POST)
	public String updatePickRole(RedirectAttributes model, HttpServletRequest request) {
		String resourceId = request.getParameter("resourceId");
		String userId = request.getParameter("userId");
		String[] roleIds = request.getParameterValues("roleId");
		String param = null;
		
		try {
			if(StringUtils.isNotEmpty(userId)) {	// 用户-角色
				List<SysUserRole> list = new ArrayList<SysUserRole>();
				
				if( roleIds != null) {
					for ( String roleId: roleIds) {
						SysUserRole sysUserRole = new SysUserRole();
						SysUserRoleItem sysUserRoleItem = new SysUserRoleItem();
						
						sysUserRoleItem.setRoleId(Long.parseLong(roleId));
						sysUserRoleItem.setUserId(Long.parseLong(userId));
						
						sysUserRole.setSysUserRole(sysUserRoleItem);
						
						list.add(sysUserRole);
					}
				}
				
				systemService.removeAndSaveUserRole(Long.parseLong(userId), list);
				param = "userId=" + userId;
				
			} else if(StringUtils.isNotEmpty(resourceId)) { // 资源-角色
				List<SysRoleResOper> list = new ArrayList<SysRoleResOper>();
				
				if( roleIds != null) {
					for ( String roleId: roleIds) {
						SysRoleResOper sysRoleResOper = new SysRoleResOper();
						SysRoleResOperItem sysRoleResOperItem = new SysRoleResOperItem();
						
						sysRoleResOperItem.setRoleId(Long.parseLong(roleId));
						sysRoleResOperItem.setResourceId(Long.parseLong(resourceId));
						sysRoleResOperItem.setOperationId(1l);
						
						sysRoleResOper.setSysRoleResOper(sysRoleResOperItem);
						
						list.add(sysRoleResOper);
					}
				}
				
				systemService.removeAndSaveResourceRole(Long.parseLong(resourceId), list);
				param = "resourceId=" + resourceId;
			}
			
			FormUtils.redirectSucceed( model);
		} catch(Exception e) {
			FormUtils.redirectError(e.getMessage(), model);
			log.error(e);
		}
		
		return "redirect:/admin/system/role/popup/pickRole.do?" + param;
	}

	/**
	 * @param selectedRole
	 * @return
	 */
	private Map<Long, SysRole> sysRole2Map(List<SysRole> selectedRole) {
		Map<Long, SysRole> rlt = new HashMap<Long, SysRole>();
		
		for(SysRole role : selectedRole) {
			rlt.put(role.getRoleId(), role);
		}
		
		return rlt;
	}
	
	
}
