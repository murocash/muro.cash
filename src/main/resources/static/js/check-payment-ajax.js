
window.addEventListener("load", function() {

	const address = document.getElementById("muro-payment-receiver").innerText;
	const waitingEffectSpan = document.getElementById("muro-waiting-effect");
	const pendingAmountSpan = document.getElementById("muro-checkout-pending-amount");
	const csrfElement = document.getElementById("csrf");
	
	document.getElementById("muro-payment-receiver").appendChild(QRimg(address));
		
	let n = 0;	
	let muroPaymentCheck = new FormData();
	muroPaymentCheck.append(csrfElement.getAttribute("name"), csrfElement.getAttribute("value"));
	
	var checkPaymentInterval = setInterval(checkpayment, 1000);	
	
	function checkpayment() {		
		muroPaymentCheck.append("receiver", address);
		let xhr = new XMLHttpRequest();
		xhr.open("POST", "/muro-checkout/checkpayment/");
		xhr.send(muroPaymentCheck);
		xhr.onload = function() {
			let amount = xhr.responseText;
			waitingEffectSpan.innerText =  waitingDotsEffect(n++);
			pendingAmountSpan.innerText = amount;
			if (!(amount > 0)) {
				clearInterval(checkPaymentInterval);
				jsUtilsAlert("muro-payment-received", "muro-payment-received-text");
				if (amount < -0.00001) { //The difference is bigger than transaction fees
					getAjaxMessage("muro-payback-sent", function(msg) {
						alert(msg);
					});
				}
				let xhrbb = new XMLHttpRequest();
				xhrbb.open("GET", "/bought-balance");
				xhrbb.send();
				xhrbb.onload = function() {
					document.querySelector("#bought-balance .value").innerText = xhrbb.responseText;
				}
			}
		}
	}	

});