package com.user.creation

import java.util.Date;

class User {

	transient springSecurityService
	
	static final def STATES = ['AL', 'AK', 'AS', 'AZ', 'AR', 'CA', 'CO', 'CT', 'DE', 'DC', 'FM', 'FL', 'GA', 'GU',
		'HI', 'ID', 'IL', 'IN', 'IA', 'KS', 'KY', 'LA', 'ME', 'MH', 'MD', 'MA', 'MI', 'MN', 'MS', 'MO', 'MT', 'NE',
		'NV', 'NH', 'NJ', 'NM', 'NY', 'NC', 'ND', 'MP', 'OH', 'OK', 'OR', 'PW', 'PA', 'PR', 'RI', 'SC', 'SD', 'TN',
		'TX', 'UT', 'VT', 'VI', 'VA', 'WA', 'WV', 'WI', 'WY']
	
	static final def COUNTRIES = ['USA']

	long   userId
	String firstName
	String lastName
	String middleInitial
	String cellPhoneNumber
	String emailAddress
	String address
	String city
	String country
	String state
	String zipcode
	String username
	String password
	String additionalInfo = "{}"
	
	Date dateCreated
	Date lastModified
	
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		password blank: false
		emailAddress blank: false,nullable:false,email:true
		firstName blank: false,nullable:false
		lastName blank: false,nullable:false
		cellPhoneNumber blank:false, nullable:false
		emailAddress blank:false, nullable:false
		address blank: true,nullable:false
		state nullable: false, blank: true, inList: STATES
		country nullable: false, blank: true, inList: COUNTRIES
		zipcode blank: true,nullable:false
		city blank: true,nullable:false
		middleInitial blank: true, nullable:true
		additionalInfo nullable:true, blank:true
	}

	static mapping = {
		table 'users'
		version false
		enabled(sqlType: "TINYINT(1)")
		accountExpired(sqlType: "TINYINT(1)")
		accountLocked(sqlType: "TINYINT(1)")
		passwordExpired(sqlType: "TINYINT(1)")
		password column: '`password`'
		firstName(sqlType: "TEXT")
		lastName(sqlType: "TEXT")
		emailAddress(sqlType: "TEXT")
		dateCreated(column:"date_created")
		additionalInfo(column:"additional_info",sqlType: "TEXT")
		id generator: 'assigned', name: "userId", type: 'long'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

	def beforeInsert() {
		//encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
		//	encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}

