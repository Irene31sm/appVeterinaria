<?php

require_once 'conexion.php';

class Cliente extends Conexion{
  private $conexion;

  public function __CONSTRUCT(){
    $this->conexion = parent::getConexion();
  }

  public function inicioSesion($data = []){
    $respuesta = [
      "status" => false,
      "mensaje" => ""
    ];

    try{
      $consulta =$this->conexion->prepare("CALL spu_inicio_sesion(?)");
      $consulta->execute(
        array(
          $data["dni"]
        )
      );
      return $consulta->fetchAll(PDO::FETCH_ASSOC);
    }
    catch(Exception $e){
      die($e->getCode());
    } 
  }

}