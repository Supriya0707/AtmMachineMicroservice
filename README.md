# ATMServices
Atm Services

1)enquiry-service 
		Request - http://host.docker.internal:8000/findCustomer?accountNumber=987654321&pin=4321
		Response - {
                     "accountNumber": "987654321",
                     "pin": 4321,
                     "balance": 1230,
                     "overdraft": 150
                   }

2) withdraw-service
		Request - http://host.docker.internal:8081/withdrawMoney?accountNumber=987654320&pin=4321&requestAmount=1000
	    Response - {
					"serviceError": {
                       "errorCode": "201",
                       "errorMessage": "Invalid input, please try again."
					}
	               }

		Request - http://host.docker.internal:8081/withdrawMoney?accountNumber=987654321&pin=4321&requestAmount=1001
        Response - {
                       "serviceError": {
                           "errorCode": "203",
                           "errorMessage": "Amount cannot be dispensed."
                       }
                   }