package com.bytmasoft.dss.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class BFFUtils {

public String getUsername() {
	if (SecurityContextHolder.getContext() != null)
		if (SecurityContextHolder.getContext().getAuthentication() != null)
			if (SecurityContextHolder.getContext().getAuthentication().getName() != null)
				return SecurityContextHolder.getContext().getAuthentication().getName();
	return "No User";
}

}
