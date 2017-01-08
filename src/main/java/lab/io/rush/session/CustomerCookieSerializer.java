package lab.io.rush.session;

import org.springframework.session.web.http.CookieSerializer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by liyang on 17/1/8.
 */
public class CustomerCookieSerializer implements CookieSerializer {
    private String cookieName = "SESSION";

    private Boolean useSecureCookie;

    private boolean useHttpOnlyCookie = isServlet3();

    private int cookieMaxAge = -1;

    private String domainName;

    private Pattern domainNamePattern;

    private String jvmRoute;

    private boolean useBase64Encoding;

    private String rememberMeRequestAttribute;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.session.web.http.CookieSerializer#readCookieValues(javax.
     * servlet.http.HttpServletRequest)
     */
    @Override
    public List<String> readCookieValues(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        List<String> matchingCookieValues = new ArrayList<String>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (this.cookieName.equals(cookie.getName())) {
                    String sessionId = this.useBase64Encoding
                            ? base64Decode(cookie.getValue()) : cookie.getValue();
                    if (sessionId == null) {
                        continue;
                    }
                    if (this.jvmRoute != null && sessionId.endsWith(this.jvmRoute)) {
                        sessionId = sessionId.substring(0,
                                sessionId.length() - this.jvmRoute.length());
                    }
                    matchingCookieValues.add(sessionId);
                }
            }
        }
        return matchingCookieValues;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.session.web.http.CookieWriter#writeCookieValue(org.
     * springframework.session.web.http.CookieWriter.CookieValue)
     */
    @Override
    public void writeCookieValue(CookieValue cookieValue) {
        HttpServletRequest request = cookieValue.getRequest();
        HttpServletResponse response = cookieValue.getResponse();

        String requestedCookieValue = cookieValue.getCookieValue();
        String actualCookieValue = this.jvmRoute == null ? requestedCookieValue
                : requestedCookieValue + this.jvmRoute;

        Cookie sessionCookie = new Cookie(this.cookieName, this.useBase64Encoding
                ? base64Encode(actualCookieValue) : actualCookieValue);
        sessionCookie.setSecure(isSecureCookie(request));
        sessionCookie.setPath("/");
        String domainName = getDomainName(request);
        if (domainName != null) {
            sessionCookie.setDomain(domainName);
        }

        if (this.useHttpOnlyCookie) {
            sessionCookie.setHttpOnly(true);
        }

        if ("".equals(requestedCookieValue)) {
            sessionCookie.setMaxAge(0);
        }
        else if (this.rememberMeRequestAttribute != null
                && request.getAttribute(this.rememberMeRequestAttribute) != null) {
            // the cookie is only written at time of session creation, so we rely on
            // session expiration rather than cookie expiration if remember me is enabled
            sessionCookie.setMaxAge(Integer.MAX_VALUE);
        }
        else {
            sessionCookie.setMaxAge(this.cookieMaxAge);
        }

        response.addCookie(sessionCookie);
    }

    /**
     * Decode the value using Base64.
     * @param base64Value the Base64 String to decode
     * @return the Base64 decoded value
     * @since 1.2.2
     */
    private String base64Decode(String base64Value) {
        try {
            byte[] decodedCookieBytes = Base64.decode(base64Value.getBytes());

            return new String(decodedCookieBytes);
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Encode the value using Base64.
     * @param value the String to Base64 encode
     * @return the Base64 encoded value
     * @since 1.2.2
     */
    private String base64Encode(String value) {
        byte[] encodedCookieBytes = Base64.encode(value.getBytes());
        return new String(encodedCookieBytes);
    }

    private boolean isSecureCookie(HttpServletRequest request) {
        if (this.useSecureCookie == null) {
            return request.isSecure();
        }
        return this.useSecureCookie;
    }

    private String getDomainName(HttpServletRequest request) {
        if (this.domainName != null) {
            return this.domainName;
        }
        if (this.domainNamePattern != null) {
            Matcher matcher = this.domainNamePattern.matcher(request.getServerName());
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    /**
     * Returns true if the Servlet 3 APIs are detected.
     *
     * @return whether the Servlet 3 APIs are detected
     */
    private boolean isServlet3() {
        try {
            ServletRequest.class.getMethod("startAsync");
            return true;
        }
        catch (NoSuchMethodException e) {
        }
        return false;
    }
}
