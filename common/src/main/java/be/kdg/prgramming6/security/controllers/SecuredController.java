package be.kdg.prgramming6.security.controllers;

import be.kdg.prgramming6.security.messages.SecuredMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured")
public class SecuredController {


    @GetMapping("principal")
    public SecuredMessage getSecuredWithPrincipal(){
        Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getInfoFromToken(token,"I got the information via the current security context");
    }

    @GetMapping("annotation")
    public SecuredMessage getSecuredWithPrincipal(@AuthenticationPrincipal Jwt token){
        return getInfoFromToken(token, "I got the information via an annotation");
    }

    @GetMapping("user")
    @PreAuthorize("hasAuthority('user')")
    public SecuredMessage getSecuredWithUserRole(@AuthenticationPrincipal Jwt token){
        return getInfoFromToken(token, "I'm a user");
    }


    @GetMapping("admin")
    @PreAuthorize("hasAuthority('admin')")
    public SecuredMessage getSecuredWithAdminRole(@AuthenticationPrincipal Jwt token){
        return getInfoFromToken(token, "I'm an admin");
    }





    private static SecuredMessage getInfoFromToken(Jwt token, String message){
        return SecuredMessage.builder()
                    .subjectid(token.getClaimAsString("sub"))
                .firstName(token.getClaimAsString("given_name"))
                .lastName(token.getClaimAsString("family_name"))
                .email(token.getClaimAsString("email"))
                .message(message)
                .build();

    }

}
