@ECHO OFF

cd /d %~dp0
cd ..
set CLASSPATH=.;build\classes;lib\javacsv-2.1.jar;lib\commons-cli-1.2.jar
java civsim.Civsim %*
