// Written by: Faiz Ather,    UNSW: z5170340

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "list.h"
#include "readData.h"

typedef struct _pageData{
   char     *urlStr;
   int       outLinks;
   double    pr;
   struct _pageData *next;
} pageData;

typedef pageData *pD;

int main(int argc, char **argv)
{
   urlList wordArr[BUFSIZ];
   int i = 0, j, k, l;
   while(i < BUFSIZ)
   {
      wordArr[i] = NULL;
      i++;
   }
   FILE *fp = Fopen("invertedIndex.txt", "r");
   char line[BUFSIZ];
   memset(line, '\0', BUFSIZ);
   char word[30];
   memset(word, '\0', 30);
   i = 0, j = 0, k = 0;
   //char *l = line, *w = word;
   while(fgets(line, BUFSIZ, fp))
   {
      k = j = 0;
      wordArr[i] = newUrlList();
      while (line[j] != '\n')
      {
         while(line[j] != ' ')
         {
            word[k] = line[j];
            j++;
            k++;
         }
         word[k] = '\0';
         if(k != 0)insertUrlNode(wordArr[i], word, true);
         k = 0;
         memset(word, '\0', 30);
         j++;
      }
      i++;
   }
   i = 0;
   while(wordArr[i] != NULL)
   {

      showUrlList(wordArr[i]);
      i++;

   }
   fclose(fp);

   FILE *fpp = Fopen("pagerankListReal.txt", "r");

   pD Map = malloc(sizeof(struct _pageData));


   char linee[BUFSIZ];
   memset(linee, '\0', BUFSIZ);
   char wordd[30];
   memset(wordd, '\0', 30);
   i = 0, j = 0, k = 0, l = 0;
   int stop = 0;
   //char *l = line, *w = word;
   double d;

   while(fgets(linee, BUFSIZ, fpp))
   {
      sscanf(linee, "%s, %d, %lf", wordd, &l, &d);
      printf("%s, %d, %lf", wordd, l, d);
      memset(word, '\0', 30);
      l = 0;
      d = 0.0;

   }

   fclose(fpp);

   return 0;
}

/*
void insertLine(pN map, char *word, int l){



k = j = 0;
while (linee[j] != '\n')
{
   while(stop == 0)
   {
      wordd[k] = linee[j];
      j++;
      k++;
      if (linee[j] == ',' || linee[j] == ' ' || linee[j] == '\n') stop = 1;
   }
   stop = 0;
   wordd[k] = '\0';
   if(k != 0)  {
      printf("%s\n", wordd);
      l++;
      if(l == 3)  l = 0;
   }
   k = 0;
   memset(wordd, '\0', 30);
   j++;
}
i++;
}
i = 0;
}
*/
