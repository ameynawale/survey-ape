const api = process.env.REACT_APP_CONTACTS_API_URL || 'http://localhost:8080'
const url = 'http://localhost:8080';
const axios = require("axios")
const headers = {
    'Accept': 'application/json'
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

export const addSurvey = (payload) =>
    fetch(`${api}/surveys/createsurvey`, {
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
        });

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
