create table SPMU_LOCATION (
    ID uniqueidentifier,
    --
    CODE varchar(32) not null,
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
);