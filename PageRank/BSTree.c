// Acknowledgement: UNSW, UNSW CSE, UNSW COMP2521
// Modified by: Faiz Ather, UNSW: z5170340
// Dated: 23/OCT/2018

// Binary Search Tree ADT implementation ...

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "BSTree.h"

#define data(tree)  ((tree)->word)
#define left(tree)  ((tree)->left)
#define right(tree) ((tree)->right)

// make a new node containing data
Tree newNode(urlNodee L) {
   Tree new = malloc(sizeof(Node));
   assert(new != NULL);
   data(new) = malloc(sizeof(char)*strlen(L->urlStr));
   strcpy(data(new), L->urlStr);
   //strdup(L->urlStr)
   left(new) = right(new) = NULL;
   new->urlLinks = newUrlList();
   new->count = L->count;
   return new;
}

// create a new empty Tree
Tree newTree() {
   return NULL;
}

// free memory associated with Tree
void freeTree(Tree t) {
   if (t != NULL) {
      freeTree(left(t));
      freeTree(right(t));
      freeUrlList(t->urlLinks);
      free(data(t));
      free(t);
   }
}
// display Tree sideways
void showTreeR(Tree t, int depth) {
   if (t != NULL) {
      showTreeR(right(t), depth+1);
      int i;
      for (i = 0; i < depth; i++)
	 putchar('\t');            // TAB character
      printf("%s->%d\n", data(t), t->count);
      //showUrlList(t->urlLinks);
      showTreeR(left(t), depth+1);
   }
}

void showTree(Tree t) {
   //printf("This happened\n");
   showTreeR(t, 0);
}

// compute height of Tree
int TreeHeight(Tree t) {

   // not yet implemented

   return -1;
}

// count #nodes in Tree
int TreeNumNodes(Tree t) {
   if (t == NULL)
      return 0;
   else
      return 1 + TreeNumNodes(left(t)) + TreeNumNodes(right(t));
}

// check whether a key is in a Tree
Tree TreeSearch(Tree t, Key word) {
   if (t == NULL)
      return NULL;
   else if (strcmp(word , data(t)) < 0)
      return TreeSearch(left(t), word);
   else if (strcmp(word , data(t)) > 0)
      return TreeSearch(right(t), word);
   else                                 // it == data(t)
      return t;
}

// insert a new item into a Tree
Tree TreeInsert(Tree t, urlNodee L, Key urlStr) {
   if ( t != NULL && !strcmp(L->urlStr, data(t)) )
   {
      t->count += L->count;
      insertUrlNode(t->urlLinks, urlStr, true);
   }
   if (t == NULL)
   {
      t = newNode(L);
      insertUrlNode(t->urlLinks, urlStr, true);
   }
   else if (strcmp(L->urlStr , data(t)) < 0)
      left(t) = TreeInsert(left(t), L, urlStr);
   else if (strcmp(L->urlStr , data(t)) > 0)
      right(t) = TreeInsert(right(t), L, urlStr);
   return t;
}

Tree joinTrees(Tree t1, Tree t2) {
   if (t1 == NULL)
      return t1;
   else if (t2 == NULL)
      return t2;
   else {
      Tree curr = t2;
      Tree parent = NULL;
      while (left(curr) != NULL) {    // find min element in t2
	 parent = curr;
	 curr = left(curr);
      }
      if (parent != NULL) {
	 left(parent) = right(curr);  // unlink min element from parent
	 right(curr) = t2;
      }
      left(curr) = t1;
      return curr;                    // min element is new root
   }
}

// delete an item from a Tree
Tree TreeDelete(Tree t, Key word) {
   if (t != NULL) {
      if (strcmp(word , data(t)) < 0)
	 left(t) = TreeDelete(left(t), word);
      else if (strcmp(word , data(t)) > 0)
	 right(t) = TreeDelete(right(t), word);
      else {
	 Tree new;
	 if (left(t) == NULL && right(t) == NULL)
	    new = NULL;
	 else if (left(t) == NULL)    // if only right subtree, make it the new root
	    new = right(t);
	 else if (right(t) == NULL)   // if only left subtree, make it the new root
	    new = left(t);
	 else                         // left(t) != NULL and right(t) != NULL
	    new = joinTrees(left(t), right(t));
	 free(t);
	 t = new;
      }
   }
   return t;
}

Tree rotateRight(Tree n1) {
   if (n1 == NULL || left(n1) == NULL)
      return n1;
   Tree n2 = left(n1);
   left(n1) = right(n2);
   right(n2) = n1;
   return n2;
}

Tree rotateLeft(Tree n2) {
   if (n2 == NULL || right(n2) == NULL)
      return n2;
   Tree n1 = right(n2);
   right(n2) = left(n1);
   left(n1) = n2;
   return n1;
}
/*
Tree insertAtRoot(Tree t, Item it) {

   printf("Not yet implemented.\n");

   return t;
}
*/
Tree partition(Tree t, int i) {
   if (t != NULL) {
      assert(0 <= i && i < TreeNumNodes(t));
      int m = TreeNumNodes(left(t));
      if (i < m) {
	 left(t) = partition(left(t), i);
	 t = rotateRight(t);
      } else if (i > m) {
	 right(t) = partition(right(t), i-m-1);
	 t = rotateLeft(t);
      }
   }
   return t;
}

Tree rebalance(Tree t) {
   int n = TreeNumNodes(t);
   if (n >= 3) {
      t = partition(t, n/2);           // put node with median key at root
      left(t) = rebalance(left(t));    // then rebalance each subtree
      right(t) = rebalance(right(t));
   }
   return t;
}
