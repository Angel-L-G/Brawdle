<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--Boostrap-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
    <!--Own Styles-->
    <link rel="stylesheet" href="../css/style.css">
    <title>Brawdle</title>

    <!--Javascript-->
    <script src="../js/script.js"></script>
</head>
<body>
    <br>
    <header><div><img src="../img/logo.png" alt="logo provisional" class="logo"></div></header>
    
    <div class="cards fade-in" id="cards">
    <div class="row row-cols-1 row-cols-md-2 g-4">
        <div class="col">
            <div class="card animate__animated animate__jackInTheBox">
            <div class="card-body">
                <h5 class="card-title">Iniciar sesion</h5>
                <p class="card-text">Inicia sesi�n para continuar jugando</p>
                <a href="#" class="btn btn-primary" onclick="openLogin()">log in</a>
            </div>
            </div>
        </div>
        <div class="col">
            <div class="card">
            <div class="card-body">
                <h5 class="card-title">Registrarse</h5>
                <p class="card-text">Reg�strate para poder empezar a jugar</p>
                <a href="#" class="btn btn-primary" onclick="openRegister()">register</a>
            </div>
            </div>
        </div>
        </div>
    </div>
    <div class="form-div fade-in" style="display: none" id="loginForm">
        <div class="left"><div class="image"><img src="../img/Koji.png" width="250px" style="margin-left: 10%;"></div></div>
        <div class="right">
            <div class="right-form">
                <p><strong>Log in</strong></p>
                <form id="login" name="login" method="post" action="login">
                    <input type="text" id="name" class="form-control" style="width: 200px; margin-left: 20%;" placeholder="Name">
                    <br>
                    <input type="password" id="password" class="form-control" style="width: 200px; margin-left: 20%;"  placeholder="Password">
                    <br>
                    <button type="button" class="btn btn-primary" onclick="closeLogin()">Cancel</button>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>
    </div>
    <div class="form-div fade-in" style="display: none" id="registerForm">
        <div class="left"><div class="image"><img src="../img/Caspian.png" width="250px" style="margin-left: 10%;"></div></div>
        <div class="right">
            <div class="right-form">
                <p><strong>Registrarse</strong></p>
                <form id="register" name="register" method="post" action="register">
                    <input type="text" id="name" class="form-control" style="width: 200px; margin-left: 20%;" placeholder="Name">
                    <br>
                    <input type="password" id="password" class="form-control" style="width: 200px; margin-left: 20%;"  placeholder="Password">
                    <br>
                    <button type="button" class="btn btn-primary" onclick="closeRegister()">Cancel</button>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>