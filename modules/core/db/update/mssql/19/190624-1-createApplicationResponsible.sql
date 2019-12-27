create table SPMU_APPLICATION_RESPONSIBLE (
    ID uniqueidentifier,
    DTYPE varchar(100),
    --
    APPLICATION_ID uniqueidentifier,
    EMPLOYEE_ID uniqueidentifier,
    ROLE varchar(50),
    --
    primary key nonclustered (ID)
);