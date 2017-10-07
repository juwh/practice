//
// Created by William on 10/6/2017.
//
/*
 * Given a 2 dimensional point of a rectangle and its area, find permutations of all the other
 * 3 points of the rectangle in 2-D space.
 */
#include <list>
#include <cmath>
#include <tuple>

class RectPermutations {
private:
    struct Point {
        int x;
        int y;

        Point(int x, int y) : x(x), y(y) {}
        explicit Point(Point p) : x(p.x), y(p.y) {}
        Point Point::operator+(const Point &other) {
            return Point{(x + other.x), (y + other.y)};
        }
    };
public:
    // function for generating factors
    std::list<Point> listFactors(int area) {
        int sqrt = (int)sqrt(area);
        std::list<Point> list;
        for (int i = 1; i <= sqrt; i++) {
            if (area%i == 0) {
                list.emplace_back(Point(i, (area/i)));
            }
        }
        return list;
    }
    // function for generating three points for each relative quadrant
    std::list<std::tuple<Point, Point, Point, Point>> rotateRect(std::list<Point> factors) {
        std::list<std::tuple<Point, Point, Point, Point>> rotations;
        // create tuples of four points, one for each quadrant
        for (Point p : factors) {
            rotations.push_back(std::tuple(Point(p.x, p.y), Point(-p.x, p.y),
                                           Point(-p.x, -p.y), Point(p.x, -p.y)));
        }
        return rotations;
    };

    std::tuple<Point, Point, Point> otherPoints(Point sp, Point ep) {

    };

    // primary function for getting all factors for the area and generating permutations
    std::list<std::tuple<Point, Point, Point>> rectPermutations(Point start, int area) {
        std::list<std::tuple<Point, Point, Point>> permutations;
        std::list<Point> factors = listFactors(area);
        if (start.x != start.y) {

        }
    };
};