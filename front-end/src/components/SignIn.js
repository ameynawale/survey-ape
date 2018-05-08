import React, { Component } from 'react';
import * as validation from '../validation/InputValidations';
import '../styles/SurveyContainer.css';
import * as API from '../api/API';

class SignIn extends Component {
    constructor(props){
        super(props);
        this.state = {
            email: '',
            password: '',
            messageDivLogin : ''
        }

    }

    handleUserSignIn = (event) => {
        var valid = validation.login(this.state);
        if(valid === ''){
            let payload ={
                email: this.state.email,
                password: this.state.password,
            }
            this.props.SignIn(payload);
            this.setState({
                messageDivLogin: this.props.messageDivLogin
            })

        }else{
            this.setState({
                ...this.state,
                messageDivLogin: valid
            });
            event.preventDefault();
        }
    }



    cancelLogin =()=>{
        this.setState({
            ...this.state,
            email: '',
            password: ''
        });
    }
    render() {
        let messageDivLogin =null;
        if(this.state.messageDivLogin !== ''){
            messageDivLogin = <div className="clearfix">
                                <div className="alert alert-danger text-center" role="alert">
                                    {this.state.messageDivLogin}
                                </div>
                              </div>;
        } else{
            messageDivLogin = <div></div>;
        }

        return (
                <div className="survey-container">

                    <div className="form-design-container">
                        <div className="form-container">
                            <div className="form-design-container">
                                {/*<div className="form-inline">*/}
                                    {/*<div className="row justify-content-md-center">*/}
                                        <div className="form-group row">
                                            <label className="col-sm-12 col-form-label">
                                                All Fields are Mandatory
                                            </label>
                                        </div>
                                        {messageDivLogin}
                                        <div className="form-group row">
                                            <label className="col-3 col-form-label">Email</label>
                                            <div className=" col-sm-8">
                                                <input type="text"  className="form-control"  placeholder="abc@gmail.com"
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
                                                <button type="submit" className="btn btn-primary" onClick={this.handleUserSignIn}>Submit</button>
                                                {/*<button type="reset" className="btn btn-primary" onClick={this.cancelLogin}>Clear</button>*/}
                                            </div>
                                        </div>
                                        {/*<input type="button" onClick={this.handleUserLogIn}>SignIn</input>*/}
                                        {/*<input type="button" onClick={this.cancelLogin}>Cancel</input>*/}
                                    {/*</div>*/}

                                {/*</div>*/}
                            {/*</div>*/}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default SignIn;

