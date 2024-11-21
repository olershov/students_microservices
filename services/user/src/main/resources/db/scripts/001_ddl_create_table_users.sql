create table if not exists t_user
(
    id bigserial primary key,
    password    varchar(255),
    username    varchar(255)
        constraint username_constraint_unique
            unique
);