<?php
	session_start();
	$MAX_ATTEMPTS = 3;
	 
	// Check if the user is already logged in
	if(isset($_SESSION["loggedin"]) && $_SESSION["loggedin"] === true){
	    header("location: home.html");
	    exit;
	}
	 
	$pdo = require "connect.php";
	 
	// Define variables and initialize with empty values
	$username = $password = "";
	$username_err = $password_err = $login_err = "";
	 
	// Processing form data when form is submitted
	if($_SERVER["REQUEST_METHOD"] == "POST"){
	 
	    // Check if username is empty
	    if(empty(trim($_POST["username"]))){
	        $username_err = "Please enter username.";
	    } else{
	        $username = trim($_POST["username"]);
	    }
	    
	    // Check if password is empty
	    if(empty(trim($_POST["password"]))){
	        $password_err = "Please enter your password.";
	    } else{
	        $password = trim($_POST["password"]);
	    }
	    
	    // Validate credentials
	    if(empty($username_err) && empty($password_err)){
	        // Prepare a select statement
	        $sql = "SELECT id, username, password, attempts, email, phone FROM users WHERE username = :username";
	        
	        if($stmt = $pdo->prepare($sql)){
	            // Bind variables to the prepared statement as parameters
	            $stmt->bindParam(":username", $param_username, PDO::PARAM_STR);
	            
	            // Set parameters
	            $param_username = trim($_POST["username"]);
	            
	            // Attempt to execute the prepared statement
	            if($stmt->execute()){
	                // Check if username exists, if yes then verify password
	                if($stmt->rowCount() == 1){
	                    if($row = $stmt->fetch()){
	                        $id = $row["id"];
	                        $username = $row["username"];
	                        $hashed_password = $row["password"];
	                        $phone = $row["phone"];
	                        $email = $row["email"];
	                        $attempts = $row["attempts"];
	                        if($attempts >= $MAX_ATTEMPTS){
	                            $login_err = "Number of attempts exceeded. Please contact the administrator to unlock your account.";
	                        }
	                        else if(password_verify($password, $hashed_password)){
	                            // Password is correct, so start a new session
	                            session_start();
	                            
	                            // Store data in session variables
	                            $_SESSION["loggedin"] = true;
	                            $_SESSION["id"] = $id;
	                            $_SESSION["username"] = $username;                            
	                            $_SESSION["phone"] = $phone;                            
	                            $_SESSION["email"] = $email;                            
	                            
	                            $sql_update_zero = "UPDATE users SET attempts = 0 WHERE username = :username";
	                            if($stmt_update_zero = $pdo->prepare($sql_update_zero)){
	                                // Bind variables to the prepared statement as parameters
	                                $stmt_update_zero->bindParam(":username", $param_username, PDO::PARAM_STR);
	                                
	                                // Set parameters
	                                $param_username = trim($_POST["username"]);
	                                $stmt_update_zero->execute();
	
	                                // Close statement
	                                unset($stmt_update_zero);
	                            }
	
	                            // Redirect user to index page
	                            header("location: index.php");
	                        } else{
	                            // Password is not valid, display a generic error message and update number of attempts
	                            $login_err = "Invalid username or password.";
	                            $sql_update = "UPDATE users SET attempts = attempts + 1 WHERE username = :username";
	                            if($stmt_update = $pdo->prepare($sql_update)){
	                                // Bind variables to the prepared statement as parameters
	                                $stmt_update->bindParam(":username", $param_username, PDO::PARAM_STR);
	                                
	                                // Set parameters
	                                $param_username = trim($_POST["username"]);
	                                $stmt_update->execute();
	
	                                // Close statement
	                                unset($stmt_update);
	                            }
	                        }
	                    }
	                } else{
	                    // Username doesn't exist, display a generic error message
	                    $login_err = "Invalid username or password.";
	                }
	            } else{
	                echo "Oops! Something went wrong. Please try again later.";
	            }
	
	            // Close statement
	            unset($stmt);
	        }
	    }
	    
	    // Close connection
	    unset($pdo);
	}
?>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<h1>European Parliament</h1>
	<!-- Nation flag -->
	<h3>Login</h3>
	<p>Citizen Access</p>
	<form action="">
	  <label for="username">Username</label>
	  <input type="text" id="username" name="username"><br><br>
	  <label for="password">Password</label>
	  <input type="text" id="password" name="password"><br><br>
	  <input type="submit" value="Login">
	</form>
	<button onclick="location.href='/';">Login</button>
</body>
</html>