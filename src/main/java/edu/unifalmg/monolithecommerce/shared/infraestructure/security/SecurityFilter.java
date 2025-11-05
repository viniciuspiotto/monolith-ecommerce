package edu.unifalmg.monolithecommerce.shared.infraestructure.security;

import edu.unifalmg.monolithecommerce.iam.application.DTO.UserDTO;
import edu.unifalmg.monolithecommerce.iam.application.DTO.commands.GetUserByEmailCommand;
import edu.unifalmg.monolithecommerce.iam.application.port.in.GetUserByEmailPort;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.security.utils.CustomUserDetails;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.security.utils.JWTUtil;
import edu.unifalmg.monolithecommerce.iam.infraestructure.adapter.security.utils.TokenExtractor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final GetUserByEmailPort getUserByEmailUseCase;
    private final TokenExtractor tokenExtractor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException {

        var token = tokenExtractor.recoveryToken(request);

        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            filterChain.doFilter(request, response);
            return;
        }

        if(token != null && jwtUtil.validateToken(token)){

            GetUserByEmailCommand cmd = new GetUserByEmailCommand(jwtUtil.extractUsername(token));
            UserDTO userDto = getUserByEmailUseCase.execute(cmd);
            CustomUserDetails customUserDetails = new CustomUserDetails(userDto);

            if(SecurityContextHolder.getContext().getAuthentication() == null){
                var auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        }

        filterChain.doFilter(request, response);
    }
}
