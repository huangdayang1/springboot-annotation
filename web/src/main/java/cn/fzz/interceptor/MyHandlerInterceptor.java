package cn.fzz.interceptor;

import cn.fzz.bean.UserEntity;
import cn.fzz.common.PermissionConstants;
import cn.fzz.common.annotation.RequiredAfterTimeLimit;
import cn.fzz.common.annotation.RequiredPermission;
import com.github.fanzezhen.utils.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Andy on 2018/11/23.
 * Desc: 登录验证拦截
 */
@Controller
@Component
public class MyHandlerInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = Logger.getLogger(String.valueOf(MyHandlerInterceptor.class));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String basePath = request.getContextPath();
        String path = request.getRequestURI();
        logger.info("basePath:" + basePath);
        logger.info("path:" + path);

        if (doLoginInterceptor(path, basePath)) {//是否进行登陆拦截
            return true;
        }

        //如果登录了，会把用户信息存进session
        HttpSession session = request.getSession();
        Object account = session.getAttribute("account");
        if (account == null) {
            /*log.info("尚未登录，跳转到登录界面");
            response.sendRedirect(request.getContextPath()+"signin");*/

            String requestType = request.getHeader("X-Requested-With");
            if (requestType != null && requestType.equals("XMLHttpRequest")) {
                response.setHeader("sessionStatus", "timeout");
                response.setHeader("basePath", request.getContextPath());
                response.getWriter().print("LoginTimeout");
                return false;
            } else {
                logger.info("尚未登录，跳转到登录界面" + request.getContextPath());
                response.sendRedirect(request.getContextPath() + "user/login");
            }
            return false;
        }
        if (hasAnnotationPermission(session, handler)) {
            return true;
        }else {
            response.setHeader("sessionStatus", "timeout");
            response.setHeader("basePath", request.getContextPath() + "user/login");
            response.getWriter().print("权限不足!");
            response.sendRedirect(request.getContextPath() + "user/login");
            return false;
        }
    }

    /**
     * 是否进行登陆过滤
     */
    private boolean doLoginInterceptor(String path, String basePath) {
        path = path.substring(basePath.length());
        Set<String> notLoginPaths = new HashSet<>();
        //设置不进行登录拦截的路径：登录注册和验证码
//        notLoginPaths.add("/user/login");
        notLoginPaths.add("/user/register");

        return notLoginPaths.contains(path);
    }

    /**
     * 是否有权限
     *
     * @param handler
     * @return
     */
    private boolean hasAnnotationPermission(HttpSession session, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            boolean pass = true;

            // 获取方法上的注解
            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
            // 如果方法上的注解为空 则获取类的注解
            if (requiredPermission == null) {
                requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
            }
            // 如果标记了注解，则判断权限
            if (requiredPermission != null && StringUtils.isNotBlank(requiredPermission.value())) {
                /*
                // redis或数据库 中获取该用户的权限信息 并判断是否有权限
                Set<String> permissionSet = adminUserService.getPermissionSet();
                if (CollectionUtils.isEmpty(permissionSet) ){
                    return false;
                }
                return permissionSet.contains(requiredPermission.value());
                */
                Object status = session.getAttribute("status");
                if (status!=null)
                    if (requiredPermission.value().equals(PermissionConstants.ADMIN_PRODUCT_LIST))
                        pass = status.equals(0);
                    else if (requiredPermission.value().equals(PermissionConstants.ADMIN_PRODUCT_DETAIL))
                        pass = status.equals(1);
            }

            if (!pass) return false;

            // 获取方法上的@RequiredAfterTimeLimit注解
            RequiredAfterTimeLimit requiredAfterTimeLimit = handlerMethod.getMethod().getAnnotation(RequiredAfterTimeLimit.class);
            // 如果方法上的@RequiredAfterTimeLimit注解为空 则获取类的@RequiredAfterTimeLimit注解
            if (requiredAfterTimeLimit == null) {
                requiredAfterTimeLimit = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredAfterTimeLimit.class);
            }
            // 如果标记了@RequiredAfterTimeLimit注解，则判断相应权限
            if (requiredAfterTimeLimit != null && !StringUtils.isEmpty(requiredAfterTimeLimit.value())) {
                /*
                // redis或数据库 中获取该用户的权限信息 并判断是否有权限
                Set<String> permissionSet = adminUserService.getPermissionSet();
                if (CollectionUtils.isEmpty(permissionSet) ){
                    return false;
                }
                return permissionSet.contains(requiredPermission.value());
                */

                try {
                    //正式开发时要从session中获取登录用户
                    UserEntity userEntity = new UserEntity();
                    pass = userEntity.getDate_joined().after(DateUtils.toDate(requiredAfterTimeLimit.value()));
                }catch (Exception e){
                    e.printStackTrace();
                    pass = false;
                }
            }
            return pass;
        }
        return false;
    }
}
