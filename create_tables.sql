create table roles (
        id INTEGER identity,
        name varchar(60),
        primary key (id)
);

CREATE TABLE user(
	id_user INTEGER identity,
	email varchar(50) not null,
	password varchar(30) not null,
	name varchar(30) not null,
	is_active boolean default true not null,
	last_login timestamp,
	created timestamp not null,
	last_update timestamp,
	UNIQUE (email)	
);

CREATE TABLE phones (
	id_phone INTEGER identity,
    id_user INTEGER,
    number varchar(10) not null,
    citycode varchar(5) not null,
    contrycode varchar(2) not null,
	FOREIGN KEY (id_user) REFERENCES user(id_user)
);