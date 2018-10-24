package pro.eekeycloak.interceptor;

import pro.eekeycloak.annotation.AllowRole;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@AllowRole
public class RoleInterceptor {

    @AroundInvoke
    protected Object invocation(final InvocationContext ic) throws Exception {
        List<String> tokenRoles = new ArrayList<>();
        Object tokenRolesItem = ic.getContextData().get(LoginInterceptor.TOKEN_KEY);

        if(tokenRolesItem != null) {
            if(tokenRolesItem instanceof List) {
                tokenRoles = (List<String>) tokenRolesItem;
            }
        }

        List<String> allowMethodRoles = getMethodRoles(ic);
        if(!allowMethodRoles.isEmpty()) {
            checkAllowedRoles(tokenRoles, allowMethodRoles);
        }

        List<String> allowClassRoles = getClassRoles(ic);
        if(!allowClassRoles.isEmpty() && allowMethodRoles.isEmpty()) {
            checkAllowedRoles(tokenRoles, allowClassRoles);
        }

        return ic.proceed();
    }

    private void checkAllowedRoles(List<String> tokenRoles, List<String> annotationRoles) throws Exception {
        if(tokenRoles.isEmpty()) {
            throw new javax.ejb.EJBAccessException("Token roles is empty");
        }

        for(String role : tokenRoles) {
            if(annotationRoles.contains(role)) {
                return;
            }
        }

        throw new Exception("Token roles is not allowed");
    }


    private List<String> getMethodRoles(InvocationContext invocationContext) {
        Annotation[] annotations = invocationContext.getMethod().getDeclaredAnnotations();
        return getAnnotationRoles(annotations);
    }

    private List<String> getClassRoles(InvocationContext invocationContext) {
        Annotation[] annotations = invocationContext.getTarget().getClass().getDeclaredAnnotations();
        return getAnnotationRoles(annotations);
    }

    private List<String> getAnnotationRoles(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof AllowRole) {
                String[] roles = ((AllowRole) annotation).roles();
                return Arrays.asList(roles);
            }
        }

        return new ArrayList<>();
    }
}