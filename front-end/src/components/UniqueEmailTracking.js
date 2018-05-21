import React, { Component } from 'react';
import '../styles/SurveyContainer.css';
import { Route, withRouter } from 'react-router-dom';
import { Button, Form, FormGroup, Label, Input } from 'reactstrap';
import * as API from '../api/API';
class UniqueEmailTracking extends Component{
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
    handleEmailInput(){
        var payload= {
            "email":this.state.email,
            "surveyid":this.state.surveyid,
            "type": this.state.type
        }
        this.props.history.push('./SurveyQuestions', {surveyData: payload});
    }
    render(){
        return(
            <div>
                <div className="survey-container">
                    <div className="form-design-container">
                        <div className="form-container">
                            <Form>
                                <FormGroup>
                                    <Label for="exampleEmail">Please Enter your email again on which you received the URL. This is for tracking purposes</Label>
                                    <Input type="email" placeholder="abc@gmail.com" value={this.state.email}
                                           onChange={(event) => {
                                               this.setState({...this.state,email: event.target.value});
                                           }}required/>
                                    <Button color="primary" onClick={() => {
                                        this.handleEmailInput()
                                    }}>Start Survey</Button>
                                </FormGroup>
                            </Form>
                            <br/>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
export default withRouter(UniqueEmailTracking);