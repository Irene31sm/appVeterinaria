<?php

require_once 'conexion.php';

class Mascota extends Conexion{
  private $conexion;

  public function __CONSTRUCT(){
    $this->conexion = parent::getConexion();
  }

  public function registrar($data = []){
    $respuesta = [
      "status" => false,
      "mensaje" => ""
    ];

    try{
      $consulta =$this->conexion->prepare("CALL spu_registrar_mascota(?,?,?,?,?,?)");
      $respuesta["status"] = $consulta->execute(
        array(
          $data["idcliente"],
          $data["idraza"],
          $data["nombre"],
          $data["fotografia"],
          $data["color"],
          $data["genero"]
        )
      );
    }
    catch(Exception $e){
      $respuesta["mensaje"] = "No se pudo registrar ".$e->getCode();
    } 
    return $respuesta;
  }

  public function buscar($idmascota = 0){
    try{
      $consulta = $this->conexion->prepare("CALL spu_informacion_mascota(?)");
      $consulta->execute(array($idmascota));
      return $consulta->fetchAll(PDO::FETCH_ASSOC);
    }
    catch(Exception $e){
      die($e->getMessage());
    }

  }

  public function listarMascotasCliente($idcliente = 0){
    try{
      $consulta = $this->conexion->prepare("CALL spu_listar_mascotasClientes(?)");
      $consulta->execute(array($idcliente));
      return $consulta->fetchAll(PDO::FETCH_ASSOC);
    }
    catch(Exception $e){
      die($e->getMessage());
    }

  }
}