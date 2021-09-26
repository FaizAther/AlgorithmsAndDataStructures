// Written by: Faiz Ather,    UNSW: z5170340
// Written By: Farhan Ghazi,  UNSW: z5199861

// Dated: 21/OCT/2018
// list.c - A Url List

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <assert.h>
#include <stdbool.h>
#include "list.h"

// Create a new URL Node
static urlNode *newUrlNode(char *urlString)
{
   urlNode *newNode;
   newNode = malloc(sizeof(urlNode));
   assert(newNode != NULL);
   newNode->urlStr = malloc(sizeof(char)*strlen(urlString));
   strcpy(newNode->urlStr, urlString);
   newNode->next = newNode->prev = NULL;
   newNode->count = 1;

   return newNode;
}

// Create New Empty URL List
urlList newUrlList(void)
{
   urlList newList;
   newList = malloc(sizeof(urlListRep));
   assert(newList != NULL);
   newList->nElem = 0;
   newList->head = newList->tail = newList->curr = NULL;

   return newList;
}

// Add URL to List
void insertUrlNode(urlList L, char *urlString, bool checkRepeated)
{
   if (checkRepeated && checkUrlList(L, urlString))
   {
      L->curr->count++;
      return;
   }
   urlNode *N = newUrlNode(urlString);

   // head NULL
   if (L->head == NULL)
   {
      L->head = L->tail = L->curr = N;
   }
   // Condition 1: Add at head
   else if ((strcmp(N->urlStr, L->head->urlStr) < 0))
   {
      N->next = L->head;
      L->head->prev = N;
      L->head = N;
   }
   // Condition 2: Add to tail
   else if ((strcmp(N->urlStr, L->head->urlStr) > 0))
   {
      L->tail->next = N;
      N->prev = L->tail;
      L->tail = N;
   }
   // Move to addition node add there
   else
   {
      for(L->curr = L->head;(L->curr != NULL); L->curr = L->curr->next)
      {
         // Do Noting Move curr along
         if (strcmp(N->urlStr, L->curr->urlStr) <= 0) break;
      }
      N->next = L->curr;
      N->prev = L->curr->prev;
      L->curr->prev->next = N;
      L->curr->prev = N;
   }
/*
   if (L->head == NULL)
   {
      L->head = L->tail = L->curr = N;
      L->nElem++;
      return;
   }
   L->tail->next = N;
   N->prev = L->tail;
   L->tail = N;
*/
   L->nElem++;
}

// Check if Url is in Linked List

bool checkUrlList(urlList L, char *urlCheck)
{
   if(L == NULL) return false;
   if (L->head == NULL) return false;
   for ( L->curr = L->head; L->curr != NULL; L->curr = L->curr->next )
   {
      if ( !strcmp(L->curr->urlStr, urlCheck) )
      {
         return true;
      }
   }

   return false;
}


// Display the list on STDIN
void showUrlList(urlList L)
{
   if (L == NULL) return;
   int i = 0;
   for(L->curr = L->head; L->curr != NULL; L->curr = L->curr->next)
   {
      printf("%d: %s:%d/%lf ", ++i, L->curr->urlStr, L->curr->count, L->curr->total);
   }

   printf("%d Item's\n", L->nElem);
   L->curr = L->head;
}

// Free URL List
void freeUrlList(urlList L)
{
   if (L == NULL) return;
   urlNode *curr, *prev;
   curr = L->head;
   while (curr != NULL)
   {
      prev = curr;
      curr = curr->next;
      free(prev->urlStr);
      free(prev);
   }
   free(L);
}

// Returns a string of each node: Remember to free
char *listToStr(urlList L)
{
   char *s = malloc(sizeof(char)*BUFSIZ);
   memset(s, '\0', BUFSIZ);
   for (L->curr = L->head; L->curr != NULL; L->curr = L->curr->next)
   {
      strcat(s, L->curr->urlStr);
      strcat(s, " ");
   }
   return s;
}

// Delete URl from List
void deleteUrl(urlList L, char *urlS)
{
   if (L == NULL) return;
   urlNode *tmp;
   for(L->curr = L->head; L->curr != NULL; L->curr = L->curr->next)
   {
      if ( !strcmp(L->curr->urlStr, urlS) )
      {
         if (L->curr == L->head)
         {
            tmp = L->head;
            L->head = L->head->next;
            L->head->prev = NULL;
         } else if ( L->curr == L->tail )
         {
            tmp = L->tail;
            L->tail = L->tail->prev;
            L->tail->next = NULL;
         } else
         {
            tmp = L->curr;
            L->curr->prev->next = L->curr->next;
            L->curr->next->prev = L->curr->prev;
         }
      }
   }
   free(tmp->urlStr);
   free(tmp);
}
