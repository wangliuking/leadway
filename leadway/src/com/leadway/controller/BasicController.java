package com.leadway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class BasicController {
	@RequestMapping(value="/*.html")
	public void basc(){
		System.out.println("--跳转-----");
	}
}
