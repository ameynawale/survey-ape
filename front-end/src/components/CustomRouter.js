import React, {Component} from 'react';
import { Route, withRouter } from 'react-router-dom';
import Samplesurveypage from './samplesurveypage';
import SurveyListing from './SurveyListing';
import HomeHeader from "./HomeHeader";
import SignIn from "./SignIn";
import SignUp from './SignUp';
import SignUpVerification from './SignUpVerification';
import Header from './Header';
import * as API from '../api/API';

class CustomRouter extends Component {

    constructor(props) {
        super(props);
        this.state = {
            surveyName: '',
            surveyId: '',
            createdbyme: [],
            sharedwithme: []
        };
    }


    handleAddSurvey = (surveydata) => {
        API.addSurvey(surveydata)
            .then((res) => {
                if(res.status === 201)
                {
                    this.setState({
                        surveyName: res.surveyName,
                        surveyId: res.surveyId
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

    render() {
        return (
            <div>
                <Route exact path="/" render={() => (
                    <div>
                        <HomeHeader/>
                        <SignIn history={this.props.history}/>
                        {/*<Samplesurveypage/>*/}
                    </div>
                )}/>
                <Route exact path="/signUp" render={() => (
                    <div>
                        <HomeHeader/>
                        <SignUp history={this.props.history}/>
                    </div>
                )}/>
                <Route exact path="/signUpVerification" render={() => (
                    <div>
                        <HomeHeader/>
                        <SignUpVerification history={this.props.history}/>
                    </div>
                )}/>
                <Route exact path="/CreateSurvey" render={() => (
                    <div>
                        <Header handleAddSurvey={this.handleAddSurvey} handleGetSurveyListing={this.handleGetSurveyListing}/>
                        <Samplesurveypage surveydata={this.state}/>
                    </div>
                )}/>
                <Route exact path="/surveys" render={() => (
                    <div>
                        <Header handleAddSurvey={this.handleAddSurvey} handleGetSurveyListing={this.handleGetSurveyListing}/>
                        <SurveyListing surveydata={this.state}/>
                    </div>
                )}/>
            </div>
        );
    }
}

export default withRouter(CustomRouter);