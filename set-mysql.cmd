rem Playing with ChatGPT
@echo off

setlocal

rem Define the XAMPP installation directory
set "xamppDir=C:\xampp"

rem Define the MySQL directory within XAMPP
set "mysqlDir=%xamppDir%\mysql"

rem Find the my.ini file within the MySQL directory and store its path
set "myIniPath="
for /r "%mysqlDir%" %%F in (my.ini) do (
    set "myIniPath=%%F"
)

rem Check if the my.ini file exists
if not exist "%myIniPath%" (
    echo The my.ini file does not exist.
    pause
    exit /b
)

rem Overwrite the max_allowed_packet setting in the my.ini file
echo Overwriting the max_allowed_packet setting...
powershell -Command "(Get-Content -Path '%myIniPath%') -replace 'max_allowed_packet\s*=\s*\d+M', 'max_allowed_packet = 500M' | Set-Content -Path '%myIniPath%'"

echo The max_allowed_packet setting has been overwritten to 500M in the my.ini file.

pause
endlocal