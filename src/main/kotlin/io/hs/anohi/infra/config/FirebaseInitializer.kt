package io.hs.anohi.infra.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream

@Configuration
class FirebaseInitializer {

    @Bean
    fun firebaseApp(): FirebaseApp? {
        val fileInputStream =
            this.javaClass.getClassLoader().getResource("anohinonikki-firebase-adminsdk-yq9nu-df5178cd09.json")?.path?.let {
                FileInputStream(
                    it
                )
            }
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(fileInputStream))
            .build()

        return FirebaseApp.initializeApp(options)
    }

    @Bean
    fun getFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance(firebaseApp())
    }

}