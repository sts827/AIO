package com.wayplus.waytraveler.controller;


import com.wayplus.waytraveler.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PageController {


    @Value("${cookie-set.domain}")
    private String cookieDomain;
    @Value("${cookie-set.prefix}")
    private String cookieName;
    @Value("${upload.path.file}")

    private String fileUploadPath;
    private final Logger logger = LoggerFactory.getLogger(PageController.class);
    private final PageService pageService;

    @Autowired
    public PageController(PageService pageService){this.pageService=pageService;}


    @GetMapping(value={"","/"})
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){

        ModelAndView mav = new ModelAndView();

        mav.setViewName("index");

        return mav;
    }

}
