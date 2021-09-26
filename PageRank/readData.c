// Written by: Faiz Ather, UNSW: z5170340
// Dated: 21/OCT/2018
// readData.c - Uses Doubly Linked List Structure to read from collections.txt

#include <assert.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>
#include <ctype.h>
#include "graph.h"
#include "readData.h"


// Read From collections.txt
urlList GetCollection(void)
{
   return readUrlFile("collection");
}

// Creates A Graph from list of Url's
Graph GetGraph(urlList L)
{
   return newGraph(L);
}

// Creates a tree with the given List made from collection.txt
Tree GetInvertedList(urlList L)
{
   Tree T = newTree();
   urlList wordsL = NULL;
   for(L->curr = L->head; L->curr != NULL; L->curr = L->curr->next)
   {
      wordsL = readUrlWords(L->curr->urlStr);
      for(wordsL->curr = wordsL->head; wordsL->curr != NULL; wordsL->curr = wordsL->curr->next)
      {
         T = TreeInsert(T, wordsL->curr, L->curr->urlStr);
      }
      freeUrlList(wordsL);
   }
   return T;
}

urlList readUrlWords(char *urlFile)
{
      return readFile(urlFile, "Section-2");
}

// Read URl's from text files return list
urlList readUrlFile(char *urlFile)
{
   return readFile(urlFile, "Section-1");
}

urlList readFile(char *urlFile, char *section)
{
   char *filename = addPath(urlFile, "");
   // Open File: For Reading
   FILE *fp = Fopen(filename, "r");

   urlList L = convertToList(fp, urlFile, section);

   fclose(fp);
   free(filename);
   return L;
}

// Helper Functions

// Adds required path & .txt
char *addPath(char *urlFile, char *path)
{
   char *filename = malloc((strlen(urlFile) + strlen(".txt") + strlen(path) + 1) * sizeof(char));
   strcpy(filename, path);
   strcat(filename , urlFile);
   strcat(filename , ".txt");
   return filename;
}

// fopen with errer check
FILE *Fopen(char *filename, char *openMode)
{
   FILE *fp = fopen(filename, openMode);
   if (fp == NULL) perror("File Open Error!");
   assert(fp != NULL);
   return fp;
}


// Create List of tokens: two modes: Section 1, Section 2
urlList convertToList(FILE *fp, char *urlFile, char *mode)
{
   // Make new List
   urlList L = newUrlList();
   assert(L != NULL);

   // Span through textfile in search of URL's
   char toks[BUFSIZ];
   int i;
   for(i = 0; i < BUFSIZ; i++)
   {
      toks[i] = '\0';
   }
   // Case 1: collections.txt
   // Case 2: <url>.txt
   bool passed = false;
   bool checkRepeated = true;
   while( fscanf(fp, "%s", toks) != EOF )
   {
      if ( !strcmp(mode, "Section-2") && passed == false )
      {
         while(strcmp(toks, "Section-2") != 0){
            fscanf(fp, "%s", toks);
         }
         passed = true;
         checkRepeated = true;
      }
      if ( !strcmp(toks, "#start") || !strcmp(toks, mode) ) continue;
      else if ( !strcmp(toks, "#end") ) break;
      else if (strstr(urlFile, toks) == NULL)
      {
         if (!strcmp(mode, "Section-2")) fixWord(toks);
         insertUrlNode(L, toks, checkRepeated);
      }
   }
   return L;
}

// Converts to Lowercase and removes non alpha characters
void fixWord(char *word)
{
   char ch;

   for( ch = *word; ch != '\0'; ch = *word )
   {
      if ( !isalpha(ch) ) *word = '\0';
      else if( isupper(ch) ) *word = ch + ('a' - 'A');
      word += sizeof(char);
   }

}


void findWord(urlList L, char *urlCheck)
{
   if(L == NULL) return;
   if (L->head == NULL) return;

   for ( L->curr = L->head; L->curr != NULL; L->curr = L->curr->next )
   {
      L->curr->count = 0;
   }
   for ( L->curr = L->head; L->curr != NULL; L->curr = L->curr->next )
   {
      urlList LL = readUrlWords(L->curr->urlStr);
      L->curr->total = (double)LL->nElem;
      for ( LL->curr = LL->head; LL->curr != NULL; LL->curr = LL->curr->next )
      {
         if ( !strcmp(LL->curr->urlStr, urlCheck) )
         {
            L->curr->count = LL->curr->count;
            L->curr->total = ((double)L->curr->count/L->curr->total);
         }
      }
      freeUrlList(LL);
   }

}
