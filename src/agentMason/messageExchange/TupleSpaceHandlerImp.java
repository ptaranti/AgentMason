package agentMason.messageExchange;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TupleSpaceHandlerImp extends Thread implements TupleSpaceHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Long lastTupleID = (long) 0;

	static Hashtable<String, List<Long>> receiverMessages;
	static Hashtable<Long, MessageTuple> indexedMessageTuples;

	static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	static Lock readLock = rwl.readLock();
	Lock writeLock = rwl.writeLock();

	private static TupleSpaceHandlerImp singleton = null;

	private TupleSpaceHandlerImp() {
		Thread t = new Thread(this);
		t.start();
		
		receiverMessages = new Hashtable<String, List<Long>>();
		indexedMessageTuples = new Hashtable<Long, MessageTuple>();		
	}

	public static TupleSpaceHandlerImp getInstance() {
		if (singleton == null) singleton = new TupleSpaceHandlerImp();
		return singleton;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	@Override
	public MessageTuple getMessage(String sender, String receiver,
			String messageClassName, long simulationTime) {
		writeLock.lock();
		try {

			List<Long> messagesToReceiver = this.receiverMessages.get(receiver);
			if (messagesToReceiver == null)
				return null;
			MessageTuple oldestTuple = new MessageTuple();
			long minorSimulationTime = simulationTime;
			long minorSimulationTimeTupleID = 0;
			for (Long tupleID : messagesToReceiver) {
				MessageTuple tempMessageTuple = this.indexedMessageTuples
						.get(tupleID);
				if (tempMessageTuple.matchMessageTuple(sender, receiver,
						messageClassName, simulationTime)) {
					if (tempMessageTuple.simuationTime < minorSimulationTime) {
						oldestTuple = tempMessageTuple;
						minorSimulationTime = tempMessageTuple.simuationTime;
						minorSimulationTimeTupleID = tupleID;
					}
				}
			}

			if (minorSimulationTimeTupleID == 0)
				return null;

			this.indexedMessageTuples.remove(minorSimulationTimeTupleID);
			this.receiverMessages.get(receiver).remove(
					minorSimulationTimeTupleID);
			return oldestTuple;

		} finally {
			writeLock.unlock();
		}

	}

	@Override
	public List<MessageTuple> getAllMessages(String sender, String receiver, String
			messageClassName, long simulationTime) {

		System.out.println("TICK 01");
		writeLock.lock();
		try {
			System.out.println("TICK 02");
			List<Long> messagesToReceiver = this.receiverMessages.get(receiver);
			if (messagesToReceiver == null){
				System.out.println("TICK 03");
				return null;
			}
			System.out.println("TICK 04");
			List<MessageTuple> allMatchingTupleMessages = new Vector<MessageTuple>();

			for (Long tupleID : messagesToReceiver) {
				MessageTuple tempMessageTuple = this.indexedMessageTuples
						.get(tupleID);
				if (tempMessageTuple.matchMessageTuple(sender, receiver,
						messageClassName, simulationTime)) {
					allMatchingTupleMessages.add(tempMessageTuple);
					this.indexedMessageTuples.remove(tupleID);
					this.receiverMessages.get(receiver).remove(tupleID);
				}

			}

			return allMatchingTupleMessages;

		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void sendMessage(MessageTuple messageTuple) {

		writeLock.lock();
		try {

			Long tupleID = this.tupleIDgenerator();
			if (this.receiverMessages.get(messageTuple.receiver) == null) {
				List<Long> temp = (new Vector<Long>());
				temp.add(tupleID);
				this.receiverMessages.put(messageTuple.receiver, temp);
			} else {
				this.receiverMessages.get(messageTuple.receiver).add(tupleID);
			}
			this.indexedMessageTuples.put(tupleID, messageTuple);

		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void sendMessage(List<String> receiverAgentIDList,
			MessageTuple messageTuple) {

		writeLock.lock();
		try {

			for (String otherReceiver : receiverAgentIDList) {
				MessageTuple tempMessageTuple = new MessageTuple(
						messageTuple.sender, otherReceiver,
						messageTuple.messageClassName, messageTuple.message,
						messageTuple.simuationTime);
				this.sendMessage(tempMessageTuple);

			}

		} finally {
			writeLock.unlock();
		}

	}

	private Long tupleIDgenerator() {
		this.lastTupleID = this.lastTupleID + 1;
		return this.lastTupleID;
	}

}
