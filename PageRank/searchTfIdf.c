#include <stdio.h>
#include <math.h>
#include "list.h"
#include "graph.h"
#include "BSTree.h"
#include "readData.h"


int main(int argc, char **argv)
{
   urlList L = GetCollection();
   //showUrlList(collections);
   Graph G = newGraph(L);
   //showGraph(G);
   Tree T = GetInvertedList(L);
   //howTree(T);
   int i =1;
   Tree node = TreeSearch(T, argv[i]);
   //while((node = TreeSearch(T, argv[i])) && node != NULL){


      findWord(node->urlLinks, argv[1]);
      showUrlList(node->urlLinks);
      double IDF = log10((double)L->nElem/(double)node->count);
      printf("%lf, %lf, %lf", IDF, (double)node->count, (double)L->nElem);

      for(node->urlLinks->curr = node->urlLinks->head;
          node->urlLinks->curr != NULL;
          node->urlLinks->curr = node->urlLinks->curr->next)
          {
             node->urlLinks->curr->total = node->urlLinks->curr->total*IDF;
          }

      showUrlList(node->urlLinks);
      i++;
   //}

   


   return 0;

}
