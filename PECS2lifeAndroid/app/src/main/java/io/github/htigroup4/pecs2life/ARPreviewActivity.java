package io.github.htigroup4.pecs2life;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.PlaneRenderer;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Objects;

public class ARPreviewActivity extends AppCompatActivity {
    Handler handler;
    Runnable r;

    private static final String TAG = ARPreviewActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private ModelRenderable modelRenderable;

//    private SceneView sceneView;
//    private Scene scene;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    // CompletableFuture requires api level 24
    // FutureReturnValueIgnored is not valid
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        setContentView(R.layout.activity_arpreview);

        handler = new Handler();


        r = () -> {
            Toast.makeText(ARPreviewActivity.this, "User was inactive in last 10 seconds", Toast.LENGTH_SHORT).show();
            //finish(); // after 10 seconds, moving back to the main activity
        };

        startHandler();

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        int position = getIntent().getIntExtra("position", 0);

        TypedArray PECSCardsImageResources =
                getResources().obtainTypedArray(R.array.models_in_3d);

        int resourceId = PECSCardsImageResources.getResourceId(position, 0);

        PECSCardsImageResources.recycle();


        // How to change plane detection colour:
//        arFragment.getArSceneView().getPlaneRenderer()
//                .getMaterial().thenAccept(material ->
//                material.setFloat3(PlaneRenderer.MATERIAL_COLOR, new Color(0.0f, 0.0f, 1.0f, 1.0f)));


        // Customize plane visualization:
        Texture.Sampler sampler =
                Texture.Sampler.builder()
                        .setMinFilter(Texture.Sampler.MinFilter.LINEAR)
                        .setWrapMode(Texture.Sampler.WrapMode.REPEAT)
                        .build();

        // R.drawable.custom_texture is a .png file in src/main/res/drawable
        Texture.builder()
                .setSource(this, R.drawable.square_bg)
                .setSampler(sampler)
                .build()
                .thenAccept(texture -> arFragment.getArSceneView().getPlaneRenderer()
                        .getMaterial().thenAccept(material ->
                                material.setTexture(PlaneRenderer.MATERIAL_TEXTURE, texture)));


        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        ModelRenderable.builder()
                .setSource(this, resourceId)//R.raw.andy
//                .setSource(this, Uri.parse("hand_rigged.sfb"))
                .build()
//                .thenAccept(renderable -> modelRenderable = renderable)
                .thenAccept(this::onRenderableLoaded)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load Renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });


        // the following lines hide and disable the planeRenderer:
//        arFragment.getPlaneDiscoveryController().hide();
//        arFragment.getPlaneDiscoveryController().setInstructionView(null);
//        arFragment.getArSceneView().getPlaneRenderer().setEnabled(false);


        // add the renderable when tapping the plane area:
//        arFragment.setOnTapArPlaneListener(
//                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
//                    if (modelRenderable == null) {
//                        return;
//                    }
//
//                    // Create the Anchor.
//                    Anchor anchor = hitResult.createAnchor();
//                    AnchorNode anchorNode = new AnchorNode(anchor);
//                    anchorNode.setParent(arFragment.getArSceneView().getScene());
//
//                    // Create the transformable 3D model and add it to the anchor.
//                    TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
//                    transformableNode.setParent(anchorNode);
//                    transformableNode.setRenderable(modelRenderable);
//                    transformableNode.select();
//                });

    }

    void onRenderableLoaded(Renderable model) {
//        AnchorNode anchorNode = new AnchorNode();
//        anchorNode.setParent(arFragment.getArSceneView().getScene());

        TransformableNode modelNode = new TransformableNode(arFragment.getTransformationSystem());
        modelNode.setParent(arFragment.getArSceneView().getScene());
//        modelNode.setParent(anchorNode);
        modelNode.setRenderable(model);
//        modelNode.select();
        modelNode.setLocalPosition(new Vector3(0, 0, 0));
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        stopHandler();
        startHandler();
    }

    public void stopHandler() {
        handler.removeCallbacks(r);
    }

    public void startHandler() {
        handler.postDelayed(r, 10 * 1000); // 10 seconds = 10 * 1000 ms
    }

    /**
     * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
     * on this device.
     *
     * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
     *
     * <p>Finishes the activity if Sceneform can not run
     */
    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        String openGlVersionString =
                ((ActivityManager) Objects.requireNonNull(activity.getSystemService(Context.ACTIVITY_SERVICE)))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }
}
