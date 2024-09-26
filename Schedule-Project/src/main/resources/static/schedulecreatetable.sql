create table schedule(
    schedule_id varchar(36) primary key ,
    user_id varchar(36),
    todo_list varchar(255),
    created_at timestamp,
    updated_at timestamp,
    foreign key (user_id) references user(user_id)
);

create table user(
    user_id varchar(36) primary key,
    schedule_id varchar(36),
    username varchar(50),
    password varchar(60),
    email varchar(100),
    created_at timestamp,
    updated_at timestamp
);