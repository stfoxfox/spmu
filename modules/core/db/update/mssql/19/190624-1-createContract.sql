create table SPMU_CONTRACT (
    ID uniqueidentifier,
    --
    CODE varchar(32),
    NAME varchar(255),
    START_ datetime2,
    END_ datetime2,
    --
    primary key nonclustered (ID)
);