const api = process.env.REACT_APP_CONTACTS_API_URL || 'http://localhost:8080'
const url = 'http://localhost:8080';
// const url = 'http://www.surveyape.ga:8080';
const axios = require("axios");
// import axios from 'axios';

const headers = {
    'Accept': 'application/json'
};

axios.defaults.withCredentials = true;

export const doLogout = () =>{
    return axios.get(url + '/users/logout', null)
        .then(function (response) {
            console.log(response);
            localStorage.removeItem("userEmail");
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
};

export const doLogin = (payload) =>{
    return axios.post(url + '/users/login', payload)
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
    return axios.post(url + '/survey/validateEmail', payload)
        .then(function (response) {
            console.log(response);
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
}


export const validateUniqueEmail = (payload) =>{
    return axios.post(url + '/survey/validateUniqueEmail', payload)
    .then(function (response) {
    console.log(response);
    return response
})
    .catch(function (error) {
    console.log(error);
    return error
});
}
export const fetchQuestions = (payload) => {
    return axios.post(url + '/survey/fetchQuestions', payload)
        .then(function (response) {
            console.log(response);
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
}

export const submitCloseUniqueSurvey = (payload) => {
    return axios.post(url + '/survey/finishClosedUniqueSurveys', payload)
        .then(function (response) {
            console.log(response);
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
}

export const saveGeneralSurveyResponse = (payload) => {
    return axios.post(url + '/response/saveGeneralSurveyResponse', payload)

        .then(function (response) {
            console.log(response);
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
}

export const saveCheckBoxSurveyResponse = (payload) => {
    return axios.post('http://localhost:8080/response/saveCheckBoxSurveyResponse', payload)

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
    return axios.post(url + '/survey/getSurvey', payload)
        .then(function (response) {
            console.log(response);
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
}

export const saveSurveyResponse = (payload) =>{
    return axios.post(url + '/response/saveSurveyResponse', payload)
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
    return axios.post(url + '/users/signup', payload)
        .then(function (response) {
            console.log(response);
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
};

export const doSignUpVerification = (payload) =>{
    return axios.post(url + '/users/signUpVerification', payload)
        .then(function (response) {
            console.log(response);
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
};

export const doRegister = (payload) =>{
    return axios.post(url + '/survey/register', payload)
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
    return axios.post(url + '/survey/create', payload)
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
    return axios.post(url + '/question/create', payload, {headers: {
        // 'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
        // "credentials":"include"
    }})
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
    return axios.post(url + '/option/create', payload)
        .then(function (response) {
            console.log(response);
            return response;
        })
        .catch(function (error) {
            console.log(error);
            return error;
        });
};

export const unpublishSurvey = (payload) =>{
    return axios.post(url + '/survey/unpublish', payload)
        .then(function (response) {
            console.log(response);
            return response;
        })
        .catch(function (error) {
            console.log(error);
            return error;
        });
};


export const publishSurvey = (payload) =>{
    return axios.post(url + '/survey/publish', payload)
        .then(function (response) {
            console.log(response);
            return response;
        })
        .catch(function (error) {
            console.log(error);
            return error;
        });
};


export const closeSurvey = (payload) =>{
    return axios.post(url + '/survey/close', payload)
        .then(function (response) {
            console.log(response);
            return response;
        })
        .catch(function (error) {
            console.log(error);
            return error;
        });
};

export const surveyListing = (payload) =>{
    return axios.post(url + '/survey/surveylisting', payload)
        .then(function (response) {
            console.log(response);
            return response;
        })
        .catch(function (error) {
            console.log(error);
            return error;
        });
};


export const uniqueSurveyListing = () =>{
    return axios.post(url + '/survey/uniqueSurveylisting',null)
        .then(function (response) {
            console.log(response);
            return response
        })
        .catch(function (error) {
            console.log(error);
            return error
        });
}

export const getQuestions = (payload) =>{
    return axios.post(url + '/survey/getquestions', payload)
        .then(function (response) {
            console.log(response);
            return response;
        })
        .catch(function (error) {
            console.log(error);
            return error;
        });
};

export const getOptions = (payload) =>{
    return axios.post(url + '/question/getoptions', payload)

        .then(function (response) {
            console.log(response);
            return response;
        })
        .catch(function (error) {
            console.log(error);
            return error;
        });
};

export const exportSurvey = (payload) =>{
    const url = url + '/survey/export-survey?surveyid=' + payload.surveyid + '&filename=' + payload.filename;
    return axios.get(url)
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
    fetch(`${api}/survey/surveylisting`, {
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
