alter table SPMU_PLAN_ARTICLE add CODE varchar(255) ^
update SPMU_PLAN_ARTICLE set CODE = '' where CODE is null ;
alter table SPMU_PLAN_ARTICLE alter column CODE varchar(255) not null ;
