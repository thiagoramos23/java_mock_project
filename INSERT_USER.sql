create user 'teste' identified by 'teste';
create database teste;



INSERT INTO `teste`.`empresas`
(`id`,
`cnpj`,
`nome_fantasia`,
`razao_social`,
`telefone`)
VALUES
(1,
 '123123123',
 'Fantasia',
 'Sem Razao',
 '2133-3123');

INSERT INTO `teste`.`pessoas`
(`id`,
`atualizado_em`,
`cpf`,
`criado_em`,
`email`,
`bairro`,
`cep`,
`cidade`,
`endereco`,
`estado`,
`foto`,
`nome`,
`rg`,
`sexo`,
`telefone_movel`,
`tipo_pessoa`,
`empresa_id`)
VALUES
(1,
 '2013-10-10 00:00:00',
 '11111111111',
 '2013-10-10 00:00:00',
 'teste@teste.com',
 'teste',
 '57000000',
 'Maceio',
 'Endereco',
 'AL',
 '',
 'Thiago',
 '99999999999999',
 'M',
 '',
 'F',
  1);

INSERT INTO `teste`.`usuarios`
(`id`,
`ativo`,
`login`,
`senha`,
`token_acesso`,
`ultimo_acesso`,
`pessoa_id`)
VALUES
(1,
 1,
 'teste',
 '698dc19d489c4e4db73e28a713eab07b',
 '698dc19d489c4e4db73e28a713eab07b',
 '2013-10-10 00:00:00',
 1);