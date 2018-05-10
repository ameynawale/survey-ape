import React, {Component} from 'react';
import { Route, withRouter } from 'react-router-dom';
import Samplesurveypage from './samplesurveypage';
import SurveyListing from './SurveyListing';
import HomeHeader from "./HomeHeader";
import SignIn from "./SignIn";
import SignUp from './SignUp';
import SignUpVerification from './SignUpVerification';
import Header from './Header';
import SurveyStats from './SurveyStats';
import TakeSurvey from './TakeSurvey';
import SurveyQuestions from './SurveyQuestions';
import EditSurvey from './EditSurvey';
import * as API from '../api/API';

class CustomRouter extends Component {

    constructor(props) {
        super(props);
        this.state = {
            surveyName: '',
            surveyId: '',
            createdbyme: [],
            sharedwithme: [],
            user: {},
            surveydata: {}
        };
    }
     UserSignUpAPICall = (payload) => {
            API.doSignUp(payload)
                .then((res) => {
                    if (res.status === 200) {
                        console.log('email', res.data.email);
                        alert("Please verify your email using the verification code sent to your email to complete registration");
                        this.setState({
                            ...this.state,
                            messageDivSignup: "SignUp successful",
                            user: res.data
                        });
                        this.props.history.push('/signUpVerification');

                    } else if (res.response.status === 400) {
                        alert("user already exists")
                        this.setState({
                            ...this.state,
                            messageDivSignUp: "User already exists"
                        });
                    } else if (res.response.status === 500) {
                        alert("internal server error")
                        this.setState({
                            ...this.state,
                            messageDivSignUp: "Internal server error"
                        });
                    }
                });
        }
    UserSignInAPICall = (payload) => {
            API.doLogin(payload)
                .then((res) => {
                    if (res.status === 200) {
                        console.log('email', res.data.email);
                        this.setState({
                            user: res.data
                        })
                        console.log("state user", this.state.user.email);
                        if(res.data.isactivated === 0){
                            this.setState({
                                ...this.state,
                                messageDivLogin: "SignIn successful",
                                user: res.data
                            });
                            alert("Email is not verified. Please verify your email");
                            this.props.history.push('/signUpVerification');
                        }
                        else{
                            alert("Login successful");
                            localStorage.setItem("userEmail", res.data.email);
                            this.props.history.push('/surveys');
                        }

                    } else if (res.response.status === 404){
                       // alert("successful login");
                        console.log(res);
                            this.setState({
                                ...this.state,
                                messageDivLogin: "Invalid email or password"
                            });
                            console.log(this.state.messageDivLogin);
                            alert(this.state.messageDivLogin);
                    } else if(res.status === 500){
                        alert("Internal Server error, try again");
                            this.setState({
                                ...this.state,
                                messageDivLogin: "Internal Server error, try again"
                            });
                    }
                });
        }
    handleAddSurvey = (surveydata) => {
        if(surveydata.surveytype === 'closed')
        {
            var inviteesEmails = surveydata.invitees.split(',');
            var invitees = [];
            inviteesEmails.forEach((invitee) => invitees.push({"email": invitee}));
            var Survey = {
                surveyname: surveydata.surveyname,
                surveytype: surveydata.surveytype,
                validity: surveydata.validity,
                invitees: invitees
            }

        }
        else
        {
            var Survey = {
                surveyname: surveydata.surveyname,
                surveytype: surveydata.surveytype,
                validity: surveydata.validity
            }
        }

        console.log('inside handleAddSurvey');

        API.addSurvey(Survey)
            .then((res) => {
                console.log(res);
                if(res.status === 201)
                {
                    console.log(res.status + " " + res.data.surveyid);
                    this.setState({
                        surveyname: res.data.surveyname,
                        surveyid: res.data.surveyid
                    });
                    this.props.history.push("/CreateSurvey");
                }
            })
    }

    handleGetSurveyListing = () => {
        API.getSurveyListing()
            .then((res) => {
                if(res.status === 200)
                {
                    this.setState({
                        createdbyme: res.createdbyme,
                        sharedwithme: res.sharedwithme
                    });
                    this.props.history.push("/surveys");
                }
            })
    }

    editSurvey = (surveydata) => {
        console.log("editurvey", surveydata.surveyid);
        API.getQuestions(surveydata)
            .then((res) => {
                console.log("get questions", res.data);
                var surveydata = {
                    // surveyid: surveydata.surveyid,
                    questions: res.data
                }
                this.setState({
                    surveydata: surveydata
                });
                console.log("after edit ", this.state.surveydata);
                this.props.history.push('/editsurvey');
            })
        /*this.setState({
            surveydata: surveydata
        },function () {
            console.log("surveyid", this.state.surveydata.surveyid);
            this.props.history.push('/editsurvey');
        })*/


    }


    render() {
        return (
            <div>
                <Route exact path="/" render={() => (
                    <div>
                        <HomeHeader/>
                        <SignIn history={this.props.history} SignIn={ this.UserSignInAPICall} messageDivLogin={this.state.messageDivLogin}/>
                        {/*<Samplesurveypage/>*/}
                    </div>
                )}/>
                <Route exact path="/signUp" render={() => (
                    <div>
                        <HomeHeader/>
                        <SignUp history={this.props.history} SignUp={ this.UserSignUpAPICall} messageDivSignUp={this.state.messageDivSignUp}/>
                    </div>
                )}/>
                <Route exact path="/signUpVerification" render={() => (
                    <div>
                        <HomeHeader/>
                        <SignUpVerification history={this.props.history} user={this.state.user}/>
                    </div>
                )}/>
                <Route exact path="/CreateSurvey" render={() => (
                    <div>
                        <Header history={this.props.history} handleAddSurvey={this.handleAddSurvey} handleGetSurveyListing={this.handleGetSurveyListing}/>
                        <Samplesurveypage surveydata={this.state}/>
                    </div>
                )}/>
                <Route exact path="/editsurvey" render={() => (
                    <div>
                        <Header history={this.props.history} handleAddSurvey={this.handleAddSurvey} handleGetSurveyListing={this.handleGetSurveyListing}/>
                        <EditSurvey surveydata={this.state.surveydata}/>
                    </div>
                )}/>
                <Route exact path="/surveys" render={() => (
                    <div>
                        <Header history={this.props.history} handleAddSurvey={this.handleAddSurvey} handleGetSurveyListing={this.handleGetSurveyListing}/>
                        <SurveyListing surveydata={this.state} editSurvey={this.editSurvey}/>
                    </div>
                )}/>
                <Route exact path="/surveyStats" render={(selectedSurvey) => (
                    <div>
                        <Header history={this.props.history} handleAddSurvey={this.handleAddSurvey} handleGetSurveyListing={this.handleGetSurveyListing}/>
                        <SurveyStats selectedSurvey={selectedSurvey.location.state.selectedSurvey}/>
                    </div>
                )}/>
                <Route exact path="/general" render={(data) => (
                    <div>
                        <TakeSurvey urlData={data} type={"general"}/>
                    </div>
                )}/>
                <Route exact path="/closed" render={(data) => (
                    <div>
                        <TakeSurvey urlData={data} type={"close"}/>
                    </div>
                )}/>
                <Route exact path="/unique" render={(data) => (
                    <div>
                        <TakeSurvey urlData={data} type={"unique"}/>
                    </div>
                )}/>
                <Route exact path="/SurveyQuestions" render={(surveyData) => (
                    <div>
                        <SurveyQuestions surveyData={surveyData.location.state.surveyData}/>
                    </div>
                )}/>
            </div>
        );
    }
}


export default withRouter(CustomRouter);
