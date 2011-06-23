CP = src/

NULL =

SOURCES = \
  $(CP)toSync.java \
  $(CP)Code.java \
  $(CP)CoupleFiles.java \
  $(NULL)

.PHONY: clean all

all: toSync.class toSync.jar clean
	
toSync.class: $(SOURCES)
	javac $(CP)*.java
	
toSync.jar: toSync.class
	jar cfm toSync.jar MANIFEST.MF -C $(CP) .
	
run:
	java -jar toSync.jar
	
clean:
	rm $(CP)*.class
