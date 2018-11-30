package cn.fzz.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by Andy on 2018/11/23.
 * Desc: 登录验证拦截
 */
@Controller
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private Logger log = Logger.getLogger(String.valueOf(LoginInterceptor.class));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String basePath = request.getContextPath();
        String path = request.getRequestURI();
        log.info("basePath:" + basePath);
        log.info("path:" + path);

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
                log.info("尚未登录，跳转到登录界面" + request.getContextPath());
                response.sendRedirect(request.getContextPath() + "user/login");
            }
            return false;
        }
        return true;
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
}
