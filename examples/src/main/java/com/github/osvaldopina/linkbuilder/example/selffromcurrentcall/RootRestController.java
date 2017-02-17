package com.github.osvaldopina.linkbuilder.example.selffromcurrentcall;

import com.github.osvaldopina.linkbuilder.annotation.SelfFromCurrentCall;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootRestController {

    @RequestMapping("/")
    @SelfFromCurrentCall
    public ResourceSupport root() {
        return new ResourceSupport();
    }

 }

