@echo off
setlocal EnableDelayedExpansion

set "status=(building...)"
title API Server !status!

cd main
call ./gradlew build

set "status=(building completed)"
echo !status!>temp.txt

for /F "usebackq delims=" %%G in ("temp.txt") do (
    title API Server %%G
)

del temp.txt

pause