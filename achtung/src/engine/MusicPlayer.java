package engine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;

/**
 * Class for playing sounds
 */
public final class MusicPlayer
{

	private MusicPlayer() {}

	public static synchronized void startOfRound() {
			new Thread(() -> {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream =
						AudioSystem.getAudioInputStream(new File("src/resources/sounds/startOfRound.wav"));
					clip.open(inputStream);
					clip.start();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}).start();
		}

}
