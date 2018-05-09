import React, { Component } from 'react';
import { Alert } from 'reactstrap';
import { Row, Col } from 'reactstrap';
import '../styles/FormContent.css';
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import * as API from '../api/API';
class OpenSurveyQuestions extends Component{
    constructor(props){
        super(props);
        let x = this.props.surveyData;
        this.state= {
            "type": '',
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
                    }
                ]
            },
            "displayQues": []
        }
    }

    componentWillMount(){
        let id = this.props.surveyid;
        // var email = data.email;
        // var id = data.surveyid;
        //api call to get all the questions and options of the survey

        // API.getSurvey(this.state)
        //     .then(
        //         response => {
        //             if(response.status === 200){
        //                 this.props.history.push('./SurveyQuestions', {surveyData: this.state});
        //             } else if(response.response.status === 404){
        //                 alert(response.response.data.message);
        //             } else if(response.response.status === 208){
        //                 //show his/her response page of the user
        //             } else if(response.response.status === 401){
        //                 alert(response.response.data.message);
        //             } else {
        //                 alert("An error occured. Please try again with correc URL");
        //             }
        //         },
        //         error => {
        //             console.log(error.data.message);
        //         }
        //     );

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
                            <Dropdown options={opt}
                                      onChange={this._onSelect} value={opt[0]}
                                      placeholder="Select an option" />
                            {/*<QuestionDropDown*/}
                            {/*optionsValue ={this.state.surveyQuestions.surveyquestions[i].options}/>*/}
                        </Col>
                    </Row>
                )
            } else if(this.state.surveyQuestions.surveyquestions[i].questiontype === "radio"){
                let temp =[];
                this.state.surveyQuestions.surveyquestions[i].options.map((opt, index) => {
                    temp.push(<div>
                            <input type="radio" name={this.state.surveyQuestions.surveyquestions[i].questionid}
                                   value={opt.option}/>
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
                            <input type="checkbox" />
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
                            <textarea width="50" value="" placeholder="Your response here"/>
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
                            <Dropdown options={opt}
                                      onChange={this._onSelect} value={opt[0]}
                                      placeholder="Select an option" />
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
export default OpenSurveyQuestions;

