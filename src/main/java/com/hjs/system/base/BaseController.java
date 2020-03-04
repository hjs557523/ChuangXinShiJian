package com.hjs.system.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/3 21:39
 * @Modified By:
 */
public abstract class BaseController {
    private final static Logger logger = LoggerFactory.getLogger(BaseController.class);
    protected int pageSize = 10;

    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public String getCurrentUser() {
        return getSession().getAttribute("user").toString();
    }
}
