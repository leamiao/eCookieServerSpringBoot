CREATE TABLE `users` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `version` int(32) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `email` varchar(255) COLLATE utf8_bin DEDAULT NULL,
  `created_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `updated_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USERS` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

 CREATE TABLE `products` (
  `id` INT(32) NOT NULL AUTO_INCREMENT,
  `version` INT(32) DEFAULT NULL,
  `name` VARCHAR(255) COLLATE utf8_bin NOT NULL,
  `category_id` INT(32) COLLATE utf8_bin NOT NULL,
  `image_url` VARCHAR(255) COLLATE utf8_bin DEFAULT NULL,
  `price` DOUBLE DEFAULT NULL,
  `description` TEXT COLLATE utf8_bin,
  `created_by` VARCHAR(255) COLLATE utf8_bin DEFAULT NULL,
  `updated_by` VARCHAR(255) COLLATE utf8_bin DEFAULT NULL,
  `created_date` DATETIME DEFAULT NULL,
  `updated_date` DATETIME DEFAULT NULL,
  `ref_category_id` int(32) NOT NULL ,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_PRODUCTS` (`name`),
  FOREIGN KEY `FK_PRODUCTS` (ref_category_id) REFERENCES categories(id) 
) ENGINE=INNODB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



UPDATE products SET NAME = 'Chocolate Cookie', description = 'Chocolate Cookie', image_url = '7.png' WHERE id = 7;

UPDATE products SET NAME = 'Walnut Butter Cookie', description = 'Walnut Butter Cookie', price = 1.99, image_url = '17.png' WHERE id = 17;
insert into products (version, name, image_url, price, description) values (0, 'Strawberry Macaron', '15.png', 2,19, 'Strawberry Macaron');
insert into products (version, name, image_url, price, description) values (0, 'Matcha Cookie', '16.png', 2,19, 'Matcha Sugar Cookie');

UPDATE products SET image_url = '16.png' WHERE id = 16;
UPDATE products SET image_url = '17.png' WHERE id = 17;
UPDATE products SET image_url = '11.png' WHERE id = 11;

 CREATE TABLE `categories` (
  `id` INT(32) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_CATEGORIES` (`name`)
) ENGINE=INNODB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


Alter table products add `category_id` INT(32) COLLATE utf8_bin NOT NULL DEFAULT 8,
Alter table products add FOREIGN KEY `FK_PRODUCTS` (category_id) REFERENCES categories(id) 


*******************************************************
 CREATE TABLE `department` (
  `id` INT(32) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) COLLATE utf8_bin NOT NULL,
  `description` TEXT,
  PRIMARY KEY (`id`),
) ENGINE=INNODB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

 CREATE TABLE `role` (
  `id` INT(32) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) COLLATE utf8_bin NOT NULL,
  `description` TEXT,
  PRIMARY KEY (`id`),
) ENGINE=INNODB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


insert into role (name, description) values ('Manager', 'Manager');
insert into role (name, description) values ('Director', 'Director');
insert into role (name, description) values ('VP', 'VP');
insert into role (name, description) values ('Senior Engineer', 'Senior Engineer');
insert into role (name, description) values ('CFO', 'CFO');
insert into role (name, description) values ('COO', 'COO');
insert into role (name, description) values ('Engineer', 'Engineer');
insert into role (name, description) values ('Staff', 'Staff');


 CREATE TABLE `employee` (
  `id` INT(32) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) COLLATE utf8_bin NOT NULL,
  `email` VARCHAR(255) COLLATE utf8_bin NOT NULL DEFAULT 8,
  `salary` DOUBLE DEFAULT NULL,
  `description` TEXT COLLATE utf8_bin,
  `manager` VARCHAR(255) COLLATE utf8_bin DEFAULT NULL,
  `birthdate` DATETIME DEFAULT NULL,
  `hire_date` DATETIME DEFAULT NULL,
  `department_id` int(32) NOT NULL,
  `role_id` int(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_EMPLOYEE` (`name`),
  FOREIGN KEY `FK_EMPLOYEE_DEPARTMENT` (department_id) REFERENCES department(id),
  FOREIGN KEY `FK_EMPLOYEE_ROLE` (role_id) REFERENCES role(id) 
) ENGINE=INNODB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

Alter table employee update `email` VARCHAR(255) COLLATE utf8_bin NOT NULL DEFAULT 8,

insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Steven', 'Steven@aaa.com', 189000, 'R and D', 'Robert', '1975-12-08', '2010-9-1', 46, 7);
insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Michael', 'Michael@aaa.com', 159000, 'HR', 'Adam', '1983-12-08', '2017-7-2', 51, 6);
insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Robert', 'Robert@aaa.com', 289000, 'management', 'none', '1980-05-08', '2009-9-1', 46, 11);
insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Laura', 'Laura@aaa.com', 105000, 'Accounting', 'Adam', '1978-01-08', '2012-8-1', 47, 6);
insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Anne', 'anne@aaa.com', 99000, 'Sales', 'Adam', '1979-12-08', '2016-7-20', 50, 6);
insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Adam', 'adam@aaa.com', 419000, 'management', 'none', '1968-09-18', '2009-9-19', 49, 10);

insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Jess', 'jess@aaa.com', 75000, 'Accounting', 'Laura', '1988-12-08', '2010-9-1', 47, 13);
insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Tom', 'tom@aaa.com', 89000, 'in customer service', 'Janet', '1988-12-08', '2010-9-1', 48, 12);
insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Arron', 'arron@aaa.com', 119000, 'R & D', 'Robert', '1988-12-08', '2010-9-1', 46, 12);

insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Arrie 2', 'arrie@aaa.com', 89000, 'in customer service', 'Janet', '1988-12-08', '2010-9-1', 48, 12);
insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Steven 2', 'Steven2@aaa.com', 89000, 'R & D', 'Robert', '1988-12-08', '2010-9-1', 46, 13);
insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Steven 3', 'Ste3ven@aaa.com', 89000, 'Accounting', 'Laura', '1988-12-08', '2010-9-1', 47, 12);
insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Laura 2', 'Laura2@aaa.com', 89000, 'Sales', 'Anne', '1988-12-08', '2010-9-1', 50, 12);
insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Anne 2', 'anne2@aaa.com', 89000, 'Customer Service', 'Janet', '1988-12-08', '2010-9-1', 48, 12);
insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Jess 2', 'jess2@aaa.com', 89000, 'Management', 'Janet', '1988-12-08', '2010-9-1', 46, 13;
insert into employee (name, email, salary, description, manager, birthdate, hire_date, department_id, role_id) values ('Nancy 2', 'nancy2@aaa.com', 89000, 'Sales', 'Anne', '1988-12-08', '2010-9-1', 50, 12);

1. get the employee from sales department
SELECT * FROM employee WHERE employee.department_id in (select id from department where name='Sales');

SELECT employee.* FROM employee INNER JOIN department ON employee.department_id = department.id WHERE department.name = 'Sales';

2. calculate the average salary of each department
SELECT department.name, AVG(employee.salary) 
FROM employee INNER JOIN department ON employee.department_id = department.id 
GROUP BY department.name;

3. find the emplyees from each department with above the average salary

SELECT department.name, employee.name, employee.salary 
FROM employee 
INNER JOIN department ON employee.department_id = department.id 
WHERE employee.salary > (SELECT AVG(salary) FROM employee) 
ORDER BY employee.salary;

4. salary above depart avg.
SELECT
    department.name AS department_name,
    employee_name,
    salary
FROM
(
    SELECT
        NAME AS employee_name,
	department_id,
	salary
    FROM employee e1
    WHERE salary >= (SELECT AVG(salary) FROM employee e2 WHERE e2.department_id = e1.department_id)
) AS T1 INNER JOIN department ON department.id = T1.department_id
ORDER BY department_name, salary DESC

5. top 3 in each group
SELECT
    employee_name,
    department.name AS department_ame,
    salary, 
    rn
FROM
(
    SELECT
        NAME AS employee_name,
	department_id,
	salary,
        @rn := IF(@prev = department_id, @rn + 1, 1) AS rn,
        @prev := department_id
    FROM employee
    ORDER BY department_id, salary DESC
) AS T1 INNER JOIN department ON department.id = T1.department_id
WHERE rn <= 3

6. Connect -- Oracle: hierarchical query: 
SELECT col1, col2, col3, col4 FROM table
START WITH col2 = val CONNECT BY (PRIOR) col2 = col3 
ORDER SIBLINGS BY col1;


get the manager for each employee
SELECT employee_id, last_name, manager_id
   FROM employees
   CONNECT BY PRIOR employee_id = manager_id;

EMPLOYEE_ID LAST_NAME                 MANAGER_ID
----------- ------------------------- ----------
        101 Kochhar                          100
        108 Greenberg                        101
        109 Faviet                           108
        110 Chen                             108
        111 Sciarra                          108
        112 Urman                            108
        113 Popp                             108
        200 Whalen                           101

Get the manager for each employee and the employee level
SELECT last_name, employee_id, manager_id, LEVEL
      FROM employees
      START WITH employee_id = 100
      CONNECT BY PRIOR employee_id = manager_id
      ORDER SIBLINGS BY last_name;

LAST_NAME                 EMPLOYEE_ID MANAGER_ID      LEVEL
------------------------- ----------- ---------- ----------
King                              100                     1
Cambrault                         148        100          2
Bates                             172        148          3
Bloom                             169        148          3
Fox                               170        148          3
Kumar                             173        148          3
Ozer                              168        148          3
Smith                             171        148          3
De Haan                           102        100          2
Hunold                            103        102          3
Austin                            105        103          4
Ernst                             104        103          4
Lorentz                           107        103          4
Pataballa                         106        103          4
Errazuriz                         147        100          2
Ande                              166        147          3
Banda                             167        147          3


<!DOCTYPE html>
<html>
<head>
<style>
.cn {
	/* position: relative; */
	display: flex;
	justify-content: center;
	align-items: center;  
    
    width: 500px;
	height: 500px;
    background-color: green;
    
}

.inner {
	/* position: absolute;
	top: 50%; left: 50%;
	transform: translate(-50%,-50%); */
	width: 300px;
	height: 300px;
    background-color: red;
}

.wrap-grid {
	display: grid;
	place-content: center; 
    /* align-content: center; */
    width: 500px;
	height: 500px;
    background-color: yellow;
}

#container {
 	width: 100px;
	height: 100px;
    background-color: red;
}

</style>
</head>
<body>

<div class="cn"><div class="inner">your content</div></div>

<div class="wrap-grid">
	<div id="container">vertical aligned text<br />some more text here
	</div>
</div>

</body>
</html>


