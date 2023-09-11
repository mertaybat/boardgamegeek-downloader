plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    id("com.github.edeandrea.xjc-generation") version "1.6"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    "xjc"("javax.xml.bind:jaxb-api:2.3.1")
    "xjc"("org.glassfish.jaxb:jaxb-runtime:2.3.1")
    "xjc"("org.glassfish.jaxb:jaxb-xjc:2.3.1")
    "xjc"("org.jvnet.jaxb2_commons:jaxb2-basics:0.12.0")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    "implementation"(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.15.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClass.set("converter.AppKt")
}

xjcGeneration {
    defaultAdditionalXjcOptions = mapOf("encoding" to "UTF-8")

    schemas {
        create("service-catalog") {
            schemaRootDir = "src/main/schema"
            schemaFile = "schema.xsd"
            //bindingFile = "src/main/schema/binding.xjb"
            additionalXjcCommandLineArgs = mapOf(
                "-nv" to "",
                "-XsimpleEquals" to "",
                "-XsimpleHashCode" to ""
            )
        }
    }
}

sourceSets {
    main {
        java {
            srcDir("$buildDir/generated-sources/main/xjc")
        }
        resources {
            srcDir("$projectDir/src/main/schema")
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn(tasks.withType<com.github.edeandrea.xjcplugin.type.Xjc>())
}

tasks.jar {
    manifest.attributes["Main-Class"] = "converter.AppKt"
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree)
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
