package common.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class SampleSolutions(val part1: Int, val part2: Int) {
}
