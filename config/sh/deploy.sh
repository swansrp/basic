basepath=$(cd "$(dirname "$0")"; pwd)  

RestartCmd='dls/dls.sh restart'
StatusCmd='dls/dls.sh status'
TraceCmd='tail -f dls/spring.log'
APICmd='tail -f log/sys.log'
ServerNum=5

DevPath=$basepath/LockScreenPortal/target/LockScreenPortal-0.0.1-SNAPSHOT.jar
DevTargetPath="~/dls/LockScreenPortal-0.0.1-SNAPSHOT.jar"
DevSSH=dev

PlanPath=$basepath/LockScreenPlan/target/LockScreenPlan-0.0.1-SNAPSHOT-exec.jar
PlanTargetPath="~/dls/LockScreenPlan-0.0.1-SNAPSHOT.jar"
PlanSSH=dls_stg_plan

ReportPath=$basepath/LockScreenReport/target/LockScreenReport-0.0.1-SNAPSHOT-exec.jar
ReportTargetPath="~/dls/LockScreenReport-0.0.1-SNAPSHOT.jar"
ReportSSH=dls_stg_report

WorkerPath=$basepath/LockScreenReportWorker/target/LockScreenReportWorker-0.0.1-SNAPSHOT-exec.jar
WorkerTargetPath="~/dls/LockScreenReportWorker-0.0.1-SNAPSHOT.jar"
WorkerSSH=dls_stg_worker

PortalPath=$basepath/LockScreenPortal/target/LockScreenPortal-0.0.1-SNAPSHOT.jar
PortalTargetPath="~/dls/LockScreenPortal-0.0.1-SNAPSHOT.jar"
PortalSSH=dls_stg_portal

ServerPathArray=($DevPath $PortalPath $PlanPath $ReportPath $WorkerPath)
ServerTargetPath=($DevTargetPath $PortalTargetPath $PlanTargetPath $ReportTargetPath $WorkerTargetPath)
ServerSSH=($DevSSH $PortalSSH $PlanSSH $ReportSSH $WorkerSSH)


upload(){
    scp ${ServerPathArray[$1]} ${ServerSSH[$1]}:${ServerTargetPath[$1]}
	return 0
}
enter() {
	ssh ${ServerSSH[$1]}
	return 0
}
restart() {
	ssh ${ServerSSH[$1]} $RestartCmd
	return 0
}
trace() {
	ssh ${ServerSSH[$1]} $TraceCmd
	return 0
}
api() {
	ssh ${ServerSSH[$1]} $APICmd
	return 0
}
deploy() {
	upload $1
	restart $1
	if test -z $2
	then
		trace $1
	fi
	
	return 0
}
status() {
	ssh ${ServerSSH[$1]} $StatusCmd
	return 0
}
allstatus() {
	echo ---------------------------
	for((i=1;i<$ServerNum;i++));
	do
	echo ${ServerSSH[$i]} 
	status i;
	echo ---------------------------
	done
	return 0
}

deployAll() {
	for((i=1;i<$ServerNum;i++));
	do
	echo "===== Start  to deploy: " ${ServerSSH[$i]} ...  ======
	deploy i "noTrace";
	echo "===== Finish to deploy: " ${ServerSSH[$i]} ...  ======
	done
	allstatus
	return 0
}



echo "┌------------------------------┐"
echo "|----0. DEV Server-------------|"
echo "|----1. Portal Server----------|"
echo "|----2. Plan Server------------|"
echo "|----3. Report Server----------|"
echo "|----4. Worker Server----------|"
echo "|----5. All deploy-------------|"
echo "|----6. Status-----------------|"
echo "└------------------------------┘"
echo ""
echo "input your option: "
read serverName

if test $serverName -eq 5
then
deployAll
elif test $serverName -eq 6
then 
allstatus
else

echo "┌-----------------------┐"
echo "|----1. Deploy----------|"
echo "|----2. Trace log-------|"
echo "|----3. API log---------|"
echo "|----4. Restart---------|"
echo "|----5. Enter-----------|"
echo "└-----------------------┘"
echo ""
echo "input your operation"
read option



case $option in
1)
	deploy $serverName
    ;;
2)
	trace $serverName
    ;;
3)	
	api $serverName
	;;
4)
	restart $serverName
    ;;
5)
	enter $serverName
    ;;
esac

fi



