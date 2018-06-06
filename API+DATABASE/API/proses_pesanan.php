<?php

include "koneksi2.php";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
    echo '{"query_result":"ERROR"}';
} 

$id = $_GET['id'];
$status = $_GET['status'];
// $operator = $_GET['operator'];
// $date = date('Y-m-d H:i:s');

$sql = "update transaksi set status='$status' where id_transaksi='$id'";

if ($conn->query($sql) == TRUE) {
    
    echo '{"query_result":"SUCCESS"}';

} else {
    echo '{"query_result":"FAILURE"}';
	echo $sql;
}
$conn->close();
?>