# MyJavaProject
我的实训项目
+----------------------+
| Tables_in_restaurant |
+----------------------+
| dinnertable          |
| menu                 |
| ordermenu            |
| orders               |
| takeman              |
| userinfo             |
+----------------------+
 desc dinnertable;
+----------+-------------+------+-----+---------+----------------+
| Field    | Type        | Null | Key | Default | Extra          |
+----------+-------------+------+-----+---------+----------------+
| tablenum | int         | NO   | PRI | NULL    | auto_increment |
| status   | varchar(20) | YES  |     | 空闲    |                |
+----------+-------------+------+-----+---------+----------------+
desc menu;
+-----------+--------------+------+-----+---------+----------------+
| Field     | Type         | Null | Key | Default | Extra          |
+-----------+--------------+------+-----+---------+----------------+
| id        | int          | NO   | PRI | NULL    | auto_increment |
| dname     | varchar(20)  | YES  | UNI | NULL    |                |
| price     | int          | YES  |     | NULL    |                |
| introduce | text         | YES  |     | NULL    |                |
| kind      | varchar(20)  | YES  |     | NULL    |                |
| image     | varchar(255) | YES  |     | NULL    |                |
+-----------+--------------+------+-----+---------+----------------+
desc ordermenu;
+-------------+-------------+------+-----+---------+-------+
| Field       | Type        | Null | Key | Default | Extra |
+-------------+-------------+------+-----+---------+-------+
| ordernumber | int         | NO   |     | NULL    |       |
| dname       | varchar(20) | NO   |     | NULL    |       |
+-------------+-------------+------+-----+---------+-------+
 desc orders;
+-------------+--------------+------+-----+-----------+----------------+
| Field       | Type         | Null | Key | Default   | Extra          |
+-------------+--------------+------+-----+-----------+----------------+
| ordernumber | int          | NO   | PRI | NULL      | auto_increment |
| telnumber   | varchar(20)  | NO   |     | NULL      |                |
| way         | varchar(255) | NO   |     | NULL      |                |
| address     | varchar(255) | NO   |     | NULL      |                |
| tablenumber | varchar(20)  | YES  |     | 待处理    |                |
| courier     | varchar(20)  | YES  |     | 待处理    |                |
| orderstatus | varchar(20)  | YES  |     | 排队中    |                |
| account     | varchar(20)  | YES  |     | NULL      |                |
+-------------+--------------+------+-----+-----------+----------------+
 desc takeman;
+-------+-------------+------+-----+---------+-------+
| Field | Type        | Null | Key | Default | Extra |
+-------+-------------+------+-----+---------+-------+
| name  | varchar(20) | NO   | PRI | NULL    |       |
+-------+-------------+------+-----+---------+-------+
 desc userinfo;
+----------+--------------+------+-----+---------+----------------+
| Field    | Type         | Null | Key | Default | Extra          |
+----------+--------------+------+-----+---------+----------------+
| account  | varchar(20)  | NO   | UNI | NULL    |                |
| password | varchar(20)  | NO   |     | NULL    |                |
| photo    | varchar(255) | YES  |     | NULL    |                |
| username | varchar(10)  | NO   |     | NULL    |                |
| tnumber  | varchar(20)  | NO   |     | NULL    |                |
| sex      | varchar(4)   | NO   |     | NULL    |                |
| address  | varchar(20)  | NO   |     | NULL    |                |
| id       | int          | NO   | PRI | NULL    | auto_increment |
| identity | varchar(20)  | NO   |     | NULL    |                |
+----------+--------------+------+-----+---------+----------------+
