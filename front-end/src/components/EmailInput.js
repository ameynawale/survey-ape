import React, { Component } from 'react';
import '../styles/SurveyContainer.css';
import { Route, withRouter } from 'react-router-dom';
import { Button, Form, FormGroup, Label, Input } from 'reactstrap';

class EmailInput extends Component{
    constructor(props){
        super(props);
        let id = this.props.surveyid;
        this.state={
            "email":'',
            "surveyid":id
        }
    }
    handleSignIn(){
        this.props.history.push('/');
    }
    handleEmailInput(){
        //API call to save the email in response and provide email and id both
        // get all the questions of the survey
        this.props.history.push('./SurveyQuestions', {surveyData: this.state});
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