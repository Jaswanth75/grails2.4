package com.example.login

class User {

	transient springSecurityService

	String username
	String password
	String firstname
	String lastname
	String gender
	
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		password blank: false
		firstname blank:true
		lastname blank:true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

	def beforeInsert() {
	//	encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			//encodePassword()
		}
	}

	protected void encodePassword() {
		//password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
	
}
