#version 150
//#ifdef GL_ES
//precision mediump float;
//#endif

in vec4 v_color;
in vec2 v_texCoords;
uniform isampler2D u_texture;
uniform sampler2D v_texture;
uniform sampler2D tileset;
uniform float aspectRatio;
uniform vec2 camPos;
uniform vec2 startPoint;
uniform vec2 stopPoint;
uniform int buildingTile;
uniform vec2 cursorPos;
uniform float scale;
uniform float mapSize;


vec4 color = vec4(0.8,0.6,0.3,1.);
vec2 tilesetSize = vec2(8.,8.);

void main()
{
    vec2 u_texCoords = v_texCoords*vec2(aspectRatio, -1.)+vec2(0.,1.);
    //gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
    vec2 tilePos = fract(((u_texCoords-vec2(0.5*aspectRatio, 0.5))*scale)+camPos);
    vec2 tileOnMap = ((u_texCoords-vec2(0.5*aspectRatio, 0.5))*scale)+camPos;
    ivec4 res = texelFetch(u_texture, ivec2(tileOnMap), 0);
    int tile = res.r;
    //tile = 2;
    //tile = 0.;
    //gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
    //color = (texture2D(v_texture, vec2(tile/tilesetSize.x, 0.) + tilePos/tilesetSize+vec2(0.001, 0.0)));
    int po = 1;
    if(ceil(tileOnMap.x) > startPoint.x && ceil(tileOnMap.y) > startPoint.y && ceil(tileOnMap.x) <= stopPoint.x+1 && ceil(tileOnMap.y) <= stopPoint.y+1)
        tile = buildingTile;
    if(tile!=0)
        color = texelFetch(tileset, ivec2(tile*16, 0) + ivec2(tilePos.x*16, tilePos.y*16), 0);
    else
        color = vec4(0);
    //+texture2D(v_texture, vec2(tile/tilesetSize.x, 0.) + tilePos/tilesetSize+vec2(0.0, -0.001))
    //+texture2D(v_texture, vec2(tile/tilesetSize.x, 0.) + tilePos/tilesetSize+vec2(-0.001, 0.0))
    //+texture2D(v_texture, vec2(tile/tilesetSize.x, 0.) + tilePos/tilesetSize+vec2(0.0, 0.0))
    //+texture2D(v_texture, vec2(tile/tilesetSize.x, 0.) + tilePos/tilesetSize+vec2(0.0, 0.001)))/5.;
    if(tileOnMap.x<0. || tileOnMap.y<0. || tileOnMap.x>mapSize || tileOnMap.y>mapSize || startPoint.x == 0.)
        color = vec4(1., 0.6, 0.8, 1.);
    //color = vec4(tile, 1, 0, 1);
    //if(distance(vec2(cursorPos.x*aspectRatio, cursorPos.y), u_texCoords)>0.3)
        //color = vec4(1., 0.6, 0.8, 1.);
    //color.r = distance(stopPoint, tileOnMap)/32./8.;
    //color.r*=1.6;
    color.gba/=1.6;
    gl_FragColor = color;
}