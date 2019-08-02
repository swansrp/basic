echo input src db name:
read srcDbName
echo input dst db name:
read dstDbName
mysqldump -udbAdmin -pAdm\!n123 $srcDbName>db.sql
mysql -udbAdmin -pAdm\!n123 $dstDbName<db.sql
rm -rf db.sql
