import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class XRealIpAndXOriginalForwardedForRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders;

    public XRealIpAndXOriginalForwardedForRequestWrapper(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null) {
            this.customHeaders.put("X-Real-IP", xForwardedFor.split(",")[0].trim());
            this.customHeaders.put("X-Original-Forwarded-For", xForwardedFor);
        }
    }

    @Override
    public String getHeader(String name) {
        String headerValue = customHeaders.get(name);
        if (headerValue != null) {
            return headerValue;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        String headerValue = customHeaders.get(name);
        if (headerValue != null) {
            return java.util.Collections.enumeration(java.util.Collections.singletonList(headerValue));
        }
        return super.getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        java.util.Enumeration<String> headerNames = super.getHeaderNames();
        java.util.List<String> list = new java.util.ArrayList<>();
        while (headerNames.hasMoreElements()) {
            list.add(headerNames.nextElement());
        }
        list.addAll(customHeaders.keySet());
        return java.util.Collections.enumeration(list);
    }
}
