export const signup = (data) => {
    let msg = "";
    const namePattern = /^[a-zA-Z\s]+$/;
    const emailPattern = /^[\w.]*@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
    const passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z@]{8,}$/;

    if(data.firstname ==='' || data.lastname ==='' || data.email ==='' || data.password ===''){
        msg = "All fields are mandatory. Please fill all details";
        return msg;
    }
    if (!(namePattern.test(data.firstname))) {
        msg = "Enter correct First Name";
        return msg;
    }
    if(!(namePattern.test(data.lastname))){
        msg = "Enter correct Last Name";
        return msg;
    }
    if (!(emailPattern.test(data.email))) {
        msg = "Enter correct Email Address";
        return msg;
    }
    if (data.password.length<6 || data.password.length>14) {
        msg = "Password must be 6 to 15 character long";
        return msg;
    }
    return msg;
};

export const login = (data) => {
    let msg = "";
    const emailPattern = /^[\w.]*@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;

    if( data.email ==='' || data.password ===''){
        msg = "All fields are mandatory. Please fill all details";
        return msg;
    }
    if (!(emailPattern.test(data.email))) {
        msg = "Enter correct email";
        return msg;
    }
    return msg;
};

export const signUpVerification = (data) => {
    let msg = "";
    const codePattern = /^[a-zA-Z0-9\s]+$/;

    if( data.code ==='' ){
        msg = "Enter the verification code";
        return msg;
    }
    if (!(codePattern.test(data.code))) {
        msg = "Enter correct code";
        return msg;
    }
    return msg;
};