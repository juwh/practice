//
// Created by William Ju on 10/18/17.
//

#include <iostream>
#include <queue>

struct CPoint {
    double x;
    double y;

    CPoint() {}
    CPoint(double horz, double vert) : x(horz), y(vert) {}
};

struct compSecond {
    bool operator()(std::pair<CPoint, double> & a, std::pair<CPoint, double> & b) const {
        return a.second > b.second;
    }
};

int main() {
    std::priority_queue<std::pair<CPoint, double>, std::vector<std::pair<CPoint, double>>, compSecond> min_heap;
    for (int i = 0; i < 3; i++) {
        CPoint test(i + 0.5, i+1.5);
        //test.x = i + 0.5;
        //test.y = (i+1.5);
        std::pair<CPoint, double> point_pair(test, (double)i + 1.5);
        //point_pair.first = test;
        //point_pair.second = (double)i + 1.5;
        min_heap.push(point_pair);
    }
    for (int i = 0; i < 3; i++) {
        std::cout << min_heap.top().second << " " << min_heap.top().first.x << " " << min_heap.top().first.y << std::endl;
        min_heap.pop();
    }
    return 0;
}