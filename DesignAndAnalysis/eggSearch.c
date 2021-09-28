
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>

#define NFOUND  -1
#define FOUND   0
#define BREAK   1
#define NBREAK  2
#define RIGHT   3
#define LEFT    4
#define UP      5
#define DOWN    6

#define ARR_SIZE_X  20
#define ARR_SIZE_Y  20

void
show (int (*)[ARR_SIZE_X]);

int *
search (int (*)[ARR_SIZE_X], int, int, int, int);

int *
searchR (int (*)[ARR_SIZE_X], int, int, int, int, int, int);

int *
searchI (int (*)[ARR_SIZE_X], int, int, int, int, bool, bool);

int
calculateJump (int, int);

int arr[ARR_SIZE_Y][ARR_SIZE_X] = {
    {NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,UP,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,UP,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,UP,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,UP,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,UP,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,UP,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,UP,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,UP,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,UP,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,UP,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,UP,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,UP,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,UP,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,NBREAK,UP,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {RIGHT,RIGHT,RIGHT,RIGHT,RIGHT,RIGHT,RIGHT,RIGHT,RIGHT,FOUND,\
    LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT,LEFT},\
    {BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,DOWN,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,DOWN,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,DOWN,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,DOWN,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK},\
    {BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,DOWN,\
    BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK,BREAK}
};


int
main (void)
{
    show (arr);
    int *res = search (arr, 0, ARR_SIZE_X-1, 0, ARR_SIZE_Y-1);
    printf ("x=%d y=%d\n", res[0], res[1]);
    free (res);
    return 0;
}

void
show (int (*arr)[ARR_SIZE_X])
{
    for (int j = 0; j < ARR_SIZE_Y; j++)
    {
        for (int i = 0; i < ARR_SIZE_X; i++)
        {
            printf ("%d ", arr[j][i]);
        }
        printf ("\n");
    }
}

int
calculateJump (int min, int max)
{
    if ( min == max ) return min;
    return (int)ceil ((sqrt ( ((2.0 * (max - min)) + 0.25) ) - 0.5 ));
}

int *
search (int (*arr)[ARR_SIZE_X], int left, int right, int down, int up)
{
    return searchR (arr, left, right, down, up,\
                    calculateJump(left, right), calculateJump(down, up));
}

int *
searchR (int (*arr)[ARR_SIZE_X], int left, int right, int down, int up,\
                                    int j_x, int j_y)
{
    int *res=NULL;
    switch ( arr[down+j_y][left+j_x] )
    {
        case NBREAK:
            res = searchR (arr, left+j_x, right, down+j_y, up, j_x-1, j_y-1); break;
        case BREAK:
            return searchI (arr, left, left+j_x-1, down, down+j_y-1, true, true);
        case RIGHT:
            res = searchR (arr, left+j_x, right, down+j_y, down+j_y, j_x-1, 0); break;
        case LEFT:
            return searchI (arr, left, left+j_x-1, down+j_y, down+j_y, true, false);
        case UP:
            res = searchR (arr, left+j_x, left+j_x, down+j_y, up, 0, j_y-1); break;
        case DOWN:
            return searchI (arr, left+j_x, left+j_x, down, down+j_y-1, false, true);
        case FOUND: 
            res = (int *)malloc (sizeof(int)*2);
            res[0] = left + j_x;    res[1] = down + j_y;
            return res;
    }
    return res;
}

int *
searchI (int (*arr)[ARR_SIZE_X], int left, int right, int down, int up,\
                                bool r_on, bool u_on)
{
    int *res = (int *)malloc (sizeof(int)*2);
    while ( arr[down][left] != FOUND )
    {
        if ( arr[down][left] == RIGHT ) u_on=false;
        else if (arr[down][left] == UP) r_on=false;
        if (r_on) left++;
        if (u_on) down++;
    }
    res[0] = left; res[1] = down;
    return res;
}
