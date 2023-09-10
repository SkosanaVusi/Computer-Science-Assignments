# binary search program makefile
# Vusi Skosana
# 26 April 2021

JAVAC =/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin


$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<
	
CLASSES = BinaryTreeNode.class BTQueueNode.class BTQueue.class BinaryTree.class Students.class AVLTree.class AccessAVLApp.class

CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)
default:$(CLASS_FILES)

clean:
	rm $(SRCDIR)/*.class
	rm $(BINDIR)/*.class


runalv: $(CLASS_FILES)
	java -cp $(BINDIR) AccessAVLApp

doc:
	javadoc -d doc src/*.java






