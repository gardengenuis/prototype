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
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.garden.sysadmin.dao.model.SysDepartment;
import org.garden.sysadmin.dao.model.SysResource;
import org.garden.sysadmin.dao.model.SysRole;
import org.garden.sysadmin.dao.model.SysRoleResOper;
import org.garden.sysadmin.dao.model.SysRoleResOperItem;
import org.garden.sysadmin.dao.model.SysUser;
import org.garden.sysadmin.dao.model.SysUserDepartment;
import org.garden.sysadmin.dao.model.SysUserDepartmentItem;
import org.garden.sysadmin.dao.model.SysUserRole;
import org.garden.sysadmin.dao.model.SysUserRoleItem;
import org.garden.sysadmin.service.SystemService;
import org.garden.web.prototype.web.vo.JSONResponse;
import org.garden.web.prototype.web.vo.TreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * AjaxController.java
 *
 * @author Garden
 * create on 2014年11月17日 上午10:56:17
 */
@Controller
@RequestMapping("/ajax")
public class AjaxController {
	private static Log log = LogFactory.getLog(AjaxController.class);
	private SystemService systemService;
	
	/**
	 * @param systemService the systemService to set
	 */
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	@RequestMapping(value="/department/tree.do", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public List<TreeVO> departmentTree(@RequestParam(value="id", required=true) Long id) {
		List<TreeVO> jsonResponse = new ArrayList<TreeVO>();
		
		List<SysDepartment> list = systemService.getDepartmentByParentId(id);
		
		for ( SysDepartment sysDepartment : list) {
			TreeVO tree = new TreeVO();
			
			long count = systemService.getDepartmentByParentIdCount(sysDepartment.getDepartId());
			
			tree.setId(sysDepartment.getDepartId());
			tree.setName(sysDepartment.getDepartName());
			if ( count == 0L) {
				tree.setIsParent("false");
			} else {
				tree.setIsParent("true");
			}
			jsonResponse.add(tree);
		}
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/userdepartment/list.do", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<List<SysDepartment>> listUserDepartment(@RequestBody SysUser sysUser) {
		JSONResponse<List<SysDepartment>> jsonResponse = new JSONResponse<List<SysDepartment>>();
		
		List<SysDepartment> deptList = systemService.getDepartmentByUserId(sysUser.getUserId());
		jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
		jsonResponse.setObject(deptList);
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/userdepartment/add.do", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<List<SysDepartment>> addUserDepartment(@RequestBody Map<String, Object> map) {
		JSONResponse<List<SysDepartment>> jsonResponse = new JSONResponse<List<SysDepartment>>();
		List<SysUserDepartment> list = new ArrayList<SysUserDepartment>();
		
		String userId = (String) map.get("userId");
		List<String> departIds = (List<String>) map.get("departId");
		
		for ( String departId: departIds) {
			SysUserDepartment sysUserDepartment = new SysUserDepartment();
			SysUserDepartmentItem sysUserDepartmentItem = new SysUserDepartmentItem();
			
			sysUserDepartmentItem.setDepartId(Long.parseLong(departId));
			sysUserDepartmentItem.setUserId(Long.parseLong(userId));
			
			sysUserDepartment.setSysUserDepartment(sysUserDepartmentItem);
			
			list.add(sysUserDepartment);
		}
				
		systemService.removeAndSaveUserDepartment(Long.parseLong(userId), list);
		
		jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/userrole/list.do", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<List<SysRole>> listUserRole(@RequestBody SysUser sysUser) {
		JSONResponse<List<SysRole>> jsonResponse = new JSONResponse<List<SysRole>>();
		
		List<SysRole> roleList = systemService.getSysRoleByUserId(sysUser.getUserId());
		jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
		jsonResponse.setObject(roleList);
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/userrole/add.do", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<List<SysRole>> addUserRole(@RequestBody Map<String, Object> map) {
		JSONResponse<List<SysRole>> jsonResponse = new JSONResponse<List<SysRole>>();
		List<SysUserRole> list = new ArrayList<SysUserRole>();
		
		String userId = (String) map.get("userId");
		List<String> roleIds = (List<String>) map.get("roleId");
		
		for ( String roleId: roleIds) {
			SysUserRole sysUserRole = new SysUserRole();
			SysUserRoleItem sysUserRoleItem = new SysUserRoleItem();
			
			sysUserRoleItem.setRoleId(Long.parseLong(roleId));
			sysUserRoleItem.setUserId(Long.parseLong(userId));
			
			sysUserRole.setSysUserRole(sysUserRoleItem);
			
			list.add(sysUserRole);
		}
				
		systemService.removeAndSaveUserRole(Long.parseLong(userId), list);
		
		jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/userrole/tree.do", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<List<SysRole>> userRoleTree() {
		JSONResponse<List<SysRole>> jsonResponse = new JSONResponse<List<SysRole>>();
		
		List<SysRole> roles = systemService.getSysRoles();
		
		jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
		jsonResponse.setObject(roles);
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/roleresource/tree.do", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<List<SysRole>> resourceRoleTree() {
		return userRoleTree();
	}
	
	@RequestMapping(value="/roleresource/list.do", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<List<SysRole>> listResourceRole(@RequestBody SysResource sysResource) {
		JSONResponse<List<SysRole>> jsonResponse = new JSONResponse<List<SysRole>>();
		
		List<SysRole> roleList = systemService.getSysRoleByResourceId(sysResource.getResourceId());
		jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
		jsonResponse.setObject(roleList);
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/roleresource/add.do", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<List<SysRole>> addResourceRole(@RequestBody Map<String, Object> map) {
		JSONResponse<List<SysRole>> jsonResponse = new JSONResponse<List<SysRole>>();
		List<SysRoleResOper> list = new ArrayList<SysRoleResOper>();
		
		String resourceId = (String) map.get("resourceId");
		List<String> roleIds = (List<String>) map.get("roleId");
		
		for ( String roleId: roleIds) {
			SysRoleResOper sysRoleResOper = new SysRoleResOper();
			SysRoleResOperItem sysRoleResOperItem = new SysRoleResOperItem();
			
			sysRoleResOperItem.setRoleId(Long.parseLong(roleId));
			sysRoleResOperItem.setResourceId(Long.parseLong(resourceId));
			sysRoleResOperItem.setOperationId(1l);
			
			sysRoleResOper.setSysRoleResOper(sysRoleResOperItem);
			
			list.add(sysRoleResOper);
		}
				
		systemService.removeAndSaveResourceRole(Long.parseLong(resourceId), list);
		
		jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
		
		return jsonResponse;
	}
}
