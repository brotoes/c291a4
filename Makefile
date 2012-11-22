all:
	javac -Xlint *.java

db:
	javac -Xlint SongDatabase.java Entry.java

clean:
	rm *.db *.class

config:
	CLASSPATH=$$CLASSPATH\:.\:/usr/share/java/db.jar:.
	export CLASSPATH
	LD_LIBRARY_PATH=/oracle/lib
	export LD_LIBRARY_PATH

.PHONY: config all db clean
