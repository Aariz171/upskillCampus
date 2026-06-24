#!/bin/bash
echo "Compiling HRMS..."
mkdir -p out
find src -name "*.java" | xargs javac -d out
if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Starting HRMS..."
    java -cp out com.hrms.main.Main
else
    echo "Compilation failed. Check errors above."
fi
