import com.user.creation.Role;
import com.user.creation.User;
import com.user.creation.UserRole;

class BootStrap {

    def init = { servletContext ->
		if(!Role.count()){
			new Role(authority: 'medstaff',description: 'Medical Staff',dateCreated: new Date(),lastModified: new Date(),lastModifiedBy: 'Jaswanth').save(flush: true,failOnError: true)
			new Role(authority: 'admin',description:'Administrator',dateCreated: new Date(),lastModified: new Date(),lastModifiedBy: 'Jaswanth').save(flush: true,failOnError: true)
			new Role(authority: 'newadmit',description:'New Admisson',dateCreated: new Date(),lastModified: new Date(),lastModifiedBy: 'Jaswanth').save(flush: true,failOnError: true)
			new Role(authority: 'accounts',description:'Accounting',dateCreated: new Date(),lastModified: new Date(),lastModifiedBy: 'Jaswanth').save(flush: true,failOnError: true)
			new Role(authority: 'medrec',description:'Medical Records Administrator',dateCreated: new Date(),lastModified: new Date(),lastModifiedBy: 'Jaswanth').save(flush: true,failOnError: true)
			new Role(authority: 'physicalThera',description:'Physical Therapist',dateCreated: new Date(),lastModified: new Date(),lastModifiedBy: 'Jaswanth').save(flush: true,failOnError: true)
			new Role(authority: 'nurse',description:'Nursing',dateCreated: new Date(),lastModified: new Date(),lastModifiedBy: 'Jaswanth').save(flush: true,failOnError: true)
			new Role(authority: 'nurseAss',description:'Nursing Assistant',dateCreated: new Date(),lastModified: new Date(),lastModifiedBy: 'Jaswanth').save(flush: true,failOnError: true)
			new Role(authority: 'nursing_sup',description:'Nursing Superintendent',dateCreated: new Date(),lastModified: new Date(),lastModifiedBy: 'Jaswanth').save(flush: true,failOnError: true)
			new Role(authority: 'other',description:'Other',dateCreated: new Date(),lastModified: new Date(),lastModifiedBy: 'Jaswanth').save(flush: true,failOnError: true)
		}
		if(!User.count()){
			new User(userId: 100,firstName: 'First',lastName: 'Last',emailAddress:'abcd@gmail.com',username:'abcd@gmail.com', password:'password01',cellPhoneNumber:'1234567890', state:'AL', country:'USA', address:'Address', city:'City',zipcode:'12345' ,dateCreated: new Date(),lastModified: new Date(),lastModifiedBy: 'Jaswanth').save(flush: true,failOnError: true)
		}
    }
    def destroy = {
    }
}
