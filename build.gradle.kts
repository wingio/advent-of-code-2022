import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    java
    application
}

group = "xyz.wingio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources"))
        archiveClassifier.set("standalone")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes(mapOf(
                "Main-Class" to application.mainClass
            ))
        }
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get().map {
            if(it.isDirectory) it else zipTree(it)
        } + sourcesMain.output

        from(contents)
    }

    build {
        dependsOn(fatJar)
    }
}