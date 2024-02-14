precision highp float;


vec4 getVideoFilter(vec4 inputTexture2D){

    float pixel = 1f;
    float imageWidthFactor = 1f / 720;
    float imageHeightFactor = 1f / 720;
    vec2 uv  = vTextureCoord.xy;
    float dx = pixel * imageWidthFactor;
    float dy = pixel * imageHeightFactor;
    vec2 coord = vec2(dx * floor(uv.x / dx), dy * floor(uv.y / dy));
    vec3 tc = texture2D(sTexture, coord).xyz;
    return vec4(tc, 1.0);
}