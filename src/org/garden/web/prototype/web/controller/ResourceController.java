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
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.garden.sysadmin.dao.model.SysResource;
import org.garden.sysadmin.dao.model.SysRoleResOper;
import org.garden.sysadmin.dao.model.SysRoleResOperItem;
import org.garden.sysadmin.service.SystemService;
import org.garden.utils.Pager;
import org.garden.utils.TextUtils;
import org.garden.web.prototype.web.utils.FormUtils;
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
 * ResourceController.java
 *
 * @author Garden
 * create on 2014年11月11日 下午9:34:49
 */
@Controller
@RequestMapping("/admin/system")
public class ResourceController {
	private static Log log = LogFactory.getLog(ResourceController.class);
	private SystemService systemService;
	
	/**
	 * @param systemService the systemService to set
	 */
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	@RequestMapping(value="/resource/list.do", method = RequestMethod.GET)
	public String listResource(Model model, HttpServletRequest request) {
		
		Pager pager = new Pager();
		String pth =request.getParameter(new ParamEncoder("resources").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
        int pagNumber = TextUtils.formatInt(pth,1);
        pager.setCurPage(pagNumber);
        pager.setItemsPerPage(10);
		
		List<SysResource> resources = systemService.getSysResources(pager);
				
		model.addAttribute("resources", resources);
		model.addAttribute("resultSize", pager.getTotalItems());
		
		return "system/listResource";
	}
	
	@RequestMapping(value="/resource/edit.do", method = RequestMethod.GET)
	public String editResource(Model model) {
		List<SysResource> resources = systemService.getSysResources();
		List<SysResource> menuRes = systemService.getMenuResources();
		
		model.addAttribute("resources", resources);
		model.addAttribute("menuRes", menuRes);
		
		return "system/editResource";
	}
	
	@RequestMapping(value="/resource/edit.do", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<SysResource> editResource(@RequestBody SysResource sysResource) throws Exception {
		JSONResponse<SysResource> jsonResponse = new JSONResponse<SysResource>();
		
		if ( sysResource.getResourceId() != null) {
			try {
				systemService.saveSysResource(sysResource);
				
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
			} catch( Exception e) {
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_ERROR);
				jsonResponse.setMsg(e.getCause() + ":" + e.getMessage());
			}
		}
		jsonResponse.setObject(sysResource);
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/resource/add.do", method = {RequestMethod.POST,RequestMethod.GET})
	public String addResource(@ModelAttribute SysResource sysResource, Model model) {
		
		if ( sysResource.getResourceName() != null) {
			systemService.saveSysResource(sysResource);
			
			return "redirect:/admin/system/resource/list.do";
		}
		
		List<SysResource> resources = systemService.getMenuResources();
		
		model.addAttribute("resources", resources);
		
		return "system/addResource";
	}
	
	@RequestMapping(value="/resource/delete.do", method = RequestMethod.DELETE,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<SysResource> deleteResource(@RequestBody SysResource sysResource) {
		JSONResponse<SysResource> jsonResponse = new JSONResponse<SysResource>();
		
		if ( sysResource.getResourceId() != null) {
			try {
				Long[] resourceIds = new Long[] {sysResource.getResourceId()};
				systemService.removeSysResources(resourceIds);
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
			} catch( Exception e) {
				jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_ERROR);
				jsonResponse.setMsg(e.getCause() + ":" + e.getMessage());
			}
		} else {
			jsonResponse.setCode(JSONResponse.JSON_RESPONSE_CODE_ERROR);
			jsonResponse.setMsg("角色ID为空");
		}
		
		jsonResponse.setObject(sysResource);
		
		return jsonResponse;
	}
	
	@RequestMapping(value="/resource/popup/pickRes.do", method = RequestMethod.GET)
	public String pickRes(Model model, HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		
		List<SysResource> selectedRes = null;
		List<SysResource> allRes = null;
		Map<Long, SysResource> selectetResMap = null;
		
		try {
			allRes = systemService.getSysResources();
			
			if(StringUtils.isNotEmpty(roleId)) {
				selectedRes = systemService.getSysResourceByRoleId(Long.parseLong(roleId));
			}
			
			selectetResMap = sysResource2Map(selectedRes);
		} catch( Exception e) {
			FormUtils.reponseError(e.getMessage(), model);
			log.error(e);
		}
		
		model.addAttribute("roleId", roleId);
		
		model.addAttribute("resources", allRes);
		model.addAttribute("selectetRes", selectetResMap);
		
		return "system/pickRes";
	}

	@RequestMapping(value="/resource/popup/updatePickRes.do", method = RequestMethod.POST)
	public String updatePickRes(RedirectAttributes model, HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String[] resourceIds = request.getParameterValues("resourceId");
		String param = null;
		
		try {
			if(StringUtils.isNotEmpty(roleId)) { // 角色-资源
				List<SysRoleResOper> list = new ArrayList<SysRoleResOper>();
				
				if( resourceIds != null) {
					for ( String resourceId: resourceIds) {
						SysRoleResOper sysRoleResOper = new SysRoleResOper();
						SysRoleResOperItem sysRoleResOperItem = new SysRoleResOperItem();
						
						sysRoleResOperItem.setRoleId(Long.parseLong(roleId));
						sysRoleResOperItem.setResourceId(Long.parseLong(resourceId));
						sysRoleResOperItem.setOperationId(1l);
						
						sysRoleResOper.setSysRoleResOper(sysRoleResOperItem);
						
						list.add(sysRoleResOper);
					}
				}
				
				systemService.removeAndSaveResourceRoleByRoleId(Long.parseLong(roleId), list);
				param = "roleId=" + roleId;
			}
			
			FormUtils.redirectSucceed( model);
		} catch(Exception e) {
			FormUtils.redirectError(e.getMessage(), model);
			log.error(e);
		}
		
		return "redirect:/admin/system/resource/popup/pickRes.do?" + param;
	}
	/**
	 * @param selectedRes
	 * @return
	 */
	private Map<Long, SysResource> sysResource2Map(List<SysResource> selectedRes) {
		Map<Long, SysResource> rlt = new HashMap<Long, SysResource>();
		
		for(SysResource res : selectedRes) {
			rlt.put(res.getResourceId(), res);
		}
		
		return rlt;
	}
}
