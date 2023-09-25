<?php

$claveBase = "123456";      //Clave ORIGINAL
$claveEncriptada = password_hash($claveBase, PASSWORD_BCRYPT); //

var_dump($claveEncriptada);


?>