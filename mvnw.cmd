@ECHO OFF
SETLOCAL
set WRAPPER_JAR=.mvn\wrapper\maven-wrapper.jar
IF NOT EXIST "%WRAPPER_JAR%" (
  ECHO Wrapper JAR not found: %WRAPPER_JAR%
  EXIT /B 1
)
REM Si JAVA_HOME no está, intenta usar 'java' del PATH
IF EXIST "%JAVA_HOME%\bin\java.exe" (
  "%JAVA_HOME%\bin\java.exe" -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
) ELSE (
  java -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
)
ENDLOCAL
