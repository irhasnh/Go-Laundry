<?php

$server = "localhost";
$user= "id2886222_laundry";
$password = "YOUR password";
$db = "id2886222_laundry";
	
	$connect = mysqli_connect($server, $user, $password) or die ("koneksi gagal");
	mysqli_select_db($connect, $db) or die ("db belum siap");
?>