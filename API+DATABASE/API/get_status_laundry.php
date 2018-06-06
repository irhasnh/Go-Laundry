<?php
	include "koneksi.php";
	$id = $_GET['id_transaksi'];
	$query = mysqli_query($connect, "select * from detil_transaksi where id_transaksi='$id'");
	$json = array();
	while($row = mysqli_fetch_assoc($query)){
		$json[]=$row;
	}
	echo json_encode($json);
?>