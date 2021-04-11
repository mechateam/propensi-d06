# What to do
###Clone
Ya clone aja biasa git clone [Copas https nya]


###Start XAMPP nya
Start all aja
kalo error jalanin ini di terminal :
-  sudo killall mysqld
- sudo /Applications/XAMPP/xamppfiles/bin/mysql.server start


###PHPMyAdmin
Buka phpmyadmin, create new database namanya "sihedes"


###application.properties
- Create file namanya **application.properties** sejajar sama file **application-production.properties**
- Trs isinya ini :
```
#konfigurasi untuk koneksi MySQL
spring.datasource.platform=mysql
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

#Sesuaikan nama database
spring.datasource.url=jdbc:mysql://localhost:3306/sihedes?useSSL=false&serverTimezone=Asia/Jakarta

#Sesuaikan nama dan password mysql
spring.datasource.username=root
spring.datasource.data-password=

#Optimize query untuk db MySQL
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect

#Pembuatan database (create || create drop || validate || update)
spring.jpa.hibernate.ddl-auto=create

#Line baru Tutorial 5
server.port=2020

```
- Kalo udah di run, jangan lupa ganti **create** jadi **update**


###Further Information
Contact Meldi Hafizh. Selamat mencoba alllllll