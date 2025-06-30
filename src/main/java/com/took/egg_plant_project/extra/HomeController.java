package com.took.egg_plant_project.extra;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping({"index/index","/"})
    public String index(){
        return "index/index";
    }
}
