
import com.example.login.Role
import com.example.login.User
import com.example.login.UserRole

import org.apache.commons.codec.digest.DigestUtils
class BootStrap {

    def init = { servletContext ->
		def admin = new Role(authority: 'ROLE_ADMIN').save(flush: true)
		def adminUser = new User(username: 'user', password: 'pass', firstname:'First', lastname:'Last',gender:'Male').save(flush: true)
		

		UserRole.create adminUser, admin, true

		assert User.count() == 1
		assert Role.count() == 1
		assert UserRole.count() == 1
    }
    def destroy = {
    }
}
