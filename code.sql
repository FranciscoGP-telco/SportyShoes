CREATE TABLE users ( 
  user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_name VARCHAR(30) NOT NULL,
  name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(120) NOT NULL,
  phone_number INT,
  address VARCHAR(1000),
  admin BOOLEAN NOT NULL
);

CREATE TABLE purchases(
  purchase_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  CONSTRAINT fk_user
    FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);
  
CREATE TABLE purchaseproducts(
  purchase_id INT NOT NULL,
  product_id INT NOT NULL,
  amount INT NOT NULL,
  PRIMARY KEY (purchase_id, product_id),
  CONSTRAINT fk_purchase
    FOREIGN KEY (purchase_id)
    REFERENCES purchases(purchase_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_product
    FOREIGN KEY (product_id)
    REFERENCES products(product_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);
 
CREATE TABLE products(
  product_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  category VARCHAR(255) NOT NULL,
  price DECIMAL(6,2) NOT NULL
);
  