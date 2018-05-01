const api = process.env.REACT_APP_CONTACTS_API_URL || 'http://localhost:3000'

const headers = {
    'Accept': 'application/json'
};

export const doLogin = (payload) =>
    fetch(`${api}/users/signin`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        credentials:'include',
        body: JSON.stringify(payload)
    }).then(res => res.json())
        .catch(error => {
            console.log("This is error");
            return error;
        });



export const doSignUp = (payload) =>
    fetch(`${api}/users/signup`, {
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
