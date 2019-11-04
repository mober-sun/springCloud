package com.sunhui.api_gateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;


/**
 * guava 限流特技
 * 使用 google guava 进行网关层限流
 */
@Component
public class OrderRateLimiterFilter extends ZuulFilter{

    /**
     * 这里根据具体部署服务器的 qps 详情设置，接口压力值是多少
     */
    private static final RateLimiter RATA_LIMITER = RateLimiter.create(1000); //每秒 1000个令牌

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -4;   // 越小越先执行
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        // 只对订单接口进行过滤
        if("/apigateway/order/api/v1/order/save".equalsIgnoreCase(request.getRequestURI())){
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();

        // 去尝试获取，如果超过了qps的极限值，默认不向下进行；报错返回客户端
        if(!RATA_LIMITER.tryAcquire()){
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());

        }
        return null;
    }
}
