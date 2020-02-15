create table employees (
	id int not null,
	name varchar(50) not null,
	position varchar(50) not null
);

create sequence employee_sequence
	minvalue 1
	start with 1
	increment by 1;