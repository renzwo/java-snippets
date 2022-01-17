package aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Aspect
@Component
@RequiredArgsConstructor
public class MyAspect {

    private final MyService myService;
    private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("Europe/Berlin"));

    @AfterReturning(value = "@annotation(path.to.annotation.LogMy)", returning = "returnObject")
    public void afterReturningLogMy(JoinPoint joinPoint, MyTwo returnObject) throws NoSuchMyTwoException, IllegalAccessException {
        final var authenticatedUser = <someUserFromSecContext>;
        switch (MyMethod.valueOf(joinPoint.getSignature().getName())) {
            case createMyTwo:
                myService.createMy(
                    authenticatedUser,
                    MyRequest.builder()
                        .my(MyMethod.createMyTwo.getName())
                        .description(MyMethod.createMyTwo.getDescription())
                        .source(MySource.remote)
                        .build());
                break;
            case updateMyTwo:
                myService.createMy(
                    authenticatedUser,
                    MyRequest.builder()
                        .my(MyMethod.updateMyTwo.getName())
                        .description(MyMethod.updateMyTwo.getDescription())
                        .source(MySource.remote)
                        .build());
                break;
            default:
                break;
        }
    }

    /* just to remember afterreturning arguments */
    @AfterReturning(value = "createMyThreePointcut() && args(request,user,..)",
        returning = "returnObject", argNames = "request,user,returnObject")
    public void afterReturningMy(final RequestResource request, final AuthenticatedUser user,
                                          final CreateResponse returnObject) {
        myService.createMyBla(user, request, returnObject);
    }

    @AfterReturning(value = "processMyTwoPointcut() && args(myTwo)")
    public void afterReturningLogMy(final MyTwo myTwo) {
        myService.createMy(myTwo);
    }

    @Pointcut("logMyAnnotation() && within(*..*MyTwoService) && execution(* processMyTwo(..))")
    private void processMyTwoPointcut() {
    }

    @Pointcut("@annotation(path.to.annotation.LogMy)")
    private void logMyAnnotation() {
    }
}
