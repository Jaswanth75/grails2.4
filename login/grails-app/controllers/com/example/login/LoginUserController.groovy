package com.example.login

import grails.converters.JSON

class LoginUserController {
    static responseFormats = ['json']
	
	/**
	 * DIsplay the login page
	 * 
	 * If user already logged-in shows the corresponding message
	 */
	def login = {
		
		if(session.getAttribute("user") != null){
			redirect(controller:"home", action:"index") 
		}
	}
	
	
	/**
	 * Authenticates the credentials, generates a token per user and redirects correspondingly
	 */
	def authenticate = {
		def user = User.findByUsernameAndPassword(params.login,params.password)
		if(user){
			if(session.getAttribute("user") == null){
				def authToken = new AuthenticationToken()
				authToken.tokenValue = UUID.randomUUID().toString()
				authToken.username = user.username
				authToken.save(flush:true)
			}
			def token = AuthenticationToken.findByUsername(user.username).tokenValue
			session.setAttribute("user",user);
			redirect(controller:"home",method:"post", action:"authenticatedUser",params:[token:token])
		}else{
		   redirect(controller:"home",method:"post", action:"invalidUser")
		}
	  }
	
	  /**
	   * Logs out based on the token value
	   */
	  def logout = {
		  def result = new HashMap()
		  def token = params.token
		  if(token != null && token != ""){
			  def authToken = AuthenticationToken.findByTokenValue(token)
			  if(authToken != null){
				  authToken.delete(flush:true)
				  result.put("message", "User logged out successfully");
			  }
			  else{
				  result.put("message", "Invalid token value");
			  }
		  }
		  else{
			  result.put("message", "Please pass token value");
		  }
		  session.setAttribute("user",null);
		  render result as JSON
	  }
}
