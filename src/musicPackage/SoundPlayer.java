package musicPackage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
	private Clip clip;
	public static SoundPlayer effect;
		
	public SoundPlayer(String s) {
			
		try {
			AudioInputStream sound = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
			AudioFormat baseFormat = sound.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			AudioInputStream dsound = AudioSystem.getAudioInputStream(decodeFormat, sound);
			clip = AudioSystem.getClip();
			clip.open(dsound);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			
	}
		
	public void play() {
		if(clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}
		
	public void stop() {
		if(clip.isRunning()) clip.stop();
	}
		
	public void close() {
		stop();
		clip.close();
	}		
}