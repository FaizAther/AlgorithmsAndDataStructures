import matplotlib.pyplot as plt

# The data
x =  [5, 10, 50, 100, 500, 1000, 10000]
"""
g1s1 = [0,  0, 0, 3, 11, 16, 422]
g1i1 = [1, 1, 1, 1, 10, 14, 405]
g1m1 = [0,2,4,8,28,85,1885]
g1q1 = [0,  0, 2, 3, 43, 168, 1228]
"""
'''
g1s1 = [0,  0, 0, 0, 3, 3, 184]
g1i1 = [0, 0, 1, 1, 1, 6, 88]
g1m1 = [0,1,2,1,11,14,1666]
g1q1 = [0,  0, 0, 0, 8, 12,79]
'''
g1s1 = [0,  0, 1, 3, 2, 11, 300]
g1i1 = [0, 0, 1, 12, 35, 27,720]
g1m1 = [0,0,2,4,32,64,2061]
g1q1 = [0,  0, 0, 3, 39, 44, 1152]

# Initialise the figure and axes.
fig, ax = plt.subplots(1, figsize=(8, 6))

# Set the title for the figure
fig.suptitle('Growth - Sorted Descending', fontsize=15)

# Draw all the lines in the same plot, assigning a label for each one to be
# shown in the legend.
ax.plot(x, g1s1, color="red", label="Selection")
ax.plot(x, g1i1, color="green", label="Insertion")
ax.plot(x, g1m1, color="blue", label="Merge")
ax.plot(x, g1q1, color="yellow", label="Quick")

# Add a legend, and position it on the lower right (with no box)
plt.legend(loc="upper left", title="Analysis Sort", frameon=False)

plt.show()
