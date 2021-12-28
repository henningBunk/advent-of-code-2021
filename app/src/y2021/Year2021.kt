package y2021

import y2021.day01.Day01SonarSweep
import y2021.day02.Day02Dive
import y2021.day03.Day03BinaryDiagnostic
import y2021.day04.Day04GiantSquid
import y2021.day05.Day05HydrothermalVenture
import y2021.day06.Day06Lanternfish
import y2021.day07.Day07TheTreacheryOfWhales
import y2021.day08.Day08WithoutAnalyzingIndividualSegments
import y2021.day09.Day09SmokeBasin
import y2021.day10.Day10SyntaxScoring
import y2021.day11.Day11DumboOctopus
import y2021.day12.Day12PassagePathing
import y2021.day13.Day13TransparentOrigami
import y2021.day14.Day14ExtendedPolymerization
import y2021.day15.Day15Chiton
import y2021.day16.Day16PacketDecoder
import y2021.day17.Day17TrickShot
import y2021.day20.Day20TrenchMap

fun main(args: Array<String>) {
    listOf(
        Day01SonarSweep(),
        Day02Dive(),
        Day03BinaryDiagnostic(),
        Day04GiantSquid(),
        Day05HydrothermalVenture(),
        Day06Lanternfish(),
        Day07TheTreacheryOfWhales(),
        Day08WithoutAnalyzingIndividualSegments(),
        Day09SmokeBasin(),
        Day10SyntaxScoring(),
        Day11DumboOctopus(),
        Day12PassagePathing(),
        Day13TransparentOrigami(),
        Day14ExtendedPolymerization(),
//        Day15Chiton(),
        Day16PacketDecoder(),
        Day17TrickShot(),
        Day20TrenchMap(),
    ).forEach {
        it.solveThem()
    }
}