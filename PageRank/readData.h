// Written by: Faiz Ather,    UNSW: z5170340
// Written By: Farhan Ghazi,  UNSW: z5199861
// Dated: 21/OCT/2018
// readData.h - Interface to read from URL text files

#ifndef READDATA_H
#define READDATA_H

#include "list.h"
#include "graph.h"
#include "BSTree.h"
// External URL List
// Implementaion in readData.c
// Implements a URL List of strings

// Reads the collections.txt and return list of URL's
urlList GetCollection(void);

// Creates A Graph from list of Url's
Graph GetGraph(urlList L);

// Creates a tree with the given List made from collection.txt
Tree GetInvertedList(urlList L);

// Reads Words from file
urlList readUrlWords(char *urlFile);

// Read URl's from text files return list
urlList readUrlFile(char *UrlFile);

// Reads words from section specified
urlList readFile(char *urlFile, char *section);

// Create List of tokens: two modes: Section 1, Section 2
urlList convertToList(FILE *fp, char *urlFile, char *mode);

// Adds required path & .txt
char *addPath(char *urlFile, char *path);

// fopen with errer check
FILE *Fopen(char *filename, char *openMode);

// Converts to Lowercase and removes non alpha characters
void fixWord(char *word);

void findWord(urlList L, char *urlCheck);

#endif
