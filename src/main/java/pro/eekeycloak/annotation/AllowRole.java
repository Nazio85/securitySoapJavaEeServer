package pro.eekeycloak.annotation;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;


@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, TYPE, METHOD})
@InterceptorBinding
public @interface AllowRole {
    @Nonbinding
    String[] roles() default {};
}
