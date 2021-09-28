#include <stdio.h>

int
FINDPOSITION_R (int *, int, int);

int
FINDPOSITION_I (int *, int, int);

//Q2)b)
int
main (void)
{
	int A[9] = {-1,0,2,3,10,11,23,24,102};

	int res = FINDPOSITION_R ( A, 0, 8 );

	printf ("%d\n", res);

	res = FINDPOSITION_I ( A, 0, 8 );

	printf ("%d\n", res);

	return 0;
}

//Q2)a)
int
FINDPOSITION_R (int *A, int left, int right)
{
	int res = -1;
	
	if (right < left) return res;

	int m = (left+right)/2;
	if ( A[m] > m )
		res = FINDPOSITION_R (A, left, m-1);
	else if ( A[m] < m )
		res = FINDPOSITION_R (A, m+1, right);
	else if ( A[m] == m )
		return m;
	return res;
}

//Q2)e)
int
FINDPOSITION_I ( int *A, int left, int right )
{
	int m = -1;
	while (left < right)
	{
		m=(left+right)/2;
		
		if ( A[m] == m ) return m;
		else if ( A[m] > m ) right = m-1;
		else left = m+1;
	}
	return -1;
}
