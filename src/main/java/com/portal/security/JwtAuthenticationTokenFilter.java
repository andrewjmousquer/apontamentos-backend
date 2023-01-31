package com.portal.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.portal.utils.PortalJwtTokenUtil;
import com.portal.utils.PortalStaticVariables;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private PortalJwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String token = request.getHeader(PortalStaticVariables.TOKEN_HEADER);

		if (token != null && token.startsWith(PortalStaticVariables.BEARER_PREFIX)) {
			token = token.substring(7);
		}

		String roleName = jwtTokenUtil.getRole(token);
		if (roleName != null) {
			if (jwtTokenUtil.tokenValido(token)) {
				String username = jwtTokenUtil.getUsernameFromToken(token);
				Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
						AuthorityUtils.createAuthorityList("ROLE_USER"));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}

}
