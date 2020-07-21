create table if not exists classification (
    id uuid not null,
     created_at timestamp,
     description varchar(255),
     name varchar(255),
     updated_at timestamp,
     classification_result_id int8,
     image_metadata_id int8,
     primary key (id)
);

create table if not exists classification_result (
    id  bigserial not null,
     status varchar(255),
     primary key (id)
 );

create table if not exists classification_tag (
    id  bigserial not null,
     confidence float8,
     name varchar(255),
     classification_result_id int8,
     primary key (id)
 );

create table if not exists image_metadata (
    id  bigserial not null,
     format varchar(255),
     name varchar(255),
     size int8,
     url varchar(255),
     primary key (id)
 );

 create table if not exists outbox (
    id uuid not null,
     aggregate_type varchar(255) not null,
     aggregate_id uuid not null,
     type varchar(255) not null,
     timestamp timestamp not null,
     payload varchar(8000),
     primary key (id)
 );

--alter table classification
--        add constraint FK3mhjfcus7e94hrf3i6srs2pn8
--        foreign key (classification_result_id)
--        references classification_result;
--
-- alter table classification
--        add constraint FKh6jhrpqd45g3lid9dpj2e3sit
--        foreign key (image_metadata_id)
--        references image_metadata;
--
-- alter table classification_tag
--        add constraint FKhglcfi4av9s7qyec2isr5dg4f
--        foreign key (classification_result_id)
--        references classification_result;


INSERT INTO outbox (id,aggregate_type,aggregate_id,"type","timestamp",payload) VALUES
('81d5a712-6569-4141-b2ac-fe2f4467fd1c','Classification','8443a2b1-d27d-423c-9ac2-ed63c0891b12','ClassificationCreated','2020-07-21 19:12:07.851','{"id":"8443a2b1-d27d-423c-9ac2-ed63c0891b12","url":"https://quarkus-hackaton.s3.eu-west-1.amazonaws.com/8443a2b1-d27d-423c-9ac2-ed63c0891b12"}')
;

INSERT INTO image_metadata (format,name,"size",url) VALUES
('image/jpeg','0.jpg',8462,'https://quarkus-hackaton.s3.eu-west-1.amazonaws.com/8443a2b1-d27d-423c-9ac2-ed63c0891b12')
;

INSERT INTO classification_result (id, status) VALUES
(1, 'ACTIVE')
;

INSERT INTO classification_tag (confidence,name,classification_result_id) VALUES
(59.8976135253906,'area',1)
,(50.0617027282715,'structure',1)
,(18.8024425506592,'history',1)
,(14.4672164916992,'roof',1)
,(12.8294620513916,'tourist',1)
,(8.47606754302979,'historical',1)
;

INSERT INTO classification (id,created_at,description,name,updated_at,classification_result_id,image_metadata_id) VALUES
('8443a2b1-d27d-423c-9ac2-ed63c0891b12','2020-07-21 19:12:07.795','','Classificação #2','2020-07-21 19:12:08.892',1,1)
;


