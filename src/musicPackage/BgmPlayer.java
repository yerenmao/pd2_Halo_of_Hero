package musicPackage;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BgmPlayer {
		
	private Clip clip;
	public static BgmPlayer background;
		
	public BgmPlayer(String s, int n) {
		
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
			clip.loop(n);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public BgmPlayer(String s) {
			
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
			clip.loop(10000);
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