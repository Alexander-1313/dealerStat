package com.leverx.dealerst.config.jwt;


import com.leverx.dealerst.entity.CustomUserDetails;
import com.leverx.dealerst.service.UserSecurityService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private UserSecurityService userSecurityService;
//
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest req,
//            HttpServletResponse res,
//            FilterChain filterChain) throws ServletException, IOException {
//
//        try {
//            String jwt = req.getHeader("Authorization");
//
//            if (jwt != null) {
//                String username = jwtUtils.getUsername(jwt);
//
//                // isUserAuthentication
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//                if (username != null && authentication == null) {
//                    UserDetails userDetails = userSecurityService.loadUserByUsername(username);
//
////                    User user = new User();
////                    user.fromDto(userDetails);
//
//
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                }
//
//            }
//
////            filterChain.doFilter(req, res);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}

@Component
@Log
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtUtils jwtProvider;

    @Autowired
    private UserSecurityService customUserDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        logger.info("do filter...");
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
//        System.out.println("token = " + token);
        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getUsername(token);
//            System.out.println("userLogin = " + userLogin);
            CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(userLogin);
//            System.out.println("customUserDetails = " + customUserDetails);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}

