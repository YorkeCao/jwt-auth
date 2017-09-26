package io.yorkecao.jwtauth.shiro;

import io.yorkecao.jwtauth.common.JwtUtils;
import io.yorkecao.jwtauth.config.JwtAuthConfing;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author Yorke
 */
public class JwtRememberMeManager extends CookieRememberMeManager {
    private static final transient Logger logger = LoggerFactory.getLogger(JwtRememberMeManager.class);

    @Autowired
    private JwtAuthConfing jwtAuthConfing;

    @Override
    protected void rememberIdentity(Subject subject, PrincipalCollection accountPrincipals) {
        String jwtStr = JwtUtils.signJwt((String) accountPrincipals.getPrimaryPrincipal(), jwtAuthConfing.getSecret());
        this.rememberJwtIdentity(subject, jwtStr);
    }

    private void rememberJwtIdentity(Subject subject, String jwt) {
        if (!WebUtils.isHttp(subject)) {
            if (logger.isDebugEnabled()) {
                String msg = "Subject argument is not an HTTP-aware instance.  This is required to obtain a servlet request and response in order to set the rememberMe cookie. Returning immediately and ignoring rememberMe operation.";
                logger.debug(msg);
            }

        } else {
            HttpServletRequest request = WebUtils.getHttpRequest(subject);
            HttpServletResponse response = WebUtils.getHttpResponse(subject);
            Cookie template = this.getCookie();
            Cookie cookie = new SimpleCookie(template);
            cookie.setValue(jwt);
            cookie.saveTo(request, response);
        }
    }

    @Override
    public PrincipalCollection getRememberedPrincipals(SubjectContext subjectContext) {
        PrincipalCollection principals = null;
        try {
            String jwtStr = getRememberedJwtIdentity(subjectContext);
            if (jwtStr != null && jwtStr.length() > 0) {
                String username = JwtUtils.verifyJwt(jwtStr, jwtAuthConfing.getSecret());
                principals = new SimplePrincipalCollection(username, CustomRealm.class.getName());
            }
        } catch (RuntimeException re) {
            principals = onRememberedPrincipalFailure(re, subjectContext);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return principals;
    }

    protected String getRememberedJwtIdentity(SubjectContext subjectContext) {

        if (!WebUtils.isHttp(subjectContext)) {
            if (logger.isDebugEnabled()) {
                String msg = "SubjectContext argument is not an HTTP-aware instance.  This is required to obtain a " +
                        "servlet request and response in order to retrieve the rememberMe cookie. Returning " +
                        "immediately and ignoring rememberMe operation.";
                logger.debug(msg);
            }
            return null;
        }

        WebSubjectContext wsc = (WebSubjectContext) subjectContext;
        if (isIdentityRemoved(wsc)) {
            return null;
        }

        HttpServletRequest request = WebUtils.getHttpRequest(wsc);
        HttpServletResponse response = WebUtils.getHttpResponse(wsc);

        String jwt = getCookie().readValue(request, response);
        // Browsers do not always remove cookies immediately (SHIRO-183)
        // ignore cookies that are scheduled for removal
        if (Cookie.DELETED_COOKIE_VALUE.equals(jwt)) return null;

        if (jwt != null) {
            return jwt;
        } else {
            //no cookie set - new site visitor?
            return null;
        }
    }

    private boolean isIdentityRemoved(WebSubjectContext subjectContext) {
        ServletRequest request = subjectContext.resolveServletRequest();
        if (request != null) {
            Boolean removed = (Boolean) request.getAttribute(ShiroHttpServletRequest.IDENTITY_REMOVED_KEY);
            return removed != null && removed;
        }
        return false;
    }
}
