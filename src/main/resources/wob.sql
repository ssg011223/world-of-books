create table if not exists marketplace
(
    id               integer not null
        constraint marketplace_pk
            primary key,
    marketplace_name varchar not null
);

create unique index if not exists marketplace_id_uindex
    on marketplace (id);

create table if not exists status
(
    id          integer not null
        constraint status_pk
            primary key,
    status_name varchar
);

create unique index if not exists status_id_uindex
    on status (id);

create table if not exists listing
(
    id                         uuid    not null
        constraint listing_pk
            primary key,
    title                      text    not null,
    description                text    not null,
    inventory_item_location_id uuid    not null,
    listing_price              integer not null,
    currency                   text    not null,
    quantity                   integer not null,
    listing_status             integer not null
        constraint listing_status_id_fk
            references status,
    marketplace                integer not null
        constraint listing_marketplace_id_fk
            references marketplace,
    upload_time                date,
    owner_email_address        text    not null,
    saved_at    timestamp not null
);

create unique index if not exists listing_id_uindex
    on listing (id);

create table if not exists location
(
    id                uuid not null
        constraint location_pk
            primary key,
    manager_name      text,
    phone             text,
    address_primary   text,
    address_secondary text,
    country           text,
    town              text,
    postal_code       text
);

create unique index if not exists location_id_uindex
    on location (id);


