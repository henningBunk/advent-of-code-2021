# Advent of Code 2021 🎄
My solutions for the Advent of Code 2021 written in Kotlin.

This repo is based my [Kotlin template for AoC](https://github.com/henningBunk/advent-of-code-kotlin-template)

## Logbook
###  [Day 1](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day01/Day01.kt)
Let's begin :-)  
First I followed a plan of giving `fold` a try but was bummed that I only got the acc and not two values to compare with each other. Therefore, I had to handle list indices. Solved it though. Cleaned it up with some extension functions and without using fold, but rather with list transforms.

After looking at other solutions I learned about the `.windowed` function and implemented an alternative solution with it. 

###  [Day 2](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day02/Day02.kt)
I wanted to solve it only by transforming the input data without creating variables. It was a challenge but it worked quite well. I introduced two classes for the second part to make it more readable.

###  [Day 3](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day03/Day03.kt)
Wow, that was harder than I expected. I took forever for that `.transpose()` function and regretted for the first time not using Python.  
For part 2 I could hone my rusty recursion skills which I only used at AoC outside of university.
I think I should come back to this problem and work on a simpler and cleaner solution.

###  [Day 4](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day04) / [Tests](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/test/kotlin/day04/Day04Test.kt)
Nothing special on this day. I tried to write clean and tested code. I am quite happy with the results and even worked test driven for some parts.   
The previous days prepared me well for writing the winning conditions which were quite fun to write. I also wrote one for diagonals which I later discovered were unnecessary. 

###  [Day 5](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day05/Day05.kt) / [Tests](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/test/kotlin/day05/Day05Test.kt)
Again pretty straight forward today. I used a list of mutable lists to count the vents. I like how the extension functions make it very easy to read what's happening in the solve functions.

###  [Day 6](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day06/Day06.kt)
My naive approach for part 1 could heat up the room pretty good for part 2.

###  [Day 7](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day07/Day07.kt)
Pretty happy with this one since it only took me only a few minutes and both tests were green on the first try, such a rare sight.  
I wonder if I there is a sigma operand for the second calculation, I feel like there should be one.   
Also, the runtime is pretty slow since it's a brute force approach. ~~But right now I don't feel like optimizing.~~ Found the trick with the triangle numbers. Which reduced the runtime from 5s to 100ms, not using higher order function reduced it further to 30ms.

###  [Day 8](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day08/Day08.kt)
###  [Day 9](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day09/Day09.kt)
###  [Day 10](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day10/Day10.kt)
###  [Day 11](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day11/Day11.kt)
###  [Day 12](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day12/Day12.kt)
###  [Day 13](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day13/Day13.kt)
###  [Day 14](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day14/Day14.kt)
###  [Day 15](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day15/Day15.kt)
###  [Day 16](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day16/Day16.kt)
###  [Day 17](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day17/Day17.kt)
###  [Day 18](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day18/Day18.kt)
###  [Day 19](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day19/Day19.kt)
###  [Day 20](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day20/Day20.kt)
###  [Day 21](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day21/Day21.kt)
###  [Day 22](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day22/Day22.kt)
###  [Day 23](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day23/Day23.kt)
###  [Day 24](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day24/Day24.kt)
###  [Day 25](https://github.com/henningBunk/advent-of-code-2021/blob/main/app/src/main/kotlin/day25/Day25.kt)



