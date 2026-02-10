def knapsack(v, w, W):
    n = len(v)
    dp = [[0] * (W + 1) for _ in range(n + 1)]
    # i determines the value/weight being added for a step
    for i in range(1, n + 1):
        # j considers all end weights from weight added from i up to W
        for j in range(W + 1):
            dp[i][j] = dp[i - 1][j]
            if j >= w[i - 1]:
                dp[i][j] = max(dp[i][j], dp[i - 1][j - w[i - 1]] + v[i - 1])
    return dp[n][W]

def knapsack_fractional(v, w, W):
    n = len(v)
    # calculate the value per weight
    d = [v[i] / w[i] for i in range(n)]
    idx = [i for i in range(n)]
    # sort indices for the value per weight by value per weight
    # in descending order
    for i in range(n):
        for j in range(i + 1, n):
            # selection sort with excessive swaps
            if d[idx[i]] < d[idx[j]]:
                idx[i], idx[j] = idx[j], idx[i]
    res = 0
    for i in range(n):
        if w[idx[i]] <= W:
            res += v[idx[i]]
            W -= w[idx[i]]
        else:
            res += d[idx[i]] * W
            break
    return res

v = [60, 100, 120]
w = [10, 20, 30]
W = 50
print(knapsack(v, w, W)) # 220
print(knapsack_fractional(v, w, W)) # 240.0