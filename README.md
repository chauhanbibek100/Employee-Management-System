cd    main file like if if your folder name is Project then ->    cd project

cmd:   Get-ChildItem src\main\java -Filter "*.java" -Recurse | ForEach-Object { '"' + $_.FullName.Replace('\', '/') + '"' } | Out-File -Encoding ascii sources.txt; javac -cp "lib/*" "@sources.txt"; if ($?) { java -cp "lib/*;src/main/java" com.ems.Main }
