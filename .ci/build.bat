cd c:\vagrant\ARE
ant || goto :error

:error
exit /b %errorlevel%
