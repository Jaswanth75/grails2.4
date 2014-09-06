package com.example.login

import grails.converters.JSON
import grails.rest.RestfulController

class RestServicesController extends RestfulController{
	static responseFormats = ['json']
    def index() { }
	
	def updateUserFirstName = {
		def result = new HashMap()
		if(params.token != null && params.token != ""){
			def authToken = AuthenticationToken.findByTokenValue(params.token)
			if(authToken != null){
				def userInstance = User.findByUsername(authToken.username)
				if(userInstance != null){
					if(params.firstname != null && params.firstname != ""){
						userInstance.firstname = params.firstname
						userInstance.save(flush:true)
						result.put("username", authToken.username)
						result.put("message", "User Firstname updated")
					}
					else{
						result.put("username", authToken.username)
						result.put("message", "No input given")
					}
				}
				else{
					result.put("username", authToken.username)
					result.put("message", "User not available. Access Denied")
				}
			}
			else{
				result.put("message", "Invalid Token. Access Denied")
			}
		}
		else{
			result.put("message", "Please provide the token info")
		}
		
		render result as JSON
	}
	
	def updateUserLastName = {
		
		def result = new HashMap()
		if(params.token != null && params.token != ""){
			def authToken = AuthenticationToken.findByTokenValue(params.token)
			if(authToken != null){
				def userInstance = User.findByUsername(authToken.username)
				if(userInstance != null){
					if(params.lastname != null && params.lastname != ""){
						userInstance.lastname = params.lastname
						userInstance.save(flush:true)
						result.put("username", authToken.username)
						result.put("message", "User Lastname updated")
					}
					else{
						result.put("username", authToken.username)
						result.put("message", "No input given")
					}
				}
				else{
					result.put("username", authToken.username)
					result.put("message", "User not available. Access Denied")
				}
			}
			else{
				result.put("message", "Invalid Token. Access Denied")
			}
		}
		else{
			result.put("message", "Please provide the token info")
		}
		
		render result as JSON
	
	}
	
}
