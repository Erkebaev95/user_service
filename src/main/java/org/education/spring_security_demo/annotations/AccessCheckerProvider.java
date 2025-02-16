package org.education.spring_security_demo.annotations;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.education.spring_security_demo.enums.Role;
import org.education.spring_security_demo.model.User;
import org.education.spring_security_demo.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class AccessCheckerProvider {

    private final UserService userService;

    @Before(value = "@within(accessChecker) || @annotation(accessChecker)", argNames = "accessChecker")
    public void checkAccess(AccessChecker accessChecker) {
        if (accessChecker != null) {
            if (accessChecker.anonymous()) {
                return;
            }

            var username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> currentUser = userService.findByUsername(username);
            User user;
            if (currentUser.isEmpty()) {
                throw new BadCredentialsException("Unauthorized!");
            } else {
                user = currentUser.get();
            }

            if (accessChecker.acceptRoles().length > 0) {
                if (Arrays.stream(accessChecker.acceptRoles()).noneMatch(type -> type == user.getRole())) {
                    throw new AccessDeniedException("Access denied!");
                }
            }
            if (!checkAccess(accessChecker, user)) {
                throw new AccessDeniedException("Access denied!");
            }
        }
    }

    private boolean checkAccess(AccessChecker accessChecker, User user) {
        //Checking admin access
        if (accessChecker.admin()) {
            if (user.getRole() == Role.ROLE_ADMIN) {
                return true;
            }
        }
        if (isEmpty(accessChecker)) {
            return true;
        }

        //Checking accepted roles
        boolean hasAcceptRoleAccess = false;
        if (accessChecker.acceptRoles().length > 0) {
            for(Role systemRole : accessChecker.acceptRoles()) {
                if (user.getRole() == systemRole) {
                    hasAcceptRoleAccess = true;
                    break;
                }
            }
        }

        //Checking rejected roles
        boolean hasNotRejectRoleAccess = false;
        if (accessChecker.rejectRoles().length > 0) {
            hasNotRejectRoleAccess = true;
            for(Role systemRole : accessChecker.rejectRoles()) {
                if (user.getRole() == systemRole) {
                    hasNotRejectRoleAccess = false;
                    break;
                }
            }
        }

        return hasAcceptRoleAccess || hasNotRejectRoleAccess;
    }

    private boolean isEmpty(AccessChecker accessChecker) {
        return accessChecker.acceptRoles().length == 0 && accessChecker.rejectRoles().length == 0;
    }
}
