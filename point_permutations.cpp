//
// Created by William on 10/6/2017.
//
/*
 * Given a 2 dimensional point of a rectangle and its area, find permutations of all the other
 * 3 points of the rectangle in 2-D space.
 */
#include <cmath>
#include <iostream>
#include <list>
#include <tuple>

struct Point {
    int x;
    int y;

    Point(int horz=0, int vert=0) : x(horz), y(vert) {}
    Point(const Point &p) : x(p.x), y(p.y) {}
    Point operator+(const Point& a) const
    {
        return Point(a.x+x, a.y+y);
    }
    std::string toString() {
        return "(" + std::to_string(x) + "," + std::to_string(y) + ")";
    }
};

// function for generating factors
std::list<Point> listFactors(int area) {
    int areaRt = (int)std::sqrt(area);
    std::list<Point> list;
    for (int i = 1; i <= areaRt; i++) {
        if (area%i == 0) {
            list.emplace_back(Point(i, (area/i)));
        }
    }
    return list;
}

std::tuple<Point, Point, Point> otherPoints(Point sp, Point ep) {
    return std::make_tuple(Point(sp.x, ep.y), ep, Point(ep.x, sp.y));
};

// function for generating three points for each relative quadrant
std::list<std::tuple<Point, Point, Point>> rotateRect(Point sp, Point ep) {
    std::list<std::tuple<Point, Point, Point>> rotations;
    int xDiff = ep.x - sp.x;
    int yDiff = ep.y - sp.y;
    // create tuples of four points, one for each quadrant
    rotations.emplace_back(otherPoints(sp, ep));
    rotations.emplace_back(otherPoints(sp, Point(sp.x-xDiff, ep.y)));
    rotations.emplace_back(otherPoints(sp, Point(ep.x, sp.y-yDiff)));
    rotations.emplace_back(otherPoints(sp, Point(sp.x-xDiff, sp.y-yDiff)));
    return rotations;
};

// primary function for getting all factors for the area and generating permutations
std::list<std::tuple<Point, Point, Point>> rectPermutations(Point start, int area) {
    std::list<std::tuple<Point, Point, Point>> permutations;
    std::list<Point> factors = listFactors(area);
    for (Point p : factors) {
        if (p.x != p.y) {
            auto rotateList = rotateRect(start, start+p);
            for (auto triplet : rotateList) {
                permutations.emplace_back(triplet);
            }
        }
        auto rotateList = rotateRect(start, Point(start.x+p.y, start.y+p.x));
        for (auto triplet : rotateList) {
            permutations.emplace_back(triplet);
        }
    }
    return permutations;
};

int main() {
    auto pointList = rectPermutations(Point(1,1), 12);
    for (const std::tuple<Point, Point, Point>& triplet : pointList) {
        Point point1 = std::get<0>(triplet);
        Point point2 = std::get<1>(triplet);
        Point point3 = std::get<2>(triplet);

        std::cout << point1.toString() << point2.toString() << point3.toString() << std::endl;
    }
}