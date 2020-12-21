package cash.muro.aop;

import java.net.InetAddress;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import cash.muro.entities.AccessedResource;
import cash.muro.entities.MuroSettings;
import cash.muro.services.AccessedResourceService;
import cash.muro.springsecurity.authentication.cashid.MuroAuthenticationToken;

@Aspect
@Component
public class MuroAspect {
	
	private static final String MSG = "msg";
	
	@Autowired
    private MessageSource messageSource;

	@Autowired
	private MuroSettings muroSettings;
	
	@Autowired
	private AccessedResourceService resourceService;

	@Around("@annotation(cash.muro.annotations.MuroRequest)")
	public ModelAndView saveMuroAccess(ProceedingJoinPoint pjp) throws Throwable {
		ModelAndView mv = (ModelAndView)pjp.proceed();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		MuroAuthenticationToken principal = (MuroAuthenticationToken)request.getUserPrincipal();
		URI uri = new URI(request.getRequestURI());
		if (principal != null && resourceService.allowed(principal.getName(), uri)) {
			InetAddress ip = InetAddress.getByName(request.getRemoteAddr());
			AccessedResource resource = new AccessedResource(principal.getName(), 
															uri, 
															ip);
			resourceService.save(resource);
			principal.setHadBalance(true);
			principal.setBalance(resourceService.freeBalance(principal.getName()), resourceService.boughtBalance(principal.getName()));
		} else {
			mv.addObject(MuroSettings.MURO_LIST, muroSettings.limitList((List<?>)mv.getModel().get(MuroSettings.MURO_LIST)));
			if (principal == null) {
				mv.addObject(MSG, messageSource.getMessage("need-login", null, null));
			} else {
				principal.setHadBalance(false);
				mv.addObject(MSG, messageSource.getMessage("muro-no-balance", null, null));
			}
		}
		return mv;
	}
}
