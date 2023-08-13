import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.ASCIIDOCTOR) version Versions.ASCIIDOCTOR
}

dependencies {
    implementation(project(mapOf("path" to ":fpay-common")))
    testImplementation(Dependencies.SPRING_REST_DOCS_MOCKMVC)
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
        dependsOn(asciidoctor)

        from(asciidoctor.get().outputDir) {
            into("BOOT-INF/classes/static/docs")
        }
    }
}
//val snippetsDir by extra {
//    file("build/generated-snippets")
//}

//tasks {
//    asciidoctor {
//        dependsOn(test)
//        configurations("asciidoctorExtensions")
////        baseDirFollowsSourceFile()  // 3
//        inputs.dir(snippetsDir)
//    }
//
//    register<Copy>("copyDocument") {  // 4
//        dependsOn(asciidoctor)
//        from(file("build/docs/asciidoc/index.html"))
//        into(file("src/main/resources/static/docs"))
//    }
//    bootJar {
//        dependsOn("copyDocument")  // 5
//    }
//}
//
//ext {
//    snippetsDir = file('build/generated-snippets')
//}
//
//bootJar {
//    dependsOn asciidoctor
//            from ("${asciidoctor.outputDir}/html5") {
//                into 'static/docs'
//            }
//}


//test {
//    outputs.dir snippetsDir
//            useJUnitPlatform()
//}
//
//asciidoctor { // asciidoctor 작업 구성
//    dependsOn test // test 작업 이후에 작동하도록 하는 설정
//            configurations 'asciidoctorExtensions' // 위에서 작성한 configuration 적용
//    inputs.dir snippetsDir // snippetsDir 를 입력으로 구성
//
//            // source가 없으면 .adoc파일을 전부 html로 만들어버림
//            // source 지정시 특정 adoc만 HTML로 만든다.
//            sources{
//                include("**/index.adoc","**/common/*.adoc")
//            }
//
//    // 특정 .adoc에 다른 adoc 파일을 가져와서(include) 사용하고 싶을 경우 경로를 baseDir로 맞춰주는 설정입니다.
//    // 개별 adoc으로 운영한다면 필요 없는 옵션입니다.
//    baseDirFollowsSourceFile()
//}
//
//asciidoctor.doFirst {
//    delete file('src/main/resources/static/docs')
//}
//
//task copyDocument(type: Copy) {
//    dependsOn asciidoctor
//            from file("build/docs/asciidoc")
//    into file("src/main/resources/static/docs")
//}
//
//build {
//    dependsOn copyDocument
//}