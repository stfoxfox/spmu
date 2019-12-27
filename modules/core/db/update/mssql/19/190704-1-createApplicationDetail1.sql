create table SPMU_APPLICATION_DETAIL_1 (
    ID uniqueidentifier,
    --
    APPLICATION_ID uniqueidentifier not null,
    TYPE_ID uniqueidentifier not null,
    VALUE1 varchar(255),
    VALUE2 varchar(255),
    VALUE3 varchar(255),
    --
    primary key nonclustered (ID)
);