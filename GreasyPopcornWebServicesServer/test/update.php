<?php
    require 'database.php';
 
    $id = null;
    if ( !empty($_GET['id'])) {
        $id = $_REQUEST['id'];
    }
     
    if ( null==$id ) {
        header("Location: index.php");
    }
     
    if ( !empty($_POST)) {
        // keep track validation errors
		$newidError = null;
        $nameError = null;
        $categoryError = null;
        $supplierError = null;
		$priceError = null;
		$dateError = null;
         
        // keep track post values
		$newid = $_POST['newid'];
        $name = $_POST['name'];
        $category = $_POST['category'];
        $supplier = $_POST['supplier'];
		$price = $_POST['price'];
		$date = $_POST['date'];
         
        // validate input
        $valid = true;
        if (empty($newid)) {
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
		
		if (empty($date)) {
            $dateError = 'Introdu prețul';
            $valid = false;
        }
         
         
        // update data
        if ($valid) {
            $pdo = Database::connect();
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            $sql = "UPDATE products 
					SET product_id = ?,
					product_name = ?,
					category_id = (select category_id from categories where category_name = ?),
					supplier_id = (select supplier_id from suppliers where supplier_name = ?),
					product_price = CONCAT(FORMAT(?,2),' LEI'),
					storage_date = ?
					where product_id = ?";
            $q = $pdo->prepare($sql);
            $q->execute(array($newid,$name,$category,$supplier,$price,$date,$id));
            Database::disconnect();
            header("Location: index.php");
        }
    } else {
        $pdo = Database::connect();
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sql = "SELECT PRODUCT_ID, PRODUCT_NAME, CATEGORY_NAME, SUPPLIER_NAME, PRODUCT_PRICE, DATE_FORMAT(STORAGE_DATE,'%d.%m.%Y') AS STORAGE_DATE
				FROM PRODUCTS P, CATEGORIES CAT, SUPPLIERS SUP
				WHERE P.CATEGORY_ID = CAT.CATEGORY_ID AND
				P.SUPPLIER_ID = SUP.SUPPLIER_ID 
				AND PRODUCT_ID = ?";
        $q = $pdo->prepare($sql);
        $q->execute(array($id));
        $data = $q->fetch(PDO::FETCH_ASSOC);
		$newid = $data['PRODUCT_ID'];
        $name = $data['PRODUCT_NAME'];
        $category = $data['CATEGORY_NAME'];
        $supplier = $data['SUPPLIER_NAME'];
		$price = $data['PRODUCT_PRICE'];
		$date = $data['STORAGE_DATE'];
        Database::disconnect();
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
                        <h3>Update a Product</h3>
                    </div>
             
                    <form class="form-horizontal" action="update.php?id=<?php echo $id?>" method="post">
					<div class="control-group <?php echo !empty($newidError)?'error':'';?>">
                        <label class="control-label">Cod de bare</label>
                        <div class="controls">
                            <input name="newid" type="text"  placeholder="Cod de bare" value="<?php echo !empty($newid)?$newid:'';?>">
                            <?php if (!empty($newidError)): ?>
                                <span class="help-inline"><?php echo $newidError;?></span>
                            <?php endif; ?>
                        </div>
                      </div>
                      <div class="control-group <?php echo !empty($nameError)?'error':'';?>">
                        <label class="control-label">Nume produs</label>
                        <div class="controls">
                            <input name="name" type="text"  placeholder="Nume produs" value="<?php echo !empty($name)?$name:'';?>">
                            <?php if (!empty($nameError)): ?>
                                <span class="help-inline"><?php echo $nameError;?></span>
                            <?php endif; ?>
                        </div>
                      </div>
                      <div class="control-group <?php echo !empty($categoryError)?'error':'';?>">
                        <label class="control-label">Categorie</label>
                        <div class="controls">
                            <input name="category" type="text" placeholder="Categorie" value="<?php echo !empty($category)?$category:'';?>">
                            <?php if (!empty($categoryError)): ?>
                                <span class="help-inline"><?php echo $categoryError;?></span>
                            <?php endif;?>
                        </div>
                      </div>
                      <div class="control-group <?php echo !empty($supplierError)?'error':'';?>">
                        <label class="control-label">Distribuitor</label>
                        <div class="controls">
                            <input name="supplier" type="text"  placeholder="Distribuitor" value="<?php echo !empty($supplier)?$supplier:'';?>">
                            <?php if (!empty($supplierError)): ?>
                                <span class="help-inline"><?php echo $supplierError;?></span>
                            <?php endif;?>
                        </div>
                      </div>
					  <div class="control-group <?php echo !empty($priceError)?'error':'';?>">
                        <label class="control-label">Preț</label>
                        <div class="controls">
                            <input name="price" type="text"  placeholder="Pret" value="<?php echo !empty($price)?$price:'';?>">
                            <?php if (!empty($priceError)): ?>
                                <span class="help-inline"><?php echo $priceError;?></span>
                            <?php endif; ?>
                        </div>
                      </div>
					  <div class="control-group <?php echo !empty($dateError)?'error':'';?>">
                        <label class="control-label">Data de depozitare</label>
                        <div class="controls">
                            <input name="date" type="text"  placeholder="Data de depozitare" value="<?php echo !empty($date)?$date:'';?>">
                            <?php if (!empty($dateError)): ?>
                                <span class="help-inline"><?php echo $dateError;?></span>
                            <?php endif; ?>
                        </div>
                      </div>
					  
                      <div class="form-actions">
                          <button type="submit" class="btn btn-success">Update</button>
                          <a class="btn" href="index.php">Back</a>
                        </div>
                    </form>
                </div>
                 
    </div> <!-- /container -->
  </body>
</html>