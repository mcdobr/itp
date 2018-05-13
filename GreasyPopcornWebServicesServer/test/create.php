<?php
     
    require 'database.php';
 
    if ( !empty($_POST)) {
        // keep track validation errors
		$idError = null;
        $nameError = null;
        $categoryError = null;
        $supplierError = null;
		$priceError = null;
		$dayError = null;
		$monthError = null;
		$yearError = null;
         
        // keep track post values
		$id = $_POST['id'];
        $name = $_POST['name'];
        $category = $_POST['category'];
        $supplier = $_POST['supplier'];
		$price = $_POST['price'];
		$sDate = $_POST['year'].$_POST['month'].$_POST['day'];
         
        // validate input
        $valid = true;
        if (empty($id)) {
            $idError = 'Introdu codul de bare';
            $valid = false;
        }
         
        if (empty($name)) {
            $nameError = 'Intodu numele produsului';
            $valid = false;
        } 
         
        if (empty($category)) {
            $categoryError = 'Introdu categoria produsului';
            $valid = false;
        }
		
		if (empty($supplier)) {
            $categoryError = 'Introdu distribuitorul';
            $valid = false;
        }
		
		if (empty($price)) {
            $categoryError = 'Introdu prețul';
            $valid = false;
        }
         
        // insert data
        if ($valid) {
            $pdo = Database::connect();
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            $sql = "INSERT INTO PRODUCTS 
			SELECT ".$id.",'".$name."',C.CATEGORY_ID,S.SUPPLIER_ID,CONCAT(FORMAT(".$price.",2),' LEI'),'".$sDate."'
			FROM CATEGORIES C, SUPPLIERS S
			WHERE C.CATEGORY_NAME = '".$category."' AND
			S.SUPPLIER_NAME = '".$supplier."'";
            $q = $pdo->query($sql);
            Database::disconnect();
            header("Location: index.php");
        }
    }
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
</head>
 
<body>
    <div class="container">
     
                <div class="span10 offset1">
                    <div class="row">
                        <h3>Create a Customer</h3>
                    </div>
             
                    <form class="form-horizontal" action="create.php" method="post">
                      <div class="control-group <?php echo !empty($idError)?'error':'';?>">
                        <label class="control-label">Code de bare</label>
                        <div class="controls">
                            <input name="id" type="text"  placeholder="Cod de bare" value="<?php echo !empty($id)?$id:'';?>">
                            <?php if (!empty($idError)): ?>
                                <span class="help-inline"><?php echo $idError;?></span>
                            <?php endif; ?>
                        </div>
                      </div>
                      <div class="control-group <?php echo !empty($nameError)?'error':'';?>">
                        <label class="control-label">Name</label>
                        <div class="controls">
                            <input name="name" type="text"  placeholder="Name" value="<?php echo !empty($name)?$name:'';?>">
                            <?php if (!empty($nameError)): ?>
                                <span class="help-inline"><?php echo $nameError;?></span>
                            <?php endif; ?>
                        </div>
                      </div>
                      <div class="control-group <?php echo !empty($categoryError)?'error':'';?>">
                        <label class="control-label">Categorie</label>
                        <div class="controls">
                            <input name="category" type="text"  placeholder="Categorie" value="<?php echo !empty($category)?$category:'';?>">
                            <?php if (!empty($categoryError)): ?>
                                <span class="help-inline"><?php echo $categoryError;?></span>
                            <?php endif; ?>
                        </div>
                      </div>
					  <div class="control-group <?php echo !empty($supplierError)?'error':'';?>">
                        <label class="control-label">Distribuitor</label>
                        <div class="controls">
                            <input name="supplier" type="text"  placeholder="Distribuitor" value="<?php echo !empty($supplier)?$supplier:'';?>">
                            <?php if (!empty($supplierError)): ?>
                                <span class="help-inline"><?php echo $supplierError;?></span>
                            <?php endif; ?>
                        </div>
                      </div>
					  <div class="control-group <?php echo !empty($priceError)?'error':'';?>">
                        <label class="control-label">Preț</label>
                        <div class="controls">
                            <input name="price" type="text"  placeholder="Preț" value="<?php echo !empty($price)?$price:'';?>">
                            <?php if (!empty($priceError)): ?>
                                <span class="help-inline"><?php echo $priceError;?></span>
                            <?php endif; ?>
                        </div>
                      </div>
					  <div class="control-group <?php echo !empty($dayError)?'error':'';?>">
                        <label class="control-label">Ziua</label>
                        <div class="controls">
                            <input name="day" type="text"  placeholder="Ziua" value="<?php echo !empty($day)?$day:'';?>">
                            <?php if (!empty($dayError)): ?>
                                <span class="help-inline"><?php echo $dayError;?></span>
                            <?php endif; ?>
                        </div>
                      </div>
					  <div class="control-group <?php echo !empty($monthError)?'error':'';?>">
                        <label class="control-label">Luna</label>
                        <div class="controls">
                            <input name="month" type="text"  placeholder="Luna" value="<?php echo !empty($month)?$month:'';?>">
                            <?php if (!empty($monthError)): ?>
                                <span class="help-inline"><?php echo $monthError;?></span>
                            <?php endif; ?>
                        </div>
                      </div>
					  <div class="control-group <?php echo !empty($yearError)?'error':'';?>">
                        <label class="control-label">Anul</label>
                        <div class="controls">
                            <input name="year" type="text"  placeholder="Anul" value="<?php echo !empty($year)?$year:'';?>">
                            <?php if (!empty($yearError)): ?>
                                <span class="help-inline"><?php echo $yearError;?></span>
                            <?php endif; ?>
                        </div>
                      </div>
                      <div class="form-actions">
                          <button type="submit" class="btn btn-success">Create</button>
                          <a class="btn" href="index.php">Back</a>
                        </div>
                    </form>
                </div>
                 
    </div> <!-- /container -->
  </body>
</html>