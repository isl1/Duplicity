package audio;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;
import paulscode.sound.libraries.LibraryJavaSound;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.codecs.CodecJOgg;
/**
 * Plays background music and explosions in 3D.
 **/
public class Master {

	public Master(){
		// Load some library and codec plug-ins:
		try
		{
			SoundSystemConfig.addLibrary( LibraryLWJGLOpenAL.class );
			SoundSystemConfig.addLibrary( LibraryJavaSound.class );
			SoundSystemConfig.setCodec( "wav", CodecWav.class );
			SoundSystemConfig.setCodec( "ogg", CodecJOgg.class );
		}
		catch( SoundSystemException e )
		{
			System.err.println("error linking with the plug-ins" );
		}
		// Instantiate the SoundSystem:
		SoundSystem mySoundSystem = new SoundSystem();
		// play some background music:
		mySoundSystem.backgroundMusic( "Music 1", "192Classical.wav", true );

		mySoundSystem.quickPlay( false, "explosion.wav", false,
				20, 0, 0,
				SoundSystemConfig.ATTENUATION_ROLLOFF,
				SoundSystemConfig.getDefaultRolloff());
	}
}