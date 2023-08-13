
dependencies {
    implementation(Dependencies.SPRING_BOOT_STARTER_DATA_JPA)
    implementation("${Dependencies.JDBC_MYSQL}:${Versions.JDBC_MYSQL}")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<Test> {
    useJUnitPlatform()
}