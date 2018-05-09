const api = process.env.REACT_APP_CONTACTS_API_URL || 'http://localhost:8080'
const url = 'http://localhost:8080';
const axios = require("axios");

const headers = {
    'Accept': 'application/json'
};

axios.defaults.withCredentials = true;

export const doLogout = () =>{
    return axios.get('http://localhost:8080/users/logout', null)
        .then(function (response) {
            console.log(response);
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
};

export const doLogin = (payload) =>{
    return axios.post('http://localhost:8080/users/login', payload)
        .then(function (response) {
            console.log(response);
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
};

export const validateEmail = (payload) =>{
    return axios.post('http://localhost:8080/survey/validateEmail', payload)
        .then(function (response) {
            console.log(response);
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
}

export const getSurvey = (payload) =>{
    return axios.post('http://localhost:8080/survey/getSurvey', payload)
        .then(function (response) {
            console.log(response);
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
}
    // fetch(`${url}/users/signin`, {
    //     method: 'POST',
    //     headers: {
    //         ...headers,
    //         'Content-Type': 'application/json'
    //     },
    //     credentials:'include',
    //     body: JSON.stringify(payload)
    // }).then(res => res.json())
    //     .catch(error => {
    //         console.log("This is error");
    //         return error;
    //     });

export const doSignUp = (payload) =>{
    return axios.post('http://localhost:8080/users/signup', payload)
        .then(function (response) {
            console.log(response);
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
};
    // fetch(`${url}/users/signup`, {
    //     method: 'POST',
    //     headers: {
    //         ...headers,
    //         'Content-Type': 'application/json'
    //     },
    //     // mode: 'cors',
    //     credentials:'include',
    //     body: JSON.stringify(payload)
    // }).then(res => res.json())
    //     .catch(error => {
    //         console.log("This is error");
    //         return error;
    //     });


export const doSignUpVerification = (payload) =>{
    return axios.post('http://localhost:8080/users/signUpVerification', payload)
        .then(function (response) {
            console.log(response);
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
};

export const addSurvey = (payload) =>{
    return axios.post('http://localhost:8080/survey/create', payload)
        .then(function (response) {
            console.log(response);
            return response;
        })
        .catch(function (error) {
            console.log(error);

            return error;
        });
};

export const addQuestion = (payload) =>{
    return axios.post('http://localhost:8080/question/create', payload)
        .then(function (response) {
            console.log(response);
            return response;
        })
        .catch(function (error) {
            console.log(error);
            return error;
        });
};

export const addOption = (payload) =>{
    return axios.post('http://localhost:8080/option/create', payload)
        .then(function (response) {
            console.log(response);
            return response;
        })
        .catch(function (error) {
            console.log(error);
            return error;
        });
};


export const getSurveyListing = () =>
    fetch(`${api}/surveys/getsurveylisting`, {
        method: 'GET',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        // mode: 'cors',
        credentials:'include',
        // body: JSON.stringify(payload)
    }).then(res => res.json())
        .catch(error => {
            console.log("This is error");
            return error;
        });



/*
export const getTasks = () =>
    fetch(`${api}/tasks/addtask`, {
        method: 'GET',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        // mode: 'cors',
        credentials:'include',
        // body: JSON.stringify(payload)
    }).then(res => res.json())
        .catch(error => {
            console.log("This is error");
            return error;
        });

export const deleteTask = (payload) =>
    fetch(`${api}/tasks/deletetask`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        // mode: 'cors',
        credentials:'include',
        body: JSON.stringify(payload)
    }).then(res => res.json())
        .catch(error => {
            console.log("This is error");
            return error;
        });*/
