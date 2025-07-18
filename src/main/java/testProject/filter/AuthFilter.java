package testProject.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final List<String> EXCLUDED = List.of(
            "/index.jsp", "/public/login.jsp", "/public/register.jsp",
            "/css/", "/js/", "/images/"
    );
    @Override
    public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  req  = (HttpServletRequest) rq;
        HttpServletResponse resp = (HttpServletResponse) rs;
        String uri = req.getRequestURI().substring(req.getContextPath().length());

        boolean ok = EXCLUDED.stream().anyMatch(uri::startsWith);
        if (ok) {
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
