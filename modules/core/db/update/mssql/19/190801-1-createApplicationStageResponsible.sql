create table SPMU_APPLICATION_STAGE_RESPONSIBLE (
    ID uniqueidentifier,
    ROLE varchar(31),
    --
    APPLICATIONSTAGE_ID uniqueidentifier,
    EMPLOYEE_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
);