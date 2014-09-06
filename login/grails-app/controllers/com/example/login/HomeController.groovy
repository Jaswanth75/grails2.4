package com.example.login

import grails.converters.JSON
import grails.rest.RestfulController;

class HomeController {

	/**
	 * 
	 * Called when user tries to login without logging out
	 */
    def index() { 
		def map = new HashMap();
		map.put("message", "User already logged in")
		def userInstance = session.getAttribute("user");
		if(userInstance){
			map.put("username", session.getAttribute("user").username)
		}
		render map as JSON
		
	}
	
	/**
	 * Displays message for authenticated user
	 */
	def authenticatedUser = {
		def map = new HashMap();
		map.put("message", "Login Successful")
		map.put("token", params.token)
		def userInstance = session.getAttribute("user");
		if(userInstance){
			map.put("username", session.getAttribute("user").username)
		}
		render map as JSON
		
	}
	
	/**
	 * Displays message for invali user
	 */
	def invalidUser = {
		def map = new HashMap();
		map.put("message", "Access Denied")
		def userInstance = session.getAttribute("user");
		if(userInstance){
			map.put("username", session.getAttribute("user").username)
		}
		render map as JSON
	}
}
