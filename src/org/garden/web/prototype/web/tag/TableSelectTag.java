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
package org.garden.web.prototype.web.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.garden.web.prototype.service.BaseService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * TableTranslateTag.java
 *
 * @author Garden
 * create on 2014年11月10日 下午7:32:51
 */
public class TableSelectTag extends TagSupport {

	private static final long serialVersionUID = 4569257117488364327L;

	private BaseService service;

	private boolean disabled = false;
	private boolean required = false;
	private String cssClass;
	private String idName;
	private String sql;
	private String tagName;
	private boolean defaultEmpty = false;
	private String onChangeMethod;
	private String fieldValue;
	private String emptyStr;
	private String style;

	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getEmptyStr() {
		return emptyStr;
	}
	public void setEmptyStr(String emptyStr) {
		this.emptyStr = emptyStr;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getOnChangeMethod() {
		return onChangeMethod;
	}
	public void setOnChangeMethod(String onChangeMethod) {
		this.onChangeMethod = onChangeMethod;
	}
	public boolean isDefaultEmpty() {
		return defaultEmpty;
	}
	public void setDefaultEmpty(boolean defaultEmpty) {
		this.defaultEmpty = defaultEmpty;
	}
	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}
	/**
	 * @param tagName the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}
	/**
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}
	/**
	 * @param systemService the systemService to set
	 */
	public void setDAO() {
        ServletContext servletContext = pageContext.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        this.service = (BaseService) wac.getBean("baseService");
	}
	/**
	 * @return the disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}
	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	/**
	 * @return the cssClass
	 */
	public String getCssClass() {
		return cssClass;
	}
	/**
	 * @param cssClass the cssClass to set
	 */
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	/**
	 * @return the idName
	 */
	public String getIdName() {
		return idName;
	}
	/**
	 * @param idName the idName to set
	 */
	public void setIdName(String idName) {
		this.idName = idName;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		
		setDAO();
	
		List<Object[]> selectList = service.findBySql(sql);
		try {
			if ( !selectList.isEmpty()) {
				String clazzStr = "";
				if ( StringUtils.isNotEmpty(cssClass)) {
					clazzStr += "class=\"" + cssClass + "\"";
				}
				String idStr = "";
				if ( StringUtils.isNotEmpty(idName)) {
					idStr += "id=\"" + idName + "\"";
				}
				String disabledStr = "";
				if ( disabled) {
					disabledStr += "disabled";
				}
				String requiredStr = "";
				if ( required) {
					requiredStr += "required";
				}
				String tagN = "";
				if ( StringUtils.isNotEmpty(tagName)) {
					tagN += "name=\"" + tagName + "\"";
				}
				String styleStr = "";
				if ( StringUtils.isNotEmpty(style)) {
					styleStr += "style=\"" + style + "\"";
				}
				
				String ocm = "";
				if ( StringUtils.isNotEmpty(onChangeMethod)) {
					ocm += "onChange=\"" + onChangeMethod + "\"";
				}
				
				out.println("<select " + clazzStr + " " + idStr + " " + disabledStr + " " + requiredStr + " " + tagN + " " + styleStr + " " + ocm + " " + ">");
				
				if ( defaultEmpty) {
					String emptyHints = "";
					if ( StringUtils.isNotEmpty(emptyStr)) {
						emptyHints = emptyStr;
					}
					out.println("<option value=\"\" selected>" + emptyHints + "</option>");
				}
				
				for (Object[] object : selectList) {
					String key = object[0].toString();
					String value = object[1].toString();
					String selectedStr = "";
					if ( key.equals(fieldValue)) {
						selectedStr = "selected";
					}
					out.println("<option value=\"" + key + "\" " + selectedStr + ">" + value + "</option>");
				}
				
				out.println("</select>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#release()
	 */
	@Override
	public void release() {
		// TODO Auto-generated method stub
		super.release();
	}

}
