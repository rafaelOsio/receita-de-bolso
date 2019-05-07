create table " + ReceitaDAO.TABELA + "
(
    " + ReceitaDAO.ID + " integer primary key autoincrement,
    " + ReceitaDAO.CATEGORIA_ID + " integer not null,
    " + ReceitaDAO.NOME + " varchar(100) not null,
    " + ReceitaDAO.TEMPO_PREPARO + " integer not null,
    " + ReceitaDAO.RENDIMENTO + " integer not null,
    " + ReceitaDAO.INGREDIENTES + " varchar(1000) not null,
    " + ReceitaDAO.MODO_PREPARO + " varchar(1000) not null,
    " + ReceitaDAO.ULTIMO_ACESSO + " varchar(50) not null
);