create table if not exists STATION
(
    id   bigint auto_increment not null,
    name varchar(255)          not null unique,
    primary key (id)
);

create table if not exists LINE
(
    id         bigint auto_increment not null,
    name       varchar(255)          not null unique,
    color      varchar(20)           not null,
    extra_fare int check (extra_fare between 0 and 2000000000),
    primary key (id)
);

create table if not exists SECTION
(
    id              bigint auto_increment not null,
    line_id         bigint                not null,
    up_station_id   bigint                not null references STATION (id),
    down_station_id bigint                not null references STATION (id),
    distance        int                   not null check (distance between 1 and 1000000),
    primary key (id),
    constraint fk_section_line_id foreign key (line_id) references Line (id) on delete cascade
);

create table if not exists MEMBER
(
    id       bigint auto_increment not null,
    email    varchar(255)          not null unique,
    password varchar(255)          not null,
    age      int                   not null CHECK (age between 1 and 200),
    primary key (id)
);

create table if not exists FAVORITE
(
    id                bigint auto_increment not null,
    member_id         bigint                not null,
    source_station_id bigint                not null,
    target_station_id bigint                not null,
    primary key (id),
    constraint fk_favorite_member_id foreign key (member_id) references MEMBER (id) on delete cascade,
    constraint fk_favorite_source_station_id foreign key (source_station_id) references STATION (id) on delete cascade,
    constraint fk_favorite_target_station_id foreign key (target_station_id) references STATION (id) on delete cascade
);