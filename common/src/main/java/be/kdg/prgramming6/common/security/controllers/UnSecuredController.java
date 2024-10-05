package be.kdg.prgramming6.common.security.controllers;

import be.kdg.prgramming6.common.security.messages.UnsecuredMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unsecured")
public class UnSecuredController {


    @GetMapping
    public UnsecuredMessage getUnsecured(){
        return new UnsecuredMessage();
    }


}
