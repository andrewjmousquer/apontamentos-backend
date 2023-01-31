-- Aguardando Entrada 1.1
INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (1,'1.1 Aguardando Entrada',1,0,10166);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (1,11,37,'Recebimento Equipe Transfer','fa-fa-user');

-- Recebimento Equipe Transfer 2.1
INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (2,'2.1 Recebimento Equipe Transfer',1,0,10341);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (2,21,37,'Aguardando Checklist Entrada','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (2,41,37,'Lavagem Entrada','fa-fa-user');

-- Lavagem de Entrada 3.1
INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (3,'3.1 Em Lavagem',1,0,10167);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (3,81,35,'Em Lavagem','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (3,21,37,'Aguardando Checklist Entrada','fa-fa-user');

-- Checklist Entrada 4.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (4,'4.1 Checklist Entrada',1,0,10168);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (4,31,35,'Inicio Cheecklist','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (4,61,37,'Envio Estacionamento','fa-fa-user');

-- Transfer Estacionamento 5.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (5,'5.1 Envio Estacionamento',1,0,10343);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (5,141,37,'Recebidos','fa-fa-user');

-- Envio Preparo Produção 8.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (6,'8.1 Aguardando Preparo Produção',1,0,10172);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (6,111,35,'Inicio Preparo Produção','fa-fa-user');

-- Preparo para Produção 9.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (7,'9.1 Inicio Preparo Produção',1,0,10173);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (7,121,36,'Pausa Preparo Produção','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (7,131,37,'Aguardando Desmontagem','fa-fa-user');

-- Produção dia 01

-- Desmontagem 10.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (8,'10.1 Aguardando Desmontagem',0,0,10174);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (8,61,35,'Inicio Desmontagem','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (8,71,36,'Pausa Desmontagem','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (8,591,37,'Concluído','fa-fa-user');

-- Desmontagem Elétrica 11.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (9,'11.1 Aguardando Desmontagem Elétrica',0,0,10177);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (9,101,35,'Inicio Desmontagem Elétrica','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (9,111,36,'Pausa Desmontagem Elétrica','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (9,591,37,'Concluído','fa-fa-user');

-- Instalação de Calço e Molas 11.2

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (10,'11.2 Aguardando Instalação Calço Molas',0,0,10344);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (10,261,35,'Inicio Instalação Calço Molas','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (10,551,36,'Pausa Instalação Calço Molas','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (10,591,37,'Concluído','fa-fa-user');

-- Fibra 12.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (11,'12.1 Aguardando Fibra',0,0,10193);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (11,661,35,'Inicio Fibra','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (11,671,36,'Pausa Fibra','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (11,591,37,'Concluído','fa-fa-user');

-- Blindagem Capô 12.2

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (12,'12.2 Aguardando Blindagem Capô',0,0,10196);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (12,701,35,'Inicio Blindagem Capô','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (12,711,36,'Pausa Blindagem Capô','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (12,591,37,'Concluído','fa-fa-user');

-- Blindagem Teto Solar 12.3

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (13,'12.3 Aguardando Blindagem Teto Solar',0,0,10393);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (13,731,35,'Inicio Blindagem Teto Solar','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (13,741,36,'Pausa Blindagem Teto Solar','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (13,591,37,'Concluído','fa-fa-user');

-- Blindagem Tampa 12.4

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (14,'12.4 Aguardando Blindagem Tampa',0,0,10183);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (14,271,35,'Inicio Blindagem Tampa','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (14,561,36,'Pausa Blindagem Tampa','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (14,591,37,'Concluído','fa-fa-user');

-- Logística 1 (Fibra e Aço) 13.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (15,'13.1 Aguardando Logistica',0,0,10396);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (15,801,35,'Inicio Logistica','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (15,871,36,'Pausa Logistica','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (15,591,37,'Concluído','fa-fa-user');

-- Produção dia 02

-- Pintura Tampa 13.2

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (16,'13.2 Aguardando Pintura Tampa',0,0,10398);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (16,901,35,'Inicio Pintura Tampa','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (16,1001,36,'Pausa Pintura Tampa','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (16,591,37,'Concluído','fa-fa-user');

-- Aço Estrutural 14.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (17,'14.1 Aguardando Aço Estrutural',0,0,10199);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (17,151,35,'Inicio Aço Estrutural','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (17,211,36,'Pausa Aço Estrutural','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (17,351,37,'Concluído','fa-fa-user');

-- Frente de Painel 14.2

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (18,'14.2 Aguardando Frente de Painel',0,0,10202);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (18,161,35,'Inicio Frente de Painel','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (18,221,36,'Pausa Frente de Painel','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (18,351,37,'Concluído','fa-fa-user');

-- Aço Bloco 14.3

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (19,'14.3 Aguardando Aço Bloco',0,0,10205);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (19,171,35,'Inicio Aço Bloco','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (19,231,36,'Pausa Aço Bloco','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (19,351,37,'Concluído','fa-fa-user');

-- Preparação Elétrica 15.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (20,'15.1 Aguardando Preparação Elétrica',0,0,10212);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (20,71,35,'Inicio Preparação Elétrica','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (20,411,36,'Pausa Preparação Elétrica','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (20,351,37,'Concluído','fa-fa-user');

-- Intercomunicador 15.2

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (21,'15.2 Aguardando Intercomunicador',0,0,10214);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (21,201,35,'Inicio Intercomunicador','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (21,421,36,'Pausa Intercomunicador','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (21,351,37,'Concluído','fa-fa-user');

-- Vidros Móveis 16.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (22,'16.1 Aguardando Vidros Moveis',0,0,10215);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (22,451,35,'Inicio Vidros Moveis','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (22,461,36,'Pausa Vidros Moveis','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (22,351,37,'Concluído','fa-fa-user');

-- Instalação Teto Solar 16.2

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (23,'16.2 Aguardando Teto Solar',0,0,10211);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (23,181,35,'Inicio Teto Solar','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (23,241,36,'Pausa Teto Solar','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (23,351,37,'Concluído','fa-fa-user');

-- Instalação da Tampa 16.3

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (24,'16.3 Aguardando Instalação Tampa',0,0,10401);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (24,501,35,'Inicio Instalação Tampa','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (24,511,36,'Pausa Instalação Tampa','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (24,351,37,'Concluído','fa-fa-user');

-- Elétrica Tampa 17.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (25,'17.1 Aguardando Elétrica Tampa',0,0,10213);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (25,541,35,'Inicio Elétrica Tampa','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (25,551,36,'Pausa Elétrica Tampa','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (25,351,37,'Concluído','fa-fa-user');

-- Colagem Frame 17.2

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (26,'17.2 Aguardando Frame',0,0,10223);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (26,571,35,'Inicio Frame','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (26,591,36,'Pausa Frame','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (26,351,37,'Concluído','fa-fa-user');

-- Logística 2 Alimentação Materiais para Montagem 18.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (27,'18.1 Aguardando Logistica Alimentação Materiais Montagem',0,0,10404);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (27,611,35,'Inicio Logistica Alimentação Materiais Montagem','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (27,351,37,'Concluído','fa-fa-user');

-- Produção dia 03

-- Couro 19.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (28,'19.1 Aguardando Couro',0,0,10219);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (28,161,35,'Inicio Couro','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (28,351,36,'Pausa Couro','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (28,21,37,'Concluído','fa-fa-user');

-- Vidros Fixos 19.2

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (29,'19.2 Aguardando Vidros Fixos',0,0,10216);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (29,571,35,'Inicio Vidros Fixos','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (29,581,36,'Pausa Vidros Fixos','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (29,21,37,'Concluído','fa-fa-user');

-- Regulagem Porta 19.3

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (30,'19.3 Aguardando Regulagem Porta',0,0,10220);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (30,181,35,'Inicio Regulagem Porta','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (30,361,36,'Pausa Regulagem Porta','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (30,21,37,'Concluído','fa-fa-user');

-- Aço Vidro BiPartido Preparação 19.4

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (31,'19.4 Aguardando Aço Vidro Bi-Partido Preparação',0,0,10217);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (31,621,35,'Inicio Aço Vidro Bi-Partido','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (31,641,36,'Pausa Aço Vidro Bi-Partido','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (31,21,37,'Concluído','fa-fa-user');

-- Montagem 20.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (32,'20.1 Aguardando Montagem',0,0,10221);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (32,221,35,'Inicio Montagem','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (32,381,36,'Pausa Montagem','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (32,21,37,'Concluído','fa-fa-user');

-- Tapeçaria 20.2

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (33,'20.2 Aguardando Tapeçaria',0,0,10218);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (33,201,35,'Inicio Tapeçaria','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (33,671,36,'Pausa Tapeçaria','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (33,21,37,'Concluído','fa-fa-user');

-- Aço Vidro Bipartido Retirada/Entrega Pintura 20.3

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (34,'20.3 Aguardando Aço Vidro Bipartido Retirada/Entrega Pintura',0,0,10407);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (34,701,35,'Inicio Aço Vidro Bipartido Retirada/Entrega Pintura','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (34,711,36,'Pausa Aço Vidro Bipartido Retirada/Entrega Pintura','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (34,21,37,'Concluído','fa-fa-user');

-- Aço Vidro Bipartido Pintura 20.4

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (35,'20.4 Aguardando Aço Vidro Bipartido Pintura',0,0,10410);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (35,741,35,'Inicio Aço Vidro Bipartido Pintura','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (35,761,36,'Pausa Aço Vidro Bipartido Pintura','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (35,21,37,'Concluído','fa-fa-user');

-- Produção dia 04

-- Preparador de Forro 21.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (36,'21.1 Aguardando Preparador Forro',0,0,10350);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (36,11,35,'Inicio Preparador Forro','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (36,21,36,'Pausa Preparador Forro','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (36,301,37,'Concluído','fa-fa-user');

-- Instalação do Amortecedor de Tampa 21.2

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (37,'21.2 Aguardando Instalação do Amortecedor Tampa',0,0,10190);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (37,51,35,'Inicio Instalação do Amortecedor de Tampa','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (37,61,36,'Pausa Instalação do Amortecedor de Tampa','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (37,301,37,'Concluído','fa-fa-user');

-- Aço Vidro BiPartido Instalação 21.3

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (38,'21.3 Aguardando Aço Vidro BiPartido Instalação',0,0,10416);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (38,111,35,'Inicio Aço Vidro BiPartido Instalação','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (38,121,36,'Pausa Aço Vidro BiPartido Instalação','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (38,301,37,'Concluído','fa-fa-user');

-- Aço Retrovisor Interno 22.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (39,'22.1 Aguardando Aço Retrovisor Interno',0,0,10351);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (39,141,35,'Inicio Aço Retrovisor Interno','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (39,151,36,'Pausa Aço Retrovisor Interno','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (39,301,37,'Concluído','fa-fa-user');

-- Regulagem da Tampa 23.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (40,'23.1 Aguardando Regulagem Tampa',0,0,10413);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (40,81,35,'Inicio Regulagem Tampa','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (40,91,36,'Pausa Regulagem Tampa','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (40,301,37,'Concluído','fa-fa-user');

-- Produção dia 05

-- Elétrica Liberação 24.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (41,'24.1 Aguardando Elétrica Liberação',0,0,10224);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (41,411,35,'Inicio Elétrica Liberação','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (41,421,36,'Pausa Elétrica Liberação','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (41,301,37,'Concluído','fa-fa-user');

-- Inspeção 1.1 Funcional (Antiga Inspeçaõ 25.1)

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (42,'25.1 Aguardando Inspeção Funcional',0,0,10354);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (42,201,35,'Inicio Inspeção Funcional','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (42,211,36,'Pausa Inspeção Funcional','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (42,301,37,'Concluído','fa-fa-user');

-- Ajustes dos Veículos 25.2

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (43,'25.2 Aguardando Ajustes dos Veículos',0,0,10357);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (43,31,35,'Inicio Ajustes dos Veículos','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (43,151,36,'Pausa Ajustes dos Veículos','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (43,301,37,'Aguardando insulfilm','fa-fa-user');

-- Ajustes e Qualidade dia 06

-- Insulfilm 26.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (44,'26.1 Aguardando Insulfilm',1,0,10229);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (44,311,35,'Inicio Insulfilm','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (44,321,36,'Pausa Insulfilm','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (44,331,37,'Aguardando inspeção Funcional','fa-fa-user');

-- Inspeção 1.2 Funcional 27.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (45,'27.1 Aguardando Inspeção Funcional',1,0,10419);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (45,341,35,'Inicio Inspeção Funcional','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (45,351,36,'Pausa Inspeção Funcional','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (45,591,37,'Aguardando Estética Produção','fa-fa-user');

-- Estética Produção 1GW 28.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (46,'28.1 Aguardando Estética Produção 1-GW',1,0,10422);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (46,371,35,'Inicio Estética Produção','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (46,381,36,'Pausa Estética Produção','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (46,391,37,'Aguardando Inspeção Estética','fa-fa-user');

-- Ajustes e Qualidade dia 07

-- Inspeção 2.1 Estética (Antigo Checkout) 29.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (47,'29.1 Aguardando Inspeção Estética',1,0,10360);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (47,401,35,'Inicio Inspeção Estética','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (47,411,36,'Pausa Inspeção Estética','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (47,421,37,'Aguardando Pintura Reparos A1','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (47,691,37,'Aguardando Customer-Experience','fa-fa-user');

-- Pintura 1 - Reparos (A1) 30.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (48,'30.1 Aguardando Pintura-1 Reparos-A1',1,0,10427);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (48,431,35,'Inicio Pintura-1 Reparos-A1','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (48,441,36,'Pausa Pintura-1 Reparos-A1','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (48,701,37,'Aguardando Estética Produção-2 GW','fa-fa-user');

-- Ajustes e Qualidade dia 08

-- Estética Produção 2 (Repasse) GW 31.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (49,'31.1 Aguardando Estética Produção-2 GW',1,0,10359);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (49,461,35,'Inicio Estética Produção-2 GW','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (49,471,36,'Pausa Estética Produção-2 GW','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (49,711,37,'Aguardando Inspeção-2.2 Equipe Estética','fa-fa-user');

-- Inspeção 2.2 - Equipe de Estética (Double Check) 31.2

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (50,'31.2 Aguardando Inspeção-2.2 Equipe Estética',1,0,10430);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (50,491,35,'Inicio Inspeção-2.2 Equipe Estética','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (50,501,36,'Pausa Inspeção-2.2 Equipe Estética','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (50,691,37,'Aguardando Customer-Experience','fa-fa-user');

-- Customer Experience 32.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (51,'32.1 Aguardando Customer Experience',1,0,10362);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (51,521,35,'Inicio Customer Experience','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (51,531,36,'Pausa Customer Experience','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (51,541,37,'Aguardando Transfer Estacionamento','fa-fa-user');

-- Transfer Estacionamento (Equipe de Transfer) 32.2

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (52,'32.2 Aguardando Transfer Estacionamento',1,0,10363);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (52,521,35,'Inicio Transfer Estacionamento','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (52,61,37,'Aguardando Documentações','fa-fa-user');

-- Liberação e Entrega 

-- Estética Produção 3 (Repasse) GW 36.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (53,'36.1 Aguardando Estética Produção-3 GW',1,0,10380);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (53,121,35,'Inicio Estética Produção-3 GW','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (53,231,37,'Aguardando Checklist Saída Checkout Final','fa-fa-user');

-- Checklist de Saída Checkout Final (Entrega) 37.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (54,'37.1 Aguardando Checklist de Saída Checkout Final',1,0,10381);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (54,151,35,'Inicio Checklist de Saída Checkout Final','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (54,191,36,'Pausa Checklist de Saída Checkout Final','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (54,241,37,'Aguardando Logística','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (54,161,37,'Aguardando Ajustes Extraordinários','fa-fa-user');

-- Equipe Transfer Entrega Veículo (Logística) 39.1

INSERT INTO `stage` (`stg_id`,`name`,`task`,`special`,`status_jira_id`)
VALUES (55,'39.1 Aguardando Equipe Transfer Entrega Veículo',1,0,10456);

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (55,151,35,'Inicio Equipe Transfer Entrega Veículo','fa-fa-user');

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (55,261,37,'Aguardando LEMAK','fa-fa-user');

-- LEMAK Entrega Veículo (Destino) 40.1

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (56,311,37,'40.1 Entrega Logística Carbon','fa-fa-user');

-- Entrega Veículo 44.1

INSERT INTO `movement_stage` (`stg_id`,`jira_id`,`type_id`,`name`,`icon`)
VALUES (57,321,37,'44.1 Entrega Veículo','fa-fa-user');