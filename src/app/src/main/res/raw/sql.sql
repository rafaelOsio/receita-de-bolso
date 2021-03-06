create table " + ReceitaDAO.TABELA + "
(
    " + ReceitaDAO.ID + " integer primary key autoincrement,
    " + ReceitaDAO.CATEGORIA_ID + " integer not null,
    " + ReceitaDAO.NOME + " varchar(100) not null,

    " + ReceitaDAO.TEMPO_PREPARO + " int default 0,
    " + ReceitaDAO.RENDIMENTO + " int default 0,
    " + ReceitaDAO.INGREDIENTES + " varchar(1000),
    " + ReceitaDAO.MODO_PREPARO + " varchar(1000),

    " + ReceitaDAO.ULTIMO_ACESSO + " varchar(50) not null,
    " + ReceitaDAO.IS_FAV + " int default 0,
    " + ReceitaDAO.NOME_IMAGEM + " varchar(200),
    " + ReceitaDAO.URL + " varchar(2000),
);