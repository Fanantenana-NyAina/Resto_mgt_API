do $$
    begin
        if not exists(select from pg_roles where rolname='restomgt') then
        create role restomgt with login password 'restomgt';
    end if;
end $$;

create database restodb owner restomgt;

grant connect on database restodb to restomgt;
grant usage on schema public to restomgt;
grant select, insert, delete, update on all tables in schema public to restomgt;
alter default privileges in schema public grant select, insert, update, delete on tables TO restomgt;
