public class SortProtocol{
	//프로토콜 타입에 관한 변수
	public static final int PT_UNDEFINED = -1;	// 프로토콜이 지정되어 있지 않은 경우
	public static final int PT_EXIT = 0;		// 프로그램 종료
	public static final int PT_REQ_DATA = 1;	// 단어, 정수 요청
	public static final int PT_RES_DATA_WORD= 2;// 단어 응답
	public static final int PT_RES_DATA_INT=3;	// 정수 응답
	public static final int PT_RES_SORT_WORD=4;	// 단어 정렬 결과
	public static final int PT_RES_SORT_INT=5;	// 정수 정렬 결과
	public static final int LEN_PROTOCOL_TYPE=1;	// 프로토콜 타입 길이
	public static final int LEN_MAX = 1000;		//최대 데이터 길이
	protected int protocolType;
	private byte[] packet;	// 프로토콜과 데이터의 저장공간이 되는 바이트 배열

	public SortProtocol(){					// 생성자
		this(PT_UNDEFINED);
	}

	public SortProtocol(int protocolType){	// 생성자
		this.protocolType = protocolType;
		getPacket(protocolType);
	}

	public byte[] getPacket(int protocolType){
	    if(packet==null){
			switch(protocolType){
				case PT_REQ_DATA :
					packet = new byte[LEN_PROTOCOL_TYPE];
					break;
				case PT_RES_DATA_WORD :
					packet = new byte[LEN_MAX];
					break;
				case PT_UNDEFINED :
					packet = new byte[LEN_MAX];
					break;
				case PT_RES_DATA_INT :
					packet = new byte[LEN_MAX];
					break;
				case PT_RES_SORT_WORD:
					packet = new byte[LEN_MAX];
					break;
				case PT_EXIT :
					packet = new byte[LEN_PROTOCOL_TYPE];
					break;
			}
	    }
	    packet[0] = (byte)protocolType;	// packet 바이트 배열의 첫 번째 바이트에 프로토콜 타입 설정
	    return packet;
	}
	public void setProtocolType(int protocolType){
		this.protocolType = protocolType;
	}
	public void setPacket(int pt, byte[] buf){
		packet = null;
		packet = getPacket(pt);
		protocolType = pt;
		System.arraycopy(buf, 0, packet, 0, packet.length);
	}
	public int getProtocolType(){
		return protocolType;
	}

	public byte[] getPacket(){
		return packet;
	}
	public String getWord() {
		return new String(packet, LEN_PROTOCOL_TYPE, LEN_MAX-1).trim();
	}
	public void setWord(String words) {
		System.arraycopy(words.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE, words.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + words.trim().getBytes().length] = '\0';
	}
	public String getInt() {
		return new String(packet, LEN_PROTOCOL_TYPE, LEN_MAX-1).trim();
	}
	public void setInt(String integer) {
		System.arraycopy(integer.trim().getBytes(), 0, packet, LEN_PROTOCOL_TYPE, integer.trim().getBytes().length);
		packet[LEN_PROTOCOL_TYPE + integer.trim().getBytes().length] = '\0';
	}
}