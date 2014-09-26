package com.user.creation

import grails.plugin.springsecurity.SpringSecurityUtils
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class HomeController {

	def springSecurityService
	
    def index = {
        if(springSecurityService.isLoggedIn()){	
			redirect(controller:"patient",action:"list")
			return
        }else{
            redirect(uri:"/login/auth")
        } 
    }
}
