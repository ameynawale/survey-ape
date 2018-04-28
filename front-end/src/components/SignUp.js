import React, { Component } from 'react';
import {Route, withRouter} from 'react-router-dom';
import * as validation from '../validation/InputValidations';
import '../styles/SurveyContainer.css';

class SignUp extends Component {
    constructor(props){
        super(props);
        this.state = {
            email: '',
            password: '',
            firstName: '',
            lastName: '',
            messageDivSignUp : ''
        }
    }
    handleUserSignUp = (event) => {
        var valid = validation.signup(this.state);
        if(valid === ''){
            let payload ={
                firstName: this.state.firstName,
                lastName: this.state.lastName,
                email: this.state.email,
                password: this.state.password,
            }
            // this.UserSignUpAPICall(payload);
        }else{
            this.setState({
                ...this.state,
                messageDivSignUp: valid
            });
            event.preventDefault();
        }
    }
    render() {
        let messageDivSignUp =null;
        if(this.state.messageDivSignUp !== ''){
            messageDivSignUp = <div className="clearfix">
                <div className="alert alert-danger text-center" role="alert">
                    {this.state.messageDivSignUp}
                </div>
            </div>;
        } else{
            messageDivSignUp = <div></div>;
        }
        return (
            <div className="survey-container">
                <div className="form-design-container">
                    <div className="form-container">
                        <div className="form-design-container">
                            <div className="form-group row">
                                <label className="col-sm-12 col-form-label">
                                    All Fields are Mandatory
                                </label>
                            </div>
                            {messageDivSignUp}
                            <div className="form-group row">
                                <label className="col-3 col-form-label">First Name</label>
                                <div className=" col-sm-8">
                                    <input type="text"  className="form-control"  placeholder="John"
                                           value={this.state.firstName}
                                           onChange={(event) => {
                                               this.setState({...this.state,firstName: event.target.value});
                                           }}required/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-3 col-form-label">Last Name</label>
                                <div className=" col-sm-8">
                                    <input type="text"  className="form-control"  placeholder="Smith"
                                           value={this.state.lastName}
                                           onChange={(event) => {
                                               this.setState({...this.state,lastName: event.target.value});
                                           }}required/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-3 col-form-label">Email</label>
                                <div className=" col-sm-8">
                                    <input type="text"  className="form-control" placeholder="abc@gmail.com"
                                           value={this.state.email}
                                           onChange={(event) => {
                                               this.setState({...this.state,email: event.target.value});
                                           }}required/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label className="col-3 col-form-label">Password</label>
                                <div className="col-sm-8">
                                    <input type="password" className="form-control" placeholder="Password"
                                           value={this.state.password}
                                           onChange={(event) => {
                                               this.setState({...this.state,password: event.target.value});
                                           }}required/>
                                </div>
                            </div>
                            <div className="form-group row">
                                <div className="col-sm-11">
                                    <button type="submit" className="btn btn-primary" onClick={this.handleUserSignUp}>Submit</button>
                                    {/*<button type="reset" className="btn btn-primary" onClick={this.cancelLogin}>Clear</button>*/}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default SignUp;

