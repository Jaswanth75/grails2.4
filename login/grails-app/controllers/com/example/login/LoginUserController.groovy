package com.example.login

class LoginUserController {
    static responseFormats = ['json']
	
	def login = {
		
		if(session.getAttribute("user") != null){
			redirect(controller:"home", action:"index") 
		}
	}
	
	
	def authenticate = {
		println "params:"+params
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
	
	  
	  def logout = {
		  def user = session.getAttribute("user")
		  if(user != null){
			  def authToken = AuthenticationToken.findByUsername(user.username)
			  authToken.delete(flush:true)
		  }
		  session.setAttribute("user",null);
		  redirect(action:"login")
	  }
}
