# COMP2521 Assignment 2 searchEngine

CC=gcc
CFLAGS=-Wall -g -lm -std=c11
BINS=testReadData inverted pagerank searchPagerank searchTfIdf

all: $(BINS)

testReadData : testReadData.o readData.o graph.o BSTree.o list.o
	$(CC) -o testReadData testReadData.o readData.o graph.o BSTree.o list.o

inverted : inverted.o readData.o graph.o BSTree.o list.o
	$(CC) -o inverted inverted.o readData.o graph.o BSTree.o list.o

pagerank : pagerank.o readData.o graph.o BSTree.o list.o
	$(CC) -o pagerank pagerank.o readData.o graph.o BSTree.o list.o

searchPagerank : searchPagerank.o readData.o graph.o BSTree.o list.o
	$(CC) -o searchPagerank searchPagerank.o readData.o graph.o BSTree.o list.o

searchTfIdf : searchTfIdf.o readData.o graph.o BSTree.o list.o
	$(CC) -o searchTfIdf searchTfIdf.o readData.o graph.o BSTree.o list.o

list.o : list.h list.c

BSTree.o : BSTree.h BSTree.c list.h

graph.o : graph.h graph.c readData.h list.h

readData.o : readData.c readData.h graph.h BSTree.h list.h

searchPagerank.o : searchPagerank.c readData.h list.h

searchTfIdf.o : searchTfIdf.c readData.h graph.h BSTree.h list.h

inverted.o : inverted.c readData.h graph.h BSTree.h list.h

pagerank.o : pagerank.c readData.h graph.h list.h

testReadData.o : testReadData.c readData.h graph.h BSTree.h list.h

clean:
	rm -f $(BINS) *.o core
