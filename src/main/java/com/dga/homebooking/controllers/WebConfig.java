package com.dga.homebooking.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  // tag::customLoginViewController[]
//  @Override
//  public void addViewControllers(ViewControllerRegistry registry) {
//
//    registry.addViewController("/login");
//  }
  // end::customLoginViewController[]

}
