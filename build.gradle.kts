import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.7.10"
    id(Plugins.SPRING_BOOT) version Versions.SPRING_BOOT
}

allprojects {
    group = "com.flab.fpay"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = Plugins.SPRING_BOOT)
    apply(plugin = Plugins.SPRING_DEPENDENCY_MANAGEMENT)

//    configurations["compileOnly"].extendsFrom(configurations["annotationProcessor"])

    dependencies {
        implementation(Dependencies.SPRING_BOOT_STARTER_WEB)
        testImplementation(Dependencies.SPRING_BOOT_STARTER_TEST)
        testImplementation("${Dependencies.JUNit}:${Versions.JUNIT_VERSION}")
        compileOnly("${Dependencies.LOMBOK}:${Versions.LOMBOK_VERSION}")
        annotationProcessor("${Dependencies.LOMBOK}:${Versions.LOMBOK_VERSION}")
    }

    tasks {
        jar { enabled = true }
        bootJar { enabled = false }
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

}

