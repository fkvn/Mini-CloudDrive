<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Set Up Environment</title>
</head>
<body class="jumbotron">
    <article class="row">
        <div class="col-12 col-lg-10 offset-lg-1">
            <h1> <a href="SetUpEnvironment" class="text-decoration-none"> Hello, Welcome!</a></h1>
            <c:if test="${not empty message}">
                <h4 style="color: red">${message}</h4>
            </c:if>
            <form action="SetUpEnvironment" method="post" class="needs-validation" novalidate>
                <div class="form-row">
                    <h4 class="col-12 mt-5 text-danger">Environment General Information:</h4>
                    <div class="col-12 col-xl-6">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text text-success"><strong>IDE</strong></span>
                            </div>
                            <label class="form-control">Eclipse Enterprise Java Developers</label>
                        </div>
                    </div>
                    
                    <div class="col-12 col-xl-6">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text text-success"><strong>Platform</strong></span>
                            </div>
                            <label class="form-control">Java Enterprise Edition (Java EE)</label>
                        </div>
                    </div>
                    <div class="col-12 col-xl-6">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text text-success"><strong>Java Development Kit (JDK)</strong></span>
                            </div>
                            <label class="form-control">JDK 8 or above</label>
                        </div>
                    </div>
                    <div class="col-12 col-xl-6">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text text-success"><strong>Server</strong></span>
                            </div>
                            <label class="form-control">Tomcat 8.5</label>
                        </div>
                    </div>
                    <div class="col-12 col-xl-6">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text text-success"><strong>DBMS</strong></span>
                            </div>
                            <label class="form-control">MySQL</label>
                        </div>
                    </div>
                    <div class="col-12 col-xl-6">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text text-success"><strong>API</strong></span>
                            </div>
                            <label class="form-control">Java Database Connectivity (JDBC)</label>
                        </div>
                    </div>
                    
                    <h4 class="col-12 mt-5 text-danger">In order to set up the database, please fill out the following information</h4>
                    
                    <div class="col-12">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text text-success"><strong>Host</strong></span>
                            </div>
                            <input type="text" class="form-control" name="host" autofocus value="localhost" required/>
                            <div class="invalid-feedback">Please enter a valid host </div>
                        </div>
                    </div>
                    
                    <div class="col-12">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text text-success"><strong>Username</strong></span>
                            </div>
                            <input type="text" class="form-control" name="username"  value="root" required/>
                            <div class="invalid-feedback">Please enter a valid username </div>
                        </div>
                    </div>

                    <div class="col-12">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text text-success"><strong>Password</strong></span>
                            </div>
                            <input type="password" class="form-control" name="password"   placeholder="Please enter your password"  required/>
                            <div class="invalid-feedback">Please enter a valid password </div>
                        </div>
                    </div>

                    <div class="col-12">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text text-success"><strong>Database</strong></span>
                            </div>
                            <input type="text" class="form-control" name="database" placeholder="Please enter your database"  required/>
                            <div class="invalid-feedback">Please enter a valid database </div>
                        </div>
                    </div>

                    <div class="col-12">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <div class="form-group">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="firstTimeSetUp" id="fisrTime">
                                        <label class="form-check-label" for="fisrTime"> First time set up </label>
                                        <small id="tips" class="form-text text-muted text-danger">All database tables relate to "users" and "files" will be recreated.</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-12">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <div class="form-group">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" value="" id="agreeTerm" required>
                                        <label class="form-check-label" for="agreeTerm"> Enabled MySQL Server </label>
                                        <div class="invalid-feedback "> You have to enable MySQL Server before setting up database. </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <a href="CloudDrive" class="btn btn-secondary">Back</a>
                <button class="btn btn-primary" type="submit">Set Up Database</button>
            </form>
        </div>
    </article>
    
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script>
        // Example starter JavaScript for disabling form submissions if there are invalid fields
        (function() {
          'use strict';
          window.addEventListener('load', function() {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
            var validation = Array.prototype.filter.call(forms, function(form) {
              form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                  event.preventDefault();
                  event.stopPropagation();
                }
                form.classList.add('was-validated');
              }, false);
            });
          }, false);
        })();
    </script>
</body>
</html>