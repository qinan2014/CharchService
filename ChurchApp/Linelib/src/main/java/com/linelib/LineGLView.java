package com.linelib;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.linelib.openglLib.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by qinan on 2017/11/23.
 */

public class LineGLView extends GLSurfaceView {
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private SceneRenderer mRenderer;
    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置X坐标

    public LineGLView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2); // GLES2.0
        mRenderer = new SceneRenderer();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        float x = event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                float dy = y - mPreviousY;
                float dx = x - mPreviousX;
                for (SixPointedStar h : mRenderer.ha){
                    h.yAngle += dx * TOUCH_SCALE_FACTOR;
                    h.xAngle += dy * TOUCH_SCALE_FACTOR;
                }
                break;
        }
        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    private class SceneRenderer implements GLSurfaceView.Renderer{
        SixPointedStar[] ha=new SixPointedStar[6];//六角星数组
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
            for (int i = 0; i < ha.length; ++i){
                ha[i] = new SixPointedStar(LineGLView.this.getResources(), 0.2f, 0.5f, -0.3f * i);
            }
            // 深度测试
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
            float ratio = (float ) width / height;
            // 平行投影
            MatrixState.setProjectOrtho(-ratio, ratio, -1, 1, 1, 10);
            // 摄像机位置矩阵
            MatrixState.setCamera(0f, 0f, 3f,
                    0f, 0f, 0f,
                    0f, 1f, 0.0f);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            for (SixPointedStar h : ha){
                h.drawSelf();
            }
        }
    }
}
