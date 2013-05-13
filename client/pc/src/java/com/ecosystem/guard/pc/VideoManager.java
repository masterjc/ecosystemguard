package com.ecosystem.guard.pc;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

import com.ecosystem.guard.camera.Resolution;
import com.ecosystem.guard.camera.impl.VideoCodec;
import com.ecosystem.guard.camera.impl.VideoContainer;
import com.ecosystem.guard.common.RandomGenerator;
import com.ecosystem.guard.common.StreamServiceRequestor;
import com.ecosystem.guard.domain.service.app.RecordVideoRequest;
import com.ecosystem.guard.domain.service.app.VideoConfiguration;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class VideoManager {
	private Scanner scanner = new Scanner(System.in);

	public void recordVideo(Session session) throws Exception {
		ClientOutput.printLogo();
		VideoContainer container = requestVideoContainer();
		VideoCodec codec = requestVideoCodec();
		Resolution resolution = requestResolution(codec);
		int length = requestVideoLength();

		RecordVideoRequest request = new RecordVideoRequest();
		request.setCredentials(session.getCredentials());
		VideoConfiguration config = new VideoConfiguration();
		config.setCodec(codec.name());
		config.setContainer(container.name());
		config.setResolution(resolution.getAbbreviation());
		request.setLength(length);

		File video = new File(RandomGenerator.generateRandom(20) + container.getExtension());
		try {
			ClientOutput.printSeparatorLine();
			System.out.print("** Recording video from your EcosystemGuard host. Please wait a minimum of " + length
					+ " seconds...");
			FileOutputStream outputPic = new FileOutputStream(video);
			StreamServiceRequestor.sendRequest(request, RecordVideoRequest.class, session.getAppUrl()
					+ ClientConstants.RECORD_VIDEO_SERVICE, outputPic);
			outputPic.close();
			System.out.println("OK");
			Desktop.getDesktop().open(video);
			System.out.println("** Video '" + video.getName()
					+ "' should be open with your default player desktop application.");
		}
		finally {
			video.deleteOnExit();
			ClientOutput.printSeparatorLine();
		}
	}

	private VideoContainer requestVideoContainer() throws Exception {
		OptionSelections<VideoContainer> videoContainerOptions = showVideoContainer();
		System.out.println();
		System.out.print("-> Select video container: ");
		int selection = Integer.parseInt(scanner.nextLine());
		VideoContainer videoContainer = videoContainerOptions.getSelection(selection);
		if (videoContainer == null)
			throw new Exception("Incorrect video container selected - " + selection);
		return videoContainer;
	}

	private Resolution requestResolution(VideoCodec videoCodec) throws Exception {
		OptionSelections<Resolution> videoResolutionOptions = showVideoResolution(videoCodec);
		System.out.println();
		System.out.print("-> Select video resolution: ");
		int selection = Integer.parseInt(scanner.nextLine());
		Resolution videoResolution = videoResolutionOptions.getSelection(selection);
		if (videoResolution == null)
			throw new Exception("Incorrect video resolution selected - " + selection);
		return videoResolution;
	}

	private VideoCodec requestVideoCodec() throws Exception {
		OptionSelections<VideoCodec> videoCodecOptions = showVideoCodec();
		System.out.println();
		System.out.print("-> Select video codec: ");
		int selection = Integer.parseInt(scanner.nextLine());
		VideoCodec videoCodec = videoCodecOptions.getSelection(selection);
		if (videoCodec == null)
			throw new Exception("Incorrect video codec selected - " + selection);
		return videoCodec;
	}

	private int requestVideoLength() {
		System.out.println();
		System.out.print("-> Enter video length in seconds: ");
		return Integer.parseInt(scanner.nextLine());
	}

	private OptionSelections<VideoCodec> showVideoCodec() {
		System.out.println();
		System.out.println("Video codec selection");
		System.out.println("---------------------");
		OptionSelections<VideoCodec> selection = new OptionSelections<VideoCodec>();
		VideoCodec[] videoCodecs = VideoCodec.values();
		int option = 1;
		for (int i = 0; i < videoCodecs.length; i++) {
			VideoCodec videoCodec = videoCodecs[i];
			System.out.println(option + ". " + videoCodec.getCodec());
			selection.add(new OptionSelection<VideoCodec>(option++, videoCodec));
		}
		return selection;
	}

	private OptionSelections<Resolution> showVideoResolution(VideoCodec videoCodec) {
		System.out.println();
		System.out.println("Video resolution selection");
		System.out.println("--------------------------");
		OptionSelections<Resolution> selection = new OptionSelections<Resolution>();
		Resolution[] resolutions = videoCodec.getResolutionClass().getEnumConstants();
		int option = 1;
		for (int i = 0; i < resolutions.length; i++) {
			Resolution resolution = resolutions[i];
			System.out.println(option + ". " + resolution.getAbbreviation() + "(" + resolution.getResolution() + ")");
			selection.add(new OptionSelection<Resolution>(option++, resolution));
		}
		return selection;
	}

	private OptionSelections<VideoContainer> showVideoContainer() {
		System.out.println();
		System.out.println("Video container selection");
		System.out.println("-------------------------");
		OptionSelections<VideoContainer> selection = new OptionSelections<VideoContainer>();
		VideoContainer[] videoContainers = VideoContainer.values();
		int option = 1;
		for (int i = 0; i < videoContainers.length; i++) {
			VideoContainer videoContainer = videoContainers[i];
			System.out.println(option + ". " + videoContainer.name());
			selection.add(new OptionSelection<VideoContainer>(option++, videoContainer));
		}
		return selection;
	}
}
