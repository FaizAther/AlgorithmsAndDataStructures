// Written by: Faiz Ather,    UNSW: z5170340
// Written By: Farhan Ghazi,  UNSW: z5199861

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>
#include "list.h"
#include "graph.h"
#include "readData.h"

void setArgs(int argc, char **argv, double *DF, double *diffPR, double *maxIteration);

int main(int argc, char **argv)
{
   double DF, diffPR, maxIteration;
   // Check args
   setArgs(argc, argv, &DF, &diffPR, &maxIteration);
   // Read collectios
   urlList L = GetCollection();
   Graph G = newGraph(L);
   FILE *fp = Fopen("pagerankList.txt", "w+");
   PageRankW(G ,DF ,diffPR , maxIteration);
   showPageRanks(fp, G);
   showGraph(G);
   showUrlList(L);
   freeGraph(G);
   freeUrlList(L);
   fclose(fp);

   return 0;
}


void setArgs(int argc, char **argv, double *DF, double *diffPR, double *maxIteration)
{
   if (argc == 4)
   {
      sscanf(argv[1], "%lf", DF);
      sscanf(argv[2], "%lf", diffPR);
      sscanf(argv[3], "%lf", maxIteration);
      return;
   }
   fprintf(stderr, "%s", "Usage: ./pagerank arg1 arg2 arg3\n");
   abort();
}
