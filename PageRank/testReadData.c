// Written by: Faiz Ather,    UNSW: z5170340
// Written By: Farhan Ghazi,  UNSW: z5199861
// Dated: 21/OCT/2018
// testReadData.c - Tests readData

#include <stdio.h>
#include "list.h"
#include "graph.h"
#include "BSTree.h"
#include "readData.h"


int main(void)
{

    urlList L = GetCollection();
    Graph G = GetGraph(L);
    showUrlList(L);
   //urlList LL = readUrlWords("url22");
    /*
    showUrlList(LL);
    freeUrlList(LL);
    if (isConnected(G, "url11", "url21")) printf("yes\n");
    if (!isConnected(G, "url11", "url211")) printf("no\n");
    showGraph(G);
    Tree T = GetInvertedList(L);
    T = rebalance(T);
    showTree(T);*/

    //Tree T = invertedToTxt();
    //showTree(T);
    //freeTree(T);


    showGraph(G);

    freeGraph(G);
    freeUrlList(L);
    //freeTree(T);

   return 0;
}
