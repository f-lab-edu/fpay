import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.ASCIIDOCTOR) version Versions.ASCIIDOCTOR
}

dependencies {
    implementation(project(mapOf("path" to ":fpay-common")))
    implementation(Dependencies.SPRING_BOOT_STARTER_DATA_JPA)
    implementation("${Dependencies.JDBC_MYSQL}:${Versions.JDBC_MYSQL}")
    implementation("${Dependencies.REDISSON}:${Versions.REDISSON}")
    testImplementation(Dependencies.SPRING_SECURITY_TEST)
    testImplementation(Dependencies.SPRING_REST_DOCS_MOCKMVC)
    implementation(Dependencies.SPRING_BOOT_STARTER_VALIDATION)
}

tasks {
    val snippetsDir = file("$buildDir/generated-snippets")

    clean {
        delete("src/main/resources/static/docs")
    }

    test {
        useJUnitPlatform()
        systemProperty("org.springframework.restdocs.outputDir", snippetsDir)
        outputs.dir(snippetsDir)
    }

    build {
        dependsOn("copyDocument")
    }

    asciidoctor {
        dependsOn(test)

        attributes(
                mapOf("snippets" to snippetsDir)
        )
        inputs.dir(snippetsDir)

        doFirst {
            delete("src/main/resources/static/docs")
        }
    }

    register<Copy>("copyDocument") {
        dependsOn(asciidoctor)

        destinationDir = file(".")
        from(asciidoctor.get().outputDir) {
            into("src/main/resources/static/docs")
        }
    }

    bootJar {
        enabled = true

        dependsOn(asciidoctor)

        from(asciidoctor.get().outputDir) {
            into("BOOT-INF/classes/static/docs")
        }
    }
}
