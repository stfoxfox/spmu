create table SPMU_APPLICATION_RESPONSIBLE (
    ID uniqueidentifier,
    --
    APPLICATION_ID uniqueidentifier,
    EMPLOYEE_ID uniqueidentifier,
    ROLE varchar(50),
    --
    primary key nonclustered (ID)
);