choco install jdk8 -y || goto :error
choco install ant -y || goto :error

netsh advfirewall firewall add rule name="Java" dir=in action=allow program="c:\program files (x86)\java\jre1.8.0_101\bin\javaw.exe" enable=yes || goto :error

:error
exit /b %errorlevel%
