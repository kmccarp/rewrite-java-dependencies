plugins {
    id("org.openrewrite.build.recipe-library") version "latest.release"
}

group = "org.openrewrite.recipe"
description = "A rewrite module automating Java dependency management."

repositories {
    maven {
        url = uri("https://repo.gradle.org/gradle/libs-releases/")
        content {
            excludeVersionByRegex(".+", ".+", ".+-rc-?[0-9]*")
        }
    }
    // Needed to pick up snapshot versions of rewrite
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

val rewriteVersion = rewriteRecipe.rewriteVersion.get()
dependencies {
    implementation(platform("org.openrewrite:rewrite-bom:$rewriteVersion"))
    implementation("org.openrewrite:rewrite-maven")
    // temporarily use SNAPSHOT version to release, to be changed back
    implementation("org.openrewrite:rewrite-gradle:7.41.0-SNAPSHOT")
    implementation("org.openrewrite:rewrite-groovy")
    runtimeOnly("org.openrewrite:rewrite-java-8")
    runtimeOnly("org.openrewrite:rewrite-java-11")
    runtimeOnly("org.openrewrite:rewrite-java-17")

    testRuntimeOnly("org.openrewrite:rewrite-java-8")
    testRuntimeOnly("org.openrewrite:rewrite-java-11")
    testRuntimeOnly("org.openrewrite:rewrite-java-17")
    testRuntimeOnly("org.projectlombok:lombok:latest.release")

    implementation("org.openrewrite.gradle.tooling:model:latest.release")

    implementation("com.google.guava:guava:latest.release")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    testRuntimeOnly("org.gradle:gradle-tooling-api:latest.release")
    compileOnly("org.projectlombok:lombok:latest.release")
    annotationProcessor("org.projectlombok:lombok:latest.release")
    testRuntimeOnly("ch.qos.logback:logback-classic:1.2.+")
}

// temporarily added to release with a SNAPSHOT version of rewrite(depends on a recipe in the SNAPSHOT version only), to be removed
nebulaPublishVerification {
    ignore("org.openrewrite:rewrite-maven")
    ignore("org.openrewrite:rewrite-gradle")
    ignore("org.openrewrite:rewrite-groovy")
    ignore("org.openrewrite.gradle.tooling:model")
}
