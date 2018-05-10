import React, { Component } from 'react';
import { Route, withRouter } from 'react-router-dom';
import { Button } from 'reactstrap';
import { Alert } from 'reactstrap';
import { Container, Row, Col } from 'reactstrap';
import '../styles/FormContent.css';
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import * as API from '../api/API';
import StarRatingComponent      from 'react-star-rating-component';
import {Form, FormGroup, Label, Input, FormText } from 'reactstrap';
import QuestionRadio from  './QuestionRadio';
class OpenSurveyQuestions extends Component{
    constructor(props){
        super(props);
        let x = this.props.surveyid;
        this.state= {
            "textValue":'',
            // "type": x.type,
            // "email": x.email,
            "surveyid": x.surveyid,
            "surveyQuestions": {},
            "displayQues": [],
            "surveyResponses": [],
            "sendEmail":''
        }

    }

    componentWillMount() {
        let data = this.props.surveyid;
        // var email = data.email;

        let payload = {
            "surveyid": data
        }
        //api call to get all the questions and options of the survey
        API.fetchQuestions(payload)
            .then(
                response => {
                    if(response.status === 200){
                        this.setState({
                            ...this.state,
                            surveyQuestions: response.data
                        }, this.handleStateData)
                    } else if(response.response.status === 404){
                        alert(response.response.data.message);
                    } else if(response.response.status === 208){
                        //show his/her response page of the user
                    } else if(response.response.status === 401){
                        alert(response.response.data.message);
                    } else {
                        alert("An error occured. Please try again with correct URL");
                    }
                },
                error => {
                    console.log(error.data.message);
                }
            );
    }
    handleStateData = ()=>{
        let x = this.state.displayQues;
        let survey = this.state.surveyQuestions.surveyquestions;

        for(let i= 0; i<survey.length ;i++){
            let questionid= survey[i].questionid;
            x.push(
                <Row>
                    <Col xs="1">{i+1}</Col>
                    <Col xs="11" className="questionColor">{survey[i].question}</Col>
                </Row>
            )
            if(survey[i].questiontype === "dropdown"){
                let opt = [];
                let options = survey[i].options;
                for(let k= 0; k<options.length; k++){
                    opt.push({value: options[k].optionid,
                        label: options[k].options});
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
            } else if(survey[i].questiontype === "radio"){
                let temp =[];
                survey[i].options.map((opt, index) => {
                    temp.push(<div>
                            <input type="radio" name={survey[i].questionid}
                                   value={opt.options} onClick={(e) => this.saveRadio(opt.options, opt.optionid, questionid, e)}/>
                            <label>{opt.options}</label>
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
            } else if(survey.questiontype === "checkbox"){
                let temp =[];
                survey[i].options.map((opt, index) => {
                    temp.push(<div>
                            <input type="checkbox" onClick={(e) => this.saveCheckBox(opt.options, opt.optionid, questionid, e)}/>
                            <label>{opt.options}</label>
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
            else if(survey[i].questiontype === "text"){
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
            } else if(survey[i].questiontype === "rating"){
                let opt = [];
                for(let k= 1; k<=survey[i].options[0].options; k++){
                    opt.push({value: k, label: k});
                }
                x.push(
                    <Row>
                        <Col xs="1"></Col>
                        <Col xs="5">
                            <StarRatingComponent
                                name={survey[i].options[0].optionid +","+questionid}
                                renderStarIcon={() => <i className="fa fa-star" aria-hidden="true"></i> }
                                starColor="#ffb400"
                                emptyStarColor="#000000"
                                starCount={survey[i].options[0].options}
                                onStarClick={ this.saveRating }
                            />
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
        var optionid = null;
        this.autoSaveSurvey(event.target.value, questionid, optionid)
    }
    saveRating=(nextValue, questionid, optionid )=>{
        // this.autoSaveSurvey(21, optionid)
        var value = optionid.split(",");
        this.autoSaveSurvey(nextValue, value[1], value[0])

        // onStarClick = (nextValue, prevValue, name) => {
        //     this.setState({ reviewedRating : nextValue });
        // };
    }

    autoSaveSurvey(response, questionid, optionid){
        var payload = {
            "type": this.state.type,
            "response": response,
            "questionid": questionid,
            "optionid": optionid
        }
        this.state.surveyResponses.push(payload);
        //API call to backend

    }

    submitGeneralSurvey(){
        if(this.state.sendEmail === '' || this.state.sendEmail === ' ' || this.state.sendEmail === undefined){
            var payload={
                openResponses: this.state.surveyResponses,
                sendEmail: this.state.sendEmail
            }
        } else{
            var payload={
                openResponses: this.state.surveyResponses,
                sendEmail: null
            }
        }

        API.saveGeneralSurveyResponse(payload)
            .then(
                response => {
                    if(response.status === 200){
                        console.log("saved");
                        alert("You have Successfully submitted the survey");
                        this.props.history.push('/');
                    } else if (response.response.status === 404) {
                        alert(response.response.data.message);
                    } else {
                        alert("An error occured. Please try again with correct URL");
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
                        <div className="form-group row">
                            <label className="col-3 col-form-label">Email(optional)</label>
                            <div className=" col-sm-8">
                                <input type="text"  className="form-control"  placeholder="abc@gmail.com"
                                       value={this.state.sendEmail}
                                       onChange={(event) => {
                                           this.setState({...this.state,sendEmail: event.target.value});
                                       }}required/>
                            </div>
                        </div>
                        <button className="btn btn-primary share-button"
                                onClick={() => {
                                    this.submitGeneralSurvey();
                                }}>Submit Survey</button>
                    </div>
                </div>
            </div>
        )
    }

}
export default withRouter(OpenSurveyQuestions);



// {
//     "email": "abc@gmail.com",
//     "responses":[
//         {
//             "type": "general",
//             "response": "female",
//             "questionid": "2",
//             "optionid": "5"
//         },
//          {
//              "type": "general",
//              "response": "male",
//              "questionid": "4",
//              "optionid": "5"
//         }
//     ]
// }