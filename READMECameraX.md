# Camera x

dependencias:

    //Camera X
    implementation ("androidx.camera:camera-core:${cameraVersion}")
    implementation ("androidx.camera:camera-camera2:${cameraVersion}")
    implementation ("androidx.camera:camera-lifecycle:${cameraVersion}")
    implementation ("androidx.camera:camera-view:${cameraVersion}")
    implementation ("androidx.camera:camera-extensions:${cameraVersion}")

Permisos:

    <uses-feature android:name="android.hardware.camera.any" />
    <uses-permission android:name="android.permission.CAMERA"/>

checar los permisos de camera y solicitarlos



**NOTA : Cuando tengamos un error extraño y que no sepamos hacemos el clean y build de la app**

