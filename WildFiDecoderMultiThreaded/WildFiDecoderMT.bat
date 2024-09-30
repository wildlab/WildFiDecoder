@echo off
rem Get total system memory in MB
for /f "tokens=2 delims==" %%A in ('wmic OS get TotalVisibleMemorySize /Value') do set TotalMemory=%%A

rem Convert total memory from KB to MB
set /A TotalMemoryMB=%TotalMemory% / 1024

rem Calculate 50% of total memory
set /A HeapMemoryMB=%TotalMemoryMB% * 50 / 100

set currentDir=%~dp0
set jarFile=%currentDir%\WildFiDecoderStandalone.jar

java -Xms2g -Xmx%HeapMemoryMB%m -XX:+UseG1GC -XX:ParallelGCThreads=4 -XX:ConcGCThreads=2 -XX:+UseAdaptiveSizePolicy -XX:MaxGCPauseMillis=200 -Djava.util.concurrent.ForkJoinPool.common.parallelism=8 -jar %jarFile%