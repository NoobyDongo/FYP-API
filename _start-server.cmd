@echo off
set /p sqlpath=<sqlpath.txt
cd main

if "%sqlpath%"=="" (
    echo Error: Failed to read path from sqlpath.txt
    pause
    exit /b
)

start /B "" "%sqlpath%\mysql_start.bat"

if "%1"=="prod" (
    GOTO PROD
) else if "%1"=="dev" (
    GOTO DEV
) else (
    echo Error: Invalid argument. Please use "prod" or "dev".
    pause
    exit /b
)

:PROD
echo Starting API Server (production)
call java -jar build/libs/fyp-0.1.0.jar
GOTO END

:DEV
echo Starting API Server (development)
call ./gradlew bootRun
GOTO END

:END
call "%sqlpath%\mysql_stop.bat"
pause