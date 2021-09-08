+# ATMServ++*ices

# Enquiry-Service 
		Request - http://host.docker.internal:8000/findCustomer?accountNumber=987654321&pin=4321
		Response - {
                     "accountNumber": "987654321",
                     "balance": 1230,
                     "overdraft": 150
                   }
# Withdraw-Service (Success)
		Request - http://host.docker.internal:8081/withdrawMoney?accountNumber=987654320&pin=4321&requestAmount=1150
	    Response - {
    			"serviceResponse": {
        			"accountNumber": "987654321",
        			"balance": 80,
        			"overdraft": 0,
        			"withdrawnAmount": "1150",
        			"withdrawnNotes": [
            			{
                			"noteValue": 50,
                			"noteQuantity": 20
            			},
            			{
                			"noteValue": 20,
                			"noteQuantity": 7
            			},
            			{
                			"noteValue": 10,
                			"noteQuantity": 1
            			}
        			]
    			}
			}
				   
# Withdraw-Service (Validation) 
        *Request - http://host.docker.internal:8081/withdrawMoney?accountNumber=987654320&pin=4321&requestAmount=1000
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
