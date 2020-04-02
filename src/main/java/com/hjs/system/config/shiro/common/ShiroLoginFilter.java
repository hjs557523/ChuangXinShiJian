package com.hjs.system.config.shiro.common;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;

/**
 * @author 黄继升 16041321
 * @Description: 首先两个方法的返回值都是boolean，那么说明这是两个“开关”。第一个开关isAccessAllowed表示是否为允许访问的方法，
 * 在这里做一个是否已登录的判断，第二个开关onAccessDenied是当访问被拒绝时是否已经被处理了，如果需要继续处理就返回true，不需要就返回false，
 * 那么在这里把状态码添加到响应中去就可以从前端取到了。

 * @date Created in 2020/4/1 14:31
 * @Modified By:
 */
public class ShiroLoginFilter extends FormAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(ShiroLoginFilter.class);

    /**
     * 在访问controller前判断是否登录，返回json，不进行重定向。
     * @param request
     * @param response
     * @return  true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        logger.info("执行了自定义的ShiroLoginFilter失效过滤器：未登录/Session过期失效就会触发这个方法");
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //这里是个坑，如果不设置的接受的访问源，那么前端都会报跨域错误，因为这里还没到corsConfig里面
        httpServletResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(403);//添加响应状态码, 没有权限

        return false;
    }

    /**
     * 如果isAccessAllowed返回false 则执行onAccessDenied
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (request instanceof HttpServletRequest) {
            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
