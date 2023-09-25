<?php

require_once '../models/Mascotas.php';
$mascota = new Mascota();
if(isset($_POST['operacion'])){
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
    
  }
}
if(isset($_GET['operacion'])){
  switch($_GET['operacion']){
    case 'listar':
      $datos = $mascota->listarMascotasCliente($_GET["idcliente"]);
      echo json_encode($datos);
      break;
    case 'buscar':
      $datos = $mascota->buscar($_GET["idmascota"]);
      echo json_encode($datos);
      break;
  }
}