insert into genders(id,name)
values(1,'MALE');

insert into genders(id,name)
values(2,'FEMALE');

insert into languages(id,name)
values (1,'UKRAINE');

insert into languages(id,name)
values (2,'ENGLISH');

insert into users (id,age,email,first_name,last_name,password,role,user_status,gender_id)
values (1,30,'admin@gmail.com','Ivan','Pylypiv','$2a$12$lC2HWrt6bR9dPy4ADukcyOBxz1Do57fSf8./G0DvQrywGUybbKeZu','ADMIN','ACTIVE',1);

insert into users (id,age,email,first_name,last_name,password,role,user_status,gender_id)
values (2,25,'client@gmail.com','Client','Clientovych','$2a$12$J6qKmOCh55pTr33ysusEEeoIaN63NaPypLjtVV21ZKnLfnvCn8oNK','CLIENT','ACTIVE',1);

insert into users (id,age,email,first_name,last_name,password,role,user_status,gender_id)
values (3,33,'guest@gmail.com','Petro','Nike','$2a$12$vtn0b8Vgd5Gl4pWAKp77iOu3mezbNmgEqQIsKwU23lCsTMpNP7xpO','GUEST','ACTIVE',1);

insert into books (id,author,book_status,pages,title,language_id)
values (1,'Joshua Bloch','IN_THE_LIBRARY',414,'Effective Java 3rd Edition, Kindle Edition',2);

insert into books (id,author,book_status,pages,title,language_id)
values (2,'Cay S. Horstmann','IN_THE_LIBRARY',889,'Core Java Volume I – Fundamentals',2);

insert into books (id,author,book_status,pages,title,language_id)
values (3,'Herbert Schildt','IN_THE_LIBRARY',684,'Java: A Beginner''s Guide',2);

insert into books (id,author,book_status,pages,title,language_id)
values (4,'Herbert Schildt','IN_THE_LIBRARY',1208,'Java: The Complete Reference',2);

insert into books (id,author,book_status,pages,title,language_id)
values (5,'Brian Goetz','IN_THE_LIBRARY',424,'Java Concurrency in Practice',2);

insert into books (id,author,book_status,pages,title,language_id)
values (6,'Lasse Koskela','IN_THE_LIBRARY',470,'Test Driven: TDD and Acceptance TDD for Java Developers',2);

insert into books (id,author,book_status,pages,title,language_id)
values (7,'Брюс Еккель','IN_THE_LIBRARY',638,'Філософія Java',1);

insert into books (id,author,book_status,pages,secret,title,language_id)
values (8,'Doug Lowe','IN_THE_LIBRARY',960,'little secret :)','Java All-in-One For Dummies 5th Edition',2);

