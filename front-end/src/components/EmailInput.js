import React, { Component } from 'react';
import '../styles/SurveyContainer.css';
import { Route, withRouter } from 'react-router-dom';
import { Button, Form, FormGroup, Label, Input } from 'reactstrap';
import * as API from '../api/API';
class EmailInput extends Component{
    constructor(props){
        super(props);
        let id = this.props.surveyid;
        let type = this.props.stype;
        this.state={
            "email":'',
            "surveyid":id,
            "type": type
        }
    }
    handleSignIn(){
        this.props.history.push('/');
    }
    handleEmailInput(){
        //API call to save the email in response and provide email and id both
        // get all the questions of the survey
        var payload= {
            "email":this.state.email,
            "surveyid":this.state.surveyid
        }
        API.validateEmail(payload)
            .then(
                response => {
                    if(response.status === 200){
                        this.props.history.push('./SurveyQuestions', {surveyData: this.state});
                    } else if(response.status === 208){
                        alert(response.data.message);
                    }else if(response.response.status === 404){
                        alert(response.response.data.message);
                    }  else if(response.response.status === 401){
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
            <div>
                <div className="survey-container">
                    <div className="form-design-container">
                        <div className="form-container">
                            <Form>
                                <FormGroup>
                                    <Label for="exampleEmail">Enter your Email</Label>
                                    <Input type="email" placeholder="abc@gmail.com" value={this.state.email}
                                           onChange={(event) => {
                                               this.setState({...this.state,email: event.target.value});
                                           }}required/>
                                    <Button color="primary" onClick={() => {
                                        this.handleEmailInput()
                                    }}>Go</Button>
                                </FormGroup>
                            </Form>
                            <br/>
                                <h3>OR</h3>
                                <Button color="primary" onClick={() => {
                                    this.handleSignIn()
                                }}>SignUp or SignIn</Button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
export default withRouter(EmailInput);