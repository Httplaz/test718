#version 150
//#ifdef GL_ES
precision highp float;
//#endif

in vec4 v_color;
in vec2 v_texCoords;
uniform sampler2D alphaTexture;
uniform isampler2D u_texture;
uniform sampler2D tileset;
uniform int size;
//uniform float time;

ivec2 rel[] = ivec2[8](ivec2(1,1),
ivec2(1,-1),
ivec2(-1,1),
ivec2(-1,-1),
ivec2(0,1),
ivec2(0, -1),
ivec2(1, 0),
ivec2(-1, 0) );


vec4 color = vec4(0.8,0.6,0.3,1.);
vec2 tilesetSize = vec2(8.,8.);

ivec3 get(ivec2 n)
{
    int info = texelFetch(u_texture, n, 0).r;
    int z = info/1048576;
    info%=1048576;
    int y = info/1024;
    info%=1024;
    int x = info;
    return ivec3(x,y,z);
}

vec4 getColor(ivec2 p)
{
    return vec4(texelFetch(colorTexture, p, 0));
}

void main()
{
    ivec2 p = ivec2(v_texCoords*size);
    vec4 color = getColor(p);
    vec4 color1 = getColor(p+ivec2(0, 1));
    ivec3 tile = get(p);
    ivec3 tile1 = get(p+ivec2(0, 1));
    if(color1.b<color.b && tile.z==tile1.z)
    color = color1;
    color = vec4(color.a);
    gl_FragColor = color;
}