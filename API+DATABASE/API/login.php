<?php
include "koneksi2.php";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
    echo '{"query_result":"ERROR"}';
} 
$level=$_GET['level'];
$user = $_GET['username'];
$pass = $_GET['pass'];


$sql = "select username from user where username='$user' and password='$pass' and level='$level'";

if ($conn->query($sql) == TRUE) {
    
	$res = $conn->query($sql);
	if ($res->num_rows > 0){
		if($row = $res->fetch_assoc()){
			echo '{"query_result":"'.$row['username'].'"}';
		}
	}else{
		echo '{"query_result":"KOSONG"}';
	}

} else {
    echo '{"query_result":"FAILURE"}';
	echo $sql;
}
$conn->close();
?>