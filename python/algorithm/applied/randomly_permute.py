import random

def random_permute(a):
    for i in range(len(a)):
        swap = random.randint(0, len(a) - i - 1) + i
        a[i], a[swap] = a[swap], a[i]

counts = [[0 for _ in range(5)] for _ in range(5)]
for i in range(100):
    a = [1, 2, 3, 4, 5]
    random_permute(a)
    for j in range(len(a)):
        counts[a[j] - 1][j] += 1
for i in range(len(counts)):
    for j in range(len(counts[i])):
        print(counts[i][j], end = " ")
    print()