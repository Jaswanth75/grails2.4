package com.example.login

import grails.rest.RestfulController
import grails.util.Holders

import org.apache.commons.codec.digest.DigestUtils

class UserController extends RestfulController {
    static responseFormats = ['json']
	def scaffold = User
	
    UserController() {
        super(User)
    }
}
