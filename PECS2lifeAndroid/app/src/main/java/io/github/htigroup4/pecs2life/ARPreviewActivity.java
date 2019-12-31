/*
 * Copyright (C) of the original layout file: 2018 Google LLC. All Rights Reserved.
 * Copyright (C) of the edited file: 2019 hti-group4 (Arttu Ylhävuori, Louis Sosa and Tamilselvi Jayavelu).
 * Changes made to this file: added user inactivity detection feature.
 * Updated ModelRenderable to load from IntExtra & resource string.
 * Texture from another drawable file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.htigroup4.pecs2life;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
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
            Toast.makeText(ARPreviewActivity.this, R.string.user_inactive, Toast.LENGTH_LONG).show();
            finish(); // after 10 seconds, moving back to the main activity
        };

        startHandler();

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        //int position = getIntent().getIntExtra("position", 0);
        //int imageResource = getIntent().getIntExtra("imageResource", 0);

        String cardId = getIntent().getStringExtra("id");

        String[] foodIds = getResources().getStringArray(R.array.food_ids);

        int needed3DModelId = 0;


        for (int i = 0; i < foodIds.length; i++) {
            String foodId = foodIds[i];
            if (Objects.equals(cardId, foodId)) {
                needed3DModelId = i;
            }
        }

        TypedArray food3DModels =
                getResources().obtainTypedArray(R.array.models_in_3d);

        //TypedArray foodImageResources = getResources().obtainTypedArray(R.array.food_images);

//        int[] result = new int[foodImageResources.length()];
//
//        for (int i = 0; i < result.length; i++) {
//        }

        //foodImageResources.recycle();

        int resourceId = food3DModels.getResourceId(needed3DModelId, 0);

        food3DModels.recycle(); // a compulsory feature after previous rows

        // the following lines hide and disable the planeRenderer:
        arFragment.getPlaneDiscoveryController().hide();
        arFragment.getPlaneDiscoveryController().setInstructionView(null);
        arFragment.getArSceneView().getPlaneRenderer().setEnabled(false);

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
                .setSource(this, resourceId) // e.g. R.raw.andy
                .build()
                .thenAccept(this::onRenderableLoaded)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load Renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }

    void onRenderableLoaded(Renderable model) {
        AnchorNode anchorNode = new AnchorNode();
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        TransformableNode modelNode = new TransformableNode(arFragment.getTransformationSystem());
        modelNode.setParent(anchorNode);
        modelNode.setRenderable(model);
        modelNode.setLocalPosition(new Vector3(0f, 0f, -0.3f));
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
