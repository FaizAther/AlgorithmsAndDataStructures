// Written by: Faiz Ather,    UNSW: z5170340
// Written By: Farhan Ghazi,  UNSW: z5199861

// Graph ADT
// Adjacency List Representation ... COMP2521
// graph.c ... Graph of strings (adjacency list)

#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <math.h>
#include "readData.h"
#include "graph.h"

Graph newGraph(urlList L)
{
   Graph G = malloc(sizeof(struct _graphRep));
   G->nV = L->nElem;
   G->hVertex = G->cVertex = NULL;
   for(L->curr = L->head; L->curr != NULL; L->curr = L->curr->next)
   {
      if ( L->head == L->curr ) G->hVertex = G->cVertex = malloc(sizeof(struct _graphNode));
      else
      {
         G->cVertex->next = malloc(sizeof(struct _graphNode));
         G->cVertex = G->cVertex->next;
      }
      G->cVertex->pPR = 0;
      G->cVertex->nPR = 0;
      G->cVertex->urlInLinks = newUrlList();
      G->cVertex->urlStr = L->curr->urlStr;
      G->cVertex->urlLinks = readUrlFile(L->curr->urlStr);
      G->cVertex->next = NULL;
   }
   GraphNode curr;
   for (curr = G->hVertex; curr != NULL; curr = curr->next){

      for(G->cVertex = G->hVertex; G->cVertex != NULL; G->cVertex = G->cVertex->next)
      {
         if (strcmp(curr->urlStr, G->cVertex->urlStr) != 0){
            if (checkUrlList(G->cVertex->urlLinks, curr->urlStr)) insertUrlNode(curr->urlInLinks, G->cVertex->urlStr, true);
         }
      }
   }


   return G;
}

void showGraph(Graph G)
{
   for(G->cVertex = G->hVertex; G->cVertex != NULL; G->cVertex = G->cVertex->next)
   {
      printf("%s -> ", G->cVertex->urlStr);
      showUrlList(G->cVertex->urlLinks);
      printf("%s <- ", G->cVertex->urlStr);
      showUrlList(G->cVertex->urlInLinks);
   }
}

bool isConnected(Graph G, char *src, char *dest)
{
   for(G->cVertex = G->hVertex; G->cVertex != NULL; G->cVertex = G->cVertex->next)
   {
      if (!strcmp(src, G->cVertex->urlStr)){
      for(G->cVertex->urlLinks->curr = G->cVertex->urlLinks->head;
          G->cVertex->urlLinks->curr != NULL;
          G->cVertex->urlLinks->curr = G->cVertex->urlLinks->curr->next)
       {
          if (!strcmp(dest, G->cVertex->urlLinks->curr->urlStr)) return true;
      }
      }
   }
   return false;
}

void freeGraph(Graph G)
{
   GraphNode tmp = NULL;
   for(G->cVertex = G->hVertex; G->cVertex != NULL; G->cVertex = G->cVertex->next)
   {
      if(tmp != NULL) free(tmp);
      freeUrlList(G->cVertex->urlLinks);
      freeUrlList(G->cVertex->urlInLinks);

      tmp = G->cVertex;
   }
   if(tmp != NULL) free(tmp);
   free(G);

}

double calculateInLinkPR(GraphNode gH, GraphNode gN)
{
    double tPR = 0.0;
    GraphNode curr;
    for(gN->urlInLinks->curr = gN->urlInLinks->head;
        gN->urlInLinks->curr != NULL;
        gN->urlInLinks->curr = gN->urlInLinks->curr->next)
    {
        curr = gH;
        while(curr != NULL){
            if (!strcmp(curr->urlStr, gN->urlInLinks->curr->urlStr)){
                tPR += curr->pPR;
            }
            curr = curr->next;
        }
    }
    return tPR;
}

double wIN(GraphNode gH, GraphNode gN)
{
    double total = 0.0;
    GraphNode curr;
    for(gN->urlInLinks->curr = gN->urlInLinks->head;
        gN->urlInLinks->curr != NULL;
        gN->urlInLinks->curr = gN->urlInLinks->curr->next)
    {
        curr = gH;
        while(curr != NULL){
            if (!strcmp(curr->urlStr, gN->urlInLinks->curr->urlStr))
                total += curr->urlInLinks->nElem;

            curr = curr->next;
        }
    }
    return gN->urlInLinks->nElem/total;
}

double wOUT(GraphNode gH, GraphNode gN)
{
    double total = 0.0;
    GraphNode curr;
    for(gN->urlLinks->curr = gN->urlLinks->head;
        gN->urlLinks->curr != NULL;
        gN->urlLinks->curr = gN->urlLinks->curr->next)
    {
        curr = gH;
        while(curr != NULL){
            if (!strcmp(curr->urlStr, gN->urlLinks->curr->urlStr))
                total += curr->urlLinks->nElem;

            curr = curr->next;
        }
    }
    if(total == 0) return 0.5;
    return gN->urlLinks->nElem/((total==0)?(double)0.5:total);
}


void PageRankW(Graph g , double DF , double diffPR , int maxIteration)
{

    for(g->cVertex = g->hVertex; g->cVertex != NULL; g->cVertex = g->cVertex->next){
        g->cVertex->pPR = 1/(double)(g->nV);
        g->cVertex->nPR = 0;
    }

    int iteration = 0;
    double diff = diffPR;
    while (iteration < maxIteration && diff >= diffPR) {
        for(g->cVertex = g->hVertex; g->cVertex != NULL; g->cVertex = g->cVertex->next){
            printf("%s: " , g->cVertex->urlStr);
            g->cVertex->nPR = CONSTANT + (DF * calculateInLinkPR(g->hVertex, g->cVertex) * wIN(g->hVertex, g->cVertex) * wOUT(g->hVertex, g->cVertex));
            //printf("%.7f\n", wIN(g->hVertex, g->cVertex));
            g->cVertex->pPR = g->cVertex->nPR;
            printf("%.7f\n" , g->cVertex->nPR);
        }
        for(g->cVertex = g->hVertex; g->cVertex != NULL; g->cVertex = g->cVertex->next)
        {
            diff = g->cVertex->nPR - g->cVertex->pPR;
            diff = fabs(diff);
        }
        iteration++;
    }
}

void showPageRanks(FILE *fp, Graph g)
{
   for(g->cVertex = g->hVertex; g->cVertex != NULL; g->cVertex = g->hVertex)
   {
      fprintf(fp, "%s, %d, %.7f\n", g->cVertex->urlStr, g->cVertex->urlLinks->nElem, g->cVertex->nPR);
   }
}
