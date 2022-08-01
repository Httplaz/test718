package com.httplaz.diefromfire;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends Game {
	
	@Override
	public void create () {
		setScreen(new MainScreen());
	}

	@Override
	public void render () {
		this.screen.render(0);
		//ScreenUtils.clear(1, 0, 0, 1);
		//batch.begin();
		//batch.draw(img, 0, 0);
		//batch.end();
	}
	
	@Override
	public void dispose () {
	}
}
