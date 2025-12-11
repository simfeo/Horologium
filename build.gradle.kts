// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.13.2" apply false
}

//afterEvaluate {
//    if (project.hasProperty("android.injected.signing.store.file")) {
//        println ("key store path: ${project.property("android.injected.signing.store.file")}");
//    }
//    if (project.hasProperty("android.injected.signing.store.password")) {
//        println ("key store password: ${project.property("android.injected.signing.store.password")}");
//    }
//    if (project.hasProperty("android.injected.signing.key.alias")) {
//        println ("key alias: ${project.property("android.injected.signing.key.alias")}");
//    }
//    if (project.hasProperty("android.injected.signing.key.password")) {
//        println ("key password: ${project.property("android.injected.signing.key.password")}");
//    }
//}