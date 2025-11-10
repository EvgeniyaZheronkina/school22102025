--liquibase formatted sql

--changeset ezheronkina:1
CREATE INDEX name_student_index ON student (name);

--changeset ezheronkina:2
CREATE INDEX faculty_name_color_index ON faculty(name, color);