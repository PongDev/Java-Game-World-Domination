package sound;

import config.Config;
import javafx.scene.media.MediaPlayer;
import utility.Logger;
import utility.ResourceManager;
import utility.ResourceManager.SoundResource;

/**
 * Sound Manager: Management Sound System
 */
public class SoundManager {

	/**
	 * Background Music SoundResource
	 */
	private static SoundResource bgmSoundResource;

	/**
	 * Set Current Background Music With Specific Volume
	 * 
	 * @param sound  Background Music SoundResource
	 * @param volume Sound Volume
	 */
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

	/**
	 * Set Current Background Music With Configured Sound Volume
	 * 
	 * @param sound Background Music SoundResource
	 */
	public static void setCurrentBGM(SoundResource sound) {
		setCurrentBGM(sound, Config.SOUND_VOLUME);
	}

	/**
	 * Play Sound Effect With Specific Volume
	 * 
	 * @param sound       Sound Effect SoundResource
	 * @param soundVolume Sound Volume
	 */
	public static void playSoundEffect(SoundResource sound, double soundVolume) {
		ResourceManager.getSound(sound).setVolume(soundVolume);
		ResourceManager.getSound(sound).play();
	}

	/**
	 * Play Sound Effect With Configured Sound Volume
	 * 
	 * @param sound Sound Effect SoundResource
	 */
	public static void playSoundEffect(SoundResource sound) {
		playSoundEffect(sound, Config.SOUND_VOLUME);
	}

	/**
	 * Get Current Background Music SoundResource
	 */
	public static SoundResource getCurrentBGMSoundResource() {
		return bgmSoundResource;
	}

}
