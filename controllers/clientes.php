<?php

require_once '../models/Clientes.php';

if(isset($_POST['operacion'])){

  $cliente = new Cliente();

  switch($_POST['operacion']){
    case 'inicioSesion':
      $parametros = [
        "dni"         => $_POST['dni']
    ];
      $respuesta = $cliente->inicioSesion($parametros);
      echo json_encode($respuesta);
      break;
  }
}