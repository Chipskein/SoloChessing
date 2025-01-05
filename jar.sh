javac -d out -sourcepath . Main.java MainTestes.java Game/*.java Game/Peca/*.java
rm MANIFEST.MF
echo "Manifest-Version: 1.0" >> MANIFEST.MF
echo "Main-Class: Main" >> MANIFEST.MF
jar cfm Game.jar MANIFEST.MF -C out . -C Resources .