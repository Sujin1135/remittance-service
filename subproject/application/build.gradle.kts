dependencies {
    implementation(project(":subproject:domain"))

    implementation(rootProject.libs.spring.tx)

    testImplementation(project(":subproject:infrastructure"))

    testFixturesImplementation(project(":subproject:domain"))

    testImplementation(rootProject.libs.bundles.test.db)
    testImplementation(rootProject.libs.bundles.flyway)

    testFixturesImplementation(rootProject.libs.fixture.monkey)
}
