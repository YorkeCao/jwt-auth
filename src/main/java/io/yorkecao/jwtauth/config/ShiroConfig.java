package io.yorkecao.jwtauth.config;

import io.yorkecao.jwtauth.shiro.CustomRealm;
import io.yorkecao.jwtauth.shiro.JwtRememberMeManager;
import io.yorkecao.jwtauth.shiro.StatelessWebSubjectFactory;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yorke
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "customRealm")
    public CustomRealm customRealm() {
        return new CustomRealm();
    }

    @Bean(name = "rememberMeManager")
    public JwtRememberMeManager jwtRememberMeManager() {
        return new JwtRememberMeManager();
    }

    @Bean(name = "sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager webSessionManager = new DefaultWebSessionManager();
        webSessionManager.setSessionValidationSchedulerEnabled(false);
        return webSessionManager;
    }

    @Bean
    public DefaultWebSubjectFactory defaultWebSubjectFactory() {
        StatelessWebSubjectFactory subjectFactory = new StatelessWebSubjectFactory();
        return subjectFactory;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(CustomRealm customRealm, JwtRememberMeManager jwtRememberMeManager) {
        DefaultWebSecurityManager  securityManager = new DefaultWebSecurityManager ();
        securityManager.setSubjectFactory(defaultWebSubjectFactory());
        securityManager.setSessionManager(defaultWebSessionManager());
        securityManager.setRealm(customRealm);
        securityManager.setRememberMeManager(jwtRememberMeManager);

        ((DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) securityManager.getSubjectDAO())
                .getSessionStorageEvaluator())
                .setSessionStorageEnabled(false);
        return securityManager;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }
}
