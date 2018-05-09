import React, { Component } from 'react';
import {Route, withRouter} from 'react-router-dom';
import * as validation from '../validation/InputValidations';
import '../styles/SurveyContainer.css';
import * as API from '../api/API';

class SignUpVerification extends Component {
    constructor(props){
        super(props);
        this.state = {
            verificationCode: '',
            messageDivSignUpVerification : '',
            user: this.props.user
        }
    }
    handleSignUpVerification = (event) => {
        var valid = validation.signUpVerification(this.state);
        if(valid === ''){
            let payload ={
                code: this.state.verificationCode,
                email: this.state.user.email
            }
            this.UserSignUpVerificationAPICall(payload);
        }else{
            this.setState({
                ...this.state,
                messageDivSignUpVerification: valid
            });
            event.preventDefault();
        }
    }

    UserSignUpVerificationAPICall = (payload) => {
        API.doSignUpVerification(payload)
            .then((res) => {
                if (res.status === 200) {
                    alert("Verification successful! You can sign in now");
                    // this.props.history.push('/surveys');
                    this.props.history.push('/');
                } else if (res.response.status === 400) {
                    alert("Incorrect verification code")
                    this.setState({
                        ...this.state,
                        messageDivSignUpVerification: "Incorrect verification code"
                    });
                } else if (res.response.status === 500) {
                    alert("internal server error")
                    this.setState({
                        ...this.state,
                        messageDivSignUpVerification: "Internal server error"
                    });
                }
            });
    }

    render() {
        let messageDivSignUpVerification =null;
        if(this.state.messageDivSignUpVerification !== ''){
            messageDivSignUpVerification = <div className="clearfix">
                <div className="alert alert-danger text-center" role="alert">
                    {this.state.messageDivSignUpVerification}
                </div>
            </div>;
        } else{
            messageDivSignUpVerification = <div></div>;
        }
        return (
            <div className="survey-container">
                <div className="form-design-container">
                    <div className="form-container">
                        <div className="form-design-container">
                            <div className="form-group row">
                                <label className="col-sm-12 col-form-label">
                                    Please enter the verification code sent to your registered email
                                </label>
                            </div>
                            {messageDivSignUpVerification}
                            <div className="form-group row">
                                <label className="col-3 col-form-label">Enter verification code</label>
                                <div className=" col-sm-8">
                                    <input type="text"  className="form-control"  placeholder="Verification code"
                                           value={this.state.verificationCode}
                                           onChange={(event) => {
                                               this.setState({...this.state,verificationCode: event.target.value});
                                           }}required/>
                                </div>
                            </div>


                            <div className="form-group row">
                                <div className="col-sm-11">
                                    <button type="submit" className="btn btn-primary" onClick={this.handleSignUpVerification}>Submit</button>
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

export default SignUpVerification;
