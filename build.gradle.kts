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

        compileOnly("${Dependencies.LOMBOK}:${Versions.LOMBOK}")
        annotationProcessor("${Dependencies.LOMBOK}:${Versions.LOMBOK}")

        implementation("${Dependencies.MPA_STRUCT}:${Versions.MPA_STRUCT}")
        annotationProcessor("${Dependencies.MPA_STRUCT_PROCESSOR}:${Versions.MPA_STRUCT}")

        testImplementation("${Dependencies.JUNIT5}:${Versions.JUNIT5}")
        testImplementation("${Dependencies.JUNIT_PLATFORM_LAUNCHER}:${Versions.JUNIT_PLATFORM_LAUNCHER}")

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

