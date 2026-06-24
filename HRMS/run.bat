@echo off
echo Compiling HRMS...
if not exist out mkdir out
for /r src %%f in (*.java) do javac -d out "%%f"
if %errorlevel% == 0 (
    echo Compilation successful!
    echo Starting HRMS...
    java -cp out com.hrms.main.Main
) else (
    echo Compilation failed. Check errors above.
)
pause
