import CreateDayTask_gradle.CreateDayTask

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    application
    id("CreateDayTask")
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

tasks.register<CreateDayTask>("create") {
    group = "Advent of Code"
    description = "Generates new files for you to work in. Define the year and day with '--year=2021 --day=8'. If the day is omitted, all 25 days are created. If the year is omitted, the current year is used."
    templateDir.set(File(".template"))
    destination.set(File("src"))
}