<?php
include "koneksi2.php";
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
    echo '{"query_result":"ERROR"}';
} 

$user = $_GET['username'];
$kilo = $_GET['kilo'];
$layanan = $_GET['layanan'];
$pengambilan = $_GET['pengambilan'];
$catatan = $_GET['catatan'];

$sql = "insert into transaksi value ('','$user','$kilo','$layanan','$pengambilan','$catatan','Pesanan Terkirim')";

if ($conn->query($sql) == TRUE) {
    
    echo '{"query_result":"SUCCESS"}';

} else {
    echo '{"query_result":"FAILURE"}';
	echo $sql;
}
$conn->close();
?>