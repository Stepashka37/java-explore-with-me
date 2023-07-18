drop table if exists users;

create table users (
    id    bigserial primary key,
    email varchar(30)
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    name  varchar(40)
);