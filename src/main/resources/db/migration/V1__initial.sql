create table if not exists _user (
    primary key (id),
    id uuid not null,
    email varchar(50) not null unique,
    password varchar(255),
    role varchar(15) not null,
    account_provider varchar(15) not null,
    profile_id bigint unique,
    created_at timestamp not null,
    updated_at timestamp not null
);

create table profile (
     primary key (id),
     id bigserial not null,
     name varchar(25) not null,
     last_name varchar(45) not null,
     cpf varchar(11) unique,
     phone varchar(14),
     picture varchar(255),
     updated_at timestamp not null
);

create table address (
     primary key (id),
     id bigserial not null,
     street varchar(60) not null,
     number varchar(4) not null,
     complement varchar(15),
     district varchar(45) not null,
     city varchar(30) not null,
     state varchar(20) not null,
     zip varchar(10) not null,
     profile_id bigint
);

create table restaurant (
    primary key (id),
    id bigserial not null,
    name varchar(45) not null unique,
    slug varchar(25) not null unique,
    cnpj varchar(25) not null unique,
    phone varchar(14),
    score float4 not null,
    address_id bigint unique,
    created_at timestamp not null,
    updated_at timestamp not null
);

alter table if exists _user
    add constraint _user_profile
        foreign key (profile_id)
            references profile;

alter table if exists address
    add constraint address_profile
        foreign key (profile_id)
            references profile;

alter table if exists restaurant
    add constraint restaurant_address
        foreign key (address_id)
            references address;