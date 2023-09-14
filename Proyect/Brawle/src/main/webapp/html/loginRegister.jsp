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
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> feat: added animations to the cards #11
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
    <!--Own Styles-->
    <link rel="stylesheet" href="../css/style.css">
    <title>Brawdle</title>

<<<<<<< HEAD
=======
    <!--Own Styles-->
    <link rel="stylesheet" href="../css/style.css">
    <title>Brawdle</title>

>>>>>>> feat: Created cards for login and register. Im ccurrently working on pop ups to show log in an register forms #11
    <!--Javascript-->
    <script src="../js/script.js"></script>
</head>
<body>
    <br>
    <header><div><img src="../img/logo.png" alt="logo provisional" class="logo"></div></header>
    
<<<<<<< HEAD
    <div class="cards fade-in" id="cards">
    <div class="row row-cols-1 row-cols-md-2 g-4">
        <div class="col">
            <div class="card animate__animated animate__jackInTheBox">
<<<<<<< HEAD
=======
    <div class="cards" id="cards">
    <div class="row row-cols-1 row-cols-md-2 g-4">
        <div class="col">
            <div class="card">
>>>>>>> feat: Created cards for login and register. Im ccurrently working on pop ups to show log in an register forms #11
=======
>>>>>>> feat: added animations to the cards #11
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
<<<<<<< HEAD

<<<<<<< HEAD
    <div class="login-register-form-popup" id="loginForm">
        <form action="/action_page.php" class="form-container">
            <h1>Login</h1>
    
            <label for="email"><b>Email</b></label>
            <input type="text" placeholder="Enter Email" name="email" required>
            
            <label for="psw"><b>Password</b></label>
            <input type="password" placeholder="Enter Password" name="psw" required>

            <button type="submit" class="btn">Login</button>
            <button type="button" class="btn cancel" onclick="closeLogin()">Close</button>
        </form>
    </div>

>>>>>>> feat: Created cards for login and register. Im ccurrently working on pop ups to show log in an register forms #11

            let resultado = document.getElementById("resultado");
            resultado.innerHTML = cajas;
=======
=======
>>>>>>> feat: finished page of login and register #11
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
<<<<<<< HEAD
>>>>>>> feat:Made changes in the form cards to look better. #11

        }
        function comprobarLetras(){
            let letras = document.getElementById("resultado")
            let arrayValores=[];
            for (let i = 0; i < jugadorEscogido.length-1; i++) {
                arrayValores.push(letras.nodeValue(i));
                
            }
            console.log(arrayValores)

        }
    </script>
</head>
<body onload=colocarTextAreas()>
    <h1>Brawle</h1>
    <div>
        <p>Adivina el nombre del jugador</p><br>
        <form>
            <div id="resultado"><br></div>
            <button type="button" onclick=comprobarLetras()>Try</button>

        </form>
    </div>
    
</body>
=======
>>>>>>> feat: finished page of login and register #11
</html>