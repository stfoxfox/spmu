update SPMU_PLAN_VARIANT_TEMPLATE set ACTIVE = 0 where ACTIVE is null ;
alter table SPMU_PLAN_VARIANT_TEMPLATE alter column ACTIVE tinyint not null ;
