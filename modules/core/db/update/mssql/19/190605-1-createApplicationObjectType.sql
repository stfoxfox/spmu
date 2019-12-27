create table SPMU_APPLICATION_OBJECT_TYPE (
    ID uniqueidentifier,
    --
    PARENT_ID uniqueidentifier,
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
);