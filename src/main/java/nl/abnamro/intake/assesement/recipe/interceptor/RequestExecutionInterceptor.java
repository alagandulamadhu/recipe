package nl.abnamro.intake.assesement.recipe.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

public class RequestExecutionInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME = "startTime";
    private static final Logger LOGGER = LogManager.getLogger(RequestExecutionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(START_TIME, Instant.now().toEpochMilli());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (long) request.getAttribute(START_TIME);
        String logMessage = String.format("[Http Method: %s, URL: %s, Http Status : %s, Response Time : %s]", request.getMethod(), request.getRequestURL().toString()
                , response.getStatus(), (Instant.now().toEpochMilli() - startTime));
        LOGGER.warn(logMessage);
    }
}
