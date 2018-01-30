package com.signature_board.jay.signatureboard;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by jay on 30/01/18.
 */

public class DrawingClass {
    Path DrawingClassPath;
    Paint DrawingClassPaint;

    public Path getPath() {
        return DrawingClassPath;
    }

    public void setPath(Path path) {
        this.DrawingClassPath = path;
    }


    public Paint getPaint() {
        return DrawingClassPaint;
    }

    public void setPaint(Paint paint) {
        this.DrawingClassPaint = paint;
    }
}
