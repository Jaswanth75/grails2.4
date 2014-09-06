class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller:'loginUser',view:"/loginUser/login")
        "500"(view:'/error')
		
		// RESTService api
		"/api/updateUserFirstName"(controller:'restServices', action:'updateUserFirstName')
		"/api/updateUserLastName"(controller:'restServices', action:'updateUserLastName')
	}
}
