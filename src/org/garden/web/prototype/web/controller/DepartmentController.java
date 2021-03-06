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
import org.garden.sysadmin.dao.model.SysDepartment;
import org.garden.sysadmin.dao.model.SysRoleDepartment;
import org.garden.sysadmin.dao.model.SysRoleDepartmentItem;
import org.garden.sysadmin.dao.model.SysUserDepartment;
import org.garden.sysadmin.dao.model.SysUserDepartmentItem;
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
 * SystemController.java
 *
 * @author Garden
 * create on 2014年11月10日 下午7:27:56
 */
@Controller
@RequestMapping("/admin/system")
public class DepartmentController {
	private static Log log = LogFactory.getLog(DepartmentController.class);
	private SystemService systemService;
	
	/**
	 * @param systemService the systemService to set
	 */
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	@RequestMapping(value="/department/list.do", method = RequestMethod.GET)
	public String listDepartment(Model model, HttpServletRequest request) {
		List<SysDepartment> departments = systemService.getDepartments(LoginUtils.getUserDepartIdTree(request));
		
		model.addAttribute("departments", departments);
		
		return "system/listDepartment";
	}
	
	@RequestMapping(value="/department/edit.do", method = RequestMethod.GET)
	public String editDepartment(Model model, HttpServletRequest request) {
		List<SysDepartment> departments = systemService.getDepartments(LoginUtils.getUserDepartIdTree(request));
		
		model.addAttribute("departments", departments);
		model.addAttribute("departs", departments);
		
		return "system/editDepartment";
	}
	
	@RequestMapping(value="/department/edit.do", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<SysDepartment> editDepartment(@RequestBody SysDepartment sysDepartment) throws Exception {
		JSONResponse<SysDepartment> jsonResponse = new JSONResponse<SysDepartment>();
		
		if ( StringUtils.isNotEmpty(sysDepartment.getDepartCode())) {
			try {
				systemService.saveSysDepartment(sysDepartment);
				
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
			} catch( Exception e) {
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_ERROR);
				jsonResponse.setMsg(e.getCause() + ":" + e.getMessage());
			}
		}
		jsonResponse.setObject(sysDepartment);
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/department/add.do", method = {RequestMethod.POST,RequestMethod.GET})
	public String addDepartment(@ModelAttribute SysDepartment sysDepartment, Model model, HttpServletRequest request) {
		
		List<SysDepartment> departments = systemService.getDepartments(LoginUtils.getUserDepartIdTree(request));
		
		model.addAttribute("departments", departments);
		
		if ( StringUtils.isNotEmpty(sysDepartment.getDepartCode())) {
			systemService.saveSysDepartment(sysDepartment);
			
			LoginUtils.refreshtUserDepartTree(request);
			
			return "redirect:/admin/system/department/list.do";
		}
		
		return "system/addDepartment";
	}
	
	@RequestMapping(value="/department/popup/pickDepartment.do", method = RequestMethod.GET)
	public String pickDepartment(Model model, HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String roleId = request.getParameter("roleId");
		
		List<SysDepartment> selectedDepart = null;
		List<SysDepartment> allDepart = null;
		Map<Long, SysDepartment> selectetDepartMap = null;
		
		try {
			allDepart = systemService.getDepartments(LoginUtils.getUserDepartIdTree(request));
			
			if(StringUtils.isNotEmpty(userId)) {
				selectedDepart = systemService.getDepartmentByUserId(Long.parseLong(userId));
			} else if(StringUtils.isNotEmpty(roleId)) {
				selectedDepart = systemService.getDepartmentByRoleId(Long.parseLong(roleId));
			}
			
			selectetDepartMap = sysDepart2Map(selectedDepart);
		} catch( Exception e) {
			FormUtils.reponseError(e.getMessage(), model);
			log.error(e);
		}
		
		model.addAttribute("userId", userId);
		model.addAttribute("roleId", roleId);
		
		model.addAttribute("departs", allDepart);
		model.addAttribute("selectetDepart", selectetDepartMap);
		
		return "system/pickDepartment";
	}
	
	@RequestMapping(value="/department/popup/updatePickDepart.do", method = RequestMethod.POST)
	public String updatePickDepart(RedirectAttributes model, HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String roleId = request.getParameter("roleId");
		String[] departIds = request.getParameterValues("departId");
		String param = null;
		
		try {
			if(StringUtils.isNotEmpty(userId)) {	// 用户-角色
				List<SysUserDepartment> list = new ArrayList<SysUserDepartment>();
				
				if( departIds != null) {
					for ( String departId: departIds) {
						SysUserDepartment sysUserDepart = new SysUserDepartment();
						SysUserDepartmentItem sysUserDepartItem = new SysUserDepartmentItem();
						
						sysUserDepartItem.setDepartId(Long.parseLong(departId));
						sysUserDepartItem.setUserId(Long.parseLong(userId));
						
						sysUserDepart.setSysUserDepartment(sysUserDepartItem);
						
						list.add(sysUserDepart);
					}
				}
				
				systemService.removeAndSaveUserDepartment(Long.parseLong(userId), list);
				param = "userId=" + userId;
				
			} else if(StringUtils.isNotEmpty(roleId)) {	// 部门-角色
				List<SysRoleDepartment> list = new ArrayList<SysRoleDepartment>();
				
				if( departIds != null) {
					for ( String departId: departIds) {
						SysRoleDepartment sysRoleDepart = new SysRoleDepartment();
						SysRoleDepartmentItem sysRoleDepartItem = new SysRoleDepartmentItem();
						
						sysRoleDepartItem.setDepartId(Long.parseLong(departId));
						sysRoleDepartItem.setRoleId(Long.parseLong(roleId));
						
						sysRoleDepart.setSysRoleDepartment(sysRoleDepartItem);
						
						list.add(sysRoleDepart);
					}
				}
				
				systemService.removeAndSaveRoleDepartment(Long.parseLong(roleId), list);
				param = "roleId=" + roleId;
			}
			
			FormUtils.redirectSucceed( model);
		} catch(Exception e) {
			FormUtils.redirectError(e.getMessage(), model);
			log.error(e);
		}
		
		return "redirect:/admin/system/department/popup/pickDepartment.do?" + param;
	}
	
	@RequestMapping(value="/department/delete.do", method = RequestMethod.DELETE,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<SysDepartment> deleteDepartment(@RequestBody SysDepartment sysDepartment) {
		JSONResponse<SysDepartment> jsonResponse = new JSONResponse<SysDepartment>();
		
		if ( sysDepartment.getDepartId() != null) {
			try {
				Long[] departIds = new Long[] {sysDepartment.getDepartId()};
				systemService.removeSysDepartments(departIds);
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
			} catch( Exception e) {
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_ERROR);
				jsonResponse.setMsg(e.getCause() + ":" + e.getMessage());
			}
		} else {
			jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_ERROR);
			jsonResponse.setMsg("部门ID为空");
		}
		
		jsonResponse.setObject(sysDepartment);
		
		return jsonResponse;
	}
	
	/**
	 * @param selectedRole
	 * @return
	 */
	private Map<Long, SysDepartment> sysDepart2Map(List<SysDepartment> selectedDepart) {
		Map<Long, SysDepartment> rlt = new HashMap<Long, SysDepartment>();
		
		for(SysDepartment depart : selectedDepart) {
			rlt.put(depart.getDepartId(), depart);
		}
		
		return rlt;
	}
}
