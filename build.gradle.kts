plugins {
    kotlin("jvm") version "1.9.23"
}

group = "jp.ac.it_college.std.s23024"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val exposedVersion:  String by project
dependencies {
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")
    implementation("org.slf4j:slf4j-simple:2.0.13")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client:3.4.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}