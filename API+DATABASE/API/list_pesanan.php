<?php
	include "koneksi.php";
	$user = $_GET['username'];
	$query = mysqli_query($connect, "select * from transaksi where username='$user'");
	$json = array();
	while($row = mysqli_fetch_assoc($query)){
		$json[]=$row;
	}
	echo json_encode($json);
?>