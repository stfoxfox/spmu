create table SPMU_CURRENCY_RATE (
    ID uniqueidentifier,
    --
    CODE varchar(3) not null,
    DATE datetime2 not null,
    RATE double precision not null,
    --
    primary key nonclustered (ID)
);