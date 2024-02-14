precision lowp float;

const mediump vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);

vec4 getVideoFilter(vec4 inputTexture2D){


    float luminance = dot(inputTexture2D.rgb, luminanceWeighting);

    lowp vec4 desat = vec4(vec3(luminance), 1.0);

    lowp vec4 outputColor = vec4(
    (desat.r < 0.5 ? (2.0 * desat.r * 0.6) : (1.0 - 2.0 * (1.0 - desat.r) * (1.0 - 0.6))),
    (desat.g < 0.5 ? (2.0 * desat.g * 0.45) : (1.0 - 2.0 * (1.0 - desat.g) * (1.0 - 0.45))),
    (desat.b < 0.5 ? (2.0 * desat.b * 0.3) : (1.0 - 2.0 * (1.0 - desat.b) * (1.0 - 0.3))),
    1.0
    );

    return vec4(mix(inputTexture2D.rgb, outputColor.rgb, 1.0), inputTexture2D.a);
}