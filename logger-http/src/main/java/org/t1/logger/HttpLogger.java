package org.t1.logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class HttpLogger implements HandlerInterceptor {

    private final Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(HttpLogger.class);

    public void setLogLevel(ch.qos.logback.classic.Level level) {
        log.setLevel(level);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis() - start;

        String logMsg = String.format(
                "Request of method: %s, request URI: %s, response status: %d, request processing time: %d ms",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                end
        );
        log.info(logMsg);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis() - start;

        String logMsg = String.format(
                "Response of method: %s, request URI: %s, response status: %d, request processing time: %d ms",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                end
        );
        log.info(logMsg);
    }
}
