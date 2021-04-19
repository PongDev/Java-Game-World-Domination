package sound;

import config.Config;
import utility.Logger;
import utility.ResourceManager;
import utility.ResourceManager.SoundResource;

public class SoundManager {

	private static SoundResource soundResource;

	public static void setCurrentSound(SoundResource sound) {
		if (soundResource != null) {
			ResourceManager.getSound(soundResource).stop();
		}
		soundResource = sound;
		ResourceManager.getSound(soundResource).setVolume(Config.SOUND_VOLUME);
		ResourceManager.getSound(soundResource).play();
		Logger.log("Playing Sound " + soundResource.name());
	}

	public static SoundResource getCurrentSoundResource() {
		return soundResource;
	}

}
