import java.time.LocalDateTime

abstract class CreateDayTask : DefaultTask() {

    private var _year: Int = LocalDateTime.now().year

    @Suppress("MemberVisibilityCanBePrivate")
    var year: String?
        @Input get() = _year.toString()
        @Option(
            option = "year",
            description = "Which year should be created. When paired with '--day', only that day is created. Without it, every day gets created. When ommited, defaults to the current year."
        )
        set(value) {
            _year = value?.toInt() ?: 0
        }

    private var _day: Int = 0

    @Suppress("MemberVisibilityCanBePrivate")
    var day: String?
        @Input get() = _day.toString()
        @Option(
            option = "day",
            description = "Which day should be created. Range is 1-25."
        )
        set(value) {
            _day = value?.toInt()?.also {
                if (it < 1 || it > 25) throw IllegalArgumentException("Please give a day between 1 and 25")
            } ?: 0
        }

    @get:OutputDirectory
    abstract val templateDir: DirectoryProperty

    @get:OutputDirectory
    abstract val destination: DirectoryProperty

    @get:Inject
    abstract val fs: FileSystemOperations

    @TaskAction
    fun create() {
        val days = if (_day != 0) listOf(_day) else (1..25)
        days.forEach { thisDay ->
            val dayString = String.format("%02d", thisDay)
            val finalDestination = destination.get().dir("y$_year/day$dayString")

            if(project.file(finalDestination).exists()) {
                println("There already is a folder for $year day $thisDay. Skipping.")
            } else {
                fs.copy {
                    from(templateDir)
                    into(finalDestination)
                    rename("XY", dayString)
                    expand("YEAR" to "$_year", "DAY" to dayString)
                }
                println("Created files for $_year day $thisDay. Have fun!")
            }
        }
    }
}
