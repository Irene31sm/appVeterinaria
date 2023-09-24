<?php

require_once '../models/Mascotas.php';

if(isset($_POST['operacion'])){

  $mascota = new Mascota();

  switch($_POST['operacion']){
    case 'registrar':
      $parametros = [
        "idcliente"           => $_POST['idcliente'],
        "idraza"              => $_POST['idraza'],
        "nombre"              => $_POST['nombre'],
        "fotografia"          => $_POST['fotografia'],
        "color"               => $_POST['color'],
        "genero"              => $_POST['genero']
    ];
      echo json_encode($mascota->registrar($parametros));
      break;
    case 'buscar':
      $datos = $mascota->buscar($_POST["idmascota"]);
      echo json_encode($datos);
      break;
    case 'listar':
      $datos = $mascota->listarMascotasCliente($_POST["idcliente"]);
      echo json_encode($datos);
  }
}