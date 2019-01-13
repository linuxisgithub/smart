(function (global) {

global.libspeex = Module || s;
global.libspeex.generateStructInfo = libspeex.generateStructInfo || Runtime.generateStructInfo;

global.types = {
	
	SPEEX_NB_MODES: 3,
	
	SPEEX_SET_ENH: 0,
	SPEEX_GET_ENH: 1,
	
	SPEEX_GET_FRAME_SIZE: 3,
	
	SPEEX_SET_QUALITY: 4,
	SPEEX_GET_QUALITY: 5, // Not used
	
	SPEEX_SET_VBR: 12,
	SPEEX_GET_VBR: 13,
	
	SPEEX_SET_VBR_QUALITY: 14,
	SPEEX_GET_VBR_QUALITY: 15,

	SPEEX_SET_COMPLEXITY: 16,
	SPEEX_GET_COMPLEXITY: 17,	
	
	SPEEX_SET_SAMPLING_RATE: 24,
	SPEEX_GET_SAMPLING_RATE: 25,
	
	SPEEX_SET_VAD: 30,
	SPEEX_GET_VAD: 31,
	
	SPEEX_SET_ABR: 32,
	SPEEX_GET_ABR: 33,
	
	SPEEX_SET_DTX: 34,
	SPEEX_GET_DTX: 35,
	
	types: {

		/**
		Bit-packing data structure representing (part of) a bit-stream.
		*/
		SpeexBits: libspeex.generateStructInfo([
			["i1*", 'chars'],
			["i32", 'nbBits'],
			["i32", 'charPtr'],
			["i32", 'bitPtr'],
			["i32", 'owner'],
			["i32", 'overflow'],
			["i32", 'buf_size'],
			["i32", 'reserved1'],
			["i8*", 'reserved2']
		]),

		/**
		  * Speex header info for file-based formats
		*/		
		SpeexHeader: libspeex.generateStructInfo([
			["i32", 'speex_version_id'],
			["i32", 'header_size'],
			["i32", 'rate'],
			["i32", 'mode'],
			["i32", 'mode_bitstream_version'],
			["i32", 'nb_channels'],
			["i32", 'bitrate'],
			["i32", 'frame_size'],
			["i32", 'vbr'],
			["i32", 'frames_per_packet'],
			["i32", 'extra_headers'],
			["i32", 'reserved1'],
			["i32", 'reserved2']
		]),

		/**
		Preprocessor internal state
		*/
		SpeexPreprocessState: libspeex.generateStructInfo([
		]),

		/**
		Echo canceller state
		typedef struct SpeexEchoState {
		} SpeexEchoState;
		*/
		SpeexEchoState: libspeex.generateStructInfo([
		])
	}
}

}(this));
