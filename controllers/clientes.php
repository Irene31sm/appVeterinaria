<?php

require_once '../models/Clientes.php';
$cliente = new Cliente();
if(isset($_GET['operacion'])){
  switch($_GET['operacion']){
    case 'inicioSesion':
      $acceso = [
        "sesion" => false,
        "mensaje" => "",
        "idcliente" => 0,
        "nombre" => "",
        "apellidos" => ""
      ];
      $respuesta = $cliente->inicioSesion($_GET['dni']);
      if($respuesta){
        $claveIngresada = $_GET['claveAcceso'];
        if(password_verify($claveIngresada, $respuesta['claveacceso'])){
          $acceso["sesion"] = true;
          $acceso["idcliente"] = $respuesta["idcliente"];
          $acceso["nombre"] = $respuesta["nombres"];
          $acceso["apellidos"] = $respuesta["apellidos"];
        }else{
          $acceso["mensaje"] = "La contraseña no es correcta";
        }
      }else{
        $acceso["mensaje"] = "El usuario no existe";
      }
      echo json_encode($acceso);
      break;

    case 'listarClientes':
      $datos = $cliente->listarClientes();
      echo json_encode($datos);
      break;
  }
}
if(isset($_POST['operacion'])){
  switch($_POST['operacion']){
    case 'registrar':
      $parametros = [
        "apellidos" => $_POST['apellidos'],
        "nombres"   => $_POST['nombres'],
        "dni"       => $_POST['dni'],
        "claveAcceso" => password_hash($_POST['claveAcceso'],PASSWORD_BCRYPT)
      ];
      $cliente->registrar($parametros);
      break;
    
  }
}
