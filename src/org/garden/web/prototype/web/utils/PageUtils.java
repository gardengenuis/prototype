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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.garden.utils.Pager;
import org.garden.utils.TextUtils;

/** 
* @ClassName: PageUtils 
* @Description: TODO
* @author Garden Lee
* @date 2016年4月20日 上午9:58:27 
*/
public class PageUtils {
	public static final int DEFAULT_ITEMS_PER_PAGE = 15;
	public static final String DEFAULT_PAGE_REQ_PARAM_NAME = "garden.page";
	public static final String DEFAULT_PAGE_TAG_NAME = "glist";
	
	/**
	 * 获取Displaytag的PAGER
	 * @param request
	 * @param tagName displaytag的列表ID
	 * @param itemsPerPage 每页显示数
	 * @return
	 */
	public static Pager getDisplaytagPager(HttpServletRequest request, String tagName, int itemsPerPage) {
		String pageParam = new ParamEncoder( tagName).encodeParameterName(TableTagParameters.PARAMETER_PAGE);
        return getPager(request, pageParam, itemsPerPage, 0);
	}
	
	/**
	 * 获取Displaytag的PAGER
	 * @param request
	 * @param tagName displaytag的列表ID
	 * @return
	 */
	public static Pager getDisplaytagPager(HttpServletRequest request, String tagName) {
		return getDisplaytagPager(request, tagName, PageUtils.DEFAULT_ITEMS_PER_PAGE);
	}
	
	/**
	 * 获取Displaytag的PAGER
	 * @param request
	 * @return
	 */
	public static Pager getDisplaytagPager(HttpServletRequest request) {
		return getDisplaytagPager(request, PageUtils.DEFAULT_PAGE_TAG_NAME, PageUtils.DEFAULT_ITEMS_PER_PAGE);
	}
	
	/**
	 * 获取PAGER
	 * @param request
	 * @return
	 */
	public static Pager getPager(HttpServletRequest request) {
		return getPager(request, PageUtils.DEFAULT_PAGE_REQ_PARAM_NAME, PageUtils.DEFAULT_ITEMS_PER_PAGE, 0);
	}
	
	/**
	 * 获取PAGER
	 * @param request
	 * @param pageParam 请求当前页的参数名称
	 * @param itemsPerPage 每页显示数
	 * @param totalSize 总记录数
	 * @return
	 */
	public static Pager getPager(HttpServletRequest request, String pageParam, int itemsPerPage, int totalSize) {
		Pager pager = new Pager();
		int pagNumber = TextUtils.formatInt(request.getParameter(pageParam), 1);
		pager.setCurPage(pagNumber);
		pager.setItemsPerPage(itemsPerPage);
		
		if( totalSize > 0) {
			pager.setTotalItems(totalSize);
			pager.calc();
		}
		
		return pager;
	}
	
	/**
	 * 获取PAGER
	 * @param request
	 * @param itemsPerPage 每页显示数
	 * @param totalSize 总记录数
	 * @return
	 */
	public static Pager getPager(HttpServletRequest request, int itemsPerPage, int totalSize) {
		return getPager(request, PageUtils.DEFAULT_PAGE_REQ_PARAM_NAME, itemsPerPage, totalSize);
	}
	
	/**
	 * 获取分页后的列表
	 * 
	 * @param list 原列表
	 * @param pager 分页控件
	 * @return
	 */
	public static <T> List<T> getPagerList(List<T> list, Pager pager) {
		if( list == null) {
			return null;
		}
		
		Integer totalItems = list.size();
		pager.setTotalItems(totalItems);
		pager.calc();
		
		return list.subList(pager.getFirstResult(), pager.getLastResult());
	}
}
