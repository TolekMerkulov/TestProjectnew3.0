package testProject.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  req  = (HttpServletRequest) rq;
        HttpServletResponse resp = (HttpServletResponse) rs;

        String path = req.getRequestURI()
                .substring(req.getContextPath().length());

        if (       path.isEmpty()
                || path.equals("/")
                || path.equals("/index.jsp")
                || path.startsWith("/public/")
                || path.equals("/login")
                || path.equals("/register")   )
        {
            chain.doFilter(rq, rs);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/public/login.jsp");
            return;
        }

        chain.doFilter(rq, rs);
    }
}
