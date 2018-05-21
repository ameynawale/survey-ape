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
import StarRatingComponent      from 'react-star-rating-component';
import {Form, FormGroup, Label, Input, FormText } from 'reactstrap';
import QuestionRadio from  './QuestionRadio';

let switchCheckbox = true
class SurveyQuestions extends Component{
    constructor(props){
        super(props);
        let x = this.props.surveyData;
        this.state= {
            "textValue":'',
            "type": x.type,
            "email": x.email,
            "surveyid": x.surveyid,
            "surveyQuestions": {},
            "displayQues": [],
            "isGoing": true,
            "surveyResponses": []
        }
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    toggleCheckBox(e){
        switchCheckbox = !switchCheckbox;
    }
    handleInputChange(event){
        // const target = event.target;
        // const value = target.type === 'checkbox' ? target.checked : target.value;
        // const name = target.name;
        //
        // this.setState({
        //     [name]: value
        // });
        this.setState({
            ...this.state,
            isGoing: false
        })
    }
    unCheck(event, i){

        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
        // // var x = document.getElementById()
        // var x = event.target.checked;
        // event.target.checked = x;
        // let ref = 'ref_' + i;
        // // this.refs[ref].checked = !this.refs[ref].checked;
        // this.refs.checked = !this.refs.checked;
    }
    // var checkBoxResponse
    componentWillMount() {
        let data = this.props.surveyData;
        var email = data.email;
        var id = data.surveyid;
        var payload = {
            "surveyid": id,
            "email": this.state.email
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
                        var x = response.response.data.message + " Go back by clicking browser back button";
                        alert(x);
                    } else if(response.response.status === 400){
                        var x = response.response.data.message + " Go back by clicking browser back button";
                        alert(x);
                    } else if(response.response.status === 208){
                        //show his/her response page of the user
                    } else if(response.response.status === 401){
                        var x = response.response.data.message + " Go back by clicking browser back button";
                        alert(x);
                    } else {
                        alert("An error occured. Please try again with correc URL. The reason can be " +
                            "that you are logged in with different email on survey ape and giving different" +
                            "email address for taking this survey." +
                            "First logout and then take this survey or take this survey in the app");
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
        let checking = false;
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
                            <Dropdown options={opt} value={survey[i].response} placeholder="Select an option"
                                      onChange={(e) => this.saveDropdown(questionid, e)}/>
                        </Col>
                    </Row>
                )
            } else if(survey[i].questiontype === "radio"){
                let temp =[];
                survey[i].options.map((opt, index) => {
                    if(opt.options === survey[i].response){
                        temp.push(<div>
                                <input type="radio" name={survey[i].questionid} value={opt.options} checked={survey[i].response}
                                       onClick={(e) => this.saveRadio(opt.options, opt.optionid, questionid, e)}/>
                                <label>{opt.options}</label>
                            </div>
                        );
                    } else{
                        temp.push(<div>
                                <input type="radio" name={survey[i].questionid} value={opt.options}
                                       onClick={(e) => this.saveRadio(opt.options, opt.optionid, questionid, e)}/>
                                <label>{opt.options}</label>
                            </div>
                        );
                    }
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
            } else if(survey[i].questiontype === "checkbox"){
                let temp =[];
                if(survey[i].response !== undefined){
                    let res = survey[i].response.split(",");
                    // let opt = survey[i].options;
                    survey[i].options.map((opt, index) =>{
                        for(let n=0; n<res.length; n++){
                            if(opt.options === res[n]){
                                checking = true;
                                temp.push(<div>
                                        <input type="checkbox" checked
                                               onClick={(e) => {
                                                   // this.unCheck(e, index);
                                                   // this.handleInputChange(e);
                                                   this.saveCheckBox(opt.options, opt.optionid, questionid, e);
                                               }}/>
                                        <label>{opt.options}</label>
                                    </div>
                                );
                                break;
                            }
                        }
                        if(checking === false){
                            temp.push(<div>
                                    <input type="checkbox" onClick={(e) => this.saveCheckBox(opt.options, opt.optionid, questionid, e)}/>
                                    <label>{opt.options}</label>
                                </div>
                            );
                        }
                        checking = false;
                    })
                } else{
                    survey[i].options.map((opt, index) => {
                        temp.push(<div>
                                <input type="checkbox" onClick={(e) => this.saveCheckBox(opt.options, opt.optionid, questionid, e)}/>
                                <label>{opt.options}</label>
                            </div>
                        );
                    });
                }
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
                // this.setState({
                //     ...this.state,
                //     textValue: survey[i].response
                // })
                x.push(
                    <Row>
                        <Col xs="1"></Col>
                        <Col xs="5">
                            <textarea width="50" value={survey[i].response + this.state.textValue} placeholder="Your response here"
                                      onBlurCapture={(event) => {
                                          this.setState({
                                              ...this.state,
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
                                value = {survey[i].response}
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
        this.autoSaveSurvey(event.label, questionid, event.value, "")
    }
    saveRadio(option, optionid, questionid, event){
        this.autoSaveSurvey(option, questionid, optionid, "")
    }
    saveCheckBox(option, optionid, questionid, event){
        if(event.target.checked === false){
            this.saveCheckBoxesSurveyValue(option, questionid, optionid, "unCheck");
        } else{
            this.saveCheckBoxesSurveyValue(option, questionid, optionid, "");
        }

    }
    saveText(questionid, event){
        var optionid = null;
        this.autoSaveSurvey(event.target.value, questionid, optionid, "")
    }
    saveRating=(nextValue, questionid, optionid)=>{
        var value = optionid.split(",");
        this.autoSaveSurvey(nextValue, value[1], value[0], "")
    }

    saveCheckBoxesSurveyValue(response, questionid, optionid , questionType){
        var payload = {
            "type": this.state.type,
            "email": this.state.email,
            "response": response,
            "questionid": questionid,
            "optionid": optionid,
            "questionType": questionType
        }
        API.saveCheckBoxSurveyResponse(payload)
            .then(
                response => {
                    if(response.status === 200){
                        console.log("saved");
                        // this.props.history.push('./SurveyQuestions', {surveyData: this.state});
                    } else {
                        // alert("An error occured. Please try again with correc URL");
                    }
                },
                error => {
                    console.log(error.data.message);
                }
            );
    }

    autoSaveSurvey(response, questionid, optionid, questionType){
        var payload = {
            "type": this.state.type,
            "email": this.state.email,
            "response": response,
            "questionid": questionid,
            "optionid": optionid,
            "questionType": questionType
        }

        //API call to backend
        API.saveSurveyResponse(payload)
            .then(
                response => {
                    if(response.status === 200){
                        console.log("saved");
                        // this.props.history.push('./SurveyQuestions', {surveyData: this.state});
                    } else {
                        // alert("An error occured. Please try again with correc URL");
                    }
                },
                error => {
                    console.log(error.data.message);
                }
            );
    }

    submitSurvey(){
        var payload={
            "email": this.state.email,
            "surveyid": this.state.surveyid
        }
        API.submitCloseUniqueSurvey(payload)
            .then(
                response => {
                    if (response.status === 200) {
                        alert("You have Successfully submitted the survey. A confirmation email has been sent to you");
                        if(localStorage.getItem("userEmail") !== null && localStorage.getItem("userEmail") !== ""
                            && localStorage.getItem("userEmail") !== undefined){
                            this.props.history.push("/surveys");
                        } else{
                            this.props.history.push("/");
                        }
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
                        <button className="btn btn-primary share-button"
                                onClick={() => {
                                    this.submitSurvey();
                                }}>Submit Survey</button>
                    </div>
                </div>
            </div>
        )
    }

}
export default withRouter(SurveyQuestions);

