create sequence hibernate_sequence start 1 increment 1;

create table books (
    id int8 not null,
    author varchar(255),
    book_status varchar(255),
    pages int4 not null,
    secret varchar(255),
    title varchar(255),
    language_id int4,
    primary key (id)
);

create table genders (
    id int4 not null,
    name varchar(255),
    primary key (id)
);

create table languages (
    id int4 not null,
    name varchar(255),
    primary key (id)
);

create table users (
    id int8 not null,
    age int4 not null,
    email varchar(255) not null,
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255) not null,
    role varchar(255),
    user_status varchar(255),
    gender_id int4,
    primary key (id)
);

alter table if exists books
    add constraint LANGUAGE_ID_FK
    foreign key (language_id) references languages;

alter table if exists users
    add constraint GENDER_ID_FK
    foreign key (gender_id) references genders;