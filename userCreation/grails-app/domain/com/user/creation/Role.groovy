package com.user.creation

import java.util.Date;

class Role {

	String authority
	String description
	
	Date dateCreated
	Date lastModified
	String lastModifiedBy
	
	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
		description blank: true,nullable:false
	}
}
