<%@ page language="java"
    contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
    href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
    crossorigin="anonymous">
<title>Recovery Password</title>
</head>
<body class="jumbotron">
    <article class="row">
        <div class="col-12 col-lg-6 offset-lg-3 ">
            <h1>
                <a href="Signup" class="text-decoration-none">
                    Hello, Welcome!</a>
            </h1>
            <h5>Please fill out the following information to change your password</h5>

            <c:if test="${not empty message}">
                <h4 style="color: red">${message}</h4>
            </c:if>

            <c:if test="${not empty sqlError}">
                <h4 style="color: red">${sqlError}</h4>
                <h5 class="text-info">If you haven't set up the
                    environment yet, please select the button below to set up the
                    environment <small class="text-danger">(For developers only. If you are an user, please contact your administrators)</small> </h5>
                <a href="SetUpEnvironment"
                    class="btn btn-success text-white">Set Up
                    Environment</a>
            </c:if>
            <form class="mt-5" action="RecoverPassword" method="post">
                <div class="form-group">
                    <label for="user_name">Username</label> <input
                        type="text" class="form-control" id="user_name"
                        name='username' aria-describedby="usernam"
                        placeholder="Enter username">
                </div>
                <div class="form-group">
                    <label for="password">New Password</label> <input
                        type="password" name='password' class="form-control"
                        id="password" placeholder="Password">
                </div>
                <a href="CloudDrive" class="btn btn-secondary">Back</a>
                <button type="submit" class="btn btn-primary">Change Password</button>
            </form>
        </div>
    </article>

    <script
        src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
    <script
        src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
    <script
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>