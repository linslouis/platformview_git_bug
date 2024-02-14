vec4 getVideoFilter(vec4 inputTexture2D) {
    const highp vec3 weight = vec3(0.2125, 0.7154, 0.0721);

    float luminance = dot(inputTexture2D.rgb, weight);
    return vec4(vec3(luminance), 1.0);
}