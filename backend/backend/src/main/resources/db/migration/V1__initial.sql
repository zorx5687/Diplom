create table post
(
    id           bigserial not null,
    address      varchar(255),
    category     varchar(255),
    created_time timestamp(6),
    description  varchar(255),
    price        float4,
    title        varchar(255),
    url          varchar(255),
    author_id    bigint    not null,
    primary key (id)
);
create table users
(
    id           bigserial not null,
    created_time timestamp(6),
    email        varchar(255),
    name         varchar(255),
    password     varchar(255),
    phone        varchar(255),
    surname      varchar(255),
    primary key (id)
);
alter table if exists post
    add constraint FK1mpebp1ayl0twrwm7ruiof778 foreign key (author_id) references users