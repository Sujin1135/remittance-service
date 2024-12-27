plugins {
    java
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.dependency.management)
    alias(libs.plugins.flyway)
}

group = "io.dflowers"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation(project(":subproject:presentation"))
    implementation(project(":subproject:domain"))
    implementation(project(":subproject:application"))
    implementation(project(":subproject:infrastructure"))
}

allprojects {
    apply(
        plugin =
            rootProject.libs.plugins.spring.boot
                .get()
                .pluginId,
    )
    apply(
        plugin =
            rootProject.libs.plugins.dependency.management
                .get()
                .pluginId,
    )
    apply(
        plugin =
            rootProject.libs.plugins.flyway
                .get()
                .pluginId,
    )
    apply(plugin = "java")

    dependencies {
        implementation(rootProject.libs.spring.web)
        implementation(rootProject.libs.bundles.db.libs)
        implementation(rootProject.libs.bundles.flyway)

        compileOnly(rootProject.libs.lombok)

        annotationProcessor(rootProject.libs.lombok)

        runtimeOnly(rootProject.libs.mysql.connector.j)

        testImplementation(rootProject.libs.bundles.spring.test)
        testImplementation(rootProject.libs.bundles.test.db)

        testRuntimeOnly(rootProject.libs.junit.platform.launcher)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
