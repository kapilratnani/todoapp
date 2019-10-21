create sequence hibernate_sequence start 1 increment 1
create table todo (id int4 not null, date_created timestamp, date_updated timestamp, description varchar(255), done_status boolean not null, title varchar(255), user_id int4 not null, primary key (id))
create table todo_user (id int4 not null, date_created timestamp, date_updated timestamp, password varchar(255), username varchar(255), primary key (id))
create table user_profile (id int4 not null, date_created timestamp, date_updated timestamp, name varchar(255), pic_url varchar(255), time_zone varchar(255), user_id int4 not null, primary key (id))
alter table todo add constraint FKrpf4b7iinprtt0j27s1kfwl5o foreign key (user_id) references todo_user
alter table user_profile add constraint FK8n3y6ykcuex57ocd2f13j4vy2 foreign key (user_id) references todo_user
