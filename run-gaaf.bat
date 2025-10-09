@echo off
setlocal
rem --- JavaFX (win) ---
set FX=%~dp0javafx-controls-21.0.2-win.jar;%~dp0javafx-graphics-21.0.2-win.jar;%~dp0javafx-base-21.0.2-win.jar

rem --- tu JAR + dependencias ---
for %%F in ("%~dp0target\gaaf-desktop-*.jar") do set APPJAR=%%~fF
set CP=%APPJAR%;%~dp0mysql-connector-j-8.0.33.jar;%~dp0protobuf-java-3.21.9.jar

rem --- ejecutar ---
java --module-path "%FX%" --add-modules javafx.controls,javafx.graphics,javafx.base -cp "%CP%" com.gaaf.App
endlocal
