package cash.muro.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.emiperez.repeson.client.JsonRpcException;

import cash.muro.bch.model.BchAddress;
import cash.muro.bch.model.BchException;
import cash.muro.bch.model.BchReceived;
import cash.muro.bch.model.BchValidatedAddress;
import cash.muro.bch.rpc.client.BchnRpcClient;

@Service
public class JsonRpcBchnService implements BchService {

	private BchnRpcClient bchClient;

	public JsonRpcBchnService(BchnRpcClient bchClient) {
		this.bchClient = bchClient;
	}

	@Override
	public boolean verifyMessage(String pubKey, String signature, String message) throws BchException {
		try {
			return bchClient.verifyMessage(new BchAddress(pubKey), signature, message);
		} catch (IOException | InterruptedException | JsonRpcException e) {
			throw new BchException(e);
		}
	}

	@Override
	public BchValidatedAddress validateAddress(String address) throws BchException {
		try {
			return bchClient.validateAddress(new BchAddress(address));
		} catch (IOException | InterruptedException | JsonRpcException e) {
			throw new BchException(e);
		}
	}

	@Override
	public BchAddress newAddress() throws BchException {
		try {
			return bchClient.getNewAddress();
		} catch (IOException | InterruptedException | JsonRpcException e) {
			throw new BchException(e);
		}
	}

	@Override
	public BigDecimal checkPayment(String paymentAddress) throws BchException {
		try {
			List<BchReceived> receivedList = bchClient.listReceivedByAddress(0, new BchAddress(paymentAddress));
			if (receivedList == null || receivedList.isEmpty()) {
				return null;
			}
			BchReceived received = bchClient.listReceivedByAddress(0, new BchAddress(paymentAddress)).get(0);
			if (received != null) {
				return received.getAmount();
			}
		} catch (IOException | InterruptedException | JsonRpcException | NullPointerException e) {
			throw new BchException(e);
		}
		return null;
	}

	@Override
	public String sendToAddress(BchAddress address, BigDecimal amount, boolean substractFeeFromAmount) throws BchException {
		try {
			return bchClient.sendToAddress(address, amount, substractFeeFromAmount);
		} catch (IOException | InterruptedException | JsonRpcException e) {
			throw new BchException(e);
		}
	}
}
