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

import org.garden.web.prototype.web.Constants;
import org.garden.web.prototype.web.vo.ReturnResponse;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** 
* @ClassName: FormUtils 
* @Description: TODO
* @author Garden Lee
* @date 2016年4月6日 下午4:54:49 
*/
public class FormUtils {
	private static void reponseMessage( String type, String msg, Model model) {
		model.addAttribute(Constants.RESP_KEY, new ReturnResponse(type, msg));
	}
	
	public static void reponseSucceed( String msg, Model model) {
		reponseMessage(ReturnResponse.RESP_STATUS_SUCCEED, msg, model);
	}
	
	public static void reponseSucceed( Model model) {
		reponseMessage(ReturnResponse.RESP_STATUS_SUCCEED, null, model);
	}
	
	public static void reponseError( String msg, Model model) {
		reponseMessage(ReturnResponse.RESP_STATUS_ERROR, msg, model);
	}
	
	private static void redirectMessage( String type, String msg, RedirectAttributes model) {
		model.addFlashAttribute(Constants.RESP_KEY, new ReturnResponse(type, msg));
	}
	
	public static void redirectSucceed( String msg, RedirectAttributes model) {
		redirectMessage(ReturnResponse.RESP_STATUS_SUCCEED, msg, model);
	}
	
	public static void redirectSucceed( RedirectAttributes model) {
		redirectMessage(ReturnResponse.RESP_STATUS_SUCCEED, null, model);
	}
	
	public static void redirectError( String msg, RedirectAttributes model) {
		redirectMessage(ReturnResponse.RESP_STATUS_ERROR, msg, model);
	}
}
