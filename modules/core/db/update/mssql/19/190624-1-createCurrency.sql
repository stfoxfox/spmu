create table SPMU_CURRENCY (
    ID uniqueidentifier,
    --
    CODE varchar(3) not null,
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
);