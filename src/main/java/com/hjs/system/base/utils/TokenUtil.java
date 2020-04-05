package com.hjs.system.base.utils;

import com.hjs.system.model.Student;
import com.hjs.system.model.Teacher;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/5 16:24
 * @Modified By:
 */

public class TokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    /**
     * 用户标识在url里携带, 该方法用于判断当前正在操作的用户是否为最近登录用户
     *
     * @param request
     * @param tokenKey
     * @return
     */
    public static Boolean IsRecentLogin(HttpServletRequest request, String tokenKey) throws NullPointerException {
        if (request == null) {
            logger.info("request 空指针异常");
            throw new NullPointerException("request 空指针异常");
        }
        if (request.getSession(false) == null) { // 其实这里应该没必要，因为能进入到这个语句的应用的场景都是cookie对应的session未失效下的判断
            logger.info("该request没有对应的session, 当前用户可能已经退出登录, 或session过期失效");
            throw new NullPointerException("该request没有对应的session, 当前用户可能已经退出登录, 或session过期失效");
        }
        else if (StringUtil.isEmpty(tokenKey)) {
            logger.info("您没有定义您的 tokenKey!");
            throw new NullPointerException("您没有定义您的 tokenKey!");
        }

        String tokenValue = request.getParameter(tokenKey); // getParameter(key)不存在就返回null

        if (StringUtil.isEmpty(tokenValue)) {
            logger.info("没有携带token, 或参数值为空, 则默认为最近登录用户");
            return true;//当请求不带参数时，默认是最近登录用户
        } else if (SecurityUtils.getSubject().getSession(false).getAttribute(tokenValue) == null) {
            logger.info("您已经退出登录了!");
            throw new NullPointerException("您已经退出登录了!");// session没有信息，说明已经退出登录了
        }

        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal().getClass().getName().equals(Student.class.getName())) {
            Student current_user = (Student) subject.getPrincipal();
            if (current_user.getStudentId().equals(tokenValue)) {
                logger.info("当前操作的学生用户是最近登录用户");
                return true; // 当前操作用户是最近登录用户
            }
            else {
                logger.info("当前操作的学生用户不是最近登录用户");
                return false; // 当前操作用户不是最近登录用户

            }
        } else if (subject.getPrincipal().getClass().getName().equals(Teacher.class.getName())) {
            Teacher current_user = (Teacher) subject.getPrincipal();
            if (current_user.getTeacherId().equals(tokenValue)) {
                logger.info("当前操作的教师用户是最近登录用户");
                return true; // 当前操作用户是最近登录用户
            }
            else {
                logger.info("当前操作的教师用户不是最近登录用户");
                return false; // 当前操作用户不是最近登录用户
            }
        } else
            return false;

    }
}
