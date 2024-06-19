package com.example.foodiefrontend.presentation.ui.screens.camera

import android.Manifest
import android.media.ToneGenerator
import android.util.Log
import android.view.SoundEffectConstants
import android.view.ViewGroup
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.hardware.buzzer.Buzzer
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(navController: NavController, navigateToScreen: (String?) -> Unit) {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    var camera: Camera? by remember { mutableStateOf(null) }
    var flashEnabled by remember { mutableStateOf(false) }
    var isBarcodeProcessed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    DisposableEffect(Unit) {
        onDispose {
            isBarcodeProcessed = false
        }
    }

    if (permissionState.status.isGranted) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    val previewView = PreviewView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }

                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }

                        val imageAnalyzer = ImageAnalysis.Builder().build().also { imageAnalysis ->
                            imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                                processImageProxy(
                                    imageProxy,
                                    navigateToScreen,
                                    isBarcodeProcessed
                                ) {
                                    isBarcodeProcessed = true
                                }
                            }
                        }

                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                        try {
                            cameraProvider.unbindAll()
                            camera = cameraProvider.bindToLifecycle(
                                lifecycleOwner, cameraSelector, preview, imageAnalyzer
                            )
                        } catch (exc: Exception) {
                            Log.e("CameraScreen", "Binding failed", exc)
                        }
                    }, ContextCompat.getMainExecutor(context))

                    previewView
                }
            )

            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .padding(top = 32.dp, start = 16.dp)
            ) {
                ImageWithResource(
                    resourceId = R.drawable.ic_chevron_left,
                    contentDescription = "Cancel scan",
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.height(20.dp),
                    onClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    } else {
        Text(text = "Permiso denegado")
    }
}

@Composable
fun CameraBackBtn(navController: NavController) {
    val view = LocalView.current
    IconButton(
        onClick = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            navController.popBackStack()
        },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Black
        ),
        modifier = Modifier
            .padding(top = 32.dp, start = 16.dp)
            .size(45.dp)
            .clip(CircleShape)
            .shadow(elevation = 2.dp),
    ) {
        ImageWithResource(
            resourceId = R.drawable.ic_chevron_left,
            contentDescription = "Cancel scan",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.height(20.dp)
        )
    }
}

@androidx.annotation.OptIn(ExperimentalGetImage::class)
private fun processImageProxy(
    imageProxy: ImageProxy,
    navigateToScreen: (String?) -> Unit,
    isBarcodeProcessed: Boolean,
    onBarcodeProcessed: () -> Unit
) {
    if (isBarcodeProcessed) {
        imageProxy.close()
        return
    }

    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        val scanner = BarcodeScanning.getClient()

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    // Log barcode data
                    when (barcode.valueType) {
                        Barcode.TYPE_PRODUCT -> {
                            val rawValue = barcode.rawValue

                            val buzzerInstance = AndroidBuzzer()
                            // buzzerInstance.beep()

                            Log.d("Barcode", "Detected EAN: $rawValue")

                            navigateToScreen(rawValue)

                            onBarcodeProcessed()

                            // Cierra cámara
                            imageProxy.close()
                            return@addOnSuccessListener
                        }

                        else -> {
                            Log.d("Barcode", "Detected non-EAN barcode")
                        }
                    }
                }
                imageProxy.close() // Ensure we close the proxy here
            }
            .addOnFailureListener {
                // Handle failure
                Log.e("Barcode", "Barcode scanning failed", it)
                imageProxy.close() // Ensure we close the proxy here
            }
            .addOnCompleteListener {
                // Close the imageProxy to avoid memory leaks
                imageProxy.close()
            }
    } else {
        imageProxy.close()
    }
}

class AndroidBuzzer : Buzzer {

    private val toneGenerator: ToneGenerator? = try {
        ToneGenerator(ToneGenerator.TONE_PROP_BEEP, 100)
    } catch (e: Exception) {
        null
    }

    override fun beep(count: Int, time: Int, interval: Int) {
        toneGenerator?.let { generator ->

            /*repeat(count) {
                generator.startTone(ToneGenerator.TONE_PROP_BEEP, time)
                Thread.sleep((time + interval).toLong())
            }*/

            //ver si esta ok
            Thread {
                try {
                    repeat(count) {
                        generator.startTone(ToneGenerator.TONE_PROP_BEEP, time)
                        Thread.sleep((time + interval).toLong())
                    }
                } catch (e: InterruptedException) {
                    Log.d("AndroidBuzzer", "Buzzer interrupted", e)
                } catch (e: Exception) {
                    Log.d("AndroidBuzzer", "Buzzer error", e)
                } finally {
                    generator.release()
                }
            }.start()
        }
    }
}
