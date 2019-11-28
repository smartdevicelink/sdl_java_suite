
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof VideoStreamingFormat){
			VideoStreamingFormat compareTo = (VideoStreamingFormat) obj;
			return getCodec() == compareTo.getCodec() && getProtocol() == compareTo.getProtocol();
		}
		return false;
	}

	@Override
	public String toString() {
		return "codec=" + String.valueOf(getCodec()) +
				", protocol=" + String.valueOf(getProtocol());
	}