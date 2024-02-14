
#extension GL_OES_EGL_image_external : require
precision mediump float;

uniform samplerExternalOES fromTex;
uniform samplerExternalOES toTex;

uniform float progress;
uniform float smoothness;
float ratio=1.0;
float ratio2=1.0;



bool opening = true;

varying vec2 v_texcoord;

vec4 transition (vec2 p);

void main() {
    gl_FragColor = transition(v_texcoord);
}

vec4 getFromColor(vec2 coordinate) {
    return texture2D(fromTex, vec2(coordinate.x, coordinate.y));
}

vec4 getToColor(vec2 coordinate) {
    vec2 adjustedCoor = 0.5 + (coordinate - 0.5) * vec2(min(ratio / ratio2, 1.0), min(ratio2 / ratio, 1.0));
    vec2 sampleCoor = vec2(adjustedCoor.x, adjustedCoor.y);
    return texture2D(toTex, sampleCoor);
}



//
//const vec2 center = vec2(0.5, 0.5);
//const float SQRT_2 = 1.414213562373;
//
//vec4 transition (vec2 uv) {
//
//    float x = opening ? progress : 1.-progress;
//    float m = smoothstep(-smoothness, 0.0, SQRT_2*distance(center, uv) - x*(1.+smoothness));
//    return mix(getVideoFilter(getFromColor(uv)), getToColor(uv), opening ? 1.-m : m);
//}

