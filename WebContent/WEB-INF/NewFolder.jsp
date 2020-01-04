<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>New Folder</title>
</head>
<body>
    <article class="jumbotron">
        <h3>Please enter a folder name</h3>
        <form class="mt-5" action="NewFolder" method="post">    
            <input type="hidden" name="parent_id" value="${id}">
            <table class="table table-primary">
                <tbody>
                    <tr><th scope="col">Name</th> <td><input type="text" class="form-control"  name="folderName"> </td> </tr>
                </tbody>
            </table>
            <c:choose>
                <c:when test="${id eq '1' }"> <a href="CloudDrive" class="btn btn-secondary">Back</a></c:when>
                <c:otherwise> <a href="CloudDrive?id=${id}" class="btn btn-secondary">Back</a> </c:otherwise>
            </c:choose>   
            <input type="submit" class="btn btn-success" value="Submit">
        </form>

    </article>



    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>