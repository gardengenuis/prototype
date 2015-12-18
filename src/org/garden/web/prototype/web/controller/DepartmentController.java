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
import org.garden.sysadmin.service.SystemService;
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
	public String listDepartment(Model model) {
		List<SysDepartment> departments = systemService.getDepartments();
		
		model.addAttribute("departments", departments);
		
		return "system/listDepartment";
	}
	
	@RequestMapping(value="/department/edit.do", method = RequestMethod.GET)
	public String editDepartment(Model model) {
		List<SysDepartment> departments = systemService.getDepartments();
		
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
	
	@RequestMapping(value="/department/add.do", method = RequestMethod.GET)
	public String addDepartment(@ModelAttribute SysDepartment sysDepartment, Model model) {
		
		List<SysDepartment> departments = systemService.getDepartments();
		
		model.addAttribute("departments", departments);
		
		if ( StringUtils.isNotEmpty(sysDepartment.getDepartCode())) {
			systemService.saveSysDepartment(sysDepartment);
			
			return "redirect:/admin/system/department/list.do";
		}
		
		return "system/addDepartment";
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
}
