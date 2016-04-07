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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.garden.sysadmin.dao.model.SysDepartment;
import org.garden.sysadmin.dao.model.SysResource;
import org.garden.sysadmin.dao.model.SysUser;
import org.garden.sysadmin.service.SystemService;
import org.garden.web.prototype.web.utils.LoginUtils;
import org.garden.web.prototype.web.vo.JSONResponse;
import org.garden.web.prototype.web.vo.MenuVO;
import org.garden.web.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * HomeController.java
 *
 * @author Garden
 * create on 2014年11月10日 下午5:07:26
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	private static Log log = LogFactory.getLog(AdminController.class);
	
	private SystemService systemService;
	
	/**
	 * @param systemService the systemService to set
	 */
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@RequestMapping(value="/index.do", method = RequestMethod.GET)
	public String index(Model model) {
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		SysUser sysUser = systemService.getSysUserByUserCode(userDetails.getUsername());
		
		List<SysResource> sysResources = systemService.getSysResourceByUserCode(userDetails.getUsername());

		sysResources = getValidResource(sysResources);
		
		List<MenuVO> rootMenus = getMenusByParentId( sysResources, null);
		
		for ( MenuVO menu : rootMenus) {
			wrapMenus(menu, sysResources);
		}
		
		model.addAttribute("menus", rootMenus);
		model.addAttribute("username", sysUser.getUserName());
		
		return "index";
	}
	
	/**
	 * @param sysResources
	 * @return
	 */
	private List<SysResource> getValidResource(List<SysResource> sysResources) {
		List<SysResource> rlt = new ArrayList<SysResource>();
		for( SysResource sysResource : sysResources) {
			String status = sysResource.getStatus();
			
			if( status.equals("1")) {
				rlt.add(sysResource);
			}
		}
		return rlt;
	}

	/**
	 * @param menu
	 * @param sysResources
	 */
	private void wrapMenus(MenuVO menu, List<SysResource> sysResources) {
		Long id = menu.getId();
		
		List<MenuVO> subMenus = getMenusByParentId(sysResources, id);
		
		for ( MenuVO subMenu : subMenus) {
			menu.addSubMenu(subMenu);
			
			wrapMenus(subMenu, sysResources);
		}
	}
	
	private List<MenuVO> getMenusByParentId(List<SysResource> sysResources, Long parentId) {
		List<MenuVO> rootMenus = new ArrayList<MenuVO>();
		
		for ( SysResource resource : sysResources) {
			if ( resource.getResourceType().equals("1")) {
				if ( (resource.getParentId() != null
						&& resource.getParentId().equals(parentId))
						||  (resource.getParentId() == null
								&& parentId == null)) {
					String name = resource.getResourceName();
					String url = resource.getResourceUrl();
					Long id = resource.getResourceId();
					
					MenuVO menu = new MenuVO();
					menu.setName(name);
					menu.setUrl(url);
					menu.setId(id);
					
					rootMenus.add(menu);
				}
			}
		}
		
		return rootMenus;
	}

	@RequestMapping(value="/desktop.do", method = RequestMethod.GET)
	public String desktop() {
		return "desktop";
	}
	
	@RequestMapping(value="/changePass.do", method = RequestMethod.GET)
	public String changePass() {
		return "changePass";
	}
	
	@RequestMapping(value="/changePass.do", method = RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONResponse<String> changePass(@RequestBody Map<String, String> map) {
		JSONResponse<String> rlt = new JSONResponse<String>();
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		SysUser sysUser = systemService.getSysUserByUserCode(userDetails.getUsername());
		
		if ( StringUtils.isNotEmpty(map.get("password"))) {
			String oldPass = SecurityUtils.encodeViaSHA(map.get("oldPassword"), sysUser.getUserCode());
			
			if ( sysUser.getPassword().equals(oldPass)) {
				sysUser.setPassword(SecurityUtils.encodeViaSHA( map.get("password"), sysUser.getUserCode()));
				
				systemService.saveSysUser(sysUser);
				
				rlt.setCode(JSONResponse.JSON_RESPONSE_CODE_SUCCESS);
			} else {
				rlt.setCode(JSONResponse.JSON_RESPONSE_CODE_ERROR);
				rlt.setMsg("原密码错误");
			}
		}
		
		return rlt;
	}
	
	@RequestMapping(value="/test.do", method = RequestMethod.GET)
	public String test(HttpServletRequest request, Model model) {
		SysUser sysUser = LoginUtils.getLoginUser(request, systemService);
		List<SysDepartment> departs = LoginUtils.getUserDeparts(request, systemService);
		List<SysDepartment> alldeparts = LoginUtils.getUserDepartTree(request, systemService);
		
		String pageIndexName =  new ParamEncoder("element").encodeParameterName(TableTagParameters.PARAMETER_PAGE);
		List<SysResource> resources = systemService.getAllResources();
		
		model.addAttribute("resources", resources);
		
		return "displaytest";
	}
}
