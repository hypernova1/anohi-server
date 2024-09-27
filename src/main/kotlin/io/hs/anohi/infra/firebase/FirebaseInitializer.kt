package io.hs.anohi.infra.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader

@Configuration
class FirebaseInitializer {

    @Autowired
    lateinit var resourceLoader: ResourceLoader

    @Bean
    fun firebaseApp(): FirebaseApp? {
        val fileInputStream = resourceLoader.getResource("classpath:anohinonikki-firebase-adminsdk-yq9nu-df5178cd09.json").inputStream
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(fileInputStream))
            .build()

        return FirebaseApp.initializeApp(options, "anohi")
    }

    @Bean
    fun getFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance(firebaseApp())
    }

}