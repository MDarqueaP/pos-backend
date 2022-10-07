package ec.com.edimca.posbackend.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ec.com.edimca.posbackend.constants.SecurityConstants.*;

/**
 *
 * @company Databits
 * @author Manuel Darquea
 * @project databits-core
 * @date 2022/05/16
 * @className AuthorizationFilter.java
 *
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private static Logger logger = LogManager.getLogger();

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> EXCLUDE_URL = Arrays.asList("/app/**");

    public AuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String header = req.getHeader(HEADER_NAME);

        if (header == null || !header.startsWith(TOKEN_PREFIX) || header.equals("Bearer null")) {
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setStatus(401);
            res.getWriter().print("Missing access token");
            res.getWriter().flush();
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            logger.error(e.getMessage());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setStatus(401);
            res.getWriter().print(e.getMessage());
            res.getWriter().flush();
            return;
        }

        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
            throws TokenExpiredException, Exception {

        String token = request.getHeader(HEADER_NAME);

        if (token != null) {
            try {
                Map<String, Claim> claims = JWT.require(Algorithm.HMAC512(KEY.getBytes())).build()
                        .verify(token.replace(TOKEN_PREFIX, "")).getClaims();

                String sub = StringUtils.substringBetween(claims.get("roles").toString(), "[", "]");

                List<String> rolesList = new ArrayList<>();

                if (!sub.equals("")) {
                    rolesList = Arrays.asList(sub.split(","));
                }

                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                rolesList.forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role.replace("\"", "")));
                });

                if (claims.get("sub") != null) {
                    return new UsernamePasswordAuthenticationToken(claims.get("sub"), null, authorities);
                }
            } catch (Exception e) {
                throw new TokenExpiredException(e.getMessage());
            }
        }
        throw new Exception("The token is not valid");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return EXCLUDE_URL.stream().anyMatch(exclude -> pathMatcher.match(exclude, request.getServletPath()));
    }
}
