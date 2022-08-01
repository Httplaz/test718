#version 150
//#ifdef GL_ES
precision highp float;
//#endif

in vec4 v_color;
in vec2 v_texCoords;
uniform sampler2D colorTexture;
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

ivec2 relq[] = ivec2[2](ivec2(-1, 0), ivec2(1, 0));

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
    ivec3 tile = get(p);
    if(tile.z>0)
    {
        gl_FragColor = color;
        return;
    }
    float difference = 0;
    for (int i=0; i<8; i++)
    {
        ivec2 pos1 = p+rel[i];
        vec4 color1 = getColor(p+rel[i]);
        ivec3 tile1 = get(p+rel[i]);
        if (tile1.z == 0 && pos1.x>=1 && pos1.y>=1 && pos1.x<255 && pos1.y<255)
        {
            //color.rgb = min(color.rgb, color1.rgb);
            vec4 d1 = color1-color;
            if(d1.a>0.03)
                difference+=0.01;
            if(d1.a<-0.03)
                difference-=0.01;
            if(d1.x<=-0.01 && d1.a>=0.01)
                color.x-=.01;
            if(d1.y<=-0.01 && d1.a>=0.01)
                color.y-=.01;
            if(d1.z<=-0.01 && d1.a>=0.005)
                color.z-=.01;
        }
    }
    color.a += difference;
    gl_FragColor = color;
}