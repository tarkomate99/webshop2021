package hu.rf1.webshop.Webshop2.Config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationSuccessWithSessionHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler, LogoutSuccessHandler {

    public static final String USERNAME = "username";
    public static final String user_type = "user_type";

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        request.getSession().removeAttribute(USERNAME);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
        request.getSession().setAttribute(USERNAME, request.getParameter(USERNAME));
        if (request.getParameter(USERNAME).equals("admin@admin.com")){
            request.getSession().setAttribute(user_type, "admin");
        }else if (request.getParameter(USERNAME).equals("uploader@uploader.com")){
            request.getSession().setAttribute(user_type,"uploader");
        }else{
            request.getSession().setAttribute(user_type, "user");
        }

    }
}
