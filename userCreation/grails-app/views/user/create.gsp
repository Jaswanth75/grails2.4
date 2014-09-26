<%@ page import="com.user.creation.User"%>
<%@page import="java.util.*" %>
<%@page import="com.user.creation.Role" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${resource(dir:'css',file:'ui.multiselect.css')}" />
       <!--  <link rel="stylesheet" href="${resource(dir:'css',file:'common.css')}" /> -->  
        <link type="text/css" rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.10/themes/ui-lightness/jquery-ui.css" />
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.10/jquery-ui.min.js"></script>
		<script type="text/javascript" src="${resource(dir:'js', file:'ui.multiselect.js')}"></script>
		<script type="text/javascript" src="${resource(dir:'js/plugins/localisation', file:'jquery.localisation-min.js')}"></script>
		<script type="text/javascript" src="${resource(dir:'js/plugins/scrollTo', file:'jquery.scrollTo-min.js')}"></script>
<link rel="stylesheet" href="${resource(dir:'css',file:'jquery.stepy.css')}" />
<script type="text/javascript" src="${resource(dir:'js', file:'jquery.stepy.js')}"></script>
<g:set var="entityName"
	value="${message(code: 'user.label', default: 'User')}" />
<title><g:message code="default.create.label"
		args="[entityName]" /></title>
		
	<script type="text/javascript">
		$(function(){
			$.localise('ui-multiselect', {/*language: 'en',*/ path: 'js/locale/'});
			$(".multiselect").multiselect();
			$('#switcher').themeswitcher();
		});
	</script>
<g:javascript>
		
	$(document).ready(function() {
  	$("#user_create").validationEngine('attach');    
		$('#user_create').stepy({
		    onNext: function(index) {
			   return $("#user_create").validationEngine('validate', {promptPosition : "topLeft", scroll: false});	
			},
			titleClick:	true
		});
		$("#roles").multiselect({searchable: false,dividerLocation:0.5});
		$("#zipcode").mask("99999");
		$("#cellPhoneNumber").mask("(999)999-9999");
		
		
	});
      </g:javascript>
        
</head>
<body>
       
				<div style="color: #181818;margin-top: 10px;" align="center"><h2>
						<g:message code="default.create.label" args="[entityName]" />
					</h2> </div>
				<div id="message">
					<g:if test="${flash.message}">
						<div class="message">
							${flash.message}
						</div>
					</g:if>
					<g:if test="${flash.error}">
						<div class="errors">
							${flash.error}
						</div>
					</g:if>
					<g:hasErrors bean="${userInstance}">
						<div class="errors">
							<g:renderErrors bean="${userInstance}" as="list" />							
						</div>
					</g:hasErrors>
				</div>
					<g:form action="save" name="user_create" autocomplete="off">
						<fieldset title="Step-1">
							<legend>Profile Information</legend>
							<table class="nobordertable" border="0" cellspacing="0" cellpadding="0">
								<tbody>
									<tr class="prop">
										<td width="10%" class="name""><span style="color: red">*</span>
											<label for="fName"><g:message code="user.fName.label"
													default="First Name" />:</label>
										</td>
										<td width="20%"
											class="value ${hasErrors(bean: userInstance, field: 'fName', 'errors')}">
											<g:textField class="validate[required,custom[alphaNumericRequired]]" name="firstName" value="${userInstance?.firstName}" />

										</td>
										<td width="5%" class="name"><label for="mInitial"><g:message
													code="user.mInitial.label" default="Middle Initial" />:</label>
										</td>
										<td width="5%"
											class="value ${hasErrors(bean: userInstance, field: 'mInitial', 'errors')}">
											<g:textField size="5" maxlength="2" class="validate[custom[alphaNumericRequired]]" name="middleInitial"
												value="${userInstance?.middleInitial}" />
										</td>
										
									</tr>
									<tr>
										<td width="5%" class="name"><span style="color: red">*&nbsp;</span><label
											for="lName"><g:message code="user.lName.label"
													default="Last Name" />:</label>
										</td>
										<td width="10%"
											class="value ${hasErrors(bean: userInstance, field: 'lName', 'errors')}">
											<g:textField class="validate[required,custom[alphaNumericRequired]]" size="40" name="lastName"
												value="${userInstance?.lastName}" />
										</td>
										<td width="70%" colspan="2"></td>
									</tr>
									<tr class="prop">
										<td width="5%" class="name"><span style="color: red">*</span>
											<label for="address"><g:message
													code="user.address.label" default="Street Address" /> </label>
										</td>
										<td width="25%" colspan="3"
											class="value ${hasErrors(bean: userInstance, field: 'address', 'errors')}">
											<g:textField class="validate[required,custom[alphaNumericRequired]]" size="50" maxLength="100" name="address"
												value="${userInstance?.address}" />
										</td>
										<td width="70%" colspan="2"></td>
									</tr>
									<tr class="prop">
										<td width="10%" class="name"><span style="color: red">*</span>
											<label for="city"><g:message code="user.city.label"
													default="City" /> </label>
										</td>
										<td width="20%"
											class="value ${hasErrors(bean: userInstance, field: 'city', 'errors')}">
											<g:textField class="validate[required,custom[alphaNumericRequired]]" name="city" value="${userInstance?.city}" />
										</td>
										<td width="10%" class="name"><span style="color: red">*</span>
											<label for="state"><g:message code="user.state.label"
													default="State" /> </label>
										</td>
										<td width="20%"
											class="value ${hasErrors(bean: userInstance, field: 'state', 'errors')}">
											<g:select class="validate[required]" name="state"
												from="${userInstance.constraints.state.inList}"
												value="${userInstance?.state}"
												valueMessagePrefix="user.state" noSelection="['': '']" /></td>
										

									</tr>
									<tr class="prop">
										<td width="10%" class="name"><span style="color: red">*</span>
											<label for="country"><g:message
													code="user.country.label" default="Country" /> </label>
										</td>
										<td width="20%"
											class="value ${hasErrors(bean: userInstance, field: 'country', 'errors')}">
											<g:select class="validate[required]" name="country"
												from="${userInstance.constraints.country.inList}"
												value="${userInstance?.country}"
												valueMessagePrefix="user.country" noSelection="['': '']" />
										</td>
										<td width="10%" class="name"><span style="color: red">*</span>
											<label for="zipcode"><g:message
													code="user.zipcode.label" default="Zipcode" /> </label>
										</td>
										<td width="20%"
											class="value ${hasErrors(bean: userInstance, field: 'zipcode', 'errors')}">
											<g:textField class="validate[required,custom[zipcode]]" maxlength="5" name="zipcode"
												value="${userInstance?.zipcode}" />
										</td>
										<td width="70%" colspan="2"></td>
									</tr>

									<tr class="prop">
										<td width="10%" class="name"><span style="color: red">*&nbsp;</span><label
											for="cellPhoneNumber"><g:message
													code="user.cellPhoneNumber.label"
													default="Cell Phone Number" /><br> </label>(xxx)xxx-xxxx</td>
										<td width="20%"
											class="value ${hasErrors(bean: userInstance, field: 'cellPhoneNumber', 'errors')}">
											<g:textField class="validate[required,custom[phone]]" name="cellPhoneNumber"
												value="${userInstance?.cellPhoneNumber}" />
										</td>
										
									</tr>
									
								</tbody>
							</table>
						</fieldset>
						<fieldset title="Step-2">
							<legend>Access Information</legend>
							<p> <h4>Passwords Must be at least 8 characters. Must contain at least one one lower case letter, one upper case letter, one digit and one special character
			      				Valid special characters are -   @#$%^&+=.</h4>
			  				</p>
			  				
							<table class="nobordertable" style="margin-top:10px">
								<tr class="prop">
									<td width="10%" class="name"><span style="color: red">*&nbsp;</span><label
										for="username"><g:message code="user.username.label"
												default="Email Address" /> </label>
									</td>
									<td width="20%"
										class="value ${hasErrors(bean: userInstance, field: 'username', 'errors')}">
										<g:textField class="validate[required,custom[email]]" id="username" name="username"
											value="${userInstance?.username}" placeholder="The Email address you enter will be your User ID" size="70"/>
									</td>
									
								</tr>
								<tr>	
									<td width="10%" class="name"><span style="color: red">*</span>
										<label for="password"><g:message
												code="user.password.label" default="Password" /> </label>
									</td>
									<td width="20%"
										class="value ${hasErrors(bean: userInstance, field: 'password', 'errors')}">
										<g:passwordField name="password" class="validate[required,custom[password]]"
											value="${userInstance?.password}" maxlength="25"/>
									</td>
									<td width="20%" class="name">
										<label for="password"><g:message
												code="user.password.label" default="ConfirmPassword" /> </label>
									</td>
									<td width="40%"
										class="value ${hasErrors(bean: userInstance, field: 'password', 'errors')}">
										<g:passwordField name="confirmpassword" class="validate[required,equals[password]]"
											value="${userInstance?.password}" maxlength="25"/>
									</td>
									
								</tr>
								
								<tr class="prop">
									<td width="5%" class="name" valign="top"><span
										style="color: red">*</span> <label for="roles"><g:message
												code="user.roles.label" default="Roles" /> </label>
									</td>
									<td width="35%" colspan="1"
										class="value ${hasErrors(bean: userInstance, field: 'roles', 'errors')}">
										<g:select id="roles" name="roles" class="multiselect validate[required]"
											multiple="multiple" from="${com.user.creation.Role.list()}"
											value="${userInstance?.getAuthorities()}"  optionKey="id" optionValue="authority"  />
									</td>
									<td colspan="2"></td>
																	
								</tr>
								
							</table>
						</fieldset>
						<g:submitButton id="createUser" name="create" class="finish" value="Create" />
					</g:form>

</body>
</html>
