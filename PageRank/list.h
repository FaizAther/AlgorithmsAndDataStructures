// Written by: Faiz Ather,    UNSW: z5170340
// Written By: Farhan Ghazi,  UNSW: z5199861
// Dated: 21/OCT/2018
// list.h - A Url List

#ifndef LIST_H
#define LIST_H

#include <stdbool.h>

// External URL List
// Implementaion in readData.c
// Implements a URL List of strings

typedef struct _urlNode {
   char   *urlStr;
   struct _urlNode *next;
   struct _urlNode *prev;
   int     count;
   double     total;
} urlNode;


typedef struct _urlListRep {
   urlNode *head;
   urlNode *tail;
   urlNode *curr;
   int      nElem;
} urlListRep;


typedef struct _urlListRep *urlList;
typedef struct _urlNode    *urlNodee;

// Checks if Url is in list
bool checkUrlList(urlList L, char *urlCheck);

// Displays whats in the linked list
void showUrlList(urlList L);

// Frees memory assossiated with Url List
void freeUrlList(urlList L);

// Deletes Url from List
void deleteUrl(urlList L, char *urlS);

// Returns a string of each node: Remember to free
char *listToStr(urlList L);

bool checkUrlList(urlList L, char *urlCheck);
void insertUrlNode(urlList L, char *urlString, bool checkRepeated);
urlList newUrlList(void);


#endif
