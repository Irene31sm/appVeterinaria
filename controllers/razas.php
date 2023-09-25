<?php

require_once '../models/Raza.php';
$raza = new Raza();
if(isset($_GET['operacion'])){
  switch($_GET['operacion']){
    case 'listarAnimal':
      echo json_encode($raza->listarAnimal());
      break;
    case 'filtroRaza':
      $datos = $raza->filtroRaza($_GET['idanimal']);
      echo json_encode($datos);
      break;
  }
}