#!/bin/bash
###############################
#FileName:backupLogCron.sh
#Function:定时备份日志文件
#Version:0.1
#Authon:ruopeng.sha
#Date:2018.08.27
###############################
currentTime=`date +%Y-%m-%d`
#######  AWS CONFIGURE  #######
access_key_id="AKIAORDSZNPVMXLHYJLA"
secret_access_key="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"
default_region_name="cn-north-1"
s3_path="s3://rewardsfrontdev/apiPoint/"
###############################
#获取当前路径
path="/home/reward/log/apipoint/"

fileList=`find ${path} -name *${currentTime}*`

#echo ${fileList[@]}

tarFileName="/home/reward/log/apipoint/apipoint_${currentTime}.tar.gz"
sudo tar -zcvf ${tarFileName} ${fileList} --remove-files

aws configure set aws_access_key_id ${access_key_id}
aws configure set aws_secret_access_key ${secret_access_key}
aws configure set default.region ${default_region_name}

aws s3 cp ${tarFileName} ${s3_path}
sudo rm -rf ${tarFileName}
