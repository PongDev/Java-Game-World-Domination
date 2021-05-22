package sound;

import config.Config;
import javafx.scene.media.MediaPlayer;
import utility.Logger;
import utility.ResourceManager;
import utility.ResourceManager.SoundResource;

public class SoundManager {

	private static SoundResource bgmSoundResource;

	public static void setCurrentBGM(SoundResource sound, double volume) {
		if (bgmSoundResource != null) {
			ResourceManager.getSound(bgmSoundResource).stop();
		}
		bgmSoundResource = sound;
		ResourceManager.getSound(bgmSoundResource).setVolume(volume);
		ResourceManager.getSound(bgmSoundResource).setCycleCount(MediaPlayer.INDEFINITE);
		ResourceManager.getSound(bgmSoundResource).play();
		Logger.log("Playing Sound " + bgmSoundResource.name());
	}

	public static void setCurrentBGM(SoundResource sound) {
		setCurrentBGM(sound, Config.SOUND_VOLUME);
	}

	public static void playSoundEffect(SoundResource sound, double soundVolume) {
		ResourceManager.getSound(sound).setVolume(soundVolume);
		ResourceManager.getSound(sound).play();
	}

	public static void playSoundEffect(SoundResource sound) {
		playSoundEffect(sound, Config.SOUND_VOLUME);
	}

	public static SoundResource getCurrentBGMSoundResource() {
		return bgmSoundResource;
	}

}
