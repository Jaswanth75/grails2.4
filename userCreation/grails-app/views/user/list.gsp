<%@ page import="com.user.creation.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <g:set var="currentNav" value="user" scope="request"/>
        
	<link type="text/css" rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.10/themes/ui-lightness/jquery-ui.css" />
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" src="js/plugins/localisation/jquery.localisation-min.js"></script>
	<script type="text/javascript" src="js/plugins/scrollTo/jquery.scrollTo-min.js"></script>
        <link rel='stylesheet' href="${resource(dir: 'media/css', file: 'demo_table_jui.css')}" />
        <link rel='stylesheet' href="${resource(dir: 'extras/TableTools/media/css', file: 'TableTools_JUI.css')}" />
		<script type="text/javascript" src="${resource(dir: 'media/js',file: 'jquery.dataTables.min.js')}"></script>
		<script type="text/javascript" charset="utf-8" src="${resource(dir: '/extras/TableTools/media/js',file: 'TableTools.min.js')}"></script>
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
       <g:javascript>
        var selectedUserId;
        var selectedMode ;
        
        function disableUser(userId){
        	selectedUserId = userId;
        	$( "#dialog-confirm" ).dialog( "open" );
        }
        
        
       
        
		$(document).ready(function() {
		 $( "#dialog-confirm" ).dialog({
            resizable: false,
            height:140,
            autoOpen: false,
            modal: true,
            buttons: {
                "ok": function() {
                    $( this ).dialog( "close" );
                    window.location="${request.contextPath}/user/delete/" + selectedUserId;
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            }
        });
        
   		$('#user').dataTable({
				"oTableTools": {
					"sRowSelect": "single"
				},
				bProcessing: true,
				bServerSide: true,
				bPaginate: true, 
				sAjaxSource: '${request.contextPath + '/user/listAll'}',
				bJQueryUI: true,
				bAutoWidth: true, 
				sPaginationType: "full_numbers",
				bLengthChange: false,
				"aoColumns":[
				    { "bSearchable": false,"bSortable": false,"bVisible":false },
					{ "fnRender": function ( oObj ) {
					    if(oObj.aData[6]){
							return "<a href='${request.contextPath}/user/edit/" + oObj.aData[0] + "'>" + oObj.aData[1] + "</a>";
						}else{
							return "<del>" + oObj.aData[1] + "</del>";
						}
					}, "bSortable": true},
					{ "fnRender": function ( oObj ) {
						return "<span style='white-space:nowrap;'>" + oObj.aData[2] + "," + oObj.aData[3]+  "</span>";
					}, "bSortable": true},
					{ "fnRender": function ( oObj ) {
						return "<span style='white-space:nowrap;'>" + oObj.aData[4] +  "</span>";
					}, "bSortable": true},
					
					{ "fnRender": function ( oObj ) {
						if(oObj.aData[6]){							
						    var link = "";
							link = link + " <a style='text-decoration: none;color: #369;' onClick=\"disableUser('" + oObj.aData[0] +  "')\" href='#'>" + "Disable</a> "
							return link;
						}else
							return "<span style='color:181818'>Inactive</span>";
					}, "bSearchable": false,"bSortable": false}
				
				]
			});
		});
	</g:javascript>
    </head>
    <body>
    <div id="dialog-confirm" title="Disable User?" style="background: #f1f1f1">
    		<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>This user will be permanently disabled and cannot be activated. Are you sure?</p>
		</div>
   		<div style="color: #181818;margin-top: 10px;" align="center"><h2>
						<g:message code="default.list.label" args="[entityName]" />
					</h2> </div>
		            <div id="message" style="margin-top: 20px">
			            <g:if test="${flash.message}">
				            <div class="message">${flash.message}</div>
			            </g:if>
			        </div>    
		            <a  href="${request.contextPath}/user/create" style="float: right;margin-bottom: 10px;font-size: 16px;color:#999">Add&nbsp;<g:message code="default.new.label" args="[entityName]" /></a>
		            <table id="user" class="display">
		                <thead>
		                    <tr>
		                    
		                        <th><g:message code="user.userId.label" default="Id" /></th>
		                    
		                        <th><g:message code="user.username.label" default="Username" /></th>
		                    
		                        <th><g:message code="user.firstName.label" default="Name" /></th>
		                    
		                        <th><g:message code="user.emailAddress.label" default="EMail Address" /></th>
		                        
		                        <th>Actions</th>
		                    
		                    </tr>
		                </thead>
		                <tbody>
		                </tbody>
		            </table>
    </body>
	</html>
