<?php

require_once '../models/Clientes.php';

if(isset($_GET['operacion'])){

  $cliente = new Cliente();

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