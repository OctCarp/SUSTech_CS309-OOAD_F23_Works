export const flightNoValidator = (rule: any, value: string, callback: Function) => {
    const re = /^[A-Z0-9]{2}\d{3,4}$/
    if (!value) {
        return callback(new Error('Please input flight No.'))
    }
    if (!re.test(value)) {
        return callback(new Error('Invalid Flight No.'))
    }
    callback()
}

export const airportCodeValidator = (rule: any, value: string, callback: Function) => {
    const re = /^[A-Z]{3}$/
    if (!value) {
        callback(new Error('Please input origin/destination airport code'))
    }
    if (!re.test(value)) {
        return callback(new Error('Invalid origin/destination airport code'))
    }
    callback()
}

export const rules = {
    FlightNo: [
        { validator: flightNoValidator, trigger: 'blur' },
        { required: true, trigger: true }
    ],
    AirlineCompany: [
        { required: true, message: 'Please input Airline Company', trigger: 'blur' },
    ],
    From: [
        { validator: airportCodeValidator, trigger: 'blur' },
        { required: true, trigger: true }
    ],
    To: [
        { validator: airportCodeValidator, trigger: 'blur' },
        { required: true, trigger: true }
    ],
    Date: [
        { required: true, message: 'Please select the date of flight', trigger: 'blur' },
    ],
    DepartureTime: [
        { required: true, message: 'Please select the departure time of flight', trigger: 'blur' },
    ],
    ArriveTime: [
        { required: true, message: 'Please select the arrival time of flight', trigger: 'blur' },
    ]
} as const
