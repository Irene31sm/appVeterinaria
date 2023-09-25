<?php

require_once 'conexion.php';

class Raza extends Conexion{
  private $conexion;

  public function __CONSTRUCT(){
    $this->conexion = parent::getConexion();
  }

  public function listarAnimal(){
    try{
      $consulta =$this->conexion->prepare("CALL spu_lista_animales()");
      $consulta->execute();
      return $consulta->fetchAll(PDO::FETCH_ASSOC);
    }
    catch(Exception $e){
      die($e->getCode());
    } 
  }
  
  public function filtroRaza($idanimal){
    try{
      $consulta =$this->conexion->prepare("CALL spu_filtro_raza(?)");
      $consulta->execute(array($idanimal));
      return $consulta->fetchAll(PDO::FETCH_ASSOC);
    }
    catch(Exception $e){
      die($e->getCode());
    } 
  }
}