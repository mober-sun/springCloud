package com.sunhui.api_gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 自定义过滤类
 */
@Component   // 让spring 扫描
public class LoginFilter extends ZuulFilter {

    /**
     * 过滤器类型
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器顺序，越小越先执行
     * @return
     */

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 过滤器是否生效
     * @return
     */
    @Override
    public boolean shouldFilter() {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        System.out.println("requesturl:"+request.getRequestURI());

        if ("/apigateway/order/order/save".equalsIgnoreCase(request.getRequestURI())){
            return true;
        }else if ("/apigateway/product/api/v1/product/find".equalsIgnoreCase(request.getRequestURI())){
            return true;
        }


        return false;
    }

    /**
     * 当上面为ture 执行此业务逻辑
     * @return
     * @throws ZuulException
     */

    @Override
    public Object run() throws ZuulException {

        HashMap<Object, Object> map = new HashMap<>();
        map.put("string","string");


        // requestContext 和ThreadLocal 绑定 ；保证了单例多线程模式下的安全；
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){

            token = request.getParameter("token");
        }

        if (StringUtils.isBlank(token)){
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }



        return null;
    }
}
