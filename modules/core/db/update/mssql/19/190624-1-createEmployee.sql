create table SPMU_EMPLOYEE (
    ID uniqueidentifier,
    --
    MANDATE_ID uniqueidentifier not null,
    FIRSTNAME varchar(255) not null,
    SECONDNAME varchar(255) not null,
    LASTNAME varchar(255) not null,
    GENDER varchar(255),
    BITHDATE datetime2,
    --
    primary key nonclustered (ID)
);