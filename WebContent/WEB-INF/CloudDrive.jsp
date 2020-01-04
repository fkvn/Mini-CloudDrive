<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>CloudDrive</title>
</head>
<body class="bg-light container-fluid">
    <article class="row mt-2">
        <div class="col-8">
            <c:if test="${not empty user}">
                <a href='CloudDrive' class="btn btn-primary btn-sm" >Home</a>
                <a href='Logout' class="btn btn-primary btn-sm" >Log Out</a>
            </c:if>
        </div>
    </article>
    
    <c:if test="${not empty user }">
	    <article class="row my-5">
	        <div class="col-6 text-center offset-3">
	            <h1>My Drive</h1>
	            <small> ${user.name}</small>
	        </div>
	    </article>
	
	    <article class="row my-5">
	        <div class="col-8 offset-2 mt-2">
	             <c:if test="${not empty message}">
	                <h4 style="color: red">${message}</h4>
	            </c:if>
	            <form action="CloudDrive" method="post" class="input-group">
	                <input class="form-control" type="search" name="searchKeyWord" placeholder="Search" aria-label="Search">
	                <div class="input-group-append">
	                    <button type="submit" class="btn btn-outline-success" type="submit">Search</button>
	                </div>
	                
	            </form>
	        </div>
	    </article>
	    
	     <article class="row my-5">
	        <div class="col-8 offset-2">
	            <table class="table">
	                <thead class="thead-dark">
	                    <tr>
	                        <th scope="col">Name</th>
	                        <th scope="col">Type</th>
	                        <th scope="col">Actions</th>
	                    </tr>
	                </thead>
	                <tbody>
	                   <c:if test="${not empty id and not empty parent_id}">
		                    <tr>
		                       <td colspan="3"> 
		                           <c:choose>
                                        <c:when test="${parent_id eq '1'}"><a href='CloudDrive' class="nav-link" > ../ </a>  </c:when>
                                        <c:otherwise> <a href='CloudDrive?id=${parent_id}' class="nav-link" > ../ </a>   </c:otherwise>
                                    </c:choose>
		                       </td>
		                    </tr>
	                    </c:if>
	                    <c:if test="${not empty files }">
	                        <c:forEach items="${files}" var="file">
	                            <tr>
	                                <c:choose> 
	                                   <c:when test="${file.folder}"> 
	                                       <td> <a href="CloudDrive?id=${file.id}" class="text-decoration-none">${file.name}</a> </td>
	                                   </c:when>
	                                   <c:otherwise><td><a href="ViewFile?id=${file.id}" class="text-danger  text-decoration-none">${file.name}</a></td> </c:otherwise>
	                                </c:choose>
	                                
	                                <td>${file.type}</td>
	                                <td>
	                                    <c:if test="${empty owner or owner eq user.id }"> 
	                                       <a href='Rename?id=${file.id}' class="mt-1 btn btn-warning btn-sm" >Rename</a>
	                                       <a href='Delete?id=${file.id}&parent_id=${file.parent_id}' class="mt-1 btn btn-danger btn-sm" >Delete</a>
	                                    </c:if>
	                                    
	                                    <c:if test="${!file.folder }">
	                                       <c:if test="${not empty owner}">
	                                           <a href='Download?id=${file.id}&owner=${owner}' class="mt-1 btn btn-primary btn-sm" >Download</a>
	                                       </c:if>	 
	                                       <c:if test="${empty owner}">
                                                <a href='Download?id=${file.id}' class="mt-1 btn btn-primary btn-sm" >Download</a>
                                           </c:if>                                  
	                                       
	                                    </c:if>
	                                    
	                                    <c:if test="${!file.folder and empty owner or owner eq user.id}"> 
                                            <button class="mt-1 btn btn-info btn-sm" data-toggle="modal" data-target="#modal${file.id}">Get shareable Link</button>
                                        
	                                        <div class="modal fade" id="modal${file.id }" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	                                            <div class="modal-dialog" role="document">
	                                                <div class="modal-content">
	                                                <div class="modal-header">
	                                                    <h5 class="modal-title" id="exampleModalLabel">Shareable Link</h5>
	                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                                                    <span aria-hidden="true">&times;</span>
	                                                    </button>
	                                                </div>
	                                                <div class="modal-body overflow-auto">
	                                                    <c:set var="url">${pageContext.request.requestURL}</c:set>
	                                                    <p class="text-success">${fn:substring(url, 0, fn:indexOf(url,'WEB-INF'))}CloudDrive?id=${file.id}&owner=${file.owner_id}</p>
	                                                </div>
	                                                <div class="modal-footer">
	                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	                                                    <!-- <button type="button" class="btn btn-primary modalSubmit" class="btn btn-primary">Copy</button> -->
	                                                </div>
	                                                </div>
	                                            </div>
	                                        </div>
                                        </c:if>
	                                </td>
	                            </tr>
	                        </c:forEach>
	                    </c:if>
	                    <c:if test="${empty owner or owner eq user.id }"> 
		                    <tr>
		                        <td colspan="3">
		                            <form action="UploadFile" method="post" enctype="multipart/form-data" class="input-group mb-3">
		                                <c:choose>
		                                    <c:when test="${empty id}"> <input type="hidden" name="parent_id" value="1"> </c:when>
		                                    <c:otherwise> <input type="hidden" name="parent_id" value="${id}"> </c:otherwise>
		                                </c:choose>
		                                
		                                <input type="hidden" name="owner_id" value="${user.id }"> 
		                                
		                                <div class="input-group-prepend">
		                                    <button type="submit" class="btn btn-primary">Upload</button>
		                                </div>
		                                <div class="custom-file">
		                                    <input type="file" name="uploadFile" class="custom-file-input" id="uploadFile"/>
		                                    <label class="custom-file-label" for="uploadFile">Choose file</label>
		                                </div>
		                                
		                            </form>
		                            
		                          <!--   <form action="NewFolder" method="get" class="input-group mb-3"> -->
		                                <c:choose>
	                                        <c:when test="${empty id}"> <a href="NewFolder" class="btn btn-primary">New Folder</a> </c:when>
	                                        <c:otherwise> <a href="NewFolder?id=${id}" class="btn btn-primary">New Folder</a> </c:otherwise>
	                                    </c:choose>
	                                    
		                                
		                            <!-- </form> -->
		                        </td>
		                    </tr>
	                    </c:if>
	                </tbody>
	            </table>
	        </div>
	     </article>
     </c:if>
    

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-maskmoney/3.0.2/jquery.maskMoney.min.js" type="text/javascript"></script>

    <script>
        $(function() {
            $('#uploadFile').on('change',function(){
                //get the file name
                var fileName = $(this).val();
                //replace the "Choose a file" label
                $(this).next('.custom-file-label').html(fileName);
            });
        })
        
    </script>
</body>
</html>