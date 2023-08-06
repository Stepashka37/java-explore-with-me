drop table if exists events cascade ;
drop table if exists geo_location cascade ;
drop table if exists users cascade ;
drop table if exists token cascade ;
drop table if exists events_compilation;
drop table if exists compilations cascade;
drop table if exists categories cascade;
drop function if exists distance;
drop table if exists requests cascade;

CREATE OR REPLACE FUNCTION distance(lat1 float, lon1 float, lat2 float, lon2 float)
    RETURNS float
AS
'
declare
    dist float = 0;
    rad_lat1 float;
    rad_lat2 float;
    theta float;
    rad_theta float;
BEGIN
    IF lat1 = lat2 AND lon1 = lon2
    THEN
        RETURN dist;
    ELSE
        -- переводим градусы широты в радианы
        rad_lat1 = pi() * lat1 / 180;
        -- переводим градусы долготы в радианы
        rad_lat2 = pi() * lat2 / 180;
        -- находим разность долгот
        theta = lon1 - lon2;
        -- переводим градусы в радианы
        rad_theta = pi() * theta / 180;
        -- находим длину ортодромии
        dist = sin(rad_lat1) * sin(rad_lat2) + cos(rad_lat1) * cos(rad_lat2) * cos(rad_theta);

        IF dist > 1
            THEN dist = 1;
        END IF;

        dist = acos(dist);
        -- переводим радианы в градусы
        dist = dist * 180 / pi();
        -- переводим градусы в километры
        dist = dist * 60 * 1.8524;

        RETURN dist;
    END IF;
END;
'
LANGUAGE PLPGSQL;


create table users (
    id    bigserial primary key,
    email varchar(254)
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    name  varchar(250),
    password varchar(255),
    role     varchar(255)
);

create table compilations
(
    id     bigserial
        primary key,
    pinned boolean,
    title  varchar(50)
);

create table token
(
    id         integer not null
        primary key,
    expired    boolean not null,
    revoked    boolean not null,
    token      varchar(255),
    token_type varchar(255),
    user_id    bigint
        constraint fkj8rfw4x0wjjyibfqq566j4qng
            references users
);

create table categories (
       id     bigserial
        primary key,
        name varchar(50)
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

create table geo_location
(
    id        bigserial
        primary key,
    latitude  real,
    longitude real,
    name      varchar(100)
);