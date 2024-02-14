vec4 getVideoFilter(vec4 inputTexture2D){
    precision mediump float;
    lowp vec4 color = inputTexture2D;
    return vec4((1.0 - color.rgb), color.w);
}