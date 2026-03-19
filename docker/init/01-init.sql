create table if not exists app_users (
                                     id numeric primary key,
                                     login varchar(255),
    name varchar(255),
    role varchar(255)
    );

insert into app_users (id, login, name, role)
values
    (1, 'login1', 'name1', 'role1'),
    (2, 'login2', 'name2', 'role2'),
    (3, 'login3', 'name3', 'role3')
    on conflict (id) do nothing;