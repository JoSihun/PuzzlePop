package com.ssafy.puzzlepop.user.filter;

import com.ssafy.puzzlepop.user.service.CustomUserDetailsService;
import com.ssafy.puzzlepop.user.util.TokenProvider;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String jwt = getJwtFromRequest(request);

        if (StringUtils.isNotBlank(jwt) && tokenProvider.validateToken(jwt)) {
            Long userId = tokenProvider.getUserIdFromToken(jwt);

            UserDetails userDetails = customUserDetailsService.loadUserById(userId);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}













//    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
//
//    private final UserService userService;
//    private final UserPrincipalService userPrincipalService;
//
//    private final AuthenticationFailureHandler failureHandler;
//
//    private final JwtProvider tokenProvider;
//
//    private final JwtResolver tokenResolver;
//
//    private AuthenticationSuccessHandler successHandler; // this is not necessary, for future usage
//
//    /**
//     * JwtAuthenticationProcessingFilter is required these fields.
//     * you can modify this filter's functionality by changing these fields.
//     * @param failureHandler In the case of the invalid jwt token,
//     *                       default behavior is just to redirect to controller to response "Invalid_Token" error.
//     */
//    public TokenAuthenticationFilter(
//            JwtProvider tokenProvider,
//            JwtResolver tokenResolver,
//            TokenAuthenticationFailureHandler failureHandler,
//            UserPrincipalService userPrincipalService, UserService userService) {
//
//        this.failureHandler = failureHandler;
//        this.tokenProvider = tokenProvider;
//        this.tokenResolver = tokenResolver;
//        this.userService = userService;
//        this.userPrincipalService = userPrincipalService;
//    }
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String token = tokenResolver.resolveTokenOrNull(request);
//
//        if (SecurityContextHolder.getContext().getAuthentication() == null && StringUtils.hasText(token)) {
//
//            try {
//                if (!tokenProvider.validate(token))
//                    throw new InvalidTokenException("유효하지 않은 토큰");
//
//                JwtAuthenticationResult authentication = (JwtAuthenticationResult) tokenProvider.decode(token);
////                Object principal = UserPrincipalService.loadUserPrincipal(authentication);
////                authentication.setPrincipal(principal);
//                PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
////                String provider = "google";
//                Long userId = principalDetails.getUser().getId();
//                String email = principalDetails.getEmail();
//                userService.getUserByIdAndEmail(userId, email);
//
//                // handle for authentication success
//                successfulAuthentication(request, response, filterChain, authentication);
//
//            } catch (InvalidTokenException | UserPrincipalNotFoundException e) {
//                this.unsuccessfulAuthentication(request, response, e);
//                return;
//            }
//        }
//
//        // If client doesn't have any token or under redirection, keep going to process client's request
//        filterChain.doFilter(request, response);
//    }
//
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//                                  Authentication authResult) throws IOException, ServletException {
//
//        authResult.setAuthenticated(true);
//        ((JwtAuthenticationResult) authResult).setDetails(request.getRemoteAddr());
//        SecurityContextHolder.getContext().setAuthentication(authResult);
//        logger.trace("jwt token authentication success!");
//
//        if (this.successHandler != null) {
//            logger.trace("Handling authentication success");
//            this.successHandler.onAuthenticationSuccess(request, response, authResult);
//        }
//    }
//
//
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                              AuthenticationException failed) throws IOException, ServletException {
//
//        SecurityContextHolder.clearContext();
//        logger.trace("jwt token validation fail", failed);
//        logger.trace("Cleared SecurityContextHolder");
//        logger.trace("Handling authentication failure");
//        this.failureHandler.onAuthenticationFailure(request, response, failed); // 리다이렉트 해야함?
//    }
//}