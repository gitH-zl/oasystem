package com.exoa.oasystem.interceptor;

/**
 * Created by xiang  on 2018/8/9;
 */

import com.exoa.oasystem.constant.WebConst;
import com.exoa.oasystem.modal.VO.UserVo;
import com.exoa.oasystem.utils.TaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 记录信息:</br> 访问时间</br>Controller路径</br>对应方法名</br>请求参数信息</br>请求相对路径</br>请求处理时长
 */
@Slf4j
@Component
public class TimeCostInterceptor implements HandlerInterceptor {

    // before the actual handler will be executed
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        String uri = request.getRequestURI();
        request.setAttribute("startTime", startTime);
        if (handler instanceof HandlerMethod) {
            StringBuilder sb = new StringBuilder(1000);
            sb.append("\n");
            sb.append("-------------------------------------------------------------------------------").append("\n");
            HandlerMethod h = (HandlerMethod) handler;
            sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
            sb.append("Method    : ").append(h.getMethod().getName()).append("\n");
            sb.append("Params    : ").append(getParamString(request.getParameterMap())).append("\n");
            sb.append("URI       : ").append(request.getRequestURI()).append("\n");
            log.info(sb.toString());
        }
        UserVo user = TaleUtils.getLoginUser(request);
        if (null == user) {
            Integer uid = TaleUtils.getCookieUid(request);
            if (null != uid) {
                //这里还是有安全隐患,cookie是可以伪造的
                //user = userService.queryUserById(uid);
                request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, user);
            }
        }

        if (uri.startsWith("/admin") && !uri.startsWith("/admin/login")&&null == user) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }
        return true;
    }

    // after the handler is executed
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        if (handler instanceof HandlerMethod) {
            StringBuilder sb = new StringBuilder(1000);
            sb.append("CostTime  : ").append(executeTime).append("ms").append("\n");
            sb.append("-------------------------------------------------------------------------------");
            log.info(sb.toString());
        }
    }

    private String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String[]> e : map.entrySet()) {
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if (value != null && value.length == 1) {
                sb.append(value[0]).append("\t");
            } else {
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }

    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }
}