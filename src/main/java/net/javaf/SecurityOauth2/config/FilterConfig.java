package net.javaf.SecurityOauth2.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<XForwardedForToXRealIpAndXOriginalForwardedForFilter> xForwardedForToXRealIpAndXOriginalForwardedForFilter() {
        FilterRegistrationBean<XForwardedForToXRealIpAndXOriginalForwardedForFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new XForwardedForToXRealIpAndXOriginalForwardedForFilter());
        registrationBean.addUrlPatterns("/*");  // 모든 URL 패턴에 필터 적용
        registrationBean.setOrder(Integer.MIN_VALUE);  // 가장 먼저 실행되도록 설정

        return registrationBean;
    }

    public static class XForwardedForToXRealIpAndXOriginalForwardedForFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            // 필터 초기화 시 필요한 설정
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            if (request instanceof HttpServletRequest) {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                XRealIpAndXOriginalForwardedForRequestWrapper wrappedRequest = new XRealIpAndXOriginalForwardedForRequestWrapper(httpRequest);
                chain.doFilter(wrappedRequest, response);
            } else {
                chain.doFilter(request, response);
            }
        }

        @Override
        public void destroy() {
            // 필터 소멸 시 필요한 설정
        }
    }
}
