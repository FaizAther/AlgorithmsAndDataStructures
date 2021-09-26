
#include <stdio.h>
#include <stdlib.h>
#include "readData.h"
#include "BSTree.h"

void iterateInvertedList(Tree t, int depth, FILE *fp);

int main()
{
   urlList L = GetCollection();
   Tree T = GetInvertedList(L);
   FILE *fp = Fopen("invertedIndex.txt", "w");
   iterateInvertedList(T, 0, fp);
   freeTree(T);
   fclose(fp);
   freeUrlList(L);
   return 0;
}


void iterateInvertedList(Tree t, int depth, FILE *fp)
{
   if (t != NULL) {
      iterateInvertedList(left(t), depth+1, fp);
      char *sLinks = listToStr(t->urlLinks);
      fprintf(fp, "%s  %s\n", data(t), sLinks);
      free(sLinks);
      iterateInvertedList(right(t), depth+1, fp);
   }
}
