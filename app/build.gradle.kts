plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.github.kittinunf.fuel:fuel:2.3.1")

    implementation("io.kotest:kotest-runner-junit5:4.6.4")
    implementation("io.kotest:kotest-assertions-core:4.6.4")
    implementation("io.mockk:mockk:1.12.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.getByName<Delete>("clean") {
    delete(rootProject.buildDir)
}

val sessionCookie: String? by project
application {
    mainClass.set("common/App")
    applicationDefaultJvmArgs = listOfNotNull(
        "-Dday=${System.getProperty("day")}",
        sessionCookie?.let { "-DsessionCookie=$it" }
    )
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }
}

abstract class CreateDayTask : DefaultTask() {

    private var _year: Int = 0

    @Suppress("MemberVisibilityCanBePrivate")
    var year: String?
        @Input get() = _year.toString()
        @Option(
            option = "year",
            description = "Which year should be created. When paired with '--day', only that day is created. Without it, every day gets created."
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
            description = "Which day should be created. Range is 1-25. Must be combined with '--year'"
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

tasks.register<CreateDayTask>("create") {
    group = "Advent of Code"
    description = "Generates new files for you to work in. Define the year and day with '--year=2021 --day=8'. If the day is omitted, all 25 days are created."
    templateDir.set(File(".template"))
    destination.set(File("src"))
}