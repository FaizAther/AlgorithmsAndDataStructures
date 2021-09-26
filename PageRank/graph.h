// Written by: Faiz Ather,    UNSW: z5170340
// Written By: Farhan Ghazi,  UNSW: z5199861
// Graph ADT interface ... COMP2521

// graph.h ... Interface to Graph of strings

#ifndef GRAPH_H
#define GRAPH_H

#include <stdbool.h>
#include "list.h"


typedef struct _graphNode {
   char               *urlStr;
   double              pPR;
   double              nPR;
   urlList             urlLinks;
   urlList             urlInLinks;
   struct _graphNode  *next;
} graphNode;


typedef struct _graphRep {
	int                 nV;
	struct _graphNode  *hVertex;
   struct _graphNode  *cVertex;
//	Num                **edges;
} graphRep;

typedef struct _graphRep *Graph;
typedef struct _graphNode *GraphNode;

#define CONSTANT ((1.0 - DF)/(double)(g->nV))


// Function signatures

Graph newGraph(urlList);
void  showGraph(Graph);
bool isConnected(Graph, char *, char *);
double calculateInLinkPR(GraphNode gH, GraphNode gN);
double wIN(GraphNode gH, GraphNode gN);
double wOUT(GraphNode gH , GraphNode gN);
void PageRankW(Graph g , double DF , double diffPR , int maxIteration);
void showPageRanks(FILE *fp, Graph G);
void orderByPR(Graph g);
void  freeGraph(Graph);

#endif
