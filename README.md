# Advent of Code 2021 🎄
My solutions for the Advent of Code 2021 written in Kotlin.

This repo is based my [Kotlin template for AoC](https://github.com/henningBunk/advent-of-code-kotlin-template)

https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day01/Day01.kt

## Logbook
###  [Day 1](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day01/Day01.kt)
Let's begin :-)  
First I followed a plan of giving `fold` a try but was bummed that I only got the acc and not two values to compare with each other. Therefore, I had to handle list indices. Solved it though. Cleaned it up with some extension functions and without using fold, but rather with list transforms.

After looking at other solutions I learned about the `.windowed` function and implemented an alternative solution with it. 

###  [Day 2](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day02/Day02.kt)
I wanted to solve it only by transforming the input data without creating variables. It was a challenge but it worked quite well. I introduced two classes for the second part to make it more readable.

###  [Day 3](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day03/Day03.kt)
Wow, that was harder than I expected. I took forever for that `.transpose()` function and regretted for the first time not using Python.  
For part 2 I could hone my rusty recursion skills which I only used at AoC outside of university.
I think I should come back to this problem and work on a simpler and cleaner solution.

###  [Day 4](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day04) / [Tests](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day04/Day04Test.kt)
Nothing special on this day. I tried to write clean and tested code. I am quite happy with the results and even worked test driven for some parts.   
The previous days prepared me well for writing the winning conditions which were quite fun to write. I also wrote one for diagonals which I later discovered were unnecessary. 

###  [Day 5](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day05/Day05.kt) / [Tests](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day05/Day05Test.kt)
Again pretty straight forward today. I used a list of mutable lists to count the vents. I like how the extension functions make it very easy to read what's happening in the solve functions.

###  [Day 6](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day06/Day06.kt)
My naive approach for part 1 could heat up the room pretty good for part 2.

###  [Day 7](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day07/Day07.kt)
Pretty happy with this one since it only took me only a few minutes and both tests were green on the first try, such a rare sight.  
I wonder if I there is a sigma operand for the second calculation, I feel like there should be one.   
Also, the runtime is pretty slow since it's a brute force approach. ~~But right now I don't feel like optimizing.~~ Found the trick with the triangle numbers. Which reduced the runtime from 5s to 100ms, not using higher order function reduced it further to 30ms.

###  Day 8 [Initial solution](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day08/Day08.kt) / [Much cleaner solution](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day08/Day08_second_try.kt)
I struggled with this one. I chose the wrong datatype to store my remaining candidates for each segment. I used an array of strings and for the operations I used char arrays. Here I got hugely confused with Kotlin's Array<Char> and CharArray. Afterwards I read the docs and am wiser now :)
I refactored my solution now that it is easier to read and more concise but it is still my original logic. I would like to implement it again using sets because I think that's way more appropriate. Also would like to test out a solution of a colleague who didn't decode the pattern but immediately determined the value of the codes.

Great and interesting day!

// edit:  
Tried it again using a map and the approach of my colleague not to decode each wire by itself but rather the numbers as a whole. SO. MUCH. EASIER!
See: https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day08/Day08_second_try.kt

###  [Day 9](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day09/Day09.kt)
Simple solution with a queue.

###  [Day 10](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day10/Day10.kt) / [Tests](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day10/Day10.kt)
Solved this one with my colleague using Ping-Pong-TDD-Pair-Programming. Great practice and lots of fun :)

###  [Day 11](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day11/Day11.kt)
Nothing special today. I like the solution of putting the octopus in a map with the coordinates as a key. Makes finding the neighbours pretty easy and you don't have to check for limits of the map. Since each octopus knows it's neighbours, I don't need to remember their position of the map which is nice too.

###  [Day 12](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day12/Day12.kt)
###  [Day 13](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day13/Day13.kt)
###  [Day 14](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day14/Day14.kt)
###  [Day 15](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day15/Day15.kt)
###  [Day 16](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day16/Day16.kt)
###  [Day 17](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day17/Day17.kt)
###  [Day 18](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day18/Day18.kt)
###  [Day 19](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day19/Day19.kt)
###  [Day 20](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day20/Day20.kt)
###  [Day 21](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day21/Day21.kt)
###  [Day 22](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day22/Day22.kt)
###  [Day 23](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day23/Day23.kt)
###  [Day 24](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day24/Day24.kt)
###  [Day 25](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/y2021/day25/Day25.kt)

## Usage
* To create a new day execute `./gradlew create --year=2021 --day=9`
* To create all days for a year execute `./gradlew create --year=2021`

## Thanks
This repo was build using inspiration from these repositories which I am thankful for:
* https://gitlab.com/martyschaer/advent-of-code-complete
  * Interface for the Solutions with solving funs
  * Annotations to mark the year and days
* https://github.com/kotlin-hands-on/advent-of-code-kotlin-template
  * A simpler folder structure
  * A single run command to run tests against the sample data and on success resume with the real data
