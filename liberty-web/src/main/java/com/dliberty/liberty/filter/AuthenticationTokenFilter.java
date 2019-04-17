package com.dliberty.liberty.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 从request中获取token,并将token转换为用户，放置到当前的spring context内
 * 这个类必须写一个@Service注解，否则spring不会加载它为filter
 * @author LG
 *
 */
@Service
public class AuthenticationTokenFilter  extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//String requestHeader = request.getHeader("Authorization");
		
		
		//UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, credentials, authorities);
		//authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		//SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

}
