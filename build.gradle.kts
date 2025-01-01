plugins {
    java
    `java-test-fixtures`
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.dependency.management)
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
    apply(plugin = "java")
    apply(plugin = "java-test-fixtures")

    dependencies {
        implementation(rootProject.libs.spring.web)

        compileOnly(rootProject.libs.lombok)

        annotationProcessor(rootProject.libs.lombok)

        testImplementation(rootProject.libs.bundles.spring.test)

        testRuntimeOnly(rootProject.libs.junit.platform.launcher)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
