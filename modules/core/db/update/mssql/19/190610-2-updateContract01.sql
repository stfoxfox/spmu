exec sp_rename 'SPMU_CONTRACT.END_', 'END___U41346', 'COLUMN' ^
alter table SPMU_CONTRACT add END_ datetime2 ;
