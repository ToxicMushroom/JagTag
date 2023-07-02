import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("maven-publish")
}

group = "me.melijn.jagtag"
version = "2.2.2"

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    mavenLocal()
}

val kotlin = "1.8.22"
val kotlinX = "1.7.2"

dependencies {
    //https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-stdlib-jdk8
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin")
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks {
    withType(JavaCompile::class) {
        options.encoding = "UTF-8"
    }
    withType(KotlinCompile::class) {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    artifacts {
        archives(sourcesJar)
    }
}

publishing {
    repositories {
        maven {
            url = if (project.version.toString().contains("SNAPSHOT")) {
                uri("https://reposilite.melijn.com/snapshots/")
            } else {
                uri("https://reposilite.melijn.com/releases/")
            }
            credentials {
                username = property("melijnReposilitePub").toString()
                password = property("melijnReposilitePassword").toString()
            }
        }
    }
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}