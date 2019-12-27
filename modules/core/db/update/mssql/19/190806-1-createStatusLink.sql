create table SPMU_STATUS_LINK (
    ID uniqueidentifier,
    --
    STATUS varchar(50),
    CODE varchar(255),
    --
    primary key nonclustered (ID)
);