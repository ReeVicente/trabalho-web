create table Usuario
(
id integer not null generated always as identity (start with 1, increment by 1),
nome varchar(100) not null,
email varchar(50) not null unique,
senha varchar(100) not null,
ativo smallint not null,
CONSTRAINT Usuario_PK PRIMARY KEY (id)
);

create table Papel (
id integer not null generated always as identity (start with 1, increment by 1),
email varchar(50) not null,
nome varchar(50) not null
);


create table Cliente (
    id integer not null generated always as identity (start with 1, increment by 1),
    cpf varchar(11) not null unique,
    telefone varchar(11),
    sexo varchar(1),
    datadenascimento varchar(10) not null,
    email varchar(50) not null,
    FOREIGN KEY (email) REFERENCES Usuario(email)
    
);

create table Locadora (
    id integer not null generated always as identity (start with 1, increment by 1),
    cnpj varchar(18) not null unique,
    cidade varchar(11),
    email varchar(50) not null,
    FOREIGN KEY (email) REFERENCES Usuario(email)
);

create table Locacao (
    id integer not null generated always as identity (start with 1, increment by 1),
    cpf varchar(11) not null unique,
    cnpj varchar(18) not null unique,
    data_locacao date not null,
    FOREIGN KEY (cpf) REFERENCES Cliente(cpf),
    FOREIGN KEY (cnpj) REFERENCES Locadora(cnpj)
);