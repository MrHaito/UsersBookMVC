drop table if exists books;
drop table if exists persons;

create table persons
(
    person_id     int generated by default as identity primary key,
    name          varchar(30)                        not null unique,
    year_of_birth int check ( year_of_birth > 1900 ) not null
);

create table books
(
    book_id   int generated by default as identity primary key,
    person_id int          references persons (person_id) on delete set null,
    title     varchar(100) not null,
    author    varchar(30)  not null,
    year      int          not null
);