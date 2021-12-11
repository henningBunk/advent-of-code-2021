plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    application
}

println(rootProject.buildDir)
//project.buildDir = File("app")



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