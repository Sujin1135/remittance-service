plugins {
    alias(libs.plugins.shadow)
}

springBoot {
    mainClass.set("io.dflowers.remittanceservice.RemittanceServiceApplication")
}

dependencies {
    implementation(project(":subproject:presentation"))
    implementation(project(":subproject:domain"))
    implementation(project(":subproject:application"))
    implementation(project(":subproject:infrastructure"))

    implementation(libs.swagger)
}

tasks {
    shadowJar {
        archiveBaseName.set("remittance-service")
        archiveClassifier.set("")
        archiveVersion.set("")
        mergeServiceFiles()
    }
}
