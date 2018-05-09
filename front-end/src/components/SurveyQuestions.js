import React, { Component } from 'react';
import { Route, withRouter } from 'react-router-dom';
import { Button } from 'reactstrap';
import { Alert } from 'reactstrap';
import { Container, Row, Col } from 'reactstrap';
import '../styles/FormContent.css';
import QuestionDropDown from  './QuestionDropDown';
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import * as API from '../api/API';
import {Form, FormGroup, Label, Input, FormText } from 'reactstrap';
import QuestionRadio from  './QuestionRadio';
class SurveyQuestions extends Component{
    constructor(props){
        super(props);
        let x = this.props.surveyData;
        this.state= {
            "textValue":'',
            "type": x.type,
            "email": x.email,
            "surveyid": '',
            "surveyQuestions": {
                "surveyid": "1",
                "surveyname": "Survey for cars",
                "surveyquestions":[
                    {
                        "questionid": 1,
                        "questiontype": "dropdown",
                        "question": "Which car is more liked in 2018",
                        "options":[
                            {
                                "optionid":1,
                                "option":"Accord"
                            },
                            {
                                "optionid":2,
                                "option":"BMW"
                            },
                            {
                                "optionid":3,
                                "option":"BMW1"
                            },
                            {
                                "optionid":4,
                                "option":"BMW2"
                            },
                            {
                                "optionid":5,
                                "option":"BMW3"
                            }]
                    },
                    {
                        "questionid": 2,
                        "questiontype": "checkbox",
                        "question": "What is the color you like most",
                        "options":[
                            {
                                "optionid":6,
                                "option":"Red"
                            },
                            {
                                "optionid":7,
                                "option":"Black"
                            },
                            {
                                "optionid":8,
                                "option":"Yellow"
                            },
                            {
                                "optionid":9,
                                "option":"White"
                            }
                        ]
                    },
                    {
                        "questionid": 3,
                        "questiontype": "radio",
                        "question": "What is your gender",
                        "options":[
                            {
                                "optionid":10,
                                "option":"Male"
                            },
                            {
                                "optionid":11,
                                "option":"Female"
                            }
                        ]
                    },
                    {
                        "questionid": 4,
                        "questiontype": "text",
                        "question": "What is your education"
                    },
                    {
                        "questionid": 5,
                        "questiontype": "rating",
                        "question": "Give rating out of 5",
                        "options":[
                            {
                                "optionid":12,
                                "option":"5"
                            }
                        ]
                    },
                    {
                        "questionid": 6,
                        "questiontype": "rating",
                        "question": "Give rating out of 10",
                        "options":[
                            {
                                "optionid":13,
                                "option":"10"
                            }
                        ]
                    },{
                        "questionid": 7,
                        "questiontype": "dropdown",
                        "question": "Which car is more liked in 2018",
                        "options":[
                            {
                                "optionid":14,
                                "option":"Accord"
                            },
                            {
                                "optionid":15,
                                "option":"BMW"
                            },
                            {
                                "optionid":16,
                                "option":"BMW1"
                            },
                            {
                                "optionid":17,
                                "option":"BMW2"
                            },
                            {
                                "optionid":18,
                                "option":"BMW3"
                            }]
                    }
                ]
            },
            "displayQues": []
        }
    }

    componentWillMount(){
        let data = this.props.surveyData;
        var email = data.email;
        var id = data.surveyid;
        //api call to get all the questions and options of the survey


        // then from the response add it to the state
        // this.setState({
        //     ...this.state,
        //     surveyQuestions:{
        //         "surveyid": "1",
        //         "surveyname": "Survey for cars",
        //         "surveyquestions":[
        //             {
        //                 "questionid": 1,
        //                 "questiontype": "dropdown",
        //                 "question": "Which car is more liked in 2018",
        //                 "options":[
        //                     {
        //                         "optionid":1,
        //                         "option":"Accord"
        //                     },
        //                     {
        //                         "optionid":2,
        //                         "option":"BMW"
        //                     },
        //                     {
        //                         "optionid":3,
        //                         "option":"BMW1"
        //                     },
        //                     {
        //                         "optionid":4,
        //                         "option":"BMW2"
        //                     },
        //                     {
        //                         "optionid":5,
        //                         "option":"BMW3"
        //                     }]
        //             },
        //             {
        //                 "questionid": 2,
        //                 "questiontype": "radio",
        //                 "question": "What is the color you like most",
        //                 "options":[
        //                     {
        //                         "optionid":6,
        //                         "option":"Red"
        //                     },
        //                     {
        //                         "optionid":7,
        //                         "option":"Black"
        //                     },
        //                     {
        //                         "optionid":8,
        //                         "option":"Yellow"
        //                     },
        //                     {
        //                         "optionid":9,
        //                         "option":"White"
        //                     }
        //                 ]
        //             },
        //             {
        //                 "questionid": 3,
        //                 "questiontype": "radio",
        //                 "question": "What is your gender",
        //                 "options":[
        //                     {
        //                         "optionid":10,
        //                         "option":"Male"
        //                     },
        //                     {
        //                         "optionid":11,
        //                         "option":"Female"
        //                     }
        //                 ]
        //             }
        //         ]
        //     }
        // }, this.displaySurveyQuestions());
        let x = this.state.displayQues;

        for(let i= 0; i<this.state.surveyQuestions.surveyquestions.length ;i++){
            let questionid= this.state.surveyQuestions.surveyquestions[i].questionid
            x.push(
                <Row>
                    <Col xs="1">{i+1}</Col>
                    <Col xs="11" className="questionColor">{this.state.surveyQuestions.surveyquestions[i].question}</Col>
                </Row>
            )
            if(this.state.surveyQuestions.surveyquestions[i].questiontype === "dropdown"){
                let opt = [];
                let options = this.state.surveyQuestions.surveyquestions[i].options;
                for(let k= 0; k<options.length; k++){
                    opt.push({value: options[k].optionid,
                        label: options[k].option});
                }
                x.push(
                    <Row>
                        <Col xs="1"></Col>
                        <Col xs="5">
                            <Dropdown options={opt} value={opt[0]} placeholder="Select an option"
                                      onChange={(e) => this.saveDropdown(questionid, e)}/>
                        </Col>
                    </Row>
                )
            } else if(this.state.surveyQuestions.surveyquestions[i].questiontype === "radio"){
                let temp =[];
                this.state.surveyQuestions.surveyquestions[i].options.map((opt, index) => {
                        temp.push(<div>
                            <input type="radio" name={this.state.surveyQuestions.surveyquestions[i].questionid}
                             value={opt.option} onClick={(e) => this.saveRadio(opt.option, opt.optionid, questionid, e)}/>
                            <label>{opt.option}</label>
                            </div>
                        );
                });
                x.push(
                    <Row>
                        <Col xs="1"></Col>
                        <Col xs="11">
                            {
                                temp
                            }
                            {/*<QuestionRadio*/}
                                {/*radioValue ={this.state.surveyQuestions.surveyquestions[i].options}/>*/}
                        </Col>
                    </Row>
                )
            } else if(this.state.surveyQuestions.surveyquestions[i].questiontype === "checkbox"){
                let temp =[];
                this.state.surveyQuestions.surveyquestions[i].options.map((opt, index) => {
                    temp.push(<div>
                        <input type="checkbox" onClick={(e) => this.saveCheckBox(opt.option, opt.optionid, questionid, e)}/>
                        <label>{opt.option}</label>
                       </div>
                    );
                });
                x.push(
                    <Row>
                        <Col xs="1"></Col>
                        <Col xs="11">
                            {temp}
                        </Col>
                    </Row>
                )
            }
            else if(this.state.surveyQuestions.surveyquestions[i].questiontype === "text"){
                x.push(
                    <Row>
                        <Col xs="1"></Col>
                        <Col xs="5">
                            <textarea width="50" placeholder="Your response here"
                                      onBlurCapture={(event) => {
                                          this.setState({
                                              textValue: event.target.value
                                          });
                                          this.saveText(questionid,event)}}
                                      />
                        </Col>
                    </Row>
                )
            } else if(this.state.surveyQuestions.surveyquestions[i].questiontype === "rating"){
                let opt = [];
                for(let k= 1; k<=this.state.surveyQuestions.surveyquestions[i].options[0].option; k++){
                    opt.push({value: k, label: k});
                }

                x.push(
                    <Row>
                        <Col xs="1"></Col>
                        <Col xs="5">
                            <Dropdown options={opt} value={opt[0]}
                                      placeholder="Select an option"
                                      onChange={(e) => this.saveRating(questionid, e)}/>
                        </Col>
                    </Row>
                )
            }
        }

        this.setState({
                    ...this.state,
                    "displayQues": x
        });
    }

    saveDropdown(questionid, event){
        this.autoSaveSurvey(event.label, questionid, event.value)
    }
    saveRadio(option, optionid, questionid, event){
        // this.autoSaveSurvey()
        console.log(" dfg");
        this.autoSaveSurvey(option, questionid, optionid)
    }
    saveCheckBox(option, optionid, questionid, event){
        // this.autoSaveSurvey()
        console.log(" dfg");
        this.autoSaveSurvey(option, questionid, optionid)
    }
    saveText(questionid, event){
        var optionid = "";
        this.autoSaveSurvey(this.state.textValue, questionid, optionid)
    }
    saveRating(questionid, event){
        var optionid = "";
        this.autoSaveSurvey(event.label, questionid, optionid)
    }
    autoSaveSurvey(response, questionid, optionid){
        var payload = {
            "type": this.state.type,
            "email": this.state.email,
            "response": response,
            "questionid": questionid,
            "optionid": optionid
        }
        //API call to backend
        API.saveSurveyResponse(payload)
            .then(
                response => {
                    if(response.status === 200){
                        // this.props.history.push('./SurveyQuestions', {surveyData: this.state});
                    } else if(response.response.status === 404){
                        alert(response.response.data.message);
                    } else if(response.response.status === 208){
                        //show his/her response page of the user
                    } else if(response.response.status === 401){
                        alert(response.response.data.message);
                    } else {
                        alert("An error occured. Please try again with correc URL");
                    }
                },
                error => {
                    console.log(error.data.message);
                }
            );
    }
    render(){
        return(
            <div className="survey-container">
                <div className="form-design-container">
                    <div className="form-container">
                        <Alert color="secondary">
                            {this.state.surveyQuestions.surveyname}
                        </Alert>
                        <div className="displayQuestions">
                            {this.state.displayQues}
                        </div>
                    </div>
                </div>
            </div>
        )
    }

}
export default SurveyQuestions;

