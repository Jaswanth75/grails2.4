package com.user.creation

import grails.converters.JSON
import grails.util.Holders

class UserController {
	def springSecurityService
	
    def index =  {
		println "here"
		render(view: "home/home.gsp");
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[userInstanceList: User.list(params), userInstanceTotal: User.count()]
	}
	
	def listAll = {
		
		
		//TODO: Add columns to be sent
		def propertiesToRender = ['userId','username','firstName','lastName','emailAddress','dateCreated']
 
		def filterPropertiesToRender = ['username','firstName','lastName','emailAddress']
		
		def currentsite = session["currentsite"]
		
		def filters = []
 
		filterPropertiesToRender.each { prop ->
		   filters << "p.${prop} like :filter"
		}
		def filter = filters.join(" OR ")
	
		def dataToRender = [:]
		dataToRender.sEcho = params.sEcho
		dataToRender.aaData=[]
	
		// Show both - active and inactive users in the UI.  If we decide to show only active users,
		// we need to modify the logic to only include User.countByEnabled(true) AND add the enabled clause in the query
		dataToRender.iTotalRecords = User.countByEnabled(true) + User.countByEnabled(false)
		dataToRender.iTotalDisplayRecords = dataToRender.iTotalRecords
	
		def query = new StringBuilder("from User as p")
		if ( params.sSearch ) {
		   query.append(" where (${filter})")
		}
		query.append(" order by p.${propertiesToRender[params.iSortCol_0 as int]} ${params.sSortDir_0}")
	
		def users = []
		if ( params.sSearch ) {
		   // Revise the number of total display records after applying the filter
		   def countQuery = new StringBuilder("select count(*) from User as p")
		   if ( params.sSearch ) {
			  countQuery.append(" where (${filter})")
		   }
		   def result = User.executeQuery(countQuery.toString(),	[filter: "%${params.sSearch}%"])
		   if ( result ) {
			  dataToRender.iTotalDisplayRecords = result[0]
		   }
		   params.max = Math.min(params.max ? params.int('max') : 10, 100)
		   
		   users = User.findAll(query.toString(),
			  [filter: "%${params.sSearch}%"],
			  [max: params.max as int, offset: params.iDisplayStart as int])
		} else {
		   users = User.findAll(query.toString(),
		   [max: params.iDisplayLength as int, offset: params.iDisplayStart as int])
		}
		def DATE_FORMAT = "MM/dd/yyyy hh:MM:mm";
		def sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
		users?.each { user ->
			//TODO: Add columns to be sent
			dataToRender.aaData << [user.userId,user.username,user.firstName,user.lastName,user.emailAddress,sdf.format(user.dateCreated),user.enabled]
		}
	
		render dataToRender as JSON
	
	}
	
	def create = {
		def userInstance = new User()
		userInstance.properties = params
		return [userInstance: userInstance]
	}
	
	
	 def save = {
		def userInstance = new User(params)
		userInstance.dateCreated = new Date()
		userInstance.lastModified = new Date()
		def principal = springSecurityService.principal
		String username = principal.username
		userInstance.emailAddress = userInstance.username
		userInstance.password = springSecurityService.encodePassword(params.password)
		
		
		if(User.findByUsernameAndEnabled(userInstance.username,true)){
			flash.error = "User with id " + userInstance.username +  " already exists"
			render(view: "create", model:[userInstance: userInstance]);
			return;
		}
		
		def c = User.createCriteria();
		def maxDcode = c.get {
				projections {
					max("userId")
				}
		}
		
		userInstance.userId = maxDcode + 10;
		userInstance.passwordExpired =true;
		
		params.findAll{pm->
			pm.key.length()>2 && pm.key[0..2]=="otm"
		}.each {  p->
			def keys = p.key.split("_");
			def classkey = keys[1]
			def addtokeyorig = keys[2]
			def addtokey = 'addTo' + addtokeyorig[0].toUpperCase() + addtokeyorig[1..-1]
			classkey = classkey[0].toUpperCase() + classkey[1..-1]
		
			User."$addtokeyorig"= null
	
			p.value.each { val->
				def temp = Class.forName(classkey, true, grailsApplication.classLoader).newInstance()
				temp = temp.get(val)
				User."$addtokey"(temp)
			}
		}
		
		
		boolean isSuccess = false;
		User.withTransaction { status ->
			if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
				addRoles(userInstance)
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.toString()])}"
				isSuccess = true
			}else{
				render(view: "create", model:[userInstance: userInstance])
				return
			}
		}
		redirect(action: "list")
		

	}
	def show = {
		def userInstance = User.get(params.id)
		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
		}
		else {
			[userInstance: userInstance]
		}
	}
		def edit = {
		def userInstance = User.get(params.id)
		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [userInstance: userInstance]
		}
	}
		def update = {
			println "params:"+params
		User userInstance = User.findByUserId( params.id )
		println "user:"+userInstance
		userInstance.lastModified = new Date()
		def principal = springSecurityService.principal
		String username = principal.username
		println "username:"+username
		def previousUserName = userInstance.username;
		if(userInstance) {
			
			if (userInstance.password != params.password) {
				params.password = springSecurityService.encodePassword(params.password)
				userInstance.passwordExpired = true;
			 }
			userInstance.properties = params
			
			userInstance.emailAddress = userInstance.username
			
			
			params.findAll{pm->
				pm.key.length()>2 && pm.key[0..2]=="otm"
			}.each {  p->
				def keys = p.key.split("_");
				def classkey = keys[1]
				def addtokeyorig = keys[2]
				def addtokey = 'addTo' + addtokeyorig[0].toUpperCase() + addtokeyorig[1..-1]
				classkey = classkey[0].toUpperCase() + classkey[1..-1]
		
				userInstance."$addtokeyorig"= null
	
				p.value.each { val->
					def temp = Class.forName(classkey, true, grailsApplication.classLoader).newInstance()
					temp = temp.get(val)
					userInstance."$addtokey"(temp)
				}
			}

			
			User.withTransaction { status ->
				if(!userInstance.hasErrors() && userInstance.save(flush:true)) {
					UserRole.removeAll(userInstance)
					addRoles userInstance
					flash.message = "User ${params.firstName} updated"
				}
				else {
					render(view:'edit',model:[userInstance:userInstance])
					return
				}
			}
			redirect(action: "list")
			
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action:edit,id:params.id)
		}
	}
		
		def delete = {
			def userInstance = User.get(params.id)
			if (userInstance) {
				try {
					User.withTransaction { status ->
						UserRole.removeAll(userInstance);
						userInstance.delete(flush: true)
						
					}
					flash.message = "User ${userInstance.firstName} account has been deleted"
					redirect(action: "list")
				}
				catch (org.springframework.dao.DataIntegrityViolationException e) {
					flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
					redirect(action: "show-1", id: params.id)
				}
			}
			else {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
				redirect(action: "list")
			}
		}
		
		def disable = {
			def userInstance = User.get(params.id)
			if (userInstance) {
				try {
					User.withTransaction { status ->
						userInstance.enabled = false;
						userInstance.save(flush: true)
					}
					flash.message = "User ${userInstance.firstName} account has been disabled"
					redirect(action: "list")
				}
				catch (org.springframework.dao.DataIntegrityViolationException e) {
					flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
					redirect(action: "show-1", id: params.id)
				}
			}
			else {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
				redirect(action: "list")
			}
		}
		
		protected void addRoles(user) {
			int priorityOrder = 1
			if(params.roles instanceof String){
				def Role role = Role.get(params.roles)
				UserRole.create(user, role, priorityOrder, true)
			}else{
				for (String key in params.roles) {
					def Role role = Role.get(key)
					UserRole.create(user, role, priorityOrder, true)
					priorityOrder = priorityOrder +1
				}
			}
		 }
		
}
