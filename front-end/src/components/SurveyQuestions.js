import React, { Component } from 'react';
import { Route, withRouter } from 'react-router-dom';
import { Button } from 'reactstrap';
import { Alert } from 'reactstrap';
import { Container, Row, Col } from 'reactstrap';
import '../styles/FormContent.css';
import QuestionDropDown from  './QuestionDropDown';
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css'
import QuestionRadio from  './QuestionRadio';
class SurveyQuestions extends Component{
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
                        "questiontype": "radio",
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
            x.push(
                <Row>
                    <Col xs="1">{i+1}</Col>
                    <Col xs="11">{this.state.surveyQuestions.surveyquestions[i].question}</Col>
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
                            <input type="radio" value={opt.option} checked={false} />
                            <label>{opt.option}</label>
                        </div>);
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
export default SurveyQuestions;

