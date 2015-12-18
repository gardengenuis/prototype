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

import org.garden.sysadmin.service.SystemService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * TableTranslateTag.java
 *
 * @author Garden
 * create on 2014年11月10日 下午7:32:51
 */
public class TableTranslateTag extends TagSupport {

	private static final long serialVersionUID = -81346252483127934L;
	
	private SystemService systemService;
	private String tableName;
	private String inputColumnName;
	private String outputColumnName;
	private String inputValue;

	/**
	 * @param systemService the systemService to set
	 */
	public void setSystemService() {
        ServletContext servletContext = pageContext.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        this.systemService = (SystemService) wac.getBean("systemService");
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the inputColumnName
	 */
	public String getInputColumnName() {
		return inputColumnName;
	}

	/**
	 * @param inputColumnName the inputColumnName to set
	 */
	public void setInputColumnName(String inputColumnName) {
		this.inputColumnName = inputColumnName;
	}

	/**
	 * @return the outputColumnName
	 */
	public String getOutputColumnName() {
		return outputColumnName;
	}

	/**
	 * @param outputColumnName the outputColumnName to set
	 */
	public void setOutputColumnName(String outputColumnName) {
		this.outputColumnName = outputColumnName;
	}

	/**
	 * @return the inputValue
	 */
	public String getInputValue() {
		return inputValue;
	}

	/**
	 * @param inputValue the inputValue to set
	 */
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
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
		String value = "";
		JspWriter out = this.pageContext.getOut();
		setSystemService();
		
		String[] columns = new String[] {outputColumnName};
		String state = inputColumnName + "='" + inputValue + "'";
		List<Object> list = systemService.getObjects(tableName, columns, state);
		
		if ( !list.isEmpty()) {
			Object obj = list.get(0);
			
			if ( obj != null) {
				value = obj.toString();
			}
		}
		
		try {
			out.println(value);
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
