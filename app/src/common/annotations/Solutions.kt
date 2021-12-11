package common.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Solutions(val part1: Int, val part2: Int) {
}
