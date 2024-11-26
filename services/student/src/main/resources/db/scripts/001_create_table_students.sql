create table if not exists t_student
(
    id bigserial primary key,
    name varchar(255),
    surname varchar(255),
    patronymic varchar(255),
    grade_book_number varchar(255)
        constraint username_constraint_unique
            unique,
    faculty varchar(255)
);