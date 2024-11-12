\c java_e_Wallet_db;

drop table if exists users, wallets, balances, transactions;
drop sequence if exists wallet_number_seq cascade;

create sequence wallet_number_seq;

create table users (
	user_id BIGSERIAL primary key,
	user_name VARCHAR not null,
	user_email VARCHAR not null,
	user_password VARCHAR not null,
	created_at BIGINT not null,
	updated_at BIGINT not null,
	deleted_at BIGINT not null default 0
);

create table wallets (
	wallet_id BIGSERIAL primary key,
	user_id BIGINT not null,
	wallet_number VARCHAR(13) not null DEFAULT CONCAT('100', lpad(nextval('wallet_number_seq')::text, 10, '0')),
	created_at BIGINT not null,
	updated_at BIGINT not null,
	deleted_at BIGINT not null default 0
);

create table balances (
	balance_id BIGSERIAL primary key,
	wallet_id BIGINT not null,
	asset VARCHAR not null,
	amount NUMERIC not null default 0,
	frozen NUMERIC not null default 0,
	created_at BIGINT not null,
	updated_at BIGINT not null,
	deleted_at BIGINT not null default 0
);

create table transactions (
	transaction_id BIGSERIAL primary key,
	sender_wallet_id BIGINT not null,
	recipient_wallet_id BIGINT not null,
	asset VARCHAR not null,
	amount NUMERIC not null,
	created_at BIGINT not null,
	updated_at BIGINT not null,
	deleted_at BIGINT not null default 0
);