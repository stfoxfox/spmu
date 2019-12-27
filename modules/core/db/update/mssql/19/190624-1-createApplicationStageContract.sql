create table SPMU_APPLICATION_STAGE_CONTRACT (
    ID uniqueidentifier,
    --
    STAGE_ID uniqueidentifier,
    CONTRACT_ID uniqueidentifier not null,
    AMOUNT double precision,
    ACCRUED double precision,
    PAID double precision,
    COMPENSATED double precision,
    --
    primary key nonclustered (ID)
);