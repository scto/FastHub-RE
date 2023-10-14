plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.apollographql.apollo3").version("4.0.0-beta.1")
    id("com.mikepenz.aboutlibraries.plugin").version("10.0.1")
    id("io.objectbox")
}

fun loadConfig(): HashMap<String, String> {
    val configs: HashMap<String, String> = HashMap()
    configs["GITHUB_CLIENT_ID"] = "473e333123519beadd63"
    configs["GITHUB_SECRET"] = "b2d158f949d3615078eaf570ff99eba81cfa1ff9"
    configs["IMGUR_CLIENT_ID"] = "5fced7f255e1dc9"
    configs["IMGUR_SECRET"] = "03025033403196a4b68b48f0738e67ef136ad64f"
    try {
        val inputFile = rootProject.file("${rootProject.projectDir}\\app\\secrets.properties")
        println("Secrets found!\nLoading FastHub-RE credentials...")
        inputFile.forEachLine {
            val data = it.split("=")
            configs[data[0]] = data[1]
        }
    } catch (e: Exception) {
        println("Secrets not found!\nUsing demo credentials...")
    }
    return configs
}

val config = loadConfig()

android {
    namespace = "com.fastaccess"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.fastaccess.github.revival"
        minSdk = 25
        targetSdk = 31
        versionCode = 477
        versionName = "4.7.7"
        buildConfigField("String", "GITHUB_APP_ID", "\"com.fastaccess.github.revival\"")
        buildConfigField("String", "GITHUB_CLIENT_ID", "\"${config["GITHUB_CLIENT_ID"]}\"")
        buildConfigField("String", "GITHUB_SECRET", "\"${config["GITHUB_SECRET"]}\"")
        buildConfigField("String", "IMGUR_CLIENT_ID", "\"${config["IMGUR_CLIENT_ID"]}\"")
        buildConfigField("String", "IMGUR_SECRET", "\"${config["IMGUR_SECRET"]}\"")
        buildConfigField("String", "REST_URL", "\"https://api.github.com/\"")
        buildConfigField("String", "IMGUR_URL", "\"https://api.imgur.com/3/\"")
        buildConfigField("String", "GITHUB_STATUS_URL", "\"https://www.githubstatus.com/\"")
        buildConfigField("String", "GITHUB_STATUS_COMPONENTS_PATH", "\"api/v2/components.json\"")
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        getByName("debug") {
            storeFile = file("${rootProject.projectDir}\\app\\keys_debug.jks")
        }
        create("release") {
            storeFile = file("${rootProject.projectDir}\\app\\keys_release.jks")
            storePassword = config["PASSWORD"]
            keyPassword = config["PASSWORD"]
            keyAlias = config["KEY_ALIAS"]
        }
    }
    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    lint {
        htmlReport = true
        xmlReport = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    sourceSets {
        getByName("main") {
            res.srcDirs(
                "src/main/res/",
                "src/main/res/layouts/main_layouts",
                "src/main/res/layouts/row_layouts",
                "src/main/res/layouts/other_layouts",
                "src/main/res/translations"
            )
        }
    }
}

//kapt {
//    keepJavacAnnotationProcessors = true
//}

apollo {
    service("service") {
        packageName.set("com.fastaccess.github")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // androidx
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.material)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.legacy.preference.v14)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.palette.ktx)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.core.splashscreen)

    // thirtyinch
    implementation(libs.thirtyinch)
    implementation(libs.thirtyinch.rx2)
    implementation(libs.thirtyinch.kotlin)
    implementation(libs.thirtyinch.kotlin.coroutines)

    // retrofit2
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.adapter.rxjava2)
    implementation(libs.converter.scalars)

    // glide
    implementation(libs.glide)

    // ShapedImageView
    implementation(libs.shapedimageview)
//    implementation("io.woong.shapedimageview:shapedimageview:1.4.3")

    // bottom-navigation
    implementation(libs.bottom.navigation)

    // rx2
    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    // autodispose
//    implementation("com.uber.autodispose2:autodispose:2.1.1")
//    implementation("com.uber.autodispose2:autodispose-lifecycle:2.1.1")
//    implementation("com.uber.autodispose2:autodispose-android:2.1.1")
//    implementation("com.uber.autodispose2:autodispose-androidx-lifecycle:2.1.1")

    // okhttp3
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.3"))
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // stream
    implementation(libs.stream)

    // Toasty
    implementation(libs.toasty)

    // retaineddatetimepickers
    implementation(libs.retaineddatetimepickers)

    // material-about-library
    implementation(libs.material.about.library)

    // requery
//    implementation("io.requery:requery:1.6.0")
//    implementation("io.requery:requery-android:1.6.0")
//    kapt("io.requery:requery-processor:1.6.0")

    // about lib
    implementation(libs.aboutlibraries.core)
    implementation(libs.aboutlibraries)

    // HtmlSpanner
    implementation(libs.htmlspanner)
    // htmlcleaner !! 2.2> cause htmlparser to not work properly
    implementation(libs.htmlcleaner)


    // commonmark
    implementation(libs.commonmark)
    implementation(libs.commonmark.ext.autolink)
    implementation(libs.commonmark.ext.gfm.strikethrough)
    implementation(libs.commonmark.ext.gfm.tables)
    implementation(libs.commonmark.ext.ins)
    implementation(libs.commonmark.ext.yaml.front.matter)

    // kotlin std
    implementation(libs.kotlin.stdlib.jdk8)

    // jsoup
    implementation(libs.jsoup)

    // state
    implementation(libs.android.state)
    kapt(libs.android.state.processor)

    // color picker
    implementation(libs.colorpicker)

    // apollo3
    implementation(libs.apollo.runtime)
    implementation(libs.apollo.rx2.support)

    // device name
    implementation(libs.android.device.names)

    // keyboard
    implementation(libs.keyboardvisibilityevent)

    // lottie
    implementation(libs.lottie)

    // mmkv
    implementation(libs.mmkv)

    // androidx javax annotation
    implementation(libs.javax.annotation)
    implementation(libs.androidx.annotation)


    // shortbread
    implementation(libs.shortbread)
//    kapt("com.github.matthiasrobbers:shortbread-compiler:1.4.0")

    // objectbox
    implementation(libs.objectbox.kotlin)
    implementation(libs.objectbox.rxjava)
//    debugImplementation("io.objectbox:objectbox-android-objectbrowser:3.1.2")
    implementation(libs.objectbox.android)


    // cache
//    implementation("com.github.ben-manes.caffeine:caffeine:3.0.6")

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.assertj.core)
    androidTestImplementation(libs.mockito.mockito.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.espresso.intents)
    androidTestImplementation(libs.androidx.espresso.core)

    // 泄漏检测
//    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.8.1")
}
