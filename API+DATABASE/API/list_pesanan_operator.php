<?php
	include "koneksi.php";
	$status = $_GET['status'];
	$query = mysqli_query($connect, "select * from transaksi where status='$status' or status='Diproses'");
	$json = array();
	while($row = mysqli_fetch_assoc($query)){
		$json[]=$row;
	}
	echo json_encode($json);
?>