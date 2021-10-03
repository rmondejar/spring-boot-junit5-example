drop table if exists book;
drop table if exists author;

create table author
(
    id                    bigint(20) not null auto_increment,
    first_name            varchar(100) not null,
    last_name             varchar(100) not null,
    email                 varchar(150) not null,
    primary key (id)
);

create table book
(
    id                    bigint(20) not null auto_increment,
    title                 varchar(100) not null,
    description           varchar(500),
    genre                 varchar(50) not null,
    price                 numeric,
    author_id             bigint(20) references author(id) on delete cascade,
    primary key (id)
);