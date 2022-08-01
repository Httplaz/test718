#version 150
//#ifdef GL_ES
precision highp float;
//#endif

in vec4 v_color;
in vec2 v_texCoords;
uniform sampler2D colorTexture;
uniform isampler2D u_texture;
uniform sampler2D tileset;
uniform float aspectRatio;
uniform vec2 camPos;
uniform vec2 cursorPos;
uniform float scale;
uniform float mapSize;
uniform bool low;
uniform bool high;
uniform bool wall;
uniform float time;

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

vec4 water(vec2 uv)
{
    vec4 texture_color = vec4(0.15, 0.54, 0.9, 1.0);

    vec4 k = vec4(time)*0.2;
    k.xy = uv * 7.0;
    float val1 = length(0.5-fract(k.yxw*=mat3(vec3(-2.0,-1.0,0.0), vec3(3.0,-1.0,1.0), vec3(1.0,-1.0,-1.0))*0.2));
    float val2 = length(0.5-fract(k.xyw*=mat3(vec3(-2.0,-1.0,0.0), vec3(3.0,-1.0,1.0), vec3(1.0,-1.0,-1.0))*0.2));
    float val3 = length(0.5-fract(k.xyw*=mat3(vec3(-2.0,-1.0,0.0), vec3(3.0,-1.0,1.0), vec3(1.0,-1.0,-1.0))*0.5));
    vec4 color = vec4 ( pow(min(min(val1,val2),val3), 7.0) * 3.0)+texture_color;
    return color;
}

vec4 getColorRaw(ivec2 n)
{
    vec4 rgba = texelFetch(colorTexture, n, 0);
    return rgba;
}

vec4 getColor(ivec2 n)
{
    float division = 1.;
    vec4 rgba = getColorRaw(n);
    //for (int i=0; i<8; i++)
        //if(get(n).z==tile.z)
    vec3 iv = vec3(1.)-vec3(rgba.rbg);
    //return vec4(vec3(1.)-iv, 1);
    if(rgba.a>0.01 && rgba.a<0.05)
        rgba.a = 0.05;
    return vec4(rgba.rgb, rgba.a);
}

vec4 pixel(ivec2 n)
{
    return texelFetch(tileset, n, 0);
}

vec4 updateColor(ivec2 d, ivec2 tileOnMap, vec2 tilePos, vec4 color, ivec2 p)
{
    if(get(tileOnMap+d).x>=3 && get(tileOnMap+d).x!=6)
    {
        return pixel(p + ivec2(tilePos.x*16, 16-tilePos.y*16)) + color*(1. - pixel(p + ivec2(tilePos.x*16, 16-tilePos.y*16)).a);
    }
    else
        return color;
}

vec4 updateColor2(ivec2 d, ivec2 tileOnMap, vec2 tilePos, vec4 color)
{
    ivec3 ntile = get(tileOnMap+d);
    ivec3 tile = get(tileOnMap);
    vec2 dist = abs((tilePos*2.-vec2(1.)) - vec2(d))/1.;
    //float f = atan(dist.x/dist.y);
    //if(dist.x>0.5)
        //dist.x = 1.;
    //if(dist.y>0.5)
        //dist.y = 1.;
    vec2 value = vec2(d.x==0? 0.:1., d.y==0? 0.:1.);
    dist = dist*value;
    //float distValue = (dist.x*value.x + dist.y*value.y);
    //if(length(value)>1.)
    //{
        dist.x = pow(dist.x*4., 2)/4.;
        dist.y = pow(dist.y*4., 2)/4.;
        //dist-=0.5;
        float distValue = min(length(dist), 1.);
    //}

    if(ntile.z>tile.z)
    {
        color = mix(pixel(ivec2(ntile.x*16, ntile.y*16)+ ivec2(tilePos.x*16, 16-tilePos.y*16)), vec4(0.), pow(distValue, 1./ntile.z));
        if(color.a>0.)
            color.a=ntile.z;
        return vec4(color.rgba);
    }
    return vec4(0.);
}

void main()
{
    vec2 u_texCoords = v_texCoords*vec2(aspectRatio, -1.)+vec2(0.,1.);
    //gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
    vec2 tilePos = fract(((u_texCoords-vec2(0.5*aspectRatio, 0.5))*scale)+camPos);
    ivec2 tileOnMap = ivec2(((u_texCoords-vec2(0.5*aspectRatio, 0.5))*scale)+camPos);
    ivec3 tile = get(tileOnMap);
    //tile = min(tile, 4);
    //tile = max(tile, 0);
    if((low && tile.z<=2) || (high && tile.z>=3))
    {
        if(tile.z==0)
            color = water(tilePos/5.+vec2(tileOnMap)/5.);
        else
            color = pixel(ivec2(tile.x*16, tile.y*16) + ivec2(16-tilePos.x*16, 16-tilePos.y*16));
        vec4 tcolor = getColor(tileOnMap);
        color.xyz = mix(color.xyz, tcolor.xyz, tcolor.a*(tile.z==0? 0.8:0.5));
    }
    else
    {
        color = vec4(0);
        //return;
    }
    if(color.a<=0.5)
    {
        //gl_FragColor = vec4(1.,0.,0.,0.);
        //return;
    }

    //if(tile<3)
    {


        vec4 dcolor = vec4(0);
        float times = 0.;
        float maxTile = 0.;
        if(wall)
            for (int i=7; i>=0; i--)
            {
                vec4 delta = vec4(0);
                if(length(rel[i])<1.1 || (get(tileOnMap+rel[i]*ivec2(1,0)).z<=tile.z && get(tileOnMap+rel[i]*ivec2(0,1)).z<=tile.z))
                delta = updateColor2(rel[i], tileOnMap, tilePos, color);
                if(delta.a>0.)
                {
                    vec4 tcolor = getColor(tileOnMap+rel[i]);
                    delta.xyz = mix(delta.xyz, tcolor.xyz, tcolor.a*0.8);
                    if (delta.a>maxTile)
                    {
                        maxTile = delta.a, dcolor.rgb = delta.rgb;
                    }
                    if(delta.a==maxTile)
                    {
                        dcolor.rgb = max(dcolor.rgb, delta.rgb);
                    }
                    dcolor.a = 1.;
                }
            }

        //dcolor/=times;
        if(dcolor.a>0.5)
            color = dcolor;

    }
    if(tileOnMap.x<0. || tileOnMap.y<0. || tileOnMap.x>mapSize || tileOnMap.y>mapSize)
        color = vec4(1., 0.6, 0.8, 1.);
    gl_FragColor = color;
}