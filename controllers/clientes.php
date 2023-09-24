<?php

require_once '../models/Clientes.php';
$cliente = new Cliente();
if(isset($_GET['operacion'])){
  switch($_GET['operacion']){
    case 'inicioSesion':
      $parametros = [
        "dni"         => $_GET['dni']
    ];
      $respuesta = $cliente->inicioSesion($parametros);
      echo json_encode($respuesta);
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
        "claveAcceso" => $_POST['claveAcceso']
      ];
      $cliente->registrar($parametros);
  }
}