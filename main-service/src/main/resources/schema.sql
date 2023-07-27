drop table if exists events cascade ;
drop table if exists users cascade ;
drop table if exists events_compilation;
drop table if exists compilations cascade;
drop table if exists categories cascade;

drop table if exists requests cascade;


create table users (
    id    bigserial primary key,
    email varchar(254)
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    name  varchar(250)
);

create table compilations
(
    id     bigserial
        primary key,
    pinned boolean,
    title  varchar(50)
);

create table categories (
       id  bigserial not null,
        name varchar(50),
        primary key (id)
    );

create table events (
    id                bigserial
        primary key,
    annotation        varchar(2000),
    created_on        timestamp,
    description       varchar(7000),
    event_date        timestamp,
    latitude          real,
    longitude         real,
    paid              boolean,
    participant_limit integer,
    published_on      timestamp,
    moderation        boolean,
    state             varchar(255) default 'PENDING'::character varying,
    title             varchar(120),
    views             integer default 0,
    category_id       bigint
        constraint fko6mla8j1p5bokt4dxrlmgwc28
            references categories,
    initiator_id      bigint
        constraint fkgsyp7tc40dhju9fq5i767kyun
            references users
);

create table events_compilation
(
    compilation_id bigint not null
        constraint fkh6gq4o4ivjet51euug2g6pvdf
            references compilations,
    event_id       bigint not null
        constraint fkbbfjvsgf2fqny8itarbtl08x5
            references events
);


create table requests (
    id           bigserial
        primary key,
    created      timestamp,
    status       varchar,
    event_id     bigint
        constraint fkm7vtr0204t3xcymbx4sa9t1ot
            references events,
    requester_id bigint
        constraint fkeoax2t4j9i61p9lmon3009tr4
            references users
);