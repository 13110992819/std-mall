部署步骤：
1，eclipse导出war包

2. 本地war包上传至服务器
   scp -P 22 std-mall.war root@121.43.101.148:/home
   cdkj123456Q

3. 备份原先配置(如果第一次部署，跳过此步骤)
  ssh root@121.43.101.148 -p 22
  cdkj123456Q

  cd /mnt/www/hw/tomcat_std_mall/webapps
  cp ./std-mall/WEB-INF/classes/application.properties .
  cp ./std-mall/WEB-INF/classes/config.properties .
  rm -rf std-mall*
  mv /home/std-mall.war .
   
4. 已备份配置文件放回原处,重启tomcat
  mv -f application.properties ./std-mall/WEB-INF/classes/
  mv -f config.properties ./std-mall/WEB-INF/classes/
  
  ../bin/shutdown.sh
  ../bin/startup.sh
  
6. 验证程序
  http://121.43.101.148:3804/std-mall/api

