package common.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AoCPuzzle(
    val year: Int,
    val day: Int,
)
