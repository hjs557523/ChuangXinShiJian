package com.hjs.system.config.shiro.common;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 黄继升 16041321
 * @Description: 当配置多个Realm时，通常使用的认证器是shiro自带的org.apache.shiro.authc.pam.ModularRealmAuthenticator,
 * 其中决定使用的Realm的是doAuthenticate()方法
 * 自定义Authenticator
 * 注意，当需要分别定义处理学生和教师和管理员验证的Realm时，对应Realm的全类名应该包含字符串“Student”、“Teacher”、或者“Admin”，并且
 * 它们不能相互包含，例如，处理学生验证的Realm的全类名中不应该包含字符串“Admin”
 * @date Created in 2020/3/9 16:07
 * @Modified By:
 */

public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {
    private static final Logger logger = LoggerFactory.getLogger(UserModularRealmAuthenticator.class);

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("UserModularRealmAuthenticator: method doAuthenticate() execute");
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        // 强制转换回自定义的CustomizedToken
        UserToken userToken = (UserToken) authenticationToken;
        // 登录类型
        String loginType = userToken.getLoginType();
        // 对所有Realm
        Collection<Realm> realms = getRealms();
        // 找到登录类型对应的Realm
        List<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            if (realm.getName().contains(loginType))
                typeRealms.add(realm);
        }

        //判断是登陆类型对应的是单Realm还是多Realm
        if (typeRealms.size() == 1) {
            logger.info("doSingleRealmAuthentication() execute");
            return doSingleRealmAuthentication(typeRealms.get(0), userToken);
        }
        else {
            logger.info("doMultiRealmAuthentication() execute");
            return doMultiRealmAuthentication(typeRealms, userToken);
        }
    }

}
